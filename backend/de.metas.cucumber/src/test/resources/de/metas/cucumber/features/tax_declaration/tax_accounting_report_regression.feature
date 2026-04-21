@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor5
Feature: Tax Accounting Report ("Mehrwertsteuer-Verprobung 3") — regression
## me03#29361: Support Reverse Charge with explicit fact acct - Tax Accounting Report fixes
##
## Regression tests for the Tax Accounting Report — the user-facing DB function
## de_metas_acct.report_taxaccounts(...) invoked by AD_Process 585325
## ("Mehrwertsteuer-Verprobung(Excel) 3"). The underlying view
## de_metas_acct.tax_accounts_details_v is an implementation detail and is not asserted
## against directly.
##
## These tests lock in the CURRENT behaviour for standard cases (regular 19% tax and
## zero-tax) so that the upcoming Reverse Charge / bug-fix PR does not silently change
## non-RC output. Customer-validated scenarios (TC-S1..TC-S8) are treated as correct.
##
## Each scenario creates its own dedicated C_Tax so that p_c_tax_id filters the report
## to scenario-scoped rows — no cross-scenario pollution. Every scenario uses 2 invoice
## lines with an unequal split (7 PCE + 3 PCE or 2 PCE + 3 PCE) at PriceStd=100, so
## per-line-to-per-tax aggregation (§14(4) Nr. 7/8 UStG) is also validated.
##
## ============================================================================================
## KNOWN ISSUES — expected values that are (or might be) buggy
## ============================================================================================
## Documented so the fix PR (sub-PR 2) has an explicit baseline. See ai-work/29361/REQUIREMENTS.md
## for the full analysis and legal reasoning.
##
## [BUG A.1] TaxAmt sign on T_Due for Reverse Charge (REQUIREMENTS.md §2.1)
##   The view computes taxamt = AmtAcctDr - AmtAcctCr, which gives -190 on T_Due for
##   sales invoices. For RC §13b UStG that's wrong (must be +190 positive output tax).
##   For non-RC (our scope here) the minus is the conventional credit-balance rendering;
##   treated as CORRECT by customers and locked in as-is in TC-S1..TC-S8.
##
## [BUG A.2] TaxBaseAmt double-counted across T_Due + T_Credit (REQUIREMENTS.md §2.1)
##   Both Fact_Acct rows for a tax join the same C_InvoiceTax, so per-account subtotals
##   each report the full base. This matters for RC (both accounts have rows) — for our
##   non-RC scope only one account has a row per tax, so the bug is latent and untested.
##   The fix PR must ensure this test still passes when the double-count is corrected.
##
## [BUG A.4] Excel "Total Amount" = TaxBaseAmt + TaxAmt per row (REQUIREMENTS.md §2.1)
##   Produces 1190 / 810, neither of which equals the invoice total. This column is in the
##   Excel template, not in the DB function output, so it is NOT tested here. Flagged so
##   the fix PR addresses it.
##
## [BUG §2.3] Doc_AllocationHdr tax-correction path inconsistency (REQUIREMENTS.md §2.3)
##   TC-S7  (ARI + Skonto): T_Due +3.80 — same output in both payment-based and
##                          discount-only paths. Customer-validated.
##   TC-S9  (ARC + Skonto): T_Due +3.80 — discount-only path produces a correction row,
##                          payment-based path does NOT (see R8a). Sign pending
##                          Steuerberater confirmation before sub-PR 2 integration.
##
##   ⚠️ TC-S10 (API + Skonto): T_Credit +3.80 — MUST-FIX in sub-PR 2 (REQUIREMENTS.md §2.3.1).
##                          The locked-in +3.80 VIOLATES §17(1)(1) UStG and EU VAT
##                          Directive Art. 185(1): when a recipient takes a Skonto the
##                          Vorsteuerabzug MUST be REDUCED, not increased. The ledger
##                          convention (AmtAcctDr − AmtAcctCr) means the correct sign
##                          is −3.80 (CR on T_Credit_Acct), which is what the
##                          payment-based allocation path already produces. The fix
##                          PR must: (a) make Doc_AllocationHdr.createTaxCorrection post
##                          the discount-only path with the same sign as the
##                          payment-based path, AND (b) re-assert TC-S10 to
##                          −3.80 / −20 on the level-4 correction row, +186.20 / +980
##                          on the level 1/2/3/ReCap subtotals.
##
##   TC-S11 (APC + Skonto): T_Credit +3.80 — same "correction row missing in payment-based
##                          path" situation as TC-S9. Sign pending Steuerberater
##                          confirmation before sub-PR 2 integration.
##
## [VAT code] "Keine MwSt." appears as vatcode on every row
##   Because the test-created C_Tax has no C_VAT_Code_ID linked. The function falls back
##   to the getmessage('notax') label. This is out of scope — tracked separately via
##   https://github.com/metasfresh/me03/issues/29363.
##
## Scenarios not flagged above are believed fully correct and customer-validated.
## ============================================================================================

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
      | Identifier        |
      | salesTaxCategory  |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | salesTax19 | salesTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier  |
      | salesProd1  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | salesProd1   |   100.00   | PCE      | salesTaxCategory |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | ariInv     | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | ariInvL1_a | ariInv       | salesProd1   | 7 PCE       | salesTax19 |
      | ariInvL1_b | ariInv       | salesProd1   | 3 PCE       | salesTax19 |
    And the invoice identified by ariInv is completed
    And Wait until documents ariInv is posted

    # Regression baseline across all aggregation levels. For sales invoices (ARI),
    # T_Due_Acct posts AmtAcctCr=190 → level 4 has TaxAmt=-190/NetAmt=-1000 and
    # levels 1/2/3/ReCap sum up to the same single-row amounts.
    Then report_taxaccounts for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | T_Due_Acct            |            |            | -1000  | -190   | customer      | ariInv     |
      | ReCap |                       | -1000      | -190       |        |        | -             | -          |


# ############################################################################################################################################
# TC-S2 — Sales credit memo (ARC) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_020
  @from:cucumber
  Scenario: sales credit memo with regular 19% tax appears on T_Due with signs inverted from ARI

    And metasfresh contains C_TaxCategory
      | Identifier         |
      | arcTaxCategory     |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | arcTax19   | arcTaxCategory   | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier |
      | arcProd1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | arcProd1     |   100.00   | PCE      | arcTaxCategory   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | arcInv     | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | arcInvL1_a | arcInv       | arcProd1     | 7 PCE       | arcTax19   |
      | arcInvL1_b | arcInv       | arcProd1     | 3 PCE       | arcTax19   |
    And the invoice identified by arcInv is completed
    And Wait until documents arcInv is posted

    # For ARC: signs are inverted vs ARI. T_Due_Acct posts AmtAcctDr=190, so TaxAmt=+190, NetAmt=+1000.
    Then report_taxaccounts for C_Tax "arcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 3     | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 4     | T_Due_Acct            |            |            | 1000   | 190    | customer      | arcInv     |
      | ReCap |                       | 1000       | 190        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S3 — Purchase invoice (API) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_030
  @from:cucumber
  Scenario: purchase invoice with regular 19% tax appears on T_Credit

    And metasfresh contains C_TaxCategory
      | Identifier       |
      | apiTaxCategory   |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | apiTax19   | apiTaxCategory   | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier |
      | apiProd1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | apiProd1     |   100.00   | PCE      | apiTaxCategory   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apiInv     | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | apiInvL1_a | apiInv       | apiProd1     | 7 PCE       | apiTax19   |
      | apiInvL1_b | apiInv       | apiProd1     | 3 PCE       | apiTax19   |
    And the invoice identified by apiInv is completed
    And Wait until documents apiInv is posted

    # For API: T_Credit_Acct posts AmtAcctDr=190, so TaxAmt=+190, NetAmt=+1000.
    Then report_taxaccounts for C_Tax "apiTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 3     | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 4     | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | apiInv     |
      | ReCap |                       | 1000       | 190        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S4 — Purchase credit memo (APC) with regular 19% tax
# ############################################################################################################################################
  @Id:S0467_TAR_040
  @from:cucumber
  Scenario: purchase credit memo with regular 19% tax appears on T_Credit with signs inverted from API

    And metasfresh contains C_TaxCategory
      | Identifier       |
      | apcTaxCategory   |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | apcTax19   | apcTaxCategory   | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier |
      | apcProd1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | apcProd1     |   100.00   | PCE      | apcTaxCategory   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apcInv     | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | apcInvL1_a | apcInv       | apcProd1     | 7 PCE       | apcTax19   |
      | apcInvL1_b | apcInv       | apcProd1     | 3 PCE       | apcTax19   |
    And the invoice identified by apcInv is completed
    And Wait until documents apcInv is posted

    # For APC: signs are inverted vs API. T_Credit_Acct posts AmtAcctCr=190, so TaxAmt=-190, NetAmt=-1000.
    Then report_taxaccounts for C_Tax "apcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 3     | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 4     | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | apcInv     |
      | ReCap |                       | -1000      | -190       |        |        | -             | -          |


# ############################################################################################################################################
# TC-S5 — Zero-tax sales invoice (tax-exempt, e.g. §4 UStG)
# ############################################################################################################################################
  @Id:S0467_TAR_050
  @from:cucumber
  Scenario: zero-tax sales invoice produces a T_Due row with zero tax amount

    And metasfresh contains C_TaxCategory
      | Identifier             |
      | exemptSalesTaxCategory |
    And metasfresh contains C_Tax
      | Identifier        | C_TaxCategory_ID        | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsTaxExempt |
      | exemptSalesTax    | exemptSalesTaxCategory  | 0    | DE                       | DE                        | Y           |
    And metasfresh contains M_Products:
      | Identifier      |
      | exemptSalesProd |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID | C_TaxCategory_ID        |
      | salesPLV               | exemptSalesProd |  100.00  | PCE      | exemptSalesTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | exemptAriInv  | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID     | M_Product_ID     | QtyInvoiced      | C_Tax_ID         |
      | exemptAriInvL1_a | exemptAriInv     | exemptSalesProd  | 2 PCE            | exemptSalesTax   |
      | exemptAriInvL1_b | exemptAriInv     | exemptSalesProd  | 3 PCE            | exemptSalesTax   |
    And the invoice identified by exemptAriInv is completed
    And Wait until documents exemptAriInv is posted

    # Zero-tax ARI: T_Due_Acct posts zero. TaxAmt=0, NetAmt=-500 (ARI sign flip applied to the 500 base).
    Then report_taxaccounts for C_Tax "exemptSalesTax" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo   |
      | 1     |                       | -500       | 0          |        |        | -             | -            |
      | 2     | T_Due_Acct            | -500       | 0          |        |        | -             | -            |
      | 3     | T_Due_Acct            | -500       | 0          |        |        | -             | -            |
      | 4     | T_Due_Acct            |            |            | -500   | 0      | customer      | exemptAriInv |
      | ReCap |                       | -500       | 0          |        |        | -             | -            |


# ############################################################################################################################################
# TC-S6 — Zero-tax purchase invoice (tax-exempt)
# ############################################################################################################################################
  @Id:S0467_TAR_060
  @from:cucumber
  Scenario: zero-tax purchase invoice produces a T_Credit row with zero tax amount

    And metasfresh contains C_TaxCategory
      | Identifier                |
      | exemptPurchaseTaxCategory |
    And metasfresh contains C_Tax
      | Identifier        | C_TaxCategory_ID           | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsTaxExempt |
      | exemptPurchaseTax | exemptPurchaseTaxCategory  | 0    | DE                       | DE                        | Y           |
    And metasfresh contains M_Products:
      | Identifier          |
      | exemptPurchaseProd  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID        | PriceStd | C_UOM_ID | C_TaxCategory_ID           |
      | purchasePLV            | exemptPurchaseProd  |  100.00  | PCE      | exemptPurchaseTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | exemptApiInv  | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier         | C_Invoice_ID       | M_Product_ID       | QtyInvoiced        | C_Tax_ID           |
      | exemptApiInvL1_a   | exemptApiInv       | exemptPurchaseProd | 2 PCE              | exemptPurchaseTax  |
      | exemptApiInvL1_b   | exemptApiInv       | exemptPurchaseProd | 3 PCE              | exemptPurchaseTax  |
    And the invoice identified by exemptApiInv is completed
    And Wait until documents exemptApiInv is posted

    # Zero-tax API: T_Credit_Acct posts zero. TaxAmt=0, NetAmt=+500.
    Then report_taxaccounts for C_Tax "exemptPurchaseTax" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo   |
      | 1     |                       | 500        | 0          |        |        | -             | -            |
      | 2     | T_Credit_Acct         | 500        | 0          |        |        | -             | -            |
      | 3     | T_Credit_Acct         | 500        | 0          |        |        | -             | -            |
      | 4     | T_Credit_Acct         |            |            | 500    | 0      | vendor        | exemptApiInv |
      | ReCap |                       | 500        | 0          |        |        | -             | -            |


# ############################################################################################################################################
# TC-S7 — Sales invoice (ARI) paid with early-payment discount (Skonto)
# ############################################################################################################################################
# The invoice posts the full tax (-190). When the payment is allocated with a discount, Doc_AllocationHdr
# creates a tax-correction Fact_Acct entry on T_Due_Acct that reduces the tax liability by the discount's
# tax portion. Exercises the view's C_AllocationHdr code path:
#   ROUND((AmtAcctDr - AmtAcctCr) * 100 / tax.Rate, 2)
# which back-computes TaxBaseAmt from TaxAmt via the tax rate.
  @Id:S0467_TAR_070
  @from:cucumber
  Scenario: sales invoice + discount-only allocation produces a tax-correction row alongside the invoice row

    And metasfresh contains C_TaxCategory
      | Identifier      |
      | allocTaxCategory |
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocTax19  | allocTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier |
      | allocProd  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID  |
      | salesPLV               | allocProd    |   100.00   | PCE      | allocTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocSalesInv  | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier        | C_Invoice_ID      | M_Product_ID      | QtyInvoiced       | C_Tax_ID          |
      | allocSalesInvL1_a | allocSalesInv     | allocProd         | 7 PCE             | allocTax19        |
      | allocSalesInvL1_b | allocSalesInv     | allocProd         | 3 PCE             | allocTax19        |
    And the invoice identified by allocSalesInv is completed

    # The discount's 19% tax portion is 3.80 EUR; the net portion is 20.00 EUR. No payment is
    # needed — Doc_AllocationHdr.createTaxCorrection only looks at DiscountAmt.
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID  | DiscountAmt |
      | ariAlloc           | allocSalesInv | 23.80 EUR   |

    And Wait until documents allocSalesInv, ariAlloc are posted

    # Two level-4 rows (invoice + allocation discount correction). Subtotals:
    #   sum(TaxAmt) = -190 + 3.80 = -186.20
    #   sum(NetAmt) = -1000 + 20  = -980
    Then report_taxaccounts for C_Tax "allocTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo    |
      | 1     |                       | -980       | -186.20    |        |        | -             | -             |
      | 2     | T_Due_Acct            | -980       | -186.20    |        |        | -             | -             |
      | 3     | T_Due_Acct            | -980       | -186.20    |        |        | -             | -             |
      | 4     | T_Due_Acct            |            |            | -1000  | -190   | customer      | allocSalesInv |
      | 4     | T_Due_Acct            |            |            | 20     | 3.80   | customer      | ariAlloc      |
      | ReCap |                       | -980       | -186.20    |        |        | -             | -             |


# ############################################################################################################################################
# TC-S8 — Mixed taxes: one sales invoice with 19% and 7% lines
# ############################################################################################################################################
# Demonstrates that a single invoice with multiple tax rates produces one T_Due_Acct row per tax,
# each reported separately via p_c_tax_id.
  @Id:S0467_TAR_080
  @from:cucumber
  Scenario: sales invoice with two tax rates produces one independent row per tax

    And metasfresh contains C_TaxCategory
      | Identifier      |
      | mixTaxCategory19 |
      | mixTaxCategory7  |
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | mixTax19    | mixTaxCategory19  | 19   | DE                       | DE                        |
      | mixTax7     | mixTaxCategory7   | 7    | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier |
      | mixProd19  |
      | mixProd7   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID  |
      | salesPLV               | mixProd19    |   100.00   | PCE      | mixTaxCategory19  |
      | salesPLV               | mixProd7     |  100.00  | PCE      | mixTaxCategory7   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | mixInv     | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | mixInvL1_a | mixInv       | mixProd19    | 7 PCE       | mixTax19   |
      | mixInvL1_b | mixInv       | mixProd19    | 3 PCE       | mixTax19   |
      | mixInvL2_a | mixInv       | mixProd7     | 2 PCE       | mixTax7    |
      | mixInvL2_b | mixInv       | mixProd7     | 3 PCE       | mixTax7    |
    And the invoice identified by mixInv is completed
    And Wait until documents mixInv is posted

    # 19% slice: 1000 base + 190 tax
    Then report_taxaccounts for C_Tax "mixTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | T_Due_Acct            |            |            | -1000  | -190   | customer      | mixInv     |
      | ReCap |                       | -1000      | -190       |        |        | -             | -          |

    # 7% slice: 500 base + 35 tax
    Then report_taxaccounts for C_Tax "mixTax7" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     |                       | -500       | -35        |        |        | -             | -          |
      | 2     | T_Due_Acct            | -500       | -35        |        |        | -             | -          |
      | 3     | T_Due_Acct            | -500       | -35        |        |        | -             | -          |
      | 4     | T_Due_Acct            |            |            | -500   | -35    | customer      | mixInv     |
      | ReCap |                       | -500       | -35        |        |        | -             | -          |


# ############################################################################################################################################
# TC-S9 — Sales credit memo (ARC) + discount-only allocation
# ############################################################################################################################################
# Credit memo posts T_Due_Acct Dr=190 (reversing the liability). When we pay the customer less than
# the full credit (keeping part as Skonto), Doc_AllocationHdr posts a correction with the opposite
# sign vs ARI.
  @Id:S0467_TAR_090
  @from:cucumber
  Scenario: sales credit memo + discount-only allocation produces an inverted tax-correction row

    And metasfresh contains C_TaxCategory
      | Identifier        |
      | allocArcTaxCategory |
    And metasfresh contains C_Tax
      | Identifier     | C_TaxCategory_ID     | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocArcTax19  | allocArcTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier   |
      | allocArcProd |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID     |
      | salesPLV               | allocArcProd |   100.00   | PCE      | allocArcTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocArcInv     | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID    | M_Product_ID    | QtyInvoiced     | C_Tax_ID        |
      | allocArcInvL1_a | allocArcInv     | allocArcProd    | 7 PCE           | allocArcTax19   |
      | allocArcInvL1_b | allocArcInv     | allocArcProd    | 3 PCE           | allocArcTax19   |
    And the invoice identified by allocArcInv is completed

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | arcAlloc           | allocArcInv  | 23.80 EUR   |

    And Wait until documents allocArcInv, arcAlloc are posted

    # Two level-4 rows: invoice (+190/+1000) and allocation discount correction (+3.80/+20).
    # Subtotals: +190+3.80=193.80 and +1000+20=1020.
    Then report_taxaccounts for C_Tax "allocArcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     |                       | 1020       | 193.80     |        |        | -             | -           |
      | 2     | T_Due_Acct            | 1020       | 193.80     |        |        | -             | -           |
      | 3     | T_Due_Acct            | 1020       | 193.80     |        |        | -             | -           |
      | 4     | T_Due_Acct            |            |            | 1000   | 190    | customer      | allocArcInv |
      | 4     | T_Due_Acct            |            |            | 20     | 3.80   | customer      | arcAlloc    |
      | ReCap |                       | 1020       | 193.80     |        |        | -             | -           |


# ############################################################################################################################################
# TC-S10 — Purchase invoice (API) + discount-only allocation
# ############################################################################################################################################
# Purchase invoice posts T_Credit_Acct Dr=190 (tax receivable). When we pay the vendor less than the
# full amount (taking Skonto), the correction posts on T_Credit_Acct with opposite sign.
  @Id:S0467_TAR_100
  @from:cucumber
  Scenario: purchase invoice + discount-only allocation produces a tax-correction row on T_Credit

    And metasfresh contains C_TaxCategory
      | Identifier        |
      | allocApiTaxCategory |
    And metasfresh contains C_Tax
      | Identifier     | C_TaxCategory_ID     | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocApiTax19  | allocApiTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier   |
      | allocApiProd |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID     |
      | purchasePLV            | allocApiProd |   100.00   | PCE      | allocApiTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocApiInv    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID    | M_Product_ID    | QtyInvoiced     | C_Tax_ID        |
      | allocApiInvL1_a | allocApiInv     | allocApiProd    | 7 PCE           | allocApiTax19   |
      | allocApiInvL1_b | allocApiInv     | allocApiProd    | 3 PCE           | allocApiTax19   |
    And the invoice identified by allocApiInv is completed

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | apiAlloc           | allocApiInv  | 23.80 EUR   |

    And Wait until documents allocApiInv, apiAlloc are posted

    Then report_taxaccounts for C_Tax "allocApiTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     |                       | 1020       | 193.80     |        |        | -             | -           |
      | 2     | T_Credit_Acct         | 1020       | 193.80     |        |        | -             | -           |
      | 3     | T_Credit_Acct         | 1020       | 193.80     |        |        | -             | -           |
      | 4     | T_Credit_Acct         |            |            | 20     | 3.80   | vendor        | apiAlloc    |
      | 4     | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | allocApiInv |
      | ReCap |                       | 1020       | 193.80     |        |        | -             | -           |


# ############################################################################################################################################
# TC-S11 — Purchase credit memo (APC) + discount-only allocation
# ############################################################################################################################################
# Purchase credit memo posts T_Credit_Acct Cr=190 (reversing the receivable). When the vendor refunds
# us less than the full credit (keeping part), the correction posts on T_Credit_Acct with opposite
# sign vs APC.
  @Id:S0467_TAR_110
  @from:cucumber
  Scenario: purchase credit memo + discount-only allocation produces an inverted tax-correction row

    And metasfresh contains C_TaxCategory
      | Identifier        |
      | allocApcTaxCategory |
    And metasfresh contains C_Tax
      | Identifier     | C_TaxCategory_ID     | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocApcTax19  | allocApcTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier   |
      | allocApcProd |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID     |
      | purchasePLV            | allocApcProd |   100.00   | PCE      | allocApcTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocApcInv    | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID    | M_Product_ID    | QtyInvoiced     | C_Tax_ID        |
      | allocApcInvL1_a | allocApcInv     | allocApcProd    | 7 PCE           | allocApcTax19   |
      | allocApcInvL1_b | allocApcInv     | allocApcProd    | 3 PCE           | allocApcTax19   |
    And the invoice identified by allocApcInv is completed

    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | apcAlloc           | allocApcInv  | 23.80 EUR   |

    And Wait until documents allocApcInv, apcAlloc are posted

    # Two level-4 rows: invoice (-190/-1000) and allocation discount correction (+3.80/+20).
    # Subtotals: -190+3.80=-186.20 and -1000+20=-980.
    Then report_taxaccounts for C_Tax "allocApcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     |                       | -980       | -186.20    |        |        | -             | -           |
      | 2     | T_Credit_Acct         | -980       | -186.20    |        |        | -             | -           |
      | 3     | T_Credit_Acct         | -980       | -186.20    |        |        | -             | -           |
      | 4     | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | allocApcInv |
      | 4     | T_Credit_Acct         |            |            | 20     | 3.80   | vendor        | apcAlloc    |
      | ReCap |                       | -980       | -186.20    |        |        | -             | -           |
