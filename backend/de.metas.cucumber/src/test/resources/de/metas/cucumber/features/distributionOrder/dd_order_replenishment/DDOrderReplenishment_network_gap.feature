@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor6
Feature: DD_Order replenishment — network gap soft-fail and repost recovery
  As a warehouse operator, when the distribution network of a packing warehouse cannot resolve a source
  warehouse, I want the reconcile to fail softly — no distribution order is created, an AD_Issue is logged
  and the event ends in Error so I can fix the network and repost — rather than blocking the sales order.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | productQ   |
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
      | priceListVersion       | productQ     | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN          | C_BPartner_ID |
      | customerLocation | bPLocation_1 | customer      |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    # The network exists (so the packing-warehouse config is valid) but has NO distribution line
    # resolving a source warehouse for the packing warehouse — the source-warehouse resolution will fail.
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID |
      | packingWH      | customer      | customerLocation       | Y           | Y                  | network                   |

  @from:cucumber
  Scenario: A missing source line makes the reconcile event fail softly, then succeeds after the network is fixed (TC6)
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | productQ     | 5          |
    And the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |

    # No source warehouse can be resolved → no DD_Order is created.
    And there is no live DD_Order for M_ShipmentSchedule shipmentSchedule
    # The reconcile event ends in Error with a readable message and an AD_Issue is logged.
    # MsgText matches the AD_Message resolved in the system base language (de_DE): "Kein Quelllager ...".
    And after not more than 120s, an AD_EventLog_Entry for the replenishment event handler is found:
      | IsError | MsgText        |
      | true    | %Quelllager%   |
    And after not more than 10s, an AD_Issue is logged for the replenishment network gap

    # Fix the network: add the missing source line resolving stockWH for the packing warehouse, then repost.
    When metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And the reconcile event for M_ShipmentSchedule shipmentSchedule is processed

    # Now the DD_Order is created.
    Then after not more than 30s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |
