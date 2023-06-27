@from:cucumber
Feature: effort issue interaction with effort control

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-09-22T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And create or update C_Project:
      | C_Project_ID.Identifier | Name               | Value              | C_Currency_ID.ISO_Code |
      | testProject             | testProject_220922 | testProject_220922 | EUR                    |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                   | OPT.EMail                        |
      | timeBookingUser       | timeBookingUser_230922 | timeBookingUser_230922@email.com |

  @from:cucumber
  Scenario: New effort issue created with costcenter & project set, then update issue budget, then log 10h on issue budget, then log another 5h on issue budget and then change issue status to "invoiced"
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterEffortTest100  | costCenterEffortTest100_220922 | costCenterEffortTest100_220922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.BudgetedEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | effortTest100Issue    | effortTest100IssueValue_220922 | effortTest100IssueName_220922 | Internal  | Y             | 10                 | costCenterEffortTest100      | testProject                 |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortTest100EffortControl    | costCenterEffortTest100  | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest100EffortControl    | costCenterEffortTest100  | testProject             | 0:00                 | 0:00          | 10         | N                | N                 |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.BudgetedEffort |
      | effortTest100Issue    | 12                 |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest100EffortControl    | costCenterEffortTest100  | testProject             | 0:00                 | 0:00          | 12         | N                | N                 |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTest100TimeBooking_1  | effortTest100Issue    | 10:00           | timeBookingUser                  | 2022-09-23 |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest100EffortControl    | costCenterEffortTest100  | testProject             | 10:00                | 10:00         | 12         | N                | N                 |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTest100TimeBooking_2  | effortTest100Issue    | 5:00            | timeBookingUser                  | 2022-09-23 |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest100EffortControl    | costCenterEffortTest100  | testProject             | 15:00                | 15:00         | 12         | Y                | N                 |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.Status | OPT.Processed |
      | effortTest100Issue    | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest100EffortControl    | costCenterEffortTest100  | testProject             | 0:00                 | 15:00         | 12         | Y                | Y                 |

  @from:cucumber
  Scenario: New effort issue created with costcenter & project set, were there is existing effort control, then change issue status to "invoiced"
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterEffortTest200  | costCenterEffortTest200_220922 | costCenterEffortTest200_220922 |
    And metasfresh contains S_EffortControl:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.Budget | OPT.PendingEffortSum | OPT.EffortSum |
      | effortTest200EffortControl    | costCenterEffortTest200  | testProject             | 10         | 1:00                 | 1:00          |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.BudgetedEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier |
      | effortTest200Issue    | effortTest200IssueValue_220922 | effortTest200IssueName_220922 | Internal  | Y             | 20                 | costCenterEffortTest200      | testProject                 |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest200EffortControl    | costCenterEffortTest200  | testProject             | 1:00                 | 1:00          | 30         | N                | N                 |
    And metasfresh contains S_TimeBooking:
      | S_TimeBooking_ID.Identifier | S_Issue_ID.Identifier | HoursAndMinutes | AD_User_Performing_ID.Identifier | BookedDate |
      | effortTest200TimeBooking_2  | effortTest200Issue    | 2:00            | timeBookingUser                  | 2022-09-23 |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest200EffortControl    | costCenterEffortTest200  | testProject             | 3:00                 | 3:00          | 30         | N                | N                 |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.Status | OPT.Processed |
      | effortTest200Issue    | Invoiced   | true          |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest200EffortControl    | costCenterEffortTest200  | testProject             | 1:00                 | 3:00          | 30         | N                | Y                 |

  @from:cucumber
  Scenario: Existing effort issue with costcenter & project set with an existing effort control, then remove cost center from effort issue
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterEffortTest300  | costCenterEffortTest300_220922 | costCenterEffortTest300_220922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.BudgetedEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.IssueEffort |
      | effortTest300Issue    | effortTest300IssueValue_220922 | effortTest300IssueName_220922 | Internal  | Y             | 30                 | costCenterEffortTest300      | testProject                 | 1:00            |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortTest300EffortControl    | costCenterEffortTest300  | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest300EffortControl    | costCenterEffortTest300  | testProject             | 1:00                 | 1:00          | 30         | N                | N                 |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.C_Activity_ID.Identifier |
      | effortTest300Issue    | null                         |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest300EffortControl    | costCenterEffortTest300  | testProject             | 0:00                 | 0:00          | 0          | N                | N                 |

  @from:cucumber
  Scenario: Existing effort issue with costcenter & project set with an existing effort control, then remove project from effort issue
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                           | Value                          |
      | costCenterEffortTest400  | costCenterEffortTest400_220922 | costCenterEffortTest400_220922 |
    And metasfresh contains S_Issue:
      | S_Issue_ID.Identifier | Value                          | OPT.Name                      | IssueType | IsEffortIssue | OPT.BudgetedEffort | OPT.C_Activity_ID.Identifier | OPT.C_Project_ID.Identifier | OPT.IssueEffort |
      | effortTest400Issue    | effortTest400IssueValue_220922 | effortTest400IssueName_220922 | Internal  | Y             | 40                 | costCenterEffortTest400      | testProject                 | 2:00            |
    And after not more than 10s, S_EffortControl is found:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier |
      | effortTest400EffortControl    | costCenterEffortTest400  | testProject             |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest400EffortControl    | costCenterEffortTest400  | testProject             | 2:00                 | 2:00          | 40         | N                | N                 |
    And update S_Issue:
      | S_Issue_ID.Identifier | OPT.C_Project_ID.Identifier |
      | effortTest400Issue    | null                        |
    And after not more than 10s, S_EffortControl is validated:
      | S_EffortControl_ID.Identifier | C_Activity_ID.Identifier | C_Project_ID.Identifier | OPT.PendingEffortSum | OPT.EffortSum | OPT.Budget | OPT.IsOverBudget | OPT.IsIssueClosed |
      | effortTest400EffortControl    | costCenterEffortTest400  | testProject             | 0:00                 | 0:00          | 0          | N                | N                 |