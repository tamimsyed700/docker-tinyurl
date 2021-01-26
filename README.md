
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
![Google](https://www.google.com/logos/doodles/2021/india-republic-day-2021-6753651837108846-l.png)
