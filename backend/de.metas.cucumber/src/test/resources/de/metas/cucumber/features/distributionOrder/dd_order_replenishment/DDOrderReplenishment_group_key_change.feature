@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — moving an assignment between product groups reconciles BOTH groups
  As a traffic manager moving one delivery to another packing workstation,
  I want the distribution order I moved it AWAY from to shrink to what is still assigned to it,
  so that the mover is not sent to fetch goods nobody at that workstation needs any more.

  Two customer deliveries share one product group at workstation A (demand 10 + 5 = 15); workstation B has its own pick-from locator, hence its own initially empty group.

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
    # IsGroundLocator=Y is required by the replenishment service's ground-filter when computing the allocation.
    And metasfresh contains M_Locator:
      | Identifier   | M_Warehouse_ID | Value    | IsGroundLocator | PriorityNo |
      | stockLocator | stockWH        | Standard | Y               | 10         |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customer      | customerLocation       | true        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID    |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocatorA |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    # A second pick-from locator in the packing warehouse: the target locator is what keys the product group,
    # so the two workstations are two different groups.
    And metasfresh contains M_Locator:
      | Identifier      | M_Warehouse_ID | Value  |
      | packingLocatorB | packingWH      | pack-B |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplaceA | packingWH      | packingLocatorA     |
      | workplaceB | packingWH      | packingLocatorB     |

    # 15 on-hand in the SOURCE warehouse exactly matches the total demand, so both groups source from ONE locator.
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

    # --- Two customer deliveries on the packing warehouse: 10 and 5 of the same product -----------------
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
    # QtyToDeliver > 0 is waited for because the packing warehouse is MRP_Exclude=Y, so only the async revalidation worker populates it.
    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID | QtyToDeliver |
      | shipmentScheduleA | orderLineA     | packingWH    | 10           |
      | shipmentScheduleB | orderLineB     | packingWH    | 5            |

    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplaceA     | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplaceA     | 5         |

  @from:cucumber
  Scenario: Re-assigning one delivery to another workstation shrinks the order it left to the remaining contributor
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID  | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocatorA | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    # No explicit reconcile step anywhere in this scenario: the re-assignment fires the real after-commit
    # events itself, and it must fire one for the group it LEFT as well as for the one it joined.
    When the picking job schedule workplace is changed:
      | M_Picking_Job_Schedule_ID | C_Workplace_ID |
      | jobScheduleA              | workplaceB     |

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID  | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocatorB | movedDDOrder | movedDDOrderLine | CO        | stockWH             | 10         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | movedDDOrderLine | jobScheduleA              | 10  |

    # The group jobScheduleA left is re-planned IN PLACE (hence it keeps the groupDDOrder identifier) down to
    # jobScheduleB's 5 — an order still sized 15 would send the mover for goods that moved to workstation B.
    And after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID  | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocatorA | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleA resolves to the DD_Order identified by movedDDOrder
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder
