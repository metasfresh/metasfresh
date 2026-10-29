@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@F00230
Feature: mobileUI Picking - Pick catch weight CUs into a TU

  ## F00230: MobileUI Picking
  ## Catch weight CUs picked one after the other into the same new TU (no LU target).

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
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | product                 | PCE                    | KGM                  | 0.10         | Y                        |

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
      | PLV                    | product      | 5.0      | KGM               | CatchWeight          | Normal                        |

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
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product                 | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | pickFromCU |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S33252_TC3a
  Scenario: Two catch weight CU picks with typed weights into one bare TU, each pick keeps its own weight
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | line1      | salesOrder            | product                 | 4          | TUx4                                   |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | line1                     | N             |
    And start picking job for sales order identified by salesOrder
    And scan picking slot identified by 200.0
    And set picking target as new TU identified by TU
    And pick lines
      | PickFromHU | QtyPicked | CatchWeight |
      | pickFromCU | 2         | 4.100       |
    And pick lines
      | PickFromHU | QtyPicked | CatchWeight |
      | pickFromCU | 2         | 3.900       |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | M_TU_HU_ID | Processed |
      | 4.100             | KGM          | 2         | tu1        | N         |
      | 3.900             | KGM          | 2         | tu1        | N         |
    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty |
      | shipmentLine1  | shipment   | product      | 4           |
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch |
      | shipmentLine1 | product      | 4           | 8.000             |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S33252_TC3b
  Scenario: Two catch weight CU picks without a typed weight into one bare TU, each pick takes the weight of its CU, not the running TU weight
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | line1      | salesOrder            | product                 | 4          | TUx4                                   |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | line1                     | N             |
    # WeightNet is an absolute per-HU value that a pick carries over: each pick of the source CU carries 0.200 kg
    And M_HU_Attribute is changed
      | M_HU_ID    | M_Attribute_ID.Value | ValueNumber |
      | pickFromCU | WeightNet            | 0.200       |
    And start picking job for sales order identified by salesOrder
    And scan picking slot identified by 200.0
    And set picking target as new TU identified by TU
    And pick lines
      | PickFromHU | QtyPicked |
      | pickFromCU | 2         |
    And pick lines
      | PickFromHU | QtyPicked |
      | pickFromCU | 2         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | M_TU_HU_ID | Processed |
      | 0.200             | KGM          | 2         | tu1        | N         |
      | 0.200             | KGM          | 2         | tu1        | N         |
    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty |
      | shipmentLine1  | shipment   | product      | 4           |
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch |
      | shipmentLine1 | product      | 4           | 0.400             |
