@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract-triggered compensation group with a service discount product on a partially delivered and invoiced sales order
## F2070: Compensation Group Contract
##
## The discount product is a service: its order line is never shipped (no shipment schedule) and its
## invoice candidate is invoiced "immediately" by itself (no InvoiceRule_Override set by the test).
## The goods are invoiced "after delivery". The discount must still follow the invoiced goods: each
## invoice carries the discount on the goods it invoices, never the whole discount up front.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    # the discount product is a service, so its tax category must not be restricted to items
    And taxCategory 'Normal' is updated to work with all productTypes

    # unique per run (auto-generated Value/Name): no category from an earlier run is reused
    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |

    # goods not stocked: shipments need no inventory
    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | ProductType | OPT.IsStocked |
      | goods1          | goodsCategory                        | I           | false         |
      | goods2          | goodsCategory                        | I           | false         |
      | goods3          | goodsCategory                        | I           | false         |
      | discountProduct |                                      | S           | false         |

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
  # The service discount is never shipped and is invoiced "immediately" - and still each invoice
  # carries only the discount on the goods it invoices
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  Scenario: A service discount on a partially delivered sales order follows the invoiced goods, without shipping the discount
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

    # only the goods are shipped: the service discount line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1  | ol_goods1                 | N             |
      | ss_goods2  | ol_goods2                 | N             |
      | ss_goods3  | ol_goods3                 | N             |
    And there is no M_ShipmentSchedule for C_OrderLine ol_discount

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_discount    | ic_discount            |

    # the discount candidate is "immediate" by its own (service), the goods "after delivery";
    # with nothing delivered yet there is no discount to invoice either
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | InvoiceRule | InvoiceRule_Override | QtyToInvoice | NetAmtToInvoice |
      | ic_goods1                         | D           | null                 | 0            | 0               |
      | ic_goods2                         | D           | null                 | 0            | 0               |
      | ic_goods3                         | D           | null                 | 0            | 0               |
      | ic_discount                       | D           | I                    | 0            | 0               |

    # ##########################################################################################
    # First delivery: goods1 only
    # ##########################################################################################
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_discount            |

    # the discount candidate is computed on the delivered goods only: 3% of 1000 - not the whole -51
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_goods1                         | 1000            |
      | ic_goods2                         | 0               |
      | ic_goods3                         | 0               |
      | ic_discount                       | -30             |

    # a compensation group is invoiced as a whole: all of its candidates are selected; the not yet
    # delivered ones have nothing to invoice
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

    # the discount candidate stays open for the goods not yet invoiced
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

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_discount            |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyOrdered | QtyInvoiced | QtyToInvoice | NetAmtToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 2          | 2           | 0            | 0               | -51            | true      | false   |
