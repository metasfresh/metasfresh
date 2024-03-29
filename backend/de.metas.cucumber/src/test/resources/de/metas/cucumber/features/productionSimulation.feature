@from:cucumber
@ghActions:run_on_executor7
Feature: create production simulation
  As a user
  I want to simulate the production of a Sales Order's line

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00

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
  Scenario:  The simulation for qty 100 is created, with duration to produce 1 qty set to 1 day, having a stock of 99 after demand date and before 'finished production' date
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_1      | pl_1           | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
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

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier | IsQtyPercentage |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               | true            |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
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
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 1        |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndcustomerPS_1 | N            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
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
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-14  | 2021-04-14T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 100        | olASI                                    |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | INVENTORY_UP      |                           | p_1          | 2021-04-16T00:00:00.00Z | 95  | 95                     | olASI                     |
      | INVENTORY_UP      |                           | p_1          | 2021-04-17T00:00:00.00Z | 4   | 99                     | olASI                     |
    And create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID | simulated |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-14T00:00:00Z | -100 | -100                   | olASI                     | true      |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-15T08:00:00Z | 1    | -99                    | productPlanningASI        | true      |
      | c_l_1      | DEMAND            | PRODUCTION                | p_2          | 2021-04-14T08:00:00Z | -1   | -1                     | bomLineASI                | true      |
      | c_l_2      | SUPPLY            |                           | p_2          | 2021-04-14T08:00:00Z | 1    | 0                      | bomLineASI                | true      |
      | c_3        | INVENTORY_UP      |                           | p_1          | 2021-04-16T00:00:00Z | 95   | 95                     | olASI                     | false     |
      | c_4        | INVENTORY_UP      |                           | p_1          | 2021-04-17T00:00:00Z | 4    | 99                     | olASI                     | false     |

    And post DeactivateAllSimulatedCandidatesEvent and wait for processing
    And delete all simulated candidates
    And validate there is no simulated md_candidate
    And validate there is no simulated PP_Order_Candidate


  @from:cucumber
  @Id:S0171.100
  Scenario: The simulation for qty 14 is created with duration to produce 1 qty set to 1 day,
  having both supplies and other demand in between demand date and initial 'production finished' date
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_1      | pl_1           | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
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

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier | IsQtyPercentage |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               | true            |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
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
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 1        |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
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
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | INVENTORY_UP      |                           | p_1          | 2021-04-14T00:00:00.00Z | 5   | 5                      | olASI                     |
      | INVENTORY_UP      |                           | p_1          | 2021-04-16T00:00:00.00Z | 5   | 10                     | olASI                     |
      | INVENTORY_DOWN    |                           | p_1          | 2021-04-16T00:00:00.00Z | -8  | 2                      | olASI                     |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_2        | true    | endcustomer_1            | 2021-04-15  | 2021-04-15T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_2       | o_2                   | p_1                     | 14         | olASI                                    |
    And create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_2                   | ol_2                      |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID | simulated |
      | c_5        | INVENTORY_UP      |                           | p_1          | 2021-04-14T00:00:00Z | 5   | 5                      | olASI                     | false     |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-15T00:00:00Z | -14 | -9                     | olASI                     | true      |
      | c_6        | INVENTORY_UP      |                           | p_1          | 2021-04-16T00:00:00Z | 5   | 10                     | olASI                     | false     |
      | c_3        | INVENTORY_DOWN    |                           | p_1          | 2021-04-16T00:00:00Z | -8  | 2                      | olASI                     | false     |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-23T08:00:00Z | 9   | 11                     | productPlanningASI        | true      |
      | c_l_3      | DEMAND            | PRODUCTION                | p_2          | 2021-04-14T08:00:00Z | -1  | -1                     | bomLineASI                | true      |
      | c_l_4      | SUPPLY            |                           | p_2          | 2021-04-14T08:00:00Z | 1   | 0                      | bomLineASI                | true      |

    And after not more than 60s, PP_Order_Candidate found for orderLine ol_2
      | Identifier |
      | ppoc_1     |
    And delete C_OrderLine identified by ol_2, but keep its id into identifierIds table
    And no PP_Order_Candidate found for orderLine ol_2


  @from:cucumber
  Scenario:  The simulation for qty 5 is created with duration to produce 1 qty set to 1 day, having some stock before demand date, but not enough
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID.Identifier |
      | p_1        | standard_category                |
      | p_2        | standard_category                |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_1      | pl_1           | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
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

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier | IsQtyPercentage |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               | true            |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
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
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 1        |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
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
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-24  | 2021-04-24T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 5          | olASI                                    |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected           | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | INVENTORY_UP      |                           | p_1          | 2021-04-18T00:00:00.00Z | 1   | 1                      | olASI                     |
    And create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID | simulated |
      | c_3        | INVENTORY_UP      |                           | p_1          | 2021-04-18T00:00:00Z | 1   | 1                      | olASI                     | false     |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-24T00:00:00Z | -5  | -4                     | olASI                     | true      |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-24T00:00:00Z | 4   | 0                      | productPlanningASI        | true      |
      | c_l_1      | DEMAND            | PRODUCTION                | p_2          | 2021-04-20T00:00:00Z | -1  | -1                     | bomLineASI                | true      |
      | c_l_2      | SUPPLY            |                           | p_2          | 2021-04-20T00:00:00Z | 1   | 0                      | bomLineASI                | true      |


  @from:cucumber
  Scenario: The simulation for qty 5 is created with duration to produce 1 qty set to 1 day, having enough stock before 'production finished' date
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID.Identifier |
      | p_1        | standard_category                |
      | p_2        | standard_category                |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_1      | pl_1           | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
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

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier | IsQtyPercentage |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               | true            |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
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
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 1        |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
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
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-15  | 2021-04-15T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 5          | olASI                                    |
    And metasfresh initially has this MD_Candidate data
      | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected           | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | INVENTORY_UP      |                               | p_1                     | 2021-04-16T00:00:00.00Z | 10  | 10                     | olASI                                    |
    And create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID | simulated |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-15T00:00:00Z | -5  | -5                     | olASI                     | true      |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-14T08:00:00Z | 0   | 0                      | olASI                     | true      |
      | c_3        | INVENTORY_UP      |                           | p_1          | 2021-04-16T00:00:00Z | 10  | 10                     | olASI                     | false     |
