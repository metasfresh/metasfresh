@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0160_Manufacturing_Execution
@allure.label.feature:F8030_MobileUI_Manufacturing
@F8030
Feature: mobileUI Manufacturing - Receive finished goods with catch weight, Lot/Best-before and generic attributes

  ## F8030: MobileUI Manufacturing

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    ## Generic editable-attribute masterdata: genericAttr (submitted through the generic Map<AttributeCode, value>
    ## apply path, wired onto the produced HU's own PI below - see the M_HU_PI_Attribute note) and notEditableAttr
    ## (on the product's attribute set but kept OUT of the editable config, so submitting it is rejected). Both are
    ## instance-level so they can belong to the finished good's editable allow-list.
    And metasfresh contains M_Attributes:
      | Identifier      | Value               | Name                   | AttributeValueType | IsInstanceAttribute |
      | genericAttr     | GenericTestAttr     | Generic Test Attr      | N                  | Y                   |
      | notEditableAttr | NotEditableTestAttr | Not Editable Test Attr | N                  | Y                   |

    ## Lot / Best-before / Production date are seeded instance attributes; load them to reference by identifier.
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value             |
      | lotNumberAttr             | Lot-Nummer        |
      | bestBeforeDateAttr        | HU_BestBeforeDate |
      | productionDateAttr        | ProductionDate    |

    ## The finished good's generic editable-attribute allow-list is resolved from its product category's
    ## M_AttributeSet, restricted to instance-level attributes. Wire a set carrying every attribute a scenario
    ## submits through the generic map, and point the finished good at it via its category. notEditableAttr sits on
    ## the set but is deliberately kept OUT of the editable config below, so a receive submitting it is rejected.
    And add M_AttributeSet:
      | Identifier | MandatoryType |
      | mfgAttrSet | N             |
    And add M_AttributeUse:
      | M_AttributeSet_ID | M_Attribute_ID     | SeqNo |
      | mfgAttrSet        | lotNumberAttr      | 10    |
      | mfgAttrSet        | bestBeforeDateAttr | 20    |
      | mfgAttrSet        | productionDateAttr | 30    |
      | mfgAttrSet        | genericAttr        | 40    |
      | mfgAttrSet        | notEditableAttr    | 50    |
    And metasfresh contains M_Product_Categories:
      | Identifier | M_AttributeSet_ID |
      | mfgCat     | mfgAttrSet        |

    And metasfresh contains M_Products:
      | Identifier           | X12DE355 | M_Product_Category_ID |
      | catchWeightFP        | PCE      | mfgCat                |
      | regularComponentProd | PCE      |                       |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | catchWeightFP           | PCE                    | KGM                  | 0.10         | Y                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | TU                 | TU         | TU          | Y         |
      | LU                 | LU         | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx2_catchWeightFP                 | TU                         | catchWeightFP           | 2   | 2000-01-01 |

    ## The "TU" M_HU_PI_Item above is itself the material item (ItemType=MI) - i.e. the actual HU whose
    ## storage carries the generic attribute value at receipt (an HU's attribute storage is generated from
    ## its OWN M_HU_PI_Version's M_HU_PI_Attribute rows, not from the product's M_AttributeSet).
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | TU                 | GenericTestAttr   |
      | TU                 | ProductionDate    |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | line1              | regularComponentProd    | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID                |
      | line1              | regularComponentProdHU |
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

    ## Make the submitted generic attributes editable (Lot / Best-before / Production date + the generic test
    ## attribute); notEditableAttr is deliberately omitted so a receive submitting it is rejected.
    And set MobileUI_MFG_Config editable attributes:
      | M_Attribute_ID     |
      | lotNumberAttr      |
      | bestBeforeDateAttr |
      | productionDateAttr |
      | genericAttr        |


  @from:cucumber
  @Id:S31771
  Scenario: Receive HUs with catch weight, BestBeforeDate & LotNumber
    ## KEEP-cucumber: exercises the DEDICATED CatchWeight/BestBeforeDate/LotNo ReceiveFrom REST fields (backward-compat for non-mobile callers) — the mobile UI only submits via the generic map, so Playwright cannot drive this path.
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
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
      | Event       | CatchWeight | BestBeforeDate | LotNo   | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | 0.5 KGM     | 2025-03-03     | LotNo_1 | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
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

  @from:cucumber
  @Id:S31771
  Scenario: A typed lot number from the mobile receipt event suppresses the BOM's lot-number sequence
    ## KEEP-cucumber: asserts AD_Sequence.CurrentNext stays UNCONSUMED when a Lot is typed — a DB counter with no UI surface.
    And metasfresh contains AD_Sequence:
      | AD_Sequence_ID.Identifier | Name                      | OPT.StartNo |
      | typedLotSequence          | TestTypedLotSuppressedSeq | 1000001     |

    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | manufacturingBOMLine | manufacturingBOM             | regularComponentProd    | 2021-01-02 | 2        |
    And the PP_Product_BOM identified by manufacturingBOM is completed
    And update PP_Product_BOM:
      | PP_Product_BOM_ID.Identifier | OPT.LotNo_Sequence_ID.Identifier |
      | manufacturingBOM             | typedLotSequence                 |

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

    ## Load standard attribute Lot-Nummer so it can be referenced by identifier in the generic Attribute column.
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value      |
      | lotNumberAttr             | Lot-Nummer |
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | Attribute     | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | lotNumberAttr | TypedLot_1     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_PP_Order_Qty
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | Qty |
      | manufacturingOrder     | catchWeightFP           | 2   |

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_LU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | Value      |
      | Produced_LU | Lot-Nummer           | TypedLot_1 |

    And AD_Sequence is validated
      | AD_Sequence_ID.Identifier | CurrentNext |
      | typedLotSequence          | 1000001     |

  @from:cucumber
  @Id:S31771
  Scenario: A blank lot number at the mobile receipt event lets the BOM's lot-number sequence fire (auto-lot)
    ## KEEP-cucumber: asserts the auto-lot AD_Sequence.CurrentNext actually ADVANCES when no Lot is submitted — a DB counter, not visible in the UI.
    And metasfresh contains AD_Sequence:
      | AD_Sequence_ID.Identifier | Name                    | OPT.StartNo |
      | autoLotSequence           | TestAutoLotFiresSeq     | 1000001     |

    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | manufacturingBOMLine | manufacturingBOM             | regularComponentProd    | 2021-01-02 | 2        |
    And the PP_Product_BOM identified by manufacturingBOM is completed
    And update PP_Product_BOM:
      | PP_Product_BOM_ID.Identifier | OPT.LotNo_Sequence_ID.Identifier |
      | manufacturingBOM             | autoLotSequence                  |

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

    ## No Lot submitted at all - neither the dedicated LotNo field nor a Lot-Nummer entry in the generic map.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_PP_Order_Qty
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | Qty |
      | manufacturingOrder     | catchWeightFP           | 2   |

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_LU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | Value   |
      | Produced_LU | Lot-Nummer           | 1000001 |

    And AD_Sequence is validated
      | AD_Sequence_ID.Identifier | CurrentNext |
      | autoLotSequence           | 1000002     |

  @from:cucumber
  @Id:S31771
  Scenario: A receive line producing more than one HU stamps the generic attribute on every produced HU
    ## KEEP-cucumber: a receive line producing multiple HUs is only reachable via the raw receipt event — the mobile dialog always aggregates to a single HU.
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
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
    ## 2 complete TUs (TUx2_catchWeightFP capacity = 2 CU) - a bulk receipt filling two full transport units.
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | manufacturingOrder     | MOP         | catchWeightFP           | 4          | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                | manufacturingProductPlanning          |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | manufacturingBOMLine           | manufacturingOrder     | regularComponentProd    | 8            | false           | PCE               | CO            |
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
      | Event       | ReceiveTo | Attribute   | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | genericAttr | 7.5             | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    ## Multi-HU line: I_PP_Order_Qty has one row per HU, so the single-row validate step is skipped; count + stamped value asserted below.
    And load manufactured HUs for PP_Order:
      | PP_Order_ID        | M_HU_ID                     |
      | manufacturingOrder | Produced_TU_1,Produced_TU_2 |

    Then M_HU_Attribute is validated
      | M_HU_ID       | M_Attribute_ID.Value | ValueNumber |
      | Produced_TU_1 | GenericTestAttr      | 7.5         |
      | Produced_TU_2 | GenericTestAttr      | 7.5         |

  @from:cucumber
  @Id:S31771
  Scenario: A Lot submitted in both the dedicated field and the generic map is rejected
    ## KEEP-cucumber: the dual-channel payload (dedicated field + generic map, same code) is REST-only — the mobile UI never submits both, so this 422 guard is unreachable via Playwright.
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
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

    ## Dedicated LotNo (DedicatedLot) + generic Lot-Nummer map entry (MapLot): same code, both non-blank — the conflict the guard rejects.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | LotNo        | Attribute     | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | DedicatedLot | lotNumberAttr | MapLot         | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    Then the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '422' status code

  @from:cucumber
  @Id:S31771
  Scenario: A Best-before date submitted in both the dedicated field and the generic map is rejected
    ## KEEP-cucumber: dual-channel HU_BestBeforeDate (dedicated field + generic map) is REST-only — the mobile UI never submits both; 422 guard unreachable via Playwright.
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
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

    ## Dedicated BestBeforeDate (2025-03-03) + generic HU_BestBeforeDate map entry (2025-09-09): same code, both non-blank — the conflict the guard rejects.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | BestBeforeDate | Attribute          | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | 2025-03-03     | bestBeforeDateAttr | 2025-09-09     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    Then the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '422' status code

  @from:cucumber
  @Id:S31771
  Scenario: A Production date submitted in both the dedicated field and the generic map is rejected
    ## KEEP-cucumber: dual-channel ProductionDate (dedicated field + generic map) is REST-only — the mobile UI never submits both; 422 guard unreachable via Playwright.
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
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

    ## Dedicated ProductionDate (2025-01-10) + generic ProductionDate map entry (2025-11-20): same code, both non-blank — the conflict the guard rejects.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | ReceiveTo | ProductionDate | Attribute          | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | 2025-01-10     | productionDateAttr | 2025-11-20     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    Then the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '422' status code

  @from:cucumber
  @Id:S31771
  Scenario: A generic attribute not in the configured editable list is rejected
    ## KEEP-cucumber: the backend 422 reject of a not-editable attribute is REST-only — the mobile UI never offers a non-configured attribute (Playwright proves non-render separately).
    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID  | PP_Product_BOMVersions_ID |
      | manufacturingBOM | catchWeightFP | manufacturingBOMVersion   |
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

    ## notEditableAttr is submitted through the generic map but is not on the editable allow-list -> reject.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | Attribute       | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | notEditableAttr | someValue      | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    Then the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '422' status code
