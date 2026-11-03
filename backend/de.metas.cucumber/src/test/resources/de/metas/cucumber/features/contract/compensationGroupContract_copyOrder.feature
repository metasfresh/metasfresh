@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Copying a sales order does not copy the contract's compensation group
## F2070: Compensation Group Contract
  - The contract's discount line and group are created when the order is completed.
  - A copy of the order is a new draft: it gets them again when it is completed itself.
  - A manually assembled compensation group is copied as before.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name  | Value          |
      | goodsCategory | Ware  | WareCopyOrder  |
      | pfandCategory | Pfand | PfandCopyOrder |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier |
      | goodsProduct    | goodsCategory                        |
      | pfandProduct    | pfandCategory                        |
      | discountProduct | goodsCategory                        |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | contractPL | contractPS                    | DE                        | EUR                 | contractPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | contractPLV | contractPL                | contractPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods    | contractPLV                       | goodsProduct            | 1000     | PCE               | Normal                        |
      | pp_pfand    | contractPLV                       | pfandProduct            | 200      | PCE               | Normal                        |
      | pp_discount | contractPLV                       | discountProduct         | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_Contract_Change:
      | Identifier   | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | contractChg1 | zeroDurTrans                        | SU     | Qu             | 0        | day          |

    And metasfresh contains C_BPartners:
      | Identifier   | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | headOfficeBP | Y              | contractPS                    |
      | storeBP      | Y              | contractPS                    |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          | OPT.IsAdditive |
      | mainSchema | Bonus Ware 3% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | mainSchemaLine | mainSchema                               | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name          | C_CompensationGroup_Schema_ID.Identifier |
      | mainSettings | Main settings | mainSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | mainSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name            | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | mainConditions | Main conditions | CompensationGroup | zeroDurTrans                            | mainSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | mainTerm   | mainConditions                      | headOfficeBP                | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by mainTerm is completed


  # ##############################################################################################
  # Copy a completed sales order that got the contract's discount line
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  Scenario: The copy of a completed sales order gets the contract's discount line only when it is completed itself
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_Order_CompensationGroups:
      | Identifier  | C_Order_ID.Identifier | Name        |
      | bundleGroup | order                 | Mischkarton |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Order_CompensationGroup_ID.Identifier |
      | ol_goods   | order                 | goodsProduct            | 1          |                                             |
      | ol_pfand   | order                 | pfandProduct            | 1          |                                             |
      | ol_bundle  | order                 | goodsProduct            | 2          | bundleGroup                                 |

    # 3% of the ungrouped goods line (1000); the Pfand line and the manually grouped line are not in the base
    And the order identified by order is completed
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | order                 | discountProduct         | 1          | true                        | 3                               | -30   | mainTerm                          |

    # ── the user copies the completed order ──
    When C_Order is cloned
      | C_Order_ID.Identifier | ClonedOrder.C_Order_ID.Identifier |
      | order                 | orderCopy                         |

    # the copy has no contract discount line and no contract group; the manual group is copied
    Then the order identified by orderCopy has 3 order lines
    And the C_Order_CompensationGroups of order "orderCopy" are:
      | Identifier      | Name        | C_Flatrate_Term_ID |
      | bundleGroupCopy | Mischkarton | null               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Order_CompensationGroup_ID.Identifier |
      | ol_goodsCopy              | orderCopy             | goodsProduct            | 1          | null                                        |
      | ol_pfandCopy              | orderCopy             | pfandProduct            | 1          | null                                        |
      | ol_bundleCopy             | orderCopy             | goodsProduct            | 2          | bundleGroupCopy                             |

    # ── completing the copy creates the contract's discount line again ──
    When the order identified by orderCopy is completed
    Then the order identified by orderCopy has 4 order lines
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discountCopy           | orderCopy             | discountProduct         | 1          | true                        | 3                               | -30   | mainTerm                          |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Order_CompensationGroup_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_goodsCopy              |                                             | mainTerm                          |
      | ol_bundleCopy             | bundleGroupCopy                             |                                   |


  # ##############################################################################################
  # Void a completed sales order with its related documents and recreate it
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  Scenario: The order recreated when voiding a completed sales order gets the contract's discount line only when it is completed itself
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods   | order                 | goodsProduct            | 1          |
      | ol_pfand   | order                 | pfandProduct            | 1          |

    And the order identified by order is completed
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | order                 | discountProduct         | 1          | true                        | -30   | mainTerm                          |

    # ── the user voids the order with its related documents and recreates it ──
    When the completed order "order" is voided with related documents and recreated as "orderRecreated"

    # the recreated order is a draft without the contract's discount line and group
    Then validate the created orders
      | C_Order_ID     | DocStatus | processed |
      | order          | VO        | true      |
      | orderRecreated | DR        | false     |
    And the order identified by orderRecreated has 2 order lines
    And no C_Order_CompensationGroup exists for order "orderRecreated"

    # ── completing the recreated order creates the contract's discount line again ──
    When the order identified by orderRecreated is completed
    Then the order identified by orderRecreated has 3 order lines
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discountRecreated      | orderRecreated        | discountProduct         | 1          | true                        | -30   | mainTerm                          |
