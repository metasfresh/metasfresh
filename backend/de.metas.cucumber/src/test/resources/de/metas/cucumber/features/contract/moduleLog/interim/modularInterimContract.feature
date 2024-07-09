@Id:S0306
@Id:S0314
@ignore
@ghActions:run_on_executor5
Feature: Interim contract and interim invoice for bpartner

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

  @Id:S0306_100
  @Id:S0314_100
  Scenario: purchase modular and interim contract (automatically) + logs are created, with C_BPartner_InterimContract
  - given there is a configured `ModCntr_InvoicingGroup`
  - the user adds a purchase order prior to the dates when the `invoicingGroup` is applicable
  - validate `C_BPartner_InterimContract` are generated
  - validate interim and modular contract created (interim automatically because of sysconfig)
  - validate interim and modular contract log created
  - validate interim contract log recreation is working
  - after receipt validate interim and modular receiptLine Log created
  - then invoke `Generate Interim Invoice` process
  - validate invoice candidate and interim invoice are generated for the actual products that were added on purchase order line
  - validate interim invoice log is created
  - validate interim invoice log recreation is working
  - validate interim invoice reverse log is created on interim invoice reverse

    Given metasfresh contains M_Products:
      | Identifier                 | Name                                  |
      | module_log_product_PO_1    | module_log_product_PO_test_08022023_1 |
      | module_log_product_PO_2    | module_log_product_PO_test_08022023_2 |
      | module_log_product_PO_base | module_log_product_PO_test_08022023_3 |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP_1    | interimPLV_PO                     | module_log_product_PO_1    | 2.00     | PCE               | Normal                        |
      | moduleLogPP_2    | interimPLV_PO                     | module_log_product_PO_2    | 2.00     | PCE               | Normal                        |
      | moduleLogPP_base | interimPLV_PO                     | module_log_product_PO_base | 3.00     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                 |
      | huPackingLU           | huPackingLU_08022023 |
      | huPackingTU           | huPackingTU_08022023 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                      | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU_08022023 | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU_08022023 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 100 | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 100 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier    | Qty  | ValidFrom  |
      | huItemPurchaseProduct_1            | huPiItemTU                 | module_log_product_PO_1    | 1000 | 2022-01-01 |
      | huItemPurchaseProduct_2            | huPiItemTU                 | module_log_product_PO_2    | 1000 | 2022-01-01 |
      | huItemPurchaseProduct_base         | huPiItemTU                 | module_log_product_PO_base | 1000 | 2022-01-01 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                    | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_08022023 | module_log_product_PO_base  | 2021-02-10 | 2023-11-25 |
    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO_1 |
      | invoicingGroup_p2                            | invoicingGroup                       | module_log_product_PO_2 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_08022023_1 | module_log_product_PO_1 | harvesting_calendar      | year_2022            | interimPS                         |
      | modCntr_settings_2             | testSettings_08022023_2 | module_log_product_PO_2 | harvesting_calendar      | year_2022            | interimPS                         |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                           | Value                          | ModularContractHandlerType  |
      | modCntr_type_1             | poLine_08022023_1              | poLine_08022023_1              | PurchaseOrderLine_Modular   |
      | modCntr_type_2             | interim_08022023_1             | interim_08022023_1             | Interim_Contract            |
      | modCntr_type_3             | receiptLine_modular_08022023_1 | receiptLine_modular_08022023_1 | MaterialReceiptLine_Modular |
      | modCntr_type_4             | receiptLine_interim_08022023_1 | receiptLine_interim_08022023_1 | MaterialReceiptLine_Interim |
      | modCntr_type_5             | invoiceLine_interim_08022023_1 | invoiceLine_interim_08022023_1 | PurchaseInvoiceLine_Interim |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                   | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_08022023_1  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | moduleTest_08022023_2  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_2             |
      | modCntr_module_3             | 30    | moduleTest_08022023_3  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_3             |
      | modCntr_module_4             | 40    | moduleTest_08022023_4  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_4             |
      | modCntr_module_5             | 10    | moduleTest_08022023_5  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_1             |
      | modCntr_module_6             | 20    | moduleTest_08022023_6  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_2             |
      | modCntr_module_7             | 30    | moduleTest_08022023_7  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_3             |
      | modCntr_module_8             | 40    | moduleTest_08022023_8  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_4             |
      | modCntr_module_9             | 50    | moduleTest_08022023_9  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_5             |
      | modCntr_module_10            | 50    | moduleTest_08022023_10 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_5             |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                    | Name                                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO_1      | moduleLogConditions_po_08022023_1      | ModularContract | interimPS                         | Ex                       | modCntr_settings_1                 |
      | moduleLogConditions_interim_1 | moduleLogConditions_interim_08022023_1 | InterimInvoice  | interimPS                         | Ex                       | modCntr_settings_1                 |
      | moduleLogConditions_PO_2      | moduleLogConditions_po_08022023_2      | ModularContract | interimPS                         | Ex                       | modCntr_settings_2                 |
      | moduleLogConditions_interim_2 | moduleLogConditions_interim_08022023_2 | InterimInvoice  | interimPS                         | Ex                       | modCntr_settings_2                 |

    And set sys config boolean value true for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

    # Set the date at the end of ModCntr_InvoicingGroup interval
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains C_BPartners:
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
      | po_order_1 | false   | bp_interimPO             | 2022-02-01  | POO             | poModuleLogContract_ref_08022023 |

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
      | moduleLogContract_2           | moduleLogConditions_interim_1       | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | InterimInvoice      | Wa                 | CO            |
      | moduleLogContract_3           | moduleLogConditions_PO_2            | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | InterimInvoice      | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_2                 | 2.00            | PCE                       |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_5                 | 2.00            | PCE                       |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_6                 | 2.00            | PCE                       |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_16082023_1   | po_order_1            | po_orderLine_1            | bp_interimPO             | bp_interimPO_Location_1           | module_log_product_PO_1 | 1000       | warehouseStd              |
      | receiptSchedule_PO_16082023_2   | po_order_1            | po_orderLine_2            | bp_interimPO             | bp_interimPO_Location_1           | module_log_product_PO_2 | 500        | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_16082023_1               | hu_16082023_1      | receiptSchedule_PO_16082023_1   | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct_1            | huPackingLU                  |
      | huLuTuConfig_16082023_2               | hu_16082023_2      | receiptSchedule_PO_16082023_2   | N               | 1     | N               | 1     | N               | 50          | huItemPurchaseProduct_2            | huPackingLU                  |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_16082023_1      | receiptSchedule_PO_16082023_1   | material_receipt_1    |
      | hu_16082023_2      | receiptSchedule_PO_16082023_2   | material_receipt_2    |
    Then validate the created material receipt
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.IsInterimInvoiceable |
      | material_receipt_1    | bp_interimPO             | bp_interimPO_Location_1           | 2022-02-01  | true      | CO        | true                     |
      | material_receipt_2    | bp_interimPO             | bp_interimPO_Location_1           | 2022-02-01  | true      | CO        | true                     |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | mr_line_1                 | material_receipt_1    | module_log_product_PO_1 | 100         | true      | po_orderLine_1                | moduleLogContract_1               |
      | mr_line_2                 | material_receipt_2    | module_log_product_PO_2 | 50          | true      | po_orderLine_2                | moduleLogContract_3               |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_2                 | 2.00            | PCE                       |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_5                 | 2.00            | PCE                       |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_6                 | 2.00            | PCE                       |
      | log_5                     | mr_line_1            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_3                 | null            | null                      |
      | log_6                     | mr_line_1            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_2           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_4                 | null            | null                      |
      | log_7                     | mr_line_2            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_3           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_7                 | null            | null                      |
      | log_8                     | mr_line_2            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_8                 | null            | null                      |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus |
      | po_orderLine_1       | C_OrderLine     | SP               |
      | po_orderLine_2       | C_OrderLine     | SP               |
      | mr_line_1            | M_InOutLine     | SP               |
      | mr_line_2            | M_InOutLine     | SP               |
      | moduleLogContract_2  | C_Flatrate_Term | SP               |
      | moduleLogContract_4  | C_Flatrate_Term | SP               |

    And recompute modular logs for record:
      | TableName       | Record_ID.Identifier |
      | C_Flatrate_Term | moduleLogContract_2  |
      | C_Flatrate_Term | moduleLogContract_4  |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | moduleLogContract_2  | C_Flatrate_Term | SP               | 2                   |
      | moduleLogContract_4  | C_Flatrate_Term | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_2                     | moduleLogContract_2  | Interim      | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_4                     | moduleLogContract_4  | Interim      | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType | OPT.IsDefault |
      | AkontorechnungDocType   | API             | DP             | false         |

    # C_BPartner_InterimContract_GenerateInterimInvoice process
    When invoke "createInterimInvoiceCandidatesFor" function:
      | C_BPartner_InterimContract_ID.Identifier | C_Flatrate_Term_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier |
      | bp_interimContractSettings_1             | moduleLogContract_2           | invoiceCand_1                         |
      | bp_interimContractSettings_1             | moduleLogContract_4           | invoiceCand_2                         |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.PriceActual | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.InvoiceRule | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.TableName   | OPT.Record_ID.Identifier | OPT.Processed | OPT.IsInterimInvoice | OPT.IsSOTrx | OPT.NetAmtToInvoice |
      | invoiceCand_1                     | 100          | 100            | 100              | 0               | module_log_product_PO_base  | 2.00            | bp_interimPO                    | bp_interimPO_Location_1         | AkontorechnungDocType       | I               | harvesting_calendar                     | year_2022                         | C_Flatrate_Term | moduleLogContract_2      | false         | true                 | false       | 200                 |
      | invoiceCand_2                     | 50           | 50             | 50               | 0               | module_log_product_PO_base  | 2.00            | bp_interimPO                    | bp_interimPO_Location_1         | AkontorechnungDocType       | I               | harvesting_calendar                     | year_2022                         | C_Flatrate_Term | moduleLogContract_4      | false         | true                 | false       | 100                 |

    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
      | invoiceCand_2                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | akonto_invoice_1        |
      | invoiceCand_2                     | akonto_invoice_2        |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.PriceActual | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.InvoiceRule | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.TableName   | OPT.Record_ID.Identifier | OPT.Processed | OPT.IsInterimInvoice | OPT.IsSOTrx | OPT.NetAmtToInvoice |
      | invoiceCand_1                     | 0            | 100            | 100              | 100             | module_log_product_PO_base  | 2.00            | bp_interimPO                    | bp_interimPO_Location_1         | AkontorechnungDocType       | I               | harvesting_calendar                     | year_2022                         | C_Flatrate_Term | moduleLogContract_2      | true          | true                 | false       | 0                   |
      | invoiceCand_2                     | 0            | 50             | 50               | 50              | module_log_product_PO_base  | 2.00            | bp_interimPO                    | bp_interimPO_Location_1         | AkontorechnungDocType       | I               | harvesting_calendar                     | year_2022                         | C_Flatrate_Term | moduleLogContract_4      | true          | true                 | false       | 0                   |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | akonto_invoice_1        | bp_interimPO             | bp_interimPO_Location_1           | 1000002     | true      | CO        | harvesting_calendar                     | year_2022                         |
      | akonto_invoice_2        | bp_interimPO             | bp_interimPO_Location_1           | 1000002     | true      | CO        | harvesting_calendar                     | year_2022                         |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier    | QtyInvoiced | Processed |
      | akonto_il_1                 | akonto_invoice_1        | module_log_product_PO_base | 100         | true      |
      | akonto_il_2                 | akonto_invoice_2        | module_log_product_PO_base | 50          | true      |

    And after not more than 60s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |                                       | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |                                       | modCntr_module_2                 | 2.00            | PCE                       |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |                                       | modCntr_module_5                 | 2.00            | PCE                       |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |                                       | modCntr_module_6                 | 2.00            | PCE                       |
      | log_5                     | mr_line_1            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |                                       | modCntr_module_3                 | null            | null                      |
      | log_6                     | mr_line_1            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_2           | modCntr_type_4                 | true          | MaterialReceipt              |                            | PCE                   |            | year_2022                         | invoiceCand_1                         | modCntr_module_4                 | null            | null                      |
      | log_7                     | mr_line_2            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_3           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |                                       | modCntr_module_7                 | null            | null                      |
      | log_8                     | mr_line_2            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | true          | MaterialReceipt              |                            | PCE                   |            | year_2022                         | invoiceCand_2                         | modCntr_module_8                 | null            | null                      |
      | log_9                     | akonto_il_1          | Interim         | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | 100  | C_InvoiceLine   | moduleLogContract_2           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | 200        | year_2022                         | invoiceCand_1                         | modCntr_module_9                 | 2.00            | PCE                       |
      | log_10                    | akonto_il_2          | Interim         | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | 50   | C_InvoiceLine   | moduleLogContract_4           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | 100        | year_2022                         | invoiceCand_2                         | modCntr_module_10                | 2.00            | PCE                       |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName     | ProcessingStatus |
      | akonto_il_1          | C_InvoiceLine | SP               |
      | akonto_il_2          | C_InvoiceLine | SP               |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | C_Invoice | akonto_invoice_1     |
      | C_Invoice | akonto_invoice_2     |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName     | ProcessingStatus | OPT.noOfLogStatuses |
      | akonto_il_1          | C_InvoiceLine | SP               | 2                   |
      | akonto_il_2          | C_InvoiceLine | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName     | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_9                     | akonto_il_1          | Interim      | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | 100 | C_InvoiceLine | moduleLogContract_2           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | 200        | year_2022                         | invoiceCand_1                         | modCntr_module_9                 | 2.00            | PCE                       |
      | log_10                    | akonto_il_2          | Interim      | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | 50  | C_InvoiceLine | moduleLogContract_4           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | 100        | year_2022                         | invoiceCand_2                         | modCntr_module_10                | 2.00            | PCE                       |

    When the invoice identified by akonto_invoice_1 is reversed
    And after not more than 60s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier    | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |                                       | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |                                       | modCntr_module_2                 | 2.00            | PCE                       |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |                                       | modCntr_module_5                 | 2.00            | PCE                       |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |                                       | modCntr_module_6                 | 2.00            | PCE                       |
      | log_5                     | mr_line_1            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |                                       | modCntr_module_3                 | null            | null                      |
      | log_6                     | mr_line_1            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1    | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_2           | modCntr_type_4                 | true          | MaterialReceipt              |                            | PCE                   |            | year_2022                         | invoiceCand_1                         | modCntr_module_4                 | null            | null                      |
      | log_7                     | mr_line_2            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_3           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |                                       | modCntr_module_7                 | null            | null                      |
      | log_8                     | mr_line_2            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2    | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | true          | MaterialReceipt              |                            | PCE                   |            | year_2022                         | invoiceCand_2                         | modCntr_module_8                 | null            | null                      |
      | log_9                     | akonto_il_1          | Interim         | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | 100  | C_InvoiceLine   | moduleLogContract_2           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | 200        | year_2022                         | invoiceCand_1                         | modCntr_module_9                 | 2.00            | PCE                       |
      | log_10                    | akonto_il_2          | Interim         | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | 50   | C_InvoiceLine   | moduleLogContract_4           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | 100        | year_2022                         | invoiceCand_2                         | modCntr_module_10                | 2.00            | PCE                       |
      | log_11                    | akonto_il_1          | Interim         | null                                     | bp_interimPO                               | warehouseStd                  | module_log_product_PO_base | bp_interimPO                        | bp_interimPO                    | -100 | C_InvoiceLine   | moduleLogContract_2           | modCntr_type_5                 | false         | InterimInvoice               | EUR                        | PCE                   | -200       | year_2022                         | invoiceCand_1                         | modCntr_module_9                 | 2.00            | PCE                       |

  @Id:S0306_200
  Scenario: purchase modular and interim contract (manually) + logs are created, with C_BPartner_InterimContract
  - validate bp interim contract settings are generated
  - validate modular contract + log created
  - after receipt validate modular receiptLine Log created
  - after create interim contract interim contract + log created
  - validate interim receiptLine Log created
  - validate error on create interim contract create, if overlapping with interim contract for same modular contract

    Given metasfresh contains M_Products:
      | Identifier                 | Name                                  |
      | module_log_product_PO_1    | module_log_product_PO_test_08032023_1 |
      | module_log_product_PO_2    | module_log_product_PO_test_08032023_2 |
      | module_log_product_PO_base | module_log_product_PO_test_08032023_3 |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP_1    | interimPLV_PO                     | module_log_product_PO_1    | 2.00     | PCE               | Normal                        |
      | moduleLogPP_2    | interimPLV_PO                     | module_log_product_PO_2    | 2.00     | PCE               | Normal                        |
      | moduleLogPP_base | interimPLV_PO                     | module_log_product_PO_base | 3.00     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                 |
      | huPackingLU           | huPackingLU_08032023 |
      | huPackingTU           | huPackingTU_08032023 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                      | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU_08032023 | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU_08032023 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 100 | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 100 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier    | Qty  | ValidFrom  |
      | huItemPurchaseProduct_1            | huPiItemTU                 | module_log_product_PO_1    | 1000 | 2022-01-01 |
      | huItemPurchaseProduct_2            | huPiItemTU                 | module_log_product_PO_2    | 1000 | 2022-01-01 |
      | huItemPurchaseProduct_base         | huPiItemTU                 | module_log_product_PO_base | 1000 | 2022-01-01 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                    | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_08032023 | module_log_product_PO_base  | 2021-02-10 | 2023-11-25 |
    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO_1 |
      | invoicingGroup_p2                            | invoicingGroup                       | module_log_product_PO_2 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_08032023_1 | module_log_product_PO_1 | harvesting_calendar      | year_2022            | interimPS                         |
      | modCntr_settings_2             | testSettings_08032023_2 | module_log_product_PO_2 | harvesting_calendar      | year_2022            | interimPS                         |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                           | Value                          | ModularContractHandlerType  |
      | modCntr_type_1             | poLine_08032023_1              | poLine_08032023_1              | PurchaseOrderLine_Modular   |
      | modCntr_type_2             | interim_08032023_1             | interim_08032023_1             | Interim_Contract            |
      | modCntr_type_3             | receiptLine_modular_08032023_1 | receiptLine_modular_08032023_1 | MaterialReceiptLine_Modular |
      | modCntr_type_4             | receiptLine_interim_08032023_1 | receiptLine_interim_08032023_1 | MaterialReceiptLine_Interim |
      | modCntr_type_5             | invoiceLine_interim_08032023_1 | invoiceLine_interim_08032023_1 | PurchaseInvoiceLine_Interim |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                   | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_08032023_1  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | moduleTest_08032023_2  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_2             |
      | modCntr_module_3             | 30    | moduleTest_08032023_3  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_3             |
      | modCntr_module_4             | 40    | moduleTest_08032023_4  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_4             |
      | modCntr_module_5             | 10    | moduleTest_08032023_5  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_1             |
      | modCntr_module_6             | 20    | moduleTest_08032023_6  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_2             |
      | modCntr_module_7             | 30    | moduleTest_08032023_7  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_3             |
      | modCntr_module_8             | 40    | moduleTest_08032023_8  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_4             |
      | modCntr_module_9             | 50    | moduleTest_08032023_9  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_5             |
      | modCntr_module_10            | 50    | moduleTest_08032023_10 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_5             |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                    | Name                                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO_1      | moduleLogConditions_po_08032023_1      | ModularContract | interimPS                         | Ex                       | modCntr_settings_1                 |
      | moduleLogConditions_interim_1 | moduleLogConditions_interim_08032023_1 | InterimInvoice  | interimPS                         | Ex                       | modCntr_settings_1                 |
      | moduleLogConditions_PO_2      | moduleLogConditions_po_08032023_2      | ModularContract | interimPS                         | Ex                       | modCntr_settings_2                 |
      | moduleLogConditions_interim_2 | moduleLogConditions_interim_08032023_2 | InterimInvoice  | interimPS                         | Ex                       | modCntr_settings_2                 |

    And set sys config boolean value false for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

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
      | po_order_1 | false   | bp_interimPO             | 2022-02-01  | POO             | poModuleLogContract_ref_S0306_200 |

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
      | moduleLogContract_2           | moduleLogConditions_PO_2            | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_5                 | 2.00            | PCE                       |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_16082023_1   | po_order_1            | po_orderLine_1            | bp_interimPO             | bp_interimPO_Location_2           | module_log_product_PO_1 | 1000       | warehouseStd              |
      | receiptSchedule_PO_16082023_2   | po_order_1            | po_orderLine_2            | bp_interimPO             | bp_interimPO_Location_2           | module_log_product_PO_2 | 500        | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_16082023_1               | hu_16082023_1      | receiptSchedule_PO_16082023_1   | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct_1            | huPackingLU                  |
      | huLuTuConfig_16082023_2               | hu_16082023_2      | receiptSchedule_PO_16082023_2   | N               | 1     | N               | 1     | N               | 50          | huItemPurchaseProduct_2            | huPackingLU                  |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_16082023_1      | receiptSchedule_PO_16082023_1   | material_receipt_1    |
      | hu_16082023_2      | receiptSchedule_PO_16082023_2   | material_receipt_2    |
    Then validate the created material receipt
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.IsInterimInvoiceable |
      | material_receipt_1    | bp_interimPO             | bp_interimPO_Location_2           | 2022-02-01  | true      | CO        | true                     |
      | material_receipt_2    | bp_interimPO             | bp_interimPO_Location_2           | 2022-02-01  | true      | CO        | true                     |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | shipmentLine_1            | material_receipt_1    | module_log_product_PO_1 | 100         | true      | po_orderLine_1                | moduleLogContract_1               |
      | shipmentLine_2            | material_receipt_2    | module_log_product_PO_2 | 50          | true      | po_orderLine_2                | moduleLogContract_2               |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_5                 | 2.00            | PCE                       |
      | log_3                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_3                 | null            | null                      |
      | log_4                     | shipmentLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine | moduleLogContract_2           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_7                 | null            | null                      |

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
      | moduleLogContract_3           | moduleLogConditions_interim_1       | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | InterimInvoice      | Wa                 | CO            |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | InterimInvoice      | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_1                 | 2.00            | PCE                       |
      | log_2                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_5                 | 2.00            | PCE                       |
      | log_3                     | shipmentLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_3                 | null            | null                      |
      | log_4                     | shipmentLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_2           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_7                 | null            | null                      |
      | log_5                     | moduleLogContract_3  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         | modCntr_module_2                 | 2.00            | PCE                       |
      | log_6                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_2           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         | modCntr_module_6                 | 2.00            | PCE                       |
      | log_7                     | shipmentLine_1       | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_3           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_4                 | null            | null                      |
      | log_8                     | shipmentLine_2       | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         | modCntr_module_8                 | null            | null                      |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | po_orderLine_1       | C_OrderLine     | SP               |                     |
      | po_orderLine_2       | C_OrderLine     | SP               |                     |
      | shipmentLine_1       | M_InOutLine     | SP               | 2                   |
      | shipmentLine_2       | M_InOutLine     | SP               | 2                   |
      | moduleLogContract_3  | C_Flatrate_Term | SP               |                     |
      | moduleLogContract_4  | C_Flatrate_Term | SP               |                     |

    When create interim contract for modular contract with error
      | C_Flatrate_Term_ID.Identifier | DateFrom   | DateTo     | errorCode                                                        |
      | moduleLogContract_1           | 2022-01-01 | 2022-03-31 | de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm |

    And set sys config boolean value true for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete


  @Id:S0314_200
  Scenario: purchase modular with interim contract and interim invoice
  - given there is a configured `ModCntr_InvoicingGroup`
  - the user adds a purchase order and the `invoicingGroup` is applicable
  - validate `C_BPartner_InterimContract` are generated
  - validate interim and modular contract created (interim automatically because of sysconfig)
  - validate interim and modular contract log created
  - after receipt validate interim and modular receiptLine Log created
  - then invoke `Generate Interim Invoice` process
  - validate invoice candidate and interim invoice are generated
  - validate the invoiced product is the one configured on `invoicingGroup`, but the price is inherited from the original product

    Given metasfresh contains M_Products:
      | Identifier                 | Name                                  |
      | module_log_product_PO_1    | module_log_product_PO_test_08042023_1 |
      | module_log_product_PO_2    | module_log_product_PO_test_08042023_2 |
      | module_log_product_PO_base | module_log_product_PO_test_08042023_3 |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP_1    | interimPLV_PO                     | module_log_product_PO_1    | 2.00     | PCE               | Normal                        |
      | moduleLogPP_2    | interimPLV_PO                     | module_log_product_PO_2    | 2.00     | PCE               | Normal                        |
      | moduleLogPP_base | interimPLV_PO                     | module_log_product_PO_base | 3.00     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                 |
      | huPackingLU           | huPackingLU_08042023 |
      | huPackingTU           | huPackingTU_08042023 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                      | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU_08042023 | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU_08042023 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 100 | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 100 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier    | Qty  | ValidFrom  |
      | huItemPurchaseProduct_1            | huPiItemTU                 | module_log_product_PO_1    | 1000 | 2022-01-01 |
      | huItemPurchaseProduct_2            | huPiItemTU                 | module_log_product_PO_2    | 1000 | 2022-01-01 |
      | huItemPurchaseProduct_base         | huPiItemTU                 | module_log_product_PO_base | 1000 | 2022-01-01 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                    | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_08042023 | module_log_product_PO_base  | 2021-02-10 | 2023-11-25 |
    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO_1 |
      | invoicingGroup_p2                            | invoicingGroup                       | module_log_product_PO_2 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_08042023_1 | module_log_product_PO_1 | harvesting_calendar      | year_2022            | interimPS                         |
      | modCntr_settings_2             | testSettings_08042023_2 | module_log_product_PO_2 | harvesting_calendar      | year_2022            | interimPS                         |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                           | Value                          | ModularContractHandlerType  |
      | modCntr_type_1             | poLine_08042023_1              | poLine_08042023_1              | PurchaseOrderLine_Modular   |
      | modCntr_type_2             | interim_08042023_1             | interim_08042023_1             | Interim_Contract            |
      | modCntr_type_3             | receiptLine_modular_08042023_1 | receiptLine_modular_08042023_1 | MaterialReceiptLine_Modular |
      | modCntr_type_4             | receiptLine_interim_08042023_1 | receiptLine_interim_08042023_1 | MaterialReceiptLine_Interim |
      | modCntr_type_5             | invoiceLine_interim_08042023_1 | invoiceLine_interim_08042023_1 | PurchaseInvoiceLine_Interim |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                   | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_08042023_1  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | moduleTest_08042023_2  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_2             |
      | modCntr_module_3             | 30    | moduleTest_08042023_3  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_3             |
      | modCntr_module_4             | 40    | moduleTest_08042023_4  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_4             |
      | modCntr_module_5             | 10    | moduleTest_08042023_5  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_1             |
      | modCntr_module_6             | 20    | moduleTest_08042023_6  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_2             |
      | modCntr_module_7             | 30    | moduleTest_08042023_7  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_3             |
      | modCntr_module_8             | 40    | moduleTest_08042023_8  | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_4             |
      | modCntr_module_9             | 50    | moduleTest_08042023_9  | module_log_product_PO_1 | Kosten         | modCntr_settings_1             | modCntr_type_5             |
      | modCntr_module_10            | 50    | moduleTest_08042023_10 | module_log_product_PO_2 | Kosten         | modCntr_settings_2             | modCntr_type_5             |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                    | Name                                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO_1      | moduleLogConditions_po_08042023_1      | ModularContract | interimPS                         | Ex                       | modCntr_settings_1                 |
      | moduleLogConditions_interim_1 | moduleLogConditions_interim_08042023_1 | InterimInvoice  | interimPS                         | Ex                       | modCntr_settings_1                 |
      | moduleLogConditions_PO_2      | moduleLogConditions_po_08042023_2      | ModularContract | interimPS                         | Ex                       | modCntr_settings_2                 |
      | moduleLogConditions_interim_2 | moduleLogConditions_interim_08042023_2 | InterimInvoice  | interimPS                         | Ex                       | modCntr_settings_2                 |

  # Set the date at the start of ModCntr_InvoicingGroup interval
    And metasfresh has date and time 2022-02-10T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

    And metasfresh contains C_BPartners:
      | Identifier   | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_interimPO | bp_interimPO_09142023 | Y            | N              | interimPS                     | 1000002                    |

    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_interimPO_Location | 0914202312346 | bp_interimPO             | true                | true                |

    When invoke "C_BPartner_InterimContract_Upsert" process:
      | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimPO             | harvesting_calendar                 | year_2022                     | true              |

    Then metasfresh contains C_BPartner_InterimContract:
      | C_BPartner_InterimContract_ID.Identifier | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimContractSettings_1             | bp_interimPO             | harvesting_calendar                 | year_2022                     | true              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                  |
      | po_order_1 | false   | bp_interimPO             | 2022-02-09  | POO             | poModuleLogContract_ref_09142023 |

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
      | moduleLogContract_2           | moduleLogConditions_interim_1       | bp_interimPO                | module_log_product_PO_1 | po_orderLine_1                     | po_order_1                     | PCE                   | 1000                  | 2.00            | interimPS                         | InterimInvoice      | Wa                 | CO            |
      | moduleLogContract_3           | moduleLogConditions_PO_2            | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_4           | moduleLogConditions_interim_2       | bp_interimPO                | module_log_product_PO_2 | po_orderLine_2                     | po_order_1                     | PCE                   | 500                   | 2.00            | interimPS                         | InterimInvoice      | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_16082023_1   | po_order_1            | po_orderLine_1            | bp_interimPO             | bp_interimPO_Location             | module_log_product_PO_1 | 1000       | warehouseStd              |
      | receiptSchedule_PO_16082023_2   | po_order_1            | po_orderLine_2            | bp_interimPO             | bp_interimPO_Location             | module_log_product_PO_2 | 500        | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_16082023_1               | hu_16082023_1      | receiptSchedule_PO_16082023_1   | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct_1            | huPackingLU                  |
      | huLuTuConfig_16082023_2               | hu_16082023_2      | receiptSchedule_PO_16082023_2   | N               | 1     | N               | 1     | N               | 50          | huItemPurchaseProduct_2            | huPackingLU                  |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_16082023_1      | receiptSchedule_PO_16082023_1   | material_receipt_1    |
      | hu_16082023_2      | receiptSchedule_PO_16082023_2   | material_receipt_2    |
    Then validate the created material receipt
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.IsInterimInvoiceable |
      | material_receipt_1    | bp_interimPO             | bp_interimPO_Location             | 2022-02-09  | true      | CO        | true                     |
      | material_receipt_2    | bp_interimPO             | bp_interimPO_Location             | 2022-02-09  | true      | CO        | true                     |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | mr_line_1                 | material_receipt_1    | module_log_product_PO_1 | 100         | true      | po_orderLine_1                | moduleLogContract_1               |
      | mr_line_2                 | material_receipt_2    | module_log_product_PO_2 | 50          | true      | po_orderLine_2                | moduleLogContract_3               |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |
      | log_5                     | mr_line_1            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_6                     | mr_line_1            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_2           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_7                     | mr_line_2            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_3           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |
      | log_8                     | mr_line_2            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus |
      | po_orderLine_1       | C_OrderLine     | SP               |
      | po_orderLine_2       | C_OrderLine     | SP               |
      | mr_line_1            | M_InOutLine     | SP               |
      | mr_line_2            | M_InOutLine     | SP               |
      | moduleLogContract_2  | C_Flatrate_Term | SP               |
      | moduleLogContract_4  | C_Flatrate_Term | SP               |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType | OPT.IsDefault |
      | AkontorechnungDocType   | API             | DP             | false         |

    # C_BPartner_InterimContract_GenerateInterimInvoice process
    When invoke "createInterimInvoiceCandidatesFor" function:
      | C_BPartner_InterimContract_ID.Identifier | C_Flatrate_Term_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier |
      | bp_interimContractSettings_1             | moduleLogContract_2           | invoiceCand_1                         |
      | bp_interimContractSettings_1             | moduleLogContract_4           | invoiceCand_2                         |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
      | invoiceCand_2                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | akonto_invoice_1        |
      | invoiceCand_2                     | akonto_invoice_2        |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.PriceActual | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.C_DocType_ID.Identifier | OPT.InvoiceRule | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.TableName   | OPT.Record_ID.Identifier | OPT.Processed | OPT.IsInterimInvoice | OPT.IsSOTrx |
      | invoiceCand_1                     | 0            | 100            | 100              | 100             | module_log_product_PO_base  | 2.00            | bp_interimPO                    | bp_interimPO_Location           | AkontorechnungDocType       | I               | harvesting_calendar                     | year_2022                         | C_Flatrate_Term | moduleLogContract_2      | true          | true                 | false       |
      | invoiceCand_2                     | 0            | 50             | 50               | 50              | module_log_product_PO_base  | 2.00            | bp_interimPO                    | bp_interimPO_Location           | AkontorechnungDocType       | I               | harvesting_calendar                     | year_2022                         | C_Flatrate_Term | moduleLogContract_4      | true          | true                 | false       |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | akonto_invoice_1        | bp_interimPO             | bp_interimPO_Location             | 1000002     | true      | CO        | harvesting_calendar                     | year_2022                         |
      | akonto_invoice_2        | bp_interimPO             | bp_interimPO_Location             | 1000002     | true      | CO        | harvesting_calendar                     | year_2022                         |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier    | QtyInvoiced | Processed |
      | akonto_il_1                 | akonto_invoice_1        | module_log_product_PO_base | 100         | true      |
      | akonto_il_2                 | akonto_invoice_2        | module_log_product_PO_base | 50          | true      |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier |
      | log_1                     | po_orderLine_1       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year_2022                         |                                       |
      | log_2                     | moduleLogContract_2  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 2000       | year_2022                         |                                       |
      | log_3                     | po_orderLine_2       | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_OrderLine     | moduleLogContract_3           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year_2022                         |                                       |
      | log_4                     | moduleLogContract_4  | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 500  | C_Flatrate_Term | moduleLogContract_3           | modCntr_type_2                 | false         | ContractPrefinancing         | EUR                        | PCE                   | 1000       | year_2022                         |                                       |
      | log_5                     | mr_line_1            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |                                       |
      | log_6                     | mr_line_1            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_1 | bp_interimPO                        | bp_interimPO                    | 100  | M_InOutLine     | moduleLogContract_2           | modCntr_type_4                 | true          | MaterialReceipt              |                            | PCE                   |            | year_2022                         | invoiceCand_1                         |
      | log_7                     | mr_line_2            | ModularContract | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_3           | modCntr_type_3                 | false         | MaterialReceipt              |                            | PCE                   |            | year_2022                         |                                       |
      | log_8                     | mr_line_2            | Interim         | invoicingGroup                           | bp_interimPO                               | warehouseStd                  | module_log_product_PO_2 | bp_interimPO                        | bp_interimPO                    | 50   | M_InOutLine     | moduleLogContract_4           | modCntr_type_4                 | true          | MaterialReceipt              |                            | PCE                   |            | year_2022                         | invoiceCand_2                         |


  @Id:S0314_300
  Scenario: Validate when adding a new `Invoicing group product` an error is thrown, if it overlaps with an existing one for the same product
    Given metasfresh contains M_Products:
      | Identifier                     | Name                                  |
      | module_log_product_base_022022 | module_log_product_base_022022        |
      | module_log_product_PO_1        | module_log_product_PO_test_10032023_1 |
      | module_log_product_PO_2        | module_log_product_PO_test_10032023_2 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                       | Group_Product_ID.Identifier    | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_08042023_10 | module_log_product_base_022022 | 2022-02-10 | 2022-02-25 |
    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO_1 |
      | invoicingGroup_p2                            | invoicingGroup                       | module_log_product_PO_2 |
    And load AD_Message:
      | Identifier                   | Value                                                     |
      | productAlreadyInAnotherGroup | de.metas.contracts.modular.invgroup.ProductInAnotherGroup |

    # overlapping with the existing ModCntr_InvoicingGroup ValidFrom:2022-02-10 / ValidTo:2022-02-25
    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier    | ValidFrom  | ValidTo    |
      | invoicingGroup_base022022            | invoicingGroup_base022022 | module_log_product_base_022022 | 2022-02-24 | 2022-03-24 |
    And the ModCntr_InvoicingGroup_Product is added expecting error:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier | OPT.AD_Message_ID.Identifier |
      | invoicingGroup_base022022_1                  | invoicingGroup_base022022            | module_log_product_PO_1 | productAlreadyInAnotherGroup |
      | invoicingGroup_base022022_2                  | invoicingGroup_base022022            | module_log_product_PO_2 | productAlreadyInAnotherGroup |

  @Id:S0314_400
  Scenario: Validate when changing the dates for an `Invoicing group` an error is thrown, if the period overlaps with an existing one for the same product
    Given metasfresh contains M_Products:
      | Identifier                     | Name                                  |
      | module_log_product_base_032022 | module_log_product_base_032022        |
      | module_log_product_PO_1        | module_log_product_PO_test_10022023_1 |
      | module_log_product_PO_2        | module_log_product_PO_test_10022023_2 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                       | Group_Product_ID.Identifier    | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_08042023_11 | module_log_product_base_032022 | 2022-02-10 | 2022-02-25 |
    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | module_log_product_PO_1 |
      | invoicingGroup_p2                            | invoicingGroup                       | module_log_product_PO_2 |

    And load AD_Message:
      | Identifier                   | Value                                                     |
      | productAlreadyInAnotherGroup | de.metas.contracts.modular.invgroup.ProductInAnotherGroup |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier    | ValidFrom  | ValidTo    |
      | invoicingGroup_base032022            | invoicingGroup_base032022 | module_log_product_base_032022 | 2023-12-01 | 2023-12-30 |
    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_base032022_1                  | invoicingGroup_base032022            | module_log_product_PO_1 |

    # overlapping with the existing ModCntr_InvoicingGroup ValidFrom:2022-02-10 / ValidTo:2022-02-25
    And the ModCntr_InvoicingGroup is updated expecting error:
      | ModCntr_InvoicingGroup_ID.Identifier | ValidFrom  | OPT.AD_Message_ID.Identifier |
      | invoicingGroup_base032022            | 2022-02-20 | productAlreadyInAnotherGroup |