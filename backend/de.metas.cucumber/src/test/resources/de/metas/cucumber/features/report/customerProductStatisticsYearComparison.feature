@from:cucumber
@ghActions:run_on_executor2
Feature: Customer/Product Statistics (Long) - customer total lines
  ## The report "Customer/Product Statistics (Long)" prints, below the products of each customer, a total line
  ## "Summe für Kunde <customer name>" with the customer's current-year amount, and below it "Vorperiode:" with the
  ## previous-year amount. The per-product deviation totals cell stays empty.
  ## - Data: customer A has 11 products, invoiced in 2025 (1 PCE x 90.00 each) and 2026 (1 PCE x 100.00 each);
  ##   customer B only in 2025 (cpsA01, 50.00); customer C only in 2026 (cpsA01, 70.00).
  ## - Amount is read from the accounting, so the report runs once the invoices are posted.
  ## - Number format: de_DE 1.234,56 / en_US 1,234.56. A: 2026 = 11x100 = 1.100,00; 2025 = 11x90 = 990,00.
  ## - Customer A's name has 61 characters on purpose: it is the long-name case (the name must stay on one line next to
  ##   the label, and wrap instead of overlapping the amount when longer).
  ## - PriceStd 100.00 in the price list is only the list price; the invoice line Price overrides it.
  ## - The Saldo is not asserted here (proven on the live instance).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-01-02T10:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And all periods are open
    And metasfresh contains C_Calendar "CPS_Calendar" with fiscal years:
      | C_Year_ID | FiscalYear |
      | yearPrev  | 2025       |
      | yearN     | 2026       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsQualityReturnWarehouse |
      | warehouseQR    | Y                        |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_TaxCategory
      | Identifier |
      | cpsTaxCat  |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | cpsTax19   | cpsTaxCat        | 19   | DE                       | DE                        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | cpsPS      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | cpsPL      | cpsPS              | DE           | CHF           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | cpsPLV     | cpsPL          |

    And metasfresh contains M_Products:
      | Identifier |
      | cpsA01     |
      | cpsA02     |
      | cpsA03     |
      | cpsA04     |
      | cpsA05     |
      | cpsA06     |
      | cpsA07     |
      | cpsA08     |
      | cpsA09     |
      | cpsA10     |
      | cpsA11     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | cpsPLV                 | cpsA01       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA02       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA03       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA04       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA05       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA06       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA07       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA08       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA09       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA10       | 100.00   | PCE      | cpsTaxCat        |
      | cpsPLV                 | cpsA11       | 100.00   | PCE      | cpsTaxCat        |

  @from:cucumber
  Scenario: Customer total lines - Summe fuer Kunde and Vorperiode
    # Unique partner values: C_BPartner_StepDef reuses an existing partner with the same Value
    Given metasfresh contains C_BPartners without locations:
      | Identifier   | Value        | Name                                                          | IsCustomer | M_PricingSystem_ID | AllowConsolidateInOut |
      | cpsCustomerA | CPS-A-@Date@ | Kundensumme Testkunde Matjes und Feinkost Handelsgesellschaft | Y          | cpsPS              | N                     |
      | cpsCustomerB | CPS-B-@Date@ | Kundensumme Vorjahreskunde                                    | Y          | cpsPS              | N                     |
      | cpsCustomerC | CPS-C-@Date@ | Kundensumme Neukunde                                          | Y          | cpsPS              | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | cpsCustomerALoc | cpsCustomerA  | DE           | Y               | Y               |
      | cpsCustomerBLoc | cpsCustomerB  | DE           | Y               | Y               |
      | cpsCustomerCLoc | cpsCustomerC  | DE           | Y               | Y               |

    # 2025: customer A all products at 90.00, customer B cpsA01 at 50.00
    And metasfresh has date and time 2025-06-16T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | cpsInvA25  | cpsCustomerA  | 2025-06-16   | true    | CHF           | cpsPL          |
      | cpsInvB25  | cpsCustomerB  | 2025-06-16   | true    | CHF           | cpsPL          |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | cpsInvA25_l1  | cpsInvA25    | cpsA01       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l2  | cpsInvA25    | cpsA02       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l3  | cpsInvA25    | cpsA03       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l4  | cpsInvA25    | cpsA04       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l5  | cpsInvA25    | cpsA05       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l6  | cpsInvA25    | cpsA06       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l7  | cpsInvA25    | cpsA07       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l8  | cpsInvA25    | cpsA08       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l9  | cpsInvA25    | cpsA09       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l10 | cpsInvA25    | cpsA10       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvA25_l11 | cpsInvA25    | cpsA11       | 1 PCE       | 90.00 | cpsTax19     |
      | cpsInvB25_l1  | cpsInvB25    | cpsA01       | 1 PCE       | 50.00 | cpsTax19     |
    And the invoice identified by cpsInvA25 is completed
    And the invoice identified by cpsInvB25 is completed

    # 2026: customer A all products at 100.00, customer C cpsA01 at 70.00
    And metasfresh has date and time 2026-06-16T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | cpsInvA26  | cpsCustomerA  | 2026-06-16   | true    | CHF           | cpsPL          |
      | cpsInvC26  | cpsCustomerC  | 2026-06-16   | true    | CHF           | cpsPL          |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | cpsInvA26_l1  | cpsInvA26    | cpsA01       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l2  | cpsInvA26    | cpsA02       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l3  | cpsInvA26    | cpsA03       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l4  | cpsInvA26    | cpsA04       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l5  | cpsInvA26    | cpsA05       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l6  | cpsInvA26    | cpsA06       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l7  | cpsInvA26    | cpsA07       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l8  | cpsInvA26    | cpsA08       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l9  | cpsInvA26    | cpsA09       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l10 | cpsInvA26    | cpsA10       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvA26_l11 | cpsInvA26    | cpsA11       | 1 PCE       | 100.00 | cpsTax19     |
      | cpsInvC26_l1  | cpsInvC26    | cpsA01       | 1 PCE       | 70.00  | cpsTax19     |
    And the invoice identified by cpsInvA26 is completed
    And the invoice identified by cpsInvC26 is completed

    # Today is after the last document, inside year N
    And metasfresh has date and time 2026-10-27T10:00:00+01:00[Europe/Berlin]
    And Wait until documents cpsInvA25, cpsInvB25, cpsInvA26, cpsInvC26 are posted

    # Run 1: customer A, de_DE
    # Why 11 products: a page holds 5 products on the first page and 6 on later ones, so 10 products fill two pages exactly
    # and the 40 pt customer footer still fits on page 2 (10 = GREEN). With 11, the last product fills page 2 and,
    # without preventOrphanFooter, the footer alone lands on page 3 (first "Summe für Kunde" on page 3 bottom 106.6,
    # last "cpsA11" on page 2 bottom 504.6 = RED). With preventOrphanFooter the last product moves to page 3 with the footer.
    # The indent check below is anchored to the product-number column (cpsA11, same page as the footer) because the
    # group-header customer name is printed on page 1 only.
    When the customer product statistics report is run for year "yearN", customer "cpsCustomerA", product "-" in language "de_DE", archived for the record identified by "cpsCustomerA"
    And in the PDF archived for the record identified by "cpsCustomerA", text "Summe für Kunde" is on the same page as the last occurrence of text "cpsA11"
    And the visual line containing text "Summe für Kunde" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Summe für Kunde Kundensumme Testkunde Matjes und Feinkost Handelsgesellschaft 1.100,00"
    And the visual line containing text "Vorperiode:" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Vorperiode: 990,00"
    And in the PDF archived for the record identified by "cpsCustomerA", exactly 0 lines appear between text "Summe für Kunde" and text "Vorperiode:"
    And in the PDF archived for the record identified by "cpsCustomerA", text "Summe für Kunde" is printed in bold
    And in the PDF archived for the record identified by "cpsCustomerA", text "Vorperiode:" is printed in bold
    And in the PDF archived for the record identified by "cpsCustomerA", text "1.100,00" is printed in bold
    And in the PDF archived for the record identified by "cpsCustomerA", text "990,00" is printed in bold
    And in the PDF archived for the record identified by "cpsCustomerA", text "Kundensumme Testkunde" starts right of where text "Summe für Kunde" ends on the same line
    And in the PDF archived for the record identified by "cpsCustomerA", text "1.100,00" starts right of where text "Summe für Kunde" ends on the same line
    And in the PDF archived for the record identified by "cpsCustomerA", text "Summe für Kunde" starts right of where text "cpsA11" starts
    And in the PDF archived for the record identified by "cpsCustomerA", text "990,00" starts right of where text "Vorperiode:" ends on the same line
    And in the PDF archived for the record identified by "cpsCustomerA", text "Vorperiode:" starts right of where text "Summe für Kunde" starts
    And in the PDF archived for the record identified by "cpsCustomerA", text "Vorperiode:" ends left of where text "November" starts
    And the PDF archived for the record identified by "cpsCustomerA" does not contain text "Abweichung % 90,00"
    And the visual line containing text "Abweichung %" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Abweichung %"
    And the PDF archived for the record identified by "cpsCustomerA" does not contain text "Kundensumme Vorjahreskunde"

    # Run 2: customer B (previous year only), de_DE
    When the customer product statistics report is run for year "yearN", customer "cpsCustomerB", product "-" in language "de_DE", archived for the record identified by "cpsCustomerB"
    And the visual line containing text "Summe für Kunde" in the PDF archived for the record identified by "cpsCustomerB" is exactly "Summe für Kunde Kundensumme Vorjahreskunde 0,00"
    And the visual line containing text "Vorperiode:" in the PDF archived for the record identified by "cpsCustomerB" is exactly "Vorperiode: 50,00"
    And the visual line containing text "Abweichung %" in the PDF archived for the record identified by "cpsCustomerB" is exactly "Abweichung %"

    # Run 3: all customers, product cpsA01 only (two footers in one run)
    When the customer product statistics report is run for year "yearN", customer "-", product "cpsA01" in language "de_DE", archived for the record identified by "cpsCustomerA"
    And the visual line containing text "Summe für Kunde Kundensumme Testkunde" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Summe für Kunde Kundensumme Testkunde Matjes und Feinkost Handelsgesellschaft 100,00"
    And in the PDF archived for the record identified by "cpsCustomerA", exactly 0 lines appear between text "Summe für Kunde Kundensumme Testkunde" and text "Vorperiode: 90,00"
    And the visual line containing text "Summe für Kunde Kundensumme Vorjahreskunde" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Summe für Kunde Kundensumme Vorjahreskunde 0,00"
    And in the PDF archived for the record identified by "cpsCustomerA", exactly 0 lines appear between text "Summe für Kunde Kundensumme Vorjahreskunde" and text "Vorperiode: 50,00"
    And the visual line containing text "Summe für Kunde Kundensumme Neukunde" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Summe für Kunde Kundensumme Neukunde 70,00"
    And in the PDF archived for the record identified by "cpsCustomerA", exactly 0 lines appear between text "Summe für Kunde Kundensumme Neukunde" and text "Vorperiode: 0,00"

    # Run 4: customer A, en_US
    When the customer product statistics report is run for year "yearN", customer "cpsCustomerA", product "-" in language "en_US", archived for the record identified by "cpsCustomerA"
    And the visual line containing text "Summe für Kunde" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Summe für Kunde Kundensumme Testkunde Matjes und Feinkost Handelsgesellschaft 1,100.00"
    And the visual line containing text "Vorperiode:" in the PDF archived for the record identified by "cpsCustomerA" is exactly "Vorperiode: 990.00"
