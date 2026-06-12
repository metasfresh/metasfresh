@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: Mass Printing - honour job-scheduled-to-workplace mode
  ## F00230.21: MobileUI Product-based Picking — mass printing
  ##
  ## When the picking profile runs in job-scheduled-to-workplace mode (IsConsideredOnlyScheduledJobs=Y),
  ## only demand that has been explicitly scheduled to the picker's workplace via a M_Picking_Job_Schedule
  ## is eligible — exactly like the regular picking launcher in that mode. A scan must pack ONLY the
  ## scheduled demand and ignore open demand that is not scheduled to the workplace, even though the
  ## scanned LU physically holds enough stock for both.

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
    # each materialising and closing exactly one box. Mirrors the validated mass-printing E2E setup.
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

    # IsConsideredOnlyScheduledJobs=Y → job-scheduled-to-workplace mode: only demand scheduled to the
    # picker's workplace (a M_Picking_Job_Schedule) is eligible for mass printing.
    And set mobile UI picking profile
      | IsMassPrinting | CreateShipmentPolicy  | IsAllowPickingAnyHU | IsAllowCompletingPartialPickingJob | IsConsideredOnlyScheduledJobs |
      | Y              | CREATE_COMPLETE_CLOSE | Y                   | Y                                  | Y                             |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    # Create warehouse inventory to produce stock (warehouse 540008 = WarehouseId.MAIN, Value=StdWarehouse).
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

    # Build the LU from inventory stock — eight discrete 1-PCE boxes on one LU (this is the LU that is scanned).
    # The LU holds 8 PCE, enough for BOTH orders' demand, so the test proves the eligibility restriction is what
    # limits packing to the scheduled demand (not a stock shortage).
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

    # The picker 'metasfresh' works at a workplace carrying a (dynamic) picking slot in warehouse StdWarehouse.
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouse      | StdWarehouse |
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot | IsDynamic |
      | pickingSlot | 1           | Y         |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | M_PickingSlot_ID |
      | workplace  | warehouse      | pickingSlot      |
    And assign C_Workplace to user
      | C_Workplace_ID | AD_User_ID.Login |
      | workplace      | metasfresh       |


  Scenario: Mass printing packs only the demand scheduled to the workplace and ignores the unscheduled demand

    # Two sales orders for the same product, each demanding 4 PCE. Only ONE will be scheduled to the workplace.
    And metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | PreparationDate      |
      | SO_scheduled    | true    | customer                 | 2024-03-26  | 2024-03-27T05:00:00Z |
      | SO_notScheduled | true    | customer                 | 2024-03-26  | 2024-03-27T06:00:00Z |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier      | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO_scheduled          | OL_scheduled    | product                 | 4          | TU1                                    |
      | SO_notScheduled       | OL_notScheduled | product                 | 4          | TU1                                    |
    And the order identified by SO_scheduled is completed
    And the order identified by SO_notScheduled is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedScheduled     | OL_scheduled              | N             |
      | schedNotScheduled  | OL_notScheduled           | N             |

    # Schedule ONLY schedScheduled to the picker's workplace. schedNotScheduled stays unscheduled, so in
    # job-scheduled-to-workplace mode it is NOT eligible for this picker.
    And create or update picking job schedules
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | schedScheduled        | workplace      | 4         |
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | schedScheduled        | workplace      | 4         |

    # The picker scans the LU — mass printing must pack ONLY the 4 PCE of the scheduled demand, even though
    # the LU holds 8 PCE and there is another open (but unscheduled) order for 4 PCE.
    And mass printing scan:
      | M_HU_ID | Login      |
      | lu      | metasfresh |

    Then validate mass printing result:
      | M_HU_ID | UnitsPacked |
      | lu      | 4           |

    # The scanned LU started with 8 PCE; only the 4 scheduled units were packed, so 4 PCE remain on the LU.
    And validate M_HUs:
      | Identifier | M_Product_ID | Qty   |
      | lu         | product      | 4 PCE |

    # The scheduled schedule was processed → a completed shipment exists for it.
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | schedScheduled                   | shipment              | CO            |

    # The unscheduled schedule was NOT touched: still fully open (QtyToDeliver=4, nothing picked, nothing delivered).
    And after not more than 10s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyPickList |
      | schedNotScheduled                | 4                | 0                | 0               |
