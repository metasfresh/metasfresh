@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — update (in place) and reverse (void only)
  As a warehouse operator running a packing workplace ("Packtisch"),
  I want a changed assignment quantity to be reflected on the distribution order without churning it (the
  existing per-locator DD_Order line is updated in place while the same source locator keeps contributing),
  a quantity of zero to void the distribution order, and a cancelled / removed assignment to void the
  distribution order without recreating it, so the picker always works from up-to-date DD_Orders.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | pricingSystem   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | priceListVersion | priceList     |
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

    # On-hand stock in the source warehouse (single locator, qty 10 — enough to cover the qty-8 update below). The
    # stock-aware split pulls from the locators that hold the product; all stock on one source locator => one DD_Order.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockProductHU     |

    # One completed sales order on the packing warehouse + a workstation assignment is the starting state for every scenario.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 5         |
    And after not more than 120s, the DD_Order linked to picking job schedule is found:
      | Identifier | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | ddOrder_v1 | jobSchedule               | CO        | stockWH             | packingWH         | packingLocator | 5          |

  @from:cucumber
  Scenario: Changing the assignment quantity updates the existing DD_Order line in place (single contributing locator, picker not busy)
    # Raise the assignment's QtyToPick to 8; the M_Picking_Job_Schedule afterChange reconcile fires.
    # AC5: the contributing-locator set is unchanged (still the one source locator), so the reconcile UPDATES the
    # existing DD_Order line qty IN PLACE — it does NOT void+recreate. ddOrder_v1 stays the same live DD_Order, now
    # carrying qty 8 (its qty change is reflected without churning the document).
    When the picking job schedule quantity is changed:
      | M_Picking_Job_Schedule_ID | QtyToPick |
      | jobSchedule               | 8         |

    # The same DD_Order (ddOrder_v1) is still live and now carries qty 8 — updated in place, not voided/recreated.
    Then after not more than 120s, the DD_Order linked to picking job schedule is found:
      | Identifier | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | ddOrder_v1 | jobSchedule               | CO        | stockWH             | packingWH         | packingLocator | 8          |
    # And it is still Completed (NOT voided) — the in-place update did not void the original DD_Order.
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder_v1 | CO        |

  @from:cucumber
  Scenario: Setting the assignment quantity to zero voids the DD_Order and creates no replacement (picker not busy)
    # The operator decides not to pick this line after all and sets QtyToPick to 0.
    # The M_Picking_Job_Schedule afterChange reconcile fires: classifyAction sees an active assignment with a
    # live DD_Order (RECREATE), but the zero-qty soft no-op guard downgrades it to VOID — no demand to plan,
    # so the existing DD_Order is voided and NO replacement is created.
    When the picking job schedule quantity is changed:
      | M_Picking_Job_Schedule_ID | QtyToPick |
      | jobSchedule               | 0         |

    # The existing DD_Order is voided and NO new live DD_Order is created.
    # (the "is Voided" step already asserts a voided DD_Order exists AND no live one remains for the schedule)
    Then after not more than 120s, the DD_Order linked to M_ShipmentSchedule shipmentSchedule is Voided
    # The original DD_Order (captured in Background as ddOrder_v1) must now be Voided.
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder_v1 | VO        |
    # The async reconcile event handler records a Done AD_EventLog_Entry on success (no error).
    And after not more than 10s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError |
      | jobSchedule               | false   |

  @from:cucumber
  Scenario: Removing the assignment voids the DD_Order and creates no replacement (picker not busy)
    # Removing the workstation assignment (mirrors cancelling the pick at this workstation) re-fires the
    # reconcile flow via the M_Picking_Job_Schedule afterDelete interceptor.
    When delete picking job schedules
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    # The existing DD_Order is voided and NO new live DD_Order is created.
    Then after not more than 120s, the DD_Order linked to M_ShipmentSchedule shipmentSchedule is Voided
    And there is no live DD_Order for M_ShipmentSchedule shipmentSchedule
