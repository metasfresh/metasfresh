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
    # Seed finProd with its own standing AveragePO current cost (10 CHF/PCE) so the receipt
    # posts at that cost: 10 CHF/PCE x 1 PCE = 10 CHF, matching the Fact_Acct assertions below.
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | finInventory   | finInventoryLine   | 2024-03-20   | 540008         | finProd      | 0       | 10       | PCE          | 10        | finHU   |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | finProd      | AveragePO        | 10 CHF           | 10 PCE     |

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
  Scenario: Finished good is received at its current cost price
    # finProd already has its own standing AveragePO cost (25 CHF/PCE), higher than the
    # 1:1 BOM rollup from compProd (10 CHF/PCE). The receipt must post at finProd's own
    # current cost, not at the BOM-rolled-up component cost.
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | finInventory   | finInventoryLine   | 2024-03-20   | 540008         | finProd      | 0       | 10       | PCE          | 25        | finHU   |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | finProd      | AveragePO        | 25 CHF           | 10 PCE     |

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

    # Issue the component to the production order (at its own actual cost, different from finProd's cost)
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
      | receiptCostCollector            | ppOrder                | finProd                 | 1           | CO        |

    And Wait until documents receiptCostCollector are posted

    # finProd's standing current cost must be unchanged by the receipt.
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | acctSchema      | finProd      | AveragePO        | 25 CHF           |

    # The PP_Order_Cost row for the main product must carry the same current cost price.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | CurrentCostPrice |
      | ppOrder                | finProd                 | AveragePO        | MR                    | 25 CHF           |

    # Receipt posts at finProd's own current cost (25), not at the 10 CHF BOM rollup.
    And Fact_Acct records are matching
      | Record_ID            | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr |
      | receiptCostCollector | P_Asset_Acct          | finProd      | 25        | 0         |
      | receiptCostCollector | P_WIP_Acct            | finProd      | 0         | 25        |

  @from:cucumber
  Scenario: Closing a manufacturing order posts its no-resource ActivityControl cost collectors with zero facts
    # The routing of this order resolves every activity to the "no resource" placeholder, which carries no
    # cost product. Closing the order reports the not-yet-started activities and creates one ActivityControl
    # cost collector per activity for that placeholder resource. Each such cost collector must post gracefully
    # (Posted='Y') with zero Fact_Acct rows, instead of failing the posting pipeline.
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | finProd                 | 1          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBomLine                 | ppOrder                | compProd                | 1            | false           | PCE               | CO            |

    # Closing reports the not-yet-started routing activities, creating the no-resource ActivityControl cost collectors.
    And the manufacturing order identified by ppOrder is closed

    Then after not more than 60s, all ActivityControl PP_Cost_Collector for PP_Order ppOrder are posted with no Fact_Acct

  @from:cucumber
  @Id:S30811_TC1
  Scenario: Distribute capitalizes the in-stock cost residual and spills the shipped portion to COGS
    # compProd is consumed at 34 CHF/PCE - pricier than its Background seed - so the component cost
    # exceeds the receipt valuation, leaving a WIP residual for the Distribute action to discharge.
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | compProd     | 34 CHF           |

    # finProd already holds 8 PCE at 30 CHF/PCE, decoupled from the BOM rollup, so the receipt of
    # 10 more units posts at its own standing cost, not the component cost.
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | finInventory   | finInventoryLine    | 2024-03-20   | 540008         | finProd      | 0       | 8        | PCE          | 30        | finHU   |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | finProd      | AveragePO        | 30 CHF           | 8 PCE      |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | finProd                 | 10         | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBomLine                 | ppOrder                | compProd                | 10           | false           | PCE               | CO            |
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

    # Issue the 10 PCE of the component the BOM requires
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event   | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowStep.Identifier | WorkflowStepQRCode.Identifier |
      | IssueTo | mfgWorkflow                | issueActivity               | issueStep               | issueQRCode                   |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    # Receive the 10 PCE of the finished good
    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowLine.Identifier | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | mfgWorkflow                | receiptActivity             | receiptLine             | receivingTargetValues                    |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | issueCostCollector              | ppOrder                | compProd                | 10          | CO        | ComponentIssue    |
      | receiptCostCollector            | ppOrder                | finProd                 | 10          | CO        | MaterialReceipt   |
    And Wait until documents issueCostCollector, receiptCostCollector are posted

    # The component is consumed at 34 CHF/PCE and the finished good is received at its own 30 CHF/PCE:
    # issued 340 - received 300 = a 40 CHF WIP residual for the Distribute action to discharge.
    And Fact_Acct records are matching
      | Record_ID            | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr |
      | issueCostCollector   | P_WIP_Acct            | compProd     | 340       | 0         |
      | issueCostCollector   | P_Asset_Acct          | compProd     | 0         | 340       |
      | receiptCostCollector | P_Asset_Acct          | finProd      | 300       | 0         |
      | receiptCostCollector | P_WIP_Acct            | finProd      | 0         | 300       |

    # Sell 10 of the 18 PCE now on hand (8 pre-existing + 10 manufactured), leaving 8 PCE in stock -
    # the qty the Distribute action will capitalize onto the finished good's current cost.
    And metasfresh contains M_PricingSystems
      | Identifier |
      | salesPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPL    | salesPS             | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPL        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV                | finProd      | 40       | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | salesPS             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | so1_l1         | customer      | 2024-03-26  | SOO         | 540008         | finProd      | 10         | 40    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID |
      | so1_l1         | shipment1_l1   |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | finProd      | AveragePO        | 30 CHF           | 8 PCE      |

    And the manufacturing order identified by ppOrder is distributed

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | finProd                 | 10          | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # Balanced WIP discharge: DR P_Asset (32, capitalized onto finProd's current cost) + DR P_COGS
    # (8, spilled - already shipped) / CR P_WIP (40, the residual computed above).
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr |
      | distributionCostCollector | P_Asset_Acct          | finProd      | 32        | 0         |
      | distributionCostCollector | P_COGS_Acct           | finProd      | 8         | 0         |
      | distributionCostCollector | P_WIP_Acct            | finProd      | 0         | 40        |

    # The capitalized 32 CHF raised finProd's current cost from 30 to 34 CHF/PCE (272 CHF over 8 PCE).
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | finProd      | AveragePO        | 34 CHF           | 8 PCE      |
