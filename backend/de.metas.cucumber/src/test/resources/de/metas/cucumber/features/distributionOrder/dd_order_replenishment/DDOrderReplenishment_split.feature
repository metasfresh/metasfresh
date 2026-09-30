@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — stock-aware multi-locator split and per-locator diff
  As a warehouse operator running a packing workplace ("Packtisch"),
  I want the replenishment to look at the actual on-hand stock per source locator and create one
  Completed distribution order per contributing locator (greedily allocating the demanded quantity by
  locator Value), so that the picker is sent to the locators that really hold the goods — and when the
  assignment quantity or the stock distribution changes, only the affected per-locator DD_Orders are
  touched (update-in-place when a locator still contributes, void when it drops out, create when a new
  locator joins).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

    # --- Pricing ---------------------------------------------------------------------------------------
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

    # --- Customer --------------------------------------------------------------------------------------
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN          | C_BPartner_ID |
      | customerLocation | bPLocation_1 | customer      |
    And contains M_Shippers
      | Identifier |
      | shipper    |

    # --- Auto-distribution network: stockWH (source) -> packingWH (target) -----------------------------
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    # Two named source locators in the stocking warehouse. Their Value drives the greedy allocation order:
    # locatorA.Value (10-A) sorts before locatorB.Value (20-B), so locatorA is consumed first.
    And metasfresh contains M_Locator:
      | Identifier | M_Warehouse_ID | Value | IsDefault | IsGroundLocator | PriorityNo |
      | locatorA   | stockWH        | 10-A  | Y         | Y               | 10         |
      | locatorB   | stockWH        | 20-B  | N         | Y               | 20         |
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
    # The picker's workstation is on the packing warehouse; its pick-from locator is where the goods must land.
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # --- On-hand stock in the source warehouse: 10 on locatorA, 7 on locatorB (17 total) ---------------
    # Stock is created via a counted inventory per locator -> one HU per locator at that locator.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | invLineA                      | product                 | locatorA     | 0       | 10       | PCE          |
      | stockInventory            | invLineB                      | product                 | locatorB     | 0       | 7        | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineA                      | huA                |
      | invLineB                      | huB                |

  @from:cucumber
  Scenario: A demand spanning two stocked locators is split greedily by locator Value into one DD_Order per locator
    # Demand 15: locatorA (lower Value) is consumed fully (10), then locatorB covers the remainder (5).
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 15         |
    And the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 15        |

    # Exactly TWO Completed DD_Orders, one line each, matched by source locator: locatorA=10, locatorB=5.
    # Both target the workstation's pick-from locator and reference the assignment.
    Then after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | locatorA     | CO        | stockWH             | packingWH         | packingLocator | 10         |
      | jobSchedule               | locatorB     | CO        | stockWH             | packingWH         | packingLocator | 5          |

  @from:cucumber
  Scenario: When on-hand stock cannot cover the full demand, DD_Orders are created for the available portion only and the remainder is left unfulfilled
    # Demand 20 but only 17 on hand (10 + 7). The two locators are fully consumed; the remaining 3 is logged
    # and NOT turned into a third / fallback-default line.
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 20         |
    And the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 20        |

    # Exactly TWO DD_Orders covering the available 17 (10 + 7); no third locator/line for the uncovered 3.
    Then after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | locatorA     | CO        | stockWH             | packingWH         | packingLocator | 10         |
      | jobSchedule               | locatorB     | CO        | stockWH             | packingWH         | packingLocator | 7          |

  @from:cucumber
  Scenario: Lowering the demand while the same locators still contribute updates the existing DD_Order lines in place
    # Start from the 15-demand split (locatorA=10, locatorB=5), capturing each DD_Order's identity.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 15         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 15        |
    And after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID | Identifier  | DocStatus | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | locatorA     | ddOrderA_v1 | CO        | packingLocator | 10         |
      | jobSchedule               | locatorB     | ddOrderB_v1 | CO        | packingLocator | 5          |

    # Lower the demand to 12 with the SAME contributing locators: greedy => locatorA=10 (unchanged),
    # locatorB=2 (was 5). Both existing DD_Orders are UPDATED IN PLACE — no DD_Order is voided and no new
    # one is created (the captured ddOrderA_v1 / ddOrderB_v1 are still the live ones, now with the new qty).
    When the picking job schedule quantity is changed:
      | M_Picking_Job_Schedule_ID | QtyToPick |
      | jobSchedule               | 12        |

    Then after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID | Identifier  | DocStatus | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | locatorA     | ddOrderA_v1 | CO        | packingLocator | 10         |
      | jobSchedule               | locatorB     | ddOrderB_v1 | CO        | packingLocator | 2          |

  @from:cucumber
  Scenario: When a locator stops contributing its DD_Order is voided while the unchanged locator's DD_Order is left untouched
    # Start from the 15-demand split (locatorA=10, locatorB=5), capturing each DD_Order's identity.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 15         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 15        |
    And after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID | Identifier  | DocStatus | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | locatorA     | ddOrderA_v1 | CO        | packingLocator | 10         |
      | jobSchedule               | locatorB     | ddOrderB_v1 | CO        | packingLocator | 5          |

    # Empty locatorB by destroying its HU: the source locator set becomes {locatorA} only (10 on hand). Re-trigger
    # the reconcile so the per-locator diff runs against the new stock picture. NOTE: the demand (QtyToPick) is
    # unchanged at 15 — what changed is the on-hand stock, not the assignment — so we drive the reconcile directly
    # (as the after-commit event / watchdog rebuild would in production) rather than re-saving the assignment: a
    # no-op assignment save (same QtyToPick, same workplace) does not fire an afterChange and would not reconcile.
    When the HU identified by huB is destroyed
    And the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    # locatorB no longer contributes -> its DD_Order (ddOrderB_v1) is VOIDED. locatorA still contributes its full
    # 10 on hand -> its DD_Order (ddOrderA_v1) is left UNTOUCHED (still CO, qty 10). The uncovered remainder (5,
    # since locatorA only holds 10 of the demanded 15) is logged — no fallback line.
    Then after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID | Identifier  | DocStatus | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | locatorA     | ddOrderA_v1 | CO        | packingLocator | 10         |
    And after not more than 5s, following DD_Orders are found
      | Identifier  | DocStatus |
      | ddOrderB_v1 | VO        |
