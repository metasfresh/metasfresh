Feature: Modular contract log from sales order for processed product

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.contracts..modular.InterimContractCreateAutomaticallyOnModularContractComplete

    And metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_03102024_1 | moduleLogPricingSystem_03102024_1 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_03102024_1 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_03102024_1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_PO_03102024_1 | 2021-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_SO_03102024_1 | 2021-02-01 |

    And load AD_Ref_Lists:
      | AD_Ref_List_ID.Identifier | Value           |
      | list_1                    | InformativeLogs |

    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_1                | 001   |

  @from:cucumber
  @Id:S0459_200
  @Id:S0462_100
  @ghActions:run_on_executor4
  Scenario: Purchase modular contract for processed product with the following computing methods:
  - InformativeLogs
  - Definitive invoice
  - Receipt
  - Interim Contract
  - SalesOnProcessedProduct
  - AddValueOnProcessedProduct (both Service and Costs)
  - Add Value on interim
  - Subtract Value on interim
  - Storage cost

  Sales Modular contract for processed product with the following computing methods
  - InformativeLogs
  - Sales
  - HL
  - Protein
  - Add Value on sales
  - Storage cost
    Given metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value | OPT.CompanyName           |
      | bp_moduleLogPO | bp_moduleLogPO_03102024_1 | Y            | N              | moduleLogPricingSystem        | 1000002                    | bp_moduleLogPO_03102024_1 |
      | bp_moduleLogSO | bp_moduleLogSO_03102024_1 | N            | Y              | moduleLogPricingSystem        | 1000002                    | bp_moduleLogPO_03102024_1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803198505483 | bp_moduleLogPO           | true                | true                |
      | bp_moduleLogSO_Location | 5803198505484 | bp_moduleLogSO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_03102024_1      | warehouse_03102024_1 | warehouse_03102024_1 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_03102024_1 | warehouse_03102024_1      |

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
      | ddNetworkLine_1                          | ddNetwork_isHUDestroyed              | warehouseStd              | warehouse_03102024_1            | shipper_1               |

    And load default C_Calendar from metasfresh as harvesting_calendar

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | final                   | API             | FI             |
      | finalSO                 | ARI             | FI             |
      | definitive              | API             | DS             |

    And metasfresh contains M_Products:
      | Identifier                     | Name                                         |
      | rawProduct                     | rawProduct_03102024                          |
      | processedProduct               | processedProduct_03102024                    |
      | interimProduct                 | interimProduct_03102024                      |
      | addValueOnProcessed_PO         | addValueOnProcessed_PO_03102024              |
      | addValueOnProcessed_PO_2       | addValueOnProcessed_PO_2_03102024            |
      | addValueOnInterim              | addValueOnInterim_test_03102024              |
      | subValueOnInterim              | subValueOnInterim_test_03102024              |
      | HL                             | HL_03102024                                  |
      | Protein                        | Protein_03102024                             |
      | storageCostForProcessedProduct | storageCostForProcessedProduct_test_03102024 |
      | salesAV                        | salesAV_test_03102024                        |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | ValidFrom  | ValidTo    | Harvesting_Year_ID.Identifier | TotalInterest | C_Currency.ISO_Code |
      | invGroup                             | invoicingGroup_03102024_1 | 2021-01-01 | 2030-12-31 | y2022                         | 40            | EUR                 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invGroup                             | rawProduct              |

    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier        | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.Name | OPT.UseScalePrice | OPT.ScalePriceQuantityFrom |
      | moduleLogPP_0  | moduleLogPLV_PO                   | rawProduct                     | 10.00    | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_1  | moduleLogPLV_PO                   | processedProduct               | 20.00    | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_1  | moduleLogPLV_PO                   | interimProduct                 | 20.00    | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_4  | moduleLogPLV_PO                   | addValueOnProcessed_PO         | 8.00     | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_5  | moduleLogPLV_PO                   | addValueOnProcessed_PO_2       | 6.00     | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_6  | moduleLogPLV_PO                   | addValueOnInterim              | 20.00    | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_7  | moduleLogPLV_PO                   | subValueOnInterim              | 20.00    | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_8  | moduleLogPLV_PO                   | storageCostForProcessedProduct | 0.06     | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_9  | moduleLogPLV_SO                   | storageCostForProcessedProduct | 0.1      | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_10 | moduleLogPLV_SO                   | processedProduct               | 20.00    | PCE               | Normale MWSt          | N                 | Q                          |
      | moduleLogPP_11 | moduleLogPLV_SO                   | HL                             | 0.00     | PCE               | Normale MWSt          | S                 | UEN1                       |
      | moduleLogPP_12 | moduleLogPLV_SO                   | Protein                        | 0.00     | PCE               | Normale MWSt          | S                 | UEN2                       |
      | moduleLogPP_13 | moduleLogPLV_SO                   | salesAV                        | 0.2      | PCE               | Normale MWSt          | N                 | Q                          |

    And metasfresh contains M_ProductScalePrices:
      | Identifier | M_ProductPrice_ID | PriceLimit | PriceList | PriceStd | Qty |
      | ps_1       | moduleLogPP_11    | 0          | 0         | -0.2     | 0   |
      | ps_2       | moduleLogPP_11    | 0          | 0         | 0        | 50  |
      | ps_3       | moduleLogPP_11    | 0          | 0         | 0.25     | 100 |
      | ps_4       | moduleLogPP_12    | 0          | 0         | -0.1     | 0   |
      | ps_5       | moduleLogPP_12    | 0          | 0         | 0        | 2   |
      | ps_6       | moduleLogPP_12    | 0          | 0         | 0.2      | 4   |

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
      | ModCntr_Settings_ID.Identifier | Name                       | M_Raw_Product_ID.Identifier | M_Processed_Product_ID.Identifier | C_Year_ID.Identifier | M_PricingSystem_ID.Identifier | OPT.StorageCostStartDate | OPT.InterestRate | OPT.IsSOTrx | OPT.FreeStorageCostDays |
      | modcntr_settings_PO_1          | testSettings_03102024_PO_1 | rawProduct                  | processedProduct                  | y2022                | moduleLogPricingSystem        | 2022-02-01               | 0.5              | N           |                         |
      | modcntr_settings_SO_1          | testSettings_03102024_SO_1 | processedProduct            |                                   | y2022                | moduleLogPricingSystem        |                          |                  | Y           | 20                      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | ModularContractHandlerType        | OPT.ColumnName     |
      | info_logs_type             | InformativeLogs                   |                    |
      | def_inv_type               | DefinitiveInvoiceProcessedProduct |                    |
      | receipt_type               | Receipt                           |                    |
      | PO_sales_type              | SalesOnProcessedProduct           |                    |
      | avOnPP_type                | AddValueOnProcessedProduct        |                    |
      | avInterim_type             | AddValueOnInterim                 |                    |
      | svInterim_type             | SubtractValueOnInterim            |                    |
      | storageCost_type           | StorageCost                       |                    |
      | interim_type               | Interim                           |                    |
      | SO_sales_type              | Sales                             |                    |
      | sales_info_logs_type       | SalesInformativeLogs              |                    |
      | HL_type                    | SalesAverageAVOnShippedQty        | UserElementNumber1 |
      | protein_type               | SalesAverageAVOnShippedQty        | UserElementNumber2 |
      | salesAV_type               | SalesAV                           |                    |
      | salesStorageCost_type      | SalesStorageCost                  |                    |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier      | SeqNo | Name                                  | M_Product_ID.Identifier        | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | PO_informative_module             | 0     | informative_03102024_1                | processedProduct               | Service        | modcntr_settings_PO_1          | info_logs_type             |
      | PO_definitive_module              | 0     | definitive_03102024_1                 | processedProduct               | Service        | modcntr_settings_PO_1          | def_inv_type               |
      | PO_receipt_module                 | 10    | receipt_03102024_1                    | rawProduct                     | Service        | modcntr_settings_PO_1          | receipt_type               |
      | PO_interimContract_module         | 20    | interimProduct_03102024               | interimProduct                 | Service        | modcntr_settings_PO_1          | interim_type               |
      | PO_salesOnProcessedProduct_module | 30    | salesOnProcessedProduct_03102024_1    | processedProduct               | Service        | modcntr_settings_PO_1          | PO_sales_type              |
      | PO_avOnProcessedProduct_module_1  | 40    | addValueOnProcessedProduct_03102024_1 | addValueOnProcessed_PO         | Service        | modcntr_settings_PO_1          | avOnPP_type                |
      | PO_avOnProcessedProduct_module_2  | 50    | addValueOnProcessedProduct_03102024_2 | addValueOnProcessed_PO_2       | Costs          | modcntr_settings_PO_1          | avOnPP_type                |
      | PO_avOnInterim_module             | 60    | addValueOnInterim_03102024_1          | addValueOnInterim              | Costs          | modcntr_settings_PO_1          | avInterim_type             |
      | PO_svOnInterim_module             | 70    | subValueOnInterim_03102024_1          | subValueOnInterim              | Costs          | modcntr_settings_PO_1          | svInterim_type             |
      | PO_storageCost_module             | 80    | storageCost_03102024_1                | storageCostForProcessedProduct | Costs          | modcntr_settings_PO_1          | storageCost_type           |
      | SO_informative_module             | 0     | informative_03102024_1                | processedProduct               | Service        | modcntr_settings_SO_1          | sales_info_logs_type       |
      | SO_sales_module                   | 10    | sales_03102024_1                      | processedProduct               | Service        | modcntr_settings_SO_1          | SO_sales_type              |
      | SO_HL_module                      | 20    | HL_03102024_1                         | HL                             | Service        | modcntr_settings_SO_1          | HL_type                    |
      | SO_salesAV_module                 | 30    | salesAV_03102024_1                    | salesAV                        | Service        | modcntr_settings_SO_1          | salesAV_type               |
      | SO_protein_module                 | 40    | protein_03102024_1                    | Protein                        | Service        | modcntr_settings_SO_1          | protein_type               |
      | SO_storageCost_module             | 50    | salesStorageCost_03102024_1           | storageCostForProcessedProduct | Service        | modcntr_settings_SO_1          | salesStorageCost_type      |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                             | Name                                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | moduleLogConditions_03102024_1         | moduleLogConditions_03102024_1         | ModularContract | moduleLogPricingSystem            | Ex                       | modcntr_settings_PO_1              | CO            |
      | moduleLogConditions_interim_03102024_1 | moduleLogConditions_interim_03102024_1 | InterimInvoice  | moduleLogPricingSystem            | Ex                       | modcntr_settings_PO_1              | CO            |
      | moduleLogConditions_SO_03102024_1      | moduleLogConditions_SO_03102024_1      | ModularContract | moduleLogPricingSystem            | Ex                       | modcntr_settings_SO_1              | CO            |

    #Interim contract setup
    And invoke "C_BPartner_InterimContract_Upsert" process:
      | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_moduleLogPO           | harvesting_calendar                 | y2022                         | true              |
      | bp_moduleLogSO           | harvesting_calendar                 | y2022                         | true              |

    And metasfresh contains C_BPartner_InterimContract:
      | C_BPartner_InterimContract_ID.Identifier | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimContractSettings_1             | bp_moduleLogPO           | harvesting_calendar                 | y2022                         | true              |
      | bp_interimContractSettings_1             | bp_moduleLogSO           | harvesting_calendar                 | y2022                         | true              |

    # PO & Modular contract
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.DatePromised        |
      | po_order   | false   | bp_moduleLogPO           | 2022-02-01  | POO             | poModuleLogContract_ref_06292023_2 | warehouse_03102024_1          | y2022                             | 2022-02-02T00:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | rawProduct              | 1000       | moduleLogConditions_03102024_1          |

    When the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier    | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_03102024_1         | rawProduct              | po_order                       | po_orderLine                       |
      | interimContract_1             | moduleLogConditions_interim_03102024_1 | rawProduct              | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus | OPT.IsSOTrx |
      | moduleLogContract_1           | moduleLogConditions_03102024_1      | bp_moduleLogPO              | rawProduct              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            | N           |
      | interimContract_1             | moduleLogConditions_03102024_1      | bp_moduleLogPO              | rawProduct              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 7.50            | moduleLogPricingSystem            | InterimInvoice      | Wa                 | CO            | N           |

    And after not more than 30s, ModCntr_Specific_Prices are found:
      | ModCntr_Specific_Price_ID.Identifier | C_Flatrate_Term_ID.Identifier | ModCntr_Module_ID.Identifier      | M_Product_ID.Identifier        | OPT.SeqNo | OPT.Price | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.IsScalePrice | OPT.MinValue |
      | price_0                              | moduleLogContract_1           | PO_definitive_module              | processedProduct               | 0         | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_20                             | moduleLogContract_1           | PO_salesOnProcessedProduct_module | processedProduct               | 30        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_30                             | moduleLogContract_1           | PO_avOnProcessedProduct_module_1  | addValueOnProcessed_PO         | 40        | 8.00      | EUR                        | PCE                   | N                | 0            |
      | price_40                             | moduleLogContract_1           | PO_avOnProcessedProduct_module_2  | addValueOnProcessed_PO_2       | 50        | 6.00      | EUR                        | PCE                   | N                | 0            |
      | price_50                             | moduleLogContract_1           | PO_avOnInterim_module             | addValueOnInterim              | 60        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_60                             | moduleLogContract_1           | PO_svOnInterim_module             | subValueOnInterim              | 70        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_70                             | moduleLogContract_1           | PO_storageCost_module             | storageCostForProcessedProduct | 80        | 0.06      | EUR                        | PCE                   | N                | 0            |
      | price_10                             | interimContract_1             | PO_interimContract_module         | rawProduct                     | 20        | 7.50      | EUR                        | PCE                   | N                | 0            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable | OPT.IsSOTrx |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine     | moduleLogContract_1           | info_logs_type             | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | y2022                             | PO_informative_module            | 10.00           | PCE                       | informative_03102024_1       | N              | N           |
      | log_2                     | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | info_logs_type             | false         | PurchaseModularContract      | EUR                        | PCE                   | 10000      | y2022                             | PO_informative_module            | 10.00           | PCE                       | informative_03102024_1       | N              | N           |
      | log_sv                    | moduleLogContract_1  | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | moduleLogContract_1           | avInterim_type             | false         | PurchaseModularContract      | EUR                        | PCE                   | 7500       | y2022                             | PO_avOnInterim_module            | 7.5             | PCE                       | addValueOnInterim_03102024_1 | Y              | N           |
      | log_ic                    | interimContract_1    | Interim         | bp_moduleLogPO                             | warehouse_03102024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_Flatrate_Term | interimContract_1             | interim_type               | false         | ContractPrefinancing         | EUR                        | PCE                   | 7500       | y2022                             | PO_interimContract_module        | 7.5             | PCE                       | interimProduct_03102024      | N              | N           |

    And there is no C_Invoice_Candidate for C_Order po_order

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | receiptSchedule_PO              | po_order              | po_orderLine              | bp_moduleLogPO           | bp_moduleLogPO_Location           | rawProduct              | 1000       | warehouse_03102024_1      | moduleLogContract_1               |

    #Material Receipts
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | rawTopHU_1         | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 1000        | 101                                | 1000006                      |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.MovementDate |
      | rawTopHU_1         | receiptSchedule_PO              | inOut_03102024_1      | 2022-02-15       |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | inOut_03102024_1      | rawProduct              | 1000        | true      | 1000           |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName    | OPT.IsBillable | OPT.IsSOTrx |
      | log_1                     | receiptLine_1        | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | rawProduct              | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_InOutLine | moduleLogContract_1           | receipt_type               | false         | MaterialReceipt              | EUR                        | PCE                   | 0          | y2022                             | PO_receipt_module                | 0.00            | PCE                       | receipt_03102024_1 | Y              | N           |

    #Manufacturing order
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.Modular_Flatrate_Term_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppo_1                  | MMO         | processedProduct        | 1000       | testResource             | 2022-02-28T23:59:00.00Z | 2022-02-28T23:59:00.00Z | 2022-02-28T23:59:00.00Z | N                | moduleLogContract_1                     | warehouse_03102024_1          |

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
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier  | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                       | OPT.IsBillable | OPT.IsSOTrx |
      | log_1                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | addValueOnProcessed_PO_2 | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | avOnPP_type                | false         | Production                   | EUR                        | PCE                   | -6000      | y2022                             | PO_avOnProcessedProduct_module_2  | -6.00           | PCE                       | addValueOnProcessedProduct_03102024_2 | Y              | N           |
      | log_2                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | addValueOnProcessed_PO   | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | avOnPP_type                | false         | Production                   | EUR                        | PCE                   | 8000       | y2022                             | PO_avOnProcessedProduct_module_1  | 8.00            | PCE                       | addValueOnProcessedProduct_03102024_1 | Y              | N           |
      | log_3                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | processedProduct         | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | def_inv_type               | false         | Production                   | EUR                        | PCE                   | 20000      | y2022                             | PO_definitive_module              | 20.00           | PCE                       | salesOnProcessedProduct_03102024_1    | Y              | N           |
      | log_4                     | ppo_1                | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | processedProduct         | bp_moduleLogPO                  | 1000 | PP_Order  | moduleLogContract_1           | PO_sales_type              | false         | Production                   | EUR                        | PCE                   | 20000      | y2022                             | PO_salesOnProcessedProduct_module | 20.00           | PCE                       | salesOnProcessedProduct_03102024_1    | Y              | N           |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | ppOrder_CostCollector_1         | ppo_1                  | processedProduct        | 1000        | CO        | MaterialReceipt   |
      | ppOrder_CostCollector_2         | ppo_1                  | rawProduct              | 1000        | CO        | MaterialReceipt   |

    #SO & contract
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference                    | OPT.M_Warehouse_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | so_1       | true    | bp_moduleLogSO           | 2022-03-01  | soModuleLogContract_ref_03102024_1 | warehouse_03102024_1          | y2022                             | locator                     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine_1   | so_1                  | processedProduct        | 1000       | moduleLogConditions_SO_03102024_1       |

    And the order identified by so_1 is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO_1        | moduleLogConditions_SO_03102024_1   | processedProduct        | so_1                           | soLine_1                           |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus | OPT.IsSOTrx |
      | moduleLogContract_SO_1        | moduleLogConditions_SO_03102024_1   | bp_moduleLogSO              | processedProduct        | soLine_1                           | so_1                           | PCE                   | 1000                  | 20.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            | Y           |
    And after not more than 30s, ModCntr_Specific_Prices are found:
      | ModCntr_Specific_Price_ID.Identifier | C_Flatrate_Term_ID.Identifier | ModCntr_Module_ID.Identifier | M_Product_ID.Identifier        | OPT.SeqNo | OPT.Price | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.IsScalePrice | OPT.MinValue |
      | price_SO_0                           | moduleLogContract_SO_1        | SO_sales_module              | processedProduct               | 10        | 20.00     | EUR                        | PCE                   | N                | 0            |
      | price_SO_10                          | moduleLogContract_SO_1        | SO_HL_module                 | HL                             | 20        | 0.25      | EUR                        | PCE                   | Y                | 100          |
      | price_SO_20                          | moduleLogContract_SO_1        | SO_HL_module                 | HL                             | 20        | 0.00      | EUR                        | PCE                   | Y                | 50           |
      | price_SO_30                          | moduleLogContract_SO_1        | SO_HL_module                 | HL                             | 20        | -0.20     | EUR                        | PCE                   | Y                | 0            |
      | price_SO_40                          | moduleLogContract_SO_1        | SO_salesAV_module            | salesAV                        | 30        | 0.20      | EUR                        | PCE                   | N                | 0            |
      | price_SO_60                          | moduleLogContract_SO_1        | SO_protein_module            | Protein                        | 40        | 0.20      | EUR                        | PCE                   | Y                | 4            |
      | price_SO_70                          | moduleLogContract_SO_1        | SO_protein_module            | Protein                        | 40        | 0.00      | EUR                        | PCE                   | Y                | 2            |
      | price_SO_80                          | moduleLogContract_SO_1        | SO_protein_module            | Protein                        | 40        | -0.10     | EUR                        | PCE                   | Y                | 0            |
      | price_SO_50                          | moduleLogContract_SO_1        | SO_storageCost_module        | storageCostForProcessedProduct | 50        | 0.10      | EUR                        | PCE                   | N                | 0            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier   | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName       | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName        | OPT.IsBillable | OPT.IsSOTrx |
      | log_1                     | soLine_1               | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | processedProduct        | bp_moduleLogSO                      | bp_moduleLogSO                  | 1000 | C_OrderLine     | moduleLogContract_SO_1        | sales_info_logs_type       | false         | SalesOrder                   | EUR                        | PCE                   | 20000      | y2022                             | SO_informative_module            | 20.00           | PCE                       | informative_03102024_1 | N              | Y           |
      | log_2                     | moduleLogContract_SO_1 | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | processedProduct        | bp_moduleLogSO                      | bp_moduleLogSO                  | 1000 | C_Flatrate_Term | moduleLogContract_SO_1        | sales_info_logs_type       | false         | SalesModularContract         | EUR                        | PCE                   | 20000      | y2022                             | SO_informative_module            | 20.00           | PCE                       | informative_03102024_1 | N              | Y           |

    #Shipment
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | sch_1      | soLine_1                  | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | so_1                  | 2022-03-04            |

    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | Harvesting_Year_ID.Identifier |
      | shippingNotification_03102024_1       | so_1                  | CO        | bp_moduleLogSO               | org_1                | locator                 | y2022                         |

    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_03102024_1       | shippingNotification_03102024_1       | sch_1                            | processedProduct        | 1000        |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier                | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName                   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName              | OPT.IsBillable | OPT.IsSOTrx |
      | log_avInterim_snline      | shippingNotificationLine_03102024_1 | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | addValueOnInterim       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_Shipping_NotificationLine | moduleLogContract_1           | avInterim_type             | false         | ShippingNotification         | EUR                        | PCE                   | -20000     | y2022                             | PO_avOnInterim_module            | -20.00          | PCE                       | addValueOnInterim_03102024_1 | Y              | N           |
      | log_svInterim_snline      | shippingNotificationLine_03102024_1 | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | subValueOnInterim       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_Shipping_NotificationLine | moduleLogContract_1           | svInterim_type             | false         | ShippingNotification         | EUR                        | PCE                   | 20000      | y2022                             | PO_svOnInterim_module            | 20.00           | PCE                       | subValueOnInterim_03102024_1 | Y              | N           |
      | log_SO_snline             | shippingNotificationLine_03102024_1 | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | processedProduct        | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | M_Shipping_NotificationLine | moduleLogContract_SO_1        | sales_info_logs_type       | false         | ShippingNotification         | EUR                        | PCE                   | 20000      | y2022                             | SO_informative_module            | 20.00           | PCE                       | informative_03102024_1       | N              | Y           |

    And the shipment schedule is split
      | Identifier | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | C_UOM_ID.X12DE355 | DeliveryDate | OPT.UserElementNumber1 | OPT.UserElementNumber2 |
      | spl_1      | sch_1                            | 750          | PCE               | 2022-05-04   | 700                    | 1                      |
      | spl_2      | sch_1                            | 250          | PCE               | 2022-05-04   | 1200                   | 5                      |

    And  'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType   | IsCompleteShipments | IsShipToday |
      | sch_1                            | SPLIT_SHIPMENT | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.M_ShipmentSchedule_Split_ID.Identifier |
      | sch_1                            | inout_1               | spl_1                                      |
      | sch_1                            | inout_2               | spl_2                                      |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered | OPT.Harvesting_Year_ID.Identifier |
      | so_ioline_1               | inout_1               | processedProduct        | 750         | true      | 750            | y2022                             |
      | so_ioline_2               | inout_2               | processedProduct        | 250         | true      | 250            | y2022                             |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier        | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.ProductName                    | OPT.IsBillable | OPT.IsSOTrx |
      | log_inout_1_1             | so_ioline_1          | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | processedProduct               | bp_moduleLogPO                      | bp_moduleLogPO                  | 750 | M_InOutLine | moduleLogContract_1           | def_inv_type               | false         | Shipment                     | EUR                        | PCE                   | 15000      | y2022                             | PO_definitive_module             | 20.00           | PCE                       | salesOnProcessedProduct_03102024_1 | Y              | N           |
      | log_inout_1_2             | so_ioline_1          | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | storageCostForProcessedProduct | bp_moduleLogPO                      | bp_moduleLogPO                  | 750 | M_InOutLine | moduleLogContract_1           | storageCost_type           | false         | Shipment                     | EUR                        | PCE                   | -4185      | y2022                             | PO_storageCost_module            | -0.06           | PCE                       | storageCost_03102024_1             | Y              | N           |
      | log_inout_1_3             | so_ioline_1          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | processedProduct               | bp_moduleLogSO                      | bp_moduleLogSO                  | 750 | M_InOutLine | moduleLogContract_SO_1        | SO_sales_type              | false         | Shipment                     | EUR                        | PCE                   | 15000      | y2022                             | SO_sales_module                  | 20.00           | PCE                       | sales_03102024_1                   | Y              | Y           |
      | log_inout_1_4             | so_ioline_1          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | storageCostForProcessedProduct | bp_moduleLogSO                      | bp_moduleLogSO                  | 750 | M_InOutLine | moduleLogContract_SO_1        | salesStorageCost_type      | false         | Shipment                     | EUR                        | PCE                   | 3000       | y2022                             | SO_storageCost_module            | 0.1             | PCE                       | salesStorageCost_03102024_1        | Y              | Y           |
      | log_inout_1_5             | so_ioline_1          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | HL                             | bp_moduleLogSO                      | bp_moduleLogSO                  | 750 | M_InOutLine | moduleLogContract_SO_1        | HL_type                    | false         | Shipment                     | EUR                        | PCE                   | 187.5      | y2022                             | SO_HL_module                     | 0.25            | PCE                       | HL_03102024_1                      | Y              | Y           |
      | log_inout_1_6             | so_ioline_1          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | Protein                        | bp_moduleLogSO                      | bp_moduleLogSO                  | 750 | M_InOutLine | moduleLogContract_SO_1        | protein_type               | false         | Shipment                     | EUR                        | PCE                   | -75        | y2022                             | SO_protein_module                | -0.1            | PCE                       | protein_03102024_1                 | Y              | Y           |
      | log_inout_1_7             | so_ioline_1          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | salesAV                        | bp_moduleLogSO                      | bp_moduleLogSO                  | 750 | M_InOutLine | moduleLogContract_SO_1        | salesAV_type               | false         | Shipment                     | EUR                        | PCE                   | 150        | y2022                             | SO_salesAV_module                | 0.2             | PCE                       | salesAV_03102024_1                 | Y              | Y           |
      | log_inout_2_1             | so_ioline_2          | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | processedProduct               | bp_moduleLogPO                      | bp_moduleLogPO                  | 250 | M_InOutLine | moduleLogContract_1           | def_inv_type               | false         | Shipment                     | EUR                        | PCE                   | 5000       | y2022                             | PO_definitive_module             | 20.00           | PCE                       | salesOnProcessedProduct_03102024_1 | Y              | N           |
      | log_inout_2_2             | so_ioline_2          | ModularContract | bp_moduleLogPO                             | warehouse_03102024_1          | storageCostForProcessedProduct | bp_moduleLogPO                      | bp_moduleLogPO                  | 250 | M_InOutLine | moduleLogContract_1           | storageCost_type           | false         | Shipment                     | EUR                        | PCE                   | -1395      | y2022                             | PO_storageCost_module            | -0.06           | PCE                       | storageCost_03102024_1             | Y              | N           |
      | log_inout_2_3             | so_ioline_2          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | processedProduct               | bp_moduleLogSO                      | bp_moduleLogSO                  | 250 | M_InOutLine | moduleLogContract_SO_1        | SO_sales_type              | false         | Shipment                     | EUR                        | PCE                   | 5000       | y2022                             | SO_sales_module                  | 20.00           | PCE                       | sales_03102024_1                   | Y              | Y           |
      | log_inout_2_4             | so_ioline_2          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | storageCostForProcessedProduct | bp_moduleLogSO                      | bp_moduleLogSO                  | 250 | M_InOutLine | moduleLogContract_SO_1        | salesStorageCost_type      | false         | Shipment                     | EUR                        | PCE                   | 1000       | y2022                             | SO_storageCost_module            | 0.1             | PCE                       | salesStorageCost_03102024_1        | Y              | Y           |
      | log_inout_2_5             | so_ioline_2          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | HL                             | bp_moduleLogSO                      | bp_moduleLogSO                  | 250 | M_InOutLine | moduleLogContract_SO_1        | HL_type                    | false         | Shipment                     | EUR                        | PCE                   | 62.5       | y2022                             | SO_HL_module                     | 0.25            | PCE                       | HL_03102024_1                      | Y              | Y           |
      | log_inout_2_6             | so_ioline_2          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | Protein                        | bp_moduleLogSO                      | bp_moduleLogSO                  | 250 | M_InOutLine | moduleLogContract_SO_1        | protein_type               | false         | Shipment                     | EUR                        | PCE                   | 50         | y2022                             | SO_protein_module                | 0.2             | PCE                       | protein_03102024_1                 | Y              | Y           |
      | log_inout_2_7             | so_ioline_2          | ModularContract | bp_moduleLogSO                             | warehouse_03102024_1          | salesAV                        | bp_moduleLogSO                      | bp_moduleLogSO                  | 250 | M_InOutLine | moduleLogContract_SO_1        | salesAV_type               | false         | Shipment                     | EUR                        | PCE                   | 50         | y2022                             | SO_salesAV_module                | 0.2             | PCE                       | salesAV_03102024_1                 | Y              | Y           |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | metasfresh_user       | metasfresh |

    And distribute interest
      | AD_User_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | InterimDate | BillingDate |
      | metasfresh_user       | invGroup                             | 2022-02-15  | 2022-05-20  |

    And load latest ModCntr_Interest_Run for invoicing group invGroup as lastInterestRun

    And validate created interestRun records for lastInterestRun
      | ModCntr_Interest_ID.Identifier | FinalInterest | C_Currency.ISO_Code | InterestDays | ShippingNotification_ModCntr_Log_ID.Identifier | OPT.InterimContract_ModCntr_Log_ID.Identifier |
      | interest_1                     | 40            | EUR                 | 19           | log_avInterim_snline                           | log_sv                                        |
      | interest_2                     | -26.39        | EUR                 | 95           | log_svInterim_snline                           |                                               |

    #Sales final invoice
    And create final invoice
      | C_Flatrate_Term_ID.Identifier | AD_User_ID.Identifier | OPT.DateInvoiced | OPT.DateAcct |
      | moduleLogContract_SO_1        | metasfresh_user       | 2022-05-30       | 2022-05-30   |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier        | ProductName                 |
      | candidate_sales                   | moduleLogContract_SO_1        | processedProduct               | sales_03102024_1            |
      | candidate_HL                      | moduleLogContract_SO_1        | HL                             | HL_03102024_1               |
      | candidate_Protein                 | moduleLogContract_SO_1        | Protein                        | protein_03102024_1          |
      | candidate_salesAV                 | moduleLogContract_SO_1        | salesAV                        | salesAV_03102024_1          |
      | candidate_storageCost             | moduleLogContract_SO_1        | storageCostForProcessedProduct | salesStorageCost_03102024_1 |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | candidate_sales                   | 0                |
      | candidate_HL                      | 0                |
      | candidate_Protein                 | 0                |
      | candidate_salesAV                 | 0                |
      | candidate_storageCost             | 0                |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.InvoiceRule | OPT.PriceActual | OPT.NetAmtToInvoice | OPT.NetAmtInvoiced | OPT.Processed |
      | candidate_sales                   | 0            | 1000           | 1000             | I               | 20.00           | 0                   | 20000              | Y             |
      | candidate_HL                      | 0            | 1000           | 1000             | I               | 0.25            | 0                   | 250                | Y             |
      | candidate_Protein                 | 0            | 1000           | 1000             | I               | -0.03           | 0                   | -30                | Y             |
      | candidate_salesAV                 | 0            | 1000           | 1000             | I               | 0.2             | 0                   | 200                | Y             |
      | candidate_storageCost             | 0            | 1              | 1                | I               | 4000            | 0                   | 4000               | Y             |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.TotalLines |
      | candidate_sales                   | final_SO_Inv            | 24420.0        |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | final_SO_Inv            | bp_moduleLogSO           | bp_moduleLogSO_Location           | 1000002     | true      | CO        | 29059.8        | finalSO                     |

    #Purchase final invoice
    And create final invoice
      | C_Flatrate_Term_ID.Identifier | AD_User_ID.Identifier | OPT.DateInvoiced | OPT.DateAcct |
      | moduleLogContract_1           | metasfresh_user       | 2022-06-01       | 2022-03-01   |

    And after not more than 60s, modular C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier      | C_Flatrate_Term_ID.Identifier | M_Product_ID.Identifier        | ProductName                           |
      | candidate_receipt                      | moduleLogContract_1           | rawProduct                     | receipt_03102024_1                    |
      | candidate_salesOnProcessedProduct      | moduleLogContract_1           | processedProduct               | salesOnProcessedProduct_03102024_1    |
      | candidate_addValueOnProcessedProduct_1 | moduleLogContract_1           | addValueOnProcessed_PO         | addValueOnProcessedProduct_03102024_1 |
      | candidate_addValueOnProcessedProduct_2 | moduleLogContract_1           | addValueOnProcessed_PO_2       | addValueOnProcessedProduct_03102024_2 |
      | candidate_addValueOnInterim            | moduleLogContract_1           | addValueOnInterim              | addValueOnInterim_03102024_1          |
      | candidate_subValueOnInterim            | moduleLogContract_1           | subValueOnInterim              | subValueOnInterim_03102024_1          |
      | candidate_storageCost                  | moduleLogContract_1           | storageCostForProcessedProduct | storageCost_03102024_1                |

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
      | candidate_subValueOnInterim            | 0            | 1              | 1                | I               | 26.39           | 0                   | 26.39              | Y             |
      | candidate_storageCost                  | 0            | 1              | 1                | I               | -5580           | 0                   | -5580              | Y             |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus | OPT.TotalLines |
      | candidate_receipt                 | final_PO_Inv            | CO            | 16406.39       |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.GrandTotal | OPT.C_DocType_ID.Identifier |
      | final_PO_Inv            | bp_moduleLogPO           | bp_moduleLogPO_Location           | 1000002     | true      | CO        | 19523.6        | final                       |

    And validate created modular invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier        | ProductName                           | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | invoiceLine_1_1             | final_PO_Inv            | rawProduct                     | receipt_03102024_1                    | 1000        | true      | 0                | 0               | 0              | PCE                   | PCE                       |
      | invoiceLine_1_2             | final_PO_Inv            | processedProduct               | salesOnProcessedProduct_03102024_1    | 1000        | true      | 20               | 20              | 20000          | PCE                   | PCE                       |
      | invoiceLine_1_3             | final_PO_Inv            | addValueOnProcessed_PO         | addValueOnProcessedProduct_03102024_1 | 1000        | true      | 8                | 8               | 8000           | PCE                   | PCE                       |
      | invoiceLine_1_4             | final_PO_Inv            | addValueOnProcessed_PO_2       | addValueOnProcessedProduct_03102024_2 | 1000        | true      | -6               | -6              | -6000          | PCE                   | PCE                       |
      | invoiceLine_1_5             | final_PO_Inv            | addValueOnInterim              | addValueOnInterim_03102024_1          | 1           | true      | -40              | -40             | -40            | PCE                   | PCE                       |
      | invoiceLine_1_6             | final_PO_Inv            | subValueOnInterim              | subValueOnInterim_03102024_1          | 1           | true      | 26.39            | 26.39           | 26.39          | PCE                   | PCE                       |
      | invoiceLine_1_7             | final_PO_Inv            | storageCostForProcessedProduct | storageCost_03102024_1                | 1           | true      | -5580            | -5580           | -5580          | PCE                   | PCE                       |
