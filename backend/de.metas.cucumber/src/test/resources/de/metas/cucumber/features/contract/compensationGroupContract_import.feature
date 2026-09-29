@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract data import for compensation-group contracts
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2024,2025,2026,2027       |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer |
      | invoicePartner | Y              |

  # ##############################################################################################
  # A product-less compensation-group row (real conditions carry no product) is created and
  # completed, keeping the imported end date even though its transition has duration 0.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC28
  Scenario: Importing a compensation-group contract row without a product creates and completes it with the imported end date
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Name         | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier |
      | importConditions | Import bonus | CompensationGroup | zeroDurTrans                            |
    And metasfresh contains I_Flatrate_Term:
      | Identifier | C_BPartner_ID.Identifier | C_Flatrate_Conditions_Value | StartDate  | EndDate    |
      | row1       | invoicePartner           | Import bonus                | 2026-01-01 | 2026-12-31 |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier | IsResolved | CreatedTermEndDate | CreatedTermDocStatus |
      | row1       | true       | 2026-12-31         | CO                   |

  # ##############################################################################################
  # A second row for the same invoice partner and the same condition, covering a non-overlapping
  # period (next year), is accepted — the new type skips the undated one-term-per-condition check.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC29
  Scenario: A second import row for the same invoice partner and condition in a non-overlapping period is accepted
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Name           | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier |
      | importConditions2 | Import bonus 2 | CompensationGroup | zeroDurTrans                            |
    And metasfresh contains I_Flatrate_Term:
      | Identifier | C_BPartner_ID.Identifier | C_Flatrate_Conditions_Value | StartDate  | EndDate    |
      | rowYear1   | invoicePartner           | Import bonus 2              | 2026-01-01 | 2026-12-31 |
      | rowYear2   | invoicePartner           | Import bonus 2              | 2027-01-01 | 2027-12-31 |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier | CreatedTermDocStatus |
      | rowYear1   | CO                   |
    And validate I_Flatrate_Term:
      | Identifier | CreatedTermDocStatus |
      | rowYear2   | CO                   |

  # ##############################################################################################
  # A row without an end date is rejected — a duration-0 transition never computes one, and the
  # save is refused just as a manually entered term without an end date would be.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC30
  Scenario: An import row without an end date is rejected
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Name           | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier |
      | importConditions3 | Import bonus 3 | CompensationGroup | zeroDurTrans                            |
    And metasfresh contains I_Flatrate_Term:
      | Identifier | C_BPartner_ID.Identifier | C_Flatrate_Conditions_Value | StartDate  |
      | rowNoEnd   | invoicePartner           | Import bonus 3              | 2026-01-01 |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier | I_ErrorMsg |
      | rowNoEnd   | EndDate    |

  # ##############################################################################################
  # Two rows for the same invoice partner, different conditions sharing a common document type,
  # with overlapping periods — the second one is rejected with an error message.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC31
  Scenario: An import row overlapping an already-imported compensation-group contract is rejected with an error message
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name                |
      | importSchema | Import bonus schema |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier     | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | importSettings | Import settings | importSchema                             |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | importSettings                                     | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name            | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | importConditions4a | Import bonus 4a | CompensationGroup | zeroDurTrans                            | importSettings                                         |
      | importConditions4b | Import bonus 4b | CompensationGroup | zeroDurTrans                            | importSettings                                         |
    And metasfresh contains I_Flatrate_Term:
      | Identifier | C_BPartner_ID.Identifier | C_Flatrate_Conditions_Value | StartDate  | EndDate    |
      | rowFirst   | invoicePartner           | Import bonus 4a             | 2026-06-01 | 2026-12-31 |
      | rowOverlap | invoicePartner           | Import bonus 4b             | 2026-09-01 | 2027-03-31 |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier | CreatedTermDocStatus |
      | rowFirst   | CO                   |
    And validate I_Flatrate_Term:
      | Identifier | I_ErrorMsg                                 |
      | rowOverlap | überschneidet sich mit dem aktiven Vertrag |

  # ##############################################################################################
  # Same as above, but both rows lie fully in the past: they are set completed without going
  # through the normal completion workflow, so the import runs the overlap check explicitly.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC32
  Scenario: A past-dated import row overlapping an already-imported past-dated contract is rejected with an error message
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name                     |
      | pastSchema | Past import bonus schema |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name                 | C_CompensationGroup_Schema_ID.Identifier |
      | pastSettings | Past import settings | pastSchema                               |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | pastSettings                                       | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | pastConditionsA | Past import bonus A | CompensationGroup | zeroDurTrans                            | pastSettings                                           |
      | pastConditionsB | Past import bonus B | CompensationGroup | zeroDurTrans                            | pastSettings                                           |
    And metasfresh contains I_Flatrate_Term:
      | Identifier    | C_BPartner_ID.Identifier | C_Flatrate_Conditions_Value | StartDate  | EndDate    |
      | rowPastFirst  | invoicePartner           | Past import bonus A         | 2024-01-01 | 2024-12-31 |
      | rowPastSecond | invoicePartner           | Past import bonus B         | 2024-06-01 | 2025-03-31 |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier   | CreatedTermDocStatus |
      | rowPastFirst | CO                   |
    And validate I_Flatrate_Term:
      | Identifier    | I_ErrorMsg                                 |
      | rowPastSecond | überschneidet sich mit dem aktiven Vertrag |
