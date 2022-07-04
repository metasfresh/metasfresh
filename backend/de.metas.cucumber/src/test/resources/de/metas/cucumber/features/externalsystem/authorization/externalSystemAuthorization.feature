@from:cucumber
@authorizationToken
Feature: external system authorization

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: obtain metasfresh authorization token for external system
    Given send authorization request towards external system to metasfresh custom queue
    Then receive authorization token reply from metasfresh to external system custom queue