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

    # ── Positive delta 100 PCE * (15 - 10) = 500 CHF booked P_Asset DR / P_Revenue CR (amount-only, Qty 0) ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID   | M_Product_ID |
      | P_Asset_Acct          | 500 CHF     |             | revaluation | product      |
      | P_Revenue_Acct        |             | 500 CHF     | revaluation | product      |
    And every Fact_Acct record for revaluation has zero Qty
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | 500 CHF       |
      | P_Revenue_Acct        | -500 CHF      |

  @Id:S30984_TC2
  Scenario: Decrease - completing a cost revaluation lowers the current cost price and books the negative delta to expense
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

    # ── Negative delta 100 PCE * (8 - 10) = -200 CHF booked P_Asset CR / P_Expense DR (amount-only, Qty 0) ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID   | M_Product_ID |
      | P_Asset_Acct          |             | 200 CHF     | revaluation | product      |
      | P_Expense_Acct        | 200 CHF     |             | revaluation | product      |
    And every Fact_Acct record for revaluation has zero Qty
    And Fact_Acct records balances for documents revaluation are matching
      | AccountConceptualName | SourceBalance |
      | P_Asset_Acct          | -200 CHF      |
      | P_Expense_Acct        | 200 CHF       |
