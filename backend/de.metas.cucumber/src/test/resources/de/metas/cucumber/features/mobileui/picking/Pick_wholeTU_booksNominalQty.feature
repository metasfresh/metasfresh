@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@F00230
Feature: mobileUI Picking - a whole-TU pick must book the qty it actually moved

  ## F00230: MobileUI Picking
  #
  # A TU-level pick of "1 TU" moves the ENTIRE source TU, but books the nominal qty derived from the
  # SALES line's packing instruction instead of the TU's real content.
  #
  # Preconditions that make the two diverge:
  #   - the picking profile enables catch-weight TU picking, and the sales line carries a FINITE
  #     packing instruction (1 piece per TU) => PickingJobLine.pickingUnit = TU
  #   - the source HU is a real, non-virtual TU produced by MANUFACTURING, packed 2 pieces per TU
  #     (a different packing instruction than the sales line's)
  #
  # PickingJobPickCommand then:
  #   - computes qtyToPickCUs = line packing info (1 CU per TU) x 1 TU = 1
  #   - takes the pickWholeTUs path, which moves the whole source TU (2 pieces) via HUTransformService
  #   - books qtyToPickCUs (1) because isPickWholeTU (the REQUEST flag) is false
  #
  # Result: the picked HU holds 2 while the booking says 1. In production the divergence only
  # surfaces later, when shipment generation aborts in EDI_Desadv_Pack_Item.validateMovementQtySum
  # (EDI_DesadvLine.QtyDeliveredInStockingUOM vs the sum of the pack items' MovementQtys) and rolls
  # the whole shipment back.
  #
  # This stays latent wherever production packs exactly ONE piece per TU, because then "move the
  # whole TU" and "move 1 piece" are the same thing and the books happen to match.

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
      | Identifier | X12DE355 | GTIN           |
      | product    | PCE      | 97311876341811 |
    # Catch weight: nominal 5.5 kg per piece, weighed individually at pick time.
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | product                 | PCE                    | KGM                  | 5.5          | Y                        |

    # Karton = the FINITE sales packing instruction (1 piece per TU) that the order line carries.
    # Rahmen = the returnable frame production packs into; infinite capacity, so it took 2 pieces.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | Karton     |
      | Rahmen     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name   | HU_UnitType | IsCurrent |
      | Karton                        | Karton                | Karton | TU          | Y         |
      | Rahmen                        | Rahmen                | Rahmen | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | Karton                     | Karton                        | 0   | MI       |
      | Rahmen                     | Rahmen                        | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity |
      | KartonX1                           | Karton                     | product                 | 1   | 2000-01-01 | N                      |
      | RahmenInfinite                     | Rahmen                     | product                 | 0   | 2000-01-01 | Y                      |

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
      | PLV                    | product      | 5.0      | KGM               | CatchWeight          | Normal                        |

    # IsCatchWeightTUPickingEnabled=Y is what makes PickingJobLine.pickingUnit resolve to TU even
    # though the product is catch weight (PickingJobLoaderAndSaver.computePickingUnit).
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | IsCatchWeightTUPickingEnabled |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | Y                             |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product                 | 0       | 2        | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | pickFromCU |

  @from:cucumber
  @Id:S31642_10
  Scenario: A TU-level pick of one whole 2-piece TU must not book 1
    # The production HU: ONE real TU on the infinite-capacity Rahmen, holding 2 pieces.
    When transform CU to new TUs
      | sourceCU   | cuQty | M_HU_PI_Item_Product_ID | resultedNewTUs |
      | pickFromCU | 2     | RahmenInfinite          | productionTU   |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | productionTUStorage        | productionTU       | product                 | 2   |

    # The sales order carries the FINITE Karton packing instruction - 1 piece per TU.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | line1      | salesOrder            | product                 | 2          | KartonX1                               |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | line1                     | N             |

    And start picking job for sales order identified by salesOrder
    And scan picking slot identified by 200.0
    # No picking target of any kind is set - as in the reported case.
    # One per-piece LMQ label: qty 1, weighed 5.772 kg. The client sends qtyPicked=1, pickWholeTU=false.
    And pick lines
      | PickFromHU   | QRCode                        |
      | productionTU | LMQ#1#5.772#28.10.2026#100308 |

    # First half of the invariant - this PASSES: the source TU was moved WHOLE, so it is now picked
    # (HUStatus S) and still carries both pieces. Nothing was split out.
    Then M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | productionTU       | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pickedTUStorage            | productionTU       | product                 | 2   |

    # Second half - this FAILS, and the two together are the defect: 2 pieces moved, 1 booked.
    # ACTUAL: QtyPicked = 1, taken from the sales line's 1-piece Karton packing info.
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | Processed |
      | 5.772             | KGM          | 2         | 1     | N         |
