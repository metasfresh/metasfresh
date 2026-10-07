@from:cucumber
@ghActions:run_on_executor5
Feature: ImportOrder BPartner Value disambiguation
  When two BPartners share the same Value (one customer, one vendor),
  the ImportOrder process should pick the correct one based on IsSOTrx.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: Sales order import picks customer when vendor has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                | Value         | IsCustomer | IsVendor |
      | bp_cust_shared   | Shared Value Cust   | SHARED_BP_VAL | Y          | N        |
      | bp_vendor_shared | Shared Value Vendor | SHARED_BP_VAL | N          | Y        |
    And metasfresh contains I_Order:
      | Identifier     | BPartnerValue | IsSOTrx |
      | iOrder_sales_1 | SHARED_BP_VAL | Y       |
    When the ImportOrder process is invoked
    Then validate I_Order:
      | Identifier     | C_BPartner_ID  |
      | iOrder_sales_1 | bp_cust_shared |

  @from:cucumber
  Scenario: Purchase order import picks vendor when customer has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier        | Name                  | Value          | IsCustomer | IsVendor |
      | bp_cust_shared2   | Shared Value Cust 2   | SHARED_BP_VAL2 | Y          | N        |
      | bp_vendor_shared2 | Shared Value Vendor 2 | SHARED_BP_VAL2 | N          | Y        |
    And metasfresh contains I_Order:
      | Identifier        | BPartnerValue  | IsSOTrx |
      | iOrder_purchase_1 | SHARED_BP_VAL2 | N       |
    When the ImportOrder process is invoked
    Then validate I_Order:
      | Identifier        | C_BPartner_ID     |
      | iOrder_purchase_1 | bp_vendor_shared2 |

  @from:cucumber
  Scenario: Unique BPartner Value resolves without ambiguity
    Given metasfresh contains C_BPartners:
      | Identifier     | Name             | Value         | IsCustomer | IsVendor |
      | bp_unique_cust | Unique Cust Only | UNIQUE_BP_VAL | Y          | N        |
    And metasfresh contains I_Order:
      | Identifier      | BPartnerValue | IsSOTrx |
      | iOrder_unique_1 | UNIQUE_BP_VAL | Y       |
    When the ImportOrder process is invoked
    Then validate I_Order:
      | Identifier      | C_BPartner_ID  |
      | iOrder_unique_1 | bp_unique_cust |
