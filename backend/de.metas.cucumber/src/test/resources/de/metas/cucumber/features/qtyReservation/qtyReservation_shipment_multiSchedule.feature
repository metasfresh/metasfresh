@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00200_Automatic_Picking
@ghActions:run_on_executor5
Feature: Multi-schedule on-the-fly picking — no double-pick, respect reservations
## Validates that when a single order with multiple lines for the same product
## creates multiple shipment schedules processed in one workpackage,
## each schedule picks the correct HU and reservations are respected.
## Covers https://github.com/metasfresh/me03/issues/29333

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-04-16T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl         | ps                 | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | customer   | true       | ps                 | A            |


  @from:cucumber
  @Id:S_QtyRes_200
  Scenario: Reserved HU respected — unreserved line processed first must skip reserved VHU
  ## _Given 1 order with 2 lines for the same product (10 PCE each)
  ## _And 2 TUs: hu_reserved (Herkunft=DE) reserved for line 1, hu_unreserved (Herkunft=AT)
  ## _When shipments are generated in one workpackage with line 2 FIRST
  ## _Then line 2 picks hu_unreserved (Herkunft=AT), line 1 picks hu_reserved (Herkunft=DE)

    Given metasfresh contains M_Attributes:
      | Identifier | Value   | IsStorageRelevant |
      | attr_QRH   | 1000001 | true              |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"1000001",
          "valueStr":"DE"
        }
      ]
    }
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_AT":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"1000001",
          "valueStr":"AT"
        }
      ]
    }
    """

    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI        |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV               | huPI        | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem         | huPIV               | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE              | huPIItem         | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv                    | product      | 10.00    | PCE               |

    # TU reserved: 10 PCE with Herkunft=DE (will be reserved for line 1)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID     |
      | inventory_DE   | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | asi_DE                    | hu_reserved |
    # TU unreserved: 10 PCE with Herkunft=AT
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID       |
      | inventory_AT   | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | asi_AT                    | hu_unreserved |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And M_HU_Attribute is changed
      | M_HU_ID       | M_Attribute_ID.Value | ValueStr |
      | hu_reserved   | 1000001              | DE       |
      | hu_unreserved | 1000001              | AT       |

    # Single order with 2 lines for the same product
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_1 | order      | product      | 10         | huPIP_10PCE              |
      | orderLine_2 | order      | product      | 10         | huPIP_10PCE              |
    And the order identified by order is completed

    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_1   | orderLine_1    | N             |
      | shipmentSchedule_2   | orderLine_2    | N             |

    # Reserve hu_reserved for order line 1
    And reserve HU to order
      | C_OrderLine_ID | M_HU_ID     |
      | orderLine_1    | hu_reserved |

    # Generate shipments in one workpackage — line 2 (unreserved) FIRST.
    # This validates the reservation policy: line 2 must skip hu_reserved on its own merit,
    # not because line 1 consumed it earlier via alreadyUsedSourceHuIds.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_2    |
      | shipmentSchedule_1    |

    Then after not more than 120s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_1    | shipment   |

    # Verify both schedules were picked (10 PCE each, on-the-fly)
    And validate M_ShipmentSchedule_QtyPicked:
      | M_ShipmentSchedule_ID | QtyPicked | IsAnonymousHuPickedOnTheFly |
      | shipmentSchedule_1    | 10        | true                        |
      | shipmentSchedule_2    | 10        | true                        |


  @from:cucumber
  @Id:S_QtyRes_210
  Scenario: Both unreserved, same product — no double-pick in single workpackage
  ## _Given 1 order with 2 lines for the same product (10 PCE each)
  ## _And 2 TUs of 10 PCE each (both unreserved)
  ## _When shipments are generated in one workpackage
  ## _Then both lines get distinct HUs, no crash
  ## _Regression test for https://github.com/metasfresh/me03/issues/29333

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI        |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV               | huPI        | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem         | huPIV               | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE              | huPIItem         | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv                    | product      | 10.00    | PCE               |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_1    | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | hu_1    |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_2    | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | hu_2    |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Single order with 2 lines for the same product
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_1 | order      | product      | 10         | huPIP_10PCE              |
      | orderLine_2 | order      | product      | 10         | huPIP_10PCE              |
    And the order identified by order is completed

    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_1   | orderLine_1    | N             |
      | shipmentSchedule_2   | orderLine_2    | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_1    |
      | shipmentSchedule_2    |

    Then after not more than 120s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_1    | shipment   |
    And validate M_ShipmentSchedule_QtyPicked:
      | M_ShipmentSchedule_ID | QtyPicked | IsAnonymousHuPickedOnTheFly |
      | shipmentSchedule_1    | 10        | true                        |
      | shipmentSchedule_2    | 10        | true                        |


  @from:cucumber
  @Id:S_QtyRes_220
  Scenario: Both unreserved, ASI-filtered — each line picks the correctly attributed HU
  ## _Given 1 order with 2 lines for the same product (10 PCE each)
  ## _And 2 TUs: hu_DE (Herkunft=DE) and hu_CH (Herkunft=CH)
  ## _And M_QtyReservation with ASI for each line (DE→line 1, CH→line 2)
  ## _When shipments are generated in one workpackage
  ## _Then schedule DE picks hu_DE, schedule CH picks hu_CH

    Given metasfresh contains M_Attributes:
      | Identifier | Value   | IsStorageRelevant |
      | attr_QRH   | 1000001 | true              |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"1000001",
          "valueStr":"DE"
        }
      ]
    }
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_CH":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"1000001",
          "valueStr":"CH"
        }
      ]
    }
    """

    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI        |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV               | huPI        | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem         | huPIV               | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE              | huPIItem         | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv                    | product      | 10.00    | PCE               |

    # HU_DE: 10 PCE with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_DE   | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | asi_DE                    | hu_DE   |
    # HU_CH: 10 PCE with Herkunft=CH
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_CH   | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | asi_CH                    | hu_CH   |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_DE   | 1000001              | DE       |
      | hu_CH   | 1000001              | CH       |

    # Single order with 2 lines for the same product
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_DE | order      | product      | 10         | huPIP_10PCE              |
      | orderLine_CH | order      | product      | 10         | huPIP_10PCE              |
    And the order identified by order is completed

    And after not more than 120s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_DE  | orderLine_DE   | N             |
      | shipmentSchedule_CH  | orderLine_CH   | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier     | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | reservation_DE | orderLine_DE   | product      | warehouse      | 10 PCE | 1     | asi_DE                    |
      | reservation_CH | orderLine_CH   | product      | warehouse      | 10 PCE | 1     | asi_CH                    |

    And after not more than 120s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_DE   |
      | shipmentSchedule_CH   |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_DE   |
      | shipmentSchedule_CH   |

    Then after not more than 120s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_DE   | shipment   |

    # Verify both schedules were picked
    And validate M_ShipmentSchedule_QtyPicked:
      | M_ShipmentSchedule_ID | QtyPicked | IsAnonymousHuPickedOnTheFly |
      | shipmentSchedule_DE   | 10        | true                        |
      | shipmentSchedule_CH   | 10        | true                        |

    And validate M_QtyReservations:
      | Identifier     | Qty    | QtyDelivered | Processed |
      | reservation_DE | 10 PCE | 10 PCE       | true      |
      | reservation_CH | 10 PCE | 10 PCE       | true      |
