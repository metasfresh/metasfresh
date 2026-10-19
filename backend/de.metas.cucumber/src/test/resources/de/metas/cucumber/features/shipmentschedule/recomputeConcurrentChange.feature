@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: shipment schedule recompute pass meets a concurrent change
## F00130: Shipment Schedule (Lieferdisposition)
# A shipment schedule that is flagged for recompute waits for the next recompute pass.
# The scenarios pause the async queues that run that pass, and run the pass themselves,
# so that a pass can run deterministically while another transaction is still open.

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2024-01-15T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_Products:
      | Identifier |
      | product_1  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | product_1    | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer_1 | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_1    | true    | customer_1    | 2024-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_1 | order_1    | product_1    | 10         |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_harness
  Scenario: a new shipment schedule waits for the paused recompute pass and is recomputed once the queues resume
    Given the shipment schedule recompute queues are paused
    When the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | Y             |
    And 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |

  @Id:S33004_TC3_product
  Scenario: a product-wide flag committed after the recompute pass is not lost
    Given the shipment schedule recompute queues are paused
    When the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    And the pending shipment schedule recompute pass runs
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |
    And the shipment schedule "ss_1" is flagged for recompute 1 times in one transaction, also from nested calls
    When the shipment schedules of product "product_1" are flagged for recompute in a transaction committed only after the pending recompute ran, observed on "ss_1"
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |

  @Id:S33004_TC3_headerKey
  Scenario: a header-aggregation-key flag committed after the recompute pass is not lost
    Given the shipment schedule recompute queues are paused
    When the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    And the pending shipment schedule recompute pass runs
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |
    And the shipment schedule "ss_1" is flagged for recompute 1 times in one transaction, also from nested calls
    When the shipment schedules with the header aggregation key of "ss_1" are flagged for recompute in a transaction committed only after the pending recompute ran, observed on "ss_1"
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |

  @Id:S33004_TC4_single
  Scenario: repeated flags of one schedule in one transaction leave exactly one marker
    Given the shipment schedule recompute queues are paused
    When the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    And the pending shipment schedule recompute pass runs
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |
    When the shipment schedule "ss_1" is flagged for recompute 5 times in one transaction, also from nested calls
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |

  @Id:S33004_TC4_withPending
  Scenario: repeated flags in a later transaction add one marker next to the older pending one
    Given the shipment schedule recompute queues are paused
    When the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    And the pending shipment schedule recompute pass runs
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |
    And the shipment schedule "ss_1" is flagged for recompute 1 times in one transaction, also from nested calls
    When the shipment schedule "ss_1" is flagged for recompute 5 times in one transaction, also from nested calls
    Then 2 untagged M_ShipmentSchedule_Recompute markers exist for ss_1
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC2_repick
  Scenario: reversing a shipment of an aggregate HU re-picks it while the pending recompute runs, and the re-picked quantity is not lost
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And temporarily set sys config boolean value true for sys config 'SKIP_WP_PROCESSOR_FOR_AUTOMATION'
    And temporarily set sys config boolean value false for sys config 'de.metas.handlingunits.HUConstants.Fresh_QuickShipment'
    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU'
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name | HU_UnitType | IsCurrent |
      | TU                            | TU                    | TU   | TU          | Y         |
      | LU                            | LU                    | LU   | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx4                               | TU                         | product_1               | 4   | 2000-01-01 |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-01-10   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product_1               | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | pickFromCU |
    And transform CU to new LU
      | sourceCU   | newLU                | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU | pickFromAggregatedLU | TU       | 4           | 10          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer_1               | 2024-01-15  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product_1               | 160        | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU           | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product_1             | pickFromAggregatedLU | 3         | 1           | N                     | 2027-03-01     | 9876  |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | shipmentSchedule      | 0               |

    # an older pending flag, which the recompute pass running during the reversal deletes.
    # Guard: the reversal's own shipment-line and HU segment changes are flushed after its commit and re-flag the schedule, so it heals
    Given the shipment schedule recompute queues are paused
    And the shipment schedule "shipmentSchedule" is flagged for recompute 1 times in one transaction, also from nested calls
    When the shipment identified by shipment is reversed, committed only after the pending recompute ran
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for shipmentSchedule
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | shipmentSchedule      | 12              |
    # the TUs replayed through the aggregate HU were merged into one row (without the merge: one row of 4 per TU)
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyPicked | Processed |
      | 12        | N         |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC2_regularPick
  Scenario: a regular pick committed after the recompute pass still gets its picked quantity onto the shipment schedule
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-01-10   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product_1               | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | line1              | hu_1    |
    And the order identified by order_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |

    # an older pending flag, which the recompute pass running during the pick deletes.
    # Guard: the pick's HU segment change is flushed after its commit and re-flags the schedule, so it heals
    Given the shipment schedule recompute queues are paused
    And the shipment schedule "ss_1" is flagged for recompute 1 times in one transaction, also from nested calls
    When the following qty is picked while the pending shipment schedule recompute runs before the pick is committed
      | M_ShipmentSchedule_ID.Identifier | M_HU_ID.Identifier | QtyPicked |
      | ss_1                             | hu_1               | 10        |
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | ss_1                  | 10              |
