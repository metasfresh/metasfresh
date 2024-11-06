@from:cucumber
@ghActions:run_on_executor6
Feature: create multiple production candidates
  As a user
  I want to create multiple production candidates for the same Sales Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_AttributeSet:
      | M_AttributeSet_ID              | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID | Name     | Value    |
      | standard_category     | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID | M_AttributeSet_ID              |
      | standard_category     | attributeSet_convenienceSalate |
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 0        |
    And create S_Resource:
      | Identifier         | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | testResource_S0212 | 1000000           | Y                       | PT                        | 999             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID      | PP_Plant_ID        |
      | production_WH_S0212 | testResource_S0212 |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1_S0212  | standard_category     |
      | p_2_S0212  | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1_S0212 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1_S0212 | ps_1_S0212         | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | plv_1_S0212 | pl_1_S0212     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0212 | plv_1_S0212            | p_1_S0212    | 10.0     | PCE      | Normal           |
      | pp_2_S0212 | plv_1_S0212            | p_2_S0212    | 10.0     | PCE      | Normal           |

    And metasfresh contains PP_Product_BOM
      | Identifier  | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1_S0212 | p_1_S0212    | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier   | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1_S0212 | bom_1_S0212       | p_2_S0212    | 10       |
    And the PP_Product_BOM identified by bom_1_S0212  is completed
    And metasfresh contains C_BPartners:
      | Identifier          | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_2_S0212 | N        | Y          | ps_1_S0212         |

    And metasfresh contains PP_Product_Plannings
      | Identifier   | M_Product_ID | S_Resource_ID      | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1_S0212 | p_1_S0212    | testResource_S0212 | bomVersions_1             | false        |













# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @Id:S0129.1_140
  @Id:S0212.100
  @from:cucumber
  Scenario:  The manufacturing candidate is created for a sales order line,
  then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second manufacturing candidate to supply the additional demand.
  Also validate that PP_Order_Candidate is marked as 'processed' after PP_Order is created.

    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID       | DateOrdered | PreparationDate      | M_Warehouse_ID      | OPT.POReference |
      | o_1_S0212_100 | true    | endcustomer_2_S0212 | 2021-04-17  | 2021-04-16T21:00:00Z | production_WH_S0212 | S0212.100       |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered |
      | ol_1_S0212_100 | o_1_S0212_100 | p_1_S0212    | 10         |
    And update S_Resource:
      | S_Resource_ID.Identifier | CapacityPerProductionCycle |
      | testResource_S0212       | 2 KGM                      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1_S0212               | PCE                    | KGM                  | 0.1          |

    #
    # Complete the sales order and expected the PP_Order_Candidate to be generated.
    When the order identified by o_1_S0212_100 is completed
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier     | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1_S0212_100 | false     | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 120s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1_S0212_100        | p_2_S0212    | 100 PCE    | CO            | boml_1_S0212          |
    And after not more than 120s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_Warehouse_ID      |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1_S0212    | 2021-04-16T21:00:00Z | -10  | -10  | production_WH_S0212 |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1_S0212    | 2021-04-16T21:00:00Z | 10   | 0    | production_WH_S0212 |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_2_S0212    | 2021-04-16T21:00:00Z | -100 | -100 | production_WH_S0212 |

    #
    # Reactivate the order, change quantity from 10 PCE to 12 PCE and completed again.
    # Expect a new PP_Order_Candidate to be generated for those 2 PCE.
    And the order identified by o_1_S0212_100 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1_S0212_100            | 12             |
    And the order identified by o_1_S0212_100 is completed
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier     | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2_S0212_100 | false     | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 2 PCE      | 2 PCE        | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 120s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_2_S0212_100        | p_2_S0212    | 20 PCE     | CO            | boml_1_S0212          |
    And after not more than 120s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_Warehouse_ID      |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1_S0212    | 2021-04-16T21:00:00Z | 12  | -12  | production_WH_S0212 |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1_S0212    | 2021-04-16T21:00:00Z | 10  | -2   | production_WH_S0212 |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_2_S0212    | 2021-04-16T21:00:00Z | 100 | -100 | production_WH_S0212 |
      | 4/c_3      | SUPPLY            | PRODUCTION                | p_1_S0212    | 2021-04-16T21:00:00Z | 2   | 0    | production_WH_S0212 |
      | 5/c_l_3    | DEMAND            | PRODUCTION                | p_2_S0212    | 2021-04-16T21:00:00Z | 20  | -120 | production_WH_S0212 |

    #
    # Process those 2 PP_Order_Candidates.
    # Expect one PP_Order with 12 PCE to be generated for both of them.
    # We expect only one PP_Order because 12 PCE = 1.2 KGM < 2 KGM (capacity per cycle).
    When generate PP_Order process is invoked for selection, with completeDocument=false and autoProcessCandidateAfterProduction=false
      | PP_Order_Candidate_ID |
      | oc_1_S0212_100        |
      | oc_2_S0212_100        |
    Then after not more than 120s, PP_Orders are found
      | Identifier      | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyOrdered | C_BPartner_ID       | DatePromised         | DocStatus |
      | ppo_1_S0212_100 | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 12 PCE     | 12         | endcustomer_2_S0212 | 2021-04-16T21:00:00Z | DR        |
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier     | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1_S0212_100 | true      | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_2_S0212_100 | true      | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 2 PCE      | 0 PCE        | 2 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 120s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID     | QtyEntered |
      | oc_1_S0212_100        | ppo_1_S0212_100 | 10 PCE     |
      | oc_2_S0212_100        | ppo_1_S0212_100 | 2 PCE      |
    And after not more than 120s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_Warehouse_ID      |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1_S0212    | 2021-04-16T21:00:00Z | 12  | -12  | production_WH_S0212 |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1_S0212    | 2021-04-16T21:00:00Z | 0   | -12  | production_WH_S0212 |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_2_S0212    | 2021-04-16T21:00:00Z | 0   | 0    | production_WH_S0212 |
      | 4/c_3      | SUPPLY            | PRODUCTION                | p_1_S0212    | 2021-04-16T21:00:00Z | 0   | -12  | production_WH_S0212 |
      | 5/c_l_3    | DEMAND            | PRODUCTION                | p_2_S0212    | 2021-04-16T21:00:00Z | 0   | 0    | production_WH_S0212 |
      | 6          | SUPPLY            | PRODUCTION                | p_1_S0212    | 2021-04-16T21:00:00Z | 12  | 0    | production_WH_S0212 |
      | 7          | DEMAND            | PRODUCTION                | p_2_S0212    | 2021-04-16T21:00:00Z | 120 | -120 | production_WH_S0212 |














# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @from:cucumber
  @Id:S0212.200
  Scenario:  The manufacturing candidate is created for a sales order line and `Generate PP_Order` process is invoked resulting multiple manufacturing orders
  and the candidate remains open as it still has unprocessed quantity and `autoProcessCandidates` parameter is not set.

    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID       | DateOrdered | PreparationDate      | M_Warehouse_ID      | OPT.POReference |
      | o_2_S0212_200 | true    | endcustomer_2_S0212 | 2022-10-10  | 2022-10-10T21:00:00Z | production_WH_S0212 | S0212.200       |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID    | M_Product_ID | QtyEntered |
      | ol_2_S0212_200 | o_2_S0212_200 | p_1_S0212    | 12         |
    And update S_Resource:
      | S_Resource_ID      | CapacityPerProductionCycle |
      | testResource_S0212 | 5 PCE                      |

    #
    # Complete the sales order, update the PP_Order_Candidate qty from 12 PCE to 11 PCE
    When the order identified by o_2_S0212_200 is completed
    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier       | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate | false     | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 12 PCE     | 12 PCE       | 0 PCE        | 2022-10-10T21:00:00Z | 2022-10-10T21:00:00Z | false    |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID | QtyToProcess |
      | ppOrderCandidate      | 11           |

    #
    # Generate 3 PP_Orders (because of capacity per cycle)
    When generate PP_Order process is invoked for selection, with completeDocument=true and autoProcessCandidateAfterProduction=false
      | PP_Order_Candidate_ID |
      | ppOrderCandidate      |
    Then after not more than 120s, load PP_Order by candidate id: ppOrderCandidate
      | PP_Order_ID | QtyEntered |
      | ppOrder_1   | 5          |
      | ppOrder_2   | 5          |
      | ppOrder_3   | 1          |
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier       | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate | false     | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 12 PCE     | 1 PCE        | 11 PCE       | 2022-10-10T21:00:00Z | 2022-10-10T21:00:00Z | false    |
    And after not more than 120s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyOrdered | C_BPartner_ID       | DatePromised         | DocStatus |
      | ppOrder_1  | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 5 PCE      | 5          | endcustomer_2_S0212 | 2022-10-10T21:00:00Z | CO        |
      | ppOrder_2  | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 5 PCE      | 5          | endcustomer_2_S0212 | 2022-10-10T21:00:00Z | CO        |
      | ppOrder_3  | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 1 PCE      | 1          | endcustomer_2_S0212 | 2022-10-10T21:00:00Z | CO        |
    And after not more than 120s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | ppOrderCandidate      | ppOrder_1   | 5 PCE      |
      | ppOrderCandidate      | ppOrder_2   | 5 PCE      |
      | ppOrderCandidate      | ppOrder_3   | 1 PCE      |








# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @from:cucumber
  @Id:S0212.300
  @flaky # https://github.com/metasfresh/metasfresh/actions/runs/10504799864/job/29103416885?pr=18701
  Scenario: The manufacturing candidate is created for a sales order line and then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second manufacturing candidate to supply the additional demand
  and openQty for the second candidate is decreased
  then `Generate PP_Order`process is invoked enforcing the candidates to be processed
  and both candidates are marked as processed

    Given update existing PP_Product_Plannings
      | Identifier   | MaxManufacturedQtyPerOrderDispo | SeqNo |
      | ppln_1_S0212 | 10 PCE                          | 10    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID       | DateOrdered | PreparationDate      | M_Warehouse_ID      |
      | o_3        | true    | endcustomer_2_S0212 | 2022-11-07  | 2022-11-07T21:00:00Z | production_WH_S0212 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_3       | o_3        | p_1_S0212    | 3          |
    And update S_Resource:
      | S_Resource_ID.Identifier | CapacityPerProductionCycle |
      | testResource_S0212       | 5 PCE                      |

    When the order identified by o_3 is completed

    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | SeqNo |
      | ppOrderCandidate_3_1 | false     | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 3 PCE      | 3 PCE        | 0 PCE        | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    | 10    |

    And the order identified by o_3 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_3                      | 12             |
    And the order identified by o_3 is completed
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | SeqNo |
      | ppOrderCandidate_3_2 | false     | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 9 PCE      | 9 PCE        | 0 PCE        | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    | 10    |

    And update PP_Order_Candidates
      | PP_Order_Candidate_ID | QtyToProcess |
      | ppOrderCandidate_3_2  | 4            |

    When generate PP_Order process is invoked for selection, with completeDocument=true and autoProcessCandidateAfterProduction=true
      | PP_Order_Candidate_ID |
      | ppOrderCandidate_3_1  |
      | ppOrderCandidate_3_2  |

    # we are expecting two PP_Orders for ppOrderCandidate_3_2, because
    # CapacityPerProductionCycle=5, and the two candidates sum up to a quantity of 3+4=7
    # so all (3) of ppOrderCandidate_3_1 end up in the first PP_Order, i.e. ppOrder_3_1.
    # Then of ppOrderCandidate_3_2's 4PCE, 2 end up on the same PP_Order ppOrder_3_1 which then is (full) with 5 items,
    # Therefore the remaining 2PCE of ppOrderCandidate_3_2 end up in a new PP_Order, i.e. ppOrder_3_2.
    Then after not more than 120s, load PP_Order by candidate id: ppOrderCandidate_3_2
      | PP_Order_ID | QtyEntered |
      | ppOrder_3_1 | 2          |
      | ppOrder_3_2 | 2          |

    And after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate_3_1 | true      | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 3 PCE      | 0 PCE        | 3 PCE        | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    |
      | ppOrderCandidate_3_2 | true      | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 9 PCE      | 5 PCE        | 4 PCE        | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    |

    And after not more than 120s, PP_Orders are found
      | Identifier  | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyOrdered | C_BPartner_ID       | DatePromised         | DocStatus |
      | ppOrder_3_1 | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 5 PCE      | 5          | endcustomer_2_S0212 | 2022-11-07T21:00:00Z | CO        |
      | ppOrder_3_2 | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 2 PCE      | 2          | endcustomer_2_S0212 | 2022-11-07T21:00:00Z | CO        |
    And after not more than 120s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | ppOrderCandidate_3_1  | ppOrder_3_1 | 3 PCE      |
      | ppOrderCandidate_3_2  | ppOrder_3_1 | 2 PCE      |
      | ppOrderCandidate_3_2  | ppOrder_3_2 | 2 PCE      |








# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @from:cucumber
  @Id:S0212.400
  Scenario: Production candidates will be manufactured taking into account the SeqNo.
  _Given two PP_Product_Plannings for different products (P1&P2)
  _And PP_Product_Planning for P1 has SeqNo = 20
  _And PP_Product_Planning for P2 has SeqNo = 10
  _When PP_Order_Candidates are created as result of stock shortage for P1 and P2
  _Then PP_Order_Candidate for P1 has SeqNo = 20
  _And PP_Order_Candidate for P2 has SeqNo = 10
  _When both PP_Order_Candidates are enqueued for manufacturing (manufacturing order is given by PP_Order_Candidate.SeqNo)
  _Then PP_Order_Candidate (for P2) with SeqNo = 10 will be manufactured first
  _And PP_Order_Candidate (for P1) with SeqNo = 20  will be manufactured second

    Given update existing PP_Product_Plannings
      | Identifier   | MaxManufacturedQtyPerOrderDispo | SeqNo |
      | ppln_1_S0212 | 5 PCE                           | 10    |

    And metasfresh contains M_Products:
      | Identifier  | M_Product_Category_ID |
      | product_4   | standard_category     |
      | component_4 | standard_category     |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | productPrice_4_1 | plv_1_S0212            | product_4    | 5.0      | PCE      | Normal           |
      | productPrice_4_2 | plv_1_S0212            | component_4  | 7.0      | PCE      | Normal           |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_4      | product_4    | bomVersions_4             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_4     | bom_4             | component_4  | 10       |
    And the PP_Product_BOM identified by bom_4 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | S_Resource_ID      | PP_Product_BOMVersions_ID | IsCreatePlan | SeqNo | MaxManufacturedQtyPerOrderDispo |
      | ppln_4     | product_4    | testResource_S0212 | bomVersions_4             | false        | 20    | 5 PCE                           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID       | DateOrdered | PreparationDate      | M_Warehouse_ID      |
      | order_4_1  | true    | endcustomer_2_S0212 | 2022-11-09  | 2022-11-09T21:00:00Z | production_WH_S0212 |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_4_1 | order_4_1  | product_4    | 5          |

    When the order identified by order_4_1 is completed

    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | SeqNo | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate_4_1 | false     | 20    | product_4    | bom_4             | ppln_4                 | testResource_S0212 | 5 PCE      | 5 PCE        | 0 PCE        | 2022-11-09T21:00:00Z | 2022-11-09T21:00:00Z | false    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID       | DateOrdered | PreparationDate      | M_Warehouse_ID      |
      | order_4_2  | true    | endcustomer_2_S0212 | 2022-11-09  | 2022-11-09T21:00:00Z | production_WH_S0212 |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_4_2 | order_4_2  | p_1_S0212    | 5          |

    When the order identified by order_4_2 is completed

    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | SeqNo | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID      | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate_4_2 | false     | 10    | p_1_S0212    | bom_1_S0212       | ppln_1_S0212           | testResource_S0212 | 5 PCE      | 5 PCE        | 0 PCE        | 2022-11-09T21:00:00Z | 2022-11-09T21:00:00Z | false    |

    When generate PP_Order process is invoked for selection, with completeDocument=false and autoProcessCandidateAfterProduction=false
      | PP_Order_Candidate_ID |
      | ppOrderCandidate_4_1  |
      | ppOrderCandidate_4_2  |

    And validate that after not more than 120s, PP_Orders are created for PP_Order_Candidate in the following order:
      | PP_Order_Candidate_ID | PP_Order_ID |
      | ppOrderCandidate_4_2  | ppOrder_4_1 |
      | ppOrderCandidate_4_1  | ppOrder_4_2 |

