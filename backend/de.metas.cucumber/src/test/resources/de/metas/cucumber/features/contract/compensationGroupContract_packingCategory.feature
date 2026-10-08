@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract compensation group — a discount line restricted to carton-packed order lines
## F2070: Compensation Group Contract
##
## A schema line with a packing-material category counts only the order lines whose packing instruction
## has a packing material with a product in that category or in one of its sub-categories.
##
## Not covered here, only by HUPackingMaterialProductCategoryProviderTest:
## - several active packing materials on one packing instruction (a unique index allows one active PM item per version)
## - a packing material without a product (order completion cannot create its packing-material line)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier     | Name           | Value                  | OPT.M_Product_Category_Parent_ID.Identifier |
      | goods_cat      | Ware           | WarePkgS32353          |                                             |
      | packaging_cat  | Verpackung     | VerpackungPkgS32353    |                                             |
      | carton_cat     | Kartonagen     | KartonagenPkgS32353    | packaging_cat                               |
      | carton_sub_cat | Kartonagen Sub | KartonagenSubPkgS32353 | carton_cat                                  |
      | crate_cat      | Pfandsteigen   | PfandsteigenPkgS32353  | packaging_cat                               |
      | foil_cat       | Folien         | FolienPkgS32353        | packaging_cat                               |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier |
      | goodsA          | goods_cat                            |
      | goodsB          | goods_cat                            |
      | goodsC          | goods_cat                            |
      | goodsD          | goods_cat                            |
      | goodsE          | goods_cat                            |
      | goodsF          | goods_cat                            |
      | discountGoods   | goods_cat                            |
      | discountCarton  | goods_cat                            |
      | cartonProduct10 | carton_cat                           |
      | cartonProduct12 | carton_sub_cat                       |
      | crateProduct    | crate_cat                            |
      | foilProduct     | foil_cat                             |

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
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goodsA       | contractPLV                       | goodsA                  | 1.20     | PCE               | Normal                        |
      | pp_goodsB       | contractPLV                       | goodsB                  | 1.10     | PCE               | Normal                        |
      | pp_goodsC       | contractPLV                       | goodsC                  | 1.40     | PCE               | Normal                        |
      | pp_goodsD       | contractPLV                       | goodsD                  | 1        | PCE               | Normal                        |
      | pp_goodsE       | contractPLV                       | goodsE                  | 1        | PCE               | Normal                        |
      | pp_goodsF       | contractPLV                       | goodsF                  | 1        | PCE               | Normal                        |
      | pp_discountGood | contractPLV                       | discountGoods           | 1        | PCE               | Normal                        |
      | pp_discountCart | contractPLV                       | discountCarton          | 1        | PCE               | Normal                        |
      | pp_crate        | contractPLV                       | crateProduct            | 2.50     | PCE               | Normal                        |
      | pp_carton10     | contractPLV                       | cartonProduct10         | 0        | PCE               | Normal                        |
      | pp_carton12     | contractPLV                       | cartonProduct12         | 0        | PCE               | Normal                        |
      | pp_foil         | contractPLV                       | foilProduct             | 0        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_Contract_Change:
      | Identifier   | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | contractChg1 | zeroDurTrans                        | SU     | Qu             | 0        | day          |

    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID.Identifier | IsInvoiceable |
      | pmCarton10 | cartonProduct10         | N             |
      | pmCarton12 | cartonProduct12         | N             |
      | pmFoil     | foilProduct             | N             |
      | pmCrate    | crateProduct            | Y             |

    And metasfresh contains C_BPartners:
      | Identifier  | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | cartonBP    | Y              | contractPS                    |
      | wholeBP     | Y              | contractPS                    |
      | twoPmBP     | Y              | contractPS                    |
      | noPiBP      | Y              | contractPS                    |
      | crateBP     | Y              | contractPS                    |
      | svcBP       | Y              | contractPS                    |
      | crateOnlyBP | Y              | contractPS                    |
      | reactBP     | Y              | contractPS                    |
      | retryBP     | Y              | contractPS                    |

  # ##############################################################################################
  # Reference case: goods 3 % on every goods line, carton bonus 0,6 % only on carton-packed lines.
  # Karton 10 kg and Karton 12 kg (a sub-category of the carton category) count, the crate does not,
  # the crate deposit line is in neither base.
  # 60 TU x 10 kg x 1,20 = 720,00 | 40 TU x 10 kg x 1,10 = 440,00 | 30 TU x 12 kg x 1,40 = 504,00
  # goods 3 % of 1.664,00 = 49,92 | carton 0,6 % of 1.224,00 = 7,34 | crate deposit 40 x 2,50 = 100,00
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC56
  Scenario: The carton bonus is computed only on the carton-packed goods lines
    Given metasfresh contains M_HU_PI:
      | Identifier |
      | piCarton10 |
      | piCrate    |
      | piCarton12 |
    And metasfresh contains M_HU_PI_Version:
      | Identifier  | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCarton10V | piCarton10            | TU          | Y         |
      | piCrateV    | piCrate               | TU          | Y         |
      | piCarton12V | piCarton12            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier     | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCarton10Item | piCarton10V                   | 0   | PM       | pmCarton10                         |
      | piCrateItem    | piCrateV                      | 0   | PM       | pmCrate                            |
      | piCarton12Item | piCarton12V                   | 0   | PM       | pmCarton12                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCarton10 | piCarton10Item             | goodsA                  | 10  |
      | pipCrate    | piCrateItem                | goodsB                  | 10  |
      | pipCarton12 | piCarton12Item             | goodsC                  | 12  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name                        | OPT.IsAdditive |
      | cartonSchema | Bonus Ware 3% + Karton 0,6% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier       | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | cartonLineGoods  | cartonSchema                             | discountGoods           | 3                         | goods_cat                            |                                                      |
      | cartonLineCarton | cartonSchema                             | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier     | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | cartonSettings | Carton settings | cartonSchema                             |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | cartonSettings                                     | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Name              | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | cartonConditions | Carton conditions | CompensationGroup | zeroDurTrans                            | cartonSettings                                         |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | cartonTerm | cartonConditions                    | cartonBP                    | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by cartonTerm is completed

    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderCarton | true    | cartonBP                 | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_carton10 | orderCarton           | goodsA                  | 600        | pipCarton10                            |
      | ol_crate    | orderCarton           | goodsB                  | 400        | pipCrate                               |
      | ol_carton12 | orderCarton           | goodsC                  | 360        | pipCarton12                            |

    And the order identified by orderCarton is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_goodsBonus             | orderCarton           | discountGoods           | 1          | true                        | 3                               | -49.92 | cartonTerm                        |
      | ol_cartonBonus            | orderCarton           | discountCarton          | 1          | true                        | 0.6                             | -7.34  | cartonTerm                        |
      | ol_deposit                | orderCarton           | crateProduct            | 40         | false                       |                                 |        |                                   |

    And the order identified by orderCarton has 8 order lines


  # ##############################################################################################
  # Whole-goods variant: no packing-material category, 0,8 % on the full goods base
  # 0,8 % of 1.664,00 = 13,31
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC57
  Scenario: Without a packing-material category the bonus is computed on all goods lines
    Given metasfresh contains M_HU_PI:
      | Identifier |
      | piCarton10 |
      | piCrate    |
      | piCarton12 |
    And metasfresh contains M_HU_PI_Version:
      | Identifier  | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCarton10V | piCarton10            | TU          | Y         |
      | piCrateV    | piCrate               | TU          | Y         |
      | piCarton12V | piCarton12            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier     | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCarton10Item | piCarton10V                   | 0   | PM       | pmCarton10                         |
      | piCrateItem    | piCrateV                      | 0   | PM       | pmCrate                            |
      | piCarton12Item | piCarton12V                   | 0   | PM       | pmCarton12                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCarton10 | piCarton10Item             | goodsA                  | 10  |
      | pipCrate    | piCrateItem                | goodsB                  | 10  |
      | pipCarton12 | piCarton12Item             | goodsC                  | 12  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name            | OPT.IsAdditive |
      | wholeSchema | Bonus Ware 0,8% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | wholeLineGoods | wholeSchema                              | discountGoods           | 0.8                       | goods_cat                            |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | wholeSettings | Whole settings | wholeSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | wholeSettings                                      | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | wholeConditions | Whole conditions | CompensationGroup | zeroDurTrans                            | wholeSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | wholeTerm  | wholeConditions                     | wholeBP                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by wholeTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderWhole | true    | wholeBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_whole10    | orderWhole            | goodsA                  | 600        | pipCarton10                            |
      | ol_wholeCrate | orderWhole            | goodsB                  | 400        | pipCrate                               |
      | ol_whole12    | orderWhole            | goodsC                  | 360        | pipCarton12                            |

    And the order identified by orderWhole is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_wholeBonus             | orderWhole            | discountGoods           | 1          | true                        | 0.8                             | -13.31 | wholeTerm                         |

    And the order identified by orderWhole has 7 order lines


  # ##############################################################################################
  # The packing instruction's goods item and its packing-material item are separate items of one
  # version (the real layout): the carton counts, a foil-only packing instruction does not
  # Carton schema line only: 10 % of the 100,00 carton line = 10,00
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC58
  Scenario: The carton counts when the packing material is a separate item of the packing instruction
    Given metasfresh contains M_HU_PI:
      | Identifier |
      | piCartonMi |
      | piFoilOnly |
    And metasfresh contains M_HU_PI_Version:
      | Identifier  | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCartonMiV | piCartonMi            | TU          | Y         |
      | piFoilOnlyV | piFoilOnly            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier        | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCartonMiGoods   | piCartonMiV                   | 0   | MI       | null                               |
      | piCartonMiPacking | piCartonMiV                   | 0   | PM       | pmCarton10                         |
      | piFoilOnlyGoods   | piFoilOnlyV                   | 0   | MI       | null                               |
      | piFoilOnlyPacking | piFoilOnlyV                   | 0   | PM       | pmFoil                             |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCartonMi | piCartonMiGoods            | goodsD                  | 10  |
      | pipFoilOnly | piFoilOnlyGoods            | goodsE                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name       | OPT.IsAdditive |
      | twoPmSchema | Karton 10% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | twoPmLine  | twoPmSchema                              | discountCarton          | 10                        | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name             | C_CompensationGroup_Schema_ID.Identifier |
      | twoPmSettings | Separate PM item | twoPmSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | twoPmSettings                                      | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | twoPmConditions | Separate PM item | CompensationGroup | zeroDurTrans                            | twoPmSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | twoPmTerm  | twoPmConditions                     | twoPmBP                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by twoPmTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderTwoPm | true    | twoPmBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_cartonMi | orderTwoPm            | goodsD                  | 100        | pipCartonMi                            |
      | ol_foilOnly | orderTwoPm            | goodsE                  | 50         | pipFoilOnly                            |

    And the order identified by orderTwoPm is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_twoPmBonus             | orderTwoPm            | discountCarton          | 1          | true                        | 10                              | -10.00 | twoPmTerm                         |

    And the order identified by orderTwoPm has 5 order lines


  # ##############################################################################################
  # A goods line without a packing instruction is outside the carton base
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC59
  Scenario: A goods line without a packing instruction does not count for the carton bonus
    Given metasfresh contains M_HU_PI:
      | Identifier |
      | piCartonX  |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCartonXV | piCartonX             | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier    | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCartonXItem | piCartonXV                    | 0   | PM       | pmCarton10                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCartonX | piCartonXItem              | goodsD                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name       | OPT.IsAdditive |
      | noPiSchema | Karton 10% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | noPiLine   | noPiSchema                               | discountCarton          | 10                        | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | noPiSettings | No PI settings | noPiSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | noPiSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | noPiConditions | No PI conditions | CompensationGroup | zeroDurTrans                            | noPiSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | noPiTerm   | noPiConditions                      | noPiBP                      | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by noPiTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderNoPi  | true    | noPiBP                   | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_withCart | orderNoPi             | goodsD                  | 100        | pipCartonX                             |
      | ol_noPi     | orderNoPi             | goodsF                  | 50         |                                        |

    And the order identified by orderNoPi is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_noPiBonus              | orderNoPi             | discountCarton          | 1          | true                        | 10                              | -10.00 | noPiTerm                          |

    And the order identified by orderNoPi has 4 order lines


  # ##############################################################################################
  # TS13 variant: an order with only crate-packed goods gets the 3 % line but no 0,6 % line (no 0,00 line)
  # crate goods 400 x 1,10 = 440,00 | goods 3 % = 13,20
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC61
  Scenario: A carton bonus line that matches no order line is not created
    Given metasfresh contains M_HU_PI:
      | Identifier |
      | piCrt      |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCrtV     | piCrt                 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCrtItem  | piCrtV                        | 0   | PM       | pmCrate                            |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCrt     | piCrtItem                  | goodsB                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                                      | OPT.IsAdditive |
      | crtSchema  | Bonus Ware 3% + Karton 0,6% (crate order) | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | crtLineGoods  | crtSchema                                | discountGoods           | 3                         | goods_cat                            |                                                      |
      | crtLineCarton | crtSchema                                | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name                                               | C_CompensationGroup_Schema_ID.Identifier |
      | crtSettings | Bonus Ware 3% + Karton 0,6% (crate order) settings | crtSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | crtSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name                                                 | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | crtConditions | Bonus Ware 3% + Karton 0,6% (crate order) conditions | CompensationGroup | zeroDurTrans                            | crtSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | crtTerm    | crtConditions                       | crateBP                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by crtTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderCrt   | true    | crateBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_crt     | orderCrt              | goodsB                  | 400        | pipCrt                                 |

    And the order identified by orderCrt is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_crtBonus               | orderCrt              | discountGoods           | 1          | true                        | 3                               | -13.20 | crtTerm                           |

    # goods line, 3 % line and the crate deposit line; no 0,6 % line
    And the order identified by orderCrt has 3 order lines


  # ##############################################################################################
  # Only the carton-packed goods line is a group member; crate goods, deposit and a line without packing instruction are not
  # carton goods 800,00 x 0,6 % = 4,80
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC62
  Scenario: A packing-material-only discount line counts just the carton-packed goods
    Given metasfresh contains M_HU_PI:
      | Identifier  |
      | piSvcCarton |
      | piSvcCrate  |
    And metasfresh contains M_HU_PI_Version:
      | Identifier   | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piSvcCartonV | piSvcCarton           | TU          | Y         |
      | piSvcCrateV  | piSvcCrate            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier      | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piSvcCartonItem | piSvcCartonV                  | 0   | PM       | pmCarton10                         |
      | piSvcCrateItem  | piSvcCrateV                   | 0   | PM       | pmCrate                            |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipSvcCarton | piSvcCartonItem            | goodsD                  | 10  |
      | pipSvcCrate  | piSvcCrateItem             | goodsE                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name        | OPT.IsAdditive |
      | svcSchema  | Karton 0,6% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | svcLineCarton | svcSchema                                | discountCarton          | 0.6                       |                                      | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name                 | C_CompensationGroup_Schema_ID.Identifier |
      | svcSettings | Karton 0,6% settings | svcSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | svcSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name                   | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | svcConditions | Karton 0,6% conditions | CompensationGroup | zeroDurTrans                            | svcSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | svcTerm    | svcConditions                       | svcBP                       | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by svcTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderSvc   | true    | svcBP                    | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_svcCarton | orderSvc              | goodsD                  | 800        | pipSvcCarton                           |
      | ol_svcCrate  | orderSvc              | goodsE                  | 400        | pipSvcCrate                            |
      | ol_goodsNoPi | orderSvc              | goodsF                  | 50         |                                        |

    And the order identified by orderSvc is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_svcBonus               | orderSvc              | discountCarton          | 1          | true                        | 0.6                             | -4.80 | svcTerm                           |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_svcCarton              | svcTerm                           |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Order_CompensationGroup_ID.Identifier |
      | ol_svcCrate               | null                                        |
      | ol_goodsNoPi              | null                                        |
    # the crate deposit line is no group member either
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Order_CompensationGroup_ID.Identifier |
      | ol_svcDeposit             | orderSvc              | crateProduct            | 40         | null                                        |

    # 3 goods lines, 2 packing-material lines (carton, crate deposit) and the 0,6 % line
    And the order identified by orderSvc has 6 order lines


  # ##############################################################################################
  # Crate goods only: the carton-only schema creates no discount line
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC63
  Scenario: A carton-only discount line on an order without carton-packed goods creates no group
    Given metasfresh contains M_HU_PI:
      | Identifier |
      | piCroCrate |
    And metasfresh contains M_HU_PI_Version:
      | Identifier  | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCroCrateV | piCroCrate            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier     | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCroCrateItem | piCroCrateV                   | 0   | PM       | pmCrate                            |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCroCrate | piCroCrateItem             | goodsB                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                      | OPT.IsAdditive |
      | croSchema  | Karton 0,6% (crate order) | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | croLineCarton | croSchema                                | discountCarton          | 0.6                       |                                      | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name                               | C_CompensationGroup_Schema_ID.Identifier |
      | croSettings | Karton 0,6% (crate order) settings | croSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | croSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name                                 | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | croConditions | Karton 0,6% (crate order) conditions | CompensationGroup | zeroDurTrans                            | croSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | croTerm    | croConditions                       | crateOnlyBP                 | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by croTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderCro   | true    | crateOnlyBP              | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_cro     | orderCro              | goodsB                  | 400        | pipCroCrate                            |

    And the order identified by orderCro is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Order_CompensationGroup_ID.Identifier |
      | ol_cro                    | null                                        |
    And no C_Order_CompensationGroup exists for order "orderCro"

    # goods line and crate deposit line only
    And the order identified by orderCro has 2 order lines


  # ##############################################################################################
  # Reactivating and completing again re-evaluates the packing instruction of the order lines
  # first completion: carton bonus 0,6 % of 1.224,00 = 7,34
  # line 20 re-packed into a carton: 0,6 % of 1.664,00 = 9,98 (goods 3 % stays 49,92)
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC64
  Scenario: Changing a line's packing instruction and completing again changes the carton base
    Given metasfresh contains M_HU_PI:
      | Identifier    |
      | piReaCarton10 |
      | piReaCrate    |
      | piReaCarton12 |
    And metasfresh contains M_HU_PI_Version:
      | Identifier     | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piReaCarton10V | piReaCarton10         | TU          | Y         |
      | piReaCrateV    | piReaCrate            | TU          | Y         |
      | piReaCarton12V | piReaCarton12         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier        | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piReaCarton10Item | piReaCarton10V                | 0   | PM       | pmCarton10                         |
      | piReaCrateItem    | piReaCrateV                   | 0   | PM       | pmCrate                            |
      | piReaCarton12Item | piReaCarton12V                | 0   | PM       | pmCarton12                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier           | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipReaCarton10       | piReaCarton10Item          | goodsA                  | 10  |
      | pipReaCrate          | piReaCrateItem             | goodsB                  | 10  |
      | pipReaCarton12       | piReaCarton12Item          | goodsC                  | 12  |
      | pipReaCrateNowCarton | piReaCarton10Item          | goodsB                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                                       | OPT.IsAdditive |
      | reaSchema  | Bonus Ware 3% + Karton 0,6% (reactivation) | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | reaLineGoods  | reaSchema                                | discountGoods           | 3                         | goods_cat                            |                                                      |
      | reaLineCarton | reaSchema                                | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name                                                | C_CompensationGroup_Schema_ID.Identifier |
      | reaSettings | Bonus Ware 3% + Karton 0,6% (reactivation) settings | reaSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | reaSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name                                                  | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | reaConditions | Bonus Ware 3% + Karton 0,6% (reactivation) conditions | CompensationGroup | zeroDurTrans                            | reaSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | reaTerm    | reaConditions                       | reactBP                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by reaTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderRea   | true    | reactBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_rea10   | orderRea              | goodsA                  | 600        | pipReaCarton10                         |
      | ol_rea20   | orderRea              | goodsB                  | 400        | pipReaCrate                            |
      | ol_rea30   | orderRea              | goodsC                  | 360        | pipReaCarton12                         |

    And the order identified by orderRea is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_reaGoodsBonus          | orderRea              | discountGoods           | 1          | true                        | 3                               | -49.92 | reaTerm                           |
      | ol_reaCartonBonus         | orderRea              | discountCarton          | 1          | true                        | 0.6                             | -7.34  | reaTerm                           |

    # a user reactivates an order whose invoice candidates already exist
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | ol_rea10                  | ic_rea10                          |
      | ol_rea20                  | ic_rea20                          |
      | ol_rea30                  | ic_rea30                          |
      | ol_reaGoodsBonus          | ic_reaGoodsBonus                  |
      | ol_reaCartonBonus         | ic_reaCartonBonus                 |

    # a user re-packs line 20 into a carton and completes the order again
    When the order identified by orderRea is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID |
      | ol_rea20                  | pipReaCrateNowCarton        |
    And the order identified by orderRea is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_reaGoodsBonus2         | orderRea              | discountGoods           | 1          | true                        | 3                               | -49.92 | reaTerm                           |
      | ol_reaCartonBonus2        | orderRea              | discountCarton          | 1          | true                        | 0.6                             | -9.98  | reaTerm                           |


  # ##############################################################################################
  # A completion that runs into a DB deadlock is rolled back and retried by the document engine;
  # the retried completion creates the same compensation group as an undisturbed one.
  # Same order and amounts as above, but both the first completion (drafted order) and the
  # completion after the reactivation (in-progress order) run into one deadlock each;
  # all steps work on the same order instance, as one caller would.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC71
  Scenario: A completion retried after a DB deadlock still creates the carton-base compensation group
    Given metasfresh contains M_HU_PI:
      | Identifier    |
      | piRtyCarton10 |
      | piRtyCrate    |
      | piRtyCarton12 |
    And metasfresh contains M_HU_PI_Version:
      | Identifier     | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piRtyCarton10V | piRtyCarton10         | TU          | Y         |
      | piRtyCrateV    | piRtyCrate            | TU          | Y         |
      | piRtyCarton12V | piRtyCarton12         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier        | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piRtyCarton10Item | piRtyCarton10V                | 0   | PM       | pmCarton10                         |
      | piRtyCrateItem    | piRtyCrateV                   | 0   | PM       | pmCrate                            |
      | piRtyCarton12Item | piRtyCarton12V                | 0   | PM       | pmCarton12                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier           | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipRtyCarton10       | piRtyCarton10Item          | goodsA                  | 10  |
      | pipRtyCrate          | piRtyCrateItem             | goodsB                  | 10  |
      | pipRtyCarton12       | piRtyCarton12Item          | goodsC                  | 12  |
      | pipRtyCrateNowCarton | piRtyCarton10Item          | goodsB                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                                         | OPT.IsAdditive |
      | rtySchema  | Bonus Ware 3% + Karton 0,6% (deadlock retry) | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | rtyLineGoods  | rtySchema                                | discountGoods           | 3                         | goods_cat                            |                                                      |
      | rtyLineCarton | rtySchema                                | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name                                                  | C_CompensationGroup_Schema_ID.Identifier |
      | rtySettings | Bonus Ware 3% + Karton 0,6% (deadlock retry) settings | rtySchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | rtySettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name                                                    | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | rtyConditions | Bonus Ware 3% + Karton 0,6% (deadlock retry) conditions | CompensationGroup | zeroDurTrans                            | rtySettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | rtyTerm    | rtyConditions                       | retryBP                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by rtyTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderRty   | true    | retryBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_rty10   | orderRty              | goodsA                  | 600        | pipRtyCarton10                         |
      | ol_rty20   | orderRty              | goodsB                  | 400        | pipRtyCrate                            |
      | ol_rty30   | orderRty              | goodsC                  | 360        | pipRtyCarton12                         |

    # an AFTER_COMPLETE interceptor saves the order before the deadlock, so that save also writes what prepareIt()/approveIt() set
    # (C_DocType_ID, IsApproved); it is rolled back, and the retried completion has to write these values again
    And the next completion of the order identified by orderRty saves the order with the description 'saved by every completion attempt' and then runs into a DB deadlock once
    And the order identified by orderRty is completed
    And the completion of the order identified by orderRty did run into the DB deadlock
    And validate the created orders
      | C_Order_ID.Identifier | processed | DocStatus | GrandTotal | C_DocType_ID.Identifier | IsApproved |
      | orderRty              | true      | CO        | 2031.02    | docTypeSalesOrder       | true       |

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_rtyGoodsBonus          | orderRty              | discountGoods           | 1          | true                        | 3                               | -49.92 | rtyTerm                           |
      | ol_rtyCartonBonus         | orderRty              | discountCarton          | 1          | true                        | 0.6                             | -7.34  | rtyTerm                           |

    # a user reactivates an order whose invoice candidates already exist
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | ol_rty10                  | ic_rty10                          |
      | ol_rty20                  | ic_rty20                          |
      | ol_rty30                  | ic_rty30                          |
      | ol_rtyGoodsBonus          | ic_rtyGoodsBonus                  |
      | ol_rtyCartonBonus         | ic_rtyCartonBonus                 |

    # a user re-packs line 20 into a carton and completes the order again
    When the order identified by orderRty is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID |
      | ol_rty20                  | pipRtyCrateNowCarton        |
    And the next completion of the order identified by orderRty runs into a DB deadlock once
    And the order identified by orderRty is completed
    And the completion of the order identified by orderRty did run into the DB deadlock
    And validate the created orders
      | C_Order_ID.Identifier | processed | DocStatus | GrandTotal |
      | orderRty              | true      | CO        | 1908.88    |

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_rtyGoodsBonus2         | orderRty              | discountGoods           | 1          | true                        | 3                               | -49.92 | rtyTerm                           |
      | ol_rtyCartonBonus2        | orderRty              | discountCarton          | 1          | true                        | 0.6                             | -9.98  | rtyTerm                           |

    # the same order instance, after its retried completions, is reactivated and completed again without a deadlock
    When the order identified by orderRty is reactivated
    And the order identified by orderRty is completed
    And validate the created orders
      | C_Order_ID.Identifier | processed | DocStatus | GrandTotal |
      | orderRty              | true      | CO        | 1908.88    |

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_rtyGoodsBonus3         | orderRty              | discountGoods           | 1          | true                        | 3                               | -49.92 | rtyTerm                           |
      | ol_rtyCartonBonus3        | orderRty              | discountCarton          | 1          | true                        | 0.6                             | -9.98  | rtyTerm                           |
