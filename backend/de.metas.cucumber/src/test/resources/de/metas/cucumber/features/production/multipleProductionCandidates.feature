@from:cucumber
Feature: create multiple production candidates
  As a user
  I want to create multiple production candidates for the same Sales Order

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
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

  @from:cucumber
  Scenario:  The manufacturing candidate is created for a sales order line,
  then the sales order is re-opened and the ordered quantity is increased,
  resulting in a second manufacturing candidate to supply the additional demand

    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_29032022_2           | standard_category                    |
      | p_2        | trackedProduct_component_29032022_2 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_29032022_2 | pricing_system_value_29032022_2 | pricing_system_description_29032022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_29032022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV29032022_2 | 2021-04-01 |
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
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                        | false        |
    And metasfresh initially has no MD_Candidate data
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | EndcustomerPP_2 | N            | Y              | ps_1                          |
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
    And the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 12             |
    And the order identified by o_1 is completed
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_2       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 2          | 2            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_2                             | olc_2      | p_2                     | 20         | PCE               | CO            | boml_1                           |
    And after not more than 30s, MD_Candidates are found
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