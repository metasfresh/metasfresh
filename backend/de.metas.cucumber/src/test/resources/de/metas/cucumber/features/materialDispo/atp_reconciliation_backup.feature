@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP reconciliation backup survives the run and names what changed
## Before the reconciliation writes anything, the affected rows must be backed up; the requirement is that the
## pre-change values stay recoverable and the run log names what changed - reachable from a fresh database read,
## not only from the return value of the call that ran the reconciliation.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | Identifier             | Name     | Value    |
      | standard_category_base | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | WH_BASE    |

  @Id:ATPBASE_016
  @from:cucumber
  Scenario: The backup is recoverable from the database, across several reconciled keys

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_bkp_a    | standard_category_base | PCE               |
      | p_bkp_b    | standard_category_base | PCE               |

    # --- key A: physical stock 100, then the customer's own cleanup zeroes the candidate --------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_bkp_a  | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_bkp_a      | invl_bkp_a | p_bkp_a      | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_bkp_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_bkp_a | INVENTORY_UP      | p_bkp_a      | 2024-09-20T06:00:00Z | 100 | 100 | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_bkp_a      |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_bkp_a | INVENTORY_UP      | p_bkp_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_bkp_a                 | 100       |

    # --- key B: physical stock 50, same cleanup ------------------------------------------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_bkp_b  | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_bkp_b      | invl_bkp_b | p_bkp_b      | 0       | 50       | WH_BASE        | PCE          |
    And the inventory identified by inv_bkp_b is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_bkp_b | INVENTORY_UP      | p_bkp_b      | 2024-09-20T06:00:00Z | 50  | 50  | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_bkp_b      |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_bkp_b | INVENTORY_UP      | p_bkp_b      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_bkp_b                 | 50        |

    # --- reconcile both keys, one call per key, one run id each --------------------------------------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the ATP reconciliation is run for M_Product_ID "p_bkp_a" and M_Warehouse_ID "WH_BASE", storing the run id as "run_a"
    And the ATP reconciliation is run for M_Product_ID "p_bkp_b" and M_Warehouse_ID "WH_BASE", storing the run id as "run_b"

    # --- both keys' pre-change values are recoverable from the database, not from the calls above -----
    Then the persisted ATP reconciliation backup for the run id "run_a" contains a row with QtyBefore "null" and QtyAfter "100"
    And the persisted ATP reconciliation backup for the run id "run_b" contains a row with QtyBefore "null" and QtyAfter "50"

    # --- key A drifts a second time: a later stock count is zeroed the same way, so a real STOCK -----
    # --- candidate (the one the first reconciliation just created) already exists in the second run's -
    # --- scope - this is the path the two assertions above never reach ---------------------------------
    When metasfresh has date and time 2024-09-25T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_bkp_a2 | WH_BASE        | 2024-09-25   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier  | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_bkp_a2     | invl_bkp_a2 | p_bkp_a      | 100     | 130      | WH_BASE        | PCE          |
    And the inventory identified by inv_bkp_a2 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_bkp_a2 | INVENTORY_UP      | p_bkp_a      | 2024-09-25T06:00:00Z | 30  | 130 | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_bkp_a2     |
    Then after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_bkp_a2 | INVENTORY_UP      | p_bkp_a      | 2024-09-25T06:00:00Z | 0   | 100 | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_bkp_a                 | 130       |

    # --- reconciling key A again, as of the earlier run date, reaches forward into the newly-drifted --
    # --- candidate above and recovers its real (non-null) prior quantity -------------------------------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the ATP reconciliation is run for M_Product_ID "p_bkp_a" and M_Warehouse_ID "WH_BASE", storing the run id as "run_a2"

    Then the persisted ATP reconciliation backup for the run id "run_a2" contains a row with QtyBefore "100" and QtyAfter "130"
