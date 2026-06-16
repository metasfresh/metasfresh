@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: mobileUI Picking - GRAI scan in the Flow Through (LU_TU) picking profile — atomic pick event
## Scenario 1: atomic pick with graiCodes; asserts the picked TUs carry the scanned GRAIs.
## Scenario 2: completion guard — fewer GRAIs than TUs blocks completion.

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
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |

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
      | product               | pickFromLU | 3         | 7613204.00307.000002 |
      | product               | pickFromLU | 3         | 7613204.00307.000003 |
    And expect current picking target
      | Existing_LU |
      | pickedLU    |

    # The picked TUs must carry the scanned GRAIs.
    Then the TUs on picked LU identified by pickedLU carry GRAIs
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
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | pickFromLU | 3         |
    And expect current picking target
      | Existing_LU |
      | pickFromLU  |

    # Capture only 2 GRAIs for a 3-TU LU (one TU left without a GRAI).
    And set picking GRAIs on LU identified by pickFromLU
      | GRAI                 |
      | 7613204.00307.000001 |
      | 7613204.00307.000002 |

    # The completion must be rejected by the completion guard with the GRAICountMismatch error (one TU has no GRAI).
    Then complete picking job expecting error
      | ErrorCode           |
      | GRAI_COUNT_MISMATCH |
