@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor6
Feature: DD_Order picking reconcile — create a distribution order for a packing-warehouse sales order
  As a warehouse operator running a packing workplace ("Packtisch"),
  I want every sales-order line on a packing warehouse to automatically get exactly one
  Completed distribution order moving the goods from the stocking warehouse to the packing warehouse,
  so that the picker always has a DD_Order to work from without manual planning.

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

  @from:cucumber
  Scenario: One Completed DD_Order is created for a sales order on the packing warehouse
    # The stocking warehouse holds the goods; the packing warehouse is where the picker delivers them.
    # The packing warehouse is flagged IsAutoDistributionOrder=Y and excluded from material-dispo (MRP_Exclude=Y),
    # so the dedicated reconcile flow (not material-dispo) drives DD_Order creation.
    Given metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customer      | customerLocation       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID |
      | packingWH      | customer      | customerLocation       | Y           | Y                  | network                   |
    # The network resolves source = stockWH for target = packingWH (per warehouse-pair, product-agnostic).
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | order      | true    | customer      | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 5          |
    And the order identified by order is completed

    # The shipment schedule is generated on order completion; the M_ShipmentSchedule interceptor then
    # triggers the async reconcile that creates the DD_Order.
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | Warehouse_ID |
      | shipmentSchedule | orderLine      | packingWH    |

    # Exactly one Completed DD_Order, qty 5, source stockWH -> target packingWH, with both
    # DD_Order.M_ShipmentSchedule_ID and DD_OrderLine.M_ShipmentSchedule_ID referencing the schedule.
    And after not more than 120s, the DD_Order linked to shipment schedule is found:
      | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
      | shipmentSchedule      | CO        | stockWH             | packingWH         | 5          |
    # The async reconcile event handler records a Done AD_EventLog_Entry on success (REQUIREMENTS §5 TC1).
    And after not more than 10s, an AD_EventLog_Entry for the reconcile handler is found:
      | IsError |
      | false   |

  @from:cucumber
  Scenario: Flagging a warehouse as packing without a distribution network is rejected
    # DD_NetworkDistribution_ID is mandatory when IsAutoDistributionOrder=Y — the M_Warehouse interceptor
    # refuses the save so an operator cannot create an unresolvable packing warehouse.
    Then saving M_Warehouse is rejected:
      | M_Warehouse_ID | IsAutoDistributionOrder |
      | packingWH      | Y                  |
