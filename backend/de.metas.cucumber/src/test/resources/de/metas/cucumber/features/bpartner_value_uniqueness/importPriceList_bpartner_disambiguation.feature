@from:cucumber
@ghActions:run_on_executor5
Feature: ImportPriceList BPartner Value disambiguation
  When two BPartners share the same Value (one customer, one vendor),
  the ImportPriceList process should pick the correct one based on IsSOPriceList.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And the C_BPartner Value unique constraint is relaxed

  @from:cucumber
  Scenario: Sales price list import picks customer when vendor has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                   | Value            | IsCustomer | IsVendor |
      | bp_cust_pl       | PriceList Shared Cust  | SHARED_PL_BP_VAL | Y          | N        |
      | bp_vendor_pl     | PriceList Shared Vendor| SHARED_PL_BP_VAL | N          | Y        |
    And metasfresh contains I_PriceList:
      | Identifier     | BPartner_Value   | IsSOPriceList |
      | iPL_sales_1    | SHARED_PL_BP_VAL | Y             |
    When the ImportPriceList process is invoked
    Then validate I_PriceList:
      | Identifier  | C_BPartner_ID |
      | iPL_sales_1 | bp_cust_pl    |

  @from:cucumber
  Scenario: Purchase price list import picks vendor when customer has same Value
    Given metasfresh contains C_BPartners with duplicate Values allowed:
      | Identifier       | Name                     | Value             | IsCustomer | IsVendor |
      | bp_cust_pl2      | PriceList Shared Cust 2  | SHARED_PL_BP_VAL2 | Y          | N        |
      | bp_vendor_pl2    | PriceList Shared Vendor 2| SHARED_PL_BP_VAL2 | N          | Y        |
    And metasfresh contains I_PriceList:
      | Identifier       | BPartner_Value    | IsSOPriceList |
      | iPL_purchase_1   | SHARED_PL_BP_VAL2 | N             |
    When the ImportPriceList process is invoked
    Then validate I_PriceList:
      | Identifier     | C_BPartner_ID |
      | iPL_purchase_1 | bp_vendor_pl2 |
