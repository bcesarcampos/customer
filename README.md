# Spring Boot Customer's Restfull API
A simple CRUD API in Java that highlights some of Spring Boot’s features. I’ll use Maven to build this project since most IDEs support it.

It uses the concepts of clean architecture and clean code.

The Spock framework was used for structuring and specifying unit tests in Groovy language, because it can help the practice of agile practices like Test-Driven Development (TDD) and Behavior-Driven Development (BDD). 

## Technologies:
  1. Spring Boot with Undertow
  2. Docker
  3. MongoDB
  4. Lombok
  5. Actuator
  6. SpringFox Swagger
  7. Groovy
  8. Junit and Spock Framework for TDD / BDD
  9. Fixture Factory for generating fake objects in unit tests
 10. Postman for building API requests

## Running Docker MongoDB Image:
Go to folder *docker-images* and execute
```sh
$ sudo docker-compose up
```

## Running Application:
```sh
$ mvn spring-boot:run
```

## Running Tests (Junit + Spock):
```sh
$ mvn clean test
```

## Swagger
To use, just up the application and invoke the url: `http://<hostname>:<port>/swagger-ui.html`

By default the *swagger* retrieve only the endpoints beginning with `api/.*`.

## Postman
Postman data can be imported from the data tab of the settings modal, or using the Import button in the header toolbar.

Go to folder *postman* and import the Project.postman_collection.json file in the Postman application.
