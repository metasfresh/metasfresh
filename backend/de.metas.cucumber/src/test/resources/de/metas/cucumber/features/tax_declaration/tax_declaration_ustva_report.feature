@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@ghActions:run_on_executor5
Feature: Tax Declaration UStVA report ("Umsatzsteuer-Voranmeldung")
##
## Coverage for the report function report.tax_declaration_ustva_report(C_TaxDeclaration_ID, AD_Language)
## behind the UStVA PDF printed from the Tax Declaration window.
##
## - The report lists whatever VAT codes the declaration holds: one SUMMARY row per code, ordered by code.
## - Figures come from the declaration only (never from live Fact_Acct amounts).
## - Each scenario creates its own VAT codes; the report step looks only at those codes.
##   Rows of codes of other scenarios in the same period are ignored.
## - The posting of invoices and allocations is real; the declaration is built by the real build function.

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
    And load C_AcctSchema:
      | Identifier |
      | acctSchema |
    And metasfresh contains C_TaxCategory
      | Identifier   |
      | taxCategory  |
      | taxCategory2 |
      | taxCategory3 |
      | taxCategory4 |


# ############################################################################################################################################
# Header: organisation, schema, period, document no., status (draft and completed), Original
# ############################################################################################################################################
  @Id:S32216_UStVA_010
  @from:cucumber
  Scenario: the header shows the declaration data for a draft and for a completed declaration

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | Description        |
      | v81        | tax19    | Y       | N          | Lieferungen 19 %   |
    And metasfresh contains M_Products:
      | Identifier |
      | p100       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | p100         | 100.00   | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv81      | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | inv81L1    | inv81        | p100         | 1 PCE       | tax19    |
    And the invoice identified by inv81 is completed
    And Wait until documents inv81 is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    Then the UStVA report for tax declaration "td" returns:
      | report_level | docstatus | is_correction | Original_ID |
      | HEADER       | DR        | N             | -           |

    When the tax declaration "td" is completed

    Then the UStVA report for tax declaration "td" returns:
      | report_level | docstatus | is_correction | Original_ID |
      | HEADER       | CO        | N             | -           |


# ############################################################################################################################################
# Summary: one row per code present, ordered by code; sales codes print positive, input tax prints positive;
# no tax line -> empty tax; balance = - sum of declared tax; refund prints negative; codes are not a fixed list
# ############################################################################################################################################
  @Id:S32216_UStVA_020
  @from:cucumber
  Scenario: one summary row per VAT code of the declaration, with the balance as the negated sum of the declared tax lines

    # five codes, in the shape of Kz 81 / 41 (sales net), 66 (input tax), 66a (input net), 61 (input tax)
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19s     | taxCategory      | 19   | DE                       | DE                        |
      | tax0s      | taxCategory2     | 0    | DE                       | DE                        |
      | tax19p     | taxCategory3     | 19   | DE                       | DE                        |
      | tax16p     | taxCategory4     | 16   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | Description        |
      | v81N       | tax19s   | Y       | N          | Lieferungen 19 %   |
      | v41N       | tax0s    | Y       | N          |                    |
      | v66T       | tax19p   | N       | T          |                    |
      | v66aN      | tax19p   | N       | N          |                    |
      | v61T       | tax16p   | N       | T          |                    |
    And metasfresh contains M_Products:
      | Identifier |
      | p100       |
      | p1000      |
      | p8655      |
      | p200       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | p100         | 100.00   | PCE      | taxCategory      |
      | salesPLV               | p1000        | 1000.00  | PCE      | taxCategory2     |
      | purchasePLV            | p8655        | 86.55    | PCE      | taxCategory3     |
      | purchasePLV            | p200         | 200.00   | PCE      | taxCategory4     |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv81      | customer      | 2024-01-15   | true    | EUR           |
      | inv41      | customer      | 2024-01-15   | true    | EUR           |
      | inv66      | vendor        | 2024-01-15   | false   | EUR           |
      | inv61      | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | inv81L1    | inv81        | p100         | 1 PCE       | tax19s   |
      | inv41L1    | inv41        | p1000        | 1 PCE       | tax0s    |
      | inv66L1    | inv66        | p8655        | 1 PCE       | tax19p   |
      | inv61L1    | inv61        | p200         | 1 PCE       | tax16p   |
    And the invoice identified by inv81 is completed
    And the invoice identified by inv41 is completed
    And the invoice identified by inv66 is completed
    And the invoice identified by inv61 is completed
    And Wait until documents inv81, inv41, inv66, inv61 are posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    # Net: sales and input base printed positive, whole euros cut off (86.55 -> 86); a code without a tax line has an empty tax.
    # Tax: input tax printed positive, with cents; a code without a net line has an empty net.
    # Balance = - (16.44 + 32.00): negative = refund.
    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | description      | net_amt | tax_amt | balance_amt |
      | SUMMARY      | v81N          | Lieferungen 19 % | 100     | -       |             |
      | SUMMARY      | v41N          | -                | 1000    | -       |             |
      | SUMMARY      | v66T          | -                | -       | 16.44   |             |
      | SUMMARY      | v66aN         | -                | 86      | -       |             |
      | SUMMARY      | v61T          | -                | -       | 32.00   |             |
      | BALANCE      |               |                  |         |         | -48.44      |


# ############################################################################################################################################
# Credit notes reduce the code; cut-off toward zero for positive and negative bases; zero lines are dropped
# ############################################################################################################################################
  @Id:S32216_UStVA_030
  @from:cucumber
  Scenario: credit notes reduce the net, cents are cut off toward zero and zero-amount lines are not printed

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | taxA       | taxCategory      | 19   | DE                       | DE                        |
      | taxB       | taxCategory2     | 7    | DE                       | DE                        |
      | taxC       | taxCategory3     | 16   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType |
      | vA         | taxA     | Y       | N          |
      | vB         | taxB     | Y       | N          |
      | vC         | taxC     | Y       | N          |
    And metasfresh contains M_Products:
      | Identifier |
      | p100       |
      | p2099      |
      | p2099b     |
      | p50        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | p100         | 100.00   | PCE      | taxCategory      |
      | salesPLV               | p2099        | 20.99    | PCE      | taxCategory      |
      | salesPLV               | p2099b       | 20.99    | PCE      | taxCategory2     |
      | salesPLV               | p50          | 50.00    | PCE      | taxCategory3     |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invA       | customer      | 2024-01-15   | true    | EUR           |
      | invC       | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID |
      | credA      | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
      | credB      | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
      | credC      | customer      | Gutschrift              | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invAL1     | invA         | p100         | 1 PCE       | taxA     |
      | credAL1    | credA        | p2099        | 1 PCE       | taxA     |
      | credBL1    | credB        | p2099b       | 1 PCE       | taxB     |
      | invCL1     | invC         | p50          | 1 PCE       | taxC     |
      | credCL1    | credC        | p50          | 1 PCE       | taxC     |
    And the invoice identified by invA is completed
    And the invoice identified by credA is completed
    And the invoice identified by credB is completed
    And the invoice identified by invC is completed
    And the invoice identified by credC is completed
    And Wait until documents invA, credA, credB, invC, credC are posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    # vA: 100.00 - 20.99 = 79.01 -> 79.   vB: credit note only = -20.99 -> -20.   vC: 50.00 - 50.00 = 0 -> no row.
    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt |
      | SUMMARY      | vA            | 79      | -       |
      | SUMMARY      | vB            | -20     | -       |
    And the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | AmountType | Record_ID | amount |
      | DETAIL       | vA            | N          | invA      | 100    |
      | DETAIL       | vA            | N          | credA     | -20.99 |
      | DETAIL       | vB            | N          | credB     | -20.99 |


# ############################################################################################################################################
# Detail list: per code every snapshot row with its source document; detail sums equal the summary before the cut-off
# ############################################################################################################################################
  @Id:S32216_UStVA_040
  @from:cucumber
  Scenario: detail rows per code add up to the declared amounts before the cut-off and show the source document

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19p     | taxCategory      | 19   | DE                       | DE                        |
    # net and tax of the same VAT code: one code "v66" with a Net row and a Tax row
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | v66N       | tax19p   | N       | N          |               |
      | v66T       | tax19p   | N       | T          | v66N          |
    And metasfresh contains M_Products:
      | Identifier |
      | p60        |
      | p25        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | p60          | 60.00    | PCE      | taxCategory      |
      | purchasePLV            | p25          | 25.00    | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invP1      | vendor        | 2024-01-15   | false   | EUR           |
      | invP2      | vendor        | 2024-01-18   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invP1L1    | invP1        | p60          | 1 PCE       | tax19p   |
      | invP2L1    | invP2        | p25          | 1 PCE       | tax19p   |
    And the invoice identified by invP1 is completed
    And the invoice identified by invP2 is completed
    And Wait until documents invP1, invP2 are posted
    # the VAT ID is entered on the partner after posting: a partner with a VAT ID only matches taxes that require a tax certificate,
    # and the report reads the partner's current VAT ID at print time
    And update C_BPartner:
      | Identifier | VATaxID     |
      | vendor     | DE811111113 |
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    # net 60.00 + 25.00 = 85.00; tax 11.40 + 4.75 = 16.15
    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt |
      | SUMMARY      | v66N          | 85      | 16.15   |
    And the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | AmountType | Record_ID | amount | posting_date | doc_date   | C_BPartner_ID | bpartner_vatid |
      | DETAIL       | v66N          | N          | invP1     | 60.00  | 2024-01-15   | 2024-01-15 | vendor        | DE811111113    |
      | DETAIL       | v66N          | T          | invP1     | 11.40  | 2024-01-15   | 2024-01-15 | vendor        | DE811111113    |
      | DETAIL       | v66N          | N          | invP2     | 25.00  | 2024-01-18   | 2024-01-18 | vendor        | DE811111113    |
      | DETAIL       | v66N          | T          | invP2     | 4.75   | 2024-01-18   | 2024-01-18 | vendor        | DE811111113    |


# ############################################################################################################################################
# Source documents: allocation (discount) rows and an invoice of the same code; display sign for a sales code
# ############################################################################################################################################
  @Id:S32216_UStVA_050
  @from:cucumber
  Scenario: an allocation and an invoice both appear in the detail list; a sales code prints positive, the balance is payable

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19s     | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | v81N       | tax19s   | Y       | N          |               |
      | v81T       | tax19s   | Y       | T          | v81N          |
    And metasfresh contains M_Products:
      | Identifier |
      | p1000      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | p1000        | 1000.00  | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv        | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invL1      | inv          | p1000        | 1 PCE       | tax19s   |
    And the invoice identified by inv is completed
    # discount of 23.80 (20.00 net + 3.80 tax) reduces the tax liability by 3.80
    And create and complete manual payment allocations
      | C_AllocationHdr_ID | C_Invoice_ID | DiscountAmt |
      | alloc              | inv          | 23.80 EUR   |
    And Wait until documents inv, alloc are posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    # declared tax = -190.00 + 3.80 = -186.20 (credit); the sales code prints it positive; balance = payable 186.20
    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
      | SUMMARY      | v81N          | 1000    | 186.20  |             |
      | BALANCE      |               |         |         | 186.20      |
    And the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | AmountType | Record_ID | amount | posting_date |
      | DETAIL       | v81N          | N          | inv       | 1000   | 2024-01-15   |
      | DETAIL       | v81N          | T          | inv       | 190    | 2024-01-15   |
      | DETAIL       | v81N          | T          | alloc     | -3.80  | 2024-01-15   |


# ############################################################################################################################################
# A code configured for sales and purchase prints the declared sign (debit - credit)
# ############################################################################################################################################
  @Id:S32216_UStVA_060
  @from:cucumber
  Scenario: a VAT code used on the sales and on the purchase side prints the declared debit minus credit

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | vMixSales  | tax19    | Y       | T          |               |
      | vMixBuy    | tax19    | N       | T          | vMixSales     |
    And metasfresh contains M_Products:
      | Identifier |
      | p1000      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | p1000        | 1000.00  | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | inv        | customer      | 2024-01-15   | true    | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invL1      | inv          | p1000        | 1 PCE       | tax19    |
    And the invoice identified by inv is completed
    And Wait until documents inv is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    # sales invoice tax = credit 190.00; the code is not sales-only, so no sign flip: -190.00; balance = +190.00
    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
      | SUMMARY      | vMixSales     | -       | -190.00 |             |
      | BALANCE      |               |         |         | 190.00      |


# ############################################################################################################################################
# Frozen snapshot: a completed declaration prints the same after new postings and a repost
# ############################################################################################################################################
  @Id:S32216_UStVA_070
  @from:cucumber
  Scenario: a completed declaration prints the same amounts after a repost and after another posting in the period

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19p     | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | v66N       | tax19p   | N       | N          |               |
      | v66T       | tax19p   | N       | T          | v66N          |
    And metasfresh contains M_Products:
      | Identifier |
      | p60        |
      | p25        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | p60          | 60.00    | PCE      | taxCategory      |
      | purchasePLV            | p25          | 25.00    | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invP1      | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invP1L1    | invP1        | p60          | 1 PCE       | tax19p   |
    And the invoice identified by invP1 is completed
    And Wait until documents invP1 is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed

    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
      | SUMMARY      | v66N          | 60      | 11.40   |             |
      | BALANCE      |               |         |         | -11.40      |
    And the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | AmountType | Record_ID | amount |
      | DETAIL       | v66N          | N          | invP1     | 60.00  |
      | DETAIL       | v66N          | T          | invP1     | 11.40  |

    # one posting is posted again and another invoice of the same code is posted in the period
    When the documents invP1 are reposted
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invP2      | vendor        | 2024-01-20   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invP2L1    | invP2        | p25          | 1 PCE       | tax19p   |
    And the invoice identified by invP2 is completed
    And Wait until documents invP2 is posted

    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
      | SUMMARY      | v66N          | 60      | 11.40   |             |
      | BALANCE      |               |         |         | -11.40      |
    And the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | AmountType | Record_ID | amount |
      | DETAIL       | v66N          | N          | invP1     | 60.00  |
      | DETAIL       | v66N          | T          | invP1     | 11.40  |


# ############################################################################################################################################
# A snapshot row whose source document cannot be found still prints, with its amount and empty document fields
# ############################################################################################################################################
  @Id:S32216_UStVA_080
  @from:cucumber
  Scenario: a snapshot row without a findable source document is printed with its amount and empty document fields

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19p     | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | v66N       | tax19p   | N       | N          |               |
      | v66T       | tax19p   | N       | T          | v66N          |
    And metasfresh contains M_Products:
      | Identifier |
      | p60        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | p60          | 60.00    | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invP1      | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invP1L1    | invP1        | p60          | 1 PCE       | tax19p   |
    And the invoice identified by invP1 is completed
    And Wait until documents invP1 is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the source document "invP1" of tax declaration "td" no longer exists

    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt |
      | SUMMARY      | v66N          | 60      | 11.40   |
    And the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | AmountType | Record_ID | amount | C_BPartner_ID | bpartner_vatid |
      | DETAIL       | v66N          | N          | -         | 60.00  | -             | -              |
      | DETAIL       | v66N          | T          | -         | 11.40  | -             | -              |


# ############################################################################################################################################
# Correction: header shows Correction and the Original's document no.
# ############################################################################################################################################
  @Id:S32216_UStVA_090
  @from:cucumber
  Scenario: a correction declaration prints as Correction with the document no. of its Original

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19p     | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | v66N       | tax19p   | N       | N          |               |
      | v66T       | tax19p   | N       | T          | v66N          |
    And metasfresh contains M_Products:
      | Identifier |
      | p60        |
      | p25        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | p60          | 60.00    | PCE      | taxCategory      |
      | purchasePLV            | p25          | 25.00    | PCE      | taxCategory      |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invP1      | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invP1L1    | invP1        | p60          | 1 PCE       | tax19p   |
    And the invoice identified by invP1 is completed
    And Wait until documents invP1 is posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built
    And the tax declaration "td" is completed

    # a later posting in the same period makes a correction necessary
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invP2      | vendor        | 2024-01-20   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invP2L1    | invP2        | p25          | 1 PCE       | tax19p   |
    And the invoice identified by invP2 is completed
    And Wait until documents invP2 is posted
    And invoke Create Correction on C_TaxDeclaration "td"
    And the tax declaration "td_correction" is built

    Then the UStVA report for tax declaration "td" returns:
      | report_level | docstatus | is_correction | Original_ID |
      | HEADER       | CO        | N             | -           |
    And the UStVA report for tax declaration "td_correction" returns:
      | report_level | docstatus | is_correction | Original_ID |
      | HEADER       | DR        | Y             | td          |
    # the correction restates the whole period
    And the UStVA report for tax declaration "td_correction" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
      | SUMMARY      | v66N          | 85      | 16.15   |             |
      | BALANCE      |               |         |         | -16.15      |


# ############################################################################################################################################
# Empty declaration: header and a zero balance, no code rows
# ############################################################################################################################################
  @Id:S32216_UStVA_100
  @from:cucumber
  Scenario: a declaration without lines prints the header and a zero balance

    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |

    Then the UStVA report for tax declaration "td" returns:
      | report_level | docstatus | is_correction | Original_ID |
      | HEADER       | DR        | N             | -           |
    And the UStVA report for tax declaration "td" returns:
      | report_level | balance_amt |
      | BALANCE      | 0           |


# ############################################################################################################################################
# Output VAT and input VAT in one period: the balance nets the declared output tax (Kz 81 net + tax rows) against the input tax (Kz 66)
# ############################################################################################################################################
  @Id:S32216_UStVA_110
  @from:cucumber
  Scenario: the balance nets the output tax of a sales code against the input tax of a purchase code

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19s     | taxCategory      | 19   | DE                       | DE                        |
      | tax19p     | taxCategory2     | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier | C_Tax_ID | IsSOTrx | AmountType | SameVATCodeAs |
      | v81N       | tax19s   | Y       | N          |               |
      | v81T       | tax19s   | Y       | T          | v81N          |
      | v66T       | tax19p   | N       | T          |               |
    And metasfresh contains M_Products:
      | Identifier |
      | p20        |
      | p8655      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | salesPLV               | p20          | 20.00    | PCE      | taxCategory      |
      | purchasePLV            | p8655        | 86.55    | PCE      | taxCategory2     |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | invS       | customer      | 2024-01-15   | true    | EUR           |
      | invP       | vendor        | 2024-01-15   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | invSL1     | invS         | p20          | 1 PCE       | tax19s   |
      | invPL1     | invP         | p8655        | 1 PCE       | tax19p   |
    And the invoice identified by invS is completed
    And the invoice identified by invP is completed
    And Wait until documents invS, invP are posted
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       |
      | td         | acctSchema      | 2024-01-15 |
    And the tax declaration "td" is built

    # output tax 20.00 * 19 % = 3.80 (credit, printed positive); input tax 86.55 * 19 % = 16.44; balance = -(-3.80 + 16.44) = -12.64 (refund)
    Then the UStVA report for tax declaration "td" returns:
      | report_level | C_VAT_Code_ID | net_amt | tax_amt | balance_amt |
      | SUMMARY      | v66T          | -       | 16.44   |             |
      | SUMMARY      | v81N          | 20      | 3.80    |             |
      | BALANCE      |               |         |         | -12.64      |


# ############################################################################################################################################
# Header: tax number and VAT ID come only from the organisation's business partner
# ############################################################################################################################################
  @Id:S32216_UStVA_120
  @from:cucumber
  Scenario: the header shows the tax number and the VAT ID of the organisation's business partner

    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Value      | Name            |
      | ustvaOrg             | USTVAORG   | UStVA Test Org  |
    And metasfresh contains C_BPartners without locations:
      | Identifier | TaxID        | VATaxID     | AD_OrgBP_ID.Identifier |
      | ustvaOrgBP | 21/815/08150 | DE136695976 | ustvaOrg               |
    And metasfresh contains C_TaxDeclaration:
      | Identifier | C_AcctSchema_ID | Date       | AD_Org_ID.Identifier |
      | td         | acctSchema      | 2024-01-15 | ustvaOrg             |

    Then the UStVA report for tax declaration "td" returns:
      | report_level | org_tax_id   | org_vat_id  |
      | HEADER       | 21/815/08150 | DE136695976 |
