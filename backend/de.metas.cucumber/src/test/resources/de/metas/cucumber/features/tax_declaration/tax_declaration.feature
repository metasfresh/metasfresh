@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@ghActions:run_on_executor5
Feature: Tax Declaration Build ("Steuererklärung aufbauen")
##
## Coverage for the Tax Declaration Build process — the DB function
## de_metas_acct.tax_declaration_build(?). Each scenario creates its own dedicated
## C_Tax + C_VAT_Codes and C_TaxDeclaration. No cross-scenario pollution.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config InterceptorEnabled_de.metas.payment.esr.model.validator.C_Invoice#createEsrPaymentRequest
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T10:00:00+01:00[Europe/Berlin]
    And documents are accounted immediately
    And a 1:1 "EUR" <-> "CHF" conversion rate is in place between "2024-01-01" and "2024-01-31"

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer   | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customer_location | customer      | Y               | Y               |
    And load C_AcctSchema:
      | Identifier |
      | acctSchema |


# ############################################################################################################################################
# TC-D1 — Build populates C_TaxDeclarationAcct with sales-invoice T_Due row
# ############################################################################################################################################
  @Id:S0467_TD_010
  @from:cucumber
  Scenario: build populates Acct snapshot from a completed sales invoice

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType |
      | sales19    | tax19    | Y       | T          |
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

    # Verify VATCode is set on T_Due_Acct row before building declaration
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | VATCode | Record_ID |
      | T_Due_Acct            |           | 190       | tax19    | sales19 | invoice   |
      | *                     |           |           |          |         | invoice   |

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td1        | acctSchema      | 2024-01-15 |

    When the tax declaration 'td1' is built

    Then the C_TaxDeclarationAcct for declaration 'td1' contains entries for documents:
      | Record_ID | VATCode  | AmountType | Amount |
      | invoice   | sales19       | T          | -190   |


# ############################################################################################################################################
# TC-D2 — Re-Build is idempotent (no duplicate rows)
# ############################################################################################################################################
  @Id:S0467_TD_020
  @from:cucumber
  Scenario: re-build is idempotent — second build produces the same snapshot without duplicates

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType |
      | sales19    | tax19    | Y       | T          |
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

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td1        | acctSchema      | 2024-01-15 |

    When the tax declaration 'td1' is built
    When the tax declaration 'td1' is built

    Then the C_TaxDeclarationAcct for declaration 'td1' contains entries for documents:
      | Record_ID | VATCode  | AmountType | Amount |
      | invoice   | sales19       | T          | -190   |


# ############################################################################################################################################
# TC-D11 — VAT code per invoice flows correctly to Acct snapshot
# Two invoices with two different taxes → each gets its own VAT code in the snapshot.
# ############################################################################################################################################
  @Id:S0467_TD_110
  @from:cucumber
  Scenario: VAT code per invoice flows correctly to Acct snapshot

    # Two independent taxes + VAT codes so the build function must map each invoice
    # to the correct VAT code without cross-contamination.
    And metasfresh contains C_TaxCategory
      | Identifier   |
      | taxCategory1 |
      | taxCategory2 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | taxA       | taxCategory1     | 19   | DE                       | DE                        |
      | taxB       | taxCategory2     | 7    | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType |
      | salesVatA  | taxA     | Y       | T          |
      | salesVatB  | taxB     | Y       | T          |
    And metasfresh contains M_Products:
      | Identifier |
      | productA   |
      | productB   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | productA     | 100.00   | PCE      | taxCategory1     |
      | salesPLV               | productB     | 100.00   | PCE      | taxCategory2     |

    And metasfresh contains C_Invoice:
      | Identifier   | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceA     | customer      | 2024-01-15   | true    | EUR           |
      | invoiceB     | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | lineA1     | invoiceA     | productA     | 10 PCE      | taxA     |
      | lineB1     | invoiceB     | productB     | 10 PCE      | taxB     |
    And the invoice identified by invoiceA is completed
    And the invoice identified by invoiceB is completed
    And Wait until documents invoiceA is posted
    And Wait until documents invoiceB is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td11       | acctSchema      | 2024-01-15 |

    When the tax declaration 'td11' is built

    # Each invoice must map to its own VAT code — no cross-contamination
    Then the C_TaxDeclarationAcct for declaration 'td11' contains entries for documents:
      | Record_ID | VATCode  | AmountType | Amount |
      | invoiceA  | salesVatA     | T          | -190   |
      | invoiceB  | salesVatB     | T          | -70    |
