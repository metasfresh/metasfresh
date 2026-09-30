@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract-triggered compensation group on sales-order completion
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name  | Value       |
      | goodsCategory | Ware  | WareS32353  |
      | pfandCategory | Pfand | PfandS32353 |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier |
      | contractProduct |                                       |
      | goodsProduct    | goodsCategory                         |
      | pfandProduct    | pfandCategory                         |
      | discountProduct | goodsCategory                         |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | contractPL | contractPS                    | DE                        | EUR                  | contractPL | true  | false          | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | contractPLV | contractPL                | contractPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods    | contractPLV                       | goodsProduct             | 1000     | PCE                | Normal                        |
      | pp_pfand    | contractPLV                       | pfandProduct             | 200      | PCE                | Normal                        |
      | pp_discount | contractPLV                       | discountProduct          | 1        | PCE                | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                 | day                   | EO                 | 2026,2027                 |
    And metasfresh contains C_Contract_Change:
      | Identifier   | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | contractChg1 | zeroDurTrans                         | SU     | Qu              | 0        | day          |

    And metasfresh contains C_BPartners:
      | Identifier   | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | headOfficeBP | Y              | contractPS                    |
      | storeBP      | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          | OPT.IsAdditive |
      | mainSchema | Bonus Ware 3% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | mainSchemaLine | mainSchema                                | discountProduct          | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name          | C_CompensationGroup_Schema_ID.Identifier |
      | mainSettings | Main settings | mainSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | mainSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name            | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | mainConditions | Main conditions | CompensationGroup | zeroDurTrans                            | mainSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | mainTerm   | mainConditions                       | headOfficeBP                 | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by mainTerm is completed

  # ##############################################################################################
  # Discount only on ungrouped, in-base lines
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC10
  Scenario: Completing a sales order adds the contract's discount line only on the ungrouped, in-base lines
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order10    | true    | storeBP                  | storeBP                                | 2026-07-01  | headOfficeBP                     |

    And metasfresh contains C_Order_CompensationGroups:
      | Identifier  | C_Order_ID.Identifier | Name        |
      | bundleGroup | order10                | Mischkarton |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Order_CompensationGroup_ID.Identifier |
      | ol_goods   | order10                | goodsProduct             | 1          |                                              |
      | ol_pfand   | order10                | pfandProduct             | 1          |                                              |
      | ol_bundle  | order10                | goodsProduct             | 1          | bundleGroup                                  |

    And the order identified by order10 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | order10               | discountProduct         | 1          | true                        | 3                               | -30   | mainTerm                          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Order_CompensationGroup_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_bundle                 | bundleGroup                                 |                                   |
      | ol_goods                  |                                             | mainTerm                          |
      | ol_pfand                  | null                                        |                                   |

  # ##############################################################################################
  # The schema line's applies-to category is a parent category: a goods product in one of its
  # sub-categories is in the discount's base
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC54
  Scenario: A goods product in a sub-category of the schema line's applies-to category gets the discount
    Given metasfresh contains M_Product_Category:
      | Identifier       | Name        | Value             | OPT.M_Product_Category_Parent_ID.Identifier |
      | goodsSubCategory | Ware Äpfel  | WareApfelS32353   | goodsCategory                               |

    And metasfresh contains M_Products:
      | Identifier       | OPT.M_Product_Category_ID.Identifier |
      | subCategoryGoods | goodsSubCategory                     |

    And metasfresh contains M_ProductPrices
      | Identifier          | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_subCategoryGoods | contractPLV                       | subCategoryGoods        | 500      | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderSubCategory | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_subCategoryGoods      | orderSubCategory      | subCategoryGoods        | 1          |
      | ol_subCategoryPfand      | orderSubCategory      | pfandProduct            | 1          |

    And the order identified by orderSubCategory is completed

    # 3% of the sub-category goods (500) only; Pfand is outside the applies-to category
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_subCategoryDiscount    | orderSubCategory      | discountProduct         | 1          | true                        | 3                               | -15   | mainTerm                          |

    And the order identified by orderSubCategory has 3 order lines

  # ##############################################################################################
  # No contract at all, or contract exists but the order doc type is not listed
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC11
  Scenario: No group is added when there is no matching contract or the order document type is not listed
    Given metasfresh contains C_BPartners:
      | Identifier        | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | noContractBP       | Y              | contractPS                    |
      | docTypeNotListedBP | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        | OPT.IsAdditive |
      | poOnlySchema | Purchase order only schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | poOnlySchemaLine | poOnlySchema                                | discountProduct          | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name                                | C_CompensationGroup_Schema_ID.Identifier |
      | poOnlySettings | Settings (purchase order only) | poOnlySchema                               |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | poOnlySettings                                       | docTypePurchaseOrder    |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name            | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | poOnlyConditions | Purchase order only conditions | CompensationGroup | contractProduct                       | zeroDurTrans                             | poOnlySettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | poOnlyTerm   | poOnlyConditions                       | docTypeNotListedBP           | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by poOnlyTerm is completed

    And metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderNoContract     | true    | noContractBP              | 2026-07-01  |
      | orderDocTypeMissing | true    | docTypeNotListedBP        | 2026-07-01  |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_nc      | orderNoContract        | goodsProduct             | 1          |
      | ol_dtm     | orderDocTypeMissing    | goodsProduct             | 1          |

    And the order identified by orderNoContract is completed
    And the order identified by orderDocTypeMissing is completed

    Then no C_Order_CompensationGroup exists for order "orderNoContract"
    And no C_Order_CompensationGroup exists for order "orderDocTypeMissing"

  # ##############################################################################################
  # Order date exactly on Start/End date (inclusive) and EndDate+1 -> none
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC12
  Scenario: The contract fires only within its Start/End date window, inclusive
    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderBefore | true    | storeBP                  | storeBP                                | 2026-06-14  | headOfficeBP                     |
      | orderStart  | true    | storeBP                  | storeBP                                | 2026-06-15  | headOfficeBP                     |
      | orderEnd    | true    | storeBP                  | storeBP                                | 2026-12-31  | headOfficeBP                     |
      | orderAfter  | true    | storeBP                  | storeBP                                | 2027-01-01  | headOfficeBP                     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_before  | orderBefore            | goodsProduct             | 1          |
      | ol_start   | orderStart             | goodsProduct             | 1          |
      | ol_end     | orderEnd               | goodsProduct             | 1          |
      | ol_after   | orderAfter             | goodsProduct             | 1          |

    And the order identified by orderBefore is completed
    And the order identified by orderStart is completed
    And the order identified by orderEnd is completed
    And the order identified by orderAfter is completed

    Then no C_Order_CompensationGroup exists for order "orderBefore"
    And no C_Order_CompensationGroup exists for order "orderAfter"

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discountStart            | orderStart             | discountProduct          | 1          | true                         | -30   |
      | ol_discountEnd              | orderEnd               | discountProduct          | 1          | true                         | -30   |

  # ##############################################################################################
  # A schema line's base with no matching lines is skipped (no 0.00 line);
  # an order entirely out of every base gets no group and completes normally
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC13
  Scenario: A schema line's base with no matching lines is skipped; a fully out-of-base order gets no group
    Given metasfresh contains M_Product_Category:
      | Identifier        | Name       | Value            |
      | packagingCategory | Verpackung | VerpackungS32353 |

    And metasfresh contains M_Products:
      | Identifier               | OPT.M_Product_Category_ID.Identifier |
      | discountProductPackaging | packagingCategory                    |

    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_discountPackaging | contractPLV                       | discountProductPackaging | 1        | PCE                | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | rf1BP      | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       | OPT.IsAdditive |
      | rf1Schema  | RF1 schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier  | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | rf1SchemaLineGoods | rf1Schema                                 | discountProduct           | 3                         | goodsCategory                        |
      | rf1SchemaLinePack  | rf1Schema                                 | discountProductPackaging  | 2                         | packagingCategory                    |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name         | C_CompensationGroup_Schema_ID.Identifier |
      | rf1Settings | RF1 settings | rf1Schema                                 |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | rf1Settings                                         | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name           | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | rf1Conditions | RF1 conditions | CompensationGroup | contractProduct                       | zeroDurTrans                             | rf1Settings                                             |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | rf1Term    | rf1Conditions                        | rf1BP                        | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by rf1Term is completed

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderOnlyGoods      | true    | rf1BP                     | 2026-07-01  |
      | orderNoInBaseLines  | true    | rf1BP                     | 2026-07-01  |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_og      | orderOnlyGoods         | goodsProduct             | 1          |
      | ol_nib     | orderNoInBaseLines     | pfandProduct             | 1          |

    And the order identified by orderOnlyGoods is completed
    And the order identified by orderNoInBaseLines is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_ogDiscount               | orderOnlyGoods         | discountProduct          | 1          | true                         | -30   |

    And the order identified by orderOnlyGoods has 2 order lines
    And no C_Order_CompensationGroup exists for order "orderNoInBaseLines"
    And the order identified by orderNoInBaseLines has 1 order lines

  # ##############################################################################################
  # A discount product not on the price list -> refused with the existing pricing error
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC14
  Scenario: A discount product not on the order's price list refuses completion with the pricing error
    Given metasfresh contains M_Products:
      | Identifier               | OPT.M_Product_Category_ID.Identifier |
      | discountProductNoPricing | goodsCategory                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | rf3BP      | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       | OPT.IsAdditive |
      | rf3Schema  | RF3 schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier  | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | rf3SchemaLine | rf3Schema                                 | discountProductNoPricing  | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name         | C_CompensationGroup_Schema_ID.Identifier |
      | rf3Settings | RF3 settings | rf3Schema                                 |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | rf3Settings                                         | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name           | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | rf3Conditions | RF3 conditions | CompensationGroup | contractProduct                       | zeroDurTrans                             | rf3Settings                                             |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | rf3Term    | rf3Conditions                        | rf3BP                        | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by rf3Term is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderRf3   | true    | rf3BP                     | 2026-07-01  |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_rf3     | orderRf3               | goodsProduct             | 1          |

    Then the order identified by orderRf3 cannot be completed because the error message contains discountProductNoPricing

  # ##############################################################################################
  # Order partner != invoice partner, each with its own contract -> only the
  # invoice partner's contract applies
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC15
  Scenario: Matching is by invoice partner — the order partner's own contract does not apply
    Given metasfresh contains M_Products:
      | Identifier           | OPT.M_Product_Category_ID.Identifier |
      | storeDiscountProduct | goodsCategory                        |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_storeDiscount | contractPLV                       | storeDiscountProduct     | 1        | PCE                | Normal                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name         | OPT.IsAdditive |
      | storeSchema | Store schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | storeSchemaLine | storeSchema                               | storeDiscountProduct     | 5                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | storeSettings | Store settings | storeSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | storeSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name              | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | storeConditions | Store conditions  | CompensationGroup | contractProduct                       | zeroDurTrans                             | storeSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | storeTerm  | storeConditions                      | storeBP                      | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by storeTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order15    | true    | storeBP                  | storeBP                                | 2026-07-01  | headOfficeBP                     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_15      | order15                | goodsProduct             | 1          |

    And the order identified by order15 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_15Discount               | order15                | discountProduct          | 1          | true                         | -30   |

    And the order identified by order15 has 2 order lines

  # ##############################################################################################
  # Real-world drop-ship case (sales side only) — real-world drop-ship sample lines, 3.00% Bonus Ware on
  # goods only, Pfand excluded; the group carries the term
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC16
  Scenario: The real-world drop-ship sample lines produce the exact contracted bonus amount
    Given metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | elstar1    | goodsCategory                        |
      | elstar2    | goodsCategory                        |
      | gala       | goodsCategory                        |
      | pfand1     | pfandCategory                        |
      | pfand2     | pfandCategory                        |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_elstar1 | contractPLV                       | elstar1                 | 921.60   | PCE               | Normal                        |
      | pp_elstar2 | contractPLV                       | elstar2                 | 672.00   | PCE               | Normal                        |
      | pp_gala    | contractPLV                       | gala                    | 561.60   | PCE               | Normal                        |
      | pp_pfand1  | contractPLV                       | pfand1                  | 416.88   | PCE               | Normal                        |
      | pp_pfand2  | contractPLV                       | pfand2                  | 185.28   | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderSample   | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_elstar1 | orderSample              | elstar1                 | 1          |
      | ol_elstar2 | orderSample              | elstar2                 | 1          |
      | ol_gala    | orderSample              | gala                    | 1          |
      | ol_pfand1  | orderSample              | pfand1                  | 1          |
      | ol_pfand2  | orderSample              | pfand2                  | 1          |

    And the order identified by orderSample is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_sampleDiscount            | orderSample              | discountProduct         | 1          | true                        | 3                               | -64.66 | mainTerm                          |

    And the order identified by orderSample has 6 order lines

  # ##############################################################################################
  # Goods + packaging bonus, each on its own base — a genuine HU packing-material order line
  # (IsPackagingMaterial=true), auto-created by de.metas.handlingunits' own BEFORE_PREPARE
  # interceptor, is the "Verpackung" base; Pfand is in neither base
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC17
  Scenario: Goods and packaging bonuses are each computed on their own base
    Given metasfresh contains M_Product_Category:
      | Identifier           | Name       | Value               |
      | goodsAndPackagingPackagingCategory | Verpackung | VerpackungS32353GoodsAndPackaging |

    And metasfresh contains M_Products:
      | Identifier                  | OPT.M_Product_Category_ID.Identifier |
      | goodsAndPackagingPackingProduct           | goodsAndPackagingPackagingCategory                 |
      | goodsAndPackagingGoodsDiscountProduct     | goodsCategory                        |
      | goodsAndPackagingPackagingDiscountProduct | goodsAndPackagingPackagingCategory                 |

    And metasfresh contains M_ProductPrices
      | Identifier              | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier     | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goodsAndPackagingPacking           | contractPLV                       | goodsAndPackagingPackingProduct           | 2        | PCE               | Normal                        |
      | pp_goodsAndPackagingGoodsDiscount     | contractPLV                       | goodsAndPackagingGoodsDiscountProduct     | 1        | PCE               | Normal                        |
      | pp_goodsAndPackagingPackagingDiscount | contractPLV                       | goodsAndPackagingPackagingDiscountProduct | 1        | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | Identifier |
      | goodsAndPackagingHuPI    |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | goodsAndPackagingHuPIV   | goodsAndPackagingHuPI               | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | Identifier           | M_Product_ID.Identifier |
      | goodsAndPackagingHuPackingMaterial | goodsAndPackagingPackingProduct       |
    And metasfresh contains M_HU_PI_Item:
      | Identifier  | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | goodsAndPackagingHuPiItem | goodsAndPackagingHuPIV                      | 0   | PM       | goodsAndPackagingHuPackingMaterial               |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier         | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | goodsAndPackagingHuPiItemProduct | goodsAndPackagingHuPiItem                | goodsProduct            | 10  |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | goodsAndPackagingBP      | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                            | OPT.IsAdditive |
      | goodsAndPackagingSchema  | Bonus Ware 3% + Verpackung 0,6% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier           | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier     | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | goodsAndPackagingSchemaLineGoods   | goodsAndPackagingSchema                                | goodsAndPackagingGoodsDiscountProduct     | 3                         | goodsCategory                        |
      | goodsAndPackagingSchemaLinePacking | goodsAndPackagingSchema                                | goodsAndPackagingPackagingDiscountProduct | 0.6                       | goodsAndPackagingPackagingCategory                 |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name         | C_CompensationGroup_Schema_ID.Identifier |
      | goodsAndPackagingSettings | Goods and packaging settings | goodsAndPackagingSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | goodsAndPackagingSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name           | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | goodsAndPackagingConditions | Goods and packaging conditions | CompensationGroup | contractProduct                      | zeroDurTrans                            | goodsAndPackagingSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | goodsAndPackagingTerm    | goodsAndPackagingConditions                       | goodsAndPackagingBP                       | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by goodsAndPackagingTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderGoodsAndPackaging   | true    | goodsAndPackagingBP                    | 2026-07-01  |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_goodsAndPackagingGoods | orderGoodsAndPackaging              | goodsProduct            | 100        | goodsAndPackagingHuPiItemProduct                     |
      | ol_goodsAndPackagingPfand | orderGoodsAndPackaging              | pfandProduct            | 1          |                                        |

    And the order identified by orderGoodsAndPackaging is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier     | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_goodsAndPackagingPacking             | orderGoodsAndPackaging              | goodsAndPackagingPackingProduct           | 10         | false                       |                                 |       |                                   |
      | ol_goodsAndPackagingGoodsDiscount       | orderGoodsAndPackaging              | goodsAndPackagingGoodsDiscountProduct     | 1          | true                        | 3                               | -3000 | goodsAndPackagingTerm                           |
      | ol_goodsAndPackagingPackagingDiscount   | orderGoodsAndPackaging              | goodsAndPackagingPackagingDiscountProduct | 1          | true                        | 0.6                             | -0.12 | goodsAndPackagingTerm                           |

    And the order identified by orderGoodsAndPackaging has 5 order lines

  # ##############################################################################################
  # Additive vs. compounding compensation lines on the same base (3.15% + 0.25% on 1 000)
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC18
  Scenario: Additive computes every discount line on the full base; non-additive compounds
    Given metasfresh contains M_Products:
      | Identifier          | OPT.M_Product_Category_ID.Identifier |
      | discountProduct2 | goodsCategory                        |

    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_discount2 | contractPLV                       | discountProduct2     | 1        | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier    | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | additiveBP | Y              | contractPS                    |
      | compoundBP | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier        | Name            | OPT.IsAdditive |
      | additiveSchema | Additive        | true           |
      | compoundSchema | Compounding     | false          |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier       | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.SeqNo |
      | additiveLine1 | additiveSchema                        | discountProduct         | 3.15                      | goodsCategory                        | 10        |
      | additiveLine2 | additiveSchema                        | discountProduct2     | 0.25                      | goodsCategory                        | 20        |
      | compoundLine1 | compoundSchema                        | discountProduct         | 3.15                      | goodsCategory                        | 10        |
      | compoundLine2 | compoundSchema                        | discountProduct2     | 0.25                      | goodsCategory                        | 20        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier          | Name                     | C_CompensationGroup_Schema_ID.Identifier |
      | additiveSettings | Additive settings        | additiveSchema                        |
      | compoundSettings | Compounding settings     | compoundSchema                        |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | additiveSettings                                | docTypeSalesOrder       |
      | compoundSettings                                | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier            | Name                       | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | additiveConditions | Additive conditions        | CompensationGroup | contractProduct                      | zeroDurTrans                            | additiveSettings                                    |
      | compoundConditions | Compounding conditions     | CompensationGroup | contractProduct                      | zeroDurTrans                            | compoundSettings                                    |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier      | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | additiveTerm | additiveConditions               | additiveBP               | 2026-06-15 | 2026-12-31 | DR            | false         |
      | compoundTerm | compoundConditions               | compoundBP               | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by additiveTerm is completed
    And the C_Flatrate_Term identified by compoundTerm is completed

    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderAdditive | true    | additiveBP            | 2026-07-01  |
      | orderCompound | true    | compoundBP            | 2026-07-01  |

    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_additiveGoods | orderAdditive      | goodsProduct            | 1          |
      | ol_compoundGoods | orderCompound      | goodsProduct            | 1          |

    And the order identified by orderAdditive is completed
    And the order identified by orderCompound is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  |
      | ol_additiveDiscount1   | orderAdditive      | discountProduct         | 1          | true                        | 3.15                            | -31.50 |
      | ol_additiveDiscount2   | orderAdditive      | discountProduct2     | 1          | true                        | 0.25                            | -2.50  |
      | ol_compoundDiscount1   | orderCompound      | discountProduct         | 1          | true                        | 3.15                            | -31.50 |
      | ol_compoundDiscount2   | orderCompound      | discountProduct2     | 1          | true                        | 0.25                            | -2.42  |

  @Id:S32353_TC19
  Scenario: The compensation-group interceptor is registered between HU's packing-material builder and freight
    Then the C_Order model interceptors are registered in this order:
      | de.metas.handlingunits.model.validator.C_Order                                              |
      | de.metas.contracts.compensationGroup.contract.interceptor.C_Order_ContractCompensationGroup |
      | de.metas.freighcost.interceptor.C_Order                                                     |
