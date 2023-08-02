@from:cucumber
Feature: workflow tests

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION


  @from:cucumber
  @Id:S0302_100
  Scenario: create and clone manufacturing workflow
    Given load AD_WF_Node:
      | AD_WF_Node_ID.Identifier | AD_WF_Node_ID |
      | node_1                   | 108           |

    And create AD_Workflow:
      | AD_Workflow_ID.Identifier | Name     | WorkflowType | OPT.AD_WF_Node_ID.Identifier | OPT.Description | OPT.Help | OPT.AccessLevel | OPT.DurationUnit | OPT.Version | OPT.ValidFrom | OPT.ValidTo | OPT.Priority | OPT.DurationLimit | OPT.Duration | OPT.Cost | OPT.WaitingTime | OPT.IsDefault |
      | workflow_1                | testName | M            | node_1                       | testDescription | testHelp | 1               | D                | 0           | 2022-08-18    | 2022-08-19  | 0            | 0                 | 2            | 1        | 1               | false         |

    When clone AD_Workflow:
      | AD_Workflow_ID.Identifier | ClonedWorkflow.AD_Workflow_ID.Identifier |
      | workflow_1                | clonedWorkflow_1                         |

    Then validate AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                   | WorkflowType | OPT.AD_WF_Node_ID.Identifier | OPT.Description | OPT.Help | OPT.AccessLevel | OPT.DurationUnit | OPT.Version | OPT.ValidFrom | OPT.ValidTo | OPT.Priority | OPT.DurationLimit | OPT.Duration | OPT.Cost | OPT.WaitingTime | OPT.IsDefault |
      | clonedWorkflow_1          | Name_20220818:13:30:13 | M            | node_1                       | testDescription | testHelp | 1               | D                | 0           | 2022-08-18    | 2022-08-19  | 0            | 0                 | 2            | 1        | 1               | false         |
