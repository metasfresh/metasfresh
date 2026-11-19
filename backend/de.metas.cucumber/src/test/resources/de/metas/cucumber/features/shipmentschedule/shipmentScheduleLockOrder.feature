@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: writers of an order's shipment schedules lock them in M_ShipmentSchedule_ID order
## F00130: Shipment Schedule (Lieferdisposition)
# The reactivation of a sales order closes its shipment schedules, and the asynchronous recompute pass updates them; both update
# several shipment schedules in one transaction. If they locked them in opposite order, they would deadlock each other, and the
# reactivation, which is not retried, would fail. So both lock them in ascending M_ShipmentSchedule_ID order.
# The scenarios pause the async queues that run the recompute pass, so that nothing else locks the order's shipment schedules.

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
      | Identifier |
      | product_1  |
      | product_2  |
      | product_3  |
      | product_4  |
      | product_5  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | product_1    | 10.0     | PCE      |
      | plv_1                  | product_2    | 10.0     | PCE      |
      | plv_1                  | product_3    | 10.0     | PCE      |
      | plv_1                  | product_4    | 10.0     | PCE      |
      | plv_1                  | product_5    | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer_1 | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_1    | true    | customer_1    | 2024-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_1 | order_1    | product_1    | 10         |
      | orderLine_2 | order_1    | product_2    | 10         |
      | orderLine_3 | order_1    | product_3    | 10         |
      | orderLine_4 | order_1    | product_4    | 10         |
      | orderLine_5 | order_1    | product_5    | 10         |
    And the shipment schedule recompute queues are paused
    And the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | Y             |
      | ss_2       | orderLine_2    | Y             |
      | ss_3       | orderLine_3    | Y             |
      | ss_4       | orderLine_4    | Y             |
      | ss_5       | orderLine_5    | Y             |


# ######################################################################################################################
# ######################################################################################################################
  @Id:shipmentScheduleLockOrder_recompute
  Scenario: the recompute pass locks the shipment schedules in ID order, not in the order in which it allocates them
    # earlier preparation dates make the recompute allocate ss_5 and ss_4 first
    Given update shipment schedules
      | M_ShipmentSchedule_ID | PreparationDate_Override |
      | ss_5                  | 2024-01-10T08:00:00Z     |
      | ss_4                  | 2024-01-11T08:00:00Z     |
    # the first recompute of the new shipment schedules changes each of them
    Then the shipment schedule recompute pass locks the shipment schedules of the order identified by order_1 in ID order


  @Id:shipmentScheduleLockOrder_reactivate
  Scenario: the reactivation of an order locks its shipment schedules in ID order before it closes them
    Given the pending shipment schedule recompute pass runs
    Then the reactivation of the order identified by order_1 locks its shipment schedules in ID order
