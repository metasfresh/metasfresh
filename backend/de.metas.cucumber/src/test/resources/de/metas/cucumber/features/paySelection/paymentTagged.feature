@from:cucumber
@PaySelection
@Proforma
@Payment
@Iteration2
@MF_29368
@ghActions:run_on_executor4
Feature: Payment generated from a proforma pay-selection line is auto-tagged
  # https://github.com/metasfresh/me03/issues/29368
  # TC6: Completing a pay-selection whose source is a purchase proforma creates a
  # payment tagged with Proforma_Invoice_ID and IsPrepayment=Y.
  #
  # Note: IsPrepayment is auto-derived by MPayment.beforeSave from C_Order_ID != 0.
  # Realistic proforma flow always has an order + allocation, which provides the
  # C_Order_ID. The setup below mirrors TC1's core (PO + payment-term + proforma +
  # allocation) but the assertions focus only on the payment-tagging contract.

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
      | Identifier   |
      | pt_lc        |
      | pt_immediate |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | ptb_lc     | pt_lc            | 30      | 0          | LC                | 10    |
      | ptb_od     | pt_lc            | 70      | 0          | OD                | 20    |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |


  # TC6: Proforma → Letter-of-Credit → Payment tagging flow
  # ─────────────────────────────────────────────────────────────────────────────
  # Tests the full split-payment path for the LC step:
  #   1. A purchase order with an LC-break payment term is created and completed.
  #   2. A purchase proforma (APF) invoice is created and allocated to the order.
  #      The allocation is the LC-break trigger: it stamps LC_Date on the order
  #      and transitions the LC pay-schedule step to Awaiting_Pay.
  #   3. The proforma appears in pay selection (gate fix, Task 9). Pay selection
  #      is completed and "Create Payments" is run.
  #   4. The generated payment must carry:
  #        Proforma_Invoice_ID = <apf invoice>   (explicit tag for reversal guard)
  #        IsPrepayment        = Y               (auto-derived from C_Order_ID ≠ 0)
  # ─────────────────────────────────────────────────────────────────────────────
  @from:cucumber
  @PaySelection
  @Proforma
  @Payment
  @Iteration2
  @MF_29368
  Scenario: TC6 - Payment created from a proforma pay-selection line is tagged with Proforma_Invoice_ID and IsPrepayment=Y
    # https://github.com/metasfresh/me03/issues/29368

    # PO + APF + allocate (provides the C_Order_ID linkage for MPayment.beforeSave).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | N       | vendor        | 2026-04-24  | POO         | wh             | pt_lc            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 1          |
    And the order identified by po is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | lcInvoice  | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price     |
      | lcInvoiceL1  | lcInvoice      | product      | 1 PCE       | 20596.32  |
    And the invoice identified by lcInvoice is completed

    And I allocate proforma 'lcInvoice' to order 'po'

    # PayDate is one day after the proforma's DateInvoiced (and therefore after the LC step's
    # DueDate, which recomputeLCStep stamps as LC_Date + offsetDays = 2026-04-24). With OnlyDue=Y
    # the SQL filter only picks up rows where DueDate <= PayDate — exactly the realistic flow.
    And metasfresh contains Pay Selection
      | Identifier | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySel     | org_EUR_account     | CT                  | 2026-04-25 |
    And "Create from..." is invoked for pay selection paySel, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | Y       |

    And the pay selection identified by paySel is completed
    Then "Create Payments" is invoked for pay selection paySel

    # "Create Payments" creates a C_Payment for every pay-selection-line.
    # The pay selection has 3 lines:
    #   Row 1 (LC step)  — no invoice link, amount = 30% of 68654.40 = 20596.32
    #                      auto-created by PaySelectionUpdater from the LC break.
    #   Row 2 (OD step)  — no invoice link, amount = 70% of 68654.40 = 48058.08
    #                      auto-created from the on-delivery break.
    #   Row 3 (APF line) — the proforma invoice; the generated payment must carry
    #                      Proforma_Invoice_ID + IsPrepayment=Y (assertion target).
    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID    |
      | -            | 20596.32 | payment_lc_step |
      | -            | 48058.08 | payment_od_step |
      | lcInvoice    | 20596.32 | payment         |

    Then validate payments
      | C_Payment_ID.Identifier | IsPrepayment | Proforma_Invoice_ID |
      | payment                 | Y            | lcInvoice             |
