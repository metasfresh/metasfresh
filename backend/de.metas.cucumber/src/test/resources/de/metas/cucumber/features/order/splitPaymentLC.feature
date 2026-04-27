@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00126_Prepayment_Order_Management
@ghActions:run_on_executor4
Feature: Split-payment LC lifecycle — proforma invoice drives the LC pay-schedule step
  # Domain: a purchase order with a complex payment term that has two breaks:
  #   - 30 % LC  (LetterOfCreditDate) — paid up-front via a proforma invoice
  #   - 70 % OD  (OrderDate)          — paid later against the delivery invoice
  #
  # The procurement worker:
  #   1. Issues a purchase order under those terms.
  #   2. Receives a proforma invoice from the vendor for the LC portion.
  #   3. Allocates the proforma to the order (vendor / currency / single LC break — guards
  #      validate this) — the LC pay-schedule step transitions to Awaiting_Pay.
  #   4. Pays the proforma via the standard pay-selection flow — the payment is auto-tagged
  #      Proforma_Invoice_ID + IsPrepayment, and the LC step transitions to Paid.
  #   5. May reverse the payment — the LC step rolls back to Awaiting_Pay (DueAmt_Actual and
  #      LC_Date preserved); or deallocate the proforma — the LC step rolls back to Pending
  #      (DueAmt_Actual and LC_Date cleared).
  #
  # Pay-selection lines reference invoices only (regular + proforma) — no order-side rows.
  # Proforma payments are full payments only (abs(PayAmt) = proforma.GrandTotal); partial
  # payments are blocked at C_Payment BEFORE_PREPARE.

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
      | plv_purchase           | product      | 68654.40 | PCE      |

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
    #   pt_lc        — order-level term: 30 % LC + 70 % OD (drives the LC pay-schedule rows)
    #   pt_immediate — proforma-level term (NetDays=0) so the proforma's DueDate = DateInvoiced
    #                  (the procurement worker pays on the proforma's due date, OnlyDue=Y picks it up)
    And metasfresh contains C_PaymentTerm
      | Identifier   |
      | pt_lc        |
      | pt_immediate |
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


  Scenario: S1 - Happy path — PO → proforma → allocate → pay-selection → payment → LC=Paid (asserts LC step at every transition)
    # The procurement worker pays the LC portion of a complex-payment-term PO via a proforma invoice.
    # LC step state walk: Pending → Awaiting_Pay (allocate) → Paid (payment).

    # ── Order completed → LC step Pending; OD step Awaiting_Pay (its OffsetDays=0 from OrderDate) ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 1          |
    And the order identified by lcOrder is completed

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | null          | PR     |
      | OD                | 48058.08 | null          | WP     |

    # ── Proforma created and completed (GrandTotal = LC plan = 20596.32) — no pay-schedule change ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | null          | PR     |
      | OD                | 48058.08 | null          | WP     |

    # AC #17 — invoiceOpenToDate proforma branch on an unpaid proforma:
    # OpenAmt = GrandTotal, PaidAmt = 0, HasAllocations = false (proformas have no C_AllocationLine rows).
    Then for invoice the following invoiceOpenToDate result is expected:
      | C_Invoice_ID            | OpenAmt  | PaidAmt | GrandTotal | HasAllocations |
      | lcInvoice               | 20596.32 | 0       | 20596.32   | false          |

    # ── Allocate proforma → LC step Awaiting_Pay; LC_Date stamped ──
    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | OD                | 48058.08 | null          | WP     |
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

    Then validate payments
      | C_Payment_ID.Identifier | IsPrepayment | Proforma_Invoice_ID | PayAmt   |
      | lcPayment               | Y            | lcInvoice           | 20596.32 |

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | P      |
      | OD                | 48058.08 | null          | WP     |

    # AC #17 — invoiceOpenToDate proforma branch after payment completion:
    # The CO payment lands in the SUM(abs(PayAmt)) so OpenAmt = GrandTotal - GrandTotal = 0,
    # PaidAmt = GrandTotal. HasAllocations stays false (proforma never has C_AllocationLine rows).
    Then for invoice the following invoiceOpenToDate result is expected:
      | C_Invoice_ID            | OpenAmt | PaidAmt  | GrandTotal | HasAllocations |
      | lcInvoice               | 0       | 20596.32 | 20596.32   | false          |

  Scenario: S2 - Proforma GrandTotal below planned LC DueAmt — DueAmt_Actual captures the actual amount
    # Procurement-worker variant: vendor sends a proforma for slightly less than the planned LC amount
    # (rounding, FX, partial coverage). The plan invariant DueAmt = order.GrandTotal × break% must hold;
    # only DueAmt_Actual reflects the actual proforma amount.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 1          |
    And the order identified by lcOrder is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 20500.00 |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    # DueAmt = plan (20596.32, unchanged); DueAmt_Actual = proforma.GrandTotal (20500.00).
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20500.00      | WP     |
      | OD                | 48058.08 | null          | WP     |


  Scenario: S3 - Deallocation before payment rolls LC back to Pending (DueAmt_Actual + LC_Date cleared)
    # The procurement worker realises the wrong proforma was allocated and removes it before paying.
    # State walk: Pending → Awaiting_Pay (allocate) → Pending (deallocate).

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 1          |
    And the order identified by lcOrder is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | OD                | 48058.08 | null          | WP     |
    And validate the created orders
      | Identifier | LC_Date    |
      | lcOrder    | 2026-04-24 |

    And I deallocate proforma 'lcInvoice' from order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | null          | PR     |
      | OD                | 48058.08 | null          | WP     |
    And validate the created orders
      | Identifier | LC_Date |
      | lcOrder    | null    |


  Scenario: S4 - Payment reversal rolls LC back from Paid to Awaiting_Pay (DueAmt_Actual + LC_Date preserved)
    # The procurement worker reverses the proforma payment (e.g., wrong bank account). The LC step
    # rolls back to Awaiting_Pay because the allocation is still active — only the payment is gone.
    # State walk: Pending → Awaiting_Pay (allocate) → Paid (payment) → Awaiting_Pay (reversal).

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 1          |
    And the order identified by lcOrder is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | OD                | 48058.08 | null          | WP     |

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

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | P      |
      | OD                | 48058.08 | null          | WP     |

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
    # on the reversal row; only PayAmt is negated; both end at DocStatus='RE'.
    Then validate payments
      | C_Payment_ID.Identifier | DocStatus | IsPrepayment | Proforma_Invoice_ID | PayAmt    |
      | lcPayment               | RE        | Y            | lcInvoice           |  20596.32 |
      | lcPaymentReversal       | RE        | Y            | lcInvoice           | -20596.32 |

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 20596.32 | 20596.32      | WP     |
      | OD                | 48058.08 | null          | WP     |

    # AC #17 — invoiceOpenToDate proforma branch after payment reversal:
    # The reversal payment ends at DocStatus='RE' which the SUM-based paid-detection excludes,
    # so the proforma reverts to unpaid: OpenAmt = GrandTotal, PaidAmt = 0.
    Then for invoice the following invoiceOpenToDate result is expected:
      | C_Invoice_ID            | OpenAmt  | PaidAmt | GrandTotal | HasAllocations |
      | lcInvoice               | 20596.32 | 0       | 20596.32   | false          |

    And validate the created orders
      | Identifier | LC_Date    |
      | lcOrder    | 2026-04-24 |


  Scenario: S5 - Proforma + regular invoice for the same vendor — both visible in pay-selection (invoice-only rows)
    # The vendor sends both a proforma (advance) and a regular invoice (against an earlier delivery).
    # Pay-selection picks up both as invoice-type lines. No order-side rows appear.

    # Per-line prices override the price-list default (Background sets product to 68654.40); each
    # invoice has GrandTotal = 20596.32 to keep the comparison simple.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 20596.32 |
    And the invoice identified by lcInvoice is completed

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID | PaymentRule |
      | regularInvoice | vendor        | 2026-04-24   | false   | EUR           | pt_immediate     | P           |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID   | M_Product_ID | QtyInvoiced | Price    |
      | regularInvoiceL1 | regularInvoice | product      | 1 PCE       | 20596.32 |
    And the invoice identified by regularInvoice is completed

    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySelection | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | Y       |

    Then the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID   | OpenAmt  |
      | lcInvoice      | 20596.32 |
      | regularInvoice | 20596.32 |
