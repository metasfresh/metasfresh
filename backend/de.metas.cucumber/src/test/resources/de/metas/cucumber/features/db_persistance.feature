@ghActions:run_on_executor7
Feature: Database persistance tests

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: Saving and loading various fields of Test record is working correctly
    Then using Test record, validate save and load of following fields:
      | T_Date     | T_DateTime           | T_Time |
      | 2024-03-23 |                      |        |
      | 2024-03-30 |                      |        |
      | 2024-03-31 |                      |        |
      | 2024-04-01 |                      |        |
      | 2024-04-02 |                      |        |
      |            | 2024-03-23T23:54:43Z |        |
      |            | 2024-03-30T01:14:23Z |        |
      |            | 2024-03-30T23:54:43Z |        |
      |            | 2024-03-31T01:14:23Z |        |
      |            | 2024-03-31T23:54:43Z |        |
      |            | 2024-04-01T01:14:23Z |        |
      |            | 2024-04-01T23:54:43Z |        |
      |            | 2024-04-02T01:14:23Z |        |
      |            | 2024-04-02T23:54:43Z |        |
      |            |                      | 00:00  |
      |            |                      | 01:23  |
      |            |                      | 21:43  |
      |            |                      | 23:59  |
