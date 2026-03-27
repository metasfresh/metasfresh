@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F_QtyReservation
@ghActions:run_on_executor5
Feature: Qty Reservation — shipment attribute and project propagation
## Validates that Qty Reservations correctly propagate attributes and project to the shipment line.
## Covers bugs reported in https://github.com/metasfresh/me03/issues/28126:
## - Bug 1: multi-product SO — each product must get HUs in shipment
## - Bug 2: reservation attributes (Herkunft/Kaliber) must appear on shipment line
## - Bug 3: project on order line must appear on shipment line

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2026-03-15T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_Warehouse:
      | Identifier  |
      | warehouse_1 |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | bp_1       | true       | ps_1               | A            |


  @from:cucumber
  @Id:S_QtyRes_110
  Scenario: Project on order line propagates to shipment line when reservation exists
  ## _Given a product with 1 TU (10 PCE) on-hand stock and a project
  ## _And a sales order line for 10 PCE
  ## _And an M_QtyReservation for 10 PCE with C_Project_ID set
  ## _When shipment is generated
  ## _Then the shipment line has the project from the order line

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory      | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | hu      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu      | ProjectValue         | P60      |

    And metasfresh contains C_Projects:
      | Identifier |
      | project    |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 10         | huPIP_10PCE             |
    And the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier  | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | C_Project_ID |
      | reservation | orderLine      | product      | warehouse_1    | 10 PCE | 1     | project      |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | shipment   | product      | 10          | project      |

    And validate M_QtyReservations:
      | Identifier  | Qty    | QtyDelivered | Processed |
      | reservation | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_120
  Scenario: Reservation from on-hand stock — shipment delivers and closes reservation
  ## _Given a product with 20 PCE on-hand stock (2 TUs)
  ## _And a sales order line for 10 PCE
  ## _And an M_QtyReservation for 10 PCE
  ## _When shipment is generated
  ## _Then QtyReservation is fully delivered (QtyDelivered=10, Processed=true)

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    # Create 20 PCE on-hand stock (2 TUs of 10 PCE each)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID | M_HU_ID2 |
      | inventory      | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 20 PCE   | huPIP_10PCE             | hu1     | hu2      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu1     | ProjectValue         | P70      |
      | hu2     | ProjectValue         | P70      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 20         | huPIP_10PCE             |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    # Reserve 20 PCE (2 complete TUs)
    And metasfresh contains M_QtyReservations:
      | Identifier  | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation | orderLine      | product      | warehouse_1    | 20 PCE | 2     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment   | product      | 20          |

    And validate M_QtyReservations:
      | Identifier  | Qty    | QtyDelivered | Processed |
      | reservation | 20 PCE | 20 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_130
  Scenario: Multi-product SO with separate reservations — each product gets HUs in shipment
  ## _Given two products, each with 1 TU (10 PCE) on-hand stock
  ## _And a sales order with two lines: 10 PCE of each product
  ## _And one M_QtyReservation per order line
  ## _When shipments are generated for both schedules
  ## _Then each shipment line has MovementQty=10
  ## _And both reservations are fully delivered

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product_A  | true      |
      | product_B  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_A_10PCE           | huPIItem        | product_A    | 10 PCE |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_B_10PCE           | huPIItem        | product_B    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_A    | 10.00    | PCE               |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_B    | 10.00    | PCE               |

    # Create inventory: 10 PCE of each product (1 TU each)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_A    | warehouse_1    | 2026-03-15   | product_A    | 0 PCE   | 10 PCE   | huPIP_A_10PCE           | hu_A    |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory_B    | warehouse_1    | 2026-03-15   | product_B    | 0 PCE   | 10 PCE   | huPIP_B_10PCE           | hu_B    |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_A    | ProjectValue         | P80      |
      | hu_B    | ProjectValue         | P80      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine_A | order      | product_A    | 10         | huPIP_A_10PCE           |
      | orderLine_B | order      | product_B    | 10         | huPIP_B_10PCE           |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_A | orderLine_A    | N             |
      | shipmentSchedule_B | orderLine_B    | N             |

    # Create one reservation per order line
    And metasfresh contains M_QtyReservations:
      | Identifier    | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation_A | orderLine_A    | product_A    | warehouse_1    | 10 PCE | 1     |
      | reservation_B | orderLine_B    | product_B    | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_A    |
      | shipmentSchedule_B    |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_A    | D            | true                | false       |
      | shipmentSchedule_B    | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_A    | shipment_A |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_B    | shipment_B |

    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment_A | product_A    | 10          |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment_B | product_B    | 10          |

    And validate M_QtyReservations:
      | Identifier    | Qty    | QtyDelivered | Processed |
      | reservation_A | 10 PCE | 10 PCE       | true      |
      | reservation_B | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_140
  Scenario: HU attribute propagates to shipment line ASI
  ## _Given a product with a Herkunft storage-relevant LIST attribute (Herkunft=DE)
  ## _And a HU created via inventory carrying that ASI
  ## _And a simple M_QtyReservation without attribute filtering
  ## _When the shipment is generated
  ## _Then the shipment line's ASI contains Herkunft=DE (attribute propagated from picked HU)
  ## _Covers Bug 2 reported in https://github.com/metasfresh/me03/issues/28126

    # Custom storage-relevant LIST attribute for attribute-based picking tests
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

    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    # 10 PCE on-hand with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory      | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_DE                    | hu      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft=DE and ProjectValue directly on the HU (inventory step creates the HU with the attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu      | 1000001              | DE       |
      | hu      | ProjectValue         | P_C      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 10         | huPIP_10PCE             |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    # Reservation without attribute filtering (single HU available, picked by FIFO)
    And metasfresh contains M_QtyReservations:
      | Identifier  | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation | orderLine      | product      | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |

    # Store the inout line identifier so we can access it in the "by id" validation step
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment   | product      | 10          | shipLine       |

    # Capture the shipment line's ASI under a new identifier, then assert its attribute values
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine   | asi_shipLine              |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine              | 1000001       | DE    |

    And validate M_QtyReservations:
      | Identifier  | Qty    | QtyDelivered | Processed |
      | reservation | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_150
  Scenario: Attribute-filtered picking — each reservation picks the correct HU by attribute
  ## _Given two HUs for the same product: HU_DE (Herkunft=DE) and HU_AT (Herkunft=AT)
  ## _And two separate sales orders for 10 PCE each
  ## _And one M_QtyReservation per order, each filtered by a different attribute
  ## _When shipments are generated for both schedules
  ## _Then shipment for order D1 has Herkunft=DE on its line
  ## _And  shipment for order D2 has Herkunft=AT on its line
  ## _Covers Bug 2 reported in https://github.com/metasfresh/me03/issues/28126

    # Re-use custom attribute from S_QtyRes_140 (upsert keeps existing if already created)
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
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    # HU_DE: 10 PCE product with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_DE   | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_DE                    | hu_DE   |
    # HU_AT: 10 PCE product with Herkunft=AT
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_AT   | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_AT                    | hu_AT   |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft and ProjectValue directly on each HU (inventory creates HU with attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_DE   | 1000001              | DE       |
      | hu_DE   | ProjectValue         | P_D      |
      | hu_AT   | 1000001              | AT       |
      | hu_AT   | ProjectValue         | P_D      |

    # Order D1: 10 PCE product (will be covered by reservation with Herkunft=DE)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order1     | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine1 | order1     | product      | 10         | huPIP_10PCE             |
    And the order identified by order1 is completed
    # Order D2: 10 PCE product (will be covered by reservation with Herkunft=AT)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order2     | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine2 | order2     | product      | 10         | huPIP_10PCE             |
    And the order identified by order2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule1 | orderLine1     | N             |
      | shipmentSchedule2 | orderLine2     | N             |

    # Reservations filtered by attribute: M_AttributeSetInstance_ID computes the AttributesKey
    # from the ASI's storage-relevant attributes
    And metasfresh contains M_QtyReservations:
      | Identifier     | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | reservation_DE | orderLine1     | product      | warehouse_1    | 10 PCE | 1     | asi_DE                    |
      | reservation_AT | orderLine2     | product      | warehouse_1    | 10 PCE | 1     | asi_AT                    |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule1     | D            | true                | false       |
      | shipmentSchedule2     | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule1     | shipment1  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule2     | shipment2  |

    # Shipment D1 must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment1  | product      | 10          | shipLine1      |
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine1  | asi_shipLine1             |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine1             | 1000001       | DE    |

    # Shipment D2 must carry Herkunft=AT
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment2  | product      | 10          | shipLine2      |
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine2  | asi_shipLine2             |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine2             | 1000001       | AT    |

    And validate M_QtyReservations:
      | Identifier     | Qty    | QtyDelivered | Processed |
      | reservation_DE | 10 PCE | 10 PCE       | true      |
      | reservation_AT | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_160
  Scenario: Plain HU (no Herkunft) — shipment line ASI has no Herkunft value
  ## _Given three HUs for the same product: HU_DE (Herkunft=DE), HU_CH (Herkunft=CH), HU_plain (no Herkunft)
  ## _And three sales orders — E1 reserved from DE HU, E2 from CH HU, E3 reserved without attribute filter (picks plain HU)
  ## _When shipments are generated for all three schedules
  ## _Then shipment for E1 has Herkunft=DE, shipment for E2 has Herkunft=CH
  ## _And  shipment for E3 has no Herkunft value in its line ASI
  ## _Covers Scenario 2 reported in https://github.com/metasfresh/me03/issues/28126

    # Re-use custom attribute from S_QtyRes_140 (upsert keeps existing if already created)
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
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Attribute_ID.Identifier | M_HU_PI_Version_ID.Identifier | M_Attribute.Value |
      | huPIAttr                        | huPIV                         | 1000001           |
      | huPIAttr_tmpl                   | 100                           | 1000001           |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    # HU_E_DE: 10 PCE with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_DE   | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_DE                    | hu_DE   |
    # HU_E_CH: 10 PCE with Herkunft=CH
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_CH   | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_CH                    | hu_CH   |
    # HU_E_plain: 10 PCE with no Herkunft attribute
    And metasfresh contains single line completed inventories
      | M_Inventory_ID  | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID  |
      | inventory_plain | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | hu_plain |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft and ProjectValue directly on each HU (inventory creates HU with attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID  | M_Attribute_ID.Value | ValueStr |
      | hu_DE    | 1000001              | DE       |
      | hu_DE    | ProjectValue         | P_E      |
      | hu_CH    | 1000001              | CH       |
      | hu_CH    | ProjectValue         | P_E      |
      | hu_plain | ProjectValue         | P_E      |

    # Order E1: 10 PCE (reserved from DE HU)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order1     | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine1 | order1     | product      | 10         | huPIP_10PCE             |
    And the order identified by order1 is completed
    # Order E2: 10 PCE (reserved from CH HU)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order2     | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine2 | order2     | product      | 10         | huPIP_10PCE             |
    And the order identified by order2 is completed
    # Order E3: 10 PCE (no attribute filter — reservation picks plain HU by exclusion)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order3     | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine3 | order3     | product      | 10         | huPIP_10PCE             |
    And the order identified by order3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule1 | orderLine1     | N             |
      | shipmentSchedule2 | orderLine2     | N             |
      | shipmentSchedule3 | orderLine3     | N             |

    # Reservations: E1 locked to DE HU, E2 locked to CH HU, E3 no attribute filter (picks remaining plain HU)
    And metasfresh contains M_QtyReservations:
      | Identifier     | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | reservation_DE | orderLine1     | product      | warehouse_1    | 10 PCE | 1     | asi_DE                    |
      | reservation_CH | orderLine2     | product      | warehouse_1    | 10 PCE | 1     | asi_CH                    |
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation_plain | orderLine3     | product      | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |
      | shipmentSchedule3     |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule1     | D            | true                | false       |
      | shipmentSchedule2     | D            | true                | false       |
      | shipmentSchedule3     | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule1     | shipment1  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule2     | shipment2  |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule3     | shipment3  |

    # Shipment E1 must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment1  | product      | 10          | shipLine1      |
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine1  | asi_shipLine1             |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine1             | 1000001       | DE    |

    # Shipment E2 must carry Herkunft=CH
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment2  | product      | 10          | shipLine2      |
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine2  | asi_shipLine2             |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine2             | 1000001       | CH    |

    # Shipment E3 must NOT carry any Herkunft value (plain HU has no Herkunft)
    # No "Value" column in this table → asserts that Herkunft value is null
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment3  | product      | 10          | shipLine3      |
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine3  | asi_shipLine3             |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode |
      | asi_shipLine3             | 1000001       |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | reservation_DE    | 10 PCE | 10 PCE       | true      |
      | reservation_CH    | 10 PCE | 10 PCE       | true      |
      | reservation_plain | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_170
  Scenario: Single order with mixed reservations — each shipment line carries its HU Herkunft attribute
  ## _Given six complete TUs (10 PCE each) for the same product:
  ## _  1 TU with Herkunft=DE, 2 TUs with Herkunft=CH, 3 TUs with no Herkunft
  ## _And a single sales order for 60 PCE (6 TUs) with three reservations on the same order line:
  ## _  10 PCE filtered to DE, 20 PCE filtered to CH, 30 PCE unfiltered (picks plain TUs)
  ## _When shipment is generated
  ## _Then three shipment lines: 10 PCE Herkunft=DE, 20 PCE Herkunft=CH, 30 PCE no Herkunft
  ## _Covers the single-order mixed-source scenario from https://github.com/metasfresh/me03/issues/28126

    # Re-use custom attribute from S_QtyRes_140 (upsert keeps existing if already created)
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
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Attribute_ID.Identifier | M_HU_PI_Version_ID.Identifier | M_Attribute.Value |
      | huPIAttr                        | huPIV                         | 1000001           |
      | huPIAttr_tmpl                   | 100                           | 1000001           |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    # 1 full TU with Herkunft=DE (10 PCE)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_DE   | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_DE                    | hu_DE   |
    # 2 full TUs with Herkunft=CH (10 PCE each)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID | M_HU_ID2 |
      | inventory_CH   | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 20 PCE   | huPIP_10PCE             | asi_CH                    | hu_CH   | hu_CH2   |
    # 3 full TUs with no Herkunft (10 PCE each)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID  | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID  | M_HU_ID2  | M_HU_ID3  |
      | inventory_plain | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 30 PCE   | huPIP_10PCE             | hu_plain | hu_plain2 | hu_plain3 |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft and ProjectValue on each HU (inventory creates HU with attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID   | M_Attribute_ID.Value | ValueStr |
      | hu_DE     | 1000001              | DE       |
      | hu_DE     | ProjectValue         | P_170    |
      | hu_CH     | 1000001              | CH       |
      | hu_CH     | ProjectValue         | P_170    |
      | hu_CH2    | 1000001              | CH       |
      | hu_CH2    | ProjectValue         | P_170    |
      | hu_plain  | ProjectValue         | P_170    |
      | hu_plain2 | ProjectValue         | P_170    |
      | hu_plain3 | ProjectValue         | P_170    |

    # Single order for 60 PCE (6 TUs) — all three reservations reference this same order line
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 60         | huPIP_10PCE             |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    # Three reservations on the same order line: 1 TU DE, 2 TUs CH, 3 TUs plain
    And metas fresh contains M_QtyReservations:
      | Identifier     | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | reservation_DE | orderLine      | product      | warehouse_1    | 10 PCE | 1     | asi_DE                    |
      | reservation_CH | orderLine      | product      | warehouse_1    | 20 PCE | 2     | asi_CH                    |
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation_plain | orderLine      | product      | warehouse_1    | 30 PCE | 3     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |

    # One shipment with three lines — one per attribute group (10/20/30 PCE)
    # Line with 10 PCE: from DE TU — must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_InOutLine_ID |
      | shipment   | product      | 10          | shipLine_DE    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_DE | asi_shipLine_DE           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_DE           | 1000001       | DE    |

    # Line with 20 PCE: from 2 CH TUs — must carry Herkunft=CH
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_InOutLine_ID |
      | shipment   | product      | 20          | shipLine_CH    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_CH | asi_shipLine_CH           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_CH           | 1000001       | CH    |

    # Line with 30 PCE: from 3 plain TUs — must NOT carry any Herkunft value
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_InOutLine_ID |
      | shipment   | product      | 30          | shipLine_plain |
    And validate the created shipment lines by id
      | Identifier     | M_AttributeSetInstance_ID |
      | shipLine_plain | asi_shipLine_plain        |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode |
      | asi_shipLine_plain        | 1000001       |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | reservation_DE    | 10 PCE | 10 PCE       | true      |
      | reservation_CH    | 20 PCE | 20 PCE       | true      |
      | reservation_plain | 30 PCE | 30 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_180
  Scenario: Shipment reversal restores reservation — QtyDelivered drops back to 0
  ## _Given a product with 1 TU (10 PCE on-hand) and a sales order for 10 PCE
  ## _And an M_QtyReservation for 10 PCE
  ## _When shipment is generated, then reversed
  ## _Then QtyDelivered goes back to 0 and Processed=false (reservation restored)

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory      | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | hu      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu      | ProjectValue         | P180     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 10         | huPIP_10PCE             |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier  | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | reservation | orderLine      | product      | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |

    # Verify reservation is fully delivered after shipment
    And validate M_QtyReservations:
      | Identifier  | Qty    | QtyDelivered | Processed |
      | reservation | 10 PCE | 10 PCE       | true      |

    # Now reverse the shipment
    And the shipment identified by shipment is reversed as shipmentReversal

    # After reversal, reservation must be restored: QtyDelivered=0, Processed=false
    And validate M_QtyReservations:
      | Identifier  | Qty    | QtyDelivered | Processed |
      | reservation | 10 PCE | 0 PCE        | false     |


  @from:cucumber
  @Id:S_QtyRes_190
  Scenario: Normal shipment without reservations — regression test for multi-pass picking
  ## _Given a product with 1 TU (10 PCE on-hand) and a sales order for 10 PCE
  ## _And NO M_QtyReservation records exist
  ## _When shipment is generated
  ## _Then shipment line has MovementQty=10 (Pass 2 is a no-op, Pass 3 picks normally)

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV              | huPI       | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem        | huPIV              | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_10PCE             | huPIItem        | product      | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product      | 10.00    | PCE               |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID |
      | inventory      | warehouse_1    | 2026-03-15   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | hu      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | bp_1          | 2026-03-15  | warehouse_1    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | orderLine  | order      | product      | 10         | huPIP_10PCE             |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule      | shipment   |
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty |
      | shipment   | product      | 10          |
