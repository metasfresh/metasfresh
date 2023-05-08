@from:cucumber
Feature: create invoices using invoice API

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-12-21T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S0248_100
  Scenario: create invoice with 1 x line with acct and priceEntered info
  - action.completeIt = 'Y'

    Given load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | endCustomerLocation_1             | 2205175                    |

    And load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | product_1               | 2005577          |

    And load C_Tax:
      | C_Tax_ID.Identifier | OPT.C_Tax_ID |
      | tax_1               | 1000023      |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | docType                 | ARI             | true          |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | elementValue_1               | element_1               | 69100 |

    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type             | ExternalReference          | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier |
      | billBPartner_ref                  | SAP            | BPartner         | bpartner_reference         | endCustomer_1                |                                       |                             |
      | billBPartnerLocation_ref          | SAP            | BPartnerLocation | bpartnerLocation_reference |                              | endCustomerLocation_1                 |                             |
      | product_ref                       | SAP            | Product          | product_reference          |                              |                                       | product_1                   |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '200' status code
    """
{
  "invoice": {
    "action": {
      "completeIt": true
    },
    "header": {
      "orgCode": "001",
      "acctSchemaCode": "metas fresh UN/34 CHF",
      "billPartnerIdentifier": "ext-SAP-bpartner_reference",
      "billLocationIdentifier": "ext-SAP-bpartnerLocation_reference",
      "dateInvoiced": "2022-01-20",
      "dateAcct": "2022-01-19",
      "dateOrdered": "2022-01-18",
      "externalHeaderId": "externalHeaderId_15022023_1",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference",
      "soTrx": "SALES",
      "currencyCode": "EUR",
      "grandTotal": 428,
      "taxTotal": 28
    },
    "lines": [
      {
        "externalLineId": "externalLineId",
        "line": 10,
        "lineDescription": "lineDescription",
        "priceEntered": {
          "priceUomCode": "PCE",
          "value": 10
        },
        "productIdentifier": "ext-SAP-product_reference",
        "qtyToInvoice": 10,
        "uomCode": "KGM",
        "acctCode": "69100"
      }
    ]
  }
}
"""

    And process invoice create response
      | C_Invoice_ID.Identifier |
      | invoice_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.C_Currency.ISO_Code | OPT.DateInvoiced | OPT.DateAcct | OPT.DateOrdered | OPT.C_DocType_ID.Identifier | OPT.C_DocTypeTarget_ID.Identifier | OPT.IsSOTrx |
      | invoice_1               | endCustomer_1            | endCustomerLocation_1             | poReference     | 30 Tage netto | true      | CO        | EUR                     | 2022-01-20       | 2022-01-19   | 2022-01-18      | docType                     | docType                           | true        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Tax_ID.Identifier | OPT.Line | OPT.TaxAmtInfo | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | OPT.IsManualPrice | OPT.QtyInvoicedInPriceUOM |
      | invoiceLine_1               | invoice_1               | product_1               | 10          | true      | 10               | 10              | 400            | tax_1                   | 10       | 28             | KGM                   | PCE                       | true              | 40                        |

    And C_Invoice_Acct is found:
      | C_Invoice_Acct_ID.Identifier | OPT.C_AcctSchema_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.C_ElementValue_ID.Identifier |
      | invoiceAcct_1                | acctSchema_1                   | invoice_1                   | invoiceLine_1                   | elementValue_1                   |

  @from:cucumber
  @Id:S0248_200
  Scenario: create invoice with 1 x line with acct, priceEntered and taxCode info
  - action.completeIt = 'Y'

    Given load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | endCustomerLocation_1             | 2205175                    |

    And load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | product_1               | 2005577          |

    And load C_Tax:
      | C_Tax_ID.Identifier | OPT.C_Tax_ID |
      | tax_1               | 1000022      |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | docType                 | ARI             | true          |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type             | ExternalReference          | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier |
      | billBPartner_ref                  | SAP            | BPartner         | bpartner_reference         | endCustomer_1                |                                       |                             |
      | billBPartnerLocation_ref          | SAP            | BPartnerLocation | bpartnerLocation_reference |                              | endCustomerLocation_1                 |                             |
      | product_ref                       | SAP            | Product          | product_reference          |                              |                                       | product_1                   |

    And metasfresh contains C_Vat_Code:
      | C_VAT_Code_ID.Identifier | C_Tax_ID.Identifier | C_AcctSchema_ID.Identifier | VATCode | ValidFrom  |
      | vat_code_1               | tax_1               | acctSchema_1               | testTax | 2021-12-01 |


    And load C_TaxCategory:
      | C_TaxCategory_ID.Identifier | OPT.C_TaxCategory_ID |
      | taxCategory_1               | 1000009              |

    And update C_TaxCategory:
      | C_TaxCategory_ID.Identifier | OPT.IsManualTax |
      | taxCategory_1               | true            |

    And load C_Element:
      | C_Element_ID.Identifier | OPT.C_Element_ID |
      | element_1               | 1000000          |

    And load C_ElementValue:
      | C_ElementValue_ID.Identifier | C_Element_ID.Identifier | Value |
      | elementValue_1               | element_1               | 69100 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '200' status code
    """
{
  "invoice": {
    "action": {
      "completeIt": true
    },
    "header": {
      "orgCode": "001",
      "acctSchemaCode": "metas fresh UN/34 CHF",
      "billPartnerIdentifier": "ext-SAP-bpartner_reference",
      "billLocationIdentifier": "ext-SAP-bpartnerLocation_reference",
      "dateInvoiced": "2022-01-20",
      "dateAcct": "2022-01-19",
      "dateOrdered": "2022-01-18",
      "externalHeaderId": "externalHeaderId_15022023_2",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference",
      "soTrx": "SALES",
      "currencyCode": "EUR",
      "grandTotal": 476,
      "taxTotal": 76
    },
    "lines": [
      {
        "externalLineId": "externalLineId",
        "line": 10,
        "lineDescription": "lineDescription",
        "priceEntered": {
          "priceUomCode": "PCE",
          "value": 10
        },
        "productIdentifier": "ext-SAP-product_reference",
        "qtyToInvoice": 10,
        "uomCode": "KGM",
        "acctCode": "69100",
        "accountName": "P_Revenue_Acct",
        "taxCode": "testTax"
      }
    ]
  }
}
"""

    And process invoice create response
      | C_Invoice_ID.Identifier |
      | invoice_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.C_Currency.ISO_Code | OPT.DateInvoiced | OPT.DateAcct | OPT.DateOrdered | OPT.C_DocType_ID.Identifier | OPT.C_DocTypeTarget_ID.Identifier | OPT.IsSOTrx |
      | invoice_1               | endCustomer_1            | endCustomerLocation_1             | poReference     | 30 Tage netto | true      | CO        | EUR                     | 2022-01-20       | 2022-01-19   | 2022-01-18      | docType                     | docType                           | true        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Tax_ID.Identifier | OPT.Line | OPT.TaxAmtInfo | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | OPT.IsManualPrice | OPT.QtyInvoicedInPriceUOM |
      | invoiceLine_1               | invoice_1               | product_1               | 10          | true      | 10               | 10              | 400            | tax_1                   | 10       | 76             | KGM                   | PCE                       | true              | 40                        |

    And C_Invoice_Acct is found:
      | C_Invoice_Acct_ID.Identifier | OPT.C_AcctSchema_ID.Identifier | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.AccountName | OPT.C_ElementValue_ID.Identifier |
      | invoiceAcct_1                | acctSchema_1                   | invoice_1                   | invoiceLine_1                   | P_Revenue_Acct  | elementValue_1                   |

    And update C_TaxCategory:
      | C_TaxCategory_ID.Identifier | OPT.IsManualTax |
      | taxCategory_1               | false           |

  @from:cucumber
  @Id:S0248_300
  Scenario: create invoice with 1 x line without acct and priceEntered info
  - action.completeIt = 'Y'

    Given load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | endCustomerLocation_1             | 2205175                    |

    And load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | product_1               | 2005577          |

    And load C_Tax:
      | C_Tax_ID.Identifier | OPT.C_Tax_ID |
      | tax_1               | 1000023      |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | docType                 | ARI             | true          |

    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type             | ExternalReference          | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier |
      | billBPartner_ref                  | SAP            | BPartner         | bpartner_reference         | endCustomer_1                |                                       |                             |
      | billBPartnerLocation_ref          | SAP            | BPartnerLocation | bpartnerLocation_reference |                              | endCustomerLocation_1                 |                             |
      | product_ref                       | SAP            | Product          | product_reference          |                              |                                       | product_1                   |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '200' status code
    """
{
  "invoice": {
    "action": {
      "completeIt": true
    },
    "header": {
      "orgCode": "001",
      "billPartnerIdentifier": "ext-SAP-bpartner_reference",
      "billLocationIdentifier": "ext-SAP-bpartnerLocation_reference",
      "dateInvoiced": "2022-01-25",
      "dateAcct": "2022-01-24",
      "dateOrdered": "2022-01-23",
      "externalHeaderId": "externalHeaderId_15022023_3",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference",
      "soTrx": "SALES",
      "currencyCode": "EUR",
      "grandTotal": 21.4,
      "taxTotal": 1.4
    },
    "lines": [
      {
        "externalLineId": "externalLineId",
        "lineDescription": "lineDescription",
        "productIdentifier": "ext-SAP-product_reference",
        "qtyToInvoice": 10,
        "uomCode": "PCE"
      }
    ]
  }
}
"""

    And process invoice create response
      | C_Invoice_ID.Identifier |
      | invoice_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.C_Currency.ISO_Code | OPT.DateInvoiced | OPT.DateAcct | OPT.DateOrdered | OPT.C_DocType_ID.Identifier | OPT.C_DocTypeTarget_ID.Identifier | OPT.IsSOTrx |
      | invoice_1               | endCustomer_1            | endCustomerLocation_1             | poReference     | 30 Tage netto | true      | CO        | EUR                     | 2022-01-25       | 2022-01-24   | 2022-01-23      | docType                     | docType                           | true        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Tax_ID.Identifier | OPT.Line | OPT.TaxAmtInfo | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | OPT.IsManualPrice | OPT.QtyInvoicedInPriceUOM |
      | invoiceLine_1               | invoice_1               | product_1               | 10          | true      | 2                | 2               | 20             | tax_1                   | 10       | 1.4            | PCE                   | PCE                       | false             | 10                        |

  @from:cucumber
  @Id:S0248_400
  Scenario: create invoice with 1 x line with no productPrice for given product and neither taxCode nor manual price provided => product not found on priceList error

    Given load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | endCustomerLocation_1             | 2205175                    |

    And metasfresh contains M_Products:
      | Identifier | Name               |
      | p_1        | product_24012023_1 |

    And load C_Tax:
      | C_Tax_ID.Identifier | OPT.C_Tax_ID |
      | tax_1               | 1000022      |

    And update C_Tax:
      | C_Tax_ID.Identifier | OPT.SeqNo |
      | tax_1               | 10        |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | docType                 | ARI             | true          |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type             | ExternalReference          | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier |
      | billBPartner_ref                  | SAP            | BPartner         | bpartner_reference         | endCustomer_1                |                                       |                             |
      | billBPartnerLocation_ref          | SAP            | BPartnerLocation | bpartnerLocation_reference |                              | endCustomerLocation_1                 |                             |
      | product_ref                       | SAP            | Product          | product_reference2         |                              |                                       | p_1                         |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '422' status code
    """
{
  "invoice": {
    "action": {
      "completeIt": true
    },
    "header": {
      "orgCode": "001",
      "billPartnerIdentifier": "ext-SAP-bpartner_reference",
      "billLocationIdentifier": "ext-SAP-bpartnerLocation_reference",
      "dateInvoiced": "2022-01-25",
      "dateAcct": "2022-01-24",
      "dateOrdered": "2022-01-23",
      "externalHeaderId": "externalHeaderId_15022023_4",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference",
      "soTrx": "SALES",
      "currencyCode": "EUR",
      "grandTotal": 21.4,
      "taxTotal": 1.4
    },
    "lines": [
      {
        "externalLineId": "externalLineId",
        "lineDescription": "lineDescription",
        "productIdentifier": "ext-SAP-product_reference2",
        "qtyToInvoice": 10,
        "uomCode": "PCE"
      }
    ]
  }
}
"""

    And validate invoice-api response error message
      | JsonErrorItem.message       |
      | Product is not on PriceList |


  @from:cucumber
  @Id:S0248_500
  Scenario: create invoice with 1 x line with no productPrice for the given product, but priceEntered and taxCode info are provided
  - action.completeIt = 'Y'

    Given load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | endCustomerLocation_1             | 2205175                    |

    And metasfresh contains M_Products:
      | Identifier | Name            |
      | product_2  | product_1502023 |

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Name           | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax        | Normal                        | TaxName_150223 | 2023-02-01 | 5    | DE                       | DE                        |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | docType                 | ARI             | true          |

    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type             | ExternalReference      | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Product_ID.Identifier |
      | billBPartnerExtRef                | SAP            | BPartner         | bpartnerExtReference   | endCustomer_1                |                                       |                             |
      | billBPartnerLocationExtRef        | SAP            | BPartnerLocation | bpartnerLocationExtRef |                              | endCustomerLocation_1                 |                             |
      | productExtRef                     | SAP            | Product          | productExtRef          |                              |                                       | product_2                   |

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And metasfresh contains C_Vat_Code:
      | C_VAT_Code_ID.Identifier | C_Tax_ID.Identifier | C_AcctSchema_ID.Identifier | VATCode  | ValidFrom  |
      | vatCode                  | tax                 | acctSchema_1               | testTax2 | 2023-02-01 |

    And load C_TaxCategory:
      | C_TaxCategory_ID.Identifier | OPT.C_TaxCategory_ID |
      | taxCategory_1               | 1000009              |

    And update C_TaxCategory:
      | C_TaxCategory_ID.Identifier | OPT.IsManualTax |
      | taxCategory_1               | true            |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '200' status code
    """
{
  "invoice": {
    "action": {
      "completeIt": true
    },
    "header": {
      "orgCode": "001",
      "billPartnerIdentifier": "ext-SAP-bpartnerExtReference",
      "billLocationIdentifier": "ext-SAP-bpartnerLocationExtRef",
      "dateInvoiced": "2022-01-20",
      "dateAcct": "2022-01-19",
      "dateOrdered": "2022-01-18",
      "externalHeaderId": "externalHeaderId_15022023_5",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference",
      "soTrx": "SALES",
      "currencyCode": "EUR",
      "grandTotal": 105,
      "taxTotal": 5
    },
    "lines": [
      {
        "externalLineId": "externalLineId",
        "line": 10,
        "lineDescription": "lineDescription",
        "priceEntered": {
          "priceUomCode": "PCE",
          "value": 10
        },
        "productIdentifier": "ext-SAP-productExtRef",
        "qtyToInvoice": 10,
        "uomCode": "PCE",
        "taxCode": "testTax2"
      }
    ]
  }
}
"""

    And process invoice create response
      | C_Invoice_ID.Identifier |
      | invoice_2               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.C_Currency.ISO_Code | OPT.DateInvoiced | OPT.DateAcct | OPT.DateOrdered | OPT.C_DocType_ID.Identifier | OPT.C_DocTypeTarget_ID.Identifier | OPT.IsSOTrx |
      | invoice_2               | endCustomer_1            | endCustomerLocation_1             | poReference     | 30 Tage netto | true      | CO        | EUR                     | 2022-01-20       | 2022-01-19   | 2022-01-18      | docType                     | docType                           | true        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.C_Tax_ID.Identifier | OPT.Line | OPT.TaxAmtInfo | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | OPT.IsManualPrice | OPT.QtyInvoicedInPriceUOM |
      | invoiceLine_2               | invoice_2               | product_2               | 10          | true      | 10               | 10              | 100            | tax                     | 10       | 5              | PCE                   | PCE                       | true              | 10                        |