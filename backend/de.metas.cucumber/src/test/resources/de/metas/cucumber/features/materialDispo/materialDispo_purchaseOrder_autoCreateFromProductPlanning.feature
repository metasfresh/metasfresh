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
      | M_AttributeSet_ID              | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID | Name     | Value    |
      | standard_category     | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID | M_AttributeSet_ID              |
      | standard_category     | attributeSet_convenienceSalate |

  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_10
  Scenario: IsCreatePlan=Y and IsDocComplete=Y — completing a sales order auto-creates and auto-completes a purchase order
    Given metasfresh contains M_Products:
      | Identifier | Name                     | M_Product_Category_ID | IsSold | IsPurchased |
      | p_1        | product_autoCreatePO_S10 | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                               | Description                          | IsActive |
      | ps_1       | pricing_system_autoCreatePO_S10 | pricing_system_val_autoCreatePO_S10 | pricing_system_desc_autoCreatePO_S10 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name                          | Description | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_1       | ps_1               | DE                    | EUR                 | s_price_list_autoCreatePO_S10 | null        | true  | false         | 2              | true     |
      | pl_2       | ps_1               | DE                    | EUR                 | p_price_list_autoCreatePO_S10 | null        | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name                   | ValidFrom  |
      | plv_1      | pl_1           | s_PLV_autoCreatePO_S10 | 2021-04-01 |
      | plv_2      | pl_2           | p_PLV_autoCreatePO_S10 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                             | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S10 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_1      | ds_1                | p_1          | ps_1                  | 10    | Y                      | P         | 10         | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsDocComplete | IsPurchased |
      | ppln_1     | p_1          |                           | true         | true          | Y           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                         | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | EndCustomer_autoCreatePO_S10 | N        | Y          | ps_1               |                      |
      | endvendor_1   | EndVendor_autoCreatePO_S10   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID  | OPT.Name                     | IsShipToDefault | IsBillToDefault |
      | endvendor_location_1   | 2411250100000 | endvendor_1    | EndVendor_autoCreatePO_S10   | Y               | Y               |
      | endcustomer_location_1 | 2411250100001 | endcustomer_1  | EndCustomer_autoCreatePO_S10 | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | endvendor_1   | p_1          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 5          |
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
      | C_Order_ID | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1       | endvendor_1   | endvendor_location_1   | 2021-04-16  | POO         | EUR          | F            | P               | true      | CO        |

    And validate C_OrderLine:
      | C_OrderLine_ID | C_Order_ID | DateOrdered | M_Product_ID | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1          | po_1       | 2021-04-16  | p_1          | 5          | 0            | 0           | 10    | 0        | EUR          | true      |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -5  | -5                     |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-16T21:00:00Z | 5   | 0                      |

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
      | Identifier | Name                     | M_Product_Category_ID | IsSold | IsPurchased |
      | p_1        | product_autoCreatePO_S20 | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                               | Description                          | IsActive |
      | ps_1       | pricing_system_autoCreatePO_S20 | pricing_system_val_autoCreatePO_S20 | pricing_system_desc_autoCreatePO_S20 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name                          | Description | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_1       | ps_1               | DE                    | EUR                 | s_price_list_autoCreatePO_S20 | null        | true  | false         | 2              | true     |
      | pl_2       | ps_1               | DE                    | EUR                 | p_price_list_autoCreatePO_S20 | null        | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name                   | ValidFrom  |
      | plv_1      | pl_1           | s_PLV_autoCreatePO_S20 | 2021-04-01 |
      | plv_2      | pl_2           | p_PLV_autoCreatePO_S20 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                             | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S20 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_1      | ds_1                | p_1          | ps_1                  | 10    | Y                      | P         | 10         | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsDocComplete | IsPurchased |
      | ppln_1     | p_1          |                           | true         | false         | Y           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                         | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | EndCustomer_autoCreatePO_S20 | N        | Y          | ps_1               |                      |
      | endvendor_1   | EndVendor_autoCreatePO_S20   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID  | OPT.Name                     | IsShipToDefault | IsBillToDefault |
      | endvendor_location_1   | 2411250100002 | endvendor_1    | EndVendor_autoCreatePO_S20   | Y               | Y               |
      | endcustomer_location_1 | 2411250100003 | endcustomer_1  | EndCustomer_autoCreatePO_S20 | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | endvendor_1   | p_1          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 5          |
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
      | C_Order_ID | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | po_1       | endvendor_1   | endvendor_location_1   | 2021-04-16  | POO         | EUR          | F            | P               | false     | DR        |

    And validate C_OrderLine:
      | C_OrderLine_ID | C_Order_ID | DateOrdered | M_Product_ID | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | pol_1          | po_1       | 2021-04-16  | p_1          | 5          | 0            | 0           | 10    | 0        | EUR          | false     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -5  | -5                     |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-16T21:00:00Z | 5   | 0                      |

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
      | Identifier | Name                     | M_Product_Category_ID | IsSold | IsPurchased |
      | p_1        | product_autoCreatePO_S30 | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                               | Description                          | IsActive |
      | ps_1       | pricing_system_autoCreatePO_S30 | pricing_system_val_autoCreatePO_S30 | pricing_system_desc_autoCreatePO_S30 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name                          | Description | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_1       | ps_1               | DE                    | EUR                 | s_price_list_autoCreatePO_S30 | null        | true  | false         | 2              | true     |
      | pl_2       | ps_1               | DE                    | EUR                 | p_price_list_autoCreatePO_S30 | null        | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name                   | ValidFrom  |
      | plv_1      | pl_1           | s_PLV_autoCreatePO_S30 | 2021-04-01 |
      | plv_2      | pl_2           | p_PLV_autoCreatePO_S30 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                             | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S30 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_1      | ds_1                | p_1          | ps_1                  | 10    | Y                      | P         | 10         | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsPurchased |
      | ppln_1     | p_1          |                           | false        | Y           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                         | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | EndCustomer_autoCreatePO_S30 | N        | Y          | ps_1               |                      |
      | endvendor_1   | EndVendor_autoCreatePO_S30   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID  | OPT.Name                     | IsShipToDefault | IsBillToDefault |
      | endvendor_location_1   | 2411250100004 | endvendor_1    | EndVendor_autoCreatePO_S30   | Y               | Y               |
      | endcustomer_location_1 | 2411250100005 | endcustomer_1  | EndCustomer_autoCreatePO_S30 | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | endvendor_1   | p_1          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 5          |
    When the order identified by o_1 is completed

    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID |
      | pc_1                   |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -5  | -5                     |
      | c_2        | SUPPLY            | PURCHASE                  | p_1          | 2021-04-16T21:00:00Z | 5   | 0                      |

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | 5                               | 5                       | 0                       | 5                            | -5                            | 5                              | 0                              | 0                          | 0                                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 5              | 5               |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_25
  Scenario: IsCreatePlan=Y and IsDocComplete=N — re-completing a reactivated sales order must NOT auto-complete the purchase order
    # With product planning IsCreatePlan=Y and IsDocComplete=N, completing a sales order auto-creates a DRAFT
    # purchase order. Reactivating and re-completing the sales order must honor IsDocComplete=N as well: the
    # purchase order must stay a draft and never be auto-completed by the re-completion.
    Given metasfresh contains M_Products:
      | Identifier | Name                     | M_Product_Category_ID | IsSold | IsPurchased |
      | p_1        | product_autoCreatePO_S25 | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                               | IsActive |
      | ps_1       | pricing_system_autoCreatePO_S25 | pricing_system_val_autoCreatePO_S25 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name                          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | s_price_list_autoCreatePO_S25 | true  | false         | 2              |
      | pl_2       | ps_1               | DE                    | EUR                 | p_price_list_autoCreatePO_S25 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name                   | ValidFrom  |
      | plv_1      | pl_1           | s_PLV_autoCreatePO_S25 | 2021-04-01 |
      | plv_2      | pl_2           | p_PLV_autoCreatePO_S25 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                             | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S25 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_1      | ds_1                | p_1          | ps_1                  | 10    | Y                      | P         | 10         | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsDocComplete | IsPurchased |
      | ppln_1     | p_1          |                           | true         | false         | Y           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                         | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | EndCustomer_autoCreatePO_S25 | N        | Y          | ps_1               |                      |
      | endvendor_1   | EndVendor_autoCreatePO_S25   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID  | OPT.Name                     | IsShipToDefault | IsBillToDefault |
      | endvendor_location_1   | 2411250100010 | endvendor_1    | EndVendor_autoCreatePO_S25   | Y               | Y               |
      | endcustomer_location_1 | 2411250100011 | endcustomer_1  | EndCustomer_autoCreatePO_S25 | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | endvendor_1   | p_1          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 5          |
    When the order identified by o_1 is completed

    # First completion produces a draft purchase order (IsDocComplete=N honored by the auto-creation path).
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
      | C_Order_ID | C_BPartner_ID | DocBaseType | DocStatus |
      | po_1       | endvendor_1   | POO         | DR        |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Reactivate the sales order, change the quantity, and re-complete.
    When the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 7              |
    And the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # After re-completion the purchase order must not have been auto-completed:
    # its DocStatus for this vendor must remain DR (draft).
    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | DocBaseType | DocStatus |
      | po_1       | endvendor_1   | POO         | DR        |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_35
  Scenario: IsCreatePlan=N — re-completing a reactivated sales order must NOT auto-create a purchase order from the dispo candidate
    # Product planning creates purchase candidates but is set NOT to auto-create purchase orders (IsCreatePlan=N),
    # so completing a sales order leaves a candidate with no purchase order. Reactivating and re-completing the
    # sales order must behave the same: the material-disposition candidate (Source='MD', IsAggregatePO='N' by
    # vendor default) must stay a candidate only — no C_PurchaseCandidate_Alloc and no auto-created purchase order.
    Given metasfresh contains M_Products:
      | Identifier | Name                     | M_Product_Category_ID | IsSold | IsPurchased |
      | p_1        | product_autoCreatePO_S35 | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                               | IsActive |
      | ps_1       | pricing_system_autoCreatePO_S35 | pricing_system_val_autoCreatePO_S35 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name                          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | s_price_list_autoCreatePO_S35 | true  | false         | 2              |
      | pl_2       | ps_1               | DE                    | EUR                 | p_price_list_autoCreatePO_S35 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name                   | ValidFrom  |
      | plv_1      | pl_1           | s_PLV_autoCreatePO_S35 | 2021-04-01 |
      | plv_2      | pl_2           | p_PLV_autoCreatePO_S35 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                             | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S35 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_1      | ds_1                | p_1          | ps_1                  | 10    | Y                      | P         | 10         | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan | IsPurchased |
      | ppln_1     | p_1          |                           | false        | Y           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                         | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | EndCustomer_autoCreatePO_S35 | N        | Y          | ps_1               |                      |
      | endvendor_1   | EndVendor_autoCreatePO_S35   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID  | OPT.Name                     | IsShipToDefault | IsBillToDefault |
      | endvendor_location_1   | 2411250100014 | endvendor_1    | EndVendor_autoCreatePO_S35   | Y               | Y               |
      | endcustomer_location_1 | 2411250100015 | endcustomer_1  | EndCustomer_autoCreatePO_S35 | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | endvendor_1   | p_1          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 5          |
    When the order identified by o_1 is completed

    # First completion: a purchase candidate is created, but NO purchase order (IsCreatePlan=N).
    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID |
      | pc_1                   |

    # Resolve the purchase-order-generation async queue so we can wait for it to drain before asserting absence.
    And load C_Queue_PackageProcessor by classname:
      | Classname                                                                    | C_Queue_PackageProcessor_ID.Identifier |
      | de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders | gen_po_pp                              |
    And load C_Queue_Processor by C_Queue_PackageProcessor:
      | C_Queue_PackageProcessor_ID.Identifier | C_Queue_Processor_ID.Identifier |
      | gen_po_pp                              | gen_po_proc                     |

    # Reactivate and re-complete the sales order; the purchase candidate already exists at this point.
    When the order identified by o_1 is reactivated
    And the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Wait for any purchase-order-generation workpackage to finish, so the absence assertion below cannot pass
    # merely because the async work has not run yet.
    And after not more than 120s, there are no C_Queue_WorkPackage pending or running in queue gen_po_proc

    # The dispo candidate must still be a candidate only — no allocation, hence no auto-created purchase order.
    Then no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID |
      | pc_1                   |


  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  @Id:S0265_36
  Scenario: Generic product planning (no product, active for all products) with IsCreatePlan=N — re-completing a reactivated sales order must NOT auto-create a purchase order
    # The PP_Product_Planning is generic: it has NO M_Product and therefore applies to ALL products — a single
    # "all products" planning set to create candidates but not auto-create purchase orders (IsCreatePlan=N).
    # Planning resolution on sales-order (re-)completion must still find this no-product planning (M_Product_ID
    # IS NULL) and honor IsCreatePlan=N: the candidate stays a candidate only — no C_PurchaseCandidate_Alloc, no PO.
    Given metasfresh contains M_Products:
      | Identifier | Name                     | M_Product_Category_ID | IsSold | IsPurchased |
      | p_1        | product_autoCreatePO_S36 | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                               | IsActive |
      | ps_1       | pricing_system_autoCreatePO_S36 | pricing_system_val_autoCreatePO_S36 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name                          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | s_price_list_autoCreatePO_S36 | true  | false         | 2              |
      | pl_2       | ps_1               | DE                    | EUR                 | p_price_list_autoCreatePO_S36 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | Name                   | ValidFrom  |
      | plv_1      | pl_1           | s_PLV_autoCreatePO_S36 | 2021-04-01 |
      | plv_2      | pl_2           | p_PLV_autoCreatePO_S36 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                             | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_autoCreatePO_S36 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_1      | ds_1                | p_1          | ps_1                  | 10    | Y                      | P         | 10         | 0             |
    # Generic planning: no M_Product column — applies to all products.
    And metasfresh contains PP_Product_Plannings
      | Identifier | PP_Product_BOMVersions_ID | IsCreatePlan | IsPurchased |
      | ppln_1     |                           | false        | Y           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                         | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | endcustomer_1 | EndCustomer_autoCreatePO_S36 | N        | Y          | ps_1               |                      |
      | endvendor_1   | EndVendor_autoCreatePO_S36   | Y        | N          | ps_1               | ds_1                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID  | OPT.Name                     | IsShipToDefault | IsBillToDefault |
      | endvendor_location_1   | 2411250100016 | endvendor_1    | EndVendor_autoCreatePO_S36   | Y               | Y               |
      | endcustomer_location_1 | 2411250100017 | endcustomer_1  | EndCustomer_autoCreatePO_S36 | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | endvendor_1   | p_1          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 5          |
    When the order identified by o_1 is completed

    # First completion: a purchase candidate is created, but NO purchase order (IsCreatePlan=N).
    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID |
      | pc_1                   |

    # Resolve the purchase-order-generation async queue so we can wait for it to drain before asserting absence.
    And load C_Queue_PackageProcessor by classname:
      | Classname                                                                    | C_Queue_PackageProcessor_ID.Identifier |
      | de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders | gen_po_pp                              |
    And load C_Queue_Processor by C_Queue_PackageProcessor:
      | C_Queue_PackageProcessor_ID.Identifier | C_Queue_Processor_ID.Identifier |
      | gen_po_pp                              | gen_po_proc                     |

    # Reactivate and re-complete the sales order; the purchase candidate already exists at this point.
    When the order identified by o_1 is reactivated
    And the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 120s, there are no C_Queue_WorkPackage pending or running in queue gen_po_proc

    # The dispo candidate must still be a candidate only — no allocation, hence no auto-created purchase order.
    Then no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID |
      | pc_1                   |
