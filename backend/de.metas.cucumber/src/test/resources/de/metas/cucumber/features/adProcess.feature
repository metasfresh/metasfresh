@from:cucumber
@ghActions:run_on_executor1
Feature: AD_Process validation

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  @Id:S0250_100
  Scenario: Check AD_Processes
    And validate all AD_Processes, except:
    | AD_Process_ID | Reason |
