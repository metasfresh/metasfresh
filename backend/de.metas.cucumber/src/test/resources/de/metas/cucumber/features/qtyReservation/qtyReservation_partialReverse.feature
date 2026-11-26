@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F_QtyReservation
@ghActions:run_on_executor5
Feature: Qty Reservation partial reverse
## Verifies QtyReservationService.reduceReservation: reversing only part of a reservation bucket keeps the rest reserved.
## Drives the M_QtyReservation domain directly (the cockpit V2 view is not available in the base cucumber schema).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-03-01T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_Warehouse:
      | Identifier  |
      | warehouse_1 |
    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product_1  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_TU    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_TU           | huPI_TU    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_TU     | huPIV_TU           | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_TU_10PCE          | huPIItem_TU     | product_1    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_1    | 10.00    | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | bp_1       | true       | ps_1               | A            |

  @from:cucumber
  @Id:S_QtyResPartialReverse_10
  Scenario: Reference case — reserve 5 TU, reverse 2 TU, 3 TU stay reserved and a new 2 TU reservation row can be added again
  ## _Given a sales order line for 50 PCE with 5 TU (50 PCE) reserved
  ## _When 2 TU of the reservation are reversed
  ## _Then the reservation holds 3 TU (30 PCE) and 3 TU are still reversible
  ## _And a new reservation row for 2 TU can be created again (service-level makeReservation, which has no availability check), bringing the order line back to 5 TU reserved

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_10   | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_10 | order_10   | product_1    | 50         |
    And the order identified by order_10 is completed
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | qtyReservation_10 | orderLine_10   | product_1    | warehouse_1    | 50 PCE | 5     | OH         |
    Then validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU | ReservedQtyTU |
      | orderLine_10   | OH         | 5               | 5             |

    When M_QtyReservation bucket is reduced:
      | C_OrderLine_ID | SupplyType | QtyTU |
      | orderLine_10   | OH         | 2     |

    Then validate M_QtyReservations:
      | Identifier        | Qty    | QtyTU | Processed |
      | qtyReservation_10 | 30 PCE | 3     | false     |
    And validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU | ReservedQtyTU |
      | orderLine_10   | OH         | 3               | 3             |

    ## a new 2 TU reservation row can be added again
    When metasfresh contains M_QtyReservations:
      | Identifier         | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | qtyReservation_10b | orderLine_10   | product_1    | warehouse_1    | 20 PCE | 2     | OH         |
    Then validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU | ReservedQtyTU |
      | orderLine_10   | OH         | 5               | 5             |

    ## more than the reversible amount is rejected and changes nothing
    When M_QtyReservation bucket reduction is rejected:
      | C_OrderLine_ID | SupplyType | QtyTU |
      | orderLine_10   | OH         | 6     |
    Then validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU | ReservedQtyTU |
      | orderLine_10   | OH         | 5               | 5             |


  @from:cucumber
  @Id:S_QtyResPartialReverse_20
  Scenario: Reversing the full reserved qty deletes the bucket
  ## _Given a sales order line with two OH reservation rows (2 TU + 1 TU)
  ## _When all 3 TU are reversed
  ## _Then both reservation rows are gone

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_20   | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_20 | order_20   | product_1    | 30         |
    And the order identified by order_20 is completed
    And metasfresh contains M_QtyReservations:
      | Identifier         | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | qtyReservation_20a | orderLine_20   | product_1    | warehouse_1    | 20 PCE | 2     | OH         |
      | qtyReservation_20b | orderLine_20   | product_1    | warehouse_1    | 10 PCE | 1     | OH         |

    When M_QtyReservation bucket is reduced:
      | C_OrderLine_ID | SupplyType | QtyTU |
      | orderLine_20   | OH         | 3     |

    Then M_QtyReservations are deleted:
      | Identifier         |
      | qtyReservation_20a |
      | qtyReservation_20b |
    And validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU | ReservedQtyTU |
      | orderLine_20   | OH         | 0               | 0             |


  @from:cucumber
  @Id:S_QtyResPartialReverse_30
  Scenario: Partially delivered reservation — only the undelivered TUs are reversible, the delivered part stays
  ## _Given an order line for 10 PCE and a reservation of 10 PCE (2 TU), of which 5 PCE (1 TU) get delivered by a partial shipment
  ## _Then only 1 TU is reversible, and reversing 2 TU is rejected
  ## _When 1 TU is reversed
  ## _Then the reservation keeps the delivered 5 PCE (1 TU)

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_30   | true    | bp_1          | 2026-03-01  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_30 | order_30   | product_1    | 10         |
    And the order identified by order_30 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_30 | orderLine_30   | N             |
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | qtyReservation_30 | orderLine_30   | product_1    | warehouse_1    | 10 PCE | 2     | OH         |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | shipmentSchedule_30   | D            | true                | false       | 5                                               |
    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | qtyReservation_30 | 10 PCE | 5 PCE        | false     |
    And validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU |
      | orderLine_30   | OH         | 1               |

    When M_QtyReservation bucket reduction is rejected:
      | C_OrderLine_ID | SupplyType | QtyTU |
      | orderLine_30   | OH         | 2     |
    And M_QtyReservation bucket is reduced:
      | C_OrderLine_ID | SupplyType | QtyTU |
      | orderLine_30   | OH         | 1     |

    Then validate M_QtyReservations:
      | Identifier        | Qty   | QtyTU | QtyDelivered |
      | qtyReservation_30 | 5 PCE | 1     | 5 PCE        |
    And validate M_QtyReservation bucket:
      | C_OrderLine_ID | SupplyType | ReversibleQtyTU |
      | orderLine_30   | OH         | 0               |
