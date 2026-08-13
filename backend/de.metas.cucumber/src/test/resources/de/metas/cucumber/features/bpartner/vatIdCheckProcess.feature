@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
Feature: The VAT-ID check process runs on a selection of Business Partners
  A process, available on the Business Partner window, checks the VAT-IDs of a single partner or of a
  selection. On a selection, the number of VAT-IDs checked in one run is capped by MaxChecksPerRun
  (default 500): the rest of the selection stays untouched, and the run logs how many are still pending.
  Leaving MaxChecksPerRun empty or setting it to zero (or less) means no limit at all.

  The online service is stubbed in these scenarios: no test may depend on a live third-party service.
  Each scenario creates its partners while the online check is disabled, then enables it and stubs the
  checker before running the process — otherwise the save-time after-commit trigger (covered by
  vatIdOnlineCheck.feature) would already have checked every partner before the process ever ran.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-19T10:00:00+02:00[Europe/Berlin]

  @from:cucumber
  @Id:S31060_1
  Scenario: A selection larger than the limit checks only the limit and leaves the remainder pending
    Given no VATaxID_CheckLog records exist for VATaxID 'DE136695976'
    And no VATaxID_CheckLog records exist for VATaxID 'ATU13585627'
    And no VATaxID_CheckLog records exist for VATaxID 'EE100594102'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value          | VATaxID     |
      | bp_check1  | ProcLimitTest1 | DE136695976 |
      | bp_check2  | ProcLimitTest2 | ATU13585627 |
      | bp_check3  | ProcLimitTest3 | EE100594102 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | DE136695976 | Valid         |
      | ATU13585627 | Valid         |
      | EE100594102 | Valid         |
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '2':
      | C_BPartner_ID |
      | bp_check1     |
      | bp_check2     |
      | bp_check3     |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_check1     | Valid         |
      | bp_check2     | Valid         |
      | bp_check3     | NotChecked    |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_check1':
      | VATaxID     | VATaxIDStatus | AD_PInstance_ID |
      | DE136695976 | Valid         | true            |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_check2':
      | VATaxID     | VATaxIDStatus | AD_PInstance_ID |
      | ATU13585627 | Valid         | true            |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_check3':
    And the VAT-ID check process run reports 1 pending checks

  @from:cucumber
  @Id:S31060_2
  Scenario Outline: An empty or zero limit checks every selected VAT-ID
    Given no VATaxID_CheckLog records exist for VATaxID 'DE136695976'
    And no VATaxID_CheckLog records exist for VATaxID 'ATU13585627'
    And no VATaxID_CheckLog records exist for VATaxID 'EE100594102'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value         | VATaxID     |
      | bp_check4  | ProcNoLimit1  | DE136695976 |
      | bp_check5  | ProcNoLimit2  | ATU13585627 |
      | bp_check6  | ProcNoLimit3  | EE100594102 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | DE136695976 | Valid         |
      | ATU13585627 | Valid         |
      | EE100594102 | Valid         |
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '<MaxChecksPerRun>':
      | C_BPartner_ID |
      | bp_check4     |
      | bp_check5     |
      | bp_check6     |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_check4     | Valid         |
      | bp_check5     | Valid         |
      | bp_check6     | Valid         |

    Examples:
      | MaxChecksPerRun |
      |                 |
      | 0               |
