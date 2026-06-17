@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — only ground-floor locators are sourced
  As a warehouse operator running a packing workplace ("Packtisch"),
  I want the replenishment to pull stock only from locators flagged as ground-floor (IsGroundLocator=Y),
  consumed in ascending PriorityNo order, so that non-ground locators are never touched by the picker and
  the replenishment produces no DD_Order when no ground locator holds any stock.

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
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customer      | customerLocation       | true        |

  @from:cucumber
  Scenario: Only ground-floor locators are sourced, consumed by PriorityNo order; non-ground locator is never picked
    # Source warehouse with two ground locators (PriorityNo 10 and 20) and one non-ground locator, all stocked.
    # The replenishment must source only the ground locators in PriorityNo order (10 before 20);
    # the non-ground locator must never appear in any DD_Order line.
    Given metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    # Two ground-floor locators with distinct priorities; the lower PriorityNo (10) is consumed first.
    And metasfresh contains M_Locator:
      | Identifier         | M_Warehouse_ID | Value     | IsGroundLocator | PriorityNo | IsDefault |
      | groundLocator10    | stockWH        | ground-10 | Y               | 10         | Y         |
      | groundLocator20    | stockWH        | ground-20 | Y               | 20         | N         |
      | nonGroundLocator   | stockWH        | upper-30  | N               | 30         | N         |
    # The packing warehouse's default locator is captured so it can be used as the workstation's pick-from locator.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID   |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # Stock: 5 on each ground locator and 5 on the non-ground locator. Total demand = 8 so the greedy allocation
    # fills groundLocator10 (PriorityNo=10) fully (5), then takes 3 from groundLocator20 (PriorityNo=20).
    # The non-ground locator (PriorityNo=30) must never be sourced.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID     | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | invLine10                     | product                 | groundLocator10  | 0       | 5        | PCE          |
      | stockInventory            | invLine20                     | product                 | groundLocator20  | 0       | 5        | PCE          |
      | stockInventory            | invLineNG                     | product                 | nonGroundLocator | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine10                     | huGround10         |
      | invLine20                     | huGround20         |
      | invLineNG                     | huNonGround        |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 8          |
    And the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |

    # Trigger replenishment: assign the schedule to the workstation with the full demand qty.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 8         |

    # Exactly TWO Completed DD_Orders: groundLocator10 (PriorityNo=10) fully consumed (qty 5),
    # groundLocator20 (PriorityNo=20) covers the remainder (qty 3). The non-ground locator (nonGroundLocator)
    # must NOT appear — the per-locator step asserts the EXACT source-locator set.
    Then after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
      | M_Picking_Job_Schedule_ID | M_Locator_ID    | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | groundLocator10 | CO        | stockWH             | packingWH         | packingLocator | 5          |
      | jobSchedule               | groundLocator20 | CO        | stockWH             | packingWH         | packingLocator | 3          |
    And after not more than 10s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError |
      | jobSchedule               | false   |

  @from:cucumber
  Scenario: No DD_Order is created when the source warehouse has only non-ground locators
    # Source warehouse with only non-ground locators (IsGroundLocator=N), all stocked.
    # The replenishment must find no ground locators, produce no allocation, and complete without error.
    Given metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    # All locators are non-ground — the replenishment ground-filter returns an empty list.
    And metasfresh contains M_Locator:
      | Identifier       | M_Warehouse_ID | Value    | IsGroundLocator | PriorityNo | IsDefault |
      | nonGroundLocator | stockWH        | upper-10 | N               | 10         | Y         |
    # The packing warehouse's default locator is captured so it can be used as the workstation's pick-from locator.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID   |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # Stock exists on the non-ground locator — enough to cover the demand — but the ground-filter ignores it.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID     | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | nonGroundLocator | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockProductHU     |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |

    # Trigger replenishment: assign the schedule to the workstation.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 5         |

    # No ground locators found → no allocation → no DD_Order created, no error.
    # The reconcile completes cleanly (Done event log entry) with no DD_Order line created.
    And after not more than 10s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError |
      | jobSchedule               | false   |
    Then there is no live DD_Order for M_ShipmentSchedule shipmentSchedule
