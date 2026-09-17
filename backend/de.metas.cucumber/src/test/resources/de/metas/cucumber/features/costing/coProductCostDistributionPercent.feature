@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@F1500
@ghActions:run_on_executor6
Feature: Co-product valuation via cost-distribution percent
## F1500: Costing

  # A production run turns one input into a premium main product plus a low-value secondary
  # output ("Randstuecke"), modelled as a co-product BOM line. When the co-product's product
  # carries a manual M_Product.CoProductCostDistributionPercent (p), the co-product is relieved
  # at p x SigmaInput (the co-product's share of the order's whole input cost pool) and the main
  # product is relieved by the remainder. A blank percent leaves the co-product's relief at ZERO
  # (opt-in; no fallback to qty-distribution - this replaces the retired fixed-price feature this
  # file used to test, whose blank case fell back to qty-distribution). The co-product's own
  # receipt still books at ITS OWN current cost (unrelated to the carve), so when that current
  # cost overvalues the co-product relative to its carve, the CostDifferenceDistribution (CC-170)
  # true-up at order close write-DOWNs the co-product's own inventory (Cr P_Asset / Dr P_WIP) -
  # the customer case this feature now asserts. Quantities are in PCE standing in for the
  # customer's kg. The order is planned for 6 finished goods (BOM ratios input 5 / co-product -1,
  # so the pool is exact at 30 x 15 = 450 and the co-product's own output at 6); the main product's
  # ACTUAL reported yield (24) is entered at receipt time, independently of the plan - carve and
  # write-down math below never depend on the main product's own qty.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | M             |
    And cost elements for material costing methods MovingAverageInvoice are active

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | mainProd   | PCE      |
      | rawProduct | PCE      |
      | coProd     | PCE      |

    # Seed the raw component with a 15 CHF/PCE current cost and create its stock HU (30 PCE).
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inputInventory | inputInventoryLine | 2024-03-20   | 540008         | rawProduct   | 0       | 30       | PCE          | 15        | inputHU |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | rawProduct   | 15 CHF           |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | rawProduct   | MovingAverageInvoice | 15 CHF           | 30 PCE     |

    # Packing instructions shared by the main-product and co-product manufacturing receipts.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name   |
      | packLU                | packLU |
      | packTU                | packTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name          | HU_UnitType | IsCurrent |
      | packLUVersion                 | packLU                | packLUVersion | LU          | Y         |
      | packTUVersion                 | packTU                | packTUVersion | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | packLUItem                 | packLUVersion                 | 1   | HU       | packTU                           |
      | packTUItem                 | packTUVersion                 | 1   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | mainProdItem                       | packTUItem                 | mainProd                | 100 | 2022-01-01 |
      | coProdItem                         | packTUItem                 | coProd                  | 100 | 2022-01-01 |

    # BOM of the main product "Bloecke": one input Component (issued) and one Co-product output "Randstuecke".
    # A co-product BOM line carries a NEGATIVE QtyBOM (it is an output). Per finished good the ratios are
    # input 5 and co-product -1, so a 6-PCE planned order requires 30 PCE input and yields 6 PCE co-product.
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom        | mainProd                | 2021-01-02 | bomVersion                           |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | inputLine  | bom                          | rawProduct              | 2021-01-02 | 5        | CO            |
      | coProdLine | bom                          | coProd                  | 2021-01-02 | -1       | CP            |
    And the PP_Product_BOM identified by bom is completed

    And load AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                   |
      | mobileWorkflow            | mobileUI_workflow_test |
    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan   | mobileWorkflow                | mainProd                | bomVersion                               | false        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

  @from:cucumber
  @Id:S29488_TC2
  Scenario: The co-product's own current cost overvalues its carve share, written down at order close
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    # Randstuecke's own current cost - overvalued vs. its carve.
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # Co-product carve p x 450 = 48, main the remainder 402, conserved to 450.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | CurrentCostPrice | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 15 CHF           | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 9.9 CHF          | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 0 CHF            | 402                |

    # Divergence by design: leg B books the co-product's OWN current cost x qty (9.9 x 6 = 59.4).
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | coProd       | MovingAverageInvoice | 9.9 CHF          | 6 PCE      |

    # Distribute the order so the main product's residual capitalizes out of WIP.
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # Write-down on the co-product's OWN legs (Cr P_Asset / Dr P_WIP 11.4), asserted
    # explicitly - not just the whole-order balance below, which would pass even if this leg were dropped.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 11.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 11.4      | 0         | 0 PCE |

    # The whole manufacturing order balances and WIP nets to 0.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC3
  Scenario: Blank cost-distribution percent leaves the co-product's carve at zero
    # Randstuecke (coProd) never gets a CoProductCostDistributionPercent, nor a prior current cost - a
    # genuinely new, never-priced co-product. Its own receipt therefore capitalizes at ZERO (exactly like
    # the main product's own receipt), and the whole 450 CHF input pool ends up on the finished good.
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # No carve at all: the co-product's post-calculation amount is zero and the finished good absorbs the
    # whole 450 CHF pool - no throw, no NPE.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 0                  |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 450                |

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The co-product's carve is zero, so it draws no CC-170 leg at all - only the finished good's own
    # residual (the whole 450 CHF) is discharged here.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 450       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 450       | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC10
  Scenario: Unset percent on the customer case writes the co-product fully down to zero
    # Same customer BOM and the same 9.9 CHF/PCE own current cost as the write-down case, but
    # CoProductCostDistributionPercent is left unset (NULL) - no update step at all. The co-product's own
    # receipt still books at its own current cost (59.4), and the CC-170 true-up must write it all the way
    # down to the zero carve without throwing an NPE (the load-bearing blank-⇒-0 guard).
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 0                  |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 450                |

    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |

    # The zero-carve true-up writes the co-product's own inventory ALL THE WAY DOWN to zero - no NPE from
    # the blank-⇒-0 guard - while the finished good absorbs the full 450 CHF pool.
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 450       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 450       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 59.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 59.4      | 0         | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC11
  Scenario: Explicit zero percent behaves identically to a blank percent
    # Same as the unset-percent case, but CoProductCostDistributionPercent is explicitly set to 0 instead of
    # being left NULL - the outcome must be byte-for-byte identical.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 0                                |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 0                  |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 450                |

    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 450       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 450       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 59.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 59.4      | 0         | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC4
  Scenario: Co-products' percent sum exceeding 100% is rejected before any amount is carved
    # Two co-products (own BOM, own order - the shared Background's single 'coProd' cannot carry two
    # independent percents at once) each get a percent that is legal ON ITS OWN ([0, 100], so the
    # M_Product per-product data-entry guard accepts both individually) but whose SUM (60% + 50% = 110%)
    # exceeds the whole pool. The order completes and its cost collectors get created normally (the
    # per-order Sigma-p guard fires at POST-CALC, which runs when each collector is COSTED/POSTED, not at
    # document completion) - but posting each co-product receipt is rejected loudly, naming the offending
    # products and the sum, before any amount is carved from the pool - so no negative finished-good
    # amount is ever persisted.
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 |
      | mainProd4   | PCE      |
      | rawProduct4 | PCE      |
      | coProdA4    | PCE      |
      | coProdB4    | PCE      |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID  | M_InventoryLine_ID  | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID  |
      | inputInventory4 | inputInventoryLine4 | 2024-03-20   | 540008         | rawProduct4  | 0       | 30       | PCE          | 15        | inputHU4 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | rawProduct4  | MovingAverageInvoice | 15 CHF           | 30 PCE     |

    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProdA4                | 60                               |
      | coProdB4                | 50                               |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | mainProd4Item                      | packTUItem                 | mainProd4               | 100 | 2022-01-01 |
      | coProdA4Item                       | packTUItem                 | coProdA4                | 100 | 2022-01-01 |
      | coProdB4Item                       | packTUItem                 | coProdB4                | 100 | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom4       | mainProd4               | 2021-01-02 | bomVersion4                          |
    And metasfresh contains PP_Product_BOMLines
      | Identifier   | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | inputLine4   | bom4                         | rawProduct4             | 2021-01-02 | 5        | CO            |
      | coProdALine4 | bom4                         | coProdA4                | 2021-01-02 | -1       | CP            |
      | coProdBLine4 | bom4                         | coProdB4                | 2021-01-02 | -1       | CP            |
    And the PP_Product_BOM identified by bom4 is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan4  | mobileWorkflow                | mainProd4               | bomVersion4                              | false        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder4               | MOP         | mainProd4               | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan4                             |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine4                  | ppOrder4               | rawProduct4             | 30           | false           | PCE               | CO            |
      | coProdABomLine4                | ppOrder4               | coProdA4                | -6           | false           | PCE               | CP            |
      | coProdBBomLine4                | ppOrder4               | coProdB4                | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU4 is issued whole to PP_Order_BOMLine inputBomLine4

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder4    | mainHU4            | N               | 0     | N               | 1     | N               | 24          | mainProd4Item                      |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder4    | coProdABomLine4     | coHUA4             | N               | 0     | N               | 1     | N               | 6           | coProdA4Item                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder4    | coProdBBomLine4     | coHUB4             | N               | 0     | N               | 1     | N               | 6           | coProdB4Item                       |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder4               |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollectorA4        | ppOrder4               | coProdA4                | -6          | CO        | MixVariance       |
      | coReceiptCostCollectorB4        | ppOrder4               | coProdB4                | -6          | CO        | MixVariance       |
    And the PP_Cost_Collector identified by coReceiptCostCollectorA4 was rejected at posting with error containing exceeds 100% for product(s)
    And the PP_Cost_Collector identified by coReceiptCostCollectorB4 was rejected at posting with error containing exceeds 100% for product(s)

    # The guard must NAME the offending products AND the sum - not just the boilerplate above, which would
    # still pass even if the interpolated sum or product names broke. Assert both actually appear: the
    # interpolated sum (110%, from 60% + 50% set above) and BOTH offending products' own names
    # (M_Product_StepDef auto-names an M_Product "<Identifier>_<timestamp>" when no Name/Value column is
    # given, so "coProdA4_" / "coProdB4_" are the deterministic, non-timestamp parts of those names).
    And the PP_Cost_Collector identified by coReceiptCostCollectorA4 was rejected at posting with error containing sum of 110% exceeds 100%
    And the PP_Cost_Collector identified by coReceiptCostCollectorA4 was rejected at posting with error containing product(s): coProdA4_
    And the PP_Cost_Collector identified by coReceiptCostCollectorA4 was rejected at posting with error containing coProdB4_

    # No negative finished-good amount was persisted - the guard rejects in PERCENT-space before any
    # amount is carved, so the finished good's post-calculation amount is untouched (still its initial 0).
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder4               | mainProd4               | MovingAverageInvoice | MR                    | 0                  |

  @from:cucumber
  @Id:S29488_TC9
  Scenario: Co-product over-receipt does not raise its total claim on the input pool
    # Randstuecke gets a 30% share of the 450 CHF pool (carve 135, finished good 315). The co-product is
    # deliberately received (8 PCE) in a GREATER quantity than the finished good (2 PCE) - the extra units
    # only dilute the co-product's per-unit value, they never raise its 135 CHF TOTAL claim on the pool.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 30                               |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 2           | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 8           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -8          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # Total carve 135 = 30% x 450, regardless of the 8-vs-2 qty imbalance; finished good keeps 315 (>= 0).
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 135                |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 315                |

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The co-product's own leg capitalizes its full 135 CHF total claim (never priced before, all 8 PCE on
    # hand); the finished good's own leg capitalizes the 315 CHF remainder. Neither is negative.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 315       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 315       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 135       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 0         | 135       | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC12
  Scenario: A co-product received in two partial receipts is trued up once, not per receipt
    # Same customer case as the write-down scenario (carve 48, own current cost 9.9 CHF/PCE), but the
    # co-product's 6 PCE arrive in TWO 3-PCE partial receipts instead of one. Each receipt books
    # current-cost x received-qty (9.9 x 3 = 29.7), so the two receipts sum to 59.4 - NOT 96 (which the old
    # full-share-per-receipt bug would have booked, 48 CHF on EACH of the two receipts). A single CC-170
    # true-up then writes the accumulated 59.4 down to the 48 CHF carve once.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU1              | N               | 0     | N               | 1     | N               | 3           | coProdItem                         |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU2              | N               | 0     | N               | 1     | N               | 3           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    # The two co-product receipts are indistinguishable by product/type/status alone (same product, same
    # collector type, same status) - wait on the (uniquely identifiable) main-product receipt instead, since
    # both are completed and posted together, synchronously, by the same "complete planning" call.
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | mainReceiptCostCollector        | ppOrder                | mainProd                | 24          | CO        | MaterialReceipt   |
    And Wait until documents mainReceiptCostCollector are posted

    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 402                |

    # A single true-up writes the accumulated 59.4 DOWN to the 48 CHF carve once - the old (N-1) x share
    # over-relief (a phantom 48 CHF WIP credit) does not occur. The write-down below is EXACTLY 11.4
    # (48 - 59.4) - had the old full-share-per-receipt bug booked 48 CHF on EACH of the two receipts (96
    # total), the required write-down would instead be -48 (48 - 96), a different number entirely; the
    # 11.4 figure is only reachable if the two partial receipts summed to 59.4, not 96.
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 11.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 11.4      | 0         | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC6
  Scenario: Part of the co-product's yield is already shipped - the write-down splits by on-hand share
    # Same customer case as the write-down scenario (carve 48, own current cost 9.9 CHF/PCE, all 6 PCE
    # received), but 2 of the 6 PCE are sold BEFORE the CC-170 adjustment - only 4 PCE are still on hand.
    # The 11.4 CHF write-down must then split proportionally: the still-in-stock share (4/6) credits
    # P_Asset, the already-shipped share (2/6) credits P_COGS, and P_WIP takes the whole 11.4 debit.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |

    # Sell 2 of the 6 PCE on hand, leaving 4 PCE for the CC-170 adjustment to capitalize into.
    And metasfresh contains M_PricingSystems
      | Identifier |
      | salesPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPL    | salesPS            | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPL        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | coProd       | 20       | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | salesPS            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | so1_l1         | customer      | 2024-03-26  | SOO         | 540008         | coProd       | 2          | 20    |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier |
      | sched1     | so1_l1                    |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | shipment1             | sched1                           |
    And Wait until documents shipment1 are posted

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The 11.4 CHF write-down splits by on-hand share (4/6 credits P_Asset 7.6, 2/6 credits P_COGS 3.8) -
    # asserted on the co-product's OWN legs, not just the whole-order balance below.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 7.6       | 0 PCE |
      | distributionCostCollector | P_COGS_Acct           | coProd       | 0         | 3.8       | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 11.4      | 0         | 0 PCE |

    # The whole order's WIP still nets to 0; P_Asset alone no longer does once part of the write-down is
    # expensed to P_COGS instead - that split is what the per-product legs above already proved correct.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |

  @from:cucumber
  @Id:S29488_TC5
  Scenario: Two co-products plus a genuine by-product - each keeps its own carve, the by-product stays zero
    # A richer BOM: two priced co-products (Randstuecke-style rework outputs, each its own percent) plus one
    # genuine by-product (animal-feed protein) that carries NO percent at all and always books zero.
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 |
      | mainProd5   | PCE      |
      | rawProduct5 | PCE      |
      | coProdA5    | PCE      |
      | coProdB5    | PCE      |
      | byProd5     | PCE      |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID  | M_InventoryLine_ID  | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID  |
      | inputInventory5 | inputInventoryLine5 | 2024-03-20   | 540008         | rawProduct5  | 0       | 30       | PCE          | 15        | inputHU5 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | rawProduct5  | MovingAverageInvoice | 15 CHF           | 30 PCE     |

    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProdA5                | 20                               |
      | coProdB5                | 15                               |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | mainProd5Item                      | packTUItem                 | mainProd5               | 100 | 2022-01-01 |
      | coProdA5Item                       | packTUItem                 | coProdA5                | 100 | 2022-01-01 |
      | coProdB5Item                       | packTUItem                 | coProdB5                | 100 | 2022-01-01 |
      | byProd5Item                        | packTUItem                 | byProd5                 | 100 | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom5       | mainProd5               | 2021-01-02 | bomVersion5                          |
    And metasfresh contains PP_Product_BOMLines
      | Identifier   | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | inputLine5   | bom5                         | rawProduct5             | 2021-01-02 | 5        | CO            |
      | coProdALine5 | bom5                         | coProdA5                | 2021-01-02 | -1       | CP            |
      | coProdBLine5 | bom5                         | coProdB5                | 2021-01-02 | -1       | CP            |
      | byProdLine5  | bom5                         | byProd5                 | 2021-01-02 | -1       | BY            |
    And the PP_Product_BOM identified by bom5 is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan5  | mobileWorkflow                | mainProd5               | bomVersion5                              | false        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder5               | MOP         | mainProd5               | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan5                             |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine5                  | ppOrder5               | rawProduct5             | 30           | false           | PCE               | CO            |
      | coProdABomLine5                | ppOrder5               | coProdA5                | -6           | false           | PCE               | CP            |
      | coProdBBomLine5                | ppOrder5               | coProdB5                | -6           | false           | PCE               | CP            |
      | byProdBomLine5                 | ppOrder5               | byProd5                 | -6           | false           | PCE               | BY            |

    And the handling unit identified by inputHU5 is issued whole to PP_Order_BOMLine inputBomLine5

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder5    | mainHU5            | N               | 0     | N               | 1     | N               | 20          | mainProd5Item                      |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder5    | coProdABomLine5     | coHUA5             | N               | 0     | N               | 1     | N               | 6           | coProdA5Item                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder5    | coProdBBomLine5     | coHUB5             | N               | 0     | N               | 1     | N               | 3           | coProdB5Item                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder5    | byProdBomLine5      | byHU5              | N               | 0     | N               | 1     | N               | 2           | byProd5Item                        |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder5               |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollectorA5        | ppOrder5               | coProdA5                | -6          | CO        | MixVariance       |
      | coReceiptCostCollectorB5        | ppOrder5               | coProdB5                | -3          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollectorA5, coReceiptCostCollectorB5 are posted

    # Each co-product keeps its own carve (20% and 15% of the 450 CHF pool); the by-product stays at zero;
    # the finished good absorbs the remainder (65% = 292.5).
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder5               | rawProduct5             | MovingAverageInvoice | MI                    | 450                |
      | ppOrder5               | coProdA5                | MovingAverageInvoice | CO                    | 90                 |
      | ppOrder5               | coProdB5                | MovingAverageInvoice | CO                    | 67.5               |
      | ppOrder5               | byProd5                 | MovingAverageInvoice | BY                    | 0                  |
      | ppOrder5               | mainProd5               | MovingAverageInvoice | MR                    | 292.5              |

    And the manufacturing order identified by ppOrder5 is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector5      | ppOrder5               | mainProd5               | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector5 are posted

    # Each co-product's own leg is asserted independently, by product - not just the whole-order balance.
    And Fact_Acct records are matching
      | Record_ID                  | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector5 | P_Asset_Acct          | mainProd5    | 292.5     | 0         | 0 PCE |
      | distributionCostCollector5 | P_WIP_Acct            | mainProd5    | 0         | 292.5     | 0 PCE |
      | distributionCostCollector5 | P_Asset_Acct          | coProdA5     | 90        | 0         | 0 PCE |
      | distributionCostCollector5 | P_WIP_Acct            | coProdA5     | 0         | 90        | 0 PCE |
      | distributionCostCollector5 | P_Asset_Acct          | coProdB5     | 67.5      | 0         | 0 PCE |
      | distributionCostCollector5 | P_WIP_Acct            | coProdB5     | 0         | 67.5      | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder5 are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC7
  Scenario: Adding a second co-product leaves the first co-product's carve unchanged
    # Two independent orders off the SAME input pool (450 CHF) and the SAME co-product A (20% share,
    # carve 90). Order 1 has only A; order 2 also carries a second co-product B (15%, carve 67.5). A's own
    # carve must be identical across both orders - only the finished good shrinks, by exactly B's carve.
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 |
      | mainProd7a  | PCE      |
      | mainProd7b  | PCE      |
      | rawProduct7 | PCE      |
      | coProdA7    | PCE      |
      | coProdB7    | PCE      |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID   | M_InventoryLine_ID   | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID   |
      | inputInventory7a | inputInventoryLine7a | 2024-03-20   | 540008         | rawProduct7  | 0       | 30       | PCE          | 15        | inputHU7a |
      | inputInventory7b | inputInventoryLine7b | 2024-03-20   | 540008         | rawProduct7  | 0       | 30       | PCE          | 15        | inputHU7b |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | rawProduct7  | MovingAverageInvoice | 15 CHF           | 60 PCE     |

    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProdA7                | 20                               |
      | coProdB7                | 15                               |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | mainProd7aItem                     | packTUItem                 | mainProd7a              | 100 | 2022-01-01 |
      | mainProd7bItem                     | packTUItem                 | mainProd7b              | 100 | 2022-01-01 |
      | coProdA7Item                       | packTUItem                 | coProdA7                | 100 | 2022-01-01 |
      | coProdB7Item                       | packTUItem                 | coProdB7                | 100 | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom7a      | mainProd7a              | 2021-01-02 | bomVersion7a                         |
      | bom7b      | mainProd7b              | 2021-01-02 | bomVersion7b                         |
    And metasfresh contains PP_Product_BOMLines
      | Identifier    | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | inputLine7a   | bom7a                        | rawProduct7             | 2021-01-02 | 5        | CO            |
      | coProdALine7a | bom7a                        | coProdA7                | 2021-01-02 | -1       | CP            |
      | inputLine7b   | bom7b                        | rawProduct7             | 2021-01-02 | 5        | CO            |
      | coProdALine7b | bom7b                        | coProdA7                | 2021-01-02 | -1       | CP            |
      | coProdBLine7b | bom7b                        | coProdB7                | 2021-01-02 | -1       | CP            |
    And the PP_Product_BOM identified by bom7a is completed
    And the PP_Product_BOM identified by bom7b is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan7a | mobileWorkflow                | mainProd7a              | bomVersion7a                             | false        |
      | prodPlan7b | mobileWorkflow                | mainProd7b              | bomVersion7b                             | false        |

    # Order 1: co-product A alone.
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder7a              | MOP         | mainProd7a              | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan7a                            |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine7a                 | ppOrder7a              | rawProduct7             | 30           | false           | PCE               | CO            |
      | coProdABomLine7a               | ppOrder7a              | coProdA7                | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU7a is issued whole to PP_Order_BOMLine inputBomLine7a

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder7a   | mainHU7a           | N               | 0     | N               | 1     | N               | 20          | mainProd7aItem                     |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder7a   | coProdABomLine7a    | coHUA7a            | N               | 0     | N               | 1     | N               | 6           | coProdA7Item                       |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder7a              |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector7a        | ppOrder7a              | coProdA7                | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector7a are posted

    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder7a              | rawProduct7             | MovingAverageInvoice | MI                    | 450                |
      | ppOrder7a              | coProdA7                | MovingAverageInvoice | CO                    | 90                 |
      | ppOrder7a              | mainProd7a              | MovingAverageInvoice | MR                    | 360                |

    And the manufacturing order identified by ppOrder7a is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector7a     | ppOrder7a              | mainProd7a              | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector7a are posted

    And Fact_Acct records are matching
      | Record_ID                   | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector7a | P_Asset_Acct          | mainProd7a   | 360       | 0         | 0 PCE |
      | distributionCostCollector7a | P_WIP_Acct            | mainProd7a   | 0         | 360       | 0 PCE |
      | distributionCostCollector7a | P_Asset_Acct          | coProdA7     | 90        | 0         | 0 PCE |
      | distributionCostCollector7a | P_WIP_Acct            | coProdA7     | 0         | 90        | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder7a are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

    # Order 2: the SAME co-product A, unchanged percent, plus a new co-product B.
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder7b              | MOP         | mainProd7b              | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan7b                            |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine7b                 | ppOrder7b              | rawProduct7             | 30           | false           | PCE               | CO            |
      | coProdABomLine7b               | ppOrder7b              | coProdA7                | -6           | false           | PCE               | CP            |
      | coProdBBomLine7b               | ppOrder7b              | coProdB7                | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU7b is issued whole to PP_Order_BOMLine inputBomLine7b

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder7b   | mainHU7b           | N               | 0     | N               | 1     | N               | 20          | mainProd7bItem                     |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder7b   | coProdABomLine7b    | coHUA7b            | N               | 0     | N               | 1     | N               | 6           | coProdA7Item                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder7b   | coProdBBomLine7b    | coHUB7b            | N               | 0     | N               | 1     | N               | 3           | coProdB7Item                       |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder7b              |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector7bA       | ppOrder7b              | coProdA7                | -6          | CO        | MixVariance       |
      | coReceiptCostCollector7bB       | ppOrder7b              | coProdB7                | -3          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector7bA, coReceiptCostCollector7bB are posted

    # Co-product A's carve is STILL 90 - unchanged by adding B; the finished good drops from 360 to 292.5,
    # exactly B's own carve (67.5).
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder7b              | rawProduct7             | MovingAverageInvoice | MI                    | 450                |
      | ppOrder7b              | coProdA7                | MovingAverageInvoice | CO                    | 90                 |
      | ppOrder7b              | coProdB7                | MovingAverageInvoice | CO                    | 67.5               |
      | ppOrder7b              | mainProd7b              | MovingAverageInvoice | MR                    | 292.5              |

    # Order 1's own CC-170 already wrote coProdA7's current cost up to 15 CHF/PCE (90 / 6 PCE) - so here,
    # in order 2, coProdA7's OWN receipt (6 PCE at ITS OWN now-15-CHF/PCE cost) capitalizes the SAME 90 CHF
    # directly at receipt, with nothing left to true up: literal proof its carve is unaffected by B.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector7bA | P_Asset_Acct          | coProdA7     | 90        | 0         | 6 PCE  |
      | coReceiptCostCollector7bA | P_WIP_Acct            | coProdA7     | 0         | 90        | -6 PCE |

    And the manufacturing order identified by ppOrder7b is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector7b     | ppOrder7b              | mainProd7b              | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector7b are posted

    # Only B's own carve (67.5, never priced before) needs a CC-170 leg - A's residual is already zero
    # (its receipt above already capitalized exactly its 90 CHF carve), so no A leg is posted here.
    And Fact_Acct records are matching
      | Record_ID                   | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector7b | P_Asset_Acct          | mainProd7b   | 292.5     | 0         | 0 PCE |
      | distributionCostCollector7b | P_WIP_Acct            | mainProd7b   | 0         | 292.5     | 0 PCE |
      | distributionCostCollector7b | P_Asset_Acct          | coProdB7     | 67.5      | 0         | 0 PCE |
      | distributionCostCollector7b | P_WIP_Acct            | coProdB7     | 0         | 67.5      | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder7b are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC8b
  Scenario: A BOM without a participating co-product books exactly as before
    And metasfresh contains M_Products:
      | Identifier   | X12DE355 |
      | mainProd8b   | PCE      |
      | rawProduct8b | PCE      |

    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | rawProduct8b | 15 CHF           |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | mainProd8bItem                     | packTUItem                 | mainProd8b              | 100 | 2022-01-01 |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID   | M_InventoryLine_ID   | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID   |
      | inputInventory8b | inputInventoryLine8b | 2024-03-20   | 540008         | rawProduct8b | 0       | 30       | PCE          | 15        | inputHU8b |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom8b      | mainProd8b              | 2021-01-02 | bomVersion8b                         |
    And metasfresh contains PP_Product_BOMLines
      | Identifier  | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | inputLine8b | bom8b                        | rawProduct8b            | 2021-01-02 | 5        | CO            |
    And the PP_Product_BOM identified by bom8b is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan8b | mobileWorkflow                | mainProd8b              | bomVersion8b                             | false        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder8b              | MOP         | mainProd8b              | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan8b                            |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine8b                 | ppOrder8b              | rawProduct8b            | 30           | false           | PCE               | CO            |

    And the handling unit identified by inputHU8b is issued whole to PP_Order_BOMLine inputBomLine8b

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder8b   | mainHU8b           | N               | 0     | N               | 1     | N               | 24          | mainProd8bItem                     |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder8b              |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | mainReceiptCostCollector8b      | ppOrder8b              | mainProd8b              | 24          | CO        | MaterialReceipt   |
    And Wait until documents mainReceiptCostCollector8b are posted

    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder8b              | rawProduct8b            | MovingAverageInvoice | MI                    | 450                |
      | ppOrder8b              | mainProd8b              | MovingAverageInvoice | MR                    | 450                |

    And the manufacturing order identified by ppOrder8b is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector8b     | ppOrder8b              | mainProd8b              | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector8b are posted

    And Fact_Acct records are matching
      | Record_ID                   | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector8b | P_Asset_Acct          | mainProd8b   | 450       | 0         | 0 PCE |
      | distributionCostCollector8b | P_WIP_Acct            | mainProd8b   | 0         | 450       | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder8b are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC14
  Scenario: Co-product receipt reversal nets Fact_Acct to zero
    # The co-product's receipt now capitalizes to inventory (Dr P_Asset / Cr P_WIP), a changed accounting
    # treatment - so its reversal must be symmetric. Reversing the receipt cost collector via the REAL
    # Reverse-Correct DocAction (the established "is reversed as" step-def, not a fabricated state) must net
    # the co-product's P_Asset and P_WIP legs back to zero - no stale inventory value or WIP residual is left
    # behind for a receipt that has been fully undone.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    # Receive the main product (24 PCE) - no BOM-line reference -> main-product receipt.
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |

    # Receive the co-product (6 PCE) - BOM-line reference -> co/by-product receipt (receivingByOrCoProduct).
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    And complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # The co-product's receipt capitalizes to inventory - Dr P_Asset / Cr P_WIP 59.4 (9.9 CHF/PCE x 6 PCE),
    # same booking as TC1's coReceiptCostCollector.
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |

    # Reverse the receipt cost collector via the REAL Reverse-Correct DocAction (the established, already-proven
    # step-def) - this drives the actual DocAction and lets the scenario assert the REAL Fact_Acct, no fabricated
    # reversal state.
    And the PP_Cost_Collector identified by coReceiptCostCollector is reversed as coReversalCostCollector
    And Wait until documents coReversalCostCollector are posted

    # The reversal's own legs are the exact negation of the receipt's legs - same account, same side, the
    # amount and qty negated (this acctSchema has IsAllowNegativePosting=Y, so the reversal posts as a
    # negative entry on the same side rather than swapping Dr/Cr - both are valid metasfresh conventions,
    # and either way the pair below nets the receipt back to zero, asserted explicitly next).
    And Fact_Acct records are matching
      | Record_ID               | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReversalCostCollector | P_Asset_Acct          | coProd       | -59.4     | 0         | -6 PCE |
      | coReversalCostCollector | P_WIP_Acct            | coProd       | 0         | -59.4     | 6 PCE  |

    # The whole receipt+reversal set nets to zero: no stale inventory value or WIP residual remains for the
    # co-product once its receipt has been fully reversed - the capitalized value fully unwinds.
    And Fact_Acct records balances for documents coReceiptCostCollector,coReversalCostCollector are matching
      | AccountConceptualName | AcctBalance |
      | P_Asset_Acct          | 0           |
      | P_WIP_Acct            | 0           |

  @from:cucumber
  @Id:S29488_TC15
  Scenario: Reversing the CostDifferenceDistribution collector that carries a co-product residual nets to zero
    # The CC-170 CostDifferenceDistribution collector carries BOTH the main product's residual AND the
    # co-product's own residual (per-product CostDetail rows under one collector). The forward posting emits
    # a per-product leg-set for each. Reversing that collector via the REAL Reverse-Correct DocAction must
    # therefore emit the SAME per-product breakdown negated - NOT funnel every product's rows through the
    # single-segment aggregate (which throws once >=2 product segments are present). The reversal's own legs
    # must be the exact per-product negation of the forward legs, so each product's P_Asset/P_WIP nets back
    # to zero - the same customer write-down case as TC1 (carve 48, own current cost 9.9 CHF/PCE).
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # Distribute the order (CC-170 true-up): main product residual 402 capitalizes; the co-product's own
    # residual is written down 11.4 (Cr P_Asset / Dr P_WIP) - both on the SAME distribution collector.
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # Forward per-product legs (main 402 write-up, co-product 11.4 write-down), asserted per product.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 11.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 11.4      | 0         | 0 PCE |

    # Reverse the distribution collector via the REAL Reverse-Correct DocAction. Before the fix this posting
    # THROWS (all products' CostDetail rows funnel through the single-segment aggregate); after the fix the
    # reversal emits one negated leg-set PER PRODUCT and posts cleanly.
    And the PP_Cost_Collector identified by distributionCostCollector is reversed as distReversalCostCollector
    And Wait until documents distReversalCostCollector are posted

    # The reversal's own legs are the exact per-product negation of the forward legs (Dr/Cr swapped, amount
    # unchanged): main product 402 the other way, co-product 11.4 the other way - each resolved against its
    # OWN product accounts, not aggregated. Asserted per product, not just via the whole-set balance below.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distReversalCostCollector | P_Asset_Acct          | mainProd     | 0         | 402       | 0 PCE |
      | distReversalCostCollector | P_WIP_Acct            | mainProd     | 402       | 0         | 0 PCE |
      | distReversalCostCollector | P_Asset_Acct          | coProd       | 11.4      | 0         | 0 PCE |
      | distReversalCostCollector | P_WIP_Acct            | coProd       | 0         | 11.4      | 0 PCE |

    # The whole distribute+reversal set nets to zero on both accounts, across both products: no stale
    # inventory value or WIP residual remains once the distribution collector is fully reversed.
    And Fact_Acct records balances for documents distributionCostCollector,distReversalCostCollector are matching
      | AccountConceptualName | AcctBalance |
      | P_Asset_Acct          | 0           |
      | P_WIP_Acct            | 0           |

  @from:cucumber
  @Id:S29488_TC16
  Scenario: Co-product received at exactly its carve needs no post-calculation adjustment
    # Randstuecke (coProd) carries the same 10.666667% percent as the write-down case, so its carve of the
    # 450 CHF input pool is p x 450 = 48 CHF (8 CHF/PCE x 6 PCE). Its OWN current cost is set to exactly
    # 8 CHF/PCE, so its receipt books 8 x 6 = 48 - identical to its carve. The residual is 48 - 48 = 0, so
    # NO co-product CC-170 leg is emitted at all; only the finished good's own 402 residual is discharged.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 8 CHF            |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # Cost conservation: the co-product is relieved at its carve (48), the main product by the remainder
    # (402), summing to the whole 450 input pool.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 402                |

    # The co-product's own receipt books exactly its carve (8 CHF/PCE x 6 = 48) - Dr P_Asset / Cr P_WIP.
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 48        | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 48        | -6 PCE |

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The co-product's residual is zero, so it draws NO CC-170 leg at all - only the finished good's own
    # 402 residual capitalizes here (mirrors TC7 order 2, where a co-product already received at its carve
    # draws no distribution leg). The exact-match assertion below fails if any co-product leg is emitted.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC17
  Scenario: Co-product receipt over-relieves the input pool, driving order WIP negative until the post-calculation correction
    # Randstuecke carries the 10.666667% percent (carve 48), but its OWN current cost is a high 100 CHF/PCE,
    # so its 6-PCE receipt books 100 x 6 = 600 - MORE than the whole 450 CHF input pool. That over-relieves
    # WIP: after the receipts the order's WIP balance is NEGATIVE (450 issued in, 600 relieved out = -150).
    # The CC-170 post-calculation then writes the co-product back DOWN to its 48 carve (a 552 write-down),
    # which corrects the order's WIP back to 0.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 100 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # The co-product's receipt books its full own value (100 x 6 = 600) into inventory - Dr P_Asset / Cr P_WIP.
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 600       | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 600       | -6 PCE |

    # After the receipts the whole order's WIP is NEGATIVE: 450 was issued into WIP, 600 was relieved out of
    # it by the co-product receipt, netting -150 - the over-relief the post-calculation still has to correct.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | -150        |

    # Cost conservation is unaffected: the co-product's carve is still 48, the main product's still 402.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 402                |

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The CC-170 true-up writes the co-product down from 600 to its 48 carve (a 552 write-down: Cr P_Asset /
    # Dr P_WIP) and capitalizes the main product's 402 - together bringing the order's WIP back from -150 to 0.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 552       | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 552       | 0         | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC18
  Scenario: Co-product with zero current cost books nothing at receipt and takes its full carve at post-calculation
    # Randstuecke carries the 10.666667% percent (carve 48) but has NO own current cost at receive time - a
    # genuinely never-priced co-product (M_Cost = 0), distinct from a blank percent (which carves nothing at
    # all). Its MixVariance receipt therefore books ZERO, and the whole 48 CHF carve is applied at the CC-170
    # post-calculation instead, as a write-UP from 0 to 48.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | rawProduct              | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # The zero-cost receipt relieves NOTHING from WIP: after the receipts the order's WIP is still the full
    # 450 issued in (contrast the write-down case, where the 59.4 receipt leaves 390.6) - proof it booked 0.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 450         |

    # Cost conservation still carves the co-product at 48 and the main product at 402.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | rawProduct              | MovingAverageInvoice | MI                    | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 402                |

    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # Because the receipt booked 0, the co-product's FULL 48 carve is discharged here as a write-UP
    # (Dr P_Asset / Cr P_WIP) - the same leg shape as the main product's own 402 residual.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 48        | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 0         | 48        | 0 PCE |

    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |
