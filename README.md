# CMSC 170 - Introduction to Artificial Intelligence
## Assignment 1 - Standard Jeopardy

This is a project for compliance with CMSC 170.

### Coding Conventions:

- camelCase for methods and variables
- PascalCase for classes
- try not to abbreviate variable or function names:
```java
// which one is more readable?
// this?
int cnt;
int usrNm;
List<Cust> custs;
Map<String, Emp> empMap;

// or this?
int count;
String userName;
List<Customer> customers;
Map<String, Employee> employeeMap;
```
- write functions/methods that can be unit tested
- place spaces around operands: 
```java
if (x + y) {} //do this
if (x+y) {} //dont do this
```
- place spaces after commas: 
```java
add(2, 2); //do this
add(2,2);  //not this
```
- put braces even for single line statements:
```java
if (x < 0) { // do this
    IO.println("hi");
}

if (x < 0)  //dont to this
    IO.println("hi");
```

- put spaces between keywords (but not methods) and parenthesis:
```java
if (true) {} //do this
if(true) {} //dont do this
void someMethod(int x) {} //do this
void someMethod (int x) {} //dont do this
```

- put spaces before brackets:
```java
if (true) {} //do this
if (false){} //dont do this
```

### How to use the build tool, Maven:

- you may opt to install maven or not, but maven will be usable for this repo even without installing maven
- replace the mvn command with the mvnw.cmd (windows) when in the root directory of this project for the commands
```bash
mvn <command>    #if maven installed
.\mvnw <command> #if maven not installed
```

- compile: 
```bash
mvn compile
```
- run tests:
```bash
mvn test
```
- package into jar:
```bash
mvn package
```
- to run the code, we can run the jar file
```bash
java -jar path\to\jarfile
```

### How to write tests:

- the src code is divided into two, the main and the test files
- main is for the actual code and test is for testing main
- each file in main will have a corresponding test file that ensures that it is working properly
- name each test method with its purpose. 
- Maven will not build the app if a test fails
```java
public class App { //this is in /src/main/java/up/tac/App.java

    static void main() {
        System.out.println("Hello World!");
    }
    
    public int add(int x, int y){
        return x + y;
    }
}

public class AppTest { //this is in /src/test/java/up/tac/TestApp.java

    @Test
    public void testAdd(){
        App app = new App();
        assertEquals(5, app.add(2, 3));
    }
}
```

### Git Branches:

- for each feature in development, create a new branch
- when feature is finished, merge in onto the main upstream branch
- to keep your feature branch updated with main upstream, use the git pull rebase command
- DO NOT REBASE MAIN UPSTREAM ONTO YOUR FEATURE BRANCH, ONLY MERGE FEATURE ONTO MAIN UPSTREAM
- for merging onto main:
```git
git switch main
git merge feature
```
- for rebasing on from main
```git
git switch feature
git pull --rebase origin main
```

- DO NOT RUN: (this messes up commit history)
```git
git switch main
git rebase feature
```

