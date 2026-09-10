@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP baseline from physical stock after an MD_Candidate cleanup
## After historic candidates are zeroed, the ATP chain restarts at 0 while the physical stock is still
## correct. Question under test: can a physical inventory put the current stock back into the ATP
## without touching the physical stock?

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

  @Id:ATPBASE_001
  @from:cucumber
  Scenario: An inventory whose count equals the book stock does NOT restore a zeroed ATP

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_base_1   | standard_category_base | PCE               |

    # --- step 1: physical stock of 100 exists, ATP is 100 -------------------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_1_a    | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_1_a        | invl_1_a   | p_base_1     | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_1_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_1_a   | INVENTORY_UP      | p_base_1     | 2024-09-20T06:00:00Z | 100 | 100 | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_base_1                | 100       |

    # --- step 2: the customer's cleanup - zero the historic candidate --------------------
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_1_a        |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_1_a   | INVENTORY_UP      | p_base_1     | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_base_1                | 100       |

    # --- step 3: the proposed fix - count the (correct) physical stock -------------------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_1_b    | WH_BASE        | 2024-09-21   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_1_b        | invl_1_b   | p_base_1     | 100     | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_1_b is completed

    # --- result: ATP is still 0 -----------------------------------------------------------
    # The engine mirrors EVERY inventory M_Transaction 1:1 into an INVENTORY_UP/DOWN candidate, and a
    # zero-movement line (QtyCount == QtyBook) still books one such transaction with MovementQty 0. So a
    # second, zero-quantity INVENTORY_DOWN candidate appears. It leaves ATP at 0 — which is exactly this
    # scenario's point: counting the correct physical stock does NOT restore a zeroed ATP.
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_1_a   | INVENTORY_UP      |                           | p_base_1     | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
      | cand_1_b   | INVENTORY_DOWN    |                           | p_base_1     | 2024-09-21T06:00:00Z | 0   | 0   | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_base_1                | 100       |

  @Id:ATPBASE_002
  @from:cucumber
  Scenario: Forcing the ATP up with a book-value-0 inventory double-counts the physical stock

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_base_2   | standard_category_base | PCE               |

    # --- step 1: physical stock of 100 exists, ATP is 100 -------------------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_2_a    | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_2_a        | invl_2_a   | p_base_2     | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_2_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_2_a   | INVENTORY_UP      | p_base_2     | 2024-09-20T06:00:00Z | 100 | 100 | WH_BASE        |

    # --- step 2: zero it, as the cleanup would --------------------------------------------
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_2_a        |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_2_a   | INVENTORY_UP      | p_base_2     | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |

    # --- step 3: pretend the book value is 0 so the inventory produces a +100 movement ----
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_2_b    | WH_BASE        | 2024-09-21   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_2_b        | invl_2_b   | p_base_2     | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_2_b is completed

    # --- result: ATP is back to 100, but the physical stock is now 200 (double-counted) ---
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_2_b   | INVENTORY_UP      | p_base_2     | 2024-09-21T06:00:00Z | 100 | 100 | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_base_2                | 200       |
