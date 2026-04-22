@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor5
Feature: Tax Accounting Report ("Mehrwertsteuer-Verprobung 3")
##
## Coverage for the Tax Accounting Report — the user-facing DB function
## de_metas_acct.report_taxaccounts(...) invoked by AD_Process 585325
## ("Mehrwertsteuer-Verprobung(Excel) 3"). The underlying view
## de_metas_acct.tax_accounts_details_v is an implementation detail and is not asserted
## against directly.
##
## Each scenario creates its own dedicated C_Tax + C_VAT_Codes and the report is filtered
## by p_c_tax_id — no cross-scenario pollution. Every scenario uses 2 invoice lines with
## an unequal split (7+3 or 2+3 PCE) at PriceStd=100 so per-line-to-per-tax aggregation
## (§14(4) Nr. 7/8 UStG) is also validated. Per-scenario notes (§17 UStG verdicts,
## DiscountAmt sign direction) live in each scenario's comment block below.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # ESR (Swiss payment slip) interceptor fires on sales invoice completion and fails on some
    # local-polluted bank accounts; it has nothing to do with the Tax Accounting Report.
    And set sys config boolean value false for sys config InterceptorEnabled_de.metas.payment.esr.model.validator.C_Invoice#createEsrPaymentRequest
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T10:00:00+01:00[Europe/Berlin]
    And documents are accounted immediately

    # Force a 1:1 EUR<->CHF rate for the test window so the view's acct-currency
    # TaxAmt column equals the source-currency invoice amount, regardless of
    # whether the AD_Client's accounting schema is in EUR or CHF.
    And a 1:1 "EUR" <-> "CHF" conversion rate is in place between "2024-01-01" and "2024-01-31"

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList    | pricingSystem      | DE           | EUR           | true  |
      | purchasePriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID    |
      | salesPLV    | salesPriceList    |
      | purchasePLV | purchasePriceList |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | PO_PricingSystem_ID |
      | customer   | Y          | N        | pricingSystem      |                     |
      | vendor     | N          | Y        |                    | pricingSystem       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_location | customer      | Y               | Y               |
      | vendor_location   | vendor        | Y               | Y               |


# ############################################################################################################################################
# TC-S1 — Sales invoice (ARI) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_010
  @from:cucumber
  Scenario: sales invoice with regular 19% tax appears on T_Due with correct base and tax amounts

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales19    | tax19    | Y       |
      | purchase19 | tax19    | N       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # Tax-related Fact_Acct posting: ARI → T_Due_Acct CR 190 (USt liability).
    # C_VAT_Code_ID = sales19 (IsSOTrx=Y) because T_Due is the sales/output side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | -190        | tax19    |

    # Regression baseline across all aggregation levels. For sales invoices (ARI),
    # T_Due_Acct posts AmtAcctCr=190 → level 4 has TaxAmt=-190/NetAmt=-1000 and
    # levels 1/2/3/ReCap sum up to the same single-row amounts.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales19       |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | sales19       | T_Due_Acct            |            |            | -1000  | -190   | customer      | invoice    |
      | ReCap | sales19       |                       | -1000      | -190       |        |        | -             | -          |


# ############################################################################################################################################
# TC-S2 — Sales credit memo (ARC) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_020
  @from:cucumber
  Scenario: sales credit memo with regular 19% tax appears on T_Due with signs inverted from ARI

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales19    | tax19    | Y       |
      | purchase19 | tax19    | N       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # ARC → T_Due_Acct DR 190 (reverses the USt that an ARI would have posted CR).
    # C_VAT_Code_ID = sales19 (IsSOTrx=Y) because T_Due is the sales/output side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            | 190       |           | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | 190         | tax19    |

    # For ARC: signs are inverted vs ARI. T_Due_Acct posts AmtAcctDr=190, so TaxAmt=+190, NetAmt=+1000.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales19       |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 4     | sales19       | T_Due_Acct            |            |            | 1000   | 190    | customer      | invoice    |
      | ReCap | sales19       |                       | 1000       | 190        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S3 — Purchase invoice (API) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_030
  @from:cucumber
  Scenario: purchase invoice with regular 19% tax appears on T_Credit

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # API → T_Credit_Acct DR 190 (VSt receivable, input-tax deduction per §15 UStG).
    # C_VAT_Code_ID = purchase19 (IsSOTrx=N) because T_Credit is the purchase/input side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | tax19    | purchase19    | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 190         | tax19    |

    # For API: T_Credit_Acct posts AmtAcctDr=190, so TaxAmt=+190, NetAmt=+1000.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | invoice    |
      | ReCap | purchase19    |                       | 1000       | 190        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S4 — Purchase credit memo (APC) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_040
  @from:cucumber
  Scenario: purchase credit memo with regular 19% tax appears on T_Credit with signs inverted from API

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # APC → T_Credit_Acct CR 190 (reverses the VSt that an API would have posted DR).
    # C_VAT_Code_ID = purchase19 (IsSOTrx=N) because T_Credit is the purchase/input side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         |           | 190       | tax19    | purchase19    | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | -190        | tax19    |

    # For APC: signs are inverted vs API. T_Credit_Acct posts AmtAcctCr=190, so TaxAmt=-190, NetAmt=-1000.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | invoice    |
      | ReCap | purchase19    |                       | -1000      | -190       |        |        | -             | -          |


# ############################################################################################################################################
# TC-S5 — Zero-tax sales invoice (tax-exempt, e.g. §4 UStG)
# ############################################################################################################################################
  @Id:S0467_TAR_050
  @from:cucumber
  Scenario: zero-tax sales invoice produces a T_Due row with zero tax amount

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsTaxExempt |
      | tax0       | taxCategory      | 0    | DE                       | DE                        | Y           |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales0     | tax0     | Y       |
      | purchase0  | tax0     | N       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 2 PCE       | tax0     |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax0     |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # Zero-tax ARI (§4 UStG): T_Due_Acct posts a zero row carrying the TaxBaseAmt only.
    # C_VAT_Code_ID = sales0 (IsSOTrx=Y) — sales/output side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            | 0         | 0         | tax0     | sales0        | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | 0           | tax0     |

    # Zero-tax ARI: T_Due_Acct posts zero. TaxAmt=0, NetAmt=-500 (ARI sign flip applied to the 500 base).
    Then report_taxaccounts for C_Tax "tax0" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales0        |                       | -500       | 0          |        |        | -             | -          |
      | 2     | sales0        | T_Due_Acct            | -500       | 0          |        |        | -             | -          |
      | 3     | sales0        | T_Due_Acct            | -500       | 0          |        |        | -             | -          |
      | 4     | sales0        | T_Due_Acct            |            |            | -500   | 0      | customer      | invoice    |
      | ReCap | sales0        |                       | -500       | 0          |        |        | -             | -          |


# ############################################################################################################################################
# TC-S6 — Zero-tax purchase invoice (tax-exempt)
# ############################################################################################################################################
  @Id:S0467_TAR_060
  @from:cucumber
  Scenario: zero-tax purchase invoice produces a T_Credit row with zero tax amount

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsTaxExempt |
      | tax0       | taxCategory      | 0    | DE                       | DE                        | Y           |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase0  | tax0     | N       |
      | sales0     | tax0     | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 2 PCE       | tax0     |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax0     |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # Zero-tax API (§4/§15(2) UStG): T_Credit_Acct posts a zero row carrying the TaxBaseAmt only.
    # C_VAT_Code_ID = purchase0 (IsSOTrx=N) — purchase/input side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 0         | 0         | tax0     | purchase0     | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 0           | tax0     |

    # Zero-tax API: T_Credit_Acct posts zero. TaxAmt=0, NetAmt=+500.
    Then report_taxaccounts for C_Tax "tax0" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase0     |                       | 500        | 0          |        |        | -             | -          |
      | 2     | purchase0     | T_Credit_Acct         | 500        | 0          |        |        | -             | -          |
      | 3     | purchase0     | T_Credit_Acct         | 500        | 0          |        |        | -             | -          |
      | 4     | purchase0     | T_Credit_Acct         |            |            | 500    | 0      | vendor        | invoice    |
      | ReCap | purchase0     |                       | 500        | 0          |        |        | -             | -          |


# ############################################################################################################################################
# TC-S7 — Sales invoice (ARI) paid with early-payment discount (Skonto)
# ############################################################################################################################################
# The invoice posts the full tax (-190). When the payment is allocated with a discount, Doc_AllocationHdr
# creates a tax-correction Fact_Acct entry on T_Due_Acct that reduces the tax liability by the discount's
# tax portion. Exercises the view's C_AllocationHdr code path:
#   ROUND((AmtAcctDr - AmtAcctCr) * 100 / tax.Rate, 2)
# which back-computes TaxBaseAmt from TaxAmt via the tax rate.
#
# DiscountAmt sign convention (C_AllocationLine) — applied to all four Skonto scenarios
#   TC-S7/S9/S10/S11 below. C_AllocationLine has no IsSOTrx-like flag; the sign IS the direction:
#     positive = we receive (customer pays us a Skonto-reduced amount, or vendor refunds less)
#     negative = we pay    (we pay the supplier a Skonto-reduced amount, or we refund the customer)
#   Production-data check on a different customer (large volume) confirmed this per doctype:
#     ARI  84944+ /   863−  overwhelmingly positive (customer pays us)
#     API   1449+ /  6683−  mostly negative (we pay the supplier)
#     ARC    684+ /   517−  mixed
#     APC     30+ /    19−  mixed
#
# Direction used in this scenario: TC-S7 ARI = +23.80 EUR (customer paid, we received).
# §17(1)(1) UStG verdict: T_Due CR 190 (invoice) + DR 3.80 (alloc) → balance −186.20.
# USt liability reduced → §17-compliant.
  @Id:S0467_TAR_070
  @from:cucumber
  Scenario: sales invoice + discount-only allocation produces a tax-correction row alongside the invoice row

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales19    | tax19    | Y       |
      | purchase19 | tax19    | N       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # The discount's 19% tax portion is 3.80 EUR; the net portion is 20.00 EUR. No payment is
    # needed — Doc_AllocationHdr.createTaxCorrection only looks at DiscountAmt.
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | invoice      | 23.80 EUR   |

    And Wait until documents invoice, alloc are posted

    # ARI + discount: invoice posts T_Due CR 190; allocation's tax-correction row posts
    # T_Due DR 3.80 (USt is reduced per §17(1)(1) UStG because the customer paid less).
    # Expected net balance: -190 + 3.80 = -186.20. C_VAT_Code_ID = sales19 (IsSOTrx=Y)
    # on both legs — the correction copies the VATCode from the invoice's source Fact_Acct row.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Due_Acct            | 3.80      |           | tax19    | sales19       | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | -186.20     | tax19    |

    # Two level-4 rows (invoice + allocation discount correction). Subtotals:
    #   sum(TaxAmt) = -190 + 3.80 = -186.20
    #   sum(NetAmt) = -1000 + 20  = -980
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales19       |                       | -980       | -186.20    |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | -980       | -186.20    |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | -980       | -186.20    |        |        | -             | -          |
      | 4     | sales19       | T_Due_Acct            |            |            | -1000  | -190   | customer      | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | 20     | 3.80   | customer      | alloc      |
      | ReCap | sales19       |                       | -980       | -186.20    |        |        | -             | -          |


# ############################################################################################################################################
# TC-S8 — Mixed taxes: one sales invoice with 19% and 7% lines
# ############################################################################################################################################
# Demonstrates that a single invoice with multiple tax rates produces one T_Due_Acct row per tax,
# each reported separately via p_c_tax_id.
  @Id:S0467_TAR_080
  @from:cucumber
  Scenario: sales invoice with two tax rates produces one independent row per tax

    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory19 |
      | taxCategory7  |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory19    | 19   | DE                       | DE                        |
      | tax7       | taxCategory7     | 7    | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales19    | tax19    | Y       |
      | purchase19 | tax19    | N       |
      | sales7     | tax7     | Y       |
      | purchase7  | tax7     | N       |
    And metasfresh contains M_Products:
      | Identifier |
      | product19  |
      | product7   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product19    | 100.00   | PCE      | taxCategory19    |
      | salesPLV               | product7     | 100.00   | PCE      | taxCategory7     |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product19    | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product19    | 3 PCE       | tax19    |
      | invoiceL3  | invoice      | product7     | 2 PCE       | tax7     |
      | invoiceL4  | invoice      | product7     | 3 PCE       | tax7     |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # One T_Due_Acct Fact_Acct row per tax: 190 for 19% slice, 35 for 7% slice.
    # Both legs sales-side → IsSOTrx=Y VATCodes (sales19 / sales7).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | T_Due_Acct            |           | 35        | tax7     | sales7        | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | -190        | tax19    |
      | T_Due_Acct            | -35         | tax7     |

    # 19% slice: 1000 base + 190 tax
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales19       |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | sales19       | T_Due_Acct            |            |            | -1000  | -190   | customer      | invoice    |
      | ReCap | sales19       |                       | -1000      | -190       |        |        | -             | -          |

    # 7% slice: 500 base + 35 tax
    Then report_taxaccounts for C_Tax "tax7" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales7        |                       | -500       | -35        |        |        | -             | -          |
      | 2     | sales7        | T_Due_Acct            | -500       | -35        |        |        | -             | -          |
      | 3     | sales7        | T_Due_Acct            | -500       | -35        |        |        | -             | -          |
      | 4     | sales7        | T_Due_Acct            |            |            | -500   | -35    | customer      | invoice    |
      | ReCap | sales7        |                       | -500       | -35        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S9 — Sales credit memo (ARC) + discount-only allocation
# ############################################################################################################################################
# Credit memo posts T_Due_Acct Dr=190 (reversing the liability). When we pay the customer less than
# the full credit (keeping part as Skonto), Doc_AllocationHdr posts a correction with the opposite
# sign vs ARI.
#
# Direction used in this scenario: TC-S9 ARC = −23.80 EUR (we refunded the customer, they kept less
# — direction = "we pay"; see DiscountAmt sign convention in TC-S7's comment block).
# §17(1)(1) UStG verdict: T_Due DR 190 (invoice) + CR 3.80 (alloc) → balance +186.20. The ARC's
# initial USt reversal is partially rolled back → "reversed-less USt" → §17-compliant.
# Steuerberater sign-off required per TRACKING.md #18.
  @Id:S0467_TAR_090
  @from:cucumber
  Scenario: sales credit memo + discount-only allocation produces an inverted tax-correction row

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | sales19    | tax19    | Y       |
      | purchase19 | tax19    | N       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # ARC direction = "we pay" (refund to customer) → DiscountAmt negative per the
    # metasfresh C_AllocationLine sign convention (positive = we receive, negative = we pay).
    # Empirical production data confirms ARC splits roughly 57%+/43%- — both directions occur.
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | invoice      | -23.80 EUR  |

    And Wait until documents invoice, alloc are posted

    # ARC + negative discount (refund paid, Skonto kept): invoice posts T_Due DR 190
    # (reversing USt); allocation posts T_Due CR 3.80 (reverses LESS of the USt because
    # we refunded less to the customer). Net balance +186.20 per §17(1) UStG.
    # VATCode on both legs = sales19 (IsSOTrx=Y) — sales/output side; correction copies it.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            | 190       |           | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Due_Acct            |           | 3.80      | tax19    | sales19       | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | 186.20      | tax19    |

    # Two level-4 rows. Invoice (+190/+1000) + allocation correction (-3.80/-20).
    # Subtotals: +190-3.80=186.20 and +1000-20=980.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | sales19       |                       | 980        | 186.20     |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | 980        | 186.20     |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | 980        | 186.20     |        |        | -             | -          |
      | 4     | sales19       | T_Due_Acct            |            |            | 1000   | 190    | customer      | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | -20    | -3.80  | customer      | alloc      |
      | ReCap | sales19       |                       | 980        | 186.20     |        |        | -             | -          |


# ############################################################################################################################################
# TC-S10 — Purchase invoice (API) + discount-only allocation
# ############################################################################################################################################
# Purchase invoice posts T_Credit_Acct Dr=190 (tax receivable). When we pay the vendor less than the
# full amount (taking Skonto), the correction posts on T_Credit_Acct with opposite sign.
#
# Direction used in this scenario: TC-S10 API = −23.80 EUR (we paid supplier, they received less
# — direction = "we pay"; see DiscountAmt sign convention in TC-S7's comment block).
# §17(1)(2) UStG + §15(1a) UStG verdict: T_Credit DR 190 (invoice) + CR 3.80 (alloc) → balance
# +186.20. VSt claim reduced → §17-compliant.
  @Id:S0467_TAR_100
  @from:cucumber
  Scenario: purchase invoice + discount-only allocation produces a tax-correction row on T_Credit

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # API direction = "we pay" (supplier) → DiscountAmt negative per the C_AllocationLine
    # sign convention. Empirical production data confirms API is mostly negative (~82%).
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | invoice      | -23.80 EUR  |

    And Wait until documents invoice, alloc are posted

    # API + negative discount (supplier paid less, Skonto taken): invoice posts T_Credit DR 190
    # (claiming VSt); allocation posts T_Credit CR 3.80 (claims LESS VSt because we deducted
    # a Skonto from what we owed). Net balance +186.20 per §17(1)(2) and §15(1a) UStG.
    # VATCode on both legs = purchase19 (IsSOTrx=N) — purchase/input side; correction copies it.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | tax19    | purchase19    | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Credit_Acct         |           | 3.80      | tax19    | purchase19    | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 186.20      | tax19    |

    # Two level-4 rows. Invoice (+190/+1000) + allocation correction (-3.80/-20).
    # Subtotals: +190-3.80=186.20 and +1000-20=980.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | 980        | 186.20     |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -20    | -3.80  | vendor        | alloc      |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | invoice    |
      | ReCap | purchase19    |                       | 980        | 186.20     |        |        | -             | -          |


# ############################################################################################################################################
# TC-S10b — Purchase invoice (API) + Skonto via payment-based path (PaymentAllocationBuilder)
# ############################################################################################################################################
# Mirror of TC-S10 but the allocation is built indirectly via `PaymentAllocationBuilder`
# (path P — payment + auto-allocation) instead of the discount-only `C_AllocationHdr_Builder`
# (path D). Both paths must produce the same Fact_Acct rows and the same report output per
# §17(1)(2) UStG — the §17 obligation is on the underlying transaction, not the UI workflow
# that created the allocation.
#
# An older observation (pre-direction-signed DiscountAmt) showed path P emitting TaxAmt = −3.80
# on T_Credit_Acct while path D emitted +3.80 for the same API + Skonto scenario. The direction-
# signed DiscountAmt shipped on this issue likely already resolved it; this scenario is the
# empirical check (green = confirmed resolved; red = concrete finding).
  @Id:S0467_TAR_100b
  @from:cucumber
  Scenario: purchase invoice + Skonto via payment-based allocation produces the same tax-correction row as the discount-only path

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # AP payment (we pay vendor): invoice gross = 1190 EUR, we keep 23.80 as Skonto → PayAmt = 1166.20.
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | payment    | vendor        | 1166.20 EUR | false     | org_EUR_account     |
    And the payment identified by payment is completed

    # Path P: PaymentAllocationBuilder produces an allocation that combines the payment with the discount.
    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | DiscountAmt |
      | invoice      | payment      | 23.80 EUR   |

    # Register the resulting allocationHdr so Fact_Acct assertions can reference it by identifier.
    And register C_AllocationHdr from C_Payment:
      | C_Payment_ID | C_AllocationHdr_ID |
      | payment      | alloc              |

    And Wait until documents invoice, alloc are posted

    # Expected Fact_Acct rows: identical to TC-S10 (path D). Invoice posts T_Credit DR 190;
    # allocation posts T_Credit CR 3.80. Net balance +186.20 per §17(1)(2) UStG.
    # C_VAT_Code on both legs = purchase19 (IsSOTrx=N) — purchase/input side.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | tax19    | purchase19    | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Credit_Acct         |           | 3.80      | tax19    | purchase19    | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 186.20      | tax19    |

    # Expected report output: identical to TC-S10. If these assertions pass, R8b is confirmed
    # resolved by the direction-signed DiscountAmt convention shipped in PR 23496.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | 980        | 186.20     |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -20    | -3.80  | vendor        | alloc      |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | invoice    |
      | ReCap | purchase19    |                       | 980        | 186.20     |        |        | -             | -          |


# ############################################################################################################################################
# TC-S11 — Purchase credit memo (APC) + discount-only allocation
# ############################################################################################################################################
# Purchase credit memo posts T_Credit_Acct Cr=190 (reversing the receivable). When the vendor refunds
# us less than the full credit (keeping part), the correction posts on T_Credit_Acct with opposite
# sign vs APC.
#
# Direction used in this scenario: TC-S11 APC = +23.80 EUR (vendor refunded us, kept less for
# themselves — direction = "we receive"; see DiscountAmt sign convention in TC-S7's comment block).
# §17(1)(1) UStG verdict: T_Credit CR 190 (invoice) + DR 3.80 (alloc) → balance −186.20. The APC's
# initial VSt reversal is partially rolled back → "reversed-less VSt" → §17-compliant.
# Steuerberater sign-off required per TRACKING.md #18.
  @Id:S0467_TAR_110
  @from:cucumber
  Scenario: purchase credit memo + discount-only allocation produces an inverted tax-correction row

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # APC direction = "we receive" (refund from vendor) → DiscountAmt positive per the
    # C_AllocationLine sign convention. Empirical production data shows APC splits 61%+/39%-.
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | invoice      | 23.80 EUR   |

    And Wait until documents invoice, alloc are posted

    # APC + positive discount (vendor refunded less, we kept a Skonto): invoice posts
    # T_Credit CR 190 (reversing VSt); allocation posts T_Credit DR 3.80 (reverses LESS
    # VSt because we retained a Skonto on the refund). Net balance -186.20 per §17(1) UStG.
    # VATCode on both legs = purchase19 (IsSOTrx=N) — purchase/input side; correction copies it.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         |           | 190       | tax19    | purchase19    | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Credit_Acct         | 3.80      |           | tax19    | purchase19    | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | -186.20     | tax19    |

    # Two level-4 rows: invoice (-190/-1000) and allocation discount correction (+3.80/+20).
    # Subtotals: -190+3.80=-186.20 and -1000+20=-980.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | -980       | -186.20    |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | -980       | -186.20    |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | -980       | -186.20    |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | invoice    |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 20     | 3.80   | vendor        | alloc      |
      | ReCap | purchase19    |                       | -980       | -186.20    |        |        | -             | -          |


# ############################################################################################################################################
# TC-S12 — Reverse-Charge purchase invoice (API) with §13b UStG
# ############################################################################################################################################
# A §13b reverse-charge invoice posts two symmetric Fact_Acct rows:
#   T_Credit_Acct DR 190 (VSt, §15 UStG input-tax receivable)
#   T_Due_Acct    CR 190 (USt, §13b UStG notional output-tax payable)
# Both rows join the same C_InvoiceTax record (C_InvoiceTax.ReverseChargeTaxAmt = 190,
# TaxAmt = 0). With the RC symmetric-reporting fix in tax_accounts_details_v, both legs
# emit the same signed base and tax amount so KZ 67 (input deduction) and KZ 84/85 (output)
# are identically populated per §13b UStG Abs. 5 + §17(1) UStG.
#
# Legal basis: §13b UStG (https://www.gesetze-im-internet.de/ustg_1980/__13b.html) +
# §15 UStG + EU VAT Directive 2006/112/EC Art. 196 (reverse charge for B2B).
  @Id:S0467_TAR_120
  @from:cucumber
  Scenario: reverse-charge purchase invoice posts two symmetric Fact_Acct rows and appears on both T_Credit and T_Due

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
      | tax19      | taxCategory      | 19   | DE                       | DE                        | true            |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # RC posting: both tax legs fire. TaxAmt on the C_InvoiceTax is 0 (not payable);
    # ReverseChargeTaxAmt = 190 drives both Fact_Acct rows.
    # Per-leg VATCode: T_Credit (§13b input, UStVA KZ 67)    → purchase19     (IsSOTrx=N).
    # Per-leg VATCode: T_Due    (§13b output, UStVA KZ 84/85) → sales19 (IsSOTrx=Y).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | tax19    | purchase19    | invoice   |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 190         | tax19    |
      | T_Due_Acct            | -190        | tax19    |

    # Report: per-leg VATCodes produce one row per vatcode at levels 1/2/3/ReCap.
    # With the RC symmetric-reporting fix in tax_accounts_details_v, the output leg
    # (sales19 → KZ 84/85) now mirrors the input leg (purchase19 → KZ 67): both
    # show NetAmt_SUM = 1000 and TaxAmt_SUM = 190. Aligns with §13b UStG + §17(1) UStG
    # (equal base and tax on both KZ cells).
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | 1000       | 190        |        |        | -             | -          |
      | 1     | sales19       |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | 1000   | 190    | vendor        | invoice    |
      | ReCap | purchase19    |                       | 1000       | 190        |        |        | -             | -          |
      | ReCap | sales19       |                       | 1000       | 190        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S13 — Reverse-Charge purchase credit memo (APC) with §13b UStG
# ############################################################################################################################################
# Symmetric to TC-S12 on the credit-memo side. The APC reverses an earlier RC API:
#   T_Credit_Acct CR 190 (reverses the VSt receivable)
#   T_Due_Acct    DR 190 (reverses the notional USt)
# The view's DocBaseType outer CASE flips TaxBaseAmt to −1000 for APC (consistent with TC-S4
# non-RC APC). TaxAmt follows the ledger formula: −190 on T_Credit, +190 on T_Due.
#
# Legal basis: §13b UStG + §17(1) UStG + EU VAT Directive Art. 185 (adjustment of deductions).
  @Id:S0467_TAR_130
  @from:cucumber
  Scenario: reverse-charge purchase credit memo posts two symmetric reversal Fact_Acct rows

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
      | tax19      | taxCategory      | 19   | DE                       | DE                        | true            |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    # Per-leg VATCode: T_Credit → purchase19 (IsSOTrx=N); T_Due → sales19 (IsSOTrx=Y).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         |           | 190       | tax19    | purchase19    | invoice   |
      | T_Due_Acct            | 190       |           | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
    And Fact_Acct records balances for documents invoice are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | -190        | tax19    |
      | T_Due_Acct            | 190         | tax19    |

    # Symmetric negative report (APC doctype → TaxBaseAmt flipped to −1000 on both legs).
    # With the RC symmetric-reporting fix, the output leg mirrors the input leg:
    # T_Credit (purchase19 → KZ 67 reversal) and T_Due (sales19 → KZ 84/85 reversal)
    # both show NetAmt_SUM = −1000 and TaxAmt_SUM = −190.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | -1000      | -190       |        |        | -             | -          |
      | 1     | sales19       |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | -1000  | -190   | vendor        | invoice    |
      | ReCap | purchase19    |                       | -1000      | -190       |        |        | -             | -          |
      | ReCap | sales19       |                       | -1000      | -190       |        |        | -             | -          |


# ############################################################################################################################################
# TC-S14 — Reverse-Charge purchase invoice (API) + Skonto (discount-only allocation, path D)
# ############################################################################################################################################
# An RC invoice followed by a Skonto allocation posts symmetric tax-correction rows on
# BOTH T_Credit_Acct and T_Due_Acct — the §17 UStG adjustment fires on the RC tax
# regardless of cash-neutrality.
#
# DiscountAmt direction (per TC-S10 convention): API = "we pay" → DiscountAmt = −23.80 EUR.
#
# Legal basis: §13b + §17(1) UStG + EU VAT Directive Art. 90 + Art. 185.
  @Id:S0467_TAR_140
  @from:cucumber
  Scenario: reverse-charge purchase invoice + discount-only allocation produces symmetric correction rows

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
      | tax19      | taxCategory      | 19   | DE                       | DE                        | true            |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # API direction = "we pay" → DiscountAmt negative (consistent with TC-S10).
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | invoice      | -23.80 EUR  |

    And Wait until documents invoice, alloc are posted

    # Invoice posting (as TC-S12) + symmetric allocation correction on the RC tax.
    # Tax correction amount on RC + Skonto = DiscountAmt × TaxRate = 23.80 × 0.19 = 4.52 EUR
    # (not 3.80 as in non-RC — RC invoice has TaxAmt=0, so the correction base is the gross
    # DiscountAmt multiplied by the tax rate, not the tax portion of the gross).
    # VATCodes per leg: T_Credit → purchase19 (N); T_Due → sales19 (Y).
    # Allocation correction copies the per-leg VATCode from each source invoice Fact_Acct row.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | tax19    | purchase19    | invoice   |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Credit_Acct         |           | 4.52      | tax19    | purchase19    | alloc     |
      | T_Due_Acct            | 4.52      |           | tax19    | sales19       | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 185.48      | tax19    |
      | T_Due_Acct            | -185.48     | tax19    |

    # Invoice: T_Credit +1000/+190 + T_Due +1000/+190 (as TC-S12 — both sides symmetric
    # after the RC fix). Allocation (path D, DiscountAmt=-23.80, direction="we pay"):
    #   Tax correction amount = DiscountAmt × TaxRate = 23.80 × 0.19 = 4.52 EUR (note: RC uses
    #   gross × rate, unlike non-RC where Skonto is split into net+tax). Posted symmetrically:
    #     T_Credit_Acct DR=0, CR=4.52   → reduces KZ 67 by 4.52 (net −23.79)
    #     T_Due_Acct    DR=4.52, CR=0   → reduces KZ 84/85 by 4.52 (net −23.79)
    # Note: 23.79 vs 23.80 is a 1-cent rounding difference in the view's NetAmt calculation
    # (the view back-computes from tax × 100 / rate = 4.52 × 100 / 19 = 23.7894...).
    # Per §17(1) UStG + §13b UStG: both KZ 84/85 and KZ 67 reduce symmetrically → both
    # VATCodes end up at NetAmt_SUM 976.21 and TaxAmt_SUM 185.48.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | 976.21     | 185.48     |        |        | -             | -          |
      | 1     | sales19       |                       | 976.21     | 185.48     |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | 976.21     | 185.48     |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | 976.21     | 185.48     |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | 976.21     | 185.48     |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | 976.21     | 185.48     |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | invoice    |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -23.79 | -4.52  | vendor        | alloc      |
      | 4     | sales19       | T_Due_Acct            |            |            | 1000   | 190    | vendor        | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | -23.79 | -4.52  | vendor        | alloc      |
      | ReCap | purchase19    |                       | 976.21     | 185.48     |        |        | -             | -          |
      | ReCap | sales19       |                       | 976.21     | 185.48     |        |        | -             | -          |


# ############################################################################################################################################
# TC-S15 — Reverse-Charge purchase credit memo (APC) + Skonto (discount-only allocation, path D)
# ############################################################################################################################################
# Symmetric to TC-S14 on the credit-memo side. APC direction = "we receive" (vendor refunds
# us, we retain a Skonto) → DiscountAmt positive. Symmetric tax-correction rows on both
# T_Credit_Acct and T_Due_Acct per the RC allocation path.
#
# Legal basis: §13b + §17(1) UStG + EU VAT Directive Art. 185.
  @Id:S0467_TAR_150
  @from:cucumber
  Scenario: reverse-charge purchase credit memo + discount-only allocation produces symmetric correction rows

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
      | tax19      | taxCategory      | 19   | DE                       | DE                        | true            |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed

    # APC direction = "we receive" → positive DiscountAmt (consistent with TC-S11).
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | invoice      | 23.80 EUR   |

    And Wait until documents invoice, alloc are posted

    # Symmetric to TC-S14 with APC sign-flip. Invoice: T_Credit CR 190, T_Due DR 190.
    # Allocation (DiscountAmt=+23.80): T_Credit DR 4.52, T_Due CR 4.52. Correction copies the
    # per-leg VATCode from each source invoice Fact_Acct row.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         |           | 190       | tax19    | purchase19    | invoice   |
      | T_Due_Acct            | 190       |           | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Credit_Acct         | 4.52      |           | tax19    | purchase19    | alloc     |
      | T_Due_Acct            |           | 4.52      | tax19    | sales19       | alloc     |
      | *                     |           |           |          |               | alloc     |
    And Fact_Acct records balances for documents invoice,alloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | -185.48     | tax19    |
      | T_Due_Acct            | 185.48      | tax19    |

    # Symmetric to TC-S14 with APC sign-flip. Output leg mirrors input leg via the RC fix:
    # both NetAmt_SUM=-976.21 and TaxAmt_SUM=-185.48 (KZ 67 and KZ 84/85 both reduced).
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | -976.21    | -185.48    |        |        | -             | -          |
      | 1     | sales19       |                       | -976.21    | -185.48    |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | -976.21    | -185.48    |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | -976.21    | -185.48    |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | -976.21    | -185.48    |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | -976.21    | -185.48    |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | invoice    |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 23.79  | 4.52   | vendor        | alloc      |
      | 4     | sales19       | T_Due_Acct            |            |            | -1000  | -190   | vendor        | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | 23.79  | 4.52   | vendor        | alloc      |
      | ReCap | purchase19    |                       | -976.21    | -185.48    |        |        | -             | -          |
      | ReCap | sales19       |                       | -976.21    | -185.48    |        |        | -             | -          |


# ############################################################################################################################################
# TC-S16 — Reversed Reverse-Charge purchase invoice (API → Reverse-Correct)
# ############################################################################################################################################
# Per R7 of the requirements catalogue: a reversed RC invoice must produce sign-negated rows
# so that the per-(account, tax) sum is zero. The counter-invoice is also DocBaseType=API; its
# C_InvoiceTax row carries negated TaxBaseAmt / TaxAmt / ReverseChargeTaxAmt.
#
# Legal basis: §13b + §14(4) UStG.
  @Id:S0467_TAR_160
  @from:cucumber
  Scenario: reversed reverse-charge purchase invoice produces sign-negated rows summing to zero per account

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsReverseCharge |
      | tax19      | taxCategory      | 19   | DE                       | DE                        | true            |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | purchase19 | tax19    | N       |
      | sales19    | tax19    | Y       |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoice    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceL1  | invoice      | product      | 7 PCE       | tax19    |
      | invoiceL2  | invoice      | product      | 3 PCE       | tax19    |
    And the invoice identified by invoice is completed
    And the invoice identified by invoice is reversed
    And the reversal of invoice invoice is identified by reversal
    And Wait until documents invoice, reversal are posted

    # Original invoice posts two legs with per-leg VATCodes (like TC-S12). Reversal negates
    # both amounts on the SAME DR/CR sides (not swapped) and keeps the same per-leg VATCodes.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | tax19    | purchase19    | invoice   |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |
      | T_Credit_Acct         | -190      |           | tax19    | purchase19    | reversal  |
      | T_Due_Acct            |           | -190      | tax19    | sales19       | reversal  |
      | *                     |           |           |          |               | reversal  |

    # Sum per (account, tax) must be zero after reversal (R7).
    And Fact_Acct records balances for documents invoice,reversal are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 0           | tax19    |
      | T_Due_Acct            | 0           | tax19    |

    # Original invoice + reversal: level-4 row pair sums to zero per (account, tax) → R7.
    # With the RC fix, level-4 T_Due rows mirror T_Credit rows (same NetAmt + same TaxAmt).
    # Level-1 / 2 / 3 / ReCap all sum to 0 per VATCode.
    Then report_taxaccounts for C_Tax "tax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | purchase19    |                       | 0          | 0          |        |        | -             | -          |
      | 1     | sales19       |                       | 0          | 0          |        |        | -             | -          |
      | 2     | purchase19    | T_Credit_Acct         | 0          | 0          |        |        | -             | -          |
      | 2     | sales19       | T_Due_Acct            | 0          | 0          |        |        | -             | -          |
      | 3     | purchase19    | T_Credit_Acct         | 0          | 0          |        |        | -             | -          |
      | 3     | sales19       | T_Due_Acct            | 0          | 0          |        |        | -             | -          |
      | 4     | purchase19    | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | invoice    |
      | 4     | purchase19    | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | reversal   |
      | 4     | sales19       | T_Due_Acct            |            |            | 1000   | 190    | vendor        | invoice    |
      | 4     | sales19       | T_Due_Acct            |            |            | -1000  | -190   | vendor        | reversal   |
      | ReCap | purchase19    |                       | 0          | 0          |        |        | -             | -          |
      | ReCap | sales19       |                       | 0          | 0          |        |        | -             | -          |
