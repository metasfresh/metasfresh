@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29060_Delivery_Instruction
@ghActions:run_on_executor5
Feature: Several delivery plannings on one delivery instruction

  A planner consolidates the loads of one truck.
  Combine builds one instruction out of a selection, Add to / Remove from re-book a single load, and the
  instruction stays a draft until the planner says it is final.

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

  @Id:S31608_TC1
  Scenario: Combine three delivery plannings into one delivery instruction

    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderCombine | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineCombine | orderCombine          | product                 | 10         | shipper_DHL                 |

    When the order identified by orderCombine is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleCombine | orderLineCombine          | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID   |
      | planningCombine_1      | orderLineCombine |

    # three loads of one order line: 4 + 3 + 3 of the 10 ordered
    When generate 2 additional M_Delivery_Planning records for: planningCombine_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                | C_OrderLine_ID   |
      | planningCombine_1,planningCombine_2,planningCombine_3 | orderLineCombine |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | M_ShipperTransportation_ID |
      | planningCombine_1      | 10         | 10           | Outgoing           | 4                     | null                       |
      | planningCombine_2      | 10         | 10           | Outgoing           | 3                     | null                       |
      | planningCombine_3      | 10         | 10           | Outgoing           | 3                     | null                       |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID                                |
      | deliveryInstructionCombine | planningCombine_1,planningCombine_2,planningCombine_3 |

    # one instruction, three allocations, three packages, three release numbers
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCombine            | shipper_DHL             | customer                       | customerLocation               | DR            |
    And the M_ShipperTransportation identified by deliveryInstructionCombine holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningCombine_1      | 10     | 4             |
      | planningCombine_2      | 20     | 3             |
      | planningCombine_3      | 30     | 3             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningCombine_1      | 10         | 10           | Outgoing           | deliveryInstructionCombine |
      | planningCombine_2      | 10         | 10           | Outgoing           | deliveryInstructionCombine |
      | planningCombine_3      | 10         | 10           | Outgoing           | deliveryInstructionCombine |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionCombine:
      | M_Delivery_Planning_ID |
      | planningCombine_1      |
      | planningCombine_2      |
      | planningCombine_3      |

  @Id:S31608_TC2
  Scenario: Combining plannings that differ in forwarder and in delivery address is refused in one message naming both

    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer2  | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customer2Location | customer2                | true                | true                |
    And contains M_Shippers
      | Identifier  | OPT.IsCreateDeliveryPlanning |
      | shipper_UPS | true                         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderMix_1 | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
      | orderMix_2 | true    | customer2                | 2023-02-03  | 2023-02-20T00:00:00Z | customer2Location                     |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineMix_1 | orderMix_1            | product                 | 4          | shipper_DHL                 |
      | orderLineMix_2 | orderMix_2            | product                 | 6          | shipper_UPS                 |

    When the order identified by orderMix_1 is completed
    And the order identified by orderMix_2 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleMix_1 | orderLineMix_1            | N             |
      | shipmentScheduleMix_2 | orderLineMix_2            | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningMix_1          | orderLineMix_1 |
      | planningMix_2          | orderLineMix_2 |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_Delivery_Planning_ID      | ErrorAdMessage                                                                 | ErrorFields               |
      | planningMix_1,planningMix_2 | de.metas.deliveryplanning.CombineIntoDeliveryInstruction.IncompatibleSelection | Forwarder,DeliveryAddress |

    # refused for the whole selection: neither planning ends up on an instruction
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningMix_1          | 4          | 4            | Outgoing           | null                       |
      | planningMix_2          | 6          | 6            | Outgoing           | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningMix_1          |
      | planningMix_2          |

  @Id:S31608_TC3
  Scenario: Add a delivery planning to a draft delivery instruction, then move it to another draft

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderAdd   | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineAdd | orderAdd              | product                 | 10         | shipper_DHL                 |

    When the order identified by orderAdd is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleAdd | orderLineAdd              | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningAdd_1          | orderLineAdd   |

    # four loads of one order line: 4 + 2 + 2 + 2 of the 10 ordered
    When generate 3 additional M_Delivery_Planning records for: planningAdd_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                  | C_OrderLine_ID |
      | planningAdd_1,planningAdd_2,planningAdd_3,planningAdd_4 | orderLineAdd   |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID      |
      | deliveryInstructionAdd_A   | planningAdd_1,planningAdd_2 |
    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionAdd_B   | planningAdd_4          |

    # ADD: a planning that is on no instruction joins a draft one
    When add M_Delivery_Planning to M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionAdd_A   | planningAdd_3          |

    Then the M_ShipperTransportation identified by deliveryInstructionAdd_A holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningAdd_1          | 10     | 4             |
      | planningAdd_2          | 20     | 2             |
      | planningAdd_3          | 30     | 2             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningAdd_3          | 10         | 10           | Outgoing           | deliveryInstructionAdd_A   |

    # MOVE: the same planning goes to the other draft, and leaves a retired allocation behind
    When add M_Delivery_Planning to M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionAdd_B   | planningAdd_3          |

    Then the M_ShipperTransportation identified by deliveryInstructionAdd_A holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningAdd_1          | 10     | 4             |
      | planningAdd_2          | 20     | 2             |
    And the M_ShipperTransportation identified by deliveryInstructionAdd_B holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningAdd_4          | 10     | 2             |
      | planningAdd_3          | 20     | 2             |
    And validate M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShipperTransportation_ID | IsActive | LineNo |
      | planningAdd_3          | deliveryInstructionAdd_A   | false    | 30     |
      | planningAdd_3          | deliveryInstructionAdd_B   | true     | 20     |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningAdd_3          | 10         | 10           | Outgoing           | deliveryInstructionAdd_B   |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionAdd_B:
      | M_Delivery_Planning_ID |
      | planningAdd_3          |
      | planningAdd_4          |

  @Id:S31608_TC4
  Scenario: Nothing moves off a completed delivery instruction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderDone  | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineDone | orderDone             | product                 | 9          | shipper_DHL                 |

    When the order identified by orderDone is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleDone | orderLineDone             | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningDone_1         | orderLineDone  |

    When generate 2 additional M_Delivery_Planning records for: planningDone_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                       | C_OrderLine_ID |
      | planningDone_1,planningDone_2,planningDone_3 | orderLineDone  |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID        | IsComplete |
      | deliveryInstructionDone    | planningDone_1,planningDone_2 | true       |
    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionDraft   | planningDone_3         |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionDone               | shipper_DHL             | customer                       | customerLocation               | CO            |
      | deliveryInstructionDraft              | shipper_DHL             | customer                       | customerLocation               | DR            |

    # ADD is refused for the whole selection, not partially performed
    When add M_Delivery_Planning to M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID | ErrorAdMessage                                                       |
      | deliveryInstructionDraft   | planningDone_2         | de.metas.deliveryplanning.DeliveryInstruction.OnCompletedInstruction |

    Then the M_ShipperTransportation identified by deliveryInstructionDone holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo |
      | planningDone_1         | 10     |
      | planningDone_2         | 20     |
    And the M_ShipperTransportation identified by deliveryInstructionDraft holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo |
      | planningDone_3         | 10     |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningDone_2         | 9          | 9            | Outgoing           | deliveryInstructionDone    |

    # and so is REMOVE
    When remove M_Delivery_Planning from M_ShipperTransportation:
      | M_Delivery_Planning_ID | ErrorAdMessage                                                       |
      | planningDone_2         | de.metas.deliveryplanning.DeliveryInstruction.OnCompletedInstruction |

    Then the M_ShipperTransportation identified by deliveryInstructionDone holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo |
      | planningDone_1         | 10     |
      | planningDone_2         | 20     |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningDone_2         | 9          | 9            | Outgoing           | deliveryInstructionDone    |

  @Id:S31608_TC5
  Scenario: Remove delivery plannings from a draft delivery instruction, a closed one included

    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderRemove | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineRemove | orderRemove           | product                 | 9          | shipper_DHL                 |

    When the order identified by orderRemove is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleRemove | orderLineRemove           | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | planningRemove_1       | orderLineRemove |

    When generate 2 additional M_Delivery_Planning records for: planningRemove_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                             | C_OrderLine_ID  |
      | planningRemove_1,planningRemove_2,planningRemove_3 | orderLineRemove |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID                             |
      | deliveryInstructionRemove  | planningRemove_1,planningRemove_2,planningRemove_3 |

    # REMOVE: the instruction and its other plannings are unaffected
    When remove M_Delivery_Planning from M_ShipperTransportation:
      | M_Delivery_Planning_ID |
      | planningRemove_2       |

    Then the M_ShipperTransportation identified by deliveryInstructionRemove holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo |
      | planningRemove_1       | 10     |
      | planningRemove_3       | 30     |
    And validate M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShipperTransportation_ID | IsActive | LineNo |
      | planningRemove_2       | deliveryInstructionRemove  | false    | 20     |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningRemove_2       | 9          | 9            | Outgoing           | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningRemove_2       |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionRemove:
      | M_Delivery_Planning_ID |
      | planningRemove_1       |
      | planningRemove_3       |

    # closing a planning takes it off the draft instruction on its own
    When M_Delivery_Planning identified by planningRemove_3 is closed

    Then the M_ShipperTransportation identified by deliveryInstructionRemove holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo |
      | planningRemove_1       | 10     |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningRemove_3       | 9          | 9            | Outgoing           | true     | null                       |

    # a closed planning in the selection does not make Remove refuse: the still-allocated one comes off
    When remove M_Delivery_Planning from M_ShipperTransportation:
      | M_Delivery_Planning_ID            |
      | planningRemove_3,planningRemove_1 |

    Then the M_ShipperTransportation identified by deliveryInstructionRemove holds no active M_Delivery_Planning_Alloc
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningRemove_1       | 9          | 9            | Outgoing           | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningRemove_1       |
      | planningRemove_3       |

  @Id:S31608_TC6
  Scenario: Generate and Combine leave the delivery instruction a draft unless completing it is asked for

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderDraft | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineDraft | orderDraft            | product                 | 12         | shipper_DHL                 |

    When the order identified by orderDraft is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleDraft | orderLineDraft            | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningDraft_1        | orderLineDraft |

    When generate 5 additional M_Delivery_Planning records for: planningDraft_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                                          | C_OrderLine_ID |
      | planningDraft_1,planningDraft_2,planningDraft_3,planningDraft_4,planningDraft_5,planningDraft_6 | orderLineDraft |

    # GENERATE: draft by default, completed only when asked for
    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID  | M_Delivery_Planning_ID |
      | deliveryInstructionGenDraft | planningDraft_1        |
    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID | IsComplete |
      | deliveryInstructionGenDone | planningDraft_2        | true       |

    # COMBINE: same default, same option
    And combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID   | M_Delivery_Planning_ID          |
      | deliveryInstructionCombDraft | planningDraft_3,planningDraft_4 |
    And combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID  | M_Delivery_Planning_ID          | IsComplete |
      | deliveryInstructionCombDone | planningDraft_5,planningDraft_6 | true       |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionGenDraft           | shipper_DHL             | customer                       | customerLocation               | DR            |
      | deliveryInstructionGenDone            | shipper_DHL             | customer                       | customerLocation               | CO            |
      | deliveryInstructionCombDraft          | shipper_DHL             | customer                       | customerLocation               | DR            |
      | deliveryInstructionCombDone           | shipper_DHL             | customer                       | customerLocation               | CO            |

    # and a draft is what a planner who ticks nothing gets: the completion flag both processes offer is a
    # dictionary default, so the code honouring the flag it is handed says nothing about it on its own
    And validate AD_Process_Para:
      | Classname                                                                            | ColumnName | DefaultValue |
      | de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction    | IsComplete | N            |
      | de.metas.deliveryplanning.process.M_Delivery_Planning_CombineIntoDeliveryInstruction | IsComplete | N            |

  @Id:S31608_TC15
  Scenario: Both delivery instruction views return one consignment row per delivery planning

    # two articles, so every consignment row carries a second discriminator next to its quantity
    Given metasfresh contains M_Products:
      | Identifier |
      | product2   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO    | product2     | 20.0     | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderView  | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineView_1 | orderView             | product                 | 7          | shipper_DHL                 |
      | orderLineView_2 | orderView             | product2                | 3          | shipper_DHL                 |

    When the order identified by orderView is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleView_1 | orderLineView_1           | N             |
      | shipmentScheduleView_2 | orderLineView_2           | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | planningView_1         | orderLineView_1 |
      | planningView_2         | orderLineView_2 |

    # distinct quantities per planning: identical ones would let a mispairing read as correct
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_Product_ID | PlannedLoadedQuantity |
      | planningView_1         | 7          | 7            | Outgoing           | product      | 7                     |
      | planningView_2         | 3          | 3            | Outgoing           | product2     | 3                     |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID        |
      | deliveryInstructionView    | planningView_1,planningView_2 |

    Then the M_ShipperTransportation identified by deliveryInstructionView holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | LineNo | ActualLoadQty |
      | planningView_1         | 10     | 7             |
      | planningView_2         | 20     | 3             |

    # two plannings, two consignment rows - not the 2 x 2 an uncorrelated package join returns,
    # and each row carries its OWN planning's article and quantities
    And the M_ShipperTransportation identified by deliveryInstructionView has exactly the following rows in M_Delivery_Planning_Delivery_Instructions_V:
      | M_Delivery_Planning_ID | M_Product_ID | ActualLoadQty | ActualDischargeQuantity |
      | planningView_1         | product      | 7             | 0                       |
      | planningView_2         | product2     | 3             | 0                       |
    And the M_ShipperTransportation identified by deliveryInstructionView has exactly the following rows in M_ShipperTransportation_Delivery_Instructions_V:
      | M_Delivery_Planning_ID | M_Product_ID | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | planningView_1         | product      | 7                     | 0                       |
      | planningView_2         | product2     | 3                     | 0                       |
