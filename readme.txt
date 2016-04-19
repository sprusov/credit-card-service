Prerequisites:

   1. jdk 1.8 should be installed.
   2. Maven should be installed.

- Overview of your design:

  Technology stack is following:
   
  Java 1.8
  Maven 
  Spring-boot
  Spring-jpa
  Hsqldb
  Swagger
  
- The reason why I choose java as programming language because this is my primary programming language 

  Spring-Boot and other spring modules used in my implementations is easy to use and POC projects can be implemented in short time of period. 

  For persistence layer I have used Spring-JPA and Hibernate and Hsqldb because It offers a small, fast multithreaded and transactional database engine with in-memory and disk-based tables
   
  Swagger was used for REST API documentation.

  Maven was used to build and organize the dependencies. 

- How to run your code and tests:

  Dependencies will installed by maven.

  To compile and run integration test: 

  Command: mvn clean install

- How to call:

  To run app with file as input parameter: 

  Command: java -jar target/credit-card-service-0.1.0.jar ./input.txt

  Note: previous command should be executed from root project folder
  Note: jar file should be created first by command: mvn clean install


  To run app and enter commands from STDIN:

  Command: java -jar target/credit-card-service-0.1.0.jar
  Command: Add Tom 4111111111111111 $1000


URLS:
1. http://localhost:8080/swagger-ui.html
2. http://localhost:8080/api/v1/credit-card/add?name=Tom&number=4111111111111111&limit=$1000
3. http://localhost:8080/api/v1/credit-card/charge?name=Tom&amount=$500
4. http://localhost:8080/api/v1/credit-card/credit?name=Tom&amount=$100
5. http://localhost:8080/api/v1/credit-card/charge?name=Tom&amount=$800

- Assumptions:

All input will be valid -- there's no need to check for illegal characters or malformed commands.
All input will be space delimited
Credit card numbers may vary in length up to 19 characters
Credit card numbers will always be numeric
Amounts will always be prefixed with "$" and will be in whole dollars (no decimals)