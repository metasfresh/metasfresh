@from:cucumber
@ghActions:run_on_executor5
Feature: BankStatement import BPartner disambiguation and AND/OR precedence
  Tests the BankStatementImportTableSqlUpdater's BPartner matching logic:
  - Unique BPartner match sets C_BPartner_ID
  - Ambiguous matches (multiple BPartners) flag an error
  - AND/OR precedence fix: rows with I_IsImported=NULL but C_BPartner_ID already set are NOT touched
  - AND/OR precedence fix: rows with I_IsImported='Y' are NOT touched

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: Unique BPartner by DebtorId sets C_BPartner_ID
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier   | Name           | Value          | IsCustomer | IsVendor | DebtorId | CreditorId |
      | bp_debtor_1  | Debtor One     | BP_DEBTOR_ONE  | Y          | N        | 99901    | 0          |
    And metasfresh contains I_BankStatement:
      | Identifier  | DebitorOrCreditorId |
      | iBS_unique  | 99901               |
    When the BankStatementImportTableSqlUpdater is invoked
    Then validate I_BankStatement import:
      | Identifier  | C_BPartner_ID |
      | iBS_unique  | bp_debtor_1   |

  @from:cucumber
  Scenario: Ambiguous BPartner match flags error
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier     | Name              | Value             | IsCustomer | IsVendor | DebtorId | CreditorId |
      | bp_ambig_1     | Ambiguous Cust 1  | BP_AMBIG_CUST_1   | Y          | N        | 99902    | 0          |
      | bp_ambig_2     | Ambiguous Cust 2  | BP_AMBIG_CUST_2   | Y          | N        | 99902    | 0          |
    And metasfresh contains I_BankStatement:
      | Identifier  | DebitorOrCreditorId |
      | iBS_ambig   | 99902               |
    When the BankStatementImportTableSqlUpdater is invoked
    Then validate I_BankStatement import:
      | Identifier  | C_BPartner_ID.isNull | I_ErrorMsg               |
      | iBS_ambig   | Y                    | Multiple BPartners found |

  @from:cucumber
  Scenario: AND/OR precedence - row with C_BPartner_ID already set and I_IsImported NULL is not overwritten
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier        | Name               | Value              | IsCustomer | IsVendor | DebtorId | CreditorId |
      | bp_preset_target  | Preset Target BP   | BP_PRESET_TARGET   | Y          | N        | 99903    | 0          |
      | bp_preset_other   | Preset Other BP    | BP_PRESET_OTHER    | Y          | N        | 99904    | 0          |
    And metasfresh contains I_BankStatement:
      | Identifier    | DebitorOrCreditorId | C_BPartner_ID    |
      | iBS_preset    | 99904               | bp_preset_target |
    When the BankStatementImportTableSqlUpdater is invoked
    Then validate I_BankStatement import:
      | Identifier    | C_BPartner_ID    | I_ErrorMsg.isNull |
      | iBS_preset    | bp_preset_target | Y                 |

  @from:cucumber
  Scenario: AND/OR precedence - already imported row is not touched
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                | Value               | IsCustomer | IsVendor | DebtorId | CreditorId |
      | bp_imported_1    | Already Imported BP | BP_ALREADY_IMPORTED | Y          | N        | 99905    | 0          |
      | bp_imported_2    | Already Imported 2  | BP_ALREADY_IMP_2    | Y          | N        | 99905    | 0          |
    And metasfresh contains I_BankStatement:
      | Identifier      | DebitorOrCreditorId | I_IsImported |
      | iBS_imported    | 99905               | Y            |
    When the BankStatementImportTableSqlUpdater is invoked
    Then validate I_BankStatement import:
      | Identifier      | C_BPartner_ID.isNull | I_ErrorMsg.isNull |
      | iBS_imported    | Y                    | Y                 |
