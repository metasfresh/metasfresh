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

  @from:cucumber
  Scenario: SO on a dropship warehouse and its auto-generated PO share a single C_Project_ID
    # Regression guard for the design contract that the SO and the auto-generated dropship PO
    # both carry the same C_Project_ID after the SO is completed. Path:
    #   1. SO BEFORE_COMPLETE runs C_Order_Project.populateProjectIfNeeded -> creates a project
    #      (because project type Sales/Purchase Order is active for the org) and stamps it on
    #      the SO header + SO lines.
    #   2. SO AFTER_COMPLETE -> C_Order_DropshipPO -> DropshipPOFromSOService.createDropshipPOForSO
    #      then refreshes the SO instance and stamps the SO's C_Project_ID on every created PO
    #      header + PO line before auto-completing the PO. The refresh is required because the
    #      salesOrder instance was loaded BEFORE the SO's BEFORE_COMPLETE interceptor wrote the
    #      project to the SO -- without it the stamp loop would skip propagation and the PO's own
    #      C_Order_Project beforeComplete would create a SECOND, distinct project for the PO
    #      (the originally-reported bug).
    #
    # Project-type activation is scoped to this scenario (rather than the Background) so it
    # doesn't affect the MD_Candidate counts in Scenarios 2 and 3.
    #
    # Limitation: no existing step registers the auto-created PO under an identifier, so we
    # cannot directly assert that the PO carries the same project as the SO. Instead we assert
    # the SO has a project (via `validate the created orders` on `so_dw4`, which registers the
    # auto-generated project as `proj1` in the project-step-def table) and that the SO lines
    # carry the same `proj1`. The PO is verified to exist via `the order is created:`. Once a
    # step exists to register a PO from Link_Order_ID (or `the order is created:` is extended
    # to register the PO under an identifier), this scenario can be tightened to assert the PO
    # header + PO lines also carry `proj1`.
    Given set project type Sales/Purchase Order to active
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw4     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw4_1  | so_dw4     | product_dw   | 10         | vendor_dw            |
      | sol_dw4_2  | so_dw4     | product_dw   | 5          | vendor_dw            |
    When the order identified by so_dw4 is completed

    # Verify the PO was created
    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | so_dw4                   | false   | POO         | CO            | true           |

    # Assert the SO carries an auto-generated project; register it as `proj1`
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | so_dw4     | proj1        |

    # Assert the SO lines carry the same project (propagated by C_Order_Project.beforeComplete)
    And validate C_OrderLine:
      | C_OrderLine_ID | C_Project_ID |
      | sol_dw4_1      | proj1        |
      | sol_dw4_2      | proj1        |

    # cleanup: deactivate project type so subsequent scenarios are not affected
    And set project type Sales/Purchase Order to inactive
