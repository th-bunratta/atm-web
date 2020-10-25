FROM maven:3.6-jdk-8
WORKDIR /usr/atm-web
COPY pom.xml .
RUN mvn dependency:resolve
COPY src/main ./src/main
RUN mvn compile
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
