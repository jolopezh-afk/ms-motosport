# ms-motosport
# 🏍️ MotoSport Microservices

Sistema de arriendo de motos desarrollado con arquitectura de microservicios utilizando Spring Boot, JWT y MySQL.

---

# 📌 Descripción

MotoSport es una plataforma de arriendo de motos basada en microservicios.

El microservicio principal es:

# 📄 Arriendo Service

Este servicio:
- centraliza la lógica de negocio,
- autentica usuarios con JWT,
- consulta clientes,
- consulta motos,
- valida disponibilidad,
- gestiona arriendos.

Los demás microservicios funcionan como servicios auxiliares.

---

# 🚀 Tecnologías utilizadas

- Java 25
- Spring Boot 4
- Spring Security
- JWT
- Spring Data JPA
- Spring Validation
- MySQL 8
- Docker
- Docker Compose
- Gradle
- Flyway

---

# 📂 Microservicios

## 📄 Arriendo Service (Principal)

Puerto:

```text
4001
```

Funciones:
- CRUD de arriendos
- JWT Authentication
- Login y registro
- Consulta Cliente MS
- Consulta Moto MS
- Validación de disponibilidad
- Cambio automático de disponibilidad de motos

---

## 👤 Cliente Service

Puerto:

```text
4002
```

Funciones:
- CRUD de clientes
- Validaciones
- Gestión de licencias

---

## 🏍️ Moto Service

Puerto:

```text
4003
```

Funciones:
- CRUD de motos
- Control de disponibilidad
- Gestión de kilometraje

---