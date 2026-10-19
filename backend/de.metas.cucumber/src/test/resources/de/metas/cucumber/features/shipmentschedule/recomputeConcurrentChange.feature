@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: shipment schedule recompute pass meets a concurrent change
## F00130: Shipment Schedule (Lieferdisposition)
# A shipment schedule that is flagged for recompute waits for the next recompute pass.
# The scenarios pause the async queues that run that pass, and run the pass themselves,
# so that a pass can run deterministically while another transaction is still open.

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
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | product_1    | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer_1 | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order_1    | true    | customer_1    | 2024-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_1 | order_1    | product_1    | 10         |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S33004_harness
  Scenario: a new shipment schedule waits for the paused recompute pass and is recomputed once the queues resume
    Given the shipment schedule recompute queues are paused
    When the order identified by order_1 is completed
    And the pending shipment schedule creation is processed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | Y             |
    And 1 untagged M_ShipmentSchedule_Recompute marker exists for ss_1
    When the shipment schedule recompute queues are resumed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_1       | orderLine_1    | N             |
