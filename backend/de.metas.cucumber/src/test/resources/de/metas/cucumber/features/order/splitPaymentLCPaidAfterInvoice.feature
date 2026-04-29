@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — LC payment completes AFTER financial invoice arrived (retro-allocation)
  # Domain: real-life docs come in any order. The procurement worker may receive goods and
  # the vendor's financial invoice BEFORE the LC payment clears. At financial-invoice complete
  # time the prepayment doesn't exist yet, so the forward allocation chain is a no-op
  # (DeliveryPrepaymentAllocationService.lookupProformaPrepaymentPayment returns null →
  # allocateForInvoice early-exits). When the LC payment eventually completes, retro-allocation
  # picks up the orphan invoice(s) FIFO by C_Invoice.DateInvoiced.
  #
  # Scenario timeline:
  #   T1: PO + proforma + allocate proforma → LC step Awaiting_Pay (no payment yet)
  #   T2: Receipt R1 + financial invoice INV1 (IsPartialInvoice=Y) complete
  #       → forward allocation skipped (no completed prepayment payment yet)
  #       → assert NO C_AllocationLine linking INV1 to any prepayment
  #   T3: Pay-selection + complete proforma payment → LC step Paid
  #       → retroAllocateUnallocatedInvoices fires
  #       → assert C_AllocationLine now exists on INV1, amount = MIN(receipt × LC%, prepay)
  #
  # Key assertion: at T2 the allocation is absent; at T3 it appears, proving the retro-allocation
  # trigger from C_Payment_LCStep.onPaymentCompleted (Phase 5.5).

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


  @from:cucumber
  @Id:S29369_TC10
  Scenario: LC payment completes after financial invoice — retro-allocation kicks in FIFO
    # ── Order: 100 PCE × 100 EUR = 10,000 GrandTotal (tax-inclusive list);
    #    LC 30% = 3,000; OD 70% = 7,000 ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 100        |
    And the order identified by lcOrder is completed

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | IsPaid |
      | LC                | 3000.00  | null          | PR     | N      |
      | OD                | 7000.00  | null          | WP     | N      |

    # ── Proforma created + completed (GrandTotal = LC plan = 3,000) ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price   |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 3000.00 |
    And the invoice identified by lcInvoice is completed

    # ── Allocate proforma → LC step Awaiting_Pay (proforma allocation present, payment NOT yet) ──
    And I allocate proforma 'lcInvoice' to order 'lcOrder'

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | IsPaid |
      | LC                | 3000.00  | 3000.00       | WP     | N      |
      | OD                | 7000.00  | null          | WP     | N      |

    # ── T2: R1 + INV1 complete BEFORE the LC payment ──
    # The procurement worker has goods received + vendor's financial invoice in hand
    # while the LC bank wire is still in flight.
    When iter3 purchase receipt 'r1' is created and completed:
      | C_Order_ID | C_OrderLine_ID | MovementQty |
      | lcOrder    | lcOrderL1      | 100         |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | true             |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID |
      | inv1L1     | inv1         | product      | 100 PCE     | 100.00 | lcOrderL1      |
    And iter3 M_MatchInv is created:
      | C_InvoiceLine_ID | M_InOut_ID | Qty |
      | inv1L1           | r1         | 100 |
    And the invoice identified by inv1 is completed

    # Forward-mode allocation chain runs at INV1 AFTER_COMPLETE but
    # lookupProformaPrepaymentPayment returns null (no completed payment yet) → no-op.
    Then there are no allocation lines for invoice
      | C_Invoice_ID |
      | inv1         |

    # ── T3: pay-selection + complete the LC payment → LC step Paid + retro-allocation fires ──
    And metasfresh contains Pay Selection
      | Identifier   | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySelection | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySelection, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | Y       |
    And the pay selection identified by paySelection is completed
    Then "Create Payments" is invoked for pay selection paySelection

    And the Pay selection identified by paySelection has exactly the following lines
      | C_Invoice_ID | OpenAmt | C_Payment_ID |
      | lcInvoice    | 3000.00 | lcPayment    |

    # LC step Paid (proforma payment completed); retroAllocateUnallocatedInvoices fires from
    # C_Payment_LCStep.onPaymentCompleted, picks up INV1, calls allocateForInvoice.
    # Expected allocation: MIN(receipt 10000 × 30%, prepay 3000) = MIN(3000, 3000) = 3000.
    Then validate C_AllocationLines for invoice inv1
      | Amount  |
      | 3000.00 |

    # LC pay-schedule line: Status=P (Paid), IsPaid=Y, ReferenceDate=proforma.DateInvoiced.
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt  | DueAmt_Actual | Status | IsPaid |
      | LC                | 3000.00 | 3000.00       | P      | Y      |
      | OD                | 7000.00 | null          | WP     | N      |

    # Prepayment fully consumed: AvailableAmt = 3000 − 3000 = 0.
    Then the payment 'lcPayment' has AvailableAmt 0
