@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66030_Partner_Location_VAT_ID
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
  Scenario: validation enabled — invalid DE format is rejected on update
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
  Scenario: validation enabled — valid DE format is accepted and stored
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
  Scenario: validation disabled — any value is accepted
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
  @Id:S0613_050
  Scenario: C_BPartner_Location — validation enabled — invalid AT format is rejected on update
    Given set sys config boolean value true for sys config C_BPartner.validateVATaxID
    And metasfresh contains C_BPartners:
      | Identifier | Value          |
      | bp_loc_tc1 | VatLocTC1Test  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | GLN           |
      | bpl_tc1    | bp_loc_tc1               | 0285601001050 |
    When update C_BPartner_Location expecting error:
      | C_BPartner_Location_ID.Identifier | VATaxID  |
      | bpl_tc1                           | ATU1234  |
    Then an AdempiereException was thrown during the last C_BPartner_Location update

  @from:cucumber
  @Id:S0613_060
  Scenario: C_BPartner_Location — validation enabled — valid AT format is accepted and stored
    Given set sys config boolean value true for sys config C_BPartner.validateVATaxID
    And metasfresh contains C_BPartners:
      | Identifier | Value          |
      | bp_loc_tc2 | VatLocTC2Test  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | GLN           |
      | bpl_tc2    | bp_loc_tc2               | 0285601001051 |
    When update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.VATaxID  |
      | bpl_tc2                           | ATU12345678  |
    Then validate C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.VATaxID  |
      | bpl_tc2                           | ATU12345678  |

  @from:cucumber
  @Id:S0613_040
  Scenario: updating a non-VATaxID column does not trigger re-validation of a stored value
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
