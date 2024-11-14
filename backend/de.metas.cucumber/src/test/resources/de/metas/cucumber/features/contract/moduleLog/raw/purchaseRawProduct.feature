Feature: Modular contract log from purchase order for raw product

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_06032024_1 | moduleLogPricingSystem_06032024_1 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_06032024_1 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_06032024_1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_PO_06032024_1 | 2021-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_SO_06032024_1 | 2021-02-01 |

    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_1                | 001   |

  @from:cucumber
  @Id:S0458_100
  @ghActions:run_on_executor4
  Scenario: raw product with the following computing methods:
  - InformativeLogs
  - Definitive invoice
  - Receipt
  - SalesOnRawProduct
  - AddValueOnRawProduct (both Service and Costs)
  - SubtractValueOnRawProduct (both Service and Costs)
  - Add Value on interim
  - Subtract Value on interim
  - Storage cost
  No interim setup, sold less than received, difference solved via inventory down, so definitive invoice is positive.

    Given metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_06032024_1 | Y            | N              | moduleLogPricingSystem        | 1000002                    |
      | bp_moduleLogSO | bp_moduleLogSO_06032024_1 | N            | Y              | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803198505483 | bp_moduleLogPO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_06032024_1      | warehouse_06032024_1 | warehouse_06032024_1 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_06032024_1 | warehouse_06032024_1      |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And load M_Shipper:
      | Identifier | Name |
      | shipper_1  | Dhl  |

    And load DD_NetworkDistribution:
      | DD_NetworkDistribution_ID.Identifier | Value   |
      | ddNetwork_isHUDestroyed              | Gebinde |

    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_isHUDestroyed              | warehouseStd              | warehouse_06032024_1            | shipper_1               |

    And load default C_Calendar from metasfresh as harvesting_calendar

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | final                   | API             | FI             |
      | definitive_cm           | APC             | DCM            |

    And metasfresh contains M_Products:
      | Identifier               | Name                                   |
      | rawProduct               | rawProduct_06032024                    |
      | addValueOnRaw_PO         | addValueOnRaw_PO_06032024              |
      | addValueOnRaw_PO_2       | addValueOnRaw_PO_2_06032024            |
      | subtractValueOnRaw_PO    | subtractValueOnRaw_PO_06032024         |
      | subtractValueOnRaw_PO_2  | subtractValueOnRaw_PO_2_06032024       |
      | addValueOnInterim        | addValueOnInterim_test_06032024        |
      | subValueOnInterim        | subValueOnInterim_test_06032024        |
      | storageCostForRawProduct | storageCostForRawProduct_test_06032024 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | ValidFrom  | ValidTo    | Harvesting_Year_ID.Identifier | TotalInterest | C_Currency.ISO_Code |
      | invGroup                             | invoicingGroup_06032024_1 | 2021-01-01 | 2030-12-31 | y2022                         | 40            | EUR                 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invGroup                             | rawProduct              |

    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.Name |
      | moduleLogPP_1 | moduleLogPLV_PO                   | rawProduct               | 10.00    | PCE               | Normale MWSt          |
      | moduleLogPP_2 | moduleLogPLV_PO                   | subtractValueOnRaw_PO    | 7.00     | PCE               | Normale MWSt          |
      | moduleLogPP_3 | moduleLogPLV_PO                   | subtractValueOnRaw_PO_2  | 9.00     | PCE               | Normale MWSt          |
      | moduleLogPP_4 | moduleLogPLV_PO                   | addValueOnRaw_PO         | 8.00     | PCE               | Normale MWSt          |
      | moduleLogPP_5 | moduleLogPLV_PO                   | addValueOnRaw_PO_2       | 6.00     | PCE               | Normale MWSt          |
      | moduleLogPP_6 | moduleLogPLV_PO                   | addValueOnInterim        | 10.00    | PCE               | Normale MWSt          |
      | moduleLogPP_7 | moduleLogPLV_PO                   | subValueOnInterim        | 10.00    | PCE               | Normale MWSt          |
      | moduleLogPP_8 | moduleLogPLV_PO                   | storageCostForRawProduct | 0.06     | PCE               | Normale MWSt          |
      | moduleLogPP_9 | moduleLogPLV_SO                   | rawProduct               | 20.00    | PCE               | Normale MWSt          |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | C_Year_ID.Identifier | M_PricingSystem_ID.Identifier | OPT.StorageCostStartDate |
      | modCntr_settings_1             | testSettings_06032024_1 | rawProduct                  | y2022                | moduleLogPricingSystem        | 2022-02-01               |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | ModularContractHandlerType  |
      | modCntr_type_0             | InformativeLogs             |
      | modCntr_type_1             | DefinitiveInvoiceRawProduct |
      | modCntr_type_2             | Receipt                     |
      | modCntr_type_3             | SalesOnRawProduct           |
      | modCntr_type_4             | AddValueOnRawProduct        |
      | modCntr_type_5             | SubtractValueOnRawProduct   |
      | modCntr_type_6             | AddValueOnInterim           |
      | modCntr_type_7             | SubtractValueOnInterim      |
      | modCntr_type_8             | StorageCost                 |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                                 | M_Product_ID.Identifier  | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | informative_module           | 0     | informative_06032024_1               | rawProduct               | Service        | modCntr_settings_1             | modCntr_type_0             |
      | definitive_module            | 0     | definitive_06032024_1                | rawProduct               | Service        | modCntr_settings_1             | modCntr_type_1             |
      | receipt_module               | 10    | receipt_06032024_1                   | rawProduct               | Service        | modCntr_settings_1             | modCntr_type_2             |
      | salesOnRawProduct_module     | 20    | salesOnRawProduct_06032024_1         | rawProduct               | Service        | modCntr_settings_1             | modCntr_type_3             |
      | avOnRawProduct_module_1      | 30    | addValueOnRawProduct_06032024_1      | addValueOnRaw_PO         | Service        | modCntr_settings_1             | modCntr_type_4             |
      | svOnRawProduct_module_1      | 40    | subtractValueOnRawProduct_06032024_1 | subtractValueOnRaw_PO    | Service        | modCntr_settings_1             | modCntr_type_5             |
      | svOnRawProduct_module_2      | 50    | subtractValueOnRawProduct_06032024_1 | subtractValueOnRaw_PO_2  | Costs          | modCntr_settings_1             | modCntr_type_5             |
      | avOnRawProduct_module_2      | 60    | addValueOnRawProduct_06032024_1      | addValueOnRaw_PO_2       | Costs          | modCntr_settings_1             | modCntr_type_4             |
      | avOnInterim_module           | 70    | addValueOnInterim_06032024_1         | addValueOnInterim        | Costs          | modCntr_settings_1             | modCntr_type_6             |
      | svOnInterim_module           | 80    | subValueOnInterim_06032024_1         | subValueOnInterim        | Costs          | modCntr_settings_1             | modCntr_type_7             |
      | storageCost_module           | 90    | storageCost_06032024_1               | storageCostForRawProduct | Costs          | modCntr_settings_1             | modCntr_type_8             |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                     | Name                           | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_06032024_1 | moduleLogConditions_06032024_1 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-02-01  | POO             | poModuleLogContract_ref_06292023_2 | warehouse_06032024_1          | y2022                             |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | rawProduct              | 1000       | moduleLogConditions_06032024_1          |

    When the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_06032024_1      | rawProduct              | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus | OPT.IsSOTrx |
      | moduleLogContract_1           | moduleLogConditions_06032024_1      | bp_moduleLogPO              | rawProduct              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            | N           |

    And after not more than 30s, ModCntr_Specific_Prices are found:
      | ModCntr_Specific_Price_ID.Identifier | C_Flatrate_Term_ID.Identifier | ModCntr_Module_ID.Identifier | M_Product_ID.Identifier  | OPT.SeqNo | OPT.Price | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.IsScalePrice | OPT.MinValue |
      | price_0                              | moduleLogContract_1           | definitive_module            | rawProduct               | 0         | 10.00     | EUR                        | PCE                   | N                | 0            |
      | price_20                             | moduleLogContract_1           | salesOnRawProduct_module     | rawProduct               | 20        | 10.00     | EUR                        | PCE                   | N                | 0            |
      | price_30                             | moduleLogContract_1           | avOnRawProduct_module_1      | addValueOnRaw_PO         | 30        | 8.00      | EUR                        | PCE                   | N                | 0            |
      | price_40                             | moduleLogContract_1           | svOnRawProduct_module_1      | subtractValueOnRaw_PO    | 40        | 7.00      | EUR                        | PCE                   | N                | 0            |
      | price_50                             | moduleLogContract_1           | svOnRawProduct_module_2      | subtractValueOnRaw_PO_2  | 50        | 9.00      | EUR                        | PCE                   | N                | 0            |
      | price_60                             | moduleLogContract_1           | avOnRawProduct_module_2      | addValueOnRaw_PO_2       | 60        | 6.00      | EUR                        | PCE                   | N                | 0            |
      | price_70                             | moduleLogContract_1           | avOnInterim_module           | addValueOnInterim        | 70        | 10.00     | EUR                        | PCE                   | N                | 0            |
      | price_80                             | moduleLogContract_1           | svOnInterim_module           | subValueOnInterim        | 80        | 10.00     | EUR                        | PCE                   | N                | 0            |
      | price_90                             | moduleLogContract_1           | storageCost_module           | storageCostForRawProduct | 90        | 0.06      | EUR                        | PCE                   | N                | 0            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | y2022                             | informative_module               | 10.00           | PCE                       | informative_06032024_1       | N              |
      | poLog_1                   | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseModularContract      | EUR                        | PCE                   | 10000      | y2022                             | informative_module               | 10.00           | PCE                       | informative_06032024_1       | N              |
      | log_sv                    | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_6             | false         | PurchaseModularContract      | EUR                        | PCE                   | 7500       | y2022                             | avOnInterim_module               | 7.5             | PCE                       | addValueOnInterim_06032024_1 | Y              |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_PO              | po_order              | po_orderLine              | bp_moduleLogPO           | bp_moduleLogPO_Location           | rawProduct              | 1000       | warehouse_06032024_1      | moduleLogContract_1               |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_1   | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 200         | 101                                | 1000006                      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.MovementDate |
      | processedTopHU_1   | receiptSchedule_PO              | inOut_06032024_1      | 2022-02-15       |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | inOut_06032024_1      | rawProduct              | 200         | true      | 200            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                 | OPT.IsBillable |
      | log_3                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | addValueOnRaw_PO        | bp_moduleLogPO                      | bp_moduleLogPO                  | 200 | M_InOutLine | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | 1600       | y2022                             | avOnRawProduct_module_1          | 8.00            | PCE                       | addValueOnRawProduct_06032024_1 | Y              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_2   | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 800         | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU_2   | receiptSchedule_PO              | inOut_06032024_2      |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | inOut_06032024_2      | rawProduct              | 800         | true      | 800            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                      | OPT.IsBillable |
      | log_receipt2_1            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | addValueOnRaw_PO        | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | 6400       | y2022                             | avOnRawProduct_module_1          | 8.00            | PCE                       | addValueOnRawProduct_06032024_1      | Y              |
      | log_receipt2_2            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_2             | false         | MaterialReceipt              | EUR                        | PCE                   | 0          | y2022                             | receipt_module                   | 0               | PCE                       | receipt_06032024_1                   | Y              |
      | log_receipt2_3            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_3             | false         | MaterialReceipt              | EUR                        | PCE                   | 8000       | y2022                             | salesOnRawProduct_module         | 10.00           | PCE                       | salesOnRawProduct_06032024_1         | Y              |
      | log_receipt2_4            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | subtractValueOnRaw_PO   | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_5             | false         | MaterialReceipt              | EUR                        | PCE                   | -5600      | y2022                             | svOnRawProduct_module_1          | -7.00           | PCE                       | subtractValueOnRawProduct_06032024_1 | Y              |
      | log_receipt2_5            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | addValueOnRaw_PO_2      | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_4             | false         | MaterialReceipt              | EUR                        | PCE                   | -4800      | y2022                             | avOnRawProduct_module_2          | -6.00           | PCE                       | addValueOnRawProduct_06032024_1      | Y              |
      | log_receipt2_6            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | subtractValueOnRaw_PO_2 | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_5             | false         | MaterialReceipt              | EUR                        | PCE                   | 7200       | y2022                             | svOnRawProduct_module_2          | 9.00            | PCE                       | subtractValueOnRawProduct_06032024_1 | Y              |
      | log_receipt2_7            | receiptLine_2        | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 800 | M_InOutLine | moduleLogContract_1           | modCntr_type_1             | false         | MaterialReceipt              | EUR                        | PCE                   | 8000       | y2022                             | definitive_module                | 10.00           | PCE                       | salesOnRawProduct_06032024_1         | Y              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | so_1       | true    | bp_moduleLogSO           | 2022-02-15  | soModuleLogContract_ref_06032024_1 | warehouse_06032024_1          | y2022                             | locator                     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_1                  | rawProduct              | 950        |

    And the order identified by so_1 is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | sch_1      | soLine_1                  | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | so_1                  | 2022-03-04            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | Harvesting_Year_ID.Identifier |
      | shippingNotification_06032024_1       | so_1                  | CO        | bp_moduleLogSO               | org_1                | locator                 | y2022                         |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_06032024_1       | shippingNotification_06032024_1       | sch_1                            | rawProduct              | 950         |

    Then after not more than 60s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier                | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName                   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable |
      | log_avInterim_snline      | shippingNotificationLine_06032024_1 | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | addValueOnInterim       | bp_moduleLogPO                      | bp_moduleLogPO                  | 950 | M_Shipping_NotificationLine | moduleLogContract_1           | modCntr_type_6             | false         | ShippingNotification         | EUR                        | PCE                   | -9500      | y2022                             | avOnInterim_module               | -10.00          | PCE                       | addValueOnInterim_06032024_1 | Y              |
      | log_svInterim_snline      | shippingNotificationLine_06032024_1 | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | subValueOnInterim       | bp_moduleLogPO                      | bp_moduleLogPO                  | 950 | M_Shipping_NotificationLine | moduleLogContract_1           | modCntr_type_7             | false         | ShippingNotification         | EUR                        | PCE                   | 9500       | y2022                             | svOnInterim_module               | 10.00           | PCE                       | subValueOnInterim_06032024_1 | Y              |

    And  'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | sch_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | sch_1                            | inout_1               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered | OPT.Harvesting_Year_ID.Identifier |
      | so_ioline_1               | inout_1               | rawProduct              | 950         | true      | 950            | y2022                             |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable |
      | log_inout_1_1             | so_ioline_1          | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct               | bp_moduleLogPO                      | bp_moduleLogPO                  | 950 | M_InOutLine | moduleLogContract_1           | modCntr_type_1             | false         | Shipment                     | EUR                        | PCE                   | 19000      | y2022                             | definitive_module                | 20.00           | PCE                       | salesOnRawProduct_06032024_1 | Y              |
      | log_inout_1_2             | so_ioline_1          | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | storageCostForRawProduct | bp_moduleLogPO                      | bp_moduleLogPO                  | 950 | M_InOutLine | moduleLogContract_1           | modCntr_type_8             | false         | Shipment                     | EUR                        | PCE                   | -1710      | y2022                             | storageCost_module               | -0.06           | PCE                       | storageCost_06032024_1       | Y              |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | metasfresh_user       | metasfresh |

    And distribute interest
      | AD_User_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | InterimDate | BillingDate |
      | metasfresh_user       | invGroup                             | 2022-02-15  | 2022-04-20  |

    And load latest ModCntr_Interest_Run for invoicing group invGroup as lastInterestRun

    And validate created interestRun records for lastInterestRun
      | ModCntr_Interest_ID.Identifier | FinalInterest | C_Currency.ISO_Code | InterestDays | ShippingNotification_ModCntr_Log_ID.Identifier | OPT.InterimContract_ModCntr_Log_ID.Identifier |
      | interest_1                     | 40            | EUR                 | 19           | log_avInterim_snline                           | log_sv                                        |


    And create final invoice
      | C_Flatrate_Term_ID.Identifier | AD_User_ID.Identifier | OPT.DateInvoiced | OPT.DateAcct |
      | moduleLogContract_1           | metasfresh_user       | 2022-03-01       | 2022-03-01   |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier     | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier  | ProductName                          |
      | candidate_receipt                     | moduleLogContract_1           | rawProduct               | receipt_06032024_1                   |
      | candidate_salesOnRawProduct           | moduleLogContract_1           | rawProduct               | salesOnRawProduct_06032024_1         |
      | candidate_addValueOnRawProduct_1      | moduleLogContract_1           | addValueOnRaw_PO         | addValueOnRawProduct_06032024_1      |
      | candidate_subtractValueOnRawProduct_1 | moduleLogContract_1           | subtractValueOnRaw_PO    | subtractValueOnRawProduct_06032024_1 |
      | candidate_addValueOnRawProduct_2      | moduleLogContract_1           | addValueOnRaw_PO_2       | addValueOnRawProduct_06032024_1      |
      | candidate_subtractValueOnRawProduct_2 | moduleLogContract_1           | subtractValueOnRaw_PO_2  | subtractValueOnRawProduct_06032024_1 |
      | candidate_addValueOnInterim           | moduleLogContract_1           | addValueOnInterim        | addValueOnInterim_06032024_1         |
      | candidate_subValueOnInterim           | moduleLogContract_1           | subValueOnInterim        | subValueOnInterim_06032024_1         |
      | candidate_storageCost                 | moduleLogContract_1           | storageCostForRawProduct | storageCost_06032024_1               |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier     | OPT.QtyToInvoice |
      | candidate_receipt                     | 0                |
      | candidate_salesOnRawProduct           | 0                |
      | candidate_addValueOnRawProduct_1      | 0                |
      | candidate_subtractValueOnRawProduct_1 | 0                |
      | candidate_addValueOnRawProduct_2      | 0                |
      | candidate_subtractValueOnRawProduct_2 | 0                |
      | candidate_addValueOnInterim           | 0                |
      | candidate_subValueOnInterim           | 0                |
      | candidate_storageCost                 | 0                |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier     | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.InvoiceRule | OPT.PriceActual | OPT.NetAmtToInvoice | OPT.NetAmtInvoiced | OPT.Processed |
      | candidate_receipt                     | 0            | 1000           | 1000             | I               | 0               | 0                   | 0                  | Y             |
      | candidate_salesOnRawProduct           | 0            | 1000           | 1000             | I               | 10              | 0                   | 10000              | Y             |
      | candidate_addValueOnRawProduct_1      | 0            | 1000           | 1000             | I               | 8               | 0                   | 8000               | Y             |
      | candidate_subtractValueOnRawProduct_1 | 0            | 1000           | 1000             | I               | -7              | 0                   | -7000              | Y             |
      | candidate_addValueOnRawProduct_2      | 0            | 1000           | 1000             | I               | -6              | 0                   | -6000              | Y             |
      | candidate_subtractValueOnRawProduct_2 | 0            | 1000           | 1000             | I               | 9               | 0                   | 9000               | Y             |
      | candidate_addValueOnInterim           | 0            | 1              | 1                | I               | -40             | 0                   | -40                | Y             |
      | candidate_subValueOnInterim           | 0            | 0              | 0                | I               | 0               | 0                   | 0                  | N             |
      | candidate_storageCost                 | 0            | 1              | 1                | I               | -1710           | 0                   | -1710              | Y             |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus | OPT.TotalLines |
      | candidate_receipt                 | invoice_1               | CO            | 12250          |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | bp_moduleLogPO           | bp_moduleLogPO_Location           | 1000002     | true      | CO        | 14577.50       | final                       |

    And validate created modular invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier  | ProductName                          | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | invoiceLine_1_1             | invoice_1               | rawProduct               | receipt_06032024_1                   | 1000        | true      | 0                | 0               | 0              | PCE                   | PCE                       |
      | invoiceLine_1_2             | invoice_1               | rawProduct               | salesOnRawProduct_06032024_1         | 1000        | true      | 10               | 10              | 10000          | PCE                   | PCE                       |
      | invoiceLine_1_3             | invoice_1               | addValueOnRaw_PO         | addValueOnRawProduct_06032024_1      | 1000        | true      | 8                | 8               | 8000           | PCE                   | PCE                       |
      | invoiceLine_1_4             | invoice_1               | subtractValueOnRaw_PO_2  | subtractValueOnRawProduct_06032024_1 | 1000        | true      | 9                | 9               | 9000           | PCE                   | PCE                       |
      | invoiceLine_1_5             | invoice_1               | subtractValueOnRaw_PO    | subtractValueOnRawProduct_06032024_1 | 1000        | true      | -7               | -7              | -7000          | PCE                   | PCE                       |
      | invoiceLine_1_6             | invoice_1               | addValueOnRaw_PO_2       | addValueOnRawProduct_06032024_1      | 1000        | true      | -6               | -6              | -6000          | PCE                   | PCE                       |
      | invoiceLine_1_7             | invoice_1               | addValueOnInterim        | addValueOnInterim_06032024_1         | 1           | true      | -40              | -40             | -40            | PCE                   | PCE                       |
      | invoiceLine_1_8             | invoice_1               | storageCostForRawProduct | storageCost_06032024_1               | 1           | true      | -1710            | -1710           | -1710          | PCE                   | PCE                       |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID       | MovementDate | OPT.DocumentNo |
      | inv_1                     | warehouse_06032024_1 | 2022-03-15   | inv_06032024_1 |

    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier | OPT.M_HU_ID.Identifier |
      | inv_1_l1                      | inv_1                     | rawProduct              | PCE          | 0        | 50      | moduleLogContract_1                     | processedTopHU_2       |

    And the inventory identified by inv_1 is completed

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable |
      | log_inv_1                 | inv_1_l1             | ModularContract | bp_moduleLogPO                             | warehouse_06032024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | -50 | M_InventoryLine | moduleLogContract_1           | modCntr_type_1             | false         | Inventory                    | EUR                        | PCE                   | -950       | y2022                             | definitive_module                | 19.00           | PCE                       | salesOnRawProduct_06032024_1 | Y              |

    And create definitive invoice
      | C_Flatrate_Term_ID.Identifier | AD_User_ID.Identifier | OPT.DateInvoiced | OPT.DateAcct |
      | moduleLogContract_1           | metasfresh_user       | 2022-04-01       | 2022-04-01   |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier | ProductName                  | OPT.QtyOrdered |
      | candidate_definitive              | moduleLogContract_1           | rawProduct              | salesOnRawProduct_06032024_1 | 50             |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | candidate_definitive              | 0                |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.InvoiceRule | OPT.PriceActual | OPT.NetAmtToInvoice | OPT.NetAmtInvoiced | OPT.Processed |
      | candidate_definitive              | 0            | 50             | 50               | I               | -19             | 0                   | -950               | Y             |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus | OPT.TotalLines |
      | candidate_definitive              | invoice_2               | CO            | 950            |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_2               | bp_moduleLogPO           | bp_moduleLogPO_Location           | 1000002     | true      | CO        | 1130.5         | definitive_cm               |

    And validate created modular invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | ProductName                  | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | invoiceLine_2_1             | invoice_2               | rawProduct              | salesOnRawProduct_06032024_1 | 50          | true      | -19              | 19              | 950            | PCE                   | PCE                       |
