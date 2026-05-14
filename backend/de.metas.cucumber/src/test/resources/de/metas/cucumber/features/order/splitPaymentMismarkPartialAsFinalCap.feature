@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — mismarked Partial-as-Final cap (AC #12)
  # Domain: proves the MIN cap on the Partial allocation rule prevents over-allocation
  # when the user mismarks the FINAL invoice as Partial (IsPartialInvoice='Y').
  #
  # Scenario (reuses TC1's R1 + R2 numbers, over-delivery):
  #   - PO GrandTotal = 70,000 EUR (700 PCE @ 100 EUR/PCE, tax-inclusive).
  #   - LC 30 % = 21,000 EUR prepayment; Delivery 70 % = 49,000 EUR.
  #   - Iter-2 cycle (complete): proforma allocated + paid → LC step Paid; prepay.AvailableAmt = 21,000.
  #   - R1 = 400 PCE = 40,000 with-tax → INV1 (Partial, correctly marked).
  #     INV1 alloc = MIN(40,000 × 30 %, 21,000) = 12,000. Remaining prepay = 9,000.
  #   - R2 = 320 PCE = 32,000 with-tax → R1+R2 = 72,000 > 70,000 (over-delivery).
  #   - INV2 MISMARKED: IsPartialInvoice='Y' (should have been 'N' = Final).
  #     Partial rule with cap: alloc = MIN(R2.with_tax × 30 %, remaining_prepay)
  #                                   = MIN(32,000 × 30 %, 9,000)
  #                                   = MIN(9,600, 9,000)
  #                                   = 9,000 (capped — not 9,600).
  #     End-state Σ allocated = 12,000 + 9,000 = 21,000 = full prepay → no over-allocation.
  #
  # Key assertion (AC #12):
  #   The MIN cap on the Partial rule clamps the allocation at remaining_prepay (9,000),
  #   NOT the proportional 9,600. The flag is wrong but the cap saves the books.
  #
  # NOTE: This feature stays RED until the production-code change `Q-iter3-trigger-after-LC-paid`
  # is approved + implemented. See https://github.com/metasfresh/me03/issues/29369 for details.

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

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |
    And metasfresh contains C_BP_BankAccount
      | Identifier          | C_BPartner_ID | C_Currency_ID |
      | vendor_bank_account | vendor        | EUR           |

    And metasfresh contains C_PaymentTerm
      | Identifier   | OPT.NetDays |
      | pt_lc        |             |
      | pt_immediate |             |
      | pt_net90     | 90          |
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
  @Id:S29369_TC3
  Scenario: INV2 mismarked Partial: MIN cap clamps allocation at remaining_prepay (AC #12)

    # ── Order completed; 700 PCE @ 100 EUR = 70,000 EUR total ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 700        |
    And the order identified by lcOrder is completed

    # ── Iter-2: proforma + payment → LC Paid ──
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

    # ── R1: 400 PCE → R1.with_tax = 40,000 ──
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

    # ── INV1: correctly marked as Partial, matched to R1 ──
    # Wait for IC to be created after receipt, then generate invoice via the real pipeline with IsPartialInvoice=Y.
    # Domain invariant: 1 IC per OrderLine — the same IC is reused for both R1 and R2.
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | M_InOutLine_ID.Identifier | QtyToInvoice |
      | ic                                | lcOrderL1                 | r1_line1                  | 400          |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsPartialInvoice | QtyInvoiced |
      | ic                                | Y                | 400         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic                                | inv1                    |
    And validate created invoices
      | C_Invoice_ID.Identifier | IsPartialInvoice | DocStatus |
      | inv1                    | Y                | CO        |
    And update C_Invoice:
      | Identifier | OPT.C_PaymentTerm_ID |
      | inv1       | pt_net90             |

    # INV1 alloc = MIN(40,000 × 30 %, 21,000) = 12,000; prepay → 9,000
    Then validate C_AllocationLines for invoice inv1
      | Amount    |
      | -12000.00 |
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 9000.00 |

    # ── R2: 320 PCE → R2.with_tax = 32,000; over-delivery (R1+R2 = 72,000 > 70,000) ──
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

    # ── INV2: MISMARKED as Partial (IsPartialInvoice='Y' when it should be 'N' Final), matched to R2 ──
    # Wait for IC to bump (QtyDelivered 400→720; QtyInvoiced still 400 → QtyToInvoice=320),
    # then generate the second invoice via the real pipeline with IsPartialInvoice=Y.
    # Same IC identifier (1-IC-per-orderline) — putOrReplace is a no-op when the row resolves to the same record.
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | M_InOutLine_ID.Identifier | QtyToInvoice |
      | ic                                | lcOrderL1                 | r2_line1                  | 320          |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsPartialInvoice | QtyInvoiced |
      | ic                                | Y                | 720         |
    # The IC now has 2 invoices linked via M_InvoiceCandidate_InOutLine + C_InvoiceLine; the IC-based
    # finder is ambiguous (findFirst over both completed invoices). Disambiguate via QtyInvoiced=320
    # + C_OrderLine_ID on the invoice line — that uniquely identifies INV2's line.
    # (load C_Invoice: legacy step requires OPT.C_OrderLine_ID.Identifier — without the OPT. prefix
    # the orderline filter is silently dropped and QtyInvoiced=320 alone is not unique across the DB.)
    And load C_Invoice:
      | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier | QtyInvoiced | OPT.C_OrderLine_ID.Identifier | DocStatus |
      | inv2                    | inv2L1                      | 320         | lcOrderL1                     | CO        |
    And validate created invoices
      | C_Invoice_ID.Identifier | IsPartialInvoice | DocStatus |
      | inv2                    | Y                | CO        |
    And update C_Invoice:
      | Identifier | OPT.C_PaymentTerm_ID |
      | inv2       | pt_net90             |

    # AC #12 — the MIN cap clamps INV2 alloc at remaining_prepay (9,000), NOT 9,600 (= 32,000 × 30 %)
    Then validate C_AllocationLines for invoice inv2
      | Amount   |
      | -9000.00 |
    # End-state Σ = 12,000 + 9,000 = 21,000 = full prepay; no over-allocation
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 0.00    |

    # INV2.OpenAmt = 32,000 − 9,000 = 23,000 (same as the canonical TC1 case — the cap saved the books)
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv2       | 23000.00 |
