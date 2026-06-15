# ms-motosport
# 🏍️ BikeSport Microservices

Sistema de arriendo de bikes desarrollado con arquitectura de microservicios utilizando Spring Boot, JWT y MySQL.

---

# 📌 Descripción

BikeSport es una plataforma de arriendo de bikes basada en microservicios.

El microservicio principal es:

# 📄 Arriendo Service

Este servicio:
- centraliza la lógica de negocio,
- autentica usuarios con JWT,
- consulta customers,
- consulta bikes,
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
- Consulta Customer MS
- Consulta Bike MS
- Validación de disponibilidad
- Cambio automático de disponibilidad de bikes

---

## 👤 Customer Service

Puerto:

```text
4002
```

Funciones:
- CRUD de customers
- Validaciones
- Gestión de licencias

---

## 🏍️ Bike Service

Puerto:

```text
4003
```

Funciones:
- CRUD de bikes
- Control de disponibilidad
- Gestión de kilometraje

---