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
  # TS5: reactivate removes the contract-created group; completing again rebuilds it on the new base
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC20
  Scenario: Reactivating a sales order removes its contract-created compensation group; completing again rebuilds it on the current lines
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderTS5   | true    | storeBP                  | storeBP                               | 2026-07-01  | headOfficeBP                    |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ts5Goods | orderTS5              | goodsProduct            | 1          |

    And the order identified by orderTS5 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_ts5Discount            | orderTS5              | discountProduct         | 1          | true                        | -30   | mainTerm                          |
    And the order identified by orderTS5 has 2 order lines

    And the order identified by orderTS5 is reactivated

    Then no C_Order_CompensationGroup exists for order "orderTS5"
    And the order identified by orderTS5 has 1 order lines

    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ts5Goods2 | orderTS5              | goodsProduct            | 1          |

    And the order identified by orderTS5 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_ts5Discount2           | orderTS5              | discountProduct         | 1          | true                        | -60   | mainTerm                          |
    And the order identified by orderTS5 has 3 order lines

  # ##############################################################################################
  # TS5 variant: reactivation is refused while a contract discount line is already invoiced
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
  # TS5 variant: reactivation is refused while a contract discount line is only PARTLY invoiced
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
