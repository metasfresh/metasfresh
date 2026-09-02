@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0160_Manufacturing_Execution
@allure.label.feature:F8030_MobileUI_Manufacturing
@F8030
Feature: mobileUI Picking - Pick mixed lines

  ## F8030: MobileUI Manufacturing

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    ## Generic editable-attribute masterdata: a plain optional attribute, a M_Attribute.IsMandatory='Y'
    ## one, plus one Date-type and one List-type attribute (the mobile app also offers these two value
    ## types besides Number), so a receive event can submit values for them through the generic
    ## Map<AttributeCode, value> apply path (wired onto the produced HU's own PI below, see the
    ## M_HU_PI_Attribute note).
    And metasfresh contains M_Attributes:
      | Identifier      | Value             | Name                 | AttributeValueType | OPT.IsMandatory |
      | genericAttr     | GenericTestAttr   | Generic Test Attr    | N                   |                 |
      | mandatoryAttr   | MandatoryTestAttr | Mandatory Test Attr  | N                   | Y               |
      | genericDateAttr | GenericDateAttr   | Generic Date Attr    | D                   |                 |
      | genericListAttr | GenericListAttr   | Generic List Attr    | L                   |                 |
    And metasfresh contains M_AttributeValues:
      | Identifier           | M_Attribute_ID  | Value | Name | IsNullFieldValue |
      | genericListValueBlue | genericListAttr | Blue  | Blue | false            |

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
    ## MandatoryTestAttr is deliberately NOT assigned here: the HU engine generates every M_HU_PI_Attribute
    ## at HU-creation time with a null seed value and rejects a null seed for an M_Attribute.IsMandatory='Y'
    ## attribute - a pre-existing HU-engine constraint, unrelated to (and unaffected by) this receive-apply
    ## feature. The "mandatory left empty still completes" scenario instead proves the new apply code never
    ## consults IsMandatory: the value is simply not applicable to this HU (guarded by hasAttribute), exactly
    ## like any other not-applicable attribute, and the receive completes regardless.
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | TU                 | GenericTestAttr    |
      | TU                 | GenericDateAttr    |
      | TU                 | GenericListAttr    |
      | TU                 | ProductionDate     |

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

    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: Receive HUs with catch weight, BestBeforeDate & LotNumber
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

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A typed lot number from the mobile receipt event suppresses the BOM's lot-number sequence
    ## The BOM has a LotNo_Sequence_ID configured, but the mobile receipt event carries its own typed Lot.
    ## The typed Lot arrives through the GENERIC attributes map - exactly how the real mobile frontend now
    ## submits it (it dropped the dedicated LotNo field in favour of the generic editable-attributes section).
    ## ReceiveGoodsCommand must route the Lot-Nummer value out of that map to the IPPOrderReceiptHUProducer
    ## setter, so AbstractPPOrderReceiptHUProducer stamps the typed value directly and never touches the
    ## sequence (updateReceivedHUs consults the sequence only in the `else` branch, i.e. when no typed
    ## lotNumber was provided). Guard: the produced HU carries the typed lot, and the sequence's CurrentNext
    ## stays UNCONSUMED. Without that routing the map value bypasses the producer, so the auto-lot gate fires
    ## and consumes the sequence (CurrentNext advances) even though an explicit Lot was typed - the regression
    ## this scenario pins.
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

    ## Lot-Nummer is a standard attribute (not created in this feature's Background), so load it into the
    ## step-def data to reference it by identifier in the generic Attribute column.
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

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A blank lot number at the mobile receipt event lets the BOM's lot-number sequence fire (auto-lot)
    ## The other half of AC6 (F8041): with a LotNo_Sequence_ID configured and NO Lot submitted at all, the
    ## producer's auto-lot gate fires - it draws the next value from the sequence, stamps it on the produced
    ## HU, and advances CurrentNext. A plain AD_Sequence (no CustomSequenceNoProvider) hands out its counter
    ## verbatim (see pporder/lotNumberSequenceProvider.feature), so StartNo=1000001 -> Lot "1000001" and
    ## CurrentNext -> 1000002. This regression guard must stay green regardless of the producer-routing fix:
    ## that fix only stops a TYPED lot (via the generic map) from bypassing the producer; it must not disturb
    ## the blank-lot auto-lot path.
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

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A generic attribute value submitted with the receive is stamped on the produced HU
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

    ## ReceiveTo=TU: catchWeightFP's "TU" M_HU_PI_Item is itself the material item (ItemType=MI, see
    ## Background) - i.e. the TU IS the HU that carries the product's attribute-set storage. An LU-wrapped
    ## receive would collect only the LU (a pure packaging wrapper, no product storage of its own) and the
    ## generic attribute would never apply there.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | ReceiveTo | Attribute   | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | genericAttr | 12.5           | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_TU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueNumber |
      | Produced_TU | GenericTestAttr      | 12.5        |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: An empty generic attribute value submitted with the receive is not stamped, and the receive completes
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

    ## The Attribute is submitted with no AttributeValue - a real empty-field-left-blank submission by the
    ## operator (as opposed to the attribute not being submitted at all). ReceiveTo=TU: see the note in the
    ## previous scenario - the TU is the HU that actually carries catchWeightFP's attribute storage.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | ReceiveTo | Attribute   | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | genericAttr | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_TU |

    ## The seeded default for a never-touched Number attribute is 0 (the storage's untouched initial
    ## value), not a NULL DB column - the assertion below proves the empty submission left it at that
    ## untouched default rather than being overwritten by a blank/zero value from the apply code.
    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueNumber |
      | Produced_TU | GenericTestAttr      | 0           |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A mandatory generic attribute left empty does not block the receive
    ## v1 does not enforce M_Attribute.IsMandatory on the mobile receive apply path - only the auto-lot
    ## sequence stays special-cased. Guard: the receive completes and nothing is stamped for it.
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

    ## MandatoryTestAttr is not part of Produced_TU's own attribute storage (see the Background note) -
    ## the point here is that the apply code never special-cases IsMandatory, so submitting a value for it
    ## behaves exactly like any other not-applicable attribute (silently skipped) and does not block the
    ## receive - proven by the order's qty actually landing (document truly completed, not just acked).
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | ReceiveTo | Attribute     | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | mandatoryAttr | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    Then validate I_PP_Order_Qty
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | Qty |
      | manufacturingOrder     | catchWeightFP           | 2   |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A receive line producing more than one HU stamps the generic attribute on every produced HU
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

    ## (I_PP_Order_Qty carries one row per produced HU here, so the single-row "validate I_PP_Order_Qty"
    ## step doesn't apply to a multi-HU line - the produced-HU count + stamped value are asserted below.)
    And load manufactured HUs for PP_Order:
      | PP_Order_ID        | M_HU_ID                     |
      | manufacturingOrder | Produced_TU_1,Produced_TU_2 |

    Then M_HU_Attribute is validated
      | M_HU_ID       | M_Attribute_ID.Value | ValueNumber |
      | Produced_TU_1 | GenericTestAttr      | 7.5         |
      | Produced_TU_2 | GenericTestAttr      | 7.5         |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A generic attribute value submitted with the default LU-wrap receive is stamped on the produced TU inside the LU
    ## No ReceiveTo column - defaults to aggregate-to-LU (the feature's own Background sets up catchWeightFP's
    ## "TU" M_HU_PI_Item as the material item, see the Background note above). On this default path,
    ## receivedHUs collects only the newly-created LU wrapper, which carries no product/attribute storage
    ## of its own - the value must be applied recursively down the HU hierarchy to the material-carrying TU
    ## for it to land anywhere. Regression guard for the bug where the value was silently dropped on this
    ## (the DEFAULT) receiving path.
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
      | Event       | Attribute   | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | genericAttr | 12.5            | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_LU |

    ## Navigate one level down the produced hierarchy: the LU's single child is the material-carrying TU
    ## (see the Background note - the "TU" M_HU_PI_Item is itself the material item, ItemType=MI).
    And validate M_HUs:
      | M_HU_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | Produced_LU_TU     | Produced_LU                |

    Then M_HU_Attribute is validated
      | M_HU_ID        | M_Attribute_ID.Value | ValueNumber |
      | Produced_LU_TU | GenericTestAttr      | 12.5        |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A Date-type generic attribute value submitted with the receive is stamped on the produced HU
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
      | Event       | ReceiveTo | Attribute       | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | genericDateAttr | 2025-04-15      | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_TU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueDate  |
      | Produced_TU | GenericDateAttr      | 2025-04-15 |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A List-type generic attribute value submitted with the receive is stamped on the produced HU
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
      | Event       | ReceiveTo | Attribute       | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | genericListAttr | Blue            | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_TU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | Value |
      | Produced_TU | GenericListAttr      | Blue  |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A Production Date submitted through the generic map is routed to the producer and stamped on the produced HU
    ## ProductionDate is producer-managed (routed to the IPPOrderReceiptHUProducer setter alongside Lot /
    ## Best-before), so submitting it through the generic map must land the OPERATOR'S value, not the default
    ## movement date the producer would otherwise stamp. Guard for the ProductionDate branch of the map
    ## extraction (submitted 2025-06-15, distinct from the Background movement date 2024-03-26).
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

    ## ProductionDate is a standard attribute (not created in this feature's Background); load it into the
    ## step-def data so it can be referenced by identifier in the generic Attribute column.
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value          |
      | productionDateAttr        | ProductionDate |
    ## ReceiveTo=TU: the TU is the material HU that carries ProductionDate storage (see Background).
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | ReceiveTo | Attribute          | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | productionDateAttr | 2025-06-15     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_TU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueDate  |
      | Produced_TU | ProductionDate       | 2025-06-15 |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A Lot submitted in both the dedicated field and the generic map - the generic map value wins
    ## The generic map is the mobile frontend's channel; the dedicated ReceiveFrom.lotNo field is only a
    ## backwards-compatible fallback for non-mobile callers. When a caller populates BOTH for the same code,
    ## ReceiveGoodsCommand's coalesce takes the (non-blank) map value. Characterizes that precedence rule -
    ## this is NOT a bug-discriminating test (both the pre- and post-fix paths would land the map value here,
    ## because the dedicated field carries a non-blank Lot too); it pins the intended coalesce direction.
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

    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value      |
      | lotNumberAttr             | Lot-Nummer |
    ## Both the dedicated LotNo field (DedicatedLot_lost) and a generic Lot-Nummer map entry (MapLot_wins) are
    ## submitted; the map value must be the one stamped on the produced HU.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | LotNo             | Attribute     | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | DedicatedLot_lost | lotNumberAttr | MapLot_wins    | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_LU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | Value       |
      | Produced_LU | Lot-Nummer           | MapLot_wins |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A Best-before date submitted in both the dedicated field and the generic map - the generic map value wins
    ## Same precedence rule as the Lot scenario, for the HU_BestBeforeDate producer-managed code: the generic
    ## map is the mobile frontend's channel; the dedicated ReceiveFrom.bestBeforeDate field is only a
    ## backwards-compatible fallback for non-mobile callers. When a caller populates BOTH for the same code,
    ## ReceiveGoodsCommand's coalesce takes the (non-blank) map value. Characterizes that precedence rule - NOT
    ## a bug-discriminating test (both the pre- and post-fix paths would land the map value here, because the
    ## dedicated field carries a non-blank date too); it pins the intended coalesce direction.
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

    ## HU_BestBeforeDate is a standard attribute (not created in this feature's Background), so load it into the
    ## step-def data to reference it by identifier in the generic Attribute column.
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value             |
      | bestBeforeDateAttr        | HU_BestBeforeDate |
    ## Both the dedicated BestBeforeDate field (2025-03-03, lost) and a generic HU_BestBeforeDate map entry
    ## (2025-09-09, wins) are submitted; the map value must be the one stamped on the produced HU.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | BestBeforeDate | Attribute          | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | 2025-03-03     | bestBeforeDateAttr | 2025-09-09     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_LU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueDate  |
      | Produced_LU | HU_BestBeforeDate    | 2025-09-09 |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771
  Scenario: A Production date submitted in both the dedicated field and the generic map - the generic map value wins
    ## Same precedence rule as the Lot scenario, for the ProductionDate producer-managed code: the generic map
    ## is the mobile frontend's channel; the dedicated ReceiveFrom.productionDate field is only a
    ## backwards-compatible fallback for non-mobile callers. When a caller populates BOTH for the same code,
    ## ReceiveGoodsCommand's coalesce takes the (non-blank) map value. Characterizes that precedence rule - NOT
    ## a bug-discriminating test (both the pre- and post-fix paths would land the map value here, because the
    ## dedicated field carries a non-blank date too); it pins the intended coalesce direction.
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

    ## ProductionDate is a standard attribute (not created in this feature's Background); load it into the
    ## step-def data so it can be referenced by identifier in the generic Attribute column.
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value          |
      | productionDateAttr        | ProductionDate |
    ## ReceiveTo=TU: the TU is the material HU that carries ProductionDate storage (see Background - the LU's PI
    ## has no ProductionDate). Both the dedicated ProductionDate field (2025-01-10, lost) and a generic
    ## ProductionDate map entry (2025-11-20, wins) are submitted; the map value must be the one stamped.
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | ReceiveTo | ProductionDate | Attribute          | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | TU        | 2025-01-10     | productionDateAttr | 2025-11-20     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And load manufactured HU for PP_Order:
      | PP_Order_ID        | M_HU_ID     |
      | manufacturingOrder | Produced_TU |

    Then M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | ValueDate  |
      | Produced_TU | ProductionDate       | 2025-11-20 |
