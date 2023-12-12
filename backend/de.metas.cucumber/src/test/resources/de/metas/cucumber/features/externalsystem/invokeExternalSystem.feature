@from:cucumber
@ghActions:run_on_executor5
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
