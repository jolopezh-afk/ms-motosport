# MotoSport

Proyecto multi-módulo en Java/Gradle para funcionalidades relacionadas con concesionaria y gestión clínica (módulos varios).

## Estructura del repositorio (actual)

- `arriendo/` — Módulo con su propio `build.gradle` y estructura Gradle (incluye `bin/`, `src/`, `gradle/wrapper`).
- `cliente/` — Módulo del cliente con configuración Gradle, `bin/`, `build/` y `src/`.
- `moto/` — Módulo de gestión de motos, con su propio `build.gradle`, `bin/`, `build/` y `src/`.
- `docker-compose.yml` — Orquestador Docker localizado en la raíz del proyecto (usa para levantar servicios dependientes).

Subproyectos relacionados con el área hospitalaria (dentro del workspace hay una carpeta `hospital/` con varios submódulos):

- `hospital/` — Contiene `docker-compose.yml` y submódulos:
  - `consulta/` — Módulo con `build.gradle`, `bin/main` (propiedades, plantillas, paquetes `com.hospital_vm`), `src/` y `build/`.
  - `fichaclinica/` — Módulo con estructura Gradle y código en `src/`.
  - `paciente/` — Módulo con estructura Gradle y código en `src/`.

Nota: cada submódulo sigue el patrón típico Gradle (carpetas `src/main/java`, `src/main/resources`, `build/`, `gradle/wrapper`).

## Descripción rápida

Este repositorio agrupa varios módulos Java/Gradle que pueden ejecutarse y desarrollarse de forma independiente. Algunos módulos (por ejemplo `cliente`, `moto`, `consulta`) parecen preparados para ejecutarse como aplicaciones Spring Boot (o similares) dado el árbol `build/bootRun` y presencia de `application.properties` en `bin/main`.

## Comandos útiles

- Compilar todo el workspace (desde la raíz):

```powershell
./gradlew build
```

- En Windows usa `gradlew.bat` cuando corresponda:

```powershell
gradlew.bat build
```

- Ejecutar un módulo en particular (ejemplo `cliente`):

```powershell
cd cliente
./gradlew bootRun
```

- Levantar servicios con Docker Compose (si aplica):

```powershell
docker compose up --build
```

## Notas y recomendaciones

- Revisa `bin/main/application.properties` dentro de cada módulo para configuraciones específicas de entorno.
- Cada submódulo mantiene su propio `build.gradle` y puede requerir comandos desde su carpeta para `bootRun` o pruebas.
- Si quieres que actualice la descripción de algún módulo en particular (por ejemplo, añadir dependencias, pasos de ejecución o endpoints relevantes), dime cuál y lo agrego.

## Próximos pasos sugeridos

- Commit del `README.md` actualizado.
- (Opcional) Añadir un `LICENSE` y un `CONTRIBUTING.md` si se prevé colaboración externa.
## 📄 Licencia

