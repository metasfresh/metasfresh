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
  Scenario: One drift-rebuild pass serves every contributor of a product group
    # The rebuild collapses a group's assignments into ONE reconcile request — the request identifies the product
    # group, not the assignment. That single reconcile must still serve EVERY contributor: a member dropped here
    # ends up with no distribution order, no exception and no log entry.
    #
    # Wait for both reconciles to have finished, so whatever they produced is committed before the drift below.
    Given after not more than 60s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError |
      | jobScheduleA              | false   |
      | jobScheduleB              | false   |

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
