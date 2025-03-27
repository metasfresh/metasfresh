@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Picking - Pick mixed lines

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier           | X12DE355 |
      | catchWeightFP        | PCE      |
      | regularComponentProd | PCE      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | catchWeightFP           | PCE                    | KGM                  | 0.10         | Y                        |

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
      | TUx2_catchWeightFP                 | TU                         | catchWeightFP           | 2   | 2000-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | regularComponentProd    | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID                |
      | line1              | regularComponentProdHU |
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Receive HUs with catch weight, BestBeforeDate & LotNumber
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | manufacturingBOM | catchWeightFP           | 2021-01-02 | manufacturingBOMVersion              |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | manufacturingBOMLine | manufacturingBOM             | regularComponentProd    | 2021-01-02 | 2        |
    And the PP_Product_BOM identified by manufacturingBOM is completed
    And load AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                   |
      | mobileWorkflow            | mobileUI_workflow_test |
    And metasfresh contains PP_Product_Plannings
      | Identifier                   | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | manufacturingProductPlanning | mobileWorkflow                | catchWeightFP           | manufacturingBOMVersion                  | false        |
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | manufacturingOrder     | MOP         | catchWeightFP           | 2          | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                | manufacturingProductPlanning          |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | manufacturingBOMLine           | manufacturingOrder     | regularComponentProd    | 4            | false           | PCE               | CO            |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract manufacturing line and receiving target values:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |

    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | CatchWeightUOMSymbol | CatchWeight | BestBeforeDate | LotNo   | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | kg                   | 0.5         | 2025-03-03     | LotNo_1 | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code


    And validate I_PP_Order_Qty
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | Qty |
      | manufacturingOrder     | catchWeightFP           | 2   |

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_LU |

    And M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueNumber | ValueDate  | Value   |
      | Produced_LU | WeightNet            | 0.5         |            |         |
      | Produced_LU | HU_BestBeforeDate    |             | 2025-03-03 |         |
      | Produced_LU | Lot-Nummer           |             |            | LotNo_1 |
