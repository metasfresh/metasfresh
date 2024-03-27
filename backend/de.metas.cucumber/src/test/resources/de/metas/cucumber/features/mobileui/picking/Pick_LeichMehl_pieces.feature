@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Picking - Test for single piece L+M items

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    And metasfresh contains M_Products:
      | Identifier | Name           |
      | product    | product @Date@ |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | product                 | PCE                    | KGM                  | 0.10         | Y                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name      |
      | TU                    | TU @Date@ |
      | LU                    | LU @Date@ |
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
      | Identifier | Name      |
      | PS         | PS @Date@ |
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
      | inventory                 | line                          | product                 | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | line                          | pickFromHU         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | salesOrderLine | salesOrder            | product                 | 12         | TUx4                                   |
    And the order identified by salesOrder is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | salesOrderLine            | N             |


  @from:cucumber
  Scenario: start a new picking job, pick L+M single unit items, complete the picking => ship the goods
    And start picking job
      | C_Order_ID |
      | salesOrder |

    And scan picking slot
      | PickingSlot |
      | 200.0       |

    And pick lines
      | PickFromHU | LMQRCode                   |
      | pickFromHU | LMQ#1#0.101#01.11.2025#500 |
      | pickFromHU | LMQ#1#0.102#02.11.2025#501 |
      | pickFromHU | LMQ#1#0.103#03.11.2025#502 |
      | pickFromHU | LMQ#1#0.104#04.11.2025#503 |
      | pickFromHU | LMQ#1#0.105#05.11.2025#504 |
      | pickFromHU | LMQ#1#0.106#06.11.2025#505 |
      | pickFromHU | LMQ#1#0.107#07.11.2025#506 |
      | pickFromHU | LMQ#1#0.108#08.11.2025#507 |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed |
      | 0.101             | KGM          | 1         | cu1    | 0     | -          | 0     | -          | N         |
      | 0.102             | KGM          | 1         | cu2    | 0     | -          | 0     | -          | N         |
      | 0.103             | KGM          | 1         | cu3    | 0     | -          | 0     | -          | N         |
      | 0.104             | KGM          | 1         | cu4    | 0     | -          | 0     | -          | N         |
      | 0.105             | KGM          | 1         | cu5    | 0     | -          | 0     | -          | N         |
      | 0.106             | KGM          | 1         | cu6    | 0     | -          | 0     | -          | N         |
      | 0.107             | KGM          | 1         | cu7    | 0     | -          | 0     | -          | N         |
      | 0.108             | KGM          | 1         | cu8    | 0     | -          | 0     | -          | N         |

    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule                 | shipment              | CO            |

    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | VHU_ID | QtyTU | M_TU_HU_ID | QtyLU | M_LU_HU_ID | Processed | M_InOutLine_ID |
      | 0.101             | KGM          | 1         | cu1    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.102             | KGM          | 1         | cu2    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.103             | KGM          | 1         | cu3    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.104             | KGM          | 1         | cu4    | 1     | tu1        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.105             | KGM          | 1         | cu5    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.106             | KGM          | 1         | cu6    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.107             | KGM          | 1         | cu7    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |
      | 0.108             | KGM          | 1         | cu8    | 1     | tu2        | 1     | lu1        | Y         | shipmentLine1  |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | ValueNumber |
      | cu1                | WeightNet            | 0.101       |
      | cu2                | WeightNet            | 0.102       |
      | cu3                | WeightNet            | 0.103       |
      | cu4                | WeightNet            | 0.104       |
      | cu5                | WeightNet            | 0.105       |
      | cu6                | WeightNet            | 0.106       |
      | cu7                | WeightNet            | 0.107       |
      | cu8                | WeightNet            | 0.108       |
      | tu1                | WeightNet            | 0.410       |
      | tu2                | WeightNet            | 0.426       |
      | lu1                | WeightNet            | 0.836       |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | ValueDate  |
      | cu1                | HU_BestBeforeDate    | 2025-11-01 |
      | cu2                | HU_BestBeforeDate    | 2025-11-02 |
      | cu3                | HU_BestBeforeDate    | 2025-11-03 |
      | cu4                | HU_BestBeforeDate    | 2025-11-04 |
      | cu5                | HU_BestBeforeDate    | 2025-11-05 |
      | cu6                | HU_BestBeforeDate    | 2025-11-06 |
      | cu7                | HU_BestBeforeDate    | 2025-11-07 |
      | cu8                | HU_BestBeforeDate    | 2025-11-08 |
      | tu1                | HU_BestBeforeDate    | -          |
      | tu2                | HU_BestBeforeDate    | -          |
      | lu1                | HU_BestBeforeDate    | -          |

    And M_HU_Attribute is validated
      | M_HU_ID.Identifier | M_Attribute_ID.Value | Value |
      | cu1                | Lot-Nummer           | 500   |
      | cu2                | Lot-Nummer           | 501   |
      | cu3                | Lot-Nummer           | 502   |
      | cu4                | Lot-Nummer           | 503   |
      | cu5                | Lot-Nummer           | 504   |
      | cu6                | Lot-Nummer           | 505   |
      | cu7                | Lot-Nummer           | 506   |
      | cu8                | Lot-Nummer           | 507   |
      | tu1                | Lot-Nummer           | -     |
      | tu2                | Lot-Nummer           | -     |
      | lu1                | Lot-Nummer           | -     |

    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch | QtyEnteredTU | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | shipmentLine1 | product      | 8           | 0.836             | 2            | TUx4                    | asi1                      |
