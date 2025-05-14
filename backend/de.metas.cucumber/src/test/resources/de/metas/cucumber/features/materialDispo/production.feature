@from:cucumber
@ghActions:run_on_executor6
Feature: Physical Inventory and disposal - Production dispo scenarios

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
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @from:cucumber
  @Id:S0124_150
  Scenario: Disposal is correctly considered in Material Dispo when the product is a component in a BOM
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
      | pl_2       | ps_1               | DE                    | EUR                 | false | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
      | plv_2      | pl_2           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | plv_2                  | p_2          | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | 2021-04-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_2          | 2021-04-01 | 1        |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                       | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_31032022_6 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_2                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.IsPurchased |
      | ppln_2     | p_2                     |                                          | true         | Y               |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | N        | Y          | ps_1               |                      |
      | endvendor_1   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_2                     |
    # note - we expect 2021-04-16 to be converted to 2021-04-15 22:00:00 UTC, because of the time zone (summer - DST) and timezone that we set in the "metasfresh has date and time.."
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | i_1        | warehouseStd   | 2021-04-16   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1            | p_2          | PCE          | 10       | 0       |
    And the inventory identified by i_1 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | il_1               | hu_1    |
    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate |
      | hu_1               | 2021-04-16   |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                           | p_2          | 2021-04-16    | 10  | 10                     |
      | c_2        | INVENTORY_DOWN    |                           | p_2          | 2021-04-16    | -10 | 0                      |

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
      | oc_1                             | olc_1      | p_2                     | 10         | PCE               | CO            | boml_1                           |

    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_2                     |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID.Identifier | OPT.QtyToPurchase |
      | pc_1                              | 10                |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_3        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10 | -10                    |
      | c_4        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0                      |
      | c_5        | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -10 | -10                    |
      | c_6        | SUPPLY            | PURCHASE                  | p_2          | 2021-04-16T21:00:00Z | 10  | 0                      |

# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @from:cucumber
  Scenario: Two manufacturing candidates are created, because the component in the first BOM is manufactured
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
      | p_3        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | 2021-04-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_2          | 2021-04-01 | 1        |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |


    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_2      | p_2          | 2021-04-01 | bomVersions_2             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_2     | bom_2             | p_3          | 2021-04-01 | 1        |
    And the PP_Product_BOM identified by bom_2 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_2     | p_2          | bomVersions_2             | false        |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |

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
      | oc_2       | false     | p_2                     | bom_2                        | ppln_2                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 10         | PCE               | CO            | boml_1                           |
      | oc_2                             | olc_2      | p_3                     | 10         | PCE               | CO            | boml_2                           |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10 | -10                    |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0                      |
      | c_l_1_1    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -10 | -10                    |
      | c_l_1_2    | SUPPLY            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | 10  | 0                      |
      | c_l_2_1    | DEMAND            | PRODUCTION                | p_3          | 2021-04-16T21:00:00Z | -10 | -10                    |
#      | c_l_2_2    | SUPPLY            |                           | p_3          | 2021-04-16T21:00:00Z | 10  | 0                      |

# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @from:cucumber
  @Id:S0129.1_130
  Scenario: One manufacturing candidate is created for the main product, as the stock for component was enough to supply the created demand.
  Partial stock for main product, enough stock for component
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
      | p_3        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | 2021-04-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_2          | 2021-04-01 | 1        |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |


    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_2      | p_2          | 2021-04-01 | bomVersions_2             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_2     | bom_2             | p_3          | 2021-04-01 | 1        |
    And the PP_Product_BOM identified by bom_2 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_2     | p_2          | bomVersions_2             | false        |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |

    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_1          | 2021-04-10T21:00:00Z | 3   | 3                      |
      | s_2        | INVENTORY_UP      |                           | p_2          | 2021-04-10T21:00:00Z | 2   | 2                      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 5          |
    When the order identified by o_1 is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 2          | 2            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 2          | PCE               | CO            | boml_1                           |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_1          | 2021-04-10T21:00:00Z | 3   | 3                      |
      | s_2        | INVENTORY_UP      |                           | p_2          | 2021-04-10T21:00:00Z | 2   | 2                      |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -5  | -2                     |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 2   | 0                      |
      | c_l_1_1    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -2  | 0                      |










# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @ignore # supply for components changed, needs to be checked
  @Id:S0196_100
  @from:cucumber
  @Id:S0129.2_110
  Scenario: Close production candidate
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | 2021-04-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_2          | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |

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
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_1_1      | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10                    |
      | c_2_1      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0                      |
      | c_l_1_1    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | -100                   |
#      | c_l_1_2    | SUPPLY            |                           | p_2          | 2021-04-16T21:00:00Z | 100  | 0                      |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | M_Warehouse_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | warehouseStd              | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | warehouseStd              | 2021-04-16  |                              | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the following PP_Order_Candidates are closed
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 0          | 0            | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | true     |

    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 0          | PCE               | CO            | boml_1                           |

    And the following MD_Candidates are validated
      | MD_Candidate_ID.Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1_1                      | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | 10  | -10                    |
      | c_2_1                      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 0   | -10                    |
      | c_l_1_1                    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | 0   | 0                      |
#      | c_l_1_2                    | SUPPLY            |                           | p_2          | 2021-04-16T21:00:00Z | 100 | 100                    |










    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | -10                            | -10                        | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  |                              | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
# ########################################################################################################################################################################
  @ignore # supply for components changed, needs to be checked
  @Id:S0196_200
  @from:cucumber
  @Id:S0129.2_120

  Scenario: Production candidate is closed after it has been processed
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | huPackingTU           |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU                 | packingVersionTU              | 10  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | p_1          | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | p_2          | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_1          | bomVersions_1             | false        |

    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | huProductTU                            |
    When the order identified by o_1 is completed
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | M_HU_PI_Item_Product_ID |
      | oc_1       | false     | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | huProductTU             |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID.Identifier | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1                  | p_2                     | 100 PCE    | CO            | boml_1                |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10                    |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0                      |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | -100                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | M_Warehouse_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | warehouseStd              | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | warehouseStd              | 2021-04-16  |                              | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID |
      | oc_1                  |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | C_BPartner_ID | DatePromised         | M_HU_PI_Item_Product_ID |
      | ppo_1      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 10         | endcustomer_1 | 2021-04-16T21:00:00Z | huProductTU             |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID | PP_Order_ID | M_Product_ID | QtyRequiered | IsQtyPercentage | ComponentType |
      | ppOrderBOMLine_1    | ppo_1       | p_2          | 100 PCE      | false           | CO            |

    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID | PP_Order_ID | QtyEntered |
      | oc_1                  | ppo_1       | 10 PCE     |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | 4/c_3      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0                      |
      | 5/c_l_3    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | -100                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  |                              | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the following PP_Order_Candidates are closed
      | PP_Order_Candidate_ID |
      | oc_1                  |

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1          | bom_1             | ppln_1                 | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | true     |

    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID | M_Product_ID | QtyEntered | ComponentType | PP_Product_BOMLine_ID |
      | oc_1                  | p_2          | 0 PCE      | CO            | boml_1                |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | 1/c_1      | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | 10  | -10                    |
      | 2/c_2      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 0   | -10                    |
      | 3/c_l_1    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | 0   | 0                      |
      | 4/c_3      | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0                      |
      | 5/c_l_3    | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | 100 | -100                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  |                              | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

