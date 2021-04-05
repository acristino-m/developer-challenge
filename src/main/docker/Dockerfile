FROM adoptopenjdk:11-jre-openj9 as builder
WORKDIR /app
COPY  [ "${project.artifactId}-${project.version}-exec.jar", "app.jar" ]
RUN java -Djarmode=layertools -jar app.jar extract

# Metadata
LABEL module.name="${project.artifactId}" \
      module.version="${project.version}"

# Image

FROM adoptopenjdk:11-jre-openj9
WORKDIR /app

VOLUME [ "/tmp" ]

ENV SPRING_PROFILE default

ENV JAVA_TOOL_OPTIONS $JAVA_TOOL_OPTIONS -Xms256M -Xmx1G
ENV SERVER_PORT 8080

EXPOSE ${SERVER_PORT}

COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/company-dependencies/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${SPRING_PROFILE}", "org.springframework.boot.loader.JarLauncher"]
