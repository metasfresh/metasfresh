@ghActions:run_on_executor5
@ignore
Feature: Clone Modular Contract Term

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |
      | y2023                | 2023       | harvesting_calendar      |

  @Id:S0300_100
  @from:cucumber
  Scenario: Happy flow - clone a Modular Contract Term -> new cloned Contract Term with Settings had the new harvest year
  - Contract Term of type Modular Contract for harvest year 2022 already exists
  - clone the contract for the harvest year 2023
  - validate modular contract term cloned
  - validate modular contract term can be edited (`Draft`)
  - validate modular contract settings cloned for the harvest year 2023

    Given metasfresh contains M_Products:
      | Identifier              | Name                               |
      | contract_module_product | contract_module_product_17072023_1 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                     | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_toclone       | modCntr_settings_toclone | contract_module_product     | harvesting_calendar      | y2022                | priceSystem_1                     |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                     | Value                    | ModularContractHandlerType |
      | modCntr_Types_1            | modCntr_Types_17072023_1 | modCntr_Types_17072023_1 | PurchaseOrderLine_Modular  |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_17072023_1 | contract_module_product | Kosten         | modCntr_settings_toclone       | modCntr_Types_1            |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier               | Name                           | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | modularContractTerm_2022 | modularContractTerm_17072023_1 | ModularContract | Ex                       | modCntr_settings_toclone           | CO            |

    When clone C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | C_Year_ID.Identifier | CLONE.C_Flatrate_Conditions_ID.Identifier |
      | modularContractTerm_2022            | y2023                | clonedModularContractTerm_2022            |

    Then validate cloned C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                          | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.DocStatus | CLONE.ModCntr_Settings_ID.Identifier |
      | clonedModularContractTerm_2022      | modularContractTerm_2022_2023 | ModularContract | Ex                       | DR            | clonedModCntr_settings_1             |

    And validate cloned ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                     | M_Raw_Product_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem.Identifier |
      | clonedModCntr_settings_1       | modCntr_settings_toclone | contract_module_product     | y2023                | priceSystem_1                  |

  @Id:S0300_200
  @from:cucumber
  Scenario: Clone fail - for Modular Contract Terms with an existing harvest year
  - Modular Contract term already exists  for harvest year 2022
  - clone the Modular Contract for harvest year 2022
  - fail with a user message : Settings with the same year already exist

    Given metasfresh contains M_Products:
      | Identifier              | Name                               |
      | contract_module_product | contract_module_product_17072023_2 |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                     | M_Raw_Product_ID.Identifier | C_Calendar_ID.Identifier | C_Year_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | modCntr_settings_toclone       | modCntr_settings_toclone | contract_module_product     | harvesting_calendar      | y2022                | priceSystem_1                     |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                     | Value                    | ModularContractHandlerType |
      | modCntr_Types_1            | modCntr_Types_17072023_2 | modCntr_Types_17072023_2 | PurchaseOrderLine_Modular  |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                  | M_Product_ID.Identifier | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1             | 10    | moduleTest_17072023_2 | contract_module_product | Kosten         | modCntr_settings_toclone       | modCntr_Types_1            |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier               | Name                           | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | modularContractTerm_2022 | modularContractTerm_17072023_2 | ModularContract | Ex                       | modCntr_settings_toclone           | CO            |

    When load AD_Message:
      | Identifier              | Value                                      |
      | settings_already_exists | MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS |

    Then clone C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | C_Year_ID.Identifier | CLONE.C_Flatrate_Conditions_ID.Identifier | OPT.AD_Message_ID.Identifier |
      | modularContractTerm_2022            | y2022                | clonedModularContractTerm_2022            | settings_already_exists      |