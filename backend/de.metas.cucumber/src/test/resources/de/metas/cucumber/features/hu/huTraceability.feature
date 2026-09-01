@from:cucumber
@allure.label.epic:E0370_Intralogistic_HUs
@allure.label.feature:F5000_Handling_Unit
@F5000
@ghActions:run_on_executor5
Feature: HU Traceability Report — SQL correctness tests
  Verifies that the M_HU_Trace_Report SQL function returns the correct rows,
  covering two previously-fixed SQL bugs plus the receipt-to-shipment pairing
  behaviour of the DIRECT_SALE_DETAIL section:
  - Bug A (Section 5): PRODUCTION_RECEIPT_DETAL appears even when the PRODUCTION_ISSUE HU has no MHD attribute
  - Bug B (Section 6): DIRECT_SALE_DETAIL appears for products with NULL lot number
  - DIRECT_SALE_DETAIL pairing (Section 6): a shipment must be paired with the receipt(s) it is
    actually traceable to, not with every receipt that merely shares the same lot and product.
    Tracing follows the M_HU_Trace graph — same VHU, or a chain of VHU_Source_ID edges — and is
    guarded by lot agreement between the receipt and the shipment.

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

  @Id:S0000.1_HUTrace_TC1
  Scenario: A graph-traced receipt suppresses the other receipts of the same lot
    Given metasfresh contains M_Products:
      | Identifier     | Value             | Name            |
      | traceProduct_1 | traceProductVal_1 | Trace Product 1 |
    When M_HU_Trace_Report test data is set up for scenario "traced_one_of_two":
      | TestType                   | M_Product_ID.Identifier |
      | TRACED_ONE_OF_TWO_RECEIPTS | traceProduct_1          |
    And M_HU_Trace_Report is invoked for scenario "traced_one_of_two"
    Then M_HU_Trace_Report detail rows for scenario "traced_one_of_two" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis | Menge | Liefermenge |
      | receipt1     | shipment      | TRACED    | 100   | 24          |

  @Id:S0000.1_HUTrace_TC0
  Scenario: A graph-traced receipt whose lot disagrees with the shipment's is not reported as traced
    Given metasfresh contains M_Products:
      | Identifier                | Value                        | Name                         |
      | traceProduct_lotMismatch  | traceProductVal_lotMismatch  | Trace Product Lot Mismatch   |
    When M_HU_Trace_Report test data is set up for scenario "lot_disagreement":
      | TestType         | M_Product_ID.Identifier  |
      | LOT_DISAGREEMENT | traceProduct_lotMismatch |
    And M_HU_Trace_Report is invoked for scenario "lot_disagreement"
    Then M_HU_Trace_Report detail rows for scenario "lot_disagreement" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis | Menge | Liefermenge |

  @Id:S0000.1_HUTrace_TC2
  Scenario: A receipt and shipment sharing the same VHU are traced without any transform edge
    Given metasfresh contains M_Products:
      | Identifier            | Value                    | Name                     |
      | traceProduct_sameVhu  | traceProductVal_sameVhu  | Trace Product Same VHU   |
    When M_HU_Trace_Report test data is set up for scenario "same_vhu_no_transform":
      | TestType               | M_Product_ID.Identifier |
      | SAME_VHU_NO_TRANSFORM  | traceProduct_sameVhu    |
    And M_HU_Trace_Report is invoked for scenario "same_vhu_no_transform"
    Then M_HU_Trace_Report detail rows for scenario "same_vhu_no_transform" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis | Menge | Liefermenge |
      | receipt1     | shipment      | TRACED    | 100   | 24          |

  @Id:S0000.1_HUTrace_TC3
  Scenario: A multi-step VHU transformation chain still traces to the original receipt
    Given metasfresh contains M_Products:
      | Identifier                | Value                     | Name                          |
      | traceProduct_twoStepChain | traceProductVal_twoStep   | Trace Product Two Step Chain  |
    When M_HU_Trace_Report test data is set up for scenario "two_step_transform":
      | TestType            | M_Product_ID.Identifier   |
      | TWO_STEP_TRANSFORM  | traceProduct_twoStepChain |
    And M_HU_Trace_Report is invoked for scenario "two_step_transform"
    Then M_HU_Trace_Report detail rows for scenario "two_step_transform" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis | Menge | Liefermenge |
      | receipt1     | shipment      | TRACED    | 100   | 24          |

  @Id:S0000.1_HUTrace_TC4
  Scenario: Candidate suppression applies per shipment, not globally across the lot
    Given metasfresh contains M_Products:
      | Identifier          | Value                  | Name                 |
      | traceProduct_mixed  | traceProductVal_mixed  | Trace Product Mixed  |
    When M_HU_Trace_Report test data is set up for scenario "mixed_traced_and_candidate":
      | TestType                    | M_Product_ID.Identifier |
      | MIXED_TRACED_AND_CANDIDATE  | traceProduct_mixed      |
    And M_HU_Trace_Report is invoked for scenario "mixed_traced_and_candidate"
    Then M_HU_Trace_Report detail rows for scenario "mixed_traced_and_candidate" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis     | Menge | Liefermenge |
      | receipt1     | shipmentA     | LOT_CANDIDATE | 100   | 30          |
      | receipt2     | shipmentA     | LOT_CANDIDATE | 50    | 30          |
      | receipt1     | shipmentB     | LOT_CANDIDATE | 100   | 20          |
      | receipt2     | shipmentB     | LOT_CANDIDATE | 50    | 20          |
      | receipt1     | shipment3     | TRACED        | 100   | 24          |

  @Id:S0000.1_HUTrace_TC5
  Scenario: A receipt and shipment with neither a lot number nor a VHU link are a product-level candidate
    Given metasfresh contains M_Products:
      | Identifier              | Value                       | Name                       |
      | traceProduct_noLotLink  | traceProductVal_noLotLink   | Trace Product No Lot Link  |
    When M_HU_Trace_Report test data is set up for scenario "no_lot_no_link":
      | TestType        | M_Product_ID.Identifier |
      | NO_LOT_NO_LINK  | traceProduct_noLotLink  |
    And M_HU_Trace_Report is invoked for scenario "no_lot_no_link"
    Then M_HU_Trace_Report detail rows for scenario "no_lot_no_link" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis          | Menge | Liefermenge |
      | receipt1     | shipment      | PRODUCT_CANDIDATE  | 100   | 24          |

  @Id:S0000.1_HUTrace_TC6
  Scenario: A receipt document's quantity across several VHUs is reported once, not once per VHU
    Given metasfresh contains M_Products:
      | Identifier             | Value                      | Name                     |
      | traceProduct_qtyDedup  | traceProductVal_qtyDedup   | Trace Product Qty Dedup  |
    When M_HU_Trace_Report test data is set up for scenario "receipt_qty_and_dedup":
      | TestType               | M_Product_ID.Identifier |
      | RECEIPT_QTY_AND_DEDUP  | traceProduct_qtyDedup   |
    And M_HU_Trace_Report is invoked for scenario "receipt_qty_and_dedup"
    Then M_HU_Trace_Report detail rows for scenario "receipt_qty_and_dedup" are:
      | ReceiptDocNo | ShipmentDocNo | LinkBasis | Menge | Liefermenge |
      | receipt1     | shipment      | TRACED    | 100   | 24          |
