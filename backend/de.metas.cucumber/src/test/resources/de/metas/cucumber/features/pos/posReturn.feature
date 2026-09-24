@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS Product Return

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]

  # ##########################################################################
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC11
  Scenario: A returned product is received and its credit priced at the till price
    Given metasfresh contains M_Warehouse:
      | Identifier       | IsQualityReturnWarehouse |
      | warehouse        |                          |
      | qualityWarehouse | Y                        |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | KGM      |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax7       | taxCategory      | 7    | DE                       | DE                        | false           |
    And metasfresh contains M_PricingSystems
      | Identifier          |
      | pricingSystem       |
      | walkInPricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID  | C_Currency_ID | SOTrx | IsTaxIncluded |
      | priceList       | pricingSystem       | EUR           | true  | true          |
      | walkInPriceList | walkInPricingSystem | EUR           | true  | true          |
    And metasfresh contains M_PriceList_Versions
      | Identifier             | M_PriceList_ID  |
      | priceListVersion       | priceList       |
      | walkInPriceListVersion | walkInPriceList |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_Product_ID | M_PriceList_Version_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | productPrice       | product      | priceListVersion       | 15.50    | KGM      | taxCategory      |
      | walkInProductPrice | product      | walkInPriceListVersion | 20.00    | KGM      | taxCategory      |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID |
      | cashbook   | EUR           |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.M_PricingSystem_ID |
      | customer   | walkInPricingSystem    |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | cashbook            | pricingSystem      | priceList      | customer      | customer               | warehouse      |

    When a product return is made at POS terminal till by metasfresh:
      | M_Product_ID | Qty | UOM | OPT.M_InOut_ID |
      | product      | 0.3 | KGM | return_1       |

    Then validate the created material receipt
      | M_InOut_ID | DocStatus | M_Warehouse_ID   | MovementType |
      | return_1   | CO        | qualityWarehouse | C+           |
    And validate the created material receipt lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | return_1   | product      | 0.3         |
    And load HUs assigned to M_InOut
      | M_InOut_ID | M_HU_ID     |
      | return_1   | return_1_hu |
    And validate M_HUs:
      | Identifier  | HUStatus | Qty       |
      | return_1_hu | A        | 0.300 KGM |
    And the return identified by return_1 was credited at the till price:
      | M_Product_ID | PriceEntered_Override | InvoiceRule_Override | IsError | C_Tax_Rate |
      | product      | 15.50                 | I                    | false   | 7          |
