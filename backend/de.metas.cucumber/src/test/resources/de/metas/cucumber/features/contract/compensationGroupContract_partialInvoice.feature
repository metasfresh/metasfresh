@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
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
  # A percent group that was not created by a contract follows partially invoiced goods, too:
  # each invoice carries the discount on its own goods
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC42
  Scenario: A percent group from a product schema carries the discount on each partial invoice's goods
    Given metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | plainBP    | Y              | contractPS                    | D               |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier    | Name       |
      | productSchema | Bundle 3 % |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_goods1  | productSchema                 | goods1       | 1   | PCE      | 10    |
      | tl_goods2  | productSchema                 | goods2       | 1   | PCE      | 20    |
      | tl_goods3  | productSchema                 | goods3       | 1   | PCE      | 30    |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier        | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount |
      | productSchemaLine | productSchema                            | discountProduct         | 3                         |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderProduct | true    | plainBP                  | 2026-07-01  |
    When create compensation group from schema template:
      | C_Order_ID   | C_CompensationGroup_Schema_ID | Qty |
      | orderProduct | productSchema                 | 1   |

    And the order identified by orderProduct is completed

    # 3% of 1000 + 500 + 200 = 51; no contract group on top: plainBP has no contract
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discount               | orderProduct          | discountProduct         | 1          | true                        | -51   |
    And the order identified by orderProduct has 4 order lines

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | schema_ol_1               | N             |
      | ss_goods2   | schema_ol_2               | N             |
      | ss_goods3   | schema_ol_3               | N             |
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
      | schema_ol_1    | ic_goods1              |
      | schema_ol_2    | ic_goods2              |
      | schema_ol_3    | ic_goods3              |
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

    # the rest of the discount stays open for the goods still to come
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 1           | 0            | -30            | false     | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderProduct | PI            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

    # second invoice: goods2 and goods3, 3% of 700
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
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_goods3                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_goods3             | goods3                  | 1           | 200        |
      | invoice2_discount           | discountProduct         | 1           | -21        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2           | 0            | -51            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderProduct | CI            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |
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
  # A percent group created by hand on the order lines follows partially invoiced goods, too
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC48
  Scenario: A percent group created on the order's lines carries the discount on each partial invoice's goods
    Given metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | plainBP    | Y              | contractPS                    | D               |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderManual | true    | plainBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderManual           | goods1                  | 1          |
      | ol_goods2  | orderManual           | goods2                  | 1          |
      | ol_goods3  | orderManual           | goods3                  | 1          |
    When create compensation group from order lines:
      | C_OrderLine_ID                | M_Product_ID    | Name       | CompensationLine | OPT.GroupCompensationPercentage |
      | ol_goods1,ol_goods2,ol_goods3 | discountProduct | Bundle 3 % | ol_discount      | 3                               |

    And the order identified by orderManual is completed

    # 3% of 1000 + 500 + 200 = 51
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discount               | orderManual           | discountProduct         | 1          | true                        | -51   |

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

    # the rest of the discount stays open for the goods still to come
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 1           | 0            | -30            | false     | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderManual  | PI            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

    # second invoice: goods2 and goods3, 3% of 700
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
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_goods3                         |
      | ic_discount                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_goods2                         |
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_goods2             | goods2                  | 1           | 500        |
      | invoice2_goods3             | goods3                  | 1           | 200        |
      | invoice2_discount           | discountProduct         | 1           | -21        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2           | 0            | -51            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderManual  | CI            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

  # ##############################################################################################
  # A fixed-amount discount is not split by partial invoicing: the first invoice carries all of it
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC49
  Scenario: A fixed-amount group discount is used up by the first partial invoice
    Given metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | plainBP    | Y              | contractPS                    | D               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderFixed | true    | plainBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderFixed            | goods1                  | 1          |
      | ol_goods2  | orderFixed            | goods2                  | 1          |
    When create compensation group from order lines:
      | C_OrderLine_ID      | M_Product_ID    | Name         | CompensationLine | OPT.GroupCompensationAmtType | OPT.PriceEntered |
      | ol_goods1,ol_goods2 | discountProduct | Bundle fixed | ol_discount      | Q                            | -50              |

    And the order identified by orderFixed is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discount               | orderFixed            | discountProduct         | 1          | true                        | -50   |

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
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_discount           | discountProduct         | 1           | -50        |

    # nothing of the fixed amount is left for the goods still to come
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | Processed |
      | ic_discount                       | 1          | 1           | 0            | true      |
