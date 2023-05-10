@from:cucumber
Feature: keep in sync issue cost center and cost center label

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-09-21T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: given there is an existing issue without cost center, set a cost center activity and check label is set
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier  | Value                              | OPT.Name                          | IssueType | IsEffortIssue |
      | costCenterTest100Issue | costCenterTest100IssueValue_210922 | costCenterTest100IssueName_210922 | Internal  | Y             |
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                     | Value                    |
      | costCenterTest100        | costCenterTest100_210922 | costCenterTest100_210922 |
    And update S_Issue:
      | S_Issue_ID.Identifier  | OPT.C_Activity_ID.Identifier |
      | costCenterTest100Issue | costCenterTest100            |
    And S_IssueLabel is found:
      | S_IssueLabel_ID.Identifier | S_Issue_ID.Identifier  | Label                         |
      | costCenterTest100Label     | costCenterTest100Issue | cost:costCenterTest100_210922 |

  @from:cucumber
  Scenario: given there is an existing issue without cost center, set a cost center label and check activity is set
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier  | Value                              | OPT.Name                          | IssueType | IsEffortIssue |
      | costCenterTest200Issue | costCenterTest200IssueValue_210922 | costCenterTest200IssueName_210922 | Internal  | Y             |
    And metasfresh contains S_IssueLabel:
      | S_IssueLabel_ID.Identifier | S_Issue_ID.Identifier  | Label                         |
      | costCenterTest200Label     | costCenterTest200Issue | cost:costCenterTest200_210922 |
    And load Cost Center Activity from issue:
      | S_Issue_ID.Identifier  | C_Activity_ID.Identifier |
      | costCenterTest200Issue | costCenterTest200        |
    And validate C_Activity:
      | C_Activity_ID.Identifier | Name                              | Value                    |
      | costCenterTest200        | costCenterTest200IssueName_210922 | costCenterTest200_210922 |

  @from:cucumber
  Scenario: given there is an existing issue with cost center set, change the cost center activity and check label is changed
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                     | Value                    |
      | costCenterTest300        | costCenterTest300_210922 | costCenterTest300_210922 |
      | newCostCenterTest        | newCostCenterTest_210922 | newCostCenterTest_210922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier  | Value                              | OPT.Name                          | IssueType | IsEffortIssue | OPT.C_Activity_ID.Identifier |
      | costCenterTest300Issue | costCenterTest300IssueValue_210922 | costCenterTest300IssueName_210922 | Internal  | Y             | costCenterTest300            |
    And S_IssueLabel is found:
      | S_IssueLabel_ID.Identifier | S_Issue_ID.Identifier  | Label                         |
      | costCenterTest300Label     | costCenterTest300Issue | cost:costCenterTest300_210922 |
    And update S_Issue:
      | S_Issue_ID.Identifier  | OPT.C_Activity_ID.Identifier |
      | costCenterTest300Issue | newCostCenterTest            |
    And S_IssueLabel is found:
      | S_IssueLabel_ID.Identifier | S_Issue_ID.Identifier  | Label                         |
      | newCostCenterTestLabel     | costCenterTest300Issue | cost:newCostCenterTest_210922 |

  @from:cucumber
  Scenario: given there is an existing issue with cost center set, remove the cost center activity and check label is removed
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                     | Value                    |
      | costCenterTest400        | costCenterTest400_210922 | costCenterTest400_210922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier  | Value                              | OPT.Name                          | IssueType | IsEffortIssue | OPT.C_Activity_ID.Identifier |
      | costCenterTest400Issue | costCenterTest400IssueValue_210922 | costCenterTest400IssueName_210922 | Internal  | Y             | costCenterTest400            |
    And S_IssueLabel is found:
      | S_IssueLabel_ID.Identifier | S_Issue_ID.Identifier  | Label                         |
      | costCenterTest400Label     | costCenterTest400Issue | cost:costCenterTest400_210922 |
    And update S_Issue:
      | S_Issue_ID.Identifier  | OPT.C_Activity_ID.Identifier |
      | costCenterTest400Issue | null                         |
    And S_IssueLabel is removed:
      | S_IssueLabel_ID.Identifier |
      | costCenterTest400Label     |

  @from:cucumber
  Scenario: given there is an existing issue with cost center set, remove the cost center label and check activity is removed
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                     | Value                    |
      | costCenterTest500        | costCenterTest500_210922 | costCenterTest500_210922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier  | Value                              | OPT.Name                          | IssueType | IsEffortIssue | OPT.C_Activity_ID.Identifier |
      | costCenterTest500Issue | costCenterTest500IssueValue_210922 | costCenterTest500IssueName_210922 | Internal  | Y             | costCenterTest500            |
    And S_IssueLabel is found:
      | S_IssueLabel_ID.Identifier | S_Issue_ID.Identifier  | Label                         |
      | costCenterTest500Label     | costCenterTest500Issue | cost:costCenterTest500_210922 |
    And remove S_IssueLabel:
      | S_IssueLabel_ID.Identifier |
      | costCenterTest500Label     |
    And Cost Center Activity removed from issue:
      | S_Issue_ID.Identifier  | C_Activity_ID.Identifier |
      | costCenterTest500Issue | costCenterTest500        |