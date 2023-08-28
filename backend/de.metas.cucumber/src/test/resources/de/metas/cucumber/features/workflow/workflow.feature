@from:cucumber
Feature: workflow tests

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
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

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create AD_WF_Node:
      | AD_WF_Node_ID.Identifier | AD_Workflow_ID.Identifier | OPT.S_Resource_ID.Identifier | Name      | Value      | Duration | OPT.Description  |
      | wfNode_1                 | workflow_1                | testResource                 | testName1 | testValue1 | 4        | testDescription1 |
      | wfNode_2                 | workflow_1                | testResource                 | testName2 | testValue2 | 4        | testDescription2 |

    And metasfresh contains M_Products:
      | Identifier | Name                      |
      | p_1        | workflowProduct1_08092023 |
      | p_2        | workflowProduct2_08092023 |

    And create PP_WF_Node_Product:
      | PP_WF_Node_Product_ID.Identifier | AD_Workflow_ID.Identifier | AD_WF_Node_ID.Identifier | M_Product_ID.Identifier | OPT.Qty | OPT.Specification | OPT.IsSubcontracting |
      | wfNodeProduct_1                  | workflow_1                | wfNode_1                 | p_1                     | 4       | testSpecification | Y                    |
      | wfNodeProduct_2                  | workflow_1                | wfNode_2                 | p_2                     | 4       | testSpecification | Y                    |

    When clone AD_Workflow:
      | AD_Workflow_ID.Identifier | ClonedWorkflow.AD_Workflow_ID.Identifier |
      | workflow_1                | clonedWorkflow_1                         |

    Then validate AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                                                | WorkflowType | OPT.Description | OPT.Help | OPT.AccessLevel | OPT.DurationUnit | OPT.Version | OPT.ValidFrom | OPT.ValidTo | OPT.Priority | OPT.DurationLimit | OPT.Duration | OPT.Cost | OPT.WaitingTime | OPT.IsDefault |
      | clonedWorkflow_1          | testName(copied on 18.08.2022, 14:30 by metasfresh) | M            | testDescription | testHelp | 1               | D                | 0           | 2022-08-18    | 2022-08-19  | 0            | 0                 | 2            | 1        | 1               | false         |

    And after not more than 10s, AD_WF_Node are found:
      | AD_WF_Node_ID.Identifier | AD_Workflow_ID.Identifier | OPT.S_Resource_ID.Identifier | Duration | OPT.Description  | OPT.Name  | OPT.Value  |
      | clonedWFNode_1           | clonedWorkflow_1          | testResource                 | 4        | testDescription1 | testName1 | testValue1 |
      | clonedWFNode_2           | clonedWorkflow_1          | testResource                 | 4        | testDescription2 | testName2 | testValue2 |

    Then validate AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                                                | WorkflowType | OPT.AD_WF_Node_ID.Identifier |
      | clonedWorkflow_1          | testName(copied on 18.08.2022, 14:30 by metasfresh) | M            | clonedWFNode_2               |

    And after not more than 10s, PP_WF_Node_Product are found:
      | PP_WF_Node_Product_ID.Identifier | AD_Workflow_ID.Identifier | AD_WF_Node_ID.Identifier | M_Product_ID.Identifier | OPT.Qty | OPT.Specification | OPT.IsSubcontracting |
      | clonedWFNodeProduct_1            | clonedWorkflow_1          | clonedWFNode_1           | p_1                     | 4       | testSpecification | Y                    |
      | clonedWFNodeProduct_2            | clonedWorkflow_1          | clonedWFNode_2           | p_2                     | 4       | testSpecification | Y                    |
