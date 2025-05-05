@from:cucumber
@ghActions:run_on_executor2
Feature: accounting-sales-harvesting-feature

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE


  @from:cucumber
  @Id:S0307_100
  Scenario: Harvesting calendar and year shall be propagated from sales order to shipment and then to fact_acct
    Given metasfresh contains M_Products:
      | Identifier   | Name            |
      | salesProduct | salesProduct_67 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                     | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_harvesting | pricing_system_harvesting | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | ppl_sales_harvesting | null            | true  | false         | 2              | true         |

    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_67 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | salesProduct            | 0.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endCustomer_1 | EndCustomer_06082023_1 | N            | Y              | ps_1                          |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |
    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | coa_1                   | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | P_Asset_Acct                 | coa_1                   | 90000 |
      | P_COGS_Acct                  | coa_1                   | 90056 |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

    And metasfresh contains C_AcctSchema_Element:
      | C_AcctSchema_Element_ID.Identifier | Name                | ElementType | C_AcctSchema_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | cae_1                              | Harvesting Calendar | HC          | acctSchema_1               | harvesting_calendar                     |                                   |
      | cae_2                              | Harvesting Year     | HY          | acctSchema_1               |                                         | y2022                             |


    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | o_1        | true    | endCustomer_1            | 2022-07-26  | po_ref_mock     | harvesting_calendar                     | y2022                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | salesProduct            | 100        |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | salesProduct            | 0            | 100        | 0           | 0     | 0        | EUR          | true      |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed |
      | s_s_1                            | 100          | 0            | 100        | false     |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | CO            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | true      | 100            | harvesting_calendar                     | y2022                             |

    #   The Fact_Acct records shall contain the the calendar and the year from shipment document
    And after not more than 30s, the inout document with identifier shipment_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | record_id  | Account        | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | factAcct_11             | shipment_1 | P_Asset_Acct   | 0  | 0  | chf                      | P_Asset_Acct              | harvesting_calendar                     | y2022                             |
      | factAcct_21             | shipment_1 | P_COGS_Acct | 0  | 0  | chf                      | P_COGS_Acct               | harvesting_calendar                     | y2022                             |


  @from:cucumber
  @Id:S0307_200
  Scenario: Harvesting calendar and year shall be propagated from sales order to invoice and then to fact_acct
    Given metasfresh contains M_Products:
      | Identifier   | Name            |
      | salesProduct | salesProduct_67 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                      | Value                     | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_harvesting | pricing_system_harvesting | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | ppl_sales_harvesting | true  | false         | 2              | true         |

    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_67 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | salesProduct            | 0.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | endCustomer_1 | EndCustomer_06082023_1 | N            | Y              | ps_1                          | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endCustomer_1            | Y                   | Y                   |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |
    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | coa_1                   | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | C_Receivable_Acct            | coa_1                   | 1100  |
      | P_Revenue_Acct               | coa_1                   | 3000  |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

    And metasfresh contains C_AcctSchema_Element:
      | C_AcctSchema_Element_ID.Identifier | Name                | ElementType | C_AcctSchema_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | cae_1                              | Harvesting Calendar | HC          | acctSchema_1               | harvesting_calendar                     |                                   |
      | cae_2                              | Harvesting Year     | HY          | acctSchema_1               |                                         | y2022                             |


    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | o_1        | true    | endCustomer_1            | 2022-07-26  | harvesting_calendar                     | y2022                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | salesProduct            | 100        |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | salesProduct            | 0            | 100        | 0           | 0     | 0        | EUR          | true      |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | QtyDelivered | QtyOrdered | Processed |
      | s_s_1                            | 100          | 0            | 100        | false     |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | CO            |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoice_candidate_1               | ol_1                      | 100          | harvesting_calendar                     | y2022                             |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |

 #   The Invoice document shall contain the the calendar and the year from invoice candidates
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoice_1               | endCustomer_1            | l_1                               | 1000002     | true      | CO        | harvesting_calendar                     | y2022                             |

  #   The Invoice lines shall contain the the calendar and the year from invoice header
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | invoiceLine1_1              | salesProduct            | 100         | true      | harvesting_calendar                     | y2022                             |


  #   The Fact_Acct records shall contain the the calendar and the year from invoice document
    And after not more than 30s, the invoice document with identifier invoice_1 has the following accounting records:
      | Fact_Acct_ID.Identifier | Account           | DR | CR | C_Currency_ID.Identifier | OPT.AccountConceptualName | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | factAcct_1              | C_Receivable_Acct | 0  | 0  | eur                      | C_Receivable_Acct         | harvesting_calendar                     | y2022                             |
      | factAcct_2              | P_Revenue_Acct    | 0  | 0  | eur                      | P_Revenue_Acct            | harvesting_calendar                     | y2022                             |

