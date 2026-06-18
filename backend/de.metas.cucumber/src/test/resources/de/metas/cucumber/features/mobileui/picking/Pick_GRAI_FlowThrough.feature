@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: mobileUI Picking - GRAI scan in the Flow Through (LU_TU) picking profile — atomic pick event
# Scenario 1: SALES_ORDER aggregation — atomic pick with graiCodes; asserts picked TUs carry the scanned GRAIs.
# Scenario 2: completion guard — fewer GRAIs than TUs in the atomic pick event blocks completion.
# Scenario 3: PRODUCT aggregation — atomic pick with graiCodes; proves the line-level LU target path.
# Scenario 4: SALES_ORDER aggregation — two products picked onto ONE shared LU; each pick carries its own GRAIs; the LU must end up carrying ALL of both picks' GRAIs.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | PCE      |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name | HU_UnitType | IsCurrent |
      | TU                            | TU                    | TU   | TU          | Y         |
      | LU                            | LU                    | LU   | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx4                               | TU                         | product                 | 4   | 2000-01-01 |

    # Declare the GRAI HU-attribute slot on the TU PI version so a GRAI can be stamped on the
    # materialised TUs. Reuses the existing `metasfresh contains M_HU_PI_Attribute:` step
    # (M_HU_PI_Attribute_StepDef); the seed DB already has the M_Attribute with Value=GRAI.
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | TU                 | GRAI              |

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
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product      | 6.0      | PCE               | Nominal              | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | PickingJobAggregationType |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | sales_order               |

    # GRAIRequired=Y customer -> the completion guard fires for this LU_TU job.
    # Distinct identity (graiCustomer / GRAI_Dummy_GLN) on purpose: the C_BPartner step upserts by
    # Value, and the picking features on this executor share one `customer` record. A GRAIRequired=Y
    # value on that shared record would leak into sibling picking scenarios on the same executor and
    # trip their completion guard, so this customer must be its own record.
    And metasfresh contains C_BPartners without locations:
      | Identifier   | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | GRAIRequired |
      | graiCustomer | graiCustomer | N            | Y              | PS                            | Y            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN            | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | graiCustomerLocation | GRAI_Dummy_GLN | graiCustomer             | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product                 | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | pickFromCU |

# ######################################################################################################################
# SCENARIO 1 — Atomic pick: graiCodes ride on the single pick event; picked TUs carry the GRAIs.
# ######################################################################################################################
  @from:cucumber
  Scenario: GRAIRequired customer - atomic pick with graiCodes; picked TUs must carry the scanned GRAIs
    When transform CU to new TUs
      | sourceCU   | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs                  |
      | pickFromCU | 12    | TUx4                    | pickFromTU1,pickFromTU2,pickFromTU3 |
    And aggregate TUs to new LU
      | sourceTUs                           | newLUs     |
      | pickFromTU1,pickFromTU2,pickFromTU3 | pickFromLU |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | graiCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 12         | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # Atomic pick: qty + 3 GRAIs (one per TU) in a single event.
    # Each row is one GRAI; pick params (PickFromHU, QtyPicked) are read from the first row.
    And pick line with GRAIs:
      | PickingLine.byProduct | PickFromHU | QtyPicked | GRAI                 |
      | product               | pickFromLU | 3         | 7613204.00307.000001 |
      |                       |            |           | 7613204.00307.000002 |
      |                       |            |           | 7613204.00307.000003 |
    And expect current picking target
      | Existing_LU |
      | pickFromLU  |

    # The picked TUs must carry the scanned GRAIs.
    Then the TUs on picked LU identified by pickFromLU carry GRAIs
      | GRAI                 |
      | 7613204.00307.000001 |
      | 7613204.00307.000002 |
      | 7613204.00307.000003 |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |

# ######################################################################################################################
# SCENARIO 2 — Completion guard: fewer GRAIs than TUs blocks completion.
# ######################################################################################################################
  @from:cucumber
  Scenario: GRAIRequired customer - completing with fewer GRAIs than TUs is blocked
    When transform CU to new TUs
      | sourceCU   | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs                  |
      | pickFromCU | 12    | TUx4                    | pickFromTU1,pickFromTU2,pickFromTU3 |
    And aggregate TUs to new LU
      | sourceTUs                           | newLUs     |
      | pickFromTU1,pickFromTU2,pickFromTU3 | pickFromLU |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | graiCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 12         | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # Atomic pick: qty=3 TUs but only 2 GRAIs — one TU is left without a GRAI.
    And pick line with GRAIs:
      | PickingLine.byProduct | PickFromHU | QtyPicked | GRAI                 |
      | product               | pickFromLU | 3         | 7613204.00307.000001 |
      |                       |            |           | 7613204.00307.000002 |

    # The completion must be rejected by the completion guard with the GRAICountMismatch error (one TU has no GRAI).
    Then complete picking job expecting error
      | ErrorCode           |
      | GRAI_COUNT_MISMATCH |

# ######################################################################################################################
# SCENARIO 3 — PRODUCT aggregation: the line-level LU picking target path.
# In PRODUCT aggregation the picking job has one extra activity: ScanPickFromHU (before ScanPickingSlot).
# The flow is: start job -> scan pick-from HU -> scan picking slot -> set LU target -> atomic pick with GRAIs -> complete.
# The picked LU target is materialised at LINE level (not header), which is the path the original bug hit:
# header-only resolution returned HTTP 422 for product-agg jobs. This scenario proves the atomic
# GRAI stamp works end-to-end for the PRODUCT aggregation type.
# ######################################################################################################################
  @from:cucumber
  Scenario: GRAIRequired customer - PRODUCT aggregation - atomic pick with graiCodes; picked TUs must carry the scanned GRAIs
    When transform CU to new TUs
      | sourceCU   | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs                  |
      | pickFromCU | 12    | TUx4                    | pickFromTU1,pickFromTU2,pickFromTU3 |
    And aggregate TUs to new LU
      | sourceTUs                           | newLUs     |
      | pickFromTU1,pickFromTU2,pickFromTU3 | pickFromLU |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | graiCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 12         | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | PickingJobAggregationType |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | product                   |

    And start picking job for sales order identified by SO
    # PRODUCT aggregation: scan the source HU first (the extra ScanPickFromHU activity).
    And scan pick from HU identified by pickFromLU
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # Atomic pick: qty + 3 GRAIs (one per TU) in a single event.
    # Each row is one GRAI; pick params (PickFromHU, QtyPicked) are read from the first row.
    And pick line with GRAIs:
      | PickingLine.byProduct | PickFromHU | QtyPicked | GRAI                 |
      | product               | pickFromLU | 3         | 7613204.00307.000004 |
      |                       |            |           | 7613204.00307.000005 |
      |                       |            |           | 7613204.00307.000006 |
    # PRODUCT aggregation: the source LU flows through to become the line-level picked LU, so the
    # effective line target IS pickFromLU — assert against it (not a fresh identifier).
    And expect line picking target
      | Existing_LU |
      | pickFromLU  |

    # The picked TUs must carry the scanned GRAIs (LU materialised at line level for PRODUCT aggregation).
    Then the TUs on picked LU identified by pickFromLU carry GRAIs
      | GRAI                 |
      | 7613204.00307.000004 |
      | 7613204.00307.000005 |
      | 7613204.00307.000006 |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |

# ######################################################################################################################
# SCENARIO 4 — SALES_ORDER aggregation, MIXED-PRODUCT shared LU: each product is picked in its own atomic
# pick event (each carrying ONLY its own GRAIs) onto ONE shared sales-order-aggregation LU. The shared LU
# must end up carrying the GRAIs of BOTH picks (3 + 3 = 6). This is Scenario 1 doubled for two products.
#
# A second product (product2), its TU item-product, price and a second inventory are added in the
# scenario (NOT the Background, so Scenarios 1-3 are unaffected). Both products' TUs are aggregated onto
# ONE shared source LU (pickFromLU). The order has two lines (one per product). The pick of product1 sends
# GRAIs ...01,02,03; the pick of product2 sends GRAIs ...04,05,06 — each event carries only its own set.
#
# This proves stampGraisIfRequired UNIONS the new pick's GRAIs with those already on the shared LU
# before calling huService.setGrais(luId, ...): HUGraiSnapshot.computeDelta treats the argument as the
# LU's COMPLETE desired set, so without the union picking product2 would WIPE product1's GRAIs (the LU
# would carry only ...04,05,06 and completion would throw GRAI_COUNT_MISMATCH). With the union the
# shared LU correctly carries all six (...01..06) and completion succeeds.
# ######################################################################################################################
  @from:cucumber
  Scenario: GRAIRequired customer - SALES_ORDER aggregation - two products on one shared LU; each pick carries its own GRAIs; the shared LU must carry ALL of them
    # Second product + its TU item-product (reuses the Background's TU PI item) + price.
    Given metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product2   | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx4_product2                      | TU                         | product2                | 4   | 2000-01-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product2     | 6.0      | PCE               | Nominal              | Normal                        |

    # Second inventory producing a pick-from CU for product2 (own M_Inventory — the Background's
    # `inventory` is already completed, so we cannot add a line to it / re-complete it).
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory2                | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory2                | line2                         | product2                | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory2'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | line2              | pickFromCU2 |

    # Materialise each product's TUs, then aggregate BOTH products' TUs onto ONE shared LU.
    # Both TU sets share the same BPartner/locator/HUStatus, so LULoader packs them onto a single LU.
    When transform CU to new TUs
      | sourceCU   | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs                  |
      | pickFromCU | 12    | TUx4                    | pickFromTU1,pickFromTU2,pickFromTU3 |
    And transform CU to new TUs
      | sourceCU    | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs                  |
      | pickFromCU2 | 12    | TUx4_product2           | pickFromTU4,pickFromTU5,pickFromTU6 |
    And aggregate TUs to new LU
      | sourceTUs                                                               | newLUs     |
      | pickFromTU1,pickFromTU2,pickFromTU3,pickFromTU4,pickFromTU5,pickFromTU6 | pickFromLU |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | graiCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 12         | TUx4                                   |
      | SO                    | L2         | product2                | 12         | TUx4_product2                          |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule  | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # Pick product1: qty=3 TUs + its OWN 3 GRAIs (one per TU). pick params (PickFromHU, QtyPicked) from row 1.
    And pick line with GRAIs:
      | PickingLine.byProduct | PickFromHU | QtyPicked | GRAI                 |
      | product               | pickFromLU | 3         | 7613204.00307.000001 |
      |                       |            |           | 7613204.00307.000002 |
      |                       |            |           | 7613204.00307.000003 |
    # Pick product2 onto the SAME shared LU: qty=3 TUs + its OWN 3 GRAIs.
    And pick line with GRAIs:
      | PickingLine.byProduct | PickFromHU | QtyPicked | GRAI                 |
      | product2              | pickFromLU | 3         | 7613204.00307.000004 |
      |                       |            |           | 7613204.00307.000005 |
      |                       |            |           | 7613204.00307.000006 |
    # SALES_ORDER aggregation materialises ONE shared header LU for the whole order (both lines pick onto
    # it). It is a NEW LU, not the source pickFromLU — register it under a fresh identifier (mixedLU).
    And expect current picking target
      | Existing_LU |
      | mixedLU     |

    # The shared LU's TUs must carry ALL SIX GRAIs (both picks). On buggy code only ...04,05,06 survive.
    Then the TUs on picked LU identified by mixedLU carry GRAIs
      | GRAI                 |
      | 7613204.00307.000001 |
      | 7613204.00307.000002 |
      | 7613204.00307.000003 |
      | 7613204.00307.000004 |
      | 7613204.00307.000005 |
      | 7613204.00307.000006 |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |
