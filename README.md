# Test project for Semtech position


## Requirements

The following are the requirements that influence the design decisions: 
- Artifact must be able to accept CSV file to provide the output
- Artifact must be hosted on a platform

## Design

The following are the design decisions/assumptions made to implement the solution: 
- Artifact must expose the rest endpoints for uploading CSV file and returning the result for Department Summary and Smallest Department
- Artifact must print the results into stdout, the logger info is used to print. Results must be printed only if executed via Command Line

## Building
The project is built using maven from the root folder: 
```
mvn clean install
```
  

## Testing

The project has number of tests that would be executed during test phase of maven build. To explicitly execute tests, run the following: 
```
mvn test
```

## Testing functionality manually 

### Using java cmd

Navigate to root folder of the project and execute the following line: 
```
java -jar ./target/semtech.test-0.0.1-SNAPSHOT.jar --file=/file-to-path/population_2019_500.csv
```
  
***Note:*** The project is built using Java 21. 

### Using Rest API 

Navigate to root folder of the project and execute the following line: 
```
mvn spring-boot:run
```
  
After the spring boot application is up and running, execute the following curl command: 
```
curl -X POST http://localhost:8080/api/smallestDepartment -F "file=@/<path-to-file>/population_2019_500.csv"
```

***Note:*** The project is built using Java 21.


## CI/CD

GitHub Actions would be used to enable CI/CD with the build on PullRequest and verifying required build and unit tests are running

## Hosting

As the project is implemented using Spring-Boot, it can be hosted by AWS EC2 or AWS Lambda or even EKS (with additional effort on helm chart). 