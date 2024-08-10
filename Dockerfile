
# Usa una imagen base de Java
FROM openjdk:21-jdk
LABEL authors="rkjayko"

# Copia el JAR construido en la imagen
COPY target/franchise-api-0.0.1-SNAPSHOT.jar /app/franchise-api-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que la aplicación Spring Boot escuchará
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/franchise-api-0.0.1-SNAPSHOT.jar"]