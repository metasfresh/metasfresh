@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor6
Feature: DD_Order replenishment — update (void + recreate) and reverse (void only)
  As a warehouse operator running a packing workplace ("Packtisch"),
  I want a changed sales-order / shipment-schedule quantity to void the old distribution order and
  recreate a fresh one with the new quantity, and a cancelled schedule to void the distribution order
  without recreating it, so the picker always works from a single up-to-date DD_Order.

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
      | Identifier  | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | ddOrder_v1  | shipmentSchedule      | CO | stockWH             | packingWH         | 5          |

  @from:cucumber
  Scenario: Changing the schedule quantity voids the old DD_Order and recreates a fresh one (picker not busy)
    # Raise the effective qty to 8 via QtyOrdered_Override; the M_ShipmentSchedule afterSave reconcile fires.
    When the M_ShipmentSchedule quantity is changed:
      | M_ShipmentSchedule_ID | QtyOrdered_Override |
      | shipmentSchedule      | 8                   |

    # The new live DD_Order carries qty 8 and the schedule linkage; the old one is voided.
    Then after not more than 120s, the DD_Order linked to shipment schedule is found:
      | Identifier | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | ddOrder_v2 | shipmentSchedule      | CO | stockWH             | packingWH         | 8          |
    # The original DD_Order (captured in Background as ddOrder_v1) must now be Voided.
    And after not more than 5s, following DD_Orders are found
      | Identifier  | DocStatus |
      | ddOrder_v1  | VO     |

  @from:cucumber
  Scenario: Deactivating the schedule voids the DD_Order and creates no replacement (picker not busy)
    # Cancelling the schedule (mirrors a sales-order line cancellation) re-fires the reconcile flow.
    When the M_ShipmentSchedule identified by shipmentSchedule is deactivated

    # The existing DD_Order is voided and NO new live DD_Order is created.
    Then after not more than 120s, the DD_Order linked to M_ShipmentSchedule shipmentSchedule is Voided
    And there is no live DD_Order for M_ShipmentSchedule shipmentSchedule
    # The async reconcile event handler records a Done AD_EventLog_Entry on success (REQUIREMENTS §5 TC3).
    And after not more than 10s, an AD_EventLog_Entry for the reconcile handler is found:
      | IsError |
      | false   |
