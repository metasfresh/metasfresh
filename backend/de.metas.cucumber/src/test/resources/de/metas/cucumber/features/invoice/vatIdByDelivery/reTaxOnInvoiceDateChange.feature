@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: A sales invoice line without shipment keeps the tax of its delivery address when the invoice date changes
  Changing the invoice date (by hand, or at completion for a doc type that overwrites the date) re-taxes the lines
  of an invoice that have no shipment line. A sales invoice line of an order line is re-taxed from the order line's
  delivery address and warehouse; a line of invoice candidates without order line (aggregated lines, order candidates)
  from the address its first candidate was taxed with. So the line keeps the tax it got when the invoice was created.
  A line added by hand is still re-taxed from the billing address, and purchase invoices are re-taxed as before.

  Customer (German head office) is billed in Germany and receives goods at a Dutch address with its own VAT ID.
  Customer AT is billed in Austria and has goods drop-shipped to a Dutch third party.
  Customer GR is billed in Germany and receives goods in Greece, where the organisation is registered for VAT.

  All taxes are in own tax categories. The scenarios run at 2026-11-24, except the Greek one, which runs at 2026-08-02
  with a registration valid only 2026-08-01 .. 2026-08-03, so no other feature sees it.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-11-24T10:00:00+01:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

    # own tax categories, so no other feature's taxes interfere; an empty RequiresTaxCertificate / IsFiscalRepresentation cell stores NULL.
    # TypeOfDestCountry is given so that the C_Tax interceptor keeps To_Country_ID on a new record
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory   |
      | taxCategoryPO |
    And metasfresh contains C_Tax
      | Identifier   | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | RequiresTaxCertificate | IsFiscalRepresentation |
      | taxDENL0     | taxCategory      | 0    | DE                       | NL                        | WITHIN_COUNTRY_AREA | true                   |                        |
      | taxDENL19    | taxCategory      | 19   | DE                       | NL                        | WITHIN_COUNTRY_AREA | false                  |                        |
      | taxDEAT0     | taxCategory      | 0    | DE                       | AT                        | WITHIN_COUNTRY_AREA | true                   |                        |
      | taxDEAT19    | taxCategory      | 19   | DE                       | AT                        | WITHIN_COUNTRY_AREA | false                  |                        |
      | taxDEDE19    | taxCategory      | 19   | DE                       | DE                        | DOMESTIC            |                        |                        |
      | taxGRGR24    | taxCategory      | 24   | GR                       | GR                        | DOMESTIC            |                        | true                   |
      | taxDEGR0     | taxCategory      | 0    | DE                       | GR                        | WITHIN_COUNTRY_AREA |                        | false                  |
      | taxCHNL0     | taxCategory      | 0    | CH                       | NL                        | WITHIN_COUNTRY_AREA |                        |                        |
      | taxPO_DENL0  | taxCategoryPO    | 0    | DE                       | NL                        | WITHIN_COUNTRY_AREA |                        |                        |
      | taxPO_DEDE19 | taxCategoryPO    | 19   | DE                       | DE                        | DOMESTIC            |                        |                        |

    And metasfresh contains M_Products:
      | Identifier | ProductType | REST.Context.Value |
      | service    | S           | serviceValue       |
      | productPO  | I           |                    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    # an order uses the price list of its delivery country, an invoice of a candidate the one of its billing country
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
      | pl_NL      | ps_1                          | NL           | EUR           | true  | false         |
      | pl_AT      | ps_1                          | AT           | EUR           | true  | false         |
      | pl_GR      | ps_1                          | GR           | EUR           | true  | false         |
      | plPO_DE    | ps_1                          | DE           | EUR           | false | false         |
      | plPO_NL    | ps_1                          | NL           | EUR           | false | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
      | plv_NL     | pl_NL                     | 2026-01-01 |
      | plv_AT     | pl_AT                     | 2026-01-01 |
      | plv_GR     | pl_GR                     | 2026-01-01 |
      | plvPO_DE   | plPO_DE                   | 2026-01-01 |
      | plvPO_NL   | plPO_NL                   | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pps_DE     | plv_DE                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_NL     | plv_NL                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_AT     | plv_AT                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_GR     | plv_GR                            | service                 | 50.0     | PCE               | taxCategory      |
      | ppPO_DE    | plvPO_DE                          | productPO               | 20.0     | PCE               | taxCategoryPO    |
      | ppPO_NL    | plvPO_NL                          | productPO               | 20.0     | PCE               | taxCategoryPO    |

    # line aggregation with the product only: two order lines of the same product become one invoice line without order line
    And metasfresh contains C_Aggregations:
      | Identifier         | TableName           | EntityType                | AggregationUsageLevel |
      | aggLineProductOnly | C_Invoice_Candidate | de.metas.invoicecandidate | L                     |
    And metasfresh contains C_AggregationItems:
      | Identifier | C_Aggregation_ID.Identifier | EntityType                | Type | ColumnName   |
      | aggProduct | aggLineProductOnly          | de.metas.invoicecandidate | COL  | M_Product_ID |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_billing         | DE          |
      | location_NL              | NL          |
      | location_AT_billing      | AT          |
      | location_thirdParty_NL   | NL          |
      | location_GR_billing      | DE          |
      | location_GR              | GR          |
      | location_representative  | GR          |
      | location_CH              | CH          |
      | location_vendor_billing  | DE          |
      | location_vendor_NL       | NL          |

    # customer: billed at its German head office, receives goods in the Netherlands
    # customerAT: billed in Austria, has goods drop-shipped to thirdParty in the Netherlands
    # customerGR: billed in Germany, receives goods in Greece
    # taxAgentGR: the organisation's fiscal representative in Greece; warehouseOwnerCH: the address of the Swiss warehouse
    And metasfresh contains C_BPartners without locations:
      | Identifier       | M_PricingSystem_ID.Identifier | IsCustomer | IsVendor | InvoiceRule | VATaxID     | SO_InvoiceLine_Aggregation_ID.Identifier | REST.Context.Value |
      | customer         | ps_1                          | Y          | N        | D           | DE123456788 | aggLineProductOnly                       |                    |
      | customerAT       | ps_1                          | Y          | N        | D           | ATU13585627 |                                          | customerATValue    |
      | thirdParty       | ps_1                          | Y          | N        | D           |             |                                          | thirdPartyValue    |
      | customerGR       | ps_1                          | Y          | N        | D           |             |                                          |                    |
      | taxAgentGR       | ps_1                          | N          | N        | D           |             |                                          |                    |
      | warehouseOwnerCH | ps_1                          | N          | N        | D           |             |                                          |                    |
      | vendor           | ps_1                          | N          | Y        | D           |             |                                          |                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customer_billing   | 0123456789601 | customer                 | N        | N               | Y        | Y               | location_billing             |
      | customer_NL        | 0123456789602 | customer                 | Y        | Y               | N        | N               | location_NL                  |
      | customerAT_billing | 0123456789603 | customerAT               | Y        | Y               | Y        | Y               | location_AT_billing          |
      | thirdParty_NL      | 0123456789604 | thirdParty               | Y        | Y               | Y        | Y               | location_thirdParty_NL       |
      | customerGR_billing | 0123456789605 | customerGR               | N        | N               | Y        | Y               | location_GR_billing          |
      | customerGR_GR      | 0123456789606 | customerGR               | Y        | Y               | N        | N               | location_GR                  |
      | taxAgentGR_GR      | 0123456789607 | taxAgentGR               | Y        | Y               | Y        | Y               | location_representative      |
      | warehouseOwner_CH  | 0123456789608 | warehouseOwnerCH         | Y        | Y               | Y        | Y               | location_CH                  |
      | vendor_billing     | 0123456789609 | vendor                   | N        | N               | Y        | Y               | location_vendor_billing      |
      | vendor_NL          | 0123456789610 | vendor                   | Y        | Y               | N        | N               | location_vendor_NL           |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_billing                  | DE123456788    |
      | customer_NL                       | NL123456782B01 |
      | customerAT_billing                | ATU13585627    |
      | thirdParty_NL                     | NL112233442B01 |
      | customerGR_billing                | DE123456788    |
      | customerGR_GR                     | EL123456783    |

    # wh_DE and wh_dropship are in Germany (default: the organisation's address); wh_CH is in Switzerland
    And metasfresh contains M_Warehouse:
      | Identifier  | IsDropShipWarehouse | C_BPartner_ID    | C_BPartner_Location_ID |
      | wh_DE       | N                   |                  |                        |
      | wh_dropship | Y                   |                  |                        |
      | wh_CH       | N                   | warehouseOwnerCH | warehouseOwner_CH      |

  @Id:S32533_TC7r1
  Scenario: service to the Dutch address, invoiced immediately without shipment: after the invoice date changes the line keeps the exempt Dutch rate and the invoice completes with the Dutch VAT ID
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-11-24  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate                  | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxDENL0 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |

    When update C_Invoice:
      | Identifier | OPT.DateInvoiced |
      | invoice    | 2026-11-25       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxDENL0 |
    And the invoice identified by invoice is completed
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   |
      | invoice    | CO        | NL123456782B01 |

  @Id:S32533_TC7r2
  Scenario: two order lines to the Dutch address invoiced as one line without order line: after the invoice date changes the line keeps the exempt Dutch rate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-11-24  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine1 | order                 | service                 | 10         |
      | orderLine2 | order                 | service                 | 5          |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate1                 | orderLine1                | 10           |
      | invoiceCandidate2                 | orderLine2                | 5            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate1                 | false                  |
      | invoiceCandidate2                 |                        |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate1                 | invoice                 | IP            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 15          | taxDENL0 |

    When update C_Invoice:
      | Identifier | OPT.DateInvoiced |
      | invoice    | 2026-11-25       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 15          | taxDENL0 |

  @Id:S32533_TC7r3
  Scenario: doc type that overwrites the invoice date on completion: the line keeps the exempt Dutch rate and the invoice completes
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-11-24  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices | DateInvoiced |
      | invoiceCandidate                  | false                  | 2026-11-23   |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And validate created invoices
      | Identifier | DateInvoiced |
      | invoice    | 2026-11-23   |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxDENL0 |
    And the doc type of invoice "invoice" has IsOverwriteDateOnComplete true for this scenario

    # completion sets the invoice date to today (2026-11-24), which re-taxes the lines without shipment
    When the invoice identified by invoice is completed
    Then validate created invoices
      | Identifier | DocStatus | DateInvoiced |
      | invoice    | CO        | 2026-11-24   |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxDENL0 |

  @Id:S32533_TC7r4
  Scenario: order candidate billed in Austria and drop-shipped to a Dutch third party, invoiced directly: after the invoice date changes the line keeps the exempt Dutch rate on the third party's VAT ID
    # order candidates whose data destination is invoicing are accepted by the v1 order candidate API;
    # each partner has one location, so it is found by the partner code alone
    Given a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '201' status code
  """
{
    "org": {
        "code": "001",
        "syncAdvise": { "ifNotExists": "FAIL", "ifExists": "DONT_UPDATE" }
    },
    "externalLineId": "@serviceValue@_L1",
    "externalHeaderId": "@serviceValue@_H",
    "externalSystemCode": "Other",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.invoicecandidate",
    "bpartner": {
        "bpartner": { "code": "@customerATValue@" },
        "syncAdvise": { "ifNotExists": "FAIL", "ifExists": "DONT_UPDATE" }
    },
    "dropShipBPartner": {
        "bpartner": { "code": "@thirdPartyValue@" },
        "syncAdvise": { "ifNotExists": "FAIL", "ifExists": "DONT_UPDATE" }
    },
    "dateRequired": "2026-11-24",
    "dateOrdered": "2026-11-24",
    "product": {
        "code": "@serviceValue@",
        "syncAdvise": { "ifNotExists": "FAIL", "ifExists": "DONT_UPDATE" }
    },
    "qty": 10,
    "uomCode": "PCE",
    "currencyCode": "EUR",
    "poReference": "@serviceValue@_PO",
    "orderDocType": "SalesOrder"
}
"""
    # (the header id derives from the product's random value, so that a rerun on a persistent DB does not hit the candidate of an earlier run)
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | invoiceCandidate                  | @serviceValue@_H |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate                  | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And validate created invoices
      | Identifier | C_BPartner_Location_ID | BuyerVATaxID   |
      | invoice    | customerAT_billing     | NL112233442B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxDENL0 |

    When update C_Invoice:
      | Identifier | OPT.DateInvoiced |
      | invoice    | 2026-11-25       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxDENL0 |
    And the invoice identified by invoice is completed
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   |
      | invoice    | CO        | NL112233442B01 |

  @Id:S32533_TC22d
  Scenario: service from the drop-ship warehouse to Greece with registration, billed in Germany: after the invoice date changes the line keeps GR->GR
    Given metasfresh has date and time 2026-08-02T10:00:00+02:00[Europe/Berlin]
    And the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    # the organisation's VAT registration in Greece, valid only around this scenario's date
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | C_BPartner_Representative_ID | C_BPartner_Location_ID | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL111111114 | taxAgentGR                   | taxAgentGR_GR          | 2026-08-01 | 2026-08-03 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customerGR               | 2026-08-02  | customerGR_GR                         | customerGR_billing              | wh_dropship                   | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | service                 | 10         | taxGRGR24               |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate                  | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceLine                 | invoice                 | service                 | 10          | taxGRGR24 |

    When update C_Invoice:
      | Identifier | OPT.DateInvoiced |
      | invoice    | 2026-08-03       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceLine                 | invoice                 | service                 | 10          | taxGRGR24 |

  @Id:S32533_TC7r5
  Scenario: service from a Swiss warehouse to the Dutch address: after the invoice date changes the line keeps the Swiss ship-from tax
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-11-24  | customer_NL                           | customer_billing                | wh_CH                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | service                 | 10         | taxCHNL0                |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate                  | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxCHNL0 |

    When update C_Invoice:
      | Identifier | OPT.DateInvoiced |
      | invoice    | 2026-11-25       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | service                 | 10          | taxCHNL0 |

  @Id:S32533_TC7r6
  Scenario: a line added by hand (no shipment, no order line) is re-taxed from the German billing address, as before
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-11-24  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate                  | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | handAddedLine | invoice      | service      | 3 PCE       |

    When update C_Invoice:
      | Identifier | OPT.DateInvoiced |
      | invoice    | 2026-11-25       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | handAddedLine               | invoice                 | service                 | 3           | taxDEDE19 |

  @Id:S32533_TC7r7
  Scenario: a purchase invoice line referencing a purchase order line to the Dutch address is re-taxed from the German billing address, as before
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_PricingSystem_ID.Identifier |
      | purchaseOrder | false   | vendor                   | 2026-11-24  | vendor_NL                             | vendor_billing                  | wh_DE                         | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | purchaseOrderLine | purchaseOrder         | productPO               | 10         |
    And the order identified by purchaseOrder is completed
    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInvoice | vendor        | vendor_billing         | plPO_DE        | Eingangsrechnung        | 2026-11-24   | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier          | C_Invoice_ID    | M_Product_ID | QtyInvoiced | C_OrderLine_ID    |
      | purchaseInvoiceLine | purchaseInvoice | productPO    | 10 PCE      | purchaseOrderLine |

    When update C_Invoice:
      | Identifier      | OPT.DateInvoiced |
      | purchaseInvoice | 2026-11-25       |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID     |
      | purchaseInvoiceLine         | purchaseInvoice         | productPO               | 10          | taxPO_DEDE19 |
