@from:cucumber
Feature: budget issue interaction with effort control

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-09-22T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And create or update C_Project:
      | C_Project_ID.Identifier | Name               | Value              | C_Currency_ID.ISO_Code |
      | testProject             | testProject_220922 | testProject_220922 | EUR                    |

  @from:cucumber
  Scenario: New budget issue created with costcenter & project set then update issue invoiceable hours
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterBudgetTest100  | costCenterBudgetTest100_220922 | costCenterBudgetTest100_220922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | budgetTest100Issue    | budgetTest100IssueValue_220922 | budgetTest100IssueName_220922 | Internal  | N             | 10                    | costCenterBudgetTest100      | testProject                 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | budgetTest100EffortControl    | costCenterBudgetTest100  | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest100EffortControl    | costCenterBudgetTest100  | testProject             | 10                   |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.InvoiceableEffort |
      | budgetTest100Issue    | 15                    |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest100EffortControl    | costCenterBudgetTest100  | testProject             | 15                   |

  @from:cucumber
  Scenario: New budget issue created with costcenter & project set, were there is existing effort control
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterBudgetTest200  | costCenterBudgetTest200_220922 | costCenterBudgetTest200_220922 |
    And metasfresh contains S_EffortControl:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest200EffortControl    | costCenterBudgetTest200  | testProject             | 0                    |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | budgetTest200Issue    | budgetTest200IssueValue_220922 | budgetTest200IssueName_220922 | Internal  | N             | 20                    | costCenterBudgetTest200      | testProject                 |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest200EffortControl    | costCenterBudgetTest200  | testProject             | 20                   |

  @from:cucumber
  Scenario: Existing budget issue with costcenter & project set with an existing effort control then remove cost center from budget issue
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterBudgetTest300  | costCenterBudgetTest300_220922 | costCenterBudgetTest300_220922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | budgetTest300Issue    | budgetTest300IssueValue_220922 | budgetTest300IssueName_220922 | Internal  | N             | 30                    | costCenterBudgetTest300      | testProject                 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | budgetTest300EffortControl    | costCenterBudgetTest300  | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest300EffortControl    | costCenterBudgetTest300  | testProject             | 30                   |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.C_Activity_ID.Identifier |
      | budgetTest300Issue    | null                         |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest300EffortControl    | costCenterBudgetTest300  | testProject             | 0                    |

  @from:cucumber
  Scenario: Existing budget issue with costcenter & project set with an existing effort control then remove project from budget issue
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterBudgetTest400  | costCenterBudgetTest400_220922 | costCenterBudgetTest400_220922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.InvoiceableEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | budgetTest400Issue    | budgetTest400IssueValue_220922 | budgetTest400IssueName_220922 | Internal  | N             | 40                    | costCenterBudgetTest400      | testProject                 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | budgetTest400EffortControl    | costCenterBudgetTest400  | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest400EffortControl    | costCenterBudgetTest400  | testProject             | 40                   |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.C_Project_ID.Identifier |
      | budgetTest400Issue    | null                        |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.InvoiceableHours |
      | budgetTest400EffortControl    | costCenterBudgetTest400  | testProject             | 0                    |