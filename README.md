Transport Park [![Java CI with Maven](https://github.com/Brest-Java-Course-2021/aprohorov-transport/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/Brest-Java-Course-2021/aprohorov-transport/actions/workflows/maven.yml)

# Brest Java Course 2021 (winter)

This is sample 'Transport Park' web application.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```

## Local tests

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



This starts application and serves up your project on [http://localhost:8080](http://localhost:8080) for web-app and [http://localhost:8090](http://localhost:8090) for rest-app.