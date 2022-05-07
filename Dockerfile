FROM openjdk:11
COPY target/*.jar app.jar
ADD ./docker/mysql/init/init.sql /docker-entrypoint-initdb.d/init.sql
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
