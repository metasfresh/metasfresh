@from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5020
@ghActions:run_on_executor7
@Id:S30640_TC1
Feature: MD_Stock reconciliation converges to HU-derived truth
  ## F5020: Stock
  Proves that MD_Stock_Update_From_M_HUs (the periodic reconciliation process):
    - corrects a divergent MD_Stock row back to the M_HU_Storage-derived truth
    - is idempotent: running it again on an already-converged row causes no drift

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

  Scenario: Reconciliation converges a divergent MD_Stock row
    # No fixed Value/Name: each scenario gets a distinct product so MD_Stock rows
    # (keyed by product) never leak between scenarios sharing the executor's DB.
    And metasfresh contains M_Products:
      | Identifier      |
      | convergeProduct |

    # HUs give on-hand truth of 100 PCE via inventory completion
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate |
      | invConverge               | warehouseStd   | 2026-06-28   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | invLineConverge               | invConverge               | convergeProduct         | PCE          | 100      | 0       |
    When the inventory identified by invConverge is completed

    Then after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineConverge               | huConverge         |

    # Event path sets MD_Stock to the HU truth: 100
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | convergeProduct         | 100       | warehouseStd                  |

    # Corrupt the same business-key row with a wrong value
    Given metasfresh has a divergent MD_Stock row:
      | M_Product_ID    | M_Warehouse_ID | QtyOnHand |
      | convergeProduct | warehouseStd   | 150       |

    When the MD_Stock reconciliation process is run

    # Reconciliation resets QtyOnHand back to the HU-derived truth: 100
    Then after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | convergeProduct         | 100       | warehouseStd                  |

  Scenario: Reconciliation is idempotent
    And metasfresh contains M_Products:
      | Identifier     |
      | idempotProduct |

    # HUs give on-hand truth of 60 PCE via inventory completion
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate |
      | invIdempot                | warehouseStd   | 2026-06-28   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | invLineIdempot                | invIdempot                | idempotProduct          | PCE          | 60       | 0       |
    When the inventory identified by invIdempot is completed

    Then after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineIdempot                | huIdempot          |

    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | idempotProduct          | 60        | warehouseStd                  |

    # Run reconciliation once: already converged, no change expected
    When the MD_Stock reconciliation process is run

    Then after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | idempotProduct          | 60        | warehouseStd                  |

    # Run reconciliation again: still 60, no runaway/accumulation
    When the MD_Stock reconciliation process is run

    Then after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | idempotProduct          | 60        | warehouseStd                  |

  Scenario: Reconciliation creates a new active MD_Stock row for an HU-only bucket
    And metasfresh contains M_Products:
      | Identifier    |
      | huOnlyProduct |

    # HUs give on-hand truth of 80 PCE via inventory completion
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate |
      | invHuOnly                 | warehouseStd   | 2026-06-28   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | invLineHuOnly                 | invHuOnly                 | huOnlyProduct           | PCE          | 80       | 0       |
    When the inventory identified by invHuOnly is completed

    Then after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLineHuOnly                 | huHuOnly           |

    # Event path creates the active MD_Stock row = 80
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | huOnlyProduct           | 80        | warehouseStd                  |

    # Deactivate the only active MD_Stock row: the HU still holds 80 on hand, but no active
    # MD_Stock row represents the bucket anymore - the HU-only case the view's FULL OUTER JOIN
    # (against the IsActive='Y'-filtered MD_Stock side) must still surface.
    Given the active MD_Stock row is deactivated:
      | M_Product_ID  | M_Warehouse_ID |
      | huOnlyProduct | warehouseStd   |

    When the MD_Stock reconciliation process is run

    # Reconciliation must CREATE a new active MD_Stock row for the HU-only bucket
    Then after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier |
      | huOnlyProduct           | 80        | warehouseStd                  |
