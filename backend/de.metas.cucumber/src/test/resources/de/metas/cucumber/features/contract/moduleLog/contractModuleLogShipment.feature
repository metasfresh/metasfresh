Feature: Modular contract log from shipment

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                            | Value                           |
      | moduleLogPricingSystem | moduleLogPricingSystem_07262023 | moduleLogPricingSystem_07262023 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_07262023 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_07262023 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_07262023_PO | 2022-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_07262023_SO | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_07262023 | Y            | N              | moduleLogPricingSystem        | 1000002                    |
      | bp_moduleLogSO | bp_moduleLogSO_07262023 | N            | Y              | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 0726202312346 | bp_moduleLogPO           | true                | true                |
      | bp_moduleLogSO_Location | 0726202312345 | bp_moduleLogSO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value              | Name               | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_07262023 | warehouse_07262023 | bp_moduleLogPO               | bp_moduleLogPO_Location               |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value            | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_07262023 | warehouseModularContract  |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                    | Value                   | Classname                                                               |
      | modCntr_type_PO_S0303      | modCntr_type_PO_S0303   | modCntr_type_PO_S0303   | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |
      | modCntr_type_SO_S0303      | modCntr_type_SO_S0303   | modCntr_type_SO_S0303   | de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler    |
      | modCntr_type_SHIP_S0303    | modCntr_type_SHIP_S0303 | modCntr_type_SHIP_S0303 | de.metas.contracts.modular.impl.ShipmentLineModularContractHandler      |

  @Id:S0303_100
  @from:cucumber
  Scenario: When a shipment is completed, create a purchase modular contract log for each line that requires it
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order and complete it
  - validate that for each line a modular contract log is created
  - create a shipment and complete it
  - validate that for each shipment line a modular contract log is created

    Given metasfresh contains M_Products:
      | Identifier                     | Name                           |
      | modularContract_prod_S0303_100 | modularContract_prod_S0303_100 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier        | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0303_100 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0303_100 | 10.00    | PCE               | Normal                        |
    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier  | Name                            | M_Product_ID.Identifier        | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_ship_settings_S0303_100 | modCntr_ship_settings_S0303_100 | modularContract_prod_S0303_100 | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier  | SeqNo | Name    | M_Product_ID.Identifier        | InvoicingGroup | ModCntr_Settings_ID.Identifier  | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO_S0303_100   | 10    | name_10 | modularContract_prod_S0303_100 | Kosten         | modCntr_ship_settings_S0303_100 | modCntr_type_PO_S0303      |
      | modCntr_module_SO_S0303_100   | 20    | name_20 | modularContract_prod_S0303_100 | Kosten         | modCntr_ship_settings_S0303_100 | modCntr_type_SO_S0303      |
      | modCntr_module_SHIP_S0303_100 | 30    | name_30 | modularContract_prod_S0303_100 | Kosten         | modCntr_ship_settings_S0303_100 | modCntr_type_SHIP_S0303    |
    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                           | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_S0303_100      | modularContractTerms_S0303_100 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_ship_settings_S0303_100    |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference             | OPT.M_Warehouse_ID.Identifier |
      | po_order_S0303_100 | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_S0303_100 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine_S0303_100 | po_order_S0303_100    | modularContract_prod_S0303_100 | 20         | modularContractTerms_S0303_100          |

    When the order identified by po_order_S0303_100 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier        | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_S0303_100   | modularContractTerms_S0303_100      | modularContract_prod_S0303_100 | po_order_S0303_100             | poLine_S0303_100                   |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier        | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_S0303_100   | modularContractTerms_S0303_100      | bp_moduleLogPO              | modularContract_prod_S0303_100 | poLine_S0303_100                   | po_order_S0303_100             | PCE                   | 10                    | 5.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order_S0303_100 | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered |
      | soLine_1_S0303_100 | so_order_S0303_100    | modularContract_prod_S0303_100 | 8          |
      | soLine_2_S0303_100 | so_order_S0303_100    | modularContract_prod_S0303_100 | 3          |

    When the order identified by so_order_S0303_100 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | soLog_1_S0303_100         | soLine_1_S0303_100   | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_100 | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract_S0303_100   | modCntr_type_SO_S0303          | false         | SalesOrder                   | year_2023                         | false       |
      | soLog_2_S0303_100         | soLine_2_S0303_100   | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_100 | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract_S0303_100   | modCntr_type_SO_S0303          | false         | SalesOrder                   | year_2023                         | false       |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0303_100 | soLine_1_S0303_100        | N             |
      | s_s_2_S0303_100 | soLine_2_S0303_100        | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0303_100                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0303_100                  | s_1_S0303_100         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_1_S0303_100           | s_1_S0303_100         | modularContract_prod_S0303_100 | 8           | false     | soLine_1_S0303_100            |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2_S0303_100                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2_S0303_100                  | s_2_S0303_100         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_2_S0303_100           | s_2_S0303_100         | modularContract_prod_S0303_100 | 3           | false     | soLine_2_S0303_100            |

    When the shipment identified by s_1_S0303_100 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | shipLog_1_S0303_100       | s_l_1_S0303_100      | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_100 | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine | moduleLogContract_S0303_100   | modCntr_type_SHIP_S0303        | false         | Shipment                     | year_2023                         | false       |
      | shipLog_2_S0303_100       | s_l_2_S0303_100      | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_100 | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine | moduleLogContract_S0303_100   | modCntr_type_SHIP_S0303        | false         | Shipment                     | year_2023                         | false       |

  @Id:S0303_200
  @from:cucumber
  Scenario: Reactivation should not be possible for completed shipments
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order and complete it
  - validate that for each line a modular contract log is created
  - create a shipment and complete it
  - validate that for each shipment line a modular contract log is created
  - `REACTIVATE` shipment
  - validate `ReactivateNotAllowed` error is thrown

    Given metasfresh contains M_Products:
      | Identifier                     | Name                           |
      | modularContract_prod_S0303_200 | modularContract_prod_S0303_200 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier        | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0303_200 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0303_200 | 10.00    | PCE               | Normal                        |
    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier  | Name                            | M_Product_ID.Identifier        | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_ship_settings_S0303_200 | modCntr_ship_settings_S0303_200 | modularContract_prod_S0303_200 | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier  | SeqNo | Name    | M_Product_ID.Identifier        | InvoicingGroup | ModCntr_Settings_ID.Identifier  | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO_S0303_200   | 10    | name_10 | modularContract_prod_S0303_200 | Kosten         | modCntr_ship_settings_S0303_200 | modCntr_type_PO_S0303      |
      | modCntr_module_SO_S0303_200   | 20    | name_20 | modularContract_prod_S0303_200 | Kosten         | modCntr_ship_settings_S0303_200 | modCntr_type_SO_S0303      |
      | modCntr_module_SHIP_S0303_200 | 30    | name_30 | modularContract_prod_S0303_200 | Kosten         | modCntr_ship_settings_S0303_200 | modCntr_type_SHIP_S0303    |
    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                           | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_S0303_200      | modularContractTerms_S0303_200 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_ship_settings_S0303_200    |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference             | OPT.M_Warehouse_ID.Identifier |
      | po_order_S0303_200 | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_S0303_200 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine_S0303_200 | po_order_S0303_200    | modularContract_prod_S0303_200 | 20         | modularContractTerms_S0303_200          |

    When the order identified by po_order_S0303_200 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier        | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_S0303_200   | modularContractTerms_S0303_200      | modularContract_prod_S0303_200 | po_order_S0303_200             | poLine_S0303_200                   |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier        | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_S0303_200   | modularContractTerms_S0303_200      | bp_moduleLogPO              | modularContract_prod_S0303_200 | poLine_S0303_200                   | po_order_S0303_200             | PCE                   | 10                    | 5.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order_S0303_200 | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered |
      | soLine_1_S0303_200 | so_order_S0303_200    | modularContract_prod_S0303_200 | 8          |
      | soLine_2_S0303_200 | so_order_S0303_200    | modularContract_prod_S0303_200 | 3          |

    When the order identified by so_order_S0303_200 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | soLog_1_S0303_200         | soLine_1_S0303_200   | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract_S0303_200   | modCntr_type_SO_S0303          | false         | SalesOrder                   | year_2023                         | false       |
      | soLog_2_S0303_200         | soLine_2_S0303_200   | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract_S0303_200   | modCntr_type_SO_S0303          | false         | SalesOrder                   | year_2023                         | false       |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0303_200 | soLine_1_S0303_200        | N             |
      | s_s_2_S0303_200 | soLine_2_S0303_200        | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0303_200                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0303_200                  | s_1_S0303_200         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_1_S0303_200           | s_1_S0303_200         | modularContract_prod_S0303_200 | 8           | false     | soLine_1_S0303_200            |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2_S0303_200                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2_S0303_200                  | s_2_S0303_200         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_2_S0303_200           | s_2_S0303_200         | modularContract_prod_S0303_200 | 3           | false     | soLine_2_S0303_200            |

    When the shipment identified by s_1_S0303_200 is completed

    And load AD_Message:
      | Identifier            | Value                                  |
      | docAction_not_allowed | de.metas.contracts.DocActionNotAllowed |

    And the shipment identified by s_1_S0303_200 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | docAction_not_allowed        |

  @Id:S0303_300
  @from:cucumber
  Scenario: When a shipment is reversed, a modular contract log is created with negated qty
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order and complete it
  - validate that for each line a modular contract log is created
  - create a shipment and complete it
  - reverse the shipment
  - validate that for each shipment line a modular contract log is created with negated qty

    Given metasfresh contains M_Products:
      | Identifier                     | Name                           |
      | modularContract_prod_S0303_300 | modularContract_prod_S0303_300 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier        | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0303_300 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0303_300 | 10.00    | PCE               | Normal                        |
    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier  | Name                            | M_Product_ID.Identifier        | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_ship_settings_S0303_300 | modCntr_ship_settings_S0303_300 | modularContract_prod_S0303_300 | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier  | SeqNo | Name    | M_Product_ID.Identifier        | InvoicingGroup | ModCntr_Settings_ID.Identifier  | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO_S0303_300   | 10    | name_10 | modularContract_prod_S0303_300 | Kosten         | modCntr_ship_settings_S0303_300 | modCntr_type_PO_S0303      |
      | modCntr_module_SO_S0303_300   | 20    | name_20 | modularContract_prod_S0303_300 | Kosten         | modCntr_ship_settings_S0303_300 | modCntr_type_SO_S0303      |
      | modCntr_module_SHIP_S0303_300 | 30    | name_30 | modularContract_prod_S0303_300 | Kosten         | modCntr_ship_settings_S0303_300 | modCntr_type_SHIP_S0303    |
    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                           | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_S0303_300      | modularContractTerms_S0303_300 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_ship_settings_S0303_300    |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference             | OPT.M_Warehouse_ID.Identifier |
      | po_order_S0303_300 | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_S0303_300 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine_S0303_300 | po_order_S0303_300    | modularContract_prod_S0303_300 | 10         | modularContractTerms_S0303_300          |

    When the order identified by po_order_S0303_300 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier        | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_S0303_300   | modularContractTerms_S0303_300      | modularContract_prod_S0303_300 | po_order_S0303_300             | poLine_S0303_300                   |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier        | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_S0303_300   | modularContractTerms_S0303_300      | bp_moduleLogPO              | modularContract_prod_S0303_300 | poLine_S0303_300                   | po_order_S0303_300             | PCE                   | 10                    | 5.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order_S0303_300 | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier        | QtyEntered |
      | soLine_1_S0303_300 | so_order_S0303_300    | modularContract_prod_S0303_300 | 8          |
      | soLine_2_S0303_300 | so_order_S0303_300    | modularContract_prod_S0303_300 | 3          |

    When the order identified by so_order_S0303_300 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | soLog_1_S0303_300         | soLine_1_S0303_300   | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine | moduleLogContract_S0303_300   | modCntr_type_SO_S0303          | false         | SalesOrder                   | year_2023                         | false       |
      | soLog_2_S0303_300         | soLine_2_S0303_300   | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract_S0303_300   | modCntr_type_SO_S0303          | false         | SalesOrder                   | year_2023                         | false       |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0303_300 | soLine_1_S0303_300        | N             |
      | s_s_2_S0303_300 | soLine_2_S0303_300        | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0303_300                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0303_300                  | s_1_S0303_300         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_1_S0303_300           | s_1_S0303_300         | modularContract_prod_S0303_300 | 8           | false     | soLine_1_S0303_300            |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2_S0303_300                  | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2_S0303_300                  | s_2_S0303_300         |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier        | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_2_S0303_300           | s_2_S0303_300         | modularContract_prod_S0303_300 | 3           | false     | soLine_2_S0303_300            |

    When the shipment identified by s_1_S0303_300 is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | shipLog_1_S0303_300       | s_l_1_S0303_300      | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | M_InOutLine | moduleLogContract_S0303_300   | modCntr_type_SHIP_S0303        | false         | Shipment                     | year_2023                         | false       |
      | shipLog_2_S0303_300       | s_l_2_S0303_300      | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | M_InOutLine | moduleLogContract_S0303_300   | modCntr_type_SHIP_S0303        | false         | Shipment                     | year_2023                         | false       |

    When the shipment identified by s_1_S0303_300 is reversed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
      | shipLog_1_S0303_300       | s_l_1_S0303_300      | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | -8  | M_InOutLine | moduleLogContract_S0303_300   | modCntr_type_SHIP_S0303        | false         | Shipment                     | year_2023                         | false       |
      | shipLog_2_S0303_300       | s_l_2_S0303_300      | ModularContract | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_S0303_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | -3  | M_InOutLine | moduleLogContract_S0303_300   | modCntr_type_SHIP_S0303        | false         | Shipment                     | year_2023                         | false       |