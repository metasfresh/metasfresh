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
      | acctSchema      | metas fresh UN/34 CHF | A             |
    And cost elements for material costing methods AveragePO are active

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | mainProd   | PCE      |
      | inputProd  | PCE      |
      | coProd     | PCE      |

    # Seed the input component with a 15 CHF/PCE AveragePO current cost and create its stock HU (30 PCE).
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inputInventory | inputInventoryLine | 2024-03-20   | 540008         | inputProd    | 0       | 30       | PCE          | 15        | inputHU |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | inputProd    | AveragePO        | 15 CHF           | 30 PCE     |

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
      | inputLine  | bom                          | inputProd               | 2021-01-02 | 5        | CO            |
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
  @Id:S29488_TC1
  Scenario: Average PO - the co-product's own current cost overvalues its carve share, write-down at close
    # Randstuecke (coProd) is given a manual cost-distribution percent p = 10.666667% (~10.67%), so its
    # carve share of the 450 CHF input pool is p x 450 = 48 CHF (8 CHF/PCE) - the customer's real case.
    # The percent carries 6 decimals so the carve lands on an exact 48.0000 (no rounding noise to chase).
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |

    # Randstuecke ALREADY has its own current cost of 9.9 CHF/PCE (from a prior receipt/valuation run,
    # unrelated to this order's carve) - higher than the 8 CHF/PCE carve. Its own receipt below books at
    # THIS price (current-cost x received-qty), not at the carve - so the two diverge and the CC-170
    # true-up at order close has to write the co-product's inventory DOWN.
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | inputProd               | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    # Issue the whole 30-PCE input HU to the component BOM line: totalInbound = 30 x 15 = 450 CHF.
    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    # Receive the main product (24 PCE) - no BOM-line reference -> main-product receipt.
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 24          | mainProdItem                       |

    # Receive the co-product (6 PCE) - BOM-line reference -> co/by-product receipt (receivingByOrCoProduct).
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    # Process the planning HUs of both receipts into completed, posted receipt cost collectors.
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    # Wait for the co-product receipt cost collector to be created and posted before reading the costs it
    # drives (its posting runs the production post-calculation).
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And Wait until documents coReceiptCostCollector are posted

    # Cost conservation via the production post-calculation (leg A): the co-product is relieved at its
    # carve (p x 450 = 48), the main product by the remainder (450 - 48 = 402), Sum = 450 = total input.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | CurrentCostPrice | PostCalculationAmt |
      | ppOrder                | inputProd               | AveragePO        | MI                    | 15 CHF           | 450                |
      | ppOrder                | coProd                  | AveragePO        | CO                    | 9.9 CHF          | 48                 |
      | ppOrder                | mainProd                | AveragePO        | MR                    | 0 CHF            | 402                |

    # Leg B (the co-product's own receipt) books at ITS OWN current cost x received qty (9.9 x 6 = 59.4),
    # NOT at the 48 carve - the two legs diverge by design (this is the customer case under test).
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |

    # After its own receipt, the co-product's current cost is unchanged (it had no prior stock, so the
    # weighted average of 0 and 9.9 x 6 stays 9.9) and its qty is now 6.
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | coProd       | AveragePO        | 9.9 CHF          | 6 PCE      |

    # Distribute the order (CC-170 true-up): the main product's residual (402, all in stock) capitalizes
    # out of WIP as usual. The co-product's OWN residual (carve 48 - booked 59.4 = -11.4) is ALSO
    # discharged here, on the SAME distribution collector, against the co-product's OWN accounts - and
    # because the co-product was overvalued at receipt (9.9/PCE booked vs. 8/PCE carve), the true-up is a
    # WRITE-DOWN: Cr P_Asset / Dr P_WIP (the opposite sign from the main product's write-UP above).
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # Do NOT settle for the whole-order balance alone (below) - it would pass even if the co-product's own
    # leg were silently dropped. Assert the co-product's OWN P_Asset/P_WIP legs, with the write-down sign:
    # Cr P_Asset 11.4 (credit - inventory written down) / Dr P_WIP 11.4 (debit - the excess returns to WIP).
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 11.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 11.4      | 0         | 0 PCE |

    # The whole manufacturing order now balances: Sum(AmtAcctDr) = Sum(AmtAcctCr) across all cost
    # collectors, WIP nets to 0 (input pool fully relieved into the outputs' inventory, including the
    # co-product's write-down), and P_Asset nets to 0 too.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC2
  Scenario: Moving Average Invoice - the same carve and write-down hold on the go-forward method
    And cost elements for material costing methods MovingAverageInvoice are active
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | M             |
    # The input's cost under the go-forward method (never received under it yet).
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | inputProd    | 15 CHF           |
    And update M_Product:
      | M_Product_ID.Identifier | CoProductCostDistributionPercent |
      | coProd                  | 10.666667                        |
    # Randstuecke's own current cost under the go-forward method - same overvaluation vs. its carve.
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | coProd       | 9.9 CHF          |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | inputProd               | 30           | false           | PCE               | CO            |
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

    # Same carve as Average PO: co-product p x 450 = 48, main the remainder 402, conserved to 450.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | CurrentCostPrice | PostCalculationAmt |
      | ppOrder                | inputProd               | MovingAverageInvoice | MI                    | 15 CHF           | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 9.9 CHF          | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 0 CHF            | 402                |

    # Same divergence as Average PO: leg B books the co-product's OWN current cost x qty (9.9 x 6 = 59.4).
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 59.4      | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 59.4      | -6 PCE |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | coProd       | MovingAverageInvoice | 9.9 CHF          | 6 PCE      |

    # Distribute the order so the main product's residual capitalizes out of WIP (same as Average PO).
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # Same write-down as Average PO on the co-product's OWN legs (Cr P_Asset / Dr P_WIP 11.4), asserted
    # explicitly - not just the whole-order balance below, which would pass even if this leg were dropped.
    And Fact_Acct records are matching
      | Record_ID                 | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty   |
      | distributionCostCollector | P_Asset_Acct          | mainProd     | 402       | 0         | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | mainProd     | 0         | 402       | 0 PCE |
      | distributionCostCollector | P_Asset_Acct          | coProd       | 0         | 11.4      | 0 PCE |
      | distributionCostCollector | P_WIP_Acct            | coProd       | 11.4      | 0         | 0 PCE |

    # The whole manufacturing order balances and WIP nets to 0 on the go-forward method too.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |
