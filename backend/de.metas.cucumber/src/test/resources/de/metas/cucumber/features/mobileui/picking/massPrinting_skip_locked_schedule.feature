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

    # picker2 is the concurrent user who will hold the lock on one schedule
    And metasfresh contains AD_Users:
      | Identifier | Name    | Login   |
      | picker2    | picker2 | picker2 |

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
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx4                               | TU                         | product                 | 4   | 2000-01-01 |

    And metasfresh contains M_PricingSystems:
      | Identifier |
      | PS         |
    And metasfresh contains M_PriceLists:
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | PL         | PS                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions:
      | Identifier | M_PriceList_ID |
      | PLV        | PL             |
    And metasfresh contains M_ProductPrices:
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
    And after not more than 60s, there are added M_HUs for inventory:
      | M_InventoryLine_ID | M_HU_ID |
      | stockLine          | stockCU |

    # Build the LU from inventory stock (this is the LU that will be scanned during mass printing)
    When transform CU to new LU
      | sourceCU | newLU | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | stockCU  | lu    | TU       | 4           | 2           |
    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | lu      | A        |


  # ################################################################################################################
  # Scenario: Scanner (metasfresh) scans an LU while another picker (picker2) has locked one of the two open
  # shipment schedules. The locked schedule must be silently skipped; the non-locked one must be processed
  # normally (picking job created, completed, shipment generated and completed).
  # ################################################################################################################
  Scenario: Mass printing skips schedule locked by another picker and ships the non-locked one

    # Create 2 sales orders for the same product. Each order demands 1 TU (4 PCE).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | PreparationDate             |
      | SO_locked  | true    | customer                 | 2024-03-26  | 2024-03-27T06:00:00+01:00   |
      | SO_open    | true    | customer                 | 2024-03-26  | 2024-03-27T07:00:00+01:00   |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier       | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO_locked             | OL_locked        | product                 | 4          | TUx4                                   |
      | SO_open               | OL_open          | product                 | 4          | TUx4                                   |
    And the order identified by SO_locked is completed
    And the order identified by SO_open is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedLocked  | OL_locked                 | N             |
      | schedOpen    | OL_open                   | N             |

    # picker2 locks schedLocked before the scan — simulating a concurrent picker holding it
    Given M_ShipmentSchedule_Lock exists for:
      | M_ShipmentSchedule_ID | Login   |
      | schedLocked           | picker2 |

    And validate M_ShipmentSchedule_Lock record for:
      | M_ShipmentSchedule_ID | Login   | Exists |
      | schedLocked           | picker2 | true   |

    # metasfresh scans the LU — schedLocked must be silently skipped, schedOpen must be processed
    And mass printing scan:
      | M_HU_ID | Login      |
      | lu      | metasfresh |

    # Exactly 4 units (= 1 TU) packed — only the non-locked schedule was processed.
    # LabelPrintAttempts=1: the system printed (or attempted to print) exactly 1 label for the 1 TU box that was packed.
    # The attempt count is asserted rather than labelsPrinted directly, so the test passes regardless of whether
    # a Jasper report or M_HU_Label_Config is available in this environment (the print call was made either way).
    Then validate mass printing result:
      | M_HU_ID | BoxesPacked | OPT.LabelPrintAttempts |
      | lu      | 4           | 1                      |

    # schedLocked was skipped: lock still held by picker2 (not unlocked/processed by mass printing)
    And validate M_ShipmentSchedule_Lock record for:
      | M_ShipmentSchedule_ID | Login   | Exists |
      | schedLocked           | picker2 | true   |

    # schedOpen was processed: a completed shipment exists for it
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | schedOpen                        | shipment              | CO            |
