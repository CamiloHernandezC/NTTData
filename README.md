# Solución prueba NTT Data



- [Ejercicio](#ejercicio)
  - [Implementación y tecnologías usadas](#Implementacion-y-tecnologias-usadas)
  - [Comentarios relevantes](#comentarios)
- [Setup](#setup)
  - [Instrucciones](#instrucciones)
  - [API Url](#API-Url)
  - [Servicios](#servicios)
- [Test](#test)
  - [Automaticos](#automaticos)
- [Mejoras](#Mejoras)

## Ejercicio

### Implementacion y tecnologias usadas

- Java 11
- H2
- JUnit
- Swagger-ui-express
- Maven
- GitHub
- Eureka
- JWT
- Api Gateway (Spring)
- Security (Spring)

### Comentarios

La solución propuesta se desarrolló en Java, ya que es un lenguaje en el que el desarrollador tiene experiencia, esto le permitió optimizar el tiempo para dar solución a la mayor cantidad de requistios, utilizando las mejores prácticas.

Al igual que java, la demás herramientas utilizadas se seleccionaron por diferentes características para llegar a la mejor solución posible. Esto complementado con ser herramientas ya utilizadas por el programador.

Se desarrolló bajo una arquitectura de microservicios, con un orquestador de servicios implementado con Eureka y un Api Gateway implementado con Spring, además de un servidor propio de seguridad para la autenticación del usuario, que provee los tokens y refresh tokens necesarios para usar el microservicio de productos.

En la base de datos se utilizó H2, se escogió por ser una base de datos en memoria que el mismo proyecto correría.

En la parte de los test unitarios se seleccionó Junit, que permite un amplio testeo de la aplicación e incluye Mockito lo que habilita las ventajas de mockear diferentes clases, métodos o interacciones.

Finalmente, en la parte de documentación se utilizó Swagger que es una herramienta de documentación muy sencilla de implementar y de usar y entender para el usuario final, esta se encuentra disponible en la siguiente direccion:

- Products Microservice: http://localhost:8085/swagger-ui.html

## Setup

### Instrucciones

#### Prerequisitos

Para correr la aplicación en su máquina local es necesario tener instalado Java 11 y Maven.

Cada proyecto se creó de forma independiente para hacerlo más desacoplado y tener una alta cohesión en cada proyecto. 

A continuación la lista de proyectos:

- Api gateway
- Productos microservice
- Database
- Security
- Service register


#### Dependencias

La administración de dependencias se hizo utilizando maven. Por lo cual puede abrir una terminal, ir al folder del proyecto y utilizar el comando:

```
mvn clean install -U
```

#### Iniciación

Para iniciar la aplicación en la terminal puede utilizar el comando:

```
mvn  spring-boot:run
```

El orden sugerido para iniciar las aplicaciones es:

Database, este crea la base de datos en memoria, por lo cual los módulos de Security, Products microservice lo necesitaran para iniciar correctamente sus conexiones JDBC.

Service Register, este crea el servicio de Eureka, por lo cual se sugiere iniciarlo antes de los siguiente módulos, para que puedan registrarse en este orquestador.

Luego de iniciar estos dos módulos podrá iniciar Api gateway, Security y Products microservice indistintamente del orden.

Si todo está bien en el puerto 8080 (puerto utilizado por defecto) podrá realizar llamadas a los diferentes enpoints. 

Para comenzar puede hacer una petición POST al login, para obtener los tokens, con las siguientes credenciales:

- username: camilo
- password: password

En la carpeta se encuentra una colección de Postman que puede utilizar para probar los diferentes endpoints.

El request de login contiene un script que automaticamente guarda el token de acceso en una variable global, la cual es utilizada por los otros microservicios


### API-Url

Como se mencionó anteriormente la dirección local (del API gateway) del proyecto se encuentra en:

http://localhost:8080/


### Servicios

Se encuentran los servicios CRUD para los productos y el servicio de Login.

### Test

#### Automaticos

Para los test se utilizó Junit y se trató de tener la mayor covertura posible, enfocandose en los controladores y la implementación de las interfaces utilizadas.


### Mejoras

- Tests de carga y de seguridad
- Dockerización (Se inició, pero no se logró completar)
- Prevenir ataques tipo brutal force implementando el método unsuccessfulAuthentication en la clase CustomAuthenticationFilter
- Crear endpoint de health check
- Crear endpoints para verificar los match y miss del caché
