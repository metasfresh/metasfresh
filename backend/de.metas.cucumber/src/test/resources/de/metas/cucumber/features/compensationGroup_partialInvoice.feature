@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00102
@ghActions:run_on_executor3
Feature: Compensation group on a partially delivered and invoiced sales order
## F00102: Compensation Group

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    # not stocked: shipments need no inventory
    And metasfresh contains M_Products:
      | Identifier      | OPT.IsStocked |
      | goods1          | false         |
      | goods2          | false         |
      | goods3          | false         |
      | discountProduct | false         |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | groupPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name    | SOTrx | IsTaxIncluded | PricePrecision |
      | groupPL    | groupPS                       | DE                        | EUR                 | groupPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name     | ValidFrom  |
      | groupPLV   | groupPL                   | groupPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods1   | groupPLV                          | goods1                  | 1000     | PCE               | Normal                        |
      | pp_goods2   | groupPLV                          | goods2                  | 500      | PCE               | Normal                        |
      | pp_goods3   | groupPLV                          | goods3                  | 200      | PCE               | Normal                        |
      | pp_discount | groupPLV                          | discountProduct         | 1        | PCE               | Normal                        |

    # invoice rule "after delivery": only what was shipped becomes invoiceable
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | plainBP    | Y              | groupPS                       | D               |

  # ##############################################################################################
  # A percent group that was not created by a contract follows partially invoiced goods, too:
  # each invoice carries the discount on its own goods
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC42
  Scenario: A percent group from a product schema carries the discount on each partial invoice's goods

    Given metasfresh contains C_CompensationGroup_Schema:
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

    # 3% of 1000 + 500 + 200 = 51
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
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 2           | 0            | -51            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderProduct | CI            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | ol_discount    | 1           |

  # ##############################################################################################
  # A percent group created by hand on the order lines follows partially invoiced goods, too
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC48
  Scenario: A percent group created on the order's lines carries the discount on each partial invoice's goods
    Given metasfresh contains C_Orders:
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
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 2           | 0            | -51            | true      | false   |
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
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC49
  Scenario: A fixed-amount group discount is used up by the first partial invoice
    Given metasfresh contains C_Orders:
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

  # ##############################################################################################
  # A 0 % discount goes with the first goods' invoice and is done once all goods are invoiced,
  # so that the order still ends completely invoiced
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC50
  Scenario: A 0 % percent group is invoiced with the first of its partially invoiced goods and the order ends completely invoiced
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderZero  | true    | plainBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderZero             | goods1                  | 1          |
      | ol_goods2  | orderZero             | goods2                  | 1          |
    When create compensation group from order lines:
      | C_OrderLine_ID      | M_Product_ID    | Name       | CompensationLine | OPT.GroupCompensationPercentage |
      | ol_goods1,ol_goods2 | discountProduct | Bundle 0 % | ol_discount      | 0                               |

    And the order identified by orderZero is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discount               | orderZero             | discountProduct         | 1          | true                        | 0     |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | ol_goods1                 | N             |
      | ss_goods2   | ol_goods2                 | N             |
      | ss_discount | ol_discount               | N             |

    # first invoice: goods1, with the 0.00 discount
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
      | invoice1_discount           | discountProduct         | 1           | 0          |

    # second invoice: goods2 only; with it, all goods are invoiced and the 0.00 discount is done
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
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 1          | 1           | 0            | 0              | true      | false   |
    And validate the created orders
      | C_Order_ID | InvoiceStatus |
      | orderZero  | CI            |

  # ##############################################################################################
  # Reversing an earlier partial invoice of a product-schema group reopens that invoice's discount
  # and keeps the later invoice's discount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC51
  Scenario: Reversing the first of two partial invoices of a product-schema percent group keeps the second invoice's discount and reopens the first one's
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier    | Name       |
      | productSchema | Bundle 3 % |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_goods1  | productSchema                 | goods1       | 1   | PCE      | 10    |
      | tl_goods2  | productSchema                 | goods2       | 1   | PCE      | 20    |
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

    # 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discount               | orderProduct          | discountProduct         | 1          | true                        | -45   |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | schema_ol_1               | N             |
      | ss_goods2   | schema_ol_2               | N             |
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
      | schema_ol_1    | ic_goods1              |
      | schema_ol_2    | ic_goods2              |
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
      | orderProduct | PI            |

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
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 2           | 0            | -45            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderProduct | CI            |

  # ##############################################################################################
  # A percent discount left off the goods' invoice follows with a later invoice at its full amount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC53
  Scenario: A product-schema percent discount held back from its goods' invoice is invoiced later at its full amount
    Given metasfresh contains C_CompensationGroup_Schema:
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

    # 3% of 1000 + 500 + 200 = 51
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price |
      | ol_discount               | orderProduct          | discountProduct         | 1          | true                        | -51   |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | schema_ol_1               | N             |
      | ss_goods2   | schema_ol_2               | N             |
      | ss_goods3   | schema_ol_3               | N             |
      | ss_discount | ol_discount               | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_goods2             |
      | ss_goods3             |
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

    # the user holds the discount back from the goods' invoice
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyToInvoice_Override |
      | ic_discount            | 0                     |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 1               |
      | ic_goods3                         | 1               |
      | ic_discount                       | 0               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_goods2             | goods2                  | 1           | 500        |
      | invoice1_goods3             | goods3                  | 1           | 200        |

    # the discount is still open at its full amount
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 0           | 0              | false     | false   |

    # the user releases the discount; it follows with the next invoice
    When update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyToInvoice_Override |
      | ic_discount            | 1                     |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | NetAmtToInvoice | Processed | IsError |
      | ic_discount                       | 1            | -51             | false     | false   |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_goods1                         |
      | ic_goods2                         |
      | ic_goods3                         |
      | ic_discount                       |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice2                | ic_discount                       |
    # a discount alone makes a credit memo: its line credits one unit of -51, i.e. 51
    And validate invoice lines for invoice2:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice2_discount           | discountProduct         | -1          | 51         |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 1          | 1           | 0            | -51            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderProduct | CI            |
