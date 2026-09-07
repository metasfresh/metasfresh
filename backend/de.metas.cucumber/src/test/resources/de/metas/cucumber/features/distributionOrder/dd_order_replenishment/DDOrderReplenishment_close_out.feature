@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — shipment close-out disposes the obsolete replenishment (packing is GOD)
  - Shipment close-out (M_Picking_Job_Schedule.Processed=Y) must always succeed, even while a picking order is open.
  - No replenishment move in progress: the obsolete DD_Order is CLOSED and the picker is released.
  - A replenishment move in progress: the DD_Order is DISCONNECTED (IsPickingDisconnected=Y, contributor rows retained) so the worker finishes it.

  # Test seams: the picker is made busy via the real mobile picking REST workflow; the close-out is real shipment
  # generation (which marks the workstation assignment Processed=Y); disposal is driven via the after-commit reconcile
  # event, invoked directly for deterministic ordering.

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
    # The stocking warehouse's default locator is flagged as a ground-floor locator so the replenishment service
    # considers it when computing the required allocation (IsGroundLocator=Y is required by the ground-filter).
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

    # On-hand stock in the SOURCE warehouse (single locator, qty 3 = the partial replenishment demand) so the
    # stock-aware split builds the DD_Order and a whole-HU pick moves exactly the scheduled qty.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | stockLocator | 0       | 3        | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockSourceHU      |

    # --- Picking prerequisites on the packing warehouse: stock via inventory -> HU + the mobile picking profile.
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

    # --- One completed sales order on the packing warehouse is the starting state for every scenario ----
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID | QtyToDeliver |
      | shipmentSchedule | orderLine      | packingWH    | 5            |
    # Assigning the schedule line to the workstation triggers the reconcile that creates the DD_Order. The assignment
    # replenishes only PART of the shipment qty (3 of 5) — the operator brought the rest to the station manually.
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobSchedule               | shipmentSchedule      | workplace      | 3         |
    And after not more than 120s, the DD_Order linked to picking job schedule is found:
      | Identifier | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
      | ddOrder    | jobSchedule               | CO        | stockWH             | packingWH         | packingLocator | 3          |

    # Start a REAL picking job for the order. With IsAllowPickingAnyHU=Y the start call alone creates an active
    # M_Picking_Job_Line referencing the schedule, so the busy guard now reports the picker as busy.
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | order                 | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            |

  @from:cucumber
  Scenario: Close-out succeeds while a picker is busy and CLOSES the obsolete replenishment + releases the picker
    # A worker has the DD_Order-backed DistributionJob (AD_User_Responsible_ID set) — proving the CLOSE path releases it.
    Given a worker takes the DD_Order linked to picking job schedule:
      | M_Picking_Job_Schedule_ID |
      | jobSchedule               |

    # Close-out is real shipment generation: the operator brought the rest of the qty to the packing station, so the
    # packing-warehouse stock ships and the workstation assignment is closed out (M_Picking_Job_Schedule.Processed=Y).
    # This is a fulfilment event — it must never be blocked by the picker-busy guard, even while a picker has the
    # DD_Order job active.
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentSchedule      | jobSchedule               |

    # Disposal happens in the after-commit reconcile (driven directly here for deterministic ordering). No move is
    # in progress, so the obsolete DD_Order is CLOSED (not Voided) and the picker's assignment is released.
    And the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    Then after not more than 10s, following DD_Orders are found
      | Identifier | DocStatus | IsPickingDisconnected | AD_User_Responsible_ID |
      | ddOrder    | CL        | false                 | -                      |

  @from:cucumber
  Scenario: The picker-busy guard is preserved — a genuine qty re-plan while a picker is busy is still refused
    # A qty change (NOT the Processed=Y close-out) while the picker is busy must still be refused: the guard
    # protects genuine re-plans. The DD_Order is left untouched (still the original Completed one).
    Then changing the picking job schedule quantity is rejected:
      | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                          |
      | jobSchedule               | 8         | DDOrderPickingReconcile_PickerBusy |

    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus | IsPickingDisconnected |
      | ddOrder    | CO        | false                 |

  @from:cucumber
  Scenario: In-progress replenishment move at close-out → DISCONNECT (not Closed), contributor rows retained, still pickable
    # A worker has started moving the replenishment stock (picked the source HU), so the move is in progress — a
    # half-done move must not be corrupted by a CLOSE (which would hit the BEFORE_CLOSE clearSchedules guard).
    Given pick from the DD_Order linked to picking job schedule:
      | M_Picking_Job_Schedule_ID | PickFrom_HU_ID |
      | jobSchedule               | stockSourceHU  |

    # Close-out still succeeds (packing is GOD): real shipment generation ships the packing-station stock and marks
    # the workstation assignment Processed=Y ...
    When shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_Picking_Job_Schedule_ID |
      | shipmentSchedule      | jobSchedule               |
    And the reconcile event for M_Picking_Job_Schedule jobSchedule is processed

    # ... and the DD_Order is DISCONNECTED, not Closed: IsPickingDisconnected=Y, still Completed, contributor rows
    # retained for traceability so the worker can finish it as a standalone replenishment.
    Then after not more than 10s, following DD_Orders are found
      | Identifier | DocStatus | IsPickingDisconnected |
      | ddOrder    | CO        | true                  |

    # No re-trigger: the disconnected DD_Order is invisible to the reconcile lookup, so a follow-up reconcile
    # resolves to NONE — it must not re-void, re-close, or create a fresh replenishment.
    And the reconcile event for M_Picking_Job_Schedule jobSchedule is processed
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus | IsPickingDisconnected |
      | ddOrder    | CO        | true                  |
