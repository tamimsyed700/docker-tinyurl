
# Tiny URL System Design Implementation of Educative.io

Here am trying to implement the Tiny URL system design i learned in the System Design course of the Educative.io. I have implemented the system using Java and Spring Boot framework. Technologies used are Java/MySQL/Redis/Spring Boot and Docker.

I have 2 modules here which is **KGSServer** and **Tiny URL web server** 

# KGSServer
It does the following things:

- **GetURL** - Gets the URL from the redis and send it to the TinyURL microservices
- **createURL** - It will create the URL on the redis with the key and value mapping.

# TinyURL Web server
It does the following things:

- **createURL** - It will create the URL by looking into the MySQL/MariaDB database and then send the key to the client after inserting the mapping into the dataabase. The same goes to the Redis server via KGSServer microservice.
- **GetURL**- It will get the URL by getting the key from the Redis via Microservice or send the 204 No content to the client.

# Architecture of the TinyURL System Design
![https://www.educative.io/courses/grokking-the-system-design-interview](./images/HighLevelTinyUrl.png)
# Getting Started
## System Requirement

- Docker/Podman
- Java 8+
- Maven 3.6.3+

```
Step 1 : git clone https://github.com/tamimsyed700/docker-tinyurl.git
```
```
Step 2 : On the root folder, run mvn clean packages -DskipTests
```
```
Step 3 : docker-compose up --build  (only for the first time otherwise remove the --build)
```

### Known issues:
Docker for the MySQL needs to be mounted for the real application. Currently it is not.

# Need to improve
- Add the integration test case.
- Add the resiliency using Hystrix when calling the upstream service.
- Move the keys generation logic into the startup SQL script and use LOAD FILE of MySQL to load the data during the app start up.
- Add user centric logic for the keys
- Add API rate limiting logic.
- Add Monitoring support.
- Add Request ID and the Trace ID logic for the Application.
- Add the Redis listener logic when the keys gets expired and do some clean up operation.
