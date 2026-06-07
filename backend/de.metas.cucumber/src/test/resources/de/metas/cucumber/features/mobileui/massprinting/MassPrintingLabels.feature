@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: Mass Printing Labels (F00230.21)
  As a warehouse operator in mass-printing mode
  I want to scan an LU and have the system automatically pack all self-packed products
  So that I get one box and one label per unit without manual order-by-order picking

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-07T08:00:00+02:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    # ── Picking profile with mass-printing ON and "don't create" shipment policy ──
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | IsMassPrinting | CreateShipmentPolicy |
      | Y                   | Y              | DO_NOT_CREATE        |

    # ── Dedicated mass-printing picker user ──
    # The workplace + picking-slot assignment is bound to this user, NOT the shared 'metasfresh'
    # picker. The cucumber executor commits data and runs all features sequentially in one JVM, so a
    # C_Workplace_User_Assign on the shared 'metasfresh' user would leak into later scenarios (e.g.
    # picking workflows) and resolve a no-longer-valid workplace. Isolating it on this dedicated user
    # keeps the leak contained to this feature's own picker.
    And metasfresh contains AD_Users:
      | Identifier         | Name               | OPT.Login          | OPT.Role_ID |
      | massPrintingPicker | massPrintingPicker | massPrintingPicker | 540024      |

    # ── Workplace with a picking slot, assigned to the dedicated picker (mirrors the operator being
    #    logged in at a Mass-Printing workplace; the programmatic PRODUCT job auto-allocates this slot) ──
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouse      | StdWarehouse |
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot | IsDynamic |
      | pickingSlot | 200         | Y         |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | M_PickingSlot_ID |
      | workplace  | warehouse      | pickingSlot      |
    And assign C_Workplace to user
      | C_Workplace_ID | AD_User_ID.Login   |
      | workplace      | massPrintingPicker |

    # ── Packing instructions: a self-packed product box PI (1 CU per TU = 1 unit = 1 box) ──
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | boxPI      |
      | luPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name  | HU_UnitType | IsCurrent |
      | boxPI_v                       | boxPI                 | boxPI | TU          | Y         |
      | luPI_v                        | luPI                  | luPI  | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | boxPI_mi                   | boxPI_v                       | 0   | MI       |                                  |
      | luPI_hu                    | luPI_v                        | 10  | HU       | boxPI                            |

    # ── Self-packed product: 1 unit per box ──
    And metasfresh contains M_Products:
      | Identifier    | X12DE355 | OPT.IsSelfPacked |
      | selfPackedPrd | PCE      | Y                |

    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | boxPI_x1                           | boxPI_mi                   | selfPackedPrd           | 1   | 2000-01-01 |

    # ── Pricing ──
    And metasfresh contains M_PricingSystems
      | Identifier |
      | PS         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | PL         | PS                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | PLV        | PL             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | selfPackedPrd | 10.0     | PCE               | Nominal              | Normal                        |

    # ── Customer ──
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | 1234500000001 | customer                 | true                | true         |

    # ── Physical stock: create an LU with 3 units of selfPackedPrd ──
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-06-01   | warehouse      |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | invLine                       | selfPackedPrd           | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLine            | stockCU |

    And transform CU to new LU
      | sourceCU | newLU | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | stockCU  | lu    | boxPI    | 1           | 3           |
    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | lu      | A        |

    # ── HU label config: one TU label per box, Picking source-doc type ──
    # SeqNo=10; process M_HU_Report_Print_Labels (prints HU labels from T_Selection).
    # One print call is issued per packed box. Whether the render succeeds depends on the environment
    # (Jasper is available in CI but not in a local run), so scenarios assert labelPrintAttempts
    # (= labelsPrinted + labelPrintFailures), which equals the box count in every environment.
    And metasfresh contains M_HU_Label_Config:
      | HU_SourceDocType | LabelReport_Process_ID.Value | SeqNo | OPT.IsApplyToTUs |
      | PI               | M_HU_Report_Print_Labels     | 10    | Y                |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: Scan LU with self-packed product and open demand — FIFO allocation and one box per unit
    # Three single-unit orders => 3 open shipment schedules for selfPackedPrd
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO1        | true    | customer                 | 2026-06-01  | 2026-06-02          |
      | SO2        | true    | customer                 | 2026-06-01  | 2026-06-03          |
      | SO3        | true    | customer                 | 2026-06-01  | 2026-06-04          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO1                   | OL1        | selfPackedPrd           | 1          |
      | SO2                   | OL2        | selfPackedPrd           | 1          |
      | SO3                   | OL3        | selfPackedPrd           | 1          |
    And the order identified by SO1 is completed
    And the order identified by SO2 is completed
    And the order identified by SO3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS1        | OL1                       | N             |
      | SS2        | OL2                       | N             |
      | SS3        | OL3                       | N             |

    # Invoke mass-printing — LU has 3 units, 3 single-unit orders → 3 boxes packed, nothing leftover
    When mass-printing scans LU
      | LU | Picker             |
      | lu | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU | OPT.unitsOfOpenDemandRemaining |
      | 3           | 3                      | 0                 | 0                              |
    # Each packed unit produces its own leaf product-holding HU carrying exactly 1 unit
    And mass-printing produced box HUs
      | boxHUCount | qtyPerBoxHU |
      | 3          | 1           |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: FIFO partial fill — last order partially filled when LU capacity is exhausted
    # Two orders each demanding 2 units, but LU has only 3 units (not 4).
    # Expected: SO1 fully filled (2 boxes), SO2 partially filled (1 box), 1 unit of SO2 demand stays open.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO_A       | true    | customer                 | 2026-06-01  | 2026-06-02          |
      | SO_B       | true    | customer                 | 2026-06-01  | 2026-06-03          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO_A                  | OL_A       | selfPackedPrd           | 2          |
      | SO_B                  | OL_B       | selfPackedPrd           | 2          |
    And the order identified by SO_A is completed
    And the order identified by SO_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS_A       | OL_A                      | N             |
      | SS_B       | OL_B                      | N             |

    # LU has 3 units; SO_A needs 2 (fully filled), SO_B needs 2 but only 1 unit remains (partially filled).
    When mass-printing scans LU
      | LU | Picker             |
      | lu | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU | OPT.unitsOfOpenDemandRemaining |
      | 3           | 3                      | 0                 | 1                              |
    # 3 box HUs: 2 for SO_A + 1 for SO_B; each holds exactly 1 unit
    And mass-printing produced box HUs
      | boxHUCount | qtyPerBoxHU |
      | 3          | 1           |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: Shipment policy CREATE_DRAFT — scan creates a draft shipment per packed order
    # Background sets policy to DO_NOT_CREATE; override it here to CREATE_DRAFT.
    And set mobile UI picking profile
      | CreateShipmentPolicy |
      | CREATE_DRAFT         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO         | true    | customer                 | 2026-06-01  | 2026-06-02          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | OL         | selfPackedPrd           | 1          |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS         | OL                        | N             |

    When mass-printing scans LU
      | LU | Picker             |
      | lu | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU |
      | 1           | 1                      | 2                 |
    And mass-printing produced box HUs
      | boxHUCount | qtyPerBoxHU |
      | 1          | 1           |

    # Shipment generated in draft — async WP processor creates it; poll until it appears.
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | SS                               | shipment              |
    And validate M_In_Out status
      | M_InOut_ID | DocStatus |
      | shipment   | DR        |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: Shipment policy CREATE_AND_COMPLETE — scan creates and completes a shipment per packed order
    # Background sets policy to DO_NOT_CREATE; override it here to CREATE_AND_COMPLETE.
    And set mobile UI picking profile
      | CreateShipmentPolicy |
      | CREATE_AND_COMPLETE  |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO         | true    | customer                 | 2026-06-01  | 2026-06-02          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | OL         | selfPackedPrd           | 1          |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS         | OL                        | N             |

    When mass-printing scans LU
      | LU | Picker             |
      | lu | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU |
      | 1           | 1                      | 2                 |
    And mass-printing produced box HUs
      | boxHUCount | qtyPerBoxHU |
      | 1          | 1           |

    # Shipment generated and completed — poll until it appears in CO status.
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | SS                               | shipment              |
    And validate M_In_Out status
      | M_InOut_ID | DocStatus |
      | shipment   | CO        |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: Shipment policy CREATE_COMPLETE_CLOSE — scan creates a completed shipment and closes the schedule
    # Background sets policy to DO_NOT_CREATE; override it here to CREATE_COMPLETE_CLOSE.
    And set mobile UI picking profile
      | CreateShipmentPolicy    |
      | CREATE_COMPLETE_CLOSE   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO         | true    | customer                 | 2026-06-01  | 2026-06-02          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | OL         | selfPackedPrd           | 1          |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS         | OL                        | N             |

    When mass-printing scans LU
      | LU | Picker             |
      | lu | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU |
      | 1           | 1                      | 2                 |
    And mass-printing produced box HUs
      | boxHUCount | qtyPerBoxHU |
      | 1          | 1           |

    # Shipment generated and completed; schedule also closed.
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | SS                               | shipment              |
    And validate M_In_Out status
      | M_InOut_ID | DocStatus |
      | shipment   | CO        |
    And the shipment-schedule is closed
      | M_ShipmentSchedule_ID.Identifier |
      | SS                               |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: Mixed-customer scan — each customer's shipment follows its own policy within one scan
    # Two customers with different policies on one scan.
    # customer (from Background) → keeps default DO_NOT_CREATE (no shipment).
    # customer2 → CREATE_DRAFT (shipment in draft).
    # LU has 3 units: 1 unit goes to customer, 1 unit goes to customer2, 1 unit leftover.

    # Set up second customer with its own pricing.
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer2  | customer2 | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customer2Location | 9876500000001 | customer2                | true                | true         |

    # Per-customer policy: customer2 → CREATE_DRAFT; customer keeps profile default (DO_NOT_CREATE).
    And set per-customer mobile UI shipment policy:
      | C_BPartner_ID | CreateShipmentPolicy |
      | customer2     | CREATE_DRAFT         |

    # One single-unit order per customer (FIFO: customer gets prep date 2026-06-02, customer2 gets 2026-06-03).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO_c1      | true    | customer                 | 2026-06-01  | 2026-06-02          |
      | SO_c2      | true    | customer2                | 2026-06-01  | 2026-06-03          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO_c1                 | OL_c1      | selfPackedPrd           | 1          |
      | SO_c2                 | OL_c2      | selfPackedPrd           | 1          |
    And the order identified by SO_c1 is completed
    And the order identified by SO_c2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS_c1      | OL_c1                     | N             |
      | SS_c2      | OL_c2                     | N             |

    # Scan: 2 units packed (1 per customer), 1 unit leftover on LU.
    When mass-printing scans LU
      | LU | Picker             |
      | lu | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU |
      | 2           | 2                      | 1                 |

    # customer (DO_NOT_CREATE): no M_InOut created for its order.
    And validate no M_InOut found for C_Order identified by SO_c1

    # customer2 (CREATE_DRAFT): shipment created in draft.
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | SS_c2                            | shipment_c2           |
    And validate M_In_Out status
      | M_InOut_ID  | DocStatus |
      | shipment_c2 | DR        |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: Multi-product LU — self-packed product packed, non-self-packed product skipped
    # The scanned LU holds two products: one IsSelfPacked=Y and one IsSelfPacked=N.
    # Only the self-packed product must be packed (1 box); the non-self-packed product must appear
    # in the skipped list and not be picked.

    # ── Non-self-packed product: no IsSelfPacked flag (defaults to N) ──
    And metasfresh contains M_Products:
      | Identifier       | X12DE355 |
      | nonSelfPackedPrd | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | boxPI_x2                           | boxPI_mi                   | nonSelfPackedPrd        | 1   | 2000-01-01 |

    # Stock the non-self-packed product (2 units so the transform has enough to work with)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | invNonSelf                | 2026-06-01   | warehouse      |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | invNonSelf                | invLineNonSelf                | nonSelfPackedPrd        | 0       | 2        | PCE          |
    And complete inventory with inventoryIdentifier 'invNonSelf'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID           |
      | invLineNonSelf     | nonSelfPackedStock |

    # Create one TU from each product, then aggregate them onto a single mixed LU
    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | stockCU             | 1     | boxPI_x1                           | selfPackedTU                  |
    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier |
      | nonSelfPackedStock  | 1     | boxPI_x2                           | nonSelfPackedTU               |
    And aggregate TUs to new LU
      | sourceTUs                    | newLUs  |
      | selfPackedTU,nonSelfPackedTU | mixedLU |
    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | mixedLU | A        |

    # One single-unit order for the self-packed product — provides the open demand that triggers packing
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO_mix     | true    | customer                 | 2026-06-01  | 2026-06-02          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO_mix                | OL_mix     | selfPackedPrd           | 1          |
    And the order identified by SO_mix is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS_mix     | OL_mix                    | N             |

    # Scan: selfPackedPrd → 1 box packed; nonSelfPackedPrd → skipped
    When mass-printing scans LU
      | LU      | Picker             |
      | mixedLU | massPrintingPicker |
    Then mass-printing result is
      | boxesPacked | OPT.labelPrintAttempts | OPT.unitsLeftOnLU | OPT.unitsOfOpenDemandRemaining |
      | 1           | 1                      | 0                 | 0                              |
    And mass-printing produced box HUs
      | boxHUCount | qtyPerBoxHU |
      | 1          | 1           |
    And mass-printing skipped non-self-packed products:
      | skippedProduct   |
      | nonSelfPackedPrd |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: LU holds only a non-self-packed product — nothing packed, product reported as skipped
    # The scanned LU holds only a product with IsSelfPacked=N.
    # The result must have no packed boxes and must list the product as skipped,
    # providing informative feedback to the operator (non-self-packed → skipped).

    # ── Non-self-packed product ──
    And metasfresh contains M_Products:
      | Identifier           | X12DE355 |
      | nonSelfPackedPrdOnly | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier  | Qty | ValidFrom  |
      | boxPI_x3                           | boxPI_mi                   | nonSelfPackedPrdOnly     | 1   | 2000-01-01 |

    # Stock it and place it on its own LU
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | invOnlyNonSelf            | 2026-06-01   | warehouse      |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier  | QtyBook | QtyCount | UOM.X12DE355 |
      | invOnlyNonSelf            | invLineOnlyNonSelf            | nonSelfPackedPrdOnly     | 0       | 2        | PCE          |
    And complete inventory with inventoryIdentifier 'invOnlyNonSelf'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID    | M_HU_ID             |
      | invLineOnlyNonSelf    | nonSelfPackedOnlyCU |

    And transform CU to new LU
      | sourceCU            | newLU         | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | nonSelfPackedOnlyCU | onlyNonSelfLU | boxPI    | 1           | 1           |
    And M_HU are validated:
      | M_HU_ID       | HUStatus |
      | onlyNonSelfLU | A        |

    # Scan: no self-packed product → nothing packed, product skipped
    When mass-printing scans LU
      | LU            | Picker             |
      | onlyNonSelfLU | massPrintingPicker |
    Then mass-printing result has no product results
    And mass-printing skipped non-self-packed products:
      | skippedProduct       |
      | nonSelfPackedPrdOnly |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: REST endpoint — scan LU, assert boxesPacked in the response
    # Single-unit order for the self-packed product; LU has 3 units, 1 order → 1 box packed, 2 leftover.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate |
      | SO_rest    | true    | customer                 | 2026-06-01  | 2026-06-02          |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO_rest               | OL_rest    | selfPackedPrd           | 1          |
    And the order identified by SO_rest is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | SS_rest    | OL_rest                   | N             |

    # Generate a QR code for the LU and store it under the HUQRCode identifier 'lu_qr'.
    # The HUQRCode column stores the HUQRCode object in HUQRCode_StepDefData;
    # the REST scan step retrieves it directly — no HTTP/JSON interpolation of the QR string.
    And generate QR Codes for HUs
      | M_HU_ID.Identifier | HUQRCode |
      | lu                 | lu_qr    |

    # Authenticate as the dedicated picker (has the WebUI role from Background).
    # This sets Env.getLoggedUserId() to the massPrintingPicker user,
    # which the controller reads as the picker identity.
    And the existing user with login 'massPrintingPicker' receives a random a API token for the existing role with name 'WebUI'

    # Call PickingRestController.massPrintingScan directly (Spring bean, no HTTP layer).
    # Using the Spring bean avoids JSON-escaping issues: the QR code string contains
    # '#' and '"' characters that break @variable@ substitution in HTTP payloads.
    When mass-printing REST scans LU
      | HUQRCode |
      | lu_qr    |
    Then mass-printing REST result is
      | boxesPacked | OPT.unitsLeftOnLU | OPT.unitsOfOpenDemandRemaining |
      | 1           | 2                 | 0                              |
