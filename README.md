z# Transport Park [![Java CI with Maven](https://github.com/Brest-Java-Course-2021/aprohorov-transport/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/Brest-Java-Course-2021/aprohorov-transport/actions/workflows/maven.yml)

# Brest Java Course 2021 (winter)

This is sample 'Transport Park' web application.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```

## Local tests with Jetty Maven Plugin

The [Jetty Maven plugin](https://www.eclipse.org/jetty/documentation/jetty-10/programming-guide/index.html#jetty-maven-plugin) is useful for development and nd local testing.

From the same directory as your root pom.xml, type:
for start web-app...
```
cd web-app/target
java -jar web-app.jar


```
for start rest-app...
```
cd rest-app/target
java -jar rest-app.jar


```



This starts Jetty and serves up your project on [http://localhost:8080](http://localhost:8080).