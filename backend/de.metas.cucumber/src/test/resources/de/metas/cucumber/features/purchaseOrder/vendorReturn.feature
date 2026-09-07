@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
@ghActions:run_on_executor7
Feature: Vendor Return from Material Receipt
## Tests that creating a vendor return via M_InOut_GenerateVendorReturn reassigns M_HUs
## from the original material receipt to the vendor return document upon completion.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | Identifier   | Value        |
      | warehouseStd | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier |
      | product_VR |

    And load M_Product:
      | Identifier      | M_Product_ID |
      | packingMaterial | 2001343      |

    And load M_HU_PI:
      | M_HU_PI_ID.Identifier  | M_HU_PI_ID |
      | huPackingTauschpalette | 1000006    |
    And load M_HU_PI_Version:
      | Identifier          | M_HU_PI_Version_ID |
      | packingItem_IFCO_VR | 2002669            |
    And metasfresh contains M_HU_PI_Item:
      | Identifier       | M_HU_PI_Version_ID  | Qty | ItemType |
      | huPiItem_IFCO_VR | packingItem_IFCO_VR | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier         | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | huPiItemProduct_VR | huPiItem_IFCO_VR | product_VR   | 10  | 2022-03-01 |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_VR      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_VR      | ps_VR              | DE                        | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_VR     | pl_VR          | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_VR      | plv_VR                 | product_VR      | 10.0     | PCE               | Normal                        |
      | pm_VR      | plv_VR                 | packingMaterial | 0.2      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID |
      | supplier_VR | Y            | N              | ps_VR              |


  @from:cucumber
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  Scenario: Create vendor return from material receipt and verify M_HU reassignment
    # Step 1: Create Purchase Order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_VR   | N       | supplier_VR   | 2022-06-10  | po_ref_VR       | POO             | ps_VR                  | supplier_VR                | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_VR | order_VR   | product_VR   | 10         |

    When the order identified by order_VR is completed

    # Step 2: Wait for material receipt candidate (M_ReceiptSchedule) and create M_HUs
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_VR   | order_VR   | orderLine_VR   | supplier_VR   | supplier_VR            | product_VR   | 10         | warehouseStd   |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | Identifier      | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID          |
      | huLuTuConfig_VR | hu_VR   | receiptSchedule_VR   | N               | 1     | N               | 1     | N               | 10          | huPiItemProduct_VR      | huPackingTauschpalette |

    # Step 3: Create material receipt (M_InOut) from material receipt candidate
    When create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | hu_VR   | receiptSchedule_VR   | receipt_VR |

    # Step 4: Verify M_HUs are associated with the material receipt line
    And M_HU are validated:
      | M_HU_ID | HUStatus | IsActive |
      | hu_VR   | A        | Y        |
    And M_HU_Storage are validated
      | M_HU_ID | M_Product_ID | Qty |
      | hu_VR   | product_VR   | 10  |

    # Step 5: Create vendor return using M_InOut_GenerateVendorReturn process
    And generate vendor return from receipt
      | M_InOut_ID | VendorReturn_ID |
      | receipt_VR | vendorReturn_VR |

    # Step 6: Complete the vendor return — this triggers VendorReturnFromReceiptHUHandler
    # which splits the receipt HUs and assigns them to the vendor return
    And the return inOut identified by vendorReturn_VR is completed

    # Step 7: Assert M_HUs are now assigned to vendor return (not original receipt)
    And load HUs assigned to M_InOut
      | M_InOut_ID      | M_HU_ID   |
      | vendorReturn_VR | return_hu |

    And M_HU are validated:
      | M_HU_ID   | HUStatus |
      | return_hu | E        |

    # The original receipt HU was fully consumed by the split and is now Destroyed
    And M_HU are validated:
      | M_HU_ID | HUStatus | IsActive |
      | hu_VR   | D        | false    |


  @from:cucumber
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  Scenario: Reactivating a completed vendor return unassigns M_HUs from vendor return and reassigns them to the original receipt
    # Step 1: Create Purchase Order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_VR2  | N       | supplier_VR   | 2022-06-10  | po_ref_VR2      | POO             | ps_VR                  | supplier_VR                | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_VR2 | order_VR2  | product_VR   | 10         |

    When the order identified by order_VR2 is completed

    # Step 2: Create material receipt (M_InOut) from material receipt candidate
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_VR2  | order_VR2  | orderLine_VR2  | supplier_VR   | supplier_VR            | product_VR   | 10         | warehouseStd   |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | Identifier       | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID          |
      | huLuTuConfig_VR2 | hu_VR2  | receiptSchedule_VR2  | N               | 1     | N               | 1     | N               | 10          | huPiItemProduct_VR      | huPackingTauschpalette |

    When create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID  |
      | hu_VR2  | receiptSchedule_VR2  | receipt_VR2 |

    # Step 3: Create vendor return and complete it
    And generate vendor return from receipt
      | M_InOut_ID  | VendorReturn_ID  |
      | receipt_VR2 | vendorReturn_VR2 |

    And the return inOut identified by vendorReturn_VR2 is completed

    # After completion: vendor return has the split HU, original receipt HU is Destroyed
    And load HUs assigned to M_InOut
      | M_InOut_ID       | M_HU_ID    |
      | vendorReturn_VR2 | return_hu2 |

    And M_HU are validated:
      | M_HU_ID    | HUStatus |
      | return_hu2 | E        |

    And M_HU are validated:
      | M_HU_ID | HUStatus | IsActive |
      | hu_VR2  | D        | false    |

    # Step 4: Reactivate the vendor return
    # processReturnsReactivation (TIMING_AFTER_REACTIVATE) will:
    #   - unassign return_hu2 from the vendor return line
    #   - reassign return_hu2 to the original receipt line
    And the return inOut identified by vendorReturn_VR2 is reactivated

    # Step 5: Assert vendor return no longer has any HU assignments
    # (processReturnsReactivation unassigns return_hu2 from vendorReturn_VR2 and
    # reassigns it to the original receipt_VR2 line)
    And assert no HUs assigned to M_InOut
      | M_InOut_ID       |
      | vendorReturn_VR2 |

    # Step 6: Assert return_hu2 still exists unchanged — its HUStatus is now Active
    # Combined with the assertion above, this confirms return_hu2 is back on the receipt.
    And M_HU are validated:
      | M_HU_ID    | HUStatus |
      | return_hu2 | A        |


  @from:cucumber
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  Scenario: Reversing a completed vendor return removes all M_HU assignments from it
    # Step 1: Create Purchase Order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_VR3  | N       | supplier_VR   | 2022-06-10  | po_ref_VR3      | POO             | ps_VR                  | supplier_VR                | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_VR3 | order_VR3  | product_VR   | 10         |

    When the order identified by order_VR3 is completed

    # Step 2: Wait for material receipt candidate and create M_HUs
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_VR3  | order_VR3  | orderLine_VR3  | supplier_VR   | supplier_VR            | product_VR   | 10         | warehouseStd   |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | Identifier       | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID          |
      | huLuTuConfig_VR3 | hu_VR3  | receiptSchedule_VR3  | N               | 1     | N               | 1     | N               | 10          | huPiItemProduct_VR      | huPackingTauschpalette |

    When create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID  |
      | hu_VR3  | receiptSchedule_VR3  | receipt_VR3 |

    # Step 3: Create vendor return and complete it
    And generate vendor return from receipt
      | M_InOut_ID  | VendorReturn_ID  |
      | receipt_VR3 | vendorReturn_VR3 |

    And the return inOut identified by vendorReturn_VR3 is completed

    # After completion: vendor return has the split HU (Shipped), original receipt HU is Destroyed
    And load HUs assigned to M_InOut
      | M_InOut_ID       | M_HU_ID    |
      | vendorReturn_VR3 | return_hu3 |

    And M_HU are validated:
      | M_HU_ID    | HUStatus |
      | return_hu3 | E        |

    And M_HU are validated:
      | M_HU_ID | HUStatus | IsActive |
      | hu_VR3  | D        | false    |

    # Step 4: Reverse the vendor return
    # reverseReturn (TIMING_AFTER_REVERSEACCRUAL | TIMING_AFTER_REVERSECORRECT) removes all HU assignments
    And the return inOut identified by vendorReturn_VR3 is reversed as vendorReturnReversal_VR3

    # Step 5: Assert vendor return no longer has any HU assignments
    And assert no HUs assigned to M_InOut
      | M_InOut_ID       |
      | vendorReturn_VR3 |

    # Step 6: Assert return_hu3 still exists (reversal does not destroy it), and marked Active
    And M_HU are validated:
      | M_HU_ID    | HUStatus | IsActive |
      | return_hu3 | A        | Y        |


  @from:cucumber
  @Id:S30583_TC1
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  Scenario: Vendor return invoice candidate prices correctly when product price is keyed on Packvorschrift
    # The product has TWO PI-keyed prices (the order-line PI + a decoy for a different PI). Having ≥2 prices
    # defeats UniqueProductPriceFallbackRule, which would otherwise return the single price and mask the defect.
    # The origin receipt line carries no Packvorschrift (vendor receipt lines are not PI-stamped); pricing must
    # resolve it from the linked purchase-order line. The invoice candidate is then priced correctly:
    # IsError=N and PriceActual=15 (the original PO price).

    # Step 0: Define the return product first — it is referenced by the PI chains and prices below.
    Given metasfresh contains M_Products:
      | Identifier    |
      | product_VR_PI |

    # Step 1a: Main Packvorschrift for product_VR_PI — used by the order line, HU config, and the main (15) price.
    # It MUST belong to product_VR_PI (a Packvorschrift is product-scoped; reusing the Background's
    # huPiItemProduct_VR — which belongs to product_VR — would make the order line get restamped to a
    # valid PI for product_VR_PI, silently defeating the test). We hang it off the Background's IFCO TU
    # item (huPiItem_IFCO_VR), which is structurally loadable onto the Tauschpalette LU used below;
    # a fresh bare TU is NOT loadable onto that LU.
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier              | M_HU_PI_Item_ID  | M_Product_ID  | Qty | ValidFrom  |
      | huPiItemProd_VR_PI_main | huPiItem_IFCO_VR | product_VR_PI | 10  | 2022-03-01 |

    # Step 1b: Build the decoy PI chain (a second PI for product_VR_PI, to force ≥2 PI-keyed price candidates).
    # Having ≥2 candidates defeats UniqueProductPriceFallbackRule — without the decoy, a single
    # PI-keyed price might be returned as a fallback regardless of which PI the order line carries.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID           |
      | huPackTU_VR_PI_decoy |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID     | M_HU_PI_ID           | HU_UnitType | IsCurrent |
      | packingVer_VR_PI_decoy | huPackTU_VR_PI_decoy | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID      | M_HU_PI_Version_ID     | ItemType |
      | huPiItem_VR_PI_decoy | packingVer_VR_PI_decoy | MI       |

    # Step 1c: Set up the pricing system / price list (product defined in Step 0; two PI-keyed prices defeat UniqueProductPriceFallbackRule)
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_VR_PI   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_VR_PI   | ps_VR_PI           | DE                        | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_VR_PI  | pl_VR_PI       | 2022-03-01 |
    # Decoy PI item product (must exist before it can be referenced by a M_ProductPrice)
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier               | M_HU_PI_Item_ID      | M_Product_ID  | Qty | ValidFrom  |
      | huPiItemProd_VR_PI_decoy | huPiItem_VR_PI_decoy | product_VR_PI | 10  | 2022-03-01 |
    # Two PI-keyed prices, BOTH for product_VR_PI Packvorschriften:
    #   pp_VR_PI      → huPiItemProd_VR_PI_main (PriceStd=15): the price matched by the order-line PI.
    #   pp_VR_PI_decoy→ huPiItemProd_VR_PI_decoy (PriceStd=99): unreferenced by order/HU; exists only
    #                    to give product_VR_PI ≥2 PI-keyed candidates, defeating UniqueProductPriceFallbackRule.
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | M_HU_PI_Item_Product_ID  |
      | pp_VR_PI           | plv_VR_PI              | product_VR_PI   | 15.0     | PCE               | Normal                        | huPiItemProd_VR_PI_main  |
      | pp_VR_PI_decoy     | plv_VR_PI              | product_VR_PI   | 99.0     | PCE               | Normal                        | huPiItemProd_VR_PI_decoy |
      | pm_VR_PI           | plv_VR_PI              | packingMaterial | 0.2      | PCE               | Normal                        |                          |
    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID |
      | supplier_VR_PI | Y            | N              | ps_VR_PI           |

    # Step 2: Create Purchase Order — order line carries the main Packvorschrift (huPiItemProd_VR_PI_main)
    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID  | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID | OPT.C_BPartner_Location_ID | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_VR_PI | N       | supplier_VR_PI | 2022-06-10  | po_ref_VR_PI    | POO             | ps_VR_PI               | supplier_VR_PI              | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID  | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_VR_PI | order_VR_PI | product_VR_PI | 10         | huPiItemProd_VR_PI_main |

    When the order identified by order_VR_PI is completed

    # Step 3: Wait for receipt schedule and create HUs with the main Packvorschrift (huPiItemProd_VR_PI_main)
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID  | C_Order_ID  | C_OrderLine_ID  | C_BPartner_ID  | C_BPartner_Location_ID | M_Product_ID  | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_VR_PI | order_VR_PI | orderLine_VR_PI | supplier_VR_PI | supplier_VR_PI         | product_VR_PI | 10         | warehouseStd   |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | Identifier         | M_HU_ID  | M_ReceiptSchedule_ID  | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID          |
      | huLuTuConfig_VR_PI | hu_VR_PI | receiptSchedule_VR_PI | N               | 1     | N               | 1     | N               | 10          | huPiItemProd_VR_PI_main | huPackingTauschpalette |

    # Step 4: Create material receipt — origin receipt line has null PI columns (design: receipt lines are not PI-stamped)
    When create material receipt
      | M_HU_ID  | M_ReceiptSchedule_ID  | M_InOut_ID    |
      | hu_VR_PI | receiptSchedule_VR_PI | receipt_VR_PI |

    # Step 5: Create vendor return via the real HU-producer path (auto-completes; no separate complete step needed)
    And generate vendor return from receipt HUs
      | M_InOut_ID    | M_HU_ID  | VendorReturn_ID    |
      | receipt_VR_PI | hu_VR_PI | vendorReturn_VR_PI |

    # Step 6: Wait for the product IC (filter by product to exclude packing-material ICs) and assert the price.
    # The return invoice candidate is priced from the origin receipt line (PI null); resolving the PI from the
    # order line finds the PI-keyed price: IsError=N, PriceActual=15 (the original PO price).
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID.Identifier | C_Invoice_Candidate_ID.Identifier | OPT.M_Product_ID.Identifier |
      | vendorReturn_VR_PI    | ic_VR_PI                          | product_VR_PI               |

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsError | PriceActual |
      | ic_VR_PI               | false   | 15          |
