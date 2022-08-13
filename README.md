
# To-Do List API
A simple example of todo list API using the technologies bellow:

- Spring boot
- Spring security
- MySQL database
- JUnit
- Mockito
- Swagger
- lombok
- Docker

### Requirements
- Java 11+
- Maven 3+
- Lombok Plugin
- Eclipse or Intelij IDE
- Docker

### How to run

#### Maven

1. Run mvn clean install
2. Run mvn spring-boot:run

#### Docker
1. Under folder "docker" in root project  run -sudo docker-compose up
2. Under folder project run - sudo docker build -t todo-list:1.0 .
3. Run sudo docker run -d -p 8080:8080 -t todo-list:1.0

### Routes

| User  |  ClientId |  ClientSecret | FlAdmin |
|---|---|---|---|
| Obi-Wan Kenoby  | Y1M96LLiAwIJ5q8WFuvl | i63aw0t39WIQZAXKsmW3zEN8A | True   |  
| Darth Vader |  nCS5852o0dDxhZRDUHKB | 4b2RoMTo7aA9EtwQkgg67MuQXy |  False |   


**Retrive token from an user (use clientId and clientSecret)**

    curl --location --request POST 'http://localhost:8080/api/v1/auth/oauth/token-jwt' --header 'Content-Type:application/json' --data-raw '{ "clientId": "Y1M96LLiAwIJ5q8WFuvl",  "clientSecret" : "i63aw0t39WIQZAXKsmW3zEN8A" }'

**Save a task into dababase of the user logged in**

    curl --location --request POST 'http://localhost:8080/api/v1/task' --header 'Authorization: Bearer <token>' --header 'Content-Type: application/json' --data-raw '{ "name": "Darth Vader",  "description": "Darth Vader is father Luke and Lea",  "status": "PENDING" }'

**Retrive all tasks from the user is logged in**

    curl --location --request GET 'http://localhost:8080/api/v1/task --header 'Authorization: Bearer <token>'

**Retrive all tasks from the user is logged in  by status**

    curl --location --request GET 'http://localhost:8080/api/v1/task?status=PENDING --header 'Authorization: Bearer <token>'

    curl --location --request GET 'http://localhost:8080/api/v1/task?status=COMPLETED --header 'Authorization: Bearer <token>'

**Update a task into dababase of the user logged in**

    curl --location --request PUT 'http://localhost:8080/api/v1/task/<idTask>' --header 'Authorization: Bearer <token> --header 'Content-Type: application/json'  --data-raw ' { "name": "Destroy Death Star",  "description": "Get Plans to Destroy Death Star",  "status": "COMPLETED" }'

**Delete a task into dababase of the user logged in**

    curl --location --request DELETE 'http://localhost:8080/api/v1/task/<taskId>'  --header 'Authorization: Bearer <token>


### API Information

**Heath Check**

    http://localhost:9090/actuator/health

**Metrics**

    http://localhost:9090/actuator/metrics

**Documentation Swagger**
    
    http://localhost:8080/swagger-ui/index.html