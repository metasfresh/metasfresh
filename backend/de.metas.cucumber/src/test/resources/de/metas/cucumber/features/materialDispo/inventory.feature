@from:cucumber
@ghActions:run_on_executor6
Feature: Physical inventory and disposal are correctly considered in Material Dispo

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

  @from:cucumber
  @Id:S0124_100
  @Id:S0223_100
  Scenario: Physical inventory is correctly considered in Material Dispo when the product is both Sold and Purchased
    Given metasfresh contains M_Products:
      | Identifier | Name               | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | product_30032022_1 | standard_category                    | Y          | Y               |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                               | p_1                     | 2021-04-10T21:00:00Z | 10  | 10                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-10  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
    When the inventory identified by i_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2021-04-10T21:00:00Z | 10  | 10                     |                                 |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 20                     | 2021-04-16T00:00:00             |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-10  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 10                           | 10                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 20                             | 20                         | 0                                  |


  @from:cucumber
  @Id:S0124_110
  Scenario: Physical inventory is correctly considered in Material Dispo when the product is Manufactured
    Given metasfresh contains M_Products:
      | Identifier | Name                                |
      | p_1        | trackedProduct_30032022_2           |
      | p_2        | trackedProduct_component_30032022_2 |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                               | p_1                     | 2021-04-10T21:00:00Z | 10  | 10                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-10  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
    When the inventory identified by i_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2021-04-10T21:00:00Z | 10  | 10                     |                                 |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 20                     | 2021-04-16T00:00:00             |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-10  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 10                           | 10                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 20                             | 20                         | 0                                  |


  @from:cucumber
  @Id:S0124_120
  Scenario: Physical inventory is correctly considered in Material Dispo when the product is a component in a BOM
    Given metasfresh contains M_Products:
      | Identifier | Name                                |
      | p_1        | trackedProduct_31032022_3           |
      | p_2        | trackedProduct_component_31032022_3 |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                               | p_2                     | 2021-04-10T21:00:00Z | 10  | 10                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_2                     | 2021-04-10  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_2                     | PCE          | 10       | 0       |
    When the inventory identified by i_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_2                     | 2021-04-10T21:00:00Z | 10  | 10                     |                                 |
      | c_2        | INVENTORY_UP      |                               | p_2                     |                      | 10  | 20                     | 2021-04-16T00:00:00             |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_2                     | 2021-04-10  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
      | cp_2       | p_2                     | 2021-04-16  |                              | 10                           | 10                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 20                             | 20                         | 0                                  |

  @from:cucumber
  @Id:S0223_400
  Scenario: Physical inventory with two lines (same product, no ASI) is correctly considered in Material Dispo and in MD_Cockpit
    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_1        | product_24112022_1 |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
      | il_2       | i_1                       | p_1                     | PCE          | 12       | 0       |
    When the inventory identified by i_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |               | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |               | 12  | 22                     | 2021-04-16T00:00:00             |
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_2       | p_1                     | 2021-04-16  |                              | 22                           | 22                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 22                             | 22                         | 0                                  |

  @from:cucumber
  @Id:S0223_500
  Scenario: Physical inventory with two lines (same product, one with ASI, one without) is correctly considered in Material Dispo and in MD_Cockpit
    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_1        | product_24112022_2 |
    And metasfresh contains M_AttributeSetInstance with identifier "ilASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.M_AttributeSetInstance_ID.Identifier |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |                                          |
      | il_2       | i_1                       | p_1                     | PCE          | 12       | 0       | ilASI                                    |
    When the inventory identified by i_1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | INVENTORY_UP      |                               | p_1                     |               | 10  | 10                     | 2021-04-16T00:00:00             |                                          |
      | c_2        | INVENTORY_UP      |                               | p_1                     |               | 12  | 12                     | 2021-04-16T00:00:00             | ilASI                                    |
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                           | 10                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
      | cp_2       | p_1                     | 2021-04-16  | ilASI                        | 12                           | 12                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 12                             | 12                         | 0                                  |

  @from:cucumber
  @Id:S0223_600
  Scenario: Physical inventory with two lines (same product, one with ASI, one without). Dispose HU for the first line. Verify MD_Candidate and MD_Cockpit
    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_1        | product_24112022_3 |
    And metasfresh contains M_AttributeSetInstance with identifier "ilASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.M_AttributeSetInstance_ID.Identifier |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |                                          |
      | il_2       | i_1                       | p_1                     | PCE          | 12       | 0       | ilASI                                    |
    When the inventory identified by i_1 is completed

    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | INVENTORY_UP      |                               | p_1                     |               | 10  | 10                     | 2021-04-16T00:00:00             |                                          |
      | c_2        | INVENTORY_UP      |                               | p_1                     |               | 12  | 12                     | 2021-04-16T00:00:00             | ilASI                                    |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                           | 10                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                                  |
      | cp_2       | p_1                     | 2021-04-16  | ilASI                        | 12                           | 12                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 12                             | 12                         | 0                                  |

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |

    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_1               | 2021-04-16T00:00:00Z |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 0                            | 0                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 0                              | 0                          | 0                                  |
      | cp_2       | p_1                     | 2021-04-16  | ilASI                        | 12                           | 12                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 12                             | 12                         | 0                                  |
