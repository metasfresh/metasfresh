@from:cucumber
@allure.label.epic:E0103_Drop_Shipment
@allure.label.feature:F72000_Drop_Shipment
@ghActions:run_on_executor1
Feature: Purchase order generated from a sales order with the same product on several lines

  # The "generate PO from SO" process creates a purchase order for a vendor.
  # - Standard and mediated: sales-order lines with the same product and attributes are merged into one purchase-order line.
  # - Drop-ship: every sales-order line gets its own purchase-order line.
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And set project type Sales/Purchase Order to inactive
    And temporarily set sys config boolean value false for sys config "AUTO_SHIP_AND_INVOICE"

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
    And metasfresh contains M_Products:
      | Identifier |
      | elstar     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_sales              | elstar       | 1.0      | PCE      |
      | plv_purch              | elstar       | 1.0      | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer   | Y          | N        | pricingSystem      |
      | vendor     | N          | Y        | pricingSystem      |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor        | elstar       |
    # the drop-ship purchase order is received into the organisation's drop-ship warehouse, not the sales order's warehouse
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

  # ##############################################################################################
  # Standard purchase: Elstar on two sales-order lines (10 and 5 PCE) ends up on one purchase-order line of 15 PCE.
  # ##############################################################################################

  Scenario: Standard purchase order from a sales order with the same product on two lines has one merged line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | so_std     | true    | customer                 | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | sol_std_a  | so_std                | elstar                  | 10         |
      | sol_std_b  | so_std                | elstar                  | 5          |
    And the order identified by so_std is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_std_a   | sol_std_a                 | N             |
      | ss_std_b   | sol_std_b                 | N             |

    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor                   | so_std                | Standard     |

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_std         | so_std                   | false   | POO         | DR            | false          |
    And the order identified by po_std has 1 order lines
    # both sales-order lines resolve to the one merged purchase-order line, hence the same identifier
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_std_a         | pol_std           | po_std         |
      | sol_std_b         | pol_std           | po_std         |
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered |
      | pol_std        | 15         |
    And the order identified by po_std is completed


  # ##############################################################################################
  # Mediated purchase: Elstar on two sales-order lines (10 and 5 PCE) ends up on one purchase-order line of 15 PCE.
  # ##############################################################################################

  Scenario: Mediated purchase order from a sales order with the same product on two lines has one merged line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | so_med     | true    | customer                 | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | sol_med_a  | so_med                | elstar                  | 10         |
      | sol_med_b  | so_med                | elstar                  | 5          |
    And the order identified by so_med is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_med_a   | sol_med_a                 | N             |
      | ss_med_b   | sol_med_b                 | N             |

    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor                   | so_med                | Mediated     |

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | DocSubType | OPT.DocStatus | OPT.IsDropShip |
      | po_med         | so_med                   | false   | POO         | MED        | DR            | false          |
    And the order identified by po_med has 1 order lines
    # both sales-order lines resolve to the one merged purchase-order line, hence the same identifier
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_med_a         | pol_med           | po_med         |
      | sol_med_b         | pol_med           | po_med         |
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered |
      | pol_med        | 15         |
    And the order identified by po_med is completed


  # ##############################################################################################
  # Drop-ship purchase: Elstar on two sales-order lines (10 and 5 PCE) stays on two purchase-order lines.
  # Each line is received as plain CU handling units and picks its own sales-order line.
  # ##############################################################################################

  Scenario: Drop-ship purchase order from a sales order with the same product on two lines has one line per sales-order line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | so_ds      | true    | customer                 | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | sol_ds_a   | so_ds                 | elstar                  | 10         |
      | sol_ds_b   | so_ds                 | elstar                  | 5          |
    And the order identified by so_ds is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_ds_a    | sol_ds_a                  | N             |
      | ss_ds_b    | sol_ds_b                  | N             |

    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor                   | so_ds                 | Dropship     |

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_ds          | so_ds                    | false   | POO         | DR            | true           |
    And the order identified by po_ds has 2 order lines
    And load C_OrderLines allocated to sales order lines:
      | C_SO_OrderLine_ID | C_PO_OrderLine_ID | OPT.C_Order_ID |
      | sol_ds_a          | pol_ds_a          | po_ds          |
      | sol_ds_b          | pol_ds_b          | po_ds          |
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered |
      | pol_ds_a       | 10         |
      | pol_ds_b       | 5          |

    # receipt schedules are created when the purchase order is completed
    When the order identified by po_ds is completed
    And after not more than 60s, load M_ReceiptSchedule for C_OrderLine:
      | C_OrderLine_ID | M_ReceiptSchedule_ID |
      | pol_ds_a       | rs_ds_a              |
      | pol_ds_b       | rs_ds_b              |
    # the goods are received as CU handling units (No Packing Item, ID 101), as the WebUI action WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam ("Receive CU with Qty") creates them
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | HUList | numberHUsGenerated | C_OrderLine_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | lutu_ds_a                             | hus_ds_a | 1                | pol_ds_a       | N               | 0     | N               | 1     | N               | 10          | 101                                |
      | lutu_ds_b                             | hus_ds_b | 1                | pol_ds_b       | N               | 0     | N               | 1     | N               | 5           | 101                                |
    And create material receipt
      | HUList   | C_OrderLine_ID | M_InOut_ID.Identifier |
      | hus_ds_a | pol_ds_a       | receipt_ds_a          |
      | hus_ds_b | pol_ds_b       | receipt_ds_b          |
    And validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | qtydelivered |
      | pol_ds_a       | 10         | 10           |
      | pol_ds_b       | 5          | 5            |

    # each sales-order line is picked with exactly its own received quantity
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.QtyPickList |
      | ss_ds_a               | 10              |
      | ss_ds_b               | 5               |
