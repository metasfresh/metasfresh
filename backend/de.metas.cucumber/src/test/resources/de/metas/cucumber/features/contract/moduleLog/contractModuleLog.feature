Feature: Call order contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @Id:S0282_100
  @from:cucumber
  Scenario: Happy flow for contract module log - purchase order with two lines completed => validate the two created Log Entries
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_06292023_2 | moduleLogPricingSystem_06292023_2 |
    And metasfresh contains M_Products:
      | Identifier            | Name                                  |
      | module_log_product_PO | module_log_product_PO_test_06292023_2 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_06292023_2 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | moduleLogPLV | moduleLogPL_PO            | moduleLogPLV_06292023_2 | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_06292023_1 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5803098505483 | bp_moduleLogPO           | true                | true                |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear |
      | year                 | 2022       |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_06292023_2 | module_log_product_PO   | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | Classname                                                               |
      | modCntr_type_1             | poLine_06292023_2 | poLine_06292023_2 | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_06292023_2 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_06292023_2 | ModularContract | moduleLogPricingSystem            | Ca                       | modCntr_settings_1                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_06292023_2 |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine   | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |
      | po_orderLine_2 | po_order              | module_log_product_PO   | 500        | moduleLogConditions_PO                  |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product_PO       | po_orderLine                       |
      | moduleLogContract_2 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product_PO       | po_orderLine_2                     |

    When the order identified by po_order is completed

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine         | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              |
      | log_2                     | po_orderLine_2       | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              |

  @Id:S0282_200
  @from:cucumber
  Scenario: Happy flow for contract module log - purchase order with two lines voided
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_06292023_4 | moduleLogPricingSystem_06292023_4 |
    And metasfresh contains M_Products:
      | Identifier            | Name                                  |
      | module_log_product_PO | module_log_product_PO_test_06292023_4 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_06292023_4 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | moduleLogPLV | moduleLogPL_PO            | moduleLogPLV_06292023_4 | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_06292023_4 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5813098505483 | bp_moduleLogPO           | true                | true                |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear |
      | year                 | 2022       |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_06292023_4 | module_log_product_PO   | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | Classname                                                               |
      | modCntr_type_1             | poLine_06292023_4 | poLine_06292023_4 | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_06292023_4 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_06292023_4 | ModularContract | moduleLogPricingSystem            | Ca                       | modCntr_settings_1                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_06292023_4 |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine   | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |
      | po_orderLine_2 | po_order              | module_log_product_PO   | 500        | moduleLogConditions_PO                  |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product_PO       | po_orderLine                       |
      | moduleLogContract_2 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product_PO       | po_orderLine_2                     |

    When the order identified by po_order is completed

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine         | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              |
      | log_2                     | po_orderLine_2       | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              |

    And the order identified by po_order is voided

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty   | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine         | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000  | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              |
      | log_2                     | po_orderLine         | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | -1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | -2000      | year                              |
      | log_3                     | po_orderLine_2       | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 500   | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              |
      | log_4                     | po_orderLine_2       | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | -500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | -1000      | year                              |

  @Id:S0282_300
  @from:cucumber
  Scenario: REACTIVATE | REVERSE purchase order with linked modular contract
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_07032023_2 | moduleLogPricingSystem_07032023_2 |
    And metasfresh contains M_Products:
      | Identifier            | Name                                  |
      | module_log_product_PO | module_log_product_PO_test_07032023_2 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_07032023_2 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | moduleLogPLV | moduleLogPL_PO            | moduleLogPLV_07032023_2 | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | moduleLogPLV                      | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_07032023_2 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5823098505483 | bp_moduleLogPO           | true                | true                |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear |
      | year                 | 2022       |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_07032023_2 | module_log_product_PO   | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | Classname                                                               |
      | modCntr_type_1             | poLine_07032023_2 | poLine_07032023_2 | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_07032023_2 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_07032023_2 | ModularContract | moduleLogPricingSystem            | Ca                       | modCntr_settings_1                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                    |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModuleLogContract_ref_07032023_2 |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine   | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |
      | po_orderLine_2 | po_order              | module_log_product_PO   | 500        | moduleLogConditions_PO                  |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product_PO       | po_orderLine                       |
      | moduleLogContract_2 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product_PO       | po_orderLine_2                     |

    When the order identified by po_order is completed

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty  | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_Currency_ID.ISO_Code | OPT.C_UOM_ID.X12DE355 | OPT.Amount | OPT.Harvesting_Year_ID.Identifier |
      | log_1                     | po_orderLine         | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 1000 | C_OrderLine | moduleLogContract_1           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 2000       | year                              |
      | log_2                     | po_orderLine_2       | bp_moduleLogPO                             | warehouseStd                  | module_log_product_PO       | bp_moduleLogPO                      | bp_moduleLogPO                  | 500  | C_OrderLine | moduleLogContract_2           | modCntr_type_1                 | false         | PurchaseOrder                | EUR                        | PCE                   | 1000       | year                              |

    And load AD_Message:
      | Identifier             | Value                                                                                        |
      | reactivate_not_allowed | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed |

    And the order identified by po_order is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | reactivate_not_allowed       |

    And the order identified by po_order is reversed expecting error
      | OPT.AD_Message_ID.Identifier |
      | reactivate_not_allowed       |

  @Id:S0282_400
  @from:cucumber
  Scenario: Happy flow for contract module log - inventory with two lines completed => validate the two created log entries
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_07042023_1 | moduleLogPricingSystem_07042023_1 |
    And metasfresh contains M_Products:
      | Identifier         | Name                               |
      | module_log_product | module_log_product_test_07042023_1 |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_07042023_1 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5823198505483 | bp_moduleLogPO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse                 | warehouse_07042023_1 | warehouse_07042023_1 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_12012023_1 | warehouse                 |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear |
      | year                 | 2022       |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_07042023_1 | module_log_product      | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name               | Value              | Classname                                                           |
      | modCntr_type_1             | invLine_07042023_1 | invLine_07042023_1 | de.metas.contracts.modular.impl.InventoryLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_07042023_1 | module_log_product      | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_07042023_1 | ModularContract | moduleLogPricingSystem            | Ca                       | modCntr_settings_1                 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product          |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | warehouse      | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier |
      | il_1       | i_1                       | module_log_product      | PCE          | 10       | 0       | moduleLogContract_1                     |
      | il_2       | i_1                       | module_log_product      | PCE          | 10       | 0       | moduleLogContract_1                     |

    When the inventory identified by i_1 is completed

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_1                     | il_1                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | 10  | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_1                     | il_2                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | 10  | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |

  @Id:S0282_500
  @from:cucumber
  Scenario: Happy flow for contract module log - internal use with two lines completed => validate the two created log entries
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_07052023_1 | moduleLogPricingSystem_07052023_1 |
    And metasfresh contains M_Products:
      | Identifier         | Name                               |
      | module_log_product | module_log_product_test_07052023_1 |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_07052023_1 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5823298505483 | bp_moduleLogPO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse                 | warehouse_07052023_1 | warehouse_07052023_1 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_12012023_1 | warehouse                 |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear |
      | year                 | 2022       |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_07052023_1 | module_log_product      | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | Classname                                                           |
      | modCntr_type_1             | poLine_07052023_1 | poLine_07052023_1 | de.metas.contracts.modular.impl.InventoryLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_07052023_1 | module_log_product      | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_07052023_1 | ModularContract | moduleLogPricingSystem            | Ca                       | modCntr_settings_1                 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product          |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | disposal                | MMI             | IUI            |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate | OPT.C_DocType_ID.Identifier |
      | i_1        | warehouse      | 2021-04-16   | disposal                    |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier | OPT.QtyInternalUse |
      | il_1       | i_1                       | module_log_product      | PCE          | 0        | 0       | moduleLogContract_1                     | 10                 |
      | il_2       | i_1                       | module_log_product      | PCE          | 0        | 0       | moduleLogContract_1                     | 10                 |

    When the inventory identified by i_1 is completed

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_1                     | il_1                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | -10 | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_1                     | il_2                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | -10 | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |

  @Id:S0282_600
  @from:cucumber
  Scenario: Happy flow for contract module log - inventory with two lines reversed
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_07052023_2 | moduleLogPricingSystem_07052023_2 |
    And metasfresh contains M_Products:
      | Identifier         | Name                               |
      | module_log_product | module_log_product_test_07052023_2 |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_07052023_2 | Y            | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 5824298505483 | bp_moduleLogPO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse                 | warehouse_07052023_2 | warehouse_07052023_2 | bp_moduleLogPO               | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_12012023_1 | warehouse                 |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear |
      | year                 | 2022       |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_07052023_2 | module_log_product      | harvesting_calendar      | year                 | moduleLogPricingSystem            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name              | Value             | Classname                                                           |
      | modCntr_type_1             | poLine_07052023_2 | poLine_07052023_2 | de.metas.contracts.modular.impl.InventoryLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_07052023_2 | module_log_product      | Kosten         | modCntr_settings_1             | modCntr_type_1             |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                              | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_07052023_2 | ModularContract | moduleLogPricingSystem            | Ca                       | modCntr_settings_1                 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | moduleLogContract_1 | moduleLogConditions_PO              | bp_moduleLogPO              | 2021-10-31 | 2022-10-30 | module_log_product          |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | warehouse      | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.Modular_Flatrate_Term_ID.Identifier |
      | il_1       | i_1                       | module_log_product      | PCE          | 10       | 0       | moduleLogContract_1                     |
      | il_2       | i_1                       | module_log_product      | PCE          | 10       | 0       | moduleLogContract_1                     |

    When the inventory identified by i_1 is completed

    Then ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_1                     | il_1                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | 10  | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_2                     | il_2                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | 10  | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |

    And the inventory identified by i_1 is reversed

    And ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.C_UOM_ID.X12DE355 | OPT.Harvesting_Year_ID.Identifier | OPT.Description                                                  |
      | log_1                     | il_1                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | 10  | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_2                     | il_1                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | -10 | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_3                     | il_2                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | 10  | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
      | log_4                     | il_2                 | warehouse                     | module_log_product          | bp_moduleLogPO                  | -10 | M_InventoryLine | moduleLogContract_1           | modCntr_type_1                 | false         | Inventory                    | PCE                   | year                              | Bei der Inventur wurde ein Fehl-/Mehrbestand von 10 Stk gezählt. |
