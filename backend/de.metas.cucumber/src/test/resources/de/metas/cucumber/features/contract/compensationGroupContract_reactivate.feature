@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract-triggered compensation group on sales-order reactivation
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name | Value                |
      | goodsCategory | Ware | WareS32353Reactivate |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | contractProduct |                                      | false         |
      | goodsProduct    | goodsCategory                        | true          |
      | discountProduct | goodsCategory                        | true          |

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
      | pp_discount | contractPLV                       | discountProduct         | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |

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
      | Identifier     | Name            | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | mainConditions | Main conditions | CompensationGroup | contractProduct                      | zeroDurTrans                            | mainSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | mainTerm   | mainConditions                      | headOfficeBP                | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by mainTerm is completed

  # ##############################################################################################
  # Reactivate removes the contract-created group; completing again rebuilds it on the new base
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC20
  Scenario: Reactivating a sales order removes its contract-created compensation group; completing again rebuilds it on the current lines
    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderReactivate | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_reactivateGoods | orderReactivate       | goodsProduct            | 1          |

    And the order identified by orderReactivate is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_reactivateDiscount     | orderReactivate       | discountProduct         | 1          | true                        | -30   | mainTerm                          |
    And the order identified by orderReactivate has 2 order lines

    # a user reactivates an order whose invoice candidates and shipment schedules already exist
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID        | C_Invoice_Candidate_ID |
      | ol_reactivateGoods    | ic_reactivateGoods     |
      | ol_reactivateDiscount | ic_reactivateDiscount  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_reactivateGoods | ol_reactivateGoods        | N             |

    And the order identified by orderReactivate is reactivated

    Then no C_Order_CompensationGroup exists for order "orderReactivate"
    And the order identified by orderReactivate has 1 order lines

    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_reactivateGoods2 | orderReactivate       | goodsProduct            | 1          |

    And the order identified by orderReactivate is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_reactivateDiscount2    | orderReactivate       | discountProduct         | 1          | true                        | -60   | mainTerm                          |
    And the order identified by orderReactivate has 3 order lines

    # The rebuilt group's invoice candidates must include ol_reactivateGoods (pre-existing before the reactivate)
    # alongside ol_reactivateGoods2 (added after it) -- not just the newly-created lines. A regular line's invoice
    # candidate created before it is (re)grouped keeps a denormalized C_Order_CompensationGroup_ID that is
    # only ever set once, at candidate creation; if it were never re-synced when the order line itself gets
    # (re)grouped, the discount candidate would recompute against ol_reactivateGoods2 alone (-30) instead of both
    # regular lines (-60), even though the order line itself already shows the correct -60 above.
    # (ic_reactivateGoods was already located before the reactivation; an identifier is registered only once)
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID         | C_Invoice_Candidate_ID |
      | ol_reactivateGoods2    | ic_reactivateGoods2    |
      | ol_reactivateDiscount2 | ic_reactivateDiscount2 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_reactivateGoods     |

    # Both goods products are stocked, so neither candidate's NetAmtToInvoice is populated without an
    # actual delivery -- override the invoice rule to Immediate (as the refused-reactivation scenarios below do) so the regular lines'
    # base amounts become invoiceable without a shipment, then wait until the resulting recompute has
    # landed (these candidates are already located above -- re-locating by order line would re-register
    # the same identifiers and fail), before checking the discount candidate's amount below.
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | ic_reactivateGoods     | I                        |
      | ic_reactivateGoods2    | I                        |
      | ic_reactivateDiscount2 | I                        |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_reactivateGoods     |
      | ic_reactivateGoods2    |
      | ic_reactivateDiscount2 |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_reactivateDiscount2            | -60             |

  # ##############################################################################################
  # Reactivating while the missing shipment schedules are being created
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC78
  Scenario: Reactivating a sales order while its missing shipment schedules are being created does not make their creation fail
  _Given a sales order whose completion added a contract discount line, and whose lines have no shipment schedules yet
  _When the order is reactivated (deleting the discount line) and, before that reactivation commits, the missing shipment schedules are created
  _Then both succeed, without a shipment schedule for the deleted discount line
  _And completing the order again creates the shipment schedules of its current lines

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderRace  | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_raceGoods | orderRace             | goodsProduct            | 1          |

    When the order identified by orderRace is completed and then reactivated while its missing shipment schedules are being created

    Then no C_Order_CompensationGroup exists for order "orderRace"
    And the order identified by orderRace has 1 order lines

    When the order identified by orderRace is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_raceDiscount           | orderRace             | discountProduct         | 1          | true                        | -30   | mainTerm                          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_raceGoods    | ol_raceGoods              | N             |
      | ss_raceDiscount | ol_raceDiscount           | N             |

  # ##############################################################################################
  # Reactivation is refused while a contract discount line is already invoiced
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC21
  Scenario: Reactivating a sales order is refused while its contract discount line is already invoiced
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderInvoiced | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_invoicedGoods | orderInvoiced         | goodsProduct            | 1          |

    And the order identified by orderInvoiced is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_invoicedDiscount       | orderInvoiced         | discountProduct         | 1          | true                        | -30   | mainTerm                          |

    # invoice rule immediate: the whole order (goods + discount) is invoiced without any delivery — a
    # compensation group's discount line cannot be invoiced on its own while its regular line is not
    # (InvoiceCandEnqueuer_IncompleteGroupsFound), so both lines' invoice candidates are overridden together.
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID      | C_Invoice_Candidate_ID |
      | ol_invoicedGoods    | ic_invoicedGoods       |
      | ol_invoicedDiscount | ic_invoicedDiscount    |

    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | ic_invoicedGoods       | I                        |
      | ic_invoicedDiscount    | I                        |

    # a compensation group is invoiced as a whole (InvoiceCandEnqueuer_IncompleteGroupsFound otherwise)
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_invoicedGoods                  |
      | ic_invoicedDiscount               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invInvoicedDiscount     | ic_invoicedDiscount               |

    And the order identified by orderInvoiced cannot be reactivated because of error code ContractCompGroup_ReactivateInvoiced

  # ##############################################################################################
  # Reactivation is refused while a contract discount line is only PARTLY invoiced
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC22
  Scenario: Reactivating a sales order is refused while its contract discount line is only partly invoiced
    Given metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderPartlyInvoiced | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_partlyInvoicedGoods | orderPartlyInvoiced   | goodsProduct            | 1          |

    And the order identified by orderPartlyInvoiced is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_partlyInvoicedDiscount | orderPartlyInvoiced   | discountProduct         | 1          | true                        | -30   | mainTerm                          |

    # same "invoiced together" constraint as the previous scenario (InvoiceCandEnqueuer_IncompleteGroupsFound);
    # unlike it, only HALF of the discount line's quantity is invoiced (QtyToInvoice_Override), so its
    # QtyInvoiced ends up non-zero but below QtyOrdered — a partial, not a full, invoice.
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID            | C_Invoice_Candidate_ID    |
      | ol_partlyInvoicedGoods    | ic_partlyInvoicedGoods    |
      | ol_partlyInvoicedDiscount | ic_partlyInvoicedDiscount |

    And update invoice candidates
      | C_Invoice_Candidate_ID    | OPT.InvoiceRule_Override | OPT.QtyToInvoice_Override |
      | ic_partlyInvoicedGoods    | I                        |                           |
      | ic_partlyInvoicedDiscount | I                        | 0.5                       |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_partlyInvoicedGoods            |                 |
      | ic_partlyInvoicedDiscount         | 0.5             |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier   | C_Invoice_Candidate_ID.Identifier |
      | invPartlyInvoicedDiscount | ic_partlyInvoicedDiscount         |

    And the order identified by orderPartlyInvoiced cannot be reactivated because of error code ContractCompGroup_ReactivateInvoiced
