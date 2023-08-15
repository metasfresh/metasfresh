@dev:runThisOne
Feature: Invoice candidate aggregation with harvesting details rule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier   | Name                  | Value                 |
      | harvestingPS | harvestingPS_08112023 | harvestingPS_08112023 |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | SOTrx | IsTaxIncluded | PricePrecision |
      | harvestingPL_SO | harvestingPS                  | DE                        | EUR                 | harvestingPL_SO_08112023 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | harvestingPLV_SO | harvestingPL_SO           | harvestingPLV_08112023_SO | 2022-02-01 |
    And metasfresh contains M_Products:
      | Identifier     | Name                    |
      | harvestingProd | harvestingProd_08112023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_SO | harvestingPLV_SO                  | harvestingProd          | 10.00    | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value | OPT.InvoiceRule |
      | bp_harvestingSO | bp_harvestingSO_08112023 | N            | Y              | harvestingPS                  | 1000002                    | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_harvestingSO_Location | 0811202312345 | bp_harvestingSO          | true                | true                |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |

  @from:cucumber
  Scenario: Validate IC aggregation with harvesting-details set as default
  - set `invoicing-agg-per-hervesting-details` as default aggregation
  - create 2 SO with harvesting details and same warehouse and complete them
  - harvesting details propagated to IC
  - generate invoice for ICs -> validate one invoice is created for both SO

    Given load C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.C_Aggregation_ID |
      | stdAgg                      | 1000000              |
      | harvestingAgg               | 540015               |
    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | stdAgg                      | N             |
      | harvestingAgg               | Y             |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.InvoiceRule |
      | so_1       | true    | bp_harvestingSO          | 2022-03-03  | harvesting_calendar                     | year_2023                         | warehouseStd                  | I               |
      | so_2       | true    | bp_harvestingSO          | 2022-03-03  | harvesting_calendar                     | year_2023                         | warehouseStd                  | I               |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_1                  | harvestingProd          | 4          |
      | soLine_2   | so_2                  | harvestingProd          | 6          |

    When the order identified by so_1 is completed
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | soLine_1                  | 4            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.HeaderAggregationKeyBuilder_ID.Identifier |
      | ic_1                              | soLine_1                      | 4            | 4              | 0                | harvestingProd              | harvesting_calendar                     | year_2023                         | warehouseStd                  | harvestingAgg                                 |

    When the order identified by so_2 is completed
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_2                              | soLine_2                  | 6            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.HeaderAggregationKeyBuilder_ID.Identifier |
      | ic_2                              | soLine_2                      | 6            | 6              | 0                | harvestingProd              | harvesting_calendar                     | year_2023                         | warehouseStd                  | harvestingAgg                                 |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1,ic_2                         |

    # we expect both ICs to end up in the same invoice
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | ic_1,ic_2                         |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | invoice_1               | bp_harvestingSO          | bp_harvestingSO_Location          | 1000002     | true      | CO        | harvesting_calendar                     | year_2023                         | warehouseStd                  |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | harvestingProd          | 4           | true      |
      | il_2                        | invoice_1               | harvestingProd          | 6           | true      |

    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | harvestingAgg               | N             |
      | stdAgg                      | Y             |

