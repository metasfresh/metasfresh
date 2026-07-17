@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00130_Shipment_Schedule
@ghActions:run_on_executor7
Feature: tag invalid shipment schedules for a recompute pass in whole-product batches
## F00130: Shipment Schedule (Lieferdisposition)
# A recompute pass claims a bounded batch of the invalidated shipment schedules by tagging their
# M_ShipmentSchedule_Recompute markers. The batch is bounded to WHOLE PRODUCTS (the stock-coherent
# unit): products are taken ascending by M_Product_ID and accumulate until the cumulative distinct-
# schedule count would reach the batch size, a product is never split across the boundary, and at
# least one whole product is always tagged even if it alone exceeds the batch size. A batch size <= 0
# tags every untagged marker. This is the DB function M_ShipmentSchedule_TagToRecompute, invoked by
# every recompute pass (UpdateInvalidShipmentSchedulesWorkpackageProcessor).

  Background:
    Given infrastructure and metasfresh are running
    And all untagged M_ShipmentSchedule_Recompute markers are deleted
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1                          | DE                        | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier |
      | plv_1      | pl_1                      |
    # productA, productB, productC are created in this order so their M_Product_IDs ascend --
    # the batching orders candidate products ascending by M_Product_ID.
    And metasfresh contains M_Products:
      | Identifier |
      | productA   |
      | productB   |
      | productC   |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | ppA        | plv_1                             | productA                | 10.0     | PCE               | Normal                        |
      | ppB        | plv_1                             | productB                | 10.0     | PCE               | Normal                        |
      | ppC        | plv_1                             | productC                | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    # One sales order whose lines produce a known per-product distribution of shipment schedules:
    # productA -> 2 schedules, productB -> 3 schedules, productC -> 1 schedule (6 total).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_1    | true    | bpartner_1               | 2024-01-15  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olA1       | order_1               | productA                | 10         |
      | olA2       | order_1               | productA                | 10         |
      | olB1       | order_1               | productB                | 10         |
      | olB2       | order_1               | productB                | 10         |
      | olB3       | order_1               | productB                | 10         |
      | olC1       | order_1               | productC                | 10         |
    When the order identified by order_1 is completed
    And the next CreateMissingShipmentSchedules workpackage is processed
    # The 6 schedules exist and are still flagged for recompute (their markers are untagged --
    # no recompute pass has claimed them yet).
    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedA1    | olA1                      | Y             |
      | schedA2    | olA2                      | Y             |
      | schedB1    | olB1                      | Y             |
      | schedB2    | olB2                      | Y             |
      | schedB3    | olB3                      | Y             |
      | schedC1    | olC1                      | Y             |
    And 6 M_ShipmentSchedule_Recompute markers remain untagged

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC2
  Scenario: a batch size <= 0 tags every untagged marker (the unbounded / manual path)
    When the invalid shipment schedules are tagged for recompute selection recomputePass and batch size 0
    Then 6 M_ShipmentSchedule_Recompute markers are tagged for recompute selection recomputePass:
      | M_ShipmentSchedule_ID |
      | schedA1               |
      | schedA2               |
      | schedB1               |
      | schedB2               |
      | schedB3               |
      | schedC1               |
    And 0 M_ShipmentSchedule_Recompute markers remain untagged

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC3
  Scenario: the tightest batch still tags one whole product even though it alone exceeds the batch size
  # Batch size 1: productA alone has 2 schedules (> 1), but the first product always qualifies
  # (its running total so far is zero), so it is tagged whole -- never split into a partial product.
    When the invalid shipment schedules are tagged for recompute selection recomputePass and batch size 1
    Then 2 M_ShipmentSchedule_Recompute markers are tagged for recompute selection recomputePass:
      | M_ShipmentSchedule_ID |
      | schedA1               |
      | schedA2               |
    And 4 M_ShipmentSchedule_Recompute markers remain untagged

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC4
  Scenario: a bounded batch accumulates whole products and never splits one across the boundary
  # Batch size 4: productA (cumulative-before=0) and productB (cumulative-before=2) both qualify (< 4);
  # productC (cumulative-before=5) does not. productA + productB are tagged whole; productC is left behind.
    When the invalid shipment schedules are tagged for recompute selection recomputePass and batch size 4
    Then 5 M_ShipmentSchedule_Recompute markers are tagged for recompute selection recomputePass:
      | M_ShipmentSchedule_ID |
      | schedA1               |
      | schedA2               |
      | schedB1               |
      | schedB2               |
      | schedB3               |
    And 1 M_ShipmentSchedule_Recompute markers remain untagged

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC5
  Scenario: a bounded batch large enough for the whole backlog drains it in one pass
  # Batch size 10 (>= the 6 distinct schedules): every product qualifies, all markers are tagged.
    When the invalid shipment schedules are tagged for recompute selection recomputePass and batch size 10
    Then 6 M_ShipmentSchedule_Recompute markers are tagged for recompute selection recomputePass:
      | M_ShipmentSchedule_ID |
      | schedA1               |
      | schedA2               |
      | schedB1               |
      | schedB2               |
      | schedB3               |
      | schedC1               |
    And 0 M_ShipmentSchedule_Recompute markers remain untagged

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC6
  Scenario: a second recompute pass tags only the still-untagged products, never re-tagging a claimed one
    When the invalid shipment schedules are tagged for recompute selection firstPass and batch size 1
    Then 2 M_ShipmentSchedule_Recompute markers are tagged for recompute selection firstPass:
      | M_ShipmentSchedule_ID |
      | schedA1               |
      | schedA2               |
    And 4 M_ShipmentSchedule_Recompute markers remain untagged
    When the invalid shipment schedules are tagged for recompute selection secondPass and batch size 100
    Then 4 M_ShipmentSchedule_Recompute markers are tagged for recompute selection secondPass:
      | M_ShipmentSchedule_ID |
      | schedB1               |
      | schedB2               |
      | schedB3               |
      | schedC1               |
    And 2 M_ShipmentSchedule_Recompute markers are tagged for recompute selection firstPass:
      | M_ShipmentSchedule_ID |
      | schedA1               |
      | schedA2               |
    And 0 M_ShipmentSchedule_Recompute markers remain untagged
