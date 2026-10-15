@from:cucumber
@allure.label.epic:E0103_Drop_Shipment
@allure.label.feature:F72000_Drop_Shipment
@ghActions:run_on_executor1
Feature: Drop-ship purchase order mirrors the sales order line by line

  # Completing a drop-ship sales order creates a purchase order for the vendor.
  # Two sales-order lines with the same product and attributes must stay two purchase-order lines,
  # each received, shipped and invoiced with its own quantity.
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And set project type Sales/Purchase Order to inactive
    And temporarily set sys config boolean value false for sys config "AUTO_SHIP_AND_INVOICE"

    # taxes, so the vendor invoice can be created
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier   | C_Tax_ID | IsSOTrx | AmountType |
      | sales19_T    | tax19    | Y       | T          |
      | sales19_N    | tax19    | Y       | N          |
      | purchase19_T | tax19    | N       | T          |
      | purchase19_N | tax19    | N       | N          |

    # the store orders, the head office is billed
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_sales   | pricingSystem      | DE                    | EUR                 | true  |
      | pl_purch   | pricingSystem      | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_sales  | pl_sales       | 2026-01-01 |
      | plv_purch  | pl_purch       | 2026-01-01 |
    And metasfresh contains C_BPartners:
      | Identifier         | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customerHeadOffice | Y          | N        | pricingSystem      |
      | customerStore      | Y          | N        | pricingSystem      |
    # a vendor is only found for a line without explicit vendor (the packing line) through its purchase discount schema
    And metasfresh contains M_DiscountSchemas:
      | Identifier         | Name                  | DiscountType | ValidFrom  |
      | vendorDiscountPlan | dropship_same_product | F            | 2026-01-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor     | N          | Y        | pricingSystem      | vendorDiscountPlan   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendor_loc | vendor                   | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

    # the category has no attribute set: both Elstar lines have the same (empty) attributes
    And metasfresh contains M_Product_Categories:
      | Identifier  |
      | pm_category |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | elstar     |                       |
      | gala       |                       |
      | crate      | pm_category           |
    And temporarily set AD_SysConfig to M_Product_Category_ID:
      | Name                             | M_Product_Category_ID |
      | PackingMaterialProductCategoryID | pm_category           |
    # Completing a drop-ship sales order needs a vendor on every line, the packing material line included.
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor        | crate        |
    # 1.00 EUR per PCE on both sides
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_sales              | elstar       | 1.0      | PCE      | taxCategory      |
      | plv_sales              | gala         | 1.0      | PCE      | taxCategory      |
      | plv_sales              | crate        | 1.0      | PCE      | taxCategory      |
      | plv_purch              | elstar       | 1.0      | PCE      | taxCategory      |
      | plv_purch              | gala         | 1.0      | PCE      | taxCategory      |
      | plv_purch              | crate        | 1.0      | PCE      | taxCategory      |
    # 11 PCE per transport unit (TU) crate
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_crate |
    And metasfresh contains M_HU_PI_Version:
      | Identifier      | M_HU_PI_ID | HU_UnitType |
      | huVersion_crate | huPI_crate | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_crate                | crate        |
    And metasfresh contains M_HU_PI_Item:
      | Identifier     | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem_crate | huVersion_crate    | 10  | PM       | pm_crate                |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID | Qty | M_Product_ID |
      | tu11_elstar | huPIItem_crate  | 11  | elstar       |
      | tu11_gala   | huPIItem_crate  | 11  | gala         |

  # ##############################################################################################
  # Elstar is ordered on two sales-order lines (924 and 671), Gala on one (561).
  # The drop-ship purchase order has three goods lines plus one packing line.
  # ##############################################################################################

  Scenario: Same product on two drop-ship order lines: the drop-ship PO has one line per sales-order line and each line is received, shipped and invoiced
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | DateOrdered | PreparationDate      | M_Warehouse_ID.Identifier |
      | so_1       | true    | customerStore            | customerHeadOffice              | 2026-07-01  | 2026-06-30T22:00:00Z | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID.Identifier | C_BPartner_Vendor_ID.Identifier |
      | sol_e1     | so_1                  | elstar                  | 924        | tu11_elstar                        | vendor                          |
      | sol_e2     | so_1                  | elstar                  | 671        | tu11_elstar                        | vendor                          |
      | sol_g      | so_1                  | gala                    | 561        | tu11_gala                          | vendor                          |
    When the order identified by so_1 is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_1           | so_1                     | false   | POO         | CO            | true           |
    # The first Elstar line is received before the second one is loaded:
    # with merged purchase-order lines, receiving fails here, before two sales-order lines map to one purchase-order line.
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_e1            | pol_e1            | po_1           |

    # Receipt schedules are created asynchronously per PO line. They are loaded without a quantity filter:
    # `M_ReceiptSchedule are found` needs the QtyOrdered, which differs when lines are merged.
    And after not more than 60s, load M_ReceiptSchedule for C_OrderLine:
      | C_OrderLine_ID | M_ReceiptSchedule_ID |
      | pol_e1         | rs_e1                |
    # one HU per receipt schedule: 84 / 61 / 51 TU of 11 PCE
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | HUList | numberHUsGenerated | C_OrderLine_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | lutu_e1                               | hus_e1 | 84                 | pol_e1         | N               | 0     | N               | 84    | N               | 11          | tu11_elstar                        |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_e1 | pol_e1         | receipt_e1            |

    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_e2            | pol_e2            | po_1           |
      | sol_g             | pol_g             | po_1           |
    And after not more than 60s, load M_ReceiptSchedule for C_OrderLine:
      | C_OrderLine_ID | M_ReceiptSchedule_ID |
      | pol_e2         | rs_e2                |
      | pol_g          | rs_g                 |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | HUList | numberHUsGenerated | C_OrderLine_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | lutu_e2                               | hus_e2 | 61                 | pol_e2         | N               | 0     | N               | 61    | N               | 11          | tu11_elstar                        |
      | lutu_g                                | hus_g  | 51                 | pol_g          | N               | 0     | N               | 51    | N               | 11          | tu11_gala                          |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_e2 | pol_e2         | receipt_e2            |
      | hus_g  | pol_g          | receipt_g             |

    # the drop-ship PO mirrors the sales order line by line
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | qtydelivered |
      | pol_e1         | 924        | 924          |
      | pol_e2         | 671        | 671          |
      | pol_g          | 561        | 561          |
    # one packing line per packing product, not one per goods line
    And the order identified by po_1 has 4 order lines

    # each sales-order line is picked with exactly its own quantity
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_e1      | sol_e1                    | N             |
      | ss_e2      | sol_e2                    | N             |
      | ss_g       | sol_g                     | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | ss_e1                 | 924             |
      | ss_e2                 | 671             |
      | ss_g                  | 561             |

    # shipment to the customer from the picked quantities
    When 'generate shipments' process is invoked with QuantityType=P, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_e1                 |
      | ss_e2                 |
      | ss_g                  |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_e1                 | shipment   |
      | ss_e2                 | shipment   |
      | ss_g                  | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | C_OrderLine_ID | M_Product_ID | MovementQty |
      | shipment   | sol_e1         | elstar       | 924         |
      | shipment   | sol_e2         | elstar       | 671         |
      | shipment   | sol_g          | gala         | 561         |

    # vendor invoice from the receipts: one invoice line per PO line
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | pol_e1         | ic_e1                  |
      | pol_e2         | ic_e2                  |
      | pol_g          | ic_g                   |
    # the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.DateToInvoice_Override |
      | ic_e1                  | 2026-07-01                 |
      | ic_e2                  | 2026-07-01                 |
      | ic_g                   | 2026-07-01                 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_e1                             |
      | ic_e2                             |
      | ic_g                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice_1         | ic_e1                             |
    And validate invoice lines for vendorInvoice_1:
      | C_InvoiceLine_ID | M_Product_ID | QtyInvoiced |
      | il_e1            | elstar       | 924         |
      | il_e2            | elstar       | 671         |
      | il_g             | gala         | 561         |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtyinvoiced |
      | pol_e1         | 924         |
      | pol_e2         | 671         |
      | pol_g          | 561         |
