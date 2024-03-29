@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Picking tests

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
      | Identifier         | Name                      | OPT.X12DE355 |
      | catchWeightProduct | catchWeightProduct @Date@ | PCE          |
      | regularProduct     | regularProduct @Date@     | PCE          |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | catchWeightProduct      | PCE                    | KGM                  | 0.10         | Y                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID | Name      |
      | TU         | TU @Date@ |
      | LU         | LU @Date@ |
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
      | TUx4_CatchWeightProduct            | TU                         | catchWeightProduct      | 4   | 2000-01-01 |
      | TUx4_RegularProduct                | TU                         | regularProduct          | 4   | 2000-01-01 |


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
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | catchWeightProduct | 5.0      | KGM               | CatchWeight          | Normal                        |
      | PLV                    | regularProduct     | 5.0      | PCE               | Nominal              | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  |
      | Y                   | CREATE_COMPLETE_CLOSE |

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
      | inventory                 | line1                         | catchWeightProduct      | 0       | 100      | PCE          |
      | inventory                 | line2                         | regularProduct          | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | line1                         | catchWeightHU      |
      | line2                         | regularProductHU   |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Pick single catch weight product
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | line1      | salesOrder            | catchWeightProduct      | 12         | TUx4_CatchWeightProduct                |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | line1                     | N             |
    And start picking job for sales order identified by salesOrder
    And scan picking slot identified by 200.0
    And pick lines
      | PickFromHU    | LMQRCode                   |
      | catchWeightHU | LMQ#1#0.101#08.11.2025#500 |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      | 0.101             | KGM          | 1         | cu1    | 0     | -          | 0     | -          | N         |
    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      | 0.101             | KGM          | 1         | cu1    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
    And M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber | ValueDate  | Value |
      | cu1     | WeightNet            | 0.101       |            |       |
      | cu1     | HU_BestBeforeDate    |             | 2025-11-08 |       |
      | cu1     | Lot-Nummer           |             |            | 500   |
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID       | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine1 | catchWeightProduct | 1           | 0.101             | 1            | TUx4_CatchWeightProduct | asi1                      |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      | asi1                      | HU_BestBeforeDate | 2025-11-08 00:00:00.0 |
      | asi1                      | Lot-Nummer        | 500                   |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Pick multiple catch weight items and regular items
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | salesOrder            | line1      | catchWeightProduct      | 12         | TUx4_CatchWeightProduct                |
      | salesOrder            | line2      | regularProduct          | 10         | TUx4_RegularProduct                    |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched_CatchWeightProduct | line1                     | N             |
      | sched_RegularProduct     | line2                     | N             |
    And start picking job for sales order identified by salesOrder

    And scan picking slot identified by 200.0

    And pick lines
      | PickingLine.byProduct | PickFromHU    | LMQRCode                   |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.101#08.11.2025#500 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.102#08.11.2025#501 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.103#08.11.2025#502 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.104#08.11.2025#503 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.105#08.11.2025#504 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.106#08.11.2025#505 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.107#08.11.2025#506 |
      | catchWeightProduct    | catchWeightHU | LMQ#1#0.108#08.11.2025#507 |
    And pick lines
      | PickingLine.byProduct | PickFromHU       | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | regularProduct        | regularProductHU | 9         | 1           | N                     | 2027-03-01     | 9876  |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_CatchWeightProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      | 0.101             | KGM          | 1         | cu1    | 0     | -          | 0     | -          | N         |
      | 0.102             | KGM          | 1         | cu2    | 0     | -          | 0     | -          | N         |
      | 0.103             | KGM          | 1         | cu3    | 0     | -          | 0     | -          | N         |
      | 0.104             | KGM          | 1         | cu4    | 0     | -          | 0     | -          | N         |
      | 0.105             | KGM          | 1         | cu5    | 0     | -          | 0     | -          | N         |
      | 0.106             | KGM          | 1         | cu6    | 0     | -          | 0     | -          | N         |
      | 0.107             | KGM          | 1         | cu7    | 0     | -          | 0     | -          | N         |
      | 0.108             | KGM          | 1         | cu8    | 0     | -          | 0     | -          | N         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_RegularProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 9         | cu9    | 0     | -          | 0     | -          | N         |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | sched_CatchWeightProduct         | shipment              | CO            |
      | sched_RegularProduct             | shipment              | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_CatchWeightProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      | 0.101             | KGM          | 1         | cu1    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.102             | KGM          | 1         | cu2    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.103             | KGM          | 1         | cu3    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.104             | KGM          | 1         | cu4    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.105             | KGM          | 1         | cu5    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.106             | KGM          | 1         | cu6    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.107             | KGM          | 1         | cu7    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.108             | KGM          | 1         | cu8    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_RegularProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 9         | cu9    | 1     | tu3        | 1     | lu2        | Y         | shipmentLine2  |
      |                   |              | -4        | cu9    | 1     | tu3        | 1     | lu2        | Y         | shipmentLine2  |
      |                   |              | +4        | cu10   | 1     | tu4        | 1     | lu2        | Y         | shipmentLine2  |
      |                   |              | -4        | cu9    | 1     | tu3        | 1     | lu2        | Y         | shipmentLine2  |
      |                   |              | +4        | cu11   | 1     | tu5        | 1     | lu2        | Y         | shipmentLine2  |

    And M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | cu1     | WeightNet            | 0.101       |
      | cu2     | WeightNet            | 0.102       |
      | cu3     | WeightNet            | 0.103       |
      | cu4     | WeightNet            | 0.104       |
      | cu5     | WeightNet            | 0.105       |
      | cu6     | WeightNet            | 0.106       |
      | cu7     | WeightNet            | 0.107       |
      | cu8     | WeightNet            | 0.108       |
      | tu1     | WeightNet            | 0.410       |
      | tu2     | WeightNet            | 0.426       |
      | lu1     | WeightNet            | 0.836       |

    And M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueDate  |
      # catch weight product:
      | cu1     | HU_BestBeforeDate    | 2025-11-08 |
      | cu2     | HU_BestBeforeDate    | 2025-11-08 |
      | cu3     | HU_BestBeforeDate    | 2025-11-08 |
      | cu4     | HU_BestBeforeDate    | 2025-11-08 |
      | cu5     | HU_BestBeforeDate    | 2025-11-08 |
      | cu6     | HU_BestBeforeDate    | 2025-11-08 |
      | cu7     | HU_BestBeforeDate    | 2025-11-08 |
      | cu8     | HU_BestBeforeDate    | 2025-11-08 |
      | tu1     | HU_BestBeforeDate    | 2025-11-08 |
      | tu2     | HU_BestBeforeDate    | 2025-11-08 |
      | lu1     | HU_BestBeforeDate    | 2025-11-08 |
      # regular product:
      | tu3     | HU_BestBeforeDate    | 2027-03-01 |
      | tu4     | HU_BestBeforeDate    | 2027-03-01 |
      | tu5     | HU_BestBeforeDate    | 2027-03-01 |
      | lu2     | HU_BestBeforeDate    | 2027-03-01 |

    And M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | Value |
      # catch weight product:
      | cu1     | Lot-Nummer           | 500   |
      | cu2     | Lot-Nummer           | 501   |
      | cu3     | Lot-Nummer           | 502   |
      | cu4     | Lot-Nummer           | 503   |
      | cu5     | Lot-Nummer           | 504   |
      | cu6     | Lot-Nummer           | 505   |
      | cu7     | Lot-Nummer           | 506   |
      | cu8     | Lot-Nummer           | 507   |
      | tu1     | Lot-Nummer           | -     |
      | tu2     | Lot-Nummer           | -     |
      | lu1     | Lot-Nummer           | -     |
      # regular product:
      | tu3     | Lot-Nummer           | 9876  |
      | tu4     | Lot-Nummer           | 9876  |
      | tu5     | Lot-Nummer           | 9876  |
      | lu2     | Lot-Nummer           | 9876  |

    And validate the created shipment lines by id
      | Identifier    | M_Product_ID       | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine1 | catchWeightProduct | 8           | 0.836             | 2            | TUx4_CatchWeightProduct | asi1                      |
      | shipmentLine2 | regularProduct     | 9           |                   | 3            | TUx4_RegularProduct     | asi2                      |

    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      # shipment line 1:
      | asi1                      | HU_BestBeforeDate | 2025-11-08 00:00:00.0 |
      | asi1                      | Lot-Nummer        |                       |
      # shipment line 2:
      | asi2                      | HU_BestBeforeDate | 2027-03-01 00:00:00.0 |
      | asi2                      | Lot-Nummer        | 9876                  |
