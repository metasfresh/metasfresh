@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
@ghActions:run_on_executor2
Feature: Lot-for-lot production disposition — supply tracks demand on qty change and reactivate
## F8022: Lot for Lot - Manufacturing Order per Sales Order
## For a manufactured lot-for-lot product, production supply tracks the demand's ordered qty net of the
## supply already created for it:
## - a real increase adds only the increment;
## - a reactivate round-trip (order or shipment, incl. an already-processed production order) stays lot-for-lot;
## - a decrease reduces supply.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier   | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_convenienceSalate   |
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 0        |


  @Id:S0264_600
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Partial stock available at demand time, supplied via production Lot for Lot
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  |

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
      | il_2       | i_2                       | p_1                     | PCE          | 5        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
      | il_2                          | hu_2               |
    And M_HU are disposed:
      | M_HU_ID | MovementDate         |
      | hu_1    | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 5                      |                                 |

    And after not more than 60s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  |                              | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 5                              | 5                          | 0                             | 0                             | warehouseStd                  | 5                            | 5                  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1             | ppln_1                 | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | I_PP_OrderLine_Candidate | M_Product_ID | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID |
      | oc_1                  | olc_1                    | p_2          | 100        | PCE               | CO            | boml_1                |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10   | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5    | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10  | 5                      |                                 |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -5                     |                                 |
      | c_5        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 5                      |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |                                 |

    And after not more than 60s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 5                              | 5                          | 0                             | 0                             | warehouseStd                  | 5                            | 5                  |


  @Id:S0264_700
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Full stock available at demand time, supplied via production Lot for Lot
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  |

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1            | p_1          | PCE          | 10       | 0       |
      | il_2       | i_2            | p_1          | PCE          | 5        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | il_1               | hu_1    |
      | il_2               | hu_2    |
    And M_HU are disposed:
      | M_HU_ID | MovementDate         |
      | hu_2    | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                           | p_1          |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                           | p_1          |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                           | p_1          | 2021-04-16T21:00:00Z | -5  | 10                     |                                 |

    And after not more than 60s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                             | 0                             | warehouseStd                  | 10                           | 10                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | I_PP_OrderLine_Candidate | M_Product_ID | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID |
      | oc_1                  | olc_1                    | p_2          | 100        | PCE               | CO            | boml_1                |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10   | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5    | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -5   | 10                     |                                 |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | 0                      |                                 |
      | c_5        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 10                     |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |                                 |

    And after not more than 60s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 10                             | 10                         | 0                             | 0                             | warehouseStd                  | 10                           | 10                 |


  @Id:S0264_800
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - a later order's production candidate is sized to its own qty, not to an earlier order's still-open demand
    # NOTE: no PP_Product_Planning is created up front. The order is completed FIRST so its demand fires
    # NoSupplyAdvice (no plan yet) and drives ATP negative WITHOUT a supply — a persistent open deficit from an earlier order.
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists.
    # No plan yet -> its demand fires NoSupplyAdvice -> ATP goes negative with NO supply (the persistent deficit).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    # Stabilisation barrier: the completing step is synchronous for DocStatus, so no order-status check is
    # needed. The async piece is the shipment-schedule recompute (the real material-event trigger) — wait until
    # it reached its expected qty and settled (IsToRecompute=N) before snapshotting MD_Candidate.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    # PRECONDITION (self-validating): order-1 demand exists, ATP -20, and NO supply (no plan yet)
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # only NOW create the lot-for-lot product planning (the "created after" in the RCA)
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    # ORDER 2 (a day later), completed AFTER planning -> lot-for-lot fires a production candidate
    # for ORDER 2's qty ONLY (20). It must NOT cover order-1's still-open demand (lot-for-lot sizes to order 2's own qty, not the global-ATP gap).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    # Stabilisation barrier: wait until order 2's shipment schedule reached qty 20 and settled (IsToRecompute=N).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    # order 2's lot-for-lot production candidate = 20 (its own qty) — must NOT cover order 1.
    # Correct pre-trigger state (a round-trip must leave it unchanged): assert MD_Candidate first, then
    # exactly one production candidate (no more).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -40                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate + re-complete ORDER 2 -> its demand is re-evaluated as an UPDATE
    And the order identified by o_2 is reactivated
    # Stabilisation barrier: wait until order 2's shipment schedule qty dropped to 0 and settled (IsToRecompute=N).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    # Reopened order 2: its shipment demand is retracted, but the already-processed production supply persists.
    # Asserting here isolates the re-evaluation to the RE-COMPLETE step.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | 0                      |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And the order identified by o_2 is completed
    # Stabilisation barrier: wait until order 2's shipment schedule qty is back to 20 and settled (IsToRecompute=N).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    # Lot-for-lot invariant: order 2's production stays sized to its OWN 20; order 1's open -20 is never absorbed
    # into it (lot-for-lot per order, not global-ATP netting).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -40                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_810
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - an order round-trip (no qty change, un-processed prior candidate) keeps supply lot-for-lot
    # As S0264_800 but the lot-for-lot plan does NOT auto-process the production candidate (IsCreatePlan=false).
    # The order-2 reactivate + re-complete round-trip (no qty change) must leave supply at 20 (lot-for-lot, not ATP-netted).
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists -> persistent negative ATP, no supply.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that does NOT auto-process the production candidate
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  |

    # ORDER 2 (a day later), completed AFTER planning -> lot-for-lot fires a production candidate for its own qty (20).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate + re-complete ORDER 2 (no qty change)
    And the order identified by o_2 is reactivated
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    # Reopened order 2: the un-processed production plan is retracted along with the demand (c_s2 -> 0), then
    # re-created on the re-complete below. (A processed production would instead persist — cf. S0264_800.)
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0   | -20                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      |
    And the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    # EXPECTED (grow-open, unified advisor): the re-complete GROWS order 2's still-open production supply back to
    # its own qty (c_s2 reused -> 20) rather than creating a fresh MD supply. The old component-demand row is drained
    # to 0 (c_cd2) and a fresh component-demand is added for the grown supply (c_cd2b -200). Order 2's demand 20 is
    # covered by the grown production; order 1's -20 stays open. Production for order 2 = its own qty (20), never
    # absorbing order 1's deficit. At the PP_Order_Candidate level the emptied candidate is deactivated and a fresh
    # one carries the 20 (below).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_cd2b     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    # ONE active production candidate (the fresh oc_2b, 20); the original oc_2 was emptied by the reactivate and
    # deactivated (IsActive=false, QtyEntered 0) — shown here rather than filtered out.
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | IsActive | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | false    | p_1          | bom_1             | ppln_1                 | 540006        | 0          | 0            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_2b      | false     | true     | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_820
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - an order qty increase (un-processed prior candidate) grows supply by the increment only
    # As S0264_810 (IsCreatePlan=false) but the reactivate is followed by a qty increase (20 -> 40) before re-complete.
    # The production supply must grow to 40 (order 2's new qty), never absorb order 1's still-open -20.
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists -> persistent negative ATP, no supply.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that does NOT auto-process the production candidate
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  |

    # ORDER 2 (a day later), completed AFTER planning -> lot-for-lot fires a production candidate for its own qty (20).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate ORDER 2, INCREASE its line to 40, then re-complete
    And the order identified by o_2 is reactivated
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    # Reopened order 2: the un-processed production plan is retracted with the demand (c_s2 -> 0); it is re-created
    # for the new qty on the re-complete below.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0   | -20                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_2                      | 40             | 40             |
    And the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 40           | N             |
    # EXPECTED (grow-open, unified advisor): the re-complete GROWS order 2's still-open production supply to its new
    # qty (c_s2 reused -> 40) rather than creating a fresh MD supply. The old component-demand row is drained to 0
    # (c_cd2) and a fresh component-demand is added for the grown supply (c_cd2b -400). Order 2's demand 40 is covered
    # by the grown production; order 1's -20 stays open. Production for order 2 = its own qty (40), not netted vs
    # global ATP. At the PP_Order_Candidate level the emptied candidate is deactivated and a fresh one carries the
    # 40 (below).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -40  | -60                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 40   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_cd2b     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -400 | -400                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | IsActive | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | false    | p_1          | bom_1             | ppln_1                 | 540006        | 0          | 0            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_2b      | false     | true     | p_1          | bom_1             | ppln_1                 | 540006        | 40         | 40           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_830
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - an order qty increase (already-processed prior candidate) grows supply by the increment only
    # As S0264_820 but the lot-for-lot plan auto-processes the production candidate (IsCreatePlan=true).
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that auto-processes the production candidate
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -40                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate ORDER 2, INCREASE its line to 40, then re-complete
    And the order identified by o_2 is reactivated
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | 0                      |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_2                      | 40             | 40             |
    And the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 40           | N             |
    # EXPECTED (correct state): order 2's demand grows to 40. Its first production (20) is already PROCESSED and
    # cannot be grown, so the missing delta (20) is produced as a NEW second candidate — the two lot-for-lot
    # candidates (20 + 20) together cover order 2's own 40. Order 1's -20 stays open.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -40  | -60                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -60                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -40                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
      | c_s3a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -40                    |
      | c_cd3a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | -200                   |
      | c_s3       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -400                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_3       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_840
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - an order qty decrease (un-processed prior candidate) reduces the production supply
    # As S0264_810 (IsCreatePlan=false) but the reactivate is followed by a qty decrease (20 -> 10) before re-complete.
    # The production supply must reduce to 10 (order 2's new qty) — lot-for-lot, not ATP-netted.
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate ORDER 2, DECREASE its line to 10, then re-complete
    And the order identified by o_2 is reactivated
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    # Reopened order 2: the un-processed production plan is retracted with the demand (c_s2 -> 0); it is re-created
    # for the new qty on the re-complete below.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0   | -20                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_2                      | 10             | 10             |
    And the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 10           | N             |
    # EXPECTED (grow-open, unified advisor): the re-complete resizes order 2's still-open production supply down to
    # its new qty (c_s2 reused -> 10) rather than creating a fresh MD supply. The old component-demand row is drained
    # to 0 (c_cd2) and a fresh component-demand is added for the resized supply (c_cd2b -100). Order 2's demand 10 is
    # covered by the resized production; order 1's -20 stays open. At the PP_Order_Candidate level the emptied
    # candidate is deactivated and a fresh one carries the 10 (below).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -30                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_cd2b     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | IsActive | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | false    | p_1          | bom_1             | ppln_1                 | 540006        | 0          | 0            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_2b      | false     | true     | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_850
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - an order qty decrease (already-processed prior candidate) reduces the production supply
    # As S0264_840 but the lot-for-lot plan auto-processes the production candidate (IsCreatePlan=true).
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -40                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate ORDER 2, DECREASE its line to 10, then re-complete
    And the order identified by o_2 is reactivated
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | 0                      |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_2                      | 10             | 10             |
    And the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 10           | N             |
    # EXPECTED (correct state): order 2's demand drops to 10, but its production was already PROCESSED and cannot be
    # un-produced, so the production STAYS at 20 (over-supply) and a SupplyRequiredDecreased notification is fired for
    # a planner to rebalance. Order 1's -20 stays open.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -30                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -30                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -10                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_860
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Stock-covered reactivate variant: order 2 is covered by pre-existing INVENTORY (10 on hand) and shipped FROM
  # that stock, not from a manufactured HU (its lot-for-lot candidate is planned/auto-processed but never turned
  # into a PP_Order). Order 1 (20) is never shipped -> stays uncovered, global ATP negative. Reactivating order 2's
  # shipment must re-evaluate its demand with lot-for-lot sizing, not ATP netting -> no extra production (order 2
  # stays covered by the returned stock).
  Scenario: Lot for Lot - a reactivated stock-shipped order stays lot-for-lot, not ATP-netted
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (earlier), completed before planning -> persistent uncovered demand, never shipped.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |

    # lot-for-lot planning
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    # ORDER 2 (later), one full TU (10 PCE).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 10         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 10           | N             |

    # Pre-existing INVENTORY of 10 on hand (dated at the pinned SystemTime 2021-04-11) -> order 2 ships from stock.
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_stock           | 10           | ss_2                             | p_1                     |

    # Post-inventory state: the +10 stock (INVENTORY_UP, not a production receipt) lifts order 1's ATP to -10, but
    # order 2 still carries its own lot-for-lot production supply (10) and ATP stays negative (-20 at order 2).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -10                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_inv      | INVENTORY_UP      |                               | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |

    # SHIP order 2 ONLY from the on-hand inventory (no production). Order 1 is never shipped -> stays uncovered.
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_2                  | shipment_2 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | QtyDelivered | IsToRecompute |
      | ss_1       | ol_1           | 20           | 0            | N             |
      | ss_2       | ol_2           | 0            | 10           | N             |
    # TRIGGER: reactivate order 2's stock shipment -> re-evaluate its demand while order 1 stays uncovered.
    And the shipment identified by shipment_2 is reactivated
    # Reactivating undoes the delivery (Processed Y->N, QtyDelivered->0); ss_2's QtyToDeliver settles at 0
    # (a reversal instead reopens it to 10). Wait for the schedules to settle first.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | QtyDelivered | IsToRecompute |
      | ss_1       | ol_1           | 20           | 0            | N             |
      | ss_2       | ol_2           | 0            | 0            | N             |
    # Invariant: order 2's demand reopens (-10) and is covered by the returned inventory + its single existing
    # production candidate — NO new production. No extra SUPPLY / component-DEMAND rows and no duplicate production
    # candidate (lot-for-lot sizing to order 2's own qty, not global-ATP netting).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -10                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_inv      | INVENTORY_UP        |                               | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 0    | -10                    |
    # Same guard, more readable: exactly ONE production candidate (order 2's own).
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # ROUND-TRIP: re-complete the reactivated shipment UNCHANGED -> order 2 re-ships from the returned stock. No qty
    # change, so order 2 stays covered by its single existing production candidate — NO new PP_Order_Candidate must
    # appear (the over-creation guard, applied to the re-ship leg). QtyDelivered 0->10 is the discriminating gate
    # for the re-completion (QtyToDeliver stays 0 throughout, so it cannot gate this).
    And the shipment identified by shipment_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | QtyDelivered | IsToRecompute |
      | ss_1       | ol_1           | 20           | 0            | N             |
      | ss_2       | ol_2           | 0            | 10           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -10                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_inv      | INVENTORY_UP        |                               | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 10   | -20                    |
    # Idempotency guard: still exactly ONE production candidate (order 2's own) after the re-ship.
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_870
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Produced reactivate variant: order 2 is produced (lot-for-lot candidate -> PP_Order -> received HU -> stock) and
  # a shipment is generated for order 2 ONLY; order 1 is never shipped, so it stays UNCOVERED and global ATP negative.
  # Reactivating order 2's shipment must re-evaluate its demand (an UPDATE) with lot-for-lot sizing, not ATP netting:
  # order 2 stays covered by its own produced stock, order 1's -20 open, exactly ONE production candidate.
  Scenario: Lot for Lot - a reactivated shipment stays lot-for-lot while an earlier order is uncovered
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | huPiLU                |
      | huPiTU                |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 10  | HU       | huPiTU            |
      | huPiItemTU      | huPiVersionTU      |     | MI       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_TU_10CU         | huPiItemTU      | p_1          | 10  | 2021-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    # Standard partner (DeliveryRule defaults to Force). Order 1 is deliberately NEVER shipped (a shipment is
    # generated only for order 2), so order 1's demand stays UNCOVERED and global ATP stays negative across the
    # reactivate.
    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists -> persistent negative ATP, no supply.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that auto-processes the production candidate (IsCreatePlan=true)
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    # ORDER 2 (a day later), one full TU (10 PCE) so it can be produced and shipped as one HU.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 10         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 10           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -30                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -30                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # PRODUCE: generate the PP_Order from order 2's production candidate, receive the finished HU (one TU), complete it.
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_2                  |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | DatePromised         |
      | ppo_2      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10         | 2021-04-16T21:00:00Z |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppo_2       | hu_2               | N               | 0     | N               | 1     | N               | 10          | product_TU_10CU                    |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppo_2                  |
    # SETTLE-WAIT before shipment generation: drains the async queues + polls until order 2's production has fully
    # landed. The planned SUPPLY (10) is realized -> SUPPLY qty 0 + a new UNEXPECTED_INCREASE +10; that +10 stock
    # lifts every global ATP by 10 vs the pre-produce snapshot. This gate MUST pass before we ship.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |

    # SHIP order 2 ONLY (order 1 is never shipped -> stays uncovered). The produced HU covers order 2's demand.
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_2                  | shipment_2 |
    # Shipping updates the schedules -> wait for the SS recompute to settle BEFORE reading MD candidates.
    # ss_1 stays open (order 1 never shipped); ss_2 is fully delivered (QtyToDeliver 0).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | QtyDelivered | IsToRecompute |
      | ss_1       | ol_1           | 20           | 0            | N             |
      | ss_2       | ol_2           | 0            | 10           | N             |
    # Post-shipment MD state: order 2's demand is fulfilled (c_d2 -> 0) and a real stock decrease (UNEXPECTED_DECREASE
    # 10) records the goods leaving; the produced +10 (UNEXPECTED_INCREASE) minus the shipped 10 nets to 0 on hand.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 10   | -20                    |

    # TRIGGER: reactivate order 2's completed shipment -> re-opens the delivery and re-evaluates order 2's demand as
    # an UPDATE while order 1's -20 keeps global ATP negative.
    And the shipment identified by shipment_2 is reactivated
    # Reactivating undoes the delivery (Processed Y->N, QtyDelivered->0); ss_2's QtyToDeliver settles at 0
    # (a reversal instead reopens it to 10). Wait for the schedules to settle first.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | QtyDelivered | IsToRecompute |
      | ss_1       | ol_1           | 20           | 0            | N             |
      | ss_2       | ol_2           | 0            | 0            | N             |
    # Invariant: order 2's demand reopens (-10) and is covered by its already-produced on-hand stock (+10, c_prod)
    # — NO new production. No extra SUPPLY / component-DEMAND rows and no duplicate production candidate (lot-for-lot
    # sizing to order 2's own qty, not to order 1's still-open 20).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 0    | -10                    |
    # Same guard, more readable: exactly ONE production candidate (order 2's own oc_2).
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # ROUND-TRIP: re-complete the reactivated shipment UNCHANGED -> order 2 re-ships from its produced on-hand stock.
    # No qty change, so order 2 stays covered by its single existing production candidate — NO new PP_Order_Candidate
    # (over-creation guard on the re-ship leg). QtyDelivered 0->10 is the discriminating gate (QtyToDeliver stays 0).
    And the shipment identified by shipment_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | QtyDelivered | IsToRecompute |
      | ss_1       | ol_1           | 20           | 0            | N             |
      | ss_2       | ol_2           | 0            | 10           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 10   | -20                    |
    # Idempotency guard: still exactly ONE production candidate (order 2's own oc_2) after the re-ship.
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

  @Id:S0264_880
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Stock-covered reverse variant: order 2 is covered by pre-existing INVENTORY (10 on hand) and shipped FROM that
  # stock, not from a manufactured HU; the shipment is then REVERSED. Order 1 (20) is never shipped -> stays
  # uncovered, global ATP negative. The reversal must re-evaluate order 2's demand with lot-for-lot sizing, not ATP
  # netting -> no extra production (order 2 stays covered by the returned inventory).
  Scenario: Lot for Lot - a reversed stock-shipped order stays lot-for-lot, not ATP-netted
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (earlier), completed before planning -> persistent uncovered demand, never shipped.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |

    # lot-for-lot planning
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    # ORDER 2 (later), one full TU (10 PCE).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 10         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 10           | N             |

    # Pre-existing INVENTORY of 10 on hand (dated at the pinned SystemTime 2021-04-11) -> order 2 ships from stock.
    And the following virtual inventory is created
      | M_HU_ID.Identifier | QtyToBeAdded | M_ShipmentSchedule_ID.Identifier | M_Product_ID.Identifier |
      | hu_stock           | 10           | ss_2                             | p_1                     |

    # Post-inventory state: the +10 stock (INVENTORY_UP) lifts order 1's ATP to -10; order 2 keeps its production supply.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -10                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_inv      | INVENTORY_UP      |                               | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |

    # SHIP order 2 ONLY from the on-hand inventory (no production). Order 1 is never shipped -> stays uncovered.
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_2                  | shipment_2 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
      | ss_2       | ol_2           | 0            | N             |
    # TRIGGER: REVERSE order 2's stock shipment (creates a reversal M_InOut) -> re-evaluate its demand.
    And the shipment identified by shipment_2 is reversed
    # Reversing fully undoes the delivery, so ss_2 reopens to QtyToDeliver 10; order 1 stays open at 20.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
      | ss_2       | ol_2           | 10           | N             |
    # Invariant: order 2's demand reopens (-10) and is covered by the returned inventory + its single existing
    # production candidate — NO new production. No extra SUPPLY / component-DEMAND rows and no duplicate production
    # candidate (lot-for-lot sizing to order 2's own qty, not global-ATP netting).
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -10                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_inv      | INVENTORY_UP        |                               | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 0    | -10                    |
    # Same guard, more readable: exactly ONE production candidate (order 2's own).
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_890
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Produced reverse variant: same setup as the produced reactivate case, but the trigger is a shipment REVERSAL.
  # Order 2 is produced and shipped; order 1 is never shipped -> stays UNCOVERED, global ATP negative. Reversing
  # order 2's shipment must re-evaluate its demand with lot-for-lot sizing, not ATP netting sized to order 1's
  # still-open demand — order 2 stays covered by its own produced stock, exactly ONE production candidate.
  Scenario: Lot for Lot - a reversed shipment stays lot-for-lot while an earlier order is uncovered
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | huPiLU                |
      | huPiTU                |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPiVersionLU      | huPiLU     | LU          | Y         |
      | huPiVersionTU      | huPiTU     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | huPiItemLU      | huPiVersionLU      | 10  | HU       | huPiTU            |
      | huPiItemTU      | huPiVersionTU      |     | MI       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_TU_10CU         | huPiItemTU      | p_1          | 10  | 2021-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists -> persistent negative ATP, no supply.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that auto-processes the production candidate (IsCreatePlan=true)
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  |

    # ORDER 2 (a day later), one full TU (10 PCE) so it can be produced and shipped as one HU.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 10         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 10           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -30                    |
      | c_s2a      | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -30                    |
      | c_cd2a     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # PRODUCE: generate the PP_Order from order 2's production candidate, receive the finished HU (one TU), complete it.
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_2                  |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | DatePromised         |
      | ppo_2      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10         | 2021-04-16T21:00:00Z |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | ppo_2       | hu_2               | N               | 0     | N               | 1     | N               | 10          | product_TU_10CU                    |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppo_2                  |
    # SETTLE-WAIT before shipment generation: the produced stock (+10 UNEXPECTED_INCREASE) must land first.
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |

    # SHIP order 2 ONLY (order 1 is never shipped -> stays uncovered).
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_2                  | shipment_2 |
    # Shipping updates the schedules -> wait for the SS recompute to settle BEFORE reading MD candidates.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
      | ss_2       | ol_2           | 0            | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 10   | -20                    |

    # TRIGGER: REVERSE order 2's completed shipment (creates a reversal M_InOut) -> undoes the delivery and
    # re-evaluates order 2's demand while order 1's -20 keeps global ATP negative.
    And the shipment identified by shipment_2 is reversed
    # Post-reverse checkpoint: 1. schedule barrier, 2. MD_Candidate has-only (primary), 3. PP_Order_Candidate
    # has-only (readability). Reversing fully undoes the delivery, so ss_2 reopens to QtyToDeliver 10 (vs 0 for the
    # reactivate trigger); order 1 stays open at 20. The MD/PP end-state is identical to the reactivate case.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
      | ss_2       | ol_2           | 10           | N             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -10                    |
      | c_d2       | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -20                    |
      | c_s2a      | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2a     | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_s2       | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -20                    |
      | c_cd2      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_prod     | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     | 2021-04-11T06:00:00Z | 10   | 10                     |
      | c_ship     | UNEXPECTED_DECREASE | SHIPMENT                      | p_1                     | 2021-04-15T22:00:00Z | 0    | -10                    |
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_900
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Capacity: a lot-for-lot order larger than MaxManufacturedQtyPerOrderDispo must split into multiple production
  # candidates (each <= the cap) — not collapse into a single candidate. Guards the per-order-capacity path for
  # lot-for-lot (the reuse-by-schedule must stay capacity-aware).
  Scenario: Lot for Lot - production respects MaxManufacturedQtyPerOrderDispo (capacity split)
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot | MaxManufacturedQtyPerOrderDispo |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  | 25 PCE                          |
    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 40         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 40           | N             |
    # 40 > cap 25 -> TWO production candidates (25 + 15), NOT one collapsed candidate.
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1a      | true      | p_1          | bom_1             | ppln_1                 | 540006        | 25         | 0            | 25           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_1b      | true      | p_1          | bom_1             | ppln_1                 | 540006        | 15         | 0            | 15           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_910
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Capacity on INCREASE (the reuse-grow path): an un-processed lot-for-lot candidate grown past
  # MaxManufacturedQtyPerOrderDispo must split into multiple candidates (each <= the cap) — the reuse-by-schedule
  # must neither grow one candidate beyond the cap nor collapse the split into a single candidate.
  Scenario: Lot for Lot - a qty increase past MaxManufacturedQtyPerOrderDispo splits production (capacity)
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot | MaxManufacturedQtyPerOrderDispo |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  | 25 PCE                          |
    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    # 20 <= cap 25 -> single un-processed production candidate
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # reactivate + increase the order line to 40 (> cap 25), then re-complete
    And the order identified by o_1 is reactivated
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 0            | N             |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_1                      | 40             | 40             |
    And the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 40           | N             |
    # 40 > cap 25 -> the reactivate drained the original candidate to 0 (deactivated, oc_1) and the re-complete
    # created a proper capacity SPLIT of the full new qty: 25 + 15 (each <= cap). NOT one grown to 40, NOT a
    # collapsed single candidate (the old reuse-by-schedule bug), NOT one candidate > cap.
    And after not more than 60s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | IsActive | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | false    | p_1          | bom_1             | ppln_1                 | 540006        | 0          | 0            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_1a      | false     | true     | p_1          | bom_1             | ppln_1                 | 540006        | 25         | 25           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_1b      | false     | true     | p_1          | bom_1             | ppln_1                 | 540006        | 15         | 15           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_920
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  Scenario: Lot for Lot - a closed prior candidate is immovable, so an order qty increase adds a new candidate
    # As S0264_820 (IsCreatePlan=false) but order 2's un-processed candidate is CLOSED before the reactivate+increase.
    # A closed candidate is immovable (IsClosed=Y, Processed=Y) — it must NOT be grown or emptied. The net shortfall
    # (own demand 40 - committed 20) becomes a NEW candidate; the closed one stays untouched. Order 1's -20 stays open.
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists -> persistent negative ATP, no supply.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that does NOT auto-process the production candidate
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot |
      | ppln_1     | p_1          | bomVersions_1             | false        | true                  |

    # ORDER 2 (a day later), completed AFTER planning -> lot-for-lot fires a production candidate for its own qty (20).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -20  | -40                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 20   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -200 | -200                   |
    And after not more than 30s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # CLOSE order 2's un-processed candidate -> it becomes immovable (IsClosed=Y, Processed=Y). closeCandidate sets
    # QtyEntered=QtyProcessed; since the candidate was un-processed (QtyProcessed=0), the closed candidate carries
    # QtyEntered=0, QtyProcessed=0 (QtyToProcess left at 20). Committed supply from this immovable candidate = 0.
    When the following PP_Order_Candidates are closed
      | PP_Order_Candidate_ID |
      | oc_2                  |
    And after not more than 30s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 0          | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | true     |

    # after-the-fact trigger: reactivate ORDER 2, INCREASE its line to 40, then re-complete
    And the order identified by o_2 is reactivated
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_2                      | 40             | 40             |
    And the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 40           | N             |
    # EXPECTED: the CLOSED candidate is immovable (committed = its QtyEntered = 0) and stays untouched. Order 2's demand
    # grows to 40, so the net shortfall (40 - 0) becomes a NEW candidate carrying the full 40. Total ACTIVE production for
    # order 2 = 0 (closed oc_2) + 40 (new oc_2b) = 40 = its own qty. Order 1's -20 stays open. At the MD level the closed
    # candidate's old component-demand row is drained to 0 (c_cd2) and the new supply carries a fresh -400 (c_cd2b).
    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20  | -20                    |
      | c_d2       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -40  | -60                    |
      | c_s2       | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 40   | -20                    |
      | c_cd2      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      |
      | c_cd2b     | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -400 | -400                   |
    And after not more than 30s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | IsActive | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | true     | p_1          | bom_1             | ppln_1                 | 540006        | 0          | 20           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | true     |
      | oc_2b      | false     | true     | p_1          | bom_1             | ppln_1                 | 540006        | 40         | 40           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |


  @Id:S0264_930
  @from:cucumber
  @allure.label.epic:E0159_Manufacturing_Planning
  @allure.label.feature:F8022_Lot_for_Lot_Manufacturing_Order_per_Sales_Order
  # Capacity + processed increase: as S0264_830 (IsCreatePlan=true, auto-processed) but with MaxManufacturedQtyPerOrderDispo
  # (25 PCE). Order 2 = 20 is produced as one processed candidate (immovable). On reactivate + increase to 60, the
  # processed 20 cannot be grown, so the delta (60 - 20 = 40) is produced as NEW candidates split by the cap: 25 + 15
  # (each <= cap). Total production for order 2 = 20 + 25 + 15 = 60 = its own qty. No chunk exceeds the cap.
  Scenario: Lot for Lot - a processed increase past capacity splits the delta only
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And metasfresh contains C_BPartners:
      | Identifier    | M_PricingSystem_ID |
      | endcustomer_1 | ps_1               |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # ORDER 1 (a day EARLIER), completed BEFORE any lot-for-lot planning exists -> persistent negative ATP, no supply.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-15  | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 20         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_1       | ol_1           | 20           | N             |
    And after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_d1       | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-15T21:00:00Z | -20 | -20                    |

    # lot-for-lot product planning that auto-processes the production candidate, with a per-order capacity cap of 25.
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsManufacturedLot4Lot | MaxManufacturedQtyPerOrderDispo |
      | ppln_1     | p_1          | bomVersions_1             | true         | true                  | 25 PCE                          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer_1 | 2021-04-16  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 20         |
    When the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 20           | N             |
    # 20 <= cap 25 -> a single processed production candidate.
    And after not more than 30s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    # after-the-fact trigger: reactivate ORDER 2, INCREASE its line to 60 (> processed 20, delta 40 > cap 25), re-complete
    And the order identified by o_2 is reactivated
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 0            | N             |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.QtyOrdered |
      | ol_2                      | 60             | 60             |
    And the order identified by o_2 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver | IsToRecompute |
      | ss_2       | ol_2           | 60           | N             |
    # EXPECTED: the processed 20 (oc_2) is immovable and untouched. The delta (60 - 20 = 40) is produced as NEW
    # candidates split by the cap: 25 + 15 (each <= cap 25). Total production for order 2 = 20 + 25 + 15 = 60 = its own
    # qty. Order 1's -20 stays open.
    And after not more than 30s, the PP_Order_Candidate table has only the following records
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 20         | 0            | 20           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_3a      | true      | p_1          | bom_1             | ppln_1                 | 540006        | 25         | 0            | 25           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_3b      | true      | p_1          | bom_1             | ppln_1                 | 540006        | 15         | 0            | 15           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
