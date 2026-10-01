@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@ghActions:run_on_executor7
@Id:CostRevaluation
Feature: Cost Revaluation / Kosten Neubewertung
## F1500: Costing

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # Some scenarios switch accounting off for a moment; switched back on here so a failed scenario cannot leak into another one
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    # TC27 and TC32 close a period for a moment; the automatic period control is switched back on here so a failed scenario cannot leak into another one
    And the accounting periods are controlled automatically
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00[Europe/Berlin]
    And documents are accounted immediately
    And load C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    # Costing method = MAI (Moving Average Invoice, code 'M'). All scenarios below use the MovingAverageInvoice
    # material cost element accordingly. Under MAI the on-hand cost is still seeded from the physical inventory's
    # CostPrice at qty 0->N (first cost event), so every current-cost / valuation / delta figure matches MovingAverageInvoice.
    # The costing method is put back after every scenario (the C_AcctSchema step restores it).
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | M             |
    # Costing level = Client for this feature's products only: their product category costs at client level, so the
    # schema keeps its own costing level and the client-level cost rows these products create cannot clash with a
    # later feature that costs at organization level. Set BEFORE any product is created: creating a product seeds its
    # default M_Cost rows at the costing level in effect at that moment.
    And metasfresh contains M_Product_Categories:
      | Identifier      |
      | productCategory |
    And update M_Product_Category_Acct:
      | M_Product_Category_ID | C_AcctSchema_ID | CostingLevel |
      | productCategory       | acctSchema      | C            |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | M_Product_Category_ID |
      | product    | PCE      | productCategory       |
      | product2   | PCE      | productCategory       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    # ── Starting stock: product = 100 PCE @ 10 CHF; product2 = 50 PCE @ 20 CHF (the "noise" product) ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inventory      | inventoryLine      | 2024-03-05   | warehouse      | product      | 0       | 100      | PCE          | 10        | hu      |
      | inventory2     | inventory2Line     | 2024-03-05   | warehouse      | product2     | 0       | 50       | PCE          | 20        | hu2     |

  @Id:CostRevaluation_TC1
  Scenario: Increase - completing a cost revaluation raises the current cost price and books the positive delta
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-05 | 1000    |

    # ── Revaluate the product's current cost from 10 to 15 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── After: current cost 15 CHF, document completed, inventory value 1500 CHF ──
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed |
      | revaluation | CO        | true      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | product      | warehouse      | 100 | 15.0000        | 1500.00          |
    # the line shows the booked values, and its value difference equals the sum of its details
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt | IsRevaluated |
      | revaluation          | product      | 100        | 10               | 500      | true         |

    # ── Positive delta 100 PCE * (15 - 10) = 500 CHF booked P_Asset DR / P_CostAdjustment CR (amount-only, Qty 0) on the posting date ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   | C_AcctSchema_ID |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | product      | 2024-03-06 | acctSchema      |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | product      | 2024-03-06 | acctSchema      |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |
    # ── Stock value: P_Asset = new price * qty = 15 * 100 ──
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1500    |

  @Id:CostRevaluation_TC2
  Scenario: Decrease - completing a cost revaluation lowers the current cost price and books the negative delta to the cost-adjustment account
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |

    # ── Revaluate the product's current cost from 10 down to 8 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 8            |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── After: current cost 8 CHF, document completed, inventory value 800 CHF ──
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed |
      | revaluation | CO        | true      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 8.0000 CHF       | 100 PCE    |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | product      | warehouse      | 100 | 8.0000         | 800.00           |

    # ── Negative delta 100 PCE * (8 - 10) = -200 CHF booked P_CostAdjustment DR / P_Asset CR (amount-only, Qty 0) ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_CostAdjustment_Acct | 200 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_Asset_Acct          |             | 200 CHF     | 0   | revaluation | product      | 2024-03-06 |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_CostAdjustment_Acct | 200 CHF       |
      | P_Asset_Acct          | -200 CHF      |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 800     |

  @Id:CostRevaluation_TC4
  Scenario: Forward-only default - a today-dated revaluation with no EvaluationStartDate restates nothing already posted
    # ── Consume 20 PCE at the old cost 10 CHF BEFORE the revaluation (inventory count 100 -> 80 on 2024-03-07):
    #    posted -200 CHF on the asset account; this already-posted consumption must stay unchanged ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease | inventoryDecreaseLine | 2024-03-07   | warehouse      | product      | 100     | 80       | PCE          | hu      |
    And Fact_Acct records balances for documents inventoryDecrease are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |

    # ── Before: inventory value 800 CHF, current cost 10 CHF / 80 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
      | 2024-03-07 | product      | warehouse      | 80  | 10.0000        | 800.00           |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 80 PCE     | 800 CHF      |

    # ── Revaluate the product's current cost from 10 to 18 CHF, WITHOUT specifying EvaluationStartDate.
    #    DateAcct is chosen clearly AFTER the posted documents (2024-03-05, 2024-03-07) so the forward-only default
    #    is unambiguous — no same-day cost detail could be pulled in by an inclusive date-range boundary
    #    (the same-day case is covered by its own scenario). ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-10 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 18           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── EvaluationStartDate defaulted to the posting date (forward-only) ──
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed | EvaluationStartDate |
      | revaluation | CO        | true      | 2024-03-10          |

    # ── After: current cost 18 CHF for the 80 PCE on hand (CumulatedAmt reflects the new price going forward) ──
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 80 PCE     | 1440 CHF     |
    # ── Forward-only proof: the already-posted 2024-03-05 and 2024-03-07 valuations stay UNCHANGED — only the
    #    forward date reflects the new price. A broken unset-guard that restated them would fail these rows. ──
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
      | 2024-03-07 | product      | warehouse      | 80  | 10.0000        | 800.00           |
      | 2024-03-11 | product      | warehouse      | 80  | 18.0000        | 1440.00          |
    # ── The already-posted consumption keeps its posting at the old cost (not restated) ──
    And Fact_Acct records balances for documents inventoryDecrease are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |

    # ── Positive delta 80 PCE * (18 - 10) = 640 CHF booked P_Asset DR / P_CostAdjustment CR (amount-only, Qty 0) ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 640 CHF     |             | 0   | revaluation | product      | 2024-03-10 |
      | P_CostAdjustment_Acct |             | 640 CHF     | 0   | revaluation | product      | 2024-03-10 |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 640 CHF       |
      | P_CostAdjustment_Acct | -640 CHF      |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-07 | 800     |
      | product      | 2024-03-10 | 1440    |

  @Id:CostRevaluation_TC5
  Scenario: Evaluation start date follows the posting date - an earlier EvaluationStartDate is saved as the posting date and nothing is restated
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    | 1000 CHF     |

    # ── An EvaluationStartDate BEFORE the posted inventory (2024-03-05) is entered ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-01          | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── The evaluation start date always equals the posting date: 2024-03-01 is saved as 2024-03-06 ──
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed | EvaluationStartDate |
      | revaluation | CO        | true      | 2024-03-06          |

    # ── After: current cost 15 CHF; CumulatedAmt 1500 CHF (the 100 PCE on hand at the new price) ──
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
    # ── Already-posted accounting is not restated: the 2024-03-05 valuation stays 10.0000/1000.00 ──
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
      | 2024-03-07 | product      | warehouse      | 100 | 15.0000        | 1500.00          |

    # ── Delta 100 PCE * (15 - 10) = 500 CHF booked on the posting date ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | product      | 2024-03-06 |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-05 | 1000    |
      | product      | 2024-03-06 | 1500    |

  @Id:CostRevaluation_TC9
  Scenario: Zero-stock init - revaluing a product with CurrentQty=0 sets the current cost with no GL impact
    # ── Seed a zero-stock product: an M_Cost row exists (CurrentQty=0), but nothing was ever posted for it ──
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 | M_Product_Category_ID |
      | productZeroStock | PCE      | productCategory       |
    And update current costs
      | M_Product_ID     |
      | productZeroStock |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productZeroStock | MovingAverageInvoice | 0 CHF            | 0 PCE      | 0 CHF        |

    # ── Revaluate to an initial price ──
    When metasfresh contains M_CostRevaluation:
      | Identifier      | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluationZero | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And create lines for cost revaluation revaluationZero
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID     | NewCostPrice |
      | revaluationZero      | productZeroStock | 12           |
    And the cost revaluation identified by revaluationZero is completed
    And Wait until documents revaluationZero are posted

    # ── After: current cost set to 12 CHF, CurrentQty still 0 ──
    And validate M_CostRevaluation:
      | Identifier      | DocStatus | Processed |
      | revaluationZero | CO        | true      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productZeroStock | MovingAverageInvoice | 12.0000 CHF      | 0 PCE      | 0 CHF        |

    # ── No GL impact: Qty 0 means the delta is 0 CHF, so no Fact_Acct rows are posted, and the stock value stays 0 ──
    And no Fact_Acct records are found for documents revaluationZero
    And expect P_Asset balance for product
      | M_Product_ID     | DateAcct   | Balance |
      | productZeroStock | 2024-03-06 | 0       |

  @Id:CostRevaluation_TC10
  Scenario: Seed-cost - quick-input on a stocked product with no M_Cost row seeds the cost at qty 0 with no GL impact
    # ── A stocked product with NO M_Cost row (as after a migration that never set up costing for it) ──
    And metasfresh contains M_Products:
      | Identifier    | X12DE355 | M_Product_Category_ID |
      | productNoCost | PCE      | productCategory       |
    And remove current costs
      | M_Product_ID  |
      | productNoCost |

    # ── Quick-input the product with an initial price; the seed path creates the M_Cost row at qty 0 ──
    When metasfresh contains M_CostRevaluation:
      | Identifier        | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluationNoCost | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID  | NewCostPrice |
      | revaluationNoCost    | productNoCost | 12           |
    And the cost revaluation identified by revaluationNoCost is completed
    And Wait until documents revaluationNoCost are posted

    # ── After: the M_Cost row was created at qty 0 with the entered price; document completed ──
    And validate M_CostRevaluation:
      | Identifier        | DocStatus | Processed |
      | revaluationNoCost | CO        | true      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID  | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productNoCost | MovingAverageInvoice | 12.0000 CHF      | 0 PCE      | 0 CHF        |

    # ── No GL impact: qty 0 means the delta is 0 CHF, so no Fact_Acct rows are posted, and the stock value stays 0 ──
    And no Fact_Acct records are found for documents revaluationNoCost
    And expect P_Asset balance for product
      | M_Product_ID  | DateAcct   | Balance |
      | productNoCost | 2024-03-06 | 0       |

  @Id:CostRevaluation_TC11
  Scenario: Same-day consumption - a revaluation posted on the day of an already-posted consumption leaves that posting unchanged
    # ── Consume 20 PCE at the old cost 10 CHF on 2024-03-10: posted -200 CHF on the asset account ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease | inventoryDecreaseLine | 2024-03-10   | warehouse      | product      | 100     | 80       | PCE          | hu      |
    And Fact_Acct records balances for documents inventoryDecrease are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |

    # ── Revaluate from 10 to 18 CHF with the default (forward-only) dates, on the SAME day as the consumption ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-10 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 18           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── The revaluation books the stock on hand at posting: 80 PCE * (18 - 10) = 640 CHF, and the consumption keeps its posting at the old cost ──
    Then Fact_Acct records balances for documents inventoryDecrease are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 640 CHF     |             | 0   | revaluation | product      | 2024-03-10 |
      | P_CostAdjustment_Acct |             | 640 CHF     | 0   | revaluation | product      | 2024-03-10 |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 640 CHF       |
      | P_CostAdjustment_Acct | -640 CHF      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 80 PCE     | 1440 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
      | 2024-03-11 | product      | warehouse      | 80  | 18.0000        | 1440.00          |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-10 | 1440    |

  @Id:CostRevaluation_TC12
  Scenario: Second revaluation on the same day is accepted and books against the first one's price
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation1 | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation1
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation1         | product      | 15           |
    And the cost revaluation identified by revaluation1 is completed
    And Wait until documents revaluation1 are posted
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID    | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation1 | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation1 | product      | 2024-03-06 |

    # ── Same day again: accepted; it books the stock on hand against the first revaluation's price 100 PCE * (18 - 15) = 300 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation2 | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation2
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation2         | product      | 18           |
    And the cost revaluation identified by revaluation2 is completed
    And Wait until documents revaluation2 are posted
    Then validate M_CostRevaluation:
      | Identifier   | DocStatus | Processed |
      | revaluation2 | CO        | true      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID    | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 300 CHF     |             | 0   | revaluation2 | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 300 CHF     | 0   | revaluation2 | product      | 2024-03-06 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 100 PCE    | 1800 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | product      | warehouse      | 100 | 18.0000        | 1800.00          |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1800    |

  @Id:CostRevaluation_TC13
  Scenario: A posting date before a later stock movement is accepted and books the stock at posting on that date (known limitation)
    # ── Posting date 2024-03-04 is BEFORE the stock receipt of 2024-03-05 (100 PCE @ 10): accepted.
    #    Forward-only, it books the stock on hand at posting 100 PCE * (15 - 10) = 500 CHF, dated 2024-03-04 ──
    When metasfresh contains M_CostRevaluation:
      | Identifier          | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationBackDate | acctSchema      | MovingAverageInvoice | 2024-03-04          | 2024-03-04 |
    And create lines for cost revaluation revaluationBackDate
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationBackDate  | product      | 15           |
    And the cost revaluation identified by revaluationBackDate is completed
    And Wait until documents revaluationBackDate are posted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID           | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluationBackDate | product      | 2024-03-04 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluationBackDate | product      | 2024-03-04 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
    # Known limitation (accepted, documented): on 2024-03-04 there is no stock yet (0 PCE), but P_Asset shows the 500 CHF
    # booked on that date - a wrong stock value between the posting date and the later receipt.
    # From the receipt on (2024-03-05) the value is right again: 1000 + 500 = 1500 = 15 * 100.
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-04 | 500     |
      | product      | 2024-03-05 | 1500    |

    # ── A later revaluation (start date entered as 03-05, saved as its posting date 03-06) books only its own step:
    #    100 PCE * (18 - 15) = 300 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation2 | acctSchema      | MovingAverageInvoice | 2024-03-05          | 2024-03-06 |
    And create lines for cost revaluation revaluation2
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation2         | product      | 18           |
    And the cost revaluation identified by revaluation2 is completed
    And Wait until documents revaluation2 are posted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID    | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 300 CHF     |             | 0   | revaluation2 | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 300 CHF     | 0   | revaluation2 | product      | 2024-03-06 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 100 PCE    | 1800 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | product      | warehouse      | 100 | 18.0000        | 1800.00          |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1800    |

  @Id:CostRevaluation_TC14
  Scenario: Two revaluations on consecutive days with the default dates each book only their own step
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation1 | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation1
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation1         | product      | 15           |
    And the cost revaluation identified by revaluation1 is completed
    And Wait until documents revaluation1 are posted

    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation2 | acctSchema      | MovingAverageInvoice | 2024-03-07          | 2024-03-07 |
    And create lines for cost revaluation revaluation2
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation2         | product      | 18           |
    And the cost revaluation identified by revaluation2 is completed
    And Wait until documents revaluation2 are posted

    Then Fact_Acct records balances for documents revaluation1 are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |
    And Fact_Acct records balances for documents revaluation2 are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 300 CHF       |
      | P_CostAdjustment_Acct | -300 CHF      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID    | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation1 | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation1 | product      | 2024-03-06 |
      | P_Asset_Acct          | 300 CHF     |             | 0   | revaluation2 | product      | 2024-03-07 |
      | P_CostAdjustment_Acct |             | 300 CHF     | 0   | revaluation2 | product      | 2024-03-07 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 100 PCE    | 1800 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-08 | product      | warehouse      | 100 | 18.0000        | 1800.00          |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1500    |
      | product      | 2024-03-07 | 1800    |

  @Id:CostRevaluation_TC15
  Scenario: Only the revaluation's cost element is revalued - the product's cost for another costing method keeps its value
    And cost elements for material costing methods AveragePO are active
    And metasfresh contains M_Products:
      | Identifier         | X12DE355 | M_Product_Category_ID |
      | productTwoElements | PCE      | productCategory       |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID       | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID    |
      | inventoryTwoElems | inventoryTwoElemsLine | 2024-03-05   | warehouse      | productTwoElements | 0       | 100      | PCE          | 10        | huTwoElems |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID       | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productTwoElements | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |
      | acctSchema      | productTwoElements | AveragePO            | 10.0000 CHF      | 100 PCE    |

    # ── Revalue the Moving Average Invoice cost only ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID       | NewCostPrice |
      | revaluation          | productTwoElements | 15           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID       | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productTwoElements | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    |
      | acctSchema      | productTwoElements | AveragePO            | 10.0000 CHF      | 100 PCE    |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID       | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | productTwoElements | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | productTwoElements | 2024-03-06 |
    # ── The stock is valued with the schema's costing method (Moving Average Invoice): 15 * 100 ──
    And expect inventory valuation report
      | Date       | M_Product_ID       | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | productTwoElements | warehouse      | 100 | 15.0000        | 1500.00          |
    And expect P_Asset balance for product
      | M_Product_ID       | DateAcct   | Balance |
      | productTwoElements | 2024-03-06 | 1500    |

  @Id:CostRevaluation_TC16
  Scenario: Seed-cost revaluation sets only the revaluation's cost element - the other seeded cost rows keep their default
    And cost elements for material costing methods AveragePO are active
    And metasfresh contains M_Products:
      | Identifier         | X12DE355 | M_Product_Category_ID |
      | productSeedTwoElem | PCE      | productCategory       |
    And remove current costs
      | M_Product_ID       |
      | productSeedTwoElem |

    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID       | NewCostPrice |
      | revaluation          | productSeedTwoElem | 12.35        |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID       | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productSeedTwoElem | MovingAverageInvoice | 12.3500 CHF      | 0 PCE      |
      | acctSchema      | productSeedTwoElem | AveragePO            | 0 CHF            | 0 PCE      |
    And expect P_Asset balance for product
      | M_Product_ID       | DateAcct   | Balance |
      | productSeedTwoElem | 2024-03-06 | 0       |

  @Id:CostRevaluation_TC17
  Scenario: Revaluing a cost element that is not the accounting schema's costing method changes only that cost, with no GL impact
    And cost elements for material costing methods AveragePO are active
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 | M_Product_Category_ID |
      | productStatistic | PCE      | productCategory       |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID   | M_InventoryLine_ID   | MovementDate | M_Warehouse_ID | M_Product_ID     | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID   |
      | inventoryStatist | inventoryStatistLine | 2024-03-05   | warehouse      | productStatistic | 0       | 100      | PCE          | 10        | huStatist |

    # ── Revalue the AveragePO cost; the accounting schema values stock with Moving Average Invoice ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | AveragePO        | 2024-03-06          | 2024-03-06 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID     | NewCostPrice |
      | revaluation          | productStatistic | 15           |
    And the cost revaluation identified by revaluation is completed

    Then no Fact_Acct records are found for documents revaluation
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productStatistic | AveragePO            | 15.0000 CHF      | 100 PCE    |
      | acctSchema      | productStatistic | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |
    # ── The stock value (Moving Average Invoice) is unchanged: 10 * 100 ──
    And expect inventory valuation report
      | Date       | M_Product_ID     | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | productStatistic | warehouse      | 100 | 10.0000        | 1000.00          |
    And expect P_Asset balance for product
      | M_Product_ID     | DateAcct   | Balance |
      | productStatistic | 2024-03-06 | 1000    |

  @Id:CostRevaluation_TC18
  Scenario: Year-start revaluation - a posting date before several months of stock movements books the stock at posting on that date (known limitation), and a later revaluation books only its own step
    # ── Movements after the Background stock (100 PCE @ 10): consume 20 on 03-10, receive 50 @ 10 on 04-15 -> 130 PCE ──
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease | inventoryDecreaseLine | 2024-03-10   | warehouse      | product      | 100     | 80       | PCE          | hu      |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID    |
      | inventoryIncrease | inventoryIncreaseLine | 2024-04-15   | warehouse      | product      | 80      | 130      | PCE          | 10        | huIncrease |

    # ── "Year start" of this fixture: 03-06, before both movements. Forward-only, it books the stock on hand at posting
    #    130 PCE * (12 - 10) = 260 CHF, dated 03-06 ──
    When metasfresh contains M_CostRevaluation:
      | Identifier           | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationYearStart | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluationYearStart
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationYearStart | product      | 12           |
    And the cost revaluation identified by revaluationYearStart is completed
    And Wait until documents revaluationYearStart are posted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID            | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 260 CHF     |             | 0   | revaluationYearStart | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 260 CHF     | 0   | revaluationYearStart | product      | 2024-03-06 |
    # 130 PCE * 12 = 1560 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 12.0000 CHF      | 130 PCE    | 1560 CHF     |
    # Known limitation (accepted, documented): on 03-06 the stock is 100 PCE, but P_Asset is 1000 + 260 = 1260 CHF
    # (not 100 * 12 = 1200) - a wrong stock value until the last movement. From the 04-15 receipt on it is right
    # again: 1000 + 260 - 200 + 500 = 1560 = 12 * 130.
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1260    |
      | product      | 2024-04-15 | 1560    |

    # ── A later revaluation (start date entered as 03-07, saved as its posting date 05-01) books only its own step:
    #    130 PCE * (13 - 12) = 130 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier       | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationLater | acctSchema      | MovingAverageInvoice | 2024-03-07          | 2024-05-01 |
    And create lines for cost revaluation revaluationLater
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationLater     | product      | 13           |
    And the cost revaluation identified by revaluationLater is completed
    And Wait until documents revaluationLater are posted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID        | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 130 CHF     |             | 0   | revaluationLater | product      | 2024-05-01 |
      | P_CostAdjustment_Acct |             | 130 CHF     | 0   | revaluationLater | product      | 2024-05-01 |
    # 130 PCE * 13 = 1690 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 13.0000 CHF      | 130 PCE    | 1690 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-05-02 | product      | warehouse      | 130 | 13.0000        | 1690.00          |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-05-01 | 1690    |

  @Id:CostRevaluation_TC19
  Scenario: A back-dated revaluation is accepted while a later one is completed but not posted, and the revaluation posted last sets the price
    # ── Complete a revaluation on 03-10 while accounting is off, so it has no posting (and no cost detail) yet ──
    Given set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier       | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationLater | acctSchema      | MovingAverageInvoice | 2024-03-10          | 2024-03-10 |
    And create lines for cost revaluation revaluationLater
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationLater     | product      | 15           |
    And the cost revaluation identified by revaluationLater is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And validate M_CostRevaluation:
      | Identifier       | DocStatus | Posted |
      | revaluationLater | CO        | false  |

    # ── Back-dating before it is accepted: 100 PCE * (12 - 10) = 200 CHF on 03-04 ──
    When metasfresh contains M_CostRevaluation:
      | Identifier          | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationBackDate | acctSchema      | MovingAverageInvoice | 2024-03-04          | 2024-03-04 |
    And create lines for cost revaluation revaluationBackDate
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationBackDate  | product      | 12           |
    And the cost revaluation identified by revaluationBackDate is completed
    And Wait until documents revaluationBackDate are posted
    Then validate M_CostRevaluation:
      | Identifier          | DocStatus | Processed |
      | revaluationBackDate | CO        | true      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID           | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 200 CHF     |             | 0   | revaluationBackDate | product      | 2024-03-04 |
      | P_CostAdjustment_Acct |             | 200 CHF     | 0   | revaluationBackDate | product      | 2024-03-04 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 12.0000 CHF      | 100 PCE    | 1200 CHF     |

    # ── The 03-10 revaluation is posted now: it books against the price at posting 100 PCE * (15 - 12) = 300 CHF,
    #    and, posted last, it sets the current cost price ──
    When the documents revaluationLater are reposted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID        | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 300 CHF     |             | 0   | revaluationLater | product      | 2024-03-10 |
      | P_CostAdjustment_Acct |             | 300 CHF     | 0   | revaluationLater | product      | 2024-03-10 |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluationLater     | product      | 100        | 12               | 300      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-04 | 200     |
      | product      | 2024-03-05 | 1200    |
      | product      | 2024-03-10 | 1500    |

  @Id:CostRevaluation_TC20
  Scenario: A year-start correction entered after a posted monthly revaluation is accepted and books against the current price
    # ── Month-end revaluation 03-31: 100 PCE * (12 - 10) = 200 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier         | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationMonthly | acctSchema      | MovingAverageInvoice | 2024-03-31          | 2024-03-31 |
    And create lines for cost revaluation revaluationMonthly
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationMonthly   | product      | 12           |
    And the cost revaluation identified by revaluationMonthly is completed
    And Wait until documents revaluationMonthly are posted
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID          | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 200 CHF     |             | 0   | revaluationMonthly | product      | 2024-03-31 |
      | P_CostAdjustment_Acct |             | 200 CHF     | 0   | revaluationMonthly | product      | 2024-03-31 |

    # ── A year-start correction entered afterwards, dated before the monthly one, is accepted:
    #    it books the stock on hand against the current price 100 PCE * (11 - 12) = -100 CHF on 03-06 ──
    When metasfresh contains M_CostRevaluation:
      | Identifier           | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationYearStart | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluationYearStart
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationYearStart | product      | 11           |
    And the cost revaluation identified by revaluationYearStart is completed
    And Wait until documents revaluationYearStart are posted
    Then validate M_CostRevaluation:
      | Identifier           | DocStatus | Processed |
      | revaluationYearStart | CO        | true      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID            | M_Product_ID | DateAcct   |
      | P_CostAdjustment_Acct | 100 CHF     |             | 0   | revaluationYearStart | product      | 2024-03-06 |
      | P_Asset_Acct          |             | 100 CHF     | 0   | revaluationYearStart | product      | 2024-03-06 |
    # 100 PCE * 11 = 1100 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 11.0000 CHF      | 100 PCE    | 1100 CHF     |
    # Known limitation (accepted, documented): between 03-06 and 03-31 P_Asset is 1000 - 100 = 900 CHF; from 03-31 on 1100 = 11 * 100
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 900     |
      | product      | 2024-03-31 | 1100    |

  @Id:CostRevaluation_TC21
  Scenario: A revaluation evaluated before another one was completed and posted is re-evaluated against the new current price when completing it
    # ── Evaluate (Run) the back-dated revaluation: 100 PCE * (12 - 10) = 200 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier          | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationBackDate | acctSchema      | MovingAverageInvoice | 2024-03-04          | 2024-03-04 |
    And create lines for cost revaluation revaluationBackDate
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationBackDate  | product      | 12           |
    And the cost revaluation identified by revaluationBackDate is evaluated
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt | IsRevaluated |
      | revaluationBackDate  | product      | 100        | 10               | 200      | true         |

    # ── Meanwhile a later revaluation is completed and posted: 100 PCE * (15 - 10) = 500 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier       | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationLater | acctSchema      | MovingAverageInvoice | 2024-03-10          | 2024-03-10 |
    And create lines for cost revaluation revaluationLater
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationLater     | product      | 15           |
    And the cost revaluation identified by revaluationLater is completed
    And Wait until documents revaluationLater are posted
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID        | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluationLater | product      | 2024-03-10 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluationLater | product      | 2024-03-10 |

    # ── Completing the back-dated one now is accepted: Complete re-evaluates it against the new current price 15:
    #    100 PCE * (12 - 15) = -300 CHF on 03-04 ──
    When the cost revaluation identified by revaluationBackDate is completed
    And Wait until documents revaluationBackDate are posted
    Then validate M_CostRevaluation:
      | Identifier          | DocStatus | Processed |
      | revaluationBackDate | CO        | true      |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluationBackDate  | product      | 100        | 15               | -300     |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID           | M_Product_ID | DateAcct   |
      | P_CostAdjustment_Acct | 300 CHF     |             | 0   | revaluationBackDate | product      | 2024-03-04 |
      | P_Asset_Acct          |             | 300 CHF     | 0   | revaluationBackDate | product      | 2024-03-04 |
    # 100 PCE * 12 = 1200 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 12.0000 CHF      | 100 PCE    | 1200 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-11 | product      | warehouse      | 100 | 12.0000        | 1200.00          |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-10 | 1200    |

  @Id:CostRevaluation_TC22
  Scenario: Run Revaluation is accepted while a later revaluation is completed but not posted, and creates the detail lines
    # ── Complete a revaluation on 03-10 while accounting is off: no posting and no cost detail yet ──
    Given set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier       | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationLater | acctSchema      | MovingAverageInvoice | 2024-03-10          | 2024-03-10 |
    And create lines for cost revaluation revaluationLater
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationLater     | product      | 15           |
    And the cost revaluation identified by revaluationLater is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And validate M_CostRevaluation:
      | Identifier       | DocStatus | Posted |
      | revaluationLater | CO        | false  |

    # ── Run Revaluation on a back-dated revaluation is accepted: the line is evaluated against the current price 10
    #    (the 03-10 revaluation is not posted, so M_Cost is unchanged): 100 PCE * (12 - 10) = 200 CHF.
    #    IsRevaluated=true also asserts the line's detail lines add up to that 200 CHF value difference ──
    When metasfresh contains M_CostRevaluation:
      | Identifier          | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationBackDate | acctSchema      | MovingAverageInvoice | 2024-03-04          | 2024-03-04 |
    And create lines for cost revaluation revaluationBackDate
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationBackDate  | product      | 12           |
    And the cost revaluation identified by revaluationBackDate is evaluated
    Then validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt | IsRevaluated |
      | revaluationBackDate  | product      | 100        | 10               | 200      | true         |
    And validate M_CostRevaluation:
      | Identifier          | DocStatus | Processed |
      | revaluationBackDate | DR        | false     |

  @Id:CostRevaluation_TC24
  Scenario: Stock issued between Complete and posting - the posting books the stock on hand at posting, and a repost or an unpost books the same amount
    # ── Complete a revaluation 10 -> 15 CHF on 03-06 while accounting is off: Complete shows 100 PCE * 5 = 500 CHF, nothing is booked yet ──
    Given set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Posted |
      | revaluation | CO        | false  |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluation          | product      | 100        | 10               | 500      |

    # ── 20 PCE are issued on 03-06 before the revaluation is posted: booked at the old cost 10 CHF -> 80 PCE @ 10 CHF ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease | inventoryDecreaseLine | 2024-03-06   | warehouse      | product      | 100     | 80       | PCE          | hu      |
    And Fact_Acct records balances for documents inventoryDecrease are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 80 PCE     |

    # ── Posting books 80 PCE * (15 - 10) = 400 CHF (not the 500 CHF shown at Complete); the line shows the booked values ──
    When the documents revaluation are reposted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 400 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 400 CHF     | 0   | revaluation | product      | 2024-03-06 |
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Posted |
      | revaluation | CO        | true   |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt | IsRevaluated |
      | revaluation          | product      | 80         | 10               | 400      | true         |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 80 PCE     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | product      | warehouse      | 80  | 15.0000        | 1200.00          |
    # 1000 - 200 + 400 = 1200 = 15 * 80
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1200    |

    # ── 10 PCE are issued on 03-07 (a later-day cost change), then the revaluation is reposted: it books the same 400 CHF and leaves M_Cost alone ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID     | M_InventoryLine_ID     | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease2 | inventoryDecrease2Line | 2024-03-07   | warehouse      | product      | 80      | 70       | PCE          | hu      |
    When the documents revaluation are reposted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 400 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 400 CHF     | 0   | revaluation | product      | 2024-03-06 |
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Posted |
      | revaluation | CO        | true   |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluation          | product      | 80         | 10               | 400      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 70 PCE     |

    # ── Unpost (as the accountant's "unpost" does: facts deleted, Posted=N, queued for repost; the cost detail is kept):
    #    the queued repost books the same 400 CHF again, reusing the cost detail, and leaves M_Cost alone ──
    When the documents revaluation are unposted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 400 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 400 CHF     | 0   | revaluation | product      | 2024-03-06 |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluation          | product      | 80         | 10               | 400      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 70 PCE     |
    # 1200 - 10 * 15 = 1050 = 15 * 70
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-07 | 1050    |

  @Id:CostRevaluation_TC27
  Scenario: A revaluation whose posting failed because its period is closed can be voided, and the voided revaluation books nothing
    # ── Complete a revaluation 10 -> 15 CHF on 03-06 while accounting is off: nothing is booked yet ──
    Given set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Posted |
      | revaluation | CO        | false  |

    # ── The period of 03-06 is closed: the posting fails with the period-closed posting error and books nothing ──
    And the period of 2024-03-06 is closed
    When reposting the documents revaluation fails with posting status p
    Then no Fact_Acct records exist for documents revaluation
    And the cost revaluation identified by revaluation has no M_CostDetails
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |

    # ── The periods are open again (so a period check cannot interfere), then the revaluation is voided ──
    And the accounting periods are controlled automatically
    When the cost revaluation identified by revaluation is voided
    Then validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed |
      | revaluation | VO        | true      |
    And no Fact_Acct records exist for documents revaluation
    And the cost revaluation identified by revaluation has no M_CostDetails
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |

    # ── The voided revaluation is reposted: posted (it leaves the posting-error list), still no facts, no cost detail, M_Cost unchanged ──
    When the documents revaluation are reposted
    Then validate M_CostRevaluation:
      | Identifier  | DocStatus | Posted |
      | revaluation | VO        | true   |
    And no Fact_Acct records are found for documents revaluation
    And the cost revaluation identified by revaluation has no M_CostDetails
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1000    |

  @Id:CostRevaluation_TC28
  Scenario: A vendor invoice matched after a revaluation adds its price variance on top of the revalued price (Moving Average Invoice)
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 | M_Product_Category_ID |
      | productPurchased | PCE      | productCategory       |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | purchasePS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePL | purchasePS         | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | purchasePLV | purchasePL     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID     | PriceStd | C_UOM_ID |
      | purchasePLV            | productPurchased | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | purchasePS         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | CH           | Y               | Y               |

    # ── Receive 100 PCE at the PO price 10 CHF: 1000 CHF stock value ──
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID     | QtyEntered | Price |
      | po_l1          | vendor        | 2021-04-14  | POO         | warehouse      | productPurchased | 100        | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po_l1          | receipt    | receipt_line1  |
    And Wait until documents receipt are posted
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productPurchased | MovingAverageInvoice | 10 CHF           | 100 PCE    | 1000 CHF     |

    # ── Revaluate 10 -> 12 CHF: 100 PCE * 2 = 200 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2021-04-14 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID     | NewCostPrice |
      | revaluation          | productPurchased | 12           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID     | DateAcct   | C_AcctSchema_ID |
      | P_Asset_Acct          | 200 CHF     |             | 0   | revaluation | productPurchased | 2021-04-14 | acctSchema      |
      | P_CostAdjustment_Acct |             | 200 CHF     | 0   | revaluation | productPurchased | 2021-04-14 | acctSchema      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productPurchased | MovingAverageInvoice | 12 CHF           | 100 PCE    | 1200 CHF     |

    # ── The next day the vendor invoice arrives at 12 CHF and is matched: Moving Average Invoice adds the invoice-vs-receipt variance
    #    100 PCE * (12 - 10) = 200 CHF to the stock still on hand, on top of the revalued price: 100 PCE @ 14 CHF ──
    Given metasfresh has date and time 2021-04-15T08:00:00+00:00[Europe/Berlin]
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | PriceEntered_Override | DateInvoiced | M_MatchInv_ID |
      | po_l1          | 12                    | 2021-04-15   | matchInv      |
    And Wait until documents matchInv are posted
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productPurchased | MovingAverageInvoice | 14 CHF           | 100 PCE    | 1400 CHF     |
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtSourceDr | AmtSourceCr | Qty      | Record_ID | M_Product_ID     | DateAcct   | C_AcctSchema_ID |
      | NotInvoicedReceipts_Acct | 1000 CHF    |             | 100 PCE  | matchInv  | productPurchased | 2021-04-15 | acctSchema      |
      | P_Asset_Acct             | 200 CHF     |             | 0        | matchInv  | productPurchased | 2021-04-15 | acctSchema      |
      | P_InventoryClearing_Acct |             | 1200 CHF    | -100 PCE | matchInv  | productPurchased | 2021-04-15 | acctSchema      |
    # 2021-04-14: receipt 1000 + revaluation 200 = 1200 = 12 * 100
    # 2021-04-15: + invoice match variance 200 = 1400 = 14 * 100
    And expect P_Asset balance for product
      | M_Product_ID     | DateAcct   | Balance |
      | productPurchased | 2021-04-14 | 1200    |
      | productPurchased | 2021-04-15 | 1400    |

  @Id:CostRevaluation_TC29
  Scenario: Zero delta - a revaluation to the current price books nothing and leaves the cost unchanged
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 10           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted
    Then validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed |
      | revaluation | CO        | true      |
    And no Fact_Acct records are found for documents revaluation
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    | 1000 CHF     |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1000    |

  @Id:CostRevaluation_TC30
  Scenario: Several products in one revaluation - one goes up, one goes down, each is booked for its own product
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
      | revaluation          | product2     | 18           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted
    # product: 100 PCE * (15 - 10) = 500 CHF; product2: 50 PCE * (18 - 20) = -100 CHF
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct | 100 CHF     |             | 0   | revaluation | product2     | 2024-03-06 |
      | P_Asset_Acct          |             | 100 CHF     | 0   | revaluation | product2     | 2024-03-06 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
      | acctSchema      | product2     | MovingAverageInvoice | 18.0000 CHF      | 50 PCE     | 900 CHF      |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1500    |
      | product2     | 2024-03-06 | 900     |

  @Id:CostRevaluation_TC31
  Scenario: Stock in several warehouses - one line per product without a locator, booked for the total stock
    # ── 40 more PCE of product @ 10 CHF in a second warehouse: 140 PCE in total ──
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse2     |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID      | M_InventoryLine_ID      | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID      |
      | inventoryWarehouse2 | inventoryWarehouse2Line | 2024-03-05   | warehouse2     | product      | 0       | 40       | PCE          | 10        | huWarehouse2 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 140 PCE    | 1400 CHF     |

    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted
    # one line for the product's whole stock: 140 PCE * (15 - 10) = 700 CHF, without a locator
    Then validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluation          | product      | 140        | 10               | 700      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   | M_Locator_ID |
      | P_Asset_Acct          | 700 CHF     |             | 0   | revaluation | product      | 2024-03-06 | null         |
      | P_CostAdjustment_Acct |             | 700 CHF     | 0   | revaluation | product      | 2024-03-06 | null         |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 140 PCE    | 2100 CHF     |
    # 1400 + 700 = 2100 = 15 * 140
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 2100    |

  @Id:CostRevaluation_TC32
  Scenario: A revaluation whose posting failed because its period is closed books once when it is reposted after the period is opened
    # ── Complete a revaluation 10 -> 15 CHF on 03-06 while accounting is off: nothing is booked yet ──
    Given set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And create lines for cost revaluation revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled

    # ── The period of 03-06 is closed: the posting fails with the period-closed posting error, books nothing, leaves M_Cost alone ──
    And the period of 2024-03-06 is closed
    When reposting the documents revaluation fails with posting status p
    Then no Fact_Acct records exist for documents revaluation
    And the cost revaluation identified by revaluation has no M_CostDetails
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    |

    # ── The period is opened again and the revaluation is reposted: it books 100 PCE * (15 - 10) = 500 CHF once ──
    And the accounting periods are controlled automatically
    When the documents revaluation are reposted
    Then validate M_CostRevaluation:
      | Identifier  | DocStatus | Posted |
      | revaluation | CO        | true   |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | product      | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | product      | 2024-03-06 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | product      | 2024-03-06 | 1500    |

  @Id:CostRevaluation_TC34
  Scenario: Over-issued stock (Moving Average Invoice floors the stock at zero) - the revaluation books nothing, and the next receipt sets the price
    And metasfresh contains M_Products:
      | Identifier    | X12DE355 | M_Product_Category_ID |
      | productTraded | PCE      | productCategory       |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | purchasePS |
      | salesPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePL | purchasePS         | CH           | CHF           | false |
      | salesPL    | salesPS            | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | purchasePLV | purchasePL     |
      | salesPLV    | salesPL        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID |
      | purchasePLV            | productTraded | 10.0     | PCE      |
      | salesPLV               | productTraded | 19.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | purchasePS         |
      | customer   | N        | Y          | salesPS            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation   | vendor        | CH           | Y               | Y               |
      | customerLocation | customer      | CH           | Y               | Y               |

    # ── Receive 10 PCE @ 10 CHF, ship them, then over-ship 5 PCE (force delivery): the stock is floored at 0 PCE ──
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID  | QtyEntered | Price |
      | po_l1          | vendor        | 2021-04-14  | POO         | warehouse      | productTraded | 10         | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po_l1          | receipt    | receipt_line1  |
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID  | QtyEntered | Price |
      | soFull_l1      | customer      | 2021-04-14  | SOO         | warehouse      | productTraded | 10         | 19    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID     |
      | soFull_l1      | shipmentFull_line1 |
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID  | QtyEntered | Price | DeliveryRule |
      | soOver_l1      | customer      | 2021-04-14  | SOO         | warehouse      | productTraded | 5          | 19    | F            |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID     |
      | soOver_l1      | shipmentOver_line1 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID  | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productTraded | MovingAverageInvoice | 10 CHF           | 0 PCE      |

    # ── Revaluate 10 -> 12 CHF: no stock on hand, so the revaluation books 0 CHF (no facts) and only sets the price ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2021-04-14 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID  | NewCostPrice |
      | revaluation          | productTraded | 12           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted
    Then no Fact_Acct records are found for documents revaluation
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID  | CurrentQty | DeltaAmt |
      | revaluation          | productTraded | 0          | 0        |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID  | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productTraded | MovingAverageInvoice | 12 CHF           | 0 PCE      |

    # ── The next receipt of 15 PCE @ 9 CHF blends onto the zero stock: its price replaces the revalued one ──
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID  | QtyEntered | Price |
      | poNext_l1      | vendor        | 2021-04-14  | POO         | warehouse      | productTraded | 15         | 9     |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID  | M_InOutLine_ID    |
      | poNext_l1      | receiptNext | receiptNext_line1 |
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID  | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productTraded | MovingAverageInvoice | 9 CHF            | 15 PCE     |

  @Id:CostRevaluation_TC37
  Scenario: Seed line with stock received between Complete and posting - the posting books the stock on hand at posting
    # ── A product with NO M_Cost row and no stock: the quick-input seed line has qty 0 ──
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 | M_Product_Category_ID |
      | productSeed | PCE      | productCategory       |
    And remove current costs
      | M_Product_ID |
      | productSeed  |

    # ── Complete the seed revaluation (new price 12 CHF) while accounting is off: nothing is booked yet ──
    Given set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | productSeed  | 12           |
    And the cost revaluation identified by revaluation is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | DeltaAmt |
      | revaluation          | productSeed  | 0          | 0        |

    # ── 10 PCE are received @ 10 CHF before the revaluation is posted ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID   | M_InventoryLine_ID   | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inventoryReceipt | inventoryReceiptLine | 2024-03-06   | warehouse      | productSeed  | 0       | 10       | PCE          | 10        | huSeed  |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productSeed  | MovingAverageInvoice | 10.0000 CHF      | 10 PCE     |

    # ── Posting books the stock on hand at posting 10 PCE * (12 - 10) = 20 CHF ──
    When the documents revaluation are reposted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID | DateAcct   |
      | P_Asset_Acct          | 20 CHF      |             | 0   | revaluation | productSeed  | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 20 CHF      | 0   | revaluation | productSeed  | 2024-03-06 |
    And validate M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | CurrentQty | CurrentCostPrice | DeltaAmt |
      | revaluation          | productSeed  | 10         | 10               | 20       |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productSeed  | MovingAverageInvoice | 12.0000 CHF      | 10 PCE     | 120 CHF      |
    And expect P_Asset balance for product
      | M_Product_ID | DateAcct   | Balance |
      | productSeed  | 2024-03-06 | 120     |

  @Id:CostRevaluation_TC38
  Scenario: Fractional price - the booked amount is rounded to the currency precision and the stock value matches price times quantity
    # ── 3 PCE @ 10 CHF ──
    And metasfresh contains M_Products:
      | Identifier        | X12DE355 | M_Product_Category_ID |
      | productFractional | PCE      | productCategory       |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID      | M_InventoryLine_ID      | MovementDate | M_Warehouse_ID | M_Product_ID      | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID      |
      | inventoryFractional | inventoryFractionalLine | 2024-03-05   | warehouse      | productFractional | 0       | 3        | PCE          | 10        | huFractional |

    # ── Revaluate 10 -> 10.3333 CHF: 3 PCE * 0.3333 = 0.9999 CHF, booked as 1.00 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06 |
    And quick-input cost revaluation line:
      | M_CostRevaluation_ID | M_Product_ID      | NewCostPrice |
      | revaluation          | productFractional | 10.3333      |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID      | DateAcct   |
      | P_Asset_Acct          | 1.00 CHF    |             | 0   | revaluation | productFractional | 2024-03-06 |
      | P_CostAdjustment_Acct |             | 1.00 CHF    | 0   | revaluation | productFractional | 2024-03-06 |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID      | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | productFractional | MovingAverageInvoice | 10.3333 CHF      | 3 PCE      |
    And expect P_Asset balance for product
      | M_Product_ID      | DateAcct   | Balance |
      | productFractional | 2024-03-06 | 31.00   |
    # P_Asset 31.00 vs. M_Cost 10.3333 * 3 = 30.9999: equal within the currency's standard precision
    And expect P_Asset balance for product equals its current cost price times quantity
      | C_AcctSchema_ID | M_Product_ID      | M_CostElement_ID     | DateAcct   |
      | acctSchema      | productFractional | MovingAverageInvoice | 2024-03-06 |
