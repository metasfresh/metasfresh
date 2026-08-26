@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
Feature: VAT-ID is checked against the online validation service
  A Business Partner's VAT-ID is checked against the EU validation service (VIES) and the outcome is
  recorded twice: as an individual, permanent check record — the legal evidence that a check was made,
  which value was checked and when — and as the status shown on the partner itself.

  The offline format check runs first, so a value it rejects never reaches the service. A VAT-ID whose
  country the service does not cover is recorded as "not supported" and keeps counting as a tax
  certificate: the service can never confirm a Swiss number, and treating that as invalid would tax
  correctly zero-rated supplies. While the last result is still younger than the configured
  re-check interval the stored result is kept and no new request is sent, which is what makes an
  unreachable service harmless.

  The online service is stubbed in these scenarios: no test may depend on a live third-party service.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-19T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |

  @from:cucumber
  @Id:S0614_010
  Scenario: TC1 — a genuine EU VAT-ID becomes Valid and the attempt is recorded
    Given no VATaxID_CheckLog records exist for VATaxID 'DE136695976'
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus | RequestIdentifier |
      | DE136695976 | Valid         | WAPIAAAAWkGa5Fka  |
    And metasfresh contains C_BPartners:
      | Identifier | Value       | VATaxID     |
      | bp_vies1   | ViesTC1Test | DE136695976 |
    When the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies1      |
    Then the VAT-ID check returned status 'Valid' for C_BPartner 'bp_vies1'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate | VATaxID_CheckLog_ID |
      | bp_vies1      | Valid         | 2026-06-19T10:00:00 | true              | true                |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies1':
      | VATaxID     | VATaxIDStatus | RequestDate         | ResponseDate        | RequestIdentifier |
      | DE136695976 | Valid         | 2026-06-19T10:00:00 | 2026-06-19T10:00:00 | WAPIAAAAWkGa5Fka  |

  @from:cucumber
  @Id:S0614_020
  Scenario: TC2 — a well-formed but non-existent EU VAT-ID becomes Invalid
    Given no VATaxID_CheckLog records exist for VATaxID 'ATU13585627'
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | ATU13585627 | Invalid       |
    And metasfresh contains C_BPartners:
      | Identifier | Value       | VATaxID     |
      | bp_vies2   | ViesTC2Test | ATU13585627 |
    When the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies2      |
    Then the VAT-ID check returned status 'Invalid' for C_BPartner 'bp_vies2'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
      | bp_vies2      | Invalid       | 2026-06-19T10:00:00 | false             |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies2':
      | VATaxID     | VATaxIDStatus | RequestDate         | ResponseDate        |
      | ATU13585627 | Invalid       | 2026-06-19T10:00:00 | 2026-06-19T10:00:00 |

  @from:cucumber
  @Id:S0614_030
  Scenario: TC4 — a CH VAT-ID is format-checked, becomes NotSupported and still counts as a tax certificate
    Given no VATaxID_CheckLog records exist for VATaxID 'CHE100155212MWST'
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID           | VATaxIDStatus |
      | CHE100155212MWST  | NotSupported  |
    And metasfresh contains C_BPartners:
      | Identifier | Value       | VATaxID          |
      | bp_vies3   | ViesTC4Test | CHE100155212MWST |
    When the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies3      |
    Then the VAT-ID check returned status 'NotSupported' for C_BPartner 'bp_vies3'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
      | bp_vies3      | NotSupported  | 2026-06-19T10:00:00 | true              |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies3':
      | VATaxID          | VATaxIDStatus | RequestDate         |
      | CHE100155212MWST | NotSupported  | 2026-06-19T10:00:00 |

  @from:cucumber
  @Id:S0614_040
  Scenario: TC7 — service unreachable while the last result is inside the re-check interval keeps that result
    # The clock is set BEFORE the checker is stubbed and the partner is created, not after: an after-commit
    # trigger schedules a check the moment "metasfresh contains C_BPartners" commits, so the clock has to
    # already read 2026-06-17 at that point, or that auto-triggered check would land its evidence row on
    # the Background's 2026-06-19 instead.
    Given no VATaxID_CheckLog records exist for VATaxID 'EE100594102'
    And metasfresh has date and time 2026-06-17T10:00:00+02:00[Europe/Berlin]
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | EE100594102 | Valid         |
    And metasfresh contains C_BPartners:
      | Identifier | Value       | VATaxID     |
      | bp_vies4   | ViesTC7Test | EE100594102 |
    # The save above schedules a check of its own; wait for it here, so it cannot still be in flight when the
    # clock jumps below and land its evidence row on 2026-06-19 instead of 2026-06-17.
    And the automatically scheduled VAT-ID check has completed
    And the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies4      |
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    |
      | bp_vies4      | Valid         | 2026-06-17T10:00:00 |
    Given metasfresh has date and time 2026-06-19T10:00:00+02:00[Europe/Berlin]
    And the VAT-ID online checker is stubbed to be unreachable
    When the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies4      |
    Then the VAT-ID check returned status 'Valid' for C_BPartner 'bp_vies4'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
      | bp_vies4      | Valid         | 2026-06-17T10:00:00 | true              |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies4':
      | VATaxID     | VATaxIDStatus | RequestDate         | ResponseDate        |
      | EE100594102 | Valid         | 2026-06-17T10:00:00 | 2026-06-17T10:00:00 |
    And the VAT-ID online checker was not called

  @from:cucumber
  @Id:S0614_050
  Scenario Outline: TC8 — service unreachable while the last result is older than the re-check interval applies the configured behaviour
    # The counterpart of TC7: outside the re-check interval the stored result is no longer good enough, so
    # the organisation's OnServiceUnavailable choice decides — fail open (Service unavailable, still a tax
    # certificate) or fail closed (Invalid, no tax certificate). Both configured values are covered, because
    # this is the one setting that can remove a tax certificate without VIES ever saying "invalid".
    # The first check is 49 days before the second, i.e. beyond the configured RecheckAfterDays of 30.
    # The clock is set BEFORE the checker is stubbed and the partner is created (see TC7's comment above):
    # otherwise the after-commit trigger's own check — fired the moment "metasfresh contains C_BPartners"
    # commits — would land its evidence row on the Background's 2026-06-19 instead of 2026-05-01.
    Given no VATaxID_CheckLog records exist for VATaxID '<VATaxID>'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable   |
      | true                 | true               | 30               | <OnServiceUnavailable> |
    And metasfresh has date and time 2026-05-01T10:00:00+02:00[Europe/Berlin]
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID   | VATaxIDStatus |
      | <VATaxID> | Valid         |
    And metasfresh contains C_BPartners:
      | Identifier | Value          | VATaxID   |
      | bp_vies5   | <BPartnerName> | <VATaxID> |
    # The save above schedules a check of its own; wait for it here, or it lands after the clock jump below,
    # finds the post-jump row inconclusive, and records a third evidence row dated 2026-06-19.
    And the automatically scheduled VAT-ID check has completed
    And the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies5      |
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    |
      | bp_vies5      | Valid         | 2026-05-01T10:00:00 |
    Given metasfresh has date and time 2026-06-19T10:00:00+02:00[Europe/Berlin]
    And the VAT-ID online checker is stubbed to be unreachable
    When the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vies5      |
    Then the VAT-ID check returned status '<ExpectedStatus>' for C_BPartner 'bp_vies5'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus    | VATaxIDCheckedAt    | HasTaxCertificate   |
      | bp_vies5      | <ExpectedStatus> | 2026-06-19T10:00:00 | <HasTaxCertificate> |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies5':
      | VATaxID   | VATaxIDStatus    | RequestDate         | ResponseDate        |
      | <VATaxID> | Valid            | 2026-05-01T10:00:00 | 2026-05-01T10:00:00 |
      | <VATaxID> | <ExpectedStatus> | 2026-06-19T10:00:00 | 2026-06-19T10:00:00 |

    Examples:
      | OnServiceUnavailable | VATaxID      | BPartnerName    | ExpectedStatus     | HasTaxCertificate |
      | ServiceUnavailable   | SI50223054   | ViesTC8FailOpen | ServiceUnavailable | true              |
      | Invalid              | SK2022749619 | ViesTC8Closed   | Invalid            | false             |

  @from:cucumber
  @Id:S0614_060
  Scenario: TC9 — saving a new C_BPartner with a VAT-ID schedules a check automatically, after commit
    # No "the VAT-ID check runs for C_BPartner" step anywhere in this scenario: the check below is proven to
    # come from the save itself (the after-commit trigger), not from an explicit call.
    Given no VATaxID_CheckLog records exist for VATaxID 'FR40303265045'
    And metasfresh has a current user session
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID       | VATaxIDStatus | RequestIdentifier |
      | FR40303265045 | Valid         | WAPIAAAATrigNew1  |
    When metasfresh contains C_BPartners:
      | Identifier | Value       | VATaxID       |
      | bp_vies6   | ViesTC9Test | FR40303265045 |
    Then the VAT-ID online checker was called for VATaxID 'FR40303265045'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
      | bp_vies6      | Valid         | 2026-06-19T10:00:00 | true              |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies6':
      | VATaxID       | VATaxIDStatus | RequestDate         | ResponseDate        | RequestIdentifier | AD_Session_ID | AD_PInstance_ID |
      | FR40303265045 | Valid         | 2026-06-19T10:00:00 | 2026-06-19T10:00:00 | WAPIAAAATrigNew1  | true          | false           |

  @from:cucumber
  @Id:S0614_070
  Scenario: TC10 — saving a changed VAT-ID on a C_BPartner_Location schedules a check automatically
    # Covers the C_BPartner_Location branch: every other scenario in this feature drives C_BPartner only.
    # Also proves the C_BPartner_Location interceptor's own AD_Session_ID capture (a separate code path
    # from C_BPartner's, exercised by TC9) — same session step, same assertion columns as TC9.
    Given no VATaxID_CheckLog records exist for VATaxID 'BE0428759497'
    And metasfresh has a current user session
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID      | VATaxIDStatus | RequestIdentifier |
      | BE0428759497 | Valid         | WAPIAAAATrigLoc1  |
    And metasfresh contains C_BPartners:
      | Identifier  | Value        |
      | bp_vies_loc | ViesTC10Test |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID | GLN           |
      | bpl_vies_loc | bp_vies_loc   | 0285601009991 |
    When update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID      |
      | bpl_vies_loc                      | BE0428759497 |
    Then the VAT-ID online checker was called for VATaxID 'BE0428759497'
    And validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
      | bpl_vies_loc           | Valid         | 2026-06-19T10:00:00 | true              |
    And validate VATaxID_CheckLog records of C_BPartner 'bp_vies_loc':
      | VATaxID      | VATaxIDStatus | RequestDate         | ResponseDate        | RequestIdentifier | AD_Session_ID | AD_PInstance_ID |
      | BE0428759497 | Valid         | 2026-06-19T10:00:00 | 2026-06-19T10:00:00 | WAPIAAAATrigLoc1  | true          | false           |

  @from:cucumber
  @Id:S0614_080
  Scenario: TC11 — the online checker throwing an exception does not fail the save
    # The client-blew-up case (as opposed to a normal ServiceUnavailable answer, covered by TC7/TC8): the
    # save must succeed and the checker must still have been asked, even though it throws instead of
    # answering.
    Given no VATaxID_CheckLog records exist for VATaxID 'IE6433435F'
    And the VAT-ID online checker is stubbed to throw an exception
    When metasfresh contains C_BPartners:
      | Identifier | Value        | VATaxID    |
      | bp_vies7   | ViesTC11Test | IE6433435F |
    # Not the "was called for" step the other scenarios use: that one also waits for the check to reach a
    # terminal status, which a checker that throws never does -- check() unwinds before completeCheck(),
    # leaving the log row at RequestSent for good.
    Then the VAT-ID online check for VATaxID 'IE6433435F' was attempted, whatever its outcome
    And validate C_BPartner:
      | C_BPartner_ID | Value        | VATaxID    |
      | bp_vies7      | ViesTC11Test | IE6433435F |
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_vies7      | NotChecked    |
