## API test-exemple with Mockito
#

#### To clean, compile and test the project:
```
./mvnw clean compile test
```
#### Or To run the project:
```
./mvnw
```
### Databases:
- H2 in "dev" profile
- Postgres in "rec" profile and in "prod" before
- Postgres for testing (to be as production)

### Topics:
 - Use of MapStruct
 - Use of spring validators (Enum validations cases)
 - Use of Mockito in Unit testing
 - Error Handling with [@ControllerAdvice]()
 - Use of TestContainer in Integration testing  
    (for this one be sur to have Docker installed locally)
 - Use of Assertj library for assertions 
   (helps you to diversify your assertions and more readable)

