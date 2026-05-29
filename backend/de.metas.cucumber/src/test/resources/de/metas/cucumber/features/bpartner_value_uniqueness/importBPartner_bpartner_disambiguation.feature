@from:cucumber
@ghActions:run_on_executor5
Feature: I_BPartner import BPartner Value disambiguation
  When two BPartners share the same Value (one customer, one vendor),
  the BPartnerImportTableSqlUpdater should pick the correct one based on
  the I_BPartner row's IsCustomer/IsVendor flags.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: BPartner import picks customer when I_BPartner.IsCustomer='Y'
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                  | Value                | IsCustomer | IsVendor |
      | bp_imp_cust      | Import BP Cust        | SHARED_IMP_BP_VAL    | Y          | N        |
      | bp_imp_vendor    | Import BP Vendor      | SHARED_IMP_BP_VAL    | N          | Y        |
    And metasfresh contains I_BPartner:
      | Identifier    | BPValue              | IsCustomer | IsVendor |
      | iBP_cust_1    | SHARED_IMP_BP_VAL    | Y          | N        |
    When the BPartnerImportProcess is invoked
    Then validate I_BPartner:
      | Identifier  | C_BPartner_ID  |
      | iBP_cust_1  | bp_imp_cust    |

  @from:cucumber
  Scenario: BPartner import picks vendor when I_BPartner.IsVendor='Y'
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier        | Name                   | Value                 | IsCustomer | IsVendor |
      | bp_imp_cust2      | Import BP Cust 2       | SHARED_IMP_BP_VAL2    | Y          | N        |
      | bp_imp_vendor2    | Import BP Vendor 2     | SHARED_IMP_BP_VAL2    | N          | Y        |
    And metasfresh contains I_BPartner:
      | Identifier     | BPValue               | IsCustomer | IsVendor |
      | iBP_vendor_1   | SHARED_IMP_BP_VAL2    | N          | Y        |
    When the BPartnerImportProcess is invoked
    Then validate I_BPartner:
      | Identifier    | C_BPartner_ID    |
      | iBP_vendor_1  | bp_imp_vendor2   |
