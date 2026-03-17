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
  Scenario: BPartner Balance — invoice creates Fact_Acct entries with balanced DR/CR
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps_bal     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_bal     | ps_bal             | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier |  M_PriceList_ID |
      | plv_bal    |  pl_bal         |
    And metasfresh contains M_Products:
      | Identifier |
      | p_bal      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_bal                | p_bal        | 100.0    | PCE      | Normal           |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | bp_bal     | Y          | ps_bal             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bp_bal_loc | bp_bal        | Y               | Y               |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv_bal    | bp_bal        | 2026-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_bal   | inv_bal      | p_bal        | 10 PCE      |
    And the invoice identified by inv_bal is completed
    Then Fact_Acct records balances for documents:
      | AD_Table_ID.TableName | Record_ID |
      | C_Invoice             | inv_bal   |
