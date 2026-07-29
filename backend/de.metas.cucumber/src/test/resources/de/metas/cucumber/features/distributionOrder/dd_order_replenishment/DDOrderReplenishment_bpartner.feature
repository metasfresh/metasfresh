@from:cucumber
@allure.label.epic:E0106_Distribution
@allure.label.feature:F5111_DDOrder_Replenishment
@ghActions:run_on_executor7
Feature: DD_Order replenishment — a distribution order belongs to the organization, not to a customer
  As a mover replenishing a packing workplace,
  I want the distribution order I work from to be owned by my own organization,
  so that neither the document nor its print-out claims to belong to a customer delivery.

  A replenishment order moves goods between the organization's own warehouses. One rule for both paths:
  - the order consolidating several customers' demand has no single customer to name;
  - the order serving a single delivery names the organization too, not that delivery's customer.

  Two customer deliveries of DIFFERENT business partners need the same product, in the same UOM, at the same
  workstation pick-from locator, from the same source locator — one product group, demand 10 + 5 = 15.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product      | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customerA  | N        | Y          | pricingSystem      |
      | customerB  | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN          | C_BPartner_ID | IsShipTo | IsBillTo |
      | customerALocation | bPLocation_1 | customerA     | true     | true     |
      | customerBLocation | bPLocation_2 | customerB     | true     | true     |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | network                   |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | stockWH        | customerA     | customerALocation      |
    # IsGroundLocator=Y is required by the replenishment service's ground-filter when computing the allocation.
    And metasfresh contains M_Locator:
      | Identifier   | M_Warehouse_ID | Value    | IsGroundLocator | PriorityNo |
      | stockLocator | stockWH        | Standard | Y               | 10         |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransitWH    | customerA     | customerALocation      | true        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude | IsAutoDistributionOrder | DD_NetworkDistribution_ID | M_Locator_ID   |
      | packingWH      | customerA     | customerALocation      | Y           | Y                       | network                   | packingLocator |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | network                   | packingWH      | stockWH              | shipper      |
    And metasfresh contains C_Workplaces
      | Identifier | M_Warehouse_ID | PickFrom_Locator_ID |
      | workplace  | packingWH      | packingLocator      |

    # 15 on-hand covers the group's summed demand, so the stock-aware split yields ONE source locator.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | stockInventory            | 2021-10-12   | stockWH        |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | stockInventory            | stockInventoryLine            | product                 | stockLocator | 0       | 15       | PCE          |
    And complete inventory with inventoryIdentifier 'stockInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | stockInventoryLine            | stockProductHU     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | orderA     | true    | customerA     | 2022-05-17  | packingWH      |
      | orderB     | true    | customerB     | 2022-05-17  | packingWH      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLineA | orderA     | product      | 10         |
      | orderLineB | orderB     | product      | 5          |
    And the order identified by orderA is completed
    And the order identified by orderB is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID | Warehouse_ID |
      | shipmentScheduleA | orderLineA     | packingWH    |
      | shipmentScheduleB | orderLineB     | packingWH    |

  @from:cucumber
  Scenario: The order consolidating two customers' demand names neither customer, but the organization
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |
      | jobScheduleB              | shipmentScheduleB     | workplace      | 5         |

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID  | DD_OrderLine_ID  | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | groupDDOrder | groupDDOrderLine | CO        | stockWH             | 15         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID  | M_Picking_Job_Schedule_ID | Qty |
      | groupDDOrderLine | jobScheduleA              | 10  |
      | groupDDOrderLine | jobScheduleB              | 5   |

    And the DD_Order identified by groupDDOrder names none of these business partners:
      | C_BPartner_ID |
      | customerA     |
      | customerB     |
    # Not merely "not a customer": the header names the organization whose warehouses the goods move between,
    # which is what drives the order's DeliveryRule, print format and print language.
    And the DD_Order identified by groupDDOrder names the business partner of its own organization

  @from:cucumber
  Scenario: The order serving a single delivery names the organization too, not that delivery's customer
    # One contributor only — the pre-aggregation shape, where the order used to carry the customer's partner.
    When create or update picking job schedules
      | M_Picking_Job_Schedule_ID | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | jobScheduleA              | shipmentScheduleA     | workplace      | 10        |

    Then after not more than 120s, exactly one live DD_Order exists for the product group:
      | M_Product_ID | M_LocatorTo_ID | DD_Order_ID           | DD_OrderLine_ID           | DocStatus | M_Warehouse_From_ID | QtyEntered |
      | product      | packingLocator | singleDeliveryDDOrder | singleDeliveryDDOrderLine | CO        | stockWH             | 10         |
    And the DD_OrderLine contributors are found:
      | DD_OrderLine_ID           | M_Picking_Job_Schedule_ID | Qty |
      | singleDeliveryDDOrderLine | jobScheduleA              | 10  |

    # customerA IS the one customer this order serves — the accepted deviation is that the header still names
    # the organization, so the single-contributor path stays single-branch with the consolidated one.
    And the DD_Order identified by singleDeliveryDDOrder names none of these business partners:
      | C_BPartner_ID |
      | customerA     |
      | customerB     |
    And the DD_Order identified by singleDeliveryDDOrder names the business partner of its own organization
