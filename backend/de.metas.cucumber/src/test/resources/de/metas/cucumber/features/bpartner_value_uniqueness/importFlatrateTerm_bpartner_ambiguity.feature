@from:cucumber
@ghActions:run_on_executor5
Feature: I_Flatrate_Term BPartner ambiguity detection
  When two BPartners share the same Value, the FlatrateTermImportTableSqlUpdater
  should mark the staging row as an error.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: I_Flatrate_Term: ambiguous BPartner Value marks row as error
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                  | Value              | IsCustomer | IsVendor |
      | bp_ft_cust       | FT BP Cust            | SHARED_FT_BP_VAL   | Y          | N        |
      | bp_ft_vendor     | FT BP Vendor          | SHARED_FT_BP_VAL   | N          | Y        |
    And metasfresh contains I_Flatrate_Term:
      | Identifier   | BPartnerValue      |
      | iFT_1        | SHARED_FT_BP_VAL   |
    When the FlatrateTermImportProcess is invoked
    Then validate I_Flatrate_Term:
      | Identifier | I_ErrorMsg                 |
      | iFT_1      | Multiple BPartners found   |
