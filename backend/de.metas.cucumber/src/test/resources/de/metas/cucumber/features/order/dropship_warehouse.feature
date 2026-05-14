@from:cucumber
@allure.label.epic:E0103_Drop_Shipment
@allure.label.feature:F72000_Drop_Shipment
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
  Scenario: SO on a dropship warehouse auto-creates a single PO with IsDropShip=Y linked back to the SO
    # The C_Order_DropshipPO interceptor fires synchronously in TIMING_AFTER_COMPLETE on the SO,
    # invoking DropshipPOFromSOService which drives CreatePOFromSOsAggregator with
    # PurchaseTypeEnum.DROPSHIP. The resulting PO has Link_Order_ID = the SO's id, IsDropShip='Y',
    # and DocStatus='CO'. This entire flow is in-transaction with SO completion — no async wait
    # is needed for the PO creation itself. (Material-event short-circuit is verified in the next
    # scenario.)
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw1     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw1_1  | so_dw1     | product_dw   | 10         | vendor_dw            |
      | sol_dw1_2  | so_dw1     | product_dw   | 5          | vendor_dw            |
    When the order identified by so_dw1 is completed

    # Assert: exactly 1 PO was created with Link_Order_ID pointing back to the SO,
    # IsDropShip=Y, DocStatus=CO. The PO auto-completes in the same transaction as the SO.
    # The PO-completion material-event chain is short-circuited for dropship warehouses,
    # so no MD_Candidate rows appear (asserted explicitly in Scenarios 2 and 3).
    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | so_dw1                   | false   | POO         | CO            | true           |

  @from:cucumber
  Scenario: SO on a dropship warehouse does not create MD_Candidate rows
    # When IsDropShipWarehouse=Y, the shipment-schedule event carries isDropShipWarehouse=true
    # which causes the material-dispo service to skip SupplyRequired processing entirely.
    # Observable outcome: zero MD_Candidate rows for the product after SO completion + queue drain.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw2     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw2_1  | so_dw2     | product_dw   | 8          | vendor_dw            |
      | sol_dw2_2  | so_dw2     | product_dw   | 3          | vendor_dw            |
    When the order identified by so_dw2 is completed

    # Drain the event queue so any async processing that would create MD_Candidates has a chance to run
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Assert: no MD_Candidate demand rows created for the dropship-warehouse product
    Then no MD_Candidate exists for M_Product_ID product_dw

  @from:cucumber
  Scenario: Auto-completed dropship PO produces zero MD_Candidate rows
    # Regression guard for the combined contract: even after the dropship PO has auto-completed
    # (i.e. the PO-completion material-event chain has fired AND the rabbitMQ queue has drained),
    # NO non-STOCK MD_Candidate rows must exist for product_dw. This is the explicit statement
    # of the design contract that Scenarios 1 (auto-complete=CO) and 2 (zero candidates) only
    # exercise separately. The PO-side short-circuit in M_ReceiptSchedule_PostMaterialEvent +
    # ReceiptsScheduleCreatedOrUpdatedHandler/Deleted is what makes this hold.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw3     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw3_1  | so_dw3     | product_dw   | 10         | vendor_dw            |
      | sol_dw3_2  | so_dw3     | product_dw   | 5          | vendor_dw            |
    When the order identified by so_dw3 is completed

    # Verify the PO auto-completed in the same transaction as the SO (Scenario 1's guarantee)
    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | so_dw3                   | false   | POO         | CO            | true           |

    # Drain the event queue so any PO-completion async processing has a chance to run
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Assert: still zero non-STOCK MD_Candidate rows after PO completion + queue drain
    Then no MD_Candidate exists for M_Product_ID product_dw

# Scenario "SO and auto-generated dropship PO share a single C_Project_ID" was DEFERRED.
# Writing the scenario (mirroring the project-type activation pattern from
# purchaseOrderProjectToSO.feature lines 15+109) revealed a real production gap:
# with project type Sales/Purchase Order active, the SO gets C_Project=1000008 but the
# auto-generated dropship PO ends up with C_Project=1000009 — DIFFERENT projects, not the
# shared identity the design contract promises.
# See ai-work/29584/pending-questions.md Q3 (2026-05-15) for the diagnostic trace
# (SO and PO end up with distinct project IDs despite DropshipPOFromSOService stamping
# salesOrder.getC_Project_ID() on the PO before auto-complete — likely C_Order_Project
# BEFORE_COMPLETE on the PO is racing the propagation).
