@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29060_Delivery_Instruction
@ghActions:run_on_executor5
Feature: The delivery instruction's three-state delivered indicator

  A delivery instruction shows none / partly / fully delivered, derived from its actively allocated
  plannings' own delivered state (spec 5.7, AC13) - not re-derived from M_InOut directly, so the two
  levels cannot disagree. Stored, recomputed at the four write points that can change it: an allocation
  created or removed, and a receipt or shipment completed or reversed.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    And metasfresh contains M_PricingSystems
      | Identifier    | OPT.IsActive |
      | pricingSystem | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_PO | pricingSystem      | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID |
      | priceListVersion_PO | priceList_PO   |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_PO    | product      | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor      | Y        | N          | pricingSystem      |
      | warehouseBP | N        | N          |                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocation     | vendor                   | true                 | true                |
      | warehouseLocation  | warehouseBP              | true                 | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor                   | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                      | Name                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseState             | warehouseValueDeliveredState | warehouseNameDeliveredState | warehouseBP                   | warehouseLocation                      |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                    | M_Warehouse_ID.Identifier |
      | locatorState            | LocatorDeliveredState    | warehouseState             |
    And contains M_Shippers
      | Identifier      | OPT.IsCreateDeliveryPlanning |
      | shipper_forward | true                         |

  @Id:S31789_TC13
  Scenario: Two-planning instruction reports none / one / both delivered, and returns to partly on reversal

    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderState    | false   | vendor                   | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation                        | warehouseState                 | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineState | orderState             | product                 | 20         | shipper_forward             |

    When the order identified by orderState is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptScheduleState             | orderLineState             | vendor                    | vendorLocation                     | product                  | 20         | warehouseState              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | planningState_1        | orderLineState |

    # two plannings of one order line, so the instruction below carries exactly two allocations
    When generate 1 additional M_Delivery_Planning records for: planningState_1

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID            | C_OrderLine_ID |
      | planningState_1,planningState_2   | orderLineState |

    When combine M_Delivery_Planning into one M_ShipperTransportation:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID           |
      | deliveryInstructionState   | planningState_1,planningState_2  |

    # STATE 1: neither allocated planning has a receipt yet - the allocation-created write point
    # (DeliveryPlanningRepository#createAllocation) has already run twice (once per combine) by this point.
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveredState |
      | deliveryInstructionState               | shipper_forward         | vendor                          | warehouseLocation                  | NotDelivered        |

    # STATE 2: one of two allocated plannings is delivered - driven through the PRODUCTION
    # generate-receipt process, which generates the receipt and completes it in one call. Only because the
    # planning link is on the draft at that moment does interceptor/M_InOut#afterComplete fire and recompute
    # the instruction.
    When the delivery planning identified by planningState_1 generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID  |
      | 2023-02-05  | 10  | receiptState_1  |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveredState |
      | deliveryInstructionState               | shipper_forward         | vendor                          | warehouseLocation                  | PartlyDelivered     |

    # STATE 3: both allocated plannings are delivered - again through the production generate-receipt
    # process, this time for the second planning.
    When the delivery planning identified by planningState_2 generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID  |
      | 2023-02-06  | 10  | receiptState_2  |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveredState |
      | deliveryInstructionState               | shipper_forward         | vendor                          | warehouseLocation                  | FullyDelivered      |

    # REVERSAL: the one case a stored implementation would get wrong (spec 5.7) - the reverse-correct
    # write point (interceptor/M_InOut#afterReverseCorrect) must recompute too, falling back to Partly.
    When the material receipt identified by receiptState_2 is reversed

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveredState |
      | deliveryInstructionState               | shipper_forward         | vendor                          | warehouseLocation                  | PartlyDelivered     |
