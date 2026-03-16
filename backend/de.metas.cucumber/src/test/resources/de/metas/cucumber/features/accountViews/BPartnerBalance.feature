@from:cucumber
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01000_Accounting
@ghActions:run_on_executor7
Feature: BPartner Balance View
## me03#28782: BPartner Balance view shows Fact_Acct entries with running balance

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-15T10:00:00+01:00[Europe/Berlin]
    And documents are accounted immediately

  @from:cucumber
  @Id:S0300.100
  Scenario: BPartner Balance view shows invoice postings with correct running balance
    Given metasfresh contains M_Products:
      | Identifier |
      | p_balance  |
    And metasfresh contains M_PricingSystems
      | Identifier  |
      | ps_balance  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_balance | ps_balance         | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | plv_balance | pl_balance     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_balance            | p_balance    | 100.0    | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | bp_balance_1 | N        | Y          | ps_balance         |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_bal_1   | true    | bp_balance_1  | 2026-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID | QtyEntered |
      | ol_bal_1      | order_bal_1 | p_balance    | 10         |
    When the order identified by order_bal_1 is completed
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_bal_1               | ol_bal_1       |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | ic_bal_1               |
    And after not more than 60s, C_Invoice is found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | inv_bal_1    | ic_bal_1               |
    Then Fact_Acct records balances for documents:
      | AD_Table_ID.TableName | Record_ID |
      | C_Invoice             | inv_bal_1 |
