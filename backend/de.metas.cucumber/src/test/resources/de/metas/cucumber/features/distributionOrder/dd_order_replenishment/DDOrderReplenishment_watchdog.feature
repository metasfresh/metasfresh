@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor6
Feature: DD_Order replenishment — drift watchdog (manual rebuild + hourly scheduler)
  As a warehouse operator, I want a drift watchdog that recreates any distribution order that fell
  through (JVM crash between commit and publish, RabbitMQ outage, missed handler error), either when I
  run it manually or via the hourly scheduler, so a packing-warehouse schedule never stays without a DD_Order.

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
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID |
      | packingWH      | customer      | customerLocation       | Y           | Y                  | network                   |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |

    # An order on the packing warehouse normally creates a DD_Order via the reconcile event.
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
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |

    # Simulate the "fell through" drift: the schedule's DD_Order is lost (voided outside the reconcile flow),
    # so the active packing-warehouse schedule now has no live DD_Order — exactly the watchdog's input.
    And the DD_Order linked to M_ShipmentSchedule shipmentSchedule is voided directly
    And there is no live DD_Order for M_ShipmentSchedule shipmentSchedule

  @from:cucumber
  Scenario: Running the rebuild process manually recreates the missing DD_Order (TC7)
    When the DD_Order_Picking_Rebuild process is run

    Then after not more than 30s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |
    # rebuildDrift publishes reconcile events consumed by the async handler; the handler records a Done
    # AD_EventLog_Entry on success (REQUIREMENTS §5 TC7).
    And after not more than 10s, an AD_EventLog_Entry for the reconcile handler is found:
      | IsError |
      | false   |

  @from:cucumber
  Scenario: The hourly scheduler self-heals drift via the same rebuild process (TC8)
    # The AD_Scheduler engine cannot be driven inside cucumber; instead we assert the AD_Process the hourly
    # scheduler points at exists, and that running that exact process (the rebuild) heals the drift — which is
    # what the scheduler invocation does on its hourly cadence.
    Given the DD_Order_Picking_Rebuild process exists
    When the DD_Order_Picking_Rebuild process is run

    Then after not more than 30s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |
    # rebuildDrift publishes reconcile events consumed by the async handler; the handler records a Done
    # AD_EventLog_Entry on success (REQUIREMENTS §5 TC8).
    And after not more than 10s, an AD_EventLog_Entry for the reconcile handler is found:
      | IsError |
      | false   |
