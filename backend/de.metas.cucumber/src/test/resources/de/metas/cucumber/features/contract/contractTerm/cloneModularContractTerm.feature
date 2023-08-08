Feature: Clone Modular Contract Term

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier                    | Name                          |
      | contract_module_product_S0300 | contract_module_product_S0300 |

    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                  |
      | harvesting_calendar      | Buchführungs-Kalender |

    And load C_Year from metasfresh:
      | C_Year_ID.Identifier | FiscalYear | C_Calendar_ID.Identifier |
      | y2022                | 2022       | harvesting_calendar      |
      | y2023                | 2023       | harvesting_calendar      |

    And metasfresh contains ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier | Name                           | M_Product_ID.Identifier       | C_Calendar_ID.Identifier | C_Year_ID.Identifier |
      | modCntr_settings_toclone_S0300 | modCntr_settings_toclone_S0300 | contract_module_product_S0300 | harvesting_calendar      | y2022                |

    And metasfresh contains ModCntr_Types:
      | ModCntr_Type_ID.Identifier | Name                  | Value                 | Classname                                                               |
      | modCntr_Types_1_S0300      | modCntr_Types_1_S0300 | modCntr_Types_1_S0300 | de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler |

    And metasfresh contains ModCntr_Modules:
      | ModCntr_Module_ID.Identifier | SeqNo | Name                   | M_Product_ID.Identifier       | InvoicingGroup | ModCntr_Settings_ID.Identifier | ModCntr_Type_ID.Identifier |
      | modCntr_module_1_S0300       | 10    | modCntr_module_1_S0300 | contract_module_product_S0300 | Kosten         | modCntr_settings_toclone_S0300 | modCntr_Types_1_S0300      |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                      | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.ModCntr_Settings_ID.Identifier | OPT.DocStatus |
      | modularContractTerm_S0300           | modularContractTerm_S0300 | ModularContract | Ex                       | modCntr_settings_toclone_S0300     | CO            |

  @Id:S0300_100
  @from:cucumber
  Scenario: Happy flow - clone a Modular Contract Term -> new cloned Contract Term with Settings had the new harvest year
  - Contract Term of type Modular Contract for harvest year 2022 already exists
  - clone the contract for the harvest year 2023
  - validate modular contract term cloned
  - validate modular contract term can be edited (`Draft`)
  - validate modular contract settings cloned for the harvest year 2023

    When clone C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | C_Year_ID.Identifier | CLONE.C_Flatrate_Conditions_ID.Identifier |
      | modularContractTerm_S0300           | y2023                | clonedModularContractTerm_S0300_100       |

    Then validate cloned C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                                | Type_Conditions | OPT.OnFlatrateTermExtend | OPT.DocStatus | CLONE.ModCntr_Settings_ID.Identifier |
      | clonedModularContractTerm_S0300_100 | clonedModularContractTerm_S0300_100 | ModularContract | Ex                       | DR            | clonedModCntr_settings_S0300_100     |

    And validate cloned ModCntr_Settings:
      | ModCntr_Settings_ID.Identifier   | Name                           | M_Product_ID.Identifier       | C_Year_ID.Identifier |
      | clonedModCntr_settings_S0300_100 | modCntr_settings_toclone_S0300 | contract_module_product_S0300 | y2023                |

  @Id:S0300_200
  @from:cucumber
  Scenario: Clone fail - for Modular Contract Terms with an existing harvest year
  - Modular Contract term already exists  for harvest year 2022
  - clone the Modular Contract for harvest year 2022
  - fail with a user message : Settings with the same year already exist

    Given load AD_Message:
      | Identifier              | Value                                      |
      | settings_already_exists | MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS |

    Then clone C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | C_Year_ID.Identifier | CLONE.C_Flatrate_Conditions_ID.Identifier | OPT.AD_Message_ID.Identifier |
      | modularContractTerm_S0300           | y2022                | clonedModularContractTerm_S0300           | settings_already_exists      |