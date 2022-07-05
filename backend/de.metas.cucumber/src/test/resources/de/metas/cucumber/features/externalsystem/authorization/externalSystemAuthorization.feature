@from:cucumber
@authorizationToken
Feature: external system authorization

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: obtain metasfresh authorization token for external system
    Given metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken.Identifier | AD_User.Identifier | AD_User_Roles.Identifier | AuthToken |
      | cucumberUserAuthToken        | 100                | 540024                   | authToken |
    And set sys config String value authToken for sys config de.metas.externalsystem.externalservice.authorization.authToken
    And Custom_ExternalSystem_To_Metasfresh queue receives an auth token request
    Then metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with authToken