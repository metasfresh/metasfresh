@from:cucumber
@ghActions:run_on_executor3
Feature: Shipment line ASI propagation
  The shipment schedule's ASI (from the order line) must propagate to the shipment line.
  M_ShipmentSchedule_AttributeConfig controls which HU attributes participate, and per attribute,
  IsHUAttributeOverridesASI (Y/N) decides whether the HU value or the schedule ASI value wins.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    # Storage-relevant LIST attribute (reuse Herkunft M_Attribute_ID=1000001 which exists in seed DB)
    And metasfresh contains M_Attributes:
      | Identifier | Value   | IsStorageRelevant |
      | attr_test  | 1000001 | true              |

    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    # TU packing instructions (10 PCE per TU)
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | huPackTU    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPackVersion_TU   | huPackTU    | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType |
      | huPackItem_MI    | huPackVersion_TU   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID |
      | huPIP_10PCE             | huPackItem_MI    | product      | 10  | PCE      |

    # Pricing
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp         | plv                    | product      | 10.0     | PCE      | Normal            |

    # BPartner with pricing
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps                 |

  @from:cucumber
  @Id:S0290_10
  Scenario: Schedule ASI propagates to shipment line when no HUs are picked
  ## When a sales order line has an ASI with a custom attribute (e.g. Herkunft=DE),
  ## and the shipment is generated without HU picking (e.g. dropship, no stock, etc.),
  ## then the shipment line's ASI must contain that attribute — even if it's not in
  ## M_ShipmentSchedule_AttributeConfig.

    # ASI with Herkunft=DE
    And metasfresh contains M_AttributeSetInstance with identifier "asi_order":
    """
    {
      "attributeInstances":[
        { "attributeCode":"1000001", "valueStr":"DE" }
      ]
    }
    """

    # Sales order with ASI on order line
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_AttributeSetInstance_ID |
      | orderLine  | order      | product      | 10         | asi_order                 |
    And the order identified by order is completed
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Wait for shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    # Generate shipment (no HU picking — manual packing / dropship path)
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false               |

    # Validate shipment was created
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | shipmentSchedule      | shipment   | CO        |

    # Validate shipment line exists
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID | M_AttributeSetInstance_ID |
      | shipment   | product      | 10          | shipLine       | asi_shipLine              |

    # Assert: the shipment line's ASI contains Herkunft=DE
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine              | 1000001       | DE    |


  @from:cucumber
  @Id:S0290_20
  Scenario: Normal flow — schedule ASI takes precedence over HU attribute
  ## When both the order line ASI and the picked HU have the same attribute with different values,
  ## the schedule ASI value must win on the shipment line — IF IsHUAttributeOverridesASI=N.

    # Configure: for Herkunft, the order line ASI value should take precedence over HU
    And update M_ShipmentSchedule_AttributeConfig:
      | M_Attribute.Value | IsHUAttributeOverridesASI |
      | 1000001           | N                         |

    # ASI for order line: Herkunft=DE
    And metasfresh contains M_AttributeSetInstance with identifier "asi_order":
    """
    {
      "attributeInstances":[
        { "attributeCode":"1000001", "valueStr":"DE" }
      ]
    }
    """

    # ASI for HU: Herkunft=IT (different value)
    And metasfresh contains M_AttributeSetInstance with identifier "asi_HU":
    """
    {
      "attributeInstances":[
        { "attributeCode":"1000001", "valueStr":"IT" }
      ]
    }
    """

    # Create HU with Herkunft=IT via inventory
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory      | warehouse      | 2022-05-17   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_HU                    | hu      |
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    # Register the HU's Herkunft attribute
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | huPackVersion_TU   | 1000001           |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier | M_Attribute_ID | Value | AttributeValueType |
      | hu                 | 1000001        | IT    | S                  |

    # Sales order with ASI containing Herkunft=DE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_AttributeSetInstance_ID |
      | orderLine  | order      | product      | 10         | asi_order                 |
    And the order identified by order is completed

    # Wait for shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    # Generate shipment — HU with Herkunft=IT will be picked on-the-fly
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false               |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | shipmentSchedule      | shipment   | CO        |

    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID | M_AttributeSetInstance_ID |
      | shipment   | product      | 10          | shipLine       | asi_shipLine              |

    # Assert: schedule ASI wins — Herkunft=DE (not IT from HU)
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine              | 1000001       | DE    |


  @from:cucumber
  @Id:S0290_30
  Scenario: HU attribute wins when IsHUAttributeOverridesASI=Y (default, backward compatible)
  ## When IsHUAttributeOverridesASI=Y (the default), the HU attribute value overwrites
  ## the order line's ASI value on the shipment line. This is the existing behavior for
  ## attributes like LotNumber and BestBefore.

    # Ensure default: IsHUAttributeOverridesASI=Y for Herkunft (it's Y by default, but be explicit)
    And update M_ShipmentSchedule_AttributeConfig:
      | M_Attribute.Value | IsHUAttributeOverridesASI |
      | 1000001           | Y                         |

    # ASI for order line: Herkunft=DE
    And metasfresh contains M_AttributeSetInstance with identifier "asi_order":
    """
    {
      "attributeInstances":[
        { "attributeCode":"1000001", "valueStr":"DE" }
      ]
    }
    """

    # ASI for HU: Herkunft=IT (different value)
    And metasfresh contains M_AttributeSetInstance with identifier "asi_HU":
    """
    {
      "attributeInstances":[
        { "attributeCode":"1000001", "valueStr":"IT" }
      ]
    }
    """

    # Create HU with Herkunft=IT via inventory
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID | M_HU_ID |
      | inventory      | warehouse      | 2022-05-17   | product      | 0 PCE   | 10 PCE   | huPIP_10PCE             | asi_HU                    | hu      |
    And wait until all rabbitMQ queues are empty or throw exception after 5 minutes

    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | huPackVersion_TU   | 1000001           |

    And update M_HU_Attribute:
      | M_HU_ID.Identifier | M_Attribute_ID | Value | AttributeValueType |
      | hu                 | 1000001        | IT    | S                  |

    # Sales order with ASI containing Herkunft=DE
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_AttributeSetInstance_ID |
      | orderLine  | order      | product      | 10         | asi_order                 |
    And the order identified by order is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule      | D            | true                | false               |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | shipmentSchedule      | shipment   | CO        |

    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | movementqty | M_InOutLine_ID | M_AttributeSetInstance_ID |
      | shipment   | product      | 10          | shipLine       | asi_shipLine              |

    # Assert: HU wins — Herkunft=IT (not DE from order line)
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_shipLine              | 1000001       | IT    |
