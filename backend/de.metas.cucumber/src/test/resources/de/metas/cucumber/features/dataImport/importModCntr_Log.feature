@Id:S0346
@from:cucumber
@ignore
Feature: Import Modular Contract Logs via DataImportRestController

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_09112023_1 | moduleLogPricingSystem_09112023_1 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_09112023_1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | moduleLogPLV | moduleLogPL_PO            | moduleLogPLV_09112023_1 | 2022-02-01 |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains C_BPartners:
      | Identifier     | Name         | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | importLog BP | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803098505483 | bp_moduleLogPO           | true                | true                |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year                 | 2022       | harvesting_calendar      |

    And load AD_ImpFormat:
      | AD_ImpFormat_ID.Identifier | Name                        |
      | importFormat               | Import Modular Contract Log |
    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | importDocType           | ARI             | true          |
    And metasfresh contains M_Products:
      | Identifier    | Name         |
      | importProduct | Sold Product |
      | fee1          | Fee1         |
      | bonus1        | Bonus1       |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_importProduct | moduleLogPLV                      | importProduct           | 10.0     | PCE               | Normal                        |
      | pp_fee1          | moduleLogPLV                      | fee1                    | 10.0     | PCE               | Normal                        |
      | pp_bonus1        | moduleLogPLV                      | bonus1                  | 15.0     | PCE               | Normal                        |
    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_06292023_2 | importProduct               | harvesting_calendar      | year                 | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                | Value               | ModularContractHandlerType |
      | modCntr_type_PO            | modCntr_type_PO     | modCntr_type_PO     | Receipt                    |
      | modCntr_type_Import        | modCntr_type_Import | modCntr_type_Import | ImportLog                  |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_product       | 10    | importProduct_module  | importProduct           | Kosten         | modCntr_settings_1             | modCntr_type_PO            |
      | modCntr_module_fee1          | 20    | moduleTest_06292023_2 | fee1                    | Kosten         | modCntr_settings_1             | modCntr_type_Import        |
      | modCntr_module_bonus1        | 30    | name_09072023_20      | bonus1                  | Leistung       | modCntr_settings_1             | modCntr_type_Import        |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO | moduleLogConditions_po_06292023_2 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_1                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_06292023_2 |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | importProduct           | 1000       | moduleLogConditions_PO                  |

    And the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO              | importProduct           | po_order                       | po_orderLine                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO              | bp_moduleLogPO              | importProduct           | po_orderLine                       | po_order                       | PCE                   | 1000                  | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Description                                                                                        |
      | log_1                     | po_orderLine         | ModularContract | bp_moduleLogPO                             | warehouseStd                  | importProduct           | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_PO                | false         | PurchaseOrder                | EUR                        | PCE                   | 10000      | year                              | modCntr_module_product           | 10.00           | PCE                       | Eine Bestellung für das Produkt Sold Product_Sold Product mit der Menge 1000 Stk wurde fertiggestellt. |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus |
      | po_orderLine         | C_OrderLine | SP               |

  @from:cucumber
  @Id:S0346_10
  Scenario: Import sales I_ModCntr_Log from csv
    And metasfresh initially has no I_ModCntr_Log import data
    And add HTTP header
      | Key          | Value      |
      | Content-Type | text/plain |
      | Accept       | */*        |
    And store modular contract DataImport String requestBody in context
      | ModCntr_Log_DocumentType | IsSOTrx | C_Flatrate_Term_ID.Identifier | ProductValue | OPT.Qty | OPT.UOMSymbol | OPT.PriceActual | OPT.PriceUOM | OPT.Amount | ISO_Code | FiscalYear | CalendarName          | ContractModuleName | BPartnerValue | Bill_BPartner_Value | CollectionPointValue | DateTrx    | WarehouseName |
      | ImportLog                | N       | moduleLogContract_1           | Fee1         | 2       | Stk           | 22              | Stk          | 44         | EUR      | 2022       | Buchführungs-Kalender |                    | importLog BP  | importLog BP        | importLog BP         | 2023-01-01 | Hauptlager    |
      | ImportLog                | N       | moduleLogContract_1           | Bonus1       | 1       | Stk           | 15              | Stk          | 15         | EUR      | 2022       | Buchführungs-Kalender |                    | importLog BP  | importLog BP        | importLog BP         | 2023-01-01 | Hauptlager    |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=ImportModularContractLog&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code
    And after no more than 30s, I_ModCntr_Log are found by product
      | I_ModCntr_Log_ID.Identifier | M_Product_ID.Identifier |
      | imcl_fee1                   | fee1                    |
      | imcl_bonus1                 | bonus1                  |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName     | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 |
      | log_fee1                  | imcl_fee1            | ModularContract | bp_moduleLogPO                             | warehouseStd                  | fee1                    | bp_moduleLogPO                      | bp_moduleLogPO                  | 2   | I_ModCntr_Log | moduleLogContract_1           | modCntr_type_Import            | false         | ImportLog                    | EUR                        | PCE                   | 44         | year                              | modCntr_module_fee1              | 22.00           | PCE                       |
      | log_bonus1                | imcl_bonus1          | ModularContract | bp_moduleLogPO                             | warehouseStd                  | bonus1                  | bp_moduleLogPO                      | bp_moduleLogPO                  | 1   | I_ModCntr_Log | moduleLogContract_1           | modCntr_type_Import            | false         | ImportLog                    | EUR                        | PCE                   | 15         | year                              | modCntr_module_bonus1            | 15.00           | PCE                       |
