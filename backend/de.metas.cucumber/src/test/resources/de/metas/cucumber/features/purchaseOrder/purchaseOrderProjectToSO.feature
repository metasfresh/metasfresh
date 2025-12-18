@from:cucumber
@ghActions:run_on_executor6
Feature: Purchase order project is automatically created when PO is completed

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
  Scenario:
    Given metasfresh contains M_Products:
      | Identifier | OPT.IsSold | OPT.IsPurchased |
      | p_1        | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1                          | DE                        | EUR                 | true  | false         | 2              |
      | pl_2       | ps_1                          | DE                        | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_1      | pl_1                      | 2021-04-01 |
      | plv_2      | pl_2                      | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name              | DiscountType | ValidFrom  |
      | ds_1       | discount_schema_1 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.IsPurchased |
      | ppln_1     | p_1                     | true         | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | endcustomer_1 | N            | Y              | ps_1                          |                                     |
      | endvendor_1   | Y            | N              | ps_1                          | ds_1                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endvendor_location_1   | endvendor_1              | Y                   | Y                   |
      | endcustomer_location_1 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | endvendor_1              | p_1                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | pc_1       | o_1                     | ol_1                        | p_1                     |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID.Identifier | OPT.QtyToPurchase |
      | pc_1                              | 10                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And the following C_PurchaseCandidates are enqueued for generating C_Orders
      | C_PurchaseCandidate_ID.Identifier |
      | pc_1                              |

    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pc_1                              | pca_1                                   |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID.Identifier | C_PurchaseCandidate_Alloc_ID.Identifier |
      | pol_1                       | pca_1                                   |

    And load C_Order from C_OrderLine
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | po_1                  | pol_1                     |

## PO header and lines
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus | C_Project_ID |
      | po_1                  | endvendor_1              | endvendor_location_1              | 2021-04-16  | POO         | EUR          | F            | P               | true      | CO        | proj1        |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | C_Project_ID |
      | pol_1                     | po_1                  | 2021-04-16      | p_1                     | 10         | 0            | 0           | 10    | 0        | EUR          | true      | proj1        |

## SO lines populated
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Project_ID |
      | ol_1                      | proj1        |
