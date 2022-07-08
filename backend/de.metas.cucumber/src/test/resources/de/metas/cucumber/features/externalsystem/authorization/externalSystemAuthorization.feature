@from:cucumber
Feature: Camel-ExternalSystem authorization

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Make sure metasfresh replies appropriately when Camel-ExternalSystem is requesting authorization
    Given metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken.Identifier | AD_User_ID | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken        | 100        | 540024     | authToken |
    And set sys config String value authToken for sys config de.metas.externalsystem.externalservice.authorization.authToken
    And Custom_ExternalSystem_To_Metasfresh queue receives an auth token request
    Then metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with authToken