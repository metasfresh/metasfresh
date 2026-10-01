@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — an order that already exists at rollout is backfilled, never re-planned
  As a warehouse operator whose instance already holds open distribution orders on the day this change is rolled out,
  I want each of those orders to keep serving its delivery exactly as before,
  so that the drift watchdog does not send the mover to fetch the same goods a second time.

  One delivery needs 10 PCE at the workstation pick-from locator; 30 PCE are on hand at one source locator,
  so a second delivery arriving after rollout can join the same group.

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

    # All 30 on one source locator, so the stock-aware split yields a single line and the assertions stay about
    # the association rather than about the split.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | stockLocator | 0       | 30       | PCE          |
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

    # A single-delivery order — the shape EVERY distribution order has before this rollout, since the pre-rollout
    # generation path produced one order per assignment.
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 10        |

  @from:cucumber
  Scenario: A pre-existing order whose association the rollout backfilled reads as served, and the watchdog leaves it alone
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID       | DD_OrderLine_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | preRolloutDDOrder | preRolloutLine  | CO        | stockWH             | 10         |

    # Reduce the order to its un-backfilled pre-rollout shape: the FK column it used to carry is gone (5816420),
    # so all that is left of a pre-existing order is a Completed document with no association at all.
    When the contributor associations of the pre-rollout DD_OrderLines are dropped:
      | DD_OrderLine_ID |
      | preRolloutLine  |
    # ... then let the rollout backfill (5816390) do its one job: Qty and UOM taken from the line, nothing re-planned.
    And the rollout backfill re-creates the contributor associations:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID |
      | preRolloutLine  | jobSchedule               |

    # The backfill wrote the line's own quantity and UOM — not a share, not a re-computed demand.
    Then the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | preRolloutLine  | jobSchedule               | 10  |

    # THE POINT OF THE ROLLOUT BACKFILL: the backfilled association is what makes the watchdog's
    # "is this contributor already served?" answer YES for an order it did not itself create.
    And the drift rebuild considers jobSchedule already served
    And one DD_Order_Picking_Rebuild pass publishes exactly 0 reconcile requests for M_Picking_Job_Schedules jobSchedule

    When the DD_Order_Picking_Rebuild process is run

    # Unaffected and behaving exactly as before: still one order, still Completed, still 10 — no duplicate, no re-plan.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 10         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | preRolloutLine  | jobSchedule               | 10  |
    # Identity, not just count: it is the SAME document, not a fresh one that happens to look alike.
    And each of jobSchedule resolves to the DD_Order identified by preRolloutDDOrder
    # And its shipment-schedule navigation survived the FK drop, because it now resolves through the association.
    And each of shipmentSchedule reaches the DD_Order identified by preRolloutDDOrder as related document

  @from:cucumber
  Scenario: Without the rollout backfill the same order is invisible to the watchdog, which issues a duplicate
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID       | DD_OrderLine_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | preRolloutDDOrder | preRolloutLine  | CO        | stockWH             | 10         |

    # Exactly the state migration 5816390 exists to repair — and exactly what running 5816420 without it would leave
    # behind: the order is Completed and open, but nothing associates it with the delivery it serves.
    When the contributor associations of the pre-rollout DD_OrderLines are dropped:
      | DD_OrderLine_ID |
      | preRolloutLine  |

    # Served-ness reads only the association, so the answer flips to NO for a delivery that IS in fact covered.
    Then the drift rebuild considers jobSchedule to still need a DD_Order

    When the DD_Order_Picking_Rebuild process is run

    # The damage an un-backfilled order causes: a SECOND live Completed order for demand the first already covers — 20 PCE planned
    # against 10 PCE of demand, i.e. the mover fetches the goods twice.
    Then after not more than 120s, exactly 2 live DD_Orders exist for the product group:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
    # The pre-existing order is not even touched — the group lookup cannot see it either, so it is neither
    # re-used nor voided; it just keeps standing, orphaned, with its 10 still to be moved.
    And after not more than 10s, following DD_Orders are found
      | Identifier        | DocStatus |
      | preRolloutDDOrder | CO        |

  @from:cucumber
  Scenario: A backfilled pre-existing order is grown into a group by later demand for the same product
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID       | DD_OrderLine_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | preRolloutDDOrder | preRolloutLine  | CO        | stockWH             | 10         |

    # Rollout day: the FK column the order used to carry is gone (5816420) and the backfill (5816390) restores its
    # association. This is the state EVERY order that already existed at rollout is in from here on.
    When the contributor associations of the pre-rollout DD_OrderLines are dropped:
      | DD_OrderLine_ID |
      | preRolloutLine  |
    And the rollout backfill re-creates the contributor associations:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID |
      | preRolloutLine  | jobSchedule               |

    # The day after: a second delivery needs the same product at the same workstation — same group key.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | laterOrder | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | laterOrderLine | laterOrder | product      | 10         |
    And the order identified by laterOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID | Warehouse_ID |
      | laterShipmentSchedule | laterOrderLine | packingWH    |

    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | laterJobSchedule          | laterShipmentSchedule | workplace      | 10        |

    # Grown, not duplicated: still ONE live order for the group, now planning both deliveries' 10 + 10.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 20         |
    # The SAME line the backfill wrote to — the legacy order was re-used, not replaced by a look-alike.
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | preRolloutLine  | jobSchedule               | 10  |
      | preRolloutLine  | laterJobSchedule          | 10  |
    And each of jobSchedule, laterJobSchedule resolves to the DD_Order identified by preRolloutDDOrder
    # Not voided either: the legacy document itself is what now serves both deliveries.
    And after not more than 10s, following DD_Orders are found
      | Identifier        | DocStatus |
      | preRolloutDDOrder | CO        |

    # The performance claim, measured rather than asserted in prose: the pre-rollout behaviour planned one order per
    # assignment, so this demand set would have cost 2. It costs 1 — never more, and strictly fewer once a group has
    # two contributors, which is exactly the mover's saved walk.
    And the live DD_Orders of the product group are no more than one per contributing assignment jobSchedule, laterJobSchedule:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
