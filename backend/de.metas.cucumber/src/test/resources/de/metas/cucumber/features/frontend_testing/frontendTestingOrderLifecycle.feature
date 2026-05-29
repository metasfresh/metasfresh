@from:cucumber
Feature: Frontend Testing API - Order Lifecycle
  Validates the business logic underlying the frontendTesting API:
  sales order completion, shipment generation from schedules, and
  purchase order completion. These are the same code paths used by
  PurchaseOrderCreateCommand, ShipmentCreateCommand, etc.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SYSTEM_INSERT_CHANGELOG

  @from:cucumber
  Scenario: Sales order completes and generates shipment schedule
    Given metasfresh contains C_BPartners:
      | Identifier | Name            | IsCustomer | IsVendor |
      | bp_so_1    | FT_SO_Partner   | Y          | N        |
    And metasfresh contains M_Products:
      | Identifier | Name         |
      | prod_so_1  | FT_SO_Prod   |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_so_1    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_1    | ps_so_1            | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so_1   | pl_so_1        |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so_1    | plv_so_1               | prod_so_1    | 25.00    | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID | IsShipTo | IsBillTo | GLN           |
      | bp_so_1_loc  | bp_so_1       | true     | true     | 7285656789011 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | M_PricingSystem_ID | DateOrdered |
      | order_so_1 | true    | bp_so_1       | bp_so_1_loc            | ps_so_1            | 2022-05-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_so_1    | order_so_1 | prod_so_1    | 10         |
    Then the order identified by order_so_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToClose |
      | ss_so_1    | ol_so_1        | N         |

  @from:cucumber
  Scenario: Purchase order completes successfully
    Given metasfresh contains C_BPartners:
      | Identifier | Name           | IsCustomer | IsVendor |
      | bp_po_1    | FT_PO_Partner  | N          | Y        |
    And metasfresh contains M_Products:
      | Identifier | Name        |
      | prod_po_1  | FT_PO_Prod  |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_po_1    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_po_1    | ps_po_1            | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_po_1   | pl_po_1        |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_po_1    | plv_po_1               | prod_po_1    | 15.00    | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | C_BPartner_ID | IsShipTo | IsBillTo | GLN           |
      | bp_po_1_loc | bp_po_1       | true     | true     | 7285656789033 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | M_PricingSystem_ID | DateOrdered |
      | order_po_1 | false   | bp_po_1       | bp_po_1_loc            | ps_po_1            | 2022-05-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_po_1    | order_po_1 | prod_po_1    | 8          |
    Then the order identified by order_po_1 is completed
