@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP double decrement — a never-issued BOM demand plus the inventory that corrects for it
## The same physical consumption is subtracted from the ATP twice: component X has ATP and stock 100;
## a production order books a BOM demand of 20 but the components are never issued while the order is
## closed anyway; a later inventory counts reality (book 100 / count 80). Stock ends correct at 80, but
## the ATP ends at 60 instead of 80 — the never-issued demand and the correcting inventory each
## decrement it.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | Identifier | Name     | Value    |
      | std_cat_dd | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | WH_DD      |
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | plant_dd                 | 540006        |

  @Id:ATPBASE_006
  @from:cucumber
  Scenario: A closed manufacturing order whose components were never issued keeps decrementing the ATP

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | fin_dd     | std_cat_dd            | PCE               |
      | comp_dd    | std_cat_dd            | PCE               |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_dd     | fin_dd                  | 2024-09-01 | bomv_dd                              |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_dd    | bom_dd                       | comp_dd                 | 2024-09-01 | 20       |
    And the PP_Product_BOM identified by bom_dd is completed
    And verify BOM for M_Product:
      | M_Product_ID.Identifier |
      | fin_dd                  |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_dd    | fin_dd                  | bomv_dd                                  | false        |

    # --- component stock of 100, ATP 100 -------------------------------------------------
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_dd_1   | WH_DD          | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_dd_1       | invl_dd_1  | comp_dd      | 0       | 100      | WH_DD          | PCE          |
    And the inventory identified by inv_dd_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invl_dd_1          | hu_dd_1 |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | comp_dd                 | 100       |

    # --- a production order books a BOM demand of 20 for the component -------------------
    When metasfresh has date and time 2024-09-21T08:00:00+01:00[Europe/Berlin]
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppo_dd                 | MOP         | fin_dd                  | 1          | plant_dd                 | WH_DD                         | 2024-09-21T07:00:00.00Z | 2024-09-21T07:00:00.00Z | 2024-09-21T07:00:00.00Z | Y                |
    # the component's ATP must now be 100 - 20 = 80 while its stock is untouched at 100
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | ATP | M_Warehouse_ID |
      | dem_dd     | DEMAND            | PRODUCTION                | comp_dd      | 2024-09-21T07:00:00.00Z | -20 | 80  | WH_DD          |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | comp_dd                 | 100       |

    # --- the order is CLOSED without ever issuing the components ------------------------
    When the manufacturing order identified by ppo_dd is closed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # --- the inventory then counts reality: the components were consumed after all -------
    And metasfresh has date and time 2024-09-22T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_dd_2   | WH_DD          | 2024-09-22   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 | M_HU_ID.Identifier |
      | inv_dd_2       | invl_dd_2  | comp_dd      | 100     | 80       | WH_DD          | PCE          | hu_dd_1            |
    And the inventory identified by inv_dd_2 is completed

    # --- stock is correct at 80, but the never-issued BOM demand still decrements a second time: today's
    # behaviour lands ATP at 60, not the physically correct 80 -----------------------------
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | comp_dd                 | 80        |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | invc_dd_2  | INVENTORY_DOWN    | comp_dd      | 2024-09-22T06:00:00Z | -20 | 60  | WH_DD          |

    # --- the reconciliation point is where this is actually fixed: once the manufacturing order is
    # closed, its never-issued demand no longer counts, so the target is the physical stock alone -------
    When metasfresh has date and time 2024-09-23T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "reconcile_dd":
      | M_Product_ID | IsDryRun |
      | comp_dd      | false    |

    # --- business-correct result: ATP is now 80, matching the physical stock --------------
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | fix_dd     | INVENTORY_UP      | ATP_RECONCILE             | comp_dd      | 2024-09-23T06:00:00Z | 20  | 80  | WH_DD          |
