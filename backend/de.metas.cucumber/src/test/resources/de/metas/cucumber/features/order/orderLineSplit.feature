@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19012_Material_Cockpit_v2_for_Reservation_in_Sales_Order
Feature: Split sales order line after partial delivery

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-05-26T08:00:00+02:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | ps_split     |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl_split     | ps_split           | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_split    | pl_split       |
    And metasfresh contains M_Products:
      | Identifier   | Value      | Name       |
      | product_P1   | P1_split   | P1_split   |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_split     | plv_split              | product_P1   | 10.00    | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | Value      | Name       | IsCustomer | M_PricingSystem_ID |
      | bpartner_C1  | C1_split   | C1_split   | Y          | ps_split           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | C_BPartner_ID |
      | bploc_C1           | bpartner_C1   |

  @from:cucumber
  @Id:S_OLSplit_10
  Scenario: S_OLSplit_10 Happy path - split partially-shipped line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_O1   | true    | bpartner_C1   | 2026-05-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line_OL1   | order_O1   | product_P1   | 10         |
    And the order identified by order_O1 is completed
    When the C_OrderLine_SplitQty process is run on "line_OL1" with QtyToSplitOff = 2

  @from:cucumber
  @Id:S_OLSplit_20
  Scenario: S_OLSplit_20 Validation - cannot split below already-delivered qty
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_O2   | true    | bpartner_C1   | 2026-05-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line_OL2   | order_O2   | product_P1   | 10         |
    And the order identified by order_O2 is completed
    When the C_OrderLine_SplitQty process is run on "line_OL2" with QtyToSplitOff = 5 expecting validation failure
    Then the validation error message includes "below already-delivered"

  @from:cucumber
  @Id:S_OLSplit_30
  Scenario: S_OLSplit_30 Shipment schedule and invoice candidate created for new line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_O3   | true    | bpartner_C1   | 2026-05-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line_OL3   | order_O3   | product_P1   | 10         |
    And the order identified by order_O3 is completed
    When the C_OrderLine_SplitQty process is run on "line_OL3" with QtyToSplitOff = 2

  @from:cucumber
  @Id:S_OLSplit_40
  Scenario: S_OLSplit_40 Reservation on original line is shrunk to fit
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_O4   | true    | bpartner_C1   | 2026-05-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line_OL4   | order_O4   | product_P1   | 10         |
    And the order identified by order_O4 is completed
    When the C_OrderLine_SplitQty process is run on "line_OL4" with QtyToSplitOff = 6
