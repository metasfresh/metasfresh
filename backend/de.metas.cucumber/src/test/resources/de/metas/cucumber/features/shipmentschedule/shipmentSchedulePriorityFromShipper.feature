@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: Shipment-schedule priority derived from the shipper
## F00130: Shipment Schedule (Lieferdisposition)
  A shipment schedule normally takes its priority from its sales order. Behind the
  M_ShipmentSchedule_PriorityRuleFromShipper sysconfig (default off), it instead takes the
  priority from its shipper whenever the shipper has one set — the delivery planner uses this
  to route urgent carriers ahead of standard ones, regardless of what the sales order says.
  # PriorityRule codes used below: 1 = Urgent, 3 = High, 5 = Medium, 7 = Low, 9 = Minor.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-08-27T08:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl         | ps                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv                    | product      | 10.0     | PCE      | taxCategory      |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |

  @Id:S31627_TC1
  Scenario: Switch off — the shipment schedule keeps the order's priority

    And set sys config boolean value false for sys config M_ShipmentSchedule_PriorityRuleFromShipper
    And contains M_Shippers
      | Identifier     | PriorityRule |
      | expressShipper | 1            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | PriorityRule |
      | order1     | true    | customer      | warehouse      | 2026-08-27  | 7            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLine1 | order1     | product      | 10         | expressShipper              |
    When the order identified by order1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule1  | orderLine1     | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PriorityRule |
      | schedule1             | 7            |

  @Id:S31627_TC2
  Scenario: Switch on — the shipment schedule takes the shipper's priority

    And set sys config boolean value true for sys config M_ShipmentSchedule_PriorityRuleFromShipper
    And contains M_Shippers
      | Identifier     | PriorityRule |
      | expressShipper | 1            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | PriorityRule |
      | order1     | true    | customer      | warehouse      | 2026-08-27  | 7            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLine1 | order1     | product      | 10         | expressShipper              |
    When the order identified by order1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule1  | orderLine1     | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PriorityRule |
      | schedule1             | 1            |

  @Id:S31627_TC3
  Scenario: Switch on, shipper has no priority set — falls back to the order's priority

    And set sys config boolean value true for sys config M_ShipmentSchedule_PriorityRuleFromShipper
    And contains M_Shippers
      | Identifier      |
      | standardShipper |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | PriorityRule |
      | order1     | true    | customer      | warehouse      | 2026-08-27  | 3            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLine1 | order1     | product      | 10         | standardShipper             |
    When the order identified by order1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule1  | orderLine1     | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PriorityRule |
      | schedule1             | 3            |

  @Id:S31627_TC4
  Scenario: Switch on, no shipper on the order line — falls back to the order's priority

    And set sys config boolean value true for sys config M_ShipmentSchedule_PriorityRuleFromShipper

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | PriorityRule |
      | order1     | true    | customer      | warehouse      | 2026-08-27  | 3            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine1 | order1     | product      | 10         |
    When the order identified by order1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule1  | orderLine1     | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PriorityRule |
      | schedule1             | 3            |

  @Id:S31627_TC6
  Scenario: Changing a shipper's priority recomputes its open schedules, but not its processed ones

    And set sys config boolean value true for sys config M_ShipmentSchedule_PriorityRuleFromShipper
    And contains M_Shippers
      | Identifier      | PriorityRule |
      | changingShipper | 7            |

    # Order 1: stays open (never shipped) — this is the one we watch through the recompute.
    # Its own priority is deliberately Urgent, so the assertion only passes if the derivation
    # really used the shipper's Low, not the order's.
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | PriorityRule |
      | pendingOrder | true    | customer      | warehouse      | 2026-08-27  | 1            |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID   | M_Product_ID | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | pendingOrderLine | pendingOrder | product      | 10         | changingShipper             |
    When the order identified by pendingOrder is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID   | IsToRecompute |
      | pendingSchedule | pendingOrderLine | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PriorityRule |
      | pendingSchedule       | 7            |

    # Order 2: shipped and completed right away, on the same shipper — its schedule becomes
    # Processed and must stay untouched by the later shipper-priority change.
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DeliveryRule | DateOrdered | PriorityRule |
      | shippedOrder | true    | customer      | warehouse      | F            | 2026-08-27  | 1            |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID   | M_Product_ID | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | shippedOrderLine | shippedOrder | product      | 10         | changingShipper             |
    And the order identified by shippedOrder is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID   | IsToRecompute |
      | shippedSchedule | shippedOrderLine | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shippedSchedule                  | D                 | Y                  |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.Processed | PriorityRule |
      | shippedSchedule       | true          | 7            |

    # Raise the shipper's priority — a carrier-service employee editing the Lieferweg window.
    When update M_Shipper:
      | Identifier      | PriorityRule |
      | changingShipper | 1            |

    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PriorityRule |
      | pendingSchedule       | 1            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | OPT.Processed | PriorityRule |
      | shippedSchedule       | true          | 7            |
