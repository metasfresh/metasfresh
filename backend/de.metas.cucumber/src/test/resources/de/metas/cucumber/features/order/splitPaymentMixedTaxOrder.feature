@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: Split-payment — mixed-tax order (per-order-line tax, AC #21)
  # Domain: proves that ReceiptTaxCalculator applies tax per order line (AC #21).
  #
  # Scenario:
  #   - PO has two lines, each product with a different tax rate (20% and 10%).
  #   - Price list is NOT tax-inclusive (IsTaxIncluded=N): tax is added on top of net.
  #   - OrderLine1 (productA): 200 PCE @ 50 EUR net, 20% tax → line gross = 12,000 EUR.
  #   - OrderLine2 (productB): 200 PCE @ 50 EUR net, 10% tax → line gross = 11,000 EUR.
  #   - GrandTotal = 23,000 EUR (12,000 + 11,000).
  #   - LC 30% = 6,900 EUR; BL 70% = 16,100 EUR (BillOfLadingDate → isMaterialReceiptDate=true).
  #   - Proforma + payment for 6,900 → LC step Paid.
  #   - R1: 200 PCE productA → BaseAmt = 12,000 (200 × 50 × 1.20, per-line 20% rate).
  #     Wrong approach (avg 15% rate) would give 200 × 50 × 1.15 = 11,500 ≠ 12,000.
  #   - R2: 200 PCE productB → BaseAmt = 11,000 (200 × 50 × 1.10).
  #     R1 + R2 = 23,000 = GrandTotal → exact delivery, no remainder row.
  #
  # Key assertion (AC #21): R1.BaseAmt = 12,000 (per-line 20% rate), not 11,500 (avg rate).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2026-04-24T10:00:00+02:00[Europe/Berlin]

    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory20 |
      | taxCategory10 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax20      | taxCategory20    | 20   | DE                       | DE                        |
      | tax10      | taxCategory10    | 10   | DE                       | DE                        |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_purchase | ps                 | DE           | EUR           | false | N             |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
    And metasfresh contains M_Products:
      | Identifier |
      | productA   |
      | productB   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_purchase           | productA     | 50.00    | PCE      | taxCategory20    |
      | plv_purchase           | productB     | 50.00    | PCE      | taxCategory10    |

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
      | ptb_bl     | pt_lc            | 70      | 0          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_lc      | Y         | Y       |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |


  @from:cucumber
  @Id:S29369_TC7
  Scenario: Mixed-tax order: R1.BaseAmt uses productA's 20% rate, not the order-average rate (AC #21)

    # ── Order: 2 lines, different tax rates ──
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | lcOrder    | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | lcOrderL1  | lcOrder    | productA     | 200        |
      | lcOrderL2  | lcOrder    | productB     | 200        |
    And the order identified by lcOrder is completed

    # GrandTotal = 12,000 (productA: 200×50×1.20) + 11,000 (productB: 200×50×1.10) = 23,000
    # LC 30% = 6,900; BL 70% = 16,100
    Then the order identified by lcOrder has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status | ReferenceDate | IsPaid |
      | LC                | 6900.00  | null          | PR     | null          | N      |
      | BL                | 16100.00 | null          | PR     | null          | N      |

    # ── Iter-2: proforma (6,900 EUR) + payment → LC Paid ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    # Proforma must equal LC portion = 6,900 EUR GROSS. Price list is tax-exclusive
    # and productA carries 20% tax → set net price to 5,750 so 5750 × 1.20 = 6900 gross.
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price   |
      | lcInvoiceL1 | lcInvoice    | productA     | 1 PCE       | 5750.00 |
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
      | C_Invoice_ID | OpenAmt | C_Payment_ID |
      | lcInvoice    | 6900.00 | lcPayment    |

    # ── R1: 200 PCE of productA ──
    # HU receipt: QtyCUsPerTU=200 → 1 HU with 200 PCE → receipt r1 QtyEntered=200.
    # BaseAmt = 200 × 50 × 1.20 = 12,000 EUR (per-line 20% rate; AC #21)
    # Wait for WP processor to create M_ReceiptSchedule for the order line (async after order completion).
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs1                  | lcOrder    | lcOrderL1      | vendor        | vendor_loc             | productA     | 200        | wh             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu1     | lcOrderL1      | 101                     | 200         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | lcOrderL1      | hu1     | r1         |
    And load M_InOut:
      | QtyEntered | M_InOutLine_ID | M_InOut_ID | DocStatus | C_OrderLine_ID |
      | 200        | r1_line1       | r1         | CO        | lcOrderL1      |

    # AC #21: BaseAmt = 12,000 proves per-line 20% rate was used (not avg 15% = 11,500)
    # Remainder row: BaseAmt = 23,000 − 12,000 = 11,000; DueAmt = 11,000 × 70% = 7,700
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | BaseAmt   | DueAmt  | Status | C_Invoice_ID |
      | LC                | null       | 23000.00  | 6900.00 | P      | null         |
      | BL                | r1         | 12000.00  | 8400.00 | PR     | null         |
      | BL                | null       | 11000.00  | 7700.00 | PR     | null         |

    # ── INV1: Partial, matched to R1 via M_InOutLine_ID FK (auto-creates M_MatchInv on completeIt) ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_OrderLine_ID | M_InOutLine_ID |
      | inv1L1     | inv1         | productA     | 200 PCE     | 50.00 | lcOrderL1      | r1_line1       |
    And the invoice identified by inv1 is completed

    # INV1 alloc = MIN(12,000 × 30%, 6,900) = MIN(3,600, 6,900) = 3,600
    Then validate C_AllocationLines for invoice inv1
      | Amount   |
      | -3600.00 |
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 3300.00 |

    # R1 sub-row → Status=Awaiting_Pay; C_Invoice_ID=inv1
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | Status | C_Invoice_ID |
      | LC                | null       | P      | null         |
      | BL                | r1         | WP     | inv1         |
      | BL                | null       | PR     | null         |

    # ── R2: 200 PCE of productB ──
    # HU receipt: QtyCUsPerTU=200 → 1 HU with 200 PCE → receipt r2 QtyEntered=200.
    # BaseAmt = 200 × 50 × 1.10 = 11,000 EUR (per-line 10% rate; AC #21)
    # R1(12,000) + R2(11,000) = 23,000 = GrandTotal → exact delivery → no remainder
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu2     | lcOrderL2      | 101                     | 200         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | lcOrderL2      | hu2     | r2         |
    And load M_InOut:
      | QtyEntered | M_InOutLine_ID | M_InOut_ID | DocStatus | C_OrderLine_ID |
      | 200        | r2_line1       | r2         | CO        | lcOrderL2      |

    # R1(12,000) + R2(11,000) = 23,000 = GrandTotal → exact delivery → no remainder row
    Then the order identified by lcOrder has following pay schedules
      | ReferenceDateType | M_InOut_ID | BaseAmt   | DueAmt  | Status | C_Invoice_ID |
      | LC                | null       | 23000.00  | 6900.00 | P      | null         |
      | BL                | r1         | 12000.00  | 8400.00 | WP     | inv1         |
      | BL                | r2         | 11000.00  | 7700.00 | PR     | null         |

    # ── INV2: Final, matched to R2 via M_InOutLine_ID FK (auto-creates M_MatchInv on completeIt) ──
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv2       | vendor        | Endabrechnung           | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_OrderLine_ID | M_InOutLine_ID |
      | inv2L1     | inv2         | productB     | 200 PCE     | 50.00 | lcOrderL2      | r2_line1       |
    And the invoice identified by inv2 is completed

    # INV2 alloc = 3,300 (remaining prepay — Final rule)
    Then validate C_AllocationLines for invoice inv2
      | Amount   |
      | -3300.00 |
    Then validate payments
      | C_Payment_ID.Identifier | OpenAmt |
      | lcPayment               | 0.00    |

    # INV2.OpenAmt = 11,000 (gross with 10% tax) − 3,300 (allocated) = 7,700
    Then validate created invoices
      | Identifier | OpenAmt |
      | inv2       | 7700.00 |
