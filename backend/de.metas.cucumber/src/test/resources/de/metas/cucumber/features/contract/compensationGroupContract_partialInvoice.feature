@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor8
Feature: Contract-triggered compensation group on a partially delivered and invoiced sales order
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    # unique per run (auto-generated Value/Name): no category from an earlier run is reused
    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |

    # not stocked: shipments need no inventory
    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goods1          | goodsCategory                        | false         |
      | goods2          | goodsCategory                        | false         |
      | goods3          | goodsCategory                        | false         |
      | discountProduct |                                      | false         |

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
      | pp_goods1   | contractPLV                       | goods1                  | 1000     | PCE               | Normal                        |
      | pp_goods2   | contractPLV                       | goods2                  | 500      | PCE               | Normal                        |
      | pp_goods3   | contractPLV                       | goods3                  | 200      | PCE               | Normal                        |
      | pp_discount | contractPLV                       | discountProduct         | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |

    # invoice rule "after delivery": only what was shipped becomes invoiceable
    And metasfresh contains C_BPartners:
      | Identifier   | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | headOfficeBP | Y              | contractPS                    | D               |
      | storeBP      | Y              | contractPS                    | D               |

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
  # The discount follows the goods: each invoice carries the discount on the goods it invoices,
  # and the invoices together carry the order's full discount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC40
  Scenario: A partially delivered sales order is invoiced with the discount on the delivered goods, the remaining discount follows with the later invoice
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |
      | ol_goods3  | orderPartial          | goods3                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 + 200 = 51
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -51   | mainTerm                          |
    And the order identified by orderPartial has 4 order lines

    # ##########################################################################################
    # First delivery: goods1 only
    # ##########################################################################################
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_goods3   | ol_goods3                 | N             |
      | ss_discount | ol_discount               | N             |

    # the order line of the discount has its own shipment schedule; it goes out with the first
    # shipment of the order, so its whole quantity (1) is delivered from then on
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_discount    | ic_discount            |

    # the discount candidate is computed on the delivered goods only: 3% of 1000
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_goods1                         | 1000            |
      | ic_goods2                         | 0               |
      | ic_goods3                         | 0               |
      | ic_discount                       | -30             |

    # a compensation group is invoiced as a whole (InvoiceCandEnqueuer_IncompleteGroupsFound otherwise):
    # all of its candidates are selected; the not yet delivered ones have nothing to invoice
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_goods3                         | 0               |
      | ic_discount                       | 1               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |

    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_discount           | discountProduct         | 1           | -30        |

    # the discount candidate stays open for the goods not yet invoiced: one more unit to come
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 1           | 0            | -30            | false     | false   |

    # ##########################################################################################
    # Second delivery: the rest
    # ##########################################################################################
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
      | ss_goods3             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |

    # the remaining discount: 3% of 500 + 200
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_goods2                         | 500             |
      | ic_goods3                         | 200             |
      | ic_discount                       | -21             |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_goods3                         |
      | ic_discount                       |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |

    # together with the first invoice's -30 this is the order's full discount of -51
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_goods3             | goods3                  | 1           | 200        |
      | invoice2_discount           | discountProduct         | 1           | -21        |

    # all goods are invoiced: nothing left, and the invoiced amount is that of both discount lines
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 2           | 0            | 0               | -51            | true      | false   |

  # ##############################################################################################
  # Reversing the first partial invoice gives its discount back to the candidate
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC41
  Scenario: Reversing a partial invoice reopens the discount on its goods
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -45   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_discount | ol_discount               | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_discount    | ic_discount            |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_discount                       | 1               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |

    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_discount           | discountProduct         | 1           | -30        |

    When the invoice identified by invoice1 is reversed

    # goods1 and its discount (3% of 1000) are to be invoiced again; nothing counts as invoiced any more
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 0           | 1            | -30             | 0              | false     | false   |

  # ##############################################################################################
  # Three partial invoices: each carries the discount on its own goods, and the order counts as
  # completely invoiced only after the last one
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC43
  Scenario: Each of three partial invoices carries the discount on its goods and the order is completely invoiced only with the last one
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |
      | ol_goods3  | orderPartial          | goods3                  | 1          |

    And the order identified by orderPartial is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -51   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_goods3   | ol_goods3                 | N             |
      | ss_discount | ol_discount               | N             |

    # first invoice: goods1, 3% of 1000
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_discount    | ic_discount            |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_goods3                         | 0               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_discount           | discountProduct         | 1           | -30        |
    # the order's invoiced quantities are summed up when its candidates' recompute updates the order lines
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | PI            |
    # the discount order line ordered one unit and never counts more than that
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

    # second invoice: goods2, 3% of 500
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 1               |
      | ic_goods3                         | 0               |
      | ic_discount                       | 2               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_discount           | discountProduct         | 1           | -15        |
    # the order's invoiced quantities are summed up when its candidates' recompute updates the order lines
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | PI            |
    # the discount order line ordered one unit and never counts more than that
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

    # third invoice: goods3, 3% of 200
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods3             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods3                        | shipment3             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods3              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_goods3                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice3                | ic_goods3                         |
    And validate invoice lines for invoice3:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice3_goods3             | goods3                  | 1           | 200        |
      | invoice3_discount           | discountProduct         | 1           | -6         |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 3           | 0            | -51            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | CI            |
    # the discount order line ordered one unit and never counts more than that
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

  # ##############################################################################################
  # Reversing an earlier partial invoice keeps the later invoice's discount as invoiced and gives
  # the earlier one back to be invoiced again
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC44
  Scenario: Reversing the first of two partial invoices keeps the second invoice's discount and reopens the first one's
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -45   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_discount | ol_discount               | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_discount    | ic_discount            |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_discount           | discountProduct         | 1           | -15        |

    When the invoice identified by invoice1 is reversed

    # the second invoice's discount stays invoiced; the first one's is to be invoiced again with its goods
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | PI            |
    # the discount order line ordered one unit and never counts more than that
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier            | C_Invoice_Candidate_ID.Identifier |
      | invoice1,invoice1Reversal,invoice3 | ic_goods1                         |
    And validate invoice lines for invoice3:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice3_goods1             | goods1                  | 1           | 1000       |
      | invoice3_discount           | discountProduct         | 1           | -30        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2           | 0            | -45            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | CI            |
    # the discount order line ordered one unit and never counts more than that
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

  # ##############################################################################################
  # A re-invoiceable credit memo on an earlier partial invoice gives back that invoice's discount;
  # reversing the credited invoice afterwards takes back nothing more
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC45
  Scenario: A re-invoiceable credit memo on the first of two partial invoices reopens its discount, and reversing that invoice afterwards keeps the second invoice's discount
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -45   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_discount | ol_discount               | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_discount    | ic_discount            |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_discount           | discountProduct         | 1           | -15        |

    When create credit memo for C_Invoice
      | CreditMemo  | C_Invoice_ID | CreditMemo.IsCreditedInvoiceReinvoicable |
      | creditMemo1 | invoice1     | true                                     |
    And the invoice identified by creditMemo1 is completed

    # the credit memo gives the first invoice's goods and discount back to be invoiced again
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |

    # the credit memo already gave the first invoice's quantities back, so reversing that invoice takes back nothing more
    When the invoice identified by invoice1 is reversed

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier                        | C_Invoice_Candidate_ID.Identifier |
      | invoice1,creditMemo1,invoice1Reversal,invoice3 | ic_goods1                         |
    And validate invoice lines for invoice3:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice3_goods1             | goods1                  | 1           | 1000       |
      | invoice3_discount           | discountProduct         | 1           | -30        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2           | 0            | -45            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | CI            |
    # the discount order line ordered one unit and never counts more than that
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

  # ##############################################################################################
  # Reversing a re-invoiceable credit memo counts the credited invoice's discount again, so reversing
  # that invoice afterwards takes it back
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC46
  Scenario: Reversing the first of two partial invoices after its re-invoiceable credit memo was reversed reopens its discount and keeps the second invoice's discount
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -45   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_discount | ol_discount               | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_discount    | ic_discount            |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_discount           | discountProduct         | 1           | -15        |

    When create credit memo for C_Invoice
      | CreditMemo  | C_Invoice_ID | CreditMemo.IsCreditedInvoiceReinvoicable |
      | creditMemo1 | invoice1     | true                                     |
    And the invoice identified by creditMemo1 is completed
    And the invoice identified by creditMemo1 is reversed

    # the credit memo's reversal counts the first invoice's goods and discount as invoiced again
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 1           | 0            | 1000           | true      | false   |
      | ic_discount                       | 2           | 0            | -45            | true      | false   |

    When the invoice identified by invoice1 is reversed

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |

  # ##############################################################################################
  # Reversing a re-invoiceable credit memo after its credited invoice was reversed gives nothing back:
  # the credited invoice's discount is already open to be invoiced again
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC47
  Scenario: Reversing a re-invoiceable credit memo after its credited partial invoice was reversed keeps only the second invoice's discount as invoiced
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -45   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_discount | ol_discount               | N             |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_discount    | ic_discount            |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_discount           | discountProduct         | 1           | -15        |

    When create credit memo for C_Invoice
      | CreditMemo  | C_Invoice_ID | CreditMemo.IsCreditedInvoiceReinvoicable |
      | creditMemo1 | invoice1     | true                                     |
    And the invoice identified by creditMemo1 is completed

    # the credit memo gives the first invoice's goods and discount back to be invoiced again
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |

    # the credit memo already gave the first invoice's quantities back, so reversing that invoice takes back nothing more
    When the invoice identified by invoice1 is reversed

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |

    When the invoice identified by creditMemo1 is reversed

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_goods1                         | 0           | 1            | 1000            | 0              | false     | false   |
      | ic_discount                       | 1           | 1            | -30             | -15            | false     | false   |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier                                            | C_Invoice_Candidate_ID.Identifier |
      | invoice1,creditMemo1,invoice1Reversal,creditMemo1Reversal,invoice3 | ic_goods1                         |
    And validate invoice lines for invoice3:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice3_goods1             | goods1                  | 1           | 1000       |
      | invoice3_discount           | discountProduct         | 1           | -30        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2           | 0            | -45            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | CI            |

  # ##############################################################################################
  # A contract discount left off a later partial invoice follows with the next invoice at its
  # remaining amount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC52
  Scenario: A contract discount held back from the second partial invoice is invoiced later at its remaining amount
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartial | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderPartial          | goods1                  | 1          |
      | ol_goods2  | orderPartial          | goods2                  | 1          |

    And the order identified by orderPartial is completed

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_discount               | orderPartial          | discountProduct         | 1          | true                        | -45   | mainTerm                          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_discount | ol_discount               | N             |

    # first invoice: goods1 with its discount
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_discount    | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 0               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_discount           | discountProduct         | 1           | -30        |

    # second invoice: goods2, with the discount held back by the user
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyToInvoice_Override |
      | ic_discount            | 0                     |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods2                        | shipment2             |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods2              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 1               |
      | ic_discount                       | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |

    # the rest of the discount is still open
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 1           | -30            | false     | false   |
    # the goods are completely invoiced; the order reads so, while the held-back discount stays open on its candidate
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | CI            |

    # the user releases the discount; its remaining amount follows with the next invoice
    When update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyToInvoice_Override |
      | ic_discount            | 1                     |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | NetAmtToInvoice | Processed | IsError |
      | ic_discount                       | 1            | -15             | false     | false   |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1,invoice3       | ic_discount                       |
    # a discount alone makes a credit memo: its line credits one unit of -15, i.e. 15
    And validate invoice lines for invoice3:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice3_discount           | discountProduct         | -1          | 15         |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 2           | 0            | -45            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderPartial | CI            |

  # ##############################################################################################
  # One Bonus line per position, on the order and on the invoice. Shape of a customer's real sales order and its invoice:
  # goods 110,00 on position 1, 220,00 on position 2, 55,00 without position, 3 % Bonus
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC1_referenceCase
  Scenario: Goods on two positions and without position: one Bonus line per position on the order and on the invoice
    Given metasfresh contains C_Projects:
      | Identifier |
      | position1  |
      | position2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderRef   | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | ol_ref1    | orderRef              | goods1                  | 1          | 110   | position1    |
      | ol_ref2    | orderRef              | goods2                  | 1          | 220   | position2    |
      | ol_ref3    | orderRef              | goods3                  | 1          | 55    |              |

    When the order identified by orderRef is completed

    # 3 % of 110,00 / 220,00 / 55,00, each Bonus line on its own position
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier | C_Project_ID |
      | ol_refBonus1              | orderRef              | discountProduct         | 1          | true                        | -3.30 | mainTerm                          | position1    |
      | ol_refBonus2              | orderRef              | discountProduct         | 1          | true                        | -6.60 | mainTerm                          | position2    |
      | ol_refBonus3              | orderRef              | discountProduct         | 1          | true                        | -1.65 | mainTerm                          | null         |
    And the order identified by orderRef has 6 order lines

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_ref1    | ol_ref1                   | N             |
      | ss_ref2    | ol_ref2                   | N             |
      | ss_ref3    | ol_ref3                   | N             |
      | ss_refB1   | ol_refBonus1              | N             |
      | ss_refB2   | ol_refBonus2              | N             |
      | ss_refB3   | ol_refBonus3              | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_ref1               |
      | ss_ref2               |
      | ss_ref3               |
      | ss_refB1              |
      | ss_refB2              |
      | ss_refB3              |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_ref1 | shipmentRef |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_ref1        | ic_ref1                |
      | ol_ref2        | ic_ref2                |
      | ol_ref3        | ic_ref3                |
      | ol_refBonus1   | ic_refB1               |
      | ol_refBonus2   | ic_refB2               |
      | ol_refBonus3   | ic_refB3               |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_ref1                           |
      | ic_ref2                           |
      | ic_ref3                           |
      | ic_refB1                          |
      | ic_refB2                          |
      | ic_refB3                          |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceRef              | ic_ref1                           |

    # one Bonus invoice line per position with the amount of its order line; total Bonus -11,55
    Then validate invoice lines for invoiceRef:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods1          | 1           | position1    | 110        |
      | goods2          | 1           | position2    | 220        |
      | goods3          | 1           | null         | 55         |
      | discountProduct | 1           | position1    | -3.30      |
      | discountProduct | 1           | position2    | -6.60      |
      | discountProduct | 1           | null         | -1.65      |

  # ##############################################################################################
  # Two positions with the same goods value: two separate Bonus lines of equal product and amount, on the order and on the invoice
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC4_equalGoodsValue
  Scenario: Two positions with equal goods value get two separate Bonus lines on the invoice
    Given metasfresh contains C_Projects:
      | Identifier |
      | position1  |
      | position2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderEq    | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | ol_eq1     | orderEq               | goods1                  | 1          | 100   | position1    |
      | ol_eq2     | orderEq               | goods2                  | 1          | 100   | position2    |

    When the order identified by orderEq is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier | C_Project_ID |
      | ol_eqBonus1               | orderEq               | discountProduct         | 1          | true                        | -3.00 | mainTerm                          | position1    |
      | ol_eqBonus2               | orderEq               | discountProduct         | 1          | true                        | -3.00 | mainTerm                          | position2    |
    And the order identified by orderEq has 4 order lines

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_eq1     | ol_eq1                    | N             |
      | ss_eq2     | ol_eq2                    | N             |
      | ss_eqB1    | ol_eqBonus1               | N             |
      | ss_eqB2    | ol_eqBonus2               | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_eq1                |
      | ss_eq2                |
      | ss_eqB1               |
      | ss_eqB2               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_eq1 | shipmentEq |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_eq1         | ic_eq1                 |
      | ol_eq2         | ic_eq2                 |
      | ol_eqBonus1    | ic_eqB1                |
      | ol_eqBonus2    | ic_eqB2                |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_eq1                            |
      | ic_eq2                            |
      | ic_eqB1                           |
      | ic_eqB2                           |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceEq               | ic_eq1                            |

    # the two Bonus lines are equal in product and amount and stay two lines, each on its own position
    Then validate invoice lines for invoiceEq:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods1          | 1           | position1    | 100        |
      | goods2          | 1           | position2    | 100        |
      | discountProduct | 1           | position1    | -3.00      |
      | discountProduct | 1           | position2    | -3.00      |

  # ##############################################################################################
  # Partial shipment on two positions (invoice rule D): each position's Bonus follows the goods shipped and invoiced for that position.
  # Position 1: goods 20 x 10,00 = 200,00; position 2: goods 1 x 185,00; 3 % Bonus: -6,00 and -5,55.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC5_partialShipmentTwoPositions
  Scenario: Partial shipment of position 1: its Bonus follows the shipped goods, position 2 stays open; the invoiced Bonus ends equal to the order's
    # a partially invoiced candidate must stay open for the goods still to ship (the local default is Y: close it)
    Given temporarily set sys config boolean value false for sys config "C_Invoice_Candidate_Close_PartiallyInvoiced"
    And metasfresh contains C_Projects:
      | Identifier |
      | position1  |
      | position2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPS    | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | ol_ps1     | orderPS               | goods1                  | 20         | 10    | position1    |
      | ol_ps2     | orderPS               | goods2                  | 1          | 185   | position2    |
    And the order identified by orderPS is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier | C_Project_ID |
      | ol_psBonus1               | orderPS               | discountProduct         | 1          | true                        | -6.00 | mainTerm                          | position1    |
      | ol_psBonus2               | orderPS               | discountProduct         | 1          | true                        | -5.55 | mainTerm                          | position2    |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_ps1     | ol_ps1                    | N             |
      | ss_ps2     | ol_ps2                    | N             |
      | ss_psB1    | ol_psBonus1               | N             |
      | ss_psB2    | ol_psBonus2               | N             |

    # first delivery: 12 of the 20 units of position 1 and the Bonus line of position 1
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | ss_ps1                | 12                                              |
      | ss_psB1               |                                                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_ps1 | shipmentPS1 |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_ps1         | ic_ps1                 |
      | ol_ps2         | ic_ps2                 |
      | ol_psBonus1    | ic_psB1                |
      | ol_psBonus2    | ic_psB2                |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_ps1                 |
      | ic_ps2                 |
      | ic_psB1                |
      | ic_psB2                |
    # the Bonus of position 1 is 3 % of the 120,00 delivered; position 2 has nothing delivered
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_ps1                            | 120             |
      | ic_ps2                            | 0               |
      | ic_psB1                           | -3.6            |
      | ic_psB2                           | 0               |

    # position 1 alone: only its group is selected, no incomplete-group error
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_ps1                            | 12              |
      | ic_psB1                           | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoicePS1              | ic_ps1                            |
    And validate invoice lines for invoicePS1:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods1          | 12          | position1    | 120        |
      | discountProduct | 1           | position1    | -3.60      |

    # second delivery: the remaining 8 units of position 1, all of position 2 with its Bonus line
    When update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | ss_ps1                            | 8                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | ss_ps1                            |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_ps1                |
      | ss_ps2                |
      | ss_psB2               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_ps2 | shipmentPS2 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_ps1                 |
      | ic_ps2                 |
      | ic_psB1                |
      | ic_psB2                |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_ps1                            | 80              |
      | ic_ps2                            | 185             |
      | ic_psB1                           | -2.4            |
      | ic_psB2                           | -5.55           |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_ps1                            |
      | ic_ps2                            |
      | ic_psB1                           |
      | ic_psB2                           |
    # the position 1 candidates carry the date of their first invoice, the position 2 candidates have none yet: their header aggregation keys
    # differ (DateInvoiced, DateAcct), so they get two invoices; position 2 with its Bonus is one of them, position 1's rest follows on the other
    # (invoicePS1 is mapped again to itself, the rest invoice of position 1 gets its own identifier)
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier        | C_Invoice_Candidate_ID.Identifier |
      | invoicePS1,invoicePS1Rest      | ic_ps1                            |
      | invoicePS2                     | ic_ps2                            |
    And validate invoice lines for invoicePS1Rest:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods1          | 8           | position1    | 80         |
      | discountProduct | 1           | position1    | -2.40      |
    And validate invoice lines for invoicePS2:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods2          | 1           | position2    | 185        |
      | discountProduct | 1           | position2    | -5.55      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | NetAmtInvoiced | Processed |
      | ic_ps1                            | 20          | 200            | true      |
      | ic_ps2                            | 1           | 185            | true      |

    # per position the invoiced Bonus equals the order line: -3.60 - 2.40 = -6.00 and -5.55
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtInvoiced | Processed |
      | ic_psB1                           | -6             | true      |
      | ic_psB2                           | -5.55          | true      |

  # ##############################################################################################
  # Partial shipment, single-group order (all goods on the order's header position): the Bonus amounts are those of today
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC17d_singleGroup
  Scenario: Partial shipment of an order with one position gives the same partial Bonus amounts as before
    # a partially invoiced candidate must stay open for the goods still to ship (the local default is Y: close it)
    Given temporarily set sys config boolean value false for sys config "C_Invoice_Candidate_Close_PartiallyInvoiced"
    And metasfresh contains C_Projects:
      | Identifier |
      | position1  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier | C_Project_ID |
      | orderSG    | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    | position1    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | ol_sg1     | orderSG               | goods1                  | 20         | 10    | position1    |
      | ol_sg2     | orderSG               | goods2                  | 1          | 185   | position1    |
    And the order identified by orderSG is completed

    # 3 % of 200 + 185 = 11.55
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_sgBonus                | orderSG               | discountProduct         | 1          | true                        | -11.55 | mainTerm                          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_sg1     | ol_sg1                    | N             |
      | ss_sg2     | ol_sg2                    | N             |
      | ss_sgB     | ol_sgBonus                | N             |
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | ss_sg1                | 12                                              |
      | ss_sgB                |                                                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_sg1 | shipmentSG1 |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_sg1         | ic_sg1                 |
      | ol_sg2         | ic_sg2                 |
      | ol_sgBonus     | ic_sgB                 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_sg1                 |
      | ic_sg2                 |
      | ic_sgB                 |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_sg1                            | 120             |
      | ic_sg2                            | 0               |
      | ic_sgB                            | -3.6            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_sg1                            | 12              |
      | ic_sg2                            | 0               |
      | ic_sgB                            | 1               |

    When update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | ss_sg1                            | 8                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | ss_sg1                            |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_sg1                |
      | ss_sg2                |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_sg2 | shipmentSG2 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_sg1                 |
      | ic_sg2                 |
      | ic_sgB                 |
    # the rest of the Bonus: 11.55 - 3.60
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_sg1                            | 80              |
      | ic_sg2                            | 185             |
      | ic_sgB                            | -7.95           |


  # ##############################################################################################
  # Partial shipment with C_Invoice_Candidate_Close_PartiallyInvoiced = Y (a customer instance value): the partially invoiced candidate is closed
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC17d_singleGroup_closePartiallyInvoicedY
  Scenario: Partial shipment of an order with one position, partially invoiced candidates closed (customer setting): Bonus amounts as before
    # a customer instance runs with this value (as does the default of a fresh database): a partially invoiced candidate is closed
    Given temporarily set sys config boolean value true for sys config "C_Invoice_Candidate_Close_PartiallyInvoiced"
    And metasfresh contains C_Projects:
      | Identifier |
      | position1  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier | C_Project_ID |
      | orderSG    | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    | position1    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | ol_sg1     | orderSG               | goods1                  | 20         | 10    | position1    |
      | ol_sg2     | orderSG               | goods2                  | 1          | 185   | position1    |
    And the order identified by orderSG is completed

    # 3 % of 200 + 185 = 11.55
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_sgBonus                | orderSG               | discountProduct         | 1          | true                        | -11.55 | mainTerm                          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_sg1     | ol_sg1                    | N             |
      | ss_sg2     | ol_sg2                    | N             |
      | ss_sgB     | ol_sgBonus                | N             |
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | ss_sg1                | 12                                              |
      | ss_sgB                |                                                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_sg1 | shipmentSG1 |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_sg1         | ic_sg1                 |
      | ol_sg2         | ic_sg2                 |
      | ol_sgBonus     | ic_sgB                 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_sg1                 |
      | ic_sg2                 |
      | ic_sgB                 |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_sg1                            | 120             |
      | ic_sg2                            | 0               |
      | ic_sgB                            | -3.6            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_sg1                            | 12              |
      | ic_sg2                            | 0               |
      | ic_sgB                            | 1               |

    When update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | ss_sg1                            | 8                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | ss_sg1                            |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_sg1                |
      | ss_sg2                |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_sg2 | shipmentSG2 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_sg1                 |
      | ic_sg2                 |
      | ic_sgB                 |
    # with this setting the partially invoiced goods candidate (12 of 20 units) is closed: its remaining 8 units are not invoiceable any more.
    # The Bonus candidate is not closed (its quantity 1 is fully invoiced) and follows the goods shipped since: 3 % of 185 = 5.55
    # (a result of the setting as it is today, same before and after the per-position Bonus)
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice | NetAmtInvoiced | Processed |
      | ic_sg1                            | 0               | 120            | true      |
      | ic_sg2                            | 185             | 0              | false     |
      | ic_sgB                            | -5.55           | -3.6           | false     |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC5_partialShipmentTwoPositions_closePartiallyInvoicedY
  Scenario: Partial shipment of position 1, partially invoiced candidates closed (customer setting): Bonus per position
    # a customer instance runs with this value (as does the default of a fresh database): a partially invoiced candidate is closed
    Given temporarily set sys config boolean value true for sys config "C_Invoice_Candidate_Close_PartiallyInvoiced"
    And metasfresh contains C_Projects:
      | Identifier |
      | position1  |
      | position2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPS    | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | ol_ps1     | orderPS               | goods1                  | 20         | 10    | position1    |
      | ol_ps2     | orderPS               | goods2                  | 1          | 185   | position2    |
    And the order identified by orderPS is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier | C_Project_ID |
      | ol_psBonus1               | orderPS               | discountProduct         | 1          | true                        | -6.00 | mainTerm                          | position1    |
      | ol_psBonus2               | orderPS               | discountProduct         | 1          | true                        | -5.55 | mainTerm                          | position2    |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_ps1     | ol_ps1                    | N             |
      | ss_ps2     | ol_ps2                    | N             |
      | ss_psB1    | ol_psBonus1               | N             |
      | ss_psB2    | ol_psBonus2               | N             |

    # first delivery: 12 of the 20 units of position 1 and the Bonus line of position 1
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | ss_ps1                | 12                                              |
      | ss_psB1               |                                                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_ps1 | shipmentPS1 |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_ps1         | ic_ps1                 |
      | ol_ps2         | ic_ps2                 |
      | ol_psBonus1    | ic_psB1                |
      | ol_psBonus2    | ic_psB2                |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_ps1                 |
      | ic_ps2                 |
      | ic_psB1                |
      | ic_psB2                |
    # the Bonus of position 1 is 3 % of the 120,00 delivered; position 2 has nothing delivered
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_ps1                            | 120             |
      | ic_ps2                            | 0               |
      | ic_psB1                           | -3.6            |
      | ic_psB2                           | 0               |

    # position 1 alone: only its group is selected, no incomplete-group error
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_ps1                            | 12              |
      | ic_psB1                           | 1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoicePS1              | ic_ps1                            |
    And validate invoice lines for invoicePS1:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods1          | 12          | position1    | 120        |
      | discountProduct | 1           | position1    | -3.60      |

    # second delivery: the remaining 8 units of position 1, all of position 2 with its Bonus line
    When update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | ss_ps1                            | 8                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | ss_ps1                            |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_ps1                |
      | ss_ps2                |
      | ss_psB2               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_ps2 | shipmentPS2 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_ps1                 |
      | ic_ps2                 |
      | ic_psB1                |
      | ic_psB2                |
    # with this setting position 1 (partially invoiced) and its Bonus are closed: the rest of its goods and of its Bonus are not invoiceable any more;
    # position 2 and its Bonus were not invoiced so far and follow in full
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_ps1                            | 0               |
      | ic_ps2                            | 185             |
      | ic_psB1                           | 0               |
      | ic_psB2                           | -5.55           |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_ps2                            |
      | ic_psB2                           |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoicePS2              | ic_ps2                            |
    And validate invoice lines for invoicePS2:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods2          | 1           | position2    | 185        |
      | discountProduct | 1           | position2    | -5.55      |

    # position 1's Bonus stays at what its first partial invoice carried; position 2's Bonus is invoiced in full
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtInvoiced | Processed |
      | ic_psB1                           | -3.6           | true      |
      | ic_psB2                           | -5.55          | true      |

  # ##############################################################################################
  # The reference case with its packing lines (the real order has packing lines on positions 1 and 2): packing material is not in the Bonus base,
  # the Bonus per position is the same as without packing lines
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S33096_TC1_referenceCase_packingLines
  Scenario: Goods with packing lines on two positions and without position: one Bonus line per position, packing lines stay out of the Bonus base
    Given metasfresh contains M_Product_Categories:
      | Identifier |
      | packCat    |
    And metasfresh contains M_Products:
      | Identifier  | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | packProduct | packCat                              | false         |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_pack    | contractPLV                       | packProduct             | 5        | PCE               | Normal                        |
    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID.Identifier | IsInvoiceable |
      | pmPack     | packProduct             | N             |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | piPack     |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | piPackV    | piPack                | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | M_HU_PackingMaterial_ID.Identifier |
      | piPackItem | piPackV                       | 0   | PM       | pmPack                             |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pipGoods1  | piPackItem                 | goods1                  | 1   |
      | pipGoods2  | piPackItem                 | goods2                  | 1   |
    And metasfresh contains C_Projects:
      | Identifier |
      | position1  |
      | position2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderRefP  | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_refP1   | orderRefP             | goods1                  | 1          | 110   | position1    | pipGoods1                              |
      | ol_refP2   | orderRefP             | goods2                  | 1          | 220   | position2    | pipGoods2                              |
      | ol_refP3   | orderRefP             | goods3                  | 1          | 55    |              |                                        |

    When the order identified by orderRefP is completed

    # packing lines (priced 5,00 each) exist on the order, but only the goods carry the Bonus: 3 % of 110,00 / 220,00 / 55,00
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier | C_Project_ID |
      | ol_refPBonus1             | orderRefP             | discountProduct         | 1          | true                        | -3.30 | mainTerm                          | position1    |
      | ol_refPBonus2             | orderRefP             | discountProduct         | 1          | true                        | -6.60 | mainTerm                          | position2    |
      | ol_refPBonus3             | orderRefP             | discountProduct         | 1          | true                        | -1.65 | mainTerm                          | null         |
    And the order identified by orderRefP has 8 order lines

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_refP1   | ol_refP1                  | N             |
      | ss_refP2   | ol_refP2                  | N             |
      | ss_refP3   | ol_refP3                  | N             |
      | ss_refPB1  | ol_refPBonus1             | N             |
      | ss_refPB2  | ol_refPBonus2             | N             |
      | ss_refPB3  | ol_refPBonus3             | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_refP1              |
      | ss_refP2              |
      | ss_refP3              |
      | ss_refPB1             |
      | ss_refPB2             |
      | ss_refPB3             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_refP1                         | shipmentRefP          |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_refP1       | ic_refP1               |
      | ol_refP2       | ic_refP2               |
      | ol_refP3       | ic_refP3               |
      | ol_refPBonus1  | ic_refPB1              |
      | ol_refPBonus2  | ic_refPB2              |
      | ol_refPBonus3  | ic_refPB3              |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule |
      | ic_refP1                          | Y                         |
      | ic_refP2                          | Y                         |
      | ic_refP3                          | Y                         |
      | ic_refPB1                         | Y                         |
      | ic_refPB2                         | Y                         |
      | ic_refPB3                         | Y                         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceRefP             | ic_refP1                          |

    Then validate invoice lines for invoiceRefP:
      | M_Product_ID    | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goods1          | 1           | position1    | 110        |
      | goods2          | 1           | position2    | 220        |
      | goods3          | 1           | null         | 55         |
      | discountProduct | 1           | position1    | -3.30      |
      | discountProduct | 1           | position2    | -6.60      |
      | discountProduct | 1           | null         | -1.65      |
