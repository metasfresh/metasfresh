@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS Cash Withdrawal

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]

  # ##########################################################################
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
      | Identifier | C_ChargeType_ID.Identifier |
      | charge1    | chargeType1                |
      | charge2    | chargeType2                |
    And C_Charge_Acct is set for:
      | C_Charge_ID.Identifier | Ch_Expense_Acct | Ch_Revenue_Acct |
      | charge1                | 600100          | 400100          |
      | charge2                | 600200          | 400200          |

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC9
  Scenario: A cash withdrawal posts the charge expense against the till clearing account
    # Expense and revenue accounts differ, so the posting shows which one the charge selects
    Given metasfresh contains C_ElementValues:
      | Identifier    | Value  |
      | travelExpense | 600300 |
      | travelRevenue | 400300 |
    And metasfresh contains C_ChargeType:
      | Identifier           |
      | withdrawalChargeType |
    And metasfresh contains C_Charge:
      | Identifier  | C_ChargeType_ID.Identifier |
      | travelCosts | withdrawalChargeType       |
    And C_Charge_Acct is set for:
      | C_Charge_ID.Identifier | Ch_Expense_Acct | Ch_Revenue_Acct |
      | travelCosts            | 600300          | 400300          |
    And the charges of C_ChargeType withdrawalChargeType are offered as POS cash withdrawal categories
    And metasfresh contains C_BPartners:
      | Identifier |
      | walkIn     |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID |
      | tillCash   | EUR           |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | tillWh     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | tillPS     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | tillPL     | tillPS             | EUR           | true  | true          |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | tillCash            | tillPS             | tillPL         | walkIn        | walkIn                 | tillWh         |
    And the cash journal of POS terminal till is opened with 100 by metasfresh

    When a cash withdrawal is taken at POS terminal till by metasfresh:
      | C_Charge_ID | Amount | C_Payment_ID |
      | travelCosts | 12.00  | withdrawal   |

    Then the POS cash withdrawal categories of till are:
      | C_Charge_ID |
      | travelCosts |
    And validate payments
      | C_Payment_ID | IsReceipt | C_Charge_ID | PayAmt | DocStatus | DateTrx    | C_BP_BankAccount_ID |
      | withdrawal   | false     | travelCosts | 12.00  | CO        | 2026-09-24 | tillCash            |
    And Fact_Acct records are matching
      | AccountConceptualName | Account_ID    | AmtSourceDr | AmtSourceCr | Record_ID  |
      | Ch_Expense_Acct       | travelExpense | 12.00 EUR   |             | withdrawal |
      | B_InTransit_Acct      |               |             | 12.00 EUR   | withdrawal |
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount | C_Charge_ID |
      | CASH_INOUT | -12.00 | travelCosts |
    And the cash journal of POS terminal till has ending balance 88.00

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC10
  Scenario: A cash withdrawal against a charge without accounts stays unposted with its posting error recorded
    Given metasfresh contains C_ChargeType:
      | Identifier           |
      | withdrawalChargeType |
    And metasfresh contains C_Charge:
      | Identifier | C_ChargeType_ID.Identifier |
      | unmapped   | withdrawalChargeType       |
    And the C_Charge_Acct of charge unmapped is deactivated
    And the charges of C_ChargeType withdrawalChargeType are offered as POS cash withdrawal categories
    And metasfresh contains C_BPartners:
      | Identifier |
      | walkIn     |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID |
      | tillCash   | EUR           |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | tillWh     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | tillPS     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | tillPL     | tillPS             | EUR           | true  | true          |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | tillCash            | tillPS             | tillPL         | walkIn        | walkIn                 | tillWh         |
    And the cash journal of POS terminal till is opened with 100 by metasfresh

    When a cash withdrawal is taken at POS terminal till by metasfresh:
      | C_Charge_ID | Amount | C_Payment_ID |
      | unmapped    | 12.00  | withdrawal   |

    Then Wait until documents withdrawal fails to post
    And validate payments
      | C_Payment_ID | IsReceipt | C_Charge_ID | DocStatus | Posted | PostingError               |
      | withdrawal   | false     | unmapped    | CO        | E      | No Charge accounts defined |
    And the cash journal of POS terminal till contains lines:
      | Type       | Amount | C_Charge_ID |
      | CASH_INOUT | -12.00 | unmapped    |
    And the cash journal of POS terminal till has ending balance 88.00
