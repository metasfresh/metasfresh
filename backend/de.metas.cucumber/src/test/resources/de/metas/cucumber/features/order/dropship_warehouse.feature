@from:cucumber
@ghActions:run_on_executor6
Feature: Dropship-warehouse SO auto-creates a PO and bypasses material disposition

  When a sales order is placed on a warehouse whose IsDropShipWarehouse='Y',
  the system automatically creates a matching purchase order (IsDropShip='Y')
  and must NOT post SupplyRequired events, so no MD_Candidate rows appear.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-06-17T08:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_AttributeSet:
      | Identifier      | Name               |
      | attributeSet_ds | Convenience Salate |
    And load M_Product_Category:
      | Identifier        | Name     | Value    |
      | standard_category | Standard | Standard |
    And update M_Product_Category:
      | Identifier        | M_AttributeSet_ID |
      | standard_category | attributeSet_ds   |

    And metasfresh contains M_PricingSystems
      | Identifier | IsActive |
      | ps_dw      | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_dw_so   | ps_dw              | DE                    | EUR                 | true  | false         | 2              | true     |
      | pl_dw_po   | ps_dw              | DE                    | EUR                 | false | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | plv_dw_so   | pl_dw_so       |
      | plv_dw_po   | pl_dw_po       |

    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID | IsSold | IsPurchased |
      | product_dw | standard_category     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_dw_so   | plv_dw_so              | product_dw   | 20.0     | PCE               | Normal                        |
      | pp_dw_po   | plv_dw_po              | product_dw   | 15.0     | PCE               | Normal                        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name             | DiscountType | ValidFrom  |
      | ds_dw      | ds_dropship_wh   | F            | 2024-01-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_dw     | ds_dw               | product_dw   | ps_dw                 | 10    | Y                      | P         | 0          | 0             |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete |
      | ppln_dw    | product_dw   | true         | Y           | true          |

    # Customer bpartner (for the SO)
    And metasfresh contains C_BPartners without locations:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_dw  | N        | Y          | ps_dw              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_dw_loc   | customer_dw   | Y               | Y               |

    # Vendor bpartner (the one who will fulfill the purchase)
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_dw   | Y        | N          | ps_dw              | ds_dw                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_dw_loc   | vendor_dw     | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_dw     | product_dw   |

    # Dropship warehouse — the key setup that triggers the dropship-warehouse flow
    And metasfresh contains M_Warehouse:
      | Identifier   | IsDropShipWarehouse |
      | warehouse_dw | Y                   |

  @from:cucumber
  Scenario: SO on a dropship warehouse auto-creates a PO with IsDropShip=Y and order-line allocation records
    # Create a sales order with 2 lines; the vendor is set on each order line so the
    # purchase candidate grouping links them to the same vendor.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw1     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_ID |
      | sol_dw1_1  | so_dw1     | product_dw   | 10         | vendor_dw     |
      | sol_dw1_2  | so_dw1     | product_dw   | 5          | vendor_dw     |
    When the order identified by so_dw1 is completed

    # Wait for async processing (purchase candidate + PO generation)
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Verify purchase candidates were created (one per SO line)
    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID |
      | pc_dw1_1   | so_dw1       | sol_dw1_1        | product_dw   |
    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID |
      | pc_dw1_2   | so_dw1       | sol_dw1_2        | product_dw   |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID | QtyToPurchase | IsDropShip |
      | pc_dw1_1               | 10            | false      |
    And C_PurchaseCandidates are validated
      | C_PurchaseCandidate_ID | QtyToPurchase | IsDropShip |
      | pc_dw1_2               | 5             | false      |

    # Verify C_PurchaseCandidate_Alloc rows — they link the PO lines back to the SO lines
    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_dw1_1               | pca_dw1_1                    |
    And after not more than 60s, C_PurchaseCandidate_Alloc are found
      | C_PurchaseCandidate_ID | C_PurchaseCandidate_Alloc_ID |
      | pc_dw1_2               | pca_dw1_2                    |

    And load C_OrderLines from C_PurchaseCandidate_Alloc
      | C_OrderLinePO_ID | C_PurchaseCandidate_Alloc_ID |
      | pol_dw1_1        | pca_dw1_1                    |
      | pol_dw1_2        | pca_dw1_2                    |

    And load C_Order from C_OrderLine
      | C_Order_ID | C_OrderLine_ID |
      | po_dw1     | pol_dw1_1      |

    # Assert: exactly 1 PO was created; it has IsDropShip=Y and DocStatus=CO
    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | DocBaseType | DocStatus | IsDropShip |
      | po_dw1     | vendor_dw     | POO         | CO        | true       |

  @from:cucumber
  Scenario: SO on a dropship warehouse does not create MD_Candidate rows
    # When IsDropShipWarehouse=Y, the shipment-schedule event carries isDropShipWarehouse=true
    # which causes the material-dispo service to skip SupplyRequired processing entirely.
    # Observable outcome: zero MD_Candidate rows for the product after SO completion + queue drain.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw2     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_ID |
      | sol_dw2_1  | so_dw2     | product_dw   | 8          | vendor_dw     |
      | sol_dw2_2  | so_dw2     | product_dw   | 3          | vendor_dw     |
    When the order identified by so_dw2 is completed

    # Drain the event queue so any async processing that would create MD_Candidates has a chance to run
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Assert: no MD_Candidate demand rows created for the dropship-warehouse product
    Then no MD_Candidate exists for M_Product_ID product_dw
