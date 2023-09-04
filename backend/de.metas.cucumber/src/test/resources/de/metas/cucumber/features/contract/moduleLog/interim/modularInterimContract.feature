@Id:S0306
Feature: Interim contract for bpartner

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier | Name               | Value              |
      | interimPS  | interimPS_08022023 | interimPS_08022023 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | interimPL_PO | interimPS                     | DE                        | EUR                 | interimPL_PO_08022023 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | interimPLV_PO | interimPL_PO              | moduleLogPLV_08022023_PO | 2022-01-01 |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2022            | 2022       | harvesting_calendar      |

    And metasfresh contains M_Products:
      | Identifier              | Name                                  |
      | module_log_product_PO_1 | module_log_product_PO_test_08022023_1 |
      | module_log_product_PO_2 | module_log_product_PO_test_08022023_2 |

    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP_1 | interimPLV_PO                     | module_log_product_PO_1 | 2.00     | PCE               | Normal                        |
      | moduleLogPP_2 | interimPLV_PO                     | module_log_product_PO_2 | 2.00     | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_08022023_1 | module_log_product_PO_1 | harvesting_calendar      | year_2022            | interimPS                         |
      | modCntr_settings_2             | testSettings_08022023_2 | module_log_product_PO_2 | harvesting_calendar      | year_2022            | interimPS                         |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                         | Value                        | Classname                                                                            |
      | modCntr_type_1             | poLine_08022023              | poLine_08022023              | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler              |
      | modCntr_type_2             | interim_08022023             | interim_08022023             | de.metas.contracts.modular.interim.logImpl.InterimContractHandler                    |
      | modCntr_type_3             | receiptLine_modular_08022023 | receiptLine_modular_08022023 | de.metas.contracts.modular.impl.MaterialReceiptLineModularContractHandler            |
      | modCntr_type_4             | receiptLine_interim_08022023 | receiptLine_interim_08022023 | de.metas.contracts.modular.interim.logImpl.MaterialReceiptLineInterimContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_08022023_1 | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | moduleTest_08022023_2 | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_2             |
      | modCntr_module_3             | 30    | moduleTest_08022023_3 | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_3             |
      | modCntr_module_4             | 40    | moduleTest_08022023_4 | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_4             |
      | modCntr_module_5             | 10    | moduleTest_08022023_5 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_1             |
      | modCntr_module_6             | 20    | moduleTest_08022023_6 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_2             |
      | modCntr_module_7             | 30    | moduleTest_08022023_7 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_3             |
      | modCntr_module_8             | 40    | moduleTest_08022023_8 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_4             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO_1            | moduleLogConditions_po_08022023_1      | ModularContract | interimPS                         | Ca                       | modCntr_settings_1                 |
      | moduleLogConditions_interim_1       | moduleLogConditions_interim_08022023_1 | InterimInvoice  | interimPS                         | Ca                       | modCntr_settings_1                 |
      | moduleLogConditions_PO_2            | moduleLogConditions_po_08022023_2      | ModularContract | interimPS                         | Ca                       | modCntr_settings_2                 |
      | moduleLogConditions_interim_2       | moduleLogConditions_interim_08022023_2 | InterimInvoice  | interimPS                         | Ca                       | modCntr_settings_2                 |

  @Id:S0306_100
  Scenario: purchase modular and interim contract (automatically) + logs are created, with C_BPartner_InterimContract
  - validate bp interim contract settings are generated
  - validate interim and modular contract created (interim automatically because of sysconfig)
  - validate interim and modular contract log created
  - after receipt validate interim and modular receiptLine Log created

    Given metasfresh contains C_BPartners:
      | Identifier   | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_interimPO | bp_interimPO_08022023 | Y            | N              | interimPS                     | 1000002                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_interimPO_Location_1 | 0802202312346 | bp_interimPO             | true                | true                |

    When invoke "C_BPartner_InterimContract_Upsert" process:
      | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimPO             | harvesting_calendar                 | year_2022                     | true              |

    Then metasfresh contains C_BPartner_InterimContract:
      | C_BPartner_InterimContract_ID.Identifier | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimContractSettings_1             | bp_interimPO             | harvesting_calendar                 | year_2022                     | true              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                  |
      | po_order_1 | false   | bp_interimPO             | 2022-01-01  | POO             | poModuleLogContract_ref_08022023 |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine_1 | po_order_1            | module_log_product_PO_1 | 1000       | moduleLogConditions_PO_1                |
      | po_orderLine_2 | po_order_1            | module_log_product_PO_2 | 500        | moduleLogConditions_PO_2                |

    When the order identified by po_order_1 is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO_1            | module_log_product_PO_1 | po_order_1                     | po_orderLine_1                     |
      | moduleLogContract_2           | moduleLogConditions_interim_1       | module_log_product_PO_1 | po_order_1                     | po_orderLine_1                     |
      | moduleLogContract_3           | moduleLogConditions_PO_2            | module_log_product_PO_2 | po_order_1                     | po_orderLine_2                     |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | module_log_product_PO_2 | po_order_1                     | po_orderLine_2                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO_1            | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_2           | moduleLogConditions_interim_1       | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | InterimContract     | Wa                 | CO            |
      | moduleLogContract_3           | moduleLogConditions_PO_2            | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | InterimContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | moduleLogContract_2  | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_3                     | po_orderLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_4                     | moduleLogContract_4  | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_16082023_1   | po_order_1            | po_orderLine_1            | bp_interimPO             | bp_interimPO_Location_1           | module_log_product_PO_1 | 1000       | warehouseStd              |
      | receiptSchedule_PO_16082023_2   | po_order_1            | po_orderLine_2            | bp_interimPO             | bp_interimPO_Location_1           | module_log_product_PO_2 | 500        | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_16082023_1               | hu_16082023_1      | receiptSchedule_PO_16082023_1   | N               | 1     | N               | 1     | N               | 100   | 101                                | 1000006                      |
      | huLuTuConfig_16082023_2               | hu_16082023_2      | receiptSchedule_PO_16082023_2   | N               | 1     | N               | 1     | N               | 50    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_16082023_1      | receiptSchedule_PO_16082023_1   | material_receipt_1    |
      | hu_16082023_2      | receiptSchedule_PO_16082023_2   | material_receipt_2    |

    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | shipmentLine_1            | material_receipt_1    | module_log_product_PO_1 | 100         | true      | po_orderLine_1                | moduleLogContract_1               |
      | shipmentLine_2            | material_receipt_2    | module_log_product_PO_2 | 50          | true      | po_orderLine_2                | moduleLogContract_3               |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | moduleLogContract_2  | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_3                     | po_orderLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_4                     | moduleLogContract_4  | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_5                     | shipmentLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_6                     | shipmentLine_1       | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_2           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_7                     | shipmentLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_3           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_8                     | shipmentLine_2       | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |


  @Id:S0306_200
  Scenario: purchase modular and interim contract (manually) + logs are created, with C_BPartner_InterimContract
  - validate bp interim contract settings are generated
  - validate modular contract + log created
  - after receipt validate modular receiptLine Log created
  - after create interim contract interim contract + log created
  - validate interim receiptLine Log created
  - validate error on create interim contract create, if overlapping with interim contract for same modular contract

    Given set sys config boolean value false for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

    And metasfresh contains C_BPartners:
      | Identifier   | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_interimPO | bp_interimPO_S0306_200 | Y            | N              | interimPS                     | 1000002                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_interimPO_Location_2 | 0802202312346 | bp_interimPO             | true                | true                |

    When invoke "C_BPartner_InterimContract_Upsert" process:
      | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimPO             | harvesting_calendar                 | year_2022                     | true              |

    Then metasfresh contains C_BPartner_InterimContract:
      | C_BPartner_InterimContract_ID.Identifier | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimContractSettings_2             | bp_interimPO             | harvesting_calendar                 | year_2022                     | true              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                   |
      | po_order_1 | false   | bp_interimPO             | 2022-01-01  | POO             | poModuleLogContract_ref_S0306_200 |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine_1 | po_order_1            | module_log_product_PO_1 | 1000       | moduleLogConditions_PO_1                |
      | po_orderLine_2 | po_order_1            | module_log_product_PO_2 | 500        | moduleLogConditions_PO_2                |

    When the order identified by po_order_1 is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO_1            | module_log_product_PO_1 | po_order_1                     | po_orderLine_1                     |
      | moduleLogContract_2           | moduleLogConditions_PO_2            | module_log_product_PO_2 | po_order_1                     | po_orderLine_2                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO_1            | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_2           | moduleLogConditions_PO_2            | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | po_orderLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_16082023_1   | po_order_1            | po_orderLine_1            | bp_interimPO             | bp_interimPO_Location_2           | module_log_product_PO_1 | 1000       | warehouseStd              |
      | receiptSchedule_PO_16082023_2   | po_order_1            | po_orderLine_2            | bp_interimPO             | bp_interimPO_Location_2           | module_log_product_PO_2 | 500        | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_16082023_1               | hu_16082023_1      | receiptSchedule_PO_16082023_1   | N               | 1     | N               | 1     | N               | 100   | 101                                | 1000006                      |
      | huLuTuConfig_16082023_2               | hu_16082023_2      | receiptSchedule_PO_16082023_2   | N               | 1     | N               | 1     | N               | 50    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_16082023_1      | receiptSchedule_PO_16082023_1   | material_receipt_1    |
      | hu_16082023_2      | receiptSchedule_PO_16082023_2   | material_receipt_2    |

    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | shipmentLine_1            | material_receipt_1    | module_log_product_PO_1 | 100         | true      | po_orderLine_1                | moduleLogContract_1               |
      | shipmentLine_2            | material_receipt_2    | module_log_product_PO_2 | 50          | true      | po_orderLine_2                | moduleLogContract_2               |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | po_orderLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_3                     | shipmentLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_4                     | shipmentLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine | moduleLogContract_2           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |

    When create interim contract for modular contract
      | C_Flatrate_Term_ID.Identifier | DateFrom   | DateTo     |
      | moduleLogContract_1           | 2022-03-01 | 2022-03-31 |
      | moduleLogContract_2           | 2022-03-01 | 2022-03-31 |

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_3           | moduleLogConditions_interim_1       | module_log_product_PO_1 | po_order_1                     | po_orderLine_1                     |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | module_log_product_PO_2 | po_order_1                     | po_orderLine_2                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_3           | moduleLogConditions_interim_1       | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | po_orderLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_3                     | shipmentLine_1       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_4                     | shipmentLine_2       | ModularContract | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_2           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_5                     | moduleLogContract_3  | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_6                     | moduleLogContract_4  | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_2           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_7                     | shipmentLine_1       | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_3           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_8                     | shipmentLine_2       | Interim         | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |

    When create interim contract for modular contract with error
      | C_Flatrate_Term_ID.Identifier | DateFrom   | DateTo     | errorCode                                                        |
      | moduleLogContract_1           | 2022-01-01 | 2022-03-31 | de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm |

    And set sys config boolean value true for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

