@from:cucumber
@ghActions:run_on_executor5
Feature: Import staging table BPartner ambiguity detection
  When two BPartners share the same Value, import processes that use
  the CASE WHEN count > 1 pattern should mark the row as an error
  instead of picking arbitrarily or crashing.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: I_Product: ambiguous BPartner Value marks row as error
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                  | Value              | IsCustomer | IsVendor |
      | bp_prod_cust     | Product BP Cust       | SHARED_PROD_BP_VAL | Y          | N        |
      | bp_prod_vendor   | Product BP Vendor     | SHARED_PROD_BP_VAL | N          | Y        |
    And metasfresh contains I_Product:
      | Identifier   | BPartner_Value     |
      | iProd_1      | SHARED_PROD_BP_VAL |
    When the ImportProduct process is invoked
    Then validate I_Product:
      | Identifier | I_ErrorMsg                 |
      | iProd_1    | Multiple BPartners found   |
