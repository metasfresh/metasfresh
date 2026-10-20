@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: shipment schedule recompute is not lost for storage-segment flags
## F00130: Shipment Schedule (Lieferdisposition)
# A storage-segment flag (stock or reservation changed) makes the shipment schedules of the segment wait for the next recompute pass.
# The scenarios pause the async queues that run that pass, and run the pass themselves,
# so that a pass can run deterministically while the changing transaction is still open.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
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
    And metasfresh contains M_Warehouse:
      | Identifier  |
      | warehouse_1 |
    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product_1  | true      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | product_1    | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | customer_1 | Y          | ps_1               | A            |

# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC7_reduceReservation
  Scenario: a reservation reduction committed after the recompute pass still flags the competing shipment schedule
    Given metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_TU    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_TU           | huPI_TU    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_TU     | huPIV_TU           | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_TU_10PCE          | huPIItem_TU     | product_1    | 10 PCE |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-01-10   | warehouse_1    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product_1               | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_a    | true    | customer_1    | 2024-01-15  | warehouse_1    | A            |
      | order_b    | true    | customer_1    | 2024-01-10  | warehouse_1    | A            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l1         | order_a    | product_1    | 10         |
      | l2         | order_b    | product_1    | 10         |
    And the order identified by order_a is completed
    And the order identified by order_b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s1         | l1             | N             |
      | s2         | l2             | N             |
    And metasfresh contains M_QtyReservations:
      | Identifier | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | SupplyType |
      | res_1      | l1             | product_1    | warehouse_1    | 10 PCE | 1     | OH         |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyToDeliver |
      | s2                    | 0                |

    Given the shipment schedule recompute queues are paused
    When M_QtyReservation bucket is reduced, committed only after the pending recompute ran:
      | C_OrderLine_ID | SupplyType | QtyTU |
      | l1             | OH         | 1     |
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for s2
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyToDeliver |
      | s2                    | 10               |

# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC7_bulkFlush
  Scenario: a stock change committed after the recompute pass is not lost when the segment flush runs mid-batch
    Given temporarily set sys config int value 1 for sys config "de.metas.inoutcandidate.ShipmentScheduleSegmentFlushThreshold"
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_a    | true    | customer_1    | 2024-01-15  | warehouse_1    | A            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l1         | order_a    | product_1    | 10         |
    And the order identified by order_a is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s          | l1             | N             |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-01-10   | warehouse_1    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product_1               | 0       | 10       | PCE          |

    Given the shipment schedule recompute queues are paused
    When the inventory identified by inventory is completed, committed only after the pending recompute ran
    # a later stock-changed re-flag heals the final value, so this marker assertion is the one that pins the lost flag
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for s
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyOnHand |
      | s                     | 10            |

# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC10_afterCommitNotify
  Scenario: a storage segment change notified after its transaction committed still flags the shipment schedules of the segment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_a    | true    | customer_1    | 2024-01-15  | warehouse_1    | A            |
      | order_b    | true    | customer_1    | 2024-01-10  | warehouse_1    | A            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | olA        | order_a    | product_1    | 10         |
      | olB        | order_b    | product_1    | 10         |
    And the order identified by order_a is completed
    And the order identified by order_b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssA        | olA            | N             |
      | ssB        | olB            | N             |
    And the shipment-schedule recompute work queue drains to zero pending workpackages

    Given the shipment schedule recompute queues are paused
    When a storage segment change for product "product_1" and warehouse "warehouse_1" is notified after its transaction committed
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ssB
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssB        | olB            | N             |

# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC10_orderLineControl
  Scenario: control - a completed order line changed in its own transaction flags the shipment schedules of its segment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_a    | true    | customer_1    | 2024-01-15  | warehouse_1    | A            |
      | order_b    | true    | customer_1    | 2024-01-10  | warehouse_1    | A            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | olA        | order_a    | product_1    | 10         |
      | olB        | order_b    | product_1    | 10         |
    And the order identified by order_a is completed
    And the order identified by order_b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssA        | olA            | N             |
      | ssB        | olB            | N             |
    And the shipment-schedule recompute work queue drains to zero pending workpackages

    Given the shipment schedule recompute queues are paused
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.Description |
      | olA                       | changed         |
    Then after not more than 30s, 1 untagged M_ShipmentSchedule_Recompute marker exists for ssB
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssB        | olB            | N             |

# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC8_commitFails
  Scenario: a recompute batch whose transaction fails at commit keeps the flags of its shipment schedules
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_a    | true    | customer_1    | 2024-01-15  | warehouse_1    | A            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l1         | order_a    | product_1    | 10         |
    And the order identified by order_a is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | l1             | N             |
    And the shipment-schedule recompute work queue drains to zero pending workpackages

    Given the shipment schedule recompute queues are paused
    And the shipment schedule "ss_1" is flagged for recompute 1 times in one transaction, also from nested calls
    And the next shipment schedule recompute pass fails when its transaction commits
    When the pending shipment schedule recompute pass runs and fails
    # the failed batch rolled back, so its flag must still wait for the next pass
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | l1             | N             |

# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC8_callerCommits
  Scenario: a recompute failing inside a caller's transaction that catches it and commits keeps the flags of its shipment schedules
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_a    | true    | customer_1    | 2024-01-15  | warehouse_1    | A            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | l1         | order_a    | product_1    | 10         |
    And the order identified by order_a is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | l1             | N             |
    And the shipment-schedule recompute work queue drains to zero pending workpackages

    Given the shipment schedule recompute queues are paused
    And the shipment schedule "ss_1" is flagged for recompute 1 times in one transaction, also from nested calls
    And the next shipment schedule recompute pass fails while recomputing
    When the pending shipment schedule recompute pass runs inside a caller's transaction that catches the failure and commits
    # the caller committed; the failed recompute must not have consumed the flag
    Then 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | l1             | N             |
