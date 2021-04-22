Author: Giuseppe Fregapane Pires - giuseppefpires@gmail.com

### Description
Simple example of Sring boot with Spring Data JPA

### Building and Running
In order to build and run the solution you will need JDK 8+ and Maven installed. Then, follow the steps: 
 - Clone the repository
 - Through a terminal go to the main directory of the source and run the commands:
 - `mvn clean package `
 - `java -jar target/spring-boot-jpa-sample-0.0.1-SNAPSHOT.jar`

### Design Decisions
- For this Rest Application, i used spring boot to facilitate the creation of the api
- I use the H2 as a database to simplify the persistence layer.
- No autentication and authorization was implemented.



