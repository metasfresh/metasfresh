@from:cucumber
Feature: external system invocation using metasfresh api
  As a REST-API invoker
  I want want to be able to invoke external systems

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And external system with type code 'S6' has an external system child with value 'testS6'
    And external system with type code 'A' has an external system child with value 'testAlberta'

  @from:cucumber
  Scenario: The request is good and the external service Shopware6 is invoked via the correct process
    When the metasfresh REST-API endpoint path 'api/externalsystem/S6/testS6/test' receives a 'POST' request

    Then a new metasfresh AD_PInstance_Log is stored for the external system 'S6' invocation

  @from:cucumber
  Scenario: The request is good and the external service Alberta is invoked via the correct process
    When the metasfresh REST-API endpoint path 'api/externalsystem/A/testAlberta/test' receives a 'POST' request

    Then a new metasfresh AD_PInstance_Log is stored for the external system 'A' invocation