# 📚 Biblioteca – Proyecto Spring Boot + MySQL + Docker

Este proyecto implementa un sistema de gestión de biblioteca utilizando **Spring Boot**,  
**MySQL con Docker**, y una arquitectura modular y mantenible.  
Incluye API REST, persistencia con JPA, subida de imágenes y despliegue automático con Docker Compose.

---

# 🚀 Getting Started

Este documento explica cómo ejecutar el proyecto, cómo funciona su arquitectura,  
cómo se estructura el código y las tecnologías principales utilizadas.

---

## 🐳 Ejecutar el Proyecto con Docker

### ✔ Requisitos previos

- **Docker**
- **Docker Compose**

### ▶ Levantar los servicios

Desde la raíz del proyecto ejecutar:

```bash
docker-compose up --build
o
sudo docker compose up -d
```

Esto despliega:

| Servicio                  | URL                                   |
|---------------------------|---------------------------------------|
| **API Backend (Spring Boot)** | http://localhost:8080                |
| **Swagger UI**            | http://localhost:8080/swagger-ui.html |
| **MySQL**                 | localhost:3306                        |


Para detener los servicios:

```bash
docker-compose down
o
sudo docker compose down
```

### ✔ Persistencia

Los datos NO se pierden gracias a:

- `mysql_data/` → Base de datos MySQL persistente  
- `app/uploads/images/` → Imágenes de usuarios/libros  

---

# 📁 Estructura del Proyecto

```
├── app
│   ├── biblioteca-0.0.1-SNAPSHOT.jar   → Backend Spring Boot empaquetado
│   ├── Dockerfile                       → Imagen personalizada del backend
│   ├── META-INF/MANIFEST.MF
│   └── uploads/images                   → Archivos persistentes
│
├── docker-compose.yml                   → Orquestación Docker
│
├── mysql_data/                          → Volumen persistente de MySQL
│   ├── ibdata1, redo logs, certificados, tablas...
│
└── README.md
```

---

# 🏛 Arquitectura del Backend

El backend está desarrollado siguiendo una arquitectura limpia y organizada por capas:

### 🔹 **Controllers**
Rutas REST que reciben peticiones externas.

### 🔹 **Services**
Contienen la lógica de negocio central.

### 🔹 **Repositories (JPA)**
Acceso y manipulación de datos en MySQL.

### 🔹 **Entities**
Representan las tablas de la base de datos.

### 🔹 **DTOs**
Modelos que comunican API ↔ usuario.

### 🔹 **Uploads**
Gestión de archivos de imagen en `/uploads/images/`.

---

# 🐬 Base de Datos MySQL (Docker)

El contenedor MySQL se configura automáticamente mediante `docker-compose.yml`.

Ejemplo del archivo:

```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: mysql_proyecto
    environment:
      MYSQL_DATABASE: proyecto_11
      MYSQL_USER: nombre_1
      MYSQL_PASSWORD: contra_1
```
