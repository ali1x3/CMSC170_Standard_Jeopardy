# CMSC 170 - Introduction to Artificial Intelligence
## Assignment 1 - Standard Jeopardy

This is a project for compliance with CMSC 170.

### Prerequisite:
- Java 25 JDK (Oracle should have one for all platforms, using OpenJDK)


### How to use the build tool, Maven:
- ensure you are in the root directory of the project before running any maven commnads
- the root directory should have a pom.xml file
- install maven using the maven wrapper using these commands

```bash
./mvnw clean install   # Linux/macOS
.\mvnw.cmd clean install   # Windows
```

- you are now able to run the following commands with maven
- compile: 
```bash
mvn compile
```
- run tests:
```bash
mvn test
```
- package into jar: (will not run as dependencies will be missing)
```bash
mvn package
```
- package into jar with dependencies: (use this)
```bash
mvn clean compile assembly:single
```
- to run the code, we can run the jar file
- this will be found in the target/folder
```bash
java -jar path\to\jarfile
java -jar target\jarfile-with-dependencies
```
