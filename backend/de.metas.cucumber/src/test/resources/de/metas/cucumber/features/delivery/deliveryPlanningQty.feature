@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29050_Delivery_Planning
@ghActions:run_on_executor5
Feature: Delivery planning quantities

  Splitting a delivery planning divides its planned figures across the resulting plannings; each
  share sums back to the figure the split started from.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    Given metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_SO | pricingSystem      | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID |
      | priceListVersion_SO | priceList_SO   |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO    | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | 1234568110599 | customer                 | true                | true                |
    And contains M_Shippers
      | Identifier  | OPT.IsCreateDeliveryPlanning |
      | shipper_DHL | true                         |

  Scenario: Splitting an unallocated delivery planning divides both the loaded and discharge planned quantities

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQty   | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQty | orderQty               | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQty is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQty_1  | orderLineQty   |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningQty_1  | 10         | 10           | Outgoing            | 10                    | 0                        |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | PlannedDischargeQuantity |
      | deliveryPlanningQty_1  | 10                       |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningQty_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                      | C_OrderLine_ID |
      | deliveryPlanningQty_1,deliveryPlanningQty_2 | orderLineQty   |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningQty_1  | 10         | 10           | Outgoing            | 5                     | 5                        |
      | deliveryPlanningQty_2  | 10         | 10           | Outgoing            | 5                     | 5                        |
