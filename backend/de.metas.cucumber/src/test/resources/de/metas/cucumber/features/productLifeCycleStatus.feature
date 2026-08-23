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

  @Id:S31659_10
  Scenario: a sales order can no longer be completed once its product is blocked
    # The line is created while the product is still OK, so the line-creation guard passes; only the
    # re-check at completion can catch the status flip that happens in between.
    Given metasfresh contains M_Products:
      | Identifier    | ProductLifeCycleStatus |
      | flippedToG    | O                      |
      | staysOk       | O                      |
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | flippedToGPrice    | plv_1                             | flippedToG              | 10.0     | PCE               | Normal                        |
      | staysOkPrice       | plv_1                             | staysOk                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | blockedOrder  | true    | endcustomer_1            | 2021-04-17  |
      | controlOrder  | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | blockedOrderLine  | blockedOrder          | flippedToG              | 10         |
      | controlOrderLine  | controlOrder          | staysOk                 | 10         |
    # only now does the product become Gesperrt
    When update M_Product:
      | M_Product_ID.Identifier | ProductLifeCycleStatus |
      | flippedToG              | G                      |
    Then the order identified by blockedOrder cannot be completed because of error code M_Product_BBSStatus_ActionBlocked
    # the control order, whose product stayed OK, is unaffected
    And the order identified by controlOrder is completed

  @Id:S31659_20
  Scenario: a purchase order can no longer be completed once its product goes to Auslauf
    # "A" (Auslauf) blocks PURCHASE: no new stock may be bought, so the open purchase order must not complete.
    Given metasfresh contains M_PricingSystems
      | Identifier            | Name                        | Value                        |
      | purchasePricingSystem | plc_purchase_pricing_system | plc_purchase_pricing_value   |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                   | SOTrx | IsTaxIncluded | PricePrecision |
      | purchasePriceList | purchasePricingSystem         | DE                        | EUR                 | plc-purchase-PriceList | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | purchasePlv      | purchasePriceList         | plc-purchase-PLV | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | vendor     | plc-Vendor | Y            | N              | purchasePricingSystem         |
    And metasfresh contains M_Products:
      | Identifier       | ProductLifeCycleStatus |
      | purchasedProduct | O                      |
    And metasfresh contains M_ProductPrices
      | Identifier            | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | purchasedProductPrice | purchasePlv                       | purchasedProduct        | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | purchaseOrder | false   | vendor                   | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | purchaseOrderLine | purchaseOrder         | purchasedProduct        | 10         |
    When update M_Product:
      | M_Product_ID.Identifier | ProductLifeCycleStatus |
      | purchasedProduct        | A                      |
    Then the order identified by purchaseOrder cannot be completed because of error code M_Product_BBSStatus_ActionBlocked

  @Id:S31659_30
  Scenario: Auslauf blocks starting a manufacturing order and completing an already-started one
    Given load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standardCategory                 | Standard | Standard |
    And metasfresh contains M_Products:
      | Identifier       | ProductLifeCycleStatus | OPT.M_Product_Category_ID.Identifier |
      | finishedProduct  | O                      | standardCategory                     |
      | componentProduct | O                      | standardCategory                     |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | finishedProductPrice | plv_1                             | finishedProduct         | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID    | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom        | finishedProduct | 2021-01-01 | bomVersions               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID     | ValidFrom  | QtyBatch |
      | bomLine    | bom               | componentProduct | 2021-01-01 | 3        |
    And the PP_Product_BOM identified by bom is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID    | PP_Product_BOMVersions_ID | IsCreatePlan |
      | productPlanning | finishedProduct | bomVersions               | false        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |
    # the manufacturing order is started while the product is still OK
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | startedOrder           | MOP         | finishedProduct         | 10         | plant                    | 2021-04-16T07:00:00.00Z | 2021-04-18T07:00:00.00Z | 2021-04-16T07:00:00.00Z | N                |
    When update M_Product:
      | M_Product_ID.Identifier | ProductLifeCycleStatus |
      | finishedProduct         | A                      |
    # no NEW manufacturing order may be started for an Auslauf product ...
    Then create PP_Order expecting error code M_Product_BBSStatus_ActionBlocked:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | refusedOrder           | MOP         | finishedProduct         | 5          | plant                    | 2021-04-16T07:00:00.00Z | 2021-04-18T07:00:00.00Z | 2021-04-16T07:00:00.00Z | N                |
    # ... and the already-started one may no longer be completed
    And the manufacturing order identified by startedOrder cannot be completed because of error code M_Product_BBSStatus_ActionBlocked
