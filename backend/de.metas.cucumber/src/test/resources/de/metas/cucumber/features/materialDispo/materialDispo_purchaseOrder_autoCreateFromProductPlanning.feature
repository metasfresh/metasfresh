@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: PP_Product_Planning.IsCreatePlan / IsDocComplete automatically create/complete purchase orders from material disposition
## F5100: Material Disposition

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

  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_10
  Scenario: IsCreatePlan=Y and IsDocComplete=Y — completing a sales order auto-creates and auto-completes a purchase order
    Given metasfresh contains M_Products:
      | Identifier | Name                        | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | product_autoCreatePO_S10    | standard_category                    | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                               | Value                               | OPT.Description                           | OPT.IsActive |
      | ps_1       | pricing_system_autoCreatePO_S10    | pricing_system_val_autoCreatePO_S10 | pricing_system_desc_autoCreatePO_S10      | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | s_price_list_autoCreatePO_S10     | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | p_price_list_autoCreatePO_S10     | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                              | ValidFrom  |
      | plv_1      | pl_1                      | s_PLV_autoCreatePO_S10            | 2021-04-01 |
      | plv_2      | pl_2                      | p_PLV_autoCreatePO_S10            | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                        | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S10 | F           | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.IsDocComplete | OPT.IsPurchased |
      | ppln_1     | p_1                     |                                          | true         | true              | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | endcustomer_1 | EndCustomer_autoCreatePO_S10 | N            | Y              | ps_1                          |                                     |
      | endvendor_1   | EndVendor_autoCreatePO_S10   | Y            | N              | ps_1                          | ds_1                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID.Identifier | OPT.Name                     | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endvendor_location_1   | 2411250100000 | endvendor_1              | EndVendor_autoCreatePO_S10   | Y                   | Y                   |
      | endcustomer_location_1 | 2411250100001 | endcustomer_1            | EndCustomer_autoCreatePO_S10 | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_1                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 5          |
    When the order identified by o_1 is completed

    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |

    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pc_1                              | pca_1                                   |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pol_1                       | pca_1                                   |

    And load C_Order from C_OrderLine
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | po_1                  | pol_1                     |

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1                  | endvendor_1              | endvendor_location_1              | 2021-04-16  | POO         | EUR          | F            | P               | true      | CO        |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1                     | po_1                  | 2021-04-16      | p_1                     | 5          | 0            | 0           | 10    | 0        | EUR          | true      |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -5  | -5                     |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-16T21:00:00Z | 5   | 0                      |

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | 5                               | 5                       | 5                       | 5                            | 0                             | 0                              | 0                              | 0                          | 5                                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 5              | 5               |
      | cp_dd_2                                 | cp_1                     | pol_1                     | 5              | 5               |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_20
  Scenario: IsCreatePlan=Y and IsDocComplete=N — completing a sales order auto-creates a draft purchase order (not completed)
    Given metasfresh contains M_Products:
      | Identifier | Name                        | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | product_autoCreatePO_S20    | standard_category                    | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                               | Value                               | OPT.Description                           | OPT.IsActive |
      | ps_1       | pricing_system_autoCreatePO_S20    | pricing_system_val_autoCreatePO_S20 | pricing_system_desc_autoCreatePO_S20      | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | s_price_list_autoCreatePO_S20     | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | p_price_list_autoCreatePO_S20     | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                              | ValidFrom  |
      | plv_1      | pl_1                      | s_PLV_autoCreatePO_S20            | 2021-04-01 |
      | plv_2      | pl_2                      | p_PLV_autoCreatePO_S20            | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                        | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S20 | F           | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.IsDocComplete | OPT.IsPurchased |
      | ppln_1     | p_1                     |                                          | true         | false             | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | endcustomer_1 | EndCustomer_autoCreatePO_S20 | N            | Y              | ps_1                          |                                     |
      | endvendor_1   | EndVendor_autoCreatePO_S20   | Y            | N              | ps_1                          | ds_1                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID.Identifier | OPT.Name                     | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endvendor_location_1   | 2411250100002 | endvendor_1              | EndVendor_autoCreatePO_S20   | Y                   | Y                   |
      | endcustomer_location_1 | 2411250100003 | endcustomer_1            | EndCustomer_autoCreatePO_S20 | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_1                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 5          |
    When the order identified by o_1 is completed

    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |

    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pc_1                              | pca_1                                   |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pol_1                       | pca_1                                   |

    And load C_Order from C_OrderLine
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | po_1                  | pol_1                     |

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1                  | endvendor_1              | endvendor_location_1              | 2021-04-16  | POO         | EUR          | F            | P               | false     | DR        |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1                     | po_1                  | 2021-04-16      | p_1                     | 5          | 0            | 0           | 10    | 0        | EUR          | false     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -5  | -5                     |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-16T21:00:00Z | 5   | 0                      |

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | 5                               | 5                       | 0                       | 5                            | -5                            | 5                              | 0                              | 0                          | 0                                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 5              | 5               |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_30
  Scenario: IsCreatePlan=N — completing a sales order creates a purchase candidate but does NOT auto-create a purchase order
    Given metasfresh contains M_Products:
      | Identifier | Name                        | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | product_autoCreatePO_S30    | standard_category                    | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                               | Value                               | OPT.Description                           | OPT.IsActive |
      | ps_1       | pricing_system_autoCreatePO_S30    | pricing_system_val_autoCreatePO_S30 | pricing_system_desc_autoCreatePO_S30      | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | s_price_list_autoCreatePO_S30     | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | p_price_list_autoCreatePO_S30     | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                              | ValidFrom  |
      | plv_1      | pl_1                      | s_PLV_autoCreatePO_S30            | 2021-04-01 |
      | plv_2      | pl_2                      | p_PLV_autoCreatePO_S30            | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                        | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S30 | F           | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.IsPurchased |
      | ppln_1     | p_1                     |                                          | false        | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | endcustomer_1 | EndCustomer_autoCreatePO_S30 | N            | Y              | ps_1                          |                                     |
      | endvendor_1   | EndVendor_autoCreatePO_S30   | Y            | N              | ps_1                          | ds_1                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID.Identifier | OPT.Name                     | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endvendor_location_1   | 2411250100004 | endvendor_1              | EndVendor_autoCreatePO_S30   | Y                   | Y                   |
      | endcustomer_location_1 | 2411250100005 | endcustomer_1            | EndCustomer_autoCreatePO_S30 | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_1                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 5          |
    When the order identified by o_1 is completed

    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID.Identifier |
      | pc_1                              |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -5  | -5                     |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-16T21:00:00Z | 5   | 0                      |

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | 5                               | 5                       | 0                       | 5                            | -5                            | 5                              | 0                              | 0                          | 0                                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 5              | 5               |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_40
  Scenario: IsCreatePlan=Y, IsDocComplete=Y, 2 products for the SAME vendor — one aggregated purchase order with 2 lines
  # Verifies the debouncer processor (C_PurchaseCandidates_GeneratePurchaseOrdersForSalesOrder)
  # correctly aggregates multiple purchase candidates from the same sales order into a single PO per vendor.

    # Set debounce quiet period low for test speed (default would be 10s)
    Given set sys config int value 2000 for sys config de.metas.purchasecandidate.POCreation.QuietPeriodMillis
    And set sys config int value 30000 for sys config de.metas.purchasecandidate.POCreation.MaxWaitMillis

    And metasfresh contains M_Products:
      | Identifier | Name                         | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | product1_autoCreatePO_S40    | standard_category                    | Y          | Y               |
      | p_2        | product2_autoCreatePO_S40    | standard_category                    | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                                | Value                                | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_autoCreatePO_S40     | pricing_system_val_autoCreatePO_S40  | pricing_system_desc_autoCreatePO_S40  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | s_price_list_autoCreatePO_S40     | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | p_price_list_autoCreatePO_S40     | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | s_PLV_autoCreatePO_S40     | 2021-04-01 |
      | plv_2      | pl_2                      | p_PLV_autoCreatePO_S40     | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_1                             | p_2                     | 20.0     | PCE               | Normal                        |
      | pp_4       | plv_2                             | p_2                     | 20.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                               | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S40   | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
      | dsb_2      | ds_1                           | p_2                     | ps_1                             | 20    | Y                          | P             | 10             | 0                 |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.IsDocComplete | OPT.IsPurchased |
      | ppln_1     | p_1                     |                                          | true         | true              | Y               |
      | ppln_2     | p_2                     |                                          | true         | true              | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                          | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | endcustomer_1 | EndCustomer_autoCreatePO_S40  | N            | Y              | ps_1                          |                                     |
      | endvendor_1   | EndVendor_autoCreatePO_S40    | Y            | N              | ps_1                          | ds_1                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID.Identifier | OPT.Name                       | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endvendor_location_1   | 2411250100006 | endvendor_1              | EndVendor_autoCreatePO_S40     | Y                   | Y                   |
      | endcustomer_location_1 | 2411250100007 | endcustomer_1            | EndCustomer_autoCreatePO_S40   | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_1                     |
      | endvendor_1              | p_2                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 5          |
      | ol_2       | o_1                   | p_2                     | 3          |
    When the order identified by o_1 is completed

    # Both products should generate purchase candidates
    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |
      | pc_2       | o_1                     | ol_2                        | p_2                     |

    # Drain the material event queue so the debouncer handler runs before the alloc poll.
    # Without this, CI-runner contention occasionally exhausts the 120 s poll budget below.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # The debouncer waits for the quiet period, then creates POs — both candidates get an alloc
    And after not more than 120s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pc_1                              | pca_1                                   |
      | pc_2                              | pca_2                                   |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pol_1                       | pca_1                                   |
      | pol_2                       | pca_2                                   |

    # Load PO from the first line only — we assert the second line belongs to the same PO below
    And load C_Order from C_OrderLine
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | po_1                  | pol_1                     |

    # KEY ASSERTION: One aggregated purchase order for the single vendor
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1                  | endvendor_1              | endvendor_location_1              | 2021-04-16  | POO         | EUR          | F            | P               | true      | CO        |

    # KEY ASSERTION: Both PO lines reference the SAME purchase order (po_1)
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1                     | po_1                  | 2021-04-16      | p_1                     | 5          | 0            | 0           | 10    | 0        | EUR          | true      |
      | pol_2                     | po_1                  | 2021-04-16      | p_2                     | 3          | 0            | 0           | 20    | 0        | EUR          | true      |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_50
  Scenario: IsCreatePlan=Y, IsDocComplete=Y, 2 products for DIFFERENT vendors — two separate purchase orders, one per vendor
  # Verifies the debouncer processor correctly splits candidates by vendor,
  # creating one purchase order per vendor even though all candidates come from the same sales order.

    # Set debounce quiet period low for test speed (default would be 10s)
    Given set sys config int value 2000 for sys config de.metas.purchasecandidate.POCreation.QuietPeriodMillis
    And set sys config int value 30000 for sys config de.metas.purchasecandidate.POCreation.MaxWaitMillis

    And metasfresh contains M_Products:
      | Identifier | Name                         | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | product1_autoCreatePO_S50    | standard_category                    | Y          | Y               |
      | p_2        | product2_autoCreatePO_S50    | standard_category                    | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                                | Value                                | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_autoCreatePO_S50     | pricing_system_val_autoCreatePO_S50  | pricing_system_desc_autoCreatePO_S50  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | s_price_list_autoCreatePO_S50     | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | p_price_list_autoCreatePO_S50     | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | s_PLV_autoCreatePO_S50     | 2021-04-01 |
      | plv_2      | pl_2                      | p_PLV_autoCreatePO_S50     | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_1                             | p_2                     | 20.0     | PCE               | Normal                        |
      | pp_4       | plv_2                             | p_2                     | 20.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                                | DiscountType | ValidFrom  |
      | ds_1       | discount_schema1_autoCreatePO_S50   | F            | 2021-04-01 |
      | ds_2       | discount_schema2_autoCreatePO_S50   | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
      | dsb_2      | ds_2                           | p_2                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.IsDocComplete | OPT.IsPurchased |
      | ppln_1     | p_1                     |                                          | true         | true              | Y               |
      | ppln_2     | p_2                     |                                          | true         | true              | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | endcustomer_1 | EndCustomer_autoCreatePO_S50   | N            | Y              | ps_1                          |                                     |
      | endvendor_1   | EndVendor1_autoCreatePO_S50    | Y            | N              | ps_1                          | ds_1                                |
      | endvendor_2   | EndVendor2_autoCreatePO_S50    | Y            | N              | ps_1                          | ds_2                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID.Identifier | OPT.Name                        | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endvendor_location_1   | 2411250100008 | endvendor_1              | EndVendor1_autoCreatePO_S50     | Y                   | Y                   |
      | endvendor_location_2   | 2411250100009 | endvendor_2              | EndVendor2_autoCreatePO_S50     | Y                   | Y                   |
      | endcustomer_location_1 | 2411250100010 | endcustomer_1            | EndCustomer_autoCreatePO_S50    | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_1                     |
      | endvendor_2              | p_2                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 5          |
      | ol_2       | o_1                   | p_2                     | 3          |
    When the order identified by o_1 is completed

    # Both products should generate purchase candidates
    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |
      | pc_2       | o_1                     | ol_2                        | p_2                     |

    # Drain the material event queue so the debouncer handler runs before the alloc poll.
    # Without this, CI-runner contention occasionally exhausts the 120 s poll budget below.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # The debouncer waits for the quiet period, then creates POs — one per vendor
    And after not more than 120s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pc_1                              | pca_1                                   |
      | pc_2                              | pca_2                                   |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pol_1                       | pca_1                                   |
      | pol_2                       | pca_2                                   |

    # Load each PO separately — they should be different orders (one per vendor)
    And load C_Order from C_OrderLine
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | po_1                  | pol_1                     |
      | po_2                  | pol_2                     |

    # KEY ASSERTION: Two separate purchase orders, one per vendor
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1                  | endvendor_1              | endvendor_location_1              | 2021-04-16  | POO         | EUR          | F            | P               | true      | CO        |
      | po_2                  | endvendor_2              | endvendor_location_2              | 2021-04-16  | POO         | EUR          | F            | P               | true      | CO        |

    # KEY ASSERTION: Each PO line belongs to its vendor's purchase order
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1                     | po_1                  | 2021-04-16      | p_1                     | 5          | 0            | 0           | 10    | 0        | EUR          | true      |
      | pol_2                     | po_2                  | 2021-04-16      | p_2                     | 3          | 0            | 0           | 20    | 0        | EUR          | true      |
