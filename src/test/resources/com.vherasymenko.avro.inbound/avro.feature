Feature: Apache Avro Experiment
  the data serialization system should encode and decode the incoming event and log the result

  @required
  Scenario: event encoding
    Given valid event according to the schema
    When the request is sent
    Then the encoded event is placed on the rabbitmq exchange

  @required
  Scenario: event decoding
    Given message with valid encoded event
    When the message is placed to the rabbitmq exchange
    Then the success is logged

  @required
  Scenario: full workflow of successful event validation
    Given valid event according to the schema
    When the request is sent
    Then the success is logged

  @required
  Scenario: full workflow of successful validation of previous version event
    Given valid event by previous version
    When the request is sent
    Then the success is logged

  @required
  Scenario: full workflow of failed event validation
    Given invalid event
    When the request is sent
    Then the failure is logged
