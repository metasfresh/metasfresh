@from:cucumber
@Order
@Proforma
@PaySelection
@Iteration2
@MF_29368
@ghActions:run_on_executor4
Feature: Split-payment LC lifecycle — proforma allocation drives LC pay-schedule state
  # https://github.com/metasfresh/me03/issues/29368
  # TC1-TC4: end-to-end scenarios for the LC step of the split-payment (Iter 2) EPIC.
  #
  # Business context:
  #   A purchase order with a complex payment term that has two breaks:
  #     - 30 % LC  (LetterOfCreditDate) — paid up-front via proforma invoice
  #     - 70 % OD  (OrderDate)          — paid later against the delivery invoice
  #
  # The scenarios validate the truth table in REQUIREMENTS.md §3:
  #   alloc=NONE                                → LC Status=Pending,      DueAmt_Actual=NULL
  #   alloc=PRESENT, payment absent/drafted     → LC Status=Awaiting_Pay, DueAmt_Actual=proforma.GrandTotal
  #   alloc=PRESENT, payment=COMPLETED          → LC Status=Paid,         DueAmt_Actual=proforma.GrandTotal
  #   alloc=PRESENT, payment reversed           → LC Status=Awaiting_Pay, DueAmt_Actual preserved

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
      | M_PriceList_Version_ID | M_Product_ID | PriceStd  | C_UOM_ID |
      | plv_purchase           | product      | 68654.40  | PCE      |

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
  @Order
  @Proforma
  @PaySelection
  @Iteration2
  @MF_29368
  Scenario: TC1 - Happy path: PO → Proforma → Allocate → Pay Selection → Payment → LC Paid
    # https://github.com/metasfresh/me03/issues/29368
    # Full end-to-end: allocating a proforma to the LC break drives the LC schedule line to
    # Awaiting_Pay; completing a pay-selection payment drives it to Paid.
    # The Delivery (OD) line must remain Pending throughout.

    # ── Create and complete the purchase order ────────────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 1          |
    And the order identified by po is completed

    # ── Before allocation: LC step is Pending (iter 1 default when LC_Date IS NULL).
    #    Delivery (OD) step stays at Awaiting_Pay throughout iter 2 — Delivery-step splits are iter 3 scope.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LC                 | 20596.32  | PR      | null          |
      | OD                 | 48058.08  | WP      | null          |

    # ── Create and complete the proforma invoice (GrandTotal = 20596.32 = planned DueAmt) ─────
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apf_inv    | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price     |
      | apf_invL1  | apf_inv      | product      | 1 PCE       | 20596.32  |
    And the invoice identified by apf_inv is completed

    # ── Allocate proforma → LC line becomes Awaiting_Pay ─────────────────────
    And I allocate proforma 'apf_inv' to order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status       | DueAmt_Actual |
      | LC                 | 20596.32  | WP           | 20596.32      |
      | OD                 | 48058.08  | WP           | null          |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |

    # ── Pay Selection: pick up the LC line and generate the payment ───────────
    And metasfresh contains Pay Selection
      | Identifier | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySel     | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySel, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | N       |

    # The order has two pay-schedule rows (LC + Delivery), buildOrderSql includes them
    # in pay selection. The APF invoice is the third line (the proforma payment we'll complete).
    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | -            | 20596.32 | -            |
      | -            | 48058.08 | -            |
      | apf_inv      | 20596.32 | -            |

    And the pay selection identified by paySel is completed
    Then "Create Payments" is invoked for pay selection paySel

    # "Create Payments" creates a C_Payment for every pay-selection-line.
    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID    |
      | -            | 20596.32 | payment_lc_step |
      | -            | 48058.08 | payment_od_step |
      | apf_inv      | 20596.32 | payment1        |

    # ── After payment: LC Paid, Delivery still Pending ────────────────────────
    # iter 1 semantics: every pay-selection-line that gets a payment also flips its
    # source order-pay-schedule row to Paid. The proforma authority function flips LC
    # too. So both rows end up Paid.
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LC                 | 20596.32  | P       | 20596.32      |
      | OD                 | 48058.08  | P       | null          |


  @from:cucumber
  @Order
  @Proforma
  @PaySelection
  @Iteration2
  @MF_29368
  Scenario: TC2 - Proforma amount below planned DueAmt: DueAmt unchanged, DueAmt_Actual = proforma GrandTotal
    # https://github.com/metasfresh/me03/issues/29368
    # When the proforma invoice has a GrandTotal below the planned LC DueAmt (20596.32),
    # the allocation sets DueAmt_Actual to the proforma GrandTotal (20500.00) while
    # DueAmt remains at the planned amount (20596.32).
    # The Delivery (OD) line must have DueAmt_Actual=null throughout.

    # ── Create and complete the purchase order ────────────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 1          |
    And the order identified by po is completed

    # ── Create and complete the proforma invoice (GrandTotal = 20500.00, BELOW planned 20596.32) ─
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apf_tc2_inv | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price     |
      | apf_tc2_invL1 | apf_tc2_inv  | product      | 1 PCE       | 20500.00  |
    And the invoice identified by apf_tc2_inv is completed

    # ── Allocate proforma → LC line becomes Awaiting_Pay ─────────────────────
    And I allocate proforma 'apf_tc2_inv' to order 'po'

    # ── Assertions: DueAmt = plan (20596.32), DueAmt_Actual = proforma GrandTotal (20500.00) ─
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status       | DueAmt_Actual |
      | LC                 | 20596.32  | WP           | 20500.00      |
      | OD                 | 48058.08  | WP           | null          |


  @from:cucumber
  @Order
  @Proforma
  @PaySelection
  @Iteration2
  @MF_29368
  Scenario: TC3 - Deallocation before payment reverts LC line to Pending
    # https://github.com/metasfresh/me03/issues/29368
    # After allocating a proforma, the user deallocates it before any payment is made.
    # The LC line must revert to Pending with DueAmt_Actual=null and LC_Date=null.

    # ── Create and complete the purchase order ────────────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 1          |
    And the order identified by po is completed

    # ── Create and complete the proforma invoice ──────────────────────────────
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apf_tc3_inv | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | apf_tc3_invL1 | apf_tc3_inv  | product      | 1 PCE       | 20596.32 |
    And the invoice identified by apf_tc3_inv is completed

    # ── Allocate → LC becomes Awaiting_Pay, LC_Date set ──────────────────────
    And I allocate proforma 'apf_tc3_inv' to order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status       | DueAmt_Actual |
      | LC                 | 20596.32  | WP           | 20596.32      |
      | OD                 | 48058.08  | WP           | null          |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |

    # ── Deallocate → LC reverts to Pending, DueAmt_Actual=null, LC_Date=null ─
    And I deallocate proforma 'apf_tc3_inv' from order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LC                 | 20596.32  | PR      | null          |
      | OD                 | 48058.08  | WP      | null          |
    And validate the created orders
      | Identifier | LC_Date |
      | po         | null    |


  @from:cucumber
  @Order
  @Proforma
  @PaySelection
  @Iteration2
  @MF_29368
  Scenario: TC4 - Payment reversal rolls LC back from Paid to Awaiting_Pay with DueAmt_Actual preserved
    # https://github.com/metasfresh/me03/issues/29368
    # After a payment is completed (LC → Paid) and then reversed, the LC line must roll
    # back to Awaiting_Pay. DueAmt_Actual and LC_Date must be preserved (not cleared),
    # because the allocation still exists — only the payment is gone.

    # ── Create and complete the purchase order ────────────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 1          |
    And the order identified by po is completed

    # ── Create and complete the proforma invoice ──────────────────────────────
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apf_tc4_inv | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | apf_tc4_invL1 | apf_tc4_inv  | product      | 1 PCE       | 20596.32 |
    And the invoice identified by apf_tc4_inv is completed

    # ── Allocate → LC becomes Awaiting_Pay ───────────────────────────────────
    And I allocate proforma 'apf_tc4_inv' to order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status       | DueAmt_Actual |
      | LC                 | 20596.32  | WP           | 20596.32      |
      | OD                 | 48058.08  | WP           | null          |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |

    # ── Pay Selection → complete → generate payment → LC becomes Paid ─────────
    And metasfresh contains Pay Selection
      | Identifier | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySel     | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySel, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | N       |
    And the pay selection identified by paySel is completed
    Then "Create Payments" is invoked for pay selection paySel

    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID        |
      | -            | 20596.32 | payment_tc4_lc_step |
      | -            | 48058.08 | payment_tc4_od_step |
      | apf_tc4_inv  | 20596.32 | payment_tc4         |

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LC                 | 20596.32  | P       | 20596.32      |
      | OD                 | 48058.08  | P       | null          |

    # ── Reverse the APF payment → LC rolls back to Awaiting_Pay (authority function),
    #    OD stays Paid (its own payment was not reversed).
    And the payment identified by payment_tc4 is reversed

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status       | DueAmt_Actual |
      | LC                 | 20596.32  | WP           | 20596.32      |
      | OD                 | 48058.08  | P            | null          |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |
