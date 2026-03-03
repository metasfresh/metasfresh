@from:cucumber
@ghActions:run_on_executor6
Feature: Dropship fields propagated from SO to Purchase Candidate and Purchase Order

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

  @from:cucumber
  Scenario: Dropship SO creates purchase candidate and PO with dropship fields
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | IsSold | IsPurchased |
      | p_ds1      | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | IsActive |
      | ps_ds1     | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_ds1     | ps_ds1             | DE                    | EUR                 | true  | false         | 2              | true     |
      | pl_ds2     | ps_ds1             | DE                    | EUR                 | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_ds1    | pl_ds1         |
      | plv_ds2    | pl_ds2         |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ds1     | plv_ds1                | p_ds1        | 10.0     | PCE               | Normal                        |
      | pp_ds2     | plv_ds2                | p_ds1        | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                      | DiscountType | ValidFrom  |
      | ds_ds1     | ds_purchCandDropShip_1     | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_ds1    | ds_ds1              | p_ds1        | ps_ds1                | 10    | Y                      | P         | 10         | 0             |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete |
      | ppln_ds1   | p_ds1        | true         | Y           | true          |

    # Customer who orders (the SO partner)
    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_ds1    | N        | Y          | ps_ds1             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_ds1_loc      | customer_ds1  | Y               | Y               |

    # Dropship destination partner (where goods are shipped to)
    And metasfresh contains C_BPartners without locations:
      | Identifier          | IsVendor | IsCustomer |
      | dropship_dest_ds1   | N        | Y          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                | C_BPartner_ID   | IsShipToDefault | IsBillToDefault |
      | dropship_dest_ds1_loc     | dropship_dest_ds1 | Y               | Y               |

    # Vendor who fulfills the purchase
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_ds1    | Y        | N          | ps_ds1             | ds_ds1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_ds1_loc    | vendor_ds1    | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_ds1    | p_ds1        |

    # Create SO with dropship fields
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID      |
      | so_ds1     | true    | customer_ds1  | 2021-04-17  | 2021-04-16T21:00:00Z | true       | dropship_dest_ds1    | dropship_dest_ds1_loc     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_ds1    | so_ds1     | p_ds1        | 10         |
    When the order identified by so_ds1 is completed

    # Verify purchase candidate was created with dropship fields
    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID |
      | pc_ds1     | so_ds1       | sol_ds1          | p_ds1        |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID | QtyToPurchase | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID      |
      | pc_ds1                 | 10            | true       | dropship_dest_ds1    | dropship_dest_ds1_loc     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Verify PO was auto-generated
    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_ds1                 | pca_ds1                      |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID | C_PurchaseCandidate_Alloc_ID |
      | pol_ds1          | pca_ds1                      |

    And load C_Order from C_OrderLine
      | C_Order_ID | C_OrderLine_ID |
      | po_ds1     | pol_ds1        |

    # Verify PO has dropship fields
    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | DocBaseType | DocStatus | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID      |
      | po_ds1     | vendor_ds1    | POO         | CO        | true       | dropship_dest_ds1    | dropship_dest_ds1_loc     |

  @from:cucumber
  Scenario: Two dropship SOs with different destinations produce two separate POs
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | IsSold | IsPurchased |
      | p_ds2      | standard_category     | Y      | Y           |
    And metasfresh contains M_PricingSystems
      | Identifier | IsActive |
      | ps_ds2     | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_ds2s    | ps_ds2             | DE                    | EUR                 | true  | false         | 2              | true     |
      | pl_ds2p    | ps_ds2             | DE                    | EUR                 | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_ds2s   | pl_ds2s        |
      | plv_ds2p   | pl_ds2p        |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ds2s    | plv_ds2s               | p_ds2        | 10.0     | PCE               | Normal                        |
      | pp_ds2p    | plv_ds2p               | p_ds2        | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                      | DiscountType | ValidFrom  |
      | ds_ds2     | ds_purchCandDropShip_2     | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_ds2    | ds_ds2              | p_ds2        | ps_ds2                | 10    | Y                      | P         | 10         | 0             |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete |
      | ppln_ds2   | p_ds2        | true         | Y           | true          |

    # Customer who orders
    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_ds2    | N        | Y          | ps_ds2             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_ds2_loc      | customer_ds2  | Y               | Y               |

    # Two different dropship destinations
    And metasfresh contains C_BPartners without locations:
      | Identifier          | IsVendor | IsCustomer |
      | dropship_dest_A     | N        | Y          |
      | dropship_dest_B     | N        | Y          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | C_BPartner_ID   | IsShipToDefault | IsBillToDefault |
      | dropship_dest_A_loc   | dropship_dest_A | Y               | Y               |
      | dropship_dest_B_loc   | dropship_dest_B | Y               | Y               |

    # Vendor
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_ds2    | Y        | N          | ps_ds2             | ds_ds2               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_ds2_loc    | vendor_ds2    | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_ds2    | p_ds2        |

    # SO 1: dropship to destination A
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID  |
      | so_ds2a    | true    | customer_ds2  | 2021-04-17  | 2021-04-16T21:00:00Z | true       | dropship_dest_A      | dropship_dest_A_loc   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_ds2a   | so_ds2a    | p_ds2        | 5          |
    When the order identified by so_ds2a is completed

    # SO 2: dropship to destination B
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID  |
      | so_ds2b    | true    | customer_ds2  | 2021-04-17  | 2021-04-16T21:00:00Z | true       | dropship_dest_B      | dropship_dest_B_loc   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_ds2b   | so_ds2b    | p_ds2        | 5          |
    When the order identified by so_ds2b is completed

    # Verify both purchase candidates created with their respective dropship fields
    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID |
      | pc_ds2a    | so_ds2a      | sol_ds2a         | p_ds2        |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID | QtyToPurchase | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID  |
      | pc_ds2a                | 5             | true       | dropship_dest_A      | dropship_dest_A_loc   |

    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID |
      | pc_ds2b    | so_ds2b      | sol_ds2b         | p_ds2        |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID | QtyToPurchase | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID  |
      | pc_ds2b                | 5             | true       | dropship_dest_B      | dropship_dest_B_loc   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Verify two separate POs were created (one per dropship destination)
    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_ds2a                | pca_ds2a                     |
    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_ds2b                | pca_ds2b                     |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID | C_PurchaseCandidate_Alloc_ID |
      | pol_ds2a         | pca_ds2a                     |
      | pol_ds2b         | pca_ds2b                     |

    And load C_Order from C_OrderLine
      | C_Order_ID | C_OrderLine_ID |
      | po_ds2a    | pol_ds2a       |
      | po_ds2b    | pol_ds2b       |

    # Verify two distinct POs with correct dropship destinations
    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | DocBaseType | DocStatus | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID  |
      | po_ds2a    | vendor_ds2    | POO         | CO        | true       | dropship_dest_A      | dropship_dest_A_loc   |
      | po_ds2b    | vendor_ds2    | POO         | CO        | true       | dropship_dest_B      | dropship_dest_B_loc   |

