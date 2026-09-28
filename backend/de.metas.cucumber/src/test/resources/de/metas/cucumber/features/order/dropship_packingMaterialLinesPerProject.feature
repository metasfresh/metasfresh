@from:cucumber
@allure.label.epic:E0240_Project_Management
@allure.label.feature:F68020
@ghActions:run_on_executor1
Feature: dropship packing material lines split per project
## F68020: C_Project propagation through Purchase ↔ Sales flow

  # Background copied verbatim from dropship_warehouse.feature:11-89 (do NOT edit that file — copy
  # only), plus the packing data every dropship scenario in this feature uses.
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
      | Identifier | M_PriceList_ID |
      | plv_dw_so  | pl_dw_so       |
      | plv_dw_po  | pl_dw_po       |

    And metasfresh contains M_Products:
      | Identifier   | M_Product_Category_ID | IsSold | IsPurchased |
      | product_dw   | standard_category     | Y      | Y           |
      | product_dw_2 | standard_category     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_dw_so   | plv_dw_so              | product_dw   | 20.0     | PCE               | Normal                        |
      | pp_dw_po   | plv_dw_po              | product_dw   | 15.0     | PCE               | Normal                        |
      | pp_dw_so_2 | plv_dw_so              | product_dw_2 | 25.0     | PCE               | Normal                        |
      | pp_dw_po_2 | plv_dw_po              | product_dw_2 | 18.0     | PCE               | Normal                        |

    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name           | DiscountType | ValidFrom  |
      | ds_dw      | ds_dropship_wh | F            | 2024-01-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb_dw     | ds_dw               | product_dw   | ps_dw                 | 10    | Y                      | P         | 0          | 0             |
      | dsb_dw_2   | ds_dw               | product_dw_2 | ps_dw                 | 20    | Y                      | P         | 0          | 0             |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete |
      | ppln_dw    | product_dw   | true         | Y           | true          |
      | ppln_dw_2  | product_dw_2 | true         | Y           | true          |

    # Customer bpartner (for the SO)
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_dw | N        | Y          | ps_dw              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_dw_loc | customer_dw   | Y               | Y               |

    # Vendor bpartner (the one who will fulfill the purchase)
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_dw   | Y        | N          | ps_dw              | ds_dw                |
      | vendor_dw_2 | Y        | N          | ps_dw              | ds_dw                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_dw_loc   | vendor_dw     | Y               | Y               |
      | vendor_dw_2_loc | vendor_dw_2   | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_dw     | product_dw   |
      | vendor_dw_2   | product_dw_2 |

    # Dropship warehouse — the key setup that triggers the dropship-warehouse flow
    And metasfresh contains M_Warehouse:
      | Identifier   | IsDropShipWarehouse |
      | warehouse_dw | Y                   |

    # Packing data used by every scenario in this feature — the per-project packing-material split.
    And metasfresh contains M_Products:
      | Identifier |
      | p_pm       |
      | p_pm2      |
    # Both order sides need a price: the vendor's HU-PI-Item-Product config (below) is also
    # assigned to the auto-created purchase order line (C_OrderLine model validator
    # add_M_HU_PI_Item_Product), which then gets its own packing-material PO line on complete
    # (HUOrderBL / OrderLinePackingMaterialDocumentLineSource) — the vendor purchase price list
    # needs a price for the packing product too, or PO completion fails with
    # ProductNotOnPriceListException.
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_dw_pm     | plv_dw_so              | p_pm         | 1.0      | PCE               | Normal                        |
      | pp_dw_pm2    | plv_dw_so              | p_pm2        | 1.0      | PCE               | Normal                        |
      | pp_dw_pm_po  | plv_dw_po              | p_pm         | 0.5      | PCE               | Normal                        |
      | pp_dw_pm2_po | plv_dw_po              | p_pm2        | 0.5      | PCE               | Normal                        |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_TU    |
      | huPI_TU2   |
    And metasfresh contains M_HU_PI_Version:
      | Identifier | M_HU_PI_ID | HU_UnitType |
      | huVersion  | huPI_TU    | TU          |
      | huVersion2 | huPI_TU2   | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_1                    | p_pm         |
      | pm_2                    | p_pm2        |
    And metasfresh contains M_HU_PI_Item:
      | Identifier | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem   | huVersion          | 10  | PM       | pm_1                    |
      | huPIItem2  | huVersion2         | 10  | PM       | pm_2                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_HU_PI_Item_ID | Qty | M_Product_ID | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsInvoiceable | OPT.M_Packing_Material_Product_ID |
      | hupip_A    | huPIItem        | 5   | product_dw   | 2024-01-01 | false                  | true              | p_pm                              |
      | hupip_B    | huPIItem        | 7   | product_dw_2 | 2024-01-01 | false                  | true              | p_pm                              |
      | hupip_B2   | huPIItem2       | 7   | product_dw_2 | 2024-01-01 | false                  | true              | p_pm2                             |

  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: dropship SO with two vendors' auto-created purchase orders ships in one shipment with one packing material line per project (2c)
    Given set project type Sales/Purchase Order to active
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_2c      | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_A      | so_2c      | product_dw   | 10         | vendor_dw            |
      | sol_B      | so_2c      | product_dw_2 | 21         | vendor_dw_2          |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_HU_PI_Item_Product_ID |
      | sol_A                     | hupip_A                     |
      | sol_B                     | hupip_B                     |
    When the order identified by so_2c is completed

    # Two POs created, one per vendor — the dropship-warehouse auto-PO mechanism (SPIKE-2c.md verdict a).
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | OPT.C_BPartner_ID | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_A           | so_2c                    | vendor_dw         | false   | POO         | CO            | true           |
      | po_B           | so_2c                    | vendor_dw_2       | false   | POO         | CO            | true           |

    # Drain the material queue so the PO→project→SO-line push-back has time to complete.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Each PO has its OWN project.
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | po_A       | projA        |
      | po_B       | projB        |

    # Each SO line carries the project of its corresponding PO, pushed back via C_PO_OrderLine_Alloc.
    And after not more than 30s, validate C_OrderLine:
      | C_OrderLine_ID | C_Project_ID |
      | sol_A          | projA        |
      | sol_B          | projB        |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID |
      | ss_A       | sol_A          |
      | ss_B       | sol_B          |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.C_Project_ID.Identifier |
      | ss_A                  | projA                       |
      | ss_B                  | projB                       |

    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_A                  |
      | ss_B                  |

    Then the shipment schedules ss_A,ss_B are shipped in exactly one M_InOut identified by shipment_2c

    # Same TU PI (huPI_TU) for both order lines → same packing material p_pm, split by project.
    And the packing material lines of shipment shipment_2c are exactly:
      | Identifier | M_Product_ID | C_Project_ID | MovementQty |
      | pm_A       | p_pm         | projA        | 2           |
      | pm_B       | p_pm         | projB        | 3           |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.M_InOutLine_ID.Identifier |
      | ic_pm_A                           | null                      | pm_A                          |
      | ic_pm_B                           | null                      | pm_B                          |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | OPT.M_Product_ID | C_Project_ID |
      | ic_pm_A                | p_pm             | projA        |
      | ic_pm_B                | p_pm             | projB        |

    And process invoice candidates together and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic_pm_A                |
      | ic_pm_B                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_2c   | ic_pm_A                |
      | invoice_2c   | ic_pm_B                |

    And validate created invoice lines
      | C_InvoiceLine_ID | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | OPT.C_Project_ID.Identifier |
      | il_pm_A          | invoice_2c   | p_pm         | 2           | pm_A           | projA                       |
      | il_pm_B          | invoice_2c   | p_pm         | 3           | pm_B           | projB                       |

    And set project type Sales/Purchase Order to inactive

# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario Outline: dropship SO with Positions Nr. set directly on the order lines ships in one shipment with one packing material line per project (<case>)
    # Project type stays inactive (Background default): the auto-created purchase order(s) get no
    # project of their own, so nothing can overwrite the Positions Nr. set directly below.
    Given metasfresh contains C_Projects:
      | Identifier |
      | P1         |
      | P2         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_direct  | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | C_BPartner_Vendor_ID | M_HU_PI_Item_Product_ID |
      | sol_A      | so_direct  | product_dw   | 10         | P1           | vendor_dw            | hupip_A                 |
      | sol_B      | so_direct  | product_dw_2 | 21         | P2           | <vendor_B>           | <pip_B>                 |
    When the order identified by so_direct is completed

    # Let the dropship auto-PO creation settle before the shipment is generated.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID |
      | ss_A       | sol_A          |
      | ss_B       | sol_B          |

    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_A                  |
      | ss_B                  |

    Then the shipment schedules ss_A,ss_B are shipped in exactly one M_InOut identified by shipment_direct

    And the packing material lines of shipment shipment_direct are exactly:
      | Identifier | M_Product_ID | C_Project_ID | MovementQty |
      | pm_line_P1 | p_pm         | P1           | 2           |
      | pm_line_P2 | <pm_B>       | P2           | 3           |

    Examples:
      | case | vendor_B    | pip_B    | pm_B  |
      | 2a   | vendor_dw   | hupip_B2 | p_pm2 |
      | 2b   | vendor_dw   | hupip_B  | p_pm  |
      | 2e   | vendor_dw_2 | hupip_B2 | p_pm2 |

# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @allure.label.epic:E0240_Project_Management
  @allure.label.feature:F68020
  Scenario: dropship SO with Positions Nr. set directly on the order lines ships in two shipments, one packing material line per project each (2d)
    # Same setup as case 2b, except the two order lines go to two different vendors: two purchase
    # orders, same packing instruction. Project type stays inactive; the Positions Nr. is set
    # directly on the order lines, as case 2c's PO-to-SO propagation would leave them.
    Given metasfresh contains C_Projects:
      | Identifier |
      | P1         |
      | P2         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_2d      | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_Project_ID | C_BPartner_Vendor_ID | M_HU_PI_Item_Product_ID |
      | sol_A      | so_2d      | product_dw   | 10         | P1           | vendor_dw            | hupip_A                 |
      | sol_B      | so_2d      | product_dw_2 | 21         | P2           | vendor_dw_2          | hupip_B                 |
    When the order identified by so_2d is completed

    # Let the dropship auto-PO creation settle before the shipments are generated.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID |
      | ss_A       | sol_A          |
      | ss_B       | sol_B          |

    And temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'

    # Each purchase order's schedule is shipped as its own delivery run - two shipments, on purpose.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_A                  |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_A                  | shipment_2d_1 |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_B                  |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | ss_B                  | shipment_2d_2 | shipment_2d_1                           |

    And the packing material lines of shipment shipment_2d_1 are exactly:
      | Identifier | M_Product_ID | C_Project_ID | MovementQty |
      | pm_line_P1 | p_pm         | P1           | 2           |
    And the packing material lines of shipment shipment_2d_2 are exactly:
      | Identifier | M_Product_ID | C_Project_ID | MovementQty |
      | pm_line_P2 | p_pm         | P2           | 3           |
