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
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_purchase | ps                 | DE           | EUR           | false |
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
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_loc | vendor        | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

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

    # ── Before allocation: both lines Pending ─────────────────────────────────
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LetterOfCreditDate | 20596.32  | Pending | null          |
      | OrderDate          | 48058.08  | Pending | null          |

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
      | LetterOfCreditDate | 20596.32  | Awaiting_Pay | 20596.32      |
      | OrderDate          | 48058.08  | Pending      | null          |
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

    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | apf_inv      | 20596.32 | -            |

    And the pay selection identified by paySel is completed
    Then "Create Payments" is invoked for pay selection paySel

    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | apf_inv      | 20596.32 | payment1     |

    # ── After payment: LC Paid, Delivery still Pending ────────────────────────
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LetterOfCreditDate | 20596.32  | Paid    | 20596.32      |
      | OrderDate          | 48058.08  | Pending | null          |


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
      | LetterOfCreditDate | 20596.32  | Awaiting_Pay | 20500.00      |
      | OrderDate          | 48058.08  | Pending      | null          |


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
      | LetterOfCreditDate | 20596.32  | Awaiting_Pay | 20596.32      |
      | OrderDate          | 48058.08  | Pending      | null          |
    And validate the created orders
      | Identifier | LC_Date    |
      | po         | 2026-04-24 |

    # ── Deallocate → LC reverts to Pending, DueAmt_Actual=null, LC_Date=null ─
    And I deallocate proforma 'apf_tc3_inv' from order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType  | DueAmt    | Status  | DueAmt_Actual |
      | LetterOfCreditDate | 20596.32  | Pending | null          |
      | OrderDate          | 48058.08  | Pending | null          |
    And validate the created orders
      | Identifier | LC_Date |
      | po         | null    |
