@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: generate shipments for the shipment schedules it locked
## F00130: Shipment Schedule (Lieferdisposition)
# "Generate shipments" locks the selected shipment schedules that can be shipped,
# then moves each schedule's lock to the workpackage that ships it.
# A schedule that becomes shippable only after the selection was locked is left for the next run.

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2024-01-15T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_Products:
      | Identifier        | IsStocked |
      | product_unstocked | false     |
      | product_noStock   | true      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | plv_1                  | product_unstocked | 10.0     | PCE      |
      | plv_1                  | product_noStock   | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | customer_a | Y          | ps_1               | A            |
      | customer_b | Y          | ps_1               | A            |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC11_lockGap
  Scenario: a schedule that becomes shippable after the selection was locked is left for the next run
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DeliveryRule |
      | order_1    | true    | customer_a    | 2024-01-15  | A            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID      | QtyEntered |
      | orderLine_0 | order_1    | product_unstocked | 10         |
      | orderLine_1 | order_1    | product_noStock   | 7          |
    And the order identified by order_1 is completed
    # s1 has no stock, so it can't be shipped yet
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | s0         | orderLine_0    | N             | 10           |
      | s1         | orderLine_1    | N             | 0            |
    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 60 seconds for order 'order_1'
    And the shipment schedule recompute queues are paused

    When 'generate shipments' is invoked for "s0" while "s1" becomes eligible after the selection was locked

    Then 'generate shipments' ended without error
    And exactly 1 shipment workpackage was enqueued containing "s0"
    And the shipment schedule "s1" is not locked
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver |
      | s1                    | 7            |
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s0                    | shipment_0 |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @Id:S33004_TC11_normalPath
  Scenario: each workpackage takes over the locks of its schedules, one workpackage per header aggregation key
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DeliveryRule |
      | order_a    | true    | customer_a    | 2024-01-15  | A            |
      | order_b    | true    | customer_b    | 2024-01-15  | A            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID      | QtyEntered |
      | orderLine_a1 | order_a    | product_unstocked | 10         |
      | orderLine_a2 | order_a    | product_unstocked | 5          |
      | orderLine_b1 | order_b    | product_unstocked | 3          |
    And the order identified by order_a is completed
    And the order identified by order_b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | s_a1       | orderLine_a1   | N             | 10           |
      | s_a2       | orderLine_a2   | N             | 5            |
      | s_b1       | orderLine_b1   | N             | 3            |

    When 'generate shipments' is invoked for "s_a1,s_a2,s_b1"

    Then 'generate shipments' ended without error
    And exactly 2 shipment workpackages were enqueued containing "s_a1,s_a2,s_b1"
    And each enqueued shipment workpackage owns the locks of its shipment schedules of one header aggregation key, and the selection lock is released
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s_a1                  | shipment_a |
      | s_a2                  | shipment_a |
      | s_b1                  | shipment_b |
