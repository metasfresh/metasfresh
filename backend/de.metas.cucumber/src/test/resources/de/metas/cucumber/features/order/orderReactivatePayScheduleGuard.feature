@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: PO reactivation guard — downstream-activity check
  # Guard blocks reactivation only when the pay schedule carries committed downstream
  # state: a goods-receipt link (M_InOut_ID) or a matched-invoice link (C_Invoice_ID).
  # An Awaiting_Pay line with no downstream must NOT block.
  # pt_od = LC 30% + OD 70% (OD resolves at completion); pt_bl = LC 30% + BL 70%;
  # pt_od_nolc = OD 25% + BL 75% — no LC break at all, so the OD row is itself the recompute target.
  # PO: 700 PCE × 100 EUR = 70,000 EUR (IsTaxIncluded=Y → GrandTotal = 70,000).

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

    # pt_od: LC 30% + OD 70% — OrderDate break resolves at completion, OD row immediately WP
    # pt_bl: LC 30% + BL 70% — BillOfLadingDate break resolves only on goods receipt
    # pt_od_nolc: OD 25% (+1 day) + BL 75% — TC8's term. NO LetterOfCredit break, so the OD row is
    # the recompute target itself (getSingleLCLine finds nothing and the prepaid fallback picks OD).
    # Exactly one prepaid (OD/LC) break, otherwise the multiple-advance-breaks guard trips.
    # pt_net5: the proforma term of TC4, TC5, TC6 and TC8 — a real 5-day offset so the proforma's
    # DueDate diverges from its DateInvoiced, making DueDate diagnostic of whether the recompute fired.
    And metasfresh contains C_PaymentTerm
      | Identifier   | OPT.NetDays |
      | pt_od        |             |
      | pt_bl        |             |
      | pt_od_nolc   |             |
      | pt_immediate |             |
      | pt_net5      | 5           |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier    | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | ptb_od_lc     | pt_od            | 30      | 0          | LC                | 10    |
      | ptb_od_od     | pt_od            | 70      | 0          | OD                | 20    |
      | ptb_bl_lc     | pt_bl            | 30      | 0          | LC                | 10    |
      | ptb_bl_bl     | pt_bl            | 70      | 0          | BL                | 20    |
      | ptb_odnolc_od | pt_od_nolc       | 25      | 1          | OD                | 10    |
      | ptb_odnolc_bl | pt_od_nolc       | 75      | 0          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_od      | Y         | Y       |
      | pt_bl      | Y         | Y       |
      | pt_od_nolc | Y         | Y       |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |


  @from:cucumber
  @Id:S30621_TC1
  Scenario: Reactivate allowed — Awaiting_Pay OD row with no downstream (regression guard)
    # A buyer completes a PO on a LC+OD term. The OD row is immediately Awaiting_Pay because
    # DateOrdered is always set at completion. No goods receipt, invoice, payment, or proforma exists.
    # The guard must allow reactivation — nothing downstream would be lost.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # OD row: Awaiting_Pay (DateOrdered known at completion); no M_InOut_ID, no C_Invoice_ID, no proforma
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | null          | null          | 9999-12-01 | PR     |
      | OD                | 49000.00 | null          | 2026-04-24    | 2026-04-24 | WP     |

    # Guard allows reactivation — OD Awaiting_Pay with no downstream does not block
    And the order identified by po is reactivated


  @from:cucumber
  @Id:S30621_TC2
  Scenario: Reactivate allowed — proforma allocation exists, nothing committed downstream
    # A buyer allocates a purchase proforma invoice to the order (LC step → Awaiting_Pay).
    # The allocation link and its prepayment both survive reactivation, so nothing downstream
    # would be lost — the guard must allow reactivation without requiring a de-allocation first.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Allocate a proforma invoice to the order (LC row → Awaiting_Pay, proforma service records the link)
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | proforma   | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | proformaL1 | proforma     | product      | 1 PCE       | 21000.00 |
    And the invoice identified by proforma is completed
    And I allocate proforma 'proforma' to order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 21000.00 | 21000.00      | WP     |

    # Guard allows reactivation — proforma allocation with no goods-receipt/invoice link does not block
    And the order identified by po is reactivated


  @from:cucumber
  @Id:S30621_TC3
  Scenario: Reactivate blocked — goods receipt links a pay-schedule line
    # A buyer completes a goods receipt against a LC+BL order. The receipt creates a BL
    # delivery sub-row carrying M_InOut_ID — committed downstream state that blocks reactivation.
    # A matched financial invoice is also completed (M_MatchInv created); on an order with no
    # proforma prepayment, split-payment tracking is dormant, so the invoice does not project
    # onto the sub-row (C_Invoice_ID stays null, status Pending). The receipt link alone blocks.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_bl            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Wait for WP processor to create M_ReceiptSchedule (async after order completion)
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs1                  | po         | poL1           | vendor        | vendor_loc             | product      | 700        | wh             |

    # Goods receipt: 700 PCE in 1 TU → receipt r1 with line r1_line1
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu1     | poL1           | 101                     | 700         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | poL1           | hu1     | r1         |
    And load M_InOut:
      | QtyEntered | M_InOutLine_ID | M_InOut_ID | DocStatus | C_OrderLine_ID |
      | 700        | r1_line1       | r1         | CO        | poL1           |

    # BL sub-row now has M_InOut_ID=r1. The matched financial invoice does NOT set C_Invoice_ID on the
    # pay-schedule line: C_Invoice.onComplete returns early when the order has no proforma allocation, so
    # split-payment delivery-step tracking is dormant and the invoice does not project onto the sub-row.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID | M_InOutLine_ID |
      | inv1L1     | inv1         | product      | 700 PCE     | 100.00 | poL1           | r1_line1       |
    And the invoice identified by inv1 is completed

    # BL sub-row carries the receipt link (M_InOut_ID=r1) — committed downstream state — while
    # C_Invoice_ID stays null (invoice does not project onto the sub-row without a proforma).
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | M_InOut_ID | C_Invoice_ID | DueAmt   |
      | BL                | r1         | null         | 49000.00 |

    # Guard blocks — pay-schedule line carries a downstream goods-receipt link
    And the order identified by po cannot be reactivated


  @from:cucumber
  @Id:S30621_TC4
  Scenario: Reactivate then re-complete preserves the paid-proforma pay-schedule state
    # A buyer completes a PO, allocates a proforma invoice, and pays it in full (payment
    # carries Proforma_Invoice_ID) — the LC row is Paid with the proforma's actual amount
    # and dates. Reactivating the order and re-completing it must restore that same Paid
    # state: the proforma allocation and its completed prepayment both survive reactivation,
    # so re-complete must re-derive the LC row from them, not just from the payment-term breaks.

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Allocate a proforma invoice to the order (LC row -> Awaiting_Pay). pt_net5 gives the
    # proforma a real 5-day offset (DueDate = DateInvoiced + 5), unlike pt_immediate.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | proforma   | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_net5          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | proformaL1 | proforma     | product      | 1 PCE       | 21000.00 |
    And the invoice identified by proforma is completed
    And I allocate proforma 'proforma' to order 'po'

    # Pay the proforma in full (Proforma_Invoice_ID -> IsPrepayment=Y) — marks the LC row Paid
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt       | IsReceipt | C_BP_BankAccount_ID | Proforma_Invoice_ID |
      | payment    | vendor        | 21000.00 EUR | false     | org_EUR_account     | proforma            |
    And the payment identified by payment is completed

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-29 | P      |

    # Reactivate (allowed - proforma allocation no longer blocks) and re-complete the order
    And the order identified by po is reactivated
    And the order identified by po is completed

    # Load-bearing columns: Status, DueAmt_Actual and DueDate actually prove the LC recompute
    # fired on re-complete (DueDate = proforma.DueDate, 5 days past DateInvoiced — the naive
    # create-path that only reads LC_Date + pt_od's zero OffsetDays cannot reproduce it).
    # ReferenceDate and LC_Date coincide with DateInvoiced either way, so they document the
    # intended state rather than prove the recompute ran.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-29 | P      |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |


  @from:cucumber
  @Id:S30621_TC5
  Scenario: Reactivate then re-complete preserves the paid-proforma pay-schedule state on a B/L term
    # Same reactivate -> re-complete round trip as TC4, but on pt_bl (LC 30% + BL 70%) instead of
    # pt_od. The BL break resolves only on goods receipt, so — with no receipt in this scenario —
    # its row stays Pending at the infinite-future sentinel throughout. Proves the LC recompute
    # restores the paid-proforma state on a B/L term too, and that the untouched BL row survives
    # the round trip unchanged.

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_bl            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Allocate a proforma invoice to the order (LC row -> Awaiting_Pay). pt_net5 gives the
    # proforma a real 5-day offset (DueDate = DateInvoiced + 5), unlike pt_immediate.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | proforma   | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_net5          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | proformaL1 | proforma     | product      | 1 PCE       | 21000.00 |
    And the invoice identified by proforma is completed
    And I allocate proforma 'proforma' to order 'po'

    # Pay the proforma in full (Proforma_Invoice_ID -> IsPrepayment=Y) — marks the LC row Paid
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt       | IsReceipt | C_BP_BankAccount_ID | Proforma_Invoice_ID |
      | payment    | vendor        | 21000.00 EUR | false     | org_EUR_account     | proforma            |
    And the payment identified by payment is completed

    # LC: Paid from the proforma. BL: still Pending — no goods receipt exists in this scenario.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-29 | P      |
      | BL                | 49000.00 | null          | null          | 9999-12-01 | PR     |

    # Reactivate (allowed - proforma allocation no longer blocks) and re-complete the order
    And the order identified by po is reactivated
    And the order identified by po is completed

    # Load-bearing columns: Status, DueAmt_Actual and DueDate on the LC row actually prove the LC
    # recompute fired on re-complete (DueDate = proforma.DueDate, 5 days past DateInvoiced — the
    # naive create-path that only reads LC_Date + pt_bl's zero OffsetDays cannot reproduce it). The
    # BL row is untouched by the recompute (it targets only the LC/prepaid line) and must come back
    # exactly as it went in — still Pending at the infinite-future sentinel.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-29 | P      |
      | BL                | 49000.00 | null          | null          | 9999-12-01 | PR     |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |


  @from:cucumber
  @Id:S30621_TC6
  Scenario: Reactivate then re-complete preserves the awaiting-pay proforma pay-schedule state
    # A buyer completes a PO and allocates a proforma invoice but does not pay it — the LC row is
    # Awaiting_Pay (not Paid), carrying the proforma's own dates and amount but no completed
    # prepayment. Reactivating and re-completing must restore that same Awaiting_Pay state,
    # exercising the recompute's awaitingPayment() branch (proforma present, no completed
    # prepayment) rather than paid().

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Allocate a proforma invoice to the order (LC row -> Awaiting_Pay). pt_net5 gives the
    # proforma a real 5-day offset (DueDate = DateInvoiced + 5), unlike pt_immediate.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | proforma   | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_net5          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | proformaL1 | proforma     | product      | 1 PCE       | 21000.00 |
    And the invoice identified by proforma is completed
    And I allocate proforma 'proforma' to order 'po'

    # No payment is made — LC row stays Awaiting_Pay, projecting the proforma's own dates/amount.
    # The OD sibling row is not the recompute target on this LC-carrying term, so it keeps the
    # plain payment-term-break state it got at completion.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-29 | WP     |
      | OD                | 49000.00 | null          | 2026-04-24    | 2026-04-24 | WP     |

    # Reactivate (allowed - proforma allocation with no downstream link does not block) and
    # re-complete the order
    And the order identified by po is reactivated
    And the order identified by po is completed

    # Load-bearing columns: Status, DueAmt_Actual and DueDate actually prove the LC recompute
    # fired on re-complete via the awaitingPayment() branch (DueDate = proforma.DueDate, 5 days
    # past DateInvoiced — the naive create-path that only reads LC_Date + pt_od's zero OffsetDays
    # cannot reproduce it). The OD sibling row must come back exactly as it went in.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-29 | WP     |
      | OD                | 49000.00 | null          | 2026-04-24    | 2026-04-24 | WP     |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |


  @from:cucumber
  @Id:S30621_TC7
  Scenario: Reactivate then re-complete leaves an ordinary order's pay schedule untouched
    # Regression guard: an order with no proforma at all must come back through reactivate ->
    # re-complete exactly as it went in. Proves the AFTER_COMPLETE recompute call does not disturb
    # an ordinary order that never had a proforma allocation.
    #
    # NOTE this term (pt_od) carries an LC break, which makes it BLIND to a recompute that resets
    # the wrong row: the LC row is the recompute target here, and it is already Pending with a null
    # LC_Date, so even a reset would be invisible. TC8 covers the no-LC term where the OD row IS
    # the target and a reset is observable — keep both.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # OD row: Awaiting_Pay (DateOrdered known at completion); LC row: Pending (no proforma at all)
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | null          | null          | 9999-12-01 | PR     |
      | OD                | 49000.00 | null          | 2026-04-24    | 2026-04-24 | WP     |

    # Reactivate and re-complete. No proforma allocation exists, so the AFTER_COMPLETE recompute
    # returns without touching the schedule at all.
    And the order identified by po is reactivated
    And the order identified by po is completed

    # Unchanged: the re-created schedule matches the payment-term breaks exactly as before
    # reactivation — proving the AFTER_COMPLETE recompute call does not disturb an ordinary order.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | null          | null          | 9999-12-01 | PR     |
      | OD                | 49000.00 | null          | 2026-04-24    | 2026-04-24 | WP     |


  @from:cucumber
  @Id:S30621_TC8
  Scenario: Reactivate then re-complete preserves the paid-proforma state on the order-date step of a no-LC term
    # The customer's real configuration: a purchase term with NO Letter-of-Credit break at all
    # (pt_od_nolc = OD 25% advance + BL 75% on goods receipt). The procurement worker pays the 25%
    # advance up front through a purchase proforma invoice, so the ORDER-DATE step — not an LC step —
    # is what the proforma drives, and it is what the recompute must target and restore.
    #
    # This is the only scenario in this file where the OD row is the recompute target: every other
    # term here carries an LC break, so getSingleLCLine() always wins and the OD row is never
    # touched. Without this scenario a recompute that wrongly resets the OD row stays invisible.

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od_nolc       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Completion alone: the OD row resolves off DateOrdered (+1 day offset) and is immediately
    # Awaiting_Pay; the BL row stays Pending at the infinite-future sentinel (no goods receipt).
    # No proforma is allocated yet, so nothing may overwrite this state.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | OD                | 17500.00 | null          | 2026-04-24    | 2026-04-25 | WP     |
      | BL                | 52500.00 | null          | null          | 9999-12-01 | PR     |

    # Vendor sends the proforma for the 25% advance. pt_net5 gives it a real 5-day offset
    # (DueDate = DateInvoiced + 5), so DueDate is diagnostic of whether the recompute fired.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | proforma   | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_net5          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | proformaL1 | proforma     | product      | 1 PCE       | 17500.00 |
    And the invoice identified by proforma is completed
    And I allocate proforma 'proforma' to order 'po'

    # Pay the proforma in full (Proforma_Invoice_ID -> IsPrepayment=Y) — marks the OD row Paid
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt       | IsReceipt | C_BP_BankAccount_ID | Proforma_Invoice_ID |
      | payment    | vendor        | 17500.00 EUR | false     | org_EUR_account     | proforma            |
    And the payment identified by payment is completed

    # OD: Paid, now carrying the proforma's own amount and dates instead of the break-derived ones.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | OD                | 17500.00 | 17500.00      | 2026-04-24    | 2026-04-29 | P      |
      | BL                | 52500.00 | null          | null          | 9999-12-01 | PR     |

    # Reactivate (allowed - the paid proforma carries no goods-receipt/invoice link) and re-complete
    And the order identified by po is reactivated
    And the order identified by po is completed

    # Load-bearing columns on the OD row: Status, DueAmt_Actual and DueDate prove the recompute
    # re-derived the order-date step from the surviving allocation + prepayment (DueDate =
    # proforma.DueDate, 2026-04-29 — the create-path would put back the break-derived 2026-04-25).
    # The BL row is not the recompute target and must come back exactly as it went in.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | OD                | 17500.00 | 17500.00      | 2026-04-24    | 2026-04-29 | P      |
      | BL                | 52500.00 | null          | null          | 9999-12-01 | PR     |
    # No LetterOfCredit break on this term, so nothing may stamp C_Order.LC_Date.
    And validate the created orders
      | Identifier | LC_Date |
      | po         | null    |
