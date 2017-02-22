Feature: Apache Avro Experiment
  the data serialization system should encode and decode the incoming event and log the result

  @required
  Scenario Outline: full workflow of successful event validation
    Given valid event
    And valid path <rest-path>
    When the request is sent
    Then the response returned with status 200
    And the success is logged

    Examples:
    | rest-path                 |
    | course/install/v1_to_v1   |
    | course/install/v1_to_v2   |

  @required
  Scenario: full workflow of failed event validation
    Given valid event
    And valid path course/install/v1_to_v3
    When the request is sent
    Then the response returned with status 200
    Then the failure is logged
