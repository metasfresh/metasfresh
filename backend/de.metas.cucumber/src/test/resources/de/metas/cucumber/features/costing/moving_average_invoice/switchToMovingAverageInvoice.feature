@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@allure.label.feature:F1514_Cost_Type_Moving_Average_Invoice
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

  @Id:S26253_TC6
  Scenario: A single switch seeds every affected product's opening in one run, each at its own price
    #
    # The switch is company-wide: one CopyFromCostElement completion seeds the MovingAverageInvoice
    # opening for every product that carries a source (AveragePO) cost — each at its OWN price — in a
    # single run, not one product at a time. Two products with different source costs prove the batch
    # seeds them independently and correctly in the same completion.
    #
    Given metasfresh contains M_Products:
      | Identifier |
      | product1   |
      | product2   |
    And update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product1     | AveragePO        | 10 CHF           |
      | product2     | AveragePO        | 20 CHF           |
    When metasfresh contains M_CostRevaluation:
      | Identifier | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | switch     | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation switch
    And the cost revaluation identified by switch is completed
    #
    # Both products' MovingAverageInvoice openings are seeded in the one completion, each carrying its
    # own source price (10 / 20) with qty 0 — a valid MA base per product.
    #
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product1     | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |
      | acctSchema      | product2     | MovingAverageInvoice | 20 CHF           | 0 PCE      | 0 CHF        |

  @Id:S26253_TC7
  Scenario: A back-dated switch opens MovingAverageInvoice with the source cost as of the cut-off, not its later value
    #
    # Purchasing masterdata for the two purchases below.
    #
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | purchasePS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePL | purchasePS         | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | purchasePLV | purchasePL     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | purchasePS         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | CH           | Y               | Y               |
    #
    # Pre-cut-off purchase, received 2025-12-15: 10 PCE @ 10 CHF.
    # The prior method (AveragePO) therefore carries 10 CHF / 10 PCE at the 31.12.2025 cut-off.
    #
    And metasfresh has date and time 2025-12-15T09:00:00+01:00[Europe/Berlin]
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po2025_l1      | vendor        | 2025-12-15  | POO         | warehouseStd   | product      | 10         | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOutLine_ID    | M_MatchPO_ID |
      | po2025_l1      | receipt2025_line1 | mpo2025      |
    # Only the M_MatchPO detail changes the AveragePO cost; the receipt line records the same amount without
    # changing it. Waiting for both to be posted is what this step does before comparing.
    And after not more than 30s, M_CostDetails are found for product product and cost element AveragePO
      | TableName   | Record_ID         | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_MatchPO   | mpo2025           | N       | 100 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt2025_line1 | N       | 100 CHF | 10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | AveragePO        | 10 CHF           | 10 PCE     | 100 CHF      |
    #
    # Post-cut-off purchase, received and invoiced 2026-01-15: 10 PCE @ 30 CHF.
    # It moves the still-active AveragePO cost on to the weighted average (100 + 300) / 20 = 20 CHF / 20 PCE
    # — a value the prior method never had at the cut-off.
    #
    When metasfresh has date and time 2026-01-15T09:00:00+01:00[Europe/Berlin]
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po2026_l1      | vendor        | 2026-01-15  | POO         | warehouseStd   | product      | 10         | 30    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOutLine_ID    | M_MatchPO_ID |
      | po2026_l1      | receipt2026_line1 | mpo2026      |
    And for costing, create completed invoice with one line
      | C_OrderLine_ID | M_MatchInv_ID |
      | po2026_l1      | matchInv2026  |
    And after not more than 30s, M_CostDetails are found for product product and cost element AveragePO
      | TableName   | Record_ID         | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_MatchPO   | mpo2025           | N       | 100 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt2025_line1 | N       | 100 CHF | 10 PCE | N               |
      | M_MatchPO   | mpo2026           | N       | 300 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt2026_line1 | N       | 300 CHF | 10 PCE | N               |
      | M_MatchInv  | matchInv2026      | N       | 300 CHF | 10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | AveragePO        | 20 CHF           | 20 PCE     | 400 CHF      |
    #
    # Switch to MovingAverageInvoice, back-dated to the 31.12.2025 cut-off.
    #
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI
    And the cost revaluation identified by costRevalMAI is completed
    #
    # The MovingAverageInvoice opening is the source's price AND quantity as of the cut-off (10 CHF / 10 PCE),
    # not the live post-cut-off value (20 CHF / 20 PCE) the source has moved on to.
    #
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 10 PCE     | 100 CHF      |
    #
    # The opening is anchored AT the cut-off and is value-neutral: the anchor itself moves no quantity and no value.
    #
    And the cost revaluation identified by costRevalMAI seeded opening cost details:
      | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
      | product      | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
    #
    # A value-neutral seed posts no GL. This step also waits for the document to finish posting.
    #
    And no Fact_Acct records are found for documents costRevalMAI

  @Id:S26253_TC8
  Scenario: A cost recompute reaching back over the opening anchor is refused instead of deleting it
    #
    # Perform the switch: seed the prior method (AveragePO) at the cut-off and copy it onto
    # MovingAverageInvoice. The opening anchor cost detail sits AT the 31.12.2025 cut-off.
    #
    Given update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product      | AveragePO        | 10 CHF           |
    When metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | costRevalMAI | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation costRevalMAI
    And the cost revaluation identified by costRevalMAI is completed
    Then the cost revaluation identified by costRevalMAI seeded opening cost details:
      | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
      | product      | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
    #
    # December is re-opened for a back-dated year-end adjustment: a zero-difference stock count dated
    # 01.12.2025 (QtyCount = QtyBook, so no stock movement and no value change).
    # Recomputing from that document starts the MovingAverageInvoice recompute at 01.12.2025 —
    # i.e. BEFORE the 31.12.2025 anchor, so the anchor lies inside the recompute's delete range.
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invDec2025     | invDec2025_l1      | 2025-12-01   | warehouseStd   | product      | 5       | 5        | PCE          |
    #
    # The recompute must refuse. Deleting the anchor does NOT corrupt M_Cost — the anchor is
    # cost-changing and its Prev_* equal the opening, so the recompute restores M_Cost to identical
    # numbers. What is lost is the anchor row itself: the only record of the opening balance, the row
    # reverseSeededCurrentCost deletes to undo the switch, and the marker the double-seed guard keys
    # on — so once it is gone, a re-switch re-seeds a cost that is already in use. The refusal names
    # the product and the anchor's date, so the operator knows to restart strictly after the cut-off.
    #
    Then invoke M_Inventory_RecomputeCosts expecting the cost-revaluation opening anchor to block it:
      | M_Inventory_ID | C_AcctSchema_ID | CostingMethod | M_Product_ID | AnchorDateAcct |
      | invDec2025     | acctSchema      | M             | product      | 2025-12-31     |
    #
    # A refused recompute destroys nothing: the opening anchor cost detail is still there, and the
    # seeded MovingAverageInvoice current-cost row still carries the opening it was seeded with.
    #
    And the cost revaluation identified by costRevalMAI seeded opening cost details:
      | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
      | product      | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |

  @Id:S26253_TC10
  Scenario: A batched cost recompute stages its documents, releases them in document-date order, and resumes after a crash
    #
    # The recompute driver deliberately has no product parameter: it recomputes EVERY product of the
    # accounting schema. So this scenario is separated from what other scenarios leave behind in the shared
    # test database by ACCOUNTING DATE - it books its post-switch stock counts into 2027 and recomputes from
    # 2027 onwards - and every assertion below names the documents it expects rather than counting rows.
    #
    # Whatever an earlier scenario left in the repost queue is let through first, so this run starts from a
    # queue the accounting server has caught up with.
    #
    Given after not more than 60s, the repost queue is drained
    And metasfresh contains M_Products:
      | Identifier |
      | product1   |
      | product2   |
      | product3   |
    #
    # The prior method (AveragePO) carries 10 CHF for each of the three products, and a pre-cut-off stock
    # count gives each of them 10 PCE of history before the cut-off. That history is what the opening balance
    # is made of, so the switch has something non-trivial to copy.
    #
    And update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | product1     | AveragePO        | 10 CHF           |
      | product2     | AveragePO        | 10 CHF           |
      | product3     | AveragePO        | 10 CHF           |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_InventoryLine_ID   | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invPreCutProduct1 | invPreCutProduct1_l1 | 2025-12-15   | warehouseStd   | product1     | 0       | 10       | PCE          |
      | invPreCutProduct2 | invPreCutProduct2_l1 | 2025-12-15   | warehouseStd   | product2     | 0       | 10       | PCE          |
      | invPreCutProduct3 | invPreCutProduct3_l1 | 2025-12-15   | warehouseStd   | product3     | 0       | 10       | PCE          |
    #
    # One post-cut-off stock count per product, each on its own accounting date. Posting explodes into EVERY
    # active material cost element, so MovingAverageInvoice already carries 2027 cost details before the
    # switch - built without an opening balance, i.e. from zero. Purging exactly those is the driver's first job.
    # The dates deliberately run opposite to the product order: the driver batches products by M_Product_ID
    # but must release documents by accounting date, so the two orders have to disagree to prove anything.
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invProduct1    | invProduct1_l1     | 2027-03-01   | warehouseStd   | product1     | 10      | 20       | PCE          |
      | invProduct2    | invProduct2_l1     | 2027-02-01   | warehouseStd   | product2     | 10      | 20       | PCE          |
      | invProduct3    | invProduct3_l1     | 2027-01-15   | warehouseStd   | product3     | 10      | 20       | PCE          |
    #
    # Switch to MovingAverageInvoice, back-dated to the 31.12.2025 cut-off: one opening anchor per product,
    # dated at the cut-off and therefore far outside the recompute's range.
    #
    And metasfresh contains M_CostRevaluation:
      | Identifier | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | switch     | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation switch
    And the cost revaluation identified by switch is completed
    Then the cost revaluation identified by switch seeded opening cost details:
      | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
      | product1     | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
      | product2     | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
      | product3     | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
    #
    # First run, one product per commit-batch, dies while staging product3's stock count - the shape of the
    # abort this driver exists for. The batches before it stay committed and their documents stay parked in
    # the staging table: nothing reaches the repost queue, because releasing is the run's very last step.
    #
    When the cost recompute driver run dies while staging document invProduct3:
      | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume |
      | acctSchema      | AveragePO        | 2027-01-01    | 1                 | N      |
    Then the cost recompute run left these documents:
      | Record_ID   | IsStaged | IsReleased |
      | invProduct1 | Y        | N          |
      | invProduct2 | Y        | N          |
      | invProduct3 | N        | N          |
    #
    # The resume run picks up where the crash left off - it must finish only the one batch that was missing,
    # never redo the batches before it - and then releases everything staged across both runs, in
    # accounting-date order: product3's count (15.01.) first, product1's (01.03.) last.
    #
    When the cost recompute driver runs:
      | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume |
      | acctSchema      | AveragePO        | 2027-01-01    | 1                 | Y      |
    Then the cost recompute run finished 1 more batch than the run before it
    And the cost recompute run left these documents:
      | Record_ID   | IsStaged | IsReleased |
      | invProduct3 | N        | Y          |
      | invProduct2 | N        | Y          |
      | invProduct1 | N        | Y          |
    And the cost recompute staging table is empty
    #
    # From here the accounting server takes over: it drains the queue and reposts, and reposting is what
    # recreates the cost details.
    #
    And after not more than 120s, the repost queue is drained
    #
    # Every product's cost details are rebuilt for the recomputed element (AveragePO): the pre-cut-off count
    # was never touched, and the post-cut-off one - which the run deleted - is back, at the same value.
    #
    And after not more than 60s, M_CostDetails are found for product product1 and cost element AveragePO
      | TableName       | Record_ID            | Amt     | Qty    |
      | M_InventoryLine | invPreCutProduct1_l1 | 100 CHF | 10 PCE |
      | M_InventoryLine | invProduct1_l1       | 100 CHF | 10 PCE |
    And after not more than 60s, M_CostDetails are found for product product2 and cost element AveragePO
      | TableName       | Record_ID            | Amt     | Qty    |
      | M_InventoryLine | invPreCutProduct2_l1 | 100 CHF | 10 PCE |
      | M_InventoryLine | invProduct2_l1       | 100 CHF | 10 PCE |
    And after not more than 60s, M_CostDetails are found for product product3 and cost element AveragePO
      | TableName       | Record_ID            | Amt     | Qty    |
      | M_InventoryLine | invPreCutProduct3_l1 | 100 CHF | 10 PCE |
      | M_InventoryLine | invProduct3_l1       | 100 CHF | 10 PCE |
    #
    # MovingAverageInvoice was rebuilt from the SEEDED OPENING (10 CHF / 10 PCE / 100 CHF), not from zero -
    # which is exactly what the purge step guarantees. The current cost is the proof: the run had rewound the
    # element to its opening and deleted its post-cut-off detail, so reaching 20 PCE / 200 CHF is only
    # possible if the repost recreated that detail on top of the opening.
    #
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product1     | MovingAverageInvoice | 10 CHF           | 20 PCE     | 200 CHF      |
      | acctSchema      | product2     | MovingAverageInvoice | 10 CHF           | 20 PCE     | 200 CHF      |
      | acctSchema      | product3     | MovingAverageInvoice | 10 CHF           | 20 PCE     | 200 CHF      |
    #
    # And the opening anchors are all still there: the recompute never reached back over the cut-off.
    #
    And the cost revaluation identified by switch seeded opening cost details:
      | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
      | product1     | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
      | product2     | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
      | product3     | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |

  @Id:S26253_TC11
  Scenario: A resume that regroups the products into different commit-batches is refused
    #
    # Same isolation technique as the batched-recompute scenario above: the driver recomputes EVERY product
    # of the accounting schema, so this scenario is separated from the shared test database by ACCOUNTING
    # DATE - it books its post-switch stock counts into the SECOND HALF of 2027 and recomputes from
    # 01.06.2027 onwards, which is disjoint from the 01.2027-03.2027 slice the scenario above uses - and
    # names the documents it expects instead of counting rows.
    # The range stays inside 2027 on purpose: the test database's fiscal calendar ends with 2027, so a
    # later year has no C_Period at all and completing a document there fails with @PeriodClosed@.
    #
    Given after not more than 60s, the repost queue is drained
    And metasfresh contains M_Products:
      | Identifier   |
      | resumeFirst  |
      | resumeSecond |
    #
    # Pre-cut-off history on the prior method (AveragePO), so the switch has a non-trivial opening to copy.
    #
    And update current costs
      | M_Product_ID | M_CostElement_ID | CurrentCostPrice |
      | resumeFirst  | AveragePO        | 10 CHF           |
      | resumeSecond | AveragePO        | 10 CHF           |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID  | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invPreCutFirst  | invPreCutFirst_l1  | 2025-12-15   | warehouseStd   | resumeFirst  | 0       | 10       | PCE          |
      | invPreCutSecond | invPreCutSecond_l1 | 2025-12-15   | warehouseStd   | resumeSecond | 0       | 10       | PCE          |
    #
    # One post-cut-off stock count per product, both inside the recompute's second-half-of-2027 range:
    # exactly the two commit-batches the crashed run below is cut into.
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invFirst       | invFirst_l1        | 2027-08-01   | warehouseStd   | resumeFirst  | 10      | 20       | PCE          |
      | invSecond      | invSecond_l1       | 2027-07-01   | warehouseStd   | resumeSecond | 10      | 20       | PCE          |
    #
    # Switch to MovingAverageInvoice, back-dated to the 31.12.2025 cut-off - far outside the recompute range.
    #
    And metasfresh contains M_CostRevaluation:
      | Identifier   | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
      | switchResume | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
    And create lines for cost revaluation switchResume
    And the cost revaluation identified by switchResume is completed
    Then the cost revaluation identified by switchResume seeded opening cost details:
      | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
      | resumeFirst  | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
      | resumeSecond | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
    #
    # First run, ONE product per commit-batch, dies while staging the second product's stock count. The
    # batch before it stays committed, and its document stays parked in the staging table.
    #
    When the cost recompute driver run dies while staging document invSecond:
      | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume |
      | acctSchema      | AveragePO        | 2027-06-01    | 1                 | N      |
    Then the cost recompute run left these documents:
      | Record_ID | IsStaged | IsReleased |
      | invFirst  | Y        | N          |
      | invSecond | N        | N          |
    #
    # Now the operator does the natural thing after a crash and resumes with a SMALLER batch count - TWO
    # products per commit instead of one. That regroups both products into a single batch numbered 1, and
    # batch 1 is already recorded DONE - for the FIRST product alone. Skipping it would leave the second
    # product's costs un-rewound and its stock count unstaged, and the run would still report success and
    # release the first product's document. That silent half-recompute is what the refusal prevents; the
    # message names both products-per-commit values so the operator can resume with the right one.
    #
    When the cost recompute driver run is refused because the recorded run used a different products-per-commit:
      | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume | RecordedProductsPerCommit |
      | acctSchema      | AveragePO        | 2027-06-01    | 2                 | Y      | 1                         |
    #
    # A refused resume changes nothing: it finished no further batch, released nothing to the repost queue,
    # and left the crashed run's parked document exactly where it was - so resuming correctly is still possible.
    #
    Then the cost recompute run finished 0 more batches than the run before it
    And the cost recompute run left these documents:
      | Record_ID | IsStaged | IsReleased |
      | invFirst  | Y        | N          |
      | invSecond | N        | N          |
