@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Picking - Pick mixed lines

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
      | Identifier         | X12DE355 |
      | catchWeightProduct | PCE      |
      | regularTUProduct   | PCE      |
      | regularCUProduct   | PCE      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | catchWeightProduct      | PCE                    | KGM                  | 0.10         | Y                        |

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
      | TUx4_CatchWeightProduct            | TU                         | catchWeightProduct      | 4   | 2000-01-01 |
      | TUx4_RegularTUProduct              | TU                         | regularTUProduct        | 4   | 2000-01-01 |


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
      | PLV                    | regularTUProduct   | 6.0      | PCE               | Nominal              | Normal                        |
      | PLV                    | regularCUProduct   | 7.0      | PCE               | Nominal              | Normal                        |

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
      | inventory                 | line2                         | regularTUProduct        | 0       | 1000     | PCE          |
      | inventory                 | line3                         | regularCUProduct        | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID            |
      | line1              | catchWeightHU      |
      | line2              | regularTUProductHU |
      | line3              | regularCUProductHU |

    
    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Pick multiple catch weight items and regular items
    When set sys config boolean value false for sys config PickingJobPickCommand.PickCUsFromCUs
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | salesOrder            | L1         | catchWeightProduct      | 12         | TUx4_CatchWeightProduct                |
      | salesOrder            | L2         | regularTUProduct        | 400        | TUx4_RegularTUProduct                  |
      | salesOrder            | L3         | regularCUProduct        | 9          |                                        |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched_CatchWeightProduct | L1                        | N             |
      | sched_RegularTUProduct   | L2                        | N             |
      | sched_RegularCUProduct   | L3                        | N             |
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
      | PickingLine.byProduct | PickFromHU         | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | regularTUProduct      | regularTUProductHU | 9         | 1           | N                     | 2027-03-01     | 9876  |
    And pick lines
      | PickingLine.byProduct | PickFromHU         | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | regularCUProduct      | regularCUProductHU | 9         | 1           | N                     | 2028-03-01     | 10876 |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_CatchWeightProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      | 0.101             | KGM          | 1         | L1_cu1 | 0     | -          | 0     | -          | N         |
      | 0.102             | KGM          | 1         | L1_cu2 | 0     | -          | 0     | -          | N         |
      | 0.103             | KGM          | 1         | L1_cu3 | 0     | -          | 0     | -          | N         |
      | 0.104             | KGM          | 1         | L1_cu4 | 0     | -          | 0     | -          | N         |
      | 0.105             | KGM          | 1         | L1_cu5 | 0     | -          | 0     | -          | N         |
      | 0.106             | KGM          | 1         | L1_cu6 | 0     | -          | 0     | -          | N         |
      | 0.107             | KGM          | 1         | L1_cu7 | 0     | -          | 0     | -          | N         |
      | 0.108             | KGM          | 1         | L1_cu8 | 0     | -          | 0     | -          | N         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_RegularTUProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 4         | -      | 1     | L2_tu1     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu2     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu3     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu4     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu5     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu6     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu7     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu8     | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | L2_tu9     | 0     | -          | N         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_RegularCUProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 9         | L3_cu1 | 0     | -          | 0     | -          | N         |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | sched_CatchWeightProduct         | shipment              | CO            |
      | sched_RegularTUProduct           | shipment              | CO            |
      | sched_RegularCUProduct           | shipment              | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_CatchWeightProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      | 0.101             | KGM          | 1         | L1_cu1 | 1     | L1_tu1     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.102             | KGM          | 1         | L1_cu2 | 1     | L1_tu1     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.103             | KGM          | 1         | L1_cu3 | 1     | L1_tu1     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.104             | KGM          | 1         | L1_cu4 | 1     | L1_tu1     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.105             | KGM          | 1         | L1_cu5 | 1     | L1_tu2     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.106             | KGM          | 1         | L1_cu6 | 1     | L1_tu2     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.107             | KGM          | 1         | L1_cu7 | 1     | L1_tu2     | 1     | L1_lu      | Y         | shipmentLine1  |
      | 0.108             | KGM          | 1         | L1_cu8 | 1     | L1_tu2     | 1     | L1_lu      | Y         | shipmentLine1  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_RegularTUProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 4         | -      | 1     | L2_tu1     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu2     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu3     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu4     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu5     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu6     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu7     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu8     | 1     | L2_lu      | Y         | shipmentLine2  |
      |                   |              | 4         | -      | 1     | L2_tu9     | 1     | L2_lu      | Y         | shipmentLine2  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by sched_RegularCUProduct
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 9         | L3_cu1 | 0     | -          | 0     | -          | Y         | shipmentLine3  |

    And M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | L1_cu1  | WeightNet            | 0.101       |
      | L1_cu2  | WeightNet            | 0.102       |
      | L1_cu3  | WeightNet            | 0.103       |
      | L1_cu4  | WeightNet            | 0.104       |
      | L1_cu5  | WeightNet            | 0.105       |
      | L1_cu6  | WeightNet            | 0.106       |
      | L1_cu7  | WeightNet            | 0.107       |
      | L1_cu8  | WeightNet            | 0.108       |
      | L1_tu1  | WeightNet            | 0.410       |
      | L1_tu2  | WeightNet            | 0.426       |
      | L1_lu   | WeightNet            | 0.836       |

    And M_HU_Attribute is validated
      | M_HU_ID                                                        | M_Attribute_ID.Value | ValueDate  |
      # catch weight product:
      | L1_cu1,L1_cu2,L1_cu3,L1_cu4,L1_cu5,L1_cu6,L1_cu7,L1_cu8        | HU_BestBeforeDate    | 2025-11-08 |
      | L1_tu1                                                         | HU_BestBeforeDate    | 2025-11-08 |
      | L1_tu2                                                         | HU_BestBeforeDate    | 2025-11-08 |
      | L1_lu                                                          | HU_BestBeforeDate    | 2025-11-08 |
      # regular TU product:
      | L2_tu1,L2_tu2,L2_tu3,L2_tu4,L2_tu5,L2_tu6,L2_tu7,L2_tu8,L2_tu9 | HU_BestBeforeDate    | 2027-03-01 |
      | L2_lu                                                          | HU_BestBeforeDate    | 2027-03-01 |
    # regular CU product
      | L3_cu1                                                         | HU_BestBeforeDate    | 2028-03-01 |

    And M_HU_Attribute is validated
      | M_HU_ID                                                        | M_Attribute_ID.Value | Value |
      # catch weight product:
      | L1_cu1                                                         | Lot-Nummer           | 500   |
      | L1_cu2                                                         | Lot-Nummer           | 501   |
      | L1_cu3                                                         | Lot-Nummer           | 502   |
      | L1_cu4                                                         | Lot-Nummer           | 503   |
      | L1_cu5                                                         | Lot-Nummer           | 504   |
      | L1_cu6                                                         | Lot-Nummer           | 505   |
      | L1_cu7                                                         | Lot-Nummer           | 506   |
      | L1_cu8                                                         | Lot-Nummer           | 507   |
      | L1_tu1                                                         | Lot-Nummer           | -     |
      | L1_tu2                                                         | Lot-Nummer           | -     |
      | L1_lu                                                          | Lot-Nummer           | -     |
      # regular TU product:
      | L2_tu1,L2_tu2,L2_tu3,L2_tu4,L2_tu5,L2_tu6,L2_tu7,L2_tu8,L2_tu9 | Lot-Nummer           | 9876  |
      | L2_lu                                                          | Lot-Nummer           | 9876  |
      # regular CU product:
      | L3_cu1                                                         | Lot-Nummer           | 10876 |

    And validate the created shipment lines by id
      | Identifier    | M_Product_ID       | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine1 | catchWeightProduct | 8           | 0.836             | 2            | TUx4_CatchWeightProduct | asi1                      |
      | shipmentLine2 | regularTUProduct   | 36          |                   | 9            | TUx4_RegularTUProduct   | asi2                      |
      | shipmentLine3 | regularCUProduct   | 9           |                   | 0            |                         | asi3                      |

    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      # shipment line 1:
      | asi1                      | HU_BestBeforeDate | 2025-11-08 00:00:00.0 |
      | asi1                      | Lot-Nummer        |                       |
      # shipment line 2:
      | asi2                      | HU_BestBeforeDate | 2027-03-01 00:00:00.0 |
      | asi2                      | Lot-Nummer        | 9876                  |
      # shipment line 3:
      | asi3                      | HU_BestBeforeDate | 2028-03-01 00:00:00.0 |
      | asi3                      | Lot-Nummer        | 10876                 |
