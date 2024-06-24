@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Picking - Pick TUs from CU

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
      | inventory                 | line1                         | product                 | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | line1              | pickFromCU |

    
    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: When PickingJobPickCommand.PickCUsFromCUs=Y
    When set sys config boolean value true for sys config PickingJobPickCommand.PickCUsFromCUs
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 160        | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product               | pickFromCU | 3         | 1           | N                     | 2027-03-01     | 9876  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 12        | vhu1   | 0     | -          | 0     | -          | N         |
    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | vhu1    | S        |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 12        | vhu1   | 1     | tu1        | 1     | lu1        | Y         | shipmentLine   |
      |                   |              | -4        | vhu1   | 1     | tu1        | 1     | lu1        | Y         | shipmentLine   |
      |                   |              | +4        | vhu2   | 1     | tu2        | 1     | lu1        | Y         | shipmentLine   |
      |                   |              | -4        | vhu1   | 1     | tu1        | 1     | lu1        | Y         | shipmentLine   |
      |                   |              | +4        | vhu3   | 1     | tu3        | 1     | lu1        | Y         | shipmentLine   |

    And validate the created shipment lines by id
      | Identifier   | M_Product_ID | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine | product      | 12          |                   | 3            | TUx4                    | asi                       |

    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | tu1     | E        |
      | tu2     | E        |
      | tu3     | E        |
      | lu1     | E        |

    And M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | Value | ValueDate  |
      | tu1,tu2,tu3 | HU_BestBeforeDate    |       | 2027-03-01 |
      | lu1         | HU_BestBeforeDate    |       | 2027-03-01 |
      | tu1,tu2,tu3 | Lot-Nummer           | 9876  |            |
      | lu1         | Lot-Nummer           | 9876  |            |

    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      | asi                       | HU_BestBeforeDate | 2027-03-01 00:00:00.0 |
      | asi                       | Lot-Nummer        | 9876                  |

    

    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: When PickingJobPickCommand.PickCUsFromCUs=N
    When set sys config boolean value false for sys config PickingJobPickCommand.PickCUsFromCUs
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product                 | 160        | TUx4                                   |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | L1                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product               | pickFromCU | 3         | 1           | N                     | 2027-03-01     | 9876  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 4         | -      | 1     | tu1        | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | tu2        | 0     | -          | N         |
      |                   |              | 4         | -      | 1     | tu3        | 0     | -          | N         |
    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | tu1     | S        |
      | tu2     | S        |
      | tu3     | S        |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 4         | -      | 1     | tu1        | 1     | lu1        | Y         | shipmentLine   |
      |                   |              | 4         | -      | 1     | tu2        | 1     | lu1        | Y         | shipmentLine   |
      |                   |              | 4         | -      | 1     | tu3        | 1     | lu1        | Y         | shipmentLine   |

    And validate the created shipment lines by id
      | Identifier   | M_Product_ID | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine | product      | 12          |                   | 3            | TUx4                    | asi                       |

    And M_HU are validated:
      | M_HU_ID | HUStatus |
      | tu1     | E        |
      | tu2     | E        |
      | tu3     | E        |
      | lu1     | E        |

    And M_HU_Attribute is validated
      | M_HU_ID     | M_Attribute_ID.Value | Value | ValueDate  |
      | tu1,tu2,tu3 | HU_BestBeforeDate    |       | 2027-03-01 |
      | lu1         | HU_BestBeforeDate    |       | 2027-03-01 |
      | tu1,tu2,tu3 | Lot-Nummer           | 9876  |            |
      | lu1         | Lot-Nummer           | 9876  |            |

    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      | asi                       | HU_BestBeforeDate | 2027-03-01 00:00:00.0 |
      | asi                       | Lot-Nummer        | 9876                  |

    

    
    
    
    
    
