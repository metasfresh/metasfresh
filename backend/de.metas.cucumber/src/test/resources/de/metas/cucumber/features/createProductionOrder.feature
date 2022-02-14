@from:cucumber
Feature: create production order
  As a user
  I want to create a Production order record

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | trackedProduct_1           |
      | p_2        | trackedProduct_component_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_1       | pricing_system_name_1 | pricing_system_value_1 | pricing_system_description_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV1 | 2021-04-01 |
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
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                        | false        |
    And metasfresh initially has no MD_Candidate data
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | EndcustomerPP_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    Then after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_1      | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_1                  | ppol_1     | p_2                     | 100          | false           | PCE               | CO            |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_2                        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -10                    |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      |
      | c_l_2                      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100 | 100                    |

  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, then the manufacturing order candidate is re-opened, and another manufacturing order is created from it
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_3        | trackedProduct_2           |
      | p_4        | trackedProduct_component_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_2       | pricing_system_name_2 | pricing_system_value_2 | pricing_system_description_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_2       | ps_2                          | DE                        | EUR                 | price_list_name_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_2      | pl_2                      | trackedProduct-PLV2 | 2021-06-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_2                             | p_3                     | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_2                             | p_4                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_2      | p_3                     | 2021-06-01 | bomVersions_2                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_2     | bom_2                        | p_4                     | 2021-06-01 | 10       |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_2     | p_3                     | bomVersions_2                        | false        |
    And metasfresh initially has no MD_Candidate data
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_3 | EndcustomerPP_2 | N            | Y              | ps_2                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_2        | true    | endcustomer_3            | 2021-06-17  | 2021-06-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_3                     | 10         |
    When the order identified by o_2 is completed
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_2        | DEMAND            | SHIPMENT                      | p_3                     | 2021-06-16T21:00:00Z | -10  | -10                    |
      | c_3        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_4                     | 2021-06-16T21:00:00Z | 100  | 0                      |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_3                     | bom_2                        | ppln_2                            | 540006        | 10         | 10           | 0            | PCE               | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_2                             | olc_2      | p_4                     | 100        | PCE               | CO            | boml_2                           |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_2                             |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_2      | true      | p_3                     | bom_2                        | ppln_2                            | 540006        | 10         | 0            | 10           | PCE               | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_3      | p_3                     | bom_2                        | ppln_2                            | 540006        | 10         | 10         | PCE               | endcustomer_3            | 2021-06-16T21:00:00Z |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_3                  | ppol_2     | p_4                     | 100          | false           | PCE               | CO            |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_2                            | ppo_3                  | 10         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_4        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 10   | 0                      |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | -100 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_3                        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-04-16T21:00:00Z | 0   | -10                    |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_4                     | 2021-04-16T21:00:00Z | 0   | 0                      |
      | c_l_2                      | SUPPLY            |                               | p_4                     | 2021-04-16T21:00:00Z | 100 | 100                    |
    And the following PP_Order_Candidates are re-opened
      | PP_Order_Candidate_ID.Identifier |
      | ocP_2                            |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule |
      | ocP_2                            | 22         |                   |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_4        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 10  | 12                     |
      | c_l_4      | SUPPLY            |                               | p_4                     | 2021-06-16T21:00:00Z | 120 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_3                        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 12  | 2                      |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | 120 | -120                   |
      | c_l_2                      | SUPPLY            |                               | p_4                     | 2021-06-16T21:00:00Z | 100 | -20                    |
      | c_l_3                      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | 100 | -120                   |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | ocP_2                            |
    Then after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_3      | true      | p_3                     | bom_2                        | ppln_2                            | 540006        | 22         | 0            | 22           | PCE               | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_3      | p_3                     | bom_2                        | ppln_2                            | 540006        | 12         | 12         | PCE               | endcustomer_3            | 2021-06-16T21:00:00Z |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_3                  | ppol_2     | p_4                     | 120          | false           | PCE               | CO            |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_3                            | ppo_3                  | 12         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_5        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 12   | 12                     |
      | c_l_5      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | -120 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_4                        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 10  | 0                      |
      | c_3                        | SUPPLY            | PRODUCTION                    | p_3                     | 2021-06-16T21:00:00Z | 0   | -10                    |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | 0   | 0                      |
      | c_l_2                      | SUPPLY            |                               | p_4                     | 2021-06-16T21:00:00Z | 100 | 100                    |
      | c_l_3                      | DEMAND            | PRODUCTION                    | p_4                     | 2021-06-16T21:00:00Z | 100 | 0                      |
      | c_l_4                      | SUPPLY            |                               | p_4                     | 2021-06-16T21:00:00Z | 120 | 120                    |


  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate and other md_candidates already exist
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_11       | trackedProduct_3           |
      | p_22       | trackedProduct_component_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_11      | pricing_system_name_3 | pricing_system_value_3 | pricing_system_description_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_11      | ps_11                         | DE                        | EUR                 | price_list_name_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_11     | pl_11                     | trackedProduct-PLV3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_11      | plv_11                            | p_11                    | 10.0     | PCE               | Normal                        |
      | pp_22      | plv_11                            | p_22                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_11     | p_11                    | 2021-04-01 | bomVersions_3                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_11    | bom_11                       | p_22                    | 2021-04-01 | 10       |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_11    | p_11                    | bomVersions_3                        | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                               | p_11                    | 2021-04-10T21:00:00Z | 10  | 10                     |
      | s_2        | INVENTORY_UP      |                               | p_11                    | 2021-04-14T21:00:00Z | 10  | 20                     |
    And metasfresh contains C_BPartners:
      | Identifier     | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_22 | EndcustomerPP_3 | N            | Y              | ps_11                         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_11       | true    | endcustomer_22           | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_11      | o_11                  | p_11                    | 20         |
    When the order identified by o_11 is completed
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_11       | DEMAND            | SHIPMENT                      | p_11                    | 2021-04-12T21:00:00Z | -20  | -10                    |
      | c_22       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-12T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_22                    | 2021-04-12T21:00:00Z | 100  | 0                      |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | false     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_11                            | olc_11     | p_22                    | 100        | PCE               | CO            | boml_11                          |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_11                            |
    Then after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_11     | true      | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 0            | 10           | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_11     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10         | PCE               | endcustomer_22           | 2021-04-12T21:00:00Z |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_11                 | ppol_11    | p_22                    | 100          | false           | PCE               | CO            |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_11                           | ppo_11                 | 10         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_33       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-12T21:00:00Z | -100 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_22                       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-12T21:00:00Z | 0   | -10                    |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-12T21:00:00Z | 0   | 0                      |
      | c_l_2                      | SUPPLY            |                               | p_22                    | 2021-04-12T21:00:00Z | 100 | 100                    |
    And the following stock MD_Candidates are validated
      | MD_Candidate_ID.Identifier | M_Product_ID.Identifier | DateProjected        | Qty |
      | s_2                        | p_11                    | 2021-04-14T21:00:00Z | 10  |


  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate and the date of the manufacturing order candidate is changed in the past
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_111      | trackedProduct_4           |
      | p_222      | trackedProduct_component_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_111     | pricing_system_name_4 | pricing_system_value_4 | pricing_system_description_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_111     | ps_111                        | DE                        | EUR                 | price_list_name_4 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_111    | pl_111                    | trackedProduct-PLV4 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_111     | plv_111                           | p_111                   | 10.0     | PCE               | Normal                        |
      | pp_222     | plv_111                           | p_222                   | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_111    | p_111                   | 2021-04-01 | bomVersions_4                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_111   | bom_111                      | p_222                   | 2021-04-01 | 10       |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_111   | p_111                   | bomVersions_4                        | false        |
    And metasfresh contains C_BPartners:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_222 | EndcustomerPP_4 | N            | Y              | ps_111                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_111      | true    | endcustomer_222          | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_111     | o_111                 | p_111                   | 10         |
    When the order identified by o_111 is completed
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_111      | DEMAND            | SHIPMENT                      | p_111                   | 2021-04-12T21:00:00Z | -10  | -10                    |
      | c_222      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-12T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_222                   | 2021-04-12T21:00:00Z | 100  | 0                      |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_111     | false     | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_111                           | olc_111    | p_222                   | 100        | PCE               | CO            | boml_111                         |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule    |
      | oc_111                           |            | 2021-04-11T21:00:00Z |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_222      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-11T21:00:00Z | 10  | 10                     |
      | c_l_3      | SUPPLY            |                               | p_222                   | 2021-04-11T21:00:00Z | 100 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_111                      | DEMAND            | SHIPMENT                      | p_111                   | 2021-04-12T21:00:00Z | 10  | 0                      |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-11T21:00:00Z | 100 | -100                   |
      | c_l_2                      | SUPPLY            |                               | p_222                   | 2021-04-12T21:00:00Z | 100 | 100                    |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_111                           |
    Then after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_111    | true      | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 0            | 10           | PCE               | 2021-04-11T21:00:00Z | 2021-04-11T21:00:00Z | false    |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_111    | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 10         | PCE               | endcustomer_222          | 2021-04-11T21:00:00Z |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_111                | ppol_111   | p_222                   | 100          | false           | PCE               | CO            |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_111                          | ppo_111                | 10         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_333      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-11T21:00:00Z | 10   | 10                     |
      | c_l_4      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-11T21:00:00Z | -100 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_222                      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-11T21:00:00Z | 0   | 0                      |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-11T21:00:00Z | 0   | 0                      |
      | c_l_2                      | SUPPLY            |                               | p_222                   | 2021-04-12T21:00:00Z | 100 | 100                    |
      | c_l_3                      | SUPPLY            |                               | p_222                   | 2021-04-11T21:00:00Z | 100 | 100                    |

  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, other md_candidates already exist and the date is changed in the future
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_11       | trackedProduct_5           |
      | p_22       | trackedProduct_component_5 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_11      | pricing_system_name_5 | pricing_system_value_5 | pricing_system_description_5 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_11      | ps_11                         | DE                        | EUR                 | price_list_name_5 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_11     | pl_11                     | trackedProduct-PLV5 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_11      | plv_11                            | p_11                    | 10.0     | PCE               | Normal                        |
      | pp_22      | plv_11                            | p_22                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_11     | p_11                    | 2021-04-01 | bomVersions_5                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_11    | bom_11                       | p_22                    | 2021-04-01 | 10       |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_11    | p_11                    | bomVersions_5                        | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                               | p_11                    | 2021-04-10T21:00:00Z | 10  | 10                     |
      | s_2        | INVENTORY_UP      |                               | p_11                    | 2021-04-14T21:00:00Z | 10  | 20                     |
    And metasfresh contains C_BPartners:
      | Identifier     | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_22 | EndcustomerPP_5 | N            | Y              | ps_11                         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_11       | true    | endcustomer_22           | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_11      | o_11                  | p_11                    | 20         |
    When the order identified by o_11 is completed
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_11       | DEMAND            | SHIPMENT                      | p_11                    | 2021-04-12T21:00:00Z | -20  | -10                    |
      | c_22       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-12T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_22                    | 2021-04-12T21:00:00Z | 100  | 0                      |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | false     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_11                            | olc_11     | p_22                    | 100        | PCE               | CO            | boml_11                          |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule    |
      | oc_11                            |            | 2021-04-15T21:00:00Z |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_22       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-15T21:00:00Z | 10  | 10                     |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_11                       | DEMAND            | SHIPMENT                      | p_11                    | 2021-04-12T21:00:00Z | 20  | -10                    |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-15T21:00:00Z | 100 | 0                      |
    And the following stock MD_Candidates are validated
      | MD_Candidate_ID.Identifier | M_Product_ID.Identifier | DateProjected        | Qty |
      | s_2                        | p_11                    | 2021-04-14T21:00:00Z | 0   |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_11                            |
    Then after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_11     | true      | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 0            | 10           | PCE               | 2021-04-15T21:00:00Z | 2021-04-15T21:00:00Z | false    |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_11     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10         | PCE               | endcustomer_22           | 2021-04-15T21:00:00Z |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_11                 | ppol_11    | p_22                    | 100          | false           | PCE               | CO            |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_11                           | ppo_11                 | 10         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_33       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-15T21:00:00Z | 10   | 10                     |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-15T21:00:00Z | -100 | 0                      |
    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_22                       | SUPPLY            | PRODUCTION                    | p_11                    | 2021-04-15T21:00:00Z | 0   | 0                      |
      | c_l_2                      | SUPPLY            |                               | p_22                    | 2021-04-12T21:00:00Z | 100 | 100                    |
      | c_l_1                      | DEMAND            | PRODUCTION                    | p_22                    | 2021-04-15T21:00:00Z | 0   | 100                    |