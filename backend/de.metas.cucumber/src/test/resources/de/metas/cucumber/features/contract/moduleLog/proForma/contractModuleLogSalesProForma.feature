@Id:S0315
Feature: Modular contract log for proForma Sales Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-10T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier  | Name              | Value             |
      | moduleLogPS | moduleLogPS_S0315 | moduleLogPS_S0315 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPS                   | DE                        | EUR                 | moduleLogPL_PO_08182023 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPS                   | DE                        | EUR                 | moduleLogPL_SO_08182023 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_S0315_PO | 2022-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_S0315_SO | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier             | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogProFormaPO | bp_moduleLogProFormaPO_S0315 | Y            | N              | moduleLogPS                   | 1000002                    |
      | bp_moduleLogProFormaSO | bp_moduleLogProFormaSO_S0315 | N            | Y              | moduleLogPS                   | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                      | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogProFormaPO_Location | 0818202312346 | bp_moduleLogProFormaPO   | true                | true                |
      | bp_moduleLogProFormaSO_Location | 0818202312345 | bp_moduleLogProFormaSO   | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value           | Name            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_S0315 | warehouse_S0315 | bp_moduleLogProFormaPO       | bp_moduleLogProFormaPO_Location       |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value         | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_S0315 | warehouseModularContract  |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2022            | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier            | Name                                  | Value                                 | Classname                                                                      |
      | modCntr_type_PO_S0315                 | modCntr_type_PO_S0315                 | modCntr_type_PO_S0315                 | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler        |
      | modCntr_type_SO_for_PO_S0315          | modCntr_type_SO_for_PO_S0315          | modCntr_type_SO_for_PO_S0315          | de.metas.contracts.modular.impl.SOLineForPOModularContractHandler              |
      | modCntr_type_SO_S0315                 | modCntr_type_SO_S0315                 | modCntr_type_SO_S0315                 | de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler           |
      | modCntr_type_MC_SO_S0315              | modCntr_type_MC_SO_S0315              | modCntr_type_MC_SO_S0315              | de.metas.contracts.modular.impl.SalesModularContractHandler                    |
      | modCntr_type_MC_PO_S0315              | modCntr_type_MC_PO_S0315              | modCntr_type_MC_PO_S0315              | de.metas.contracts.modular.impl.PurchaseModularContractHandler                 |
      | modCntr_type_proForma_SO_for_PO_S0315 | modCntr_type_proForma_SO_for_PO_S0315 | modCntr_type_proForma_SO_for_PO_S0315 | de.metas.contracts.modular.impl.SalesOrderLineProFormaPOModularContractHandler |
      | modCntr_type_proForma_SO_S0315        | modCntr_type_proForma_SO_S0315        | modCntr_type_proForma_SO_S0315        | de.metas.contracts.modular.impl.SalesOrderLineProFormaModularContractHandler   |
      | modCntr_type_MC_proForma_SO_S0315     | modCntr_type_MC_proForma_SO_S0315     | modCntr_type_MC_proForma_SO_S0315     | de.metas.contracts.modular.impl.SalesProFormaModularContractHandler            |

  @Id:S0315_100
  @from:cucumber
  Scenario: When a shipment based on a sales order line which generated modular contract is completed

    Given metasfresh contains M_Products:
      | Identifier                 | Name                       |
      | modularContract_prod_S0315 | modularContract_prod_S0315 |

    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0315 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0315 | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier     | Name                               | M_Product_ID.Identifier    | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.IsSOTrx |
      | modCntr_proForma_PO_settings_S0315 | modCntr_proForma_PO_settings_S0315 | modularContract_prod_S0315 | harvesting_calendar      | year_2022            | moduleLogPS                       | false       |
      | modCntr_proForma_SO_settings_S0315 | modCntr_proForma_SO_settings_S0315 | modularContract_prod_S0315 | harvesting_calendar      | year_2022            | moduleLogPS                       | true        |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier            | SeqNo | Name    | M_Product_ID.Identifier    | InvoicingGroup | ModCntr_Settings_ID.Identifier     | ModCntr_Type_ID.Identifier            |
      | modCntr_module_PO_S0315                 | 10    | name_10 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_PO_settings_S0315 | modCntr_type_PO_S0315                 |
      | modCntr_module_MC_PO_S0315              | 20    | name_20 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_PO_settings_S0315 | modCntr_type_MC_PO_S0315              |
      | modCntr_module_ProForma_SO_for_PO_S0315 | 30    | name_30 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_PO_settings_S0315 | modCntr_type_proForma_SO_for_PO_S0315 |
      | modCntr_module_SO_for_PO_S0315          | 10    | name_10 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_SO_for_PO_S0315          |
      | modCntr_module_SO_S0315                 | 20    | name_20 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_SO_S0315                 |
      | modCntr_module_MC_SO_S0315              | 30    | name_30 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_MC_SO_S0315              |
      | modCntr_module_ProForma_SO_S0315        | 40    | name_40 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_proForma_SO_S0315        |
      | modCntr_module_MC_ProForma_SO_S0315     | 50    | name_50 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_MC_proForma_SO_S0315     |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                       | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_proForma_S0315 | modularContractTerms_S0315 | ModularContract | moduleLogPricingSystem            | modCntr_proForma_settings_S0315    |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | po_order_S0315 | true    | bp_moduleLogProFormaPO   | 2022-03-03  | po_order_S0315  | harvesting_calendar                     | year_2022                         | warehouseModularContract      |                 |                |
      | so_order_S0315 | true    | bp_moduleLogProFormaSO   | 2022-03-03  | so_order_S0315  | harvesting_calendar                     | year_2022                         | warehouseModularContract      | SOO             | PF             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier    | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine_1_S0315 | po_order_S0315        | modularContract_prod_S0315 | 10         | modularContractTerms_proForma_S0315     |
      | soLine_1_S0315 | so_order_S0315        | modularContract_prod_S0315 | 5          | modularContractTerms_proForma_S0315     |

    When the order identified by po_order_S0315 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier    | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_PO_S0315    | modularContractTerms_S0315          | modularContract_prod_S0315 | po_order_S0315                 | poLine_1_S0315                     |

    When the order identified by so_order_S0315 is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier    | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO_S0315    | modularContractTerms_S0315          | modularContract_prod_S0315 | so_order_S0315                 | soLine_1_S0315                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier    | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.DocStatus |
      | moduleLogContract_PO_S0315    | modularContractTerms_S0315          | bp_moduleLogProFormaPO      | modularContract_prod_S0315 | poLine_1_S0315                     | po_order_S0315                 | PCE                   | 10                    | 5.00            | moduleLogPricingSystem            | ModularContract     | CO            |
      | moduleLogContract_SO_S0315    | modularContractTerms_S0315          | bp_moduleLogProFormaSO      | modularContract_prod_S0315 | soLine_1_S0315                     | so_order_S0315                 | PCE                   | 5                     | 10.00           | moduleLogPricingSystem            | ModularContract     | CO            |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0315 | soLine_1_S0315            | N             |

    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier    |
      | so_order_S0315        | modularContract_prod_S0315 |

#    Then after not more than 30s, ModCntr_Logs are found:
#      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier   | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
#      | shipLog_1_S0315           | s_l_1_S0315          | ModularContract | bp_moduleLogProFormaPO                             | warehouseModularContract      | modularContract_prod_S0315| bp_moduleLogProFormaSO           | bp_moduleLogProFormaSO       | 8   | M_InOutLine | moduleLogContract_SO_S0315    | modCntr_type_SO_SHIP_S0315     | false         | Shipment                     | year_2022                         | true        | modCntr_module_SO_SHIP_S0315     | null            | null                      |
#
#    And after not more than 30s, validate ModCntr_Log_Statuses:
#      | Record_ID.Identifier | TableName   | ProcessingStatus |
#      | soLine_1_S0315       | C_OrderLine | SP               |
#      | s_l_1_S0315          | M_InOutLine | SP               |
#
#    And recompute modular logs for record:
#      | TableName | Record_ID.Identifier |
#      | M_InOut   | s_1_S0315            |
#    And after not more than 30s, validate ModCntr_Log_Statuses:
#      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
#      | s_l_1_S0315          | M_InOutLine | SP               | 2                   |
#    And after not more than 30s, ModCntr_Logs are found:
#      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx |
#      | shipLog_1_S0315           | s_l_1_S0315          | ModularContract | bp_moduleLogProFormaPO                             | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO           | bp_moduleLogProFormaSO       | 8   | M_InOutLine | moduleLogContract_SO_S0315    | modCntr_type_SO_SHIP_S0315     | false         | Shipment                     | year_2022                         | true        |
#
#    And 'generate shipments' process is invoked
#      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
#      | s_s_1_S0315                      | D            | false               | false       |
#    And after not more than 60s, M_InOut is found:
#      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
#      | s_s_1_S0315                      | s_1_S0315             |
#    And validate the created shipment lines
#      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier   | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
#      | s_l_1_S0315               | s_1_S0315             | modularContract_prod_S0315| 8           | false     | soLine_1_S0315                |
#
#    When the shipment identified by s_1_S0315 is completed