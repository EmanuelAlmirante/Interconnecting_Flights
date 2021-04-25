# Interconnecting Flights

A Spring Boot based RESTful API application which serves information about possible direct and interconnected flights (
maximum 1 stop) based on the data consumed from external APIs.

### Tech Stack:

- Java 11
- Spring Boot
- Spring Webflux
- Swagger
- JUnit
- Mockito

### Setup without Docker:

- Clone/extract project to a folder
- Run the application with:
  - _mvn clean install_
  - _mvn spring-boot:run_
- Test the application with:
  - _mvn test_ -> run all tests
  - _mvn -Dtest=TestClass test_ -> run a single test class
  - _mvn -Dtest=TestClass1,TestClass2 test_ -> run multiple test classes
- Package the application with _mvn package_


### Setup with Docker:

- Install Docker on your machine
- Launch Docker
- Run the command _sudo systemctl status docker_ to confirm Docker is running
- Open terminal in the project folder
- Run the command _sudo docker-compose build_
- Run the command _sudo docker-compose up -d_
- The container will be up and the application will be running inside


### Endpoints:

The documentation of this API can be found at _http://localhost:8080/swagger-ui.html_ (**Note: you need to start the application to access this link**).
