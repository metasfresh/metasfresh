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
    And temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO"
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
    # Only sol_e1 is loaded here: sol_e1 and sol_e2 have the same product and attributes and must stay two purchase-order lines;
    # if they shared one, the e1 receipt below is where it must fail.
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_e1            | pol_e1            | po_1           |

    # the goods arrive after the sales order's shipment schedules exist and are up to date
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_e1      | sol_e1                    | N             |
      | ss_e2      | sol_e2                    | N             |
      | ss_g       | sol_g                     | N             |

    # Receipt schedules are created asynchronously per PO line. They are loaded without a quantity filter:
    # `M_ReceiptSchedule are found` needs the QtyOrdered, which differs when lines are merged.
    And after not more than 60s, load M_ReceiptSchedule for C_OrderLine:
      | C_OrderLine_ID | M_ReceiptSchedule_ID |
      | pol_e1         | rs_e1                |
    # the goods are received as CU handling units (No Packing Item, ID 101), as the WebUI action WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam ("Receive CU with Qty") creates them
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | HUList | numberHUsGenerated | C_OrderLine_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | lutu_e1                               | hus_e1 | 1                  | pol_e1         | N               | 0     | N               | 1     | N               | 924         | 101                                |
    # Fails if one purchase-order line carries allocations to several sales-order lines.
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_e1 | pol_e1         | receipt_e1            |
    # the next goods arrive after the previous receipt's picked quantity is on the shipment schedules
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_e1      | sol_e1                    | N             |
      | ss_e2      | sol_e2                    | N             |
      | ss_g       | sol_g                     | N             |

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
      | lutu_e2                               | hus_e2 | 1                  | pol_e2         | N               | 0     | N               | 1     | N               | 671         | 101                                |
      | lutu_g                                | hus_g  | 1                  | pol_g          | N               | 0     | N               | 1     | N               | 561         | 101                                |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_e2 | pol_e2         | receipt_e2            |
    # the next goods arrive after the previous receipt's picked quantity is on the shipment schedules
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_e1      | sol_e1                    | N             |
      | ss_e2      | sol_e2                    | N             |
      | ss_g       | sol_g                     | N             |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
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


  # ##############################################################################################
  # Elstar is ordered on three sales-order lines (924, 671 and 561 PCE, 84 / 61 / 51 TU of 11 PCE).
  # The drop-ship purchase order has three Elstar lines plus one packing line.
  # The 84 / 61 / 51 TU of 11 PCE is the order packing; the goods are received as plain CU handling units.
  # ##############################################################################################

  Scenario: Three drop-ship order lines of one product: the drop-ship PO has three lines and each is received and picked with its own quantity
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | DateOrdered | PreparationDate      | M_Warehouse_ID.Identifier |
      | so_3       | true    | customerStore            | customerHeadOffice              | 2026-07-01  | 2026-06-30T22:00:00Z | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID.Identifier | C_BPartner_Vendor_ID.Identifier |
      | sol_3a     | so_3                  | elstar                  | 924        | tu11_elstar                        | vendor                          |
      | sol_3b     | so_3                  | elstar                  | 671        | tu11_elstar                        | vendor                          |
      | sol_3c     | so_3                  | elstar                  | 561        | tu11_elstar                        | vendor                          |
    When the order identified by so_3 is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_3           | so_3                     | false   | POO         | CO            | true           |
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_3a            | pol_3a            | po_3           |
      | sol_3b            | pol_3b            | po_3           |
      | sol_3c            | pol_3c            | po_3           |
    # three goods lines plus one packing line: the three Elstar lines are not merged
    And the order identified by po_3 has 4 order lines
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered |
      | pol_3a         | 924        |
      | pol_3b         | 671        |
      | pol_3c         | 561        |

    # the goods arrive after the sales order's shipment schedules exist and are up to date
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_3a      | sol_3a                    | N             |
      | ss_3b      | sol_3b                    | N             |
      | ss_3c      | sol_3c                    | N             |

    # one receipt schedule per purchase-order line, each with the quantity of its own sales-order line
    # the receipt schedules are in the organisation's drop-ship warehouse
    And load M_Warehouse:
      | M_Warehouse_ID         | Value             |
      | warehouse_org_dropship | DropshipWarehouse |
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs_3a                           | po_3                  | pol_3a                    | vendor                   | vendor_loc                        | elstar                  | 924        | warehouse_org_dropship    |
      | rs_3b                           | po_3                  | pol_3b                    | vendor                   | vendor_loc                        | elstar                  | 671        | warehouse_org_dropship    |
      | rs_3c                           | po_3                  | pol_3c                    | vendor                   | vendor_loc                        | elstar                  | 561        | warehouse_org_dropship    |
    # the goods are received as CU handling units (No Packing Item, ID 101), as the WebUI action WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam ("Receive CU with Qty") creates them
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | HUList  | numberHUsGenerated | C_OrderLine_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | lutu_3a                               | hus_3a  | 1                  | pol_3a         | N               | 0     | N               | 1     | N               | 924         | 101                                |
      | lutu_3b                               | hus_3b  | 1                  | pol_3b         | N               | 0     | N               | 1     | N               | 671         | 101                                |
      | lutu_3c                               | hus_3c  | 1                  | pol_3c         | N               | 0     | N               | 1     | N               | 561         | 101                                |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_3a | pol_3a         | receipt_3a            |
    # the next goods arrive after the previous receipt's picked quantity is on the shipment schedules
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_3a      | sol_3a                    | N             |
      | ss_3b      | sol_3b                    | N             |
      | ss_3c      | sol_3c                    | N             |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_3b | pol_3b         | receipt_3b            |
    # the next goods arrive after the previous receipt's picked quantity is on the shipment schedules
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_3a      | sol_3a                    | N             |
      | ss_3b      | sol_3b                    | N             |
      | ss_3c      | sol_3c                    | N             |
    And create material receipt
      | HUList | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_3c | pol_3c         | receipt_3c            |
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | qtydelivered |
      | pol_3a         | 924        | 924          |
      | pol_3b         | 671        | 671          |
      | pol_3c         | 561        | 561          |

    # each sales-order line is picked with exactly its own quantity
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_3a      | sol_3a                    | N             |
      | ss_3b      | sol_3b                    | N             |
      | ss_3c      | sol_3c                    | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | ss_3a                 | 924             |
      | ss_3b                 | 671             |
      | ss_3c                 | 561             |


  # ##############################################################################################
  # One product is ordered on two sales-order lines with different attribute values (attribute
  # "Marke (ADR)", values 02 and 03). The purchase order keeps two lines, each with the attribute value of its own
  # sales-order line.
  # ##############################################################################################

  Scenario: Same product with different attributes on two drop-ship order lines: each drop-ship PO line keeps the attributes of its own sales-order line
    Given load M_AttributeSet:
      | Identifier      | Name               |
      | attributeSet_ds | Convenience Salate |
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value   |
      | attr_brand                | 1000015 |
    And metasfresh contains M_Product_Categories:
      | Identifier    | M_AttributeSet_ID |
      | attr_category | attributeSet_ds   |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | braeburn   | attr_category         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_sales              | braeburn     | 1.0      | PCE      | taxCategory      |
      | plv_purch              | braeburn     | 1.0      | PCE      | taxCategory      |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier    | M_HU_PI_Item_ID | Qty | M_Product_ID |
      | tu11_braeburn | huPIItem_crate  | 11  | braeburn     |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_brand02":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"1000015",
          "valueStr":"02"
        }
      ]
    }
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_brand03":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"1000015",
          "valueStr":"03"
        }
      ]
    }
    """

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | DateOrdered | PreparationDate      | M_Warehouse_ID.Identifier |
      | so_10      | true    | customerStore            | customerHeadOffice              | 2026-07-01  | 2026-06-30T22:00:00Z | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID.Identifier | C_BPartner_Vendor_ID.Identifier | M_AttributeSetInstance_ID |
      | sol_10a    | so_10                 | braeburn                | 110        | tu11_braeburn                      | vendor                          | asi_brand02               |
      | sol_10b    | so_10                 | braeburn                | 66         | tu11_braeburn                      | vendor                          | asi_brand03               |
    When the order identified by so_10 is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_10          | so_10                    | false   | POO         | CO            | true           |
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_10a           | pol_10a           | po_10          |
      | sol_10b           | pol_10b           | po_10          |
    # two goods lines plus one packing line
    And the order identified by po_10 has 3 order lines
    # the PO line gets a copy of the sales-order line's attribute set instance: the attribute values are compared, not the ids
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | attribute:attr_brand |
      | pol_10a        | 110        | 02                   |
      | pol_10b        | 66         | 03                   |

    # the goods arrive after the sales order's shipment schedules exist and are up to date
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_10a     | sol_10a                   | N             |
      | ss_10b     | sol_10b                   | N             |

    And after not more than 60s, load M_ReceiptSchedule for C_OrderLine:
      | C_OrderLine_ID | M_ReceiptSchedule_ID |
      | pol_10a        | rs_10a               |
      | pol_10b        | rs_10b               |
    # the goods are received as CU handling units (No Packing Item, ID 101), as the WebUI action WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam ("Receive CU with Qty") creates them
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | HUList  | numberHUsGenerated | C_OrderLine_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | lutu_10a                              | hus_10a | 1                  | pol_10a        | N               | 0     | N               | 1     | N               | 110         | 101                                |
      | lutu_10b                              | hus_10b | 1                  | pol_10b        | N               | 0     | N               | 1     | N               | 66          | 101                                |
    And create material receipt
      | HUList  | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_10a | pol_10a        | receipt_10a           |
    # the next goods arrive after the previous receipt's picked quantity is on the shipment schedules
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_10a     | sol_10a                   | N             |
      | ss_10b     | sol_10b                   | N             |
    And create material receipt
      | HUList  | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_10b | pol_10b        | receipt_10b           |
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | qtydelivered |
      | pol_10a        | 110        | 110          |
      | pol_10b        | 66         | 66           |

    # each sales-order line is picked with exactly its own quantity
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_10a     | sol_10a                   | N             |
      | ss_10b     | sol_10b                   | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | ss_10a                | 110             |
      | ss_10b                | 66              |
