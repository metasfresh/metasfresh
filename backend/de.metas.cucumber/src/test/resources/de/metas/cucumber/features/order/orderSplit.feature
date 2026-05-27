@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19012_Material_Cockpit_v2_for_Reservation_in_Sales_Order
@ghActions:run_on_executor5
Feature: Split sales order — create a continuation order for unshipped quantity

  When a completed sales order that has at least one shipment is split via the C_Order_Split process,
  the system creates a continuation (NEW) SO with one line per old-SO line that still has unshipped
  quantity, and closes all shipment schedules and active reservations of the original (OLD) SO.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-05-17T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_os      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl_os      | ps_os              | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_os     | pl_os          |

    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product_os | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_os      | plv_os                 | product_os   | 10.00    | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | bp_os      | true       | ps_os              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bp_os_loc  | bp_os         | Y               | Y               |

    And metasfresh contains M_Warehouse:
      | Identifier   |
      | warehouse_os |


  @from:cucumber
  @Id:S_OS_10
  Scenario: Happy path — single line partial delivery, process creates continuation order with residue qty
  # OLD SO has 1 line QtyOrdered=10, QtyDelivered=8.
  # After split: OLD line untouched; NEW SO created with POReference=oldSO.DocumentNo,
  # DocStatus=DR, C_Project_ID cleared, 1 line with QtyEntered=2 QtyDelivered=0.

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os10    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os10_1   | so_os10    | product_os   | 10         |
    And the order identified by so_os10 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | sched_os10_1  | sol_os10_1     | N             |

    # Deliver 8 of 10 (partial shipment)
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_os10_1          | D            | true                | false       | 8                                               |

    # Wait for the async shipment workpackage to complete and create the M_InOut
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | sched_os10_1          | inout_os10  |

    When the C_Order_Split process is invoked on "so_os10"

    # NEW continuation order must exist with POReference = old SO's DocumentNo
    Then the continuation order for "so_os10" is found and stored as "new_so_os10"

    # NEW SO: DocStatus=DR, C_Project_ID null
    And validate the created orders
      | C_Order_ID  | DocStatus | C_Project_ID |
      | new_so_os10 | DR        | null         |

    # NEW SO: exactly 1 line with QtyEntered=2 (residue)
    And the continuation order "new_so_os10" has exactly 1 line with QtyEntered=2 for product "product_os"

    # OLD line is untouched (QtyOrdered=10 unchanged)
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered |
      | sol_os10_1     | 10         |


  @from:cucumber
  @Id:S_OS_20
  Scenario: Multi-line mix — fully shipped line stays on OLD SO, partial and never-shipped lines go to NEW SO
  # OLD SO has 3 lines:
  #   line A: QtyOrdered=5, QtyDelivered=5 (fully shipped — stays on OLD SO only)
  #   line B: QtyOrdered=10, QtyDelivered=4 (partial — goes to NEW SO with residue qty=6)
  #   line C: QtyOrdered=7, QtyDelivered=0 (never shipped — goes to NEW SO with qty=7)
  # 1 shipment exists (for lines A and B).
  # After split: NEW SO has exactly 2 lines (B residue=6 and C=7); A stays on OLD SO only.

    Given metasfresh contains M_Products:
      | Identifier  | IsStocked |
      | product_os2 | false     |
      | product_os3 | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_os2     | plv_os                 | product_os2  | 12.00    | PCE               | Normal                        |
      | pp_os3     | plv_os                 | product_os3  | 15.00    | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os20    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os20_A  | so_os20    | product_os   | 5          |
      | sol_os20_B  | so_os20    | product_os2  | 10         |
      | sol_os20_C  | so_os20    | product_os3  | 7          |
    And the order identified by so_os20 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | sched_os20_A | sol_os20_A     | N             |
      | sched_os20_B | sol_os20_B     | N             |
      | sched_os20_C | sol_os20_C     | N             |

    # Ship all 5 of line A and 4 of line B in two generate-shipments runs
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | sched_os20_A          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | sched_os20_A          | inout_os20_A  |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_os20_B          | D            | true                | false       | 4                                               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | sched_os20_B          | inout_os20_B  |

    When the C_Order_Split process is invoked on "so_os20"

    # NEW SO created
    Then the continuation order for "so_os20" is found and stored as "new_so_os20"

    # NEW SO must have exactly 2 lines (B residue=6 and C=7); A (fully shipped) must NOT appear
    And the continuation order "new_so_os20" has exactly 2 lines
    And the continuation order "new_so_os20" has exactly 1 line with QtyEntered=6 for product "product_os2"
    And the continuation order "new_so_os20" has exactly 1 line with QtyEntered=7 for product "product_os3"
    And the continuation order "new_so_os20" has no line for product "product_os"


  @from:cucumber
  @Id:S_OS_30
  Scenario: Fully-delivered line stays on OLD SO — not copied to continuation order
  # OLD SO has 2 lines:
  #   line A: QtyOrdered=5, QtyDelivered=5 (fully delivered — stays on OLD SO; QtyOrdered <= QtyDelivered)
  #   line B: QtyOrdered=10, QtyDelivered=6 (partial — goes to NEW SO with residue qty=4)
  # After split: NEW SO has exactly 1 line for B; A is NOT in the NEW SO.
  #
  # Note on design: the original scenario S_OS_30 specified an over-delivered line (QtyOrdered=5,
  # QtyDelivered=7). Over-delivery cannot be set up via the standard generate-shipments step defs
  # (the system enforces QtyDelivered <= QtyOrdered). This scenario tests the equivalent code path:
  # any line where QtyOrdered <= QtyDelivered is excluded from the NEW SO. A fully-delivered line
  # (5/5) exercises exactly the same `QtyOrdered > QtyDelivered` branch as an over-delivered line.
  # The true over-delivery edge case should be covered by the OrderSplitCommandTest unit tests.

    Given metasfresh contains M_Products:
      | Identifier   | IsStocked |
      | product_os30 | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_os30    | plv_os                 | product_os30  | 8.00     | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os30    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os30_A  | so_os30    | product_os   | 5          |
      | sol_os30_B  | so_os30    | product_os30 | 10         |
    And the order identified by so_os30 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | sched_os30_A | sol_os30_A     | N             |
      | sched_os30_B | sol_os30_B     | N             |

    # Ship all 5 of line A (fully delivers it) and 6 of line B (partial)
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | sched_os30_A          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | sched_os30_A          | inout_os30_A  |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_os30_B          | D            | true                | false       | 6                                               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | sched_os30_B          | inout_os30_B  |

    When the C_Order_Split process is invoked on "so_os30"

    # NEW SO has exactly 1 line for B (residue=4); A (fully delivered, stays on OLD)
    Then the continuation order for "so_os30" is found and stored as "new_so_os30"
    And the continuation order "new_so_os30" has exactly 1 line with QtyEntered=4 for product "product_os30"
    And the continuation order "new_so_os30" has no line for product "product_os"


  @from:cucumber
  @Id:S_OS_40
  Scenario: Active reservations on OLD SO lines are closed after split
  # OLD SO has 1 line QtyOrdered=10, QtyDelivered=4.
  # An active M_QtyReservation for that line (Qty=6, Processed=N) exists.
  # After split: the reservation has Processed=Y (closed by the process).

    Given metasfresh contains M_HU_PI:
      | M_HU_PI_ID   |
      | huPI_os40    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_os40         | huPI_os40  | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID  | M_HU_PI_Version_ID | ItemType |
      | huPIItem_os40    | huPIV_os40         | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_os40              | huPIItem_os40   | product_os   | 10 PCE |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os40    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os40_1  | so_os40    | product_os   | 10         |
    And the order identified by so_os40 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | sched_os40_1 | sol_os40_1     | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_os40_1          | D            | true                | false       | 4                                               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | sched_os40_1          | inout_os40  |

    And metasfresh contains M_QtyReservations:
      | Identifier      | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation_os40 | sol_os40_1    | product_os   | warehouse_os   | 6 PCE  | 1     |

    When the C_Order_Split process is invoked on "so_os40"

    # Reservation must be closed (Processed=Y) after the split
    Then validate M_QtyReservations:
      | Identifier       | Processed |
      | reservation_os40 | true      |


  @from:cucumber
  @Id:S_OS_50
  Scenario: All shipment schedules on OLD SO are closed after split
  # OLD SO has 2 lines. Both have M_ShipmentSchedule rows.
  # 1 shipment exists for line A only; line B has no shipment.
  # After split: every M_ShipmentSchedule for the old SO has IsClosed=Y.

    Given metasfresh contains M_Products:
      | Identifier   | IsStocked |
      | product_os50 | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_os50    | plv_os                 | product_os50  | 9.00     | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os50    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os50_A  | so_os50    | product_os   | 5          |
      | sol_os50_B  | so_os50    | product_os50 | 8          |
    And the order identified by so_os50 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | sched_os50_A | sol_os50_A     | N             |
      | sched_os50_B | sol_os50_B     | N             |

    # Ship line A only
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | sched_os50_A          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | sched_os50_A          | inout_os50_A  |

    When the C_Order_Split process is invoked on "so_os50"

    # All shipment schedules for the old SO must now be closed
    Then after not more than 30s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | IsClosed |
      | sched_os50_A                     | true     |
      | sched_os50_B                     | true     |


  @from:cucumber
  @Id:S_OS_60
  Scenario: Validation — process rejects order with no completed shipment
  # OLD SO has 1 line QtyOrdered=10, QtyDelivered=0 and NO M_InOut exists.
  # Process must fail with an error containing "C_Order_Split_NoShipments".
  # No NEW SO is created; OLD line retains QtyOrdered=10.

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os60    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os60_1  | so_os60    | product_os   | 10         |
    And the order identified by so_os60 is completed

    When the C_Order_Split process is invoked on "so_os60" expecting validation failure

    Then the validation error message includes "C_Order_Split_NoShipments"

    # No continuation order created
    And no continuation order exists for "so_os60"

    # OLD line unchanged
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered |
      | sol_os60_1     | 10         |


  @from:cucumber
  @Id:S_OS_70
  Scenario: Validation — process rejects order where all qty is already delivered
  # OLD SO has 1 line QtyOrdered=10 QtyDelivered=10; a completed shipment for the full qty exists.
  # Process must fail with an error containing "C_Order_Split_NothingToSplit".
  # No NEW SO is created.

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | so_os70    | true    | bp_os         | 2026-05-17  | warehouse_os   | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_os70_1  | so_os70    | product_os   | 10         |
    And the order identified by so_os70 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | sched_os70_1 | sol_os70_1     | N             |

    # Ship the full qty
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | sched_os70_1          | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | sched_os70_1          | inout_os70  |

    When the C_Order_Split process is invoked on "so_os70" expecting validation failure

    Then the validation error message includes "C_Order_Split_NothingToSplit"

    # No continuation order created
    And no continuation order exists for "so_os70"
