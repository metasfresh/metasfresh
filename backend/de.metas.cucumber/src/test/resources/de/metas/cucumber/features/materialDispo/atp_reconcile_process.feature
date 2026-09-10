@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP reconciliation process - dry run and selection filter
## The MD_Candidate_Reconcile_ATP process is the operator's entry point into the reconciliation: it can be
## restricted by warehouse/product/product category, and run in dry-run mode first to preview the effect.

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

  @Id:ATPBASE_010
  @from:cucumber
  Scenario: A dry run reports the would-change value and writes nothing

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_dry_a    | standard_category_base | PCE               |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_dry_a  | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_dry_a      | invl_dry_a | p_dry_a      | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_dry_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_dry_a | INVENTORY_UP      | p_dry_a      | 2024-09-20T06:00:00Z | 100 | 100 | WH_BASE        |
    # the customer's own cleanup zeroes the candidate, so the stored projection drifts to 0 although
    # physical stock is still 100 - this is the divergence the dry run must report
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_dry_a      |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_dry_a | INVENTORY_UP      | p_dry_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |

    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "dry_run":
      | M_Product_ID | IsDryRun |
      | p_dry_a      | true     |

    Then the ATP reconciliation process log for the run id "dry_run" contains "would change to 100"
    # nothing was written: still exactly the one, unchanged candidate from before the dry run
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_dry_a | INVENTORY_UP      | p_dry_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_dry_a                 | 100       |

  @Id:ATPBASE_011
  @from:cucumber
  Scenario: A run restricted by M_Product_ID changes only the selected key

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_sel_a    | standard_category_base | PCE               |
      | p_out_a    | standard_category_base | PCE               |

    # --- key inside the selection: physical stock 100, drifted to 0 -----------------------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_sel_a  | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_sel_a      | invl_sel_a | p_sel_a      | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_sel_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_sel_a | INVENTORY_UP      | p_sel_a      | 2024-09-20T06:00:00Z | 100 | 100 | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_sel_a      |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_sel_a | INVENTORY_UP      | p_sel_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |

    # --- key outside the selection: physical stock 60, drifted to 0 the same way -----------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_out_a  | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_out_a      | invl_out_a | p_out_a      | 0       | 60       | WH_BASE        | PCE          |
    And the inventory identified by inv_out_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_out_a | INVENTORY_UP      | p_out_a      | 2024-09-20T06:00:00Z | 60  | 60  | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_out_a      |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_out_a | INVENTORY_UP      | p_out_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |

    # --- run the process restricted to p_sel_a only ----------------------------------------------------
    When metasfresh has date and time 2024-09-22T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "selective_run":
      | M_Product_ID | IsDryRun |
      | p_sel_a      | false    |

    # the selected key was corrected: a new candidate at the run date restores the physical quantity
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier    | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_sel_a    | INVENTORY_UP      | p_sel_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
      | cand_sel_a_fx | INVENTORY_UP      | p_sel_a      | 2024-09-22T06:00:00Z | 100 | 100 | WH_BASE        |
    # the key outside the selection was left untouched: still exactly the one, drifted candidate
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_out_a | INVENTORY_UP      | p_out_a      | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_sel_a                 | 100       |
      | p_out_a                 | 60        |

  @Id:ATPBASE_012
  @from:cucumber
  Scenario: A liveness cutoff excludes an open demand dated before it from the target, previewed and applied

    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps_cut     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_cut  | ps_cut             | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so_cut | pl_so_cut      |
    And metasfresh contains C_BPartners:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_cut | N        | Y          | ps_cut             |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_cut_a    | standard_category_base | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_cut_a   | plv_so_cut             | p_cut_a      | 10.0     | PCE               | Normal           |

    # physical stock of 100 - the anchor the target expression starts from, unaffected by the cutoff
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_cut_a  | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_cut_a      | invl_cut_a | p_cut_a      | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_cut_a is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_cut_a                 | 100       |

    # open sales order for 30, unshipped, prepared BEFORE the cutoff date
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_cut_a   | true    | customer_cut  | 2024-09-20  | 2024-09-21T21:00:00Z | WH_BASE        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_cut_a  | so_cut_a   | p_cut_a      | 30         |
    And the order identified by so_cut_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_cut_a    | DEMAND            | SHIPMENT                  | p_cut_a      | 2024-09-21T21:00:00Z | -30 | 70  | WH_BASE        |

    # a second open sales order for 20, unshipped, prepared AFTER the cutoff date
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_cut_b   | true    | customer_cut  | 2024-09-23  | 2024-09-24T21:00:00Z | WH_BASE        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_cut_b  | so_cut_b   | p_cut_a      | 20         |
    And the order identified by so_cut_b is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_cut_b    | DEMAND            | SHIPMENT                  | p_cut_a      | 2024-09-24T21:00:00Z | -20 | 50  | WH_BASE        |

    # WITHOUT a cutoff both open demands still count towards the target (100 - 30 - 20 = 50), which already
    # matches the stored ATP of 50 above - nothing to reconcile, so this key produces no divergence at all
    When metasfresh has date and time 2024-09-25T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "no_cutoff_run":
      | M_Product_ID | IsDryRun |
      | p_cut_a      | true     |
    Then the ATP reconciliation process log for the run id "no_cutoff_run" contains "0 of 1 matching key"

    # WITH a cutoff strictly between the two demands' dates, the earlier 30-unit demand is treated as closed and
    # drops out of the target; only the later 20-unit demand still counts: target = 100 - 20 = 80 - a 30
    # divergence against the still-unchanged stored ATP of 50, so the cutoff alone (not merely the dry run) is
    # what produces this result: without it the same key reported no divergence at all, just above
    When the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "with_cutoff_run":
      | M_Product_ID | IsDryRun | LivenessCutoffDate |
      | p_cut_a      | true     | 2024-09-23         |
    Then the ATP reconciliation process log for the run id "with_cutoff_run" contains "would change to 80"

    # --- the same cutoff on a REAL run. A real run is not performed by the process at all: it is enqueued as
    # a work package and reconciled by the app server, so the cutoff has to survive that round trip - the two
    # dry runs above prove nothing about it. Target 80 against the still-stored 50 => an INVENTORY_UP of 30 at
    # the run date, leaving the projection at 80 instead of the 50 an uncut run would have left it at. -------
    When metasfresh has date and time 2024-09-26T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "real_cutoff_run":
      | M_Product_ID | IsDryRun | LivenessCutoffDate |
      | p_cut_a      | false    | 2024-09-23         |
    Then the ATP reconciliation process log for the run id "real_cutoff_run" contains "Enqueued work package"
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | fix_cut_a  | INVENTORY_UP      | ATP_RECONCILE             | p_cut_a      | 2024-09-26T06:00:00Z | 30  | 80  | WH_BASE        |
    And after not more than 60s, the persisted ATP reconciliation backup for M_Product_ID "p_cut_a" contains a row with QtyBefore "null" and QtyAfter "80"
