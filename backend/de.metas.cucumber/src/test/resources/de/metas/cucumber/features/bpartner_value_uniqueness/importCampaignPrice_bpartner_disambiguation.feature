@from:cucumber
@ghActions:run_on_executor5
Feature: I_Campaign_Price BPartner Value disambiguation
  When two BPartners share the same Value, the CampaignPriceImportTableSqlUpdater
  should prefer the customer (IsCustomer='Y') since campaign prices are sales-only.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: Campaign price import picks customer when vendor has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                     | Value               | IsCustomer | IsVendor |
      | bp_cp_cust       | Campaign BP Cust         | SHARED_CP_BP_VAL    | Y          | N        |
      | bp_cp_vendor     | Campaign BP Vendor       | SHARED_CP_BP_VAL    | N          | Y        |
    And metasfresh contains I_Campaign_Price:
      | Identifier    | BPartner_Value      |
      | iCP_1         | SHARED_CP_BP_VAL    |
    When the CampaignPriceImportProcess is invoked
    Then validate I_Campaign_Price:
      | Identifier  | C_BPartner_ID  |
      | iCP_1       | bp_cp_cust     |
