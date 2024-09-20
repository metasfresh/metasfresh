@from:cucumber
@ghActions:run_on_executor6
Feature: Production dispo scenarios with BOMs whose components have their own BOMs in turn


  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | M_Product_Category_ID   | Name     | Value    |
      | standard_category_Sxxxx | Standard | Standard |
    And create S_Resource:
      | Identifier     | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | resource_Sxxxx | 1000000           | Y                       | PT                        | 999             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | PP_Plant_ID    |
      | WH_Sxxxx       | resource_Sxxxx |

  @Id:Sxxxx_10
  @from:cucumber
  Scenario:  There is a BOM with two components. One of those components' product is the main-product of its own BOM.
  We expect manufacturing candidates to be created for all involed BOMs' main-products,
  then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second set of manufacturing candidates to supply the additional demand.

    Given metasfresh contains M_Products:
      | Identifier           | M_Product_Category_ID   |
      | product_1_1_Sxxxx_10 | standard_category_Sxxxx |
      | product_2_1_Sxxxx_10 | standard_category_Sxxxx |
      | product_2_2_Sxxxx_10 | standard_category_Sxxxx |
      | product_3_1_Sxxxx_10 | standard_category_Sxxxx |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_1_Sxxxx_10 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1_Sxxxx_10 | ps_1_Sxxxx_10      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_1_Sxxxx_10 | pl_1_Sxxxx_10  |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_Sxxxx_10 | plv_1_Sxxxx_10         | product_1_1_Sxxxx_10 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier        | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_Sxxxx_10 | N        | Y          | ps_1_Sxxxx_10      |

    And metasfresh contains PP_Product_BOM
      | Identifier     | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1_Sxxxx_10 | product_1_1_Sxxxx_10    | 2024-09-01 | bomVersions_1_Sxxxx_10               |
      | bom_2_Sxxxx_10 | product_2_1_Sxxxx_10    | 2024-09-01 | bomVersions_2_Sxxxx_10               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier        | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_2_1_Sxxxx_10 | bom_1_Sxxxx_10               | product_2_1_Sxxxx_10    | 2024-09-01 | 20       |
      | boml_2_2_Sxxxx_10 | bom_1_Sxxxx_10               | product_2_2_Sxxxx_10    | 2024-09-01 | 10       |
      | boml_3_1_Sxxxx_10 | bom_2_Sxxxx_10               | product_3_1_Sxxxx_10    | 2024-09-01 | 5        |
    And the PP_Product_BOM identified by bom_1_Sxxxx_10 is completed
    And the PP_Product_BOM identified by bom_2_Sxxxx_10 is completed

    ## Since IsCreatePlan, we expect IsDocComplete to be ignored
    ## We expect the intermediate-PP_Order_Candidate of ppln_2_1_Sxxxx_10 to get SeqNo=10, but the finished-product PP_Order_Candidate of ppln_1_1_Sxxxx_10 shall get SeqNo=20!
    And metasfresh contains PP_Product_Plannings
      | Identifier        | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | OPT.IsCreatePlan | OPT.IsDocComplete | OPT.S_Resource_ID.Identifier | OPT.SeqNo |
      | ppln_1_1_Sxxxx_10 | product_1_1_Sxxxx_10    | bomVersions_1_Sxxxx_10                   | false            | true              | resource_Sxxxx               | 10        |
      | ppln_2_1_Sxxxx_10 | product_2_1_Sxxxx_10    | bomVersions_2_Sxxxx_10                   | false            | true              | resource_Sxxxx               | 10        |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID     | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1_Sxxxx_10 | true    | customer_Sxxxx_10 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_Sxxxx       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID         | QtyEntered |
      | ol_1_Sxxxx_10 | o_1_Sxxxx_10 | product_1_1_Sxxxx_10 | 1          |
    When the order identified by o_1_Sxxxx_10 is completed
    
    # for 1x product_1_1_Sxxxx_10 we need 20x product_2_1_Sxxxx_10 and 10x product_2_2_Sxxxx_10 and (20*5=) 100x product_3_1_Sxxxx_10
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier    | Processed | M_Product_ID         | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID  | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed | OPT.SeqNo |
      | oc_1_Sxxxx_10 | false     | product_1_1_Sxxxx_10 | bom_1_Sxxxx_10    | ppln_1_1_Sxxxx_10      | resource_Sxxxx | 1 PCE      | 1 PCE        | 0 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | false         | 20        |
      | oc_2_Sxxxx_10 | false     | product_2_1_Sxxxx_10 | bom_2_Sxxxx_10    | ppln_2_1_Sxxxx_10      | resource_Sxxxx | 20 PCE     | 20 PCE       | 0 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | false         | 10        |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID         | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1_Sxxxx_10         | product_2_1_Sxxxx_10 | 20 PCE     | CO            | boml_2_1_Sxxxx_10     |
      | oc_1_Sxxxx_10         | product_2_2_Sxxxx_10 | 10 PCE     | CO            | boml_2_2_Sxxxx_10     |
      | oc_2_Sxxxx_10         | product_3_1_Sxxxx_10 | 100 PCE    | CO            | boml_3_1_Sxxxx_10     |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier       | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | 01/d_1_1_Sxxxx_10 | DEMAND            | SHIPMENT                  | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 1   | -1   | WH_Sxxxx       |
      | 02/s_1_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 1   | 0    | WH_Sxxxx       |
      | 03/d_2_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | -20  | WH_Sxxxx       |
      | 03/d_2_2_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_2_Sxxxx_10 | 2024-09-22T21:00:00Z | 10  | -10  | WH_Sxxxx       |
      | 04/s_2_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | 0    | WH_Sxxxx       |
      | 05/d_3_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_3_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 100 | -100 | WH_Sxxxx       |
    
    ########################################
    #
    # Reactivate the order, change quantity from 10 PCE to 12 PCE and completed again.
    # Expect a new PP_Order_Candidate to be generated for those 2 PCE.
    When the order identified by o_1_Sxxxx_10 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1_Sxxxx_10             | 3              |
    And the order identified by o_1_Sxxxx_10 is completed

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier      | Processed | M_Product_ID         | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID  | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed | OPT.SeqNo |
      | oc_2_1_Sxxxx_10 | false     | product_1_1_Sxxxx_10 | bom_1_Sxxxx_10    | ppln_1_1_Sxxxx_10      | resource_Sxxxx | 2 PCE      | 2 PCE        | 0 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | false         | 20        |
      | oc_2_2_Sxxxx_10 | false     | product_2_1_Sxxxx_10 | bom_2_Sxxxx_10    | ppln_2_1_Sxxxx_10      | resource_Sxxxx | 40 PCE     | 40 PCE       | 0 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | false         | 10        |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID         | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_2_1_Sxxxx_10       | product_2_1_Sxxxx_10 | 40 PCE     | CO            | boml_2_1_Sxxxx_10     |
      | oc_2_1_Sxxxx_10       | product_2_2_Sxxxx_10 | 20 PCE     | CO            | boml_2_2_Sxxxx_10     |
      | oc_2_2_Sxxxx_10       | product_3_1_Sxxxx_10 | 200 PCE    | CO            | boml_3_1_Sxxxx_10     |

    ## starting at line 06, we have the new MD_Candidates which address the 2nd set of PP_Order_Candidates
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | 01/d_1_1_Sxxxx_10 | DEMAND            | SHIPMENT                  | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 3   | -3   | WH_Sxxxx       |
      | 02/s_1_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 1   | -2   | WH_Sxxxx       |
      | 03/d_2_2_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_2_Sxxxx_10 | 2024-09-22T21:00:00Z | 10  | -10  | WH_Sxxxx       |
      | 04/s_2_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | 0    | WH_Sxxxx       |
      | 05/d_3_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_3_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 100 | -100 | WH_Sxxxx       |
      | 06/d_2_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | -20  | WH_Sxxxx       |
      | 07/s_1_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 2   | 0    | WH_Sxxxx       |
      | 08/d_2_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 40  | -40  | WH_Sxxxx       |
      | 09/d_2_2_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_2_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | -20  | WH_Sxxxx       |
      | 10/s_2_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 40  | 0    | WH_Sxxxx       |
      | 11/d_3_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_3_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 200 | -200 | WH_Sxxxx       |

  @Id:Sxxxx_20
  @from:cucumber
  Scenario: Like Sxxxx_10, but now PP_Product_Planning.IsCreatePlan=true and we exprect PP_Orders to be created and completed right away

    Given metasfresh contains M_Products:
      | Identifier           | M_Product_Category_ID   |
      | product_1_1_Sxxxx_10 | standard_category_Sxxxx |
      | product_2_1_Sxxxx_10 | standard_category_Sxxxx |
      | product_2_2_Sxxxx_10 | standard_category_Sxxxx |
      | product_3_1_Sxxxx_10 | standard_category_Sxxxx |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_1_Sxxxx_10 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1_Sxxxx_10 | ps_1_Sxxxx_10      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_1_Sxxxx_10 | pl_1_Sxxxx_10  |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_Sxxxx_10 | plv_1_Sxxxx_10         | product_1_1_Sxxxx_10 | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier        | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_Sxxxx_10 | N        | Y          | ps_1_Sxxxx_10      |

    And metasfresh contains PP_Product_BOM
      | Identifier     | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1_Sxxxx_10 | product_1_1_Sxxxx_10    | 2024-09-01 | bomVersions_1_Sxxxx_10               |
      | bom_2_Sxxxx_10 | product_2_1_Sxxxx_10    | 2024-09-01 | bomVersions_2_Sxxxx_10               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier        | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_2_1_Sxxxx_10 | bom_1_Sxxxx_10               | product_2_1_Sxxxx_10    | 2024-09-01 | 20       |
      | boml_2_2_Sxxxx_10 | bom_1_Sxxxx_10               | product_2_2_Sxxxx_10    | 2024-09-01 | 10       |
      | boml_3_1_Sxxxx_10 | bom_2_Sxxxx_10               | product_3_1_Sxxxx_10    | 2024-09-01 | 5        |
    And the PP_Product_BOM identified by bom_1_Sxxxx_10 is completed
    And the PP_Product_BOM identified by bom_2_Sxxxx_10 is completed

    ## We expect the intermediate-PP_Order_Candidate of ppln_2_1_Sxxxx_10 to get SeqNo=10, but the finished-product PP_Order_Candidate of ppln_1_1_Sxxxx_10 shall get SeqNo=20!
    And metasfresh contains PP_Product_Plannings
      | Identifier        | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | OPT.IsCreatePlan | OPT.IsDocComplete | OPT.S_Resource_ID.Identifier | OPT.SeqNo |
      | ppln_1_1_Sxxxx_10 | product_1_1_Sxxxx_10    | bomVersions_1_Sxxxx_10                   | true             | true              | resource_Sxxxx               | 10        |
      | ppln_2_1_Sxxxx_10 | product_2_1_Sxxxx_10    | bomVersions_2_Sxxxx_10                   | true             | true              | resource_Sxxxx               | 10        |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID     | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1_Sxxxx_10 | true    | customer_Sxxxx_10 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_Sxxxx       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID         | QtyEntered |
      | ol_1_Sxxxx_10 | o_1_Sxxxx_10 | product_1_1_Sxxxx_10 | 1          |
    When the order identified by o_1_Sxxxx_10 is completed
    
    # for 1x product_1_1_Sxxxx_10 we need 20x product_2_1_Sxxxx_10 and 10x product_2_2_Sxxxx_10 and (20*5=) 100x product_3_1_Sxxxx_10
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier    | Processed | M_Product_ID         | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID  | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed | OPT.SeqNo |
      | oc_1_Sxxxx_10 | false     | product_1_1_Sxxxx_10 | bom_1_Sxxxx_10    | ppln_1_1_Sxxxx_10      | resource_Sxxxx | 1 PCE      | 0 PCE        | 1 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | true          | 20        |
      | oc_2_Sxxxx_10 | false     | product_2_1_Sxxxx_10 | bom_2_Sxxxx_10    | ppln_2_1_Sxxxx_10      | resource_Sxxxx | 20 PCE     | 0 PCE        | 20 PCE       | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | true          | 10        |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID         | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1_Sxxxx_10         | product_2_1_Sxxxx_10 | 20 PCE     | CO            | boml_2_1_Sxxxx_10     |
      | oc_1_Sxxxx_10         | product_2_2_Sxxxx_10 | 10 PCE     | CO            | boml_2_2_Sxxxx_10     |
      | oc_2_Sxxxx_10         | product_3_1_Sxxxx_10 | 100 PCE    | CO            | boml_3_1_Sxxxx_10     |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier       | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | 01/d_1_1_Sxxxx_10 | DEMAND            | SHIPMENT                  | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 1   | -1   | WH_Sxxxx       |
      | 02/s_1_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 1   | 0    | WH_Sxxxx       |
      | 03/d_2_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | -20  | WH_Sxxxx       |
      | 03/d_2_2_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_2_Sxxxx_10 | 2024-09-22T21:00:00Z | 10  | -10  | WH_Sxxxx       |
      | 04/s_2_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | 0    | WH_Sxxxx       |
      | 05/d_3_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_3_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 100 | -100 | WH_Sxxxx       |
    
    ########################################
    #
    # Reactivate the order, change quantity from 10 PCE to 12 PCE and completed again.
    # Expect a new PP_Order_Candidate to be generated for those 2 PCE.
    When the order identified by o_1_Sxxxx_10 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1_Sxxxx_10             | 3              |
    And the order identified by o_1_Sxxxx_10 is completed

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier      | Processed | M_Product_ID         | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID  | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed | OPT.SeqNo |
      | oc_2_1_Sxxxx_10 | false     | product_1_1_Sxxxx_10 | bom_1_Sxxxx_10    | ppln_1_1_Sxxxx_10      | resource_Sxxxx | 2 PCE      | 0 PCE        | 2 PCE        | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | true          | 20        |
      | oc_2_2_Sxxxx_10 | false     | product_2_1_Sxxxx_10 | bom_2_Sxxxx_10    | ppln_2_1_Sxxxx_10      | resource_Sxxxx | 40 PCE     | 0 PCE        | 40 PCE       | 2024-09-22T21:00:00Z | 2024-09-22T21:00:00Z | false        | true          | 10        |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID         | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_2_1_Sxxxx_10       | product_2_1_Sxxxx_10 | 40 PCE     | CO            | boml_2_1_Sxxxx_10     |
      | oc_2_1_Sxxxx_10       | product_2_2_Sxxxx_10 | 20 PCE     | CO            | boml_2_2_Sxxxx_10     |
      | oc_2_2_Sxxxx_10       | product_3_1_Sxxxx_10 | 200 PCE    | CO            | boml_3_1_Sxxxx_10     |

    ## starting at line 06, we have the new MD_Candidates which address the 2nd set of PP_Order_Candidates
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | 01/d_1_1_Sxxxx_10 | DEMAND            | SHIPMENT                  | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 3   | -3   | WH_Sxxxx       |
      | 02/s_1_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 1   | -2   | WH_Sxxxx       |
      | 03/d_2_2_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_2_Sxxxx_10 | 2024-09-22T21:00:00Z | 10  | -10  | WH_Sxxxx       |
      | 04/s_2_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | 0    | WH_Sxxxx       |
      | 05/d_3_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_3_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 100 | -100 | WH_Sxxxx       |
      | 06/d_2_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | -20  | WH_Sxxxx       |
      | 07/s_1_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_1_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 2   | 0    | WH_Sxxxx       |
      | 08/d_2_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 40  | -40  | WH_Sxxxx       |
      | 09/d_2_2_Sxxxx_10 | DEMAND            | PRODUCTION                | product_2_2_Sxxxx_10 | 2024-09-22T21:00:00Z | 20  | -20  | WH_Sxxxx       |
      | 10/s_2_1_Sxxxx_10 | SUPPLY            | PRODUCTION                | product_2_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 40  | 0    | WH_Sxxxx       |
      | 11/d_3_1_Sxxxx_10 | DEMAND            | PRODUCTION                | product_3_1_Sxxxx_10 | 2024-09-22T21:00:00Z | 200 | -200 | WH_Sxxxx       |
    