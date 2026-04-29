@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — non-proforma order regression (split-payment dormant)
  # Domain: a purchase order with the same LC+OD payment term but WITHOUT a proforma
  # allocation. Proves AC #22: iter-3 services are dormant unless a proforma-allocated
  # prepayment payment exists for the order.
  #
  # Scenario:
  #   - PO GrandTotal = 70,000 EUR (700 PCE @ 100 EUR/PCE, tax-inclusive).
  #   - LC 30 % + OD 70 % payment term — same as TC1/TC2.
  #   - NO proforma allocation, NO prepayment payment.
  #   - After completing the order: LC row Pending, OD row Awaiting_Pay (iter-2 behaviour).
  #   - Receive R1 (400 PCE): recomputeDeliverySteps is NOT triggered (no proforma).
  #     Pay schedule unchanged — still only the two iter-2 rows (LC + single OD).
  #   - Complete a financial invoice INV1 matched to R1: DeliveryPrepaymentAllocationService
  #     is NOT triggered. Zero allocation lines for INV1.
  #   - The single OD delivery row is NOT split into sub-rows.
  #
  # This scenario gating the entire iter-3 PR (AC #22).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2026-04-24T10:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_purchase | ps                 | DE           | EUR           | false | Y             |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_purchase           | product      | 100.00   | PCE      |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PaymentRulePO |
      | vendor     | Y        | N          | ps                 | P             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_loc | vendor        | Y               | Y               |

    And metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_lc      |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | ptb_lc     | pt_lc            | 30      | 0          | LC                | 10    |
      | ptb_od     | pt_lc            | 70      | 0          | OD                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_lc      | Y         | Y       |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |


  @from:cucumber
  @Id:S29369_TC8
  Scenario: No proforma allocation → split-payment dormant; delivery schedule unchanged by receipt + invoice

    # ── Order completed — LC row Pending, OD row Awaiting_Pay (iter-2 behaviour) ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 700        |
    And the order identified by lcOrder is completed

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 21000.00 | null          | PR     | null          | N      |
      | OD                | 49000.00 | null          | WP     | 2026-04-24    | N      |

    # ── R1: 400 PCE received — no proforma, so recomputeDeliverySteps is dormant ──
    When iter3 purchase receipt 'r1' is created and completed:
      | C_Order_ID | C_OrderLine_ID | MovementQty |
      | lcOrder    | lcOrderL1      | 400         |

    # AC #22 — schedule unchanged: still exactly 2 iter-2 rows (LC + OD), no delivery sub-rows
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 21000.00 | null          | PR     | null          | N      |
      | OD                | 49000.00 | null          | WP     | 2026-04-24    | N      |
    And the order identified by lcOrder has exactly 1 delivery sub-rows

    # ── INV1: financial purchase invoice, matched to R1 ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | true             |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID |
      | inv1L1     | inv1         | product      | 400 PCE     | 100.00 | lcOrderL1      |
    And iter3 M_MatchInv is created:
      | C_InvoiceLine_ID | M_InOut_ID | Qty |
      | inv1L1           | r1         | 400 |
    And the invoice identified by inv1 is completed

    # AC #22 — no allocation created (DeliveryPrepaymentAllocationService dormant)
    Then there are no allocation lines for invoice
      | C_Invoice_ID |
      | inv1         |

    # AC #22 — delivery schedule still unchanged after invoice completion
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 21000.00 | null          | PR     | null          | N      |
      | OD                | 49000.00 | null          | WP     | 2026-04-24    | N      |
    And the order identified by lcOrder has exactly 1 delivery sub-rows
