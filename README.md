# 🏍️ BikeSport (ms-motosport)

Plataforma de arriendo de motos/bicicletas construida con una arquitectura de
**microservicios** en Spring Boot, con un **BFF (Backend For Frontend)** como
única puerta de entrada, autenticación centralizada vía **JWT firmado con
RSA (RS256)**, y persistencia en **MySQL** (una base por servicio).

---

## 1. Arquitectura general

```
                         ┌──────────────┐
   Cliente (web/app) ──▶ │     BFF      │  puerto 8080
                         │ (Spring Boot)│
                         └──────┬───────┘
                                │  (valida JWT con la JWK pública de ms-auth
                                │   y reenvía el header Authorization)
        ┌───────────────┬──────┼──────────────┬────────────────┐
        ▼               ▼      ▼              ▼                ▼
  ┌───────────┐  ┌────────────┐        ┌────────────┐   ┌────────────┐
  │  ms-auth  │  │  ms-bike   │        │ ms-customer│   │  ms-rent   │
  │  :4004    │  │  :4003     │        │  :4002     │   │  :4001     │
  └─────┬─────┘  └─────┬──────┘        └─────┬──────┘   └─────┬──────┘
        │              │                     │                │
     (sin BD)     mysql_bike:3309      mysql_customer:3308  mysql_rent:3307
```

- **El BFF es la única puerta de entrada real para el negocio.** Los
  microservicios internos (`ms-bike`, `ms-customer`, `ms-rent`) no tienen
  seguridad propia (no validan JWT): confían en que solo el BFF les habla.
  En un entorno productivo, estos puertos deberían quedar en una red
  interna, no expuestos a internet.
- **`ms-auth`** es el único servicio con lógica de negocio de usuarios. Emite
  los tokens y publica su llave pública en formato JWK para que el BFF
  pueda validar la firma sin tener que llamarlo en cada request.
- **`ms-rent`** es el orquestador de negocio dentro del BFF: al crear/eliminar
  un arriendo, el BFF consulta a `ms-bike` y `ms-customer`, valida reglas de
  negocio, y sincroniza la disponibilidad de la moto.

---

## 2. Servicios y puertos

| Servicio     | Puerto | Responsabilidad                                              | Base de datos       |
|--------------|--------|---------------------------------------------------------------|----------------------|
| `bff`        | 8080   | Punto de entrada único, login/registro, seguridad JWT, orquestación de arriendos | - (sin BD propia) |
| `ms-auth`    | 4004   | Registro/login de usuarios, emisión de JWT (RS256), expone JWKS | - (en memoria/JPA, sin BD en `docker-compose.yml` actual) |
| `ms-bike`    | 4003   | CRUD de motos, disponibilidad, kilometraje                    | `bike_db` (:3309)   |
| `ms-customer`| 4002   | CRUD de clientes, validación de licencia de conducir           | `customer_db` (:3308)|
| `ms-rent`    | 4001   | CRUD de arriendos (persistencia pura, sin reglas de negocio)   | `rent_db` (:3307)   |

> Nota: `docker-compose.yml` solo levanta las 3 bases de datos MySQL
> (rent, customer, bike). Los 5 servicios Spring Boot (bff, ms-auth,
> ms-bike, ms-customer, ms-rent) se ejecutan aparte, típicamente con
> `./gradlew bootRun` en cada carpeta.

---

## 3. Flujo de autenticación (login)

1. El cliente llama `POST /login` **al BFF** (`:8080`), con `email` y
   `password`.
2. El BFF reenvía la petición a `ms-auth` → `POST /api/auth/login` (`:4004`).
3. `ms-auth` busca el usuario, valida el password con BCrypt y, si es
   correcto, firma un JWT **RS256** con:
   - `sub`: email del usuario
   - `claim "name"` y `claim "role"`
   - `iss`: emisor configurado (`security.jwt.issuer`)
   - `aud`: `["bff"]`
   - expiración configurable (`security.jwt.access-token-minutes`, 30 min por defecto)
4. `ms-auth` firma con su llave **privada** (`ms-auth/keys/private_key.pem`) y
   expone la llave **pública** correspondiente en formato JWK en:
   ```
   GET http://localhost:4004/.well-known/jwks.json
   ```
5. El BFF, como *OAuth2 Resource Server*, usa esa URL (`jwk-set-uri`) para
   descargar la llave pública y **validar la firma de cada token** que
   recibe en el header `Authorization: Bearer <token>` en los endpoints
   protegidos (`/bikes`, `/customers`, `/rents`).
6. En cada llamada del BFF hacia los microservicios internos, un
   interceptor (`RestClientConfig`) reenvía el mismo header `Authorization`
   que llegó del cliente, para no perder el contexto (aunque hoy los
   microservicios internos no lo validan).

El registro (`POST /register`) sigue el mismo camino: el BFF llama a
`ms-auth` para crear el usuario y, si tiene éxito, hace login automático
para devolver el token en la misma respuesta.

---

## 4. Endpoints expuestos por el BFF

Todos los endpoints de negocio se consumen **a través del BFF**
(`http://localhost:8080`), nunca directo contra los microservicios internos.

| Método | Ruta            | Auth requerida | Descripción                          |
|--------|-----------------|----------------|----------------------------------------|
| GET    | `/api/health`   | No             | Health check del BFF                   |
| POST   | `/login`        | No             | Login, devuelve `accessToken`          |
| POST   | `/register`     | No             | Registro + login automático            |
| GET    | `/bikes`        | Sí (Bearer)    | Listar motos                           |
| GET    | `/bikes/{id}`   | Sí (Bearer)    | Detalle de una moto                    |
| POST   | `/bikes`        | Sí (Bearer)    | Crear moto                             |
| PUT    | `/bikes/{id}`   | Sí (Bearer)    | Actualizar moto                        |
| DELETE | `/bikes/{id}`   | Sí (Bearer)    | Eliminar moto                          |
| GET    | `/customers`    | Sí (Bearer)    | Listar clientes                        |
| GET    | `/customers/{id}`| Sí (Bearer)   | Detalle de cliente                     |
| POST   | `/customers`    | Sí (Bearer)    | Crear cliente                          |
| PUT    | `/customers/{id}`| Sí (Bearer)   | Actualizar cliente                     |
| DELETE | `/customers/{id}`| Sí (Bearer)   | Eliminar cliente                       |
| GET    | `/rents`        | Sí (Bearer)    | Listar arriendos                       |
| GET    | `/rents/{id}`   | Sí (Bearer)    | Detalle de arriendo                    |
| POST   | `/rents`        | Sí (Bearer)    | Crear arriendo (valida moto/cliente)   |
| PUT    | `/rents/{id}`   | Sí (Bearer)    | Actualizar arriendo                    |
| DELETE | `/rents/{id}`   | Sí (Bearer)    | Eliminar arriendo (libera la moto)     |

Documentación interactiva (Swagger) del BFF, una vez levantado:
`http://localhost:8080/swagger-ui.html`

### Reglas de negocio al crear/eliminar un arriendo (`RentService` del BFF)

- No se puede arrendar una moto con `disponibilidad = false`.
- No se puede arrendar a un cliente con `fechaVencimiento` de licencia en el
  pasado.
- `fechaFin` no puede ser anterior a `fechaInicio`.
- Al crear un arriendo exitosamente, la moto se marca automáticamente
  `disponibilidad = false`.
- Al eliminar un arriendo, la moto vuelve a `disponibilidad = true`.

---

## 5. Tecnologías

- Java 21 (toolchain declarado en `build.gradle`: Java 25 / Spring Boot 4)
- Spring Boot 4, Spring Web (WebMVC), Spring Security, Spring Data JPA
- Spring Security OAuth2 Resource Server (validación JWT en el BFF)
- `spring-security-oauth2-jose` / Nimbus (emisión JWT RSA en `ms-auth`)
- MySQL 8 + Flyway (migraciones) en `ms-bike`, `ms-customer`, `ms-rent`
- springdoc-openapi (Swagger UI) en el BFF y `ms-bike`
- Gradle (wrapper incluido en cada módulo)
- Docker Compose (solo para las bases de datos)

---

## 6. Cómo levantar el proyecto localmente

### 6.1 Bases de datos

```bash
docker compose up -d
```

Esto levanta `mysql_rent` (:3307), `mysql_customer` (:3308) y
`mysql_bike` (:3309).

### 6.2 Microservicios

En terminales separadas, desde la raíz del repo:

```bash
cd ms-auth   && ./gradlew bootRun    # :4004
cd ms-bike   && ./gradlew bootRun    # :4003
cd ms-customer && ./gradlew bootRun  # :4002
cd ms-rent   && ./gradlew bootRun    # :4001
cd bff       && ./gradlew bootRun    # :8080
```

> Levanta `ms-auth` **antes** que el `bff`: el BFF necesita descargar la
> JWK pública de `ms-auth` (`/.well-known/jwks.json`) para poder validar
> tokens. Si `ms-auth` no está disponible cuando llega la primera petición
> protegida al BFF, esa validación fallará.

### 6.3 Probar los endpoints

Se incluye `bff-api-tests.http` con toda la batería de pruebas (login,
registro, casos de error, y CRUD completo de bikes/customers/rents a
través del BFF). Se puede ejecutar con la extensión **REST Client** de
VS Code o directamente con el HTTP Client de IntelliJ.

Flujo recomendado dentro de ese archivo:
1. Sección 1: health check.
2. Sección 2-3: registro y login (guarda el `accessToken` recibido).
3. Sección 4: confirma que sin token todo responde 401.
4. Secciones 5-7: CRUD de bikes, customers y el flujo completo de rents
   (incluye casos de error esperados: moto no disponible, licencia
   vencida, fechas inválidas, IDs inexistentes).

---

## 7. Problemas encontrados y corregidos durante esta revisión

Al revisar el código para poder probar el login de punta a punta, se
encontraron **dos bugs de configuración que impedían que los servicios
arrancaran**. Ya fueron corregidos en este repo:

1. **`ms-auth` no arrancaba.** `JwtKeyConfig` y `JwtService` requieren las
   propiedades `security.jwt.private-key-path`, `security.jwt.public-key-path`
   y `security.jwt.issuer`, pero no estaban definidas en
   `application.properties` (aunque las llaves `.pem` sí existían en
   `ms-auth/keys/`). Sin esas propiedades, Spring falla al resolver los
   placeholders y el contexto no levanta.
   → Se agregaron las tres propiedades apuntando a las llaves existentes.

2. **El `bff` no arrancaba.** `SecurityConfig` configura
   `oauth2ResourceServer().jwt(...)` pero no había ningún
   `spring.security.oauth2.resourceserver.jwt.jwk-set-uri` (ni
   `issuer-uri`, ni un bean `JwtDecoder` manual). Sin eso, Spring Security
   no tiene cómo construir el decodificador de JWT y falla al construir el
   `SecurityFilterChain`.
   → Se agregó `jwk-set-uri` apuntando al endpoint JWKS de `ms-auth`.

### Known issues (no corregidos, pendientes si quieres que los aborde)

- **El BFF no tiene un `@RestControllerAdvice` / manejo global de
  excepciones.** Esto significa que errores esperables van a llegar como
  `500 Internal Server Error` en vez del código correcto:
  - Login con credenciales inválidas → hoy probablemente `500` en vez de `401`.
  - Reglas de negocio de `RentService` (`IllegalArgumentException`: moto no
    disponible, licencia vencida, fechas inválidas) → hoy `500` en vez de `400`.
  - Recurso no encontrado en un microservicio interno (moto/cliente/arriendo
    inexistente) → hoy `500` en vez de `404`.
  El archivo de pruebas incluido (`bff-api-tests.http`) marca estos casos
  como "known issue" para que puedas verificarlo tú mismo al ejecutar.
- Los microservicios internos (`ms-bike`, `ms-customer`, `ms-rent`) no
  validan el JWT — cualquiera con acceso de red a esos puertos puede
  llamarlos directo, sin pasar por el BFF ni por el login.
- `docker-compose.yml` no incluye una base de datos para `ms-auth` (los
  usuarios se persisten vía JPA pero no hay datasource declarado en su
  `application.properties`); revisa que tu configuración de base de datos
  para `ms-auth` esté completa antes de probar el registro.

---

## 8. Estructura del repositorio

```
ms-motosport/
├── bff/            # Backend For Frontend (puerto 8080)
├── ms-auth/        # Autenticación y emisión de JWT (puerto 4004)
├── ms-bike/        # CRUD de motos (puerto 4003)
├── ms-customer/    # CRUD de clientes (puerto 4002)
├── ms-rent/        # CRUD de arriendos (puerto 4001)
├── docker-compose.yml   # Bases de datos MySQL
└── README.md
```
