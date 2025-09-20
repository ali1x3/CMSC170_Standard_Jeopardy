# CMSC 170 - Introduction to Artificial Intelligence
## Assignment 1 - Standard Jeopardy

This is a project for compliance with CMSC 170.

Coding Conventions:

- do not abbreviate variable or function names
- write functions/methods that can be unit tested
- place spaces around operands: (2 + 2) not (2+2)
- place spaces after commas: (2, 2) not (2,2)




How to use the build tool, Maven:

- you may opt to install maven or not, but maven will be usable for this repo even without installing maven
- replace the mvn command with the mvnw.cmd (windows) when in the root directory of this project for the commands
- compile: mvn compile
- run tests: mvn test
- package into jar: mvn package
- to run the code, we can run the jar file

How to write tests:

- the src code is divided into two, the main and the test files
- main is for the actual code and test is for testing main
- each file in main will have a corresponding test file that ensures that it is working properly
- name each test method with its purpose. Ex. two_plus_two_is_four(){ assertEqual( 4, add(2, 2))}

Git Branches:

