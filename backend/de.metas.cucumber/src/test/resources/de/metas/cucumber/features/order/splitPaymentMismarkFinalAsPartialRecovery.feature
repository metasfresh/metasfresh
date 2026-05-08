@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — mismarked Final-as-Partial + correction by reverse-and-reissue (AC #13)
  # Domain: proves the documented correction path when the user mismarks INV1 as Final
  # (IsPartialInvoice='N') instead of Partial. The Final rule consumes the full prepay,
  # leaving INV1.OpenAmt too low. The customer detects the mistake, reverses INV1, and
  # reissues it correctly marked as Partial — at which point the alloc converges back
  # to the canonical TC1 step-3 state.
  #
  # Sequence:
  #   - PO 70,000 EUR; LC 30 % = 21,000 prepay; iter-2 cycle complete; prepay.AvailableAmt = 21,000.
  #   - R1 = 400 PCE = 40,000 with-tax.
  #   - INV1 MISMARKED as Final (IsPartialInvoice='N').
  #     Final rule: alloc = remaining_prepay = 21,000 (full prepay consumed at INV1).
  #     INV1.OpenAmt = 40,000 − 21,000 = 19,000 (too low).
  #     prepay.AvailableAmt = 0.
  #   - User detects mistake → reverses INV1.
  #     Auto-reverse cascade: iter-3 alloc reverses; prepay.AvailableAmt restored to 21,000.
  #   - User reissues INV1' with IsPartialInvoice='Y' (Partial), matched to R1, completes.
  #     Partial rule: alloc = MIN(40,000 × 30 %, 21,000) = 12,000.
  #     INV1'.OpenAmt = 28,000; prepay.AvailableAmt = 9,000.
  #
  # End-state matches TC1 step 3.
  #
  # Key assertion (AC #13):
  #   Reversal of a mismarked-as-Final invoice fully restores the prepayment, and the
  #   reissued Partial invoice produces the canonical alloc value.
  #
  # NOTE: This feature stays RED until the production-code change `Q-iter3-trigger-after-LC-paid`
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
  @ignore
  @Id:S29369_TC4
  Scenario: INV1 mismarked Final → reverse → reissue as Partial → end-state = canonical step 3 (AC #13)

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

    # ── INV1bad: MISMARKED as Final (IsPartialInvoice='N' when it should be 'Y' Partial) ──
    # Wait for IC to be created after receipt, then generate invoice via the real pipeline with IsPartialInvoice=N.
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic1                               | lcOrderL1                 | 400          |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsPartialInvoice | QtyInvoiced |
      | ic1                               | N                | 400         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | inv1bad                 |
    And validate created invoices
      | C_Invoice_ID.Identifier | IsPartialInvoice | DocStatus |
      | inv1bad                 | N                | CO        |
    # Override inv1bad's payment term so it is not due on 2026-04-24 (pay date) — prevents it from
    # appearing in any pay-selection's "Create from..." run.
    And update C_Invoice:
      | Identifier | OPT.C_PaymentTerm_ID |
      | inv1bad    | pt_net90             |

    # Final rule: alloc = remaining_prepay = 21,000 (full prepay drained at first invoice)
    Then validate C_AllocationLines for invoice inv1bad
      | Amount    |
      | -21000.00 |
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 0.00    |

    # INV1bad.OpenAmt = 40,000 − 21,000 = 19,000 (visibly too low → user detects)
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv1bad    | 19000.00 |

    # OD sub-row for r1 linked to inv1bad; Status = Awaiting_Pay
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | Status | C_Invoice_ID |
      | LC                | null       | P      | null         |
      | OD                | r1         | WP     | inv1bad      |

    # ── User detects mistake → reverses INV1bad (AC #13 + AC #16 cascade) ──
    And the invoice identified by inv1bad is reversed

    # iter-3 alloc auto-reverses → prepay.AvailableAmt restored to full 21,000
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt   |
      | lcPayment               | 21000.00  |
    # No active allocation lines link prepayment to inv1bad after reversal
    And there are no allocation lines for invoice
      | C_Invoice_ID |
      | inv1bad      |

    # ── User reissues INV1' correctly marked as Partial (IsPartialInvoice='Y'), matched to R1 ──
    # After inv1bad reversal the IC is reset; generate the corrected invoice via the real pipeline.
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic1                               | lcOrderL1                 | 400          |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsPartialInvoice | QtyInvoiced |
      | ic1                               | Y                | 400         |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | inv1                    |
    And validate created invoices
      | C_Invoice_ID.Identifier | IsPartialInvoice | DocStatus |
      | inv1                    | Y                | CO        |
    And update C_Invoice:
      | Identifier | OPT.C_PaymentTerm_ID |
      | inv1       | pt_net90             |

    # AC #13 — end-state matches TC1 step 3:
    #   alloc = MIN(40,000 × 30 %, 21,000) = 12,000; prepay = 9,000; INV1.OpenAmt = 28,000
    Then validate C_AllocationLines for invoice inv1
      | Amount    |
      | -12000.00 |
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 9000.00 |
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv1       | 28000.00 |

    # R1 sub-row now points to the corrected invoice; Status = Awaiting_Pay
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | Status | C_Invoice_ID |
      | LC                | null       | P      | null         |
      | OD                | r1         | WP     | inv1         |
