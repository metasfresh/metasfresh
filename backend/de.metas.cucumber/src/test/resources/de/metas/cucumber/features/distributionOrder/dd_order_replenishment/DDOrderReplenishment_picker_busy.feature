@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — picker-busy guard (real picking flow: start + abort)
  As a warehouse operator, when a picker is already working on the distribution order of a shipment
  schedule, I want any attempt to change that schedule to be refused immediately (with a clear error),
  and the async reconcile event to be refused as well — until the picking job is aborted, after which a
  repost recreates the distribution order with the new quantity.

  # The picker is made busy through the REAL mobile picking workflow (REST: start the wfProcess + scan the
  # picking slot), which produces an in-progress (Drafted) M_Picking_Job_Line for the schedule. The picker
  # is released through the REAL abort endpoint (POST .../wfProcess/{wfProcessId}/abort), which voids the
  # picking job so the (voided-aware) busy guard reports the picker as free again.

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
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customer      | customerLocation       | true        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID |
      | packingWH      | customer      | customerLocation       | Y           | Y                       | network                   |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |

    # --- Picking prerequisites on the packing warehouse (where the schedule, and thus the picking job,
    #     sources its HUs from): stock via inventory -> HU, a picking slot, and the mobile picking profile.
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |
    # IsAllowPickingAnyHU=Y so starting the picking job does NOT require HUs to be pre-planned/allocated
    # to the schedule (the schedule sits on an auto-distribution warehouse awaiting replenishment, so its
    # stock is not allocated up front) — the picker may grab any HU, matching how the Playwright picking
    # tests configure the profile. This lets a real picking job start and create the in-progress
    # M_Picking_Job_Line the busy guard checks.
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
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot | IsDynamic |
      | pickingSlot | 063.1       | Y         |

    # --- One completed sales order on the packing warehouse is the starting state for every scenario ----
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
    And after not more than 120s, the DD_Order linked to shipment schedule is found:
      | Identifier | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | ddOrder    | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |

  @from:cucumber
  Scenario: A busy picker (real picking job) makes the beforeSave interceptor reject the schedule change
    # Start a REAL picking job for the order and scan the picking slot. This creates an in-progress
    # (Drafted) M_Picking_Job_Line referencing the same M_ShipmentSchedule_ID as the DD_Order, so the
    # busy guard now reports the picker as busy.
    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | order                 | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            | pickingStep            | pickingStepQR                |
    And scan M_PickingSlot for PickingJob
      | WorkflowProcess.Identifier | M_PickingSlot_ID.Identifier |
      | pickingWF                  | pickingSlot                 |

    # Attempting to change the schedule qty is rejected by the M_ShipmentSchedule.beforeSave guard
    # (the tx rolls back; the schedule's persisted value stays unchanged and no event is published).
    Then changing the M_ShipmentSchedule quantity is rejected:
      | M_ShipmentSchedule_ID | QtyOrdered_Override |
      | shipmentSchedule      | 8                   |

    # ABORT the picking job through the real REST endpoint -> the picking job becomes Voided, so the
    # (voided-aware) busy guard reports the picker as free again.
    Given store workflow endpointPath api/v2/userWorkflows/wfProcess/@pickingWF@/abort in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # The DD_Order is untouched (still the original Completed one, qty 5) — because the rejected save
    # rolled back, afterSave never published a reconcile event, so no void/recreate happened.
    Then after not more than 10s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |
    # The original DD_Order identifier (captured in Background) must be unchanged (same ID, still Completed).
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder    | CO        |

  @from:cucumber
  Scenario: A busy picker (real picking job) makes the reconcile event fail; aborting the job lets the repost recreate the DD_Order
    # Start a REAL picking job for the order and scan the picking slot — picker is now busy.
    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | order                 | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            | pickingStep            | pickingStepQR                |
    And scan M_PickingSlot for PickingJob
      | WorkflowProcess.Identifier | M_PickingSlot_ID.Identifier |
      | pickingWF                  | pickingSlot                 |

    # Processing the reconcile event (the consumer-side definitive guard) is rejected while the picker is busy.
    # NOTE: this step drives the BL directly (not via the async DDOrderReconciliationEventHandler) so
    # no AD_EventLog_Entry is produced here. The handler's error-recording path (IsError=true) is covered
    # by the async-flow scenario. This scenario focuses on the BL-level picker-busy guard.
    Then processing the reconcile event for M_ShipmentSchedule shipmentSchedule is rejected

    # The DD_Order is left unchanged by the failed reconcile.
    And after not more than 5s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |

    # ABORT the picking job through the real REST endpoint -> the picking job becomes Voided, releasing the
    # picker. Then reprocessing the event succeeds: the reconcile classifies as RECREATE (active schedule +
    # existing live DD_Order) — the old DD_Order is voided and a fresh one is created. Capturing a new
    # Identifier pins that recreate actually happened.
    Given store workflow endpointPath api/v2/userWorkflows/wfProcess/@pickingWF@/abort in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    And the reconcile event for M_ShipmentSchedule shipmentSchedule is processed
    Then after not more than 10s, the DD_Order linked to shipment schedule is found:
      | Identifier | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | ddOrderV2  | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |
    # The original DD_Order (captured in Background as ddOrder) must now be Voided — RECREATE voided it.
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder    | VO        |
