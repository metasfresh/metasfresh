@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5114_MobileUI_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — a product group never plans more stock than exists
  As a traffic manager planning a packing workplace,
  I want the demand of every delivery needing the same product netted against the on-hand stock ONCE,
  so that no source locator is planned twice over and the shortfall lands on a known delivery.

  On hand in the stocking warehouse: 8 on locatorA, 4 on locatorB — 12 in total.
  Each scenario's deliveries are separate customer orders picked at the same workstation.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN          | C_BPartner_ID |
      | customerLocation | bPLocation_1 | customer      |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    # locatorA.Value (10-A) sorts before locatorB.Value (20-B), so locatorA is consumed first.
    And metasfresh contains M_Locator:
      | Identifier | M_Warehouse_ID | Value | IsDefault | IsGroundLocator | PriorityNo |
      | locatorA   | stockWH        | 10-A  | Y         | Y               | 10         |
      | locatorB   | stockWH        | 20-B  | N         | Y               | 20         |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customer      | customerLocation       | true        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID   |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # 8 on locatorA + 4 on locatorB, each as one counted HU at its locator.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | invLineA                      | product                 | locatorA     | 0       | 8        | PCE          |
      | stockInventory            | invLineB                      | product                 | locatorB     | 0       | 4        | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineA                      | huA                |
      | invLineB                      | huB                |

  @from:cucumber
  Scenario: The group's summed demand is planned against the real stock per locator, and the later delivery is shorted
    # Demand 8 + 6 = 14 against 12 on hand. Planned once for the whole group: locatorA 8, locatorB 4.
    # Allocated per delivery instead, both would claim locatorA's 8 and 14 would be planned out of 12.
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderCovered | true    | customer      | 2022-05-17  | packingWH      |
      | orderShorted | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID   | M_Product_ID | QtyEntered |
      | orderLineCovered | orderCovered | product      | 8          |
      | orderLineShorted | orderShorted | product      | 6          |
    And the order identified by orderCovered is completed
    And the order identified by orderShorted is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID   | Warehouse_ID |
      | shipmentScheduleCovered | orderLineCovered | packingWH    |
      | shipmentScheduleShorted | orderLineShorted | packingWH    |

    # The covered delivery is prepared a day earlier, so it is served first and the other one bears the shortfall.
    And update shipment schedules
      | M_ShipmentSchedule_ID   | PreparationDate_Override |
      | shipmentScheduleCovered | 2022-05-18T08:00:00Z     |
      | shipmentScheduleShorted | 2022-05-19T08:00:00Z     |

    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID   | C_Workplace_ID | QtyToPick |
      | jobScheduleCovered        | shipmentScheduleCovered | workplace      | 8         |
      | jobScheduleShorted        | shipmentScheduleShorted | workplace      | 6         |

    Then after not more than 120s, the product group's completed DD_Orders source from:
      | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered | DD_OrderLine_ID |
      | product      | packingLocator | locatorA     | 8          | lineFromA       |
      | product      | packingLocator | locatorB     | 4          | lineFromB       |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | lineFromA       | jobScheduleCovered        | 8   |
      | lineFromB       | jobScheduleShorted        | 4   |
    # The 2 nobody can be served with is reported, not silently dropped.
    And after not more than 60s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError | MsgText                                |
      | jobScheduleShorted        | false   | uncovered remainder=2 left unfulfilled |

    # Re-planning unchanged demand must reproduce the same split on the same lines — the contributor rows are
    # asserted against the lines captured above, so a void-and-recreate would leave them without contributors.
    When the reconcile event for M_Picking_Job_Schedule jobScheduleShorted is processed
    Then after not more than 120s, the product group's completed DD_Orders source from:
      | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered |
      | product      | packingLocator | locatorA     | 8          |
      | product      | packingLocator | locatorB     | 4          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | lineFromA       | jobScheduleCovered        | 8   |
      | lineFromB       | jobScheduleShorted        | 4   |

  @from:cucumber
  Scenario: A delivery the stock cannot reach at all stays unserved for the watchdog
    # Demand 12 + 5 = 17 against 12 on hand: the first delivery consumes both locators, the second gets nothing.
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderServed   | true    | customer      | 2022-05-17  | packingWH      |
      | orderUnserved | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID    | M_Product_ID | QtyEntered |
      | orderLineServed   | orderServed   | product      | 12         |
      | orderLineUnserved | orderUnserved | product      | 5          |
    And the order identified by orderServed is completed
    And the order identified by orderUnserved is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID    | Warehouse_ID |
      | shipmentScheduleServed   | orderLineServed   | packingWH    |
      | shipmentScheduleUnserved | orderLineUnserved | packingWH    |
    And update shipment schedules
      | M_ShipmentSchedule_ID    | PreparationDate_Override |
      | shipmentScheduleServed   | 2022-05-18T08:00:00Z     |
      | shipmentScheduleUnserved | 2022-05-19T08:00:00Z     |

    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID    | C_Workplace_ID | QtyToPick |
      | jobScheduleServed         | shipmentScheduleServed   | workplace      | 12        |
      | jobScheduleUnserved       | shipmentScheduleUnserved | workplace      | 5         |

    # What IS planned covers the served delivery exactly; no third document is invented for the uncovered 5.
    Then after not more than 120s, the product group's completed DD_Orders source from:
      | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered | DD_OrderLine_ID |
      | product      | packingLocator | locatorA     | 8          | lineFromA       |
      | product      | packingLocator | locatorB     | 4          | lineFromB       |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | lineFromA       | jobScheduleServed         | 8   |
      | lineFromB       | jobScheduleServed         | 4   |
    And after not more than 60s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError | MsgText                                |
      | jobScheduleUnserved       | false   | uncovered remainder=5 left unfulfilled |

    # The unserved assignment is left open, so the drift watchdog keeps it on its list and retries it.
    And after not more than 10s, picking job schedules are found:
      | M_ShipmentSchedule_ID    | C_Workplace_ID | QtyToPick | Processed |
      | shipmentScheduleUnserved | workplace      | 5         | false     |
    And the drift rebuild considers jobScheduleServed already served
    And the drift rebuild considers jobScheduleUnserved to still need a DD_Order

  @from:cucumber
  Scenario: A short close re-issues the whole group's outstanding demand
    # Demand 5 + 3 = 8, covered by locatorA alone, so both deliveries share one distribution order.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderA     | true    | customer      | 2022-05-17  | packingWH      |
      | orderB     | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineA | orderA     | product      | 5          |
      | orderLineB | orderB     | product      | 3          |
    And the order identified by orderA is completed
    And the order identified by orderB is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID |
      | shipmentScheduleA | orderLineA     | packingWH    |
      | shipmentScheduleB | orderLineB     | packingWH    |
    And update shipment schedules
      | M_ShipmentSchedule_ID | PreparationDate_Override |
      | shipmentScheduleA     | 2022-05-18T08:00:00Z     |
      | shipmentScheduleB     | 2022-05-19T08:00:00Z     |
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 5         |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 3         |
    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID   | DD_OrderLine_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | closedDDOrder | closedLine      | CO        | stockWH             | 8          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | closedLine      | jobScheduleA              | 5   |
      | closedLine      | jobScheduleB              | 3   |

    When the mover gives up the remaining quantity and completes the distribution job of DD_Order closedDDOrder
    Then after not more than 10s, following DD_Orders are found
      | Identifier    | DocStatus |
      | closedDDOrder | CL        |
    # A closed order covers nobody, so BOTH deliveries are back on the watchdog's list — this re-arms the recovery.
    And the drift rebuild considers jobScheduleA, jobScheduleB to still need a DD_Order

    When the DD_Order_Picking_Rebuild process is run
    # One fresh order for the group's whole outstanding demand (8), not netted against the closed document.
    Then after not more than 120s, the product group's completed DD_Orders source from:
      | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered | DD_OrderLine_ID |
      | product      | packingLocator | locatorA     | 8          | reissuedLine    |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | reissuedLine    | jobScheduleA              | 5   |
      | reissuedLine    | jobScheduleB              | 3   |
    # The closed document stays closed — the outstanding demand comes back as a NEW order, not as a reopening.
    And after not more than 10s, following DD_Orders are found
      | Identifier    | DocStatus |
      | closedDDOrder | CL        |
