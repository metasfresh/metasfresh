@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5114_MobileUI_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — one distribution order per product group
  As a mover replenishing a packing workplace,
  I want the demand that shares product, UOM and target locator to arrive as ONE distribution order
  carrying the summed quantity,
  so that I walk the route once instead of once per customer delivery.

  Two customer deliveries need the same product, in the same UOM, at the same workstation pick-from
  locator, replenished from the same source locator — one product group, demand 10 + 5 = 15.

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
    # The stocking warehouse's default locator is flagged as a ground-floor locator so the replenishment service
    # considers it when computing the required allocation (IsGroundLocator=Y is required by the ground-filter).
    And metasfresh contains M_Locator:
      | Identifier   | M_Warehouse_ID | Value    | IsGroundLocator | PriorityNo |
      | stockLocator | stockWH        | Standard | Y               | 10         |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customer      | customerLocation       | true        |
    # The packing warehouse's default locator is captured so it can be used as the workstation's pick-from locator.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID   |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    # The mover's workstation is on the packing warehouse; its pick-from locator is where the goods must land,
    # and it is what makes both deliveries below one product group.
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # On-hand stock in the source warehouse: 15 on a single locator — exactly the group's summed demand, so the
    # stock-aware split produces ONE source locator and therefore one line.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | stockLocator | 0       | 15       | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockProductHU     |

    # Two customer deliveries on the packing warehouse: 10 and 5 of the same product.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderA     | true    | customer      | 2022-05-17  | packingWH      |
      | orderB     | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineA | orderA     | product      | 10         |
      | orderLineB | orderB     | product      | 5          |
    And the order identified by orderA is completed
    And the order identified by orderB is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID |
      | shipmentScheduleA | orderLineA     | packingWH    |
      | shipmentScheduleB | orderLineB     | packingWH    |

    # Both deliveries are assigned to the same workstation — the two contributors of the product group.
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 5         |

  @from:cucumber
  Scenario: Two same-group assignments share one Completed DD_Order with the summed quantity
    # One order of 15, not one order per assignment.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    # The COMPLETE contributor set is persisted, each with its own quantity — the shared order has no single owner.
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    # Both deliveries settle against that one order: neither is left dangling nor served twice.
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: The mover walks the route once, and the single move settles both deliveries
    # The pay-off of consolidating, and the half of it that the generation-side scenarios cannot show: ONE physical
    # trip must settle EVERY delivery behind the shared order. The mover opens the consolidated replenishment on the
    # mobile app, scans the source HU and drops it at the workstation — after which the goods are at the shared
    # pick-from locator and the record of who gets how much of them is still complete and exact.
    #
    # A contributor lost anywhere along that path is silent and expensive: its demand has been served physically
    # while the system no longer knows it, so the replenishment keeps considering it unserved and sends the mover
    # back for goods that are already standing at the station.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # The mover's real path: start the job on the shared order, scan the source HU, drop it at the workstation. The
    # whole consolidated quantity moves in one go — the source locator holds exactly the group's 15 PCE on one HU.
    When Start distribution job for dd_order identified by groupDDOrder
      | DD_Order_ID  |
      | groupDDOrder |
    And Pick HU for distribution job line
      | M_HU_ID        |
      | stockProductHU |
    And Drop HU for distribution job line

    # The goods have arrived where both deliveries will be picked from — the workstation's pick-from locator.
    Then M_HU are validated:
      | M_HU_ID        | HUStatus | IsActive | M_Locator_ID   |
      | stockProductHU | A        | true     | packingLocator |

    # Still ONE order of 15 for the group: the move did not cause a second replenishment to be planned for either
    # delivery, and it did not shrink the one that served them.
    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # The COMPLETE contributor set survived the move, each delivery still carrying its own share of what was moved:
    # 10 for the first, 5 for the second. The assertion is exact-set, so a contributor dropped by the move fails it
    # just as loudly as one invented for a delivery that contributed nothing.
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    # And neither delivery is left dangling (no order at all) nor served twice (a second order).
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: One drift-rebuild pass serves every contributor of a product group
    # The rebuild collapses a group's assignments into ONE reconcile request — the request identifies the product
    # group, not the assignment. That single reconcile must still serve EVERY contributor: a member dropped here
    # ends up with no distribution order, no exception and no log entry.
    #
    # Wait for the group to be planned, so whatever the reconcile produced is committed before the drift below. Gated
    # on the observable outcome, NOT on one event-log entry per assignment: the whole point of the group key is that N
    # same-group requests dedup to ONE event, so a per-assignment gate would break the moment two creations land in one
    # transaction — and it would time out with a message about the event log rather than about the missing order.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # Simulate the drift the watchdog exists for (event lost, node restarted): the group's orders are gone, so BOTH
    # assignments are unserved and one rebuild pass has to plan the whole group.
    When every live DD_Order for the product group is voided directly:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
    And the DD_Order_Picking_Rebuild process is run

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: A contributor joining grows the consolidated quantity
    # A third customer delivery of the same product is assigned to the same workstation. The mover's route must not
    # gain a second document: the joining contributor grows the SHARED order's quantity instead.
    #
    # Wait for the two-contributor order first — the group is planned as soon as the Background's assignments commit,
    # and asserting the grown state without pinning the starting state would pass on a run where nothing grew.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # Book in the 5 extra PCE the third delivery needs, on the SAME source locator (a second source locator would be
    # planned as its own DD_Order). This has to happen BEFORE the third assignment exists: the split is stock-aware,
    # so a demand of 20 against 15 on-hand would be covered only partially and the joining contributor would get
    # no share at all — which is a shortfall scenario, not the growth this asserts.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | topUpInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | topUpInventory            | topUpInventoryLine            | product                 | stockLocator | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'topUpInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | topUpInventoryLine            | topUpProductHU     |

    # The third customer delivery, 5 of the same product on the same packing warehouse.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderC     | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineC | orderC     | product      | 5          |
    And the order identified by orderC is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID |
      | shipmentScheduleC | orderLineC     | packingWH    |

    # Assigning it to the same workstation makes it the third contributor of the same product group.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleC              | shipmentScheduleC     | workplace      | 5         |

    # Still ONE order, now carrying 10 + 5 + 5.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 20         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
      | groupDDOrderLine | jobScheduleC              | 5   |
    And each of jobScheduleA, jobScheduleB, jobScheduleC resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: A contributor leaving shrinks the consolidated quantity to the remaining contributors' sum
    # Two departure routes, both leaving the shared order alive because other deliveries still depend on it:
    #   1. the assignment is deactivated;
    #   2. its QtyToPick is reduced to zero — a departure like any other, so its allocation row is DELETED rather
    #      than kept at Qty=0 (a zero row would still answer "this delivery is served by that order").
    # The group starts with three contributors so that each departure still leaves more than one behind.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | topUpInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | topUpInventory            | topUpInventoryLine            | product                 | stockLocator | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'topUpInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | topUpInventoryLine            | topUpProductHU     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderC     | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineC | orderC     | product      | 5          |
    And the order identified by orderC is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID |
      | shipmentScheduleC | orderLineC     | packingWH    |
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleC              | shipmentScheduleC     | workplace      | 5         |
    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 20         |

    # Route 1 — the traffic manager deactivates the biggest delivery's assignment. The reconcile is driven from a
    # SURVIVING contributor to make the point that the group, not the changed record, is the unit of work.
    When the picking job schedule is deactivated:
      | M_Picking_Job_Schedule_ID |
      | jobScheduleA              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 10         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
      | groupDDOrderLine | jobScheduleC              | 5   |
    And each of jobScheduleB, jobScheduleC resolves to the DD_Order identified by groupDDOrder

    # Route 2 — the second delivery no longer needs replenishing from stock, so its quantity is set to zero.
    When the picking job schedule quantity is changed:
      | M_Picking_Job_Schedule_ID | QtyToPick |
      | jobScheduleB              | 0         |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleC is processed

    # The contributor assertion is exact-set, so jobScheduleB's absence IS the assertion that its allocation row was
    # deleted — a row kept at Qty=0 would show up here and fail.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleC              | 5   |
    And each of jobScheduleC resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: One contributor's close-out does not close the shared order, and the last one's does
    # Packing is GOD: a shipment close-out (M_Picking_Job_Schedule.Processed=Y) always succeeds. What must NOT happen
    # is one delivery's close-out taking the shared replenishment away from the deliveries still waiting for it.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # The first delivery ships (real shipment generation closes its workstation assignment out) ...
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentScheduleA     | jobScheduleA              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    # ... and the order stays Completed, shrunk to what the remaining delivery still needs.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder

    # Only when the LAST contributor is closed out is the replenishment obsolete — and then it is CLOSED, not voided:
    # whatever was already moved stays moved.
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentScheduleB     | jobScheduleB              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    Then after not more than 10s, following DD_Orders are found
      | Identifier   | DocStatus | IsPickingDisconnected |
      | groupDDOrder | CL        | false                 |

  @from:cucumber
  Scenario: Deactivating the LAST contributor voids the shared order
    # The sibling of the close-out above: nothing was fulfilled, the demand was simply un-assigned, so the obsolete
    # replenishment is VOIDED rather than closed. And, as above, only the departure of the LAST contributor disposes
    # of it — the first departure only shrinks it.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    When the picking job schedule is deactivated:
      | M_Picking_Job_Schedule_ID |
      | jobScheduleA              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |

    When the picking job schedule is deactivated:
      | M_Picking_Job_Schedule_ID |
      | jobScheduleB              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    Then after not more than 10s, following DD_Orders are found
      | Identifier   | DocStatus |
      | groupDDOrder | VO        |

  @from:cucumber
  Scenario: A close-out is accepted while the shared order's goods are in transit, and the order survives it
    # The shipment close-out is exempt from the change guard that otherwise refuses any re-plan once the replenishment
    # is on the move (QtyInTransit > 0): it is a fulfilment event, not a user re-plan, and packing must never be
    # blocked. The exemption is deliberate — this scenario pins it, together with the fact that the departing
    # contributor's close-out does not dispose of an order the other contributor still depends on.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # The mover has dispatched the consolidated replenishment: its goods are between the source and the workstation.
    And simulate goods in transit on DD_Order linked to picking job schedule jobScheduleA

    # The first delivery ships anyway — the close-out is NOT refused, even though the guard would refuse a re-plan now.
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentScheduleA     | jobScheduleA              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    # The second delivery is still waiting for its goods, so the order lives on and serves it alone.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: Deleting the last contributor disposes of the shared order it no longer owns the back-reference of
    # A consolidated order carries the back-reference of ONE of its contributors. Once that one has left, the order can
    # only be reached through its contributor association — and the un-assignment that removes the last association row
    # is the very transaction that would make it unreachable. If the order is not disposed of there and then, it stays
    # Completed for a demand nobody has, invisible to every group-keyed lookup and to the drift watchdog alike, and the
    # mover keeps being sent to fetch it.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # The first delivery ships, so it leaves the group and the order is rewritten to serve only the second one — while
    # still carrying the departed first delivery's assignment as its back-reference.
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentScheduleA     | jobScheduleA              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |

    # The traffic manager un-assigns the last remaining delivery by deleting its workstation assignment.
    When delete picking job schedules
      | M_ShipmentSchedule_ID |
      | shipmentScheduleB     |

    # Nothing is left to replenish, so nothing may be left planned.
    Then after not more than 60s, no live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
