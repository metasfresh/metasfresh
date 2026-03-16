@from:cucumber
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01000_Accounting
@ghActions:run_on_executor7
Feature: Account Hierarchy View
## me03#28782: Account Hierarchy tree view shows chart of accounts with balances

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-15T10:00:00+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S0300.200
  Scenario: Account Hierarchy view loads chart of accounts
    Given load C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    Then the account hierarchy tree can be loaded for acctSchema
