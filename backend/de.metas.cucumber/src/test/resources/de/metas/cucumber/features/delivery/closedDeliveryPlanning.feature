@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29060_Delivery_Instruction
@ghActions:run_on_executor5
Feature: A closed delivery planning is finished and nothing processes it any further

  Closing a load means "stop processing this cargo".
  It can no longer be put on a delivery instruction, cancelling passes it by, and closing or re-opening it a
  second time is an error rather than a silent no-op. A load that already sits on a finalised instruction cannot
  be called off behind the instruction's back - the planner re-activates the instruction first.

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

  @Id:S31608_TC10
  Scenario: Combining a selection that holds a closed delivery planning is refused for the whole selection

    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderCombined | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineCombined | orderCombined         | product                 | 8          | shipper_DHL                 |

    When the order identified by orderCombined is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleCombined | orderLineCombined         | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID    |
      | planningCombined_1     | orderLineCombined |

    # two loads of one order line: 4 + 4 of the 8 ordered
    When generate 1 additional M_Delivery_Planning records for: planningCombined_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                | C_OrderLine_ID    |
      | planningCombined_1,planningCombined_2 | orderLineCombined |

    When M_Delivery_Planning identified by planningCombined_2 is closed
    And combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_Delivery_Planning_ID                | ErrorAdMessage                                                           |
      | planningCombined_1,planningCombined_2 | de.metas.deliveryplanning.CombineIntoDeliveryInstruction.ClosedPlannings |

    # refused for the whole selection: the open one does not get an instruction of its own either
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningCombined_1     | 8          | 8            | Outgoing           | false    | null                       |
      | planningCombined_2     | 8          | 8            | Outgoing           | true     | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningCombined_1     |
      | planningCombined_2     |

  @Id:S31608_TC11
  Scenario: Adding a selection that holds a closed delivery planning to a draft instruction is refused for the whole selection

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderAdded | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineAdded | orderAdded            | product                 | 12         | shipper_DHL                 |

    When the order identified by orderAdded is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleAdded | orderLineAdded            | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningAdded_1        | orderLineAdded |

    # three loads of one order line: 4 + 4 + 4 of the 12 ordered
    When generate 2 additional M_Delivery_Planning records for: planningAdded_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                          | C_OrderLine_ID |
      | planningAdded_1,planningAdded_2,planningAdded_3 | orderLineAdded |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionAdded   | planningAdded_1        |
    And M_Delivery_Planning identified by planningAdded_3 is closed
    And add M_Delivery_Planning to M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID          | ErrorAdMessage                                                           |
      | deliveryInstructionAdded   | planningAdded_2,planningAdded_3 | de.metas.deliveryplanning.CombineIntoDeliveryInstruction.ClosedPlannings |

    # refused for the whole selection: the open one of the two does not join the truck either
    Then the M_ShipperTransportation identified by deliveryInstructionAdded holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningAdded_1        | 10     | 4             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningAdded_2        | 12         | 12           | Outgoing           | false    | null                       |
      | planningAdded_3        | 12         | 12           | Outgoing           | true     | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningAdded_2        |
      | planningAdded_3        |

  @Id:S31608_TC12
  Scenario: A delivery planning on a finalised instruction is closed only after the instruction is re-activated

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderFinal | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineFinal | orderFinal            | product                 | 10         | shipper_DHL                 |

    When the order identified by orderFinal is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleFinal | orderLineFinal            | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningFinal_1        | orderLineFinal |

    # two loads of one order line: 5 + 5 of the 10 ordered
    When generate 1 additional M_Delivery_Planning records for: planningFinal_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID          | C_OrderLine_ID |
      | planningFinal_1,planningFinal_2 | orderLineFinal |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID          | IsComplete |
      | deliveryInstructionFinal   | planningFinal_1,planningFinal_2 | true       |

    # calling the cargo off behind the finalised instruction's back is refused
    When closing M_Delivery_Planning identified by planningFinal_2 is refused:
      | ErrorAdMessage                                                                |
      | de.metas.deliveryplanning.DeliveryPlanningService.CloseOnCompletedInstruction |

    Then the M_ShipperTransportation identified by deliveryInstructionFinal holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningFinal_1        | 10     | 5             |
      | planningFinal_2        | 20     | 5             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningFinal_2        | 10         | 10           | Outgoing           | false    | deliveryInstructionFinal   |

    # re-activating the instruction is the way out, and then closing takes the load off it
    When the M_ShipperTransportation identified by deliveryInstructionFinal is reactivated
    And M_Delivery_Planning identified by planningFinal_2 is closed

    Then the M_ShipperTransportation identified by deliveryInstructionFinal holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningFinal_1        | 10     | 5             |
    And validate M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShipperTransportation_ID | IsActive | LineNo |
      | planningFinal_2        | deliveryInstructionFinal   | false    | 20     |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningFinal_2        | 10         | 10           | Outgoing           | true     | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningFinal_2        |

  @Id:S31608_TC13
  Scenario: Closing an already closed delivery planning and re-opening an open one are both errors

    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderToggle | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineToggle | orderToggle           | product                 | 8          | shipper_DHL                 |

    When the order identified by orderToggle is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleToggle | orderLineToggle           | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | planningToggle_1       | orderLineToggle |

    # two loads of one order line: 4 + 4 of the 8 ordered
    When generate 1 additional M_Delivery_Planning records for: planningToggle_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID            | C_OrderLine_ID  |
      | planningToggle_1,planningToggle_2 | orderLineToggle |

    When M_Delivery_Planning identified by planningToggle_1 is closed

    # closing a closed one, and re-opening an open one, report an error instead of doing nothing
    Then closing M_Delivery_Planning identified by planningToggle_1 is refused:
      | ErrorMessage |
      | @Closed@=@Y@ |
    And reopening M_Delivery_Planning identified by planningToggle_2 is refused:
      | ErrorMessage |
      | @Closed@=@N@ |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed |
      | planningToggle_1       | 8          | 8            | Outgoing           | true     |
      | planningToggle_2       | 8          | 8            | Outgoing           | false    |

    # the legitimate transitions still work
    When M_Delivery_Planning identified by planningToggle_1 is opened
    And M_Delivery_Planning identified by planningToggle_2 is closed

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed |
      | planningToggle_1       | 8          | 8            | Outgoing           | false    |
      | planningToggle_2       | 8          | 8            | Outgoing           | true     |

  @Id:S31608_TC14
  Scenario: Cancelling a selection cancels the open delivery planning and passes the closed one by

    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderCancel | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineCancel | orderCancel           | product                 | 10         | shipper_DHL                 |

    When the order identified by orderCancel is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleCancel | orderLineCancel           | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | planningCancel_1       | orderLineCancel |

    # two loads of one order line: 5 + 5 of the 10 ordered
    When generate 1 additional M_Delivery_Planning records for: planningCancel_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID            | C_OrderLine_ID  |
      | planningCancel_1,planningCancel_2 | orderLineCancel |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID            | IsComplete |
      | deliveryInstructionCancel  | planningCancel_1,planningCancel_2 | true       |
    And the M_ShipperTransportation identified by deliveryInstructionCancel is reactivated
    And M_Delivery_Planning identified by planningCancel_2 is closed

    # both are selected; only the open one is cancelled. Two independent guards keep the closed one out - it
    # carries no release number any more (closing deallocated it) AND cancel skips a closed row - so breaking
    # either one alone leaves this scenario green; breaking both cancels it and the quantity below goes to 0.
    When M_Delivery_Planning identified by planningCancel_1,planningCancel_2 is canceled

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | OrderStatus | PlannedLoadedQuantity | M_ShipperTransportation_ID |
      | planningCancel_1       | 10         | 10           | Outgoing           | true     | Canceled    | 0                     | null                       |
      | planningCancel_2       | 10         | 10           | Outgoing           | true     |             | 5                     | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningCancel_1       |
      | planningCancel_2       |
    And validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCancel             | shipper_DHL             | customer                       | customerLocation               | VO            |
    And the M_ShipperTransportation identified by deliveryInstructionCancel holds no active M_Delivery_Planning_Alloc
