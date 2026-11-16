@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — a duplicate holding moved goods is disconnected, not voided
  As a warehouse operator, when two live distribution orders end up serving one product group and a worker has
  already begun moving the goods of the losing one, I want that order left standing and disconnected — not voided —
  so the worker finishes the move he has in his hands while the group re-plans around it.

  One delivery needs 10 PCE at the workstation pick-from locator; 30 PCE are on hand at one source locator, as a
  10-PCE HU (the one the mover takes) plus a 20-PCE one.

  "A worker has begun the move" is the real mover state: an IN_PROGRESS DD_Order_MoveSchedule, reached by picking the
  source HU of the DD_Order-backed mobile DistributionJob and not yet dropping it at the workstation.

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

    # All 30 on one source locator, so both orders of the group source from the SAME locator — the collision the
    # reconcile resolves by keeping the older order and disposing of the younger duplicate.
    #
    # Split over two HUs because a DD_Order move always takes the WHOLE source HU: the mover picks the 10-PCE one
    # (exactly the group's demand of 10), and the remaining 20 keep the locator stocked so the re-plan still has a
    # source to allocate from.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | moveStockInventory        | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | moveStockInventory        | moveStockInventoryLine        | product                 | stockLocator | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'moveStockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | moveStockInventoryLine        | moveSourceHU       |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | stockLocator | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockProductHU     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 10        |

  @from:cucumber
  @Id:S30919_10
  Scenario: A disconnected duplicate keeps its contributor association so the delivery still navigates to it
    # The order the assignment triggered on the packing warehouse: the eventual WINNER of the locator collision.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID | DD_OrderLine_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | winnerOrder | winnerLine      | CO        | stockWH             | 10         |

    # Reach a genuine two-live-orders-in-one-group state: drop the first order's association so the watchdog cannot
    # see it, then let a rebuild pass issue a SECOND live order for the same demand from the same source locator.
    When the contributor associations of the pre-rollout DD_OrderLines are dropped:
      | DD_OrderLine_ID |
      | winnerLine      |
    And the DD_Order_Picking_Rebuild process is run
    And after not more than 120s, exactly 2 live DD_Orders exist for the product group:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |

    # The duplicate is the one the rebuild associated with the assignment (the first order is still orphaned here).
    And after not more than 120s, the DD_Order linked to picking job schedule is found:
      | M_Picking_Job_Schedule_ID | DD_Order_ID    | DD_OrderLine_ID | DocStatus | QtyEntered |
      | jobSchedule               | duplicateOrder | duplicateLine   | CO        | 10         |

    # A worker has already begun the duplicate's move: he picked its 10-PCE source HU off the stock locator, so the
    # goods are in transit and not yet dropped at the workstation — the move that must not be stranded.
    And pick from the DD_Order linked to picking job schedule:
      | M_Picking_Job_Schedule_ID | PickFrom_HU_ID |
      | jobSchedule               | moveSourceHU   |

    # Restore the first order's association so BOTH orders are visible to the reconcile as one group; the older order
    # keeps the source locator, the younger duplicate is the one the reconcile must dispose of.
    And the rollout backfill re-creates the contributor associations:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID |
      | winnerLine      | jobSchedule               |

    When the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    # The disconnect must RETAIN the duplicate's alloc row — the trailing cleanup re-derives obsolete lines from the
    # contributors' schedules and must exclude the disconnected line, or the delivery can no longer navigate to it.
    Then the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | duplicateLine   | jobSchedule               | 10  |

    # The duplicate held goods in transit, so it is DISCONNECTED (IsPickingDisconnected=Y, still Completed) — never
    # voided — and its worker finishes it.
    And after not more than 10s, following DD_Orders are found
      | Identifier     | DocStatus | IsPickingDisconnected |
      | duplicateOrder | CO        | true                  |

    # AC4 navigation survives: with its association retained, the delivery still reaches the disconnected order that
    # is replenishing it as a Related Document.
    And each of shipmentSchedule reaches the DD_Order identified by duplicateOrder as related document

  @from:cucumber
  @Id:S30919_20
  Scenario: The group nets the disconnected duplicate's in-transit qty off, so the winner is not double-planned
    # The order the assignment triggered on the packing warehouse: the eventual WINNER of the locator collision.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID | DD_OrderLine_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | winnerOrder | winnerLine      | CO        | stockWH             | 10         |

    # Reach a genuine two-live-orders-in-one-group state, as above.
    When the contributor associations of the pre-rollout DD_OrderLines are dropped:
      | DD_OrderLine_ID |
      | winnerLine      |
    And the DD_Order_Picking_Rebuild process is run
    And after not more than 120s, exactly 2 live DD_Orders exist for the product group:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |

    And after not more than 120s, the DD_Order linked to picking job schedule is found:
      | M_Picking_Job_Schedule_ID | DD_Order_ID    | DocStatus | QtyEntered |
      | jobSchedule               | duplicateOrder | CO        | 10         |

    # The worker picked the duplicate's 10-PCE source HU: this move alone covers the group's entire demand of 10.
    And pick from the DD_Order linked to picking job schedule:
      | M_Picking_Job_Schedule_ID | PickFrom_HU_ID |
      | jobSchedule               | moveSourceHU   |

    And the rollout backfill re-creates the contributor associations:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID |
      | winnerLine      | jobSchedule               |

    When the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    # The disconnected duplicate's 10 in transit already cover the demand of 10, so the winner has nothing left to
    # plan — it is VOIDED, not left re-planning the full 10 on top of the move (which would move 20 for a demand of 10).
    Then after not more than 10s, following DD_Orders are found
      | Identifier     | DocStatus | IsPickingDisconnected |
      | winnerOrder    | VO        | false                 |
      | duplicateOrder | CO        | true                  |

    # Exactly the disconnected duplicate remains live, still carrying its 10 — total committed equals the demand of 10.
    And after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | QtyEntered |
      | product      | packingLocator | CO        | 10         |
