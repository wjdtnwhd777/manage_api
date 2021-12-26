FROM adoptopenjdk/openjdk11:latest
RUN mkdir /web
COPY build/libs/manage-1.0.0.jar /web/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/web/app.jar"]