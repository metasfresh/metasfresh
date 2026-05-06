@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor4
Feature: Split-payment unified end-to-end story using customer-spreadsheet numbers
  # Domain: a purchase order with a complex payment term that has two breaks:
  #   - 30 % LC  (LetterOfCreditDate) — paid up-front via a proforma invoice
  #   - 70 % BL  (BillOfLadingDate)   — paid later against the delivery invoice
  #
  # The procurement worker:
  #   1. Issues a purchase order under those terms (PO GrandTotal = 68,654.40 EUR, 1 PCE at list price).
  #   2. Receives a proforma invoice from the vendor for the LC portion (20,596.32 EUR).
  #   3. Allocates the proforma to the order — the LC pay-schedule step transitions to Awaiting_Pay.
  #   4. Pays the proforma via the standard pay-selection flow — the LC step transitions to Paid.
  #   5. Receives goods (R1 + R2) and the system computes delivery-based sub-rows and invoice allocations.
  #
  # Customer spreadsheet numbers (dt204.Multiple.Payments.xlsx):
  #   PO GrandTotal:        68,654.40 EUR (tax-inclusive, 19% VAT, price-list item 68,654.40 EUR/PCE)
  #   LC break 30%:         20,596.32 EUR  (= 68,654.40 × 0.30)
  #   BL break 70%:         48,058.08 EUR  (= 68,654.40 × 0.70)
  #   INV1 (Partial) alloc:  9,542.40 EUR  (= MIN(R1_with_tax × LC%, remaining_prepay))
  #   INV2 (Final)   alloc: 11,053.92 EUR  (full remaining prepay consumed)
  #
  # iter-3 design: ReferenceDateType BL → isMaterialReceiptDate(BL) = true
  #               (OD → isMaterialReceiptDate(OD) = false)
  # Refs: https://github.com/metasfresh/me03/issues/29369

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
      | lcProduct  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_purchase           | lcProduct    | 68654.40 | PCE      |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PaymentRulePO |
      | vendor     | Y        | N          | ps                 | P             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_loc | vendor        | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |
    And metasfresh contains C_BP_BankAccount
      | Identifier          | C_BPartner_ID | C_Currency_ID |
      | vendor_bank_account | vendor        | EUR           |

    # Two payment terms:
    #   pt_lc        — order-level term: 30 % LC + 70 % BL (drives the LC pay-schedule rows)
    #                  break-2 uses BL (BillOfLadingDate) — iter-3 design: isMaterialReceiptDate(BL)=true
    #   pt_immediate — proforma-level term (NetDays=0) so the proforma's DueDate = DateInvoiced
    #                  (the procurement worker pays on the proforma's due date, OnlyDue=Y picks it up)
    And metasfresh contains C_PaymentTerm
      | Identifier   |
      | pt_lc        |
      | pt_immediate |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | ptb_lc     | pt_lc            | 30      | 0          | LC                | 10    |
      | ptb_bl     | pt_lc            | 70      | 0          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_lc      | Y         | Y       |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |

    # Canonical accounting/tax identifiers
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory19 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory19    | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales19    | tax19    | Y       |
      | purchase19 | tax19    | N       |


  Scenario: S1 - Happy path — PO → proforma → allocate → pay-selection → payment → LC=Paid (asserts LC step at every transition)
    # The procurement worker pays the LC portion of a complex-payment-term PO via a proforma invoice.
    # LC step state walk: Pending → Awaiting_Pay (allocate) → Paid (payment).

    # ── Order completed → both LC and BL steps Pending (BL reference date = BillOfLadingDate, not yet known) ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | lcProduct    | 1          |
    And the order identified by lcOrder is completed

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | null          | PR     |
      | BL                | 48058.08 | null          | PR     |

    # ── Proforma created and completed (GrandTotal = LC plan = 20596.32) — no pay-schedule change ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | lcProduct    | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    # A completed proforma is not yet paid — IsPaid stays N until its full payment completes.
    And validate created invoices
      | Identifier | IsPaid |
      | lcInvoice  | N      |

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | null          | PR     |
      | BL                | 48058.08 | null          | PR     |

    # AC #17 — invoiceOpenToDate proforma branch on an unpaid proforma:
    # OpenAmt = GrandTotal, PaidAmt = 0, HasAllocations = false (proformas have no C_AllocationLine rows).
    Then for invoice the following invoiceOpenToDate result is expected:
      | C_Invoice_ID | OpenAmt  | PaidAmt | GrandTotal | HasAllocations |
      | lcInvoice    | 20596.32 | 0       | 20596.32   | false          |

    # ── Allocate proforma → LC step Awaiting_Pay; LC_Date stamped ──
    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | BL                | 48058.08 | null          | PR     |
    And validate the created orders
      | Identifier | LC_Date    |
      | lcOrder    | 2026-04-24 |

    # ── Pay-selection on the proforma's DueDate (= DateInvoiced for pt_immediate) — invoice-only line ──
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySelection | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | Y       |

    Then the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt  |
      | lcInvoice    | 20596.32 |

    # ── Generate payment → full payment (abs(PayAmt) = proforma.GrandTotal — AC #16 guard);
    #    payment carries Proforma_Invoice_ID + IsPrepayment=Y; LC step Paid ──
    And the pay selection identified by paySelection is completed
    Then "Create Payments" is invoked for pay selection paySelection

    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | lcInvoice    | 20596.32 | lcPayment    |

    # Proforma-payment shape: C_Invoice_ID null (no allocation), Proforma_Invoice_ID set,
    # IsPrepayment=Y. PayAmt = proforma.GrandTotal (full-payment guard at BEFORE_PREPARE).
    Then validate payments
      | C_Payment_ID.Identifier | IsPrepayment | C_Invoice_ID | Proforma_Invoice_ID | PayAmt   |
      | lcPayment               | Y            | null         | lcInvoice           | 20596.32 |

    # The proforma flips to IsPaid=Y when its full payment completes — the regular
    # allocation-driven IsPaid update doesn't fire because proforma payments have no
    # C_AllocationLine rows; the C_Payment AFTER_COMPLETE interceptor sets the flag directly.
    And validate created invoices
      | Identifier | IsPaid |
      | lcInvoice  | Y      |

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | P      |
      | BL                | 48058.08 | null          | PR     |

    # AC #17 — invoiceOpenToDate proforma branch after payment completion:
    # The CO payment lands in the SUM(abs(PayAmt)) so OpenAmt = GrandTotal - GrandTotal = 0,
    # PaidAmt = GrandTotal. HasAllocations stays false (proforma never has C_AllocationLine rows).
    Then for invoice the following invoiceOpenToDate result is expected:
      | C_Invoice_ID | OpenAmt | PaidAmt  | GrandTotal | HasAllocations |
      | lcInvoice    | 0       | 20596.32 | 20596.32   | false          |


  Scenario: S2 - Proforma GrandTotal below planned LC DueAmt — DueAmt_Actual captures the actual amount
    # Procurement-worker variant: vendor sends a proforma for slightly less than the planned LC amount
    # (rounding, FX, partial coverage). The plan invariant DueAmt = order.GrandTotal × break% must hold;
    # only DueAmt_Actual reflects the actual proforma amount.
    # BL break stays PR (Pending) until a goods receipt is recorded — BillOfLadingDate is not yet known.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | lcProduct    | 1          |
    And the order identified by lcOrder is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | lcProduct    | 1 PCE       | 20500.00 |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    # DueAmt = plan (20596.32, unchanged); DueAmt_Actual = proforma.GrandTotal (20500.00).
    # BL break is PR (Pending) because BillOfLadingDate is not yet known (no goods receipt).
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20500.00      | WP     |
      | BL                | 48058.08 | null          | PR     |


  Scenario: S3 - Deallocation before payment rolls LC back to Pending (DueAmt_Actual + LC_Date cleared)
    # The procurement worker realises the wrong proforma was allocated and removes it before paying.
    # State walk: Pending → Awaiting_Pay (allocate) → Pending (deallocate).
    # BL break stays PR (Pending) throughout — BillOfLadingDate is not yet known (no goods receipt).

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | lcProduct    | 1          |
    And the order identified by lcOrder is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | lcProduct    | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | BL                | 48058.08 | null          | PR     |
    And validate the created orders
      | Identifier | LC_Date    |
      | lcOrder    | 2026-04-24 |

    And I deallocate proforma 'lcInvoice' from order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | null          | PR     |
      | BL                | 48058.08 | null          | PR     |
    And validate the created orders
      | Identifier | LC_Date |
      | lcOrder    | null    |


  Scenario: S4 - Payment reversal rolls LC back from Paid to Awaiting_Pay (DueAmt_Actual + LC_Date preserved)
    # The procurement worker reverses the proforma payment (e.g., wrong bank account). The LC step
    # rolls back to Awaiting_Pay because the allocation is still active — only the payment is gone.
    # State walk: Pending → Awaiting_Pay (allocate) → Paid (payment) → Awaiting_Pay (reversal).
    # BL break stays PR (Pending) throughout — BillOfLadingDate is not yet known (no goods receipt).

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | lcProduct    | 1          |
    And the order identified by lcOrder is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | lcProduct    | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | BL                | 48058.08 | null          | PR     |

    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySelection | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | Y       |
    And the pay selection identified by paySelection is completed
    Then "Create Payments" is invoked for pay selection paySelection

    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | lcInvoice    | 20596.32 | lcPayment    |

    # Proforma flips to IsPaid=Y when the full payment completes (C_Payment AFTER_COMPLETE
    # interceptor — proforma payments have no C_AllocationLine rows, so the regular
    # allocation-driven update doesn't fire).
    And validate created invoices
      | Identifier | IsPaid |
      | lcInvoice  | Y      |

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | P      |
      | BL                | 48058.08 | null          | PR     |

    # ── Reverse the proforma payment ──
    # MPayment.reverseCorrectIt() creates a counter-payment that mirrors the original — every
    # classification field (Proforma_Invoice_ID, IsPrepayment) is preserved; only the numeric
    # effect is negated (PayAmt = -GrandTotal). Both rows end at DocStatus='RE' linked by
    # Reversal_ID. The LC-step authority's `findCompletedOrClosedByProformaInvoiceId` filters
    # on DocStatus IN (CO,CL), so it sees no completed payment and rolls the LC step back to
    # Awaiting_Pay. DueAmt_Actual + LC_Date are preserved because the proforma allocation is
    # still active.
    And the payment identified by lcPayment is reversed with a reversal identified by lcPaymentReversal

    # Reversal symmetry — AC #14: every classification field of the original is preserved
    # on the reversal row (C_Invoice_ID stays null, Proforma_Invoice_ID stays set,
    # IsPrepayment stays Y); only PayAmt is negated; both end at DocStatus='RE'.
    Then validate payments
      | C_Payment_ID.Identifier | DocStatus | IsPrepayment | C_Invoice_ID | Proforma_Invoice_ID | PayAmt    |
      | lcPayment               | RE        | Y            | null         | lcInvoice           |  20596.32 |
      | lcPaymentReversal       | RE        | Y            | null         | lcInvoice           | -20596.32 |

    # Proforma rolls back to IsPaid=N once the only completed payment is reversed
    # (C_Payment AFTER_REVERSECORRECT interceptor — symmetric to AFTER_COMPLETE).
    And validate created invoices
      | Identifier | IsPaid |
      | lcInvoice  | N      |

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | BL                | 48058.08 | null          | PR     |

    # AC #17 — invoiceOpenToDate proforma branch after payment reversal:
    # The reversal payment ends at DocStatus='RE' which the SUM-based paid-detection excludes,
    # so the proforma reverts to unpaid: OpenAmt = GrandTotal, PaidAmt = 0.
    Then for invoice the following invoiceOpenToDate result is expected:
      | C_Invoice_ID | OpenAmt  | PaidAmt | GrandTotal | HasAllocations |
      | lcInvoice    | 20596.32 | 0       | 20596.32   | false          |

    And validate the created orders
      | Identifier | LC_Date    |
      | lcOrder    | 2026-04-24 |


  # ═══════════════════════════════════════════════════════════════════════════════════
  # S5 CUSTOMER-SPREADSHEET 2-LINE DECOMPOSITION
  # ═══════════════════════════════════════════════════════════════════════════════════
  #
  # Source: https://github.com/user-attachments/files/25867894/dt204.Multiple.Payments.xlsx
  # Ref:    ai-work/29369/REQUIREMENTS.md §1
  #
  # ── PO totals ──────────────────────────────────────────────────────────────────
  #   Tax rate:          19% German standard
  #   PO with-tax:       68,654.40 EUR  (customer spreadsheet, price-list item)
  #   PO net:            68,654.40 / 1.19 = 57,692.77 EUR
  #                      (exact: 57,692.773109...; banker's rounding → 57,692.77)
  #
  # ── Chosen qty/price decomposition ────────────────────────────────────────────
  #   Line A (under-delivery by 1):
  #     orderedQtyA  = 196  PCE
  #     actualQtyA   = 195  PCE  (= orderedQtyA − 1)
  #     priceA       = 137.0739 EUR/PCE  (net unit price, 4 decimal places)
  #
  #   Line B (over-delivery by 4):
  #     orderedQtyB  = 359  PCE
  #     actualQtyB   = 363  PCE  (= orderedQtyB + 4)
  #     priceB       =  85.8671 EUR/PCE  (net unit price, 4 decimal places)
  #
  # ── Verification (6 hand-calculations) ────────────────────────────────────────
  #   1) R1 with-tax  = actualQtyA × priceA × 1.19
  #                   = 195 × 137.0739 × 1.19
  #                   = 26,729.4105 × 1.19
  #                   = 31,808.00 EUR   (target 31,808.00; diff = 0.00)
  #
  #   2) R2 with-tax  = actualQtyB × priceB × 1.19
  #                   = 363 × 85.8671 × 1.19
  #                   = 31,179.7173 × 1.19
  #                   = 37,092.01 EUR   (target 37,092.00; diff = +0.01, within +-0.01)
  #
  #   3) PO with-tax  = (orderedQtyA × priceA + orderedQtyB × priceB) × 1.19
  #                   = (196 × 137.0739 + 359 × 85.8671) × 1.19
  #                   = (26,866.4844 + 30,826.2889) × 1.19
  #                   = 57,692.7733 × 1.19
  #                   = 68,654.40 EUR   (target 68,654.40; diff = 0.00)
  #
  #   4) Net over-delivery with-tax
  #                   = (actualQtyB - orderedQtyB) × priceB × 1.19
  #                     - (orderedQtyA - actualQtyA) × priceA × 1.19
  #                   = 4 × 85.8671 × 1.19 - 1 × 137.0739 × 1.19
  #                   = 408.9272 - 163.0179
  #                   = +245.61 EUR     (target +245.60; diff = +0.01, within +-0.01)
  #
  #   5) R1 + R2 - PO = 31,808.00 + 37,092.01 - 68,654.40 = +245.61 EUR
  #                     consistent with (4) above
  #
  #   6) PO net check = orderedQtyA × priceA + orderedQtyB × priceB
  #                   = 57,692.7733 EUR  approx 57,692.77 EUR
  #
  # ── What S5 demonstrates ───────────────────────────────────────────────────────
  #   Over-delivery scenario: PO with LC prepay 30% paid upfront (20,596.32 EUR);
  #   R1 (under-delivered) creates INV1 Partial with alloc 9,542.40 EUR;
  #   R2 (over-delivered) creates INV2 Final with alloc 11,053.92 EUR consuming the
  #   full remaining prepay, and the over-delivery remainder row is deleted.
  #
  # ── Search provenance ─────────────────────────────────────────────────────────
  #   Solution found by brute-force integer search over qtyA in [50,1000],
  #   qtyB in [50,1000].  Prices derived via:
  #     priceA = 31,808.00 / (1.19 x (qtyA - 1))  rounded to 4 decimal places
  #     priceB = 37,092.00 / (1.19 x (qtyB + 4))  rounded to 4 decimal places
  #   Selection criterion: smallest qtyA + qtyB satisfying PO with-tax = 68,654.40
  #   and all 4 targets within +-0.01 EUR.
  #   qtyA=196, qtyB=359 was chosen (PO diff = 0.00, R1 diff = 0.00).
  #
  # ── notes (for human review) ──────────────────────────────────────────────────
  #   Task brief stated PO net = 57,693.61 EUR; verified value is 57,692.77 EUR.
  #   The brief contained a transcription error; the with-tax total 68,654.40 is
  #   the authoritative figure and is satisfied exactly (diff = 0.00).
  #   R2 and over-delivery each land at +0.01 EUR vs target due to the 4-decimal
  #   price rounding of priceB; this is within the permitted +-0.01 tolerance.
  #   No adjustment to REQUIREMENTS.md is needed — all customer-stated with-tax
  #   totals (68,654.40 / 31,808.00 / 37,092.00 / +245.60) are preserved to +-0.01.
  #
  # ═══════════════════════════════════════════════════════════════════════════════════
  # --- S5 will be authored in B.3 below ---
