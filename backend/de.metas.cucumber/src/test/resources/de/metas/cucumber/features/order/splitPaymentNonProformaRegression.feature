@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — non-proforma order regression (split-payment dormant)
  # Domain: a purchase order with the same LC+OD payment term but WITHOUT a proforma
  # allocation. Proves AC #22: iter-3 services are dormant unless a proforma-allocated
  # prepayment payment exists for the order.
  #
  # Scenario:
  #   - PO GrandTotal = 70,000 EUR (700 PCE @ 100 EUR/PCE, tax-inclusive).
  #   - LC 30 % + OD 70 % payment term — same as TC1/TC2.
  #   - NO proforma allocation, NO prepayment payment.
  #   - After completing the order: LC row Pending, OD row Awaiting_Pay (iter-2 behaviour).
  #   - Receive R1 (400 PCE): recomputeDeliverySteps is NOT triggered (no proforma).
  #     Pay schedule unchanged — still only the two iter-2 rows (LC + single OD).
  #   - Complete a financial invoice INV1 matched to R1: DeliveryPrepaymentAllocationService
  #     is NOT triggered. Zero allocation lines for INV1.
  #   - The single OD delivery row is NOT split into sub-rows.
  #
  # This scenario gating the entire iter-3 PR (AC #22).

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
  @Id:S29369_TC8
  Scenario: No proforma allocation → split-payment dormant; delivery schedule unchanged by receipt + invoice

    # ── Order completed — LC row Pending, OD row Awaiting_Pay (iter-2 behaviour) ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | product      | 700        |
    And the order identified by lcOrder is completed

    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 21000.00 | null          | PR     | null          | N      |
      | OD                | 49000.00 | null          | WP     | 2026-04-24    | N      |

    # ── R1: 400 PCE received — no proforma, so recomputeDeliverySteps is dormant ──
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

    # AC #22 — schedule unchanged: still exactly 2 iter-2 rows (LC + OD), no delivery sub-rows
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 21000.00 | null          | PR     | null          | N      |
      | OD                | 49000.00 | null          | WP     | 2026-04-24    | N      |

    # ── INV1: financial purchase invoice, matched to R1 via M_InOutLine_ID FK ──
    # (auto-creates M_MatchInv on completeIt — resolves the DateAcct NPE from the manual path)
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | true             |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID | M_InOutLine_ID |
      | inv1L1     | inv1         | product      | 400 PCE     | 100.00 | lcOrderL1      | r1_line1       |
    And the invoice identified by inv1 is completed

    # AC #22 — no allocation created (DeliveryPrepaymentAllocationService dormant)
    Then there are no allocation lines for invoice
      | C_Invoice_ID |
      | inv1         |

    # AC #22 — delivery schedule still unchanged after invoice completion
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 21000.00 | null          | PR     | null          | N      |
      | OD                | 49000.00 | null          | WP     | 2026-04-24    | N      |


  @from:cucumber
  @Id:S29369_TC23
  Scenario: Non-proforma payment with IsAutoAllocateAvailableAmt='Y' triggers standard greedy allocation (AC #23 regression guard)
    # Domain: a vendor invoice + a vendor payment that are allocated via the standard
    # IAllocationBL greedy MIN path.  There is NO proforma allocation on any related order,
    # so iter-3's DeliveryPrepaymentAllocationService is entirely dormant.
    #
    # Allocation rule under test: alloc = MIN(payment.AvailableAmt, invoice.OpenAmt)
    #
    # Setup:
    #   - Vendor invoice: 10 PCE × 100 EUR (tax-inclusive) → GrandTotal = 1,000 EUR
    #   - Vendor payment: PayAmt = 600 EUR  (partial — tests the MIN cap)
    #   - Expected C_AllocationLine.Amount = MIN(600, 1000) = 600 EUR
    #
    # Assertions:
    #   1. Exactly one C_AllocationLine exists for the invoice (standard path).
    #   2. Amount = 600 EUR  (greedy MIN: payment amt < invoice open amt → amount = payAmt).
    #   3. Invoice.IsPartiallyPaid = true (600 out of 1,000 paid).

    # ── Organisation bank account (needed to create the vendor payment) ──
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    # ── Non-proforma PO: 10 PCE × 100 EUR = 1,000 EUR (no proforma allocation) ──
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | ac23_order    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | ac23_orderL1  | ac23_order | product      | 10         |
    And the order identified by ac23_order is completed

    # ── Vendor invoice: 10 PCE × 100 EUR = 1,000 EUR, matched to the order line ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | IsPartialInvoice |
      | ac23_inv   | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           | false            |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID |
      | ac23_invL1  | ac23_inv     | product      | 10 PCE      | 100.00 | ac23_orderL1   |
    And the invoice identified by ac23_inv is completed

    # ── Vendor payment: 600 EUR (IsPrepayment=N, no Proforma_Invoice_ID) ──
    And metasfresh contains C_Payment
      | Identifier  | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | ac23_pmt    | vendor        | 600 EUR  | false     | org_EUR_account     |
    And the payment identified by ac23_pmt is completed

    # ── Trigger standard greedy MIN allocation via PaymentAllocationBuilder ──
    # alloc = MIN(payment.AvailableAmt=600, invoice.OpenAmt=1000) = 600
    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | ac23_inv     | ac23_pmt     |

    # ── Assert: standard C_AllocationLine created with Amount = -600 (AP sign: negative) ──
    # alloc = MIN(payment.AvailableAmt=600, invoice.OpenAmt=1000) = 600; stored as -600 for AP
    Then validate C_AllocationLines for invoice ac23_inv
      | C_Payment_ID | Amount  |
      | ac23_pmt     | -600.00 |

    # ── Assert: invoice is partially paid (standard allocation reduced OpenAmt) ──
    And validate created invoices
      | C_Invoice_ID | IsPartiallyPaid |
      | ac23_inv     | true            |
