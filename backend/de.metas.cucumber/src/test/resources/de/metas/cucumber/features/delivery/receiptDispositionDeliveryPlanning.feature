@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29050_Delivery_Planning
@ghActions:run_on_executor5
Feature: The receipt-disposition delivery-planning window lists what is arriving, planned or not

  A procurement dispatcher planning inbound receipts has to read two lists today: the delivery plannings
  somebody already made, and the receipt schedules nobody has planned yet. RV_ReceiptDisposition_DeliveryPlanning is those two
  lists unioned, and every scenario here drives a REAL purchase order to the point where the row exists -
  a seeded row would prove the SQL and nothing about what a completing order actually produces.

  What decides which list an order lands on is the shipper on its order line: a shipper flagged
  IsCreateDeliveryPlanning gets a delivery planning generated on completion (a PLANNED row), an unflagged one
  does not (an UNPLANNED row, keyed 1000000000 + the receipt schedule id so the two branches stay disjoint).
  Outgoing transports are not receipt logistics and must not appear. Dropship ones do appear: the goods go
  from the vendor straight to the customer, but the planning is still made by the incoming generate command
  and still carries a receipt schedule.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    Given metasfresh contains M_PricingSystems
      | Identifier            |
      | pricingSystem_RL31789 |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID    | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_RL_PO   | pricingSystem_RL31789 | DE                    | EUR                 | false |
      | priceList_RL_SO   | pricingSystem_RL31789 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier             | M_PriceList_ID  |
      | priceListVersion_RL_PO | priceList_RL_PO |
      | priceListVersion_RL_SO | priceList_RL_SO |
    And metasfresh contains M_Products:
      | Identifier    |
      | product_RL    |
      | product2_RL   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_RL_PO | product_RL   | 5.0      | PCE               | Normal                        |
      | priceListVersion_RL_SO | product_RL   | 10.0     | PCE               | Normal                        |
      | priceListVersion_RL_PO | product2_RL  | 7.0      | PCE               | Normal                        |
      | priceListVersion_RL_SO | product2_RL  | 14.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID    |
      | vendor_RL       | Y        | N          | pricingSystem_RL31789 |
      | customer_RL     | N        | Y          | pricingSystem_RL31789 |
      | warehouseBP_RL  |          |            |                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocation_RL    | vendor_RL                | true                | true                |
      | customerLocation_RL  | customer_RL              | true                | true                |
      | warehouseLocation_RL | warehouseBP_RL           | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_RL                | product_RL              |
      | vendor_RL                | product2_RL             |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_RL              | warehouseBP_RL               | warehouseLocation_RL                  |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier |
      | locator_RL              | warehouse_RL              |
    And contains M_Shippers
      | Identifier          | OPT.IsCreateDeliveryPlanning |
      | shipperPlanning_RL  | true                         |
      | shipperPlain_RL     | false                        |

  @Id:S31789_TC1
  Scenario: A purchase order completing with a flagged shipper appears as a planned row

    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderPlanned_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC1       |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLinePlanned_RL | orderPlanned_RL       | product_RL              | 5          | shipperPlanning_RL          |

    When the order identified by orderPlanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | schedulePlanned_RL              | orderPlanned_RL       | orderLinePlanned_RL       | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID      |
      | planningPlanned_RL     | orderLinePlanned_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | C_Order_ID      | C_OrderLine_ID      |
      | planningPlanned_RL     | 5          | 5            | Incoming           | orderPlanned_RL | orderLinePlanned_RL |

    # The row keys on the PLANNING, which is what makes it a planned row rather than an unplanned one.
    Then after not more than 60s, the C_Order identified by orderPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.ETA    | OPT.QtyOrdered | OPT.C_BPartner_ID | OPT.M_Product_ID | OPT.M_Warehouse_ID | OPT.POReference |
      | rowPlanned_RL          | planningPlanned_RL     | schedulePlanned_RL   | 2023-02-20 | 5              | vendor_RL         | product_RL       | warehouse_RL       | PO-RL-TC1       |

  @Id:S31789_TC2
  Scenario: A purchase order completing with an unflagged shipper appears as an unplanned row

    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderUnplanned_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-25T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC2       |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineUnplanned_RL | orderUnplanned_RL     | product_RL              | 7          | shipperPlain_RL             |

    When the order identified by orderUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleUnplanned_RL            | orderUnplanned_RL     | orderLineUnplanned_RL     | vendor_RL                | vendorLocation_RL                 | product_RL              | 7          | warehouse_RL              |

    # No planning was generated, so the schedule itself is the row - and it keys 1000000000 above the
    # schedule id, which is what keeps the two branches' keys disjoint.
    Then after not more than 60s, the C_Order identified by orderUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.ETA    | OPT.QtyOrdered | OPT.C_BPartner_ID | OPT.M_Product_ID | OPT.M_Warehouse_ID | OPT.POReference |
      | rowUnplanned_RL        | null                   | scheduleUnplanned_RL | 2023-02-25 | 7              | vendor_RL         | product_RL       | warehouse_RL       | PO-RL-TC2       |

  @Id:S31789_TC3
  Scenario: An outgoing planning never appears, a dropship planning appears as a planned row

    # An outgoing transport: goods leave the warehouse, so receipt logistics has nothing to say about it.
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderOutgoing_RL  | true    | customer_RL              | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation_RL                   | warehouse_RL                  |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineOutgoing_RL | orderOutgoing_RL      | product_RL              | 5          | shipperPlanning_RL          |

    When the order identified by orderOutgoing_RL is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleOut_RL  | orderLineOutgoing_RL      | N             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID       |
      | planningOutgoing_RL    | orderLineOutgoing_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | C_Order_ID       | C_OrderLine_ID       |
      | planningOutgoing_RL    | 5          | 5            | Outgoing           | orderOutgoing_RL | orderLineOutgoing_RL |

    # Asserted only AFTER the planning exists: an empty result before generation would prove nothing.
    Then RV_ReceiptDisposition_DeliveryPlanning has no row for the C_Order identified by orderOutgoing_RL

    # A dropship: the goods go from the vendor straight to the customer, but the planning is still made by the
    # INCOMING generate command and still carries a receipt schedule, so it belongs on this list.
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.IsDropShip | OPT.DropShip_Location_ID.Identifier |
      | orderDropship_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | true           | customerLocation_RL                 |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineDropship_RL | orderDropship_RL      | product_RL              | 5          | shipperPlanning_RL          |

    When the order identified by orderDropship_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleDropship_RL             | orderDropship_RL      | orderLineDropship_RL      | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID       |
      | planningDropship_RL    | orderLineDropship_RL |
    # M_ReceiptSchedule_ID is asserted here because carrying a receipt schedule is what makes a dropship
    # planning receipt logistics - the fact the row's presence rests on.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | C_Order_ID       | C_OrderLine_ID       | M_ReceiptSchedule_ID |
      | planningDropship_RL    | 5          | 5            | Dropship           | orderDropship_RL | orderLineDropship_RL | scheduleDropship_RL  |

    Then after not more than 60s, the C_Order identified by orderDropship_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned | OPT.QtyOrdered | OPT.QtyToMove | OPT.PlannedDischargeQuantity | OPT.ActualDischargeQuantity |
      | rowDropship_RL         | planningDropship_RL    | scheduleDropship_RL  | true          | 5              | 5             | 5                            | 0                           |

  @Id:S31789_TC4
  Scenario: Each row type carries its ETA from its own source

    Given metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderEtaPlanned_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
      | orderEtaUnplanned_RL| false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier              | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineEtaPlanned_RL  | orderEtaPlanned_RL    | product_RL              | 5          | shipperPlanning_RL          |
      | orderLineEtaUnplanned_RL| orderEtaUnplanned_RL  | product_RL              | 5          | shipperPlain_RL             |

    When the order identified by orderEtaPlanned_RL is completed
    And the order identified by orderEtaUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleEtaPlanned_RL           | orderEtaPlanned_RL    | orderLineEtaPlanned_RL    | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
      | scheduleEtaUnplanned_RL         | orderEtaUnplanned_RL  | orderLineEtaUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID         |
      | planningEtaPlanned_RL  | orderLineEtaPlanned_RL |

    # Both rows start on the same date, so nothing distinguishes the two sources yet.
    Then after not more than 60s, the C_Order identified by orderEtaPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaPlanned_RL       | planningEtaPlanned_RL  | scheduleEtaPlanned_RL | 2023-02-20 | 2023-02-20                 |
    And after not more than 60s, the C_Order identified by orderEtaUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaUnplanned_RL     | null                   | scheduleEtaUnplanned_RL | 2023-02-20 | 2023-02-20                 |

    # The operator moves the promise on BOTH schedules.
    When update M_ReceiptSchedule:
      | M_ReceiptSchedule_ID    | OPT.DatePromised_Override |
      | scheduleEtaPlanned_RL   | 2023-03-15                |
      | scheduleEtaUnplanned_RL | 2023-03-15                |

    # The planned row keeps the PLANNING's arrival date and only its order promise moves; the unplanned row
    # has no plan of its own, so its arrival date follows the promise.
    Then after not more than 60s, the C_Order identified by orderEtaPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaPlanned_RL       | planningEtaPlanned_RL  | scheduleEtaPlanned_RL | 2023-02-20 | 2023-03-15                 |
    And after not more than 60s, the C_Order identified by orderEtaUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaUnplanned_RL     | null                   | scheduleEtaUnplanned_RL | 2023-03-15 | 2023-03-15                 |

  @Id:S31789_TC5
  Scenario: Calendar week matches the row's own ETA across a year boundary

    Given metasfresh contains C_Orders:
      | Identifier            | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderWeekPlanned_RL   | false   | vendor_RL                | 2022-12-20  | 2023-01-01T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
      | orderWeekUnplanned_RL | false   | vendor_RL                | 2022-12-20  | 2023-01-01T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier                | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineWeekPlanned_RL   | orderWeekPlanned_RL    | product_RL              | 5          | shipperPlanning_RL          |
      | orderLineWeekUnplanned_RL | orderWeekUnplanned_RL  | product_RL              | 5          | shipperPlain_RL             |

    When the order identified by orderWeekPlanned_RL is completed
    And the order identified by orderWeekUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier  | C_OrderLine_ID.Identifier  | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleWeekPlanned_RL          | orderWeekPlanned_RL    | orderLineWeekPlanned_RL    | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
      | scheduleWeekUnplanned_RL        | orderWeekUnplanned_RL  | orderLineWeekUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID          |
      | planningWeekPlanned_RL | orderLineWeekPlanned_RL |

    # 2023-01-01 is a Sunday: the calendar year has turned but the ISO week has not - it is still week 52
    # of ISO year 2022, not week 1 of 2023. Both row types must report 52, which only holds if the
    # derivation is the ISO week (postgres EXTRACT(week from ...)), not a naive "week of the new year".
    Then after not more than 60s, the C_Order identified by orderWeekPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID   | OPT.ETA    | OPT.CalendarWeek |
      | rowWeekPlanned_RL      | planningWeekPlanned_RL | scheduleWeekPlanned_RL | 2023-01-01 | 52               |
    And after not more than 60s, the C_Order identified by orderWeekUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID     | OPT.ETA    | OPT.CalendarWeek |
      | rowWeekUnplanned_RL    | null                   | scheduleWeekUnplanned_RL | 2023-01-01 | 52               |

  @Id:S31789_TC6
  Scenario: The planned flag is set on a planning-backed row and unset on a schedule-only row

    Given metasfresh contains C_Orders:
      | Identifier            | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderFlagPlanned_RL   | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
      | orderFlagUnplanned_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier                | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineFlagPlanned_RL   | orderFlagPlanned_RL   | product_RL              | 5          | shipperPlanning_RL          |
      | orderLineFlagUnplanned_RL | orderFlagUnplanned_RL | product_RL              | 5          | shipperPlain_RL              |

    When the order identified by orderFlagPlanned_RL is completed
    And the order identified by orderFlagUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleFlagPlanned_RL          | orderFlagPlanned_RL   | orderLineFlagPlanned_RL   | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
      | scheduleFlagUnplanned_RL        | orderFlagUnplanned_RL | orderLineFlagUnplanned_RL | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID           |
      | planningFlagPlanned_RL | orderLineFlagPlanned_RL  |

    # The flag is what a dispatcher reads to tell the two row types apart at a glance - it must agree with
    # the branch the row actually came from (a planning for the planned row, a bare schedule for the other).
    Then after not more than 60s, the C_Order identified by orderFlagPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.IsPlanned |
      | rowFlagPlanned_RL      | planningFlagPlanned_RL | scheduleFlagPlanned_RL | true          |
    And after not more than 60s, the C_Order identified by orderFlagUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.IsPlanned |
      | rowFlagUnplanned_RL    | null                   | scheduleFlagUnplanned_RL | false         |

  @Id:S31789_TC6b
  Scenario: Readiness follows the planning's own instruction, and a row with no planning is always ready

    Given metasfresh contains C_Orders:
      | Identifier           | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderRdyPlanned_RL   | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC6B-A    |
      | orderRdyUnplanned_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC6B-B    |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineRdyPlanned_RL   | orderRdyPlanned_RL    | product_RL              | 6          | shipperPlanning_RL          |
      | orderLineRdyUnplanned_RL | orderRdyUnplanned_RL  | product_RL              | 4          | shipperPlain_RL             |

    When the order identified by orderRdyPlanned_RL is completed
    And the order identified by orderRdyUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleRdyPlanned_RL           | orderRdyPlanned_RL    | orderLineRdyPlanned_RL    | vendor_RL                | vendorLocation_RL                 | product_RL              | 6          | warehouse_RL              |
      | scheduleRdyUnplanned_RL         | orderRdyUnplanned_RL  | orderLineRdyUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL              | 4          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID         |
      | planningRdy_RL         | orderLineRdyPlanned_RL |

    Then after not more than 60s, the C_Order identified by orderRdyPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.IsPlanned | OPT.IsReadyForReceipt |
      | rowRdyPlanned_RL       | planningRdy_RL         | scheduleRdyPlanned_RL | true          | false                 |

    # A DRAFT instruction is the planner still arranging the transport, so allocation ALONE does not make the row
    # actionable - only the instruction completing does.
    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID | IsComplete |
      | transportRdy_RL            | planningRdy_RL         | false      |

    Then after not more than 60s, the C_Order identified by orderRdyPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.IsPlanned | OPT.IsReadyForReceipt |
      | rowRdyPlanned_RL       | planningRdy_RL         | scheduleRdyPlanned_RL | true          | false                 |

    # THE transition, on the SAME planning: two plannings compared side by side would pass on a flag that never moves.
    When the transport order identified by transportRdy_RL is completed

    Then after not more than 60s, the C_Order identified by orderRdyPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.IsPlanned | OPT.IsReadyForReceipt |
      | rowRdyPlanned_RL       | planningRdy_RL         | scheduleRdyPlanned_RL | true          | true                  |

    # An unplanned row has no planning to wait on, so it is ready from the moment it appears - never 'N' first.
    And after not more than 60s, the C_Order identified by orderRdyUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.IsPlanned | OPT.IsReadyForReceipt |
      | rowRdyUnplanned_RL     | null                   | scheduleRdyUnplanned_RL | false         | true                  |

  @Id:S31789_TC7
  Scenario: Receiving an unplanned row produces a plain receipt against its schedule

    # An unplanned row is a receipt schedule nobody has planned. Receiving it must produce exactly what
    # window 541954 produces: a completed receipt booked against that schedule and linked to NO delivery
    # planning - the row has none to link to, and inventing one would be worse than omitting it.
    Given metasfresh contains C_Orders:
      | Identifier            | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderRcvUnplanned_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC7       |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineRcvUnplanned_RL | orderRcvUnplanned_RL  | product_RL              | 7          | shipperPlain_RL             |

    When the order identified by orderRcvUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleRcvUnplanned_RL         | orderRcvUnplanned_RL  | orderLineRcvUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL              | 7          | warehouse_RL              |
    And after not more than 60s, the C_Order identified by orderRcvUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.IsPlanned |
      | rowRcvUnplanned_RL     | null                   | scheduleRcvUnplanned_RL | false         |

    When the receipt-disposition delivery-planning row identified by rowRcvUnplanned_RL is received:
      | OPT.Qty | OPT.M_InOut_ID       |
      | 7       | receiptUnplanned_RL  |

    And validate M_In_Out status
      | M_InOut_ID          | DocStatus |
      | receiptUnplanned_RL | CO        |

    # The row carried no planning id, so the receipt's line carries none either - the null branch of the shared
    # request.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID          | C_OrderLine_ID           | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptUnplanned_RL | orderLineRcvUnplanned_RL | null                   | 7               |

    # ... and the goods are booked on the schedule, which is what "the same receipt as window 541954" means.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleRcvUnplanned_RL         | orderRcvUnplanned_RL  | orderLineRcvUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL              | 7          | warehouse_RL              | 7            |

  @Id:S31789_TC8
  Scenario: Receiving a planned row produces the receipt the delivery-planning window would, planning and all

    # THE point of the shared request: the planning id travels INSIDE the request onto the DRAFT receipt header, and
    # the completion in the same call fires the delivery-planning interceptor. Everything asserted below is derived
    # from that id being present BEFORE completion - set afterwards, or dropped (as the HU-editor path does), every
    # one of these assertions fails.
    Given metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderRcvPlanned_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC8       |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineRcvPlanned_RL | orderRcvPlanned_RL    | product_RL              | 5          | shipperPlanning_RL          |

    When the order identified by orderRcvPlanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleRcvPlanned_RL           | orderRcvPlanned_RL    | orderLineRcvPlanned_RL    | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID         |
      | planningRcvPlanned_RL  | orderLineRcvPlanned_RL |
    And after not more than 60s, the C_Order identified by orderRcvPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.IsPlanned |
      | rowRcvPlanned_RL       | planningRcvPlanned_RL  | scheduleRcvPlanned_RL | true          |

    # Nothing received yet: the discharge end is empty and the planning is still open.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity | IsClosed | Processed |
      | planningRcvPlanned_RL  | 5          | 5            | Incoming           | 0                       | false    | false     |

    When the receipt-disposition delivery-planning row identified by rowRcvPlanned_RL is received:
      | OPT.Qty | OPT.M_InOut_ID     |
      | 5       | receiptPlanned_RL  |

    And validate M_In_Out status
      | M_InOut_ID        | DocStatus |
      | receiptPlanned_RL | CO        |

    # The link the HU-editor path silently omits.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID        | C_OrderLine_ID         | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptPlanned_RL | orderLineRcvPlanned_RL | planningRcvPlanned_RL  | 5               |

    # ... and everything the interceptor derives from it, plus the planned-discharge write-back that
    # M_Delivery_Planning_GenerateReceipt performs - i.e. exactly the state the delivery-planning window leaves.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity | M_InOut_ID        | IsClosed | Processed |
      | planningRcvPlanned_RL  | 5          | 0            | Incoming           | 5                        | 5                       | receiptPlanned_RL | false    | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleRcvPlanned_RL           | orderRcvPlanned_RL    | orderLineRcvPlanned_RL    | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              | 5            |

  @Id:S31789_TC8b
  Scenario: Receiving a planned row through the HU editor lands the planning on the receipt LINE

    # The receipt-disposition window's "HUs annehmen" is TWO steps, and this scenario is the second one reaching
    # the database. The action itself only GENERATES planning HUs and opens the HU editor; the goods are booked by
    # the confirm inside that editor, which resolves the launching row back to its receipt schedule AND its
    # delivery planning and hands the planning to processReceiptSchedules keyed by the HUs being confirmed.
    #
    # What is at stake is the key: InOutProducerFromReceiptScheduleHU#extractDeliveryPlanningId looks the planning
    # up by the allocation's TU id and then its LU id, mirroring #isInSelectedHUs - so a map keyed by anything
    # other than the HUs handed over as selected silently produces a receipt line with NO planning, while the
    # receive still reports success. Here the HU received is an LU, i.e. the fallback half of that lookup.
    #
    # The step below is the confirm's own call and shares its map builder
    # (ReceiptFromReceiptScheduleService#deliveryPlanningIdByHuId); the row-to-planning resolution it cannot reach
    # from here - de.metas.cucumber excludes de.metas.ui.web.base - is covered by HUEditorReceiptSourcesTest.
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderRcvHU_RL      | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC8B      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineRcvHU_RL | orderRcvHU_RL         | product_RL              | 10         | shipperPlanning_RL          |

    When the order identified by orderRcvHU_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleRcvHU_RL                | orderRcvHU_RL         | orderLineRcvHU_RL         | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID    |
      | planningRcvHU_RL       | orderLineRcvHU_RL |
    And after not more than 60s, the C_Order identified by orderRcvHU_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowRcvHU_RL                               | planningRcvHU_RL       | scheduleRcvHU_RL     | true          |

    # Nothing received yet.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity | IsClosed | Processed |
      | planningRcvHU_RL       | 10         | 10           | Incoming           | 0                       | false    | false     |

      # This scenario's own packing instructions, deliberately built here rather than borrowing seeded ids.
      # It previously used M_HU_PI_Item_Product 101 ("No Packing Item") and M_HU_PI 1000006
      # ("EUR-Tauschpalette Holz") - the latter an INACTIVE returnable pallet, i.e. real Gebinde packing
      # material. Generating HUs against it made every HU here carry packing material, so the empties path
      # became relevant for this feature's own warehouse; that warehouse has no line in the Gebinde
      # distribution network, and the resulting exception surfaced inside unrelated scenarios sharing this
      # executor. Note the deliberate absence of a PM item below: with no packing material there is no
      # empties relationship to be missing in the first place.
      And metasfresh contains M_HU_PI:
        | M_HU_PI_ID | Name     |
        | tuPi_RL    | RL_TU_PI |
        | luPi_RL    | RL_LU_PI |
      And metasfresh contains M_HU_PI_Version:
        | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
        | tuPiVer_RL         | tuPi_RL    | TU          | Y         |
        | luPiVer_RL         | luPi_RL    | LU          | Y         |
      And metasfresh contains M_HU_PI_Item:
        | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
        | tuMiItem_RL     | tuPiVer_RL         | 0   | MI       |                   |
        | luHuItem_RL     | luPiVer_RL         | 100 | HU       | tuPi_RL           |
      And metasfresh contains M_HU_PI_Item_Product:
        | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
        | pipTU_RL                | tuMiItem_RL     | product_RL   | 10  | 2000-01-01 |

    # Step one of the gesture: the action generates the planning HUs and hands them to the editor. Nothing is
    # booked, which is why the planning is still untouched below.
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | lutuConfigRcvHU_RL                    | huRcvHU_RL         | scheduleRcvHU_RL                | N               | 1     | N               | 1     | N               | 10          | pipTU_RL                           | luPi_RL                      |

    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity | IsClosed | Processed |
      | planningRcvHU_RL       | 10         | 10           | Incoming           | 0                       | false    | false     |

    # Step two: the confirm inside the editor.
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Delivery_Planning_ID |
      | huRcvHU_RL         | scheduleRcvHU_RL                | receiptRcvHU_RL       | planningRcvHU_RL           |

    And validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptRcvHU_RL | CO        |

    # THE assertion this scenario exists for: the planning is on the receipt LINE.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID      | C_OrderLine_ID    | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptRcvHU_RL | orderLineRcvHU_RL | planningRcvHU_RL       | 10              |

    # ... and everything the TIMING_AFTER_COMPLETE interceptor derives from that link. PlannedDischargeQuantity is
    # deliberately NOT asserted as changed: a receive no longer overwrites the plan.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity | M_InOut_ID      | IsClosed | Processed |
      | planningRcvHU_RL       | 10         | 0            | Incoming           | 10                       | 10                      | receiptRcvHU_RL | false    | true      |

  @Id:S31789_TC9
  Scenario: Several unplanned rows received together are grouped exactly as the receipt-schedule batch groups them

    # The multi-row receive is one gesture over a whole selection. What must NOT depend on the gesture is the
    # grouping: two rows of the SAME order belong on one receipt, a row of another order cannot join it. That is
    # InOutProducer#isNewReceiptRequired's rule - header aggregation key plus an unchanged C_Order_ID - and it is
    # reused rather than reinvented, which is exactly what this scenario pins: three selected rows, two receipts.
    Given metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderMultiA_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9A      |
      | orderMultiB_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9B      |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineMultiA1_RL | orderMultiA_RL        | product_RL              | 4          | shipperPlain_RL             |
      | orderLineMultiA2_RL | orderMultiA_RL        | product2_RL             | 6          | shipperPlain_RL             |
      | orderLineMultiB1_RL | orderMultiB_RL        | product_RL              | 3          | shipperPlain_RL             |

    When the order identified by orderMultiA_RL is completed
    And the order identified by orderMultiB_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleMultiA1_RL              | orderMultiA_RL        | orderLineMultiA1_RL       | vendor_RL                | vendorLocation_RL                 | product_RL              | 4          | warehouse_RL              |
      | scheduleMultiA2_RL              | orderMultiA_RL        | orderLineMultiA2_RL       | vendor_RL                | vendorLocation_RL                 | product2_RL             | 6          | warehouse_RL              |
      | scheduleMultiB1_RL              | orderMultiB_RL        | orderLineMultiB1_RL       | vendor_RL                | vendorLocation_RL                 | product_RL              | 3          | warehouse_RL              |
    And after not more than 60s, the C_Order identified by orderMultiA_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowMultiA1_RL          | null                   | scheduleMultiA1_RL   | false         |
      | rowMultiA2_RL          | null                   | scheduleMultiA2_RL   | false         |
    And after not more than 60s, the C_Order identified by orderMultiB_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowMultiB1_RL          | null                   | scheduleMultiB1_RL   | false         |

    # Completing the two orders above leaves their invoice candidates flagged 'to recompute', and the async
    # UpdateInvalidInvoiceCandidatesWorkpackageProcessor updates those same candidates in its own transaction.
    # The receive gesture below writes them synchronously too, via
    # M_InOutLine.createC_InvoiceCandidate_InOutLines - so while the recompute is still in flight the two
    # transactions take c_invoice_candidate row locks in opposite order and Postgres kills one of them
    # (DBDeadLockDetectedException). Waiting for the flag to clear is commit-visible, not a sleep: the flag is
    # only gone once the updater's transaction committed and dropped its row locks, so the second writer is
    # removed rather than merely re-timed. Purely a precondition - no assertion is relaxed.
    And after not more than 60s locate invoice candidates by order id:
      | C_Invoice_Candidate_ID.Identifier | C_Order_ID.Identifier |
      | icMultiA1_RL, icMultiA2_RL        | orderMultiA_RL        |
      | icMultiB1_RL                      | orderMultiB_RL        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | icMultiA1_RL                      |
      | icMultiA2_RL                      |
      | icMultiB1_RL                      |

    # Three rows in, TWO receipts out: the two rows of order A share one, order B's cannot join them.
    When the receipt-disposition delivery-planning rows identified by rowMultiA1_RL, rowMultiA2_RL, rowMultiB1_RL are received together:
      | M_InOut_ID       |
      | receiptMultiA_RL |
      | receiptMultiB_RL |

    Then validate M_In_Out status
      | M_InOut_ID       | DocStatus |
      | receiptMultiA_RL | CO        |
      | receiptMultiB_RL | CO        |

    # No row carried a planning, so no line carries one - the null branch of the shared request, N times.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID       | C_OrderLine_ID      | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptMultiA_RL | orderLineMultiA1_RL | null                   | 4               |
      | receiptMultiA_RL | orderLineMultiA2_RL | null                   | 6               |
      | receiptMultiB_RL | orderLineMultiB1_RL | null                   | 3               |

    # WHICH rows landed together, and with what quantity - the grouping assertion proper.
    And validate the created material receipt lines
      | M_InOut_ID       | C_OrderLine_ID      | MovementQty |
      | receiptMultiA_RL | orderLineMultiA1_RL | 4           |
      | receiptMultiA_RL | orderLineMultiA2_RL | 6           |
      | receiptMultiB_RL | orderLineMultiB1_RL | 3           |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleMultiA1_RL              | orderMultiA_RL        | orderLineMultiA1_RL       | vendor_RL                | vendorLocation_RL                 | product_RL              | 4          | warehouse_RL              | 4            |
      | scheduleMultiA2_RL              | orderMultiA_RL        | orderLineMultiA2_RL       | vendor_RL                | vendorLocation_RL                 | product2_RL             | 6          | warehouse_RL              | 6            |
      | scheduleMultiB1_RL              | orderMultiB_RL        | orderLineMultiB1_RL       | vendor_RL                | vendorLocation_RL                 | product_RL              | 3          | warehouse_RL              | 3            |

  @Id:S31789_TC9b
  Scenario: Several planned rows received together share ONE receipt, each planning on its own line

    # Two planned rows of the SAME order - so the aggregation key and the C_Order_ID agree, which is exactly
    # what the receipt-schedule window's batch would put on ONE receipt. The planning does not split them,
    # because the planning lives on the LINE: each gets its own line, each line names its own planning, and
    # both plannings get their delivered state from that one document.
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderPlanTwo_RL | false | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9B2     |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLinePlanTwo1_RL | orderPlanTwo_RL     | product_RL              | 4          | shipperPlanning_RL          |
      | orderLinePlanTwo2_RL | orderPlanTwo_RL     | product2_RL             | 6          | shipperPlanning_RL          |

    When the order identified by orderPlanTwo_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | schedulePlanTwo1_RL             | orderPlanTwo_RL       | orderLinePlanTwo1_RL      | vendor_RL                | vendorLocation_RL                 | product_RL              | 4          | warehouse_RL              |
      | schedulePlanTwo2_RL             | orderPlanTwo_RL       | orderLinePlanTwo2_RL      | vendor_RL                | vendorLocation_RL                 | product2_RL             | 6          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID       |
      | planningPlanTwo1_RL    | orderLinePlanTwo1_RL |
      | planningPlanTwo2_RL    | orderLinePlanTwo2_RL |
    And after not more than 60s, the C_Order identified by orderPlanTwo_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowPlanTwo1_RL         | planningPlanTwo1_RL    | schedulePlanTwo1_RL  | true          |
      | rowPlanTwo2_RL         | planningPlanTwo2_RL    | schedulePlanTwo2_RL  | true          |


    # Completing the order above leaves its invoice candidates flagged 'to recompute', and the async
    # UpdateInvalidInvoiceCandidatesWorkpackageProcessor updates those same candidates in its own transaction.
    # The receive below writes them synchronously too, via M_InOutLine.createC_InvoiceCandidate_InOutLines - so
    # while the recompute is still in flight the two transactions take c_invoice_candidate row locks in opposite
    # order and Postgres kills one of them (DBDeadLockDetectedException). Waiting for the flag to clear is
    # commit-visible, not a sleep: the flag is only gone once the updater's transaction committed and dropped its
    # row locks. Purely a precondition - no assertion is relaxed. Same guard as S31789_TC9.
    And after not more than 60s locate invoice candidates by order id:
      | C_Invoice_Candidate_ID.Identifier | C_Order_ID.Identifier |
      | icPlanTwo1_RL, icPlanTwo2_RL        | orderPlanTwo_RL        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | icPlanTwo1_RL                     |
      | icPlanTwo2_RL                     |

    When the receipt-disposition delivery-planning rows identified by rowPlanTwo1_RL, rowPlanTwo2_RL are received together:
      | M_InOut_ID         |
      | receiptPlanTwo_RL  |

    Then validate M_In_Out status
      | M_InOut_ID        | DocStatus |
      | receiptPlanTwo_RL | CO        |

    # Each LINE carries ITS OWN planning - the link the HU-editor path silently omits, here N times over on one
    # document.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID        | C_OrderLine_ID       | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptPlanTwo_RL | orderLinePlanTwo1_RL | planningPlanTwo1_RL    | 4               |
      | receiptPlanTwo_RL | orderLinePlanTwo2_RL | planningPlanTwo2_RL    | 6               |

    # ... and everything the completion interceptor derives from those ids, for BOTH plannings - i.e. exactly what
    # receiving each of them from the delivery-planning window one at a time would have left, except that both
    # now name the same receipt.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity | M_InOut_ID        | IsClosed | Processed |
      | planningPlanTwo1_RL    | 4          | 0            | Incoming           | 4                        | 4                       | receiptPlanTwo_RL | false    | true      |
      | planningPlanTwo2_RL    | 6          | 0            | Incoming           | 6                        | 6                       | receiptPlanTwo_RL | false    | true      |

  @Id:S31789_TC9c
  Scenario: A mixed selection routes per row, so one gesture yields a linked line beside a plain one

    # Not an expected operator use case - but routing is PER ROW, so it works anyway, and behaviour that ships
    # untested is a liability. Nothing extra is built for it: this asserts what per-row routing already produces.
    # The two rows sit on ONE order and agree on the aggregation key, so they DO share a receipt; what the
    # planned row gets that the unplanned one does not is the planning on its own line.
    Given metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderMixed_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9C      |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineMixedPlan_RL | orderMixed_RL         | product_RL              | 5          | shipperPlanning_RL          |
      | orderLineMixedPlain_RL| orderMixed_RL         | product2_RL             | 7          | shipperPlain_RL             |

    When the order identified by orderMixed_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleMixedPlan_RL            | orderMixed_RL         | orderLineMixedPlan_RL     | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              |
      | scheduleMixedPlain_RL           | orderMixed_RL         | orderLineMixedPlain_RL    | vendor_RL                | vendorLocation_RL                 | product2_RL             | 7          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID        |
      | planningMixed_RL       | orderLineMixedPlan_RL |
    And after not more than 60s, the C_Order identified by orderMixed_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.IsPlanned |
      | rowMixedPlan_RL        | planningMixed_RL       | scheduleMixedPlan_RL  | true          |
      | rowMixedPlain_RL       | null                   | scheduleMixedPlain_RL | false         |


    # Completing the order above leaves its invoice candidates flagged 'to recompute', and the async
    # UpdateInvalidInvoiceCandidatesWorkpackageProcessor updates those same candidates in its own transaction.
    # The receive below writes them synchronously too, via M_InOutLine.createC_InvoiceCandidate_InOutLines - so
    # while the recompute is still in flight the two transactions take c_invoice_candidate row locks in opposite
    # order and Postgres kills one of them (DBDeadLockDetectedException). Waiting for the flag to clear is
    # commit-visible, not a sleep: the flag is only gone once the updater's transaction committed and dropped its
    # row locks. Purely a precondition - no assertion is relaxed. Same guard as S31789_TC9.
    And after not more than 60s locate invoice candidates by order id:
      | C_Invoice_Candidate_ID.Identifier | C_Order_ID.Identifier |
      | icMixed1_RL, icMixed2_RL        | orderMixed_RL        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | icMixed1_RL                       |
      | icMixed2_RL                       |

    When the receipt-disposition delivery-planning rows identified by rowMixedPlan_RL, rowMixedPlain_RL are received together:
      | M_InOut_ID      |
      | receiptMixed_RL |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptMixed_RL | CO        |

    # The planned row's LINE is linked, the unplanned row's is not - one gesture, two paths, decided per row.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID      | C_OrderLine_ID         | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptMixed_RL | orderLineMixedPlan_RL  | planningMixed_RL       | 5               |
      | receiptMixed_RL | orderLineMixedPlain_RL | null                   | 7               |

    # 5, not 12: the planning books its OWN line, never the whole document - which is what the unplanned line
    # sharing this receipt puts to the test.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity | M_InOut_ID      | IsClosed | Processed |
      | planningMixed_RL       | 5          | 0            | Incoming           | 5                       | receiptMixed_RL | false    | true      |

  @Id:S31789_TC9d
  Scenario: Two plannings of ONE order line are received together without receiving that line twice

    # The sharpest case, and the one a naive "one row -> the schedule's whole remaining quantity" implementation
    # gets wrong. A split copies M_ReceiptSchedule_ID onto every new planning, so these two rows point at the
    # SAME receipt schedule and the SAME order line. Received together they must consume that line exactly once:
    # each planning receives ITS OWN planned share onto ITS OWN line of the one receipt, and the schedule's
    # QtyMoved ends at QtyOrdered, not above it.
    Given metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderSplit_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9D      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineSplit_RL | orderSplit_RL         | product_RL              | 10         | shipperPlanning_RL          |

    When the order identified by orderSplit_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleSplit_RL                | orderSplit_RL         | orderLineSplit_RL         | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID    |
      | planningSplit1_RL      | orderLineSplit_RL |

    # The split itself: the order line's 10 is distributed over the two plannings, 5 each.
    When generate 1 additional M_Delivery_Planning records for: planningSplit1_RL
    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID              | C_OrderLine_ID    |
      | planningSplit1_RL,planningSplit2_RL | orderLineSplit_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | TransportDirection | Processed |
      | planningSplit1_RL      | 10         | 10           | 5                        | Incoming           | false     |
      | planningSplit2_RL      | 10         | 10           | 5                        | Incoming           | false     |

    # BOTH rows carry the same receipt schedule - that is the shape under test.
    And after not more than 60s, the C_Order identified by orderSplit_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowSplit1_RL           | planningSplit1_RL      | scheduleSplit_RL     | true          |
      | rowSplit2_RL           | planningSplit2_RL      | scheduleSplit_RL     | true          |

    When the receipt-disposition delivery-planning rows identified by rowSplit1_RL, rowSplit2_RL are received together:
      | M_InOut_ID       |
      | receiptSplit_RL  |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptSplit_RL | CO        |

    # 5 and 5 against the ONE order line, never 10 and 10 - and the two lines are told apart by nothing but
    # their planning, which is the whole reason the link is on the line.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID      | C_OrderLine_ID    | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptSplit_RL | orderLineSplit_RL | planningSplit1_RL      | 5               |
      | receiptSplit_RL | orderLineSplit_RL | planningSplit2_RL      | 5               |

    # The line is consumed exactly once: QtyMoved equals QtyOrdered, and each planning carries its own half.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleSplit_RL                | orderSplit_RL         | orderLineSplit_RL         | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              | 10           |
    # QtyTotalOpen is the ORDER LINE's remaining figure - QtyOrdered less what has ACTUALLY arrived on it - so
    # both plannings read 0 once the two halves together have consumed the line exactly once. It reading
    # anything else is the over-receive this scenario exists to catch.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | M_InOut_ID      | IsClosed | Processed |
      | planningSplit1_RL      | 10         | 0            | 5                        | 5                       | Incoming           | receiptSplit_RL | false    | true      |
      | planningSplit2_RL      | 10         | 0            | 5                        | 5                       | Incoming           | receiptSplit_RL | false    | true      |

  @Id:S31789_TC9f
  Scenario: Three plannings of ONE order line received together land on ONE receipt, one line per planning

    # A receipt LINE corresponds to a receipt schedule and therefore to a planning; a receipt HEADER aggregates
    # lines by the standard criteria. So three plannings of one order line, received in one gesture, are one
    # receipt with three lines - which is also what the receipt-schedule window would produce for that order.
    # Three rather than two: 10 over three plannings distributes 4/3/3, so the selection contains both a line no
    # other line's quantity can be confused with AND two lines of equal quantity that only their own planning
    # tells apart.
    Given metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderTriple_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9F      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineTriple_RL | orderTriple_RL        | product_RL              | 10         | shipperPlanning_RL          |

    When the order identified by orderTriple_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleTriple_RL               | orderTriple_RL        | orderLineTriple_RL        | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID     |
      | planningTriple1_RL     | orderLineTriple_RL |

    # The split: the order line's 10 goes 4/3/3 over the three plannings - the target absorbs the down-rounding
    # remainder.
    When generate 2 additional M_Delivery_Planning records for: planningTriple1_RL
    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                   | C_OrderLine_ID     |
      | planningTriple1_RL,planningTriple2_RL,planningTriple3_RL | orderLineTriple_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | TransportDirection | Processed |
      | planningTriple1_RL     | 10         | 10           | 4                        | Incoming           | false     |
      | planningTriple2_RL     | 10         | 10           | 3                        | Incoming           | false     |
      | planningTriple3_RL     | 10         | 10           | 3                        | Incoming           | false     |

    # All three rows carry the SAME receipt schedule - that is the shape under test.
    And after not more than 60s, the C_Order identified by orderTriple_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowTriple1_RL          | planningTriple1_RL     | scheduleTriple_RL    | true          |
      | rowTriple2_RL          | planningTriple2_RL     | scheduleTriple_RL    | true          |
      | rowTriple3_RL          | planningTriple3_RL     | scheduleTriple_RL    | true          |

    # ONE row in the table, so ONE receipt: the three rows agree on the header aggregation key and on the order,
    # so nothing keeps them apart.
    When the receipt-disposition delivery-planning rows identified by rowTriple1_RL, rowTriple2_RL, rowTriple3_RL are received together:
      | M_InOut_ID       |
      | receiptTriple_RL |

    Then validate M_In_Out status
      | M_InOut_ID       | DocStatus |
      | receiptTriple_RL | CO        |

    # Three lines on the one receipt, each naming its own planning - and the two 3s are told apart by nothing
    # but that.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID       | C_OrderLine_ID     | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptTriple_RL | orderLineTriple_RL | planningTriple1_RL     | 4               |
      | receiptTriple_RL | orderLineTriple_RL | planningTriple2_RL     | 3               |
      | receiptTriple_RL | orderLineTriple_RL | planningTriple3_RL     | 3               |

    # The line is consumed exactly once: QtyMoved reaches QtyOrdered, never above it.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleTriple_RL               | orderTriple_RL        | orderLineTriple_RL        | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              | 10           |

    # Every planning gets its OWN discharge actual off its OWN line, and every planning is delivered - the state
    # a per-header derivation cannot produce for more than one of them.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | M_InOut_ID       | IsClosed | Processed |
      | planningTriple1_RL     | 10         | 0            | 4                        | 4                       | Incoming           | receiptTriple_RL | false    | true      |
      | planningTriple2_RL     | 10         | 0            | 3                        | 3                       | Incoming           | receiptTriple_RL | false    | true      |
      | planningTriple3_RL     | 10         | 0            | 3                        | 3                       | Incoming           | receiptTriple_RL | false    | true      |

  @Id:S31789_TC9e
  Scenario: One row of a SPLIT planning received ALONE takes only its own share, so its sibling can still receive

    # The single-row counterpart of TC9d: the two split rows are received ONE AT A TIME, as the window's "CUs
    # annehmen" button does. A split copies M_ReceiptSchedule_ID onto both plannings, so the SCHEDULE's remainder
    # is the whole ORDER LINE's (10) while each PLANNING's share is 5 - a receive that read the schedule would
    # consume all 10 on the first row and leave the sibling looking receivable but unable to receive. OPT.Qty is
    # deliberately NOT stated: a stated quantity is the operator's own and would hide the divergence.
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderSplitSolo_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9E      |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineSplitSolo_RL | orderSplitSolo_RL     | product_RL              | 10         | shipperPlanning_RL          |

    When the order identified by orderSplitSolo_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleSplitSolo_RL            | orderSplitSolo_RL     | orderLineSplitSolo_RL     | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID        |
      | planningSolo1_RL       | orderLineSplitSolo_RL |

    # The split: the order line's 10 is distributed over the two plannings, 5 each.
    When generate 1 additional M_Delivery_Planning records for: planningSolo1_RL
    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID            | C_OrderLine_ID        |
      | planningSolo1_RL,planningSolo2_RL | orderLineSplitSolo_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | TransportDirection | Processed |
      | planningSolo1_RL       | 10         | 10           | 5                        | Incoming           | false     |
      | planningSolo2_RL       | 10         | 10           | 5                        | Incoming           | false     |

    # Both rows carry the SAME receipt schedule - 10 outstanding on it, 5 planned per row.
    And after not more than 60s, the C_Order identified by orderSplitSolo_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowSolo1_RL            | planningSolo1_RL       | scheduleSplitSolo_RL | true          |
      | rowSolo2_RL            | planningSolo2_RL       | scheduleSplitSolo_RL | true          |

    # The FIRST row alone, quantity not stated: it must take its planning's 5, never the schedule's 10.
    When the receipt-disposition delivery-planning row identified by rowSolo1_RL is received:
      | OPT.M_InOut_ID  |
      | receiptSolo1_RL |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptSolo1_RL | CO        |
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID      | C_OrderLine_ID        | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptSolo1_RL | orderLineSplitSolo_RL | planningSolo1_RL       | 5               |
    And validate the created material receipt lines
      | M_InOut_ID      | C_OrderLine_ID        | MovementQty |
      | receiptSolo1_RL | orderLineSplitSolo_RL | 5           |

    # Half the line consumed, half still outstanding - and that remainder is what keeps the sibling receivable.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleSplitSolo_RL            | orderSplitSolo_RL     | orderLineSplitSolo_RL     | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              | 5            |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | M_InOut_ID      | IsClosed | Processed |
      | planningSolo1_RL       | 10         | 5            | 5                        | 5                       | Incoming           | receiptSolo1_RL | false    | true      |
      | planningSolo2_RL       | 10         | 5            | 5                        | 0                       | Incoming           | null            | false    | false     |

    # THE point of the scenario: the sibling still receives, and gets its own 5.
    When the receipt-disposition delivery-planning row identified by rowSolo2_RL is received:
      | OPT.M_InOut_ID  |
      | receiptSolo2_RL |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptSolo2_RL | CO        |
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID      | C_OrderLine_ID        | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptSolo2_RL | orderLineSplitSolo_RL | planningSolo2_RL       | 5               |
    And validate the created material receipt lines
      | M_InOut_ID      | C_OrderLine_ID        | MovementQty |
      | receiptSolo2_RL | orderLineSplitSolo_RL | 5           |

    # The order line is consumed exactly once across the two separate single-row receives.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleSplitSolo_RL            | orderSplitSolo_RL     | orderLineSplitSolo_RL     | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              | 10           |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | M_InOut_ID      | IsClosed | Processed |
      | planningSolo1_RL       | 10         | 0            | 5                        | 5                       | Incoming           | receiptSolo1_RL | false    | true      |
      | planningSolo2_RL       | 10         | 0            | 5                        | 5                       | Incoming           | receiptSolo2_RL | false    | true      |

  @Id:S31789_TC9g
  Scenario: A short receive leaves the PLAN standing, so the shortfall stays visible

    # A planning is exactly ONE receipt - getReceiveRejectionReason rejects a second one, and the receive marks
    # the planning Processed - so a shortfall does not stay open on it; the remainder becomes a NEW planning,
    # because it will be a new transport. That only works if the plan survives the receive: planned 4 against
    # actual 3 says "4 was expected, 3 arrived". Overwriting the plan with what arrived collapses it to 3/3 and
    # destroys the very fact the operator needs to see.
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderShort_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC9G      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineShort_RL | orderShort_RL         | product_RL              | 8          | shipperPlanning_RL          |

    When the order identified by orderShort_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleShort_RL                | orderShort_RL         | orderLineShort_RL         | vendor_RL                | vendorLocation_RL                 | product_RL              | 8          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID    |
      | planningShort1_RL      | orderLineShort_RL |

    # 8 over two plannings: 4 planned each. The second one is the untouched control.
    When generate 1 additional M_Delivery_Planning records for: planningShort1_RL
    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID              | C_OrderLine_ID    |
      | planningShort1_RL,planningShort2_RL | orderLineShort_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | Processed |
      | planningShort1_RL      | 8          | 8            | 4                        | 0                       | Incoming           | false     |
      | planningShort2_RL      | 8          | 8            | 4                        | 0                       | Incoming           | false     |

    # Nothing received yet, so each row still has its whole plan to move.
    And after not more than 60s, the C_Order identified by orderShort_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned | OPT.QtyToMove | OPT.PlannedDischargeQuantity | OPT.ActualDischargeQuantity |
      | rowShort1_RL           | planningShort1_RL      | scheduleShort_RL     | true          | 4             | 4                            | 0                           |
      | rowShort2_RL           | planningShort2_RL      | scheduleShort_RL     | true          | 4             | 4                            | 0                           |

    # 3 of the 4 planned arrive - a short delivery, the case the whole rule exists for.
    When the receipt-disposition delivery-planning row identified by rowShort1_RL is received:
      | OPT.Qty | OPT.M_InOut_ID  |
      | 3       | receiptShort_RL |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptShort_RL | CO        |

    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID      | C_OrderLine_ID    | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptShort_RL | orderLineShort_RL | planningShort1_RL      | 3               |

    # PLANNED STAYS 4. Actual is the 3 that arrived, and the planning is Processed - so the missing 1 is not
    # outstanding on this planning, it is a new transport somebody has to plan. The untouched sibling shows the
    # same plan with nothing against it yet.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | M_InOut_ID      | IsClosed | Processed |
      | planningShort1_RL      | 8          | 5            | 4                        | 3                       | Incoming           | receiptShort_RL | false    | true      |
      | planningShort2_RL      | 8          | 5            | 4                        | 0                       | Incoming           | null            | false    | false     |

    # And the grid says the same in its own three columns: the received row has NOTHING left to move even
    # though only 3 of the 4 planned arrived - a planning is one receipt - while the untouched sibling still
    # has its whole 4 to move.
    And after not more than 60s, the C_Order identified by orderShort_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned | OPT.QtyToMove | OPT.PlannedDischargeQuantity | OPT.ActualDischargeQuantity |
      | rowShort1_RL           | planningShort1_RL      | scheduleShort_RL     | true          | 0             | 4                            | 3                           |
      | rowShort2_RL           | planningShort2_RL      | scheduleShort_RL     | true          | 4             | 4                            | 0                           |

  @Id:S31789_TC16
  Scenario: Processed is read from the row's own source - the planning on a planned row, the schedule on an unplanned one

    # Processed is the shared blocker on both row types, but each row reads it from its OWN source: the
    # planning's own Processed on a planned row, the receipt schedule's own Processed on an unplanned row.
    # If the planned row read the schedule's flag instead, it would stay false here even after the planning
    # is fully received - the schedule is never touched by receiving, only by closing it.
    Given metasfresh contains C_Orders:
      | Identifier                 | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderProcPlanned_RL        | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC10A     |
      | orderProcUnplanned_RL      | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC10B     |
    And metasfresh contains C_OrderLines:
      | Identifier                 | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineProcPlanned_RL    | orderProcPlanned_RL    | product_RL              | 5          | shipperPlanning_RL          |
      | orderLineProcUnplanned_RL  | orderProcUnplanned_RL  | product_RL              | 5          | shipperPlain_RL              |

    When the order identified by orderProcPlanned_RL is completed
    And the order identified by orderProcUnplanned_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleProcPlanned_RL          | orderProcPlanned_RL    | orderLineProcPlanned_RL   | vendor_RL                | vendorLocation_RL                 | product_RL               | 5          | warehouse_RL               |
      | scheduleProcUnplanned_RL        | orderProcUnplanned_RL  | orderLineProcUnplanned_RL | vendor_RL                | vendorLocation_RL                 | product_RL               | 5          | warehouse_RL               |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID           |
      | planningProcPlanned_RL | orderLineProcPlanned_RL  |

    # Neither side is processed yet.
    And after not more than 60s, the C_Order identified by orderProcPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID   | OPT.IsPlanned | OPT.Processed |
      | rowProcPlanned_RL      | planningProcPlanned_RL | scheduleProcPlanned_RL | true          | false         |
    And after not more than 60s, the C_Order identified by orderProcUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID     | OPT.IsPlanned | OPT.Processed |
      | rowProcUnplanned_RL    | null                    | scheduleProcUnplanned_RL | false         | false         |

    # Receiving the planned row in full marks the PLANNING processed - the schedule underneath stays open.
    When the receipt-disposition delivery-planning row identified by rowProcPlanned_RL is received:
      | OPT.Qty | OPT.M_InOut_ID       |
      | 5       | receiptProcPlanned_RL |

    And validate M_In_Out status
      | M_InOut_ID             | DocStatus |
      | receiptProcPlanned_RL  | CO        |

    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | Processed |
      | planningProcPlanned_RL | 5          | 0            | Incoming            | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | scheduleProcPlanned_RL          | orderProcPlanned_RL    | orderLineProcPlanned_RL   | vendor_RL                | vendorLocation_RL                 | product_RL               | 5          | warehouse_RL               | 5            | false         |

    # The row's Processed follows the PLANNING, which is now true, even though its own schedule is still false.
    Then after not more than 60s, the C_Order identified by orderProcPlanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID   | OPT.Processed |
      | rowProcPlanned_RL      | planningProcPlanned_RL | scheduleProcPlanned_RL | true          |

    # Closing the unplanned row's schedule marks the SCHEDULE processed - there is no planning to read instead.
    And the M_ReceiptSchedule identified by scheduleProcUnplanned_RL is closed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier  | C_OrderLine_ID.Identifier  | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.Processed | IsClosed |
      | scheduleProcUnplanned_RL        | orderProcUnplanned_RL  | orderLineProcUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL               | 5          | warehouse_RL               | true          | true     |

    And after not more than 60s, the C_Order identified by orderProcUnplanned_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID     | OPT.Processed |
      | rowProcUnplanned_RL    | null                    | scheduleProcUnplanned_RL | true          |

  @Id:S31789_TC17
  Scenario: Each row's discharge quantities are its own planning's, and an unplanned row's are its schedule's

    # A split copies M_ReceiptSchedule_ID onto every sibling planning, so the schedule carries ONE figure for the
    # whole order line - a row reading it would show 10 on all three siblings.
    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderQtySplit_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC14A     |
      | orderQtyPlain_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC14B     |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtySplit_RL | orderQtySplit_RL      | product_RL              | 10         | shipperPlanning_RL          |
      | orderLineQtyPlain_RL | orderQtyPlain_RL      | product_RL              | 8          | shipperPlain_RL             |

    When the order identified by orderQtySplit_RL is completed
    And the order identified by orderQtyPlain_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyToMove |
      | scheduleQtySplit_RL             | orderQtySplit_RL      | orderLineQtySplit_RL      | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              | 10            |
      | scheduleQtyPlain_RL             | orderQtyPlain_RL      | orderLineQtyPlain_RL      | vendor_RL                | vendorLocation_RL                 | product_RL              | 8          | warehouse_RL              | 8             |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID       |
      | planningQty1_RL        | orderLineQtySplit_RL |

    # 10 over three plannings distributes 4/3/3 - the target absorbs the DOWN-rounding remainder.
    When generate 2 additional M_Delivery_Planning records for: planningQty1_RL
    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                          | C_OrderLine_ID       |
      | planningQty1_RL,planningQty2_RL,planningQty3_RL | orderLineQtySplit_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | TransportDirection |
      | planningQty1_RL        | 10         | 10           | 4                        | Incoming           |
      | planningQty2_RL        | 10         | 10           | 3                        | Incoming           |
      | planningQty3_RL        | 10         | 10           | 3                        | Incoming           |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | ActualDischargeQuantity |
      | planningQty1_RL        | 2                       |
      | planningQty2_RL        | 1                       |
      | planningQty3_RL        | 3                       |
    # QtyTotalOpen drops to 4 on ALL THREE: it is the ORDER LINE's remaining figure (10 less 2+1+3), not the
    # planning's.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity |
      | planningQty1_RL        | 10         | 4            | Incoming           | 4                        | 2                       |
      | planningQty2_RL        | 10         | 4            | Incoming           | 3                        | 1                       |
      | planningQty3_RL        | 10         | 4            | Incoming           | 3                        | 3                       |

    # The unplanned control gets a partial receipt: QtyToMove 3 against QtyMoved 5, two different non-zero
    # numbers from two different source columns, so neither view column can pass by reading the other.
    And update M_ReceiptSchedule:
      | M_ReceiptSchedule_ID | OPT.QtyMoved |
      | scheduleQtyPlain_RL  | 5            |
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyToMove | OPT.QtyMoved |
      | scheduleQtyPlain_RL             | orderQtyPlain_RL      | orderLineQtyPlain_RL      | vendor_RL                | vendorLocation_RL                 | product_RL              | 8          | warehouse_RL              | 3             | 5            |

    # None of the three is Processed - the actuals above were set directly - so each still has its whole
    # planned share to move.
    Then after not more than 60s, the C_Order identified by orderQtySplit_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned | OPT.QtyOrdered | OPT.QtyToMove | OPT.PlannedDischargeQuantity | OPT.ActualDischargeQuantity |
      | rowQty1_RL             | planningQty1_RL        | scheduleQtySplit_RL  | true          | 10             | 4             | 4                            | 2                           |
      | rowQty2_RL             | planningQty2_RL        | scheduleQtySplit_RL  | true          | 10             | 3             | 3                            | 1                           |
      | rowQty3_RL             | planningQty3_RL        | scheduleQtySplit_RL  | true          | 10             | 3             | 3                            | 3                           |

    # The unplanned control keeps THREE distinct non-zero numbers from three different schedule columns, so no
    # view column can pass by reading another: QtyToMove is the schedule's own stored remainder (3), the
    # planned figure is what was ordered (8), and the actual is what has moved (5).
    And after not more than 60s, the C_Order identified by orderQtyPlain_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned | OPT.QtyOrdered | OPT.QtyToMove | OPT.PlannedDischargeQuantity | OPT.ActualDischargeQuantity |
      | rowQtyPlain_RL         | null                   | scheduleQtyPlain_RL  | false         | 8              | 3             | 8                            | 5                           |

  @Id:S31789_TC15
  Scenario: Each row's ContainerNo and BL / booking / WE-notice flags are its own transport order's, not the order's

    Given metasfresh contains C_Orders:
      | Identifier              | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.POReference |
      | orderContainer_RL       | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC15A     |
      | orderContainerPlain_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | PO-RL-TC15B     |
    And metasfresh contains C_OrderLines:
      | Identifier                 | C_Order_ID.Identifier  | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineContainer_RL      | orderContainer_RL      | product_RL              | 10         | shipperPlanning_RL          |
      | orderLineContainerPlain_RL | orderContainerPlain_RL | product_RL              | 8          | shipperPlain_RL             |

    When the order identified by orderContainer_RL is completed
    And the order identified by orderContainerPlain_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier  | C_OrderLine_ID.Identifier  | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleContainer_RL            | orderContainer_RL      | orderLineContainer_RL      | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              |
      | scheduleContainerPlain_RL       | orderContainerPlain_RL | orderLineContainerPlain_RL | vendor_RL                | vendorLocation_RL                 | product_RL              | 8          | warehouse_RL              |
    And after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID        |
      | planningContainer1_RL  | orderLineContainer_RL |

    When generate 1 additional M_Delivery_Planning records for: planningContainer1_RL
    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                      | C_OrderLine_ID        |
      | planningContainer1_RL,planningContainer2_RL | orderLineContainer_RL |

    # generateDeliveryInstructions creates ONE instruction PER planning, so the two siblings of the split end up
    # on two DIFFERENT transport orders while sharing one receipt schedule - the case that tells an own-row read
    # apart from an order-wide one.
    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
      | transportContainer1_RL     | planningContainer1_RL  |
      | transportContainer2_RL     | planningContainer2_RL  |
    # The three flags are OPPOSED between the two transport orders on purpose: an OR over the order reads Y for
    # all three on both rows, so every column below disagrees with the aggregate on at least one row.
    And update transport order
      | M_ShipperTransportation_ID | ContainerNo | IsBLReceived | IsBookingConfirmed | IsWENotice |
      | transportContainer1_RL     | CONT-RL-001 | true         | false              | true       |
      | transportContainer2_RL     | CONT-RL-002 | false        | true               | false      |

    # The unplanned control reaches a transport order through the order-line AddTo process - the only route a row
    # with no planning has, and the one the schedule's own ContainerNo column reads.
    And metasfresh contains Transport Order
      | Identifier                 | M_Shipper_ID    | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | transportContainerPlain_RL | shipperPlain_RL | vendor_RL           | vendorLocation_RL   | Incoming           |
    And C_Order_AddTo_M_ShipperTransportation is invoked for order orderContainerPlain_RL and transportation order: transportContainerPlain_RL
    And update transport order
      | M_ShipperTransportation_ID | ContainerNo | IsBLReceived | IsBookingConfirmed | IsWENotice |
      | transportContainerPlain_RL | CONT-RL-003 | true         | false              | true       |

    Then after not more than 60s, the C_Order identified by orderContainer_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned | OPT.ContainerNo | OPT.IsBLReceived | OPT.IsBookingConfirmed | OPT.IsWENotice |
      | rowContainer1_RL                          | planningContainer1_RL  | scheduleContainer_RL | true          | CONT-RL-001     | true             | false                  | true           |
      | rowContainer2_RL                          | planningContainer2_RL  | scheduleContainer_RL | true          | CONT-RL-002     | false            | true                   | false          |

    And after not more than 60s, the C_Order identified by orderContainerPlain_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID      | OPT.IsPlanned | OPT.ContainerNo | OPT.IsBLReceived | OPT.IsBookingConfirmed | OPT.IsWENotice |
      | rowContainerPlain_RL                      | null                   | scheduleContainerPlain_RL | false         | CONT-RL-003     | true             | false                  | true           |

  @Id:S31789_TC9g
  Scenario: The batch receive packs into the row's OWN configuration, and a split row is packed to ITS share

    # The gap this pins. The batch receive builds a packing-free planning VHU, so a product WITH a packing
    # instruction is received loose - the configuration the operator maintains is ignored, which the per-row
    # HU actions on this same window honour. Owner 2026-09-11: "align new window process with existing, so
    # including packing and that is it", plus "our window process needs to respect planning qty if split".
    #
    # Those two pull against each other, which is the whole reason this scenario exists: ReceiptScheduleHUGenerator
    # sizes from the SCHEDULE, and a split leaves N plannings sharing ONE schedule - so packing naively would let
    # this row pack the whole order line. capToPlannedShare is what keeps the two compatible, exactly as the
    # per-row actions already use it.
    #
    # A DEDICATED product: product_RL and product2_RL are Background fixtures shared with every other scenario
    # here, and an M_HU_PI_Item_Product is a global row keyed on the product - attaching one to either of those
    # would silently hand packing to scenarios that assert loose receipts.

    Given metasfresh contains M_Products:
      | Identifier      |
      | productPacked_RL |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_RL_PO            | productPacked_RL        | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_RL                | productPacked_RL        |

    # 10 CUs per TU, and the TU sits on an LU - so a correctly-packed receive of a 5-CU share is ONE TU on ONE LU,
    # not a loose CU heap and not the two TUs the full order line would need.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   | Name           |
      | tuPiPk_RL    | RL_TU_PI_Pk    |
      | luPiPk_RL    | RL_LU_PI_Pk    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | tuPiVerPk_RL       | tuPiPk_RL  | TU          | Y         |
      | luPiVerPk_RL       | luPiPk_RL  | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | tuMiItemPk_RL   | tuPiVerPk_RL       | 0   | MI       |                   |
      | luHuItemPk_RL   | luPiVerPk_RL       | 100 | HU       | tuPiPk_RL         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID     | Qty | ValidFrom  |
      | pipTUPk_RL              | tuMiItemPk_RL   | productPacked_RL | 10  | 2000-01-01 |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderPacked_RL | false  | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLinePacked_RL | orderPacked_RL        | productPacked_RL        | 10         | shipperPlanning_RL          |

    When the order identified by orderPacked_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | schedulePacked_RL               | orderPacked_RL        | orderLinePacked_RL        | vendor_RL                | vendorLocation_RL                 | productPacked_RL        | 10         | warehouse_RL              |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID     |
      | planningPacked1_RL     | orderLinePacked_RL |

    # Split 10 into two plannings of 5, so both share schedulePacked_RL.
    When generate 1 additional M_Delivery_Planning records for: planningPacked1_RL

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                    | C_OrderLine_ID     |
      | planningPacked1_RL,planningPacked2_RL     | orderLinePacked_RL |
    And after not more than 60s, the C_Order identified by orderPacked_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowPacked1_RL                             | planningPacked1_RL     | schedulePacked_RL    | true          |
      | rowPacked2_RL                             | planningPacked2_RL     | schedulePacked_RL    | true          |

    # Receive ONE of the two, so the share and the packing are exercised together.
    When the receipt-disposition delivery-planning rows identified by rowPacked1_RL are received together:
      | M_InOut_ID.Identifier |
      | receiptPacked_RL      |

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | receiptPacked_RL      | CO        |

    # BOTH halves of the owner's instruction, on one row.
    #
    # QtyTU_Calculated is THE PACKING: the producer writes it from the HUs actually received, so a receive that
    # built a real TU from the row's configuration reports 1, while one that made a bare virtual HU has no TU to
    # count and reports 0. That is what fails today.
    #
    # MovementQty is THE SHARE: 5, not the line's 10. Packing sized from the SCHEDULE rather than the planning
    # would receive the whole line here and starve planningPacked2_RL.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID       | C_OrderLine_ID     | M_Delivery_Planning_ID | OPT.MovementQty | OPT.QtyTU_Calculated |
      | receiptPacked_RL | orderLinePacked_RL | planningPacked1_RL     | 5               | 1                    |

    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | IsClosed | Processed |
      | planningPacked1_RL     | 10         | 5            | 5                        | 5                       | Incoming           | false    | true      |
      | planningPacked2_RL     | 10         | 5            | 5                        | 0                       | Incoming           | false    | false     |

  @Id:S31789_TC9h
  Scenario: A share spanning MORE than one TU is received at the share, not at the packing's capacity

    # The regression guard for the allocation clamp in ReceiptFromReceiptScheduleService#createPackedHUs.
    #
    # capToPlannedShare rounds the TU COUNT up - a partial TU still has to be received - and leaves QtyCUsPerTU
    # alone. So a share of 15 against a ten-per-TU packing gives TWO TUs, i.e. a configuration that can hold 20.
    # Allocating that capacity would draw five units belonging to the sibling planning on the same receipt
    # schedule. The clamp is what keeps the receipt at 15.
    #
    # TC9g cannot catch this: its share of 5 is SMALLER than one TU, so capToPlannedShare shrinks that TU's
    # contents to 5 and capacity equals the share exactly - min(5,5) is a no-op there, and the clamp could be
    # deleted without the scenario noticing.
    #
    # Only ONE of the two plannings is received, deliberately: ReceiptScheduleHUGenerator is scoped to the
    # receipt SCHEDULE, so receiving both siblings in one gesture is the separate, already-recorded defect. One
    # row means one generation and keeps this scenario about the clamp alone.

    Given metasfresh contains M_Products:
      | Identifier        |
      | productSpan_RL    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_RL_PO            | productSpan_RL          | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_RL                | productSpan_RL          |

    # Ten CUs per TU, the TU on an LU - so a share of 15 needs two TUs and the configuration can hold 20.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   | Name          |
      | tuPiSpan_RL  | RL_TU_PI_Span |
      | luPiSpan_RL  | RL_LU_PI_Span |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID  | HU_UnitType | IsCurrent |
      | tuPiVerSpan_RL     | tuPiSpan_RL | TU          | Y         |
      | luPiVerSpan_RL     | luPiSpan_RL | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | tuMiItemSpan_RL | tuPiVerSpan_RL     | 0   | MI       |                   |
      | luHuItemSpan_RL | luPiVerSpan_RL     | 100 | HU       | tuPiSpan_RL       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID   | Qty | ValidFrom  |
      | pipTUSpan_RL            | tuMiItemSpan_RL | productSpan_RL | 10  | 2000-01-01 |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderSpan_RL | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineSpan_RL | orderSpan_RL          | productSpan_RL          | 20         | shipperPlanning_RL          |

    When the order identified by orderSpan_RL is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | scheduleSpan_RL                 | orderSpan_RL          | orderLineSpan_RL          | vendor_RL                | vendorLocation_RL                 | productSpan_RL          | 20         | warehouse_RL              |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID   |
      | planningSpan1_RL       | orderLineSpan_RL |

    When generate 1 additional M_Delivery_Planning records for: planningSpan1_RL

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID             | C_OrderLine_ID   |
      | planningSpan1_RL,planningSpan2_RL  | orderLineSpan_RL |

    # The split halves 20 into 10/10, which is an exact multiple of the ten-per-TU packing and would leave
    # capacity equal to the share again. Moving the first planning to 15 is what makes the share span two TUs
    # UNEVENLY - the only shape in which the clamp does any work.
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | PlannedDischargeQuantity |
      | planningSpan1_RL       | 15                       |

    And after not more than 60s, the C_Order identified by orderSpan_RL has exactly the following rows in RV_ReceiptDisposition_DeliveryPlanning:
      | RV_ReceiptDisposition_DeliveryPlanning_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.IsPlanned |
      | rowSpan1_RL                               | planningSpan1_RL       | scheduleSpan_RL      | true          |
      | rowSpan2_RL                               | planningSpan2_RL       | scheduleSpan_RL      | true          |

    When the receipt-disposition delivery-planning rows identified by rowSpan1_RL are received together:
      | M_InOut_ID.Identifier |
      | receiptSpan_RL        |

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | receiptSpan_RL        | CO        |

    # 15, NOT the configuration's capacity of 20 - that is the clamp, and it is the whole point of this scenario.
    #
    # QtyTU_Calculated is deliberately NOT asserted here. It comes back as 1, where a share of 15 against a
    # ten-per-TU packing should need two TUs (one full, one holding the remaining five). Either the count means
    # something other than "TUs received" on this path, or the allocation overfills a single TU - and asserting
    # either number before knowing which would be inventing agreement. The open question is recorded in
    # ai-work/31789/pending-questions.md; MovementQty is what guards the clamp and it is exact.
    And validate the delivery planning link of the material receipt lines:
      | M_InOut_ID     | C_OrderLine_ID   | M_Delivery_Planning_ID | OPT.MovementQty |
      | receiptSpan_RL | orderLineSpan_RL | planningSpan1_RL       | 15              |
