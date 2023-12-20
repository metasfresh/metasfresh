@from:cucumber
@ghActions:run_on_executor6
Feature: Disposal is correctly considered in Material Dispo. Stock shortage solved via production

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
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 0        |

  @Id:S0264_100
  @from:cucumber
  @Id:S0129.1_110
  Scenario: Partial stock available at demand time, supplied via production
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_31032022_0           | standard_category                    |
      | p_2        | trackedProduct_component_31032022_0 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_31032022_1 | pricing_system_value_31032022_1 | pricing_system_description_31032022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_31032022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_31032022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.BOMType |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | A           |
      | bom_spare  | p_1                     | 2021-04-05 | bomVersions_1                        | S           |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
      | boml_spare | bom_spare                    | p_2                     | 2021-04-05 | 20       |
    And the PP_Product_BOM identified by bom_1 is completed
    And the PP_Product_BOM identified by bom_spare is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_31032022_1 | N            | Y              | ps_1                          |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
      | il_2       | i_2                       | p_1                     | PCE          | 5        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
      | il_2                          | hu_2               |
    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_1               | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 5                      |                                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 5          | 5            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 50         | PCE               | CO            | boml_1                           |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 5                      |                                 |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10 | -5                     |                                 |
      | c_5        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 5   | 0                      |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -50 | -50                    |                                 |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 50  | 0                      |                                 |


  @Id:S0264_200
  @from:cucumber
  @Id:S0124_140
  Scenario: Disposal is correctly considered in Material Dispo when the product is Manufactured
  No stock available at demand time, supplied via production
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_31032022_1           | standard_category                    |
      | p_2        | trackedProduct_component_31032022_1 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_31032022_1 | pricing_system_value_31032022_1 | pricing_system_description_31032022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_31032022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_31032022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

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

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_31032022_1 | N            | Y              | ps_1                          |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
    And the inventory identified by i_1 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_1               | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 0                      |                                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10   | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10  | 0                      |                                 |
      | c_3        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    |                                 |
      | c_4        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |                                 |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      |                                 |


  @Id:S0264_300
  @from:cucumber
  @Id:S0124_140
  Scenario: Disposal is correctly considered in Material Dispo when the product is Manufactured
  Partial stock available at demand time, supplied via production
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_31032022_2           | standard_category                    |
      | p_2        | trackedProduct_component_31032022_2 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_31032022_2 | pricing_system_value_31032022_2 | pricing_system_description_31032022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_31032022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_31032022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

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

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_31032022_2 | N            | Y              | ps_1                          |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
      | il_2       | i_2                       | p_1                     | PCE          | 5        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
      | il_2                          | hu_2               |
    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_1               | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 5                      |                                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 5          | 5            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 50         | PCE               | CO            | boml_1                           |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 5                      |                                 |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10 | -5                     |                                 |
      | c_5        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 5   | 0                      |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -50 | -50                    |                                 |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 50  | 0                      |                                 |

  @Id:S0264_400
  @from:cucumber
  @Id:S0124_140
  Scenario: Disposal is correctly considered in Material Dispo when the product is Manufactured
  Stock available at demand time, no supply needed
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_01042022_3           | standard_category                    |
      | p_2        | trackedProduct_component_01042022_3 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_01042022_3 | pricing_system_value_01042022_3 | pricing_system_description_01042022_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_01042022_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_01042022_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

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

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_01042022_3 | N            | Y              | ps_1                          |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
    And the inventory identified by i_1 is completed

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |               | 10  | 10                     | 2021-04-16T00:00:00             |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10 | 0                      |                                 |

  @Id:S0264_500
  @Id:S0196_300
  @from:cucumber
  @flaky
  Scenario: Production candidate is created in the past
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_12092022_1           | standard_category                    |
      | p_2        | trackedProduct_component_12092022_1 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_12092022_1 | pricing_system_value_12092022_1 | pricing_system_description_12092022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_12092022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_12092022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

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

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_12092022_1 | N            | Y              | ps_1                          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-04  | 2021-04-03T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-11T07:00:00Z | 2021-04-11T07:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-03T21:00:00Z | -10  | -10                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-11T07:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-11T07:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-11T07:00:00Z | 100  | 0                      |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-03  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | -10                            | -10                        | 0                             | 0                             |
      | cp_2       | p_1                     | 2021-04-11  |                              | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 0                              | 0                          | 0                             | 0                             |
      | cp_3       | p_2                     | 2021-04-11  |                              | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_1            | 2021-04-11T07:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBOMLine_1               | ppo_1                  | p_2                     | 100          | false           | PCE               | CO            |

    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | oc_1                             | ppo_1                  | 10         | PCE               |

    And the manufacturing order identified by ppo_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-03  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | -10                            | -10                        | 0                             | 0                             |
      | cp_2       | p_1                     | 2021-04-11  |                              | 0                               | 0                       | 10                      | 0                            | 10                            | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_3       | p_2                     | 2021-04-11  |                              | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @Id:S0264_600
  @from:cucumber
  Scenario: Partial stock available at demand time, supplied via production Lot for Lot
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_27042023_0           | standard_category                    |
      | p_2        | trackedProduct_component_27042023_0 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_27042023_0 | pricing_system_value_27042023_0 | pricing_system_description_27042023_0 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_27042023_0 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_27042023_0 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | IsLotForLot |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | true        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_31032022_1 | N            | Y              | ps_1                          |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
      | il_2       | i_2                       | p_1                     | PCE          | 5        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
      | il_2                          | hu_2               |
    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_1               | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10 | 5                      |                                 |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  |                              | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 5                              | 5                          | 0                             | 0                             | warehouseStd                  | 5                            | 5                  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10   | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5    | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -10  | 5                      |                                 |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -5                     |                                 |
      | c_5        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 5                      |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |                                 |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      |                                 |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 5                              | 5                          | 0                             | 0                             | warehouseStd                  | 5                            | 5                  |


  @Id:S0264_700
  @from:cucumber
  Scenario: Full stock available at demand time, supplied via production Lot for Lot
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_27042023_1           | standard_category                    |
      | p_2        | trackedProduct_component_27042023_1 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_27042023_1 | pricing_system_value_27042023_1 | pricing_system_description_27042023_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_27042023_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_27042023_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | IsLotForLot |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | true        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | EndCustomer_31032022_1 | N            | Y              | ps_1                          |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | 540008         | 2021-04-16   |
      | i_2        | 540008         | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 10       | 0       |
      | il_2       | i_2                       | p_1                     | PCE          | 5        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |
      | il_2                          | hu_2               |
    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | hu_2               | 2021-04-16T21:00:00Z |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10  | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5   | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -5  | 10                     |                                 |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  |                              | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                             | 0                             | warehouseStd                  | 10                           | 10                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           |
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.DateProjected_LocalTimeZone |
      | c_1        | INVENTORY_UP      |                               | p_1                     |                      | 10   | 10                     | 2021-04-16T00:00:00             |
      | c_2        | INVENTORY_UP      |                               | p_1                     |                      | 5    | 15                     | 2021-04-16T00:00:00             |
      | c_3        | INVENTORY_DOWN    |                               | p_1                     | 2021-04-16T21:00:00Z | -5   | 10                     |                                 |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | 0                      |                                 |
      | c_5        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 10                     |                                 |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   |                                 |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      |                                 |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 10                             | 10                         | 0                             | 0                             | warehouseStd                  | 10                           | 10                 |

