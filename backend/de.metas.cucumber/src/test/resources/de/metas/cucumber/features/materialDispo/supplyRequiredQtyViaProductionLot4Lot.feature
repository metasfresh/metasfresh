@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: Disposal is correctly considered in Material Dispo. Stock shortage solved via production
## F5100: Material Disposition

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
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
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
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
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

