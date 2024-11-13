Feature: Modular contract log from purchase order for processed product

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

    And metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_06062024_1 | moduleLogPricingSystem_06062024_1 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_06062024_1 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_06062024_1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_PO_06062024_1 | 2021-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_SO_06062024_1 | 2021-02-01 |

    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_1                | 001   |

  @from:cucumber
  @Id:S0459_100
  @ghActions:run_on_executor4
  Scenario: processed product with the following computing methods:
  - InformativeLogs
  - Definitive invoice
  - Receipt
  - Interim Contract
  - SalesOnProcessedProduct
  - AddValueOnProcessedProduct (both Service and Costs)
  - Add Value on interim
  - Subtract Value on interim
  - Storage cost
  No interim invoice created, sold more than received so definitive invoice will be a Credit Memo.

    Given metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value | OPT.CompanyName           |
      | bp_moduleLogPO | bp_moduleLogPO_06062024_1 | Y            | N              | moduleLogPricingSystem        | 1000002                    | bp_moduleLogPO_06062024_1 |
      | bp_moduleLogSO | bp_moduleLogSO_06062024_1 | N            | Y              | moduleLogPricingSystem        | 1000002                    | bp_moduleLogPO_06062024_1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803198505483 | bp_moduleLogPO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_06062024_1      | warehouse_06062024_1 | warehouse_06062024_1 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_06062024_1 | warehouse_06062024_1      |

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
      | ddNetworkLine_1                          | ddNetwork_isHUDestroyed              | warehouseStd              | warehouse_06062024_1            | shipper_1               |

    And load default C_Calendar from metasfresh as harvesting_calendar

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | final                   | API             | FI             |
      | definitive              | API             | DS             |
      | interim                 | API             | DP             |

    And metasfresh contains M_Products:
      | Identifier                     | Name                                         |
      | rawProduct                     | rawProduct_06062024                          |
      | processedProduct               | processedProduct_06062024                    |
      | interimProduct                 | interimProduct_06062024                      |
      | addValueOnProcessed_PO         | addValueOnProcessed_PO_06062024              |
      | addValueOnProcessed_PO_2       | addValueOnProcessed_PO_2_06062024            |
      | addValueOnInterim              | addValueOnInterim_test_06062024              |
      | subValueOnInterim              | subValueOnInterim_test_06062024              |
      | storageCostForProcessedProduct | storageCostForProcessedProduct_test_06062024 |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | ValidFrom  | ValidTo    | Harvesting_Year_ID.Identifier | TotalInterest | C_Currency.ISO_Code |
      | invGroup                             | invoicingGroup_06062024_1 | 2021-01-01 | 2030-12-31 | y2022                         | 40            | EUR                 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invGroup                             | rawProduct              |

    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier        | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.Name |
      | moduleLogPP_0 | moduleLogPLV_PO                   | rawProduct                     | 10.00    | PCE               | Normale MWSt          |
      | moduleLogPP_1 | moduleLogPLV_PO                   | processedProduct               | 20.00    | PCE               | Normale MWSt          |
      | moduleLogPP_1 | moduleLogPLV_PO                   | interimProduct                 | 20.00    | PCE               | Normale MWSt          |
      | moduleLogPP_4 | moduleLogPLV_PO                   | addValueOnProcessed_PO         | 8.00     | PCE               | Normale MWSt          |
      | moduleLogPP_5 | moduleLogPLV_PO                   | addValueOnProcessed_PO_2       | 6.00     | PCE               | Normale MWSt          |
      | moduleLogPP_6 | moduleLogPLV_PO                   | addValueOnInterim              | 20.00    | PCE               | Normale MWSt          |
      | moduleLogPP_7 | moduleLogPLV_PO                   | subValueOnInterim              | 20.00    | PCE               | Normale MWSt          |
      | moduleLogPP_8 | moduleLogPLV_PO                   | storageCostForProcessedProduct | 0.06     | PCE               | Normale MWSt          |
      | moduleLogPP_9 | moduleLogPLV_SO                   | processedProduct               | 20.00    | PCE               | Normale MWSt          |

    #Manufacturing setup
    And metasfresh contains PP_Product_BOM
      | Identifier           | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_processedProduct | processedProduct        | 2021-01-02 | bomVersions_manufacturing            |

    And metasfresh contains PP_Product_BOMLines
      | Identifier              | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | IsQtyPercentage | OPT.ComponentType |
      | bom_l_processedProduct1 | bom_processedProduct         | rawProduct              | 2021-01-02 | 100      | true            | CO                |

    And the PP_Product_BOM identified by bom_processedProduct is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |

    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |

    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty  | ValidFrom  |
      | huItemManufacturingProduct         | huPiItemTU                 | processedProduct        | 1000 | 2021-01-01 |

    #ModCntr setup
    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | M_Processed_Product_ID.Identifier | C_Year_ID.Identifier | M_PricingSystem_ID.Identifier | OPT.StorageCostStartDate | OPT.InterestRate |
      | modCntr_settings_1             | testSettings_06062024_1 | rawProduct                  | processedProduct                  | y2022                | moduleLogPricingSystem        | 2022-02-01               | 0.5              |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | ModularContractHandlerType        |
      | modCntr_type_0             | InformativeLogs                   |
      | modCntr_type_1             | DefinitiveInvoiceProcessedProduct |
      | modCntr_type_2             | Receipt                           |
      | modCntr_type_3             | SalesOnProcessedProduct           |
      | modCntr_type_4             | AddValueOnProcessedProduct        |
      | modCntr_type_5             | AddValueOnInterim                 |
      | modCntr_type_6             | SubtractValueOnInterim            |
      | modCntr_type_7             | StorageCost                       |
      | modCntr_type_8             | Interim                           |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier   | SeqNo | Name                                  | M_Product_ID.Identifier        | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | informative_module             | 0     | informative_06062024_1                | processedProduct               | Service        | modCntr_settings_1             | modCntr_type_0             |
      | definitive_module              | 0     | definitive_06062024_1                 | processedProduct               | Service        | modCntr_settings_1             | modCntr_type_1             |
      | receipt_module                 | 10    | receipt_06062024_1                    | rawProduct                     | Service        | modCntr_settings_1             | modCntr_type_2             |
      | interimContract_module         | 20    | interimProduct_06062024               | interimProduct                 | Service        | modCntr_settings_1             | modCntr_type_8             |
      | salesOnProcessedProduct_module | 30    | salesOnProcessedProduct_06062024_1    | processedProduct               | Service        | modCntr_settings_1             | modCntr_type_3             |
      | avOnProcessedProduct_module_1  | 40    | addValueOnProcessedProduct_06062024_1 | addValueOnProcessed_PO         | Service        | modCntr_settings_1             | modCntr_type_4             |
      | avOnProcessedProduct_module_2  | 50    | addValueOnProcessedProduct_06062024_2 | addValueOnProcessed_PO_2       | Costs          | modCntr_settings_1             | modCntr_type_4             |
      | avOnInterim_module             | 60    | addValueOnInterim_06062024_1          | addValueOnInterim              | Costs          | modCntr_settings_1             | modCntr_type_5             |
      | svOnInterim_module             | 70    | subValueOnInterim_06062024_1          | subValueOnInterim              | Costs          | modCntr_settings_1             | modCntr_type_6             |
      | storageCost_module             | 80    | storageCost_06062024_1                | storageCostForProcessedProduct | Costs          | modCntr_settings_1             | modCntr_type_7             |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                             | Name                                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_06062024_1         | moduleLogConditions_06062024_1         | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 | CO            |
      | moduleLogConditions_interim_06062024_1 | moduleLogConditions_interim_06062024_1 | InterimInvoice  | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 | CO            |

    #Interim contract setup
    And invoke "C_BPartner_InterimContract_Upsert" process:
      | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_moduleLogPO           | harvesting_calendar                 | y2022                         | true              |

    And metasfresh contains C_BPartner_InterimContract:
      | C_BPartner_InterimContract_ID.Identifier | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimContractSettings_1             | bp_moduleLogPO           | harvesting_calendar                 | y2022                         | true              |

    #Modular contract
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-02-01  | POO             | poModuleLogContract_ref_06292023_2 | warehouse_06062024_1          | y2022                             |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | rawProduct              | 1000       | moduleLogConditions_06062024_1          |

    When the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier    | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_06062024_1         | rawProduct              | po_order                       | po_orderLine                       |
      | interimContract_1             | moduleLogConditions_interim_06062024_1 | rawProduct              | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus | OPT.IsSOTrx |
      | moduleLogContract_1           | moduleLogConditions_06062024_1      | bp_moduleLogPO              | rawProduct              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            | N           |
      | interimContract_1             | moduleLogConditions_06062024_1      | bp_moduleLogPO              | rawProduct              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 7.50            | moduleLogPricingSystem            | InterimInvoice      | Wa                 | CO            | N           |

    And after not more than 30s, ModCntr_Specific_Prices are found:
      | ModCntr_Specific_Price_ID.Identifier | C_Flatrate_Term_ID.Identifier | ModCntr_Module_ID.Identifier   | M_Product_ID.Identifier        | OPT.SeqNo | OPT.Price | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.IsScalePrice | OPT.MinValue |
      | price_0                              | moduleLogContract_1           | definitive_module              | processedProduct               | 0         | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_20                             | moduleLogContract_1           | salesOnProcessedProduct_module | processedProduct               | 30        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_30                             | moduleLogContract_1           | avOnProcessedProduct_module_1  | addValueOnProcessed_PO         | 40        | 8.00      | EUR                        | PCE                   | N                | 0            |
      | price_40                             | moduleLogContract_1           | avOnProcessedProduct_module_2  | addValueOnProcessed_PO_2       | 50        | 6.00      | EUR                        | PCE                   | N                | 0            |
      | price_50                             | moduleLogContract_1           | avOnInterim_module             | addValueOnInterim              | 60        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_60                             | moduleLogContract_1           | svOnInterim_module             | subValueOnInterim              | 70        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_70                             | moduleLogContract_1           | storageCost_module             | storageCostForProcessedProduct | 80        | 0.06      | EUR                        | PCE                   | N                | 0            |
      | price_10                             | interimContract_1             | interimContract_module         | rawProduct                     | 20        | 7.50      | EUR                        | PCE                   | N                | 0            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | y2022                             | informative_module               | 10.00           | PCE                       | informative_06062024_1       | N              |
      | log_2                     | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_0             | false         | PurchaseModularContract      | EUR                        | PCE                   | 10000      | y2022                             | informative_module               | 10.00           | PCE                       | informative_06062024_1       | N              |
      | log_sv                    | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | modCntr_type_5             | false         | PurchaseModularContract      | EUR                        | PCE                   | 7500       | y2022                             | avOnInterim_module               | 7.5             | PCE                       | addValueOnInterim_06062024_1 | Y              |
      | log_ic                    | interimContract_1    | Interim         | bp_moduleLogPO                             | warehouse_06062024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | interimContract_1             | modCntr_type_8             | false         | ContractPrefinancing         | EUR                        | PCE                   | 7500       | y2022                             | interimContract_module           | 7.5             | PCE                       | interimProduct_06062024      | N              |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_PO              | po_order              | po_orderLine              | bp_moduleLogPO           | bp_moduleLogPO_Location           | rawProduct              | 1000       | warehouse_06062024_1      | moduleLogContract_1               |

    #Material Receipts
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | rawTopHU_1         | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 1000        | 101                                | 1000006                      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.MovementDate |
      | rawTopHU_1         | receiptSchedule_PO              | inOut_06062024_1      | 2022-02-15       |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | inOut_06062024_1      | rawProduct              | 1000        | true      | 1000           |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName    | OPT.IsBillable |
      | log_1                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_2             | false         | MaterialReceipt              | EUR                        | PCE                   | 0          | y2022                             | receipt_module                   | 0.00            | PCE                       | receipt_06062024_1 | Y              |

    #Manufacturing order
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.Modular_Flatrate_Term_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppo_1                  | MMO         | processedProduct        | 1000       | testResource             | 2022-02-28T23:59:00.00Z | 2022-02-28T23:59:00.00Z | 2022-02-28T23:59:00.00Z | N                | moduleLogContract_1                     | warehouse_06062024_1          |

    And after not more than 60s, PP_Order_BomLines are found
      | Identifier       | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBOMLine_1 | ppo_1                  | rawProduct              | 1000         | true            | PCE               | CO            |

    And the manufacturing order identified by ppo_1 is completed

    And select M_HU to be issued for productionOrder
      | M_HU_ID.Identifier | PP_Order_Qty_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.MovementDate        |
      | rawTopHU_1         | pp_order_qty_1             | ppOrderBOMLine_1               | 2022-02-28T23:59:00.00Z |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | PP_Order_ID.Identifier | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig                          | ppo_1                  | processedProductTU | N               | 0     | N               | 1     | N               | 1000        | huItemManufacturingProduct         |

    And complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppo_1                  |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.HUStatus |
      | processedProductTU | A            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                       | OPT.IsBillable |
      | log_1                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | addValueOnProcessed_PO_2 | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | modCntr_type_4             | false         | Production                   | EUR                        | PCE                   | -6000      | y2022                             | avOnProcessedProduct_module_2    | -6.00           | PCE                       | addValueOnProcessedProduct_06062024_2 | Y              |
      | log_2                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | addValueOnProcessed_PO   | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | modCntr_type_4             | false         | Production                   | EUR                        | PCE                   | 8000       | y2022                             | avOnProcessedProduct_module_1    | 8.00            | PCE                       | addValueOnProcessedProduct_06062024_1 | Y              |
      | log_3                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | processedProduct         | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | modCntr_type_1             | false         | Production                   | EUR                        | PCE                   | 20000      | y2022                             | definitive_module                | 20.00           | PCE                       | salesOnProcessedProduct_06062024_1    | Y              |
      | log_4                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | processedProduct         | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | modCntr_type_3             | false         | Production                   | EUR                        | PCE                   | 20000      | y2022                             | salesOnProcessedProduct_module   | 20.00           | PCE                       | salesOnProcessedProduct_06062024_1    | Y              |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | ppOrder_CostCollector_1         | ppo_1                  | processedProduct        | 1000        | CO        | MaterialReceipt   |
      | ppOrder_CostCollector_2         | ppo_1                  | rawProduct              | 1000        | CO        | MaterialReceipt   |

    #SO
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | so_1       | true    | bp_moduleLogSO           | 2022-02-15  | soModuleLogContract_ref_06062024_1 | warehouse_06062024_1          | y2022                             | locator                     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_1                  | processedProduct        | 1050       |

    And the order identified by so_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | sch_1      | soLine_1                  | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | so_1                  | 2022-03-04            |

    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | Harvesting_Year_ID.Identifier |
      | shippingNotification_06062024_1       | so_1                  | CO        | bp_moduleLogSO               | org_1                | locator                 | y2022                         |

    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_06062024_1       | shippingNotification_06062024_1       | sch_1                            | processedProduct        | 1050        |

    And after not more than 60s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier                | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName                   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable |
      | log_avInterim_snline      | shippingNotificationLine_06062024_1 | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | addValueOnInterim       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1050 | M_Shipping_NotificationLine | moduleLogContract_1           | modCntr_type_5             | false         | ShippingNotification         | EUR                        | PCE                   | -21000     | y2022                             | avOnInterim_module               | -20.00          | PCE                       | addValueOnInterim_06062024_1 | Y              |
      | log_svInterim_snline      | shippingNotificationLine_06062024_1 | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | subValueOnInterim       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1050 | M_Shipping_NotificationLine | moduleLogContract_1           | modCntr_type_6             | false         | ShippingNotification         | EUR                        | PCE                   | 21000      | y2022                             | svOnInterim_module               | 20.00           | PCE                       | subValueOnInterim_06062024_1 | Y              |

    And  'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | sch_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | sch_1                            | inout_1               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered | OPT.Harvesting_Year_ID.Identifier |
      | so_ioline_1               | inout_1               | processedProduct        | 1000        | true      | 1000           | y2022                             |
      | so_ioline_2               | inout_1               | processedProduct        | 50          | true      | 50             | y2022                             |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                    | OPT.IsBillable |
      | log_inout_1_1             | so_ioline_1          | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | processedProduct               | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_1             | false         | Shipment                     | EUR                        | PCE                   | 20000      | y2022                             | definitive_module                | 20.00           | PCE                       | salesOnProcessedProduct_06062024_1 | Y              |
      | log_inout_1_2             | so_ioline_1          | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | storageCostForProcessedProduct | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_InOutLine | moduleLogContract_1           | modCntr_type_7             | false         | Shipment                     | EUR                        | PCE                   | -1800      | y2022                             | storageCost_module               | -0.06           | PCE                       | storageCost_06062024_1             | Y              |
      | log_inout_2_1             | so_ioline_2          | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | processedProduct               | bp_moduleLogPO                      | bp_moduleLogPO                  | 50   | M_InOutLine | moduleLogContract_1           | modCntr_type_1             | false         | Shipment                     | EUR                        | PCE                   | 1000       | y2022                             | definitive_module                | 20.00           | PCE                       | salesOnProcessedProduct_06062024_1 | Y              |
      | log_inout_2_2             | so_ioline_2          | ModularContract | bp_moduleLogPO                             | warehouse_06062024_1          | storageCostForProcessedProduct | bp_moduleLogPO                      | bp_moduleLogPO                  | 50   | M_InOutLine | moduleLogContract_1           | modCntr_type_7             | false         | Shipment                     | EUR                        | PCE                   | -90        | y2022                             | storageCost_module               | -0.06           | PCE                       | storageCost_06062024_1             | Y              |

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
      | interest_2                     | -18.96        | EUR                 | 65           | log_svInterim_snline                           |                                               |

    And create final invoice
      | C_Flatrate_Term_ID.Identifier | AD_User_ID.Identifier | OPT.DateInvoiced | OPT.DateAcct |
      | moduleLogContract_1           | metasfresh_user       | 2022-03-01       | 2022-03-01   |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier      | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier        | ProductName                           |
      | candidate_receipt                      | moduleLogContract_1           | rawProduct                     | receipt_06062024_1                    |
      | candidate_salesOnProcessedProduct      | moduleLogContract_1           | processedProduct               | salesOnProcessedProduct_06062024_1    |
      | candidate_addValueOnProcessedProduct_1 | moduleLogContract_1           | addValueOnProcessed_PO         | addValueOnProcessedProduct_06062024_1 |
      | candidate_addValueOnProcessedProduct_2 | moduleLogContract_1           | addValueOnProcessed_PO_2       | addValueOnProcessedProduct_06062024_2 |
      | candidate_addValueOnInterim            | moduleLogContract_1           | addValueOnInterim              | addValueOnInterim_06062024_1          |
      | candidate_subValueOnInterim            | moduleLogContract_1           | subValueOnInterim              | subValueOnInterim_06062024_1          |
      | candidate_storageCost                  | moduleLogContract_1           | storageCostForProcessedProduct | storageCost_06062024_1                |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier      | OPT.QtyToInvoice |
      | candidate_receipt                      | 0                |
      | candidate_salesOnProcessedProduct      | 0                |
      | candidate_addValueOnProcessedProduct_1 | 0                |
      | candidate_addValueOnProcessedProduct_2 | 0                |
      | candidate_addValueOnInterim            | 0                |
      | candidate_subValueOnInterim            | 0                |
      | candidate_storageCost                  | 0                |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier      | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.InvoiceRule | OPT.PriceActual | OPT.NetAmtToInvoice | OPT.NetAmtInvoiced | OPT.Processed |
      | candidate_receipt                      | 0            | 1000           | 1000             | I               | 0               | 0                   | 0                  | Y             |
      | candidate_salesOnProcessedProduct      | 0            | 1000           | 1000             | I               | 20              | 0                   | 20000              | Y             |
      | candidate_addValueOnProcessedProduct_1 | 0            | 1000           | 1000             | I               | 8               | 0                   | 8000               | Y             |
      | candidate_addValueOnProcessedProduct_2 | 0            | 1000           | 1000             | I               | -6              | 0                   | -6000              | Y             |
      | candidate_addValueOnInterim            | 0            | 1              | 1                | I               | -40             | 0                   | -40                | Y             |
      | candidate_subValueOnInterim            | 0            | 1              | 1                | I               | 18.96           | 0                   | 18.96              | Y             |
      | candidate_storageCost                  | 0            | 1              | 1                | I               | -1890           | 0                   | -1890              | Y             |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus | OPT.TotalLines |
      | candidate_receipt                 | invoice_1               | CO            | 20088.96       |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | invoice_1               | bp_moduleLogPO           | bp_moduleLogPO_Location           | 1000002     | true      | CO        | 23905.86       | final                       |

    And validate created modular invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier        | ProductName                           | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | invoiceLine_1_1             | invoice_1               | rawProduct                     | receipt_06062024_1                    | 1000        | true      | 0                | 0               | 0              | PCE                   | PCE                       |
      | invoiceLine_1_2             | invoice_1               | processedProduct               | salesOnProcessedProduct_06062024_1    | 1000        | true      | 20               | 20              | 20000          | PCE                   | PCE                       |
      | invoiceLine_1_3             | invoice_1               | addValueOnProcessed_PO         | addValueOnProcessedProduct_06062024_1 | 1000        | true      | 8                | 8               | 8000           | PCE                   | PCE                       |
      | invoiceLine_1_4             | invoice_1               | addValueOnProcessed_PO_2       | addValueOnProcessedProduct_06062024_2 | 1000        | true      | -6               | -6              | -6000          | PCE                   | PCE                       |
      | invoiceLine_1_5             | invoice_1               | addValueOnInterim              | addValueOnInterim_06062024_1          | 1           | true      | -40              | -40             | -40            | PCE                   | PCE                       |
      | invoiceLine_1_6             | invoice_1               | subValueOnInterim              | subValueOnInterim_06062024_1          | 1           | true      | 18.96            | 18.96           | 18.96          | PCE                   | PCE                       |
      | invoiceLine_1_7             | invoice_1               | storageCostForProcessedProduct | storageCost_06062024_1                | 1           | true      | -1890            | -1890           | -1890          | PCE                   | PCE                       |

    And create definitive invoice
      | C_Flatrate_Term_ID.Identifier | AD_User_ID.Identifier | OPT.DateInvoiced | OPT.DateAcct |
      | moduleLogContract_1           | metasfresh_user       | 2022-04-01       | 2022-04-01   |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier | ProductName                        | OPT.QtyOrdered |
      | candidate_definitive              | moduleLogContract_1           | processedProduct        | salesOnProcessedProduct_06062024_1 | 50             |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | candidate_definitive              | 0                |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.InvoiceRule | OPT.PriceActual | OPT.NetAmtToInvoice | OPT.NetAmtInvoiced | OPT.Processed |
      | candidate_definitive              | 0            | 50             | 50               | I               | 19              | 0                   | 950                | Y             |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus | OPT.TotalLines |
      | candidate_definitive              | defInv                  | CO            | 950            |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | defInv                  | bp_moduleLogPO           | bp_moduleLogPO_Location           | 1000002     | true      | CO        | 1130.5         | definitive                  |

    And validate created modular invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | ProductName                        | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | invoiceLine_2_1             | defInv                  | processedProduct        | salesOnProcessedProduct_06062024_1 | 50          | true      | 19.00            | 19.00           | 950            | PCE                   | PCE                       |
