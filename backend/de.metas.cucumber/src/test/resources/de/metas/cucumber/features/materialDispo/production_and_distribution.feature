@from:cucumber
@ghActions:run_on_executor6
Feature: Production + Distribution material dispo scenarios

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config DDOrder_isCreateMovementOnComplete
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And metasfresh contains M_Products:
      | Identifier  |
      | bom_product |
      | component   |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | bom_product  | 10.0     | PCE      |



    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID  | IsInTransit | PP_Plant_ID |
      | inTransit       | true        |             |
      | rawMaterials_WH | false       |             |
      | production_WH   | false       | plant       |

    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | N1                        |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | NL1                           | N1                        | production_WH  | rawMaterials_WH      | shipper      |



    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | bom_product  | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | component    | 1        |
    And the PP_Product_BOM identified by bom_1 is completed


    And metasfresh contains PP_Product_Plannings
      | Identifier           | M_Product_ID | PP_Product_BOMVersions_ID | DD_NetworkDistribution_ID | M_Warehouse_ID | S_Resource_ID |
      | bom_product_planning | bom_product  | bomVersions_1             |                           | production_WH  | plant         |
      | component_planning   | component    |                           | N1                        | production_WH  | plant         |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps_1               |

    
    
    
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: sales order -> PP_Order_Candidate -> DD_Order_Candidate
    When update existing PP_Product_Plannings
      | Identifier           | IsCreatePlan |
      | bom_product_planning | N            |
      | component_planning   | N            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO         | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | SO_L1      | SO         | bom_product  | 10         |
    When the order identified by SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | Identifier | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | ocl_1      | oc_1                  | component    | 10 PCE     | CO            | boml_1                |

    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_Candidate_ID | Forward_PP_OrderLine_Candidate_ID |
      | ddoc1      | component    | rawMaterials_WH     | production_WH    | 10 PCE | N         | oc_1                          | ocl_1                             |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID  | DD_Order_Candidate_ID | PP_Order_Candidate_ID | PP_OrderLine_Candidate_ID |
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -10                    | production_WH   | -                     | -                     | -                         |
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 10  | 0                      | production_WH   | -                     | oc_1                  | -                         |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | -10                    | production_WH   | -                     | oc_1                  | ocl_1                     |
      | 4          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | 0                      | production_WH   | ddoc1                 | -                     | -                         |
      | 5          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | -10                    | rawMaterials_WH | ddoc1                 | -                     | -                         |

    
    
    
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: sales order -> PP_Order_Candidate + PP_Order -> DD_Order_Candidate
    When update existing PP_Product_Plannings
      | Identifier           | IsCreatePlan |
      | bom_product_planning | Y            |
      | component_planning   | N            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO         | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | SO_L1      | SO         | bom_product  | 10         |
    When the order identified by SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | Identifier | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | ocl_1      | oc_1                  | component    | 0 PCE      | CO            | boml_1                |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         |
      | ppOrder    | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 10         | customer      | 2021-04-16T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | Identifier     | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | ppOrderBOMLine | ppOrder     | component    | 10 PCE       | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_1                  | ppOrder     | 10 PCE     |

    And after not more than 60s, following DD_Order_Candidates are found
      | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID |
      | component    | rawMaterials_WH     | production_WH    | 10 PCE | N         | ppOrder             |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID  |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -10                    | production_WH   |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 0   | -10                    | production_WH   |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | 0   | 0                      | production_WH   |
      # DD_Order_Candidate:
      | 6          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | 0                      | production_WH   |
      | 7          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | -10                    | rawMaterials_WH |
      # PP_Order:
      | 4          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 10  | 0                      | production_WH   |
      | 5          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | -10                    | production_WH   |

    
    
    


# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @ignore #DD_OrderCandidate is not processed as expected
  Scenario: sales order -> PP_Order_Candidate + PP_Order -> DD_Order_Candidate + DD_Order
    When update existing PP_Product_Plannings
      | Identifier           | IsCreatePlan |
      | bom_product_planning | Y            |
      | component_planning   | Y            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO         | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | SO_L1      | SO         | bom_product  | 10         |
    When the order identified by SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | Identifier | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | ocl_1      | oc_1                  | component    | 0 PCE      | CO            | boml_1                |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         |
      | ppOrder    | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 10         | customer      | 2021-04-16T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | Identifier     | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType |
      | ppOrderBOMLine | ppOrder     | component    | 10 PCE       | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_1                  | ppOrder     | 10 PCE     |

    And after not more than 60s, following DD_Order_Candidates are found
      | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | QtyProcessed | QtyToProcess | Processed | Forward_PP_Order_Candidate_ID | Forward_PP_OrderLine_Candidate_ID | Forward_PP_Order_ID | C_OrderSO_ID | C_OrderLineSO_ID |
      | component    | rawMaterials_WH     | production_WH    | 10 PCE | 10 PCE       | 0 PCE        | Y         | oc_1                          | ocl_1                             | ppOrder             | SO           | SO_L1            |
    And after not more than 60s, DD_OrderLine found for orderLine SO_L1
      | Identifier | DD_Order_ID | M_Product_ID | QtyEntered | M_Warehouse_From_ID | M_Warehouse_To_ID |
      | ddol1      | ddo         | component    | 10         | rawMaterials_WH     | production_WH     |
    And after not more than 60s, following DD_Orders are found
      | Identifier | Forward_PP_Order_ID | C_Order_ID |
      | ddo        | ppOrder             | SO         |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 0   | -10 | production_WH   |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | 0   | 0   | production_WH   |
      # DD_Order_Candidate:
      | 4          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | 0   | production_WH   |
      | 5          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | 0   | rawMaterials_WH |
      # PP_Order:
      | 6          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   |
      | 7          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   | 
      # DD_Order:
      | 8          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   |
      | 9          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | -10 | rawMaterials_WH |








# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: sales order -> PP_Order_Candidate -> DD_Order_Candidate with products partially on stock
    When update existing PP_Product_Plannings
      | Identifier           | IsCreatePlan |
      | bom_product_planning | N            |
      | component_planning   | N            |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  |
      | stock1     | INVENTORY_UP      |                           | bom_product  | 2021-04-01T21:00:00Z | 3   | 3   | production_WH   |
      | stock2     | INVENTORY_UP      |                           | component    | 2021-04-01T21:00:00Z | 2   | 2   | production_WH   |
      | stock3     | INVENTORY_UP      |                           | component    | 2021-04-01T21:00:00Z | 1   | 1   | rawMaterials_WH |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO         | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | SO_L1      | SO         | bom_product  | 10         |
    When the order identified by SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | bom_product  | bom_1             | bom_product_planning   | plant         | 7 PCE      | 7 PCE        | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | Identifier | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | ocl_1      | oc_1                  | component    | 7 PCE      | CO            | boml_1                |

    And after not more than 60s, following DD_Order_Candidates are found
      | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty   | Processed | Forward_PP_Order_Candidate_ID | Forward_PP_OrderLine_Candidate_ID |
      | component    | rawMaterials_WH     | production_WH    | 5 PCE | N         | oc_1                          | ocl_1                             |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  |
      # Already existing stock (see above):
      | stock1     | INVENTORY_UP      |                           | bom_product  | 2021-04-01T21:00:00Z | 3   | 3   | production_WH   |
      | stock2     | INVENTORY_UP      |                           | component    | 2021-04-01T21:00:00Z | 2   | 2   | production_WH   |
      | stock3     | INVENTORY_UP      |                           | component    | 2021-04-01T21:00:00Z | 1   | 1   | rawMaterials_WH |
      # Sales Order / Shipment Schedule
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -7  | production_WH   |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 7   | 0   | production_WH   |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -7  | -5  | production_WH   |
      # DD_Order_Candidate:
      | 4          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 5   | 0   | production_WH   |
      | 5          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -5  | -4  | rawMaterials_WH |











# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: sales order -> PP_Order_Candidate -> DD_Order_Candidate -> Manually process PP_Order_Candidate -> Manually process DD_Order_Candidate
    When update existing PP_Product_Plannings
      | Identifier           | IsCreatePlan |
      | bom_product_planning | N            |
      | component_planning   | N            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | SO         | true    | customer      | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | SO_L1      | SO         | bom_product  | 10         |
    
    #
    # Complete the Sales order and expect PP_Order_Candidate and DD_Order_Candidate to be generated
    When the order identified by SO is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | Identifier | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | ocl_1      | oc_1                  | component    | 10 PCE     | CO            | boml_1                |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | QtyProcessed | QtyToProcess | Processed | Forward_PP_Order_Candidate_ID | Forward_PP_OrderLine_Candidate_ID | Forward_PP_Order_ID |
      | ddoc       | component    | rawMaterials_WH     | production_WH    | 10 PCE | 0 PCE        | 10 PCE       | N         | oc_1                          | ocl_1                             | -                   |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   |
      # DD_Order_Candidate:
      | 4          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   |
      | 5          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | -10 | rawMaterials_WH |

    #
    # Process the PP_Order_Candidate and expect PP_Order to be generated
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_1                  |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | QtyToProcess | QtyProcessed |
      | oc_1       | true      | 0 PCE        | 10 PCE       |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         |
      | ppOrder    | bom_product  | bom_1             | bom_product_planning   | plant         | 10 PCE     | 10         | customer      | 2021-04-16T21:00:00Z |
    And after not more than 60s, following DD_Order_Candidates are found
      | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty    | Processed | Forward_PP_Order_ID |
      | component    | rawMaterials_WH     | production_WH    | 10 PCE | N         | ppOrder             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 0   | -10 | production_WH   |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | 0   | 0   | production_WH   |
      # DD_Order_Candidate:
      | 4          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   |
      | 5          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | -10 | rawMaterials_WH |
      # PP_Order:
      | 6          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   |
      | 7          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   |

    #
    # Process the DD_Order_Candidate and expect DD_Order to be generated
    And the following DD_Order_Candidates are enqueued for generating DD_Orders
      | DD_Order_Candidate_ID |
      | ddoc                  |
    And after not more than 60s, DD_OrderLine found for orderLine SO_L1
      | Identifier | DD_Order_ID | M_Product_ID | QtyEntered | M_Warehouse_From_ID | M_Warehouse_To_ID |
      | ddol       | ddo         | component    | 10         | rawMaterials_WH     | production_WH     |
    And after not more than 60s, following DD_Orders are found
      | Identifier | Forward_PP_Order_ID |
      | ddo        | ppOrder             |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID  | Forward_PP_Order_ID |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | bom_product  | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   | -                   |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 0   | -10 | production_WH   | -                   |
      | 3          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | 0   | 0   | production_WH   | -                   |
      # DD_Order_Candidate:
      | 4          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | 0   | production_WH   | ppOrder             |
      | 5          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 0   | 0   | rawMaterials_WH | ppOrder             |
      # PP_Order:
      | 6          | SUPPLY            | PRODUCTION                | bom_product  | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   | -                   |
      | 7          | DEMAND            | PRODUCTION                | component    | 2021-04-16T21:00:00Z | -10 | -10 | production_WH   | -                   |
      # DD_Order:
      | 8          | SUPPLY            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | 10  | 0   | production_WH   | ppOrder             |
      | 9          | DEMAND            | DISTRIBUTION              | component    | 2021-04-16T21:00:00Z | -10 | -10 | rawMaterials_WH | ppOrder             |
    

    




    


