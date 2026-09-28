@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@ghActions:run_on_executor7
@Id:S30984
Feature: Cost Revaluation / Kosten Neubewertung
## F1500: Costing

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00[Europe/Berlin]
    And documents are accounted immediately
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | PCE      |
      | product2   | PCE      |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And load C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | A             |
    # ── Starting stock: product = 100 PCE @ 10 CHF; product2 = 50 PCE @ 20 CHF (the "noise" product) ──
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inventory      | inventoryLine      | 2024-03-05   | warehouse      | product      | 0       | 100      | PCE          | 10        | hu      |
      | inventory2     | inventory2Line     | 2024-03-05   | warehouse      | product2     | 0       | 50       | PCE          | 20        | hu2     |

  @Id:S30984_TC1
  Scenario: Increase - completing a cost revaluation raises the current cost price and books the positive delta
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 10.0000 CHF      | 100 PCE    |

    # ── Revaluate the product's current cost from 10 to 15 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | AveragePO        | 2024-03-06          | 2024-03-06 |
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
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 15.0000 CHF      | 100 PCE    |
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

  @Id:S30984_TC2
  Scenario: Decrease - completing a cost revaluation lowers the current cost price and books the negative delta to the cost-adjustment account
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 10.0000 CHF      | 100 PCE    |

    # ── Revaluate the product's current cost from 10 down to 8 CHF ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | AveragePO        | 2024-03-06          | 2024-03-06 |
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
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 8.0000 CHF       | 100 PCE    |
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

  @Id:S30984_TC4
  Scenario: Forward-only default - a today-dated revaluation with no EvaluationStartDate restates nothing already posted
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | AveragePO        | 10.0000 CHF      | 100 PCE    | 1000 CHF     |

    # ── Revaluate the product's current cost from 10 to 18 CHF, WITHOUT specifying EvaluationStartDate.
    #    DateAcct is chosen clearly AFTER the posted inventory (2024-03-05) so the forward-only default
    #    is unambiguous — no same-day cost detail could be pulled in by an inclusive date-range boundary. ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID | DateAcct   |
      | revaluation | acctSchema      | AveragePO        | 2024-03-10 |
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

    # ── After: current cost 18 CHF for the 100 PCE on hand (CumulatedAmt reflects the new price going forward) ──
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | AveragePO        | 18.0000 CHF      | 100 PCE    | 1800 CHF     |
    # ── Forward-only proof: the already-posted 2024-03-05 valuation stays UNCHANGED (10.0000/1000.00) — only the
    #    forward date reflects the new price. A broken unset-guard that restated 2024-03-05 would fail this row. ──
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
      | 2024-03-11 | product      | warehouse      | 100 | 18.0000        | 1800.00          |

    # ── Positive delta 100 PCE * (18 - 10) = 800 CHF booked P_Asset DR / P_CostAdjustment CR (amount-only, Qty 0) ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Qty | Record_ID   | M_Product_ID |
      | P_Asset_Acct          | 800 CHF     |             | 0   | revaluation | product      |
      | P_CostAdjustment_Acct |             | 800 CHF     | 0   | revaluation | product      |
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 800 CHF       |
      | P_CostAdjustment_Acct | -800 CHF      |

  @Id:S30984_TC5
  Scenario: Retrospective - an earlier EvaluationStartDate still restates the posted cost detail (engine behavior intact)
    # ── Before: inventory value 1000 CHF, current cost 10 CHF / 100 PCE ──
    Then expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 10.0000        | 1000.00          |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | AveragePO        | 10.0000 CHF      | 100 PCE    | 1000 CHF     |

    # ── Revaluate from an EvaluationStartDate BEFORE the posted inventory (2024-03-05), so it gets replayed ──
    When metasfresh contains M_CostRevaluation:
      | Identifier  | C_AcctSchema_ID | M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | revaluation | acctSchema      | AveragePO        | 2024-03-01          | 2024-03-06 |
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
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | AveragePO        | 15.0000 CHF      | 100 PCE    | 1500 CHF     |
    # ── Retrospective proof: the already-posted 2024-03-05 valuation is REWRITTEN to the new price (15.0000/1500.00),
    #    unlike TC4's forward-only case where that same date stayed at 10.0000/1000.00. ──
    And expect inventory valuation report
      | Date       | M_Product_ID | M_Warehouse_ID | Qty | Acct_CostPrice | Acct_ExpectedAmt |
      | 2024-03-05 | product      | warehouse      | 100 | 15.0000        | 1500.00          |
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

  @Id:S30984_TC9
  Scenario: Zero-stock init - revaluing a product with CurrentQty=0 sets the current cost with no GL impact
    # ── Seed a zero-stock product: an M_Cost row exists (CurrentQty=0), but nothing was ever posted for it ──
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 |
      | productZeroStock | PCE      |
    And update current costs
      | M_Product_ID     |
      | productZeroStock |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productZeroStock | AveragePO        | 0 CHF            | 0 PCE      | 0 CHF        |

    # ── Revaluate to an initial price ──
    When metasfresh contains M_CostRevaluation:
      | Identifier      | C_AcctSchema_ID | M_CostElement_ID | DateAcct   |
      | revaluationZero | acctSchema      | AveragePO        | 2024-03-06 |
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
      | C_AcctSchema_ID | M_Product_ID     | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | productZeroStock | AveragePO        | 12.0000 CHF      | 0 PCE      | 0 CHF        |

    # ── No GL impact: Qty 0 means the delta is 0 CHF, so no Fact_Acct rows are posted ──
    And no Fact_Acct records are found for documents revaluationZero
