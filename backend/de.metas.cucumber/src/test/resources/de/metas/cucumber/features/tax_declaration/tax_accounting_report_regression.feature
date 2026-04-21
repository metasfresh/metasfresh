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
## These tests lock in the CURRENT behaviour for standard non-RC cases so the upcoming
## Reverse Charge / bug-fix PR (sub-PR 2) cannot silently change non-RC output. Each
## scenario creates its own dedicated C_Tax + C_VAT_Code and the report is filtered by
## p_c_tax_id — no cross-scenario pollution. Every scenario uses 2 invoice lines with
## an unequal split (7+3 or 2+3 PCE) at PriceStd=100 so per-line-to-per-tax aggregation
## (§14(4) Nr. 7/8 UStG) is also validated.
##
## Out-of-scope (sub-PR 2 must address): Reverse Charge view/function bugs, the
## payment-based allocation gap R8a, and the NetAmt sign convention on the declaration
## side. See the authoritative list on the me03 spotlight
## https://github.com/metasfresh/me03/issues/29361#issuecomment-4286107583 and the
## technical analysis in ai-work/29361/REQUIREMENTS.md. Per-scenario notes (§17 UStG
## verdicts, DiscountAmt sign direction) live in each scenario's comment block below.

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
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID   | IsSOTrx |
      | salesVat19 | salesTax19 | Y       |
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

    # Tax-related Fact_Acct posting: ARI → T_Due_Acct CR 190 (USt liability).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID   | Record_ID |
      | T_Due_Acct            |           | 190       | salesTax19 | ariInv    |
      | *                     |           |           |            | ariInv    |
    And Fact_Acct records balances for documents ariInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID   |
      | T_Due_Acct            | -190        | salesTax19 |

    # Regression baseline across all aggregation levels. For sales invoices (ARI),
    # T_Due_Acct posts AmtAcctCr=190 → level 4 has TaxAmt=-190/NetAmt=-1000 and
    # levels 1/2/3/ReCap sum up to the same single-row amounts.
    Then report_taxaccounts for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | salesVat19    |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | salesVat19    | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | salesVat19    | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | salesVat19    | T_Due_Acct            |            |            | -1000  | -190   | customer      | ariInv     |
      | ReCap | salesVat19    |                       | -1000      | -190       |        |        | -             | -          |


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
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | arcVat19   | arcTax19 | Y       |
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

    # ARC → T_Due_Acct DR 190 (reverses the USt that an ARI would have posted CR).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | Record_ID |
      | T_Due_Acct            | 190       |           | arcTax19 | arcInv    |
      | *                     |           |           |          | arcInv    |
    And Fact_Acct records balances for documents arcInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | 190         | arcTax19 |

    # For ARC: signs are inverted vs ARI. T_Due_Acct posts AmtAcctDr=190, so TaxAmt=+190, NetAmt=+1000.
    Then report_taxaccounts for C_Tax "arcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | arcVat19      |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | arcVat19      | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 3     | arcVat19      | T_Due_Acct            | 1000       | 190        |        |        | -             | -          |
      | 4     | arcVat19      | T_Due_Acct            |            |            | 1000   | 190    | customer      | arcInv     |
      | ReCap | arcVat19      |                       | 1000       | 190        |        |        | -             | -          |


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
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | apiVat19   | apiTax19 | N       |
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

    # API → T_Credit_Acct DR 190 (VSt receivable, input-tax deduction per §15 UStG).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | Record_ID |
      | T_Credit_Acct         | 190       |           | apiTax19 | apiInv    |
      | *                     |           |           |          | apiInv    |
    And Fact_Acct records balances for documents apiInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | 190         | apiTax19 |

    # For API: T_Credit_Acct posts AmtAcctDr=190, so TaxAmt=+190, NetAmt=+1000.
    Then report_taxaccounts for C_Tax "apiTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | apiVat19      |                       | 1000       | 190        |        |        | -             | -          |
      | 2     | apiVat19      | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 3     | apiVat19      | T_Credit_Acct         | 1000       | 190        |        |        | -             | -          |
      | 4     | apiVat19      | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | apiInv     |
      | ReCap | apiVat19      |                       | 1000       | 190        |        |        | -             | -          |


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
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | apcVat19   | apcTax19 | N       |
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

    # APC → T_Credit_Acct CR 190 (reverses the VSt that an API would have posted DR).
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | Record_ID |
      | T_Credit_Acct         |           | 190       | apcTax19 | apcInv    |
      | *                     |           |           |          | apcInv    |
    And Fact_Acct records balances for documents apcInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Credit_Acct         | -190        | apcTax19 |

    # For APC: signs are inverted vs API. T_Credit_Acct posts AmtAcctCr=190, so TaxAmt=-190, NetAmt=-1000.
    Then report_taxaccounts for C_Tax "apcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | apcVat19      |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | apcVat19      | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 3     | apcVat19      | T_Credit_Acct         | -1000      | -190       |        |        | -             | -          |
      | 4     | apcVat19      | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | apcInv     |
      | ReCap | apcVat19      |                       | -1000      | -190       |        |        | -             | -          |


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
    And metasfresh contains C_VAT_Codes:
      | Identifier     | C_Tax_ID       | IsSOTrx |
      | exemptSalesVat | exemptSalesTax | Y       |
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

    # Zero-tax ARI (§4 UStG): T_Due_Acct posts a zero row carrying the TaxBaseAmt only.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID       | Record_ID    |
      | T_Due_Acct            | 0         | 0         | exemptSalesTax | exemptAriInv |
      | *                     |           |           |                | exemptAriInv |
    And Fact_Acct records balances for documents exemptAriInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID       |
      | T_Due_Acct            | 0           | exemptSalesTax |

    # Zero-tax ARI: T_Due_Acct posts zero. TaxAmt=0, NetAmt=-500 (ARI sign flip applied to the 500 base).
    Then report_taxaccounts for C_Tax "exemptSalesTax" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID  | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo   |
      | 1     | exemptSalesVat |                       | -500       | 0          |        |        | -             | -            |
      | 2     | exemptSalesVat | T_Due_Acct            | -500       | 0          |        |        | -             | -            |
      | 3     | exemptSalesVat | T_Due_Acct            | -500       | 0          |        |        | -             | -            |
      | 4     | exemptSalesVat | T_Due_Acct            |            |            | -500   | 0      | customer      | exemptAriInv |
      | ReCap | exemptSalesVat |                       | -500       | 0          |        |        | -             | -            |


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
    And metasfresh contains C_VAT_Codes:
      | Identifier        | C_Tax_ID          | IsSOTrx |
      | exemptPurchaseVat | exemptPurchaseTax | N       |
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

    # Zero-tax API (§4/§15(2) UStG): T_Credit_Acct posts a zero row carrying the TaxBaseAmt only.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID          | Record_ID    |
      | T_Credit_Acct         | 0         | 0         | exemptPurchaseTax | exemptApiInv |
      | *                     |           |           |                   | exemptApiInv |
    And Fact_Acct records balances for documents exemptApiInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID          |
      | T_Credit_Acct         | 0           | exemptPurchaseTax |

    # Zero-tax API: T_Credit_Acct posts zero. TaxAmt=0, NetAmt=+500.
    Then report_taxaccounts for C_Tax "exemptPurchaseTax" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID     | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo   |
      | 1     | exemptPurchaseVat |                       | 500        | 0          |        |        | -             | -            |
      | 2     | exemptPurchaseVat | T_Credit_Acct         | 500        | 0          |        |        | -             | -            |
      | 3     | exemptPurchaseVat | T_Credit_Acct         | 500        | 0          |        |        | -             | -            |
      | 4     | exemptPurchaseVat | T_Credit_Acct         |            |            | 500    | 0      | vendor        | exemptApiInv |
      | ReCap | exemptPurchaseVat |                       | 500        | 0          |        |        | -             | -            |


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
# USt liability reduced → compliant. Customer-validated; no sub-PR 2 action needed.
  @Id:S0467_TAR_070
  @from:cucumber
  Scenario: sales invoice + discount-only allocation produces a tax-correction row alongside the invoice row

    And metasfresh contains C_TaxCategory
      | Identifier      |
      | allocTaxCategory |
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocTax19  | allocTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID   | IsSOTrx |
      | allocVat19 | allocTax19 | Y       |
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

    # ARI + discount: invoice posts T_Due CR 190; allocation's tax-correction row posts
    # T_Due DR 3.80 (USt is reduced per §17(1)(1) UStG because the customer paid less).
    # Expected net balance: -190 + 3.80 = -186.20.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID   | Record_ID     |
      | T_Due_Acct            |           | 190       | allocTax19 | allocSalesInv |
      | *                     |           |           |            | allocSalesInv |
      | T_Due_Acct            | 3.80      |           | allocTax19 | ariAlloc      |
      | *                     |           |           |            | ariAlloc      |
    And Fact_Acct records balances for documents allocSalesInv,ariAlloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID   |
      | T_Due_Acct            | -186.20     | allocTax19 |

    # Two level-4 rows (invoice + allocation discount correction). Subtotals:
    #   sum(TaxAmt) = -190 + 3.80 = -186.20
    #   sum(NetAmt) = -1000 + 20  = -980
    Then report_taxaccounts for C_Tax "allocTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo    |
      | 1     | allocVat19    |                       | -980       | -186.20    |        |        | -             | -             |
      | 2     | allocVat19    | T_Due_Acct            | -980       | -186.20    |        |        | -             | -             |
      | 3     | allocVat19    | T_Due_Acct            | -980       | -186.20    |        |        | -             | -             |
      | 4     | allocVat19    | T_Due_Acct            |            |            | -1000  | -190   | customer      | allocSalesInv |
      | 4     | allocVat19    | T_Due_Acct            |            |            | 20     | 3.80   | customer      | ariAlloc      |
      | ReCap | allocVat19    |                       | -980       | -186.20    |        |        | -             | -             |


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
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx |
      | mixVat19   | mixTax19 | Y       |
      | mixVat7    | mixTax7  | Y       |
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

    # One T_Due_Acct Fact_Acct row per tax: 190 for 19% slice, 35 for 7% slice.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | Record_ID |
      | T_Due_Acct            |           | 190       | mixTax19 | mixInv    |
      | T_Due_Acct            |           | 35        | mixTax7  | mixInv    |
      | *                     |           |           |          | mixInv    |
    And Fact_Acct records balances for documents mixInv are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID |
      | T_Due_Acct            | -190        | mixTax19 |
      | T_Due_Acct            | -35         | mixTax7  |

    # 19% slice: 1000 base + 190 tax
    Then report_taxaccounts for C_Tax "mixTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | mixVat19      |                       | -1000      | -190       |        |        | -             | -          |
      | 2     | mixVat19      | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 3     | mixVat19      | T_Due_Acct            | -1000      | -190       |        |        | -             | -          |
      | 4     | mixVat19      | T_Due_Acct            |            |            | -1000  | -190   | customer      | mixInv     |
      | ReCap | mixVat19      |                       | -1000      | -190       |        |        | -             | -          |

    # 7% slice: 500 base + 35 tax
    Then report_taxaccounts for C_Tax "mixTax7" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo |
      | 1     | mixVat7       |                       | -500       | -35        |        |        | -             | -          |
      | 2     | mixVat7       | T_Due_Acct            | -500       | -35        |        |        | -             | -          |
      | 3     | mixVat7       | T_Due_Acct            | -500       | -35        |        |        | -             | -          |
      | 4     | mixVat7       | T_Due_Acct            |            |            | -500   | -35    | customer      | mixInv     |
      | ReCap | mixVat7       |                       | -500       | -35        |        |        | -             | -          |


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
      | Identifier        |
      | allocArcTaxCategory |
    And metasfresh contains C_Tax
      | Identifier     | C_TaxCategory_ID     | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocArcTax19  | allocArcTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier    | C_Tax_ID      | IsSOTrx |
      | allocArcVat19 | allocArcTax19 | Y       |
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

    # ARC direction = "we pay" (refund to customer) → DiscountAmt negative per the
    # metasfresh C_AllocationLine sign convention (positive = we receive, negative = we pay).
    # Empirical production data confirms ARC splits roughly 57%+/43%- — both directions occur.
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | arcAlloc           | allocArcInv  | -23.80 EUR  |

    And Wait until documents allocArcInv, arcAlloc are posted

    # ARC + negative discount (refund paid, Skonto kept): invoice posts T_Due DR 190
    # (reversing USt); allocation posts T_Due CR 3.80 (reverses LESS of the USt because
    # we refunded less to the customer). Net balance +186.20 per §17(1) UStG.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID      | Record_ID   |
      | T_Due_Acct            | 190       |           | allocArcTax19 | allocArcInv |
      | *                     |           |           |               | allocArcInv |
      | T_Due_Acct            |           | 3.80      | allocArcTax19 | arcAlloc    |
      | *                     |           |           |               | arcAlloc    |
    And Fact_Acct records balances for documents allocArcInv,arcAlloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID      |
      | T_Due_Acct            | 186.20      | allocArcTax19 |

    # Two level-4 rows. Invoice (+190/+1000) + allocation correction (-3.80/-20).
    # Subtotals: +190-3.80=186.20 and +1000-20=980.
    Then report_taxaccounts for C_Tax "allocArcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     | allocArcVat19 |                       | 980        | 186.20     |        |        | -             | -           |
      | 2     | allocArcVat19 | T_Due_Acct            | 980        | 186.20     |        |        | -             | -           |
      | 3     | allocArcVat19 | T_Due_Acct            | 980        | 186.20     |        |        | -             | -           |
      | 4     | allocArcVat19 | T_Due_Acct            |            |            | 1000   | 190    | customer      | allocArcInv |
      | 4     | allocArcVat19 | T_Due_Acct            |            |            | -20    | -3.80  | customer      | arcAlloc    |
      | ReCap | allocArcVat19 |                       | 980        | 186.20     |        |        | -             | -           |


# ############################################################################################################################################
# TC-S10 — Purchase invoice (API) + discount-only allocation
# ############################################################################################################################################
# Purchase invoice posts T_Credit_Acct Dr=190 (tax receivable). When we pay the vendor less than the
# full amount (taking Skonto), the correction posts on T_Credit_Acct with opposite sign.
#
# Direction used in this scenario: TC-S10 API = −23.80 EUR (we paid supplier, they received less
# — direction = "we pay"; see DiscountAmt sign convention in TC-S7's comment block).
# §17(1)(2) UStG + §15(1a) UStG verdict: T_Credit DR 190 (invoice) + CR 3.80 (alloc) → balance
# +186.20. VSt claim reduced → §17-compliant. Earlier spotlight drafts flagged this as
# "LIKELY WRONG" because they assumed a positive DiscountAmt — that was the wrong input.
  @Id:S0467_TAR_100
  @from:cucumber
  Scenario: purchase invoice + discount-only allocation produces a tax-correction row on T_Credit

    And metasfresh contains C_TaxCategory
      | Identifier        |
      | allocApiTaxCategory |
    And metasfresh contains C_Tax
      | Identifier     | C_TaxCategory_ID     | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocApiTax19  | allocApiTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier    | C_Tax_ID      | IsSOTrx |
      | allocApiVat19 | allocApiTax19 | N       |
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

    # API direction = "we pay" (supplier) → DiscountAmt negative per the C_AllocationLine
    # sign convention. Empirical production data confirms API is mostly negative (~82%).
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | apiAlloc           | allocApiInv  | -23.80 EUR  |

    And Wait until documents allocApiInv, apiAlloc are posted

    # API + negative discount (supplier paid less, Skonto taken): invoice posts T_Credit DR 190
    # (claiming VSt); allocation posts T_Credit CR 3.80 (claims LESS VSt because we deducted
    # a Skonto from what we owed). Net balance +186.20 per §17(1)(2) and §15(1a) UStG.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID      | Record_ID   |
      | T_Credit_Acct         | 190       |           | allocApiTax19 | allocApiInv |
      | *                     |           |           |               | allocApiInv |
      | T_Credit_Acct         |           | 3.80      | allocApiTax19 | apiAlloc    |
      | *                     |           |           |               | apiAlloc    |
    And Fact_Acct records balances for documents allocApiInv,apiAlloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID      |
      | T_Credit_Acct         | 186.20      | allocApiTax19 |

    # Two level-4 rows. Invoice (+190/+1000) + allocation correction (-3.80/-20).
    # Subtotals: +190-3.80=186.20 and +1000-20=980.
    Then report_taxaccounts for C_Tax "allocApiTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     | allocApiVat19 |                       | 980        | 186.20     |        |        | -             | -           |
      | 2     | allocApiVat19 | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -           |
      | 3     | allocApiVat19 | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -           |
      | 4     | allocApiVat19 | T_Credit_Acct         |            |            | -20    | -3.80  | vendor        | apiAlloc    |
      | 4     | allocApiVat19 | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | allocApiInv |
      | ReCap | allocApiVat19 |                       | 980        | 186.20     |        |        | -             | -           |


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
      | Identifier        |
      | allocApcTaxCategory |
    And metasfresh contains C_Tax
      | Identifier     | C_TaxCategory_ID     | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | allocApcTax19  | allocApcTaxCategory  | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier    | C_Tax_ID      | IsSOTrx |
      | allocApcVat19 | allocApcTax19 | N       |
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

    # APC direction = "we receive" (refund from vendor) → DiscountAmt positive per the
    # C_AllocationLine sign convention. Empirical production data shows APC splits 61%+/39%-.
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | apcAlloc           | allocApcInv  | 23.80 EUR   |

    And Wait until documents allocApcInv, apcAlloc are posted

    # APC + positive discount (vendor refunded less, we kept a Skonto): invoice posts
    # T_Credit CR 190 (reversing VSt); allocation posts T_Credit DR 3.80 (reverses LESS
    # VSt because we retained a Skonto on the refund). Net balance -186.20 per §17(1) UStG.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID      | Record_ID   |
      | T_Credit_Acct         |           | 190       | allocApcTax19 | allocApcInv |
      | *                     |           |           |               | allocApcInv |
      | T_Credit_Acct         | 3.80      |           | allocApcTax19 | apcAlloc    |
      | *                     |           |           |               | apcAlloc    |
    And Fact_Acct records balances for documents allocApcInv,apcAlloc are matching
      | AccountConceptualName | AcctBalance | C_Tax_ID      |
      | T_Credit_Acct         | -186.20     | allocApcTax19 |

    # Two level-4 rows: invoice (-190/-1000) and allocation discount correction (+3.80/+20).
    # Subtotals: -190+3.80=-186.20 and -1000+20=-980.
    Then report_taxaccounts for C_Tax "allocApcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | C_VAT_Code_ID | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     | allocApcVat19 |                       | -980       | -186.20    |        |        | -             | -           |
      | 2     | allocApcVat19 | T_Credit_Acct         | -980       | -186.20    |        |        | -             | -           |
      | 3     | allocApcVat19 | T_Credit_Acct         | -980       | -186.20    |        |        | -             | -           |
      | 4     | allocApcVat19 | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | allocApcInv |
      | 4     | allocApcVat19 | T_Credit_Acct         |            |            | 20     | 3.80   | vendor        | apcAlloc    |
      | ReCap | allocApcVat19 |                       | -980       | -186.20    |        |        | -             | -           |
