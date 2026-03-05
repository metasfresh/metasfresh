@from:cucumber
@ghActions:run_on_executor6
Feature: Packing items, TU quantities, and attributes propagated from SO to Purchase Candidate and Purchase Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_Product:
      | Identifier | OPT.M_Product_ID |
      | ifco_6410  | 2001343          |

    And update M_Product:
      | Identifier | IsActive |
      | ifco_6410  | Y        |

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
  Scenario: SO with packing item and TU qty creates purchase candidate and PO with same packing and TU qty
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | IsSold | IsPurchased |
      | p_pk1      | standard_category     | Y      | Y           |

    # Set up HU packing (TU)
    And load M_HU_PI:
      | M_HU_PI_ID.Identifier | M_HU_PI_ID |
      | huPI_pk1              | 1000006    |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | huPIV_pk1                     | 2002669            |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItem_pk1               | huPIV_pk1                     | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemProd_pk1                   | huPiItem_pk1               | p_pk1                   | 10  | 2021-01-01 |

    # Set up an ASI to put on the SO line
    And metasfresh contains M_AttributeSetInstance with identifier "asi_pk1":
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

    And metasfresh contains M_PricingSystems
      | Identifier | IsActive |
      | ps_pk1     | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_pk1s    | ps_pk1             | DE                    | EUR                 | true  | false         | 2              | true     |
      | pl_pk1p    | ps_pk1             | DE                    | EUR                 | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_pk1s   | pl_pk1s        |
      | plv_pk1p   | pl_pk1p        |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_pk1s    | plv_pk1s               | p_pk1        | 20.0     | PCE               | Normal                        |
      | pp_pk1p    | plv_pk1p               | p_pk1        | 10.0     | PCE               | Normal                        |
      | ifco_6410s | plv_pk1s               | ifco_6410    | 0.3      | PCE               | Normal                        |
      | ifco_6410p | plv_pk1p               | ifco_6410    | 0.1      | PCE               | Normal                        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                  | DiscountType | ValidFrom  |
      | ds_pk1     | ds_purchCandPacking_1 | F            | 2021-04-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_pk1    | ds_pk1              | p_pk1        | ps_pk1                | 10    | Y                      | P         | 10         | 0             |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete |
      | ppln_pk1   | p_pk1        | true         | Y           | true          |

    # Customer
    And metasfresh contains C_BPartners without locations:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_pk1 | N        | Y          | ps_pk1             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_pk1_loc | customer_pk1  | Y               | Y               |

    # Vendor
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_pk1 | Y        | N          | ps_pk1             | ds_pk1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_pk1_loc | vendor_pk1    | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_pk1    | p_pk1        |

    # Create SO with packing item, TU qty, and ASI on the order line
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      |
      | so_pk1     | true    | customer_pk1  | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID | QtyEnteredTU | M_AttributeSetInstance_ID |
      | sol_pk1    | so_pk1     | p_pk1        | 30         | huPiItemProd_pk1        | 3            | asi_pk1                   |
    When the order identified by so_pk1 is completed

    # Verify purchase candidate has packing item, TU qty, and ASI
    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID |
      | pc_pk1     | so_pk1       | sol_pk1          | p_pk1        |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID | QtyToPurchase | M_HU_PI_Item_Product_ID | QtyEnteredTU |
      | pc_pk1                 | 30            | huPiItemProd_pk1        | 3            |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Verify PO was auto-generated
    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_pk1                 | pca_pk1                      |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID | C_PurchaseCandidate_Alloc_ID |
      | pol_pk1          | pca_pk1                      |

    And load C_Order from C_OrderLine
      | C_Order_ID | C_OrderLine_ID |
      | po_pk1     | pol_pk1        |

    # Verify PO line has packing item and TU qty
    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | DocBaseType | DocStatus |
      | po_pk1     | vendor_pk1    | POO         | CO        |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | pol_pk1                   | huPiItemProd_pk1                       | 3                |
