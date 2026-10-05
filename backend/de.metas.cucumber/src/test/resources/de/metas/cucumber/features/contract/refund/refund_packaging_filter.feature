@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund contracts restricted to packaging options
## The refund of a contract condition with packaging options only applies to the sales lines that are delivered in one of these packagings

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | packCategory  |
      | bonusCategory |
    # not stocked: no inventory needed
    And metasfresh contains M_Products:
      | Identifier    | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct  | goodsCategory                        | false         |
      | cartonProduct | packCategory                         | false         |
      | crateProduct  | packCategory                         | false         |
      | bonusWare     | bonusCategory                        | false         |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | refundPS   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name     | SOTrx | IsTaxIncluded | PricePrecision |
      | refundPL   | refundPS                      | DE                        | EUR                 | refundPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name      | ValidFrom  |
      | refundPLV  | refundPL                  | refundPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods   | refundPLV                         | goodsProduct            | 100      | PCE               | Normal                        |
      | pp_carton  | refundPLV                         | cartonProduct           | 1        | PCE               | Normal                        |
      | pp_crate   | refundPLV                         | crateProduct            | 1        | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP | Y              | refundPS                      | I               |

    # the goods are delivered in a carton or on a crate (Pfandsteige)
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   |
      | piCarton     |
      | piCrate      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | piCartonVersion    | piCarton   | TU          | Y         |
      | piCrateVersion     | piCrate    | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID  |
      | pmCarton                | cartonProduct |
      | pmCrate                 | crateProduct  |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | itemCarton      | piCartonVersion    | 0   | PM       | pmCarton                |
      | itemCrate       | piCrateVersion     | 0   | PM       | pmCrate                 |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | itemProductCarton       | itemCarton      | goodsProduct | 10  |
      | itemProductCrate        | itemCrate       | goodsProduct | 10  |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier   | Type_Conditions |
      | condCarton   | Refund          |
      | condEveryone | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgCarton    | condCarton               | monthlySchedule      | 6             | goodsCategory         | bonusWare        |
      | cfgEveryone  | condEveryone             | monthlySchedule      | 2             | goodsCategory         | bonusWare        |
    # the carton contract only applies to goods that are delivered in a carton
    And metasfresh contains C_Flatrate_RefundConfig_PackingOptions:
      | C_Flatrate_RefundConfig_ID | M_HU_PackingMaterial_ID |
      | cfgCarton                  | pmCarton                |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termCarton   | condCarton                          | customerBP                  | 2026-07-01 | 2026-12-31 |
      | termEveryone | condEveryone                        | customerBP                  | 2026-07-01 | 2026-12-31 |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundPackagingFilter_TC1
  Scenario: A contract restricted to cartons only refunds the lines in a carton, an unrestricted contract refunds all lines
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | olCarton   | order1                | goodsProduct            | 10         | itemProductCarton                      |
      | olCrate    | order1                | goodsProduct            | 20         | itemProductCrate                       |
      | olNoPI     | order1                | goodsProduct            | 30         |                                        |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olCarton       | icCarton               |
      | olCrate        | icCrate                |
      | olNoPI         | icNoPI                 |

    # carton line 1000 -> 6% = 60; every line 6000 -> 2% = 120
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice |
      | refundCarton           | termCarton         | 60              |
      | refundEveryone         | termEveryone       | 120             |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundCarton                | icCarton                        | termCarton         | 60                  |
      | refundEveryone              | icCarton                        | termEveryone       | 20                  |
      | refundEveryone              | icCrate                         | termEveryone       | 40                  |
      | refundEveryone              | icNoPI                          | termEveryone       | 60                  |
