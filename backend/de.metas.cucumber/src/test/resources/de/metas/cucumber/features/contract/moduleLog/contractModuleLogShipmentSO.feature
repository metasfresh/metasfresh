@ignore
@ghActions:run_on_executor5
Feature: Modular contract log from shipment (SO)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                         | Value                        |
      | moduleLogPricingSystem | moduleLogPricingSystem_S0321 | moduleLogPricingSystem_S0321 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_07262023 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_07262023 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_07262023_PO | 2022-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_07262023_SO | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_S0321 | Y            | N              | moduleLogPricingSystem        | 1000002                    |
      | bp_moduleLogSO | bp_moduleLogSO_S0321 | N            | Y              | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 0726202312346 | bp_moduleLogPO           | true                | true                |
      | bp_moduleLogSO_Location | 0726202312345 | bp_moduleLogSO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value           | Name            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_S0321 | warehouse_S0321 | bp_moduleLogPO               | bp_moduleLogPO_Location               |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value         | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_S0321 | warehouseModularContract  |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier   | Name                         | Value                        | ModularContractHandlerType |
      | modCntr_type_PO_S0321        | modCntr_type_PO_S0321        | modCntr_type_PO_S0321        | PurchaseOrderLine_Modular  |
      | modCntr_type_SO_for_PO_S0321 | modCntr_type_SO_for_PO_S0321 | modCntr_type_SO_for_PO_S0321 | SOLineForPO_Modular        |
      | modCntr_type_SO_S0321        | modCntr_type_SO_S0321        | modCntr_type_SO_S0321        | SalesOrderLine_Modular     |
      | modCntr_type_MC_SO_S0321     | modCntr_type_MC_SO_S0321     | modCntr_type_MC_SO_S0321     | SalesModularContract       |
      | modCntr_type_MC_PO_S0321     | modCntr_type_MC_PO_S0321     | modCntr_type_MC_PO_S0321     | PurchaseModularContract    |
      | modCntr_type_PO_SHIP_S0321   | modCntr_type_PO_SHIP_S0303   | modCntr_type_PO_SHIP_S0303   | ShipmentLineForPO_Modular  |
      | modCntr_type_SO_SHIP_S0321   | modCntr_type_SO_SHIP_S0303   | modCntr_type_SO_SHIP_S0303   | ShipmentLineForSO_Modular  |

  @Id:S0321_100
  @from:cucumber
  Scenario: When a shipment based on a sales order line which generated modular contract is completed
  -> modular contract log is created for each shipment line

    Given metasfresh contains M_Products:
      | Identifier                 | Name                       |
      | modularContract_prod_S0321 | modularContract_prod_S0321 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0321 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0321 | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                        | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.IsSOTrx |
      | modCntr_ship_settings_S0321    | modCntr_ship_settings_S0321 | modularContract_prod_S0321  | harvesting_calendar      | year_2023            | moduleLogPricingSystem            | true        |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier   | SeqNo | Name    | M_Product_ID.Identifier    | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier   |
      | modCntr_module_PO_S0321        | 10    | name_10 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_PO_S0321        |
      | modCntr_module_SO_for_PO_S0321 | 20    | name_20 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_SO_for_PO_S0321 |
      | modCntr_module_MC_PO_S0321     | 30    | name_30 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_MC_PO_S0321     |
      | modCntr_module_SO_S0321        | 40    | name_40 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_SO_S0321        |
      | modCntr_module_MC_S0321        | 50    | name_50 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_MC_SO_S0321     |
      | modCntr_module_PO_SHIP_S0321   | 60    | name_60 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_PO_SHIP_S0321   |
      | modCntr_module_SO_SHIP_S0321   | 70    | name_70 | modularContract_prod_S0321 | Kosten         | modCntr_ship_settings_S0321    | modCntr_type_SO_SHIP_S0321   |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                 | Name                       | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_S0321 | modularContractTerms_S0321 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_ship_settings_S0321        |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_8 | modularContract_prod_S0321  | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier    |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod_S0321 |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order_S0321 | true    | bp_moduleLogSO           | 2022-03-03  | so_order_S0321  | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier    | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine_1_S0321 | so_order_S0321        | modularContract_prod_S0321 | 8          | modularContractTerms_S0321              |

    When the order identified by so_order_S0321 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier    | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO_S0321    | modularContractTerms_S0321          | modularContract_prod_S0321 | so_order_S0321                 | soLine_1_S0321                     |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier    | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_SO_S0321    | modularContractTerms_S0321          | bp_moduleLogSO              | modularContract_prod_S0321 | soLine_1_S0321                     | so_order_S0321                 | PCE                   | 8                     | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0321 | soLine_1_S0321            | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0321                      | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0321                      | s_1_S0321             |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier    | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_1_S0321               | s_1_S0321             | modularContract_prod_S0321 | 8           | false     | soLine_1_S0321                |

    When the shipment identified by s_1_S0321 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | shipLog_1_S0321           | s_l_1_S0321          | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0321 | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_InOutLine | moduleLogContract_SO_S0321    | modCntr_type_SO_SHIP_S0321     | false         | Shipment                     | year_2023                         | true        | modCntr_module_SO_SHIP_S0321     | null            | null                      |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus |
      | soLine_1_S0321       | C_OrderLine | SP               |
      | s_l_1_S0321          | M_InOutLine | SP               |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | M_InOut   | s_1_S0321            |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | s_l_1_S0321          | M_InOutLine | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | shipLog_1_S0321           | s_l_1_S0321          | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0321 | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_InOutLine | moduleLogContract_SO_S0321    | modCntr_type_SO_SHIP_S0321     | false         | Shipment                     | year_2023                         | true        |

  @Id:S0321_200
  @from:cucumber
  Scenario: When a shipment has any ongoing sales modular contracts linked to it and the shipment is reversed
  -> a modular contract log should be added for each suitable shipment line, with the negated qty

    Given metasfresh contains M_Products:
      | Identifier                     | Name                           |
      | modularContract_prod_S0321_200 | modularContract_prod_S0321_200 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier        | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0321_200 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0321_200 | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier  | Name                            | M_Raw_Product_ID.Identifier    | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.IsSOTrx |
      | modCntr_ship_settings_S0321_200 | modCntr_ship_settings_S0321_200 | modularContract_prod_S0321_200 | harvesting_calendar      | year_2023            | moduleLogPricingSystem            | true        |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier       | SeqNo | Name    | M_Product_ID.Identifier        | InvoicingGroup | ModCntr_Settings_ID.Identifier  | ModCntr_Type_ID.Identifier   |
      | modCntr_module_PO_S0321_200        | 10    | name_10 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_PO_S0321        |
      | modCntr_module_SO_for_PO_S0321_200 | 20    | name_20 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_SO_for_PO_S0321 |
      | modCntr_module_MC_PO_S0321_200     | 30    | name_30 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_MC_PO_S0321     |
      | modCntr_module_SO_S0321_200        | 40    | name_40 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_SO_S0321        |
      | modCntr_module_MC_S0321_200        | 50    | name_50 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_MC_SO_S0321     |
      | modCntr_module_PO_SHIP_S0321_200   | 60    | name_60 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_PO_SHIP_S0321   |
      | modCntr_module_SO_SHIP_S0321_200   | 70    | name_70 | modularContract_prod_S0321_200 | Kosten         | modCntr_ship_settings_S0321_200 | modCntr_type_SO_SHIP_S0321   |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                     | Name                           | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_S0321_200 | modularContractTerms_S0321_200 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_ship_settings_S0321_200    |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier    | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_9 | modularContract_prod_S0321_200 | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier        |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod_S0321_200 |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference    | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order_S0321_200 | true    | bp_moduleLogSO           | 2022-03-03  | so_order_S0321_200 | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine_1_S0321_200 | so_order_S0321_200    | modularContract_prod_S0321_200 | 8          | modularContractTerms_S0321_200          |

    When the order identified by so_order_S0321_200 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier  | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier        | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO_S0321_200 | modularContractTerms_S0321_200      | modularContract_prod_S0321_200 | so_order_S0321_200             | soLine_1_S0321_200                 |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier        | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_SO_S0321_200 | modularContractTerms_S0321_200      | bp_moduleLogSO              | modularContract_prod_S0321_200 | soLine_1_S0321_200                 | so_order_S0321_200             | PCE                   | 8                     | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0321_200 | soLine_1_S0321_200        | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0321_200                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0321_200                  | s_1_S0321_200         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_1_S0321_200           | s_1_S0321_200         | modularContract_prod_S0321_200 | 8           | false     | soLine_1_S0321_200            |

    When the shipment identified by s_1_S0321_200 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier  | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Description                                                                                                                    |
      | shipLog_1_S0321_200       | s_l_1_S0321_200      | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0321_200 | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_InOutLine | moduleLogContract_SO_S0321_200 | modCntr_type_SO_SHIP_S0321     | false         | Shipment                     | year_2023                         | true        | modCntr_module_SO_SHIP_S0321_200 | null            | null                      | Eine Lieferung für Produkt modularContract_prod_S0321_200_modularContract_prod_S0321_200 mit der Menge 8 Stk wurde fertiggestellt. |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus |
      | soLine_1_S0321_200   | C_OrderLine | SP               |
      | s_l_1_S0321_200      | M_InOutLine | SP               |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | M_InOut   | s_1_S0321_200        |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | s_l_1_S0321_200      | M_InOutLine | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier  | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.Description                                                                                                                    |
      | shipLog_1_S0321_200       | s_l_1_S0321_200      | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0321_200 | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_InOutLine | moduleLogContract_SO_S0321_200 | modCntr_type_SO_SHIP_S0321     | false         | Shipment                     | year_2023                         | true        | Eine Lieferung für Produkt modularContract_prod_S0321_200_modularContract_prod_S0321_200 mit der Menge 8 Stk wurde fertiggestellt. |

    When the shipment identified by s_1_S0321_200 is reversed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier  | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.Description                                                                                                               |
      | shipLog_1_S0321_200       | s_l_1_S0321_200      | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0321_200 | bp_moduleLogSO                      | bp_moduleLogSO                  | -8  | M_InOutLine | moduleLogContract_SO_S0321_200 | modCntr_type_SO_SHIP_S0321     | false         | Shipment                     | year_2023                         | true        | Eine Lieferung für Produkt modularContract_prod_S0321_200_modularContract_prod_S0321_200 mit der Menge 8 Stk wurde storniert. |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1_S0321_200   | C_OrderLine | SP               |                     |
      | s_l_1_S0321_200      | M_InOutLine | SP               | 3                   |
