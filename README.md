# Franchise-api

# Proyecto Spring Boot con PostgreSQL y Docker

Este proyecto es una aplicación Spring Boot que utiliza PostgreSQL como base de datos, y se ejecuta en contenedores
Docker usando Docker Compose.

## Prerrequisitos

- Java 21
- PostgreSQL
- Docker
- Docker Compose

## Estructura del Proyecto

- `src/main/java/` - Código fuente de la aplicación Spring Boot.
- `src/main/resources/` - Archivos de configuración.
- `Dockerfile` - Dockerfile para construir la imagen de la aplicación Spring Boot.
- `docker-compose.yml` - Configuración de Docker Compose para orquestar los servicios.

## Documentacion adicional :

Puede ingresar al swagger para ver los endpoints a usar en el proyecto : http://localhost:8080/swagger-ui/index.html

## Tests :

Se adiciona algunos tests para cubrir algunos casos de uso

## Configuración del Proyecto

1 - Construir el proyecto con mvn clean package
2 - Construir la imagen con docker-compose build
3 - Iniciar los servicios con docker-compose up

## Configuración de la Base de Datos

La aplicación se conecta a PostgreSQL usando las siguientes credenciales por defecto:

URL: jdbc:postgresql://db:5432/postgres
Usuario: postgres
Contraseña: postgres

## Docker Hub : 

También se dejan las imagenes publicas en docker hub por si se necesita : https://hub.docker.com/u/rkjayko2

# AWS Ec2 : 

Se deja la url donde esta funcionando para EC2 : http://52.15.76.205:8080/api/franchises/1/top-products -- Endpoint para traer los top product , en swagger esta los endpoints restantes y como utilizarlos
