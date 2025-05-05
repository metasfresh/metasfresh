@ghActions:run_on_executor5
@ignore
Feature: Modular contract log - Recompute

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]

  @S0319
  @from:cucumber
  Scenario: Try to recompute an already processed log -> expect error message
    Given metasfresh contains M_PricingSystems
      | Identifier                              | Name                                    | Value                                   |
      | moduleLogPricingSystem_recompute_150923 | moduleLogPricingSystem_recompute_150923 | moduleLogPricingSystem_recompute_150923 |
    And metasfresh contains M_Products:
      | Identifier                  | Name                        |
      | module_log_recompute_150923 | module_log_recompute_150923 |
    And metasfresh contains C_BPartners:
      | Identifier                      | Name                            | OPT.IsVendor | M_PricingSystem_ID.Identifier           | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO_recompute_150923 | bp_moduleLogPO_recompute_150923 | Y            | moduleLogPricingSystem_recompute_150923 | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier        | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5823198505483 | bp_moduleLogPO_recompute_150923 | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier  | Value                      | Name                       | OPT.C_BPartner_ID.Identifier    | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_recompute_150923 | warehouse_recompute_150923 | warehouse_recompute_150923 | bp_moduleLogPO_recompute_150923 | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier  |
      | locator                 | locator_12012023_1 | warehouse_recompute_150923 |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier    | Name                    | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier       |
      | modCntr_settings_recompute_150923 | testSettings_07042023_0 | module_log_recompute_150923 | harvesting_calendar      | year                 | moduleLogPricingSystem_recompute_150923 |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier      | Name               | Value              | ModularContractHandlerType |
      | modCntr_type_recompute_150923_1 | invLine_07042023_1 | invLine_07042023_1 | InventoryLine_Modular      |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier      | SeqNo | Name                              | M_Product_ID.Identifier     | InvoicingGroup | ModCntr_Settings_ID.Identifier    | ModCntr_Type_ID.Identifier      |
      | modCntr_module_recompute_150923_1 | 10    | modCntr_module_recompute_150923_1 | module_log_recompute_150923 | Kosten         | modCntr_settings_recompute_150923 | modCntr_type_recompute_150923_1 |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                              | Name                                    | Type_Conditions | OPT.M_PricingSystem_ID.Identifier       | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_PO_recompute_150923 | moduleLogConditions_PO_recompute_150923 | ModularContract | moduleLogPricingSystem_recompute_150923 | Ex                       | modCntr_settings_recompute_150923  | DR            |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier                           | C_Flatrate_Conditions_ID.Identifier     | Bill_BPartner_ID.Identifier     | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_recompute_150923_1 | moduleLogConditions_PO_recompute_150923 | bp_moduleLogPO_recompute_150923 | 2021-10-31 | 2022-10-30 | module_log_recompute_150923 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09262023_7 | module_log_recompute_150923 | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier     |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_recompute_150923 |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID             | MovementDate |
      | i_1        | warehouse_recompute_150923 | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier     | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier |
      | il_1       | i_1                       | module_log_recompute_150923 | PCE          | 10       | 0       | moduleLogContract_recompute_150923_1    |

    When the inventory identified by i_1 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.M_Warehouse_ID.Identifier | ContractType    | M_Product_ID.Identifier     | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier        | OPT.ModCntr_Type_ID.Identifier  | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_1                     | il_1                 | invoicingGroup                           | warehouse_recompute_150923    | ModularContract | module_log_recompute_150923 | bp_moduleLogPO_recompute_150923 | 10  | M_InventoryLine | moduleLogContract_recompute_150923_1 | modCntr_type_recompute_150923_1 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus |
      | il_1                 | M_InventoryLine | SP               |

    And update ModCntr_Logs:
      | ModCntr_Log_ID.Identifier | OPT.Processed |
      | log_1                     | true          |

    And recompute modular log for record expecting error
      | TableName   | Record_ID.Identifier | ErrorMessage                                     |
      | M_Inventory | i_1                  | de.metas.contracts.modular.PROCESSED_LOGS_EXISTS |