@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@ghActions:run_on_executor7
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
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And load C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    And update C_AcctSchema:
      | C_AcctSchema_ID | CostingMethod |
      | acctSchema      | A             |

  @Id:S30984_TC1
  Scenario: Completing a cost revaluation raises the product's current cost price and books the delta
    # ── Establish the starting cost: 100 PCE received @ 10 CHF via a completed inventory ──
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inventory      | inventoryLine      | 2024-03-05   | warehouse      | product      | 0       | 100      | PCE          | 10        | hu      |
    Then validate current costs
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

    # ── MUST: the product's current cost price is now the revaluated 15 CHF ──
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | acctSchema      | product      | AveragePO        | 15.0000 CHF      |

    # ── SHOULD: the positive delta 100 PCE * (15 - 10) = 500 CHF is booked P_Asset DR / P_Revenue CR ──
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID   | M_Product_ID |
      | P_Asset_Acct          | 500 CHF     |             | revaluation | product      |
      | P_Revenue_Acct        |             | 500 CHF     | revaluation | product      |
