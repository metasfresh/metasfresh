@Id:S0315
@ignore
@ghActions:run_on_executor5
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

    And metasfresh contains M_Products:
      | Identifier           | Name                 |
      | packingProduct_S0315 | packingProduct_S0315 |

    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                    | OPT.M_Product_ID.Identifier |
      | huPackingMaterial                  | HUPackingMaterial_S0315 | packingProduct_S0315        |

    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_SO_PM | moduleLogPLV_SO                   | packingProduct_S0315    | 2.00     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingLU_2         | huPackingLU_2   |
      | huPackingTU           | huPackingTU     |
      | huPackingPM           | huPackingPM     |
      | huPackingVirtualPI    | No Packing Item |

    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name               | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU   | LU          | Y         |
      | packingVersionLU_2            | huPackingLU_2         | packingVersionLU_2 | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU   | TU          | Y         |
      | packingVersionPM              | huPackingPM           | packingVersionPM   | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item    | V           | Y         |


    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |                                        |
      | huPiItemLU_2               | packingVersionLU_2            | 10  | HU       | huPackingPM                      |                                        |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |                                        |
      | huPiItemPM                 | packingVersionPM              | 0   | PM       |                                  | huPackingMaterial                      |

    And metasfresh contains C_BPartners:
      | Identifier             | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogProFormaPO | bp_moduleLogProFormaPO_S0315 | Y            | N              | moduleLogPS                   | 1000002                    |
      | bp_moduleLogProFormaSO | bp_moduleLogProFormaSO_S0315 | N            | Y              | moduleLogPS                   | 1000002                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                      | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogProFormaPO_Location | 0818202312346 | bp_moduleLogProFormaPO   | true                | true                |
      | bp_moduleLogProFormaSO_Location | 0818202312345 | bp_moduleLogProFormaSO   | true                | true                |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier  | Value            |
      | emptyContainerAndPackingWH | Leergebindelager |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value           | Name            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_S0315 | warehouse_S0315 | bp_moduleLogProFormaPO       | bp_moduleLogProFormaPO_Location       |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value         | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_S0315 | warehouseModularContract  |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | ownTransport            | 1000000          |
    And load DD_NetworkDistribution:
      | DD_NetworkDistribution_ID.Identifier | Value   |
      | ddNetwork_isHUDestroyed              | Gebinde |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier  | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_S0315_1                    | ddNetwork_isHUDestroyed              | emptyContainerAndPackingWH | warehouseModularContract        | ownTransport            |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2022            | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier            | Name                                  | Value                                 | ModularContractHandlerType       |
      | modCntr_type_PO_S0315                 | modCntr_type_PO_S0315                 | modCntr_type_PO_S0315                 | PurchaseOrderLine_Modular        |
      | modCntr_type_SO_for_PO_S0315          | modCntr_type_SO_for_PO_S0315          | modCntr_type_SO_for_PO_S0315          | SOLineForPO_Modular              |
      | modCntr_type_SO_S0315                 | modCntr_type_SO_S0315                 | modCntr_type_SO_S0315                 | SalesOrderLine_Modular           |
      | modCntr_type_MC_SO_S0315              | modCntr_type_MC_SO_S0315              | modCntr_type_MC_SO_S0315              | SalesModularContract             |
      | modCntr_type_MC_PO_S0315              | modCntr_type_MC_PO_S0315              | modCntr_type_MC_PO_S0315              | PurchaseModularContract          |
      | modCntr_type_proForma_SO_for_PO_S0315 | modCntr_type_proForma_SO_for_PO_S0315 | modCntr_type_proForma_SO_for_PO_S0315 | SalesOrderLineProFormaPO_Modular |
      | modCntr_type_proForma_SO_S0315        | modCntr_type_proForma_SO_S0315        | modCntr_type_proForma_SO_S0315        | SalesOrderLineProForma_Modular   |
      | modCntr_type_MC_proForma_SO_S0315     | modCntr_type_MC_proForma_SO_S0315     | modCntr_type_MC_proForma_SO_S0315     | SalesContractProForma_Modular    |

  @Id:S0315_100
  @from:cucumber
  Scenario: When a proForma Sales Order with modular Condition is completed and Purchase Order with same Harvesting Details exists
  - validate Modular Contract for ProForma Sales Order is created
  - validate no IC for ProForma Sales Order and it's shipment (include packing materials) is created
  - validate ProForma Sales Order can't be reactivated or voided
  - validate Modular Contract Log for ProForma Sales Order Modular Contract is created
  - validate Modular Contract Log for related Purchase Modular Contract of ProForma Sales Order is created
  - validate Modular Contract Log for ProForma Sales OrderLines is created
  - validate Modular Contract Logs can be recreated

    Given metasfresh contains M_Products:
      | Identifier                 | Name                       |
      | modularContract_prod_S0315 | modularContract_prod_S0315 |

    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_S0315 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_S0315 | 10.00    | PCE               | Normal                        |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier    | Qty | ValidFrom  |
      | huItemPOProduct                    | huPiItemTU                 | modularContract_prod_S0315 | 10  | 2022-02-01 |
      | huItemSOProduct                    | huPiItemPM                 | modularContract_prod_S0315 | 10  | 2022-02-01 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier     | Name                               | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.IsSOTrx |
      | modCntr_proForma_PO_settings_S0315 | modCntr_proForma_PO_settings_S0315 | modularContract_prod_S0315  | harvesting_calendar      | year_2022            | moduleLogPS                       | false       |
      | modCntr_proForma_SO_settings_S0315 | modCntr_proForma_SO_settings_S0315 | modularContract_prod_S0315  | harvesting_calendar      | year_2022            | moduleLogPS                       | true        |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier            | SeqNo | Name    | M_Product_ID.Identifier    | InvoicingGroup | ModCntr_Settings_ID.Identifier     | ModCntr_Type_ID.Identifier            |
      | modCntr_module_PO_S0315                 | 10    | name_10 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_PO_settings_S0315 | modCntr_type_PO_S0315                 |
      | modCntr_module_MC_PO_S0315              | 20    | name_20 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_PO_settings_S0315 | modCntr_type_MC_PO_S0315              |
      | modCntr_module_proForma_SO_for_PO_S0315 | 30    | name_30 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_PO_settings_S0315 | modCntr_type_proForma_SO_for_PO_S0315 |
      | modCntr_module_SO_for_PO_S0315          | 10    | name_10 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_SO_for_PO_S0315          |
      | modCntr_module_SO_S0315                 | 20    | name_20 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_SO_S0315                 |
      | modCntr_module_MC_SO_S0315              | 30    | name_30 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_MC_SO_S0315              |
      | modCntr_module_proForma_SO_S0315        | 40    | name_40 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_proForma_SO_S0315        |
      | modCntr_module_MC_proForma_SO_S0315     | 50    | name_50 | modularContract_prod_S0315 | Kosten         | modCntr_proForma_SO_settings_S0315 | modCntr_type_MC_proForma_SO_S0315     |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                             | Name                          | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.ModCntr_Settings_ID.Identifier | OPT.OnFlatrateTermExtend |
      | modularContractTerms_proForma_PO_S0315 | modularContractTerms_PO_S0315 | ModularContract | moduleLogPS                       | modCntr_proForma_PO_settings_S0315 | Ex                       |
      | modularContractTerms_proForma_SO_S0315 | modularContractTerms_SO_S0315 | ModularContract | moduleLogPS                       | modCntr_proForma_SO_settings_S0315 | Ex                       |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.DocSubType | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | po_order_S0315 | false   | bp_moduleLogProFormaPO   | 2022-03-03  | po_order_S0315  |                                         |                                   | warehouseModularContract      | POO             |                |                                        |
      | so_order_S0315 | true    | bp_moduleLogProFormaSO   | 2022-03-03  | so_order_S0315  | harvesting_calendar                     | year_2022                         | warehouseModularContract      | SOO             | PF             |                                        |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier    | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine_1_S0315 | po_order_S0315        | modularContract_prod_S0315 | 10         | modularContractTerms_proForma_PO_S0315  |
      | soLine_1_S0315 | so_order_S0315        | modularContract_prod_S0315 | 10         | modularContractTerms_proForma_SO_S0315  |

    When the order identified by po_order_S0315 is completed

    Then retrieve C_Flatrate_Term within 120s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier    | M_Product_ID.Identifier    | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_PO_S0315    | modularContractTerms_proForma_PO_S0315 | modularContract_prod_S0315 | po_order_S0315                 | poLine_1_S0315                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier    | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier    | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.DocStatus |
      | moduleLogContract_PO_S0315    | modularContractTerms_proForma_PO_S0315 | bp_moduleLogProFormaPO      | modularContract_prod_S0315 | poLine_1_S0315                     | po_order_S0315                 | PCE                   | 10                    | 5.00            | moduleLogPS                       | ModularContract     | CO            |

    And after not more than 120s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier    | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | r_s_1_S0315                     | po_order_S0315        | poLine_1_S0315            | bp_moduleLogProFormaPO   | bp_moduleLogProFormaPO_Location   | modularContract_prod_S0315 | 10         | warehouseModularContract  | moduleLogContract_PO_S0315        |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu_S0315           | r_s_1_S0315                     | N               | 1     | N               | 1     | N               | 10          | huItemPOProduct                    | huPackingLU                  |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0315           | r_s_1_S0315                     | m_r_1_S0315           |

    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier    | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | shipmentLine_1            | m_r_1_S0315           | modularContract_prod_S0315 | 10          | true      | poLine_1_S0315                | moduleLogContract_PO_S0315        |

    When the order identified by so_order_S0315 is completed

    Then retrieve C_Flatrate_Term within 120s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier    | M_Product_ID.Identifier    | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO_S0315    | modularContractTerms_proForma_SO_S0315 | modularContract_prod_S0315 | so_order_S0315                 | soLine_1_S0315                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier    | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier    | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.DocStatus |
      | moduleLogContract_SO_S0315    | modularContractTerms_proForma_SO_S0315 | bp_moduleLogProFormaSO      | modularContract_prod_S0315 | soLine_1_S0315                     | so_order_S0315                 | PCE                   | 10                    | 10.00           | moduleLogPS                       | ModularContract     | CO            |

    And load AD_Message:
      | Identifier            | Value                                  |
      | docAction_not_allowed | de.metas.contracts.DocActionNotAllowed |

    And the order identified by so_order_S0315 is voided expecting error
      | OPT.AD_Message_ID.Identifier |
      | docAction_not_allowed        |

    And the order identified by so_order_S0315 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | docAction_not_allowed        |

    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1_S0315 | soLine_1_S0315            | N             |

    And the following qty is picked
      | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier    | QtyPicked | M_HU_ID.Identifier | OPT.PackTo_HU_PI_Item_Product_ID.Identifier |
      | s_s_1_S0315                      | modularContract_prod_S0315 | 10        | hu_S0315           | huItemSOProduct                             |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 120 seconds for order 'so_order_S0315'

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1_S0315                      | PD           | true                | false       |

    Then after not more than 120s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1_S0315                      | s_1_S0315             |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier    | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | s_l_1_S0315               | s_1_S0315             | modularContract_prod_S0315 | 10          | true      | soLine_1_S0315                |
      | s_l_2_S0315               | s_1_S0315             | packingProduct_S0315       | 1           | true      |                               |

    And there is no C_Invoice_Candidate for C_Order so_order_S0315

    And there is no C_Invoice_Candidate for M_InOutLine s_l_2_S0315

    And after not more than 120s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier       | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier        | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier        | OPT.Amount | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | Log_1                     | moduleLogContract_SO_S0315 | ModularContract | bp_moduleLogProFormaPO                     | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO              | bp_moduleLogProFormaSO          | 10  | C_Flatrate_Term | moduleLogContract_SO_S0315    | modCntr_type_MC_proForma_SO_S0315     | false         | ProFormaSOModularContract    | year_2022                         | true        | modCntr_module_MC_proForma_SO_S0315     | 100        | 10              | PCE                       |
      | Log_2                     | soLine_1_S0315             | ModularContract | bp_moduleLogProFormaPO                     | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO              | bp_moduleLogProFormaSO          | 10  | C_OrderLine     | moduleLogContract_SO_S0315    | modCntr_type_proForma_SO_S0315        | false         | ProFormaSO                   | year_2022                         | true        | modCntr_module_proForma_SO_S0315        | 100        | 10              | PCE                       |
      | Log_3                     | soLine_1_S0315             | ModularContract | bp_moduleLogProFormaPO                     | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO              | bp_moduleLogProFormaSO          | 10  | C_OrderLine     | moduleLogContract_PO_S0315    | modCntr_type_proForma_SO_for_PO_S0315 | false         | ProFormaSO                   | year_2022                         | false       | modCntr_module_proForma_SO_for_PO_S0315 | 100        | 10              | PCE                       |

    And after not more than 120s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier       | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1_S0315             | C_OrderLine     | SP               | 1                   |
      | moduleLogContract_SO_S0315 | C_Flatrate_Term | SP               | 1                   |

    And recompute modular logs for record:
      | TableName       | Record_ID.Identifier       |
      | C_Order         | so_order_S0315             |
      | C_Flatrate_Term | moduleLogContract_SO_S0315 |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier       | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1_S0315             | C_OrderLine     | SP               | 2                   |
      | moduleLogContract_SO_S0315 | C_Flatrate_Term | SP               | 2                   |

    And after not more than 120s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier       | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier        | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier        | OPT.Amount | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | Log_1                     | moduleLogContract_SO_S0315 | ModularContract | bp_moduleLogProFormaPO                     | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO              | bp_moduleLogProFormaSO          | 10  | C_Flatrate_Term | moduleLogContract_SO_S0315    | modCntr_type_MC_proForma_SO_S0315     | false         | ProFormaSOModularContract    | year_2022                         | true        | modCntr_module_MC_proForma_SO_S0315     | 100        | 10              | PCE                       |
      | Log_2                     | soLine_1_S0315             | ModularContract | bp_moduleLogProFormaPO                     | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO              | bp_moduleLogProFormaSO          | 10  | C_OrderLine     | moduleLogContract_SO_S0315    | modCntr_type_proForma_SO_S0315        | false         | ProFormaSO                   | year_2022                         | true        | modCntr_module_proForma_SO_S0315        | 100        | 10              | PCE                       |
      | Log_3                     | soLine_1_S0315             | ModularContract | bp_moduleLogProFormaPO                     | warehouseModularContract      | modularContract_prod_S0315 | bp_moduleLogProFormaSO              | bp_moduleLogProFormaSO          | 10  | C_OrderLine     | moduleLogContract_PO_S0315    | modCntr_type_proForma_SO_for_PO_S0315 | false         | ProFormaSO                   | year_2022                         | false       | modCntr_module_proForma_SO_for_PO_S0315 | 100        | 10              | PCE                       |
