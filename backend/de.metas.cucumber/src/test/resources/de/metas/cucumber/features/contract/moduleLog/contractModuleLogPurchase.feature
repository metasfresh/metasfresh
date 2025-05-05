@ignore
@ghActions:run_on_executor5
Feature: Modular contract log from purchase order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_06292023_2 | moduleLogPricingSystem_06292023_2 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_06292023_2 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | moduleLogPLV | moduleLogPL_PO            | moduleLogPLV_06292023_2 | 2022-02-01 |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_06292023_1 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803098505483 | bp_moduleLogPO           | true                | true                |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |


  @Id:S0282_100
  @Id:S0298_700
  @from:cucumber
  Scenario: Happy flow - purchase order -> material receipt schedule
  - purchase order created with two lines with modular contract terms
  - complete PO
  - validate modular contract is created for the line
  - validate Log Entry is created for the line
  - validate Log Entry is created for the purchase modular contract
  - validate no invoice candidate is created
  - validate material receipt schedule is created and modular contract id is propagated

    Given metasfresh contains M_Products:
      | Identifier            | Name                                  |
      | module_log_product_PO | module_log_product_PO_test_06292023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_06292023_2 | module_log_product_PO   | harvesting_calendar      | year                 | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | ModularContractHandlerType |
      | modCntr_type_1             | poLine_06292023_2 | poLine_06292023_2 | Receipt                    |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_06292023_2 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO | moduleLogConditions_po_06292023_2 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_4 | module_log_product_PO       | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_06292023_2 |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |

    When the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO              | module_log_product_PO   | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO              | bp_moduleLogPO              | module_log_product_PO   | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | cp_bp                    | 2155894           |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Description                                                                                                                                          |
      | log_1                     | po_orderLine         | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       | Eine Bestellung für das Produkt module_log_product_PO_test_06292023_2_module_log_product_PO_test_06292023_2 mit der Menge 1000 Stk wurde fertiggestellt. |
      | poLog_1                   | moduleLogContract_1  | ModularContract | invoicingGroup                           | cp_bp                                      | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseModularContract      | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       | Modularer Vertrag für Produkt module_log_product_PO_test_06292023_2_module_log_product_PO_test_06292023_2 mit der Menge 1000 Stk wurde fertiggestellt.   |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus |
      | po_orderLine         | C_OrderLine     | SP               |
      | moduleLogContract_1  | C_Flatrate_Term | SP               |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_PO              | po_order              | po_orderLine              | bp_moduleLogPO           | bp_moduleLogPO_Location           | module_log_product_PO   | 1000       | warehouseStd              | moduleLogContract_1               |

    And recompute modular logs for record:
      | TableName       | Record_ID.Identifier |
      | C_Order         | po_order             |
      | C_Flatrate_Term | moduleLogContract_1  |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | po_orderLine         | C_OrderLine     | SP               | 2                   |
      | moduleLogContract_1  | C_Flatrate_Term | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine         | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              |
      | poLog_1                   | moduleLogContract_1  | ModularContract | invoicingGroup                           | cp_bp                                      | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseModularContract      | EUR                        | PCE                   | 2000       | year                              |


  @Id:S0282_200
  @from:cucumber
  Scenario: VOID purchase order with modular contract and without linked documents
  - purchase order created with two lines with modular contract terms
  - complete PO
  - validate two modular contracts are created, one for each line
  - validate two Log Entries are created
  - validate no invoice candidate is created
  - validate material receipt schedule is created and modular contract id is propagated
  - `VOID` purchase order
  - validate modular contracts are voided
  - validate another two Log Entries are added with negated quantity
  - validate no material receipt schedule is present for PO

    Given metasfresh contains M_Products:
      | Identifier            | Name                                  |
      | module_log_product_PO | module_log_product_PO_test_06292023_4 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_06292023_4 | module_log_product_PO   | harvesting_calendar      | year                 | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | ModularContractHandlerType |
      | modCntr_type_1             | poLine_06292023_4 | poLine_06292023_4 | PurchaseOrderLine_Modular  |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_06292023_4 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO | moduleLogConditions_po_06292023_4 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_5 | module_log_product_PO       | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_06292023_4 |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO              | module_log_product_PO   | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO              | bp_moduleLogPO              | module_log_product_PO   | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine         | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus |
      | po_orderLine         | C_OrderLine | SP               |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_PO              | po_order              | po_orderLine              | bp_moduleLogPO           | bp_moduleLogPO_Location           | module_log_product_PO   | 1000       | warehouseStd              | moduleLogContract_1               |

    When the order identified by po_order is voided

    Then validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO              | bp_moduleLogPO              | module_log_product_PO   | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            | moduleLogPricingSystem            | ModularContract     | Vo                 | CL            |


    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty   | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Description                                                                                                                                          |
      | log_1                     | po_orderLine         | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000  | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       | Eine Bestellung für das Produkt module_log_product_PO_test_06292023_4_module_log_product_PO_test_06292023_4 mit der Menge 1000 Stk wurde fertiggestellt. |
      | log_2                     | po_orderLine         | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | -1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | -2000      | year                              | modCntr_module_1                 | 2.00            | PCE                       | Eine Bestellung für das Produkt module_log_product_PO_test_06292023_4_module_log_product_PO_test_06292023_4 mit der Menge 1000 Stk wurde fertiggestellt. |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | po_orderLine         | C_OrderLine | SP               | 2                   |

    And there is no M_ShipmentSchedule for C_Order po_order

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | C_Order   | po_order             |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | po_orderLine         | C_OrderLine | SP               | 3                   |
    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName   |
      | po_orderLine         | C_OrderLine |


  @Id:S0282_300
  @from:cucumber
  Scenario: REACTIVATE | REVERSE purchase order with linked modular contract
  - purchase order created with one lines with modular contract terms
  - complete PO
  - validate two modular contracts are created, one for each line
  - validate two Log Entries are created
  - `REACTIVATE` purchase order
  - validate `ReactivateNotAllowed` error is thrown
  - `REVERSE` purchase order
  - validate `ReactivateNotAllowed` error is thrown

    Given metasfresh contains M_Products:
      | Identifier            | Name                                  |
      | module_log_product_PO | module_log_product_PO_test_07032023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_07032023_2 | module_log_product_PO   | harvesting_calendar      | year                 | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | ModularContractHandlerType |
      | modCntr_type_1             | poLine_07032023_2 | poLine_07032023_2 | PurchaseOrderLine_Modular  |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_07032023_2 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO | moduleLogConditions_po_07032023_2 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_6 | module_log_product_PO       | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_07032023_2 |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO              | module_log_product_PO   | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO              | bp_moduleLogPO              | module_log_product_PO   | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine         | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus |
      | po_orderLine         | C_OrderLine | SP               |

    And load AD_Message:
      | Identifier             | Value                                                |
      | reactivate_not_allowed | de.metas.contracts.modular.impl.ReactivateNotAllowed |

    And the order identified by po_order is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | reactivate_not_allowed       |

    And the order identified by po_order is reversed expecting error
      | OPT.AD_Message_ID.Identifier |
      | reactivate_not_allowed       |

  @Id:S0282_400
  @from:cucumber
  Scenario: Contract module log - purchase order -> material receipt
  validate receipt schedule flatrate term is propagated from po
  validate receiptline flatrate term is propagated from receipt schedule
  validate on receipt complete, reactivate and reverse logs are created correctly
  validate on receipt void error is thrown
  validate on receiptLine delete error is thrown if modular contract log exist for this line
  validate on `VOID` purchase order with material receipt created -> `VoidNotAllowed` error is thrown
  validate on second order with same partner, product, condition and overlapping time period error is thrown

    Given metasfresh contains M_Products:
      | Identifier            | Name                             |
      | module_log_product_PO | module_log_product_PO_05072023_1 |
      | module_log_product_MR | module_log_product_MR_05072023_1 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP  | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |
      | moduleLogPP2 | moduleLogPLV                      | module_log_product_MR   | 2.00     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPOProduct                    | huPiItemTU                 | module_log_product_PO   | 10  | 2022-02-01 |
      | huItemMRProduct                    | huPiItemTU                 | module_log_product_MR   | 10  | 2022-02-01 |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogMR | bp_moduleLogMR_05072023_1 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogMR_Location | 5823098505483 | bp_moduleLogMR           | true                | true                |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_05072023_1 | module_log_product_MR   | harvesting_calendar      | year                 | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | ModularContractHandlerType  |
      | modCntr_type_1             | poLine_05072023_1 | poLine_05072023_1 | PurchaseOrderLine_Modular   |
      | modCntr_type_2             | mrLine_05072023_1 | mrLine_05072023_1 | MaterialReceiptLine_Modular |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_05072023_1 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | moduleTest_05072023_2 | module_log_product_MR   | Kosten         | modCntr_settings_1             | modCntr_type_2             |
      | modCntr_module_3             | 30    | moduleTest_05072023_3 | module_log_product_MR   | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_4             | 40    | moduleTest_05072023_4 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_2             |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_MR | moduleLogConditions_po_05072023_1 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_6 | module_log_product_PO       | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO   |
      | invoicingGroup_p2                            | invoicingGroup                       | module_log_product_MR   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogMR           | 2022-03-03  | POO             | mrModuleLogContract_ref_05072023_1 |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine_1 | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_MR                  |
      | po_orderLine_2 | po_order              | module_log_product_MR   | 500        | moduleLogConditions_MR                  |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_MR              | module_log_product_PO   | po_order                       | po_orderLine_1                     |
      | moduleLogContract_2           | moduleLogConditions_MR              | module_log_product_MR   | po_order                       | po_orderLine_2                     |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_MR              | bp_moduleLogMR              | module_log_product_PO   | po_orderLine_1                     | po_order                       | PCE                   | 1000                  | 2.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |
      | moduleLogContract_2           | moduleLogConditions_MR              | bp_moduleLogMR              | module_log_product_MR   | po_orderLine_2                     | po_order                       | PCE                   | 500                   | 2.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              | modCntr_module_3                 | 2.00            | PCE                       |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 120s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_05072023_1      | po_order              | po_orderLine_1            | bp_moduleLogMR           | bp_moduleLogMR_Location           | module_log_product_PO   | 1000       | warehouseStd              | moduleLogContract_1               |
      | receiptSchedule_05072023_2      | po_order              | po_orderLine_2            | bp_moduleLogMR           | bp_moduleLogMR_Location           | module_log_product_MR   | 500        | warehouseStd              | moduleLogContract_2               |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_1                        | hu_1               | receiptSchedule_05072023_1      | N               | 1     | N               | 1     | N               | 1000        | huItemPOProduct                    | huPackingLU                  |
      | huLuTuConfig_2                        | hu_2               | receiptSchedule_05072023_2      | N               | 1     | N               | 1     | N               | 500         | huItemMRProduct                    | huPackingLU                  |
    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_1               | receiptSchedule_05072023_1      | material_receipt_1    |
      | hu_2               | receiptSchedule_05072023_2      | material_receipt_2    |

    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | shipmentLine_1            | material_receipt_1    | module_log_product_PO   | 1000        | true      | po_orderLine_1                | moduleLogContract_1               |
      | shipmentLine_2            | material_receipt_2    | module_log_product_MR   | 500         | true      | po_orderLine_2                | moduleLogContract_2               |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              | modCntr_module_3                 | 2.00            | PCE                       |
      | log_3                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |
      | log_4                     | shipmentLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500  | M_InOutLine | moduleLogContract_2           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_2                 | null            | null                      |

    When the material receipt identified by material_receipt_1 is reactivated

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty   | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500   | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              | modCntr_module_3                 | 2.00            | PCE                       |
      | log_3                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |
      | log_4                     | shipmentLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500   | M_InOutLine | moduleLogContract_2           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_2                 | null            | null                      |
      | log_5                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | -1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty   | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500   | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              | modCntr_module_3                 | 2.00            | PCE                       |
      | log_3                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |
      | log_4                     | shipmentLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500   | M_InOutLine | moduleLogContract_2           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_2                 | null            | null                      |
      | log_5                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | -1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |
      | log_6                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |

    When the material receipt identified by material_receipt_2 is reversed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty   | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500   | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              | modCntr_module_3                 | 2.00            | PCE                       |
      | log_3                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | 1000  | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |
      | log_4                     | shipmentLine_2       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_MR   | bp_moduleLogMR                      | bp_moduleLogMR                  | 500   | M_InOutLine | moduleLogContract_2           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_2                 | null            | null                      |
      | log_5                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | -1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |
      | log_6                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_moduleLogMR                             | warehouseStd                  | module_log_product_PO   | bp_moduleLogMR                      | bp_moduleLogMR                  | -1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_2                 | false         | MaterialReceipt              |                            | PCE                   |            | year                              | modCntr_module_4                 | null            | null                      |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | po_orderLine_1       | C_OrderLine | SP               |                     |
      | po_orderLine_2       | C_OrderLine | SP               |                     |
      | shipmentLine_1       | M_InOutLine | SP               | 2                   |
      | shipmentLine_2       | M_InOutLine | SP               | 2                   |

    When load AD_Message:
      | Identifier         | Value                                                      |
      | delete_not_allowed | documentLineDeletionErrorBecauseOfRelatedModuleContractLog |

    Then delete M_InOutLine identified by shipmentLine_1 is expecting error
      | OPT.AD_Message_ID.Identifier |
      | delete_not_allowed           |

    And load AD_Message:
      | Identifier       | Value                                  |
      | void_not_allowed | de.metas.contracts.DocActionNotAllowed |

    And the material receipt identified by material_receipt_1 is voided expecting error
      | OPT.AD_Message_ID.Identifier |
      | void_not_allowed             |

    And load AD_Message:
      | Identifier          | Value                                                                                  |
      | po_void_not_allowed | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed |

    And the order identified by po_order is voided expecting error
      | OPT.AD_Message_ID.Identifier | OPT.M_InOut_ID.Identifier |
      | po_void_not_allowed          | material_receipt_1        |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order_2 | false   | bp_moduleLogMR           | 2022-03-03  | POO             | mrModuleLogContract_ref_05072023_2 |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine_1 | po_order_2            | module_log_product_PO   | 1000       | moduleLogConditions_MR                  |

    Then the order identified by po_order_2 is completed and an exception with error-code de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm is thrown

