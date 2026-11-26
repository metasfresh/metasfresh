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
    # productA, productB, productC are created in this order so their M_Product_IDs ascend --
    # the whole-product batching orders candidate products ascending by M_Product_ID.
    And metasfresh contains M_Products:
      | Identifier |
      | productA   |
      | productB   |
      | productC   |
    # Seed the recompute backlog DIRECTLY, not via the real order->complete->CreateMissingShipmentSchedules
    # ->invalidate pipeline. Reasons:
    # - that pipeline auto-enqueues UpdateInvalidShipmentSchedulesWorkpackageProcessor (NOT gated by
    #   SKIP_WP_PROCESSOR_FOR_AUTOMATION), which claims/drains markers concurrently and races the counts.
    # - the tag DB function reads only the marker rows + their schedules' M_Product_ID, so this seeds
    #   exactly that state.
    # Distribution: productA -> 2 schedules, productB -> 3, productC -> 1 (6 total), each with one marker.
    And the following M_ShipmentSchedules are seeded, each with one untagged recompute marker:
      | Identifier | M_Product_ID |
      | schedA1    | productA     |
      | schedA2    | productA     |
      | schedB1    | productB     |
      | schedB2    | productB     |
      | schedB3    | productB     |
      | schedC1    | productC     |
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

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00130_Shipment_Schedule
  @Id:S31050_TC7
  Scenario: an orphaned recompute marker is reaped and does not livelock the recompute queue
  # M_ShipmentSchedule_Recompute has no FK to M_ShipmentSchedule, so a marker can
  # outlive its schedule. Before the fix, existsUntaggedRecomputeMarkers() counted this untaggable orphan
  # forever, so a bounded recompute pass never saw its backlog reach zero and kept re-enqueueing a follow-up
  # workpackage -- an endless loop. The fix also reaps the orphan so it stops accumulating in the queue table.
    Given an orphaned untagged M_ShipmentSchedule_Recompute marker exists for "orphanSchedule"
    When a shipment-schedule recompute pass is enqueued
    Then no untagged M_ShipmentSchedule_Recompute marker remains for "orphanSchedule"
    And the shipment-schedule recompute work queue drains to zero pending workpackages
