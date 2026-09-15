@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1530_Recreate_product_costs
@ghActions:run_on_executor7
Feature: Recompute Costs - cost-update inventory dated earlier in the same month
  ## F1530: Recreate product costs

  Regression guard for the recompute reorder: when an `M_Inventory` line that updates costs
  shares its calendar month with later same-month outbound documents, the recompute must
  re-post the cost-update inventory STRICTLY BEFORE the later docs so the running cost
  is already in place when those later docs compute their own cost details.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-04-01T08:00:00+02:00[Europe/Berlin]
    And documents are accounted immediately
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | P1         | PCE      |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | A             |
    And cost elements for material costing methods AveragePO are active

  Scenario: Cost-update inventory dated earlier in the same month is honoured by later same-month outbound inventories
    # Initial inbound inventory dated BEFORE the recompute start date sets the running cost to 10
    # and creates an HU (hu1) that the later outbound inventories will draw from.
    # It will not be touched by the recompute (its MovementDate is before the cost-update inventory).
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice | M_HU_ID |
      | inv_setup      | inv_setup_l1       | 2024-02-28   | wh             | P1           | 0       | 100      | PCE          | 10        | hu1     |

    # Two outbound inventories in March, created BEFORE the cost-update inventory so their
    # record_ids are lower. With the buggy 'MM' truncation, they would be re-posted FIRST
    # inside the March bucket (same tablename_prio = ordered by record_id) and pick up the
    # pre-recompute cost (10); with the 'DD' fix they sit in their own (later) day-buckets
    # and see the new cost (30).
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_ID |
      | inv_outbound1  | inv_outbound1_l1   | 2024-03-15   | wh             | P1           | 100     | 98       | PCE          | hu1     |
      | inv_outbound2  | inv_outbound2_l1   | 2024-03-20   | wh             | P1           | 98      | 97       | PCE          | hu1     |

    # Cost-update inventory dated 2024-03-02 (NOT day=1, so it does not get the special prio=5
    # treatment; it has the default prio=110, exactly the case that broke under 'MM'). qty diff = 0,
    # explicit cost price 30. No M_HU_ID column on this line — qty diff = 0 means no stock
    # movement, so no HU is created or consumed; including hu1 here would re-register it.
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice |
      | inv_costUpdate | inv_costUpdate_l1  | 2024-03-02   | wh             | P1           | 97      | 97       | PCE          | 30        |

    # Run the recompute starting from the cost-update inventory's MovementDate (2024-03-02).
    # The reorder must place inv_costUpdate strictly before inv_outbound1 / inv_outbound2 in the
    # repost queue so the outbounds re-post with the new cost = 30.
    When invoke M_Inventory_RecomputeCosts:
      | M_Inventory_ID | C_AcctSchema_ID | CostingMethod |
      | inv_costUpdate | acctSchema      | A             |

    # After recompute with the 'DD' fix:
    #   inv_setup       remains (DateAcct < startDate)
    #   inv_costUpdate  re-posts first => running cost = 30
    #   inv_outbound1   re-posts => Amt = -2 PCE * 30 CHF/PCE = -60 CHF
    #   inv_outbound2   re-posts => Amt = -1 PCE * 30 CHF/PCE = -30 CHF
    # Without the fix ('MM' truncation), the outbounds would re-post first with the pre-recompute
    # cost (10), giving Amt = -20 CHF / -10 CHF, and this assertion would fail.
    Then after not more than 30s, M_CostDetails are found for product P1 and cost element AveragePO
      | TableName       | Record_ID         | Amt      | Qty     |
      | M_InventoryLine | inv_setup_l1      | 1000 CHF | 100 PCE |
      | M_InventoryLine | inv_costUpdate_l1 | 0 CHF    | 0 PCE   |
      | M_InventoryLine | inv_outbound1_l1  | -60 CHF  | -2 PCE  |
      | M_InventoryLine | inv_outbound2_l1  | -30 CHF  | -1 PCE  |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | P1           | AveragePO        | 30 CHF           | 97 PCE     |
