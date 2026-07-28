@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — a duplicate holding moved goods is disconnected, not voided
  As a warehouse operator, when two live distribution orders end up serving one product group and a worker has
  already begun moving the goods of the losing one, I want that order left standing and disconnected — not voided —
  so the worker finishes the move he has in his hands while the group re-plans around it.

  When the group is re-planned it keeps ONE order per source locator; a second live order on the same locator is a
  duplicate the reconcile disposes of. A duplicate that is empty is voided, but a duplicate already holding goods in
  transit or delivered is a move in progress: voiding it would strand that in-hand stock, so it is DISCONNECTED
  (IsPickingDisconnected=Y, still Completed) and its worker finishes it as a standalone replenishment.

  One delivery needs 10 PCE at the workstation pick-from locator; 30 PCE are on hand at one source locator.

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
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 10        |

  @from:cucumber
  @Id:S30919_10
  Scenario: A second live order holding moved goods is disconnected while the group re-plans on the first
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
      | M_Picking_Job_Schedule_ID | DD_Order_ID    | DocStatus | QtyEntered |
      | jobSchedule               | duplicateOrder | CO        | 10         |

    # A worker has already moved the whole 10 PCE of the duplicate into transit — the move that must not be stranded.
    And simulate goods in transit of 10 on DD_Order linked to picking job schedule jobSchedule

    # Restore the first order's association so BOTH orders are visible to the reconcile as one group; the older order
    # keeps the source locator, the younger duplicate is the one the reconcile must dispose of.
    And the rollout backfill re-creates the contributor associations:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID |
      | winnerLine      | jobSchedule               |

    When the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    # The duplicate held goods in transit, so it is DISCONNECTED (IsPickingDisconnected=Y, still Completed) — never
    # voided — and the worker finishes it. The first order keeps serving the group's demand, untouched.
    Then after not more than 10s, following DD_Orders are found
      | Identifier     | DocStatus | IsPickingDisconnected |
      | duplicateOrder | CO        | true                  |
      | winnerOrder    | CO        | false                 |
