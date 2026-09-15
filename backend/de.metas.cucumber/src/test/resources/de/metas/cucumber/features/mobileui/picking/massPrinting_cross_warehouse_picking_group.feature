@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: Mass Printing - scan an LU stored in a different warehouse of the same picking group
  ## F00230.21: MobileUI Product-based Picking — mass printing
  ##
  ## A picker works at a workplace in the packing warehouse, but the LU they scan for mass printing
  ## physically sits in a separate storage warehouse. Both warehouses belong to ONE picking group, so
  ## they form a single picking area: the open sales demand lives in the packing warehouse while the
  ## stock lives in the storage warehouse.
  ##
  ## When the picker scans the LU, mass printing must find the open demand of the workplace's picking
  ## group (not only the LU's own storage warehouse) and pack against it. If the scanned LU is located
  ## in a warehouse that is NOT part of the workplace's picking group, the scan must be rejected.

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
    # Capacity-1 TU PI: 1 PCE per box (mirrors the validated mass-printing E2E setup), so the pickTuLine loop
    # materialises and closes exactly one box per unit.
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

    # The packing/workplace warehouse — open sales demand and the picking machinery (picking slot) live here.
    # warehouse 540008 = WarehouseId.MAIN (Value=StdWarehouse). It is also where the picking slot is created.
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseWork  | StdWarehouse |

    # The storage warehouse — the scanned LU physically sits here, in a different warehouse than the demand.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | M_Locator_ID  |
      | warehouseStock | locatorStock  |

    # Picking slot lives in WarehouseId.MAIN regardless; the workplace points the picker at warehouseWork.
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot | IsDynamic |
      | pickingSlot | 1           | Y         |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | M_PickingSlot_ID |
      | workplace  | warehouseWork  | pickingSlot      |
    And assign C_Workplace to user
      | C_Workplace_ID | AD_User_ID.Login |
      | workplace      | metasfresh       |


  Scenario: Mass printing packs demand of the workplace's picking group from an LU stored in another warehouse of that group

    # Both warehouses form one picking area, so a picker at the workplace (warehouseWork) may pick the LU
    # that physically sits in warehouseStock.
    And the following warehouses share one M_Warehouse_PickingGroup:
      | Name        | M_Warehouse_ID |
      | pickingArea | warehouseWork  |
      | pickingArea | warehouseStock |

    # Build the LU from inventory stock located in the STORAGE warehouse (this is the LU that will be scanned).
    # Use individual (non-aggregated) TUs — eight real 1-PCE boxes loaded onto one LU — so the picking-slot
    # close path can extract them one at a time.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | warehouseStock |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | stockLine                     | product                 | 0       | 80       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | stockLine          | stockCU |

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

    # Open sales demand of 4 PCE, delivered from the WORKPLACE warehouse (not the LU's storage warehouse).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | M_Warehouse_ID | DateOrdered | PreparationDate      |
      | SO         | true    | customer                 | warehouseWork  | 2024-03-26  | 2024-03-27T05:00:00Z |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | OL         | product                 | 4          | TU1                                    |
    And the order identified by SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched      | OL                        | N             |

    # The picker scans the LU (located in warehouseStock) — mass printing must search the workplace's picking
    # group, find the open demand in warehouseWork, and pack 4 units from the LU.
    And mass printing scan:
      | M_HU_ID | Login      |
      | lu      | metasfresh |

    Then validate mass printing result:
      | M_HU_ID | UnitsPacked |
      | lu      | 4           |

    # The scanned LU started with 8 PCE; 4 units were packed against the demand, so 4 PCE remain on the LU.
    And validate M_HUs:
      | Identifier | M_Product_ID | Qty   |
      | lu         | product      | 4 PCE |

    # The processed schedule produced a completed shipment.
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | sched                            | shipment              | CO            |


  Scenario: Mass printing rejects an LU located in a warehouse outside the workplace's picking group

    # Only the workplace warehouse is in the picking group; the storage warehouse holding the LU is NOT.
    And the following warehouses share one M_Warehouse_PickingGroup:
      | Name        | M_Warehouse_ID |
      | pickingArea | warehouseWork  |

    # Build the LU in the storage warehouse, which is outside the workplace's picking group.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | warehouseStock |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | stockLine                     | product                 | 0       | 80       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | stockLine          | stockCU |

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

    # Open sales demand of 4 PCE in the workplace warehouse.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | M_Warehouse_ID | DateOrdered | PreparationDate      |
      | SO         | true    | customer                 | warehouseWork  | 2024-03-26  | 2024-03-27T05:00:00Z |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | OL         | product                 | 4          | TU1                                    |
    And the order identified by SO is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched      | OL                        | N             |

    # Scanning an LU located outside the workplace's picking group must be rejected, not silently pack nothing.
    Then mass printing scan is rejected:
      | M_HU_ID | Login      | AD_Message                                                               |
      | lu      | metasfresh | de.metas.handlingunits.picking.massprinting.LUNotInWorkplacePickingGroup |
