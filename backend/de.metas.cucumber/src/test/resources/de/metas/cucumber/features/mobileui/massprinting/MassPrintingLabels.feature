@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: Mass Printing Labels — https://github.com/metasfresh/me03/issues/29942 (F00230.21)
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

    # ── Workplace with a picking slot, assigned to the picker (mirrors the operator being logged in
    #    at a Mass-Printing workplace; the programmatic PRODUCT job auto-allocates this slot) ──
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
      | C_Workplace_ID | AD_User_ID.Login |
      | workplace      | metasfresh       |

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
      | Identifier   | X12DE355 | OPT.IsSelfPacked |
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
      | inventory                 | 2026-06-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | invLine                       | selfPackedPrd           | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID  |
      | invLine            | stockCU  |

    And transform CU to new LU
      | sourceCU | newLU | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | stockCU  | lu    | boxPI    | 1           | 3           |
    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | lu      | A        |


  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  Scenario: 2.1-RED Scan LU with self-packed product and open demand — service invocation (RED phase)
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

    # Invoke mass-printing — expects service to pack 3 boxes (RED: throws UnsupportedOperationException)
    When mass-printing scans LU
      | LU |
      | lu |
    Then mass-printing result is
      | boxesPacked | OPT.unitsLeftOnLU | OPT.unitsOfOpenDemandRemaining |
      | 3           | 0                 | 0                              |
