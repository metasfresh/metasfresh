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
    And for this scenario only, set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
    # the PDF steps read the archive back out of the database
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # a fixed time makes the dispo's DateOrdered equal for all candidates (it is part of the aggregation key)
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    # one DD_Order line may bundle the demand of several manufacturing orders
    And for this scenario only, set sys config boolean value false for sys config DDOrderAggregation.header.byPPOrderRef

    And metasfresh contains M_Products:
      | Identifier |
      | COMP       |
      | FG         |
      | FG2        |

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
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | PickingInstruction |
      | boml_fg    | bom_fg            | COMP         | 1        | Sahne              |
      | boml_fg2   | bom_fg2           | COMP         | 1        |                    |
    And the PP_Product_BOM identified by bom_fg is completed
    And the PP_Product_BOM identified by bom_fg2 is completed

    # IsCreatePlan=N: the DD_Order_Candidates are enqueued by the scenario, not by the dispo
    And metasfresh contains PP_Product_Plannings
      | Identifier    | M_Product_ID | PP_Product_BOMVersions_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | S_Resource_ID | IsCreatePlan |
      | fg_planning   | FG           | bomVersions_fg            |                           | prodWH         | plant         | N            |
      | fg2_planning  | FG2          | bomVersions_fg2           |                           | prodWH         | plant         | N            |
      | comp_planning | COMP         |                           | N1                        | prodWH         | plant         | N            |

  @from:cucumber
  Scenario: Bundled line for two manufacturing orders with different picking instructions prints one summary line per order
    # one DD_Order line may bundle the demand of several sales order lines
    Given for this scenario only, set sys config boolean value false for sys config DDOrderAggregation.line.bySalesOrderLineId
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | FG           | 10.0     | PCE      |
      | plv                    | FG2          | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps                 |
    # the start date is a midday instant, so 12.04.2021 prints whatever the JVM default time zone is
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO         | true    | customer      | 2021-04-11  | 2021-04-12T10:00:00Z | prodWH         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | SO_L1      | SO         | FG           | 10         |
      | SO_L2      | SO         | FG2          | 10         |
    When the order identified by SO is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | Processed |
      | oc1        | FG           | bom_fg            | fg_planning            | plant         | 10 PCE     | false     |
      | oc2        | FG2          | bom_fg2           | fg2_planning           | plant         | 10 PCE     | false     |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc1                   |
      | oc2                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | DatePromised         |
      | PP1        | FG           | bom_fg            | fg_planning            | plant         | 10 PCE     | 10         | 2021-04-12T10:00:00Z |
      | PP2        | FG2          | bom_fg2           | fg2_planning           | plant         | 10 PCE     | 10         | 2021-04-12T10:00:00Z |
    # lets the PDF assertions name the order numbers
    And update PP_Order:
      | PP_Order_ID.Identifier | OPT.DocumentNo |
      | PP1                    | MBS_PP1        |
      | PP2                    | MBS_PP2        |
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

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv1                      | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv1                      | invLine1                      | COMP                    | rawLoc       | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'inv1'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine1                      | hu1                |

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
    # the QR label lines
    And the PDF archived for the record identified by "trolleyLoc" contains text "MBS_PP1: Sahne"
    And the PDF archived for the record identified by "trolleyLoc" contains text "MBS_PP2: ohne Rinde"
    # the HU row does not repeat the order list
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "Kommissionierhinweis: MBS_PP1"
    And the visual line containing text "MBS_PP1: Sahne" in the PDF archived for the record identified by "trolleyLoc" is exactly "MBS_PP1: Sahne"
    And the PDF archived for the record identified by "trolleyLoc" has no overlapping text

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty

  @from:cucumber
  Scenario: One manufacturing order without picking instruction keeps the single-order header
    When create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP3                    | MOP         | FG2                     | 10         | plant                    | prodWH                        | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP3    |
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
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv2                      | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv2                      | invLine2                      | COMP                    | rawLoc       | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv2'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine2                      | hu2                |

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
    # no summary line, and an empty note omits the label
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "(Start "
    And the PDF archived for the record identified by "trolleyLoc" does not contain text "Kommissionierhinweis"

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty

  @from:cucumber
  Scenario: One manufacturing order with a picking instruction prints the summary line and hides the single-order header
    When create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument | DocumentNo |
      | PP4                    | MOP         | FG                      | 10         | plant                    | prodWH                        | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | 2021-04-12T10:00:00Z | Y                | MBS_PP4    |
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
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv3                      | 2021-04-11   | rawWH          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv3                      | invLine3                      | COMP                    | rawLoc       | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv3'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine3                      | hu3                |

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
    And the PDF archived for the record identified by "trolleyLoc" contains text "MBS_PP4: Sahne"
    # the old group header is hidden: the first "Produktionsauftrag:" is the QR label's, after the HU rows
    And in the PDF archived for the record identified by "trolleyLoc", at least 3 lines appear between text "Materialbegleitschein" and text "Produktionsauftrag:"

    And the mobile user's trolley is cleared
    And the mobile user's trolley is empty
