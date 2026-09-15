@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@F1500
@ghActions:run_on_executor6
Feature: Co-product valuation at a manual fixed cost price
## F1500: Costing

  # A production run turns one input into a premium main product plus a low-value secondary
  # output ("Rework" / "Randstuecke"), modelled as a co-product BOM line. When the co-product's
  # product carries a manual M_Product.CoProductFixedCostPrice, that co-product is valued at
  # fixedPrice x received-qty: its own receipt books at the fixed price and the main product is
  # relieved only by the remainder of the input cost pool. A blank fixed price keeps today's
  # qty-distribution. Quantities are in PCE standing in for the customer's kg; the BOM ratios are
  # chosen so the input cost pool (450) and the co-product quantity (6) are exact.

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

    # BOM of the main product "blocks": one input Component (issued) and one Co-product output "Randstuecke".
    # A co-product BOM line carries a NEGATIVE QtyBOM (it is an output). Per finished good the ratios are
    # input 5 and co-product -1, so a 6-PCE order requires 30 PCE input and yields 6 PCE co-product.
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

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

  @from:cucumber
  @Id:S29488_TC1
  Scenario: Average PO - co-product relieved at its fixed cost price, main by the remainder
    # Randstuecke (coProd) carries a manual fixed cost price of 8 CHF/PCE.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductFixedCostPrice |
      | coProd                  | 8                       |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder                | MOP         | mainProd                | 6          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | prodPlan                              |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | inputBomLine                   | ppOrder                | inputProd               | 30           | false           | PCE               | CO            |
      | coProdBomLine                  | ppOrder                | coProd                  | -6           | false           | PCE               | CP            |

    # Issue the whole 30-PCE input HU to the component BOM line: totalInbound = 30 x 15 = 450 CHF.
    And the handling unit identified by inputHU is issued whole to PP_Order_BOMLine inputBomLine

    # Receive the main product (6 PCE) - no BOM-line reference -> main-product receipt.
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 6           | mainProdItem                       |

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
    # fixed price (8 x 6 = 48), the main product by the remainder (450 - 48 = 402), Sum = 450 = total input.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | CurrentCostPrice | PostCalculationAmt |
      | ppOrder                | inputProd               | AveragePO        | MI                    | 15 CHF           | 450                |
      | ppOrder                | coProd                  | AveragePO        | CO                    | 8 CHF            | 48                 |
      | ppOrder                | mainProd                | AveragePO        | MR                    | 0 CHF            | 402                |

    # The co-product's own inventory is valued at the fixed 8 CHF (leg B), not at its live current cost
    # (0) - so a downstream product made from the co-product inherits a realistic cost.
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | coProd       | AveragePO        | 8 CHF            | 6 PCE      |

    # The co-product receipt capitalizes its 48 CHF valuation to inventory (Dr P_Asset / Cr P_WIP,
    # positive, received qty on P_Asset), mirroring the main-product receipt - so the value reaches
    # report_InventoryValue, which sums Fact_Acct.qty on P_Asset. The two legs balance.
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 48        | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 48        | -6 PCE |

    # The co-product's value/qty now IS in inventory - the Lagerwert / inventory-value report reads the
    # P_Asset Fact_Acct qty (6 PCE at the fixed 8 CHF = 48). Before the fix its value went to
    # P_MixVariance (P&L) and never reached inventory.
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | InventoryValueAcctAmt |
      | 2024-03-27 | coProd       | warehouseStd   | 6   | 8.0000         | 48.00                 |

    # Distribute the order: the main product's receipt booked at its current cost (0), so the input pool
    # remains in WIP until the post-calculation capitalizes the residual (402, all in stock) to the main
    # product's P_Asset. This is the step that clears WIP - and it only nets to 0 because the co-product's
    # 48 already left WIP into inventory (with the pre-fix P_MixVariance routing, P_Asset would end at +48).
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The whole manufacturing order now balances: Sum(AmtAcctDr) = Sum(AmtAcctCr) across all cost
    # collectors, WIP nets to 0 (input pool fully relieved into the outputs' inventory), and the input
    # value transferred entirely to the outputs' P_Asset (net 0 over the order).
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC2
  Scenario: Moving Average Invoice - the same fixed-price relief holds on the go-forward method
    And cost elements for material costing methods MovingAverageInvoice are active
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | M             |
    # The input's cost under the go-forward method (never received under it yet).
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | inputProd    | 15 CHF           |
    And update M_Product:
      | M_Product_ID.Identifier | CoProductFixedCostPrice |
      | coProd                  | 8                       |

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
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 6           | mainProdItem                       |
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

    # Same relief as Average PO: co-product 8 x 6 = 48, main the remainder 402, conserved to 450.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID     | PP_Order_Cost_TrxType | CurrentCostPrice | PostCalculationAmt |
      | ppOrder                | inputProd               | MovingAverageInvoice | MI                    | 15 CHF           | 450                |
      | ppOrder                | coProd                  | MovingAverageInvoice | CO                    | 8 CHF            | 48                 |
      | ppOrder                | mainProd                | MovingAverageInvoice | MR                    | 0 CHF            | 402                |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | coProd       | MovingAverageInvoice | 8 CHF            | 6 PCE      |

    # Same corrected posting as Average PO: the co-product receipt capitalizes to inventory
    # (Dr P_Asset / Cr P_WIP, positive, received qty on P_Asset), not to the P_MixVariance P&L account.
    And Fact_Acct records are matching
      | Record_ID              | AccountConceptualName | M_Product_ID | AmtAcctDr | AmtAcctCr | Qty    |
      | coReceiptCostCollector | P_Asset_Acct          | coProd       | 48        | 0         | 6 PCE  |
      | coReceiptCostCollector | P_WIP_Acct            | coProd       | 0         | 48        | -6 PCE |

    # Distribute the order so the main product's residual capitalizes out of WIP (same as Average PO).
    And the manufacturing order identified by ppOrder is distributed
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType          |
      | distributionCostCollector       | ppOrder                | mainProd                | 0           | CO        | CostDifferenceDistribution |
    And Wait until documents distributionCostCollector are posted

    # The whole manufacturing order balances and WIP nets to 0 on the go-forward method too.
    And Fact_Acct records balances over the whole PP_Order ppOrder are matching
      | AccountConceptualName | AcctBalance |
      | P_WIP_Acct            | 0           |
      | P_Asset_Acct          | 0           |

  @from:cucumber
  @Id:S29488_TC3
  Scenario: Opt-in - a blank fixed cost price keeps today's qty-distribution behaviour
    # coProd's CoProductFixedCostPrice is left blank: the feature is opt-in, so the co-product is NOT
    # valued at 8. It keeps the pre-feature behaviour (qty-distribution; its live current cost) and the
    # order completes with no guard exception.
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
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 6           | mainProdItem                       |
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

    # Opt-out: the co-product is valued by the pre-feature qty-distribution (input pool 450 x 1/qty = 450/6
    # = 75.0002), NOT at a fixed 8; the main product carries the remainder (374.9998). No fixed price is read.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | inputProd               | AveragePO        | MI                    | 450                |
      | ppOrder                | coProd                  | AveragePO        | CO                    | 75.0002            |
      | ppOrder                | mainProd                | AveragePO        | MR                    | 374.9998           |

  @from:cucumber
  @Id:S29488_TC4
  Scenario: Guard - a fixed price that exceeds the input cost pool is rejected at posting
    # coProd's fixed price 80 CHF/PCE values the co-product at 80 x 6 = 480 CHF, which exceeds the order's
    # 450 CHF input pool and would drive the main product negative. Posting the cost collectors throws.
    And update M_Product:
      | M_Product_ID.Identifier | CoProductFixedCostPrice |
      | coProd                  | 80                      |

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
      | ppOrder     | mainHU             | N               | 0     | N               | 1     | N               | 6           | mainProdItem                       |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppOrder     | coProdBomLine       | coHU               | N               | 0     | N               | 1     | N               | 6           | coProdItem                         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder                |

    # The co-product receipt cost collector is created (its physical receipt happened) but its GL posting is
    # rejected by the guard: the post-calculation would value the co-products (480) above the input pool (450)
    # and drive the main product negative. The document stays completed with Posted='E'.
    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | coReceiptCostCollector          | ppOrder                | coProd                  | -6          | CO        | MixVariance       |
    And the PP_Cost_Collector identified by coReceiptCostCollector was rejected at posting with error containing exceeds the production order's input cost pool of

    # The main product is never persisted negative: with the co-product relief rejected it keeps the full pool.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | PostCalculationAmt |
      | ppOrder                | mainProd                | AveragePO        | MR                    | 450                |

  @from:cucumber
  @Id:S29488_TC5
  Scenario: Multiple co-products plus a by-product - each co-product relieved at its own fixed price
    # A cheese run yields a premium main output (blocks) plus two priced rework co-products and one genuine
    # by-product (whey). Per finished good (order qty 2) the ratios are curd 15, reworkA -3, reworkB -2,
    # whey -1, so 30 PCE curd yields 6 reworkA, 4 reworkB, 2 whey.
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 |
      | cheeseBlock | PCE      |
      | curd        | PCE      |
      | reworkA     | PCE      |
      | reworkB     | PCE      |
      | whey        | PCE      |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | curdInventory  | curdInventoryLine  | 2024-03-20   | 540008         | curd         | 0       | 30       | PCE          | 15        | curdHU  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | cheeseBlockItem                    | packTUItem                 | cheeseBlock             | 100 | 2022-01-01 |
      | reworkAItem                        | packTUItem                 | reworkA                 | 100 | 2022-01-01 |
      | reworkBItem                        | packTUItem                 | reworkB                 | 100 | 2022-01-01 |
      | wheyItem                           | packTUItem                 | whey                    | 100 | 2022-01-01 |
    And metasfresh contains PP_Product_BOM
      | Identifier     | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | cheeseBlockBom | cheeseBlock             | 2021-01-02 | cheeseBlockBomVersion                |
    And metasfresh contains PP_Product_BOMLines
      | Identifier  | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | curdLine    | cheeseBlockBom               | curd                    | 2021-01-02 | 15       | CO            |
      | reworkALine | cheeseBlockBom               | reworkA                 | 2021-01-02 | -3       | CP            |
      | reworkBLine | cheeseBlockBom               | reworkB                 | 2021-01-02 | -2       | CP            |
      | wheyLine    | cheeseBlockBom               | whey                    | 2021-01-02 | -1       | BY            |
    And the PP_Product_BOM identified by cheeseBlockBom is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier      | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | cheeseBlockPlan | mobileWorkflow                | cheeseBlock             | cheeseBlockBomVersion                    | false        |
    And update M_Product:
      | M_Product_ID.Identifier | CoProductFixedCostPrice |
      | reworkA                 | 8                       |
      | reworkB                 | 5                       |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | cheeseBlockOrder       | MOP         | cheeseBlock             | 2          | testResource             | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | 2024-03-26T23:59:00.00Z | Y                | cheeseBlockPlan                       |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | curdBomLine                    | cheeseBlockOrder       | curd                    | 30           | false           | PCE               | CO            |
      | reworkABomLine                 | cheeseBlockOrder       | reworkA                 | -6           | false           | PCE               | CP            |
      | reworkBBomLine                 | cheeseBlockOrder       | reworkB                 | -4           | false           | PCE               | CP            |
      | wheyBomLine                    | cheeseBlockOrder       | whey                    | -2           | false           | PCE               | BY            |

    And the handling unit identified by curdHU is issued whole to PP_Order_BOMLine curdBomLine
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID      | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | cheeseBlockOrder | cheeseBlockHU      | N               | 0     | N               | 1     | N               | 2           | cheeseBlockItem                    |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID      | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | cheeseBlockOrder | reworkABomLine      | reworkAHU          | N               | 0     | N               | 1     | N               | 6           | reworkAItem                        |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID      | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | cheeseBlockOrder | reworkBBomLine      | reworkBHU          | N               | 0     | N               | 1     | N               | 4           | reworkBItem                        |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID      | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | cheeseBlockOrder | wheyBomLine         | wheyHU             | N               | 0     | N               | 1     | N               | 2           | wheyItem                           |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | cheeseBlockOrder       |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus | CostCollectorType |
      | reworkAReceiptCostCollector     | cheeseBlockOrder       | reworkA                 | -6          | CO        | MixVariance       |
      | reworkBReceiptCostCollector     | cheeseBlockOrder       | reworkB                 | -4          | CO        | MixVariance       |
    And Wait until documents reworkAReceiptCostCollector, reworkBReceiptCostCollector are posted

    # Each co-product is relieved at its own fixed price (reworkA 8 x 6 = 48, reworkB 5 x 4 = 20); the
    # by-product stays at zero; the main product carries the remainder (450 - 48 - 20 = 382). Conserved to 450.
    And PP_Order_Cost are found:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_CostElement_ID | PP_Order_Cost_TrxType | PostCalculationAmt |
      | cheeseBlockOrder       | curd                    | AveragePO        | MI                    | 450                |
      | cheeseBlockOrder       | reworkA                 | AveragePO        | CO                    | 48                 |
      | cheeseBlockOrder       | reworkB                 | AveragePO        | CO                    | 20                 |
      | cheeseBlockOrder       | whey                    | AveragePO        | BY                    | 0                  |
      | cheeseBlockOrder       | cheeseBlock             | AveragePO        | MR                    | 382                |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | reworkA      | AveragePO        | 8 CHF            | 6 PCE      |
      | acctSchema      | reworkB      | AveragePO        | 5 CHF            | 4 PCE      |
