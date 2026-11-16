@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS Cash Sale

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC8
  Scenario: An ordinary POS cash sale completes, journals and posts
    Given metasfresh contains C_BPartners:
      | Identifier |
      | customer   |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID |
      | cashbook   | EUR           |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | priceList  | pricingSystem      | EUR           | true  | true          |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax19      | taxCategory      | 19   | DE                       | DE                        | false           |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_Product_ID | M_PriceList_Version_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | productPrice | product      | priceListVersion       | 2.50     | PCE      | taxCategory      |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | cashbook            | pricingSystem      | priceList      | customer      | customer               | warehouse      |

    When the cash journal of POS terminal till is opened with 100 by metasfresh
    And a POS cash sale is made at till:
      | M_Product_ID | Price | C_TaxCategory_ID |
      | product      | 2.50  | taxCategory      |

    Then the cash journal of POS terminal till has ending balance 102.50
    And the cash journal of POS terminal till contains lines:
      | Type     | Amount |
      | CASH_PAY | 2.50   |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | Record_ID   |
      | B_InTransit_Acct       | 2.50 EUR    |             | tillReceipt |
      | B_UnallocatedCash_Acct |             | 2.50 EUR    | tillReceipt |
