@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — create a distribution order when a picker is assigned to a workstation
  As a warehouse operator running a packing workplace ("Packtisch"),
  I want a Completed distribution order to be created when a shipment-schedule line is assigned to a
  workstation for picking, moving the goods from the stocking warehouse to that workstation's pick-from
  locator, so that the picker always has a DD_Order to work from from the moment the job is assigned.

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
  Scenario: Assigning a shipment-schedule line to a workstation creates one Completed DD_Order; deleting the assignment voids it
    # The stocking warehouse holds the goods; the packing warehouse is where the picker delivers them.
    # The packing warehouse is flagged IsAutoDistributionOrder=Y and excluded from material-dispo (MRP_Exclude=Y),
    # so the dedicated reconcile flow (not material-dispo) drives DD_Order creation.
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
    # The packing warehouse's default locator is captured so it can be used as the workstation's pick-from locator.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID    |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocator  |
    # The network resolves source = stockWH for target = packingWH (per warehouse-pair, product-agnostic).
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    # The picker's workstation is on the packing warehouse; its pick-from locator is where the goods must land.
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # On-hand stock in the source warehouse (single locator, qty 5). The stock-aware split pulls from the locators
    # that actually hold the product; with all stock on one source locator the split produces exactly one DD_Order.
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

    # The shipment schedule is generated on order completion. No DD_Order yet — the trigger is now the workstation
    # assignment, not the schedule itself.
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |

    # Assigning the schedule line to the workstation (with QtyToPick=5) fires the M_Picking_Job_Schedule interceptor,
    # which reconciles and creates exactly one Completed DD_Order.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 5         |

    # Exactly one Completed DD_Order, qty 5, source stockWH, target locator = the workstation's pick-from locator,
    # with the line's contributor set being exactly that assignment.
    Then after not more than 120s, the DD_Order linked to picking job schedule is found:
      | Identifier | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | ddOrder    | jobSchedule               | CO        | stockWH             | packingWH         | packingLocator | 5          |
    # The async reconcile event handler records a Done AD_EventLog_Entry on success.
    And after not more than 10s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError |
      | jobSchedule               | false   |

    # Removing the workstation assignment voids the DD_Order — there is nothing left to pick at this workstation.
    # The delete→void runs synchronously in the delete transaction and drops the assignment's contributor rows, so the
    # deferrable FK on DD_OrderLine_PickingJobSchedule passes at commit. The departed assignment therefore no longer
    # resolves the order, so the void is asserted on the captured ddOrder and the emptiness on the product group.
    When delete picking job schedules
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |
    Then after not more than 120s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder    | VO        |
    And after not more than 30s, no live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID |
      | product      | packingLocator |
