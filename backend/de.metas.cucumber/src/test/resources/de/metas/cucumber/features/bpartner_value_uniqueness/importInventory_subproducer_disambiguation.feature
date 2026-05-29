@from:cucumber
@ghActions:run_on_executor5
Feature: I_Inventory SubProducer BPartner disambiguation
  When two BPartners share the same Value, the MInventoryImportTableSqlUpdater
  should only pick a vendor (IsVendor='Y') as SubProducer.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: Inventory import SubProducer picks vendor, ignores customer
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier         | Name                     | Value               | IsCustomer | IsVendor |
      | bp_inv_cust        | Inventory BP Cust        | SHARED_INV_SUB_VAL  | Y          | N        |
      | bp_inv_vendor      | Inventory BP Vendor      | SHARED_INV_SUB_VAL  | N          | Y        |
    And metasfresh contains I_Inventory:
      | Identifier     | SubProducerBPartner_Value  |
      | iInv_1         | SHARED_INV_SUB_VAL         |
    When the ImportInventory process is invoked
    Then validate I_Inventory:
      | Identifier  | SubProducer_BPartner_ID  |
      | iInv_1      | bp_inv_vendor            |

  @from:cucumber
  Scenario: Inventory import SubProducer unresolved when only customer exists
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier          | Name                       | Value                | IsCustomer | IsVendor |
      | bp_inv_cust_only    | Inventory Cust Only        | CUST_ONLY_INV_VAL    | Y          | N        |
    And metasfresh contains I_Inventory:
      | Identifier      | SubProducerBPartner_Value  |
      | iInv_cust_only  | CUST_ONLY_INV_VAL          |
    When the ImportInventory process is invoked
    Then validate I_Inventory:
      | Identifier      | IsUnresolved |
      | iInv_cust_only  | true         |
