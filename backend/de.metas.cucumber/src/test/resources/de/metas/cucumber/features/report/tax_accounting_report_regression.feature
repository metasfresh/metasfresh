@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@F00700
@ghActions:run_on_executor5
Feature: Tax Accounting Report ("Mehrwertsteuer-Verprobung 3") — regression
## me03#29361: Support Reverse Charge with explicit fact acct - Tax Accounting Report fixes
##
## Regression tests for the Tax Accounting Report used as AD_Process 585325
## ("Mehrwertsteuer-Verprobung(Excel) 3"). Exercises both:
##   - view de_metas_acct.tax_accounts_details_v (raw per-Fact_Acct rows)
##   - function de_metas_acct.report_taxaccounts(...) (aggregated report)
##
## These tests lock in the CURRENT behavior of the view + function for standard cases
## (regular 19% tax and zero-tax), so that the upcoming Reverse Charge fix does not
## silently change non-RC output.
##
## Each scenario creates its own dedicated C_Tax so that p_c_tax_id filters the report
## to scenario-scoped rows — no cross-scenario pollution.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T10:00:00+01:00[Europe/Berlin]
    And documents are accounted immediately

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

    # Regression baseline: capture the current output of the view and the report function.
    # For sales invoices (ARI), T_Due_Acct has AmtSourceCr=190 → view's TaxAmt = 0 - 190 = -190.
    # View's outer TaxBaseAmt: DocBaseType=ARI → -TaxBaseAmt = -1000.
    Then the tax_accounts_details_v for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
      | AccountConceptualName | TaxAmt | TaxBaseAmt |
      | T_Due_Acct            | -190   | -1000      |

    Then report_taxaccounts level 4 for C_Tax "salesTax19" between "2024-01-01" and "2024-01-31" returns:
      | TaxAmt | NetAmt |
      | -190   | -1000  |


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

    # For ARC: T_Due_Acct has AmtSourceDr=190 → view's TaxAmt = 190 - 0 = +190.
    # View's outer TaxBaseAmt: DocBaseType=ARC → +TaxBaseAmt = +1000.
    Then the tax_accounts_details_v for C_Tax "arcTax19" between "2024-01-01" and "2024-01-31" returns:
      | AccountConceptualName | TaxAmt | TaxBaseAmt |
      | T_Due_Acct            | 190    | 1000       |

    Then report_taxaccounts level 4 for C_Tax "arcTax19" between "2024-01-01" and "2024-01-31" returns:
      | TaxAmt | NetAmt |
      | 190    | 1000   |


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

    # For API: T_Credit_Acct has AmtSourceDr=190 → view's TaxAmt = 190 - 0 = +190.
    # View's outer TaxBaseAmt: DocBaseType=API → +TaxBaseAmt = +1000.
    Then the tax_accounts_details_v for C_Tax "apiTax19" between "2024-01-01" and "2024-01-31" returns:
      | AccountConceptualName | TaxAmt | TaxBaseAmt |
      | T_Credit_Acct         | 190    | 1000       |

    Then report_taxaccounts level 4 for C_Tax "apiTax19" between "2024-01-01" and "2024-01-31" returns:
      | TaxAmt | NetAmt |
      | 190    | 1000   |


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

    # For APC: T_Credit_Acct has AmtSourceCr=190 → view's TaxAmt = 0 - 190 = -190.
    # View's outer TaxBaseAmt: DocBaseType=APC → -TaxBaseAmt = -1000.
    Then the tax_accounts_details_v for C_Tax "apcTax19" between "2024-01-01" and "2024-01-31" returns:
      | AccountConceptualName | TaxAmt | TaxBaseAmt |
      | T_Credit_Acct         | -190   | -1000      |

    Then report_taxaccounts level 4 for C_Tax "apcTax19" between "2024-01-01" and "2024-01-31" returns:
      | TaxAmt | NetAmt |
      | -190   | -1000  |


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
      | Identifier        | C_TaxCategory_ID        | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | exemptSalesTax    | exemptSalesTaxCategory  | 0    | DE                       | DE                        |
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

    # Zero-tax ARI: T_Due_Acct line has DR=0, CR=0. View's TaxAmt = 0 - 0 = 0.
    # View's outer TaxBaseAmt: DocBaseType=ARI → -500.
    Then the tax_accounts_details_v for C_Tax "exemptSalesTax" between "2024-01-01" and "2024-01-31" returns:
      | AccountConceptualName | TaxAmt | TaxBaseAmt |
      | T_Due_Acct            | 0      | -500       |

    Then report_taxaccounts level 4 for C_Tax "exemptSalesTax" between "2024-01-01" and "2024-01-31" returns:
      | TaxAmt | NetAmt |
      | 0      | -500   |


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
      | Identifier        | C_TaxCategory_ID           | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | exemptPurchaseTax | exemptPurchaseTaxCategory  | 0    | DE                       | DE                        |
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

    # Zero-tax API: T_Credit_Acct line has DR=0, CR=0. View's TaxAmt = 0 - 0 = 0.
    # View's outer TaxBaseAmt: DocBaseType=API → +500.
    Then the tax_accounts_details_v for C_Tax "exemptPurchaseTax" between "2024-01-01" and "2024-01-31" returns:
      | AccountConceptualName | TaxAmt | TaxBaseAmt |
      | T_Credit_Acct         | 0      | 500        |

    Then report_taxaccounts level 4 for C_Tax "exemptPurchaseTax" between "2024-01-01" and "2024-01-31" returns:
      | TaxAmt | NetAmt |
      | 0      | 500    |
