@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29060_Delivery_Instruction
@ghActions:run_on_executor5
Feature: A closed delivery planning is finished and nothing processes it any further

  Closing a load means "I am done with this cargo, leave it alone".
  It is a terminal indicator, not an action: closing changes the flag and nothing else, so a load already on a
  truck stays on it, with its allocation, its shipping package and its release number. What closing does is
  BLOCK: the load can no longer be put on, moved between or taken off a delivery instruction, cancelling passes
  it by, the truck it rides on can be neither completed, re-activated nor voided, and closing or re-opening it
  a second time is an error rather than a silent no-op. Only two things still reach a closed load: the dates of
  the instruction it is still allocated to, and re-opening it.

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
      | M_Delivery_Planning_ID | M_ShippingPackage_ID   |
      | planningAdded_1        | shippingPackageAdded_1 |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID   | ActualLoadQty |
      | shippingPackageAdded_1 | 4             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningAdded_2        | 12         | 12           | Outgoing           | false    | null                       |
      | planningAdded_3        | 12         | 12           | Outgoing           | true     | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningAdded_2        |
      | planningAdded_3        |

  @Id:S31608_TC12
  Scenario: Closing a delivery planning leaves it on its delivery instruction and blocks the instruction's Re-Activate

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

    # Pressed on the grid, so the Close action's OWN precondition decides whether the button is offered - and it
    # is: the load is allocated to a finalised truck and carries a release number, and neither makes Close
    # unavailable. Closing takes nothing off the truck.
    When the planner presses Close on M_Delivery_Planning identified by planningFinal_2

    Then the M_ShipperTransportation identified by deliveryInstructionFinal holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID   |
      | planningFinal_1        | shippingPackageFinal_1 |
      | planningFinal_2        | shippingPackageFinal_2 |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID   | ActualLoadQty |
      | shippingPackageFinal_1 | 5             |
      | shippingPackageFinal_2 | 5             |
    # the flag moved; the allocation, the instruction reference and the release number did not
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningFinal_1        | 10         | 10           | Outgoing           | false    | deliveryInstructionFinal   |
      | planningFinal_2        | 10         | 10           | Outgoing           | true     | deliveryInstructionFinal   |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionFinal:
      | M_Delivery_Planning_ID |
      | planningFinal_1        |
      | planningFinal_2        |

    # what closing DOES do is block: the truck carrying a called-off load is not re-opened for editing
    When reactivating the M_ShipperTransportation identified by deliveryInstructionFinal is refused:
      | ErrorAdMessage                                                                   |
      | de.metas.deliveryplanning.ReActivateDeliveryInstruction.ClosedAllocatedPlannings |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionFinal              | shipper_DHL             | customer                       | customerLocation               | CO            |
    And the M_ShipperTransportation identified by deliveryInstructionFinal holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID   |
      | planningFinal_1        | shippingPackageFinal_1 |
      | planningFinal_2        | shippingPackageFinal_2 |

    # re-opening the load is what unblocks the instruction - pressed on the grid, precondition and all
    When the planner presses Re-Open on M_Delivery_Planning identified by planningFinal_2
    And the M_ShipperTransportation identified by deliveryInstructionFinal is reactivated

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionFinal              | shipper_DHL             | customer                       | customerLocation               | IP            |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | M_ShipperTransportation_ID |
      | planningFinal_2        | 10         | 10           | Outgoing           | false    | deliveryInstructionFinal   |

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

    # A MIXED selection - one closed, one open - is refused before the button is offered, naming the closed one.
    # Close is all-or-nothing, so a precondition that only asked "is any of them still open?" would offer the
    # button and let doIt abort the whole batch, closing nothing.
    Then pressing Close on M_Delivery_Planning identified by planningToggle_1,planningToggle_2 is unavailable:
      | ErrorAdMessage                                           |
      | de.metas.deliveryplanning.DeliveryPlanningService.Closed |

    # Re-Open is the mirror and refuses the same mixed selection, naming the open one: it is all-or-nothing too,
    # so a precondition that only asked "is any of them closed?" would offer the button and let doIt abort the
    # whole batch, re-opening nothing.
    And pressing Re-Open on M_Delivery_Planning identified by planningToggle_1,planningToggle_2 is unavailable:
      | ErrorAdMessage                                         |
      | de.metas.deliveryplanning.DeliveryPlanningService.Open |

    # and on the grid the button is not offered for the closed load on its own either: it is already in the
    # state the action would put it in. This is what the planner sees disabled, with the reason in the tooltip.
    And pressing Close on M_Delivery_Planning identified by planningToggle_1 is unavailable:
      | ErrorAdMessage                                           |
      | de.metas.deliveryplanning.DeliveryPlanningService.Closed |
    And pressing Re-Open on M_Delivery_Planning identified by planningToggle_2 is unavailable:
      | ErrorAdMessage                                         |
      | de.metas.deliveryplanning.DeliveryPlanningService.Open |

    # and the runtime backstop behind that button, which the process can be invoked past: closing a closed one,
    # and re-opening an open one, report the same message the precondition does - never a developer token
    # carrying a record's toString()
    And closing M_Delivery_Planning identified by planningToggle_1 is refused:
      | ErrorAdMessage                                           |
      | de.metas.deliveryplanning.DeliveryPlanningService.Closed |
    And reopening M_Delivery_Planning identified by planningToggle_2 is refused:
      | ErrorAdMessage                                         |
      | de.metas.deliveryplanning.DeliveryPlanningService.Open |
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
  Scenario: Cancelling is refused while a closed delivery planning rides the same instruction, and goes through once it is re-opened

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

    # both loads on ONE truck; then the planner calls the second one off
    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID            |
      | deliveryInstructionCancel  | planningCancel_1,planningCancel_2 |
    And the planner presses Close on M_Delivery_Planning identified by planningCancel_2

    # Cancel voids the truck, and voiding it would release EVERY load on it - including the closed one, whose
    # allocation, release number and dates would all be undone. Closed says "leave me alone", so the void is
    # refused and with it the whole cancel, rather than the closed load being quietly retired.
    When cancelling M_Delivery_Planning identified by planningCancel_1,planningCancel_2 is refused:
      | ErrorAdMessage                                                            |
      | de.metas.deliveryplanning.VoidDeliveryInstruction.ClosedAllocatedPlannings |

    # nothing moved: the truck still stands, with both loads on it
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCancel             | shipper_DHL             | customer                       | customerLocation               | DR            |
    And the M_ShipperTransportation identified by deliveryInstructionCancel holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID    |
      | planningCancel_1       | shippingPackageCancel_1 |
      | planningCancel_2       | shippingPackageCancel_2 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | OrderStatus | M_ShipperTransportation_ID |
      | planningCancel_1       | 10         | 10           | Outgoing           | false    | null        | deliveryInstructionCancel  |
      | planningCancel_2       | 10         | 10           | Outgoing           | true     | null        | deliveryInstructionCancel  |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionCancel:
      | M_Delivery_Planning_ID |
      | planningCancel_1       |
      | planningCancel_2       |

    # re-opening the called-off load lifts the refusal; the cancel then runs and voids the truck, releasing both
    When the planner presses Re-Open on M_Delivery_Planning identified by planningCancel_2
    And M_Delivery_Planning identified by planningCancel_1,planningCancel_2 is canceled

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCancel             | shipper_DHL             | customer                       | customerLocation               | VO            |
    And the M_ShipperTransportation identified by deliveryInstructionCancel holds no active M_Delivery_Planning_Alloc
    # both were still allocated to deliveryInstructionCancel when this cancel ran, so their planned figures
    # are committed cargo (D8/D19) and are left exactly as the earlier split set them, not zeroed
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | OrderStatus | PlannedLoadedQuantity | M_ShipperTransportation_ID |
      | planningCancel_1       | 10         | 10           | Outgoing           | true     | Canceled    | 5                     | null                       |
      | planningCancel_2       | 10         | 10           | Outgoing           | true     | Canceled    | 5                     | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningCancel_1       |
      | planningCancel_2       |

  @Id:S31608_TC24
  Scenario: Cancel passes a closed delivery planning by and cancels the open one on its own instruction

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderSkip  | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineSkip | orderSkip             | product                 | 10         | shipper_DHL                 |

    When the order identified by orderSkip is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleSkip | orderLineSkip             | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningSkip_1         | orderLineSkip  |

    # two loads of one order line: 5 + 5 of the 10 ordered
    When generate 1 additional M_Delivery_Planning records for: planningSkip_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID        | C_OrderLine_ID |
      | planningSkip_1,planningSkip_2 | orderLineSkip  |

    # a truck each, so cancelling one cannot reach the other's load
    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionOpen    | planningSkip_1         |
    And combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | deliveryInstructionClosed  | planningSkip_2         |
    And the planner presses Close on M_Delivery_Planning identified by planningSkip_2

    # both are selected; cancel is per row, so the closed one is passed by rather than aborting the selection
    When M_Delivery_Planning identified by planningSkip_1,planningSkip_2 is canceled

    # the open load's truck is voided and the load cancelled
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionOpen               | shipper_DHL             | customer                       | customerLocation               | VO            |
      | deliveryInstructionClosed             | shipper_DHL             | customer                       | customerLocation               | DR            |
    And the M_ShipperTransportation identified by deliveryInstructionOpen holds no active M_Delivery_Planning_Alloc

    # the closed load is untouched: still on its truck, still holding its allocation and its release number
    And the M_ShipperTransportation identified by deliveryInstructionClosed holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID  |
      | planningSkip_2         | shippingPackageSkip_2 |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID  | ActualLoadQty |
      | shippingPackageSkip_2 | 5             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | OrderStatus | M_ShipperTransportation_ID |
      | planningSkip_1         | 10         | 10           | Outgoing           | true     | Canceled    | null                       |
      | planningSkip_2         | 10         | 10           | Outgoing           | true     | null        | deliveryInstructionClosed  |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningSkip_1         |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionClosed:
      | M_Delivery_Planning_ID |
      | planningSkip_2         |
