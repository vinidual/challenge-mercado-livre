# challenge-mercado-livre

## Swagger

Check the swagger ui below to see the Rest API documentation

**http://mercadolivre-env.huk9kg68dj.us-east-2.elasticbeanstalk.com/swagger-ui.html**

## Specs

* **Spring boot 2.2 maven application**

    * To run locally

        * mvn clean install -U
        * mvn spring-boot:run
        * http://localhost:8080/swagger-ui.html

* **Java 8**

* **Lombok**

* **Swagger**

* **PostgreSQL**

* **JACOCO**

* **Using AWS Cloud environment**

    * Free tier account (I know u have to test but don't need to destroy my free quota)

    * AWS Elastic Beanstalk (Please be kind using resources, auto scaling and load balance are actives, 
    but as soon as I got an interview result will be disable, be ethic)

    * RDS PostgreSQL Database in use (Again, its limited to 20GB disk space, be nice)
    
        * Connection settings on application properties

* **Test Coverage**

    * Using jacoco maven plugin
    
        * To verify the test coverage build the application and open the 
        **target/site/jacoco/index.html** file on a browser