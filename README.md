# Sutton Framework, Application for the Assignment, and Demo Application

This repository belongs to the module "Foundations of Distributed Systems" at the Technical University of
Applied Sciences Würzburg-Schweinfurt. It contains the Sutton Framework, the application I implemented for the assignment 
(PartnerUniversityAdministrator), and a demo application that uses the framework.

## How to start my application for the assignment

### Use class `Start`

Execute method `main` in class `Start` in `Backend/src/main/java/de/fhws/fiw/fds/PartnerUniAdministrator`. 
This will start the embedded Tomcat server and deploy my application. 
The PartnerUniversityAdministrator application is available at `http://localhost:8080/uni/api1`.


## Run the tests

To run the tests please use the class `Start` as described above. Then go to 
`src/test/java/de/thws/fiw/fds/ParterUniAdministrator/server` and execute the `TestPuaAppIT`class.
This class contains all the tests.

### Docker

Sadly, I couldn't get docker working. When I wanted to test the system using `mvn verify`I always received a
NullPointerException. Since I had to deal with other bugs I focused on fixing them rather than to get docker running.