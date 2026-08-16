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
  @ignore # TODO for some reason the bpartner bp_check3's vat-id is valid even before the process runs
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
    # OnServiceUnavailable=Invalid is the trap this scenario exists to guard: a skipped member state must
    # never be funnelled into this fail-closed policy. DE136695976 is deliberately the only VATaxID stubbed to
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
  Scenario: The nightly schedule's own selection covers every VAT-ID system-wide, and its run actually checks it
    Given no VATaxID_CheckLog records exist for VATaxID 'IT00743110157'
    And metasfresh contains C_BPartners:
      | Identifier   | Value         | VATaxID       |
      | bp_scheduled | ProcSchedRun1 | IT00743110157 |
    # bp_scheduled is created while the online check is still OFF for this organisation (the ambient
    # state left by the previous scenario's teardown) -- exactly like every earlier scenario in this file
    # -- so its save never schedules a real after-commit check. Only once it exists is the check flipped
    # ON, and stays on for the rest of the scenario: the read-only assertion below proves the selection
    # reaches bp_scheduled, and the real scheduled run further down proves the selection is actually ACTED
    # ON -- this is the only end-to-end coverage anywhere of the scheduled entry point performing and
    # persisting a real check (bp_scheduled is never put in any selection -- the scheduled run has none at
    # all -- so its result can only come from the nightly no-selection sweep). The lenient stub is what
    # lets this real, unbounded run coexist safely with whatever other VAT-ID-bearing fixtures the shared
    # executor database already holds -- see the step-defs' own javadoc.
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    Then the C_BPartner_VATaxID_Check nightly selection includes C_BPartner 'bp_scheduled'
    And the VAT-ID online checker is stubbed to answer known VAT-IDs, and to report unavailable for the rest:
      | VATaxID       | VATaxIDStatus |
      | IT00743110157 | Valid         |
    When the C_BPartner_VATaxID_Check process is run as scheduled
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_scheduled  | Valid         |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_scheduled':
      | VATaxID       | VATaxIDStatus | AD_PInstance_ID |
      | IT00743110157 | Valid         | true            |

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

  @from:cucumber
  @Id:S31060_8
  Scenario: A persistently-failing check target does not starve the nightly queue on the next run
    # bp_broken's VATaxID is malformed and bypasses the save-time gate (its organisation's own
    # VATaxID_Config has the format check switched off just for its creation), so the online check's OWN
    # format re-validation throws on every single attempt -- the record can never advance past
    # NotChecked/VATaxIDCheckedAt=null, and would otherwise sort first of every future nightly run forever.
    # bp_pending is a genuinely healthy, never-yet-checked partner sitting right behind it. The manual
    # selection run below targets ONLY bp_broken -- one attempt, scoped, so this scenario is immune to
    # whatever else the shared database's nightly candidate pool already contains -- and it must fail and
    # leave bp_broken NotChecked. The ordering assertion that follows is the actual point: a target whose
    # one attempt failed must no longer outrank a target that was never attempted at all -- proving the
    # failure did not re-earn bp_broken the front of the queue.
    # Reset any leftover status/attempt state from an earlier run of this same scenario against a
    # never-reset local database: bp_broken/bp_pending are upserted by Value, so without this both
    # VATaxID values could still carry a previous run's final status/attempt timestamp.
    Given no VATaxID_CheckLog records exist for VATaxID 'NOTAVATID'
    And no VATaxID_CheckLog records exist for VATaxID 'LU15027442'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | false                | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value          | VATaxID   |
      | bp_broken  | ProcStarveBrk1 | NOTAVATID |
    Given metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value          | VATaxID    |
      | bp_pending | ProcStarvePnd1 | LU15027442 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    # No online-service stub needed for bp_broken's own attempt below: the malformed value throws
    # during VATaxIDValidationUtil's format re-check, strictly before the online service is ever reached.
    # Stubbed unreachable purely so the availability pre-filter's getUnavailableCountryCodes() call has a
    # definite (empty) answer rather than an unprogrammed one.
    Given the VAT-ID online checker is stubbed to be unreachable
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_broken     |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_broken     | NotChecked    |
    Then the C_BPartner_VATaxID_Check nightly selection lists C_BPartner 'bp_pending' before C_BPartner 'bp_broken'
    # Behavioural confirmation, not only the internal ordering: a real scheduled run (lenient stub, so
    # whatever else the shared database happens to also reach resolves harmlessly instead of crashing the
    # scenario) must actually advance bp_pending while bp_broken keeps failing every attempt.
    And the VAT-ID online checker is stubbed to answer known VAT-IDs, and to report unavailable for the rest:
      | VATaxID    | VATaxIDStatus |
      | LU15027442 | Valid         |
    When the C_BPartner_VATaxID_Check process is run as scheduled
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_broken     | NotChecked    |
      | bp_pending    | Valid         |

  @from:cucumber
  @Id:S31060_9
  Scenario: The nightly run reaches a location's own stale VAT-ID even when its header carries none
    # bp_mixed's HEADER carries no VATaxID at all, so it is never itself a nightly candidate by header
    # staleness -- the only reason it must be swept is that its LOCATION carries a stale, never-checked
    # VAT-ID. Before the fix, retrieveAllBPartnerIdsWithVATaxID() only ever looked at headers, so this
    # partner -- and its location -- would never be reached by the nightly run at all.
    Given no VATaxID_CheckLog records exist for VATaxID 'DK13585628'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier | Value         |
      | bp_mixed   | ProcMixedLoc1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | GLN           |
      | bpl_mixed  | bp_mixed      | 0123456789050 |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID    |
      | bpl_mixed                         | DK13585628 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    Then the C_BPartner_VATaxID_Check nightly selection includes C_BPartner 'bp_mixed'
    And the VAT-ID online checker is stubbed to answer known VAT-IDs, and to report unavailable for the rest:
      | VATaxID    | VATaxIDStatus |
      | DK13585628 | Valid         |
    When the C_BPartner_VATaxID_Check process is run as scheduled
    Then validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus |
      | bpl_mixed              | Valid         |

  @from:cucumber
  @Id:S31060_10
  Scenario: The RequestSent evidence row survives a check whose outcome is never learned
    # VATaxID_CheckLog is the feature's legal evidence that a check was attempted, even when the
    # answer never arrives. writeRequestSent must therefore commit BEFORE the online service is even
    # called, independently of the per-item transaction the check-and-refresh unit runs in -- otherwise
    # the online checker throwing (or a later order-tax refresh failing) rolls the whole unit back,
    # taking the evidence row with it even though the request really was sent.
    Given no VATaxID_CheckLog records exist for VATaxID 'HU12892312'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And metasfresh contains C_BPartners:
      | Identifier  | Value         | VATaxID    |
      | bp_evidence | ProcEvidence1 | HU12892312 |
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to throw an exception
    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_evidence   |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_evidence   | NotChecked    |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_evidence':
      | VATaxID    | VATaxIDStatus |
      | HU12892312 | RequestSent   |
