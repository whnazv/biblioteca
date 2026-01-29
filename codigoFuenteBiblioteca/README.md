# 📚 Biblioteca – Base de Datos MySQL con Docker

Este proyecto utiliza **MySQL 8.0** como base de datos, levantada mediante **Docker**.
La aplicación (Spring Boot) se ejecuta fuera del contenedor, pero necesita que la base de datos esté disponible en **localhost**.

---

## 🐳 Ejecutar la Base de Datos con Docker

### ✔ Requisitos previos

- **Docker** instalado en tu sistema (Linux, Windows o Mac).
- **Docker Compose** para orquestar los servicios.

Descarga Docker desde: https://www.docker.com/products/docker-desktop

Verifica la instalación con:

    docker --version
    docker compose version

---

## 🐳 Archivo docker-compose.yml (configuración del proyecto)

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

Este archivo crea:
- Una base llamada **proyecto_11**
- Un usuario **nombre_1**
- La contraseña **contra_1** tanto para root como para el usuario
- Un volumen persistente en la carpeta `mysql_data`
---


## ▶ Levantar la base de datos

    docker compose up -d

## ⏹ Detener la base de datos

    docker compose down

---

## 🔑 Datos de conexión para la aplicación

URL:
jdbc:mysql://localhost:3306/proyecto_11

Usuario:
nombre_1

Contraseña:
contra_1

