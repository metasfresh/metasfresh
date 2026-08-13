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
    Given no VATaxID_CheckLog records exist for VATaxID 'EE100594102'
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID     | VATaxIDStatus |
      | EE100594102 | Valid         |
    And metasfresh contains C_BPartners:
      | Identifier | Value       | VATaxID     |
      | bp_vies4   | ViesTC7Test | EE100594102 |
    And metasfresh has date and time 2026-06-17T10:00:00+02:00[Europe/Berlin]
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
    Given no VATaxID_CheckLog records exist for VATaxID '<VATaxID>'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable   |
      | true                 | true               | 30               | <OnServiceUnavailable> |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID   | VATaxIDStatus |
      | <VATaxID> | Valid         |
    And metasfresh contains C_BPartners:
      | Identifier | Value          | VATaxID   |
      | bp_vies5   | <BPartnerName> | <VATaxID> |
    And metasfresh has date and time 2026-05-01T10:00:00+02:00[Europe/Berlin]
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
