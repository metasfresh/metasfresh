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
    # DD_Orders are aggregated by the PP_Order reference, unless a scenario says otherwise.
    And set sys config boolean value true for sys config DDOrderAggregation.header.bySalesOrderId
    And set sys config boolean value true for sys config DDOrderAggregation.header.byPPOrderRef
    And set sys config boolean value true for sys config DDOrderAggregation.line.bySalesOrderLineId
    And AD_Note table is reset
    And the existing AD_Issue records are remembered
    And load AD_Message:
      | Identifier           | Value                                                                                   |
      | noteMsg              | de.metas.material.dispo.SupplyRequiredDecreaseFailed                                    |
      | processedRejectedMsg | org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED |

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

    # Required component qty: 1 (BOM QtyBatch) x 20 (new PP_Order qty) - 0 (no component stock in production_WH) = 20.
    # How many DD_Order_Candidates distribute it is not pinned; all of them supply the new BOM line.
    Then after not more than 60s, all DD_Order_Candidates of product component totalling 20 supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | DateProjected_LocalTimeZone | Qty | ATP | M_Warehouse_ID  | PP_Order_ID | PP_Order_BOMLine_ID |
      | stock      | INVENTORY_UP      |                           | component    |                      | 2021-04-12T00:00:00         | 100 | 100 | rawMaterials_WH | -           | -                   |
      | supply     | SUPPLY            | PRODUCTION                | finishedGood | 2021-04-16T21:00:00Z |                             | 20  | 20  | production_WH   | ppOrder     | -                   |
      | demand     | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z |                             | -20 | -20 | production_WH   | ppOrder     | newBOMLine          |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_15
  Scenario: manually created PP_Order; decrease its qty right away and complete it -> every DD_Order_Candidate and distribution MD_Candidate supplies the re-created BOM line
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |

    # The qty is decreased before material dispo has finished planning the distribution. The resulting qty is not asserted.
    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    And the manufacturing order identified by ppOrder is completed
    And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_20
  Scenario: manually created PP_Order; decrease its qty after planning -> the DD_Order_Candidate is reduced and supplies the re-created BOM line
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | oldBOMLine                  |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | newBOMLine                  |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   | ppOrder             | newBOMLine                  |
      | distDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH | ppOrder             | newBOMLine                  |
    And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_30
  Scenario: manually created PP_Order; increase its qty after planning -> the DD_Order_Candidates total the new qty and supply the re-created BOM line
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | oldBOMLine                  |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 20         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    And after not more than 60s, all DD_Order_Candidates of product component totalling 20 supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_40
  Scenario: PP_Order with a processed DD_Order_Candidate; decrease its qty -> the change succeeds, the processed candidate and DD_Order stay, the user is notified
    When update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | Y            |
    And create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | Y         | ppOrder             | newBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID | DD_OrderLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | newBOMLine                  | ddOrderLine     |
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | DD_Order_Candidate_ID | DD_OrderLine_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distCandSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | production_WH   | ddOrderCandidate      |                 | ppOrder             | newBOMLine                  |
      | distCandDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | rawMaterials_WH | ddOrderCandidate      |                 | ppOrder             | newBOMLine                  |
      | distLineSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH   |                       | ddOrderLine     | ppOrder             | newBOMLine                  |
      | distLineDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH |                       | ddOrderLine     | ppOrder             | newBOMLine                  |
    And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID | TextContains |
      | noteMsg       | ppOrder   | 10           |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_50
  Scenario: PP_Order with a processed DD_Order_Candidate; increase its qty -> a second processed candidate supplies the re-created BOM line
    When update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | Y            |
    And create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 20         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | newBOMLine                  |
      | secondCandidate  | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | newBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier    | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID | DD_OrderLine_ID   |
      | ddOrder       | ddOrderCandidate      | ppOrder             | newBOMLine                  | firstDDOrderLine  |
      | secondDDOrder | secondCandidate       | ppOrder             | newBOMLine                  | secondDDOrderLine |
    And after not more than 60s, MD_Candidates are found
      | Identifier       | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | DD_Order_Candidate_ID | DD_OrderLine_ID   | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | firstCandSupply  | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | production_WH   | ddOrderCandidate      |                   | ppOrder             | newBOMLine                  |
      | firstCandDemand  | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | rawMaterials_WH | ddOrderCandidate      |                   | ppOrder             | newBOMLine                  |
      | firstLineSupply  | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   |                       | firstDDOrderLine  | ppOrder             | newBOMLine                  |
      | firstLineDemand  | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH |                       | firstDDOrderLine  | ppOrder             | newBOMLine                  |
      | secondCandSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | production_WH   | secondCandidate       |                   | ppOrder             | newBOMLine                  |
      | secondCandDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | rawMaterials_WH | secondCandidate       |                   | ppOrder             | newBOMLine                  |
      | secondLineSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   |                       | secondDDOrderLine | ppOrder             | newBOMLine                  |
      | secondLineDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH |                       | secondDDOrderLine | ppOrder             | newBOMLine                  |
    And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_55
  Scenario: PP_Order with a processed DD_Order_Candidate; change its DateStartSchedule -> still rejected, candidate and DD_Order unchanged
    When update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | Y            |
    And create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |

    When update PP_Order expecting error:
      | PP_Order_ID | DateStartSchedule    | AD_Message_ID        |
      | ppOrder     | 2021-04-17T21:00:00Z | processedRejectedMsg |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_60
  Scenario: user flow: PP_Order created, DD_Order_Candidate planned, qty decreased, PP_Order completed -> the DD_Order_Candidate supplies the re-created BOM line
    # DDOrderAggregation.header.byPPOrderRef=N: DD_Orders are aggregated without the PP_Order reference.
    Given set sys config boolean value false for sys config DDOrderAggregation.header.byPPOrderRef
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | oldBOMLine                  |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    And the manufacturing order identified by ppOrder is completed
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | newBOMLine                  |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   | ppOrder             | newBOMLine                  |
      | distDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH | ppOrder             | newBOMLine                  |
    And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered
    And set sys config boolean value true for sys config DDOrderAggregation.header.byPPOrderRef



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_65
  Scenario: user flow: PP_Order created, DD_Order_Candidate planned, qty increased, PP_Order completed -> the DD_Order_Candidates total the new qty on the re-created BOM line
    # DDOrderAggregation.header.byPPOrderRef=N: DD_Orders are aggregated without the PP_Order reference.
    Given set sys config boolean value false for sys config DDOrderAggregation.header.byPPOrderRef
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | oldBOMLine                  |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 20         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    And the manufacturing order identified by ppOrder is completed
    And after not more than 60s, all DD_Order_Candidates of product component totalling 20 supply PP_Order_BOMLine newBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine newBOMLine
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered
    And set sys config boolean value true for sys config DDOrderAggregation.header.byPPOrderRef



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_52
  Scenario: PP_Order with a processed and an unprocessed DD_Order_Candidate; decrease its qty below the processed candidate -> the unprocessed one is reduced to 0, the processed one stays, the user is notified
    When update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | Y            |
    And create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |

    # From now on, new DD_Order_Candidates stay unprocessed.
    And update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | N            |
    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 20         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | increasedBOMLine    | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier           | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate     | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | increasedBOMLine            |
      | unprocessedCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | N         | ppOrder             | increasedBOMLine            |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 5          |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | reducedBOMLine      | ppOrder     | component    | 5 PCE        | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      # the unprocessed candidate is reduced to 0 and kept (same as the sales-order decrease, distribution.feature S0171.300)
      | Identifier           | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate     | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | reducedBOMLine              |
      | unprocessedCandidate | component    | rawMaterials_WH     | production_WH    | 0 PCE  | N         | ppOrder             | reducedBOMLine              |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | reducedBOMLine              |
    And after not more than 60s, all DD_Order_Candidates of product component supply PP_Order_BOMLine reducedBOMLine
    And after not more than 60s, all DISTRIBUTION MD_Candidates of product component supply PP_Order_BOMLine reducedBOMLine
    And after not more than 60s, AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID | TextContains |
      | noteMsg       | ppOrder   | 5            |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_70
  Scenario: two manually created PP_Orders of the same component; decrease the second one -> only its DD_Order_Candidate is reduced
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrderA    | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
      | ppOrderB    | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | bomLineA            | ppOrderA    | component    | 10 PCE       | CO            |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | bomLineB            | ppOrderB    | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | candidateA | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrderA            | bomLineA                    |
      | candidateB | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrderB            | bomLineB                    |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrderB    | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLineB         | ppOrderB    | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | candidateA | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrderA            | bomLineA                    |
      | candidateB | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrderB            | newBOMLineB                 |
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distASupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   | ppOrderA            | bomLineA                    |
      | distADemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH | ppOrderA            | bomLineA                    |
      | distBSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   | ppOrderB            | newBOMLineB                 |
      | distBDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH | ppOrderB            | newBOMLineB                 |
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrderB  |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_72
  Scenario: two PP_Orders whose processed DD_Order_Candidates share one DD_Order; decrease the second one -> the DD_Order and the other candidate stay, the user is notified
    # DDOrderAggregation.header.byPPOrderRef=N: DD_Orders are aggregated without the PP_Order reference.
    Given set sys config boolean value false for sys config DDOrderAggregation.header.byPPOrderRef
    And create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrderA    | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
      | ppOrderB    | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | bomLineA            | ppOrderA    | component    | 10 PCE       | CO            |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | bomLineB            | ppOrderB    | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | candidateA | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrderA            | bomLineA                    |
      | candidateB | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrderB            | bomLineB                    |
    # Generated together, so that one DD_Order serves both candidates.
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | candidateA            |
      | candidateB            |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID |
      | ddOrder    | candidateA            |
      | ddOrder    | candidateB            |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | DD_OrderLine_QtyEntered_Total |
      | ddOrder    | candidateA            | 30                            |
    And after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | candidateA | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrderA            | bomLineA                    |
      | candidateB | component    | rawMaterials_WH     | production_WH    | 20 PCE | Y         | ppOrderB            | bomLineB                    |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrderB    | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLineB         | ppOrderB    | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | candidateA | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrderA            | bomLineA                    |
      | candidateB | component    | rawMaterials_WH     | production_WH    | 20 PCE | Y         | ppOrderB            | newBOMLineB                 |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID |
      | ddOrder    | candidateA            |
      | ddOrder    | candidateB            |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | DD_OrderLine_QtyEntered_Total |
      | ddOrder    | candidateA            | 30                            |
    And after not more than 60s, AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID | TextContains |
      | noteMsg       | ppOrderB  | 10           |
    And no AD_Issue was created since the existing AD_Issue records were remembered
    And set sys config boolean value true for sys config DDOrderAggregation.header.byPPOrderRef



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_75
  Scenario: PP_Order created from a PP_Order_Candidate with two distributed components; decrease its qty -> each component's DD_Order_Candidate is reduced by its own decrease
    Given metasfresh contains M_Products:
      | Identifier   |
      | multiProduct |
      | component2   |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps                 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | multiProduct | 10.0     | PCE      |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | multiBom   | multiProduct | multiBomVersions          |
    And metasfresh contains PP_Product_BOMLines
      | Identifier    | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | multiBomLine1 | multiBom          | component    | 1        |
      | multiBomLine2 | multiBom          | component2   | 1        |
    And the PP_Product_BOM identified by multiBom is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier         | M_Product_ID | PP_Product_BOMVersions_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | S_Resource_ID | IsCreatePlan |
      | multiPlanning      | multiProduct | multiBomVersions          |                           | production_WH  | plant         | Y            |
      | component2Planning | component2   |                           | network                   | production_WH  | plant         | N            |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID  | MovementDate |
      | inventory2 | rawMaterials_WH | 2021-04-12   |
    And metasfresh contains M_InventoriesLines:
      | Identifier     | M_Inventory_ID | M_Product_ID | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine2 | inventory2     | component2   | PCE          | 100      | 0       |
    And the inventory identified by inventory2 is completed

    # The PP_Order is created from the sales order's PP_Order_Candidate; its DD_Order_Candidates have no BOM line.
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | salesOrder | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | salesOrderLine | salesOrder | multiProduct | 20         |
    And the order identified by salesOrder is completed
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | DatePromised         |
      | ppOrder    | multiProduct | multiBom          | multiPlanning          | plant         | 20 PCE     | 20         | 2021-04-16T21:00:00Z |
    And after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | candidate1 | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             |
      | candidate2 | component2   | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             |
    And after not more than 60s, MD_Candidates are found
      | Identifier    | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | DD_Order_Candidate_ID | DD_OrderLine_ID | Forward_PP_Order_ID |
      | before1Supply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH   |                       |                 | ppOrder             |
      | before1Demand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH |                       |                 | ppOrder             |
      | before2Supply | SUPPLY            | DISTRIBUTION              | component2   | 2021-04-16T21:00:00Z | 20  | *   | production_WH   |                       |                 | ppOrder             |
      | before2Demand | DEMAND            | DISTRIBUTION              | component2   | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH |                       |                 | ppOrder             |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID |
      | candidate1 | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             |
      | candidate2 | component2   | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             |
    And after not more than 60s, MD_Candidates are found
      | Identifier    | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | DD_Order_Candidate_ID | DD_OrderLine_ID | Forward_PP_Order_ID |
      | before1Supply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   |                       |                 | ppOrder             |
      | before1Demand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH |                       |                 | ppOrder             |
      | before2Supply | SUPPLY            | DISTRIBUTION              | component2   | 2021-04-16T21:00:00Z | 10  | *   | production_WH   |                       |                 | ppOrder             |
      | before2Demand | DEMAND            | DISTRIBUTION              | component2   | 2021-04-16T21:00:00Z | -10 | *   | rawMaterials_WH |                       |                 | ppOrder             |
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_80
  Scenario: manually created PP_Order; void it after planning -> its unprocessed DD_Order_Candidates are removed
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | oldBOMLine                  |

    When the PP_Order ppOrder is voided
    Then no DD_Order_Candidates found for product component
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    # Not asserted: voiding the PP_Order makes dispo log an AD_Issue "No Candidates found for PP_Order_ID=...".



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_85
  Scenario: PP_Order with a processed DD_Order_Candidate; void it -> the void is rejected
    When update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | Y            |
    And create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 10         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |

    When the PP_Order ppOrder is voided expecting rejection with AD_Message processedRejectedMsg
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 10 PCE | Y         | ppOrder             | oldBOMLine                  |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | oldBOMLine                  |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_47
  Scenario: PP_Order created from a PP_Order_Candidate with a processed DD_Order_Candidate; decrease its qty -> the processed candidate and DD_Order stay, the PP_Order's user is notified
    When update existing PP_Product_Plannings
      | Identifier        | IsCreatePlan |
      | componentPlanning | Y            |
    And metasfresh contains PP_Product_Plannings
      | Identifier           | M_Product_ID | PP_Product_BOMVersions_ID | M_Warehouse_ID | S_Resource_ID | IsCreatePlan |
      | finishedGoodPlanning | finishedGood | bomVersions               | production_WH  | plant         | Y            |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps                 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | finishedGood | 10.0     | PCE      |

    # The PP_Order is created from the sales order's PP_Order_Candidate; its DD_Order_Candidates have no BOM line.
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | salesOrder | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | salesOrderLine | salesOrder | finishedGood | 20         |
    And the order identified by salesOrder is completed
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | DatePromised         |
      | ppOrder    | finishedGood | bom               | finishedGoodPlanning   | plant         | 20 PCE     | 20         | 2021-04-16T21:00:00Z |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | Y         | ppOrder             |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | DD_OrderLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | ddOrderLine     |
    And after not more than 60s, MD_Candidates are found
      | Identifier       | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | DD_Order_Candidate_ID | DD_OrderLine_ID | Forward_PP_Order_ID |
      | beforeCandSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | production_WH   | ddOrderCandidate      |                 | ppOrder             |
      | beforeCandDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | rawMaterials_WH | ddOrderCandidate      |                 | ppOrder             |
      | beforeLineSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH   |                       | ddOrderLine     | ppOrder             |
      | beforeLineDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH |                       | ddOrderLine     | ppOrder             |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | Y         | ppOrder             |
    And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
      | Identifier | DD_Order_Candidate_ID | Forward_PP_Order_ID | DD_OrderLine_ID |
      | ddOrder    | ddOrderCandidate      | ppOrder             | ddOrderLine     |
    And after not more than 60s, MD_Candidates are found
      | Identifier       | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | DD_Order_Candidate_ID | DD_OrderLine_ID | Forward_PP_Order_ID |
      | beforeCandSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | production_WH   | ddOrderCandidate      |                 | ppOrder             |
      | beforeCandDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | *   | rawMaterials_WH | ddOrderCandidate      |                 | ppOrder             |
      | beforeLineSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH   |                       | ddOrderLine     | ppOrder             |
      | beforeLineDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH |                       | ddOrderLine     | ppOrder             |
    And after not more than 60s, AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID | TextContains |
      | noteMsg       | ppOrder   | 10           |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_45
  Scenario: manually created PP_Order whose component is fully in stock at the production warehouse; decrease its qty -> no DD_Order_Candidate, the user is notified
    Given metasfresh contains M_Inventories:
      | Identifier          | M_Warehouse_ID | MovementDate |
      | inventoryProduction | production_WH  | 2021-04-12   |
    And metasfresh contains M_InventoriesLines:
      | Identifier              | M_Inventory_ID      | M_Product_ID | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLineProduction | inventoryProduction | component    | PCE          | 100      | 0       |
    And the inventory identified by inventoryProduction is completed
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID | PP_Order_ID |
      | demand     | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -20 | *   | production_WH  | ppOrder     |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID | PP_Order_ID | PP_Order_BOMLine_ID |
      | demand     | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | *   | production_WH  | ppOrder     | newBOMLine          |
    And after not more than 60s, AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID | TextContains |
      | noteMsg       | ppOrder   | 10           |
    And no DD_Order_Candidates found for product component
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_90
  Scenario: manually created PP_Order; delete it after planning -> its DD_Order_Candidates and MD_Candidates are removed
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | finishedGood | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine          | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier       | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | oldBOMLine                  |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | PP_Order_ID | PP_Order_BOMLine_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ppSupply   | SUPPLY            | PRODUCTION                | finishedGood | 2021-04-16T21:00:00Z | 20  | *   | production_WH   | ppOrder     | -                   | -                   | -                           |
      | ppDemand   | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -20 | *   | production_WH   | ppOrder     | oldBOMLine          | -                   | -                           |
      | distSupply | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH   | -           | -                   | ppOrder             | oldBOMLine                  |
      | distDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH | -           | -                   | ppOrder             | oldBOMLine                  |

    When the record reference of ppOrder is remembered as deletedPPOrder
    And the PP_Order ppOrder is deleted
    Then no DD_Order_Candidates found for product component
    And after not more than 60s, metasfresh has no MD_Candidate for identifier ppSupply
    And after not more than 60s, metasfresh has no MD_Candidate for identifier ppDemand
    And after not more than 60s, metasfresh has no MD_Candidate for identifier distSupply
    And after not more than 60s, metasfresh has no MD_Candidate for identifier distDemand
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID      |
      | noteMsg       | deletedPPOrder |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_95
  Scenario: PP_Order whose BOM has the same component twice; decrease its qty -> each DD_Order_Candidate follows the re-created BOM line of its own BOM line
    # The same component is in the BOM twice, with different qtys per finished good; the BOM is completed after both lines exist.
    Given metasfresh contains M_Products:
      | Identifier |
      | twiceGood  |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bomTwice   | twiceGood    | bomVersionsTwice          |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | IsAdditionalLine |
      | bomLineOne | bomTwice          | component    | 1        | N                |
      | bomLineTwo | bomTwice          | component    | 2        | Y                |
    And the PP_Product_BOM identified by bomTwice is completed
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | twiceGood    | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine1         | ppOrder     | component    | 20 PCE       | CO            |
      | oldBOMLine2         | ppOrder     | component    | 40 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier        | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate1 | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | oldBOMLine1                 |
      | ddOrderCandidate2 | component    | rawMaterials_WH     | production_WH    | 40 PCE | ppOrder             | oldBOMLine2                 |

    When update PP_Order:
      | PP_Order_ID | QtyEntered |
      | ppOrder     | 10         |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine1         | ppOrder     | component    | 10 PCE       | CO            |
      | newBOMLine2         | ppOrder     | component    | 20 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier        | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate1 | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | newBOMLine1                 |
      | ddOrderCandidate2 | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | newBOMLine2                 |
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distSupply1 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH  | ppOrder             | newBOMLine1                 |
      | distSupply2 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH  | ppOrder             | newBOMLine2                 |
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered



# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
# #############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @allure.label.feature:F5113_Materialbegleitschein_Distribution_Order_Document
  @Id:S28916_100
  Scenario: PP_Order whose second BOM line is no longer valid at the new start date; decrease its qty and move its start -> that BOM line has no successor and its DD_Order_Candidate is removed, the other follows
    # The same component is in the BOM twice; the second line is valid only until 2021-04-20.
    Given metasfresh contains M_Products:
      | Identifier |
      | removeGood |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bomRemove  | removeGood   | bomVersionsRemove         |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | IsAdditionalLine | ValidTo    |
      | bomLineOne | bomRemove         | component    | 1        | N                |            |
      | bomLineTwo | bomRemove         | component    | 2        | Y                | 2021-04-20 |
    And the PP_Product_BOM identified by bomRemove is completed
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | ppOrder     | MOP         | removeGood   | 20         | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | oldBOMLine1         | ppOrder     | component    | 20 PCE       | CO            |
      | oldBOMLine2         | ppOrder     | component    | 40 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier        | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate1 | component    | rawMaterials_WH     | production_WH    | 20 PCE | ppOrder             | oldBOMLine1                 |
      | ddOrderCandidate2 | component    | rawMaterials_WH     | production_WH    | 40 PCE | ppOrder             | oldBOMLine2                 |
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distSupply1 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 20  | *   | production_WH   | ppOrder             | oldBOMLine1                 |
      | distDemand1 | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -20 | *   | rawMaterials_WH | ppOrder             | oldBOMLine1                 |
      | distSupply2 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 40  | *   | production_WH   | ppOrder             | oldBOMLine2                 |
      | distDemand2 | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -40 | *   | rawMaterials_WH | ppOrder             | oldBOMLine2                 |

    # A later demand for the component on the same warehouse, created before the change
    When create PP_Order:
      | PP_Order_ID | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | M_Warehouse_ID | DateOrdered          | DatePromised         | DateStartSchedule    | completeDocument |
      | otherOrder  | MOP         | finishedGood | 5          | plant         | production_WH  | 2021-04-11T08:00:00Z | 2021-04-25T21:00:00Z | 2021-04-25T21:00:00Z | N                |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | otherBOMLine        | otherOrder  | component    | 5 PCE        | CO            |
    And after not more than 60s, MD_Candidates are found
      | Identifier   | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | otherDemand  | DEMAND            | DISTRIBUTION              | component    | 2021-04-25T21:00:00Z | -5  | 35  | rawMaterials_WH | otherOrder          | otherBOMLine                |

    # Re-creating the BOM lines at the later start date leaves out the second BOM line
    When update PP_Order:
      | PP_Order_ID | QtyEntered | DatePromised         | DateStartSchedule    |
      | ppOrder     | 10         | 2021-05-05T21:00:00Z | 2021-04-30T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | newBOMLine          | ppOrder     | component    | 10 PCE       | CO            |
    Then after not more than 60s, the DD_Order_Candidate table has only the following records
      | Identifier        | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | ddOrderCandidate1 | component    | rawMaterials_WH     | production_WH    | 10 PCE | ppOrder             | newBOMLine                  |
      | ddOrderCandidate3 | component    | rawMaterials_WH     | production_WH    | 5 PCE  | otherOrder          | otherBOMLine                |
    And after not more than 60s, metasfresh has no MD_Candidate for identifier distSupply2
    And after not more than 60s, metasfresh has no MD_Candidate for identifier distDemand2
    # The distribution candidates keep their DateProjected: following the BOM lines covers the quantity, the start date move is not followed.
    # ATP of the later demand 85 = 100 in stock - 10 - 5: the deleted demand of 40 is no longer counted.
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID | Forward_PP_Order_BOMLine_ID |
      | distSupply1 | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | *   | production_WH   | ppOrder             | newBOMLine                  |
      | distDemand1 | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | 90  | rawMaterials_WH | ppOrder             | newBOMLine                  |
      | otherDemand | DEMAND            | DISTRIBUTION              | component    | 2021-04-25T21:00:00Z | -5  | 85  | rawMaterials_WH | otherOrder          | otherBOMLine                |
    And after not more than 5s, no AD_Note records exist for the referenced record:
      | AD_Message_ID | Record_ID |
      | noteMsg       | ppOrder   |
    And no AD_Issue was created since the existing AD_Issue records were remembered
