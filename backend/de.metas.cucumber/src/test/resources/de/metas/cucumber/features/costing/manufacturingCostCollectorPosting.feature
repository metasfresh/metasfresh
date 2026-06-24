@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@F1500
@ghActions:run_on_executor6
Feature: Manufacturing cost collector posting - component issue vs material receipt signs
## F1500: Costing

  # A manufacturing order backflushes one BOM component and receives the finished good.
  # The component-issue cost collector must post DR P_WIP_Acct / CR P_Asset_Acct (inventory down, WIP up).
  # The finished-good material-receipt cost collector must post DR P_Asset_Acct / CR P_WIP_Acct (inventory up, WIP down).
  #
  # For a CLOSED AveragePO order all production cost must be recovered into the finished good, so the
  # P_WIP_Acct nets to zero across the order's cost collectors. This holds even when the component's
  # actual cost at issue time differs from the planned BOM-rollup frozen at order completion (e.g. the
  # component price rose between production planning and the actual material issue).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | A             |
    And cost elements for material costing methods AveragePO are active

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | finProd    | PCE      |
      | compProd   | PCE      |

    # Seed the component with a non-zero AveragePO current cost (10 CHF/PCE) and create its stock HU.
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | compInventory  | compInventoryLine  | 2024-03-20   | 540008         | compProd     | 0       | 100      | PCE          | 10        | compHU  |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | compProd     | AveragePO        | 10 CHF           | 100 PCE    |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | finPackLU             | finPackLU   |
      | finPackTU             | finPackTU   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | finPackLUVersion              | finPackLU             | finPackLUVersion | LU          | Y         |
      | finPackTUVersion              | finPackTU             | finPackTUVersion | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | finPackLUItem              | finPackLUVersion              | 1   | HU       | finPackTU                        |
      | finPackTUItem              | finPackTUVersion              | 1   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | finProdItem                        | finPackTUItem              | finProd                 | 1   | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom        | finProd                 | 2021-01-02 | bomVersion                           |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bomLine    | bom                          | compProd                | 2021-01-02 | 1        |
    And the PP_Product_BOM identified by bom is completed

    And load AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                   |
      | mobileWorkflow            | mobileUI_workflow_test |
    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan   | mobileWorkflow                | finProd                 | bomVersion                               | false        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

  @from:cucumber
  Scenario: Issue a component and receive the finished good, then check Fact_Acct signs
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | finProd                 | 1          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBomLine                 | ppOrder                | compProd                | 1            | false           | PCE               | CO            |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder                |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract manufacturing step and issueTo HU manufacturing candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowStep.Identifier | WorkflowStepQRCode.Identifier |
      | mfgWorkflow                | issueActivity               | issueStep               | issueQRCode                   |
    And process response and extract manufacturing line and receiving target values:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowLine.Identifier | WorkflowReceivingTargetValues.Identifier |
      | mfgWorkflow                | receiptActivity             | receiptLine             | receivingTargetValues                    |

    # Issue the component to the production order
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event   | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowStep.Identifier | WorkflowStepQRCode.Identifier |
      | IssueTo | mfgWorkflow                | issueActivity               | issueStep               | issueQRCode                   |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    # Receive the finished good
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowLine.Identifier | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | mfgWorkflow                | receiptActivity             | receiptLine             | receivingTargetValues                    |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    # Load both cost collectors so they can be referenced as Fact_Acct Record_ID
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus |
      | issueCostCollector              | ppOrder                | compProd                | 1           | CO        |
      | receiptCostCollector            | ppOrder                | finProd                 | 1           | CO        |

    And Wait until documents issueCostCollector, receiptCostCollector are posted

    # Component issue: DR P_WIP_Acct / CR P_Asset_Acct (inventory down, WIP up).
    # Material receipt of finished good: DR P_Asset_Acct / CR P_WIP_Acct (inventory up, WIP down).
    And Fact_Acct records are matching
      | Record_ID            | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr |
      | issueCostCollector   | P_WIP_Acct            | compProd     | 10        | 0         |
      | issueCostCollector   | P_Asset_Acct          | compProd     | 0         | 10        |
      | receiptCostCollector | P_Asset_Acct          | finProd      | 10        | 0         |
      | receiptCostCollector | P_WIP_Acct            | finProd      | 0         | 10        |

  @from:cucumber
  Scenario: Component cost rises after order completion - all production cost is still recovered so WIP clears
    # Complete the order while the component still costs 10 CHF/PCE. This freezes the finished good's
    # planned BOM-rollup price at 10 (PP_Order_Cost), with its own standing cost cleared.
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | finProd                 | 1          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBomLine                 | ppOrder                | compProd                | 1            | false           | PCE               | CO            |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    # The component price rises to 90 CHF/PCE BEFORE the material is issued, modelling a real-world
    # price increase between production planning and the actual material issue. (Using a direct cost
    # update rather than a second receipt keeps the single component stock HU, as the mobile issue
    # step expects exactly one HU to issue from.)
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | compProd     | 90 CHF           |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | compProd     | AveragePO        | 90 CHF           | 100 PCE    |

    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder                |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract manufacturing step and issueTo HU manufacturing candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowStep.Identifier | WorkflowStepQRCode.Identifier |
      | mfgWorkflow                | issueActivity               | issueStep               | issueQRCode                   |
    And process response and extract manufacturing line and receiving target values:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowLine.Identifier | WorkflowReceivingTargetValues.Identifier |
      | mfgWorkflow                | receiptActivity             | receiptLine             | receivingTargetValues                    |

    # Issue the component to the production order (now valued at the risen cost of 90 CHF)
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event   | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowStep.Identifier | WorkflowStepQRCode.Identifier |
      | IssueTo | mfgWorkflow                | issueActivity               | issueStep               | issueQRCode                   |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    # Receive the finished good
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowLine.Identifier | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | mfgWorkflow                | receiptActivity             | receiptLine             | receivingTargetValues                    |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus |
      | issueCostCollector              | ppOrder                | compProd                | 1           | CO        |
      | receiptCostCollector            | ppOrder                | finProd                 | 1           | CO        |

    And Wait until documents issueCostCollector, receiptCostCollector are posted

    # The whole production cost (90 CHF of issued component) must be recovered into the finished good,
    # so the work-in-process account nets to zero across the order's cost collectors.
    And Fact_Acct records balances for documents issueCostCollector, receiptCostCollector are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
