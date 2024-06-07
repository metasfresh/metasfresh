@ignore
@ghActions:run_on_executor5
Feature: Modular contract log from sales order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                            | Value                           |
      | moduleLogPricingSystem | moduleLogPricingSystem_07262023 | moduleLogPricingSystem_07262023 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision |
      | moduleLogPL_PO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_PO_07262023 | false | false         | 2              |
      | moduleLogPL_SO | moduleLogPricingSystem        | DE                        | EUR                 | moduleLogPL_SO_07262023 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | moduleLogPLV_PO | moduleLogPL_PO            | moduleLogPLV_07262023_PO | 2022-02-01 |
      | moduleLogPLV_SO | moduleLogPL_SO            | moduleLogPLV_07262023_SO | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier     | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO | bp_moduleLogPO_07262023 | Y            | N              | moduleLogPricingSystem        | 1000002                    |
      | bp_moduleLogSO | bp_moduleLogSO_07262023 | N            | Y              | moduleLogPricingSystem        | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 0726202312346 | bp_moduleLogPO           | true                | true                |
      | bp_moduleLogSO_Location | 0726202312345 | bp_moduleLogSO           | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value              | Name               | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseModularContract  | warehouse_07262023 | warehouse_07262023 | bp_moduleLogPO               | bp_moduleLogPO_Location               |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value            | M_Warehouse_ID.Identifier |
      | locatorModularContract  | locator_07262023 | warehouseModularContract  |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |
      | year_2022            | 2022       | harvesting_calendar      |
      | year_2021            | 2021       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                            | Value                           | ModularContractHandlerType |
      | modCntr_type_PO            | modCntr_type_PO_09212023        | modCntr_type_PO_09212023        | PurchaseOrderLine_Modular  |
      | modCntr_type_SO_for_PO     | modCntr_type_SO_for_PO_09212023 | modCntr_type_SO_for_PO_09212023 | SOLineForPO_Modular        |
      | modCntr_type_SO            | modCntr_type_SO_09212023        | modCntr_type_SO_09212023        | SalesOrderLine_Modular     |
      | modCntr_type_MC_SO         | modCntr_type_MC_SO_09212023     | modCntr_type_MC_SO_09212023     | SalesModularContract       |
      | modCntr_type_MC_PO         | modCntr_type_MC_PO_09212023     | modCntr_type_MC_PO_09212023     | PurchaseModularContract    |


  @Id:S0298_100
  @from:cucumber
  Scenario: When a sales order is completed, create a modular contract log for each of the lines that require it
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order and complete it
  - validate that for each line a modular contract log is created

    Given metasfresh contains M_Products:
      | Identifier           | Name                          |
      | modularContract_prod | modularContract_prod_07262023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod    | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.IsSOTrx |
      | modCntr_settings_2023          | testSettings_07262023   | modularContract_prod    | harvesting_calendar      | year_2023            | moduleLogPricingSystem            | false       |
      | modCntr_settings_2023_2        | testSettings_07262023_2 | modularContract_prod    | harvesting_calendar      | year_2022            | moduleLogPricingSystem            | true        |
      | modCntr_settings_2023_3        | testSettings_07262023_3 | modularContract_prod    | harvesting_calendar      | year_2021            | moduleLogPricingSystem            | true        |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name             | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO            | 10    | name_10          | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_PO            |
      | modCntr_module_SO_for_PO     | 20    | name_20          | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SO_for_PO     |
      | modCntr_module_MC_PO         | 30    | name_30          | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_MC_PO         |
      | modCntr_module_SO_1          | 10    | name_09202023_10 | modularContract_prod    | Kosten         | modCntr_settings_2023_2        | modCntr_type_SO            |
      | modCntr_module_SO_2          | 10    | name_09202023_20 | modularContract_prod    | Kosten         | modCntr_settings_2023_3        | modCntr_type_SO            |
      | modCntr_module_MC_1          | 10    | name_09202023_30 | modularContract_prod    | Kosten         | modCntr_settings_2023_2        | modCntr_type_MC_SO         |
      | modCntr_module_MC_2          | 10    | name_09202023_40 | modularContract_prod    | Kosten         | modCntr_settings_2023_3        | modCntr_type_MC_SO         |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                  | Name                        | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023   | modularContractTerms_2023   | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023              |
      | modularContractTerms_2023_2 | modularContractTerms_2023_2 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023_2            |
      | modularContractTerms_2023_3 | modularContractTerms_2023_3 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023_3            |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_1 | modularContract_prod        | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference            | OPT.M_Warehouse_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_07262023 | warehouseModularContract      |
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

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | poLog_1                   | poLine               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_OrderLine     | moduleLogContract             | modCntr_type_PO                | false         | PurchaseOrder                | year_2023                         | false       | modCntr_module_PO                | 5.00            | PCE                       | 50         |
      | poLog_2                   | moduleLogContract    | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_Flatrate_Term | moduleLogContract             | modCntr_type_MC_PO             | false         | PurchaseModularContract      | year_2023                         | false       | modCntr_module_MC_PO             | 5.00            | PCE                       | 50         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine_1   | so_order              | modularContract_prod    | 8          | modularContractTerms_2023_2             |
      | soLine_2   | so_order              | modularContract_prod    | 3          | modularContractTerms_2023_3             |

    When the order identified by so_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_SO          | modularContractTerms_2023_2         | modularContract_prod    | so_order                       | soLine_1                           |
      | moduleLogContract_SO_2        | modularContractTerms_2023_3         | modularContract_prod    | so_order                       | soLine_2                           |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_SO          | modularContractTerms_2023_2         | bp_moduleLogPO              | modularContract_prod    | soLine_1                           | so_order                       | PCE                   | 8                     | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |
      | moduleLogContract_SO_2        | modularContractTerms_2023_3         | bp_moduleLogPO              | modularContract_prod    | soLine_2                           | so_order                       | PCE                   | 3                     | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier   | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.Description                                                                                                                                                                                   |
      | soLog_1                   | soLine_1               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 8   | C_OrderLine     | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO_for_PO         | 10.00           | PCE                       | 80         | Auftrag für Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 8 Stk wurde fertiggestellt.                                                                         |
      | soLog_2                   | soLine_2               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine     | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO_for_PO         | 10.00           | PCE                       | 30         | Auftrag für Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 3 Stk wurde fertiggestellt.                                                                         |
      | soLog_3                   | soLine_1               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | C_OrderLine     | moduleLogContract_SO          | modCntr_type_SO                | false         | SalesOrder                   | year_2022                         | true        | modCntr_module_SO_1              | 10.00           | PCE                       | 80         | Ein Auftrag, der das Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 8 Stk enthält, wurde fertiggestellt, was die Erstellung eines modularen Vertrags auslöste. |
      | soLog_4                   | soLine_2               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 3   | C_OrderLine     | moduleLogContract_SO_2        | modCntr_type_SO                | false         | SalesOrder                   | year_2021                         | true        | modCntr_module_SO_2              | 10.00           | PCE                       | 30         | Ein Auftrag, der das Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 3 Stk enthält, wurde fertiggestellt, was die Erstellung eines modularen Vertrags auslöste. |
      | soLog_5                   | moduleLogContract_SO   | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 8   | C_Flatrate_Term | moduleLogContract_SO          | modCntr_type_MC_SO             | false         | SalesModularContract         | year_2022                         | true        | modCntr_module_MC_1              | 10.00           | PCE                       | 80         | Modularer Vertrag für Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 8 Stk wurde fertiggestellt.                                                               |
      | soLog_6                   | moduleLogContract_SO_2 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 3   | C_Flatrate_Term | moduleLogContract_SO_2        | modCntr_type_MC_SO             | false         | SalesModularContract         | year_2021                         | true        | modCntr_module_MC_2              | 10.00           | PCE                       | 30         | Modularer Vertrag für Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 3 Stk wurde fertiggestellt.                                                               |
      | poLog_1                   | poLine                 | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_OrderLine     | moduleLogContract             | modCntr_type_PO                | false         | PurchaseOrder                | year_2023                         | false       | modCntr_module_PO                | 5.00            | PCE                       | 50         | Eine Bestellung für das Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 10 Stk wurde fertiggestellt.                                                            |
      | poLog_2                   | moduleLogContract      | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_Flatrate_Term | moduleLogContract             | modCntr_type_MC_PO             | false         | PurchaseModularContract      | year_2023                         | false       | modCntr_module_MC_PO             | 5.00            | PCE                       | 50         | Modularer Vertrag für Produkt modularContract_prod_07262023_modularContract_prod_07262023 mit der Menge 10 Stk wurde fertiggestellt.                                                              |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus |
      | soLine_1             | C_OrderLine | SP               |
      | soLine_2             | C_OrderLine | SP               |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | C_Order   | so_order             |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1             | C_OrderLine | SP               | 2                   |
      | soLine_2             | C_OrderLine | SP               | 2                   |
    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.Amount |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogPO                      | bp_moduleLogPO                  | 3   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | 30         |

  @Id:S0298_200
  @from:cucumber
  Scenario: When a sales order is voided, a modular contract log is created with negated qty
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order and complete it
  - validate that for each line a modular contract log is created
  - void the sales order
  - validate that for each line a modular contract log is created with negated qty

    Given metasfresh contains M_Products:
      | Identifier               | Name                              |
      | modularContract_prod_200 | modularContract_prod_200_07262023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_200 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_200 | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                  | M_Product_ID.Identifier  | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_2023          | testSettings_07262023 | modularContract_prod_200 | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name    | M_Product_ID.Identifier  | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO            | 10    | name_10 | modularContract_prod_200 | Kosten         | modCntr_settings_2023          | modCntr_type_PO            |
      | modCntr_module_SO            | 20    | name_20 | modularContract_prod_200 | Kosten         | modCntr_settings_2023          | modCntr_type_SO_for_PO     |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                | Name                      | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023 | modularContractTerms_2023 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023              |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_2 | modularContract_prod_200    | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier  |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod_200 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference            | OPT.M_Warehouse_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_07262023 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine     | po_order              | modularContract_prod_200 | 20         | modularContractTerms_2023               |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier  | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod_200 | po_order                       | poLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier  | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogPO              | modularContract_prod_200 | poLine                             | po_order                       | PCE                   | 20                    | 5.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod_200 | 10         |
      | soLine_2   | so_order              | modularContract_prod_200 | 5          |

    When the order identified by so_order is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 100        |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | 5   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 50         |

    When the order identified by so_order is voided

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 100        |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | 5   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 50         |
      | soLog_3                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | -10 | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | -100       |
      | soLog_4                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_200 | bp_moduleLogPO                      | bp_moduleLogPO                  | -5  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | -50        |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1             | C_OrderLine | SP               | 2                   |
      | soLine_2             | C_OrderLine | SP               | 2                   |

    And recompute modular logs for record:
      | TableName | Record_ID.Identifier |
      | C_Order   | so_order             |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1             | C_OrderLine | SP               | 3                   |
      | soLine_1             | C_OrderLine | SP               | 3                   |
    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName   |
      | soLine_1             | C_OrderLine |
      | soLine_2             | C_OrderLine |


  @Id:S0298_300
  @from:cucumber
  Scenario: When a sales order is reactivate, a modular contract log is created with negated qty and when the user tries to delete an order line with modular contract log and error is thrown
  - create modular contract terms
  - create a modular purchase order and complete it
  - a modular contract is automatically created
  - create a sales order and complete it
  - validate that for each line a modular contract log is created
  - reactivate the sales order
  - validate that for each line a modular contract log is created with negated qty
  - attempt to delete order line with modular contract log
  - validate an error is thrown
  - update qty on sales order line and complete order again
  - validate that for each line a modular contract log is created with the updated qty

    Given metasfresh contains M_Products:
      | Identifier               | Name                              |
      | modularContract_prod_300 | modularContract_prod_300_07262023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_PO | moduleLogPLV_PO                   | modularContract_prod_300 | 5.00     | PCE               | Normal                        |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod_300 | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                  | M_Product_ID.Identifier  | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_2023          | testSettings_07262023 | modularContract_prod_300 | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name    | M_Product_ID.Identifier  | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_PO            | 10    | name_10 | modularContract_prod_300 | Kosten         | modCntr_settings_2023          | modCntr_type_PO            |
      | modCntr_module_SO            | 20    | name_20 | modularContract_prod_300 | Kosten         | modCntr_settings_2023          | modCntr_type_SO_for_PO     |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                | Name                      | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023 | modularContractTerms_2023 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023              |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_3 | modularContract_prod_300    | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier  |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod_300 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference            | OPT.M_Warehouse_ID.Identifier |
      | po_order   | false   | bp_moduleLogPO           | 2022-03-03  | POO             | poModularContract_07262023 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | poLine     | po_order              | modularContract_prod_300 | 20         | modularContractTerms_2023               |

    When the order identified by po_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier  | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod_300 | po_order                       | poLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier  | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogPO              | modularContract_prod_300 | poLine                             | po_order                       | PCE                   | 20                    | 5.00            | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_Harvesting_Calendar_ID.Identifier | OPT.Harvesting_Year_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | harvesting_calendar                     | year_2023                         | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod_300 | 10         |
      | soLine_2   | so_order              | modularContract_prod_300 | 5          |

    When the order identified by so_order is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 100        |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 5   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 50         |

    When the order identified by so_order is reactivated

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 5   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 50         |
      | soLog_3                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | -10 | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | -100       |
      | soLog_4                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | -5  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | -50        |

    And load AD_Message:
      | Identifier           | Value                                                                 |
      | deletion_not_allowed | de.metas.contracts.modular.interceptor.C_OrderLine.DeletionNotAllowed |

    And the order line identified by soLine_1 is deleted expecting error
      | OPT.AD_Message_ID.Identifier |
      | deletion_not_allowed         |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | soLine_1                  | 12             |
      | soLine_2                  | 7              |

    When the order identified by so_order is completed

    Then after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier  | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName   | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount |
      | soLog_1                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 10  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 100        |
      | soLog_2                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 5   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 50         |
      | soLog_3                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | -10 | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | -100       |
      | soLog_4                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | -5  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | -50        |
      | soLog_5                   | soLine_1             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 12  | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 120        |
      | soLog_6                   | soLine_2             | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod_300 | bp_moduleLogPO                      | bp_moduleLogPO                  | 7   | C_OrderLine | moduleLogContract             | modCntr_type_SO_for_PO         | false         | SalesOrder                   | year_2023                         | false       | modCntr_module_SO                | 10.00           | PCE                       | 70         |

    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName   | ProcessingStatus | OPT.noOfLogStatuses |
      | soLine_1             | C_OrderLine | SP               | 3                   |
      | soLine_2             | C_OrderLine | SP               | 3                   |


  @Id:S0298_400
  @from:cucumber
  Scenario: Regression: When a sales order is completed without harvesting calendar and harvesting year populated, validate that no modular contract log is created for order lines
    Given metasfresh contains M_Products:
      | Identifier               | Name                              |
      | modularContract_prod_300 | modularContract_prod_300_07262023 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | so_07262023     | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered |
      | soLine_1   | so_order              | modularContract_prod_300 | 10         |
      | soLine_2   | so_order              | modularContract_prod_300 | 5          |

    When the order identified by so_order is completed

    Then after not more than 30s, no ModCntr_Logs are found:
      | Record_ID.Identifier | TableName   |
      | soLine_1             | C_OrderLine |
      | soLine_2             | C_OrderLine |

    And after not more than 30s, no ModCntr_Log_Statuses are found:
      | Record_ID.Identifier | TableName   |
      | soLine_1             | C_OrderLine |
      | soLine_2             | C_OrderLine |

  @Id:S0298_500
  @from:cucumber
  Scenario: When a sales order is completed, create a sales modular contract for each of the lines that require it
  - create modular contract terms
  - create a modular sales order and complete it
  - a modular contract is automatically created
  - a modular contract log is created for the sales modular contract
  - a modular contract log is created for the sales order line

    Given metasfresh contains M_Products:
      | Identifier           | Name                          |
      | modularContract_prod | modularContract_prod_09062023 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                  | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_2023          | testSettings_09062023 | modularContract_prod    | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name             | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_SO            | 10    | name_09062023_10 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SO            |
      | modCntr_module_MC            | 20    | name_09062023_20 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_MC_SO         |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                | Name                          | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023 | modularContractTerms_09062023 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023              |

    And metasfresh contains ModCntr_InvoicingGroup:
      | ModCntr_InvoicingGroup_ID.Identifier | Name                      | Group_Product_ID.Identifier | ValidFrom  | ValidTo    |
      | invoicingGroup                       | invoicingGroup_09272023_4 | modularContract_prod        | 2021-04-14 | 2022-12-12 |

    And metasfresh contains ModCntr_InvoicingGroup_Product:
      | ModCntr_InvoicingGroup_Product_ID.Identifier | ModCntr_InvoicingGroup_ID.Identifier | M_Product_ID.Identifier |
      | invoicingGroup_p1                            | invoicingGroup                       | modularContract_prod    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference            | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | soModularContract_09062023 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine     | so_order              | modularContract_prod    | 10         | modularContractTerms_2023               |

    When the order identified by so_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod    | so_order                       | soLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogSO              | modularContract_prod    | soLine                             | so_order                       | PCE                   | 10                    | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            |

    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.C_Currency_ID.ISO_Code |
      | soLog_1                   | soLine               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 10  | C_OrderLine     | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | true        | modCntr_module_SO                | 10.00           | PCE                       | 100        | EUR                        |
      | soLog_2                   | moduleLogContract    | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 10  | C_Flatrate_Term | moduleLogContract             | modCntr_type_MC_SO             | false         | SalesModularContract         | year_2023                         | true        | modCntr_module_MC                | 10.00           | PCE                       | 100        | EUR                        |

    And recompute modular logs for record:
      | TableName       | Record_ID.Identifier |
      | C_Flatrate_Term | moduleLogContract    |
    And after not more than 30s, validate ModCntr_Log_Statuses:
      | Record_ID.Identifier | TableName       | ProcessingStatus | OPT.noOfLogStatuses |
      | moduleLogContract    | C_Flatrate_Term | SP               | 2                   |
    And after not more than 30s, ModCntr_Logs are found:
      | ModCntr_Log_ID.Identifier | Record_ID.Identifier | ContractType    | OPT.ModCntr_InvoicingGroup_ID.Identifier | OPT.CollectionPoint_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | M_Product_ID.Identifier | OPT.Producer_BPartner_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | Qty | TableName       | C_Flatrate_Term_ID.Identifier | OPT.ModCntr_Type_ID.Identifier | OPT.Processed | OPT.ModCntr_Log_DocumentType | OPT.Harvesting_Year_ID.Identifier | OPT.IsSOTrx | OPT.ModCntr_Module_ID.Identifier | OPT.PriceActual | OPT.Price_UOM_ID.X12DE355 | OPT.Amount | OPT.C_Currency_ID.ISO_Code |
      | soLog_1                   | soLine               | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 10  | C_OrderLine     | moduleLogContract             | modCntr_type_SO                | false         | SalesOrder                   | year_2023                         | true        | modCntr_module_SO                | 10.00           | PCE                       | 100        | EUR                        |
      | soLog_2                   | moduleLogContract    | ModularContract | invoicingGroup                           | bp_moduleLogPO                             | warehouseModularContract      | modularContract_prod    | bp_moduleLogSO                      | bp_moduleLogSO                  | 10  | C_Flatrate_Term | moduleLogContract             | modCntr_type_MC_SO             | false         | SalesModularContract         | year_2023                         | true        | modCntr_module_MC                | 10.00           | PCE                       | 100        | EUR                        |


  @Id:S0298_600
  @from:cucumber
  Scenario: REACTIVATE | REVERSE sales order with linked sales modular contract
  - sales order created with one line with modular contract terms
  - complete SO
  - validate sales modular contract is created
  - `REACTIVATE` sales order
  - validate `ReactivateNotAllowed` error is thrown
  - `REVERSE` sales order
  - validate `ReactivateNotAllowed` error is thrown

    Given metasfresh contains M_Products:
      | Identifier           | Name                            |
      | modularContract_prod | modularContract_prod_09062023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | modularPP_SO | moduleLogPLV_SO                   | modularContract_prod    | 10.00    | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                    | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_2023          | testSettings_09062023_2 | modularContract_prod    | harvesting_calendar      | year_2023            | moduleLogPricingSystem            |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name               | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_SO            | 10    | name_09062023_10_2 | modularContract_prod    | Kosten         | modCntr_settings_2023          | modCntr_type_SO            |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                | Name                            | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | modularContractTerms_2023 | modularContractTerms_09062023_2 | ModularContract | moduleLogPricingSystem            | Ex                       | modCntr_settings_2023              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference              | OPT.M_Warehouse_ID.Identifier |
      | so_order   | true    | bp_moduleLogSO           | 2022-03-03  | soModularContract_09062023_2 | warehouseModularContract      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | soLine     | so_order              | modularContract_prod    | 10         | modularContractTerms_2023               |

    When the order identified by so_order is completed

    Then retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract             | modularContractTerms_2023           | modularContract_prod    | so_order                       | soLine                             |
    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus | OPT.Amount | OPT.C_Currency_ID.ISO_Code |
      | moduleLogContract             | modularContractTerms_2023           | bp_moduleLogSO              | modularContract_prod    | soLine                             | so_order                       | PCE                   | 10                    | 10.00           | moduleLogPricingSystem            | ModularContract     | Wa                 | CO            | 100        | EUR                        |

    And load AD_Message:
      | Identifier             | Value                                                |
      | reactivate_not_allowed | de.metas.contracts.modular.impl.ReactivateNotAllowed |

    And the order identified by so_order is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | reactivate_not_allowed       |

    And the order identified by so_order is reversed expecting error
      | OPT.AD_Message_ID.Identifier |
      | reactivate_not_allowed       |