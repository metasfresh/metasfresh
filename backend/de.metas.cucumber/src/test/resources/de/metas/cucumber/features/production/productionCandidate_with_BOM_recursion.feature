@from:cucumber
@ghActions:run_on_executor6
Feature: Production dispo scenarios with BOMs whose components have their own BOMs in turn.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | M_Product_Category_ID   | Name     | Value    |
      | standard_category_S0460 | Standard | Standard |
    And create S_Resource:
      | Identifier     | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | resource_S0460 | 1000000           | Y                       | PT                        | 999             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | PP_Plant_ID    |
      | WH_S0460       | resource_S0460 |

  @Id:S0460_10
  @from:cucumber
  Scenario:  There is a BOM with two components. One of those components' product is the main-product of its own BOM.
  We expect manufacturing candidates to be created for all involed BOMs' main-products,
  then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second set of manufacturing candidates to supply the additional demand.

    Given metasfresh contains M_Products:
      | Identifier           | M_Product_Category_ID   | C_UOM_ID.X12DE355 |
      | product_1_1_S0460_10 | standard_category_S0460 | PCE               |
      | product_2_1_S0460_10 | standard_category_S0460 | PCE               |
      | product_2_2_S0460_10 | standard_category_S0460 | PCE               |
      | product_3_1_S0460_10 | standard_category_S0460 | PCE               |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | product_1_1_S0460_10    | PCE                    | KGM                  | 4            |
      | product_2_1_S0460_10    | PCE                    | KGM                  | 6            |

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_1_S0460_10 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1_S0460_10 | ps_1_S0460_10      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_1_S0460_10 | pl_1_S0460_10  |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0460_10 | plv_1_S0460_10         | product_1_1_S0460_10 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier        | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_S0460_10 | N        | Y          | ps_1_S0460_10      |

    And metasfresh contains PP_Product_BOM
      | Identifier     | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1_S0460_10 | product_1_1_S0460_10    | 2024-09-01 | bomVersions_1_S0460_10               |
      | bom_2_S0460_10 | product_2_1_S0460_10    | 2024-09-01 | bomVersions_2_S0460_10               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier        | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.IsQtyPercentage | C_UOM_ID.X12DE355 |
      | boml_2_1_S0460_10 | bom_1_S0460_10               | product_2_1_S0460_10    | 2024-09-01 | 140      | true                | KGM               |
      | boml_2_2_S0460_10 | bom_1_S0460_10               | product_2_2_S0460_10    | 2024-09-01 | 2        | false               | PCE               |
      | boml_3_1_S0460_10 | bom_2_S0460_10               | product_3_1_S0460_10    | 2024-09-01 | 3        | false               | PCE               |
    And the PP_Product_BOM identified by bom_1_S0460_10 is completed
    And the PP_Product_BOM identified by bom_2_S0460_10 is completed

    ## Since IsCreatePlan, we expect IsDocComplete to be ignored
    And metasfresh contains PP_Product_Plannings
      | Identifier        | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | OPT.IsCreatePlan | OPT.IsDocComplete | OPT.S_Resource_ID.Identifier | OPT.SeqNo |
      | ppln_1_1_S0460_10 | product_1_1_S0460_10    | bomVersions_1_S0460_10                   | false            | true              | resource_S0460               | 20        |
      | ppln_2_1_S0460_10 | product_2_1_S0460_10    | bomVersions_2_S0460_10                   | false            | true              | resource_S0460               | 10        |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID     | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1_S0460_10 | true    | customer_S0460_10 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_S0460       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID         | QtyEntered |
      | ol_1_S0460_10 | o_1_S0460_10 | product_1_1_S0460_10 | 11         |
    When the order identified by o_1_S0460_10 is completed
    
    # oc_1_S0460_10: we need 11PCE of the final product => 44KGM. 140% of 44KGM is 61.6KGM or the 
    # oc_2_S0460_10: 61.6KGM of the semi-product is actually 10.2666667 PCE, which is rounded up to 11PCE (because UOM-precision=0), so we have 11PCE also in the 2nd PP_Order_Candidate!
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier    | M_Product_ID         | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID  | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed | OPT.PP_Order_Candidate_Parent_ID |
      | oc_1_S0460_10 | product_1_1_S0460_10 | bom_1_S0460_10    | ppln_1_1_S0460_10      | resource_S0460 | 11 PCE     | 11 PCE       | 0 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | false         |                                  |
      | oc_2_S0460_10 | product_2_1_S0460_10 | bom_2_S0460_10    | ppln_2_1_S0460_10      | resource_S0460 | 11 PCE     | 11 PCE       | 0 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | false         | oc_1_S0460_10                    |
    # oc_2_S0460_10: we want to produce 11PCE of the semi-product, which times 3 is 33
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID         | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1_S0460_10         | product_2_1_S0460_10 | 61.6 KGM   | CO            | boml_2_1_S0460_10     |
      | oc_1_S0460_10         | product_2_2_S0460_10 | 22 PCE     | CO            | boml_2_2_S0460_10     |
      | oc_2_S0460_10         | product_3_1_S0460_10 | 33 PCE     | CO            | boml_3_1_S0460_10     |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | 01/d_1_1_S0460_10 | DEMAND            | SHIPMENT                  | product_1_1_S0460_10 | 2024-09-22T21:00:00Z | 11  | -11 | WH_S0460       |
      | 02/s_1_1_S0460_10 | SUPPLY            | PRODUCTION                | product_1_1_S0460_10 | 2024-09-22T21:00:00Z | 11  | 0   | WH_S0460       |
      | 03/d_2_1_S0460_10 | DEMAND            | PRODUCTION                | product_2_1_S0460_10 | 2024-09-22T21:00:00Z | 11  | -11 | WH_S0460       |
      | 04/d_2_2_S0460_10 | DEMAND            | PRODUCTION                | product_2_2_S0460_10 | 2024-09-22T21:00:00Z | 22  | -22 | WH_S0460       |
      | 05/s_2_1_S0460_10 | SUPPLY            | PRODUCTION                | product_2_1_S0460_10 | 2024-09-22T21:00:00Z | 11  | 0   | WH_S0460       |
      | 06/d_3_1_S0460_10 | DEMAND            | PRODUCTION                | product_3_1_S0460_10 | 2024-09-22T21:00:00Z | 33  | -33 | WH_S0460       |

  @Id:S0460_20
  @from:cucumber
  Scenario: Like S0460_20, but now PP_Product_Planning.IsCreatePlan=true and we exprect PP_Orders to be created and completed right away

    Given metasfresh contains M_Products:
      | Identifier           | M_Product_Category_ID   |
      | product_1_1_S0460_20 | standard_category_S0460 |
      | product_2_1_S0460_20 | standard_category_S0460 |
      | product_2_2_S0460_20 | standard_category_S0460 |
      | product_3_1_S0460_20 | standard_category_S0460 |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_1_S0460_20 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1_S0460_20 | ps_1_S0460_20      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_1_S0460_20 | pl_1_S0460_20  |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0460_20 | plv_1_S0460_20         | product_1_1_S0460_20 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier        | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_S0460_20 | N        | Y          | ps_1_S0460_20      |

    And metasfresh contains PP_Product_BOM
      | Identifier     | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1_S0460_20 | product_1_1_S0460_20    | 2024-09-01 | bomVersions_1_S0460_20               |
      | bom_2_S0460_20 | product_2_1_S0460_20    | 2024-09-01 | bomVersions_2_S0460_20               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier        | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_2_1_S0460_20 | bom_1_S0460_20               | product_2_1_S0460_20    | 2024-09-01 | 20       |
      | boml_2_2_S0460_20 | bom_1_S0460_20               | product_2_2_S0460_20    | 2024-09-01 | 10       |
      | boml_3_1_S0460_20 | bom_2_S0460_20               | product_3_1_S0460_20    | 2024-09-01 | 5        |
    And the PP_Product_BOM identified by bom_1_S0460_20 is completed
    And the PP_Product_BOM identified by bom_2_S0460_20 is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier        | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | OPT.IsCreatePlan | OPT.IsDocComplete | OPT.S_Resource_ID.Identifier | OPT.SeqNo |
      | ppln_1_1_S0460_20 | product_1_1_S0460_20    | bomVersions_1_S0460_20                   | true             | true              | resource_S0460               | 20        |
      | ppln_2_1_S0460_20 | product_2_1_S0460_20    | bomVersions_2_S0460_20                   | true             | true              | resource_S0460               | 10        |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID     | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1_S0460_20 | true    | customer_S0460_20 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_S0460       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID         | QtyEntered |
      | ol_1_S0460_20 | o_1_S0460_20 | product_1_1_S0460_20 | 1          |
    When the order identified by o_1_S0460_20 is completed
    
    # for 1x product_1_1_S0460_20 we need 20x product_2_1_S0460_20 and 10x product_2_2_S0460_20 and (20*5=) 100x product_3_1_S0460_20
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier    | M_Product_ID         | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID  | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed | OPT.PP_Order_Candidate_Parent_ID |
      | oc_1_S0460_20 | product_1_1_S0460_20 | bom_1_S0460_20    | ppln_1_1_S0460_20      | resource_S0460 | 1 PCE      | 0 PCE        | 1 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | true          |                                  |
      | oc_2_S0460_20 | product_2_1_S0460_20 | bom_2_S0460_20    | ppln_2_1_S0460_20      | resource_S0460 | 20 PCE     | 0 PCE        | 20 PCE       | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | true          | oc_1_S0460_20                    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID         | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1_S0460_20         | product_2_1_S0460_20 | 0 PCE      | CO            | boml_2_1_S0460_20     |
      | oc_1_S0460_20         | product_2_2_S0460_20 | 0 PCE      | CO            | boml_2_2_S0460_20     |
      | oc_2_S0460_20         | product_3_1_S0460_20 | 0 PCE      | CO            | boml_3_1_S0460_20     |

    # the first 1 MD_Candidates belonss the original demand. the next 5 belong to the PP_Order_Candidates; the last 5 MD_Candidates belong to the PP_Orders
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | 01/d_1_1_S0460_20 | DEMAND            | SHIPMENT                  | product_1_1_S0460_20 | 2024-09-22T21:00:00Z | 1   | -1   | WH_S0460       |
      | 02/s_1_1_S0460_20 | SUPPLY            | PRODUCTION                | product_1_1_S0460_20 | 2024-09-22T21:00:00Z | 0   | -1   | WH_S0460       |
      | 03/d_2_1_S0460_20 | DEMAND            | PRODUCTION                | product_2_1_S0460_20 | 2024-09-22T21:00:00Z | 0   | 0    | WH_S0460       |
      | 04/d_2_2_S0460_20 | DEMAND            | PRODUCTION                | product_2_2_S0460_20 | 2024-09-22T21:00:00Z | 0   | 0    | WH_S0460       |
      | 05/s_2_1_S0460_20 | SUPPLY            | PRODUCTION                | product_2_1_S0460_20 | 2024-09-22T21:00:00Z | 0   | 0    | WH_S0460       |
      | 06/d_3_1_S0460_20 | DEMAND            | PRODUCTION                | product_3_1_S0460_20 | 2024-09-22T21:00:00Z | 0   | 0    | WH_S0460       |
      | 07/s_1_1_S0460_20 | SUPPLY            | PRODUCTION                | product_1_1_S0460_20 | 2024-09-22T21:00:00Z | 1   | 0    | WH_S0460       |
      | 08/d_2_1_S0460_20 | DEMAND            | PRODUCTION                | product_2_1_S0460_20 | 2024-09-22T21:00:00Z | 20  | -20  | WH_S0460       |
      | 09/d_2_2_S0460_20 | DEMAND            | PRODUCTION                | product_2_2_S0460_20 | 2024-09-22T21:00:00Z | 10  | -10  | WH_S0460       |
      | 10/s_2_1_S0460_20 | SUPPLY            | PRODUCTION                | product_2_1_S0460_20 | 2024-09-22T21:00:00Z | 20  | 0    | WH_S0460       |
      | 11/d_3_1_S0460_20 | DEMAND            | PRODUCTION                | product_3_1_S0460_20 | 2024-09-22T21:00:00Z | 100 | -100 | WH_S0460       |
    