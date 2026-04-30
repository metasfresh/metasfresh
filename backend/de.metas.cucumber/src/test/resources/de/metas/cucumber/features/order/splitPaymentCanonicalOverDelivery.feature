@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — canonical over-delivery (Partial → Final)
  # Domain: a purchase order with LC (30%) + Delivery (70%) payment term.
  # Iter-2 produced the LC-Paid state machine. Iter-3 splits the Delivery row per receipt
  # and allocates the prepayment proportionally to each financial invoice.
  #
  # Scenario:
  #   - PO GrandTotal = 70,000 EUR (1 product, 700 PCE @ 100 EUR/PCE, tax-inclusive).
  #   - LC 30 % = 21,000 EUR prepayment; Delivery 70 % = 49,000 EUR.
  #   - Iter-2 cycle: proforma allocated + paid → LC step Paid; prepay.AvailableAmt = 21,000.
  #   - R1 = 400 PCE @ 100 EUR = 40,000 with-tax → Delivery sub-row R1 + remainder created.
  #   - INV1 (Partial, matched to R1): alloc = MIN(40,000 × 30 %, 21,000) = 12,000 EUR.
  #     Remaining prepay = 9,000 EUR; INV1.OpenAmt = 28,000 EUR.
  #   - R2 = 320 PCE @ 100 EUR = 32,000 with-tax → R1+R2 = 72,000 > 70,000 (over-delivery)
  #     → remainder row deleted.
  #   - INV2 (Final, matched to R2): alloc = 9,000 EUR (remaining prepay — Final rule).
  #     Prepay.AvailableAmt = 0; INV2.OpenAmt = 23,000 EUR.
  #
  # Key assertions:
  #   AC #1 — initial Delivery remainder row after LC Paid.
  #   AC #3 — Delivery sub-rows after R1.
  #   AC #4 — allocation amount for INV1 (Partial).
  #   AC #5 — R1 sub-row C_Invoice_ID + Status = Awaiting_Pay after INV1.
  #   AC #7 — remainder row deleted after R2 (over-delivery).
  #   AC #8 — allocation amount for INV2 (Final).
  #   AC #9 — R2 sub-row Status = Awaiting_Pay after INV2.

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

    # pt_lc:        30 % LC (LetterOfCreditDate) + 70 % OD (OrderDate)
    # pt_immediate: proforma payment term (NetDays=0, OnlyDue=Y picks it up in pay-selection)
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
  @ghActions:run_on_executor1
  @Id:S29369_TC1
  Scenario: Canonical over-delivery: Partial (INV1) → Final (INV2); remainder row deleted

    #
    # ── Purchase Order completed; 700 PCE @ 100 EUR = 70,000 EUR total ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID | InvoiceRule |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            | D           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 700        |
    And the order identified by lcOrder is completed
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | BaseAmt  | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status | IsPaid | M_InOut_ID | C_Invoice_ID |
      | LC                | 70000.00 | 21000.00 | 21000.00      | -             | 9999-12-01 | PR     | N      | -          | -            |
      # FIXME: Status shall be PR and not WP
      | OD                | 70000.00 | 49000.00 | 49000.00      | 2026-04-24    | 2026-04-24 | WP     | N      | -          | -            |
    
    
    #
    # ── Proforma + payment → LC Paid; Delivery row = 1 remainder ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | lcInvoiceL1 | lcInvoice    | product      | 1 PCE       | 21000.00 |
    And the invoice identified by lcInvoice is completed
    And I allocate proforma 'lcInvoice' to order 'lcOrder'
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | BaseAmt  | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status | IsPaid | M_InOut_ID | C_Invoice_ID |
      | LC                | 70000.00 | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-24 | WP     | N      | -          | -            |
      | OD                | 70000.00 | 49000.00 | 49000.00      | 2026-04-24    | 2026-04-24 | WP     | N      | -          | -            |
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
    # AC #1 — after LC Paid: 1 Delivery remainder row, LC row Paid
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | BaseAmt  | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status | IsPaid | M_InOut_ID | C_Invoice_ID |
      | LC                | 70000.00 | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-24 | P      | Y      | -          | -            |
      | OD                | 70000.00 | 49000.00 | 49000.00      | 2026-04-24    | 2026-04-24 | WP     | N      | -          | -            |

    
    
    #
    # ── Receipt 1: 400 PCE of product → R1.with_tax = 40,000 ──
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID |
      | hu1     | lcOrderL1      | 101                     |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID |
      | lcOrderL1      | hu1     |
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | BaseAmt  | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status | IsPaid | M_InOut_ID | C_Invoice_ID |
      | LC                | 70000.00 | 21000.00 | 21000.00      | 2026-04-24    | 2026-04-24 | P      | Y      | -          |              |
      | OD                | 70000.00 | 40000.00 | 40000.00      | 2026-04-24    | 9999-12-01 | PR     | N      | r1         | -            |
      | OD                | 70000.00 | 30000.00 | 30000.00      | 2026-04-24    | 9999-12-01 | PR     | N      | -          | -            |

    
    
    
    
    # ── INV1: canonical IC pipeline, IsPartialInvoice=Y (Partial) ──
    And after not more than 60s locate invoice candidates by order id:
      | C_Invoice_Candidate_ID.Identifier | C_Order_ID.Identifier |
      | ic1                               | lcOrder               |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsPartialInvoice |
      | ic1                               | true             |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | inv1                    |

    # AC #4 — exactly 1 allocation line linking prepayment to INV1, Amount = 12,000
    Then validate C_AllocationLines for invoice inv1
      | Amount   |
      | 12000.00 |

    # prepayment AvailableAmt = 21,000 − 12,000 = 9,000
    Then the payment 'lcPayment' has AvailableAmt 9000.00

    # INV1.OpenAmt = 40,000 − 12,000 = 28,000
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv1       | 28000.00 |

    # AC #5 — R1 sub-row gets C_Invoice_ID=inv1, Status=Awaiting_Pay
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | Status | IsPaid | C_Invoice_ID |
      | LC                | P      | Y      |              |
      | OD                | WP     | N      | inv1         |
      | OD                | PR     | N      | -            |

    # ── R2: 320 PCE → R2.with_tax = 32,000; R1+R2 = 72,000 > 70,000 (over-delivery) ──
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu2     | lcOrderL1      | 101                     | 320         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | lcOrderL1      | hu2     | r2         |

    # AC #7 — remainder row deleted (over-delivery); now LC + R1 + R2 = 3 rows
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | Status | IsPaid | C_Invoice_ID |
      | LC                | P      | Y      |              |
      | OD                | WP     | N      | inv1         |
      | OD                | PR     | N      | -            |

    # ── INV2: canonical IC pipeline, IsPartialInvoice=N (Final) ──
    And after not more than 60s locate invoice candidates by order id:
      | C_Invoice_Candidate_ID.Identifier | C_Order_ID.Identifier |
      | ic2                               | lcOrder               |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IsPartialInvoice |
      | ic2                               | false            |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic2                               | inv2                    |

    # AC #8 — exactly 1 allocation line linking prepayment to INV2, Amount = 9,000 (remaining prepay — Final)
    Then validate C_AllocationLines for invoice inv2
      | Amount  |
      | 9000.00 |

    # prepayment AvailableAmt = 0 (fully consumed)
    Then the payment 'lcPayment' has AvailableAmt 0.00

    # INV2.OpenAmt = 32,000 − 9,000 = 23,000
    Then validate created invoices
      | Identifier | OpenAmt  |
      | inv2       | 23000.00 |

    # AC #9 — R2 sub-row gets C_Invoice_ID=inv2, Status=Awaiting_Pay
    And the order identified by lcOrder has following pay schedules
      | ReferenceDateType | Status | IsPaid | C_Invoice_ID |
      | LC                | P      | Y      |              |
      | OD                | WP     | N      | inv1         |
      | OD                | WP     | N      | inv2         |
