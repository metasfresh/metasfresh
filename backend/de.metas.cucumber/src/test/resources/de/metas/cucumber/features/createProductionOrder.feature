@from:cucumber
@ghActions:run_on_executor4
Feature: create production order
  As a user
  I want to create a Production order record

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_AttributeSet:
      | Identifier                     | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | Identifier        | Name     | Value    |
      | standard_category | Standard | Standard |
    And update M_Product_Category:
      | Identifier        | M_AttributeSet_ID              |
      | standard_category | attributeSet_convenienceSalate |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |

    And metasfresh contains C_BPartners:
      | Identifier  | IsCustomer | M_PricingSystem_ID |
      | endcustomer | Y          | ps_1               |










# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate (S0129.1_100) (S0129.2_100)
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_1          | 10.0     | PCE      | Normal           |
      | plv_1                  | p_2          | 10.0     | PCE      | Normal           |

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
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | M_AttributeSetInstance_ID |
      | bom_1      | p_1          | bomVersions_1             | bomASI                    |

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
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | M_AttributeSetInstance_ID |
      | boml_1     | bom_1             | p_2          | 10       | bomLineASI                |
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
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | M_AttributeSetInstance_ID |
      | ppln_1     | p_1          | bomVersions_1             | false        | productPlanningASI        |
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
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer   | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_AttributeSetInstance_ID |
      | ol_1       | o_1        | p_1          | 10         | olASI                     |
    # Complete the order to set the production dispo in motion
    When the order identified by o_1 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_AttributeSetInstance_ID |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10  | olASI                     |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0    | productPlanningASI        |
      | 3          | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | -100 | bomLineASI                |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_1       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID | M_AttributeSetInstance_ID |
      | oc_1                  | p_2          | 100 PCE    | CO            | boml_1                | bomLineASI                |

    #
    # Now create the PP_Order from the candidates
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_1                  |
    # this is the PP_Order_Candidates from above, but now with processed=Y
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_1       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         | M_AttributeSetInstance_ID |
      | ppo_1      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10         | endcustomer   | 2021-04-16T21:00:00Z | bomASI                    |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID | M_Product_ID | QtyRequiered | ComponentType | M_AttributeSetInstance_ID |
      | ppo_1       | p_2          | 100 PCE      | CO            | bomLineASI                |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_1                  | ppo_1       | 10 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_AttributeSetInstance_ID |
      # Sales Order / Shipment Schedule:
      | 1          | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10  | olASI                     |
      # PP_Order_Candidate:
      | 2          | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 0    | -10  | productPlanningASI        |
      | 3          | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | 0    | 0    | bomLineASI                |
      # PP_Order:
      | 5          | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0    | bomASI                    |
      | 6          | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | -100 | bomLineASI                |









# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, then the manufacturing order candidate is re-opened, and another manufacturing order is created from it (S0129.2_130) (S0129.2_150) (S0129.2_170)
    # Basic setup
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_3        | standard_category     |
      | p_4        | standard_category     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_3          | 10.0     | PCE      | Normal           |
      | plv_1                  | p_4          | 10.0     | PCE      | Normal           |
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
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | M_AttributeSetInstance_ID |
      | bom_2      | p_3          | bomVersions_2             | bomASI                    |
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
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | M_AttributeSetInstance_ID |
      | boml_2     | bom_2             | p_4          | 10       | bomLineASI                |
    And the PP_Product_BOM identified by bom_2 is completed
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
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | M_AttributeSetInstance_ID |
      | ppln_2     | p_3          | bomVersions_2             | false        | productPlanningASI        |
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
    # Create the order to start it all
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_2        | true    | endcustomer   | 2021-06-17  | 2021-06-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_AttributeSetInstance_ID |
      | ol_2       | o_2        | p_3          | 10         | olASI                     |
    When the order identified by o_2 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_AttributeSetInstance_ID |
      | 1          | DEMAND            | SHIPMENT                  | p_3          | 2021-06-16T21:00:00Z | -10  | -10  | olASI                     |
      | 2          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10   | 0    | productPlanningASI        |
      | 3          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | -100 | -100 | bomLineASI                |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_2       | false     | p_3          | bom_2             | ppln_2                 | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID | M_AttributeSetInstance_ID |
      | oc_2                  | p_4          | 100 PCE    | CO            | boml_2                | bomLineASI                |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_2                  |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_2       | true      | p_3          | bom_2             | ppln_2                 | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         | M_AttributeSetInstance_ID |
      | ppo_3      | p_3          | bom_2             | ppln_2                 | 540006        | 10 PCE     | 10         | endcustomer   | 2021-06-16T21:00:00Z | bomASI                    |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType | M_AttributeSetInstance_ID |
      | ppOrderBOMLine_3    | ppo_3       | p_4          | 100 PCE      | false           | CO            | bomLineASI                |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_2                  | ppo_3       | 10 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_AttributeSetInstance_ID |
      | 1          | DEMAND            | SHIPMENT                  | p_3          | 2021-06-16T21:00:00Z | -10  | -10  | olASI                     |
      | 2          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 0    | -10  | bomASI                    |
      | 3          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 0    | 0    | bomLineASI                |
      | 5          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10   | 0    | bomASI                    |
      | 6          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | -100 | -100 | bomLineASI                |

    And the following PP_Order_Candidates are re-opened
      | PP_Order_Candidate_ID |
      | oc_2                  |
    # raise the quantity from 10 to 22, so +12 additional items are required
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID | QtyEntered |
      | oc_2                  | 22         |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_AttributeSetInstance_ID |
      | 1          | DEMAND            | SHIPMENT                  | p_3          | 2021-06-16T21:00:00Z | -10 | -10  | olASI                     |
      | 2          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 12  | 2    | bomASI                    |
      | 3          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 120 | -120 | bomLineASI                |
      | 5          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10  | 12   | bomASI                    |
      | 6          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 100 | -220 | bomLineASI                |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_2                  |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_2       | true      | p_3          | bom_2             | ppln_2                 | 540006        | 22 PCE     | 0 PCE        | 22 PCE       | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         | M_AttributeSetInstance_ID |
      | ppo_3_1    | p_3          | bom_2             | ppln_2                 | 540006        | 12 PCE     | 12         | endcustomer   | 2021-06-16T21:00:00Z | bomASI                    |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType | M_AttributeSetInstance_ID |
      | ppOrderBOMLine_3    | ppo_3_1     | p_4          | 120 PCE      | false           | CO            | bomLineASI                |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_2                  | ppo_3_1     | 12 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_AttributeSetInstance_ID |
      | 1          | DEMAND            | SHIPMENT                  | p_3          | 2021-06-16T21:00:00Z | -10 | -10  | olASI                     |
      | 2          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 0   | -10  | bomASI                    |
      | 3          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 0   | 0    | bomLineASI                |
      | 5          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10  | 0    | bomASI                    |
      | 6          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 100 | -100 | bomLineASI                |
      | 8          | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 12  | 12   | bomASI                    |
      | 9          | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 120 | -120 | bomLineASI                |







# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate and other md_candidates already exist
    Given metasfresh contains M_Products:
      | Identifier |
      | p_11       |
      | p_22       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_11         | 10.0     | PCE      | Normal           |
      | plv_1                  | p_22         | 10.0     | PCE      | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_11     | p_11         | bomVersions_3             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_11    | bom_11            | p_22         | 10       |
    And the PP_Product_BOM identified by bom_11 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_11    | p_11         | bomVersions_3             | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10  |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 20  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_11       | true    | endcustomer   | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_11      | o_11       | p_11         | 20         |
    When the order identified by o_11 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10   | 10   |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10   | 10   |
      | 3/c_11     | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | -20  | -10  |
      | 4/c_22     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 10   | 0    |
      | 5/c_l_1    | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | -100 | -100 |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | false     | p_11         | bom_11            | ppln_11                | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_11                 | p_22         | 100 PCE    | CO            | boml_11               |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_11                 |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | true      | p_11         | bom_11            | ppln_11                | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         |
      | ppo_11     | p_11         | bom_11            | ppln_11                | 540006        | 10 PCE     | 10         | endcustomer   | 2021-04-12T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType |
      | ppo_11      | p_22         | 100 PCE      | false           | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_11                 | ppo_11      | 10 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10   | 10   |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10   | 10   |
      | 3/c_11     | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | -20  | -10  |
      | 4/c_22     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 0    | -10  |
      | 5/c_l_1    | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | 0    | 0    |
      | 7/c_33     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 10   | 0    |
      | 8/c_l_3    | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | -100 | -100 |












# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate and the date of the manufacturing order candidate is changed in the past (S0129.2_200)
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_111      | standard_category     |
      | p_222      | standard_category     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_111        | 10.0     | PCE      | Normal           |
      | plv_1                  | p_222        | 10.0     | PCE      | Normal           |

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
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | M_AttributeSetInstance_ID |
      | bom_111    | p_111        | bomVersions_4             | bomASI                    |

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
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch | M_AttributeSetInstance_ID |
      | boml_111   | bom_111           | p_222        | 10       | bomLineASI                |
    And the PP_Product_BOM identified by bom_111 is completed

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
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | M_AttributeSetInstance_ID |
      | ppln_111   | p_111        | bomVersions_4             | false        | productPlanningASI        |
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
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_111      | true    | endcustomer   | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_AttributeSetInstance_ID |
      | ol_111     | o_111      | p_111        | 10         | olASI                     |
    When the order identified by o_111 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_AttributeSetInstance_ID |
      | 1/c_111    | DEMAND            | SHIPMENT                  | p_111        | 2021-04-12T21:00:00Z | -10  | -10  | olASI                     |
      | 2/c_222    | SUPPLY            | PRODUCTION                | p_111        | 2021-04-12T21:00:00Z | 10   | 0    | productPlanningASI        |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_222        | 2021-04-12T21:00:00Z | -100 | -100 | bomLineASI                |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_111     | false     | p_111        | bom_111           | ppln_111               | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID | M_AttributeSetInstance_ID |
      | oc_111                | p_222        | 100 PCE    | CO            | boml_111              | bomLineASI                |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID | QtyEntered | DateStartSchedule    |
      | oc_111                |            | 2021-04-11T21:00:00Z |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_AttributeSetInstance_ID |
      | 1/c_111    | DEMAND            | SHIPMENT                  | p_111        | 2021-04-12T21:00:00Z | 10  | 0    | bomASI                    |
      | 2/c_222    | SUPPLY            | PRODUCTION                | p_111        | 2021-04-11T21:00:00Z | 10  | 10   | bomASI                    |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_222        | 2021-04-11T21:00:00Z | 100 | -100 | bomLineASI                |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_111                |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_AttributeSetInstance_ID |
      | oc_111     | true      | p_111        | bom_111           | ppln_111               | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-11T21:00:00Z | 2021-04-11T21:00:00Z | false    | bomASI                    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         | M_AttributeSetInstance_ID |
      | ppo_111    | p_111        | bom_111           | ppln_111               | 540006        | 10 PCE     | 10         | endcustomer   | 2021-04-11T21:00:00Z | bomASI                    |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType |
      | ppo_111     | p_222        | 100 PCE      | false           | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_111                | ppo_111     | 10 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  | M_AttributeSetInstance_ID |
      | 1/c_111    | DEMAND            | SHIPMENT                  | p_111        | 2021-04-12T21:00:00Z | 10   | 0    | bomASI                    |
      | 2/c_222    | SUPPLY            | PRODUCTION                | p_111        | 2021-04-11T21:00:00Z | 0    | 0    | bomASI                    |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_222        | 2021-04-11T21:00:00Z | 0    | 0    | bomLineASI                |
      | 4/c_333    | SUPPLY            | PRODUCTION                | p_111        | 2021-04-11T21:00:00Z | 10   | 10   | bomASI                    |
      | 5/c_l_4    | DEMAND            | PRODUCTION                | p_222        | 2021-04-11T21:00:00Z | -100 | -100 | bomLineASI                |











# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, other md_candidates already exist and the date is changed in the future
    Given metasfresh contains M_Products:
      | Identifier |
      | p_11       |
      | p_22       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_11         | 10.0     | PCE      | Normal           |
      | plv_1                  | p_22         | 10.0     | PCE      | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_11     | p_11         | bomVersions_5             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_11    | bom_11            | p_22         | 10       |
    And the PP_Product_BOM identified by bom_11 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_11    | p_11         | bomVersions_5             | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10  |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 20  |
    # Create a sales order at a date in between the two MD_Candidates
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_11       | true    | endcustomer   | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_11      | o_11       | p_11         | 20         |
    When the order identified by o_11 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10   | 10   |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10   | 10   |
      | 3/c_11     | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | -20  | -10  |
      | 4/c_22     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 10   | 0    |
      | 5/c_l_1    | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | -100 | -100 |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | false     | p_11         | bom_11            | ppln_11                | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_11                 | p_22         | 100 PCE    | CO            | boml_11               |
    # leave QtyEntered untouched, but update DateStartSchedule, to move the candidate "behind" the second inventory-up
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID | DateStartSchedule    |
      | oc_11                 | 2021-04-15T21:00:00Z |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10   |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 0    |
      | 3/c_11     | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | 20  | -10  |
      | 4/c_22     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-15T21:00:00Z | 10  | 10   |
      | 5/c_l_1    | DEMAND            | PRODUCTION                | p_22         | 2021-04-15T21:00:00Z | 100 | -100 |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_11                 |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | true      | p_11         | bom_11            | ppln_11                | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-15T21:00:00Z | 2021-04-15T21:00:00Z | false    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         |
      | ppo_11     | p_11         | bom_11            | ppln_11                | 540006        | 10 PCE     | 10         | endcustomer   | 2021-04-15T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType |
      | ppo_11      | p_22         | 100 PCE      | false           | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_11                 | ppo_11      | 10 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  |
      | 1/s_1      | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10   |
      | 2/s_2      | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 0    |
      | 3/c_11     | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | 20  | -10  |
      | 4/c_22     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-15T21:00:00Z | 0   | 0    |
      | 5/c_l_1    | DEMAND            | PRODUCTION                | p_22         | 2021-04-15T21:00:00Z | 0   | 0    |
      | 6/c_33     | SUPPLY            | PRODUCTION                | p_11         | 2021-04-15T21:00:00Z | 10  | 10   |
      | 7/c_l_3    | DEMAND            | PRODUCTION                | p_22         | 2021-04-15T21:00:00Z | 100 | -100 |













# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: BOM bom_1 is created with two components. Manufacturing order candidate is generated,
  then another BOM (newer than the previous one in terms of validFrom) is created from the API,
  Manufacturing order candidate and Material schedules are updated accordingly,
  then the manufacturing order is created from the manufacturing order candidate.
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_comp_1   |
      | p_comp_2   |
      | p_comp_3   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | 2021-04-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_comp_1     | 10       |
      | boml_2     | bom_1             | p_comp_2     | 15       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer   | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP  |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10  |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0    |
      | 3/c_l_1_d  | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | -100 | -100 |
      | 4/c_l_2_d  | DEMAND            | PRODUCTION                | p_comp_2     | 2021-04-16T21:00:00Z | -150 | -150 |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | Identifier | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1                  | olc_1      | p_comp_1     | 100 PCE    | CO            | boml_1                |
      | oc_1                  | olc_2      | p_comp_2     | 150 PCE    | CO            | boml_2                |


    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference        | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | finishedGoodExternalRef6 | Product                    | p_1                 |
      | GRSSignum           | component1ExternalRef6   | Product                    | p_comp_1            |
      | GRSSignum           | component3ExternalRef6   | Product                    | p_comp_3            |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bom/version/001' and fulfills with '200' status code
    """
 {
    "uomCode": "PCE",
    "productIdentifier": "ext-GRSSignum-finishedGoodExternalRef6",
    "name": "trackedProduct_6",
    "isActive": true,
    "validFrom":"2021-04-10T12:00:00.000000Z",
    "bomLines": [
        {
            "productIdentifier": "ext-GRSSignum-component1ExternalRef6",
            "qtyBom": {
                "qty": 20.0,
                "uomCode": "PCE"
            },
            "line": 1
        },
        {
            "productIdentifier": "ext-GRSSignum-component3ExternalRef6",
            "qtyBom": {
                "qty": 30.0,
                "uomCode": "PCE"
            },
            "line": 2
        }
    ]
}
    """
    And process metasfresh response:
      | PP_Product_BOM_ID | PP_Product_BOMVersions_ID |
      | bom_2             | bomVersions_1             |
    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID | M_Product_ID |
      | bomVersions_1             | p_1          |
    And verify that bom was created for product
      | PP_Product_BOM_ID | M_Product_ID | PP_Product_BOMVersions_ID | UomCode | ValidFrom                   |
      | bom_2             | p_1          | bomVersions_1             | PCE     | 2021-04-10T12:00:00.000000Z |
    And verify that bomLine was created for bom
      | PP_Product_BOMLine_ID | PP_Product_BOM_ID | M_Product_ID | Qty      | IsQtyPercentage | Line |
      | boml_3                | bom_2             | p_comp_1     | 20.0 PCE | false           | 1    |
      | boml_4                | bom_2             | p_comp_3     | 30.0 PCE | false           | 2    |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1          | bom_2             | ppln_1                 | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | Identifier | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1                  | olc_1      | p_comp_1     | 200 PCE    | CO            | boml_3                |
      | oc_1                  | olc_2      | p_comp_2     | 0 PCE      | CO            | boml_2                |
      | oc_1                  | olc_3      | p_comp_3     | 300 PCE    | CO            | boml_4                |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | 10  | -10  |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0    |
      | 3/c_l_1_d  | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | 200 | -200 |
      | 4/c_l_2_d  | DEMAND            | PRODUCTION                | p_comp_2     | 2021-04-16T21:00:00Z | 0   | 0    |
      | 5/c_l_3_d  | DEMAND            | PRODUCTION                | p_comp_3     | 2021-04-16T21:00:00Z | 300 | -300 |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_1                  |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1          | bom_2             | ppln_1                 | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         |
      | ppo_1      | p_1          | bom_2             | ppln_1                 | 540006        | 10 PCE     | 10         | endcustomer   | 2021-04-16T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID | PP_Order_BOMLine_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType |
      | ppo_1       | ppol_1              | p_comp_1     | 200 PCE      | false           | CO            |
      | ppo_1       | ppol_2              | p_comp_3     | 300 PCE      | false           | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_1                  | ppo_1       | 10 PCE     |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier    | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  |
      | 1/c_1         | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | 10  | -10  |
      | 2/c_2         | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 0   | -10  |
      | 3/c_l_1_d     | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | 0   | 0    |
      | 4/c_l_2_d     | DEMAND            | PRODUCTION                | p_comp_2     | 2021-04-16T21:00:00Z | 0   | 0    |
      | 5/c_l_3_d     | DEMAND            | PRODUCTION                | p_comp_3     | 2021-04-16T21:00:00Z | 0   | 0    |
      | 6/c_3         | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0    |
      | 7/c_ppo_l_1_d | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | 200 | -200 |
      | 8/c_ppo_l_2_d | DEMAND            | PRODUCTION                | p_comp_3     | 2021-04-16T21:00:00Z | 300 | -300 |
