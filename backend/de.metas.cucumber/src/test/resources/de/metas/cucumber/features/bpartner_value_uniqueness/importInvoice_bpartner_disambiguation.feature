@from:cucumber
@ghActions:run_on_executor5
Feature: ImportInvoice BPartner Value disambiguation
  When two BPartners share the same Value (one customer, one vendor),
  the ImportInvoice process should pick the correct one based on IsSOTrx.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: AR invoice import picks customer when vendor has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                     | Value             | IsCustomer | IsVendor |
      | bp_cust_inv      | Invoice Shared Cust      | SHARED_INV_BP_VAL | Y          | N        |
      | bp_vendor_inv    | Invoice Shared Vendor    | SHARED_INV_BP_VAL | N          | Y        |
    And metasfresh contains I_Invoice:
      | Identifier     | BPartnerValue     | IsSOTrx |
      | iInv_sales_1   | SHARED_INV_BP_VAL | Y       |
    When the ImportInvoice process is invoked
    Then validate I_Invoice:
      | Identifier   | C_BPartner_ID |
      | iInv_sales_1 | bp_cust_inv   |

  @from:cucumber
  Scenario: AP invoice import picks vendor when customer has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                      | Value              | IsCustomer | IsVendor |
      | bp_cust_inv2     | Invoice Shared Cust 2     | SHARED_INV_BP_VAL2 | Y          | N        |
      | bp_vendor_inv2   | Invoice Shared Vendor 2   | SHARED_INV_BP_VAL2 | N          | Y        |
    And metasfresh contains I_Invoice:
      | Identifier       | BPartnerValue      | IsSOTrx |
      | iInv_purchase_1  | SHARED_INV_BP_VAL2 | N       |
    When the ImportInvoice process is invoked
    Then validate I_Invoice:
      | Identifier      | C_BPartner_ID  |
      | iInv_purchase_1 | bp_vendor_inv2 |
