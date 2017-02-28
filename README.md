#avro-experiment-poc

##Overview
Avro Experiment Proof of Concept contains examples of using Avro for data serialization/deserialization. 

##Prerequisites
* [JDK 8](http://www.oracle.com/technetwork/java/index.html) installed and working
* Building under [Ubuntu Linux](http://www.ubuntu.com/) is supported and recommended 
* [Confluent Schema Registry](http://docs.confluent.io/1.0/schema-registry/docs/index.html) running. 
Use the [Confluent Platform Quickstart](http://docs.confluent.io/3.0.1/quickstart.html) guide to start it.
* [RabbitMQ](https://www.rabbitmq.com/download.html) running on the custom port. 

##Building
Type `./gradlew` to build and assemble the service.

##Rest API Documentation

 Rest Endpoints           | Http method  | Description  
 ------------------------ | ------------ | -----------------
 /course/install/v1_to_v1 |  POST        | The example of document avro serialization/deserialization to json format using the same schema. 
 /course/install/v1_to_v2 |  POST        | The example of document avro serialization/deserialization to json format using different write and read schemas. 
 /course/install/v1_to_v3 |  POST        | The example of document avro serialization/deserialization to json format using incompatible write and read schemas. 
 /lesson/status           |  POST        | The example of document avro serialization/deserialization to binary using the same schema. 

##Operations Endpoints
The services supports a variety of endpoints useful to an Operations engineer.

* /operations - Provides a hypermedia-based “discovery page” for the other endpoints.
* /operations/actuator - Provides a hypermedia-based “discovery page” for the other endpoints.
* /operations/autoconfig - Displays an auto-configuration report showing all auto-configuration candidates and the reason why they ‘were’ or ‘were not’ applied.
* /operations/beans - Displays a complete list of all the Spring beans in your application.
* /operations/configprops - Displays a collated list of all `@ConfigurationProperties`.
* /operations/docs - Displays documentation, including example requests and responses, for the Actuator’s endpoints.
* /operations/dump - Performs a thread dump.
* /operations/env - Exposes properties from Spring’s `ConfigurableEnvironment`.
* /operations/flyway - Shows any `Flyway` database migrations that have been applied.
* /operations/health - Shows application health information.
* /operations/info - Displays arbitrary application info.
* /operations/liquibase - Shows any `Liquibase` database migrations that have been applied.
* /operations/logfile - Returns the contents of the logfile (if logging.file or logging.path properties have been set).
* /operations/metrics - Shows ‘metrics’ information for the current application.
* /operations/mappings - Displays a collated list of all `@RequestMapping` paths.
* /operations/shutdown - Allows the application to be gracefully shutdown (not enabled by default).
* /operations/trace - Displays trace information (by default the last few HTTP requests).