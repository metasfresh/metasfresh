@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Picking - Pick multiple products to LU

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
      | product1   | PCE      |
      | product2   | PCE      |

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
      | TUx4_P1                            | TU                         | product1                | 4   | 2000-01-01 |
      | TUx4_P2                            | TU                         | product2                | 4   | 2000-01-01 |


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
      | PLV                    | product1     | 6.0      | PCE               | Nominal              | Normal                        |
      | PLV                    | product2     | 8.0      | PCE               | Nominal              | Normal                        |

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
      | inventory                 | line1                         | product1                | 0       | 1000     | PCE          |
      | inventory                 | line2                         | product2                | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | line1              | pickFromCU1 |
      | line2              | pickFromCU2 |


    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Pick TUs from LU with aggregated TUs - into a new LU
    When transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 4           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 4           | 10          |
    And M_HU are validated:
      | M_HU_ID               | HUStatus |
      | pickFromAggregatedLU1 | A        |
      | pickFromAggregatedLU2 | A        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 160        | TUx4_P1                                |
      | SO                    | L2         | product2                | 160        | TUx4_P2                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 3         | 1           | N                     | 2027-03-01     | 9876  |
      | product2              | pickFromAggregatedLU2 | 4         | 0           |                       | 2028-04-02     | 5432  |
    And expect current picking target
      | Existing_LU |
      | lu          |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule1
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 12        | tus_agg1 | 3     | tus_agg1   | 1     | lu         | N         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule2
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 16        | tus_agg2 | 4     | tus_agg2   | 1     | lu         | N         |
    And M_HU are validated:
      | M_HU_ID  | HUStatus |
      | tus_agg1 | S        |
      | tus_agg2 | S        |
      | lu       | S        |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment_20240711_1   | CO            |
      | shipmentSchedule2                | shipment_20240711_1   | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule1
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 12        | tus_agg1 | 3     | tus_agg1   | 1     | lu         | Y         | shipmentLine1  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule2
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 16        | tus_agg2 | 4     | tus_agg2   | 1     | lu         | Y         | shipmentLine2  |

    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine1 | product1     | 12          |                   | 3            | TUx4_P1                 | asi1                      |
      | shipmentLine2 | product2     | 16          |                   | 4            | TUx4_P2                 | asi2                      |

    And M_HU are validated:
      | M_HU_ID  | HUStatus |
      | tus_agg1 | E        |
      | tus_agg2 | E        |
      | lu       | E        |

    And M_HU_Attribute is validated
      | M_HU_ID  | M_Attribute_ID.Value | Value | ValueDate  |
      | tus_agg1 | HU_BestBeforeDate    |       | 2027-03-01 |
      | tus_agg1 | Lot-Nummer           | 9876  |            |
      | tus_agg2 | HU_BestBeforeDate    |       | 2028-04-02 |
      | tus_agg2 | Lot-Nummer           | 5432  |            |
      | lu       | HU_BestBeforeDate    | -     | -          |
      | lu       | Lot-Nummer           | -     | -          |

    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      | asi1                      | HU_BestBeforeDate | 2027-03-01 00:00:00.0 |
      | asi1                      | Lot-Nummer        | 9876                  |
      | asi2                      | HU_BestBeforeDate | 2028-04-02 00:00:00.0 |
      | asi2                      | Lot-Nummer        | 5432                  |







# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Pick from CUs into a new LU
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 160        | TUx4_P1                                |
      | SO                    | L2         | product2                | 160        | TUx4_P2                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU  | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromCU1 | 3         | 1           | N                     | 2027-03-01     | 9876  |
      | product2              | pickFromCU2 | 4         | 0           |                       | 2028-04-02     | 5432  |
    And expect current picking target
      | Existing_LU |
      | lu          |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule1
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 12        | tus_agg1 | 3     | tus_agg1   | 1     | lu         | N         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule2
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      |                   |              | 16        | tus_agg2 | 4     | tus_agg2   | 1     | lu         | N         |
    And M_HU are validated:
      | M_HU_ID  | HUStatus |
      | tus_agg1 | S        |
      | tus_agg2 | S        |
      | lu       | S        |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment_20240711_2   | CO            |
      | shipmentSchedule2                | shipment_20240711_2   | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule1
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 12        | tus_agg1 | 3     | tus_agg1   | 1     | lu         | Y         | shipmentLine1  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule2
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID   | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      |                   |              | 16        | tus_agg2 | 4     | tus_agg2   | 1     | lu         | Y         | shipmentLine2  |

    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine1 | product1     | 12          |                   | 3            | TUx4_P1                 | asi1                      |
      | shipmentLine2 | product2     | 16          |                   | 4            | TUx4_P2                 | asi2                      |

    And M_HU are validated:
      | M_HU_ID  | HUStatus |
      | tus_agg1 | E        |
      | tus_agg2 | E        |
      | lu       | E        |

    And M_HU_Attribute is validated
      | M_HU_ID  | M_Attribute_ID.Value | Value | ValueDate  |
      | tus_agg1 | HU_BestBeforeDate    |       | 2027-03-01 |
      | tus_agg1 | Lot-Nummer           | 9876  |            |
      | tus_agg2 | HU_BestBeforeDate    |       | 2028-04-02 |
      | tus_agg2 | Lot-Nummer           | 5432  |            |
      | lu       | HU_BestBeforeDate    | -     | -          |
      | lu       | Lot-Nummer           | -     | -          |

    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode     | Value                 |
      | asi1                      | HU_BestBeforeDate | 2027-03-01 00:00:00.0 |
      | asi1                      | Lot-Nummer        | 9876                  |
      | asi2                      | HU_BestBeforeDate | 2028-04-02 00:00:00.0 |
      | asi2                      | Lot-Nummer        | 5432                  |
