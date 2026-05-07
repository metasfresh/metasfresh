@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — reversal cascade (AC #16/#17/#18/#25)
  # Domain: covers the four reversal cascades documented in §3.6 of the iter-3 requirements.
  # Each scenario starts from TC1's step-5 state (INV1 Partial + INV2 Final both completed,
  # prepay drained to 0) and reverses one element to assert the cascade behaviour.
  #
  # Canonical numbers (TC1):
  #   PO 70,000 EUR; LC 30 % = 21,000 prepay; R1 = 40,000 with-tax; R2 = 32,000 with-tax (over-delivery).
  #   INV1 Partial alloc = 12,000 → prepay 9,000 → INV2 Final alloc = 9,000 → prepay 0.
  #   At step 5: INV1.OpenAmt = 28,000; INV2.OpenAmt = 23,000.
  #
  # Scenarios in this file:
  #   TC5a — reverse INV1 only (recommended sequence first step). AC #16.
  #          Cascade: iter-3 alloc auto-reverses; R1 sub-row C_Invoice_ID cleared; Status → Pending.
  #          Prepay restored from 0 to 12,000. INV2 alloc unchanged (still 9,000 against INV2).
  #
  #   TC5b — reverse R1 after first reversing INV1 (recommended sequence). AC #17.
  #          Cascade: R1 sub-row deleted from schedule; remainder row recomputes.
  #
  #   TC5c — reverse the prepayment payment (after TC1 step 5). AC #18.
  #          Cascade: ALL iter-3 C_AllocationHdr rows auto-reverse via MPayment.deAllocate().
  #          LC step Paid → Awaiting_Pay (iter-2 contract).
  #          Delivery sub-rows unchanged (still tied to invoices).
  #          Both INV1.OpenAmt and INV2.OpenAmt grow back to full receipt-with-tax.
  #
  #   TC5d — reverse INV1 ONLY after INV2 (Final) has completed (stranding case). AC #25.
  #          Cascade: iter-3 alloc for INV1 auto-reverses; INV2's alloc untouched.
  #          Prepay.AvailableAmt = 12,000 (stranded — sits available until user reverses
  #          INV2 too or a future partial invoice claims it).
  #
  # NOTE: All scenarios stay RED until the production-code change `Q-iter3-trigger-after-LC-paid`
  # is approved + implemented. See https://github.com/metasfresh/me03/issues/29369.

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
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_purchase           | product      | 100.00   | PCE      | TaxFree          |

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

    # ── TC1 step-5 state setup (shared by every scenario in this file) ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 700        |
    And the order identified by lcOrder is completed

    # Iter-2: proforma + payment → LC Paid; prepay = 21,000
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 21000.00 |
    And the invoice identified by lcInvoice is completed
    And I allocate proforma 'lcInvoice' to order 'lcOrder'
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
      | lcInvoice    | 21000.00 | lcPayment    |

    # R1 = 40,000 with-tax (400 PCE × 100 EUR, TaxFree, IsTaxIncluded=Y).
    # Wait for WP processor to create M_ReceiptSchedule for the order line (async after order completion).
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs1                  | lcOrder    | lcOrderL1      | vendor        | vendor_loc             | product      | 700        | wh             |
    # Canonical HU receipt: 400 PCE in 1 TU → receipt r1 with line r1_line1.
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu1     | lcOrderL1      | 101                     | 400         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | lcOrderL1      | hu1     | r1         |
    And load M_InOut:
      | QtyEntered | M_InOutLine_ID | M_InOut_ID | DocStatus | C_OrderLine_ID |
      | 400        | r1_line1       | r1         | CO        | lcOrderL1      |

    # INV1 Partial → alloc 12,000; prepay = 9,000.
    # M_InOutLine_ID FK drives auto M_MatchInv on MInvoice.completeIt().
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | true             |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID | M_InOutLine_ID |
      | inv1L1     | inv1         | product      | 400 PCE     | 100.00 | lcOrderL1      | r1_line1       |
    And the invoice identified by inv1 is completed

    # R2 = 32,000 with-tax (320 PCE × 100 EUR, over-delivery).
    # Canonical HU receipt: 320 PCE in 1 TU → receipt r2 with line r2_line1.
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu2     | lcOrderL1      | 101                     | 320         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | lcOrderL1      | hu2     | r2         |
    And load M_InOut:
      | QtyEntered | M_InOutLine_ID | M_InOut_ID | DocStatus | C_OrderLine_ID |
      | 320        | r2_line1       | r2         | CO        | lcOrderL1      |

    # INV2 Final → alloc 9,000; prepay = 0.
    # M_InOutLine_ID FK drives auto M_MatchInv on MInvoice.completeIt().
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv2       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | false            |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID | M_InOutLine_ID |
      | inv2L1     | inv2         | product      | 320 PCE     | 100.00 | lcOrderL1      | r2_line1       |
    And the invoice identified by inv2 is completed

    # Sanity: TC1 step-5 state baseline
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 0.00    |
    And validate C_AllocationLines for invoice inv1
      | Amount    |
      | -12000.00 |
    And validate C_AllocationLines for invoice inv2
      | Amount   |
      | -9000.00 |
    And validate created invoices
      | Identifier | OpenAmt  |
      | inv1       | 28000.00 |
      | inv2       | 23000.00 |


  @from:cucumber
  @Id:S29369_TC5a
  Scenario: Reverse INV1 (Partial): alloc auto-reverses; R1 sub-row Status → Pending (AC #16)

    # ── Reverse INV1 only ──
    And the invoice identified by inv1 is reversed

    # AC #16 — iter-3 alloc auto-reversed; INV1.OpenAmt=0 (reversed invoice FULLY_PAID by design)
    # metasfresh's MInvoice.reverseCorrectIt() explicitly sets OpenAmt=0 + IsPaid=Y on the original invoice
    # Expected allocation lines after reversal (all active, ordered by C_AllocationLine_ID):
    #   1. Original payment→inv1 line: Amount=-12000 (in original alloc hdr, DocStatus=RE)
    #   2. Counter-line from reversal hdr: Amount=+12000 (DocStatus=RE; nets #1 to zero)
    #   3. Invoice-to-invoice alloc inv1 side: Amount=-40000 (DocStatus=CO; inv1 GrandTotal negated for AP)
    # Lines #1+#2 net to zero (payment de-allocated). Line #3 is netted by line #4 in the same hdr (inv1_reversal side).
    And validate C_AllocationLines for invoice inv1
      | Amount     |
      | -12000.00  |
      | 12000.00   |
      | -40000.00  |
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv1       | 0.00     |

    # prepay restored: was 0, INV1 alloc 12,000 reversed → prepay = 12,000
    # INV2 alloc unchanged → 9,000 still consumed
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt  |
      | lcPayment               | 12000.00 |

    # AC #16 — R1 sub-row: C_Invoice_ID cleared, Status → Pending (PR); R2 sub-row unchanged
    # LC row stays Paid; total pay schedule lines = LC + r1 + r2 = 3
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | Status | C_Invoice_ID |
      | LC                | null       | P      | null         |
      | OD                | r1         | PR     | null         |
      | OD                | r2         | WP     | inv2         |


  @from:cucumber
  @Id:S29369_TC5b
  Scenario: Reverse R1 after first reversing INV1 (recommended sequence): R1 sub-row dropped (AC #17)

    # ── Step 1: reverse INV1 (per recommended sequence: invoice first, then receipt) ──
    And the invoice identified by inv1 is reversed
    # Same mirror-pair shape as TC5a: 3 active lines for inv1 after reversal
    And validate C_AllocationLines for invoice inv1
      | Amount     |
      | -12000.00  |
      | 12000.00   |
      | -40000.00  |
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt  |
      | lcPayment               | 12000.00 |

    # ── Step 2: reverse R1 ──
    And the material receipt identified by r1 is reversed

    # AC #17 — R1 sub-row deleted from schedule; remainder row recomputes.
    # After: 1 sub-row for R2 (still completed) + 1 remainder row.
    # R2 still 32,000 with-tax → remainder BaseAmt = 70,000 − 32,000 = 38,000; DueAmt = 38,000 × 70 % = 26,600.
    # LC row stays Paid; total pay schedule lines = LC + r2 + null(remainder) = 3
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | BaseAmt  | DueAmt   | Status | C_Invoice_ID |
      | LC                | null       | null     | null     | P      | null         |
      | OD                | r2         | 32000.00 | 22400.00 | WP     | inv2         |
      | OD                | null       | 38000.00 | 26600.00 | PR     | null         |


  @from:cucumber
  @Id:S29369_TC5c
  Scenario: Reverse the prepayment payment: cascade reverses ALL allocs; LC → Awaiting_Pay (AC #18)

    # ── Reverse the iter-2 prepayment payment ──
    And the payment identified by lcPayment is reversed

    # AC #18 — all iter-3 C_AllocationHdr auto-reverse via MPayment.deAllocate()
    # After reversing the payment, each invoice's allocation is reversed (reverseCorrectIt0 path):
    #   inv1: original line Amount=-12000 + counter-line Amount=+12000 (net zero, both DocStatus=RE hdrs)
    #   inv2: original line Amount=-9000  + counter-line Amount=+9000  (net zero, both DocStatus=RE hdrs)
    # No new invoice-to-invoice alloc (that is only created by MInvoice.reverseCorrectIt, not MPayment).
    And validate C_AllocationLines for invoice inv1
      | Amount     |
      | -12000.00  |
      | 12000.00   |
    And validate C_AllocationLines for invoice inv2
      | Amount    |
      | -9000.00  |
      | 9000.00   |

    # AC #18 (G4/G5 fold-in 2026-04-28) — both invoices' OpenAmt grow back to full receipt-with-tax
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv1       | 40000.00 |
      | inv2       | 32000.00 |

    # AC #18 — LC step Paid → Awaiting_Pay (iter-2 contract).
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | Status | IsPaid |
      | LC                | WP     | N      |

    # Delivery sub-rows: still 2 rows (R1 + R2), still pointing at the invoices, no remainder (over-delivery preserved)
    # LC row reverted to WP; total pay schedule lines = LC + r1 + r2 = 3
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | C_Invoice_ID |
      | LC                | null       | null         |
      | OD                | r1         | inv1         |
      | OD                | r2         | inv2         |


  @from:cucumber
  @Id:S29369_TC5d
  Scenario: Reverse INV1 (Partial) AFTER INV2 (Final) has completed: stranded prepay (AC #25)

    # ── Reverse INV1 only (INV2 stays completed) ──
    And the invoice identified by inv1 is reversed

    # AC #25 — INV1's iter-3 alloc auto-reversed; INV1.OpenAmt=0 (reversed invoice FULLY_PAID by design)
    # metasfresh's MInvoice.reverseCorrectIt() explicitly sets OpenAmt=0 + IsPaid=Y on the original invoice
    # Same mirror-pair shape as TC5a: 3 active lines for inv1 after reversal
    And validate C_AllocationLines for invoice inv1
      | Amount     |
      | -12000.00  |
      | 12000.00   |
      | -40000.00  |
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv1       | 0.00     |
      | inv2       | 23000.00 |

    # AC #25 — INV2's alloc unchanged (still 9,000 against INV2)
    And validate C_AllocationLines for invoice inv2
      | Amount   |
      | -9000.00 |

    # AC #25 — stranded amount: prepay.AvailableAmt = 12,000 (the freed INV1 alloc)
    # Iter-3 does NOT auto-re-allocate; the 12,000 sits available until either INV2 is also
    # reversed or a new partial invoice claims it.
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt  |
      | lcPayment               | 12000.00 |

    # R1 sub-row: C_Invoice_ID cleared; R2 sub-row unchanged (still tied to INV2)
    # LC row stays Paid; total pay schedule lines = LC + r1 + r2 = 3
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | Status | C_Invoice_ID |
      | LC                | null       | P      | null         |
      | OD                | r1         | PR     | null         |
      | OD                | r2         | WP     | inv2         |
