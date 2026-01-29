

📚 PROYECTO BIBLIOTECA — DESCRIPCIÓN DE LAS CARPETAS
=


Este proyecto está organizado en tres carpetas principales.
Cada una cumple una función específica dentro del sistema.

-----------------------------------------------------------
📁 1. codigoFuenteBiblioteca/
-----------------------------------------------------------
Contiene todo el código fuente del proyecto:
- Aplicación principal (backend)
- Controladores, servicios y repositorios
- Archivos de configuración
- Recursos estáticos (como imágenes de libros)
- Estructura típica de un proyecto Java/Spring Boot

🔧 NOTA IMPORTANTE SOBRE LA BASE DE DATOS:
El código fuente utiliza un `application.properties` pensado para
desarrollo local, por lo que la conexión apunta a:
    localhost
Esto permite ejecutar la aplicación directamente desde el IDE
sin necesidad de Docker.

-----------------------------------------------------------
🐳 2. dockerBiblioteca/
-----------------------------------------------------------
Incluye todo lo necesario para ejecutar el proyecto con Docker:
- Dockerfile para construir la imagen de la aplicación
- docker-compose.yml para levantar los servicios
- Archivo .jar generado (biblioteca-0.0.1-SNAPSHOT.jar)

🗄️ DIFERENCIA DE CONFIGURACIÓN:
El .jar que se ejecuta dentro del contenedor utiliza un archivo
`application.properties` distinto, donde la base de datos apunta al
servicio MySQL definido en Docker Compose, por ejemplo:
    mysql-biblioteca
Esto es necesario porque dentro de Docker no se usa "localhost",
sino el nombre del contenedor como host de la base de datos.

Permite desplegar el proyecto rápidamente con:
    docker compose up --build

Ideal para entornos de pruebas o despliegue.

-----------------------------------------------------------
📄 3. documentacion/
-----------------------------------------------------------
Contiene toda la documentación técnica del proyecto:
- Manual de uso de la API
- Documentación de la aplicación web
- PDFs explicativos
- Documentación generada con Swagger

Es la referencia para entender el funcionamiento general
y técnico del sistema.

-----------------------------------------------------------
🧩 ESTRUCTURA GENERAL DEL REPOSITORIO
-----------------------------------------------------------
```
proyectoBiblioteca/
│
├── codigoFuenteBiblioteca/   # Código fuente del proyecto (modo local)
├── dockerBiblioteca/         # Infraestructura Docker + .jar con config Docker
└── documentacion/            # Documentación técnica y manuales
```

===========================================================
Autor: whnazv (Juanmi)
===========================================================
