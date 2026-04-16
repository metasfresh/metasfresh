@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00200_Automatic_Picking
@ghActions:run_on_executor5
Feature: Multi-schedule on-the-fly picking — no double-pick, respect reservations
## Validates that when multiple shipment schedules for the same product are processed
## in one workpackage, each schedule picks distinct HUs and reservations are respected.
## Covers https://github.com/metasfresh/me03/issues/29333

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-04-16T13:30:13+02:00[Europe/Berlin]
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
  Scenario: Reserved HU respected — unreserved schedule processed first must skip reserved VHU
  ## _Given 2 TUs of 10 PCE each
  ## _And 2 sales orders for 10 PCE each
  ## _And HU_A is reserved for order A
  ## _When shipments are generated in one workpackage, with order B FIRST
  ## _Then order B skips the reserved HU (reservation policy), order A gets it

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

    # TU A: 10 PCE (will be reserved for order A)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_A    | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | hu_A    |
    # TU B: 10 PCE (unreserved)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_B    | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | hu_B    |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Order A: 10 PCE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_A    | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_A | order_A    | product      | 10         | huPIP_10PCE              |
    And the order identified by order_A is completed
    # Order B: 10 PCE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_B    | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_B | order_B    | product      | 10         | huPIP_10PCE              |
    And the order identified by order_B is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_A   | orderLine_A    | N             |
      | shipmentSchedule_B   | orderLine_B    | N             |

    # Reserve hu_A for order A
    And reserve HU to order
      | C_OrderLine_ID | M_HU_ID |
      | orderLine_A    | hu_A    |

    # Generate shipments in one workpackage — B (unreserved) FIRST, then A (reserved).
    # This validates the reservation policy: B must skip the reserved VHU on its own merit,
    # not because A consumed it earlier via alreadyUsedSourceHuIds.
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_B    |
      | shipmentSchedule_A    |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_A    | shipment_A |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_B    | shipment_B |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment_A | product      | 10          |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment_B | product      | 10          |


  @from:cucumber
  @Id:S_QtyRes_210
  Scenario: Both unreserved, same product — no double-pick in single workpackage
  ## _Given 2 TUs of 10 PCE each (both unreserved)
  ## _And 2 sales orders for 10 PCE each
  ## _When shipments are generated in one workpackage
  ## _Then both shipments created, no crash, distinct HUs picked
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

    # TU 1: 10 PCE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_1    | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | hu_1    |
    # TU 2: 10 PCE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_2    | warehouse      | 2026-04-16   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE              | hu_2    |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Order 1: 10 PCE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_1    | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_1 | order_1    | product      | 10         | huPIP_10PCE              |
    And the order identified by order_1 is completed
    # Order 2: 10 PCE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_2    | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_2 | order_2    | product      | 10         | huPIP_10PCE              |
    And the order identified by order_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_1   | orderLine_1    | N             |
      | shipmentSchedule_2   | orderLine_2    | N             |

    # Generate shipments in one workpackage (both schedules together)
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_1    |
      | shipmentSchedule_2    |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_1    | shipment_1 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_2    | shipment_2 |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment_1 | product      | 10          |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment_2 | product      | 10          |


  @from:cucumber
  @Id:S_QtyRes_220
  Scenario: Both unreserved, ASI-filtered — each schedule picks the correctly attributed HU
  ## _Given 2 TUs: hu_DE (Herkunft=DE) and hu_CH (Herkunft=CH), each 10 PCE
  ## _And 2 sales orders for 10 PCE each
  ## _And M_QtyReservation with ASI for each order (DE→order 1, CH→order 2)
  ## _When shipments are generated in one workpackage
  ## _Then shipment 1 has Herkunft=DE, shipment 2 has Herkunft=CH

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

    # Order 1: 10 PCE (reservation picks DE)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_DE   | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_DE | order_DE   | product      | 10         | huPIP_10PCE              |
    And the order identified by order_DE is completed
    # Order 2: 10 PCE (reservation picks CH)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_CH   | true    | customer      | 2026-04-16  | warehouse      | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_CH | order_CH   | product      | 10         | huPIP_10PCE              |
    And the order identified by order_CH is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_DE  | orderLine_DE   | N             |
      | shipmentSchedule_CH  | orderLine_CH   | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier     | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | reservation_DE | orderLine_DE   | product      | warehouse      | 10 PCE | 1     | asi_DE                    |
      | reservation_CH | orderLine_CH   | product      | warehouse      | 10 PCE | 1     | asi_CH                    |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_DE   |
      | shipmentSchedule_CH   |

    # Generate shipments in one workpackage (both schedules together)
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_DE   |
      | shipmentSchedule_CH   |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_DE   | shipment_DE |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_CH   | shipment_CH |

    # Shipment DE must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_DE | product      | 10          | shipLine_DE    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_DE | asi_shipLine_DE           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_DE           | 1000001       | DE    |

    # Shipment CH must carry Herkunft=CH
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_CH | product      | 10          | shipLine_CH    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_CH | asi_shipLine_CH           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_CH           | 1000001       | CH    |

    And validate M_QtyReservations:
      | Identifier     | Qty    | QtyDelivered | Processed |
      | reservation_DE | 10 PCE | 10 PCE       | true      |
      | reservation_CH | 10 PCE | 10 PCE       | true      |
