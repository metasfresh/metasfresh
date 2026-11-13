@from:cucumber
@ghActions:run_on_executor3
Feature: Programmatic Void on a completed sales invoice
## - The document actions offered to a user on a completed invoice do not include Void (Reverse-Correct only),
##   but the document engine still accepts Void when it is requested programmatically (e.g. by a process or an import).
## - On a completed invoice the engine runs reverse-correct for Void: the invoice and its reversal invoice both end up with DocStatus RE.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | lowTaxCateg |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | lowTax     | lowTaxCateg      | 7    | DE                       | DE                        |
    And metasfresh contains M_Products:
      | Identifier   | OPT.IsStocked |
      | voidProduct  | false         |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | voidPS     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name   | SOTrx | IsTaxIncluded | PricePrecision |
      | voidPL     | voidPS                        | DE                        | EUR                 | voidPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name    | ValidFrom  |
      | voidPLV    | voidPL                    | voidPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_void    | voidPLV                           | voidProduct             | 10       | PCE               | lowTaxCateg      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | voidBP     | Y              | N            | voidPS                        | I               |

  @from:cucumber
  Scenario: A completed sales invoice processed with DocAction VO is reversed and has a reversal invoice
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | voidBP        | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | voidLine   | invoice      | voidProduct  | 2 PCE       |
    And the invoice identified by invoice is completed
    And validate created invoices
      | C_Invoice_ID | processed | DocStatus |
      | invoice      | true      | CO        |

    When the invoice identified by invoice is processed with DocAction VO expecting DocStatus RE

    Then the reversal of invoice invoice is identified by reversalInvoice
    And validate created invoices
      | C_Invoice_ID    | processed | DocStatus |
      | invoice         | true      | RE        |
      | reversalInvoice | true      | RE        |
