@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29060_Delivery_Instruction
@ghActions:run_on_executor5
Feature: The delivery instruction owns the dates of the delivery plannings booked on it

  A planner books loads onto one truck and then moves that truck's departure.
  The first load proposes its dates to the empty instruction, every later load adopts them, a change on the
  instruction reaches every load still on it, and a load taken off gets its own order-derived dates back.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    Given metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_SO | pricingSystem      | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID |
      | priceListVersion_SO | priceList_SO   |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO    | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | customer                 | true                | true                |
    And contains M_Shippers
      | Identifier  | OPT.IsCreateDeliveryPlanning |
      | shipper_DHL | true                         |

  @Id:S31608_TC16
  Scenario: The first delivery planning put on a delivery instruction proposes its dates

    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderPropose | true    | customer                 | 2023-02-03  | 2023-03-05T00:00:00Z | 2023-03-10T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLinePropose | orderPropose          | product                 | 5          | shipper_DHL                 |

    When the order identified by orderPropose is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedulePropose | orderLinePropose          | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID   |
      | planningPropose        | orderLinePropose |

    # the dates the planning brings: departure from the order's preparation date, arrival from the order line
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETD        | ETA        |
      | planningPropose        | 5          | 5            | Outgoing           | 2023-03-05 | 2023-03-10 |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionPropose | planningPropose        |

    # the empty instruction took them over unchanged
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus | OPT.ETA    |
      | deliveryInstructionPropose            | shipper_DHL             | customer                       | customerLocation               | DR            | 2023-03-10 |
    And the following M_Delivery_Planning carry the date fields of M_ShipperTransportation deliveryInstructionPropose:
      | M_Delivery_Planning_ID |
      | planningPropose        |

  @Id:S31608_TC17
  Scenario: A delivery planning added to a draft delivery instruction adopts the instruction's dates

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderEarly | true    | customer                 | 2023-02-03  | 2023-03-05T00:00:00Z | 2023-03-10T00:00:00Z | customerLocation                      |
      | orderLate  | true    | customer                 | 2023-02-03  | 2023-04-15T00:00:00Z | 2023-04-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineEarly | orderEarly            | product                 | 5          | shipper_DHL                 |
      | orderLineLate  | orderLate             | product                 | 4          | shipper_DHL                 |

    When the order identified by orderEarly is completed
    And the order identified by orderLate is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleEarly | orderLineEarly            | N             |
      | shipmentScheduleLate  | orderLineLate             | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningEarly          | orderLineEarly |
      | planningLate           | orderLineLate  |

    # two loads that disagree on when they arrive
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETD        | ETA        |
      | planningEarly          | 5          | 5            | Outgoing           | 2023-03-05 | 2023-03-10 |
      | planningLate           | 4          | 4            | Outgoing           | 2023-04-15 | 2023-04-20 |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionAdopt   | planningEarly          |
    And add M_Delivery_Planning to M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionAdopt   | planningLate           |

    # First-writer-wins, and deliberately so: the instruction keeps the arrival it already had rather than
    # taking the latest across its plannings. This is the same rule the transport order applies on this same
    # table - applyDefaultDatesFromFirstOrder fills each date only while it is still empty, from ONE order -
    # so a planner sees one behaviour on both documents instead of two.
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus | OPT.ETA    |
      | deliveryInstructionAdopt              | shipper_DHL             | customer                       | customerLocation               | DR            | 2023-03-10 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETA        | M_ShipperTransportation_ID |
      | planningEarly          | 5          | 5            | Outgoing           | 2023-03-10 | deliveryInstructionAdopt   |
      | planningLate           | 4          | 4            | Outgoing           | 2023-03-10 | deliveryInstructionAdopt   |
    And the following M_Delivery_Planning carry the date fields of M_ShipperTransportation deliveryInstructionAdopt:
      | M_Delivery_Planning_ID |
      | planningEarly          |
      | planningLate           |

  @Id:S31608_TC18
  Scenario: A date changed on the delivery instruction reaches every delivery planning still on it

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderSync  | true    | customer                 | 2023-02-03  | 2023-03-05T00:00:00Z | 2023-03-10T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineSync | orderSync             | product                 | 8          | shipper_DHL                 |

    When the order identified by orderSync is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleSync | orderLineSync             | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningSync_1         | orderLineSync  |

    When generate 1 additional M_Delivery_Planning records for: planningSync_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID        | C_OrderLine_ID |
      | planningSync_1,planningSync_2 | orderLineSync  |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID        |
      | deliveryInstructionSync    | planningSync_1,planningSync_2 |

    # the truck is rescheduled on the instruction
    And update transport order
      | M_ShipperTransportation_ID | ETD                  | ETA                  |
      | deliveryInstructionSync    | 2023-06-01T00:00:00Z | 2023-06-05T00:00:00Z |

    # and both loads move with it
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETD        | ETA        | M_ShipperTransportation_ID |
      | planningSync_1         | 8          | 8            | Outgoing           | 2023-06-01 | 2023-06-05 | deliveryInstructionSync    |
      | planningSync_2         | 8          | 8            | Outgoing           | 2023-06-01 | 2023-06-05 | deliveryInstructionSync    |
    And the following M_Delivery_Planning carry the date fields of M_ShipperTransportation deliveryInstructionSync:
      | M_Delivery_Planning_ID |
      | planningSync_1         |
      | planningSync_2         |

  @Id:S31608_TC19
  Scenario: A delivery planning taken off the delivery instruction gets its order-derived dates back

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderStay  | true    | customer                 | 2023-02-03  | 2023-03-05T00:00:00Z | 2023-03-10T00:00:00Z | customerLocation                      |
      | orderLeave | true    | customer                 | 2023-02-03  | 2023-04-15T00:00:00Z | 2023-04-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineStay  | orderStay             | product                 | 5          | shipper_DHL                 |
      | orderLineLeave | orderLeave            | product                 | 4          | shipper_DHL                 |

    When the order identified by orderStay is completed
    And the order identified by orderLeave is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleStay  | orderLineStay             | N             |
      | shipmentScheduleLeave | orderLineLeave            | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningStay           | orderLineStay  |
      | planningLeave          | orderLineLeave |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID     |
      | deliveryInstructionReset   | planningStay,planningLeave |
    And update transport order
      | M_ShipperTransportation_ID | ETD                  | ETA                  |
      | deliveryInstructionReset   | 2023-06-01T00:00:00Z | 2023-06-05T00:00:00Z |

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETD        | ETA        | M_ShipperTransportation_ID |
      | planningStay           | 5          | 5            | Outgoing           | 2023-06-01 | 2023-06-05 | deliveryInstructionReset   |
      | planningLeave          | 4          | 4            | Outgoing           | 2023-06-01 | 2023-06-05 | deliveryInstructionReset   |

    When remove M_Delivery_Planning from M_ShipperTransportation:
      | M_Delivery_Planning_ID |
      | planningLeave          |

    # the removed load is back on its own order's dates, not the truck's
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETD        | ETA        | ATD        | ATA        | M_ShipperTransportation_ID |
      | planningLeave          | 4          | 4            | Outgoing           | 2023-04-15 | 2023-04-20 | 2023-04-15 | 2023-04-20 | null                       |

    # and the one still on board is unmoved
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ETD        | ETA        | M_ShipperTransportation_ID |
      | planningStay           | 5          | 5            | Outgoing           | 2023-06-01 | 2023-06-05 | deliveryInstructionReset   |
    And the following M_Delivery_Planning carry the date fields of M_ShipperTransportation deliveryInstructionReset:
      | M_Delivery_Planning_ID |
      | planningStay           |
