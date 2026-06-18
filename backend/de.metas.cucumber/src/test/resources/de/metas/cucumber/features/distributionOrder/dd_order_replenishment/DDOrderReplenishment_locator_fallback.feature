@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — fall back to the packing warehouse's default locator when the workplace has no pick-from locator
  As a warehouse operator running a packing workplace ("Packtisch") whose workplace has no explicit pick-from locator,
  I want the replenishment to still create the distribution order and deliver the goods to the packing warehouse's
  default locator, so that the picker always gets a DD_Order to work from even when the workplace's
  PickFrom_Locator_ID was never configured.

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
  Scenario: A workplace without a PickFrom_Locator_ID still creates a DD_Order, delivering to the packing warehouse's default locator
    # The stocking warehouse holds the goods; the packing warehouse is where the picker delivers them.
    Given metasfresh contains DD_NetworkDistribution
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
    # The packing warehouse's default locator is captured (via getOrCreateDefaultLocator) so it can be asserted as
    # the fallback target locator — the workplace itself has NO PickFrom_Locator_ID.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID          |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingDefaultLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    # The picker's workstation is on the packing warehouse but has NO PickFrom_Locator_ID — the replenishment must
    # fall back to the packing warehouse's default locator (packingDefaultLocator) as the delivery target.
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID |
      | workplace  | packingWH      |

    # On-hand stock in the source warehouse (single locator, qty 5).
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | stockLocator | 0       | 5        | PCE          |
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

    # Assigning the schedule line to the workstation (with QtyToPick=5) fires the M_Picking_Job_Schedule
    # interceptor, which reconciles. The workplace has no PickFrom_Locator_ID — the fix must still create the
    # DD_Order and deliver to the packing warehouse's default locator.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 5         |

    # Exactly one Completed DD_Order, qty 5, source stockWH, target locator = the packing warehouse's DEFAULT
    # locator (the fallback), with both DD_Order and DD_OrderLine referencing the assignment.
    Then after not more than 120s, the DD_Order linked to picking job schedule is found:
      | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID        | QtyEntered |
      | jobSchedule               | CO        | stockWH             | packingWH         | packingDefaultLocator | 5          |
    # The async reconcile event handler records a Done AD_EventLog_Entry on success.
    And after not more than 10s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError |
      | jobSchedule               | false   |
