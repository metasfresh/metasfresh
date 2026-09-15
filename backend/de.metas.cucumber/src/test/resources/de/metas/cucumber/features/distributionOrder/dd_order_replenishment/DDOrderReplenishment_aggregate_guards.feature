@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — the change guards cover every contributor of a shared order
  As a traffic manager editing a workstation assignment,
  I want the refusal that protects a replenishment already in progress to hold for EVERY delivery behind that
  replenishment, and to tell me whose work is blocking me, so that I can go and resolve it.

  Two customer deliveries share one product group — same product, UOM, workstation pick-from locator and source locator, demand 10 + 5 = 15.

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
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID   |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   | packingLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # 15 on-hand in the SOURCE warehouse exactly matches the group's summed demand, so the stock-aware split yields ONE source locator.
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

    # --- Picking prerequisites on the PACKING warehouse -------------------------------------------------
    # IsAllowPickingAnyHU=Y lets the picking job start without pre-allocated HUs — the schedule is still awaiting replenishment.
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | packingInventory          | 2021-10-12   | packingWH      |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | packingInventory          | packingInventoryLine          | product                 | 0       | 5        | PCE          |
    And complete inventory with inventoryIdentifier 'packingInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | packingInventoryLine          | packingProductHU   |

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

    # jobScheduleA becomes the shared order's back-reference: created first, and the attribution order breaks the PriorityRule/PreparationDate tie on the lower M_Picking_Job_Schedule_ID.
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 5         |

  @from:cucumber
  # LEGACY-COLUMN SEAM ONLY, same as the QtyDelivered twin below: no production flow writes DD_OrderLine.QtyInTransit,
  # so the seeded column is the only way to reach this refusal. Pins the message contract, not a production state.
  Scenario: Goods in transit refuse a change to the contributor whose back-reference the order does NOT carry
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    # 7.5 (not a round number like 1) is deliberate — with 1 PCE the moved-quantity assertion could accidentally match a leading digit of a record id already in the message.
    When seed the legacy QtyInTransit column of 7.5 on DD_Order linked to picking job schedule jobScheduleA

    # Blocking_M_Picking_Job_Schedule_ID names jobScheduleA via the contributor association's lowest-ID tie-break — never via the order's single (soon-removed) back-reference column.
    Then changing the picking job schedule quantity is rejected:
      | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                               | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID | Blocking_QtyMoved |
      | jobScheduleB              | 2         | DDOrderPickingReconcile_MovementStarted | jobScheduleA                       | shipmentScheduleA              | groupDDOrder         | 7.5               |

    And after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

  @from:cucumber
  # LEGACY-COLUMN SEAM ONLY — this state is NOT reachable in production. No production flow writes
  # DD_OrderLine.QtyDelivered (the only writer is MDDOrderLine's zero-initialiser), so the scenario has to seed the
  # column itself. It therefore pins the refusal's MESSAGE CONTRACT (which four blocking facts it names), not a state a
  # live instance can produce. The live "goods are already moving" signal is an in-progress DD_Order_MoveSchedule, and
  # that one IS exercised for real — via the picking mover step — in DDOrderReplenishment_duplicate_disconnect.feature.
  # Re-keying this guard onto the live signal is tracked separately; until then read this scenario as seam coverage only.
  Scenario: A directly-seeded legacy QtyDelivered refuses a change to the contributor it was not moved for
    # The refusal adds up what LEFT the source and what ARRIVED at the workstation. Here the whole 15 has arrived, so
    # nothing is in transit any more and only the delivered side can hold the refusal up.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    When seed the legacy QtyDelivered column of 15 on DD_Order linked to picking job schedule jobScheduleA

    # The same four blocking facts the in-transit refusal names: whose work, whose delivery, which order, how much moved.
    Then changing the picking job schedule quantity is rejected:
      | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                               | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID | Blocking_QtyMoved |
      | jobScheduleB              | 2         | DDOrderPickingReconcile_MovementStarted | jobScheduleA                       | shipmentScheduleA              | groupDDOrder         | 15                |

    And after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

  @from:cucumber
  Scenario: A picker working on one delivery refuses a change to the other contributor of the shared order
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    # Starting the picking workflow alone creates the in-progress M_Picking_Job_Line that the busy guard checks (IsAllowPickingAnyHU=Y, no pre-allocated HU needed).
    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | orderB                | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            |

    Then changing the picking job schedule quantity is rejected:
      | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
      | jobScheduleA              | 8         | DDOrderPickingReconcile_PickerBusy | jobScheduleB                       | shipmentScheduleB              | groupDDOrder         |

    Given store workflow endpointPath api/v2/userWorkflows/wfProcess/@pickingWF@/abort in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

  @from:cucumber
  Scenario: A picker working on the last contributor's delivery refuses the DISPOSAL that un-assigning it would cause
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    When the picking job schedule is deactivated:
      | M_Picking_Job_Schedule_ID |
      | jobScheduleA              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |

    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | orderB                | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            |

    Then deleting the picking job schedules is rejected:
      | M_ShipmentSchedule_ID | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
      | shipmentScheduleB     | DDOrderPickingReconcile_PickerBusy | jobScheduleB                       | shipmentScheduleB              | groupDDOrder         |

    Given store workflow endpointPath api/v2/userWorkflows/wfProcess/@pickingWF@/abort in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: A picker on the departing contributor's OWN delivery refuses the disposal, even when it also owns the back-reference
    # Here the departing assignment is BOTH the back-ref owner AND the last contributor — a post-delete lookup via the back-reference alone would find an empty contributor set and never see the busy picker, voiding the order silently.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    When the picking job schedule is deactivated:
      | M_Picking_Job_Schedule_ID |
      | jobScheduleB              |
    And the reconcile event for M_Picking_Job_Schedule jobScheduleA is processed
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 10         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |

    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | orderA                | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            |

    Then deleting the picking job schedules is rejected:
      | M_ShipmentSchedule_ID | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
      | shipmentScheduleA     | DDOrderPickingReconcile_PickerBusy | jobScheduleA                       | shipmentScheduleA              | groupDDOrder         |

    Given store workflow endpointPath api/v2/userWorkflows/wfProcess/@pickingWF@/abort in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 10         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
    And each of jobScheduleA resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: Un-assigning the delivery whose back-reference the shared order carries leaves the order to its sibling
    # The departing delivery leaves by DELETE, which must satisfy the deferrable FK on that back-reference column within the same transaction; disposal is the departure of the LAST contributor, never of the arbitrary one the back-reference names.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    When delete picking job schedules
      | M_ShipmentSchedule_ID |
      | shipmentScheduleA     |

    Then after not more than 10s, following DD_Orders are found
      | Identifier   | DocStatus | IsPickingDisconnected |
      | groupDDOrder | CO        | false                 |

    # No explicit reconcile step here — the un-assignment fires the real after-commit event itself, which rewrites the order down to what the remaining delivery needs.
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder
