@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor5
Feature: Reverse Charge tax — accounting posting for purchase documents
## me03#28726: Support Reverse Charge with explicit Fact_Acct
##
## When a C_Tax has IsReverseCharge='Y', the invoice posting engine creates
## two offsetting Fact_Acct lines (T_Credit_Acct = VSt, T_Due_Acct = USt)
## that net to zero. The invoice grand total is unchanged (RC tax is not payable).
##
## These tests cover purchase invoice (API) and purchase credit memo (APC) only.
## Sales-side RC posting (ARI, ARC) is not yet implemented — see out-of-scope in REQUIREMENTS.md.
##
## Every RC scenario includes an allocation with discount (Skonto) to verify
## that no tax correction is posted for the RC tax (Decision D1: BC-style skip).
##
## Reversal tests cover only API and APC (not ARI/ARC). The posting engine
## is the same for all doc types — the forward-path sign differences are already
## exercised by TC1/TC2, so additional reversal tests would add no coverage.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T10:00:00+01:00[Europe/Berlin]
    And documents are accounted immediately

    And metasfresh contains C_TaxCategory
      | Identifier    |
      | rcTaxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
      | rcTax19    | rcTaxCategory    | 19   | DE                       | DE                        | true            |

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID    |
      | purchasePLV | purchasePriceList |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd  | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 1000.00   | PCE      | rcTaxCategory    |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | PO_PricingSystem_ID |
      | vendor     | N          | Y        | pricingSystem       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_location | vendor        | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier  | C_Currency_ID |
      | bankAccount | EUR           |


# ############################################################################################################################################
# TC1 — Purchase invoice (API) with RC + Skonto allocation
# Primary acceptance test: RC posting fires, allocation has no RC tax correction
# ############################################################################################################################################
  @Id:S0466_RC_010
  @from:cucumber
  Scenario: purchase invoice with reverse charge tax posts two offsetting lines, allocation with discount has no RC tax correction

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | rcInvoice  | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | rcInvLine  | rcInvoice    | product      | 1 PCE       | rcTax19  |
    And the invoice identified by rcInvoice is completed

    # GrandTotal = TotalLines = 1000 (RC tax is not payable, doesn't increase the total)
    Then validate created invoices
      | C_Invoice_ID | GrandTotal | TotalLines  |
      | rcInvoice    | 1000 EUR   | 1000        |

    # C_InvoiceTax: TaxAmt=0 (not payable), ReverseChargeTaxAmt=190 (for declaration)
    And C_InvoiceTax records for invoice "rcInvoice" are matching
      | C_Tax_ID | TaxAmt | TaxBaseAmt | ReverseChargeTaxAmt | IsReverseCharge |
      | rcTax19  | 0      | 1000       | 190                 | true            |

    # Invoice posting: expense DR, liability CR, VSt DR (tax receivable), USt CR (tax payable)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID |
      | V_Liability_Acct      |             | 1000 EUR    | -        | rcInvoice |
      | P_Expense_Acct        | 1000 EUR    |             | rcTax19  | rcInvoice |
      | T_Credit_Acct         | 190 EUR     |             | rcTax19  | rcInvoice |
      | T_Due_Acct            |             | 190 EUR     | rcTax19  | rcInvoice |

    # Payment with discount (Skonto): pay 970, discount 30
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | rcPayment  | vendor        | 970 EUR  | false     | bankAccount         |
    And the payment identified by rcPayment is completed

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | C_Payment_ID | Amount   | DiscountAmt |
      | rcAlloc            | rcInvoice    | rcPayment    | 970 EUR  | 30 EUR      |

    # Allocation posting: payable clearing + payment + discount + RC tax correction on discount.
    # Note: allocation uses negated amounts on same side (not flipped DR/CR).
    # The RC tax correction adjusts both VSt (T_Credit) and USt (T_Due) for the discount amount:
    #   discount 30 EUR * 19% = 5.70 EUR tax correction per leg.
    # Both legs net to zero in cash terms but are required for correct VAT declaration (§17 UStG).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID |
      | V_Liability_Acct      | -1000 EUR   |             | -        | rcAlloc   |
      | B_PaymentSelect_Acct  |             | -970 EUR    | -        | rcAlloc   |
      | PayDiscount_Rev_Acct  |             | -30 EUR     | rcTax19  | rcAlloc   |
      | PayDiscount_Rev_Acct  | -5.70 EUR   |             | rcTax19  | rcAlloc   |
      | T_Credit_Acct         |             | -5.70 EUR   | rcTax19  | rcAlloc   |
      | T_Due_Acct            | -5.70 EUR   |             | rcTax19  | rcAlloc   |
      | PayDiscount_Rev_Acct  |             | -5.70 EUR   | rcTax19  | rcAlloc   |


# ############################################################################################################################################
# TC2 — Purchase credit memo (APC) with RC + Skonto allocation
# ############################################################################################################################################
  @Id:S0466_RC_020
  @from:cucumber
  Scenario: purchase credit memo with reverse charge tax posts two offsetting lines on CR side

    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | C_DocTypeTarget_ID.Name         | DateInvoiced | IsSOTrx | C_Currency_ID |
      | rcCreditMemo  | vendor        | Gutschrift (Lieferant)          | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | rcCmLine      | rcCreditMemo | product      | 1 PCE       | rcTax19  |
    And the invoice identified by rcCreditMemo is completed

    Then validate created invoices
      | C_Invoice_ID | GrandTotal | TotalLines  |
      | rcCreditMemo | 1000 EUR   | 1000        |

    And C_InvoiceTax records for invoice "rcCreditMemo" are matching
      | C_Tax_ID | TaxAmt | TaxBaseAmt | ReverseChargeTaxAmt | IsReverseCharge |
      | rcTax19  | 0      | 1000       | 190                 | true            |

    # APC posting: Liability DR, Expense CR, VSt CR (reverses debit), USt DR (reverses credit)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID    |
      | V_Liability_Acct      | 1000 EUR    |             | -        | rcCreditMemo |
      | P_Expense_Acct        |             | 1000 EUR    | rcTax19  | rcCreditMemo |
      | T_Credit_Acct         |             | 190 EUR     | rcTax19  | rcCreditMemo |
      | T_Due_Acct            | 190 EUR     |             | rcTax19  | rcCreditMemo |


# ############################################################################################################################################
# TC3 — Reversal of RC purchase invoice (API)
# ############################################################################################################################################
  @Id:S0466_RC_030
  @from:cucumber
  Scenario: reversal of RC purchase invoice cancels the RC Fact_Acct lines

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | rcInvToReverse  | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier        | C_Invoice_ID   | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | rcInvToReverseLn  | rcInvToReverse | product      | 1 PCE       | rcTax19  |
    And the invoice identified by rcInvToReverse is completed

    # Verify original posting: VSt DR 190 (receivable), USt CR 190 (payable)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID      |
      | V_Liability_Acct      |             | 1000 EUR    | -        | rcInvToReverse |
      | P_Expense_Acct        | 1000 EUR    |             | rcTax19  | rcInvToReverse |
      | T_Credit_Acct         | 190 EUR     |             | rcTax19  | rcInvToReverse |
      | T_Due_Acct            |             | 190 EUR     | rcTax19  | rcInvToReverse |

    And the invoice identified by rcInvToReverse is reversed
    And the reversal of invoice rcInvToReverse is identified by rcInvReversal

    # Reversal: Reverse-Correct negates amounts on the SAME side.
    # Original: V_Liability CR 1000, P_Expense DR 1000, T_Credit DR 190, T_Due CR 190
    # Reversal: V_Liability CR -1000, P_Expense DR -1000, T_Credit DR -190, T_Due CR -190
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID     |
      | V_Liability_Acct      |             | -1000 EUR   | -        | rcInvReversal |
      | P_Expense_Acct        | -1000 EUR   |             | rcTax19  | rcInvReversal |
      | T_Credit_Acct         | -190 EUR    |             | rcTax19  | rcInvReversal |
      | T_Due_Acct            |             | -190 EUR    | rcTax19  | rcInvReversal |

    # Sum of original + reversal on T_Credit_Acct = 0, T_Due_Acct = 0


# ############################################################################################################################################
# TC4 — Reversal of RC purchase credit memo (APC)
# ############################################################################################################################################
  @Id:S0466_RC_040
  @from:cucumber
  Scenario: reversal of RC purchase credit memo cancels the RC Fact_Acct lines

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | C_DocTypeTarget_ID.Name         | DateInvoiced | IsSOTrx | C_Currency_ID |
      | rcCmToReverse  | vendor        | Gutschrift (Lieferant)          | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID  | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | rcCmToReverseLn  | rcCmToReverse | product      | 1 PCE       | rcTax19  |
    And the invoice identified by rcCmToReverse is completed

    # Verify original APC posting: Liability DR, Expense CR, VSt CR, USt DR
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID     |
      | V_Liability_Acct      | 1000 EUR    |             | -        | rcCmToReverse |
      | P_Expense_Acct        |             | 1000 EUR    | rcTax19  | rcCmToReverse |
      | T_Credit_Acct         |             | 190 EUR     | rcTax19  | rcCmToReverse |
      | T_Due_Acct            | 190 EUR     |             | rcTax19  | rcCmToReverse |

    And the invoice identified by rcCmToReverse is reversed
    And the reversal of invoice rcCmToReverse is identified by rcCmReversal

    # Reversal: negates amounts on the SAME side.
    # Original APC: V_Liability DR 1000, P_Expense CR 1000, T_Credit CR 190, T_Due DR 190
    # Reversal:     V_Liability DR -1000, P_Expense CR -1000, T_Credit CR -190, T_Due DR -190
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_Tax_ID | Record_ID    |
      | V_Liability_Acct      | -1000 EUR   |             | -        | rcCmReversal |
      | P_Expense_Acct        |             | -1000 EUR   | rcTax19  | rcCmReversal |
      | T_Credit_Acct         |             | -190 EUR    | rcTax19  | rcCmReversal |
      | T_Due_Acct            | -190 EUR    |             | rcTax19  | rcCmReversal |
