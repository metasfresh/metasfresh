@from:cucumber
@ignore
@ghActions:run_on_executor5
Feature: Shipping Notifications

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]

    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                              | Value                             |
      | moduleLogPricingSystem | moduleLogPricingSystem_04102023_3 | moduleLogPricingSystem_04102023_3 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_04102023_3 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_04102023_3 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_04102023_3_PO | 2022-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_04102023_3_SO | 2022-02-01 |

    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_1                | 001   |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_04102023_3 | Y            | N              | moduleLogPricingSystem        | 1000002                    |
      | bp_moduleLogSO | bp_moduleLogSO_04102023_3 | N            | Y              | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN             | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 04102023_312346 | bp_moduleLogPO           | true                | true                |
      | bp_moduleLogSO_Location | 04102023_312345 | bp_moduleLogSO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_04102023_3 | warehouse_04102023_3 | bp_moduleLogPO               | bp_moduleLogPO_Location               |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_04102023_3 | warehouseModularContract  |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |
      | year_2022            | 2022       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                              | Value                             | ModularContractHandlerType              |
      | modCntr_type_PO            | modCntr_type_PO_04102023_3        | modCntr_type_PO_04102023_3        | PurchaseOrderLine_Modular               |
      | modCntr_type_SO_for_PO     | modCntr_type_SO_for_PO_04102023_3 | modCntr_type_SO_for_PO_04102023_3 | SOLineForPO_Modular                     |
      | modCntr_type_SO            | modCntr_type_SO_04102023_3        | modCntr_type_SO_04102023_3        | SalesOrderLine_Modular                  |
      | modCntr_type_MC_SO         | modCntr_type_MC_SO_04102023_3     | modCntr_type_MC_SO_04102023_3     | SalesModularContract                    |
      | modCntr_type_MC_PO         | modCntr_type_MC_PO_04102023_3     | modCntr_type_MC_PO_04102023_3     | PurchaseModularContract                 |
      | modCntr_type_MC_SN_SO      | modCntr_type_MC_SN_SO_04102023_3  | modCntr_type_MC_SN_SO_04102023_3  | ShippingNotificationForSales_Modular    |
      | modCntr_type_MC_SN_PO      | modCntr_type_MC_SN_PO_04102023_3  | modCntr_type_MC_SN_PO_04102023_3  | ShippingNotificationForPurchase_Modular |


  @from:cucumber
  Scenario: Covers:
  validate that a purchase modular contract log is created on a shipping notification completion for an eligible line.
  validate that a sales modular contract log is created on a shipping notification completion for an eligible line.
  validate recomputation process for a completed shipping notification
  validate that a modular contract log was created with negative qty and the correct description when a shipping notification was reversed.
  validate recomputation process for a reversed shipping notification

    Given metasfresh contains M_Products:
      | Identifier           | Name                            |
      | modularContract_prod | modularContract_prod_04102023_3 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod    | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                      | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.IsSOTrx |
      | modCntr_settings_2023          | testSettings_04102023_3   | modularContract_prod        | harvesting_calendar      | year_2023            | moduleLogPricingSystem            | false       |
      | modCntr_settings_2023_2        | testSettings_04102023_3_2 | modularContract_prod        | harvesting_calendar      | year_2022            | moduleLogPricingSystem            | true        |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name               | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO            | 10    | name_10            | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_PO            |
      | modCntr_module_SO_for_PO     | 20    | name_20            | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SO_for_PO     |
      | modCntr_module_MC_PO         | 30    | name_30            | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_MC_PO         |
      | modCntr_module_MC_SN_PO      | 40    | name_40            | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_MC_SN_PO      |
      | modCntr_module_SO_1          | 10    | name_04102023_3_10 | modularContract_prod    | Kosten         | modCntr_settings_2023_2        | modCntr_type_SO            |
      | modCntr_module_MC_1          | 20    | name_04102023_3_20 | modularContract_prod    | Kosten         | modCntr_settings_2023_2        | modCntr_type_MC_SO         |
      | modCntr_module_MC_SN_SO      | 30    | name_04102023_3_30 | modularContract_prod    | Kosten         | modCntr_settings_2023_2        | modCntr_type_MC_SN_SO      |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                  | Name                        | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023   | modularContractTerms_2023   | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023              |
      | modularContractTerms_2023_2 | modularContractTerms_2023_2 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023_2            |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_1 | modularContract_prod        | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference              | OPT.M_Warehouse_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_04102023_3 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine     | po_order              | modularContract_prod    | 10         | modularContractTerms_2023               |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod    | po_order                       | poLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogPO              | modularContract_prod    | poLine                             | po_order                       | PCE                   | 10                    | 5.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_04102023_3   | harvesting_calendar                     | year_2023                         | warehouseModularContract      | locatorModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine_1   | so_order              | modularContract_prod    | 8          | modularContractTerms_2023_2             |

    When the order identified by so_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO          | modularContractTerms_2023_2         | modularContract_prod    | so_order                       | soLine_1                           |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_SO          | modularContractTerms_2023_2         | bp_moduleLogPO              | modularContract_prod    | soLine_1                           | so_order                       | PCE                   | 8                     | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | soLine_1                  | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | so_order              | 2022-03-04            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier |
      | shippingNotification_04102023_4       | so_order              | CO        | bp_moduleLogSO               | bp_moduleLogSO_04102023_3             | org_1                | locatorModularContract  | harvesting_calendar                 | year_2023                     |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_04102023_3_1     | shippingNotification_04102023_4       | s_ol_1                           | modularContract_prod    | 8           |

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier                  | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName                   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier |
      | soLog_1                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_Shipping_NotificationLine | moduleLogContract             | modCntr_type_MC_SN_PO          | false         | ShippingNotification         | year_2023                         | false       | modCntr_module_MC_SN_PO          |
      | poLog_2                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_Shipping_NotificationLine | moduleLogContract_SO          | modCntr_type_MC_SN_SO          | false         | ShippingNotification         | year_2023                         | true        | modCntr_module_MC_SN_SO          |

    And recompute modular logs for record:
      | TableName               | Record_ID.Identifier            |
      | M_Shipping_Notification | shippingNotification_04102023_4 |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier                  | TableName                   | ProcessingStatus | OPT.noOfLogStatuses |
      | shippingNotificationLine_04102023_3_1 | M_Shipping_NotificationLine | SP               | 2                   |
    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier                  | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName                   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier |
      | soLog_1                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_Shipping_NotificationLine | moduleLogContract             | modCntr_type_MC_SN_PO          | false         | ShippingNotification         | year_2023                         | false       | modCntr_module_MC_SN_PO          |
      | poLog_2                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_Shipping_NotificationLine | moduleLogContract_SO          | modCntr_type_MC_SN_SO          | false         | ShippingNotification         | year_2023                         | true        | modCntr_module_MC_SN_SO          |

    When the ShippingNotification identified by shippingNotification_04102023_4 is reversed
    And after not more than 30s, locate reversal M_Shipping_Notifications
      | M_Shipping_Notification_ID.Identifier    | Reversal_ID.Identifier          |
      | reversal_shippingNotification_04102023_4 | shippingNotification_04102023_4 |

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier                  | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName                   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier |
      | soLog_1                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_Shipping_NotificationLine | moduleLogContract             | modCntr_type_MC_SN_PO          | false         | ShippingNotification         | year_2023                         | false       | modCntr_module_MC_SN_PO          |
      | poLog_2                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | M_Shipping_NotificationLine | moduleLogContract_SO          | modCntr_type_MC_SN_SO          | false         | ShippingNotification         | year_2023                         | true        | modCntr_module_MC_SN_SO          |
      | soLog_3                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | -8  | M_Shipping_NotificationLine | moduleLogContract             | modCntr_type_MC_SN_PO          | false         | ShippingNotification         | year_2023                         | false       | modCntr_module_MC_SN_PO          |
      | poLog_4                   | shippingNotificationLine_04102023_3_1 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | -8  | M_Shipping_NotificationLine | moduleLogContract_SO          | modCntr_type_MC_SN_SO          | false         | ShippingNotification         | year_2023                         | true        | modCntr_module_MC_SN_SO          |

    And recompute modular logs for record:
      | TableName               | Record_ID.Identifier            |
      | M_Shipping_Notification | shippingNotification_04102023_4 |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier                  | TableName                   | ProcessingStatus | OPT.noOfLogStatuses |
      | shippingNotificationLine_04102023_3_1 | M_Shipping_NotificationLine | SP               | 4                   |
    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier                  | TableName                   |
      | shippingNotificationLine_04102023_3_1 | M_Shipping_NotificationLine |


  @from:cucumber
  Scenario: Validate that a shipping notification can be created from an order with no harvesting details

    Given metasfresh contains M_Products:
      | Identifier           | Name                            |
      | modularContract_prod | modularContract_prod_04102023_3 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod    | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.M_Warehouse_ID.Identifier | OPT.M_Locator_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_04102023_3   | warehouseModularContract      | locatorModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod    | 8          |

    When the order identified by so_order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | soLine_1                  | N             |

    And generate M_Shipping_Notification:
      | C_Order_ID.Identifier | PhysicalClearanceDate |
      | so_order              | 2022-03-04            |
    And after not more than 30s, M_Shipping_Notifications are found
      | M_Shipping_Notification_ID.Identifier | C_Order_ID.Identifier | DocStatus | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | AD_Org_ID.Identifier | M_Locator_ID.Identifier |
      | shippingNotification_04102023_4       | so_order              | CO        | bp_moduleLogSO               | bp_moduleLogSO_04102023_3             | org_1                | locatorModularContract  |
    And validate M_Shipping_NotificationLines:
      | M_Shipping_NotificationLine_ID.Identifier | M_Shipping_Notification_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier | MovementQty |
      | shippingNotificationLine_04102023_3_1     | shippingNotification_04102023_4       | s_ol_1                           | modularContract_prod    | 8           |



