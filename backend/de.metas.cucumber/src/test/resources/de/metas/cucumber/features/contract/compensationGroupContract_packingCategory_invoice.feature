@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract compensation group — the carton-only discount line on shipped and invoiced orders
## F2070: Compensation Group Contract
##
## The discount follows the carton-packed goods that are invoiced. The packing is always the one of the
## order line, whatever packing the shipment ends up with.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier     | Name           | Value                  | OPT.M_Product_Category_Parent_ID.Identifier |
      | goods_cat      | Ware           | WareInvS32353          |                                             |
      | packaging_cat  | Verpackung     | VerpackungInvS32353    |                                             |
      | carton_cat     | Kartonagen     | KartonagenInvS32353    | packaging_cat                               |
      | carton_sub_cat | Kartonagen Sub | KartonagenSubInvS32353 | carton_cat                                  |
      | crate_cat      | Pfandsteigen   | PfandsteigenInvS32353  | packaging_cat                               |

    # not stocked: shipments need no inventory
    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsA          | goods_cat                            | false         |
      | goodsB          | goods_cat                            | false         |
      | goodsC          | goods_cat                            | false         |
      | discountGoods   | goods_cat                            | false         |
      | discountCarton  | goods_cat                            | false         |
      | cartonProduct10 | carton_cat                           | false         |
      | cartonProduct12 | carton_sub_cat                       | false         |
      | crateProduct    | crate_cat                            | false         |

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
      | pp_discountGood | contractPLV                       | discountGoods           | 1        | PCE               | Normal                        |
      | pp_discountCart | contractPLV                       | discountCarton          | 1        | PCE               | Normal                        |
      | pp_crate        | contractPLV                       | crateProduct            | 2.50     | PCE               | Normal                        |
      | pp_carton10     | contractPLV                       | cartonProduct10         | 0        | PCE               | Normal                        |
      | pp_carton12     | contractPLV                       | cartonProduct12         | 0        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |

    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID.Identifier | IsInvoiceable |
      | pmCarton10 | cartonProduct10         | N             |
      | pmCarton12 | cartonProduct12         | N             |
      | pmCrate    | crateProduct            | Y             |

    # invoice rule "after delivery": only what was shipped becomes invoiceable
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | fullBP     | Y              | contractPS                    | D               |
      | wholeBP    | Y              | contractPS                    | D               |
      | partBP     | Y              | contractPS                    | D               |
      | overBP     | Y              | contractPS                    | D               |

  # ##############################################################################################
  # Full delivery of the reference order, one invoice
  # goods 3 % of 1.664,00 = 49,92 | carton 0,6 % of 1.224,00 = 7,34
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC65
  Scenario: The invoice of a fully shipped order carries the bonus on the carton-packed goods
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
      | Identifier | Name                          | OPT.IsAdditive |
      | fullSchema | Invoice Ware 3% + Karton 0,6% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | fullLineGoods  | fullSchema                               | discountGoods           | 3                         | goods_cat                            |                                                      |
      | fullLineCarton | fullSchema                               | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name                                   | C_CompensationGroup_Schema_ID.Identifier |
      | fullSettings | Invoice Ware 3% + Karton 0,6% settings | fullSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | fullSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name                                     | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | fullConditions | Invoice Ware 3% + Karton 0,6% conditions | CompensationGroup | zeroDurTrans                            | fullSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | fullTerm   | fullConditions                      | fullBP                      | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by fullTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderFull  | true    | fullBP                   | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_carton10 | orderFull             | goodsA                  | 600        | pipCarton10                            |
      | ol_crate    | orderFull             | goodsB                  | 400        | pipCrate                               |
      | ol_carton12 | orderFull             | goodsC                  | 360        | pipCarton12                            |

    And the order identified by orderFull is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  |
      | ol_goodsBonus             | orderFull             | discountGoods           | 1          | true                        | -49.92 |
      | ol_cartonBonus            | orderFull             | discountCarton          | 1          | true                        | -7.34  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_carton10    | ol_carton10               | N             |
      | ss_crate       | ol_crate                  | N             |
      | ss_carton12    | ol_carton12               | N             |
      | ss_goodsBonus  | ol_goodsBonus             | N             |
      | ss_cartonBonus | ol_cartonBonus            | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_carton10           |
      | ss_crate              |
      | ss_carton12           |
      | ss_goodsBonus         |
      | ss_cartonBonus        |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_carton10                      | shipmentFull          |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_carton10    | ic_carton10            |
      | ol_crate       | ic_crate               |
      | ol_carton12    | ic_carton12            |
      | ol_goodsBonus  | ic_goodsBonus          |
      | ol_cartonBonus | ic_cartonBonus         |
    # a shipment of packed goods is dated with the real clock, past the frozen test clock: invoice regardless of the schedule date
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule |
      | ic_carton10                       | Y                         |
      | ic_crate                          | Y                         |
      | ic_carton12                       | Y                         |
      | ic_goodsBonus                     | Y                         |
      | ic_cartonBonus                    | Y                         |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceFull             | ic_carton10                       |

    And validate invoice lines for invoiceFull:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invFull_carton10            | goodsA                  | 600         | 720        |
      | invFull_crate               | goodsB                  | 400         | 440        |
      | invFull_carton12            | goodsC                  | 360         | 504        |
      | invFull_goodsBonus          | discountGoods           | 1           | -49.92     |
      | invFull_cartonBonus         | discountCarton          | 1           | -7.34      |

  # ##############################################################################################
  # Whole-goods variant: 0,8 % of 1.664,00 = 13,31
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC68
  Scenario: Without a packing-material category the invoice carries the bonus on all goods
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
      | wholeSchema | Invoice Ware 0,8% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | wholeLineGoods | wholeSchema                              | discountGoods           | 0.8                       | goods_cat                            |                                                      |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name                       | C_CompensationGroup_Schema_ID.Identifier |
      | wholeSettings | Invoice Ware 0,8% settings | wholeSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | wholeSettings                                      | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name                         | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | wholeConditions | Invoice Ware 0,8% conditions | CompensationGroup | zeroDurTrans                            | wholeSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | wholeTerm  | wholeConditions                     | wholeBP                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by wholeTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderWhole | true    | wholeBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_carton10 | orderWhole            | goodsA                  | 600        | pipCarton10                            |
      | ol_crate    | orderWhole            | goodsB                  | 400        | pipCrate                               |
      | ol_carton12 | orderWhole            | goodsC                  | 360        | pipCarton12                            |

    And the order identified by orderWhole is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  |
      | ol_goodsBonus             | orderWhole            | discountGoods           | 1          | true                        | -13.31 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_carton10   | ol_carton10               | N             |
      | ss_crate      | ol_crate                  | N             |
      | ss_carton12   | ol_carton12               | N             |
      | ss_goodsBonus | ol_goodsBonus             | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_carton10           |
      | ss_crate              |
      | ss_carton12           |
      | ss_goodsBonus         |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_carton10                      | shipmentWhole         |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_carton10    | ic_carton10            |
      | ol_crate       | ic_crate               |
      | ol_carton12    | ic_carton12            |
      | ol_goodsBonus  | ic_goodsBonus          |
    # a shipment of packed goods is dated with the real clock, past the frozen test clock: invoice regardless of the schedule date
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule |
      | ic_carton10                       | Y                         |
      | ic_crate                          | Y                         |
      | ic_carton12                       | Y                         |
      | ic_goodsBonus                     | Y                         |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceWhole            | ic_carton10                       |

    And validate invoice lines for invoiceWhole:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invWhole_carton10           | goodsA                  | 600         | 720        |
      | invWhole_crate              | goodsB                  | 400         | 440        |
      | invWhole_carton12           | goodsC                  | 360         | 504        |
      | invWhole_goodsBonus         | discountGoods           | 1           | -13.31     |

  # ##############################################################################################
  # First delivery: carton 10 kg line and crate line | second delivery: carton 12 kg line
  # goods 3 % of 1.160,00 = 34,80 then 15,12 | carton 0,6 % of 720,00 = 4,32 then 3,02
  # together 49,92 and 7,34, like the full invoice
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC66
  Scenario: Partial invoices carry the bonus on the carton-packed goods invoiced so far, the rest follows with the later invoice
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
      | Identifier | Name                                  | OPT.IsAdditive |
      | partSchema | Invoice partial Ware 3% + Karton 0,6% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | partLineGoods  | partSchema                               | discountGoods           | 3                         | goods_cat                            |                                                      |
      | partLineCarton | partSchema                               | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name                                           | C_CompensationGroup_Schema_ID.Identifier |
      | partSettings | Invoice partial Ware 3% + Karton 0,6% settings | partSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | partSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name                                             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | partConditions | Invoice partial Ware 3% + Karton 0,6% conditions | CompensationGroup | zeroDurTrans                            | partSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | partTerm   | partConditions                      | partBP                      | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by partTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderPart  | true    | partBP                   | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_carton10 | orderPart             | goodsA                  | 600        | pipCarton10                            |
      | ol_crate    | orderPart             | goodsB                  | 400        | pipCrate                               |
      | ol_carton12 | orderPart             | goodsC                  | 360        | pipCarton12                            |

    And the order identified by orderPart is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  |
      | ol_goodsBonus             | orderPart             | discountGoods           | 1          | true                        | -49.92 |
      | ol_cartonBonus            | orderPart             | discountCarton          | 1          | true                        | -7.34  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_carton10    | ol_carton10               | N             |
      | ss_crate       | ol_crate                  | N             |
      | ss_carton12    | ol_carton12               | N             |
      | ss_goodsBonus  | ol_goodsBonus             | N             |
      | ss_cartonBonus | ol_cartonBonus            | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_carton10           |
      | ss_crate              |
      | ss_goodsBonus         |
      | ss_cartonBonus        |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_carton10                      | shipmentPart1         |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_carton10    | ic_carton10            |
      | ol_crate       | ic_crate               |
      | ol_carton12    | ic_carton12            |
      | ol_goodsBonus  | ic_goodsBonus          |
      | ol_cartonBonus | ic_cartonBonus         |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_carton10            |
      | ic_crate               |
      | ic_carton12            |
      | ic_goodsBonus          |
      | ic_cartonBonus         |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_carton10                       | 720             |
      | ic_crate                          | 440             |
      | ic_carton12                       | 0               |
      | ic_goodsBonus                     | -34.8           |
      | ic_cartonBonus                    | -4.32           |
    # a shipment of packed goods is dated with the real clock, past the frozen test clock: invoice regardless of the schedule date
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule | OPT.QtyInvoiced |
      | ic_carton10                       | Y                         | 600             |
      | ic_crate                          | Y                         | 400             |
      | ic_carton12                       | Y                         | 0               |
      | ic_goodsBonus                     | Y                         | 1               |
      | ic_cartonBonus                    | Y                         | 1               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoicePart1            | ic_carton10                       |

    And validate invoice lines for invoicePart1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invPart1_carton10           | goodsA                  | 600         | 720        |
      | invPart1_crate              | goodsB                  | 400         | 440        |
      | invPart1_goodsBonus         | discountGoods           | 1           | -34.8      |
      | invPart1_cartonBonus        | discountCarton          | 1           | -4.32      |

    # second delivery: the remaining carton 12 kg line
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_carton12           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_carton12                      | shipmentPart2         |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_carton12            |
      | ic_goodsBonus          |
      | ic_cartonBonus         |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_carton12            |
      | ic_goodsBonus          |
      | ic_cartonBonus         |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_carton12                       | 504             |
      | ic_goodsBonus                     | -15.12          |
      | ic_cartonBonus                    | -3.02           |
    # a shipment of packed goods is dated with the real clock, past the frozen test clock: invoice regardless of the schedule date
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule | OPT.QtyInvoiced |
      | ic_carton10                       | Y                         | 600             |
      | ic_crate                          | Y                         | 400             |
      | ic_carton12                       | Y                         | 360             |
      | ic_goodsBonus                     | Y                         | 2               |
      | ic_cartonBonus                    | Y                         | 2               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoicePart2            | ic_carton12                       |

    And validate invoice lines for invoicePart2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invPart2_carton12           | goodsC                  | 360         | 504        |
      | invPart2_goodsBonus         | discountGoods           | 1           | -15.12     |
      | invPart2_cartonBonus        | discountCarton          | 1           | -3.02      |

  # ##############################################################################################
  # The crate line (not carton-packed on the order) gets a carton packing instruction on its shipment schedule:
  # the bonus on the first invoice stays 0,6 % of the carton 10 kg line only = 4,32
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC67
  Scenario: The carton bonus follows the packing of the order line, not the packing the shipment schedule is changed to
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
      | Identifier      | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipCarton10     | piCarton10Item             | goodsA                  | 10  |
      | pipCrate        | piCrateItem                | goodsB                  | 10  |
      | pipCarton12     | piCarton12Item             | goodsC                  | 12  |
      | pipCarton10ForB | piCarton10Item             | goodsB                  | 10  |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                                   | OPT.IsAdditive |
      | overSchema | Invoice override Ware 3% + Karton 0,6% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier | OPT.M_Product_Category_PackingMaterial_ID.Identifier |
      | overLineGoods  | overSchema                               | discountGoods           | 3                         | goods_cat                            |                                                      |
      | overLineCarton | overSchema                               | discountCarton          | 0.6                       | goods_cat                            | carton_cat                                           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name                                            | C_CompensationGroup_Schema_ID.Identifier |
      | overSettings | Invoice override Ware 3% + Karton 0,6% settings | overSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | overSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name                                              | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | overConditions | Invoice override Ware 3% + Karton 0,6% conditions | CompensationGroup | zeroDurTrans                            | overSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | overTerm   | overConditions                      | overBP                      | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by overTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderOver  | true    | overBP                   | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_carton10 | orderOver             | goodsA                  | 600        | pipCarton10                            |
      | ol_crate    | orderOver             | goodsB                  | 400        | pipCrate                               |
      | ol_carton12 | orderOver             | goodsC                  | 360        | pipCarton12                            |

    And the order identified by orderOver is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  |
      | ol_goodsBonus             | orderOver             | discountGoods           | 1          | true                        | -49.92 |
      | ol_cartonBonus            | orderOver             | discountCarton          | 1          | true                        | -7.34  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_carton10    | ol_carton10               | N             |
      | ss_crate       | ol_crate                  | N             |
      | ss_carton12    | ol_carton12               | N             |
      | ss_goodsBonus  | ol_goodsBonus             | N             |
      | ss_cartonBonus | ol_cartonBonus            | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.M_HU_PI_Item_Product_Override_ID.Identifier |
      | ss_crate                         | pipCarton10ForB                                 |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_carton10           |
      | ss_crate              |
      | ss_goodsBonus         |
      | ss_cartonBonus        |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_carton10                      | shipmentOver          |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_carton10    | ic_carton10            |
      | ol_crate       | ic_crate               |
      | ol_carton12    | ic_carton12            |
      | ol_goodsBonus  | ic_goodsBonus          |
      | ol_cartonBonus | ic_cartonBonus         |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_carton10            |
      | ic_crate               |
      | ic_goodsBonus          |
      | ic_cartonBonus         |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_carton10                       | 720             |
      | ic_crate                          | 440             |
      | ic_goodsBonus                     | -34.8           |
      | ic_cartonBonus                    | -4.32           |
    # a shipment of packed goods is dated with the real clock, past the frozen test clock: invoice regardless of the schedule date
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule | OPT.QtyInvoiced |
      | ic_carton10                       | Y                         | 600             |
      | ic_crate                          | Y                         | 400             |
      | ic_carton12                       | Y                         | 0               |
      | ic_goodsBonus                     | Y                         | 1               |
      | ic_cartonBonus                    | Y                         | 1               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceOver             | ic_carton10                       |

    And validate invoice lines for invoiceOver:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invOver_carton10            | goodsA                  | 600         | 720        |
      | invOver_crate               | goodsB                  | 400         | 440        |
      | invOver_goodsBonus          | discountGoods           | 1           | -34.8      |
      | invOver_cartonBonus         | discountCarton          | 1           | -4.32      |
