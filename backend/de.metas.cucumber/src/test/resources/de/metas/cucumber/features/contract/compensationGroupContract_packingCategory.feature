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
      | Identifier              | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | cartonBP      | Y              | contractPS                    |
      | wholeBP | Y              | contractPS                    |
      | twoPmBP      | Y              | contractPS                    |
      | noPiBP       | Y              | contractPS                    |

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
      | Identifier  |
      | piCarton10  |
      | piCrate     |
      | piCarton12  |
    And metasfresh contains M_HU_PI_Version:
      | Identifier   | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCarton10V  | piCarton10            | TU          | Y         |
      | piCrateV     | piCrate               | TU          | Y         |
      | piCarton12V  | piCarton12            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier      | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCarton10Item  | piCarton10V                   | 0   | PM       | pmCarton10                         |
      | piCrateItem     | piCrateV                      | 0   | PM       | pmCrate                            |
      | piCarton12Item  | piCarton12V                   | 0   | PM       | pmCarton12                         |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCarton10  | piCarton10Item             | goodsA                  | 10  |
      | pipCrate     | piCrateItem                | goodsB                  | 10  |
      | pipCarton12  | piCarton12Item             | goodsC                  | 12  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name                      | OPT.IsAdditive |
      | cartonSchema | Bonus Ware 3% + Karton 0,6% | true         |
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
      | cartonTerm | cartonConditions                    | cartonBP          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by cartonTerm is completed

    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderCarton | true    | cartonBP       | 2026-07-01  |
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
      | Identifier  | Name              | OPT.IsAdditive |
      | wholeSchema | Bonus Ware 0,8%   | true           |
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
      | wholeTerm  | wholeConditions                     | wholeBP     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by wholeTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderWhole | true    | wholeBP  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_whole10   | orderWhole            | goodsA                  | 600        | pipCarton10                            |
      | ol_wholeCrate| orderWhole            | goodsB                  | 400        | pipCrate                               |
      | ol_whole12   | orderWhole            | goodsC                  | 360        | pipCarton12                            |

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
      | Identifier  |
      | piCartonMi  |
      | piFoilOnly  |
    And metasfresh contains M_HU_PI_Version:
      | Identifier   | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piCartonMiV  | piCartonMi            | TU          | Y         |
      | piFoilOnlyV  | piFoilOnly            | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier         | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piCartonMiGoods    | piCartonMiV                   | 0   | MI       | null                               |
      | piCartonMiPacking  | piCartonMiV                   | 0   | PM       | pmCarton10                         |
      | piFoilOnlyGoods    | piFoilOnlyV                   | 0   | MI       | null                               |
      | piFoilOnlyPacking  | piFoilOnlyV                   | 0   | PM       | pmFoil                             |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCartonMi  | piCartonMiGoods            | goodsD                  | 10  |
      | pipFoilOnly  | piFoilOnlyGoods            | goodsE                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name       | OPT.IsAdditive |
      | twoPmSchema  | Karton 10% | true           |
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
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_cartonMi   | orderTwoPm            | goodsD                  | 100        | pipCartonMi                            |
      | ol_foilOnly   | orderTwoPm            | goodsE                  | 50         | pipFoilOnly                            |

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
      | Identifier  | Name       | OPT.IsAdditive |
      | noPiSchema  | Karton 10% | true           |
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
      | noPiTerm   | noPiConditions                      | noPiBP           | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by noPiTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderNoPi  | true    | noPiBP        | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_withCart | orderNoPi             | goodsD                  | 100        | pipCartonX                             |
      | ol_noPi     | orderNoPi             | goodsF                  | 50         |                                        |

    And the order identified by orderNoPi is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_noPiBonus              | orderNoPi             | discountCarton          | 1          | true                        | 10                              | -10.00 | noPiTerm                          |

    And the order identified by orderNoPi has 4 order lines
