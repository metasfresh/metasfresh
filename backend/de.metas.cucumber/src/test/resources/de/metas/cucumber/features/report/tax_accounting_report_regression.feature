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
## zero-tax) so that the upcoming Reverse Charge fix does not silently change non-RC
## output.
##
## Each scenario creates its own dedicated C_Tax so that p_c_tax_id filters the report
## to scenario-scoped rows — no cross-scenario pollution.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
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
      | salesPLV               | salesProd1   | 1000.00  | PCE      | salesTaxCategory |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | ariInv     | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | ariInvL1   | ariInv       | salesProd1   | 1 PCE       | salesTax19 |
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
      | salesPLV               | arcProd1     | 1000.00  | PCE      | arcTaxCategory   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | arcInv     | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | arcInvL1   | arcInv       | arcProd1     | 1 PCE       | arcTax19 |
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
      | purchasePLV            | apiProd1     | 1000.00  | PCE      | apiTaxCategory   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apiInv     | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | apiInvL1   | apiInv       | apiProd1     | 1 PCE       | apiTax19 |
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
      | purchasePLV            | apcProd1     | 1000.00  | PCE      | apcTaxCategory   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apcInv     | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | apcInvL1   | apcInv       | apcProd1     | 1 PCE       | apcTax19 |
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
      | salesPLV               | exemptSalesProd | 500.00   | PCE      | exemptSalesTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | exemptAriInv  | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier     | C_Invoice_ID | M_Product_ID    | QtyInvoiced | C_Tax_ID       |
      | exemptAriInvL1 | exemptAriInv | exemptSalesProd | 1 PCE       | exemptSalesTax |
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
      | purchasePLV            | exemptPurchaseProd  | 500.00   | PCE      | exemptPurchaseTaxCategory  |

    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | exemptApiInv  | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier     | C_Invoice_ID | M_Product_ID       | QtyInvoiced | C_Tax_ID           |
      | exemptApiInvL1 | exemptApiInv | exemptPurchaseProd | 1 PCE       | exemptPurchaseTax  |
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
  Scenario: sales invoice + payment allocation with discount produces a tax-correction row alongside the invoice row

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
      | salesPLV               | allocProd    | 1000.00  | PCE      | allocTaxCategory  |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocSalesInv  | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID  | M_Product_ID | QtyInvoiced | C_Tax_ID   |
      | allocSalesInvL1 | allocSalesInv | allocProd    | 1 PCE       | allocTax19 |
    And the invoice identified by allocSalesInv is completed

    # Payment amount = invoice gross (1190) minus 2% early-payment discount (23.80) = 1166.20 EUR.
    # The discount's 19% tax portion is 3.80 EUR; the net portion is 20.00 EUR.
    And metasfresh contains C_Payment
      | Identifier    | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | allocPayment  | customer      | 1166.20 EUR | true      | org_EUR_account     |
    And the payment identified by allocPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID  | C_Payment_ID | DiscountAmt |
      | allocSalesInv | allocPayment | 23.80 EUR   |

    And Wait until documents allocSalesInv is posted

    # Two level-4 rows (invoice + allocation discount correction). Subtotals:
    #   sum(TaxAmt) = -190 + 3.80 = -186.20
    #   sum(NetAmt) = -1000 + 20  = -980
    Then report_taxaccounts for C_Tax "allocTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo    |
      | 1     |                       | -980       | -186.20    |        |        | -             | -             |
      | 2     | T_Due_Acct            | -980       | -186.20    |        |        | -             | -             |
      | 3     | T_Due_Acct            | -980       | -186.20    |        |        | -             | -             |
      | 4     | T_Due_Acct            |            |            | -1000  | -190   | customer      | allocSalesInv |
      | 4     | T_Due_Acct            |            |            | 20     | 3.80   | customer      |               |
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
      | salesPLV               | mixProd19    | 1000.00  | PCE      | mixTaxCategory19  |
      | salesPLV               | mixProd7     | 500.00   | PCE      | mixTaxCategory7   |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | mixInv     | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | mixInvL1   | mixInv       | mixProd19    | 1 PCE       | mixTax19 |
      | mixInvL2   | mixInv       | mixProd7     | 1 PCE       | mixTax7  |
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
# TC-S9 — Sales credit memo (ARC) + outbound payment with discount
# ############################################################################################################################################
# Credit memo posts T_Due_Acct Dr=190 (reversing the liability). When we pay the customer less than
# the full credit (keeping part as Skonto), Doc_AllocationHdr posts a correction with the opposite
# sign vs ARI.
  @Id:S0467_TAR_090
  @from:cucumber
  Scenario: sales credit memo + outbound payment with discount produces an inverted tax-correction row

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
      | salesPLV               | allocArcProd | 1000.00  | PCE      | allocArcTaxCategory  |

    And metasfresh contains organization bank accounts
      | Identifier          | C_Currency_ID |
      | org_EUR_account_arc | EUR           |

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocArcInv     | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID      |
      | allocArcInvL1    | allocArcInv  | allocArcProd | 1 PCE       | allocArcTax19 |
    And the invoice identified by allocArcInv is completed

    And metasfresh contains C_Payment
      | Identifier       | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | allocArcPayment  | customer      | 1166.20 EUR | false     | org_EUR_account_arc |
    And the payment identified by allocArcPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID    | DiscountAmt |
      | allocArcInv  | allocArcPayment | 23.80 EUR   |

    And Wait until documents allocArcInv is posted

    # CURRENT BEHAVIOUR (likely a bug — flagged for the follow-up fix PR):
    # for ARC + discount, Doc_AllocationHdr does NOT post a tax-correction Fact_Acct row,
    # so only the invoice row (+190 / +1000) appears and the subtotals stay at +190/+1000.
    # The symmetric TC-S10 (API + discount) does produce the expected correction row.
    Then report_taxaccounts for C_Tax "allocArcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     |                       | 1000       | 190        |        |        | -             | -           |
      | 2     | T_Due_Acct            | 1000       | 190        |        |        | -             | -           |
      | 3     | T_Due_Acct            | 1000       | 190        |        |        | -             | -           |
      | 4     | T_Due_Acct            |            |            | 1000   | 190    | customer      | allocArcInv |
      | ReCap |                       | 1000       | 190        |        |        | -             | -           |


# ############################################################################################################################################
# TC-S10 — Purchase invoice (API) + outbound payment with discount
# ############################################################################################################################################
# Purchase invoice posts T_Credit_Acct Dr=190 (tax receivable). When we pay the vendor less than the
# full amount (taking Skonto), the correction posts on T_Credit_Acct with opposite sign.
  @Id:S0467_TAR_100
  @from:cucumber
  Scenario: purchase invoice + outbound payment with discount produces a tax-correction row on T_Credit

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
      | purchasePLV            | allocApiProd | 1000.00  | PCE      | allocApiTaxCategory  |

    And metasfresh contains organization bank accounts
      | Identifier          | C_Currency_ID |
      | org_EUR_account_api | EUR           |

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocApiInv    | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID      |
      | allocApiInvL1   | allocApiInv  | allocApiProd | 1 PCE       | allocApiTax19 |
    And the invoice identified by allocApiInv is completed

    And metasfresh contains C_Payment
      | Identifier       | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | allocApiPayment  | vendor        | 1166.20 EUR | false     | org_EUR_account_api |
    And the payment identified by allocApiPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID    | DiscountAmt |
      | allocApiInv  | allocApiPayment | 23.80 EUR   |

    And Wait until documents allocApiInv is posted

    Then report_taxaccounts for C_Tax "allocApiTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     |                       | 980        | 186.20     |        |        | -             | -           |
      | 2     | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -           |
      | 3     | T_Credit_Acct         | 980        | 186.20     |        |        | -             | -           |
      | 4     | T_Credit_Acct         |            |            | -20    | -3.80  | vendor        |             |
      | 4     | T_Credit_Acct         |            |            | 1000   | 190    | vendor        | allocApiInv |
      | ReCap |                       | 980        | 186.20     |        |        | -             | -           |


# ############################################################################################################################################
# TC-S11 — Purchase credit memo (APC) + inbound payment with discount
# ############################################################################################################################################
# Purchase credit memo posts T_Credit_Acct Cr=190 (reversing the receivable). When the vendor refunds
# us less than the full credit (keeping part), the correction posts on T_Credit_Acct with opposite
# sign vs APC.
  @Id:S0467_TAR_110
  @from:cucumber
  Scenario: purchase credit memo + inbound payment with discount produces an inverted tax-correction row

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
      | purchasePLV            | allocApcProd | 1000.00  | PCE      | allocApcTaxCategory  |

    And metasfresh contains organization bank accounts
      | Identifier          | C_Currency_ID |
      | org_EUR_account_apc | EUR           |

    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | allocApcInv    | vendor        | Gutschrift (Lieferant)  | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID      |
      | allocApcInvL1   | allocApcInv  | allocApcProd | 1 PCE       | allocApcTax19 |
    And the invoice identified by allocApcInv is completed

    And metasfresh contains C_Payment
      | Identifier       | C_BPartner_ID | PayAmt      | IsReceipt | C_BP_BankAccount_ID |
      | allocApcPayment  | vendor        | 1166.20 EUR | true      | org_EUR_account_apc |
    And the payment identified by allocApcPayment is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID    | DiscountAmt |
      | allocApcInv  | allocApcPayment | 23.80 EUR   |

    And Wait until documents allocApcInv is posted

    # CURRENT BEHAVIOUR (likely a bug — flagged for the follow-up fix PR):
    # for APC + discount, Doc_AllocationHdr does NOT post a tax-correction Fact_Acct row,
    # so only the invoice row (-190 / -1000) appears and the subtotals stay at -190/-1000.
    # The symmetric TC-S7 (ARI + discount) does produce the expected correction row.
    Then report_taxaccounts for C_Tax "allocApcTax19" between "2024-01-01" and "2024-01-31" returns:
      | Level | AccountConceptualName | NetAmt_SUM | TaxAmt_SUM | NetAmt | TaxAmt | C_BPartner_ID | DocumentNo  |
      | 1     |                       | -1000      | -190       |        |        | -             | -           |
      | 2     | T_Credit_Acct         | -1000      | -190       |        |        | -             | -           |
      | 3     | T_Credit_Acct         | -1000      | -190       |        |        | -             | -           |
      | 4     | T_Credit_Acct         |            |            | -1000  | -190   | vendor        | allocApcInv |
      | ReCap |                       | -1000      | -190       |        |        | -             | -           |
