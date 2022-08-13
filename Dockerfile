FROM maven:3-jdk-11

ARG JAR_FILE=target/todo-list.jar

WORKDIR ../todo-list

COPY . .

RUN mvn clean install
CMD mvn spring-boot:run