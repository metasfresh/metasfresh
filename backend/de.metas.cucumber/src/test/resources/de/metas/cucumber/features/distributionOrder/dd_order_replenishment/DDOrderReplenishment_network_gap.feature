@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — network gap soft-fail and repost recovery
  As a warehouse operator, when the distribution network of a packing warehouse cannot resolve a source
  warehouse, I want the reconcile to fail softly — no distribution order is created, an AD_Issue is logged
  and the event ends in Error so I can fix the network and repost — rather than blocking the sales order.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | productQ   |
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
      | priceListVersion       | productQ     | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN          | C_BPartner_ID |
      | customerLocation | bPLocation_1 | customer      |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    # The network exists (so the packing-warehouse config is valid) but has NO distribution line
    # resolving a source warehouse for the packing warehouse — the source-warehouse resolution will fail.
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
    # The picker's workstation is on the packing warehouse; its pick-from locator is set so the reconcile gets
    # past the pick-from-locator check and reaches the source-warehouse resolution (the network gap under test).
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # On-hand stock in the source warehouse (single locator, qty 5) so that — once the network gap is fixed and a
    # source warehouse resolves — the stock-aware split has stock to pull and creates the DD_Order.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | productQ                | stockLocator | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockProductHU     |

  @from:cucumber
  Scenario: A missing source line makes the reconcile event fail softly, then succeeds after the network is fixed
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | productQ     | 5          |
    And the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |

    # Assigning the schedule line to the workstation triggers the reconcile via the M_Picking_Job_Schedule interceptor.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 5         |

    # No source warehouse can be resolved → no DD_Order is created.
    Then there is no live DD_Order for M_ShipmentSchedule shipmentSchedule
    # The reconcile event ends in Error with a readable message and an AD_Issue is logged.
    # MsgText matches the AD_Message resolved in the system base language (de_DE): "Kein Quelllager ...".
    And after not more than 120s, an AD_EventLog_Entry for the replenishment event handler is found:
      | M_Picking_Job_Schedule_ID | IsError | MsgText      |
      | jobSchedule               | true    | %Quelllager% |
    And after not more than 10s, an AD_Issue is logged for the replenishment network gap of M_Picking_Job_Schedule jobSchedule

    # Fix the network: add the missing source line resolving stockWH for the packing warehouse, then repost.
    When metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    # Now the DD_Order is created.
    Then after not more than 30s, the DD_Order linked to picking job schedule is found:
      | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | jobSchedule               | CO        | stockWH             | packingWH         | packingLocator | 5          |
