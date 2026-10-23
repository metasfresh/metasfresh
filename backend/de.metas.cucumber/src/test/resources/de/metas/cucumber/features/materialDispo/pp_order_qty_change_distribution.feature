@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
@ghActions:run_on_executor6
Feature: Distribution for a manufacturing order's component follows the order's quantity change
## F5100: Material Disposition
  - Changing a manufacturing order's quantity deletes and re-creates its BOM lines.
  - The planned distribution of a component must then supply the re-created BOM line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier   |
      | finishedGood |
      | component    |

    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID  | IsInTransit | PP_Plant_ID |
      | inTransit       | true        |             |
      | rawMaterials_WH | false       |             |
      | production_WH   | false       | plant       |

    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | networkLine                   | network                   | production_WH  | rawMaterials_WH      | shipper      |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom        | finishedGood | bomVersions               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | bomLine    | bom               | component    | 1        |
    And the PP_Product_BOM identified by bom is completed

    # Only the component is planned; the manufacturing order itself is created manually.
    And metasfresh contains PP_Product_Plannings
      | Identifier        | M_Product_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | S_Resource_ID | IsCreatePlan |
      | componentPlanning | component    | network                   | production_WH  | plant         | N            |

    # Stock of the component in the distribution's source warehouse
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID  | MovementDate |
      | inventory  | rawMaterials_WH | 2021-04-12   |
    And metasfresh contains M_InventoriesLines:
      | Identifier    | M_Inventory_ID | M_Product_ID | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine | inventory      | component    | PCE          | 100      | 0       |
    And the inventory identified by inventory is completed




# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_10
  Scenario: manually created PP_Order; change its qty right away and complete it -> the DD_Order_Candidates supply the re-created BOM line with the new qty
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |

    # The qty is changed right after creation, before material dispo has finished planning the distribution.
    # Changing the qty deletes the BOM line of qty 10 and creates a new one
    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 20         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    And the manufacturing order identified by ppOrder is completed

    # Required component qty: 1 (BOM QtyBatch) x 20 (new PP_Order qty) - 0 (no component stock in production_WH) = 20,
    # distributed by two DD_Order_Candidates of 10 each, both supplying the new BOM line.
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier        | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate1 | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | newBOMLine                  |
      | ddOrderCandidate2 | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | newBOMLine                  |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | DateProjected_LocalTimeZone | Qty | ATP | M_Warehouse_ID  | PP_Order_ID | PP_Order_BOMLine_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | stock       | INVENTORY_UP      |                           | component    |                      | 2021-04-12T00:00:00         | 100 | 100 | rawMaterials_WH | -           | -                   | -                   | -                           |
      | supply      | SUPPLY            | PRODUCTION                | finishedGood | 2021-04-16T21:00:00Z |                             | 20  | 20  | production_WH   | ppOrder     | -                   | -                   | -                           |
      | demand      | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z |                             | -20 | -20 | production_WH   | ppOrder     | newBOMLine          | -                   | -                           |
      | distSupply1 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z |                             | 10  | -10 | production_WH   | -           | -                   | ppOrder             | newBOMLine                  |
      | distSupply2 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z |                             | 10  | 0   | production_WH   | -           | -                   | ppOrder             | newBOMLine                  |
      | distDemand1 | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z |                             | -10 | 90  | rawMaterials_WH | -           | -                   | ppOrder             | newBOMLine                  |
      | distDemand2 | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z |                             | -10 | 80  | rawMaterials_WH | -           | -                   | ppOrder             | newBOMLine                  |
