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
  ## _And a sales order line for 10 PCE with C_Project_ID set
  ## _And an M_QtyReservation for 10 PCE
  ## _When shipment is generated
  ## _Then the shipment line has the project from the order line

    Given metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product_60 | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_60    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_60           | huPI_60    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_60     | huPIV_60           | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_60_10PCE          | huPIItem_60     | product_60   | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_60   | 10.00    | PCE               |

    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID |
      | inventory_60   | warehouse_1    | 2026-03-15   | product_60   | 0 PCE   | 10 PCE   | huPIP_60_10PCE          |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh contains C_Projects:
      | Identifier |
      | project_60 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_60   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_60 | order_60   | product_60   | 10         |
    And the order identified by order_60 is completed

    # Set project on order line (simulates reservation inheriting project to OL)
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Project_ID.Identifier |
      | orderLine_60              | project_60                  |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_60 | orderLine_60   | N             |

    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_60 | orderLine_60   | product_60   | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_60   |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_60   | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_60   | shipment_60 |
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | C_Project_ID |
      | shipment_60 | product_60   | 10          | project_60   |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | qtyReservation_60 | 10 PCE | 10 PCE       | true      |


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
      | product_70 | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_70    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_70           | huPI_70    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_70     | huPIV_70           | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_70_10PCE          | huPIItem_70     | product_70   | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_70   | 10.00    | PCE               |

    # Create 20 PCE on-hand stock (2 TUs of 10 PCE each)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID |
      | inventory_70   | warehouse_1    | 2026-03-15   | product_70   | 0 PCE   | 20 PCE   | huPIP_70_10PCE          |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_70   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_70 | order_70   | product_70   | 10         |
    And the order identified by order_70 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_70 | orderLine_70   | N             |

    # Reserve 10 PCE — no specific attributesKey (basic reservation)
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_70 | orderLine_70   | product_70   | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_70   |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_70   | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_70   | shipment_70 |
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty |
      | shipment_70 | product_70   | 10          |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | qtyReservation_70 | 10 PCE | 10 PCE       | true      |


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
      | huPI_80    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_80           | huPI_80    | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_80     | huPIV_80           | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_80A_10PCE         | huPIItem_80     | product_A    | 10 PCE |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_80B_10PCE         | huPIItem_80     | product_B    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_A    | 10.00    | PCE               |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_B    | 10.00    | PCE               |

    # Create inventory: 10 PCE of each product
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID |
      | inventory_80A  | warehouse_1    | 2026-03-15   | product_A    | 0 PCE   | 10 PCE   | huPIP_80A_10PCE         |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID |
      | inventory_80B  | warehouse_1    | 2026-03-15   | product_B    | 0 PCE   | 10 PCE   | huPIP_80B_10PCE         |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_80   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_80A | order_80   | product_A    | 10         |
      | orderLine_80B | order_80   | product_B    | 10         |
    And the order identified by order_80 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_80A | orderLine_80A  | N             |
      | shipmentSchedule_80B | orderLine_80B  | N             |

    # Create one reservation per order line
    And metasfresh contains M_QtyReservations:
      | Identifier         | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_80A | orderLine_80A  | product_A    | warehouse_1    | 10 PCE | 1     |
      | qtyReservation_80B | orderLine_80B  | product_B    | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_80A  |
      | shipmentSchedule_80B  |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_80A  | D            | true                | false       |
      | shipmentSchedule_80B  | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | shipmentSchedule_80A  | shipment_80A |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | shipmentSchedule_80B  | shipment_80B |

    And validate the created shipment lines
      | M_InOut_ID   | M_Product_ID | movementqty |
      | shipment_80A | product_A    | 10          |
    And validate the created shipment lines
      | M_InOut_ID   | M_Product_ID | movementqty |
      | shipment_80B | product_B    | 10          |

    And validate M_QtyReservations:
      | Identifier         | Qty    | QtyDelivered | Processed |
      | qtyReservation_80A | 10 PCE | 10 PCE       | true      |
      | qtyReservation_80B | 10 PCE | 10 PCE       | true      |


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
    And metasfresh contains M_AttributeSetInstance with identifier "asi_QRH_D":
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
      | product_C  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_C     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_C            | huPI_C     | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_C      | huPIV_C            | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_C_10PCE           | huPIItem_C      | product_C    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_C    | 10.00    | PCE               |

    # 10 PCE on-hand with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_C    | warehouse_1    | 2026-03-15   | product_C    | 0 PCE   | 10 PCE   | huPIP_C_10PCE           | asi_QRH_D                 | hu_C    |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft=DE directly on the HU (inventory step creates the HU with the attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_C    | 1000001              | DE       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_C    | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_C | order_C    | product_C    | 10         |
    And the order identified by order_C is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_C | orderLine_C    | N             |

    # Reservation without attribute filtering (single HU available, picked by FIFO)
    And metasfresh contains M_QtyReservations:
      | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_C | orderLine_C    | product_C    | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_C    |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_C    | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_C    | shipment_C |

    # Store the inout line identifier so we can access it in the "by id" validation step
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_C | product_C    | 10          | shipLine_C     |

    # Capture the shipment line's ASI under a new identifier, then assert its attribute values
    And validate the created shipment lines by id
      | Identifier | M_AttributeSetInstance_ID |
      | shipLine_C | asi_shipLine_C            |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_C            | 1000001       | DE    |

    And validate M_QtyReservations:
      | Identifier       | Qty    | QtyDelivered | Processed |
      | qtyReservation_C | 10 PCE | 10 PCE       | true      |


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
    And metasfresh contains M_AttributeSetInstance with identifier "asi_D_DE":
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
    And metasfresh contains M_AttributeSetInstance with identifier "asi_D_AT":
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
      | product_D  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_D     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_D            | huPI_D     | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_D      | huPIV_D            | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_D_10PCE           | huPIItem_D      | product_D    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_D    | 10.00    | PCE               |

    # HU_DE: 10 PCE product_D with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_D_DE | warehouse_1    | 2026-03-15   | product_D    | 0 PCE   | 10 PCE   | huPIP_D_10PCE           | asi_D_DE                  | hu_D_DE |
    # HU_AT: 10 PCE product_D with Herkunft=AT
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_D_AT | warehouse_1    | 2026-03-15   | product_D    | 0 PCE   | 10 PCE   | huPIP_D_10PCE           | asi_D_AT                  | hu_D_AT |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft directly on each HU (inventory creates HU with attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_D_DE | 1000001              | DE       |
      | hu_D_AT | 1000001              | AT       |

    # Order D1: 10 PCE product_D (will be covered by reservation with Herkunft=DE)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_D1   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_D1 | order_D1   | product_D    | 10         |
    And the order identified by order_D1 is completed
    # Order D2: 10 PCE product_D (will be covered by reservation with Herkunft=AT)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_D2   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_D2 | order_D2   | product_D    | 10         |
    And the order identified by order_D2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_D1 | orderLine_D1   | N             |
      | shipmentSchedule_D2 | orderLine_D2   | N             |

    # Reservations filtered by attribute: M_AttributeSetInstance_ID computes the AttributesKey
    # from the ASI's storage-relevant attributes
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | qtyReservation_D1 | orderLine_D1   | product_D    | warehouse_1    | 10 PCE | 1     | asi_D_DE                  |
      | qtyReservation_D2 | orderLine_D2   | product_D    | warehouse_1    | 10 PCE | 1     | asi_D_AT                  |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_D1   |
      | shipmentSchedule_D2   |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_D1   | D            | true                | false       |
      | shipmentSchedule_D2   | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_D1   | shipment_D1 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_D2   | shipment_D2 |

    # Shipment D1 must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_D1 | product_D    | 10          | shipLine_D1    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_D1 | asi_shipLine_D1           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_D1           | 1000001       | DE    |

    # Shipment D2 must carry Herkunft=AT
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_D2 | product_D    | 10          | shipLine_D2    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_D2 | asi_shipLine_D2           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_D2           | 1000001       | AT    |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | qtyReservation_D1 | 10 PCE | 10 PCE       | true      |
      | qtyReservation_D2 | 10 PCE | 10 PCE       | true      |


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
    And metasfresh contains M_AttributeSetInstance with identifier "asi_E_DE":
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
    And metasfresh contains M_AttributeSetInstance with identifier "asi_E_CH":
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
      | product_E  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_E     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_E            | huPI_E     | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_E      | huPIV_E            | MI       |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Attribute_ID.Identifier | M_HU_PI_Version_ID.Identifier | M_Attribute.Value |
      | huPIAttr_E                      | huPIV_E                       | 1000001           |
      | huPIAttr_E_tmpl                 | 100                           | 1000001           |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty    |
      | huPIP_E_10PCE           | huPIItem_E      | product_E    | 10 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_E    | 10.00    | PCE               |

    # HU_E_DE: 10 PCE with Herkunft=DE
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_E_DE | warehouse_1    | 2026-03-15   | product_E    | 0 PCE   | 10 PCE   | huPIP_E_10PCE           | asi_E_DE                  | hu_E_DE |
    # HU_E_CH: 10 PCE with Herkunft=CH
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_E_CH | warehouse_1    | 2026-03-15   | product_E    | 0 PCE   | 10 PCE   | huPIP_E_10PCE           | asi_E_CH                  | hu_E_CH |
    # HU_E_plain: 10 PCE with no Herkunft attribute
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID    |
      | inventory_E_plain | warehouse_1    | 2026-03-15   | product_E    | 0 PCE   | 10 PCE   | huPIP_E_10PCE           | hu_E_plain |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft directly on DE and CH HUs (inventory creates HU with attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_E_DE | 1000001              | DE       |
      | hu_E_CH | 1000001              | CH       |

    # Order E1: 10 PCE (reserved from DE HU)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_E1   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_E1 | order_E1   | product_E    | 10         |
    And the order identified by order_E1 is completed
    # Order E2: 10 PCE (reserved from CH HU)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_E2   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_E2 | order_E2   | product_E    | 10         |
    And the order identified by order_E2 is completed
    # Order E3: 10 PCE (no attribute filter — reservation picks plain HU by exclusion)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_E3   | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_E3 | order_E3   | product_E    | 10         |
    And the order identified by order_E3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_E1 | orderLine_E1   | N             |
      | shipmentSchedule_E2 | orderLine_E2   | N             |
      | shipmentSchedule_E3 | orderLine_E3   | N             |

    # Reservations: E1 locked to DE HU, E2 locked to CH HU, E3 no attribute filter (picks remaining plain HU)
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | qtyReservation_E1 | orderLine_E1   | product_E    | warehouse_1    | 10 PCE | 1     | asi_E_DE                  |
      | qtyReservation_E2 | orderLine_E2   | product_E    | warehouse_1    | 10 PCE | 1     | asi_E_CH                  |
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_E3 | orderLine_E3   | product_E    | warehouse_1    | 10 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_E1   |
      | shipmentSchedule_E2   |
      | shipmentSchedule_E3   |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_E1   | D            | true                | false       |
      | shipmentSchedule_E2   | D            | true                | false       |
      | shipmentSchedule_E3   | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_E1   | shipment_E1 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_E2   | shipment_E2 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID  |
      | shipmentSchedule_E3   | shipment_E3 |

    # Shipment E1 must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_E1 | product_E    | 10          | shipLine_E1    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_E1 | asi_shipLine_E1           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_E1           | 1000001       | DE    |

    # Shipment E2 must carry Herkunft=CH
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_E2 | product_E    | 10          | shipLine_E2    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_E2 | asi_shipLine_E2           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_E2           | 1000001       | CH    |

    # Shipment E3 must NOT carry any Herkunft value (plain HU has no Herkunft)
    # No "Value" column in this table → asserts that Herkunft value is null
    And validate the created shipment lines
      | M_InOut_ID  | M_Product_ID | movementqty | M_InOutLine_ID |
      | shipment_E3 | product_E    | 10          | shipLine_E3    |
    And validate the created shipment lines by id
      | Identifier  | M_AttributeSetInstance_ID |
      | shipLine_E3 | asi_shipLine_E3           |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode |
      | asi_shipLine_E3           | 1000001       |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | qtyReservation_E1 | 10 PCE | 10 PCE       | true      |
      | qtyReservation_E2 | 10 PCE | 10 PCE       | true      |
      | qtyReservation_E3 | 10 PCE | 10 PCE       | true      |


  @from:cucumber
  @Id:S_QtyRes_170
  Scenario: Single order with mixed reservations — each shipment line carries its HU Herkunft attribute
  ## _Given three HUs for the same product: HU_DE (5 PCE, Herkunft=DE), HU_CH (10 PCE, Herkunft=CH), HU_plain (15 PCE, no Herkunft)
  ## _And a single sales order for 30 PCE with three reservations on the same order line
  ## _  (5 PCE filtered to DE HU, 10 PCE filtered to CH HU, 15 PCE unfiltered picking plain HU)
  ## _When shipment is generated
  ## _Then three shipment lines are created: 5 PCE with Herkunft=DE, 10 PCE with Herkunft=CH, 15 PCE with no Herkunft
  ## _Covers the single-order mixed-source scenario from https://github.com/metasfresh/me03/issues/28126

    # Re-use custom attribute from S_QtyRes_140 (upsert keeps existing if already created)
    Given metasfresh contains M_Attributes:
      | Identifier | Value   | IsStorageRelevant |
      | attr_QRH   | 1000001 | true              |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_F_DE":
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
    And metasfresh contains M_AttributeSetInstance with identifier "asi_F_CH":
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
      | product_F  | true      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPI_F     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | huPIV_F            | huPI_F     | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPIItem_F      | huPIV_F            | MI       |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Attribute_ID.Identifier | M_HU_PI_Version_ID.Identifier | M_Attribute.Value |
      | huPIAttr_F                      | huPIV_F                       | 1000001           |
      | huPIAttr_F_tmpl                 | 100                           | 1000001           |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty     |
      | huPIP_F_100PCE          | huPIItem_F      | product_F    | 100 PCE |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv_1                  | product_F    | 10.00    | PCE               |

    # HU_F_DE: 5 PCE with Herkunft=DE (distinct qty so shipment line is identifiable by movementqty)
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_F_DE | warehouse_1    | 2026-03-15   | product_F    | 0 PCE   | 5 PCE    | huPIP_F_100PCE          | asi_F_DE                  | hu_F_DE |
    # HU_F_CH: 10 PCE with Herkunft=CH
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory_F_CH | warehouse_1    | 2026-03-15   | product_F    | 0 PCE   | 10 PCE   | huPIP_F_100PCE          | asi_F_CH                  | hu_F_CH |
    # HU_F_plain: 15 PCE with no Herkunft attribute
    And metasfresh contains single line completed inventories
      | M_Inventory_ID    | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_HU_ID    |
      | inventory_F_plain | warehouse_1    | 2026-03-15   | product_F    | 0 PCE   | 15 PCE   | huPIP_F_100PCE          | hu_F_plain |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # Set Herkunft directly on DE and CH HUs (inventory creates HU with attribute but value=null)
    And M_HU_Attribute is changed
      | M_HU_ID | M_Attribute_ID.Value | ValueStr |
      | hu_F_DE | 1000001              | DE       |
      | hu_F_CH | 1000001              | CH       |

    # Single order for 30 PCE — all three reservations reference this same order line
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | DeliveryRule |
      | order_F    | true    | bp_1          | 2026-03-15  | warehouse_1    | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_F | order_F    | product_F    | 30         |
    And the order identified by order_F is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule_F | orderLine_F    | N             |

    # Three reservations on the same order line: DE (5 PCE), CH (10 PCE), plain (15 PCE, no attribute filter)
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU | M_AttributeSetInstance_ID |
      | qtyReservation_F1 | orderLine_F    | product_F    | warehouse_1    | 5 PCE  | 1     | asi_F_DE                  |
      | qtyReservation_F2 | orderLine_F    | product_F    | warehouse_1    | 10 PCE | 1     | asi_F_CH                  |
    And metasfresh contains M_QtyReservations:
      | Identifier        | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty    | QtyTU |
      | qtyReservation_F3 | orderLine_F    | product_F    | warehouse_1    | 15 PCE | 1     |

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule_F    |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_F    | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | shipmentSchedule_F    | shipment_F |

    # One shipment with three lines — disambiguated by movementqty (5, 10, 15 are distinct)
    # Line with 5 PCE: from DE HU — must carry Herkunft=DE
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_InOutLine_ID |
      | shipment_F | product_F    | 5           | shipLine_F_DE  |
    And validate the created shipment lines by id
      | Identifier    | M_AttributeSetInstance_ID |
      | shipLine_F_DE | asi_shipLine_F_DE         |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_F_DE         | 1000001       | DE    |

    # Line with 10 PCE: from CH HU — must carry Herkunft=CH
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_InOutLine_ID |
      | shipment_F | product_F    | 10          | shipLine_F_CH  |
    And validate the created shipment lines by id
      | Identifier    | M_AttributeSetInstance_ID |
      | shipLine_F_CH | asi_shipLine_F_CH         |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine_F_CH         | 1000001       | CH    |

    # Line with 15 PCE: from plain HU — must NOT carry any Herkunft value
    # No "Value" column in the table — asserts that Herkunft value is null
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_InOutLine_ID   |
      | shipment_F | product_F    | 15          | shipLine_F_plain |
    And validate the created shipment lines by id
      | Identifier       | M_AttributeSetInstance_ID |
      | shipLine_F_plain | asi_shipLine_F_plain      |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode |
      | asi_shipLine_F_plain      | 1000001       |

    And validate M_QtyReservations:
      | Identifier        | Qty    | QtyDelivered | Processed |
      | qtyReservation_F1 | 5 PCE  | 5 PCE        | true      |
      | qtyReservation_F2 | 10 PCE | 10 PCE       | true      |
      | qtyReservation_F3 | 15 PCE | 15 PCE       | true      |
