@from:cucumber
@ghActions:run_on_executor3
Feature: C_BPartner.validateVATaxID SysConfig gates VATaxID format validation on save
  The SysConfig C_BPartner.validateVATaxID (default Y) controls whether the VATaxID column
  is validated against EU VAT-ID structural patterns when saving a C_BPartner record.
  When Y, invalid formats are rejected with a user error; when N, any value is accepted.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-19T10:00:00+02:00[Europe/Berlin]

  @from:cucumber
  @Id:S0613_010
  Scenario: TC1 - validation ON, invalid DE VAT-ID format is rejected on update
    Given set sys config boolean value true for sys config C_BPartner.validateVATaxID
    And metasfresh contains C_BPartners:
      | Identifier | Value      | OPT.VATaxID |
      | bp_tc1     | VatTC1Test | null        |
    When update C_BPartner expecting error:
      | Identifier | VATaxID |
      | bp_tc1     | DE12345 |
    Then an AdempiereException was thrown during the last C_BPartner update

  @from:cucumber
  @Id:S0613_020
  Scenario: TC2 - validation ON, valid DE VAT-ID is accepted and stored
    Given set sys config boolean value true for sys config C_BPartner.validateVATaxID
    And metasfresh contains C_BPartners:
      | Identifier | Value      |
      | bp_tc2     | VatTC2Test |
    When update C_BPartner:
      | Identifier | VATaxID     |
      | bp_tc2     | DE123456789 |
    Then validate C_BPartner:
      | C_BPartner_ID.Identifier | Value      | OPT.VATaxID |
      | bp_tc2                   | VatTC2Test | DE123456789 |

  @from:cucumber
  @Id:S0613_030
  Scenario: TC8 - validation OFF, invalid DE VAT-ID is accepted
    Given set sys config boolean value false for sys config C_BPartner.validateVATaxID
    And metasfresh contains C_BPartners:
      | Identifier | Value      |
      | bp_tc8     | VatTC8Test |
    When update C_BPartner:
      | Identifier | VATaxID |
      | bp_tc8     | DE12345 |
    Then validate C_BPartner:
      | C_BPartner_ID.Identifier | Value      | OPT.VATaxID |
      | bp_tc8                   | VatTC8Test | DE12345     |

  @from:cucumber
  @Id:S0613_040
  Scenario: TC7 - validation ON, update non-VATaxID column does not trigger validation even when VATaxID is invalid
    Given set sys config boolean value false for sys config C_BPartner.validateVATaxID
    And metasfresh contains C_BPartners:
      | Identifier | Value      |
      | bp_tc7     | VatTC7Test |
    When update C_BPartner:
      | Identifier | VATaxID |
      | bp_tc7     | DE12345 |
    Given set sys config boolean value true for sys config C_BPartner.validateVATaxID
    When update C_BPartner:
      | Identifier | Name        |
      | bp_tc7     | VatTC7Renamed |
    Then validate C_BPartner:
      | C_BPartner_ID.Identifier | Value      | OPT.VATaxID |
      | bp_tc7                   | VatTC7Test | DE12345     |
