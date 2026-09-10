@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP divergence report - read-only preview of stored vs. expected ATP
## The MD_Candidate_ATP_Divergence_Report process reports, per reconciliation key, the divergence between
## the stored and the expected Available-to-Promise (ATP), and separately every open source document that
## no candidate references at all. It writes nothing.

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

  @Id:ATPDIV_001
  @from:cucumber
  Scenario: The report flags exactly the drifted key, the healthy key stays absent

    Given metasfresh contains M_Product_Categories:
      | Identifier   | Name          | Value         |
      | cat_atpdiv_a | AtpDivReportA | AtpDivReportA |
    And metasfresh contains M_Products:
      | Identifier  | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_healthy_a | cat_atpdiv_a          | PCE               |
      | p_drifted_a | cat_atpdiv_a          | PCE               |

    # healthy chain: physical stock 80, stored ATP still 80 - no divergence
    And metasfresh contains M_Inventories:
      | Identifier    | M_Warehouse_ID | MovementDate |
      | inv_healthy_a | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier     | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_healthy_a  | invl_healthy_a | p_healthy_a  | 0       | 80       | WH_BASE        | PCE          |
    And the inventory identified by inv_healthy_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_healthy_a | INVENTORY_UP      | p_healthy_a  | 2024-09-20T06:00:00Z | 80  | 80  | WH_BASE        |

    # drifted chain: physical stock 100, but the customer's own cleanup zeroed the stored STOCK candidate
    And metasfresh contains M_Inventories:
      | Identifier    | M_Warehouse_ID | MovementDate |
      | inv_drifted_a | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier     | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_drifted_a  | invl_drifted_a | p_drifted_a  | 0       | 100      | WH_BASE        | PCE          |
    And the inventory identified by inv_drifted_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_drifted_a | INVENTORY_UP      | p_drifted_a  | 2024-09-20T06:00:00Z | 100 | 100 | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_drifted_a  |
    Then after not more than 60s, MD_Candidates are found
      | Identifier     | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_drifted_a | INVENTORY_UP      | p_drifted_a  | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |

    # scoped per product, not the shared category: "metasfresh contains M_Product_Categories" reuses an
    # existing row by Value, so a category-wide count accumulates this scenario's products across runs on a
    # reused database and drifts upward, while a per-product count stays exact regardless of database history.
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as "report_healthy_a":
      | M_Product_ID |
      | p_healthy_a  |
    And the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as "report_drifted_a":
      | M_Product_ID |
      | p_drifted_a  |

    Then the divergence report process log for the run id "report_healthy_a" contains "Checked 1 key(s); 0 diverged"
    And the divergence report process log for the run id "report_drifted_a" contains "Checked 1 key(s); 1 diverged"
    And the divergence report process log for the run id "report_drifted_a" contains "expectedAtp=100, storedAtp=0, difference=100"
    # the healthy key's own expected/stored values, had it been logged despite no divergence
    And the divergence report process log for the run id "report_healthy_a" does not contain "storedAtp=80, difference=0"

  @Id:ATPDIV_002
  @from:cucumber
  Scenario: Reconciling a drifted key then re-running the report shows no divergence

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_recon_a  | standard_category_base | PCE               |
    And metasfresh contains M_Inventories:
      | Identifier  | M_Warehouse_ID | MovementDate |
      | inv_recon_a | WH_BASE        | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier   | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_recon_a    | invl_recon_a | p_recon_a    | 0       | 200      | WH_BASE        | PCE          |
    And the inventory identified by inv_recon_a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier   | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_recon_a | INVENTORY_UP      | p_recon_a    | 2024-09-20T06:00:00Z | 200 | 200 | WH_BASE        |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_recon_a    |
    Then after not more than 60s, MD_Candidates are found
      | Identifier   | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_recon_a | INVENTORY_UP      | p_recon_a    | 2024-09-20T06:00:00Z | 0   | 0   | WH_BASE        |

    When metasfresh has date and time 2024-09-22T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "reconcile_a":
      | M_Product_ID | IsDryRun |
      | p_recon_a    | false    |
    # a real run only ENQUEUES: the process returns as soon as the work package exists and the app server
    # reconciles afterwards, so the report below would otherwise re-read the chain before the correction has
    # landed and still see the divergence. Wait for the correction candidate itself - the run's end-state -
    # rather than for a duration.
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | fix_recon_a | INVENTORY_UP      | p_recon_a    | 2024-09-22T06:00:00Z | 200 | 200 | WH_BASE        |

    And the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as "report_b":
      | M_Product_ID |
      | p_recon_a    |
    Then the divergence report process log for the run id "report_b" contains "Checked 1 key(s); 0 diverged"

  @Id:ATPDIV_003
  @from:cucumber
  Scenario: An open shipment schedule with no candidate at all is reported as uncovered, a covered one stays excluded

    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps_uncov   |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_uncov | ps_uncov           | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_so_uncov | pl_so_uncov    |
    And metasfresh contains C_BPartners:
      | Identifier     | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_uncov | N        | Y          | ps_uncov           |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID  | C_UOM_ID.X12DE355 |
      | p_uncov_a  | standard_category_base | PCE               |
      | p_cov_a    | standard_category_base | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_uncov_a | plv_so_uncov           | p_uncov_a    | 10.0     | PCE               | Normal           |
      | pp_cov_a   | plv_so_uncov           | p_cov_a      | 10.0     | PCE               | Normal           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID  | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_uncov_a | true    | customer_uncov | 2024-09-20  | 2024-09-21T21:00:00Z | WH_BASE        |
      | so_cov_a   | true    | customer_uncov | 2024-09-20  | 2024-09-21T21:00:00Z | WH_BASE        |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_uncov_a | so_uncov_a | p_uncov_a    | 30         |
      | sol_cov_a   | so_cov_a   | p_cov_a      | 50         |
    And the order identified by so_uncov_a is completed
    And the order identified by so_cov_a is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_uncov_a  | DEMAND            | SHIPMENT                  | p_uncov_a    | 2024-09-21T21:00:00Z | -30 | -30 | WH_BASE        |
      | d_cov_a    | DEMAND            | SHIPMENT                  | p_cov_a      | 2024-09-21T21:00:00Z | -50 | -50 | WH_BASE        |

    # the candidate that used to cover the p_uncov_a schedule is gone - e.g. purged by a cleanup job - while
    # the schedule itself is still open: the one gap a recompute cannot close. The p_cov_a schedule keeps its
    # MD_Candidate_Demand_Detail link intact - it is genuinely covered and must stay excluded from the report.
    When the MD_Candidate_Demand_Detail of d_uncov_a is deleted
    And the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as "report_c":
      | M_Product_ID |
      | p_uncov_a    |
    Then the divergence report process log for the run id "report_c" contains "1 open source document(s) with no candidate at all"
    And the divergence report process log for the run id "report_c" contains "Uncovered open M_ShipmentSchedule"

    # The negative control, and the reason this scenario can fail at all: p_cov_a's schedule is open exactly
    # like p_uncov_a's, and differs only in still having its MD_Candidate_Demand_Detail link. Reporting it
    # would mean the report answers "which open documents exist" instead of "which are uncovered". Scoped to
    # p_cov_a alone rather than to a shared product category, because the category step reuses an existing row
    # by Value - so a category-wide count accumulates this scenario's products across runs on a reused
    # database and drifts upward, while a per-product count stays exact.
    And the MD_Candidate_ATP_Divergence_Report process is run with parameters, storing the run id as "report_c_covered":
      | M_Product_ID |
      | p_cov_a      |
    Then the divergence report process log for the run id "report_c_covered" contains "0 open source document(s) with no candidate at all"
