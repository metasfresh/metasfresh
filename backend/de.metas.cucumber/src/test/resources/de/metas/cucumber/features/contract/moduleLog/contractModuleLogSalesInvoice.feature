@ignore
@ghActions:run_on_executor5
Feature: Modular contract log for sales invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-10T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier  | Name                 | Value                |
      | moduleLogPS | moduleLogPS_08182023 | moduleLogPS_08182023 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPS                   | DE                        | EUR                 | moduleLogPL_PO_08182023 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPS                   | DE                        | EUR                 | moduleLogPL_SO_08182023 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_08182023_PO | 2022-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_08182023_SO | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_08182023 | Y            | N              | moduleLogPS                   | 1000002                    |
      | bp_moduleLogSO | bp_moduleLogSO_08182023 | N            | Y              | moduleLogPS                   | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 0818202312346 | bp_moduleLogPO           | true                | true                |
      | bp_moduleLogSO_Location | 0818202312345 | bp_moduleLogSO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value              | Name               | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_08182023 | warehouse_08182023 | bp_moduleLogPO               | bp_moduleLogPO_Location               |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value            | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_08182023 | warehouseModularContract  |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                | Value               | ModularContractHandlerType |
      | modCntr_type_PO            | modCntr_type_PO     | modCntr_type_PO     | PurchaseOrderLine_Modular  |
      | modCntr_type_SO            | modCntr_type_SO     | modCntr_type_SO     | SOLineForPO_Modular        |
      | modCntr_type_SHIP          | modCntr_type_SHIP   | modCntr_type_SHIP   | ShipmentLineForPO_Modular  |
      | modCntr_type_INV_SO        | modCntr_type_INV_SO | modCntr_type_INV_SO | SalesInvoiceLine_Modular   |


  @Id:S0305_100
  @from:cucumber
  Scenario: When a sales invoice is completed, create a modular contract log for each line that requires it
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order with two order line and complete it
  - generate shipment and invoice
  - validate that for each invoice line a modular contract log is created

    Given load C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.C_Aggregation_ID |
      | stdAgg                      | 1000000              |
      | harvestingAgg               | 540015               |
    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | stdAgg                      | N             |
      | harvestingAgg               | Y             |

    And metasfresh contains M_Products:
      | Identifier           | Name                              |
      | modularContract_prod | modularContract_prod_100_08182023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod    | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                  | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_2023          | testSettings_08182023 | modularContract_prod        | harvesting_calendar      | year_2023            | moduleLogPS                       |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name    | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO            | 10    | name_10 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_PO            |
      | modCntr_module_SO            | 20    | name_20 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SO            |
      | modCntr_module_SHIP          | 30    | name_30 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SHIP          |
      | modCntr_module_INV_SO        | 40    | name_40 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_INV_SO        |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                | Name                      | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023 | modularContractTerms_2023 | ModularContract | moduleLogPS                       | Ex                       | modCntr_settings_2023              |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_8 | modularContract_prod        | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference            | OPT.M_Warehouse_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_08182023 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine     | po_order              | modularContract_prod    | 10         | modularContractTerms_2023               |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod    | po_order                       | poLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogPO              | modularContract_prod    | poLine                             | po_order                       | PCE                   | 10                    | 5.00            | moduleLogPS                       | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_100_08182023 | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod    | 8          |
      | soLine_2   | so_order              | modularContract_prod    | 3          |

    When the order identified by so_order is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.Description                                                                                                                   |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         | Auftrag für Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 8 Stk wurde fertiggestellt. |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         | Auftrag für Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 3 Stk wurde fertiggestellt. |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipSched_1 | soLine_1                  | N             |
      | shipSched_2 | soLine_2                  | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched_1                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched_1                      | ship_1                |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipLine_1                | ship_1                | modularContract_prod    | 8           | true      | soLine_1                      |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched_2                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched_2                      | ship_2                |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipLine_2                | ship_2                | modularContract_prod    | 3           | true      | soLine_2                      |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         |
      | shipLog_1                 | shipLine_1           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            |
      | shipLog_2                 | shipLine_2           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | soLine_1                  | 8            |
      | ic_2                              | soLine_2                  | 3            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.M_Product_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.HeaderAggregationKeyBuilder_ID.Identifier |
      | ic_1                              | soLine_1                      | 8            | modularContract_prod        | harvesting_calendar                     | year_2023                         | warehouseModularContract      | harvestingAgg                                 |
      | ic_2                              | soLine_2                      | 3            | modularContract_prod        | harvesting_calendar                     | year_2023                         | warehouseModularContract      | harvestingAgg                                 |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1,ic_2                         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | ic_1,ic_2                         |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | invoice_1               | bp_moduleLogSO           | bp_moduleLogSO_Location           | 1000002     | true      | CO        | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | modularContract_prod    | 8           | true      |
      | il_2                        | invoice_1               | modularContract_prod    | 3           | true      |

    # dev-note: ModCntr_InvoicingGroup_ID is null for invoice lines because their DateTrx is after ModCntr_InvoicingGroup.ValidTo (2022-12-12)
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName     | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.Description                                                                                                                                 |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine   | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         | Auftrag für Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 8 Stk wurde fertiggestellt.               |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine   | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         | Auftrag für Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 3 Stk wurde fertiggestellt.               |
      | shipLog_1                 | shipLine_1           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine   | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            | Eine Lieferung für das Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 8 Stk wurde fertiggestellt.    |
      | shipLog_2                 | shipLine_2           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine   | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            | Eine Lieferung für das Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 3 Stk wurde fertiggestellt.    |
      | ilLog_1                   | il_1                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | 80         | Debitorenrechnung für das Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 8 Stk wurde fertiggestellt. |
      | ilLog_2                   | il_2                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | 30         | Debitorenrechnung für das Produkt modularContract_prod_100_08182023_modularContract_prod_100_08182023 mit der Menge 3 Stk wurde fertiggestellt. |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName     | ProcessingStatus |
      | soLine_1             | C_OrderLine   | SP               |
      | soLine_2             | C_OrderLine   | SP               |
      | shipLine_1           | M_InOutLine   | SP               |
      | shipLine_2           | M_InOutLine   | SP               |
      | il_1                 | C_InvoiceLine | SP               |
      | il_2                 | C_InvoiceLine | SP               |

    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | harvestingAgg               | N             |
      | stdAgg                      | Y             |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | C_Invoice | invoice_1            |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName     | ProcessingStatus | OPT.noOfLogStatuses |
      | il_1                 | C_InvoiceLine | SP               | 2                   |
      | il_2                 | C_InvoiceLine | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName     | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | ilLog_1                   | il_1                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       |
      | ilLog_2                   | il_2                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       |


  @Id:S0305_200
  @from:cucumber
  Scenario: When a sales invoice is reversed, a modular contract log is created with negated qty
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order with two order line and complete it
  - generate shipment and invoice
  - reverse the invoice and validate that logs with negated qty are created

    Given load C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.C_Aggregation_ID |
      | stdAgg                      | 1000000              |
      | harvestingAgg               | 540015               |
    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | stdAgg                      | N             |
      | harvestingAgg               | Y             |

    And metasfresh contains M_Products:
      | Identifier           | Name                              |
      | modularContract_prod | modularContract_prod_200_08212023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod    | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                  | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_2023          | testSettings_08212023 | modularContract_prod        | harvesting_calendar      | year_2023            | moduleLogPS                       |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name    | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO            | 10    | name_10 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_PO            |
      | modCntr_module_SO            | 20    | name_20 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SO            |
      | modCntr_module_SHIP          | 30    | name_30 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SHIP          |
      | modCntr_module_INV_SO        | 40    | name_40 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_INV_SO        |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                | Name                      | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023 | modularContractTerms_2023 | ModularContract | moduleLogPS                       | Ex                       | modCntr_settings_2023              |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_9 | modularContract_prod        | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference            | OPT.M_Warehouse_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_08212023 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine     | po_order              | modularContract_prod    | 10         | modularContractTerms_2023               |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod    | po_order                       | poLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogPO              | modularContract_prod    | poLine                             | po_order                       | PCE                   | 10                    | 5.00            | moduleLogPS                       | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_200_08212023 | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod    | 8          |
      | soLine_2   | so_order              | modularContract_prod    | 3          |

    When the order identified by so_order is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.Description |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         |                 |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         |                 |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipSched_1 | soLine_1                  | N             |
      | shipSched_2 | soLine_2                  | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched_1                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched_1                      | ship_1                |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipLine_1                | ship_1                | modularContract_prod    | 8           | true      | soLine_1                      |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched_2                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched_2                      | ship_2                |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipLine_2                | ship_2                | modularContract_prod    | 3           | true      | soLine_2                      |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.Description                                                                                                                              |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         | Auftrag für Produkt modularContract_prod_200_08212023_modularContract_prod_200_08212023 mit der Menge 8 Stk wurde fertiggestellt.            |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         | Auftrag für Produkt modularContract_prod_200_08212023_modularContract_prod_200_08212023 mit der Menge 3 Stk wurde fertiggestellt.            |
      | shipLog_1                 | shipLine_1           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            | Eine Lieferung für das Produkt modularContract_prod_200_08212023_modularContract_prod_200_08212023 mit der Menge 8 Stk wurde fertiggestellt. |
      | shipLog_2                 | shipLine_2           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            | Eine Lieferung für das Produkt modularContract_prod_200_08212023_modularContract_prod_200_08212023 mit der Menge 3 Stk wurde fertiggestellt. |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | soLine_1                  | 8            |
      | ic_2                              | soLine_2                  | 3            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.M_Product_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.HeaderAggregationKeyBuilder_ID.Identifier |
      | ic_1                              | soLine_1                      | 8            | modularContract_prod        | harvesting_calendar                     | year_2023                         | warehouseModularContract      | harvestingAgg                                 |
      | ic_2                              | soLine_2                      | 3            | modularContract_prod        | harvesting_calendar                     | year_2023                         | warehouseModularContract      | harvestingAgg                                 |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1,ic_2                         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | ic_1,ic_2                         |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | invoice_1               | bp_moduleLogSO           | bp_moduleLogSO_Location           | 1000002     | true      | CO        | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | modularContract_prod    | 8           | true      |
      | il_2                        | invoice_1               | modularContract_prod    | 3           | true      |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName     | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine   | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine   | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         |
      | shipLog_1                 | shipLine_1           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine   | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            |
      | shipLog_2                 | shipLine_2           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine   | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            |
      | ilLog_1                   | il_1                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | 80         |
      | ilLog_2                   | il_2                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | 30         |

    When the invoice identified by invoice_1 is reversed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName     | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine   | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 80         |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine   | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 30         |
      | shipLog_1                 | shipLine_1           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine   | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            |
      | shipLog_2                 | shipLine_2           | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine   | moduleLogContract             | modCntr_type_SHIP              | false         | Shipment                     | year_2023                         | false       | null                             | null            | null                      |            |
      | ilLog_1                   | il_1                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | 80         |
      | ilLog_2                   | il_2                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | 30         |
      | ilLog_3                   | il_1                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | -8  | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | -80        |
      | ilLog_4                   | il_2                 | ModularContract | null                                     | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | -3  | C_InvoiceLine | moduleLogContract             | modCntr_type_INV_SO            | false         | SalesInvoice                 | year_2023                         | false       | modCntr_module_INV_SO            | 10.00           | PCE                       | -30        |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName     | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1             | C_OrderLine   | SP               |                     |
      | soLine_2             | C_OrderLine   | SP               |                     |
      | shipLine_1           | M_InOutLine   | SP               |                     |
      | shipLine_2           | M_InOutLine   | SP               |                     |
      | il_1                 | C_InvoiceLine | SP               | 2                   |
      | il_2                 | C_InvoiceLine | SP               | 2                   |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | C_Invoice | invoice_1            |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName     | ProcessingStatus | OPT.noOfLogStatuses |
      | il_1                 | C_InvoiceLine | SP               | 3                   |
      | il_2                 | C_InvoiceLine | SP               | 3                   |
    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName     |
      | il_1                 | C_InvoiceLine |
      | il_2                 | C_InvoiceLine |

    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | harvestingAgg               | N             |
      | stdAgg                      | Y             |


  @Id:S0305_300
  @from:cucumber
  Scenario: Regression: When a sales invoice is completed without modular settings, validate that no modular contract log is created for invoice lines
  - complete SO and validate no ModCntr_Logs are created for order lines
  - generate shipment and validate no ModCntr_Logs for shipment lines
  - generate invoice and validate no ModCntr_Logs for invoice lines

    Given load C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.C_Aggregation_ID |
      | stdAgg                      | 1000000              |
      | harvestingAgg               | 540015               |
    And update C_Aggregation:
      | C_Aggregation_ID.Identifier | OPT.IsDefault |
      | harvestingAgg               | N             |
      | stdAgg                      | Y             |

    And metasfresh contains M_Products:
      | Identifier           | Name                              |
      | modularContract_prod | modularContract_prod_300_08212023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |


    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_300_08212023 | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod    | 10         |
      | soLine_2   | so_order              | modularContract_prod    | 5          |

    When the order identified by so_order is completed

    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName   |
      | soLine_1             | C_OrderLine |
      | soLine_2             | C_OrderLine |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipSched_1 | soLine_1                  | N             |
      | shipSched_2 | soLine_2                  | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched_1                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched_1                      | ship_1                |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipLine_1                | ship_1                | modularContract_prod    | 10          | true      | soLine_1                      |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipSched_2                      | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipSched_2                      | ship_2                |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipLine_2                | ship_2                | modularContract_prod    | 5           | true      | soLine_2                      |

    And after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName   |
      | shipLine_1           | M_InOutLine |
      | shipLine_2           | M_InOutLine |

    And after not more than 30s, no ModCntr_Log_Statuses are found:
      | Record_ID.Identifier | TableName   |
      | shipLine_1           | M_InOutLine |
      | shipLine_2           | M_InOutLine |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | soLine_1                  | 10           |
      | ic_2                              | soLine_2                  | 5            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.M_Product_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ic_1                              | soLine_1                      | 10           | modularContract_prod        | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
      | ic_2                              | soLine_2                      | 5            | modularContract_prod        | harvesting_calendar                     | year_2023                         | warehouseModularContract      |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1,ic_2                         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | ic_1,ic_2                         |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | invoice_1               | bp_moduleLogSO           | bp_moduleLogSO_Location           | 1000002     | true      | CO        | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il_1                        | invoice_1               | modularContract_prod    | 10          | true      |
      | il_2                        | invoice_1               | modularContract_prod    | 5           | true      |

    And after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName     |
      | il_1                 | C_InvoiceLine |
      | il_2                 | C_InvoiceLine |

    And after not more than 30s, no ModCntr_Log_Statuses are found:
      | Record_ID.Identifier | TableName     |
      | il_1                 | C_InvoiceLine |
      | il_2                 | C_InvoiceLine |

