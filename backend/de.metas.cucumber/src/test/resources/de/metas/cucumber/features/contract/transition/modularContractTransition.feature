@ignore
Feature: Calculation of Modular Contract Transition

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: no Modular Contract Transition for the Modular Contract Settings Calendar exists
  Create new Flatrate Conditions and complete it -> modular contract transition is generated for the respective calendar based on default transition template
  Validate the created transition and that is correctly set on C_Flatrate_Conditions.C_Flatrate_Transition_ID

    Given metasfresh contains C_Calendar
      | C_Calendar_ID.Identifier | Name            |
      | calendar_1               | test_10242023_1 |

    And metasfresh contains C_Year
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | calendar_1               |

    And metasfresh contains C_Period
      | C_Period_ID.Identifier | C_Year_ID.Identifier | Name      | StartDate  | OPT.EndDate | OPT.PeriodNo |
      | period_1               | y2022                | test_2022 | 2022-01-01 | 2022-12-31  | 0            |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                       | Value                      | ModularContractHandlerType |
      | modCntr_type_1             | modCntr_type_PO_10242023_1 | modCntr_type_PO_10242023_1 | PurchaseOrderLine_Modular  |

    And metasfresh contains M_Products:
      | Identifier              | Name                               |
      | contract_module_product | contract_module_product_10242023_1 |

    And metasfresh contains M_PricingSystems
      | Identifier    | Name           | Value         | OPT.Description | OPT.IsActive |
      | priceSystem_1 | pricingSysName | pricingSysVal | pricingSysDesc  | true         |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                        | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | modCntr_settings_10242023_1 | contract_module_product     | calendar_1               | y2022                | priceSystem_1                     |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                     | Value                    | ModularContractHandlerType |
      | modCntr_Types_1            | modCntr_Types_10242023_1 | modCntr_Types_10242023_1 | PurchaseOrderLine_Modular  |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_10242024_1 | contract_module_product | Kosten         | modCntr_settings_1             | modCntr_Types_1            |

    When metasfresh contains C_Flatrate_Conditions:
      | Identifier               | Name                           | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | modularContractTerm_2022 | modularContractTerm_10242023_1 | ModularContract | Ex                       | modCntr_settings_1                 | DR            |

    And the C_Flatrate_Conditions identified by modularContractTerm_2022 is completed

    And load C_Flatrate_Transition from metasfresh:
      | C_Flatrate_Transition_ID.Identifier | Name                                                   |
      | transition_2                        | Modularer Vertrag Übergangsbedingungen test_10242023_1 |

    And validate C_Flatrate_Transition
      | C_Flatrate_Transition_ID.Identifier | OPT.C_Calendar_Contract_ID.Identifier | OPT.EndsWithCalendarYear | OPT.TermDuration | OPT.TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.IsNotifyUserInCharge |
      | transition_2                        | calendar_1                            | true                     | 1                | year                 | 0                | day                  | false                    |

    Then validate C_Flatrate_Conditions
      | C_Flatrate_Conditions_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier |
      | modularContractTerm_2022            | transition_2                            |

  @from:cucumber
  Scenario: Modular Contract Transition exists for the Modular Contract Settings Calendar
  Create new Flatrate Conditions and complete it -> the existing transition is used, no additional transition is created
  Validate that the Modular Contract Transition is correctly set on C_Flatrate_Conditions.C_Flatrate_Transition_ID
  Create a new contract using the above mentioned Flatrate Conditions -> the Modular Contract End Date is set to the last Day of Modular Contract Settings Calendar and Year

    Given metasfresh contains C_Calendar
      | C_Calendar_ID.Identifier | Name            |
      | calendar_1               | test_10242023_2 |

    And metasfresh contains C_Year
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2021                | 2021       | calendar_1               |

    And metasfresh contains C_Period
      | C_Period_ID.Identifier | C_Year_ID.Identifier | Name      | StartDate  | OPT.EndDate | OPT.PeriodNo |
      | period_1               | y2021                | test_2021 | 2021-01-01 | 2021-12-31  | 0            |

    And metasfresh contains C_Flatrate_Transition
      | C_Flatrate_Transition_ID.Identifier | Name            | C_Calendar_Contract_ID.Identifier | EndsWithCalendarYear | TermDuration | TermDurationUnit | TermOfNotice | TermOfNoticeUnit | IsNotifyUserInCharge |
      | transition_1                        | test_10242023_2 | calendar_1                        | true                 | 1            | year             | 0            | day              | false                |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                       | Value                      | ModularContractHandlerType |
      | modCntr_type_1             | modCntr_type_PO_10242023_2 | modCntr_type_PO_10242023_2 | PurchaseOrderLine_Modular  |

    And metasfresh contains M_Products:
      | Identifier              | Name                               |
      | contract_module_product | contract_module_product_10242023_2 |

    And metasfresh contains M_PricingSystems
      | Identifier    | Name           | Value         | OPT.Description | OPT.IsActive |
      | priceSystem_1 | pricingSysName | pricingSysVal | pricingSysDesc  | true         |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                        | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_1             | modCntr_settings_10242023_2 | contract_module_product     | calendar_1               | y2021                | priceSystem_1                     |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                     | Value                    | ModularContractHandlerType |
      | modCntr_Types_1            | modCntr_Types_10242023_2 | modCntr_Types_10242023_2 | PurchaseOrderLine_Modular  |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_10242024_2 | contract_module_product | Kosten         | modCntr_settings_1             | modCntr_Types_1            |

    When metasfresh contains C_Flatrate_Conditions:
      | Identifier               | Name                           | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | modularContractTerm_2021 | modularContractTerm_10242023_2 | ModularContract | Ex                       | modCntr_settings_1                 | DR            |

    And the C_Flatrate_Conditions identified by modularContractTerm_2021 is completed

    Then validate C_Flatrate_Conditions
      | C_Flatrate_Conditions_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier |
      | modularContractTerm_2021            | transition_1                            |

    And metasfresh contains M_PricingSystems
      | Identifier                        | Name                              | Value                             |
      | moduleLogPricingSystem_10242023_2 | moduleLogPricingSystem_10242023_2 | moduleLogPricingSystem_10242023_2 |

    And metasfresh contains C_BPartners:
      | Identifier                | Name                      | OPT.IsVendor | M_PricingSystem_ID.Identifier     | OPT.C_PaymentTerm_ID.Value |
      | bp_moduleLogPO_10242023_2 | bp_moduleLogPO_10242023_2 | Y            | moduleLogPricingSystem_10242023_2 | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier  | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_moduleLogPO_Location | 6824298505483 | bp_moduleLogPO_10242023_2 | true                | true                |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_10242023_2      | warehouse_10242023_2 | warehouse_10242023_2 | bp_moduleLogPO_10242023_2    | bp_moduleLogPO_Location               |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locator                 | locator_10242023_2 | warehouse_10242023_2      |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier                   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | OPT.M_Product_ID.Identifier | OPT.DocStatus | OPT.Processed |
      | moduleLogContract_10242023_2 | modularContractTerm_2021            | bp_moduleLogPO_10242023_2   | 2021-01-01 | contract_module_product     | DR            | false         |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.EndDate |
      | moduleLogContract_10242023_2  | modularContractTerm_2021            | bp_moduleLogPO_10242023_2   | contract_module_product | 2021-12-31  |