@ghActions:run_on_executor5
@ignore
Feature: Modular contract log from inventory

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @Id:S0284_100
  @Id:S0282_500
  @from:cucumber
  Scenario: Happy flow for contract module log - inventory with two lines completed => validate the two created log entries
    Given metasfresh contains M_PricingSystems
      | Identifier                       | Name                             | Value                            |
      | moduleLogPricingSystem_S0282_500 | moduleLogPricingSystem_S0282_500 | moduleLogPricingSystem_S0282_500 |
    And metasfresh contains M_Products:
      | Identifier           | Name                 |
      | module_log_S0282_500 | module_log_S0282_500 |
    And metasfresh contains C_BPartners:
      | Identifier               | Name                     | OPT.IsVendor | M_PricingSystem_ID.Identifier    | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO_S0282_500 | bp_moduleLogPO_S0282_500 | Y            | moduleLogPricingSystem_S0282_500 | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5823198505483 | bp_moduleLogPO_S0282_500 | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value               | Name                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_S0282_500       | warehouse_S0282_500 | warehouse_S0282_500 | bp_moduleLogPO_S0282_500     | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_12012023_1 | warehouse_S0282_500       |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_S0282_500     | testSettings_07042023_0 | module_log_S0282_500        | harvesting_calendar      | year                 | moduleLogPricingSystem_S0282_500  |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name               | Value              | ModularContractHandlerType |
      | modCntr_type_S0282_500_1   | invLine_07042023_1 | invLine_07042023_1 | InventoryLine_Modular      |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                       | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_S0282_500_1   | 10    | modCntr_module_S0282_500_1 | module_log_S0282_500    | Kosten         | modCntr_settings_S0282_500     | modCntr_type_S0282_500_1   |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                       | Name                             | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_PO_S0282_500 | moduleLogConditions_PO_S0282_500 | ModularContract | moduleLogPricingSystem_S0282_500  | Ex                       | modCntr_settings_S0282_500         | DR            |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier                    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_S0282_500_1 | moduleLogConditions_PO_S0282_500    | bp_moduleLogPO_S0282_500    | 2021-10-31 | 2022-10-30 | module_log_S0282_500        |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_1 | module_log_S0282_500        | 2021-04-14 | 2022-02-25 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_S0282_500    |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID      | MovementDate |
      | i_1        | warehouse_S0282_500 | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier |
      | il_1       | i_1                       | module_log_S0282_500    | PCE          | 10       | 0       | moduleLogContract_S0282_500_1           |
      | il_2       | i_1                       | module_log_S0282_500    | PCE          | 10       | 0       | moduleLogContract_S0282_500_1           |

    When the inventory identified by i_1 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_500                   | warehouse_S0282_500           | ModularContract | module_log_S0282_500    | bp_moduleLogPO_S0282_500        | 10  | M_InventoryLine | moduleLogContract_S0282_500_1 | modCntr_type_S0282_500_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_500_1       | null            | null                      |
      | log_2                     | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_500                   | warehouse_S0282_500           | ModularContract | module_log_S0282_500    | bp_moduleLogPO_S0282_500        | 10  | M_InventoryLine | moduleLogContract_S0282_500_1 | modCntr_type_S0282_500_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_500_1       | null            | null                      |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus |
      | il_1                 | M_InventoryLine | SP               |
      | il_2                 | M_InventoryLine | SP               |
    And recompute modular logs for record:
      | TableName   | Record_ID.Identifier |
      | M_Inventory | i_1                  |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | il_1                 | M_InventoryLine | SP               | 2                   |
      | il_2                 | M_InventoryLine | SP               | 2                   |
    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_3                     | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_500                   | warehouse_S0282_500           | ModularContract | module_log_S0282_500    | bp_moduleLogPO_S0282_500        | 10  | M_InventoryLine | moduleLogContract_S0282_500_1 | modCntr_type_S0282_500_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_4                     | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_500                   | warehouse_S0282_500           | ModularContract | module_log_S0282_500    | bp_moduleLogPO_S0282_500        | 10  | M_InventoryLine | moduleLogContract_S0282_500_1 | modCntr_type_S0282_500_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |

  @Id:S0284_200
  @Id:S0282_600
  @from:cucumber
  Scenario: Happy flow for contract module log - internal use with two lines completed => validate the two created log entries
    Given metasfresh contains M_PricingSystems
      | Identifier                       | Name                             | Value                            |
      | moduleLogPricingSystem_S0282_600 | moduleLogPricingSystem_S0282_600 | moduleLogPricingSystem_S0282_600 |
    And metasfresh contains M_Products:
      | Identifier           | Name                 |
      | module_log_S0282_600 | module_log_S0282_600 |
    And metasfresh contains C_BPartners:
      | Identifier               | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier    | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO_S0282_600 | bp_moduleLogPO_07052023_1 | Y            | moduleLogPricingSystem_S0282_600 | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5823298505483 | bp_moduleLogPO_S0282_600 | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value               | Name                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_S0282_600       | warehouse_S0282_600 | warehouse_S0282_600 | bp_moduleLogPO_S0282_600     | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_12012023_1 | warehouse_S0282_600       |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                         | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_S0282_600_1   | modCntr_settings_S0282_600_1 | module_log_S0282_600        | harvesting_calendar      | year                 | moduleLogPricingSystem_S0282_600  |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name               | Value              | ModularContractHandlerType |
      | modCntr_type_S0282_600_1   | poLine_S0282_600_1 | poLine_S0282_600_1 | InventoryLine_Modular      |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                       | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_S0282_600_1   | 10    | modCntr_module_S0282_600_1 | module_log_S0282_600    | Kosten         | modCntr_settings_S0282_600_1   | modCntr_type_S0282_600_1   |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                       | Name                             | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_PO_S0282_600 | moduleLogConditions_PO_S0282_600 | ModularContract | moduleLogPricingSystem_S0282_600  | Ex                       | modCntr_settings_S0282_600_1       | DR            |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier                    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_S0282_600_1 | moduleLogConditions_PO_S0282_600    | bp_moduleLogPO_S0282_600    | 2021-10-31 | 2022-10-30 | module_log_S0282_600        |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | disposal                | MMI             | IUI            |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_2 | module_log_S0282_600        | 2021-04-14 | 2022-02-25 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_S0282_600    |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID      | MovementDate | OPT.C_DocType_ID.Identifier |
      | i_1        | warehouse_S0282_600 | 2021-04-16   | disposal                    |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier | OPT.QtyInternalUse |
      | il_1       | i_1                       | module_log_S0282_600    | PCE          | 0        | 0       | moduleLogContract_S0282_600_1           | 10                 |
      | il_2       | i_1                       | module_log_S0282_600    | PCE          | 0        | 0       | moduleLogContract_S0282_600_1           | 10                 |

    When the inventory identified by i_1 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_S0282_600_1           | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_600                   | warehouse_S0282_600           | ModularContract | module_log_S0282_600    | bp_moduleLogPO_S0282_600        | -10 | M_InventoryLine | moduleLogContract_S0282_600_1 | modCntr_type_S0282_600_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_600_1       | null            | null                      |
      | log_S0282_600_2           | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_600                   | warehouse_S0282_600           | ModularContract | module_log_S0282_600    | bp_moduleLogPO_S0282_600        | -10 | M_InventoryLine | moduleLogContract_S0282_600_1 | modCntr_type_S0282_600_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_600_1       | null            | null                      |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus |
      | il_1                 | M_InventoryLine | SP               |
      | il_2                 | M_InventoryLine | SP               |
    And recompute modular logs for record:
      | TableName   | Record_ID.Identifier |
      | M_Inventory | i_1                  |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | il_1                 | M_InventoryLine | SP               | 2                   |
      | il_2                 | M_InventoryLine | SP               | 2                   |
    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_S0282_600_3           | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_600                   | warehouse_S0282_600           | ModularContract | module_log_S0282_600    | bp_moduleLogPO_S0282_600        | -10 | M_InventoryLine | moduleLogContract_S0282_600_1 | modCntr_type_S0282_600_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_S0282_600_4           | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_600                   | warehouse_S0282_600           | ModularContract | module_log_S0282_600    | bp_moduleLogPO_S0282_600        | -10 | M_InventoryLine | moduleLogContract_S0282_600_1 | modCntr_type_S0282_600_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |


  @Id:S0284_300
  @Id:S0282_700
  @from:cucumber
  Scenario: Happy flow for contract module log - inventory with two lines reversed
    Given metasfresh contains M_PricingSystems
      | Identifier                       | Name                             | Value                            |
      | moduleLogPricingSystem_S0282_700 | moduleLogPricingSystem_S0282_700 | moduleLogPricingSystem_S0282_700 |
    And metasfresh contains M_Products:
      | Identifier           | Name                 |
      | module_log_S0282_700 | module_log_S0282_700 |
    And metasfresh contains C_BPartners:
      | Identifier               | Name                     | OPT.IsVendor | M_PricingSystem_ID.Identifier    | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO_S0282_700 | bp_moduleLogPO_S0282_700 | Y            | moduleLogPricingSystem_S0282_700 | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5824298505483 | bp_moduleLogPO_S0282_700 | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value               | Name                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_S0282_700       | warehouse_S0282_700 | warehouse_S0282_700 | bp_moduleLogPO_S0282_700     | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_12012023_1 | warehouse_S0282_700       |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                     | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_S0282_700_1   | testSettings_S0282_700_1 | module_log_S0282_700        | harvesting_calendar      | year                 | moduleLogPricingSystem_S0282_700  |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | ModularContractHandlerType |
      | modCntr_type_S0282_700_1   | poLine_07052023_2 | poLine_07052023_2 | InventoryLine_Modular      |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_S0282_700_1   | 10    | moduleTest_07052023_2 | module_log_S0282_700    | Kosten         | modCntr_settings_S0282_700_1   | modCntr_type_S0282_700_1   |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_PO | moduleLogConditions_po_07052023_2 | ModularContract | moduleLogPricingSystem_S0282_700  | Ex                       | modCntr_settings_S0282_700_1       | DR            |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier                    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_S0282_700_1 | moduleLogConditions_PO              | bp_moduleLogPO_S0282_700    | 2021-10-31 | 2022-10-30 | module_log_S0282_700        |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_3 | module_log_S0282_700        | 2021-04-14 | 2022-02-25 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_S0282_700    |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID      | MovementDate |
      | i_1        | warehouse_S0282_700 | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier |
      | il_1       | i_1                       | module_log_S0282_700    | PCE          | 10       | 0       | moduleLogContract_S0282_700_1           |
      | il_2       | i_1                       | module_log_S0282_700    | PCE          | 10       | 0       | moduleLogContract_S0282_700_1           |

    When the inventory identified by i_1 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_700                   | warehouse_S0282_700           | ModularContract | module_log_S0282_700    | bp_moduleLogPO_S0282_700        | 10  | M_InventoryLine | moduleLogContract_S0282_700_1 | modCntr_type_S0282_700_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_700_1       | null            | null                      |
      | log_2                     | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_700                   | warehouse_S0282_700           | ModularContract | module_log_S0282_700    | bp_moduleLogPO_S0282_700        | 10  | M_InventoryLine | moduleLogContract_S0282_700_1 | modCntr_type_S0282_700_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_700_1       | null            | null                      |

    And the inventory identified by i_1 is reversed

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_700                   | warehouse_S0282_700           | ModularContract | module_log_S0282_700    | bp_moduleLogPO_S0282_700        | 10  | M_InventoryLine | moduleLogContract_S0282_700_1 | modCntr_type_S0282_700_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_700_1       | null            | null                      |
      | log_2                     | il_1                 | invoicingGroup                           | bp_moduleLogPO_S0282_700                   | warehouse_S0282_700           | ModularContract | module_log_S0282_700    | bp_moduleLogPO_S0282_700        | -10 | M_InventoryLine | moduleLogContract_S0282_700_1 | modCntr_type_S0282_700_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_700_1       | null            | null                      |
      | log_3                     | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_700                   | warehouse_S0282_700           | ModularContract | module_log_S0282_700    | bp_moduleLogPO_S0282_700        | 10  | M_InventoryLine | moduleLogContract_S0282_700_1 | modCntr_type_S0282_700_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_700_1       | null            | null                      |
      | log_4                     | il_2                 | invoicingGroup                           | bp_moduleLogPO_S0282_700                   | warehouse_S0282_700           | ModularContract | module_log_S0282_700    | bp_moduleLogPO_S0282_700        | -10 | M_InventoryLine | moduleLogContract_S0282_700_1 | modCntr_type_S0282_700_1       | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. | modCntr_module_S0282_700_1       | null            | null                      |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | il_1                 | M_InventoryLine | SP               | 2                   |
      | il_2                 | M_InventoryLine | SP               | 2                   |
    And recompute modular logs for record:
      | TableName   | Record_ID.Identifier |
      | M_Inventory | i_1                  |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | il_1                 | M_InventoryLine | SP               | 3                   |
      | il_2                 | M_InventoryLine | SP               | 3                   |
    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName       |
      | il_1                 | M_InventoryLine |
      | il_2                 | M_InventoryLine |