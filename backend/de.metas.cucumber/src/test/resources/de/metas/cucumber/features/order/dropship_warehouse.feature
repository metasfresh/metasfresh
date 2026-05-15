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
      | Identifier | Name             | DiscountType | ValidFrom  |
      | ds_dw      | ds_dropship_wh   | F            | 2024-01-01 |
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
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_dw  | N        | Y          | ps_dw              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_dw_loc   | customer_dw   | Y               | Y               |

    # Vendor bpartner (the one who will fulfill the purchase)
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_dw   | Y        | N          | ps_dw              | ds_dw                |
      | vendor_dw_2 | Y        | N          | ps_dw              | ds_dw                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_dw_loc     | vendor_dw     | Y               | Y               |
      | vendor_dw_2_loc   | vendor_dw_2   | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_dw     | product_dw   |
      | vendor_dw_2   | product_dw_2 |

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
  Scenario: Single-vendor dropship SO — auto-generated PO carries its own project, pushed back to SO lines
    # Design contract for the single-vendor case: the PO's own BEFORE_COMPLETE
    # (C_Order_Project.populateProjectIfNeeded) creates one project for the PO, stamps it on the
    # PO header + PO lines, then PurchaseOrderProjectListener
    # (UpdateSalesOrderFromPurchaseOrderProjectListener) propagates that project back to the
    # corresponding SO line(s) via C_PO_OrderLine_Alloc.
    #
    # The SO-side C_Order_Project interceptor explicitly skips sales orders, so the SO header
    # stays NULL — the project lives on the PO and is pushed back to the SO LINES only.
    #
    # Project-type activation is scoped to this scenario (rather than the Background) so it
    # doesn't affect the MD_Candidate counts in Scenarios 2 and 3.
    Given set project type Sales/Purchase Order to active
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw4     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw4_1  | so_dw4     | product_dw   | 10         | vendor_dw            |
      | sol_dw4_2  | so_dw4     | product_dw   | 5          | vendor_dw            |
    When the order identified by so_dw4 is completed

    # Verify the PO was created and register it as `po_dw4` for downstream assertions
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_dw4         | so_dw4                   | false   | POO         | CO            | true           |

    # Drain the material queue first so async work has a chance to complete.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Register the PO's project as `proj1` (created by the PO's own BEFORE_COMPLETE).
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | po_dw4     | proj1        |

    # CORE assertion of the new design: the SO lines receive the PO's project via the
    # PurchaseOrderProjectListener pushback (using C_PO_OrderLine_Alloc lookup for dropship POs,
    # which don't have C_PurchaseCandidate rows). Polling because the listener dispatch is
    # async (localAndAsync event bus, after-commit).
    And after not more than 30s, validate C_OrderLine:
      | C_OrderLine_ID | C_Project_ID |
      | sol_dw4_1      | proj1        |
      | sol_dw4_2      | proj1        |

    # SO header stays NULL: the SO-side C_Order_Project interceptor early-returns on isSOTrx()
    # and the listener only pushes the project to the SO LINES — never the SO header. This is
    # intentional and accepted (see refactor notes); the project lives on the PO and the SO line
    # is the link surface for downstream consumers.
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | so_dw4     | -            |

    # cleanup: deactivate project type so subsequent scenarios are not affected
    And set project type Sales/Purchase Order to inactive

  @from:cucumber
  Scenario: SO on a dropship warehouse with a line that has no vendor AND no default-vendor mapping fails completion
    # Regression guard for C_Order_DropshipPO.validateVendorsBeforeComplete (TIMING_BEFORE_COMPLETE).
    # When an SO line on a dropship warehouse has neither an explicit C_BPartner_Vendor_ID nor a
    # matching C_BPartner_Product canonical mapping, completion must FAIL with a user-validation
    # error (AdMessageKey 'DropshipWarehouse_MissingVendorOnLine') listing the offending line number.
    # The error is caught by the "cannot be completed" step which asserts an exception was thrown
    # (the existing step only verifies that an exception is raised — it does not currently expose
    # the exception's AD_Message, but raising any exception during BEFORE_COMPLETE guarantees that
    # the SO did not progress to DocStatus=CO and that the dropship PO was not created downstream).
    #
    # Setup: a new product `product_no_vendor` with NO C_BPartner_Product mapping. The Background's
    # `vendor_dw` is a vendor with PO_DiscountSchema_ID set, but is not mapped to `product_no_vendor`,
    # so VendorProductInfoService.getDefaultVendorProductInfo() returns empty for this product.
    Given metasfresh contains M_Products:
      | Identifier        | M_Product_Category_ID | IsSold | IsPurchased |
      | product_no_vendor | standard_category     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_nv_so     | plv_dw_so              | product_no_vendor | 20.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw5     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    # Note: NO C_BPartner_Vendor_ID column on the line — and product_no_vendor has no canonical mapping
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID      | QtyEntered |
      | sol_dw5_1  | so_dw5     | product_no_vendor | 10         |
    # Assert: completion is blocked (an exception is thrown by the BEFORE_COMPLETE interceptor)
    Then the order identified by so_dw5 cannot be completed

  @from:cucumber
  Scenario: SO on a dropship warehouse with one line missing a vendor — vendor is auto-filled from C_BPartner_Product
    # Regression guard for C_Order_DropshipPO.validateVendorsBeforeComplete (TIMING_BEFORE_COMPLETE)
    # auto-fill path. When an SO line on a dropship warehouse has no explicit C_BPartner_Vendor_ID
    # but the canonical lookup (C_BPartner_Product) yields a default vendor, the interceptor must
    # set C_BPartner_Vendor_ID on the line and persist it before the SO completes. Downstream this
    # means both lines share the same vendor, so the dropship-PO aggregator produces a SINGLE PO.
    #
    # Setup: the Background already wires C_BPartner_Product(vendor_dw, product_dw). Line 1 carries
    # an explicit vendor (the no-op path of validateVendorsBeforeComplete); line 2 carries no vendor
    # (the auto-fill path).
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw6     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw6_1  | so_dw6     | product_dw   | 7          | vendor_dw            |
      | sol_dw6_2  | so_dw6     | product_dw   | 4          |                      |
    When the order identified by so_dw6 is completed

    # Assert: line 2's vendor was auto-filled to vendor_dw by the interceptor
    Then validate C_OrderLine:
      | C_OrderLine_ID | C_BPartner_Vendor_ID |
      | sol_dw6_1      | vendor_dw            |
      | sol_dw6_2      | vendor_dw            |

    # Assert: a single dropship PO was created for the SO, with both lines aggregated under vendor_dw
    And the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_dw6         | so_dw6                   | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | po_dw6     | vendor_dw     |

  @from:cucumber
  Scenario: SO on a regular (non-dropship) warehouse still creates SUPPLY/PURCHASE MD_Candidate rows
    # Negative regression guard for the dropship-warehouse material-disposition bypass: the bypass
    # must ONLY fire when IsDropShipWarehouse=Y. On a regular warehouse the standard chain
    # (ShipmentSchedule → SupplyRequiredEvent → PurchaseCandidateAdvisedEvent → C_PurchaseCandidate
    # → auto-generated PO → ReceiptSchedule → MD_Candidate SUPPLY) must still run end-to-end.
    #
    # Setup: same products / vendor / pricing as Background, but with a fresh warehouse whose
    # IsDropShipWarehouse=N. The PP_Product_Planning (`ppln_dw`) from Background already has
    # IsCreatePlan=Y / IsPurchased=Y / IsDocComplete=Y, which is what drives the auto-PO chain.
    Given metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | warehouse_regular | N                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID    |
      | so_dw9     | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_regular |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw9_1  | so_dw9     | product_dw   | 10         | vendor_dw            |
    When the order identified by so_dw9 is completed

    # Drain the event queue so the full SupplyRequired → PurchaseCandidate → PO → ReceiptSchedule
    # → MD_Candidate chain has time to run
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Assert: at least one SUPPLY/PURCHASE MD_Candidate row exists for product_dw — confirming
    # that the bypass did NOT short-circuit the standard material-disposition flow on a regular warehouse
    Then MD_Candidates exist for M_Product_ID product_dw:
      | MD_Candidate_Type | MD_Candidate_BusinessCase |
      | SUPPLY            | PURCHASE                  |

  @from:cucumber
  Scenario: Multi-vendor dropship SO — each vendor's PO carries its OWN project, pushed back to the matching SO line
    # Design contract for the multi-vendor case (the case the per-PO project model was designed for):
    # N vendors on one dropship SO produce N POs (one per vendor); each PO's own BEFORE_COMPLETE
    # creates its own project; the listener pushes each PO's project back to the SO lines that
    # belong to that PO via C_PO_OrderLine_Alloc. Different SO lines therefore carry different
    # projects — and the SO header stays NULL (no shared project across vendors).
    Given set project type Sales/Purchase Order to active
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw10    | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw10_A | so_dw10    | product_dw   | 10         | vendor_dw            |
      | sol_dw10_B | so_dw10    | product_dw_2 | 7          | vendor_dw_2          |
    When the order identified by so_dw10 is completed

    # Two POs created, one per vendor. Register each via the OPT.C_BPartner_ID disambiguation column.
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | OPT.C_BPartner_ID | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_dw10_A      | so_dw10                  | vendor_dw         | false   | POO         | CO            | true           |
      | po_dw10_B      | so_dw10                  | vendor_dw_2       | false   | POO         | CO            | true           |

    # Drain the material queue first so async work has a chance to complete.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Each PO has its OWN project — register them as projA and projB.
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | po_dw10_A  | projA        |
      | po_dw10_B  | projB        |

    # CORE assertion: each SO line carries the project of its corresponding PO, pushed back by
    # PurchaseOrderProjectListener via C_PO_OrderLine_Alloc lookup.
    And after not more than 30s, validate C_OrderLine:
      | C_OrderLine_ID | C_Project_ID |
      | sol_dw10_A     | projA        |
      | sol_dw10_B     | projB        |

    # SO header stays NULL — multiple projects on lines, no single promotion possible.
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | so_dw10    | -            |

    # cleanup
    And set project type Sales/Purchase Order to inactive

  @from:cucumber
  Scenario: Dropship warehouse — receipt processing creates no MD_Candidate even after m_transaction
    # Regression guard for the transaction-event short-circuit path (T1–T5 in PR #23918).
    # When a dropship PO receipt is completed, the HU receipt generates m_transaction rows that
    # trigger TransactionCreatedEvent. Because TransactionCreatedEvent carries isDropShipWarehouse=true,
    # the cockpit handler must short-circuit and produce zero MD_Candidate rows — even though
    # m_transaction rows DO exist for the receipt.
    #
    # Existing scenarios 2, 3, and 9 verify the SO/PO-completion path (SupplyRequired → short-circuit).
    # This scenario covers the later receipt path: SO → auto-PO (short-circuit already) → PO receipt
    # (TransactionCreatedEvent → short-circuit) → still zero MD_Candidate.
    #
    # Single SO line keeps the auto-PO to exactly one PO line, simplifying receipt-schedule lookup.
    # product_dw_2 / vendor_dw_2 are used here (rather than product_dw / vendor_dw) because
    # Scenario 9 creates MD_Candidate SUPPLY/PURCHASE rows for product_dw on warehouse_regular,
    # and those rows persist across scenarios. Using product_dw_2 keeps the final
    # "no MD_Candidate" assertion free of cross-scenario pollution.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | so_dw11    | true    | customer_dw   | 2024-06-17  | 2024-06-16T22:00:00Z | warehouse_dw   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID |
      | sol_dw11_1 | so_dw11    | product_dw_2 | 10         | vendor_dw_2          |
    When the order identified by so_dw11 is completed

    # The auto-PO is created synchronously in the same transaction as SO completion.
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po_dw11        | so_dw11                  | false   | POO         | CO            | true           |

    # Load the auto-created PO line so we can look up its receipt schedule below.
    And load C_OrderLines from C_Order:
      | C_Order_ID | C_OrderLine_ID | OPT.M_Product_ID |
      | po_dw11    | pol_dw11_1     | product_dw_2     |

    # Drain the material queue so any ReceiptScheduleCreatedEvent short-circuit has run.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # The receipt schedule was created by PO completion; locate and register it.
    # Note: for a dropship PO the delivery-location on the receipt schedule comes from the SO's
    # delivery location (customer_dw_loc), not from the vendor's own location — this is the
    # standard dropship behaviour (goods delivered directly to the customer).
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs_dw11                         | po_dw11               | pol_dw11_1                | vendor_dw_2              | customer_dw_loc                   | product_dw_2            | 10         | warehouse_dw              |

    # Generate a planning HU (No Packing Item = ID 101) and create the material receipt.
    # Completing the receipt fires TransactionCreatedEvent — the path under test.
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig_dw11                      | hu_dw11            | rs_dw11                         | N               | 0     | N               | 1     | N               | 10          | 101                                |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_dw11            | rs_dw11                         | receipt_dw11          |

    # Drain the event queue so any TransactionCreatedEvent processing has a chance to run.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # CORE assertion: even after the receipt fires m_transaction rows and TransactionCreatedEvent,
    # zero non-STOCK MD_Candidate rows must exist because isDropShipWarehouse=true triggers the
    # short-circuit in the cockpit TransactionCreatedHandler.
    Then no MD_Candidate exists for M_Product_ID product_dw_2
