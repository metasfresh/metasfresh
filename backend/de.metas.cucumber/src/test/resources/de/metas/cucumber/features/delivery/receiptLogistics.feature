@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29050_Delivery_Planning
@ghActions:run_on_executor5
Feature: The receipt-logistics window lists what is arriving, planned or not

  A procurement dispatcher planning inbound receipts has to read two lists today: the delivery plannings
  somebody already made, and the receipt schedules nobody has planned yet. RV_ReceiptLogistics is those two
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
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_RL_PO | product_RL   | 5.0      | PCE               | Normal                        |
      | priceListVersion_RL_SO | product_RL   | 10.0     | PCE               | Normal                        |
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
    Then after not more than 60s, the C_Order identified by orderPlanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.ETA    | OPT.QtyOrdered | OPT.C_BPartner_ID | OPT.M_Product_ID | OPT.M_Warehouse_ID | OPT.POReference |
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
    Then after not more than 60s, the C_Order identified by orderUnplanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.ETA    | OPT.QtyOrdered | OPT.C_BPartner_ID | OPT.M_Product_ID | OPT.M_Warehouse_ID | OPT.POReference |
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
    Then RV_ReceiptLogistics has no row for the C_Order identified by orderOutgoing_RL

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

    Then RV_ReceiptLogistics has no row for the C_Order identified by orderDropship_RL

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
    Then after not more than 60s, the C_Order identified by orderEtaPlanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaPlanned_RL       | planningEtaPlanned_RL  | scheduleEtaPlanned_RL | 2023-02-20 | 2023-02-20                 |
    And after not more than 60s, the C_Order identified by orderEtaUnplanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaUnplanned_RL     | null                   | scheduleEtaUnplanned_RL | 2023-02-20 | 2023-02-20                 |

    # The operator moves the promise on BOTH schedules.
    When update M_ReceiptSchedule:
      | M_ReceiptSchedule_ID    | OPT.DatePromised_Override |
      | scheduleEtaPlanned_RL   | 2023-03-15                |
      | scheduleEtaUnplanned_RL | 2023-03-15                |

    # The planned row keeps the PLANNING's arrival date and only its order promise moves; the unplanned row
    # has no plan of its own, so its arrival date follows the promise.
    Then after not more than 60s, the C_Order identified by orderEtaPlanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID  | OPT.ETA    | OPT.DatePromised_Effective |
      | rowEtaPlanned_RL       | planningEtaPlanned_RL  | scheduleEtaPlanned_RL | 2023-02-20 | 2023-03-15                 |
    And after not more than 60s, the C_Order identified by orderEtaUnplanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID    | OPT.ETA    | OPT.DatePromised_Effective |
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
    Then after not more than 60s, the C_Order identified by orderWeekPlanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID   | OPT.ETA    | OPT.CalendarWeek |
      | rowWeekPlanned_RL      | planningWeekPlanned_RL | scheduleWeekPlanned_RL | 2023-01-01 | 52               |
    And after not more than 60s, the C_Order identified by orderWeekUnplanned_RL has exactly the following rows in RV_ReceiptLogistics:
      | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID     | OPT.ETA    | OPT.CalendarWeek |
      | rowWeekUnplanned_RL    | null                   | scheduleWeekUnplanned_RL | 2023-01-01 | 52               |
