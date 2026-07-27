@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — the change guards cover every contributor of a shared order
  As a traffic manager editing a workstation assignment,
  I want the refusal that protects a replenishment already in progress to hold for EVERY delivery behind
  that replenishment — not only the one whose back-reference the document happens to carry —
  and to tell me whose work is blocking me, so that I can go and resolve it.

  A consolidated distribution order serves several deliveries but carries the back-reference of exactly one
  of them. Guarding only that one leaves every other contributor freely editable while the goods are on the
  move or a picker is working on them — silently, with no error and no log entry. The rule itself is
  unchanged: what widens is the set it is evaluated over.

  Two customer deliveries need the same product, in the same UOM, at the same workstation pick-from
  locator, replenished from the same source locator — one product group, demand 10 + 5 = 15.

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

    # On-hand stock in the SOURCE warehouse: 15 on a single locator — exactly the group's summed demand, so the
    # stock-aware split produces ONE source locator and therefore one shared line.
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
    # The second scenario makes a picker busy through the real mobile picking workflow, which sources its HUs
    # from the packing warehouse (where the shipment schedules live). IsAllowPickingAnyHU=Y so starting the job
    # does not require HUs to be pre-allocated to the schedule — the schedule is still awaiting replenishment.
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
    # QtyToDeliver (not just the row) is waited for: the async revalidation worker populates it, and
    # M_Packageable_V — hence the picking-job start of the second scenario — only sees a schedule once
    # QtyToDeliver > 0. The packing warehouse is MRP_Exclude=Y, so readiness comes solely from that worker.
    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID | QtyToDeliver |
      | shipmentScheduleA | orderLineA     | packingWH    | 10           |
      | shipmentScheduleB | orderLineB     | packingWH    | 5            |

    # Both deliveries are assigned to the same workstation — the two contributors of the product group. The
    # shared DD_Order carries jobScheduleA's back-reference: A is created first and the attribution order breaks
    # the PriorityRule/PreparationDate tie on the lower M_Picking_Job_Schedule_ID.
    And create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 5         |

  @from:cucumber
  Scenario: Goods in transit refuse a change to the contributor whose back-reference the order does NOT carry
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    # The mover has dispatched part of the consolidated replenishment: 7.5 PCE of the shared line are between the
    # source locator and the workstation. The odd quantity is deliberate — with 1 PCE, "the message reports the moved
    # quantity" would be satisfied by the leading digit of any seven-digit record id already in the message.
    When simulate goods in transit of 7.5 on DD_Order linked to picking job schedule jobScheduleA

    # The traffic manager now tries to re-plan the OTHER delivery — the one the shared order does not name.
    # The refusal must reach them anyway, and it must say whose work is in the way: the moved quantity is not
    # theirs, and neither is the assignment that the order names.
    #
    # The named assignment is jobScheduleA because the moved goods sit on the SHARED line and therefore belong to no
    # single delivery: the message names a contributor of that line OTHER than the one being edited (lowest
    # M_Picking_Job_Schedule_ID first, so it is the same on every run). It is resolved through the contributor
    # association, never through the order's single M_Picking_Job_Schedule_ID back-reference — that column names one
    # arbitrary contributor and is being removed.
    Then changing the picking job schedule quantity is rejected:
      | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                               | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID | Blocking_QtyMoved |
      | jobScheduleB              | 2         | DDOrderPickingReconcile_MovementStarted | jobScheduleA                       | shipmentScheduleA              | groupDDOrder         | 7.5               |

    # Nothing was re-planned around the refusal: same order, same quantity, same contributor set.
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

    # A picker starts the REAL mobile picking workflow for the SECOND delivery — the contributor the shared
    # DD_Order does not name. With IsAllowPickingAnyHU=Y the start call alone creates the in-progress
    # M_Picking_Job_Line the busy guard checks.
    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | orderB                | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            |

    # Changing the FIRST delivery — the one the order does name — is refused because the picker is working on
    # the SECOND one. That is the whole point: the busy picker belongs to another delivery entirely, so the
    # message has to name that delivery or the traffic manager cannot act on it.
    Then changing the picking job schedule quantity is rejected:
      | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
      | jobScheduleA              | 8         | DDOrderPickingReconcile_PickerBusy | jobScheduleB                       | shipmentScheduleB              | groupDDOrder         |

    # Release the picker through the real abort endpoint, then assert the shared order came through untouched.
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
    # The two scenarios above are re-plan refusals — a CHANGE to a surviving assignment. This one is the DISPOSAL:
    # the departure of the LAST contributor is what voids a consolidated replenishment, and voiding it is at least as
    # destructive to a picker mid-job on it as re-planning it would be. The order is the very document they are
    # working on, so the un-assignment has to be refused too.
    #
    # The un-assignment is done by DELETING the workstation assignment, which is the route where the order can no
    # longer be reached through its own back-reference: it names the contributor that left first, and the departing
    # one is somebody else entirely. Only the contributor association still connects the two.
    Given after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    # The FIRST delivery — the one whose assignment the shared order back-references — is un-assigned, so the order is
    # shrunk to serve only the second one while still carrying the departed assignment as its back-reference. The
    # second delivery is now the group's last contributor.
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

    # A picker starts the REAL mobile picking workflow for that last remaining delivery. With IsAllowPickingAnyHU=Y
    # the start call alone creates the in-progress M_Picking_Job_Line the busy guard checks.
    Given create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | orderB                | customer                 | customerLocation                  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | pickingWF                  | pickingActivity             | pickingLine            |

    # Deleting that last assignment would void the shared order out from under the picker. It is refused instead, and
    # the refusal names the delivery being picked so the traffic manager can go and resolve it.
    Then deleting the picking job schedules is rejected:
      | M_ShipmentSchedule_ID | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
      | shipmentScheduleB     | DDOrderPickingReconcile_PickerBusy | jobScheduleB                       | shipmentScheduleB              | groupDDOrder         |

    # Release the picker through the real abort endpoint, then assert the shared order — and the association that is
    # the only remaining way to reach it — survived the refused disposal intact.
    Given store workflow endpointPath api/v2/userWorkflows/wfProcess/@pickingWF@/abort in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 10s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    # ... and it is the SAME order, still reachable from the assignment that was not deleted.
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder

  @from:cucumber
  Scenario: Un-assigning the delivery whose back-reference the shared order carries leaves the order to its sibling
    # The counterpart of the disposal above, with the picker deliberately out of the picture: nobody is picking
    # anything here, so nothing but the contributor count decides whether the document lives.
    #
    # What departs is the FIRST delivery — the one whose assignment the shared order's M_Picking_Job_Schedule_ID
    # happens to name — and it departs by DELETE, the route that has to satisfy the deferrable FK on that very
    # column inside the delete transaction. The SECOND delivery is untouched and still needs its goods, so the
    # shared document must survive and simply shrink: disposal is the departure of the LAST contributor, never of
    # the arbitrary one the back-reference points at.
    #
    # Voiding it here would take the mover's only document for a demand nobody cancelled, silently — and nothing
    # would notice until the drift watchdog re-issues it as a second, un-netted physical move.
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

    # The document the surviving delivery depends on is still there. Asserted through the identifier bound above, so
    # a re-created replacement order cannot satisfy it — only the very order that was shared.
    Then after not more than 10s, following DD_Orders are found
      | Identifier   | DocStatus | IsPickingDisconnected |
      | groupDDOrder | CO        | false                 |

    # The group reconcile driven from the surviving contributor then rewrites that same order down to what that one
    # delivery alone needs, exactly as it does after every other departure route.
    When the reconcile event for M_Picking_Job_Schedule jobScheduleB is processed
    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | CO        | stockWH             | 5          |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleB              | 5   |
    And each of jobScheduleB resolves to the DD_Order identified by groupDDOrder
