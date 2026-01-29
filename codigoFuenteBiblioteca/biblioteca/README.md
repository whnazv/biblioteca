# 📚 Biblioteca – Proyecto Spring Boot + MySQL + Docker

Este proyecto implementa un sistema de gestión de biblioteca utilizando **Spring Boot**,  
**MySQL en contenedor Docker**, y una arquitectura modular basada en controladores, servicios, repositorios y entidades.  
Incluye API REST, vistas Thymeleaf, persistencia con JPA, manejo de imágenes y despliegue mediante Docker Compose.

---

# 🚀 Getting Started

Este documento explica cómo ejecutar el proyecto, cómo funciona su estructura interna  
y cómo levantar la base de datos y el backend correctamente utilizando Docker.

---

# 🐳 Ejecutar el Proyecto con Docker

## ✔ Requisitos previos

- Docker instalado
- Docker Compose instalado

Puedes verificarlo con:

    docker --version
    docker compose version

---

## ▶ Levantar todos los servicios (Backend + MySQL)

Desde la **raíz del proyecto** ejecutar:

    docker-compose up --build
    o
    sudo docker compose up -d

Servicios que se despliegan:

| Servicio                      | URL                                   |
|-------------------------------|----------------------------------------|
| Backend API (Spring Boot)     | http://localhost:8080                 |
| Swagger UI                    | http://localhost:8080/swagger-ui.html |
| MySQL                         | localhost:3306                        |

Para detener:

    docker-compose down
    o
    sudo docker compose down

---

# ✔ Persistencia

El proyecto mantiene los datos incluso si el contenedor se detiene gracias a:

- **mysql_data/** → Volume de la base de datos MySQL
- **app/uploads/images/** → Imágenes subidas por usuarios y libros

Ninguno de estos datos se elimina al detener los contenedores.

---

# 📁 Estructura del Proyecto
```
 📁 Estructura del Proyecto

 ├── codigoFuenteBiblioteca
 │   ├── docker-compose.yml   → Orquestación Docker (MySQL)
 │   └── README.md            → Documentación de la base
 │
 ├── src
 │   ├── main
 │   │   ├── java
 │   │   │   └── onion/whnazv/biblioteca
 │   │   │       ├── application
 │   │   │       │   ├── port
 │   │   │       │   │   ├── in/        → Interfaces de casos de uso (BookUseCase, SaleUseCase, UserUseCase…)
 │   │   │       │   │   └── out/       → Interfaces de repositorios (BookRepositoryPort, UserRepositoryPort…)
 │   │   │       │   └── usecase/       → Implementaciones de servicios (BookService, SaleService, UserService…)
 │   │   │       ├── domain
 │   │   │       │   ├── exception/     → Excepciones personalizadas y manejador global
 │   │   │       │   └── model/         → Entidades de dominio (Book, Sale, User, Cart…)
 │   │   │       ├── infrastructure
 │   │   │       │   ├── adapter
 │   │   │       │   │   ├── in
 │   │   │       │   │   │   ├── dto/   → DTOs para comunicación (BookDto, UserDto, SaleDto…)
 │   │   │       │   │   │   ├── rest/  → Controladores REST (AdminBookRestController, AuthRestController…)
 │   │   │       │   │   │   └── web/   → Controladores MVC con Thymeleaf (AdminUserController, BookController…)
 │   │   │       │   │   └── out/persistence
 │   │   │       │   │       ├── entity/     → Entidades JPA (BookEntity, UserEntity, SaleEntity…)
 │   │   │       │   │       ├── repository/ → Repositorios Spring Data JPA
 │   │   │       │   │       └── adapters    → Implementaciones de repositorios
 │   │   │       │   ├── mapper
 │   │   │       │   │   ├── dto/       → Mappers entre entidades y DTOs
 │   │   │       │   │   └── entity/    → Mappers entre dominio y entidades JPA
 │   │   │       │   └── security/      → Configuración de seguridad (Jwt, SecurityConfig, filtros…)
 │   │   │       └── BibliotecaApplication.java → Clase principal de Spring Boot
 │   │   └── resources
 │   │       ├── application.properties → Configuración de la aplicación
 │   │       ├── static/                → Recursos estáticos (CSS, imágenes…)
 │   │       └── templates/             → Vistas Thymeleaf (admin, client, public…)
 │
 └── README.md

```

---

# 🏛 Arquitectura del Backend

El backend usa una organización modular clásica y clara:

### 🔹 Controllers
Puntos de entrada de la API REST y controladores web.

### 🔹 Services
Implementan la lógica de negocio.

### 🔹 Repositories (JPA)
Manipulan los datos mediante interfaces JPA.

### 🔹 Entities
Representan directamente las tablas de la base de datos.

### 🔹 DTOs
Transforman datos entre el backend y el cliente.

### 🔹 Uploads
Manejo de archivos en `/uploads/images/`.

---

# 🐬 Base de Datos MySQL (Docker)

La base de datos se levanta automáticamente mediante el `docker-compose.yml` ubicado en la raíz.

Ejemplo del bloque de configuración utilizado en este proyecto:

```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: mysql_proyecto
    environment:
      MYSQL_DATABASE: proyecto_11
      MYSQL_USER: nombre_1
      MYSQL_PASSWORD: contra_1
      MYSQL_ROOT_PASSWORD: contra_1
    ports:
      - "3306:3306"
    volumes:
      - ./mysql_data:/var/lib/mysql
```

#  EL USUARIO DE PRUEBA DE ADMINISTRADOR TIENE ESTAS CREDENCIALES:
```
 
    -Gmail:admin33@admin.com
    -Contraseña:123
```