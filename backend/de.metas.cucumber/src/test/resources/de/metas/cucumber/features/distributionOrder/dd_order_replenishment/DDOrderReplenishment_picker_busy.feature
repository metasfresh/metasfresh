@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — picker-busy guard (sync rollback + async consumer error)
  As a warehouse operator, when a picker is already working on the distribution order of a shipment
  schedule, I want any attempt to change that schedule to be refused immediately (with a clear error),
  and — should the picker grab the job in the race window after the change was accepted — the async
  reconcile event to end in Error so an operator can repost it once picking is undone.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | pricingSystem   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID |
      | priceListVersion | priceList     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN          | C_BPartner_ID |
      | customerLocation | bPLocation_1 | customer      |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customer      | customerLocation       | true        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID |
      | packingWH      | customer      | customerLocation       | Y           | Y                  | network                   |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |

    # TC1 starting state: one sales order on the packing warehouse → exactly one Completed DD_Order.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |
    And after not more than 120s, the DD_Order linked to shipment schedule is found:
      | Identifier | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | ddOrder    | shipmentSchedule      | CO | stockWH             | packingWH         | 5          |

  @from:cucumber
  Scenario: A busy picker makes the beforeSave interceptor reject the schedule change
    # The picker is busy: a M_Picking_Job_Line references the same M_ShipmentSchedule_ID as the DD_Order.
    Given metasfresh contains M_Picking_Job_Line:
      | M_ShipmentSchedule_ID | C_OrderLine_ID | M_Product_ID | QtyToPick | C_UOM_ID |
      | shipmentSchedule      | orderLine      | product      | 5         | 100      |

    # Attempting to change the schedule qty is rejected by the M_ShipmentSchedule.beforeSave guard
    # (the tx rolls back; the schedule's persisted value stays unchanged and no event is published).
    Then changing the M_ShipmentSchedule quantity is rejected:
      | M_ShipmentSchedule_ID | QtyOrdered_Override |
      | shipmentSchedule      | 8                   |

    # Cleanup first so it always runs regardless of subsequent assertion results.
    # Deactivates the picking job line (and its parent job) so it does not
    # pollute subsequent test scenarios that query for active picking records.
    When the M_Picking_Job_Line for M_ShipmentSchedule shipmentSchedule is removed

    # The DD_Order is untouched (still the original Completed one, qty 5).
    Then after not more than 10s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO | stockWH             | packingWH         | 5          |
    # The original DD_Order identifier (captured in Background) must be unchanged (same ID, still Completed).
    # Because the tx rolled back, afterSave never published a reconcile event — no void/recreate happened.
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder    | CO |

  @from:cucumber
  Scenario: A picker who grabs the job in the race window makes the async reconcile event fail
    # Simulate the race deterministically: the picker becomes busy AFTER the schedule change was accepted
    # but BEFORE the reconcile event is processed by the consumer.
    Given metasfresh contains M_Picking_Job_Line:
      | M_ShipmentSchedule_ID | C_OrderLine_ID | M_Product_ID | QtyToPick | C_UOM_ID |
      | shipmentSchedule      | orderLine      | product      | 5         | 100      |

    # Processing the reconcile event (the consumer-side definitive guard) is rejected while the picker is busy.
    # NOTE: this step drives the BL directly (not via the async DDOrderReconciliationEventHandler) so
    # no AD_EventLog_Entry is produced here. The handler's error-recording path (IsError=true) is covered
    # by TC6, which goes through the real async event flow. TC5 is about the BL-level picker-busy guard.
    Then processing the reconcile event for M_ShipmentSchedule shipmentSchedule is rejected

    # The DD_Order is left unchanged by the failed reconcile.
    And after not more than 5s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO | stockWH             | packingWH         | 5          |

    # Once the picker releases the job (picking line inactivated), reprocessing the event succeeds.
    # The reconcile classifies as RECREATE (active schedule + existing live DD_Order): the old DD_Order
    # is voided and a fresh one is created. Capturing a new Identifier pins that recreate actually happened.
    When the M_Picking_Job_Line for M_ShipmentSchedule shipmentSchedule is removed
    And the reconcile event for M_ShipmentSchedule shipmentSchedule is processed
    Then after not more than 10s, the DD_Order linked to shipment schedule is found:
      | Identifier  | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | ddOrder_v2  | shipmentSchedule      | CO | stockWH             | packingWH         | 5          |
    # The original DD_Order (captured in Background as ddOrder) must now be Voided — RECREATE voided it.
    And after not more than 5s, following DD_Orders are found
      | Identifier | DocStatus |
      | ddOrder    | VO     |
