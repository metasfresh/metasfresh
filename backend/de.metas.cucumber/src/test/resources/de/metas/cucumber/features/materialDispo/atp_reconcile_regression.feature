@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP reconciliation regression coverage
## Closes the coverage gaps left after the baseline probe, the process, and the divergence report were
## built: mixed closed/open liveness in one chain, a reconciliation surviving a later ordinary event, the
## reconciliation's arithmetic against the engine's own, and a real (non-dry) run's log naming what it
## changed.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | Identifier            | Name     | Value    |
      | standard_category_reg | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | WH_REG     |
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | plant_reg                | 540006        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_reg     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_reg  | ps_reg             | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so_reg | pl_so_reg      |
    And metasfresh contains C_BPartners:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_reg | N        | Y          | ps_reg             |

  @Id:ATPREG_001
  @from:cucumber
  Scenario: A closed production demand and an open sales demand on the same product: only the open one contributes

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | fin_reg1   | standard_category_reg | PCE               |
      | comp_reg1  | standard_category_reg | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_reg1    | plv_so_reg             | comp_reg1    | 10.0     | PCE               | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_reg1   | fin_reg1                | 2024-09-01 | bomv_reg1                            |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_reg1  | bom_reg1                     | comp_reg1               | 2024-09-01 | 20       |
    And the PP_Product_BOM identified by bom_reg1 is completed
    And verify BOM for M_Product:
      | M_Product_ID.Identifier |
      | fin_reg1                |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_reg1  | fin_reg1                | bomv_reg1                                | false        |

    # --- component stock of 100, ATP 100 -------------------------------------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_reg1   | WH_REG         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_reg1       | invl_reg1  | comp_reg1    | 0       | 100      | WH_REG         | PCE          |
    And the inventory identified by inv_reg1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invl_reg1          | hu_reg1 |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | comp_reg1               | 100       |

    # --- a production order books a BOM demand of 20 for the component -------------------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppo_reg1               | MOP         | fin_reg1                | 1          | plant_reg                | WH_REG                        | 2024-09-21T07:00:00.00Z | 2024-09-21T07:00:00.00Z | 2024-09-21T07:00:00.00Z | Y                |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP | M_Warehouse_ID |
      | dem_reg1   | DEMAND            | PRODUCTION                | comp_reg1    | 2024-09-21T07:00:00.00Z | -20 | 80  | WH_REG         |

    # --- the order is CLOSED without ever issuing the components: this demand is now a closed source
    # document, and must stop contributing once reconciled -------------------------------
    When the manufacturing order identified by ppo_reg1 is closed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # --- a genuinely open, unshipped sales order for the same component, dated after the closing -------
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_reg1    | true    | customer_reg  | 2024-09-22  | 2024-09-22T21:00:00Z | WH_REG         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_reg1   | so_reg1    | comp_reg1    | 30         |
    And the order identified by so_reg1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_reg1     | DEMAND            | SHIPMENT                  | comp_reg1    | 2024-09-22T21:00:00Z | -30 | 50  | WH_REG         |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | comp_reg1               | 100       |

    # --- reconcile: only the still-open sales demand (30) counts; the closed production demand (20)
    # must not - target = physical 100 - open 30 = 70, not 100 - 30 - 20 = 50 (naive full inclusion,
    # which would already match the stored 50 and reconcile as a no-op) and not 100 (bare physical,
    # ignoring the open demand) -------------------------------------------------------------
    When metasfresh has date and time 2024-09-23T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "mixed_liveness":
      | M_Product_ID | IsDryRun |
      | comp_reg1    | false    |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | fix_reg1   | INVENTORY_UP      | comp_reg1    | 2024-09-23T06:00:00Z | 20  | 70  | WH_REG         |

  @Id:ATPREG_002
  @from:cucumber
  Scenario: A reconciled chain stays consistent after an ordinary subsequent material event

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_reg2     | standard_category_reg | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_reg2    | plv_so_reg             | p_reg2       | 10.0     | PCE               | Normal           |

    # --- physical stock 100, then the customer's own cleanup zeroes the stored candidate ----------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_reg2   | WH_REG         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_reg2       | invl_reg2  | p_reg2       | 0       | 100      | WH_REG         | PCE          |
    And the inventory identified by inv_reg2 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_reg2  | INVENTORY_UP      | p_reg2       | 2024-09-20T06:00:00Z | 100 | 100 | WH_REG         |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_reg2       |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_reg2  | INVENTORY_UP      | p_reg2       | 2024-09-20T06:00:00Z | 0   | 0   | WH_REG         |

    # --- reconcile: the projection is restored to the physical stock ------------------------------------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "consistency_run":
      | M_Product_ID | IsDryRun |
      | p_reg2       | false    |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | fix_reg2   | INVENTORY_UP      | p_reg2       | 2024-09-21T06:00:00Z | 100 | 100 | WH_REG         |

    # --- an ordinary, unrelated material event follows: a genuinely open, unshipped sales order ---------
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_reg2    | true    | customer_reg  | 2024-09-22  | 2024-09-22T21:00:00Z | WH_REG         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_reg2   | so_reg2    | p_reg2       | 40         |
    And the order identified by so_reg2 is completed

    # --- the chain builds forward from the reconciled 100, not from the pre-reconciliation drift of 0:
    # a silently discarded reconciliation would show ATP 0 - 40 = -40 here instead --------------------
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_reg2     | DEMAND            | SHIPMENT                  | p_reg2       | 2024-09-22T21:00:00Z | -40 | 60  | WH_REG         |

  @Id:ATPREG_003
  @from:cucumber
  Scenario: The reconciliation's arithmetic agrees with the engine's own stock-impact rule

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_reg3a    | standard_category_reg | PCE               |
      | p_reg3b    | standard_category_reg | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_reg3a   | plv_so_reg             | p_reg3a      | 10.0     | PCE               | Normal           |
      | pp_reg3b   | plv_so_reg             | p_reg3b      | 10.0     | PCE               | Normal           |

    # --- product A: driven purely by ordinary engine events, never touched by the reconciliation --------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_reg3a  | WH_REG         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_reg3a      | invl_reg3a | p_reg3a      | 0       | 100      | WH_REG         | PCE          |
    And the inventory identified by inv_reg3a is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_reg3a   | true    | customer_reg  | 2024-09-20  | 2024-09-20T21:00:00Z | WH_REG         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_reg3a  | so_reg3a   | p_reg3a      | 25         |
    And the order identified by so_reg3a is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_reg3a    | DEMAND            | SHIPMENT                  | p_reg3a      | 2024-09-20T21:00:00Z | -25 | 75  | WH_REG         |

    # --- product B: an identical physical chain, but its stored ATP is corrupted then reconciled --------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_reg3b  | WH_REG         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_reg3b      | invl_reg3b | p_reg3b      | 0       | 100      | WH_REG         | PCE          |
    And the inventory identified by inv_reg3b is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_reg3b   | true    | customer_reg  | 2024-09-20  | 2024-09-20T21:00:00Z | WH_REG         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_reg3b  | so_reg3b   | p_reg3b      | 25         |
    And the order identified by so_reg3b is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_reg3b    | DEMAND            | SHIPMENT                  | p_reg3b      | 2024-09-20T21:00:00Z | -25 | 75  | WH_REG         |
    # corrupt the youngest STOCK candidate of the chain (the demand's own), so the stored balance
    # diverges from the physical position while nothing physical actually changed
    And the ATP of the STOCK candidate of d_reg3b is manually set to 999

    # --- reconcile product B; the physical stock and the still-open demand are the same as A's ----------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "arithmetic_check":
      | M_Product_ID | IsDryRun |
      | p_reg3b      | false    |

    # --- both chains land on the same ATP for the same physical position: 100 - 25 = 75 ------------------
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty  | ATP | M_Warehouse_ID |
      | fix_reg3b  | INVENTORY_DOWN    | p_reg3b      | 2024-09-21T06:00:00Z | -924 | 75  | WH_REG         |

  @Id:ATPREG_004
  @from:cucumber
  Scenario: A real reconciliation run's audit trail names the candidate it changed

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_reg4     | standard_category_reg | PCE               |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_reg4   | WH_REG         | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_reg4       | invl_reg4  | p_reg4       | 0       | 60       | WH_REG         | PCE          |
    And the inventory identified by inv_reg4 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_reg4  | INVENTORY_UP      | p_reg4       | 2024-09-20T06:00:00Z | 60  | 60  | WH_REG         |
    When the MD_Candidate_Remove_From_ATP process is run
      | MD_Candidate_ID |
      | cand_reg4       |
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | cand_reg4  | INVENTORY_UP      | p_reg4       | 2024-09-20T06:00:00Z | 0   | 0   | WH_REG         |

    # --- a real (non-dry) run: the audit trail must name the candidate it changed and its new value, not
    # just report a count. It is read from the durable MD_ATP_Reconciliation_Backup rather than from the
    # process log, because a real run is enqueued and reconciled in the app server: the process returns as
    # soon as the work package exists, so its own log cannot contain what the run went on to change. ------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "log_naming_run":
      | M_Product_ID | IsDryRun |
      | p_reg4       | false    |

    Then the ATP reconciliation process log for the run id "log_naming_run" contains "Enqueued work package"
    # QtyBefore "null" = the STOCK candidate the correction itself created, so there was no earlier value to
    # back up; QtyAfter 60 is the physical stock the projection was brought back onto
    And after not more than 60s, the persisted ATP reconciliation backup for M_Product_ID "p_reg4" contains a row with QtyBefore "null" and QtyAfter "60"
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | fix_reg4   | INVENTORY_UP      | p_reg4       | 2024-09-21T06:00:00Z | 60  | 60  | WH_REG         |
