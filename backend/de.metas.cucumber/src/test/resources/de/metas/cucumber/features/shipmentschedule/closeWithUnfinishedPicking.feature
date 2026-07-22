@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@F00130
@ghActions:run_on_executor7
Feature: Closing a shipment schedule with an unfinished picking order
## F00130: Shipment Schedule
##
## The user-initiated "Close shipment schedules" action must refuse to close a shipment schedule
## that is still referenced by a Drafted picking job — otherwise the schedule is marked done while
## the warehouse pick stays open.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-22T13:30:13+01:00[Europe/Berlin]

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

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-07-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product                 | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | pickFromCU |

  @Id:S30915_010
  Scenario: Close is refused while the schedule's picking job is still Drafted
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2026-07-22  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 160        | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    # a real warehouse worker started picking this schedule (M_Picking_Job created, DocStatus Drafted)
    # but has not finished picking it yet
    And start picking job for sales order identified by SO

    # the user now runs the "Close shipment schedules" action on the schedule that is still being picked
    When the M_ShipmentSchedule_CloseShipmentSchedules process is run for selection:
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    Then the M_ShipmentSchedule_CloseShipmentSchedules process is rejected
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed |
      | shipmentSchedule                 | false        |

  @Id:S30915_020
  Scenario: Close succeeds when the schedule has no unfinished picking job
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2026-07-22  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 160        | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    # no picking job was ever started for this schedule
    When the M_ShipmentSchedule_CloseShipmentSchedules process is run for selection:
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    Then the M_ShipmentSchedule_CloseShipmentSchedules process is not rejected
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed |
      | shipmentSchedule                 | true         |

  @Id:S30915_030
  Scenario: Multi-selection is all-or-nothing when one of the selected schedules has an unfinished picking job
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO1        | true    | customer                 | 2026-07-22  |
      | SO2        | true    | customer                 | 2026-07-22  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO1                   | L1         | product                 | 160        | TUx4                                   |
      | SO2                   | L2         | product                 | 160        | TUx4                                   |
    And the order identified by SO1 is completed
    And the order identified by SO2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | busySchedule  | L1                        | N             |
      | cleanSchedule | L2                        | N             |

    # only SO1's schedule is still being picked; SO2's schedule has no picking job at all
    And start picking job for sales order identified by SO1

    When the M_ShipmentSchedule_CloseShipmentSchedules process is run for selection:
      | M_ShipmentSchedule_ID |
      | busySchedule          |
      | cleanSchedule         |

    Then the M_ShipmentSchedule_CloseShipmentSchedules process is rejected
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed |
      | busySchedule                     | false        |
      | cleanSchedule                    | false        |

  @Id:S30915_040
  Scenario: Automatic/system close paths are not affected by the user-Close guard
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2026-07-22  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 160        | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    # same as S30915_010: a real warehouse worker started picking this schedule (M_Picking_Job created,
    # DocStatus Drafted) and has not finished picking it yet
    And start picking job for sales order identified by SO

    # this closes the schedule via ShipmentScheduleBL.closeShipmentSchedule directly -- the same BL
    # chokepoint every automatic/system close path uses (post-shipment auto-close, picking-complete,
    # REST close, contract close, order-triggered close) -- bypassing the user-Close process the guard
    # lives in
    And the M_ShipmentSchedule identified by shipmentSchedule is closed

    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed |
      | shipmentSchedule                 | true         |
