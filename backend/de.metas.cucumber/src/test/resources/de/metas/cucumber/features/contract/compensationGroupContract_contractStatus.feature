@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract status, contract date and master start date of compensation-group contracts
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name | Value                    |
      | goodsCategory | Ware | WareS32353ContractStatus |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier |
      | discountProduct | goodsCategory                        |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name  | OPT.IsAdditive |
      | bonusSchema | Bonus | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | bonusSchemaLine | bonusSchema                              | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | bonusSettings | Bonus settings | bonusSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | bonusSettings                                      | docTypeSalesOrder       |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | invoicePartner | Y              | contractPS                    |


  # ##############################################################################################
  # A contract whose start date lies in the future:
  # - completing it sets "not yet started" (Wa), the contract date (creation day) and the master start date
  # - the daily status update sets "running" (Ru) once the start date is reached
  # - after the end date, without an extension, it sets "contract end" (Ec)
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC101
  Scenario: A compensation-group contract starting in the future is not yet started, then running, then at its contract end
    Given metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | bonusConditions | Bonus conditions | CompensationGroup | zeroDurTrans                            | bonusSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | term       | bonusConditions                     | invoicePartner              | 2026-08-01 | 2026-12-31 | DR            | false         |

    When the C_Flatrate_Term identified by term is completed
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus | DateContracted | MasterStartDate |
      | Wa             | 2026-07-01     | 2026-08-01      |

    # the day before the start date: nothing changes
    And metasfresh has date and time 2026-07-31T09:00:00+02:00[Europe/Berlin]
    When the daily contract status update of compensation-group contracts runs
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus |
      | Wa             |

    # the start date
    And metasfresh has date and time 2026-08-01T09:00:00+02:00[Europe/Berlin]
    When the daily contract status update of compensation-group contracts runs
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus |
      | Ru             |

    # the end date: still running
    And metasfresh has date and time 2026-12-31T09:00:00+01:00[Europe/Berlin]
    When the daily contract status update of compensation-group contracts runs
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus |
      | Ru             |

    # the day after the end date, not extended
    And metasfresh has date and time 2027-01-01T09:00:00+01:00[Europe/Berlin]
    When the daily contract status update of compensation-group contracts runs
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus | DateContracted | MasterStartDate |
      | Ec             | 2026-07-01     | 2026-08-01      |


  # ##############################################################################################
  # A running contract that is cancelled keeps its "quit" status (Qu) after its end date.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC102
  Scenario: A cancelled compensation-group contract keeps its quit status after its end date
    Given metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_Contract_Change:
      | Identifier   | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | contractChg1 | zeroDurTrans                        | ST     | Qu             | 0        | day          |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | bonusConditions | Bonus conditions | CompensationGroup | zeroDurTrans                            | bonusSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | term       | bonusConditions                     | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |

    When the C_Flatrate_Term identified by term is completed
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus | DateContracted | MasterStartDate |
      | Ru             | 2026-07-01     | 2026-06-01      |

    When the C_Flatrate_Term identified by term is cancelled with change date 2026-09-30
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus |
      | Qu             |

    And metasfresh has date and time 2027-01-15T09:00:00+01:00[Europe/Berlin]
    When the daily contract status update of compensation-group contracts runs
    Then the C_Flatrate_Term identified by term has the contract status and dates:
      | ContractStatus |
      | Qu             |


  # ##############################################################################################
  # A contract renewed by the scheduled extension:
  # - the follow-up term starts in the future when it is completed: "not yet started" (Wa),
  #   its master start date is the first term's start date, its contract date the day it was created
  # - after the first term's end date the first term stays "running" (it was extended),
  #   the follow-up term becomes "running"
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC103
  Scenario: A renewed compensation-group contract: the extended term stays running, the follow-up term carries the master start date and starts running on its start date
    Given metasfresh has date and time 2026-01-01T09:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.IsAutoCompleteNewTerm | OPT.EnsurePeriodsForYears |
      | oneYearTrans | 1            | year             | 1                | month                | EO                | true                      | 2026,2027,2028            |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | bonusConditions | Bonus conditions | CompensationGroup | oneYearTrans                            | bonusSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | firstTerm  | bonusConditions                     | invoicePartner              | 2026-01-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by firstTerm is completed
    Then the C_Flatrate_Term identified by firstTerm has the contract status and dates:
      | ContractStatus | DateContracted | MasterStartDate |
      | Ru             | 2026-01-01     | 2026-01-01      |

    # the notice date has passed
    And metasfresh has date and time 2026-12-15T09:00:00+01:00[Europe/Berlin]
    When the scheduled contract extension runs for the C_Flatrate_Term identified by firstTerm
    Then the C_Flatrate_Term identified by firstTerm has the follow-up C_Flatrate_Term:
      | Identifier | StartDate  | EndDate    | DocStatus |
      | nextTerm   | 2027-01-01 | 2027-12-31 | CO        |
    And the C_Flatrate_Term identified by nextTerm has the contract status and dates:
      | ContractStatus | DateContracted | MasterStartDate |
      | Wa             | 2026-12-15     | 2026-01-01      |

    And metasfresh has date and time 2027-01-02T09:00:00+01:00[Europe/Berlin]
    When the daily contract status update of compensation-group contracts runs
    Then the C_Flatrate_Term identified by firstTerm has the contract status and dates:
      | ContractStatus |
      | Ru             |
    And the C_Flatrate_Term identified by nextTerm has the contract status and dates:
      | ContractStatus | MasterStartDate |
      | Ru             | 2026-01-01      |
