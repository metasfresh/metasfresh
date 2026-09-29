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
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00[Europe/Berlin]
    And documents are accounted immediately
    And load C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    # Costing method = MAI (Moving Average Invoice, code 'M'). All scenarios below use the MovingAverageInvoice
    # material cost element accordingly. Under MAI the on-hand cost is still seeded from the physical inventory's
    # CostPrice at qty 0->N (first cost event), so every current-cost / valuation / delta figure matches MovingAverageInvoice.
    # Costing level = Client (code 'C'), pinned explicitly so the scenarios don't depend on the schema's preset level.
    # Pinned BEFORE any product is created: creating a product seeds its default M_Cost rows at the costing level in
    # effect at that moment, so a product created under a preset Organization level would keep org-level rows next to
    # the client-level rows its stock later creates (two cost segments -> two revaluation lines per product).
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod | CostingLevel |
      | acctSchema      | M             | C            |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | PCE      |
      | product2   | PCE      |
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

    # ── Revaluate the product's current cost from 10 to 15 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And cost revaluation lines are created for revaluation
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

    # ── Positive delta 100 PCE * (15 - 10) = 500 CHF booked P_Asset DR / P_CostAdjustment CR (amount-only, Qty 0) ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | product      |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | product      |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |

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
    And cost revaluation lines are created for revaluation
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
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID |
      | P_CostAdjustment_Acct | 200 CHF     |             | 0   | revaluation | product      |
      | P_Asset_Acct          |             | 200 CHF     | 0   | revaluation | product      |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_CostAdjustment_Acct | 200 CHF       |
      | P_Asset_Acct          | -200 CHF      |

  @Id:CostRevaluation_TC4
  Scenario: Forward-only default - a today-dated revaluation with no EvaluationStartDate restates nothing already posted
    # ── Consume 20 PCE at the old cost 10 CHF BEFORE the revaluation (inventory count 100 -> 80 on 2024-03-07):
    #    posted -200 CHF on the asset account; this already-posted consumption must stay unchanged ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID     | M_InventoryLine_ID     | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease  | inventoryDecreaseLine  | 2024-03-07   | warehouse      | product      | 100     | 80       | PCE          | hu      |
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
    And cost revaluation lines are created for revaluation
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
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID |
      | P_Asset_Acct          | 640 CHF     |             | 0   | revaluation | product      |
      | P_CostAdjustment_Acct |             | 640 CHF     | 0   | revaluation | product      |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 640 CHF       |
      | P_CostAdjustment_Acct | -640 CHF      |

  @Id:CostRevaluation_TC5
  Scenario: Retrospective - an earlier EvaluationStartDate still restates the posted cost detail (engine behavior intact)
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10.0000 CHF      | 100 PCE    | 1000 CHF     |

    # ── Revaluate from an EvaluationStartDate BEFORE the posted inventory (2024-03-05), so it gets replayed ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | MovingAverageInvoice | 2024-03-01          | 2024-03-06 |
    And cost revaluation lines are created for revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 15           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── EvaluationStartDate must NOT be clobbered by the forward-only default — the user's own earlier date survives ──
    And validate M_CostRevaluation:
      | Identifier  | DocStatus | Processed | EvaluationStartDate |
      | revaluation | CO        | true      | 2024-03-01          |

    # ── After: current cost 15 CHF; CumulatedAmt RECALCULATED to 1500 CHF (100 PCE replayed at the new price) — proves the retrospective replay happened ──
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
    # ── The earlier EvaluationStartDate survives the forward-only default and restates the CURRENT cost
    #    (15.0000 / CumulatedAmt 1500, asserted by the "validate current costs" step above). Already-posted
    #    accounting is NOT backdated: the correction posts forward-dated at DateAcct 2024-03-06, and the
    #    inventory valuation report is Fact_Acct as-of-date (DateAcct <= p_DateAcct), so the already-posted
    #    2024-03-05 valuation stays UNCHANGED at 10.0000/1000.00 (the same row the forward-only-default scenario asserts) — correct accounting. ──
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
      | 2024-03-07 | product      | warehouse      | 100 | 15.0000        | 1500.00          |

    # ── Net delta 100 PCE * (15 - 10) = 500 CHF booked P_Asset DR / P_CostAdjustment CR — same net posting as the forward-only case ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID |
      | P_Asset_Acct          | 500 CHF     |             | 0   | revaluation | product      |
      | P_CostAdjustment_Acct |             | 500 CHF     | 0   | revaluation | product      |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |

  @Id:CostRevaluation_TC9
  Scenario: Zero-stock init - revaluing a product with CurrentQty=0 sets the current cost with no GL impact
    # ── Seed a zero-stock product: an M_Cost row exists (CurrentQty=0), but nothing was ever posted for it ──
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 |
      | productZeroStock | PCE      |
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
    And cost revaluation lines are created for revaluationZero
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

    # ── No GL impact: Qty 0 means the delta is 0 CHF, so no Fact_Acct rows are posted ──
    And no Fact_Acct records are found for documents revaluationZero

  @Id:CostRevaluation_TC10
  Scenario: Seed-cost - quick-input on a stocked product with no M_Cost row seeds the cost at qty 0 with no GL impact
    # ── A stocked product with NO M_Cost row (as after a migration that never set up costing for it) ──
    And metasfresh contains M_Products:
      | Identifier    | X12DE355 |
      | productNoCost | PCE      |
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

    # ── No GL impact: qty 0 means the delta is 0 CHF, so no Fact_Acct rows are posted ──
    And no Fact_Acct records are found for documents revaluationNoCost

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
    And cost revaluation lines are created for revaluation
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation          | product      | 18           |
    And the cost revaluation identified by revaluation is completed
    And Wait until documents revaluation are posted

    # ── The same-day consumption is inside the (inclusive, day-granular) evaluation window, but the revaluation books only
    #    the remaining stock's delta 80 PCE * (18 - 10) = 640 CHF, and the consumption keeps its posting at the old cost ──
    Then Fact_Acct records balances for documents inventoryDecrease are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |
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

  @Id:CostRevaluation_TC12
  Scenario: Second revaluation on the same day with the default dates is refused with an actionable message
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation1 | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And cost revaluation lines are created for revaluation1
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation1         | product      | 15           |
    And the cost revaluation identified by revaluation1 is completed
    And Wait until documents revaluation1 are posted
    And Fact_Acct records balances for documents revaluation1 are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |

    # ── Same day again: its evaluation window contains the first revaluation, which cannot be replayed ──
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation2 | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And cost revaluation lines are created for revaluation2
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation2         | product      | 18           |
    Then completing the cost revaluation identified by revaluation2 is refused with AD_Message "CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported"
    And validate M_CostRevaluation:
      | Identifier   | DocStatus | Processed |
      | revaluation2 | DR        | false     |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |

  @Id:CostRevaluation_TC13
  Scenario: Back-dated revaluation - a posting date before a replayed stock movement is accepted, and a later revaluation replaying the same movement books only its own step
    # ── Posting date 2024-03-04 is BEFORE the stock movement of 2024-03-05 that the window replays: accepted ──
    When metasfresh contains M_CostRevaluation:
      | Identifier          | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationBackDate | acctSchema      | MovingAverageInvoice | 2024-03-04          | 2024-03-04 |
    And cost revaluation lines are created for revaluationBackDate
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationBackDate  | product      | 15           |
    And the cost revaluation identified by revaluationBackDate is completed
    And Wait until documents revaluationBackDate are posted
    # no stock before 03-05 (0 PCE) + the 03-05 inventory restated 100 PCE * (15 - 10) = 500 CHF
    Then Fact_Acct records balances for documents revaluationBackDate are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_CostAdjustment_Acct | -500 CHF      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15.0000 CHF      | 100 PCE    | 1500 CHF     |

    # ── A later revaluation whose window replays the same 03-05 inventory (the back-dated revaluation lies before it) ──
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation2 | acctSchema      | MovingAverageInvoice | 2024-03-05          | 2024-03-06 |
    And cost revaluation lines are created for revaluation2
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation2         | product      | 18           |
    And the cost revaluation identified by revaluation2 is completed
    And Wait until documents revaluation2 are posted
    # the inventory is replayed from its restated 1500 CHF, not its original 1000 CHF: 100 PCE * (18 - 15) = 300 CHF (not 800)
    Then Fact_Acct records balances for documents revaluation2 are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 300 CHF       |
      | P_CostAdjustment_Acct | -300 CHF      |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 100 PCE    | 1800 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-07 | product      | warehouse      | 100 | 18.0000        | 1800.00          |

  @Id:CostRevaluation_TC14
  Scenario: Two revaluations on consecutive days with the default dates each book only their own step
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation1 | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And cost revaluation lines are created for revaluation1
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluation1         | product      | 15           |
    And the cost revaluation identified by revaluation1 is completed
    And Wait until documents revaluation1 are posted

    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluation2 | acctSchema      | MovingAverageInvoice | 2024-03-07          | 2024-03-07 |
    And cost revaluation lines are created for revaluation2
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
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 18.0000 CHF      | 100 PCE    | 1800 CHF     |

  @Id:CostRevaluation_TC15
  Scenario: Only the revaluation's cost element is revalued - the product's cost for another costing method keeps its value
    And cost elements for material costing methods AveragePO are active
    And metasfresh contains M_Products:
      | Identifier         | X12DE355 |
      | productTwoElements | PCE      |
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

  @Id:CostRevaluation_TC16
  Scenario: Seed-cost revaluation sets only the revaluation's cost element - the other seeded cost rows keep their default
    And cost elements for material costing methods AveragePO are active
    And metasfresh contains M_Products:
      | Identifier         | X12DE355 |
      | productSeedTwoElem | PCE      |
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

  @Id:CostRevaluation_TC17
  Scenario: Revaluing a cost element that is not the accounting schema's costing method changes only that cost, with no GL impact
    And cost elements for material costing methods AveragePO are active
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 |
      | productStatistic | PCE      |
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

  @Id:CostRevaluation_TC18
  Scenario: Year-start revaluation - back-dated before several months of stock movements restates them, and a later revaluation starting after it books only its own step
    # ── Movements after the Background stock (100 PCE @ 10): consume 20 on 03-10, receive 50 @ 10 on 04-15 ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inventoryDecrease | inventoryDecreaseLine | 2024-03-10   | warehouse      | product      | 100     | 80       | PCE          | hu      |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID    | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID    |
      | inventoryIncrease | inventoryIncreaseLine | 2024-04-15   | warehouse      | product      | 80      | 130      | PCE          | 10        | huIncrease |

    # ── "Year start" of this fixture: 03-06, before both movements ──
    When metasfresh contains M_CostRevaluation:
      | Identifier           | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationYearStart | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And cost revaluation lines are created for revaluationYearStart
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationYearStart | product      | 12           |
    And the cost revaluation identified by revaluationYearStart is completed
    And Wait until documents revaluationYearStart are posted
    # stock before the window 100 PCE * (12 - 10) = 200; decrease -20 PCE * 2 = -40; increase 50 PCE * 2 = 100 => 260 CHF
    Then Fact_Acct records balances for documents revaluationYearStart are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 260 CHF       |
      | P_CostAdjustment_Acct | -260 CHF      |
    # 130 PCE * 12 = 1560 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 12.0000 CHF      | 130 PCE    | 1560 CHF     |

    # ── A later revaluation starting 03-07 (after the year-start revaluation) replays both restated movements ──
    When metasfresh contains M_CostRevaluation:
      | Identifier       | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationLater | acctSchema      | MovingAverageInvoice | 2024-03-07          | 2024-05-01 |
    And cost revaluation lines are created for revaluationLater
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationLater     | product      | 13           |
    And the cost revaluation identified by revaluationLater is completed
    And Wait until documents revaluationLater are posted
    # rewound from the restated 12 (not 10): 100 PCE * (13 - 12) = 100; decrease -20 * 1 = -20; increase 50 * 1 = 50 => 130 CHF (not 390)
    Then Fact_Acct records balances for documents revaluationLater are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 130 CHF       |
      | P_CostAdjustment_Acct | -130 CHF      |
    # 130 PCE * 13 = 1690 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 13.0000 CHF      | 130 PCE    | 1690 CHF     |
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-05-02 | product      | warehouse      | 130 | 13.0000        | 1690.00          |

  @Id:CostRevaluation_TC19
  Scenario: A later revaluation that is completed but not yet posted still blocks back-dating before it
    # ── Complete a revaluation on 03-10 while accounting is off, so it has no posting (and no cost detail) yet ──
    And set sys config boolean value false for sys config org.adempiere.acct.Enabled
    When metasfresh contains M_CostRevaluation:
      | Identifier       | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationLater | acctSchema      | MovingAverageInvoice | 2024-03-10          | 2024-03-10 |
    And cost revaluation lines are created for revaluationLater
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationLater     | product      | 15           |
    And the cost revaluation identified by revaluationLater is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And validate M_CostRevaluation:
      | Identifier       | DocStatus | Posted |
      | revaluationLater | CO        | false  |

    # ── Back-dating before it is refused, naming the later revaluation's product and date ──
    When metasfresh contains M_CostRevaluation:
      | Identifier          | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationBackDate | acctSchema      | MovingAverageInvoice | 2024-03-04          | 2024-03-04 |
    And cost revaluation lines are created for revaluationBackDate
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationBackDate  | product      | 12           |
    Then completing the cost revaluation identified by revaluationBackDate is refused with AD_Message "CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported"
    And validate M_CostRevaluation:
      | Identifier          | DocStatus | Processed |
      | revaluationBackDate | DR        | false     |

  @Id:CostRevaluation_TC20
  Scenario: A posted monthly revaluation blocks a later-entered year-start correction before it
    # ── Month-end revaluation 03-31: 100 PCE * (12 - 10) = 200 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier         | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationMonthly | acctSchema      | MovingAverageInvoice | 2024-03-31          | 2024-03-31 |
    And cost revaluation lines are created for revaluationMonthly
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationMonthly   | product      | 12           |
    And the cost revaluation identified by revaluationMonthly is completed
    And Wait until documents revaluationMonthly are posted
    And Fact_Acct records balances for documents revaluationMonthly are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 200 CHF       |
      | P_CostAdjustment_Acct | -200 CHF      |

    # ── A year-start correction entered afterwards, starting before the monthly one, is refused ──
    When metasfresh contains M_CostRevaluation:
      | Identifier           | C_AcctSchema_ID | M_CostElement_ID     | EvaluationStartDate | DateAcct   |
      | revaluationYearStart | acctSchema      | MovingAverageInvoice | 2024-03-06          | 2024-03-06 |
    And cost revaluation lines are created for revaluationYearStart
    And update M_CostRevaluationLine:
      | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
      | revaluationYearStart | product      | 11           |
    Then completing the cost revaluation identified by revaluationYearStart is refused with AD_Message "CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported"
    And validate M_CostRevaluation:
      | Identifier           | DocStatus | Processed |
      | revaluationYearStart | DR        | false     |
    # nothing booked by the refused correction: 100 PCE * 12 = 1200 CHF
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 12.0000 CHF      | 100 PCE    | 1200 CHF     |
