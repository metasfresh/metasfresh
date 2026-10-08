@from:cucumber
@ghActions:run_on_executor5
Feature: Product Statistics report uses C_BPartner_ID join instead of Value
  The fresh_Product_Statistics_Non0_Report function should join by c_bpartner_id
  rather than by bp_value + ad_org_id, ensuring correct results even when
  multiple BPartners share the same Value.

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  Scenario: fresh_product_statistics_non0_report function is callable and returns c_bpartner_id column
    Then the fresh_product_statistics_non0_report function returns a result set with c_bpartner_id column
