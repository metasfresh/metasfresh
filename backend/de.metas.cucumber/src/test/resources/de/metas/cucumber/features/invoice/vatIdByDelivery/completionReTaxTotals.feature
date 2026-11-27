@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: When completion re-taxes an invoice line, the invoice's tax rows and totals follow the new tax
  A doc type that overwrites the invoice date on completion re-taxes the invoice lines after the invoice is already
  processed. The tax row of the old tax is recomputed (or removed, when it keeps no line), the tax row of the new tax
  is created, all tax rows stay processed, and TotalLines / GrandTotal match the tax rows.

  Customer is billed and receives goods in Germany, so only the domestic rates apply and the VAT ID has no effect.
  Product19 is in a tax category with the German 19 % rate, product7 in one with the German 7 % rate (own categories,
  so no other feature's taxes interfere). The scenarios run at 2026-11-24; the invoices are dated 2026-11-23.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-11-24T10:00:00+01:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

    # TypeOfDestCountry is given so that the C_Tax interceptor keeps To_Country_ID on a new record;
    # the empty RequiresTaxCertificate cell stores NULL, so the domestic rates apply with and without a VAT ID
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory19 |
      | taxCategory7  |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry | RequiresTaxCertificate |
      | taxDEDE19  | taxCategory19    | 19   | DE                       | DE                        | DOMESTIC          |                        |
      | taxDEDE7   | taxCategory7     | 7    | DE                       | DE                        | DOMESTIC          |                        |

    And metasfresh contains M_Products:
      | Identifier | ProductType |
      | product19  | I           |
      | product7   | I           |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp19       | plv_DE                            | product19               | 100.0    | PCE               | taxCategory19    |
      | pp7        | plv_DE                            | product7                | 50.0     | PCE               | taxCategory7     |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_billing         | DE          |
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     |
      | customer   | ps_1                          | Y          | D           | DE123456788 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customer_billing | 0123456789701 | customer                 | Y        | Y               | Y        | Y               | location_billing             |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID     |
      | customer_billing                  | DE123456788 |

  @Id:S32533_TC23a
  Scenario: TC23 - completion re-taxes the only line of the old tax: the old tax row is removed, the new one is created processed and the totals include the new tax
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customer      | customer_billing       | pl_DE          | Ausgangsrechnung        | 2026-11-23   | true    | EUR                 |
    # the 7 % rate is forced on a product of the 19 % category; the re-tax at completion gives it the 19 % rate
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID$set |
      | line19     | invoice      | product19    | 10 PCE      | taxDEDE7     |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | line19                      | invoice                 | product19               | 10          | taxDEDE7 |
    And the doc type of invoice "invoice" has IsOverwriteDateOnComplete true for this scenario

    # completion sets the invoice date to today (2026-11-24), which re-taxes the line from the German billing address
    When the invoice identified by invoice is completed
    Then validate created invoices
      | Identifier | DocStatus | DateInvoiced | TotalLines | GrandTotal |
      | invoice    | CO        | 2026-11-24   | 1000       | 1190 EUR   |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | line19                      | invoice                 | product19               | 10          | taxDEDE19 |
    And the invoice "invoice" has 1 C_InvoiceTax records
    And C_InvoiceTax records for invoice "invoice" are matching
      | C_Tax_ID  | TaxBaseAmt | TaxAmt | Processed |
      | taxDEDE19 | 1000       | 190    | true      |

  @Id:S32533_TC23b
  Scenario: TC23 - completion re-taxes one of two lines of the old tax: the old tax row covers the remaining line, the new one is created processed and the totals include both taxes
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customer      | customer_billing       | pl_DE          | Ausgangsrechnung        | 2026-11-23   | true    | EUR                 |
    # line7 keeps the 7 % rate of its category; line19 has the 7 % rate forced on a product of the 19 % category
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID$set |
      | line7      | invoice      | product7     | 10 PCE      | taxDEDE7     |
      | line19     | invoice      | product19    | 10 PCE      | taxDEDE7     |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | line7                       | invoice                 | product7                | 10          | taxDEDE7 |
      | line19                      | invoice                 | product19               | 10          | taxDEDE7 |
    And the doc type of invoice "invoice" has IsOverwriteDateOnComplete true for this scenario

    # completion sets the invoice date to today (2026-11-24), which re-taxes the lines from the German billing address
    When the invoice identified by invoice is completed
    Then validate created invoices
      | Identifier | DocStatus | DateInvoiced | TotalLines | GrandTotal |
      | invoice    | CO        | 2026-11-24   | 1500       | 1725 EUR   |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | line7                       | invoice                 | product7                | 10          | taxDEDE7  |
      | line19                      | invoice                 | product19               | 10          | taxDEDE19 |
    And the invoice "invoice" has 2 C_InvoiceTax records
    And C_InvoiceTax records for invoice "invoice" are matching
      | C_Tax_ID  | TaxBaseAmt | TaxAmt | Processed |
      | taxDEDE7  | 500        | 35     | true      |
      | taxDEDE19 | 1000       | 190    | true      |
