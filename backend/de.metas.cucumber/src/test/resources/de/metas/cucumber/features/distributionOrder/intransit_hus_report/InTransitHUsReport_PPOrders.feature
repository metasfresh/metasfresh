@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5114_MobileUI_Distribution
@ghActions:run_on_executor8
Feature: Materialbegleitschein (In-Transit HUs report) for DD_Order lines that supply manufacturing orders
  As a mover with HUs on my trolley,
  I want the printed Materialbegleitschein to list the manufacturing orders (start date, quantity, picking instruction) each product is meant for,
  so that I know where the goods go, also when one distribution order line bundles the demand of several manufacturing orders.

  The scenarios assert the PDF as rendered and archived (not a mock): a bundled line shows one summary line per manufacturing order,
  a line for one order without a picking instruction keeps the single-order header.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    # render the real jasper report; the mock report service would archive a placeholder PDF instead
    And temporarily set sys config boolean value false for sys config 'de.metas.report.jasper.IsMockReportService'
    # the PDF steps read the archive back out of the database
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And temporarily set sys config boolean value true for sys config 'SKIP_WP_PROCESSOR_FOR_AUTOMATION'
    # a fixed time makes the dispo's DateOrdered equal for all candidates (it is part of the aggregation key)
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    # one DD_Order line may bundle the demand of several manufacturing orders
    And temporarily set sys config boolean value false for sys config 'DDOrderAggregation.header.byPPOrderRef'

    # a short fixed value and name: the HU row and the QR label then do not wrap, so the line counts below do not depend on the clock
    And metasfresh contains M_Products:
      | Identifier | Value    | Name             |
      | COMP       | MBS_COMP | MBS Komponente   |
      | COMP2      | MBS_C2   | MBS Komponente 2 |
    And metasfresh contains M_Products:
      | Identifier |
      | FG         |
      | FG2        |
      | FG3        |

    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsInTransit | PP_Plant_ID | M_Locator_ID |
      | rawWH          | false       |             | rawLoc       |
      | prodWH         | false       | plant       |              |
    # the trolley's own in-transit warehouse and locator, so the report lists exactly this scenario's HUs
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsInTransit |
      | trolleyWH      | true        |
    And metasfresh contains M_Locator:
      | Identifier | M_Warehouse_ID | IsDefault |
      | trolleyLoc | trolleyWH      | N         |

    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | N1                        |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | NL1                           | N1                        | prodWH         | rawWH                | shipper      |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_fg     | FG           | bomVersions_fg            |
      | bom_fg2    | FG2          | bomVersions_fg2           |
      | bom_fg3    | FG3          | bomVersions_fg3           |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | PickingInstruction |
      | boml_fg    | bom_fg            | COMP         | 1        | Sahne              |
      | boml_fg2   | bom_fg2           | COMP         | 1        |                    |
      | boml_fg3_1 | bom_fg3           | COMP         | 1        | ohne Rinde         |
      | boml_fg3_2 | bom_fg3           | COMP2        | 1        | gerieben           |
    And the PP_Product_BOM identified by bom_fg is completed
    And the PP_Product_BOM identified by bom_fg2 is completed
    And the PP_Product_BOM identified by bom_fg3 is completed

    # IsCreatePlan=N: the DD_Order_Candidates are enqueued by the scenario, not by the dispo
    And metasfresh contains PP_Product_Plannings
      | Identifier    | M_Product_ID | PP_Product_BOMVersions_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | S_Resource_ID | IsCreatePlan |
      | fg_planning   | FG           | bomVersions_fg            |                           | prodWH         | plant         | N            |
      | fg2_planning  | FG2          | bomVersions_fg2           |                           | prodWH         | plant         | N            |
      | fg3_planning  | FG3          | bomVersions_fg3           |                           | prodWH         | plant         | N            |
      | comp_planning | COMP         |                           | N1                        | prodWH         | plant         | N            |
      | comp2_plan    | COMP2        |                           | N1                        | prodWH         | plant         | N            |

  @from:cucumber
  Scenario: Bundled line for two manufacturing orders with different picking instructions prints one summary line per order
    # the raw material is in stock when the orders are planned, so the dispo advises a distribution candidate for each order
    Given metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv1           | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv1           | invLine1           | COMP         | rawLoc       | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'inv1'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLine1           | hu1     |

    # the start date is a midday instant, so 12.04.2021 prints whatever the JVM default time zone is
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP1         | MOP         | FG           | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP1    |
      | PP2         | MOP         | FG           | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP2    |
    And after not more than 60s, PP_Order_BomLines are found
      | Identifier | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | PP1_L1     | PP1         | COMP         | 10 PCE       | CO            |
      | PP2_L1     | PP2         | COMP         | 10 PCE       | CO            |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | c1         | COMP         | rawWH               | prodWH           | 10 PCE | PP1                 |
      | c2         | COMP         | rawWH               | prodWH           | 10 PCE | PP2                 |
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c1                    |
      | c2                    |
    # one DD_Order with ONE line for both manufacturing orders
    And after not more than 60s, the DD_Order_Candidates are allocated to DD_OrderLines:
      | DD_Order_Candidate_ID | DD_Order_ID | DD_OrderLine_ID |
      | c1                    | ddo1        | ddol1           |
      | c2                    | ddo1        | ddol1           |
    # a per-order edit after the DD_Order exists
    And update PP_Order_BOMLines:
      | PP_Order_BOMLine_ID | PickingInstruction |
      | PP2_L1              | ohne Rinde         |

    And the mobile user's trolley is set to locator trolleyLoc
    And Start distribution job for dd_order identified by ddo1
      | DD_Order_ID |
      | ddo1        |
    And Pick HU for distribution job line
      | M_HU_ID |
      | hu1     |
    And M_HU are validated:
      | M_HU_ID | HUStatus | IsActive | M_Locator_ID |
      | hu1     | A        | true     | trolleyLoc   |

    When the Materialbegleitschein is printed for locator trolleyLoc

    Then an AD_Archive exists for the record identified by "trolleyLoc"
    # one summary line per order, with the order's own quantity and picking instruction
    And the visual line containing text "MBS_PP1 (Start" in the PDF archived for the record identified by "trolleyLoc" is exactly "Produktionsauftrag MBS_PP1 (Start 12.04.2021, 10 Stk): Kommissionierhinweis: Sahne"
    And the visual line containing text "MBS_PP2 (Start" in the PDF archived for the record identified by "trolleyLoc" is exactly "Produktionsauftrag MBS_PP2 (Start 12.04.2021, 10 Stk): Kommissionierhinweis: ohne Rinde"
    # each order once, one line each, in order
    And in the PDF archived for the record identified by "trolleyLoc", exactly 0 lines appear between text "MBS_PP1 (Start" and text "MBS_PP2 (Start"
    # no single-order header (the QR label hides its order line too, since the line has 2 orders)
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "Produktionsauftrag:"
    # neither the HU row nor the QR label page repeats the order list: the QR label shows the product name only
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "MBS_PP1: Sahne"
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "MBS_PP2: ohne Rinde"
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "Kommissionierhinweis: MBS_PP1"
    And the PDF archived for the record identified by "trolleyLoc" has no overlapping text

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty

  @from:cucumber
  Scenario: One manufacturing order without picking instruction keeps the single-order header
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP3         | MOP         | FG2          | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP3    |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | c3         | COMP         | rawWH               | prodWH           | 10 PCE | PP3                 |
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c3                    |
    And after not more than 60s, the DD_Order_Candidates are allocated to DD_OrderLines:
      | DD_Order_Candidate_ID | DD_Order_ID | DD_OrderLine_ID |
      | c3                    | ddo2        | ddol2           |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv2           | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv2           | invLine2           | COMP         | rawLoc       | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv2'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLine2           | hu2     |

    And the mobile user's trolley is set to locator trolleyLoc
    And Start distribution job for dd_order identified by ddo2
      | DD_Order_ID |
      | ddo2        |
    And Pick HU for distribution job line
      | M_HU_ID |
      | hu2     |

    When the Materialbegleitschein is printed for locator trolleyLoc

    Then an AD_Archive exists for the record identified by "trolleyLoc"
    And the PDF archived for the record identified by "trolleyLoc" contains text "MBS_PP3"
    # today's group header: the title is followed directly by the "Produktionsauftrag:" header line.
    # (The QR label prints "Produktionsauftrag:" too, but after the HU rows, so a plain 'contains' could not tell them apart.)
    And in the PDF archived for the record identified by "trolleyLoc", exactly 2 lines appear between text "Materialbegleitschein" and text "Produktionsauftrag:"
    And the visual line containing text "Produktionsauftrag:" in the PDF archived for the record identified by "trolleyLoc" is exactly "Produktionsauftrag: MBS_PP3 Datum: 12.04.2021"
    # no summary line, and an empty note omits the label
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "(Start "
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "Kommissionierhinweis"

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty

  @from:cucumber
  Scenario: One manufacturing order with a picking instruction prints the summary line and hides the single-order header
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP4         | MOP         | FG           | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP4    |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | c4         | COMP         | rawWH               | prodWH           | 10 PCE | PP4                 |
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c4                    |
    And after not more than 60s, the DD_Order_Candidates are allocated to DD_OrderLines:
      | DD_Order_Candidate_ID | DD_Order_ID | DD_OrderLine_ID |
      | c4                    | ddo3        | ddol3           |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv3           | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv3           | invLine3           | COMP         | rawLoc       | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv3'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLine3           | hu3     |

    And the mobile user's trolley is set to locator trolleyLoc
    And Start distribution job for dd_order identified by ddo3
      | DD_Order_ID |
      | ddo3        |
    And Pick HU for distribution job line
      | M_HU_ID |
      | hu3     |

    When the Materialbegleitschein is printed for locator trolleyLoc

    Then an AD_Archive exists for the record identified by "trolleyLoc"
    And the visual line containing text "MBS_PP4 (Start" in the PDF archived for the record identified by "trolleyLoc" is exactly "Produktionsauftrag MBS_PP4 (Start 12.04.2021, 10 Stk): Kommissionierhinweis: Sahne"
    # the QR label page shows the product name only
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "MBS_PP4: Sahne"
    # the old group header is hidden
    # no single-order header between the target lines and the column header
    And in the PDF archived for the record identified by "trolleyLoc", exactly 0 lines appear between text "Ziellagerort" and text "Art. Nr."
    # the 5 lines: the two target lines, the column header (2 lines) and the product row; the summary line sits under the product row
    And in the PDF archived for the record identified by "trolleyLoc", exactly 5 lines appear between text "Materialbegleitschein" and text "MBS_PP4 (Start"
    # the HU row (one line, no picking instruction repeated) follows the summary line directly, then the page footer
    And in the PDF archived for the record identified by "trolleyLoc", exactly 1 lines appear between text "MBS_PP4 (Start" and text "Seite 1"
    And the PDF archived for the record identified by "trolleyLoc" has no overlapping text

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty

  @from:cucumber
  Scenario: Two products on the trolley (one distribution order each) print one summary row per product inside its table
    # each product has its own product planning, which is part of the DD_Order header key, so each component gets its own DD_Order
    Given metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv4           | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv4           | invLine4           | COMP         | rawLoc       | 0       | 20       | PCE          |
      | inv4           | invLine5           | COMP2        | rawLoc       | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv4'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLine4           | hu4     |
      | invLine5           | hu5     |

    # MBS_PP5 needs COMP; MBS_PP6 needs COMP and COMP2
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP5         | MOP         | FG           | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP5    |
      | PP6         | MOP         | FG3          | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP6    |
    And after not more than 60s, PP_Order_BomLines are found
      | Identifier | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | PP5_L1     | PP5         | COMP         | 10 PCE       | CO            |
      | PP6_L1     | PP6         | COMP         | 10 PCE       | CO            |
      | PP6_L2     | PP6         | COMP2        | 10 PCE       | CO            |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | c5         | COMP         | rawWH               | prodWH           | 10 PCE | PP5                 |
      | c6         | COMP         | rawWH               | prodWH           | 10 PCE | PP6                 |
      | c7         | COMP2        | rawWH               | prodWH           | 10 PCE | PP6                 |
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c5                    |
      | c6                    |
      | c7                    |
    And after not more than 60s, the DD_Order_Candidates are allocated to DD_OrderLines:
      | DD_Order_Candidate_ID | DD_Order_ID | DD_OrderLine_ID |
      | c5                    | ddo4        | ddol4_comp      |
      | c6                    | ddo4        | ddol4_comp      |
      | c7                    | ddo5        | ddol5_comp2     |

    And the mobile user's trolley is set to locator trolleyLoc
    And Start distribution job for dd_order identified by ddo4
      | DD_Order_ID |
      | ddo4        |
    And Pick HU for distribution job line
      | M_HU_ID |
      | hu4     |
    And Start distribution job for dd_order identified by ddo5
      | DD_Order_ID |
      | ddo5        |
    And Pick HU for distribution job line
      | M_HU_ID |
      | hu5     |
    And M_HU are validated:
      | M_HU_ID | HUStatus | IsActive | M_Locator_ID |
      | hu4     | A        | true     | trolleyLoc   |
      | hu5     | A        | true     | trolleyLoc   |

    When the Materialbegleitschein is printed for locator trolleyLoc

    Then an AD_Archive exists for the record identified by "trolleyLoc"
    # MBS_COMP: both orders
    And the visual line containing text "MBS_PP5 (Start" in the PDF archived for the record identified by "trolleyLoc" is exactly "Produktionsauftrag MBS_PP5 (Start 12.04.2021, 10 Stk): Kommissionierhinweis: Sahne"
    And the PDF archived for the record identified by "trolleyLoc" contains text "Produktionsauftrag MBS_PP6 (Start 12.04.2021, 10 Stk): Kommissionierhinweis: ohne Rinde"
    # MBS_C2: its own summary row with MBS_PP6's instruction for that component
    And the visual line containing text "Kommissionierhinweis: gerieben" in the PDF archived for the record identified by "trolleyLoc" is exactly "Produktionsauftrag MBS_PP6 (Start 12.04.2021, 10 Stk): Kommissionierhinweis: gerieben"
    # each product's summary row: the product line, directly followed by its order lines (independent of which DD_Order prints first)
    And the visual line containing text "MBS_C2 MBS Komponente 2" in the PDF archived for the record identified by "trolleyLoc" is exactly "MBS_C2 MBS Komponente 2"
    And in the PDF archived for the record identified by "trolleyLoc", exactly 0 lines appear between text "MBS_C2 MBS Komponente 2" and text "Kommissionierhinweis: gerieben"
    And the visual line containing text "MBS_COMP MBS Komponente" in the PDF archived for the record identified by "trolleyLoc" is exactly "MBS_COMP MBS Komponente"
    And in the PDF archived for the record identified by "trolleyLoc", exactly 0 lines appear between text "MBS_COMP MBS Komponente" and text "MBS_PP5 (Start"
    And the PDF archived for the record identified by "trolleyLoc" has no overlapping text

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty

  @from:cucumber
  Scenario: A trolley warehouse name too long for one line wraps and pushes the rows below it down
    # a fixed value and name: the name is too long for the "Ziellager" field, so it always wraps to a second line
    Given metasfresh contains M_Warehouse:
      | M_Warehouse_ID | Value            | Name                                                  | IsInTransit |
      | longTrolleyWH  | MBS_TROLLEY_LONG | MBS Trolley Lager mit einem langen Namen, der umbricht | true        |
    And metasfresh contains M_Locator:
      | Identifier     | M_Warehouse_ID | IsDefault |
      | longTrolleyLoc | longTrolleyWH  | N         |

    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP7         | MOP         | FG2          | 10         | plant         | prodWH         | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP7    |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | c8         | COMP         | rawWH               | prodWH           | 10 PCE | PP7                 |
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | c8                    |
    And after not more than 60s, the DD_Order_Candidates are allocated to DD_OrderLines:
      | DD_Order_Candidate_ID | DD_Order_ID | DD_OrderLine_ID |
      | c8                    | ddo6        | ddol6           |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv5           | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv5           | invLine6           | COMP         | rawLoc       | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv5'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLine6           | hu6     |

    And the mobile user's trolley is set to locator longTrolleyLoc
    And Start distribution job for dd_order identified by ddo6
      | DD_Order_ID |
      | ddo6        |
    And Pick HU for distribution job line
      | M_HU_ID |
      | hu6     |

    When the Materialbegleitschein is printed for locator longTrolleyLoc

    Then an AD_Archive exists for the record identified by "longTrolleyLoc"
    And the PDF archived for the record identified by "longTrolleyLoc" has no overlapping text
    # the second line of the warehouse name is a visual line of its own, not printed into the "Ziellagerort" row
    And the visual line containing text "Namen, der umbricht" in the PDF archived for the record identified by "longTrolleyLoc" is exactly "Namen, der umbricht"
    # group header: the two lines of the warehouse name and the "Ziellagerort" line, then the single-order header
    And in the PDF archived for the record identified by "longTrolleyLoc", exactly 3 lines appear between text "Materialbegleitschein" and text "Produktionsauftrag:"
    And the visual line containing text "Produktionsauftrag:" in the PDF archived for the record identified by "longTrolleyLoc" is exactly "Produktionsauftrag: MBS_PP7 Datum: 12.04.2021"
    # the QR label page (after the first page's footer) has the same three lines above its order header
    And in the PDF archived for the record identified by "longTrolleyLoc", exactly 3 lines appear between text "Seite 1" and text "Produktionsauftrag:"

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty
