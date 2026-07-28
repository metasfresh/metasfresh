@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5114_MobileUI_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — one distribution order per product group
  As a mover replenishing a packing workplace,
  I want the demand that shares product, UOM and target locator to arrive as ONE distribution order carrying the summed quantity,
  so that I walk the route once instead of once per customer delivery.

  Two customer deliveries share one product group — same product, UOM, workstation pick-from locator and source locator, demand 10 + 5 = 15.

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
    # IsGroundLocator=Y is required by the replenishment service's ground-filter when computing the allocation.
    And metasfresh contains M_Locator:
      | Identifier   | M_Warehouse_ID | Value    | IsGroundLocator | PriorityNo |
      | stockLocator | stockWH        | Standard | Y               | 10         |
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

    # 15 on-hand exactly matches the group's summed demand, so the stock-aware split yields ONE source locator.
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

    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 5         |

  @from:cucumber
  Scenario: Two same-group assignments share one Completed DD_Order with the summed quantity
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: Both deliveries sharing the consolidated order stay reachable from it, and it from each of them
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    Then each of shipmentScheduleA, shipmentScheduleB reaches the DD_Order identified by groupDDOrder as related document
    And the DD_Order identified by groupDDOrder reaches shipmentScheduleA, shipmentScheduleB as related documents

  @from:cucumber
  Scenario: The mover walks the route once, and the single move settles both deliveries
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    When Start distribution job for dd_order identified by groupDDOrder
      | DD_Order_ID  |
      | groupDDOrder |
    And Pick HU for distribution job line
      | M_HU_ID        |
      | stockProductHU |
    And Drop HU for distribution job line

    Then M_HU are validated:
      | M_HU_ID        | HUStatus | IsActive | M_Locator_ID   |
      | stockProductHU | A        | true     | packingLocator |

    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: The single arrival settles each delivery with its own share, not with the consolidated quantity
    # The workstation holds no stock of its own, so the 15 the mover brings is the only stock either delivery can be
    # settled from. It must settle 10 into one delivery and 5 into the other — never the consolidated 15 into either.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    When Start distribution job for dd_order identified by groupDDOrder
      | DD_Order_ID  |
      | groupDDOrder |
    And Pick HU for distribution job line
      | M_HU_ID        |
      | stockProductHU |
    And Drop HU for distribution job line

    Then M_HU are validated:
      | M_HU_ID        | HUStatus | IsActive | M_Locator_ID   |
      | stockProductHU | A        | true     | packingLocator |

    # Each delivery is now shipped on its own, out of that one arrival: the workstation holds no other stock, so
    # every PCE either shipment takes is a PCE the mover brought.
    #
    # Deliberately NOT scoped to the workstation assignment: with an M_Picking_Job_Schedule supplied, shipment
    # generation ships ONLY what that assignment already has picked
    # (ShipmentScheduleWithHUService#prepareShipmentSchedulesWithHUForQtyToDeliver refuses the QtyToDeliver path for a
    # job-schedule-scoped request), and no picker has picked here — the mover's drop is an arrival, not a pick. Shipping
    # per delivery is what settles the arrival, and that is what this scenario is about.
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentScheduleA     | shipmentA  |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentScheduleB     | shipmentB  |

    # Proportional and complete, per delivery: each is settled in full for its own 10 and 5, leaving nothing to deliver.
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToDeliver |
      | shipmentScheduleA     | 10             | 10               | 0                |
      | shipmentScheduleB     | 5              | 5                | 0                |

    # Not double-counted: each share landed on its OWN customer order line, and the two together are exactly the 15 moved.
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | C_OrderLine_ID | movementqty |
      | shipmentA  | product      | orderLineA     | 10          |
      | shipmentB  | product      | orderLineB     | 5           |

  @from:cucumber
  Scenario: One drift-rebuild pass serves every contributor of a product group
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    When every live DD_Order for the product group is voided directly:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
    Then the drift rebuild considers jobScheduleA, jobScheduleB to still need a DD_Order
    When the DD_Order_Picking_Rebuild process is run

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: One rebuild pass costs one reconcile per group needing work, not one per contributor
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # Both contributors are served, so the group needs no work and the pass costs nothing.
    Then one DD_Order_Picking_Rebuild pass publishes exactly 0 reconcile requests for M_Picking_Job_Schedules jobScheduleA, jobScheduleB

    When every live DD_Order for the product group is voided directly:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
    Then the drift rebuild considers jobScheduleA, jobScheduleB to still need a DD_Order

    # Two contributors now need work; they share one group key, so the pass publishes ONE request, not two.
    Then one DD_Order_Picking_Rebuild pass publishes exactly 1 reconcile request for M_Picking_Job_Schedules jobScheduleA, jobScheduleB

    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

  @from:cucumber
  Scenario: Re-running the rebuild against an unchanged group changes nothing
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    Then the drift rebuild considers jobScheduleA, jobScheduleB already served

    When the DD_Order_Picking_Rebuild process is run
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

    When the DD_Order_Picking_Rebuild process is run
    Then the drift rebuild considers jobScheduleA, jobScheduleB already served
    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA, jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: A contributor joining grows the consolidated quantity
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # The top-up must land BEFORE the third assignment exists — 20 against 15 on-hand would be a shortfall, not growth.
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

    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleC              | shipmentScheduleC     | workplace      | 5         |

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
    # A departure DELETES the contributor's allocation row rather than keeping it at Qty=0 (which would still read as served).
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

    When the picking job schedule quantity is changed:
      | M_Picking_Job_Schedule_ID | QtyToPick |
      | jobScheduleB              | 0         |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleC is processed

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleC              | 5   |
    And each of jobScheduleC resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: One contributor's close-out does not close the shared order, and the last one's does
    # A shipment close-out always succeeds — it must not take the shared order away from deliveries still waiting on it.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

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
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder

    # Closing out the LAST contributor CLOSES the order (not voids it): whatever was already moved stays moved.
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentScheduleB     | jobScheduleB              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed

    Then after not more than 10s, following DD_Orders are found
      | Identifier   | DocStatus | IsPickingDisconnected |
      | groupDDOrder | CL        | false                 |

  @from:cucumber
  Scenario: Deactivating the LAST contributor voids the shared order
    # Un-assigning (vs. shipping) the LAST contributor VOIDS the order instead of closing it.
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
    # Shipment close-out is exempt from the in-transit re-plan guard — it's a fulfilment event, and packing must never be blocked.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

    # Seeding the legacy column is the only way to arm that guard at all — no production flow writes QtyInTransit —
    # so what this scenario proves is the EXEMPTION, which is live behaviour, not the seeded state itself.
    And seed the legacy QtyInTransit column on DD_Order linked to picking job schedule jobScheduleA

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
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: Deleting the last contributor disposes of the shared order it no longer owns the back-reference of
    # Once the back-referenced contributor has left, the order is reachable only via the contributor association — must be disposed here or it stays invisible to the drift watchdog.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |

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

    When delete picking job schedules
      | M_ShipmentSchedule_ID |
      | shipmentScheduleB     |

    Then after not more than 60s, no live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
