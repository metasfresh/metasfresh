@from:cucumber
@allure.label.epic:E0130_Payment
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@ghActions:run_on_executor1
Feature: PO reactivation guard — downstream-activity check
  # Guard blocks reactivation only when the pay schedule carries committed downstream
  # state: a goods-receipt link (M_InOut_ID), a matched-invoice link (C_Invoice_ID),
  # or a proforma allocation. An Awaiting_Pay line with no downstream must NOT block.
  # pt_od = LC 30% + OD 70% (OD resolves at completion); pt_bl = LC 30% + BL 70%.
  # PO: 700 PCE × 100 EUR = 70,000 EUR (IsTaxIncluded=Y → GrandTotal = 70,000).

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

    # pt_od: LC 30% + OD 70% — OrderDate break resolves at completion, OD row immediately WP
    # pt_bl: LC 30% + BL 70% — BillOfLadingDate break resolves only on goods receipt
    And metasfresh contains C_PaymentTerm
      | Identifier   |
      | pt_od        |
      | pt_bl        |
      | pt_immediate |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier  | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | ptb_od_lc   | pt_od            | 30      | 0          | LC                | 10    |
      | ptb_od_od   | pt_od            | 70      | 0          | OD                | 20    |
      | ptb_bl_lc   | pt_bl            | 30      | 0          | LC                | 10    |
      | ptb_bl_bl   | pt_bl            | 70      | 0          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_od      | Y         | Y       |
      | pt_bl      | Y         | Y       |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |


  @from:cucumber
  @Id:S30621_TC1
  Scenario: Reactivate allowed — Awaiting_Pay OD row with no downstream (regression guard)
    # A buyer completes a PO on a LC+OD term. The OD row is immediately Awaiting_Pay because
    # DateOrdered is always set at completion. No goods receipt, invoice, payment, or proforma exists.
    # The guard must allow reactivation — nothing downstream would be lost.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # OD row: Awaiting_Pay (DateOrdered known at completion); no M_InOut_ID, no C_Invoice_ID, no proforma
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | ReferenceDate | DueDate    | Status |
      | LC                | 21000.00 | null          | null          | 9999-12-01 | PR     |
      | OD                | 49000.00 | null          | 2026-04-24    | 2026-04-24 | WP     |

    # Guard allows reactivation — OD Awaiting_Pay with no downstream does not block
    And the order identified by po is reactivated


  @from:cucumber
  @Id:S30621_TC2
  Scenario: Reactivate blocked — proforma allocation exists
    # A buyer allocates a purchase proforma invoice to the order (LC step → Awaiting_Pay).
    # The guard detects the proforma allocation and blocks reactivation. The buyer must
    # de-allocate the proforma before they can reactivate.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_od            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Allocate a proforma invoice to the order (LC row → Awaiting_Pay, proforma service records the link)
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | proforma   | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price    |
      | proformaL1 | proforma     | product      | 1 PCE       | 21000.00 |
    And the invoice identified by proforma is completed
    And I allocate proforma 'proforma' to order 'po'

    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
      | LC                | 21000.00 | 21000.00      | WP     |

    # Guard blocks — proforma allocation exists; user must de-allocate first
    And the order identified by po cannot be reactivated


  @from:cucumber
  @Id:S30621_TC3
  Scenario: Reactivate blocked — goods receipt links a pay-schedule line
    # A buyer completes a goods receipt against a LC+BL order. The receipt creates a BL
    # delivery sub-row carrying M_InOut_ID — committed downstream state that blocks reactivation.
    # A matched financial invoice is also completed (M_MatchInv created); on an order with no
    # proforma prepayment, split-payment tracking is dormant, so the invoice does not project
    # onto the sub-row (C_Invoice_ID stays null, status Pending). The receipt link alone blocks.

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_bl            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | poL1       | po         | product      | 700        |
    And the order identified by po is completed

    # Wait for WP processor to create M_ReceiptSchedule (async after order completion)
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs1                  | po         | poL1           | vendor        | vendor_loc             | product      | 700        | wh             |

    # Goods receipt: 700 PCE in 1 TU → receipt r1 with line r1_line1
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | C_OrderLine_ID | M_HU_PI_Item_Product_ID | QtyCUsPerTU |
      | hu1     | poL1           | 101                     | 700         |
    And create material receipt
      | C_OrderLine_ID | M_HU_ID | M_InOut_ID |
      | poL1           | hu1     | r1         |
    And load M_InOut:
      | QtyEntered | M_InOutLine_ID | M_InOut_ID | DocStatus | C_OrderLine_ID |
      | 700        | r1_line1       | r1         | CO        | poL1           |

    # BL sub-row now exists with M_InOut_ID=r1; matched financial invoice sets C_Invoice_ID on it
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv1       | vendor        | Eingangsrechnung        | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_OrderLine_ID | M_InOutLine_ID |
      | inv1L1     | inv1         | product      | 700 PCE     | 100.00 | poL1           | r1_line1       |
    And the invoice identified by inv1 is completed

    # BL sub-row carries the receipt link (M_InOut_ID=r1) — committed downstream state
    Then the order identified by po has following pay schedule lines by ReferenceDateType
      | ReferenceDateType | M_InOut_ID | DueAmt   |
      | BL                | r1         | 49000.00 |

    # Guard blocks — pay-schedule line carries a downstream goods-receipt link
    And the order identified by po cannot be reactivated
