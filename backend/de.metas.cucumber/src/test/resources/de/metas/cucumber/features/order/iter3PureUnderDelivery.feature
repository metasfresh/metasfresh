@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment iter-3 TC2 — pure under-delivery (Final invoice consumes remaining prepay)
  # Domain: proves the Partial vs Final allocation rules diverge as designed.
  #
  # Scenario:
  #   - PO GrandTotal = 70,000 EUR (700 PCE @ 100 EUR/PCE, tax-inclusive).
  #   - LC 30 % = 21,000 EUR prepayment; Delivery 70 % = 49,000 EUR.
  #   - Iter-2 cycle: proforma allocated + paid → LC step Paid; prepay.AvailableAmt = 21,000.
  #   - R1 = 200 PCE @ 100 EUR = 20,000 with-tax (under-delivery).
  #   - INV1 (Partial, matched to R1): alloc = MIN(20,000 × 30 %, 21,000) = 6,000 EUR.
  #     Remaining prepay = 15,000 EUR; INV1.OpenAmt = 14,000 EUR.
  #   - R2 = 300 PCE @ 100 EUR = 30,000 with-tax. R1+R2 = 50,000 < 70,000 (under-delivery).
  #     Remainder row persists with BaseAmt = 20,000.
  #   - INV2 (Final, matched to R2): alloc = 15,000 EUR (remaining_prepay — Final rule).
  #     NOT 9,000 EUR (= 30,000 × 30 %) — the Final rule consumes the remainder, no stranding.
  #     Prepay.AvailableAmt = 0; INV2.OpenAmt = 15,000 EUR.
  #
  # Key assertion (AC #10):
  #   The Final rule allocates remaining_prepay (15,000), NOT MIN(R2.with_tax × LC%, remaining).
  #   Proves the Partial and Final rules diverge.

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
  @Id:S29369_TC2
  Scenario: TC2 — pure under-delivery: Final invoice consumes remaining prepay (AC #10)

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

    # ── R1: 200 PCE → R1.with_tax = 20,000 ──
    When iter3 purchase receipt 'r1' is created and completed:
      | C_Order_ID | C_OrderLine_ID | MovementQty |
      | lcOrder    | lcOrderL1      | 200         |

    Then the order identified by lcOrder has exactly 2 delivery sub-rows
    And the order identified by lcOrder has following delivery sub-rows:
      | M_InOut_ID | BaseAmt  | DueAmt   | Status | C_Invoice_ID |
      | r1         | 20000.00 | 14000.00 | PR     | null         |
      | null       | 50000.00 | 35000.00 | PR     | null         |

    # ── INV1: Partial, matched to R1 ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | true             |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID |
      | inv1L1     | inv1         | product      | 200 PCE     | 100.00 | lcOrderL1      |
    And iter3 M_MatchInv is created:
      | C_InvoiceLine_ID | M_InOut_ID | Qty |
      | inv1L1           | r1         | 200 |
    And the invoice identified by inv1 is completed

    # INV1 alloc = MIN(20,000 × 30 %, 21,000) = 6,000
    Then validate C_AllocationLines for invoice inv1
      | Amount  |
      | 6000.00 |
    Then the payment 'lcPayment' has AvailableAmt 15000.00

    # ── R2: 300 PCE → R2.with_tax = 30,000. R1+R2 = 50,000 < 70,000 (under-delivery) ──
    When iter3 purchase receipt 'r2' is created and completed:
      | C_Order_ID | C_OrderLine_ID | MovementQty |
      | lcOrder    | lcOrderL1      | 300         |

    # Remainder still exists with BaseAmt = 20,000 (under-delivery)
    Then the order identified by lcOrder has exactly 3 delivery sub-rows
    And the order identified by lcOrder has following delivery sub-rows:
      | M_InOut_ID | BaseAmt  | DueAmt   | Status | C_Invoice_ID |
      | r1         | 20000.00 | 14000.00 | WP     | inv1         |
      | r2         | 30000.00 | 21000.00 | PR     | null         |
      | null       | 20000.00 | 14000.00 | PR     | null         |

    # ── INV2: Final, matched to R2 ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv2       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | false            |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID |
      | inv2L1     | inv2         | product      | 300 PCE     | 100.00 | lcOrderL1      |
    And iter3 M_MatchInv is created:
      | C_InvoiceLine_ID | M_InOut_ID | Qty |
      | inv2L1           | r2         | 300 |
    And the invoice identified by inv2 is completed

    # AC #10 — INV2 alloc = 15,000 (remaining prepay — Final rule), NOT 9,000 (= 30,000 × 30 %)
    Then validate C_AllocationLines for invoice inv2
      | Amount   |
      | 15000.00 |
    Then the payment 'lcPayment' has AvailableAmt 0.00

    # INV2.OpenAmt = 30,000 − 15,000 = 15,000
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv2       | 15000.00 |
