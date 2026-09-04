@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29050_Delivery_Planning
@ghActions:run_on_executor5
Feature: The receipt-logistics window lists what is arriving, planned or not

  A procurement dispatcher planning inbound receipts has to read two lists today: the delivery plannings
  somebody already made, and the receipt schedules nobody has planned yet. RV_ReceiptDisposition_DeliveryPlanning is those two
  lists unioned, and every scenario here drives a REAL purchase order to the point where the row exists -
  a seeded row would prove the SQL and nothing about what a completing order actually produces.

  What decides which list an order lands on is the shipper on its order line: a shipper flagged
  IsCreateDeliveryPlanning gets a delivery planning generated on completion (a PLANNED row), an unflagged one
  does not (an UNPLANNED row, keyed 1000000000 + the receipt schedule id so the two branches stay disjoint).
  Outgoing and dropship transports are not receipt logistics at all and must not appear: a dropship sends the
  goods from the vendor straight to the customer, so they never reach the warehouse.

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
      | M_Warehouse_ID.Identifier | Value                  | Name                   | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse_RL              | warehouseValue_RL31789 | warehouseName_RL31789  | warehouseBP_RL               | warehouseLocation_RL                  |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_RL              | locatorValue_RL31789| warehouse_RL              |
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
  Scenario: An outgoing planning and a dropship planning appear not at all

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

    # A dropship: the vendor delivers straight to the customer, so the goods never reach the warehouse.
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.IsDropShip | OPT.DropShip_Location_ID.Identifier |
      | orderDropship_RL  | false   | vendor_RL                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation_RL                     | warehouse_RL                  | POO             | true           | customerLocation_RL                 |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineDropship_RL | orderDropship_RL      | product_RL              | 5          | shipperPlanning_RL          |

    When the order identified by orderDropship_RL is completed

    Then after not more than 60s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID       |
      | planningDropship_RL    | orderLineDropship_RL |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | C_Order_ID       | C_OrderLine_ID       |
      | planningDropship_RL    | 5          | 5            | Dropship           | orderDropship_RL | orderLineDropship_RL |

    Then RV_ReceiptDisposition_DeliveryPlanning has no row for the C_Order identified by orderDropship_RL

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

    When the receipt-logistics row identified by rowRcvUnplanned_RL is received:
      | OPT.Qty | OPT.M_InOut_ID       |
      | 7       | receiptUnplanned_RL  |

    And validate M_In_Out status
      | M_InOut_ID          | DocStatus |
      | receiptUnplanned_RL | CO        |

    # The row carried no planning id, so the receipt carries none either - the null branch of the shared request.
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID          | M_Delivery_Planning_ID |
      | receiptUnplanned_RL | null                   |

    # ... and the goods are booked on the schedule, which is what "the same receipt as window 541954" means.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleRcvUnplanned_RL         | orderRcvUnplanned_RL  | orderLineRcvUnplanned_RL  | vendor_RL                | vendorLocation_RL                 | product_RL              | 7          | warehouse_RL              | 7            |

  @Id:S31789_TC8
  Scenario: Receiving a planned row produces the receipt the delivery-planning window would, planning and all

    # THE point of the shared request. A planned row hands the receive its M_Delivery_Planning_ID, the id
    # travels INSIDE the request onto the draft receipt header, and the completion that happens in the same
    # call fires the delivery-planning interceptor. Everything asserted below - the back-link on the planning,
    # the actual discharge quantity, Processed, the open quantity going to zero - is derived from that one id
    # being present BEFORE completion. Set afterwards, or dropped (which is what the HU-editor receive path
    # does), every one of these assertions fails.
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

    When the receipt-logistics row identified by rowRcvPlanned_RL is received:
      | OPT.Qty | OPT.M_InOut_ID     |
      | 5       | receiptPlanned_RL  |

    And validate M_In_Out status
      | M_InOut_ID        | DocStatus |
      | receiptPlanned_RL | CO        |

    # The link the HU-editor path silently omits.
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID        | M_Delivery_Planning_ID |
      | receiptPlanned_RL | planningRcvPlanned_RL  |

    # ... and everything the interceptor derives from it, plus the planned-discharge write-back that
    # M_Delivery_Planning_GenerateReceipt performs - i.e. exactly the state the delivery-planning window leaves.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity | M_InOut_ID        | IsClosed | Processed |
      | planningRcvPlanned_RL  | 5          | 0            | Incoming           | 5                        | 5                       | receiptPlanned_RL | false    | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleRcvPlanned_RL           | orderRcvPlanned_RL    | orderLineRcvPlanned_RL    | vendor_RL                | vendorLocation_RL                 | product_RL              | 5          | warehouse_RL              | 5            |

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

    # Three rows in, TWO receipts out: the two rows of order A share one, order B's cannot join them.
    When the receipt-logistics rows identified by rowMultiA1_RL, rowMultiA2_RL, rowMultiB1_RL are received together:
      | M_InOut_ID       |
      | receiptMultiA_RL |
      | receiptMultiB_RL |

    Then validate M_In_Out status
      | M_InOut_ID       | DocStatus |
      | receiptMultiA_RL | CO        |
      | receiptMultiB_RL | CO        |

    # No row carried a planning, so no receipt carries one - the null branch of the shared request, N times.
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID       | M_Delivery_Planning_ID |
      | receiptMultiA_RL | null                   |
      | receiptMultiB_RL | null                   |

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
  Scenario: Several planned rows received together produce one receipt per planning, each carrying its own

    # Two planned rows of the SAME order - so the aggregation key and the C_Order_ID agree, and an
    # aggregation-only implementation would put them on one receipt. They must NOT share one: a receipt header
    # holds a single M_Delivery_Planning_ID, so grouping is per planning FIRST and by aggregation key second.
    # Merging them would leave one of the two plannings with no receipt and no delivered state at all.
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

    When the receipt-logistics rows identified by rowPlanTwo1_RL, rowPlanTwo2_RL are received together:
      | M_InOut_ID          |
      | receiptPlanTwo1_RL  |
      | receiptPlanTwo2_RL  |

    Then validate M_In_Out status
      | M_InOut_ID         | DocStatus |
      | receiptPlanTwo1_RL | CO        |
      | receiptPlanTwo2_RL | CO        |

    # Each receipt carries ITS OWN planning - the link the HU-editor path silently omits, here N times over.
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID         | M_Delivery_Planning_ID |
      | receiptPlanTwo1_RL | planningPlanTwo1_RL    |
      | receiptPlanTwo2_RL | planningPlanTwo2_RL    |

    And validate the created material receipt lines
      | M_InOut_ID         | C_OrderLine_ID       | MovementQty |
      | receiptPlanTwo1_RL | orderLinePlanTwo1_RL | 4           |
      | receiptPlanTwo2_RL | orderLinePlanTwo2_RL | 6           |

    # ... and everything the completion interceptor derives from that id, for BOTH plannings - i.e. exactly what
    # receiving each of them from the delivery-planning window one at a time would have left.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity | M_InOut_ID         | IsClosed | Processed |
      | planningPlanTwo1_RL    | 4          | 0            | Incoming           | 4                        | 4                       | receiptPlanTwo1_RL | false    | true      |
      | planningPlanTwo2_RL    | 6          | 0            | Incoming           | 6                        | 6                       | receiptPlanTwo2_RL | false    | true      |

  @Id:S31789_TC9c
  Scenario: A mixed selection routes per row, so one gesture yields a linked receipt and a plain one

    # Not an expected operator use case - but routing is PER ROW, so it works anyway, and behaviour that ships
    # untested is a liability. Nothing extra is built for it: this asserts what per-row routing already produces.
    # The two rows sit on ONE order, so they agree on the aggregation key; they still cannot share a receipt,
    # because one of them carries a planning and a receipt header holds only one.
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

    When the receipt-logistics rows identified by rowMixedPlan_RL, rowMixedPlain_RL are received together:
      | M_InOut_ID           |
      | receiptMixedPlan_RL  |
      | receiptMixedPlain_RL |

    Then validate M_In_Out status
      | M_InOut_ID           | DocStatus |
      | receiptMixedPlan_RL  | CO        |
      | receiptMixedPlain_RL | CO        |

    # The planned row's receipt is linked, the unplanned row's is not - one gesture, two paths, decided per row.
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID           | M_Delivery_Planning_ID |
      | receiptMixedPlan_RL  | planningMixed_RL       |
      | receiptMixedPlain_RL | null                   |

    And validate the created material receipt lines
      | M_InOut_ID           | C_OrderLine_ID         | MovementQty |
      | receiptMixedPlan_RL  | orderLineMixedPlan_RL  | 5           |
      | receiptMixedPlain_RL | orderLineMixedPlain_RL | 7           |

    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity | M_InOut_ID          | IsClosed | Processed |
      | planningMixed_RL       | 5          | 0            | Incoming           | 5                       | receiptMixedPlan_RL | false    | true      |

  @Id:S31789_TC9d
  Scenario: Two plannings of ONE order line are received together without receiving that line twice

    # The sharpest case, and the one a naive "one row -> the schedule's whole remaining quantity" implementation
    # gets wrong. A split copies M_ReceiptSchedule_ID onto every new planning, so these two rows point at the
    # SAME receipt schedule and the SAME order line. Received together they must consume that line exactly once:
    # each planning receives ITS OWN planned share, and the schedule's QtyMoved ends at QtyOrdered, not above it.
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

    When the receipt-logistics rows identified by rowSplit1_RL, rowSplit2_RL are received together:
      | M_InOut_ID        |
      | receiptSplit1_RL  |
      | receiptSplit2_RL  |

    Then validate M_In_Out status
      | M_InOut_ID       | DocStatus |
      | receiptSplit1_RL | CO        |
      | receiptSplit2_RL | CO        |

    And validate the delivery planning link of M_InOut:
      | M_InOut_ID       | M_Delivery_Planning_ID |
      | receiptSplit1_RL | planningSplit1_RL      |
      | receiptSplit2_RL | planningSplit2_RL      |

    # 5 and 5 against the ONE order line, never 10 and 10.
    And validate the created material receipt lines
      | M_InOut_ID       | C_OrderLine_ID    | MovementQty |
      | receiptSplit1_RL | orderLineSplit_RL | 5           |
      | receiptSplit2_RL | orderLineSplit_RL | 5           |

    # The line is consumed exactly once: QtyMoved equals QtyOrdered, and each planning carries its own half.
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved |
      | scheduleSplit_RL                | orderSplit_RL         | orderLineSplit_RL         | vendor_RL                | vendorLocation_RL                 | product_RL              | 10         | warehouse_RL              | 10           |
    # QtyTotalOpen is the ORDER LINE's remaining figure - QtyOrdered less what has ACTUALLY arrived on it - so
    # both plannings read 0 once the two halves together have consumed the line exactly once. It reading
    # anything else is the over-receive this scenario exists to catch.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | PlannedDischargeQuantity | ActualDischargeQuantity | TransportDirection | M_InOut_ID       | IsClosed | Processed |
      | planningSplit1_RL      | 10         | 0            | 5                        | 5                       | Incoming           | receiptSplit1_RL | false    | true      |
      | planningSplit2_RL      | 10         | 0            | 5                        | 5                       | Incoming           | receiptSplit2_RL | false    | true      |

  @Id:S31789_TC9e
  Scenario: One row of a SPLIT planning received ALONE takes only its own share, so its sibling can still receive

    # H1's shape, and the one every other scenario misses. TC9d receives both split rows in ONE gesture; this
    # one receives them ONE AT A TIME, which is what the window's single-row "CUs annehmen" button does. A split
    # copies M_ReceiptSchedule_ID onto both plannings, so the SCHEDULE's remaining quantity is the whole ORDER
    # LINE's - 10 - while each PLANNING's own share is 5. A single-row receive that read the schedule would
    # consume all 10 on the first row and leave the sibling with a row that looks receivable and is not: no
    # receipt, no delivered state, and no way to get one from this window. The quantity is deliberately NOT
    # stated in the step (no OPT.Qty), because a stated quantity is the operator's own and would hide the
    # divergence - what is under test is what the receive DERIVES for a planned row. Every other single-row
    # scenario uses an UNSPLIT planning, where the planning's share and the schedule's remainder coincide, so
    # none of them can tell the two rules apart.
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
    When the receipt-logistics row identified by rowSolo1_RL is received:
      | OPT.M_InOut_ID  |
      | receiptSolo1_RL |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptSolo1_RL | CO        |
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID      | M_Delivery_Planning_ID |
      | receiptSolo1_RL | planningSolo1_RL       |
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
    When the receipt-logistics row identified by rowSolo2_RL is received:
      | OPT.M_InOut_ID  |
      | receiptSolo2_RL |

    Then validate M_In_Out status
      | M_InOut_ID      | DocStatus |
      | receiptSolo2_RL | CO        |
    And validate the delivery planning link of M_InOut:
      | M_InOut_ID      | M_Delivery_Planning_ID |
      | receiptSolo2_RL | planningSolo2_RL       |
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

  @Id:S31789_TC10
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
    When the receipt-logistics row identified by rowProcPlanned_RL is received:
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
