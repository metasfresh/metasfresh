@from:cucumber
@allure.label.epic:E0370_Intralogistic_HUs
@allure.label.feature:F5000_Handling_Unit
@F5000
@ghActions:run_on_executor5
Feature: HU Traceability Report — SQL correctness tests
  Verifies that the M_HU_Trace_Report SQL function returns the correct rows
  for the two bug fixes:
  - Bug A (Section 5): PRODUCTION_RECEIPT_DETAL appears even when the PRODUCTION_ISSUE HU has no MHD attribute
  - Bug B (Section 6): DIRECT_SALE_DETAIL appears for products with NULL lot number

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @Id:S0000.1_HUTrace_BugB
  Scenario: DIRECT_SALE_DETAIL row appears for non-manufactured product with NULL lot number (Bug B fix — IS NOT DISTINCT FROM)
    # Before fix: shipment_trace.lotnumber = t.lotnumber fails for NULL = NULL
    # After fix:  shipment_trace.lotnumber IS NOT DISTINCT FROM t.lotnumber handles NULL correctly
    Given metasfresh contains M_Products:
      | Identifier        | Value                   | Name                    |
      | traceProduct_BugB | traceProductValue_BugB  | Trace Product Bug B     |
    When M_HU_Trace_Report test data is set up for scenario "direct_sale_null_lot":
      | TestType           | M_Product_ID.Identifier |
      | DIRECT_SALE_NULL_LOT | traceProduct_BugB      |
    And M_HU_Trace_Report is invoked for scenario "direct_sale_null_lot"
    Then M_HU_Trace_Report result for scenario "direct_sale_null_lot" contains detail_type row "DIRECT_SALE_DETAIL"

  @Id:S0000.1_HUTrace_BugA
  Scenario: PRODUCTION_RECEIPT_DETAL row appears for manufactured product whose PRODUCTION_ISSUE HU has no MHD attribute (Bug A fix — LEFT JOIN)
    # Before fix: JOIN m_hu_attribute mhd excluded products without best-before date
    # After fix:  LEFT JOIN m_hu_attribute mhd includes them (mhd fields are NULL)
    Given metasfresh contains M_Products:
      | Identifier            | Value                       | Name                        |
      | finishedProduct_BugA  | finishedProductValue_BugA   | Finished Product Bug A      |
      | rawMaterial_BugA      | rawMaterialValue_BugA       | Raw Material Bug A          |
    When M_HU_Trace_Report test data is set up for scenario "production_receipt_no_mhd":
      | TestType                      | M_Product_ID.Identifier | RawMaterial_ID.Identifier |
      | PRODUCTION_RECEIPT_NO_MHD     | finishedProduct_BugA    | rawMaterial_BugA          |
    And M_HU_Trace_Report is invoked for scenario "production_receipt_no_mhd"
    Then M_HU_Trace_Report result for scenario "production_receipt_no_mhd" contains detail_type row "PRODUCTION_RECEIPT_DETAL"
