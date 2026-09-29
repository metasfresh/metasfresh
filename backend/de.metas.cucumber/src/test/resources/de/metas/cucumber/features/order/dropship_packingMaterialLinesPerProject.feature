@from:cucumber
@allure.label.epic:E0240_Project_Management
@allure.label.feature:F68020
@ghActions:run_on_executor1
Feature: dropship packing material lines split per project
## F68020: C_Project propagation through Purchase ↔ Sales flow

  # A dropship warehouse: completing a sales order creates one purchase order per vendor.
  # goods_1 is packed in crates only; goods_2 either in crates or in boxes.
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-06-17T08:00:00+02:00[Europe/Berlin]
    And set project type Sales/Purchase Order to inactive
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_sales   | pricingSystem      | DE                    | EUR                 | true  |
      | pl_purch   | pricingSystem      | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_sales  | pl_sales       |
      | plv_purch  | pl_purch       |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
      | vendor_1   | Y        | N          | pricingSystem      |
      | vendor_2   | Y        | N          | pricingSystem      |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains M_Products:
      | Identifier |
      | goods_1    |
      | goods_2    |
      | crate      |
      | box        |
    # The purchase orders get packing material lines too, so both price lists need the packing material.
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_sales              | goods_1      | 20.0     | PCE      |
      | plv_sales              | goods_2      | 25.0     | PCE      |
      | plv_sales              | crate        | 1.0      | PCE      |
      | plv_sales              | box          | 1.0      | PCE      |
      | plv_purch              | goods_1      | 15.0     | PCE      |
      | plv_purch              | goods_2      | 18.0     | PCE      |
      | plv_purch              | crate        | 0.5      | PCE      |
      | plv_purch              | box          | 0.5      | PCE      |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_crate |
      | huPI_box   |
    And metasfresh contains M_HU_PI_Version:
      | Identifier      | M_HU_PI_ID | HU_UnitType |
      | huVersion_crate | huPI_crate | TU          |
      | huVersion_box   | huPI_box   | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_crate                | crate        |
      | pm_box                  | box          |
    And metasfresh contains M_HU_PI_Item:
      | Identifier     | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem_crate | huVersion_crate    | 10  | PM       | pm_crate                |
      | huPIItem_box   | huVersion_box      | 10  | PM       | pm_box                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier    | M_HU_PI_Item_ID | Qty | M_Product_ID |
      | goods_1_crate | huPIItem_crate  | 5   | goods_1      |
      | goods_2_crate | huPIItem_crate  | 7   | goods_2      |
      | goods_2_box   | huPIItem_box    | 7   | goods_2      |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: projects of two vendors' purchase orders reach one shipment and invoice as one packing material line each
    Given set project type Sales/Purchase Order to active
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID    |
      | salesOrder | true    | customer      | 2024-06-17  | 2024-06-16T22:00:00Z | dropshipWarehouse |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID | M_HU_PI_Item_Product_ID |
      | sol_goods1 | salesOrder | goods_1      | 10         | vendor_1             | goods_1_crate           |
      | sol_goods2 | salesOrder | goods_2      | 21         | vendor_2             | goods_2_crate           |
    When the order identified by salesOrder is completed

    Then the order is created:
      | Identifier | Link_Order_ID | C_BPartner_ID | IsSOTrx | DocBaseType | DocStatus | IsDropShip |
      | po_1       | salesOrder    | vendor_1      | false   | POO         | CO        | true       |
      | po_2       | salesOrder    | vendor_2      | false   | POO         | CO        | true       |
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | po_1       | project_1    |
      | po_2       | project_2    |
    And after not more than 60s, validate C_OrderLine:
      | C_OrderLine_ID | C_Project_ID |
      | sol_goods1     | project_1    |
      | sol_goods2     | project_2    |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | sol_goods1     | N             |
      | ss_goods2  | sol_goods2     | N             |

    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_goods2             |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment   |
      | ss_goods2             | shipment   |
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | crate_project1 | shipment   | crate        | 2           | project_1    |
      | crate_project2 | shipment   | crate        | 3           | project_2    |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID |
      | ic_crate_project1      | crate_project1 |
      | ic_crate_project2      | crate_project2 |
    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_crate_project1      |
      | ic_crate_project2      |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice      | ic_crate_project1      |
    And validate created invoice lines
      | C_InvoiceLine_ID  | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | C_Project_ID |
      | il_crate_project1 | invoice      | crate        | 2           | crate_project1 | project_1    |
      | il_crate_project2 | invoice      | crate        | 3           | crate_project2 | project_2    |

    And set project type Sales/Purchase Order to inactive

  # The already correct dropship cases: the projects are set on the sales order lines, as the purchase-to-sales
  # propagation leaves them; each Examples row is one case (vendors x packing of the second line).
  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario Outline: projects on the order lines reach one shipment and invoice as one packing material line each - <case>
    Given metasfresh contains C_Projects:
      | Identifier |
      | project_1  |
      | project_2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID    |
      | salesOrder | true    | customer      | 2024-06-17  | 2024-06-16T22:00:00Z | dropshipWarehouse |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | C_BPartner_Vendor_ID | M_HU_PI_Item_Product_ID |
      | sol_goods1 | salesOrder | goods_1      | 10         | project_1    | vendor_1             | goods_1_crate           |
      | sol_goods2 | salesOrder | goods_2      | 21         | project_2    | <vendor_of_line_2>   | <packing_of_line_2>     |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | sol_goods1     | N             |
      | ss_goods2  | sol_goods2     | N             |

    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_goods2             |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment   |
      | ss_goods2             | shipment   |
    And validate the created shipment lines
      | M_InOutLine_ID   | M_InOut_ID | M_Product_ID                 | MovementQty | C_Project_ID |
      | packing_project1 | shipment   | crate                        | 2           | project_1    |
      | packing_project2 | shipment   | <packing_material_of_line_2> | 3           | project_2    |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID   |
      | ic_packing_project1    | packing_project1 |
      | ic_packing_project2    | packing_project2 |
    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_packing_project1    |
      | ic_packing_project2    |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice      | ic_packing_project1    |
    And validate created invoice lines
      | C_InvoiceLine_ID    | C_Invoice_ID | M_Product_ID                 | QtyInvoiced | M_InOutLine_ID   | C_Project_ID |
      | il_packing_project1 | invoice      | crate                        | 2           | packing_project1 | project_1    |
      | il_packing_project2 | invoice      | <packing_material_of_line_2> | 3           | packing_project2 | project_2    |

    Examples:
      | case                           | vendor_of_line_2 | packing_of_line_2 | packing_material_of_line_2 |
      | one vendor, different packing  | vendor_1         | goods_2_box       | box                        |
      | one vendor, same packing       | vendor_1         | goods_2_crate     | crate                      |
      | two vendors, different packing | vendor_2         | goods_2_box       | box                        |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: projects on the order lines of two vendors with the same packing reach two shipments with one packing material line each
    Given metasfresh contains C_Projects:
      | Identifier |
      | project_1  |
      | project_2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID    |
      | salesOrder | true    | customer      | 2024-06-17  | 2024-06-16T22:00:00Z | dropshipWarehouse |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | C_BPartner_Vendor_ID | M_HU_PI_Item_Product_ID |
      | sol_goods1 | salesOrder | goods_1      | 10         | project_1    | vendor_1             | goods_1_crate           |
      | sol_goods2 | salesOrder | goods_2      | 21         | project_2    | vendor_2             | goods_2_crate           |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | sol_goods1     | N             |
      | ss_goods2  | sol_goods2     | N             |

    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    # One delivery run per purchase order: two shipments, on purpose.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID       |
      | ss_goods1             | shipment_vendor1 |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods2             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID       |
      | ss_goods2             | shipment_vendor2 |

    Then validate the created shipment lines
      | M_InOut_ID       | M_Product_ID | MovementQty | C_Project_ID |
      | shipment_vendor1 | crate        | 2           | project_1    |
      | shipment_vendor2 | crate        | 3           | project_2    |
