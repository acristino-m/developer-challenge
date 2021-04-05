# Developer Challenge

## Requisitos para desarrollo de backend en Java

Implementar un Microservicio que consuma Tweets y basado en unos criterios de configuración los persista en una base de datos para su gestión a través de una API REST.

Restricciones:

- Solo se deben persistir aquellos tweets cuyos usuarios superen un número N de
seguidores (default 1500).
- Solo se deben persistir aquellos tweets cuyo idioma esté en una lista de idiomas
permitidos (default español, francés, italiano).
- De cada tweet deben almacenarse los siguientes datos: usuario, texto, localización,
validación.
- El API REST debe permitir:
   - Consultar los tweets.
   - Marcar un tweet como validado.
   - Consultar los tweets validados por usuario.
   - Consultar una clasificación de los N hashtags más usados (default 10).
- Utilizar Spring Boot para la implementación.
- Realizar la persistencia en memoria.

Links útiles:
- http://twitter4j.org/en/ (Utilizar streaming API)
- https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object

## Desarrollo

### Stack tecnológico

Además de los requisitos tecnológicos indicados previamente, se ha considerado el siguiente stack tecnológico:

- Java 11.
- Spring Boot 2.4.4.
- [Maven](https://maven.apache.org/) 3.6.3.
- [ArchUnit](https://www.archunit.org/) 0.17.0. Librería para realizar pruebas unitarias sobre la "architectura Java".  
- [H2 Database Engine](https://www.h2database.com/) 1.4.200. Base de datos en memoria.
- [Liquibase](https://www.liquibase.org/) 3.10.3. Gestión de cambios en la base de datos.
- [MapStruct](https://mapstruct.org/) 1.4.2.Final. Librería/generador de código que simplifica la implementación de mapeos entre beans de Java.
- [Project Lombok](https://projectlombok.org/) 1.18.18. Librería que reduce el código desarrollado, ahorrando tiempo y mejorando la legibilidad del mismo.
- [Spock Framework](https://spockframework.org/) 2.0-M5. Framework de testing y especificación ([BDD](https://es.wikipedia.org/wiki/Desarrollo_guiado_por_comportamiento)). Se ha utilizado para las pruebas unitarias.   
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa). Facilita la creación de repositorios JPA y el acceso a los datos.
- [springdoc-openapi](https://springdoc.org/) 1.5.6. Librería que facilita la generación de documentación en formato [OpenAPI](https://www.openapis.org/) de las interfaces expuestas.
- [Twitter4J](http://twitter4j.org) 4.0.7.

### Buenas prácticas

Para el desarrollo, se han tenido en cuenta buenas prácticas de desarrollo tales como:

- [The Twelve-Factor App](https://12factor.net/). La configuración como nº de seguidores, idiomas u otros parámetros se ha establecido en el archivo de configuración `application.yaml` indicando además la posibilidad de indicar los valores vía externa (uso de variables de entorno).
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).
- [Arquitectura hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)).  
- [S.O.L.I.D.](https://es.wikipedia.org/wiki/SOLID).

Para asegurar la estructura del proyecto y dependencias entre clases, se ha utilizado ArchUnit.

### Twitter4J

Se ha utilizado esta librería para la integración con el servicio de Twitter. En este sentido y tal y como se solicita en los requisitos, se ha utilizado el servicio de Streaming, para lo cual, se ha definido la clase `StreamConfiguration`, en la cual se da de alta el proceso de consumo de tweets vía streaming.

Este proceso se ejecuta en función de la activación de un flag: `application.twitter.stream.enabled`. Si está activo (valor `true`), sí se lanza el proceso de streaming. Esto es útil por dos razones:

1. En caso de estar lanzándose las pruebas unitarias, no se desea que se lance el proceso. Para lo cual, se desactiva mediante la utilización del archivo `application-test.yaml`.
1. Para la [configuración de la librería](http://twitter4j.org/en/configuration.html), se ha utilizado el procedimiento de [variables de entorno](http://twitter4j.org/en/configuration.html#environmentvariableconfiguration) directamente en el archivo `docker-compose.yml`.

### Docker

Se ha utilizado la [creación eficiente de imágenes Docker](https://spring.io/blog/2020/08/14/creating-efficient-docker-images-with-spring-boot-2-3), para lo cual:

1. Se ha configurado Maven ajustando el plugin de Spring Boot para la generación de capas:

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <outputDirectory>${project.build.directory}/docker</outputDirectory>
    <classifier>exec</classifier>
    <layers>
      <enabled>true</enabled>
      <configuration>${project.basedir}/src/main/docker/layers.xml</configuration>
    </layers>
    <excludes>
      <exclude>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
      </exclude>
    </excludes>
  </configuration>
</plugin>
```

2. En el directorio `src/main/docker` se han dispuesto dos archivos:
   1. `Dockerfile`. Archivo de generación de la imagen Docker utilizando las capas generadas.
   1. `layers.xml`. Archivo describiendo las capas existentes.
    
Para generar la imagen, en primer lugar hay que lanzar el proceso de Maven de empaquetado:

```shell
mvn clean package
```

En el cual, además de lanzar los tests automatizados, generará el archivo `developer-challenge-0.0.1-SNAPSHOT-exec.jar` en la ruta `target/docker`; junto con la adaptación del archivo `Dockerfile`.

Una vez disponibles estos ficheros en dicha ruta, es posible lanzar la aplicación mediante la herramienta [Docker Compose](https://docs.docker.com/compose/). Hay que asegurarse previamente de haber establecido los valores correctos de configuración del API de Twitter:

```yaml
    environment:
      twitter4j.oauth.consumerKey: 
      twitter4j.oauth.consumerSecret:
      twitter4j.oauth.accessToken:
      twitter4j.oauth.accessTokenSecret:
```

Una vez actualizados, es posible lanzar la aplicación:

```shell
docker-compose up -d challenge
```

### Prueba del servicio

Una vez arrancado el desarrollo en Docker, se puede probar el servicio directamente utilizando la funcionalidad de OpenAPI. Springdoc expone los siguientes endpoints (configurados en el `application.yaml`):

- [Interfaz de usuario](http://localhost:8080/openapi/swagger-ui). Interfaz donde se visualizan los endpoints expuestos, teniendo la posibilidad de lanzar las pruebas directamente desde esta interfaz de usuario (sin necesidad de utilización de Postman o herramienta similar).
- [Especificación OpenAPI](http://localhost:8080/openapi/api-docs). Endpoint para visualizar la especificación propia del servicio, para posteriormente poder crear clientes a partir de la misma. Si se desea descargar en formato YAML, basta con añadir la extensión `.yaml` a la URL.
