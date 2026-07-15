@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@F1500
@ghActions:run_on_executor6
Feature: Switch to Moving Average Invoice
## F1500: Costing
# A customer used a prior costing method (AveragePO) until a cut-off date.
# A CopyFromCostElement cost revaluation copies the prior element's current cost onto the
# MovingAverageInvoice element, value-neutral (no GL), so MAI forward-costing starts from the seeded base.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2025-12-31T13:30:13+01:00[Europe/Berlin]
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    And cost elements for material costing methods AveragePO,MovingAverageInvoice are active
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |

  @Id:S26253_TC1
  Scenario: Value-neutral copy from AveragePO onto MovingAverageInvoice
    #
    # Seed the source (AveragePO) opening cost: prior-method accumulated cost at cut-off.
    #
    Given update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product      | AveragePO        | 10 CHF           |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | acctSchema      | product      | AveragePO        | 10 CHF           |
    #
    # Copy AveragePO -> MovingAverageInvoice via a cost revaluation dated the cut-off.
    #
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI
    And the cost revaluation identified by costRevalMAI is completed
    #
    # The MAI element now carries the source's opening cost (value-neutral copy). The seeded opening price AND
    # quantity are both present on the MAI element, so it is a valid moving-average base: a future forward MAI
    # movement blends onto this opening cost/qty, not from a zero/absent row.
    #
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |
    #
    # A value-neutral seed posts no GL. This step also waits for the document to finish posting,
    # so the assertions below observe the fully-posted state.
    #
    And no Fact_Acct records are found for documents costRevalMAI
    #
    # After posting, the copy must have touched ONLY the MAI element. The StandardCosting element
    # (the acct-schema's own costing method) must NOT have had its current cost overwritten with the
    # copied price by a posting-time fan-out.
    #
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | acctSchema      | product      | StandardCosting  | 0 CHF            |

  @Id:S26253_TC2
  Scenario: A cost recompute for a post-cutoff period preserves the seeded MovingAverageInvoice opening
    #
    # Perform the switch (as in the value-neutral copy scenario): seed the prior-method (AveragePO)
    # opening at the cut-off and copy it onto MovingAverageInvoice.
    #
    Given update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product      | AveragePO        | 10 CHF           |
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI
    And the cost revaluation identified by costRevalMAI is completed
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |
    #
    # A trivial post-cutoff (2026-01-02), zero-difference inventory (QtyCount = QtyBook → no stock
    # movement, no value change). It drives the recompute: its product + MovementDate become the
    # recompute's product selection + start date.
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invPostCutoff  | invPostCutoff_l1   | 2026-01-02   | warehouseStd   | product      | 5       | 5        | PCE          |
    #
    # Recompute the MovingAverageInvoice costs for the post-cutoff period.
    #
    And invoke M_Inventory_RecomputeCosts:
      | M_Inventory_ID | C_AcctSchema_ID | CostingMethod |
      | invPostCutoff  | acctSchema      | M             |
    #
    # The recompute must NOT delete or zero the seeded opening: the MovingAverageInvoice current cost
    # must still equal the opening balance carried over at the switch.
    #
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |

  @Id:S26253_TC3
  Scenario: Reversing the switch with no later cost events restores the pre-switch state and posts nothing
    #
    # Perform the switch: seed AveragePO opening at the cut-off and copy it onto MovingAverageInvoice.
    #
    Given update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product      | AveragePO        | 10 CHF           |
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI
    And the cost revaluation identified by costRevalMAI is completed
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           |
    #
    # The value-neutral switch posts no GL. This also waits for posting to finish before we reverse.
    #
    And no Fact_Acct records are found for documents costRevalMAI
    #
    # No later cost event built on the seed, so reversal restores the pre-switch state: the
    # MovingAverageInvoice element returns to its absent (zero) opening.
    #
    When the cost revaluation identified by costRevalMAI is reversed
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 0 CHF            | 0 PCE      | 0 CHF        |
    #
    # The reversal is value-neutral too: still no GL for the document.
    #
    And no Fact_Acct records are found for documents costRevalMAI

  @Id:S26253_TC4
  Scenario: Re-running the switch skips an already-seeded product so a redundant reversal cannot disturb it
    #
    # First switch: seed AveragePO opening at the cut-off and copy it onto MovingAverageInvoice.
    #
    Given update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product      | AveragePO        | 10 CHF           |
    When metasfresh contains M_CostRevaluation:
      | Identifier    | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI1 | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI1
    And the cost revaluation identified by costRevalMAI1 is completed
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           |
    #
    # A second, redundant switch for the same product: it must SKIP the already-seeded product (no re-seed,
    # no error) and own nothing — never re-seeding an in-use cost (that would be a value change belonging to
    # the separate GL-adjustment feature).
    #
    When metasfresh contains M_CostRevaluation:
      | Identifier    | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI2 | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI2
    And the cost revaluation identified by costRevalMAI2 is completed
    #
    # Because the second switch owns nothing, reversing it must NOT disturb the first switch's still-active
    # seeded opening: the MovingAverageInvoice opening stays exactly as the first switch left it.
    #
    When the cost revaluation identified by costRevalMAI2 is reversed
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |

  @Id:S26253_TC5
  Scenario: Copying a cost element onto itself is refused
    #
    # Seed AveragePO, then attempt a CopyFromCostElement switch whose source and target are BOTH AveragePO.
    # This self-copy is a nonsensical no-op and must be refused with a clear message.
    #
    Given update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product      | AveragePO        | 10 CHF           |
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | selfCopy     | acctSchema      | AveragePO        | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    Then create lines for cost revaluation selfCopy expecting error
