@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: Mass Printing - Skip shipment schedule locked by another user
  ## F00230.21: MobileUI Product-based Picking — mass printing
  ##
  ## When a user scans an LU for mass printing and one of the open shipment schedules
  ## for a product on the LU is already locked by a different picker, that schedule
  ## must be silently skipped (with a warning logged) and the remaining unlocked
  ## schedules must still be processed normally.
  ##
  ## Covers the resilience case: a locked schedule must never block the whole scan.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    # Defensive: clear any leaked picker->workplace assignment so getWorkplaceByUserId resolves only OUR workplace.
    # Do NOT deactivate all C_Workplace here — that would deactivate seed workplaces other executor features depend on.
    And delete all C_Workplace_User_Assign records

    # otherPicker simulates a SECOND warehouse worker who is already picking one of the orders on this LU:
    # below they take a picking lock on that order's shipment schedule. The scenario then asserts that mass
    # printing silently skips the locked schedule (it belongs to someone else) and still processes the other.
    And metasfresh contains AD_Users:
      | Identifier  | Name        | Login       |
      | otherPicker | otherPicker | otherPicker |

    And metasfresh contains M_Products:
      | Identifier | X12DE355 | IsSelfPacked |
      | product    | PCE      | Y            |

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
    # Capacity-1 TU PI: 1 PCE per box. Mass printing packs one box (and prints one label) per unit, so the
    # TU packing instruction MUST have capacity 1 — the pickTuLine loop fires cappedQty PICK events of qty 1,
    # each materialising and closing exactly one box. A higher-capacity TU would be consumed by the first pick
    # and the next iteration would fail ("HU ... wurde bereits zerstört"). Mirrors the validated mass-printing
    # E2E setup (massPrinting.spec.js: "box PI: 1 CU per TU = 1 unit = 1 box", qtyCUsPerTU=1).
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TU1                                | TU                         | product                 | 1   | 2000-01-01 |

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
      | IsMassPrinting | CreateShipmentPolicy  | IsAllowPickingAnyHU | IsAllowCompletingPartialPickingJob |
      | Y              | CREATE_COMPLETE_CLOSE | Y                   | Y                                  |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    # Create warehouse inventory to produce stock
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | stockLine                     | product                 | 0       | 80       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | stockLine          | stockCU |

    # Build the LU from inventory stock (this is the LU that will be scanned during mass printing).
    # Use individual (non-aggregated) TUs — eight real 1-PCE boxes loaded onto one LU — NOT an aggregate TU.
    # When a picking slot is allocated, the mass-printing close path adds the picked HU to the picking-slot
    # queue, which extracts that HU from its LU parent. An aggregate TU (created by `transform CU to new LU`)
    # cannot be re-parented ("Changing parent for the entire Aggregate TU is not allowed"), so the source LU
    # must consist of discrete TUs that can be extracted one at a time.
    # 8 single-PCE boxes are enough for both 4-PCE schedules (the locked one is skipped, so only 4 boxes are
    # actually picked). Each box is a discrete TU so the picking-slot close can extract them one by one.
    When transform CU to new TUs
      | sourceCU | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs              |
      | stockCU  | 8     | TU1                     | tu1,tu2,tu3,tu4,tu5,tu6,tu7,tu8 |
    And aggregate TUs to new LU
      | sourceTUs                       | newLUs |
      | tu1,tu2,tu3,tu4,tu5,tu6,tu7,tu8 | lu     |
    And M_HU are validated:
      | M_HU_ID                         | HUStatus | Parent |
      | lu                              | A        | -      |
      | tu1,tu2,tu3,tu4,tu5,tu6,tu7,tu8 | A        | lu     |

    # Mass printing creates a PRODUCT picking job and picks it programmatically. For PRODUCT jobs the pick
    # command requires the job to have a picking slot, which is auto-allocated from the picker's workplace
    # (WorkplaceService.getWorkplaceByUserId -> Workplace.getPickingSlotId). So the picker 'metasfresh' must
    # have a workplace carrying a picking slot, otherwise the pick fails with "scan a picking slot first".
    # warehouse 540008 = WarehouseId.MAIN (Value=StdWarehouse) — the same warehouse the inventory/LU and the
    # picking slot live in.
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouse      | StdWarehouse |
    # IsDynamic=Y is required: HUPickingSlotBL.allocatePickingSlotIfPossible refuses to allocate a
    # non-dynamic slot ("Not a dynamic picking slot"), and PickingJobCreateCommand allocates with
    # failIfNotAllocated=false — so a non-dynamic slot silently stays unallocated and the pick then
    # fails with "scan a picking slot first". A dynamic slot allocates to the schedule's bpartner/location.
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot | IsDynamic |
      | pickingSlot | 1           | Y         |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | M_PickingSlot_ID |
      | workplace  | warehouse      | pickingSlot      |
    And assign C_Workplace to user
      | C_Workplace_ID | AD_User_ID.Login |
      | workplace      | metasfresh       |


  @from:cucumber
  Scenario: Mass printing skips schedule locked by another picker and ships the non-locked one

    # Create 2 sales orders for the same product. Each order demands 4 PCE (= 4 single-PCE boxes).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | PreparationDate      |
      | SO_locked  | true    | customer                 | 2024-03-26  | 2024-03-27T05:00:00Z |
      | SO_open    | true    | customer                 | 2024-03-26  | 2024-03-27T06:00:00Z |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier       | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO_locked             | OL_locked        | product                 | 4          | TU1                                    |
      | SO_open               | OL_open          | product                 | 4          | TU1                                    |
    And the order identified by SO_locked is completed
    And the order identified by SO_open is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedLocked  | OL_locked                 | N             |
      | schedOpen    | OL_open                   | N             |

    # otherPicker takes a picking lock on schedLocked before the scan — i.e. another worker is already
    # picking that order, so its schedule is off-limits to mass printing.
    Given M_ShipmentSchedule_Lock exists for:
      | M_ShipmentSchedule_ID | Login       |
      | schedLocked           | otherPicker |

    And validate M_ShipmentSchedule_Lock record for:
      | M_ShipmentSchedule_ID | Login       | Exists |
      | schedLocked           | otherPicker | true   |

    # metasfresh scans the LU — schedLocked must be silently skipped, schedOpen must be processed
    And mass printing scan:
      | M_HU_ID | Login      |
      | lu      | metasfresh |

    # Exactly 4 units (= 4 single-PCE boxes) packed — only the non-locked schedule was processed.
    Then validate mass printing result:
      | M_HU_ID | UnitsPacked |
      | lu      | 4           |

    # The scanned LU started with 8 PCE; only the non-locked schedule's 4 units were packed, so 4 PCE remain.
    And validate M_HUs:
      | Identifier | M_Product_ID | Qty   |
      | lu         | product      | 4 PCE |

    # schedLocked was skipped: its lock is still held by otherPicker (mass printing did not unlock or process it)
    And validate M_ShipmentSchedule_Lock record for:
      | M_ShipmentSchedule_ID | Login       | Exists |
      | schedLocked           | otherPicker | true   |

    # schedOpen was processed: a completed shipment exists for it
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | schedOpen                        | shipment              | CO            |
