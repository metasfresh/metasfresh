@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29060_Delivery_Instruction
@ghActions:run_on_executor5
Feature: The document lifecycle of a delivery instruction and its delivery plannings

  A planner finalises, re-opens and cancels a truck.
  Completing and re-activating leave the loads on the instruction; voiding releases every one of them and keeps
  the record of what was once planned. An instruction with nothing on it cannot be finalised at all, and one
  carrying a load the planner has called off can be neither completed, re-activated nor voided, while a plain
  transport order - which never carries plannings - is unaffected by any of it.

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

  @Id:S31608_TC7
  Scenario: Completing and re-activating keep the delivery plannings on the instruction, voiding releases them all

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderLife  | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineLife | orderLife             | product                 | 10         | shipper_DHL                 |

    When the order identified by orderLife is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleLife | orderLineLife             | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningLife_1         | orderLineLife  |

    # three loads of one order line: 4 + 3 + 3 of the 10 ordered
    When generate 2 additional M_Delivery_Planning records for: planningLife_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                       | C_OrderLine_ID |
      | planningLife_1,planningLife_2,planningLife_3 | orderLineLife  |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID                       |
      | deliveryInstructionLife    | planningLife_1,planningLife_2,planningLife_3 |

    # COMPLETE: the planner says the truck is final; its three loads stay exactly where they are
    When the M_ShipperTransportation identified by deliveryInstructionLife is completed

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionLife               | shipper_DHL             | customer                       | customerLocation               | CO            |
    And the M_ShipperTransportation identified by deliveryInstructionLife holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID  |
      | planningLife_1         | shippingPackageLife_1 |
      | planningLife_2         | shippingPackageLife_2 |
      | planningLife_3         | shippingPackageLife_3 |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID  | ActualLoadQty |
      | shippingPackageLife_1 | 4             |
      | shippingPackageLife_2 | 3             |
      | shippingPackageLife_3 | 3             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningLife_1         | 10         | 10           | Outgoing           | deliveryInstructionLife    |
      | planningLife_2         | 10         | 10           | Outgoing           | deliveryInstructionLife    |
      | planningLife_3         | 10         | 10           | Outgoing           | deliveryInstructionLife    |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionLife:
      | M_Delivery_Planning_ID |
      | planningLife_1         |
      | planningLife_2         |
      | planningLife_3         |

    # RE-ACTIVATE: open for editing again, still the same truck - so the loads are still on it
    When the M_ShipperTransportation identified by deliveryInstructionLife is reactivated

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionLife               | shipper_DHL             | customer                       | customerLocation               | IP            |
    And the M_ShipperTransportation identified by deliveryInstructionLife holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID  |
      | planningLife_1         | shippingPackageLife_1 |
      | planningLife_2         | shippingPackageLife_2 |
      | planningLife_3         | shippingPackageLife_3 |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID  | ActualLoadQty |
      | shippingPackageLife_1 | 4             |
      | shippingPackageLife_2 | 3             |
      | shippingPackageLife_3 | 3             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningLife_1         | 10         | 10           | Outgoing           | deliveryInstructionLife    |
      | planningLife_2         | 10         | 10           | Outgoing           | deliveryInstructionLife    |
      | planningLife_3         | 10         | 10           | Outgoing           | deliveryInstructionLife    |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionLife:
      | M_Delivery_Planning_ID |
      | planningLife_1         |
      | planningLife_2         |
      | planningLife_3         |

    # VOID: the truck is off, every load is released - retired, not erased
    When the M_ShipperTransportation identified by deliveryInstructionLife is voided

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionLife               | shipper_DHL             | customer                       | customerLocation               | VO            |
    And the M_ShipperTransportation identified by deliveryInstructionLife holds no active M_Delivery_Planning_Alloc
    And validate M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShipperTransportation_ID | IsActive |
      | planningLife_1         | deliveryInstructionLife    | false    |
      | planningLife_2         | deliveryInstructionLife    | false    |
      | planningLife_3         | deliveryInstructionLife    | false    |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningLife_1         | 10         | 10           | Outgoing           | null                       |
      | planningLife_2         | 10         | 10           | Outgoing           | null                       |
      | planningLife_3         | 10         | 10           | Outgoing           | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningLife_1         |
      | planningLife_2         |
      | planningLife_3         |

  @Id:S31608_TC8
  Scenario: A delivery instruction whose last delivery planning was taken off it cannot be completed

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderEmpty | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineEmpty | orderEmpty            | product                 | 6          | shipper_DHL                 |

    When the order identified by orderEmpty is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier            | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleEmpty | orderLineEmpty            | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningEmpty_1        | orderLineEmpty |

    # two loads of one order line: 3 + 3 of the 6 ordered
    When generate 1 additional M_Delivery_Planning records for: planningEmpty_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID          | C_OrderLine_ID |
      | planningEmpty_1,planningEmpty_2 | orderLineEmpty |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID          |
      | deliveryInstructionEmpty   | planningEmpty_1,planningEmpty_2 |
    And remove M_Delivery_Planning from M_ShipperTransportation:
      | M_Delivery_Planning_ID          |
      | planningEmpty_1,planningEmpty_2 |

    Then the M_ShipperTransportation identified by deliveryInstructionEmpty holds no active M_Delivery_Planning_Alloc

    # completing it would freight nothing while printing a document that resolves everything through the allocation
    When completing the M_ShipperTransportation identified by deliveryInstructionEmpty is refused:
      | ErrorAdMessage                                                                 |
      | de.metas.deliveryplanning.CompleteDeliveryInstruction.EmptyDeliveryInstruction |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionEmpty              | shipper_DHL             | customer                       | customerLocation               | DR            |

  @Id:S31608_TC23
  Scenario: A delivery instruction carrying a closed delivery planning cannot be completed

    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderCalled | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineCalled | orderCalled           | product                 | 8          | shipper_DHL                 |

    When the order identified by orderCalled is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier             | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleCalled | orderLineCalled           | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | planningCalled_1       | orderLineCalled |

    # two loads of one order line: 4 + 4 of the 8 ordered
    When generate 1 additional M_Delivery_Planning records for: planningCalled_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID            | C_OrderLine_ID  |
      | planningCalled_1,planningCalled_2 | orderLineCalled |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID            |
      | deliveryInstructionCalled  | planningCalled_1,planningCalled_2 |
    # the planner calls one load off AFTER it was loaded onto the truck; it stays on it, closed
    And the planner presses Close on M_Delivery_Planning identified by planningCalled_2

    Then the M_ShipperTransportation identified by deliveryInstructionCalled holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID |
      | planningCalled_1       |
      | planningCalled_2       |

    # finalising the truck would freight exactly what the planner already called off
    When completing the M_ShipperTransportation identified by deliveryInstructionCalled is refused:
      | ErrorAdMessage                                                                 |
      | de.metas.deliveryplanning.CompleteDeliveryInstruction.ClosedAllocatedPlannings |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCalled             | shipper_DHL             | customer                       | customerLocation               | DR            |

    # re-opening the load is what unblocks the truck; it then completes with both loads on board
    When the planner presses Re-Open on M_Delivery_Planning identified by planningCalled_2
    And the M_ShipperTransportation identified by deliveryInstructionCalled is completed

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCalled             | shipper_DHL             | customer                       | customerLocation               | CO            |
    And the M_ShipperTransportation identified by deliveryInstructionCalled holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID |
      | planningCalled_1       |
      | planningCalled_2       |

  @Id:S31608_TC25
  Scenario: A delivery instruction carrying a closed delivery planning cannot be voided either

    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderScrapped | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineScrapped | orderScrapped         | product                 | 8          | shipper_DHL                 |

    When the order identified by orderScrapped is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleScrapped | orderLineScrapped         | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID    |
      | planningScrapped_1     | orderLineScrapped |

    # two loads of one order line: 4 + 4 of the 8 ordered
    When generate 1 additional M_Delivery_Planning records for: planningScrapped_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                | C_OrderLine_ID    |
      | planningScrapped_1,planningScrapped_2 | orderLineScrapped |

    # Completed, because that is the state Void is OFFERED in: ShipperTransportationDocActionCustomizer removes
    # Void from a drafted instruction's actions, so a scenario voiding a draft would exercise a button the
    # planner is never shown.
    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID  | M_Delivery_Planning_ID                | IsComplete |
      | deliveryInstructionScrapped | planningScrapped_1,planningScrapped_2 | true       |
    # the planner calls one load off AFTER the truck was finalised; it stays on it, closed
    And the planner presses Close on M_Delivery_Planning identified by planningScrapped_2

    # Voiding is the action that would change the closed load the MOST: it releases every allocation on the
    # truck, so the called-off load would lose its allocation, its release number and its dates. Refused.
    When voiding the M_ShipperTransportation identified by deliveryInstructionScrapped is refused:
      | ErrorAdMessage                                                            |
      | de.metas.deliveryplanning.VoidDeliveryInstruction.ClosedAllocatedPlannings |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionScrapped           | shipper_DHL             | customer                       | customerLocation               | CO            |
    And the M_ShipperTransportation identified by deliveryInstructionScrapped holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID |
      | planningScrapped_1     |
      | planningScrapped_2     |
    And each M_Delivery_Planning has its own ReleaseNo stamped from M_ShipperTransportation deliveryInstructionScrapped:
      | M_Delivery_Planning_ID |
      | planningScrapped_1     |
      | planningScrapped_2     |

    # re-opening the load is what unblocks the void; both loads are then released
    When the planner presses Re-Open on M_Delivery_Planning identified by planningScrapped_2
    And the M_ShipperTransportation identified by deliveryInstructionScrapped is voided

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionScrapped           | shipper_DHL             | customer                       | customerLocation               | VO            |
    And the M_ShipperTransportation identified by deliveryInstructionScrapped holds no active M_Delivery_Planning_Alloc
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | M_ShipperTransportation_ID |
      | planningScrapped_1     | 8          | 8            | Outgoing           | null                       |
      | planningScrapped_2     | 8          | 8            | Outgoing           | null                       |
    And the following M_Delivery_Planning have no ReleaseNo:
      | M_Delivery_Planning_ID |
      | planningScrapped_1     |
      | planningScrapped_2     |

  @Id:S31608_TC9
  Scenario: A transport order carries no delivery plannings and completes unchanged

    Given metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_PO | pricingSystem      | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID |
      | priceListVersion_PO | priceList_PO   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_PO    | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | supplier   | Y        | N          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | supplierLocation | supplier                 | true                | true                |
    And contains M_Shippers
      | Identifier      |
      | shipper_freight |

    And metasfresh contains Transport Order
      | Identifier     | M_Shipper_ID    | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | transportOrder | shipper_freight | supplier            | supplierLocation    | Incoming           |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | M_Shipper_ID    |
      | orderTransport | N       | supplier                 | 2023-02-03  | POO             | pricingSystem                     | supplierLocation                      | A                | S                   | shipper_freight |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLineTransport | orderTransport        | product                 | 10         |

    When the order identified by orderTransport is completed
    And C_Order_AddTo_M_ShipperTransportation is invoked for order orderTransport and transportation order: transportOrder

    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: transportOrder

    # the rules that guard a delivery instruction are told apart by document sub-type, never by the absence of
    # allocations - so a transport order, which legitimately never has any, completes normally
    When the transport order identified by transportOrder is completed

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | transportOrder                        | shipper_freight         | supplier                       | supplierLocation               | CO            |
    And the M_ShipperTransportation identified by transportOrder holds no active M_Delivery_Planning_Alloc
