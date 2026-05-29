@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@ghActions:run_on_executor5
Feature: Tax Declaration Build ("Steuererklärung aufbauen")
##
## Coverage for the Tax Declaration Build process — the DB function
## de_metas_acct.tax_declaration_build(?). Each scenario creates its own dedicated
## C_Tax + C_VAT_Codes and C_TaxDeclaration. Scenarios share the same acct schema and
## period (Jan-24); the "Clear previous Tax Declaration documents" Background step gives
## each scenario a clean slate so a completed Original left by one scenario does not leak
## into the period-uniqueness / no-lines paths of the next.

  Background:
    Given infrastructure and metasfresh are running
    And Clear previous Tax Declaration documents
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
    # (`C_VAT_Code_ID` column is the cucumber identifier; the matcher resolves it to
    # the actual VATCode string via vatCodeTable — see FactAcctMatchersFactory#extractVatCode)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtAcctDr | AmtAcctCr | C_Tax_ID | C_VAT_Code_ID | Record_ID |
      | T_Due_Acct            |           | 190       | tax19    | sales19       | invoice   |
      | *                     |           |           |          |               | invoice   |

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
      | Record_ID | VATCode   | AmountType | Amount |
      | invoiceA  | salesVatA | T          | -190   |
      | invoiceB  | salesVatB | T          | -70    |


# ############################################################################################################################################
# TC-D3 — Complete + Reactivate roundtrip: built declaration completes, reactivates, retains snapshot.
# Lock is enforced by AD_Tab.ReadOnlyLogic='@Processed@=Y' (WebUI).
# ############################################################################################################################################
  @Id:S0467_TD_030
  @from:cucumber
  Scenario: complete + reactivate roundtrip on a built declaration

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
      | invoiceL1  | invoice      | product      | 10 PCE      | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    When the tax declaration "td" is completed
    Then the tax declaration "td" has Processed='Y' and DocStatus='CO' and DocAction='RE'

    When the tax declaration "td" is reactivated
    Then the tax declaration "td" has Processed='N' and DocStatus='IP' and DocAction='CO'
    And the C_TaxDeclarationLine rows for "td" are still present


# ############################################################################################################################################
# TC-D4 — Period uniqueness: a second completed declaration on same (AcctSchema, Period) is rejected
# ############################################################################################################################################
  @Id:S0467_TD_040
  @from:cucumber
  Scenario: second completed declaration on same period and acct schema is rejected

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
      | invoiceL1  | invoice      | product      | 10 PCE      | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier  | C_AcctSchema_ID | Date       |
      | tdOriginal  | acctSchema      | 2024-01-15 |
      | tdDuplicate | acctSchema      | 2024-01-15 |
    And the tax declaration "tdOriginal" is built
    And the tax declaration "tdOriginal" is completed
    And the tax declaration "tdDuplicate" is built
    When the tax declaration "tdDuplicate" is completed
    Then the tax declaration completion fails with message 'TAXDECLARATION_PERIOD_OVERLAP'


# ############################################################################################################################################
# TC-D-NoLines — Complete-without-Build is rejected with TaxDeclaration_NoLinesYet
# ############################################################################################################################################
  @Id:S0467_TD_050
  @from:cucumber
  Scenario: complete-without-build rejected with TaxDeclaration_NoLinesYet

    # The Background "Clear previous Tax Declaration documents" step guarantees no completed
    # Original lingers on this period, so completion reaches the no-lines guard (rather than the
    # period-uniqueness guard, which is checked first).
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    When the tax declaration "td" is completed
    Then the tax declaration completion fails with message 'TAXDECLARATION_NO_LINES_YET'


# ############################################################################################################################################
# TC-D6 — Reactivating an Original that already has a Correction is rejected (TAXDECLARATION_HAS_CORRECTIONS)
# ############################################################################################################################################
  @Id:S0467_TD_060
  @from:cucumber
  Scenario: reactivating an Original that already has a Correction is rejected

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
      | invoiceL1  | invoice      | product      | 10 PCE      | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed
    And invoke Create Correction on C_TaxDeclaration "td"

    When the tax declaration "td" is reactivated expecting failure
    Then the tax declaration operation fails with message 'TAXDECLARATION_HAS_CORRECTIONS'


# ############################################################################################################################################
# TC-D8 — Create Correction inherits Period, DateAcct and AcctSchema from the Original
# ############################################################################################################################################
  @Id:S0467_TD_080
  @from:cucumber
  Scenario: Create Correction inherits period, posting date and acct schema from the Original

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
      | invoiceL1  | invoice      | product      | 10 PCE      | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed

    When invoke Create Correction on C_TaxDeclaration "td"
    Then the tax declaration "td_correction" is a Correction inheriting Period, DateAcct and AcctSchema from "td"


# ############################################################################################################################################
# TC-D9 — Completing a Correction clears the Original's "Berichtigung erforderlich" (IsCorrectionNeeded) flag
# ############################################################################################################################################
  @Id:S0467_TD_090
  @from:cucumber
  Scenario: completing a Correction clears the Original's correction-needed flag

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
      | invoiceL1  | invoice      | product      | 10 PCE      | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed
    And C_TaxDeclaration "td" has IsCorrectionNeeded set to "Y"

    And invoke Create Correction on C_TaxDeclaration "td"
    And the tax declaration "td_correction" is built

    When the tax declaration "td_correction" is completed
    Then C_TaxDeclaration "td" has IsCorrectionNeeded = "N"


# ############################################################################################################################################
# TC-D10 — A Correction of a Correction is rejected (star topology: only an Original may be the template)
# ############################################################################################################################################
  @Id:S0467_TD_100
  @from:cucumber
  Scenario: a Correction of a Correction is rejected

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
      | invoiceL1  | invoice      | product      | 10 PCE      | tax19    |
    And the invoice identified by invoice is completed
    And Wait until documents invoice is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed

    # First Correction: legal. Build + complete it so it becomes a locked (Processed='Y') Correction.
    And invoke Create Correction on C_TaxDeclaration "td"
    And the tax declaration "td_correction" is built
    And the tax declaration "td_correction" is completed

    # Second Correction anchored to the first Correction: rejected — only an Original may be the template.
    When invoke Create Correction on C_TaxDeclaration "td_correction"
    Then the tax declaration operation fails with message 'TAXDECLARATION_ORIGINAL_MUST_BE_ORIGINAL'


# ############################################################################################################################################
# TC-D12 — A Correction Build snapshots ALL period facts (full restatement), including facts posted after the Original was locked
# ############################################################################################################################################
  @Id:S0467_TD_120
  @from:cucumber
  Scenario: a Correction Build snapshots all period facts (full restatement)

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

    # Two sales invoices in Jan-24, posted BEFORE the Original is locked.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceA   | customer      | 2024-01-15   | true    | EUR           |
      | invoiceB   | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | lineA1     | invoiceA     | product      | 10 PCE      | tax19    |
      | lineB1     | invoiceB     | product      | 20 PCE      | tax19    |
    And the invoice identified by invoiceA is completed
    And the invoice identified by invoiceB is completed
    And Wait until documents invoiceA is posted
    And Wait until documents invoiceB is posted

    # Original is built (snapshots A + B) and locked.
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed

    # A third sales invoice in the SAME period, posted AFTER the Original was locked.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceC   | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | lineC1     | invoiceC     | product      | 30 PCE      | tax19    |
    And the invoice identified by invoiceC is completed
    And Wait until documents invoiceC is posted

    # The Correction's Build must restate the WHOLE period: A + B (already in the Original's snapshot)
    # AND C (posted after the lock). IsCorrection='Y' bypasses the build engine's NOT-EXISTS exclusion.
    And invoke Create Correction on C_TaxDeclaration "td"
    When the tax declaration "td_correction" is built

    Then the C_TaxDeclarationAcct for declaration 'td_correction' contains entries for documents:
      | Record_ID | VATCode | AmountType | Amount |
      | invoiceA  | sales19 | T          | -190   |
      | invoiceB  | sales19 | T          | -380   |
      | invoiceC  | sales19 | T          | -570   |


# ############################################################################################################################################
# TC-D7 — Drift detected when a new invoice is posted after the declaration is built
# ############################################################################################################################################
  @Id:S0467_TD_070
  @from:cucumber
  Scenario: drift detected — orphan Fact_Acct rows from an invoice posted after the declaration was built

    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategoryD7 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | taxD7      | taxCategoryD7    | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType |
      | vatD7      | taxD7    | Y       | T          |
    And metasfresh contains M_Products:
      | Identifier |
      | productD7  |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | productD7    | 100.00   | PCE      | taxCategoryD7    |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceD7a | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceD7aL1 | invoiceD7a   | productD7    | 1 PCE       | taxD7    |
    And the invoice identified by invoiceD7a is completed
    And Wait until documents invoiceD7a is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | tdD7       | acctSchema      | 2024-01-15 |
    And the tax declaration "tdD7" is built

    # No drift yet — declaration snapshot matches live Fact_Acct
    When the drift check process is run on tax declaration "tdD7"
    Then C_TaxDeclaration "tdD7" has IsCorrectionNeeded = "N"

    # Post a second invoice in the same period → orphan Fact_Acct rows not in the snapshot
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceD7b | customer      | 2024-01-20   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier   | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceD7bL1 | invoiceD7b   | productD7    | 2 PCE       | taxD7    |
    And the invoice identified by invoiceD7b is completed
    And Wait until documents invoiceD7b is posted

    # Drift detected — orphan rows from invoiceD7b not captured in the snapshot
    When the drift check process is run on tax declaration "tdD7"
    Then C_TaxDeclaration "tdD7" has IsCorrectionNeeded = "Y"


# ############################################################################################################################################
# TC-D7b — Drift detected independently — second scenario uses different identifiers to avoid leakage
# ############################################################################################################################################
  @Id:S0467_TD_071
  @from:cucumber
  Scenario: drift detected — orphan Fact_Acct rows — independent scenario with separate identifiers to avoid cross-scenario leakage

    And metasfresh contains C_TaxCategory
      | Identifier     |
      | taxCategoryD71 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | taxD71     | taxCategoryD71   | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType |
      | vatD71     | taxD71   | Y       | T          |
    And metasfresh contains M_Products:
      | Identifier |
      | productD71 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | productD71   | 100.00   | PCE      | taxCategoryD71   |

    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceD71a | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceD71aL1 | invoiceD71a  | productD71   | 1 PCE       | taxD71   |
    And the invoice identified by invoiceD71a is completed
    And Wait until documents invoiceD71a is posted

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | tdD71      | acctSchema      | 2024-01-15 |
    And the tax declaration "tdD71" is built

    # Post second invoice → drift
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invoiceD71b | customer      | 2024-01-20   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invoiceD71bL1 | invoiceD71b  | productD71   | 3 PCE       | taxD71   |
    And the invoice identified by invoiceD71b is completed
    And Wait until documents invoiceD71b is posted

    When the drift check process is run on tax declaration "tdD71"
    Then C_TaxDeclaration "tdD71" has IsCorrectionNeeded = "Y"
