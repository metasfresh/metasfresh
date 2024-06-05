Feature: Modular contract log from purchase order for raw product

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_05272024_1 | moduleLogPricingSystem_05272024_1 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_05272024_1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | moduleLogPLV | moduleLogPL_PO            | moduleLogPLV_05272024_1 | 2021-02-01 |

    And load AD_Ref_Lists:
      | AD_Ref_List_ID.Identifier | Value           |
      | list_1                    | InformativeLogs |

    And update AD_Ref_Lists:
      | AD_Ref_List_ID.Identifier | IsActive |
      | list_1                    | Y        |


  @from:cucumber
  Scenario: raw product with the following computing methods:
  - InformativeLogs
  - Receipt
  - SalesOnRawProduct
  - AddValueOnRawProduct (both Service and Costs)
  - SubtractValueOnRawProduct (both Service and Costs)

    Given metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_05272024_1 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803198505483 | bp_moduleLogPO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_05272024_1      | warehouse_05272024_1 | warehouse_05272024_1 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_05272024_1 | warehouse_05272024_1      |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And load DD_NetworkDistribution:
      | DD_NetworkDistribution_ID.Identifier | Value   |
      | ddNetwork_isHUDestroyed              | Gebinde |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_isHUDestroyed              | warehouseStd              | warehouse_05272024_1            | shipper_1               |

    And metasfresh contains C_Calendar
      | C_Calendar_ID.Identifier | Name            |
      | calendar_1               | test_05272024_1 |

    And metasfresh contains C_Year
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | calendar_1               |

    And metasfresh contains C_Period
      | C_Period_ID.Identifier | C_Year_ID.Identifier | Name            | StartDate  | OPT.EndDate | OPT.PeriodNo |
      | period_1               | y2022                | test_05272024_1 | 2022-01-01 | 2022-12-31  | 0            |

    And metasfresh contains M_Products:
      | Identifier              | Name                                  |
      | rawProduct              | module_log_product_PO_test_05272024_1 |
      | addValueOnRaw_PO        | module_log_product_PO_test_05272024_3 |
      | subtractValueOnRaw_PO   | module_log_product_PO_test_05272024_4 |
      | subtractValueOnRaw_PO_2 | module_log_product_PO_test_06032024_1 |
      | addValueOnRaw_PO_2      | module_log_product_PO_test_06032024_2 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP_1 | moduleLogPLV                      | rawProduct              | 10.00    | PCE               | Normal                        |
      | moduleLogPP_2 | moduleLogPLV                      | subtractValueOnRaw_PO_2 | 9.00     | PCE               | Normal                        |
      | moduleLogPP_3 | moduleLogPLV                      | addValueOnRaw_PO        | 8.00     | PCE               | Normal                        |
      | moduleLogPP_4 | moduleLogPLV                      | subtractValueOnRaw_PO   | 7.00     | PCE               | Normal                        |
      | moduleLogPP_5 | moduleLogPLV                      | addValueOnRaw_PO_2      | 6.00     | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_05272024_1 | rawProduct                  | calendar_1               | y2022                | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                                 | Value                                | ModularContractHandlerType |
      | modCntr_type_0             | informative_05272024_1               | informative_05272024_1               | InformativeLogs            |
      | modCntr_type_1             | receipt_05272024_1                   | receipt_05272024_1                   | Receipt                    |
      | modCntr_type_2             | salesOnRawProduct_05272024_1         | salesOnRawProduct_05272024_1         | SalesOnRawProduct          |
      | modCntr_type_3             | addValueOnRawProduct_05272024_1      | addValueOnRawProduct_05272024_1      | AddValueOnRawProduct       |
      | modCntr_type_4             | subtractValueOnRawProduct_05272024_1 | subtractValueOnRawProduct_05272024_1 | SubtractValueOnRawProduct  |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                                 | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_0             | 0     | informative_05272024_1               | rawProduct              | Service        | modCntr_settings_1             | modCntr_type_0             |
      | modCntr_module_1             | 10    | receipt_05272024_1                   | rawProduct              | Service        | modCntr_settings_1             | modCntr_type_1             |
      | modCntr_module_2             | 20    | salesOnRawProduct_05272024_1         | rawProduct              | Service        | modCntr_settings_1             | modCntr_type_2             |
      | modCntr_module_3             | 30    | addValueOnRawProduct_05272024_1      | addValueOnRaw_PO        | Service        | modCntr_settings_1             | modCntr_type_3             |
      | modCntr_module_4             | 40    | subtractValueOnRawProduct_05272024_1 | subtractValueOnRaw_PO   | Service        | modCntr_settings_1             | modCntr_type_4             |
      | modCntr_module_5             | 50    | subtractValueOnRawProduct_06032024_1 | subtractValueOnRaw_PO_2 | Costs          | modCntr_settings_1             | modCntr_type_4             |
      | modCntr_module_6             | 60    | addValueOnRawProduct_06032024_1      | addValueOnRaw_PO_2      | Costs          | modCntr_settings_1             | modCntr_type_3             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                           | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_05272024_1      | moduleLogConditions_05272024_1 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.C_Harvesting_Calendar_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_06292023_2 | warehouse_05272024_1          | y2022                             | calendar_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | rawProduct              | 1000       | moduleLogConditions_05272024_1          |

    When the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_05272024_1      | rawProduct              | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_05272024_1      | bp_moduleLogPO              | rawProduct              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Specific_Prices are found:
      | ModCntr_Specific_Price_ID.Identifier | C_Flatrate_Term_ID.Identifier | ModCntr_Module_ID.Identifier | M_Product_ID.Identifier | OPT.SeqNo | OPT.Price | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.IsScalePrice | OPT.MinValue |
      | price_1                              | moduleLogContract_1           | modCntr_module_2             | rawProduct              | 20        | 10.00     | EUR                        | PCE                   | N                | 0            |
      | price_2                              | moduleLogContract_1           | modCntr_module_3             | addValueOnRaw_PO        | 30        | 8.00      | EUR                        | PCE                   | N                | 0            |
      | price_3                              | moduleLogContract_1           | modCntr_module_4             | subtractValueOnRaw_PO   | 40        | 7.00      | EUR                        | PCE                   | N                | 0            |
      | price_4                              | moduleLogContract_1           | modCntr_module_5             | subtractValueOnRaw_PO_2 | 50        | 9.00      | EUR                        | PCE                   | N                | 0            |
      | price_5                              | moduleLogContract_1           | modCntr_module_6             | addValueOnRaw_PO_2      | 60        | 6.00      | EUR                        | PCE                   | N                | 0            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | y2022                             | modCntr_module_0                 | 10.00           | PCE                       |
      | poLog_1                   | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseModularContract      | EUR                        | PCE                   | 10000      | y2022                             | modCntr_module_0                 | 10.00           | PCE                       |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_PO              | po_order              | po_orderLine              | bp_moduleLogPO           | bp_moduleLogPO_Location           | rawProduct              | 1000       | warehouse_05272024_1      | moduleLogContract_1               |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100   | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | inOut_05272024_1      |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | inOut_05272024_1      | rawProduct              | 100         | true      | 100            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                      | OPT.IsBillable |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | y2022                             | modCntr_module_0                 | 10.00           | PCE                       | informative_05272024_1               | N              |
      | log_2                     | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseModularContract      | EUR                        | PCE                   | 10000      | y2022                             | modCntr_module_0                 | 10.00           | PCE                       | informative_05272024_1               | N              |
      | log_3                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | addValueOnRaw_PO        | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | 800        | y2022                             | modCntr_module_3                 | 8.00            | PCE                       | addValueOnRawProduct_05272024_1      | Y              |
      | log_4                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_1             | false         | MaterialReceipt              | EUR                        | PCE                   | 0          | y2022                             | modCntr_module_1                 | 0               | PCE                       | receipt_05272024_1                   | Y              |
      | log_5                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_2             | false         | MaterialReceipt              | EUR                        | PCE                   | 1000       | y2022                             | modCntr_module_2                 | 10.00           | PCE                       | salesOnRawProduct_05272024_1         | Y              |
      | log_6                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | subtractValueOnRaw_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | -700       | y2022                             | modCntr_module_4                 | -7.00           | PCE                       | subtractValueOnRawProduct_05272024_1 | Y              |
      | log_7                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | addValueOnRaw_PO_2      | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | -600       | y2022                             | modCntr_module_6                 | -6.00           | PCE                       | addValueOnRawProduct_06032024_1      | Y              |
      | log_8                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | subtractValueOnRaw_PO_2 | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | 900        | y2022                             | modCntr_module_5                 | 9.00            | PCE                       | subtractValueOnRawProduct_06032024_1 | Y              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 200   | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | inOut_05272024_2      |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | inOut_05272024_2      | rawProduct              | 200         | true      | 200            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                      | OPT.IsBillable |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | y2022                             | modCntr_module_0                 | 10.00           | PCE                       | informative_05272024_1               | N              |
      | log_2                     | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseModularContract      | EUR                        | PCE                   | 10000      | y2022                             | modCntr_module_0                 | 10.00           | PCE                       | informative_05272024_1               | N              |
      | log_3                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | addValueOnRaw_PO        | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | 800        | y2022                             | modCntr_module_3                 | 8.00            | PCE                       | addValueOnRawProduct_05272024_1      | Y              |
      | log_4                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_1             | false         | MaterialReceipt              | EUR                        | PCE                   | 0          | y2022                             | modCntr_module_1                 | 0               | PCE                       | receipt_05272024_1                   | Y              |
      | log_5                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_2             | false         | MaterialReceipt              | EUR                        | PCE                   | 1000       | y2022                             | modCntr_module_2                 | 10.00           | PCE                       | salesOnRawProduct_05272024_1         | Y              |
      | log_6                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | subtractValueOnRaw_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | -700       | y2022                             | modCntr_module_4                 | -7.00           | PCE                       | subtractValueOnRawProduct_05272024_1 | Y              |
      | log_7                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | addValueOnRaw_PO_2      | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | -600       | y2022                             | modCntr_module_6                 | -6.00           | PCE                       | addValueOnRawProduct_06032024_1      | Y              |
      | log_8                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | subtractValueOnRaw_PO_2 | bp_moduleLogPO                      | bp_moduleLogPO                  | 100  | M_InOutLine     | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | 900        | y2022                             | modCntr_module_5                 | 9.00            | PCE                       | subtractValueOnRawProduct_06032024_1 | Y              |
      | log_9                     | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | addValueOnRaw_PO        | bp_moduleLogPO                      | bp_moduleLogPO                  | 200  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | 1600       | y2022                             | modCntr_module_3                 | 8.00            | PCE                       | addValueOnRawProduct_05272024_1      | Y              |
      | log_10                    | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 200  | M_InOutLine     | moduleLogContract_1           | modCntr_type_1             | false         | MaterialReceipt              | EUR                        | PCE                   | 0          | y2022                             | modCntr_module_1                 | 0               | PCE                       | receipt_05272024_1                   | Y              |
      | log_11                    | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 200  | M_InOutLine     | moduleLogContract_1           | modCntr_type_2             | false         | MaterialReceipt              | EUR                        | PCE                   | 2000       | y2022                             | modCntr_module_2                 | 10.00           | PCE                       | salesOnRawProduct_05272024_1         | Y              |
      | log_12                    | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | subtractValueOnRaw_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 200  | M_InOutLine     | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | -1400      | y2022                             | modCntr_module_4                 | -7.00           | PCE                       | subtractValueOnRawProduct_05272024_1 | Y              |
      | log_13                    | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | addValueOnRaw_PO_2      | bp_moduleLogPO                      | bp_moduleLogPO                  | 200  | M_InOutLine     | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | -1200      | y2022                             | modCntr_module_6                 | -6.00           | PCE                       | addValueOnRawProduct_06032024_1      | Y              |
      | log_14                    | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_05272024_1          | subtractValueOnRaw_PO_2 | bp_moduleLogPO                      | bp_moduleLogPO                  | 200  | M_InOutLine     | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | 1800       | y2022                             | modCntr_module_5                 | 9.00            | PCE                       | subtractValueOnRawProduct_06032024_1 | Y              |

    And create final invoice
      | C_Flatrate_Term_ID.Identifier | UserLogin  |
      | moduleLogContract_1           | metasfresh |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier | ProductName                          |
      | candidate_1                       | moduleLogContract_1           | rawProduct              | receipt_05272024_1                   |
      | candidate_2                       | moduleLogContract_1           | rawProduct              | salesOnRawProduct_05272024_1         |
      | candidate_3                       | moduleLogContract_1           | addValueOnRaw_PO        | addValueOnRawProduct_05272024_1      |
      | candidate_4                       | moduleLogContract_1           | subtractValueOnRaw_PO   | subtractValueOnRawProduct_05272024_1 |
      | candidate_5                       | moduleLogContract_1           | addValueOnRaw_PO_2      | addValueOnRawProduct_06032024_1      |
      | candidate_6                       | moduleLogContract_1           | subtractValueOnRaw_PO_2 | subtractValueOnRawProduct_06032024_1 |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | candidate_1                       | 0                |
      | candidate_2                       | 0                |
      | candidate_3                       | 0                |
      | candidate_4                       | 0                |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.InvoiceRule | OPT.PriceActual | OPT.NetAmtToInvoice | OPT.NetAmtInvoiced | OPT.Processed |
      | candidate_1                       | 0            | 300            | 300              | I               | 0               | 0                   | 0                  | Y             |
      | candidate_2                       | 0            | 300            | 300              | I               | 10              | 0                   | 3000               | Y             |
      | candidate_3                       | 0            | 300            | 300              | I               | 8               | 0                   | 2400               | Y             |
      | candidate_4                       | 0            | 300            | 300              | I               | -7              | 0                   | -2100              | Y             |
      | candidate_5                       | 0            | 300            | 300              | I               | -6              | 0                   | -1800              | Y             |
      | candidate_6                       | 0            | 300            | 300              | I               | 9               | 0                   | 2700               | Y             |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus | OPT.TotalLines |
      | candidate_1                       | invoice_1               | CO            | 4200           |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal |
      | invoice_1               | bp_moduleLogPO           | bp_moduleLogPO_Location           | 1000002     | true      | CO        | 4998           |
    And validate created modular invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | ProductName                          | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | invoiceLine_1_1             | invoice_1               | rawProduct              | receipt_05272024_1                   | 300         | true      | 0                | 0               | 0              | PCE                   | PCE                       |
      | invoiceLine_1_2             | invoice_1               | rawProduct              | salesOnRawProduct_05272024_1         | 300         | true      | 10               | 10              | 3000           | PCE                   | PCE                       |
      | invoiceLine_1_3             | invoice_1               | addValueOnRaw_PO        | addValueOnRawProduct_05272024_1      | 300         | true      | 8                | 8               | 2400           | PCE                   | PCE                       |
      | invoiceLine_1_4             | invoice_1               | subtractValueOnRaw_PO_2 | subtractValueOnRawProduct_06032024_1 | 300         | true      | 9                | 9               | 2700           | PCE                   | PCE                       |
      | invoiceLine_1_5             | invoice_1               | subtractValueOnRaw_PO   | subtractValueOnRawProduct_05272024_1 | 300         | true      | -7               | -7              | -2100          | PCE                   | PCE                       |
      | invoiceLine_1_6             | invoice_1               | addValueOnRaw_PO_2      | addValueOnRawProduct_06032024_1      | 300         | true      | -6               | -6              | -1800          | PCE                   | PCE                       |

    And update AD_Ref_Lists:
      | AD_Ref_List_ID.Identifier | IsActive |
      | list_1                    | N        |
    