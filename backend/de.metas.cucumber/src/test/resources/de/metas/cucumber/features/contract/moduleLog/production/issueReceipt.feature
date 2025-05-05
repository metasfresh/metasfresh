@Id:S0299
@from:cucumber
@ghActions:run_on_executor5
@ignore
Feature: After a quantity of a product is issued/received for the manufacturing order, an entry is created in the module contract log

  Scenario: There is an entry created in the module contract log when a quantity for the manufacturing order is issued and receipt
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-31T13:30:13+01:00[Europe/Berlin]

    And destroy existing M_HUs
    And metasfresh contains M_Products:
      | Identifier           | Value                           | Name                            |
      | componentProduct     | componentProduct_07212023_1     | huProduct_07212023_1            |
      | manufacturingProduct | manufacturingProduct_07212023_1 | manufacturingProduct_07212023_1 |
      | coProduct            | coProduct_07212023_1            | coProduct_07212023_1            |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |

    And metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_07212023_1 | moduleLogPricingSystem_07212023_1 |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogMO | bp_moduleLogMO_07212023_1 | Y            | Y              | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogMO_Location | 5823198505583 | bp_moduleLogMO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse                 | warehouse_07212023_1 | warehouse_07212023_1 | bp_moduleLogMO               | bp_moduleLogMO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_07212023_1 | warehouse                 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_07212023_1 | componentProduct            | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                          | Value                         | ModularContractHandlerType |
      | modCntr_type_1             | manufacturingOrder_07212023_1 | manufacturingOrder_07212023_1 | PPCostCollector_Modular    |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_07212023_1 | componentProduct        | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | moduleTest_07212023_2 | manufacturingProduct    | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09282023_1 | componentProduct            | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | componentProduct        |
      | invoicingGroup_p1                            | invoicingGroup                       | manufacturingProduct    |
      | invoicingGroup_p1                            | invoicingGroup                       | coProduct               |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_MO | moduleLogConditions_MO_07212023_1 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_MO              | bp_moduleLogMO              | 2021-10-31 | 2022-10-30 | componentProduct            | bp_moduleLogMO                      |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | huProduct_inventory       | 2022-03-20   | warehouse      |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | componentProduct        | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

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
      | huProductTU                        | huPiItemTU                 | componentProduct        | 10  | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier        | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_manufacturing | manufacturingProduct    | 2021-01-02 | bomVersions_manufacturing            |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | IsQtyPercentage | OPT.ComponentType |
      | bom_l_manufacturing1 | bom_manufacturing            | componentProduct        | 2021-01-02 | 100      | true            | CO                |
      | bom_l_manufacturing2 | bom_manufacturing            | coProduct               | 2021-01-02 | -15      | true            | CP                |

    And the PP_Product_BOM identified by bom_manufacturing is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.Modular_Flatrate_Term_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppOrder_manufacturing  | MMO         | manufacturingProduct    | 10         | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | N                | moduleLogContract_1                     | warehouse                     |

    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBOMLine_1               | ppOrder_manufacturing  | componentProduct        | 10           | true            | PCE               | CO            |
      | ppOrderBOMLine_2               | ppOrder_manufacturing  | coProduct               | -1.5         | true            | PCE               | CP            |

    And the manufacturing order identified by ppOrder_manufacturing is completed

    And select M_HU to be issued for productionOrder
      | M_HU_ID.Identifier | PP_Order_Qty_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.MovementDate     |
      | createdCU          | pp_order_qty_1             | ppOrderBOMLine_1               | 2022-03-31T13:30:13Z |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | PP_Order_ID.Identifier | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig                          | ppOrder_manufacturing  | receiptTU          | N               | 0     | N               | 1     | N               | 10          | huProductTU                        |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder_manufacturing  |

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier  | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | ppOrder_manufacturing | ModularContract | invoicingGroup                           | warehouse                     | componentProduct        | bp_moduleLogMO                  | -10 | PP_Order  | moduleLogContract_1           | modCntr_type_1                 | false         | Production                   | PCE                   | year                              | bp_moduleLogMO                             | modCntr_module_1                 | null            | null                      |
      | log_2                     | ppOrder_manufacturing | ModularContract | invoicingGroup                           | warehouse                     | manufacturingProduct    | bp_moduleLogMO                  | 10  | PP_Order  | moduleLogContract_1           | modCntr_type_1                 | false         | Production                   | PCE                   | year                              | bp_moduleLogMO                             | modCntr_module_2                 | null            | null                      |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus |
      | ppOrder_CostCollector_1         | ppOrder_manufacturing  | manufacturingProduct    | 10          | CO        |
      | ppOrder_CostCollector_2         | ppOrder_manufacturing  | componentProduct        | 10          | CO        |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier    | TableName         | ProcessingStatus |
      | ppOrder_CostCollector_1 | PP_Cost_Collector | SP               |
      | ppOrder_CostCollector_2 | PP_Cost_Collector | SP               |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier  |
      | PP_Order  | ppOrder_manufacturing |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier    | TableName         | ProcessingStatus | OPT.noOfLogStatuses |
      | ppOrder_CostCollector_1 | PP_Cost_Collector | SP               | 2                   |
      | ppOrder_CostCollector_2 | PP_Cost_Collector | SP               | 2                   |
    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier  | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier |
      | log_1                     | ppOrder_manufacturing | ModularContract | invoicingGroup                           | warehouse                     | componentProduct        | bp_moduleLogMO                  | -10 | PP_Order  | moduleLogContract_1           | modCntr_type_1                 | false         | Production                   | PCE                   | year                              | bp_moduleLogMO                             |
      | log_2                     | ppOrder_manufacturing | ModularContract | invoicingGroup                           | warehouse                     | manufacturingProduct    | bp_moduleLogMO                  | 10  | PP_Order  | moduleLogContract_1           | modCntr_type_1                 | false         | Production                   | PCE                   | year                              | bp_moduleLogMO                             |