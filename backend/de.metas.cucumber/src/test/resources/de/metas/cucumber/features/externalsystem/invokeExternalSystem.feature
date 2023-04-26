@from:cucumber
Feature: external system invocation using metasfresh api
  As a REST-API invoker
  I want want to be able to invoke external systems

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue |
      | S6_config                           | S6   | testS6              |
      | alberta_config                      | A    | testAlberta         |

  @from:cucumber
  Scenario: The request is good and the external service Shopware6 is invoked via the correct process
    When the metasfresh REST-API endpoint path 'api/externalsystem/S6/testS6/test' receives a 'POST' request

    Then a new metasfresh AD_PInstance_Log is stored for the external system 'S6' invocation

  @from:cucumber
  Scenario: The request is good and the external service Alberta is invoked via the correct process
    When the metasfresh REST-API endpoint path 'api/externalsystem/A/testAlberta/test' receives a 'POST' request
    Then a new metasfresh AD_PInstance_Log is stored for the external system 'A' invocation

  @from:cucumber
  Scenario: Invoke external system for Shopware without reqBody
    When the metasfresh REST-API endpoint path 'api/v2/externalsystem/invoke/S6/testS6/test' receives a 'POST' request
    Then a new metasfresh AD_PInstance_Log is stored for the external system 'S6' invocation

  @from:cucumber
  Scenario: Invoke external system for Shopware with reqBody
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/externalsystem/invoke/S6/testS6/test' and fulfills with '200' status code
  """
{
    "params": {
        "OrderId": "111",
        "OrderNo": "222"
    }
}
  """
    Then a new metasfresh AD_PInstance_Para is stored for the external system invocation
      | param.key | param.value |
      | OrderId   | 111         |
      | OrderNo   | 222         |


  @from:cucumber
  Scenario: The request is good and the external Other is invoked via the correct process
    Given add Other external system config with identifier: otherConfigIdentifier
      | Name      | Value      |
      | TestName1 | TestValue1 |
      | TestName2 | TestValue2 |

    And store endpointPath /api/externalsystem/Other/:otherConfigIdentifier/test in context, resolving placeholder as ExternalSystem.value

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    When a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | JsonExternalSystemRequest.parameters.RAW                                      |
      | otherConfigIdentifier               | {"TestName1":"TestValue1","TestName2":"TestValue2","External_Request":"test"} |

    And a new metasfresh AD_PInstance_Log is stored for the external system 'Other' invocation
