@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS Cash Withdrawal

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]

  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC5
  Scenario: Charges are configured with distinct expense and revenue accounts
    Given metasfresh contains C_ElementValues:
      | Identifier   | Value  |
      | expenseAcct1 | 600100 |
      | revenueAcct1 | 400100 |
      | expenseAcct2 | 600200 |
      | revenueAcct2 | 400200 |
    And metasfresh contains C_ChargeType:
      | Identifier  |
      | chargeType1 |
      | chargeType2 |
    And metasfresh contains C_Charge:
      | Identifier | Name                     | C_ChargeType_ID.Identifier |
      | charge1    | Cash Withdrawal Charge 1 | chargeType1                |
      | charge2    | Cash Withdrawal Charge 2 | chargeType2                |
    And C_Charge_Acct is set for:
      | C_Charge_ID.Identifier | Ch_Expense_Acct | Ch_Revenue_Acct |
      | charge1                | 600100          | 400100          |
      | charge2                | 600200          | 400200          |
