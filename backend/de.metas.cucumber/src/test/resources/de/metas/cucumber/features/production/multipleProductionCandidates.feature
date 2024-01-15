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
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

  @flaky # https://github.com/metasfresh/metasfresh/actions/runs/7504398064/job/20433480647
  @Id:S0129.1_140
  @Id:S0212.100
  @from:cucumber
  Scenario:  The manufacturing candidate is created for a sales order line,
  then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second manufacturing candidate to supply the additional demand.
  Also validate that PP_Order_Candidate is marked as 'processed' after PP_Order is created.

    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_10042023_1           | standard_category                    |
      | p_2        | trackedProduct_component_10042023_1 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_10042023_1 | pricing_system_value_10042023_1 | pricing_system_description_10042023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_10042023_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_10042023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | Endcustomer_10042023_1 | N            | Y              | ps_1                          |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    And update S_Resource:
      | S_Resource_ID.Identifier | OPT.CapacityPerProductionCycle | OPT.CapacityPerProductionCycleUOMCode |
      | testResource             | 2                              | KGM                                   |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | KGM                  | 0.1          |
    When the order identified by o_1 is completed
    And after not more than 120s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      |
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 120s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           |
    And the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 12             |
    And the order identified by o_1 is completed
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 2          | 2            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 120s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_2                             | olc_2      | p_2                     | 20         | PCE               | CO            | boml_1                           |
    And after not more than 120s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 2   | 0                      |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -20 | -20                    |
      | c_l_4      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 20  | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1                        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | 12  | -12                    |
      | c_2                        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10  | -2                     |
      | c_3                        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 2   | 0                      |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 100 | -100                   |
      | c_l_2                      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100 | 0                      |
      | c_l_3                      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 20  | -20                    |
      | c_l_4                      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 20  | 0                      |

    When generate PP_Order process is invoked for selection, with completeDocument=false and autoProcessCandidateAfterProduction=false
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
      | oc_2                             |

    Then after not more than 120s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.DocStatus |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 12         | 12         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z | DR            |

    And after not more than 120s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
      | oc_2       | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 2          | 0            | 2            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 120s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | oc_1                             | ppo_1                  | 10         | PCE               |
      | oc_2                             | ppo_1                  | 2          | PCE               |


  @from:cucumber
  @Id:S0212.200
  Scenario:  The manufacturing candidate is created for a sales order line and `Generate PP_Order` process is invoked resulting multiple manufacturing orders
  and the candidate remains open as it still has unprocessed quantity and `autoProcessCandidates` parameter is not set.

    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_10042023_2           | standard_category                    |
      | p_2        | trackedProduct_component_10042023_2 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_10042023_2 | pricing_system_value_10042023_2 | pricing_system_description_10042023_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_10042023_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_10042023_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | Endcustomer_10042023_2 | N            | Y              | ps_1                          |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_2        | true    | endcustomer_2            | 2022-10-10  | 2022-10-10T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_1                     | 12         |
    And update S_Resource:
      | S_Resource_ID.Identifier | OPT.CapacityPerProductionCycle | OPT.CapacityPerProductionCycleUOMCode |
      | testResource             | 5                              | PCE                                   |

    When the order identified by o_2 is completed

    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier       | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 12         | 12           | 0            | PCE               | 2022-10-10T21:00:00Z | 2022-10-10T21:00:00Z | false    |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | OPT.QtyToProcess |
      | ppOrderCandidate                 | 11               |
    When generate PP_Order process is invoked for selection, with completeDocument=true and autoProcessCandidateAfterProduction=false
      | PP_Order_Candidate_ID.Identifier |
      | ppOrderCandidate                 |

    Then after not more than 120s, load PP_Order by candidate id: ppOrderCandidate
      | PP_Order_ID.Identifier | QtyEntered |
      | ppOrder_1              | 5          |
      | ppOrder_2              | 5          |
      | ppOrder_3              | 1          |

    And after not more than 120s, PP_Order_Candidates are found
      | Identifier       | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 12         | 1            | 11           | PCE               | 2022-10-10T21:00:00Z | 2022-10-10T21:00:00Z | false    |

    And after not more than 120s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.DocStatus |
      | ppOrder_1  | p_1                     | bom_1                        | ppln_1                            | 540006        | 5          | 5          | PCE               | endcustomer_2            | 2022-10-10T21:00:00Z | CO            |
      | ppOrder_2  | p_1                     | bom_1                        | ppln_1                            | 540006        | 5          | 5          | PCE               | endcustomer_2            | 2022-10-10T21:00:00Z | CO            |
      | ppOrder_3  | p_1                     | bom_1                        | ppln_1                            | 540006        | 1          | 1          | PCE               | endcustomer_2            | 2022-10-10T21:00:00Z | CO            |
    And after not more than 120s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ppOrderCandidate                 | ppOrder_1              | 5          | PCE               |
      | ppOrderCandidate                 | ppOrder_2              | 5          | PCE               |
      | ppOrderCandidate                 | ppOrder_3              | 1          | PCE               |


  # TODO remove this ignore when https://github.com/metasfresh/mf15/issues/2228 is done
  @ignore
  @from:cucumber
  @Id:S0212.300
  Scenario: The manufacturing candidate is created for a sales order line and then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second manufacturing candidate to supply the additional demand
  and openQty for the second candidate is decreased
  then `Generate PP_Order`process is invoked enforcing the candidates to be processed
  and both candidates are marked as processed

    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_10042023_3           | standard_category                    |
      | p_2        | trackedProduct_component_10042023_3 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_10042023_3 | pricing_system_value_10042023_3 | pricing_system_description_10042023_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_10042023_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_10042023_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | Endcustomer_10042023_3 | N            | Y              | ps_1                          |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.MaxManufacturedQtyPerOrderDispo | OPT.MaxManufacturedQtyPerOrderDispoUOMCode | OPT.SeqNo |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | 10                                  | PCE                                        | 10        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_3        | true    | endcustomer_2            | 2022-11-07  | 2022-11-07T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_3       | o_3                   | p_1                     | 3          |
    And update S_Resource:
      | S_Resource_ID.Identifier | OPT.CapacityPerProductionCycle | OPT.CapacityPerProductionCycleUOMCode |
      | testResource             | 5                              | PCE                                   |

    When the order identified by o_3 is completed

    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.SeqNo |
      | ppOrderCandidate_3_1 | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 3          | 3            | 0            | PCE               | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    | 10        |

    And the order identified by o_3 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_3                      | 12             |
    And the order identified by o_3 is completed
    And after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.SeqNo |
      | ppOrderCandidate_3_1 | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 3          | 3            | 0            | PCE               | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    | 10        |
      | ppOrderCandidate_3_2 | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 9          | 9            | 0            | PCE               | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    | 10        |

    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | OPT.QtyToProcess |
      | ppOrderCandidate_3_2             | 4                |

    When generate PP_Order process is invoked for selection, with completeDocument=true and autoProcessCandidateAfterProduction=true
      | PP_Order_Candidate_ID.Identifier |
      | ppOrderCandidate_3_1             |
      | ppOrderCandidate_3_2             |

    # we are expecting two PP_Orders for ppOrderCandidate_3_2, because
    # CapacityPerProductionCycle=5, and the two candidates sum up to a quantity of 3+4=7
    # so all (3) of ppOrderCandidate_3_1 end up in the first PP_Order, i.e. ppOrder_3_1.
    # Then of ppOrderCandidate_3_2's 4PCE, 2 end up on the same PP_Order ppOrder_3_1 which then is (full) with 5 items,
    # Therefore the remaining 2PCE of ppOrderCandidate_3_2 end up in a new PP_Order, i.e. ppOrder_3_2.
    Then after not more than 120s, load PP_Order by candidate id: ppOrderCandidate_3_2
      | PP_Order_ID.Identifier | QtyEntered |
      | ppOrder_3_1            | 2          |
      | ppOrder_3_2            | 2          |

    And after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate_3_1 | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 3          | 0            | 3            | PCE               | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    |
      | ppOrderCandidate_3_2 | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 9          | 5            | 4            | PCE               | 2022-11-07T21:00:00Z | 2022-11-07T21:00:00Z | false    |

    And after not more than 120s, PP_Orders are found
      | Identifier  | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.DocStatus |
      | ppOrder_3_1 | p_1                     | bom_1                        | ppln_1                            | 540006        | 5          | 5          | PCE               | endcustomer_2            | 2022-11-07T21:00:00Z | CO            |
      | ppOrder_3_2 | p_1                     | bom_1                        | ppln_1                            | 540006        | 2          | 2          | PCE               | endcustomer_2            | 2022-11-07T21:00:00Z | CO            |
    And after not more than 120s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ppOrderCandidate_3_1             | ppOrder_3_1            | 3          | PCE               |
      | ppOrderCandidate_3_2             | ppOrder_3_1            | 2          | PCE               |
      | ppOrderCandidate_3_2             | ppOrder_3_2            | 2          | PCE               |


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

    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_10042023_4           | standard_category                    |
      | p_2        | trackedProduct_component_10042023_4 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_10042023_4 | pricing_system_value_10042023_4 | pricing_system_description_10042023_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_10042023_4 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_10042023_4 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | Endcustomer_10042023_4 | N            | Y              | ps_1                          |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.MaxManufacturedQtyPerOrderDispo | OPT.MaxManufacturedQtyPerOrderDispoUOMCode | OPT.SeqNo |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | 5                                   | PCE                                        | 10        |

    And metasfresh contains M_Products:
      | Identifier  | Name                 | OPT.M_Product_Category_ID.Identifier |
      | product_4   | product_09112022_1   | standard_category                    |
      | component_4 | component_09112022_1 | standard_category                    |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_4_1 | plv_1                             | product_4               | 5.0      | PCE               | Normal                        |
      | productPrice_4_2 | plv_1                             | component_4             | 7.0      | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_4      | product_4               | 2022-11-01 | bomVersions_4                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_4     | bom_4                        | component_4             | 2022-11-09 | 10       |
    And the PP_Product_BOM identified by bom_4 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.SeqNo | OPT.MaxManufacturedQtyPerOrderDispo | OPT.MaxManufacturedQtyPerOrderDispoUOMCode |
      | ppln_4     | product_4               | bomVersions_4                            | false        | 20        | 5                                   | PCE                                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | order_4_1  | true    | endcustomer_2            | 2022-11-09  | 2022-11-09T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_4_1 | order_4_1             | product_4               | 5          |

    When the order identified by order_4_1 is completed

    Then after not more than 120s, PP_Order_Candidates are found
      | Identifier           | Processed | OPT.SeqNo | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate_4_1 | false     | 20        | product_4               | bom_4                        | ppln_4                            | 540006        | 5          | 5            | 0            | PCE               | 2022-11-09T21:00:00Z | 2022-11-09T21:00:00Z | false    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | order_4_2  | true    | endcustomer_2            | 2022-11-09  | 2022-11-09T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_4_2 | order_4_2             | p_1                     | 5          |

    When the order identified by order_4_2 is completed

    Then after not more than 90s, PP_Order_Candidates are found
      | Identifier           | Processed | OPT.SeqNo | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ppOrderCandidate_4_2 | false     | 10        | p_1                     | bom_1                        | ppln_1                            | 540006        | 5          | 5            | 0            | PCE               | 2022-11-09T21:00:00Z | 2022-11-09T21:00:00Z | false    |

    When generate PP_Order process is invoked for selection, with completeDocument=false and autoProcessCandidateAfterProduction=false
      | PP_Order_Candidate_ID.Identifier |
      | ppOrderCandidate_4_1             |
      | ppOrderCandidate_4_2             |

    And validate that after not more than 120s, PP_Orders are created for PP_Order_Candidate in the following order:
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier |
      | ppOrderCandidate_4_2             | ppOrder_4_1            |
      | ppOrderCandidate_4_1             | ppOrder_4_2            |

