@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor5
Feature: Check accounting for invoices with 0% tax
## F00700: Invoice

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00
    And documents are accounted immediately

    And metasfresh contains C_TaxCategory
      | Identifier      |
      | zeroTaxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | zeroTax    | zeroTaxCategory  | 0    | DE                       | DE                        |

    And metasfresh contains C_VAT_Codes:
      | Identifier  | C_Tax_ID | IsSOTrx | AmountType |
      | sales0_T    | zeroTax  | Y       | T          |
      | sales0_N    | zeroTax  | Y       | N          |
      | purchase0_T | zeroTax  | N       | T          |
      | purchase0_N | zeroTax  | N       | N          |

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList    | pricingSystem      | DE           | EUR           | true  |
      | purchasePriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID    |
      | salesPLV    | salesPriceList    |
      | purchasePLV | purchasePriceList |

    And metasfresh contains M_Products:
      | Identifier |
      | product1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product1     | 5.00     | PCE      | zeroTaxCategory  |
      | purchasePLV            | product1     | 5.00     | PCE      | zeroTaxCategory  |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | PO_PricingSystem_ID |
      | customer1  | Y          | N        | pricingSystem      |                     |
      | vendor1    | N          | Y        |                    | pricingSystem       |

    And metasfresh contains C_BPartner_Locations:
      | Identifier         | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer1_location | customer1     | Y               | Y               |
      | vendor1_location   | vendor1       | Y               | Y               |















# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0470_010
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  Scenario: sales invoice
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice1   | customer1     | 2022-05-11   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_100   | invoice1     | product1     | 1 PCE       | zeroTax  |
    And the invoice identified by invoice1 is completed
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID | M_Product_ID | C_Tax_ID | C_VAT_Code_ID |
      | C_Receivable_Acct     | 5 EUR       |             | customer1     | invoice1  | -            | -        | -             |
      | P_Revenue_Acct        |             | 5 EUR       | customer1     | invoice1  | product1     | zeroTax  | sales0_N      |
      | T_Due_Acct            | 0 EUR       | 0 EUR       | customer1     | invoice1  | -            | zeroTax  | sales0_T      |

    
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0470_020
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  Scenario: purchase invoice
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice1   | vendor1       | 2022-05-11   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invl_100   | invoice1     | product1     | 1 PCE       | zeroTax  |
    And the invoice identified by invoice1 is completed
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID | M_Product_ID | C_Tax_ID | C_VAT_Code_ID |
      | V_Liability_Acct      |             | 5 EUR       | vendor1       | invoice1  | -            | -        | -             |
      | P_Expense_Acct        | 5 EUR       |             | vendor1       | invoice1  | product1     | zeroTax  | purchase0_N   |
      | T_Credit_Acct         | 0 EUR       | 0 EUR       | vendor1       | invoice1  | -            | zeroTax  | purchase0_T   |
