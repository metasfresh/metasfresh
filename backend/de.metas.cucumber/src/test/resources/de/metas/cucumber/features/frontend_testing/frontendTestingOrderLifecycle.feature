@from:cucumber
@ghActions:run_on_executor3
Feature: Frontend Testing API - Order Lifecycle

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SYSTEM_INSERT_CHANGELOG

  @from:cucumber
  Scenario: Create sales order via frontendTesting API and verify it exists
    Given metasfresh contains C_BPartners:
      | Identifier | Name                 | IsCustomer | IsVendor |
      | bp_ft_1    | FT_Lifecycle_Partner | Y          | Y        |
    And metasfresh contains M_Products:
      | Identifier | Name              |
      | prod_ft_1  | FT_Lifecycle_Prod |
    And metasfresh contains M_PriceLists:
      | Identifier | Name               | SOTrx | IsTaxIncluded | PricePrecision | C_Currency.ISO_Code |
      | pl_ft_1    | FT_Lifecycle_PL_SO | true  | false         | 2              | EUR                 |
    And metasfresh contains M_PriceList_Versions:
      | Identifier | M_PriceList_ID | Name               |
      | plv_ft_1   | pl_ft_1        | FT_Lifecycle_PLV_SO|
    And metasfresh contains M_ProductPrices:
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ft_1    | plv_ft_1               | prod_ft_1    | 25.00    | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID | IsShipTo | IsBillTo | GLN           |
      | bp_ft_1_loc  | bp_ft_1       | true     | true     | 0285656789099 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | M_PricingSystem_ID | DateOrdered |
      | order_ft_1 | true    | bp_ft_1       | bp_ft_1_loc            | pl_ft_1            | 2022-05-17  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_ft_1     | order_ft_1 | prod_ft_1    | 10         |
    And the order identified by order_ft_1 is completed
    Then the order identified by order_ft_1 has DocStatus 'CO'
