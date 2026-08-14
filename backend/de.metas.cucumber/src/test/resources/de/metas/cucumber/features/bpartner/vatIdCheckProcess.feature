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
      | VATaxID | VATaxIDStatus |
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

  @from:cucumber
  @Id:S31060_3
  Scenario: Selecting a partner also covers its location's VAT-ID, not only the partner header
    # The partner header itself carries no VATaxID at all -- only its location does. Selecting the
    # partner (the only selection the Business Partner window offers) must still reach the location.
    Given no VATaxID_CheckLog records exist for VATaxID 'ATU13585627'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier  | Value        |
      | bp_checkLoc | ProcLocTest1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID | GLN           |
      | bpl_checkLoc | bp_checkLoc   | 0123456789043 |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID     |
      | bpl_checkLoc                      | ATU13585627 |
    Then validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus |
      | bpl_checkLoc           | NotChecked    |

    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | ATU13585627 | Valid         |
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_checkLoc   |
    Then validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus | HasTaxCertificate |
      | bpl_checkLoc           | Valid         | true              |

  @from:cucumber
  @Id:S31060_4
  Scenario: A member state reports itself unavailable, so its VAT-IDs are skipped and never marked Invalid
    Given no VATaxID_CheckLog records exist for VATaxID 'EL094259216'
    And no VATaxID_CheckLog records exist for VATaxID 'DE136695976'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | Invalid              |
    And metasfresh contains C_BPartners:
      | Identifier   | Value        | VATaxID     |
      | bp_elSkip    | ProcSkipEL1  | EL094259216 |
      | bp_deChecked | ProcSkipDE1  | DE136695976 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | Invalid              |
    # OnServiceUnavailable=Invalid is the trap TC12 exists to guard: a skipped member state must never
    # be funnelled into this fail-closed policy. DE136695976 is deliberately the only VATaxID stubbed to
    # answer, so a run that (wrongly) called the checker for the skipped Greek VAT-ID fails loudly.
    And the VAT-ID online checker is stubbed to report member state 'EL' unavailable, and to answer:
      | VATaxID     | VATaxIDStatus |
      | DE136695976 | Valid         |
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_elSkip     |
      | bp_deChecked  |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_elSkip     | NotChecked    |
      | bp_deChecked  | Valid         |
    And the VAT-ID check process run reports member state 'EL' skipped 1 VAT-IDs
    # Both are each's own first-ever check (NotChecked -> its result), so neither logs a per-record
    # status-changed line -- proving both halves of the per-record logging policy in one run: the
    # skipped target (never checked at all) and the first-run suppression (checked, but still no line).
    And the VAT-ID check process run reports no status-changed line for VATaxID 'EL094259216'
    And the VAT-ID check process run reports no status-changed line for VATaxID 'DE136695976'

  @from:cucumber
  @Id:S31060_5
  Scenario: A run reports its calls and average response time, and logs no line for an unchanged re-check
    Given no VATaxID_CheckLog records exist for VATaxID 'ATU13585627'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 0                | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value         | VATaxID     |
      | bp_recheck | ProcRecheck1  | ATU13585627 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 0                | ServiceUnavailable   |
    # RecheckAfterDays=0 disables de-duplication entirely, so the SECOND run below sends a genuine
    # request rather than reusing the first run's still-fresh result.
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | ATU13585627 | Valid         |
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_recheck    |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_recheck    | Valid         |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | ATU13585627 | Valid         |
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_recheck    |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_recheck    | Valid         |
    And the VAT-ID check process run reports 1 calls with average response time 0ms
    And the VAT-ID check process run reports no status-changed line for VATaxID 'ATU13585627'

  @from:cucumber
  @Id:S31060_6
  Scenario: The nightly schedule's own selection covers every VAT-ID system-wide, not only a selection
    Given no VATaxID_CheckLog records exist for VATaxID 'IT00743110157'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier   | Value         | VATaxID       |
      | bp_scheduled | ProcSchedRun1 | IT00743110157 |
    # bp_scheduled is never put in any selection below -- the scheduled run has none at all -- so its
    # presence in the nightly selection proves the selection reaches every VAT-ID system-wide, not only a
    # selected few. The online check is ON only long enough for the read-only assertion right below --
    # which never executes a real check, so nothing is called while it is -- and is switched back OFF
    # before the actual scheduled run further down: a "no selection" sweep reaches every OTHER feature's
    # VAT-ID-bearing fixtures on this shared database too, and this is the only way to prove the selection
    # and the branch without actually checking any of them -- see the step-defs' own javadoc.
    Then the C_BPartner_VATaxID_Check nightly selection includes C_BPartner 'bp_scheduled'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    When the C_BPartner_VATaxID_Check process is run as scheduled
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_scheduled  | NotChecked    |

  @from:cucumber
  @Id:S31060_7
  Scenario: The nightly schedule's own selection excludes an organisation that has the online check switched off
    Given metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value        | VATaxID        |
      | bp_viesOff | ProcViesOff1 | NL004495445B01 |
    # bp_viesOff's own organisation has the online check switched off, so an actual check of it can never
    # do anything: VATaxIDCheckService#check returns before touching the online service or writing
    # anything, and the record stays NotChecked forever. A never-checked record sorts first of all in the
    # nightly candidate list -- if such a record were still LISTED as due, it would permanently occupy the
    # front of every night's selection and could consume the whole MaxChecksPerRun budget without ever
    # making progress, starving out every other, checkable record behind it.
    Then the C_BPartner_VATaxID_Check nightly selection does not include C_BPartner 'bp_viesOff'
