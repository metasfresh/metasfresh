@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5114_MobileUI_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — a contributor straddling two source locators
  As a mover replenishing a packing workplace,
  I want a delivery leaving the product group to shrink EVERY distribution order it contributed to,
  so that no order keeps sending me for a quantity nobody demands any more.

  Three customer deliveries share one product group (10 + 10 + 10 = 30) sourced from locatorA (15) and locatorB (15), so the middle delivery straddles the locator boundary with 5 on each.

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
    # locatorA.Value (10-A) sorts before locatorB.Value (20-B), so locatorA is allocated first.
    And metasfresh contains M_Locator:
      | Identifier | M_Warehouse_ID | Value | IsDefault | IsGroundLocator | PriorityNo |
      | locatorA   | stockWH        | 10-A  | Y         | Y               | 10         |
      | locatorB   | stockWH        | 20-B  | N         | Y               | 20         |
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

    # --- On-hand stock: 15 on locatorA, 15 on locatorB — the group's demand of 30 needs both ------------
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | invLineA                      | product                 | locatorA     | 0       | 15       | PCE          |
      | stockInventory            | invLineB                      | product                 | locatorB     | 0       | 15       | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineA                      | huA                |
      | invLineB                      | huB                |

    # --- Three deliveries of the same product, one per customer order ----------------------------------
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderA     | true    | customer      | 2022-05-17  | packingWH      |
      | orderB     | true    | customer      | 2022-05-17  | packingWH      |
      | orderC     | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineA | orderA     | product      | 10         |
      | orderLineB | orderB     | product      | 10         |
      | orderLineC | orderC     | product      | 10         |
    And the order identified by orderA is completed
    And the order identified by orderB is completed
    And the order identified by orderC is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID |
      | shipmentScheduleA | orderLineA     | packingWH    |
      | shipmentScheduleB | orderLineB     | packingWH    |
      | shipmentScheduleC | orderLineC     | packingWH    |

    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 10        |
      | jobScheduleC              | shipmentScheduleC     | workplace      | 10        |

  @from:cucumber
  Scenario: Deleting the contributor that straddles both source locators shrinks the order it is not the last contributor of
    Given after not more than 120s, the product group's completed DD_Orders source from:
      | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered | DD_OrderLine_ID |
      | product      | packingLocator | locatorA     | 15         | lineFromA       |
      | product      | packingLocator | locatorB     | 15         | lineFromB       |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | lineFromA       | jobScheduleA              | 10  |
      | lineFromA       | jobScheduleB              | 5   |
      | lineFromB       | jobScheduleB              | 5   |
      | lineFromB       | jobScheduleC              | 10  |

    # No reconcile is driven by hand here: the departure itself must revisit BOTH orders the leaving
    # delivery contributed to, and it is the last contributor of neither.
    When delete picking job schedules
      | M_ShipmentSchedule_ID |
      | shipmentScheduleB     |

    # The remaining demand is 20: locatorA still carries its full 15, locatorB shrinks from 15 to 5.
    Then after not more than 120s, the product group's completed DD_Orders source from:
      | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered | DD_OrderLine_ID |
      | product      | packingLocator | locatorA     | 15         | lineFromA       |
      | product      | packingLocator | locatorB     | 5          | lineFromB       |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID | M_Picking_Job_Schedule_ID | Qty |
      | lineFromA       | jobScheduleA              | 10  |
      | lineFromA       | jobScheduleC              | 5   |
      | lineFromB       | jobScheduleC              | 5   |
