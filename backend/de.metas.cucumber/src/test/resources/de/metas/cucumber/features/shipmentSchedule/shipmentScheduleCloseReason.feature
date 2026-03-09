@from:cucumber
@ghActions:run_on_executor3
Feature: Shipment schedule CloseReason tracks why a schedule was closed

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier      |
      | product_closeRsn |
    And metasfresh contains M_PricingSystems
      | Identifier  |
      | ps_closeRsn |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_closeRsn | ps_closeRsn                   | DE                        | EUR                 | pl_closeRsn | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | plv_closeRsn | pl_closeRsn               | plv_closeRsn | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_closeRsn | plv_closeRsn                      | product_closeRsn        | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier   | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bp_closeRsn  | Y              | ps_closeRsn                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpLoc_closeRsn  | 2705501234567 | bp_closeRsn              | Y                   | Y                   |


  @from:cucumber
  @Id:S27550_100
  Scenario: Order reactivation sets CloseReason=OR on the shipment schedule
    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule |
      | order_cr100      | true    | bp_closeRsn              | 2022-05-17  | ps_closeRsn                       | bpLoc_closeRsn                        | F                |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_cr100  | order_cr100           | product_closeRsn        | 10         |
    When the order identified by order_cr100 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched_cr100 | orderLine_cr100           | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed | OPT.CloseReason |
      | sched_cr100                      | false        |                 |

    # Reactivate the order => schedule should be closed with reason OR (OrderReactivated)
    When the order identified by order_cr100 is reactivated
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed | OPT.CloseReason  |
      | sched_cr100                      | true         | OR               |


  @from:cucumber
  @Id:S27550_200
  Scenario: Manual close sets CloseReason=MA on the shipment schedule
    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule |
      | order_cr200      | true    | bp_closeRsn              | 2022-05-17  | ps_closeRsn                       | bpLoc_closeRsn                        | F                |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_cr200  | order_cr200           | product_closeRsn        | 10         |
    When the order identified by order_cr200 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched_cr200 | orderLine_cr200           | N             |

    # Manually close the schedule => CloseReason=MA
    When the M_ShipmentSchedule identified by sched_cr200 is closed with reason MA
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed | OPT.CloseReason |
      | sched_cr200                      | true         | MA              |


  @from:cucumber
  @Id:S27550_300
  Scenario: Reopen clears CloseReason to null
    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule |
      | order_cr300      | true    | bp_closeRsn              | 2022-05-17  | ps_closeRsn                       | bpLoc_closeRsn                        | F                |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_cr300  | order_cr300           | product_closeRsn        | 10         |
    When the order identified by order_cr300 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | sched_cr300 | orderLine_cr300           | N             |

    # Close with reason, then reopen => CloseReason should be null
    When the M_ShipmentSchedule identified by sched_cr300 is closed with reason MA
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed | OPT.CloseReason |
      | sched_cr300                      | true         | MA              |

    When the M_ShipmentSchedule identified by sched_cr300 is reactivated
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed | OPT.CloseReason |
      | sched_cr300                      | false        |                 |
