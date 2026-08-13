@from:cucumber
@allure.label.epic:E0380_Masterdata_Products
@allure.label.feature:F6000_Maintain_Product_Data
@ghActions:run_on_executor4
Feature: product life-cycle status enforcement on order lines
## F6000: Maintain Product Data
# A product's life-cycle status (BBS-Status) gates whether it may be bought/sold:
# - "G" (Gesperrt) blocks buying and selling
# - "O" (OK) is unrestricted
# Enforcement is on the order line, independent of the M_Product_EnforcePurchaseSalesFlags SysConfig.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                |
      | ps_1       | pricing_system_name | pricing_system_value |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1                          | DE                        | EUR                 | plc-PriceList | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name    | ValidFrom  |
      | plv_1      | pl_1                      | plc-PLV | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier    | Name        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer | N            | Y              | ps_1                          |

  @Id:S31039
  Scenario: the life-cycle status gates whether a product can be sold on an order
    # two products: one blocked (Gesperrt), one OK
    And metasfresh contains M_Products:
      | Identifier | ProductLifeCycleStatus |
      | blocked    | G                      |
      | ok         | O                      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_blocked | plv_1                             | blocked                 | 10.0     | PCE               | Normal                        |
      | pp_ok      | plv_1                             | ok                      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | so_1       | true    | endcustomer_1            | 2021-04-17  |
    # the OK product is accepted on the sales order
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ok      | so_1                  | ok                      | 10         |
    # the Gesperrt product is rejected at order-line creation
    And metasfresh contains C_OrderLine expecting error:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | ErrorCode                         |
      | ol_blocked | so_1                  | blocked                 | 10         | M_Product_BBSStatus_ActionBlocked |
    # the order (carrying only the OK line) completes and produces a shipment schedule
    When the order identified by so_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_ok    | ol_ok                     | N             |
