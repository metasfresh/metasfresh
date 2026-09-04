@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29050_Delivery_Planning
@ghActions:run_on_executor5
Feature: The delivery planning's status colour is the delivered state, rendered

  DeliveryStatus_Color_ID is the FIRST column of the planning grid - the colour the operator scans a
  result set by - and it carries no information beyond "is this planning delivered?": the palette
  offers exactly two colours, one for delivered and one for not. IsDelivered answers the same
  question in words. The two must therefore never disagree, and the way to guarantee that is to
  derive both from the same fact (the planning's own M_InOut_ID) rather than to store the colour and
  hope every write path maintains it.

  The split is the write path that proves the point: a planning created by splitting another is a
  full planning the operator sees in the same grid, so it must resolve a colour like any other.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    Given metasfresh contains M_PricingSystems
      | Identifier          |
      | pricingSystemColour |
    And metasfresh contains M_PriceLists
      | Identifier         | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceListSOColour  | pricingSystemColour           | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier                | M_PriceList_ID.Identifier |
      | priceListVersionSOColour  | priceListSOColour         |
    And metasfresh contains M_Products:
      | Identifier      | Name           |
      | productColour   | ProductColour  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersionSOColour          | productColour           | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | customerColour  | N        | Y          | pricingSystemColour           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocationColour | customerColour           | true                | true                |
    And contains M_Shippers
      | Identifier     | OPT.IsCreateDeliveryPlanning |
      | shipperColour  | true                         |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                 | Name                 |
      | warehouseColour           | warehouseValueColour  | warehouseNameColour  |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locatorColour           | locatorValueColour  | warehouseColour           |

  @Id:S31789_TC_StatusColourDerivedFromDeliveredState
  Scenario: The status colour agrees with IsDelivered on both a generated planning and a SPLIT sibling

    # DeliveryRule=Force so the shipment quantity is decided by the process parameter rather than by
    # warehouse availability - the subject here is the colour, not stock. The own warehouse keeps these
    # shipments out of any other scenario's consolidation window.
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DeliveryRule |
      | orderColour   | true    | customerColour           | 2023-02-03  | 2023-02-05T00:00:00Z | customerLocationColour                | warehouseColour               | F                |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineColour   | orderColour           | productColour           | 10         | shipperColour               |

    When the order identified by orderColour is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID  | C_OrderLine_ID  |
      | planningColour_1        | orderLineColour |

    # Nothing is delivered yet, so the generated planning shows the not-delivered colour.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsDelivered | DeliveryStatus_Color_ID.Name | M_InOut_ID |
      | planningColour_1       | 10         | 10           | Outgoing           | false       | Rot                          | null       |

    # THE split - the write path that leaves a stored colour blank: the sibling is a planning of its own,
    # in the same grid, and must resolve the same not-delivered colour as the row it came from.
    When generate 1 additional M_Delivery_Planning records for: planningColour_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID            | C_OrderLine_ID  |
      | planningColour_1,planningColour_2 | orderLineColour |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsDelivered | DeliveryStatus_Color_ID.Name | M_InOut_ID |
      | planningColour_1       | 10         | 10           | Outgoing           | false       | Rot                          | null       |
      | planningColour_2       | 10         | 10           | Outgoing           | false       | Rot                          | null       |

    # One sibling gets a shipment. Its colour must flip with IsDelivered, and the other sibling's must not.
    When the delivery planning identified by planningColour_1 generates a shipment:
      | DeliveryDate | Qty | OPT.M_InOut_ID   |
      | 2023-02-05   | 5   | shipmentColour_1 |

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsDelivered | DeliveryStatus_Color_ID.Name | M_InOut_ID       |
      | planningColour_1       | 10         | 5            | Outgoing           | true        | Gruen                        | shipmentColour_1 |
      | planningColour_2       | 10         | 5            | Outgoing           | false       | Rot                          | null             |

    # And back: reversing the shipment clears M_InOut_ID, so both the flag and the colour must return.
    When the shipment identified by shipmentColour_1 is reversed

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsDelivered | DeliveryStatus_Color_ID.Name | M_InOut_ID |
      | planningColour_1       | 10         | 10           | Outgoing           | false       | Rot                          | null       |
      | planningColour_2       | 10         | 10           | Outgoing           | false       | Rot                          | null       |
