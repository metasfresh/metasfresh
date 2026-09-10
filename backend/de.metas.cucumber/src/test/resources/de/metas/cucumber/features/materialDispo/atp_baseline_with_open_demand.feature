@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: ATP baseline from MD_Stock when an open sales order precedes it
## Does a reset-stock StockChangedEvent still honour an open, unshipped demand? Business-correct answer
## in both scenarios: ATP = stock 200 - open demand 30 = 170.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | Identifier | Name     | Value    |
      | std_cat_od | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | WH_OD      |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_od      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so_od   | ps_od              | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so_od  | pl_so_od       |
    And metasfresh contains C_BPartners:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_od | N        | Y          | ps_od              |

  @Id:ATPBASE_003
  @from:cucumber
  Scenario: Open demand dated BEFORE the baseline

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_od_1     | std_cat_od            | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_od_1    | plv_so_od              | p_od_1       | 10.0     | PCE               | Normal           |

    # 1) inventory of 100
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_1a  | WH_OD          | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_1a      | invl_od_1a | p_od_1       | 0       | 100      | WH_OD          | PCE          |
    And the inventory identified by inv_od_1a is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_od_1a    | INVENTORY_UP      | p_od_1       | 2024-09-20T06:00:00Z | 100 | 100 | WH_OD          |

    # 2) sales order for 30, NO shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_od_1    | true    | customer_od   | 2024-09-20  | 2024-09-21T21:00:00Z | WH_OD          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_od_1   | so_od_1    | p_od_1       | 30         |
    And the order identified by so_od_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_od_1     | DEMAND            | SHIPMENT                  | p_od_1       | 2024-09-21T21:00:00Z | -30 | 70  | WH_OD          |

    # 3) inventory of 200, then corrupt its ATP to 1000
    And metasfresh has date and time 2024-09-22T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_1b  | WH_OD          | 2024-09-22   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_1b      | invl_od_1b | p_od_1       | 100     | 200      | WH_OD          | PCE          |
    And the inventory identified by inv_od_1b is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_od_1b    | INVENTORY_UP      | p_od_1       | 2024-09-22T06:00:00Z | 100 | 170 | WH_OD          |
    And the ATP of the STOCK candidate of c_od_1b is manually set to 1000

    # 4) the reconciliation point is the mechanism this scenario is actually about: a reset-stock event
    # re-baselines onto bare physical stock and would land here at 200, silently absorbing the still-open
    # demand - the reconciliation is the mechanism that must not do that.
    When metasfresh has date and time 2024-09-23T08:00:00+01:00[Europe/Berlin]
    And the MD_Candidate_Reconcile_ATP process is run with parameters, storing the run id as "reconcile_od_1":
      | M_Product_ID | IsDryRun |
      | p_od_1       | false    |

    # business-correct expectation: stock 200 minus the still-open demand 30 = 170
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty  | ATP | M_Warehouse_ID |
      | base_od_1  | INVENTORY_DOWN    | p_od_1       | 2024-09-23T06:00:00Z | -830 | 170 | WH_OD          |

  @Id:ATPBASE_004
  @from:cucumber
  Scenario: Open demand dated AFTER the baseline

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_od_2     | std_cat_od            | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_od_2    | plv_so_od              | p_od_2       | 10.0     | PCE               | Normal           |

    # 1) inventory of 100
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_2a  | WH_OD          | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_2a      | invl_od_2a | p_od_2       | 0       | 100      | WH_OD          | PCE          |
    And the inventory identified by inv_od_2a is completed

    # 2) inventory of 200, then corrupt its ATP to 1000
    And metasfresh has date and time 2024-09-22T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_2b  | WH_OD          | 2024-09-22   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_2b      | invl_od_2b | p_od_2       | 100     | 200      | WH_OD          | PCE          |
    And the inventory identified by inv_od_2b is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_od_2b    | INVENTORY_UP      | p_od_2       | 2024-09-22T06:00:00Z | 100 | 200 | WH_OD          |
    And the ATP of the STOCK candidate of c_od_2b is manually set to 1000

    # 3) sales order for 30 dated AFTER the baseline date, NO shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_od_2    | true    | customer_od   | 2024-09-22  | 2024-09-25T21:00:00Z | WH_OD          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_od_2   | so_od_2    | p_od_2       | 30         |
    And the order identified by so_od_2 is completed

    # 4) post the reset-stock event; MD_Stock is still 200
    When metasfresh receives a StockChangedEvent for the current MD_Stock
      | M_Product_ID | OPT.ChangeDate       |
      | p_od_2       | 2024-09-23T06:00:00Z |

    # business-correct expectation: the later demand still applies -> 200 - 30 = 170
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_od_2     | DEMAND            | SHIPMENT                  | p_od_2       | 2024-09-25T21:00:00Z | -30 | 170 | WH_OD          |

  @Id:ATPBASE_005
  @from:cucumber
  Scenario: Shipping an open order does not change the ATP — expected decrease becomes certain decrease

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_od_3     | std_cat_od            | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_od_3    | plv_so_od              | p_od_3       | 10.0     | PCE               | Normal           |

    # stock of 100
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_3   | WH_OD          | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_3       | invl_od_3  | p_od_3       | 0       | 100      | WH_OD          | PCE          |
    And the inventory identified by inv_od_3 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_od_3                  | 100       |

    # open sales order for 30 -> ATP 70, stock still 100
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_od_3    | true    | customer_od   | 2024-09-20  | 2024-09-21T21:00:00Z | WH_OD          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_od_3   | so_od_3    | p_od_3       | 30         |
    And the order identified by so_od_3 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_od_3     | DEMAND            | SHIPMENT                  | p_od_3       | 2024-09-21T21:00:00Z | -30 | 70  | WH_OD          |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_od_3                  | 100       |

    # ship it in full
    When after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_od_3    | sol_od_3       | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | ship_od_3             | ss_od_3                          | D                 | Y                  |

    # the invariant: stock dropped to 70, ATP is UNCHANGED at 70. The full shipment fulfils the open
    # demand (its own remaining qty goes to 0) and books the actual movement as a separate certain
    # decrease - the "expected decrease becomes certain decrease" this scenario is titled for - so ATP
    # never moves a second time for the same physical consumption.
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_od_3                  | 70        |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_od_3     | DEMAND              | SHIPMENT                  | p_od_3       | 2024-09-21T21:00:00Z | 0   | 70  | WH_OD          |
      | ud_od_3    | UNEXPECTED_DECREASE | SHIPMENT                  | p_od_3       | 2024-09-20T22:00:00Z | -30 | 70  | WH_OD          |

  @Id:ATPBASE_007
  @from:cucumber
  Scenario: A reset-stock refresh applies its own physical movement and leaves the open demand alone

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_od_4     | std_cat_od            | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_od_4    | plv_so_od              | p_od_4       | 10.0     | PCE               | Normal           |

    # stock of 200
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_4   | WH_OD          | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_4       | invl_od_4  | p_od_4       | 0       | 200      | WH_OD          | PCE          |
    And the inventory identified by inv_od_4 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | p_od_4                  | 200       |

    # open sales order for 30 -> the projection is 170, deliberately NOT the bare physical 200
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_od_4    | true    | customer_od   | 2024-09-20  | 2024-09-21T21:00:00Z | WH_OD          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_od_4   | so_od_4    | p_od_4       | 30         |
    And the order identified by so_od_4 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_od_4     | DEMAND            | SHIPMENT                  | p_od_4       | 2024-09-21T21:00:00Z | -30 | 170 | WH_OD          |

    # a reset-stock refresh whose recount from the HUs disagreed with the stored quantity: it found 150 stored,
    # wrote the recomputed 200 - the quantity MD_Stock now holds - and reports that +50 movement. This is the only
    # event shape the reset-stock process emits: it skips every key whose recomputed quantity did not move.
    When metasfresh receives a StockChangedEvent for the current MD_Stock
      | M_Product_ID | OPT.ChangeDate       | OPT.QtyOnHandOld |
      | p_od_4       | 2024-09-23T06:00:00Z | 150              |

    # only the refresh's own +50 may reach the chain: the projection moves 170 -> 220 and keeps the open demand.
    # Re-baselining onto the bare physical 200 would land the projection there and absorb the demand.
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_od_4     | INVENTORY_UP      |                           | p_od_4       | 2024-09-20T06:00:00Z | 200 | 200 | WH_OD          |
      | d_od_4     | DEMAND            | SHIPMENT                  | p_od_4       | 2024-09-21T21:00:00Z | -30 | 170 | WH_OD          |
      | base_od_4  | INVENTORY_UP      |                           | p_od_4       | 2024-09-23T06:00:00Z | 50  | 220 | WH_OD          |

  @Id:ATPBASE_008
  @from:cucumber
  Scenario: A reset-stock refresh with no recorded movement leaves an open demand dated before it untouched

    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | C_UOM_ID.X12DE355 |
      | p_od_5     | std_cat_od            | PCE               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_od_5    | plv_so_od              | p_od_5       | 10.0     | PCE               | Normal           |

    # stock of 200
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_od_5   | WH_OD          | 2024-09-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | Identifier | M_Product_ID | QtyBook | QtyCount | M_Warehouse_ID | UOM.X12DE355 |
      | inv_od_5       | invl_od_5  | p_od_5       | 0       | 200      | WH_OD          | PCE          |
    And the inventory identified by inv_od_5 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_od_5     | INVENTORY_UP      | p_od_5       | 2024-09-20T06:00:00Z | 200 | 200 | WH_OD          |

    # open sales order for 30, dated BEFORE the refresh below, NO shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_od_5    | true    | customer_od   | 2024-09-20  | 2024-09-21T21:00:00Z | WH_OD          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_od_5   | so_od_5    | p_od_5       | 30         |
    And the order identified by so_od_5 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | d_od_5     | DEMAND            | SHIPMENT                  | p_od_5       | 2024-09-21T21:00:00Z | -30 | 170 | WH_OD          |

    # a reset-stock refresh whose recount from the HUs agreed with the stored quantity, dated AFTER the open
    # demand above: no QtyOnHandOld override, so the step defaults it to the stock's own current QtyOnHand -
    # a genuinely zero physical movement, with a live open position still in the chain at the event date.
    When metasfresh receives a StockChangedEvent for the current MD_Stock
      | M_Product_ID | OPT.ChangeDate       |
      | p_od_5       | 2024-09-23T06:00:00Z |

    # the refresh must add no candidate at all: the chain still carries the open demand, and its projection
    # stays exactly what it was before the refresh.
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_od_5     | INVENTORY_UP      |                           | p_od_5       | 2024-09-20T06:00:00Z | 200 | 200 | WH_OD          |
      | d_od_5     | DEMAND            | SHIPMENT                  | p_od_5       | 2024-09-21T21:00:00Z | -30 | 170 | WH_OD          |
