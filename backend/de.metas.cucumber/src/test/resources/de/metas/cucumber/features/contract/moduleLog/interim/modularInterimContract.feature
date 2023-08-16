Feature: Interim contract settings for bpartner

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
      | interimPLV_PO | interimPL_PO              | moduleLogPLV_08022023_PO | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier   | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bp_interimPO | bp_interimPO_08022023 | Y            | N              | interimPS                     | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_interimPO_Location | 0802202312346 | bp_interimPO             | true                | true                |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |
    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | year_2023            | 2023       | harvesting_calendar      |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name            | Value           | Classname                                                               |
      | modCntr_type_PO            | modCntr_type_PO | modCntr_type_PO | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |

  Scenario: purchase modular contract created and modular settings existing for selected harvesting details
  - validate bp interim contract settings are generated
    Given metasfresh contains M_Products:
      | Identifier            | Name                                |
      | module_log_product_PO | module_log_product_PO_test_08022023 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | moduleLogPP | interimPLV_PO                     | module_log_product_PO   | 2.00     | PCE               | Normal                        |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                  | M_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | testSettings_08022023 | module_log_product_PO   | harvesting_calendar      | year_2023            | interimPS                         |
    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name            | Value           | Classname                                                               |
      | modCntr_type_1             | poLine_08022023 | poLine_08022023 | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |
    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_08022023 | module_log_product_PO   | Kosten         | modCntr_settings_1             | modCntr_type_1             |
    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                                 | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier |
      | moduleLogConditions_PO              | moduleLogConditions_po_08022023      | ModularContract | interimPS                         | Ca                       | modCntr_settings_1                 |
      | moduleLogConditions_interim         | moduleLogConditions_interim_08022023 | InterimInvoice  | interimPS                         | Ca                       | modCntr_settings_1                 |

    When invoke "C_BPartner_InterimContract_Upsert" process:
      | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimPO             | harvesting_calendar                 | year_2023                     | true              |

    Then metasfresh contains C_BPartner_InterimContract:
      | C_BPartner_InterimContract_ID.Identifier | C_BPartner_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | Harvesting_Year_ID.Identifier | IsInterimContract |
      | bp_interimContractSettings               | bp_interimPO             | harvesting_calendar                 | year_2023                     | true              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.POReference                  |
      | po_order   | false   | bp_interimPO             | 2022-03-03  | POO             | poModuleLogContract_ref_08022023 |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine   | po_order              | module_log_product_PO   | 1000       | moduleLogConditions_PO                  |
      | po_orderLine_2 | po_order              | module_log_product_PO   | 500        | moduleLogConditions_PO                  |

    When the order identified by po_order is completed

    And retrieve C_Flatrate_Term within 60s:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier |
      | moduleLogContract_1           | moduleLogConditions_PO              | module_log_product_PO   | po_order                       | po_orderLine                       |
      | moduleLogContract_2           | moduleLogConditions_interim         | module_log_product_PO   | po_order                       | po_orderLine                       |
      | moduleLogContract_3           | moduleLogConditions_PO              | module_log_product_PO   | po_order                       | po_orderLine_2                     |
      | moduleLogContract_4           | moduleLogConditions_interim         | module_log_product_PO   | po_order                       | po_orderLine_2                     |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual | OPT.M_PricingSystem_ID.Identifier | OPT.Type_Conditions | OPT.ContractStatus | OPT.DocStatus |
      | moduleLogContract_1           | moduleLogConditions_PO              | bp_interimPO                | module_log_product_PO   | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_2           | moduleLogConditions_interim         | bp_interimPO                | module_log_product_PO   | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            | interimPS                         | InterimContract     | Wa                 | CO            |
      | moduleLogContract_3           | moduleLogConditions_PO              | bp_interimPO                | module_log_product_PO   | po_orderLine_2                     | po_order                       | PCE                   | 500                   | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |
      | moduleLogContract_4           | moduleLogConditions_interim         | bp_interimPO                | module_log_product_PO   | po_orderLine_2                     | po_order                       | PCE                   | 500                   | 2.00            | interimPS                         | ModularContract     | Wa                 | CO            |


    


