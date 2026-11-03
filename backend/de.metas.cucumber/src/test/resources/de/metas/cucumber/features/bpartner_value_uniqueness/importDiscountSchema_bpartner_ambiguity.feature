@from:cucumber
@ghActions:run_on_executor5
Feature: I_DiscountSchema BPartner ambiguity detection
  When two BPartners share the same Value, the MDiscountSchemaImportTableSqlUpdater
  should mark the staging row as an error.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: I_DiscountSchema: ambiguous BPartner Value marks row as error
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                  | Value              | IsCustomer | IsVendor |
      | bp_ds_cust       | DS BP Cust            | SHARED_DS_BP_VAL   | Y          | N        |
      | bp_ds_vendor     | DS BP Vendor          | SHARED_DS_BP_VAL   | N          | Y        |
    And metasfresh contains I_DiscountSchema:
      | Identifier   | BPartner_Value     |
      | iDS_1        | SHARED_DS_BP_VAL   |
    When the ImportDiscountSchema process is invoked
    Then validate I_DiscountSchema:
      | Identifier | I_ErrorMsg                 |
      | iDS_1      | Multiple BPartners found   |
