@from:cucumber
@Topic:InvoiceReview
Feature: external references for metasfresh resources
  As a REST-API invoker
  I want to push invoice review status changes to metasfresh
  So that the invoices can be invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-04-05T12:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load C_BPartner:
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

  @from:cucumber
  @Id:S14758_100
  Scenario: Insert review by C_Invoice_ID and update it via ExternalId
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '200' status code
"""
{
  "invoice": {
    "header": {
      "orgCode": "001",
      "acctSchemaCode": "metas fresh UN/34 CHF",
      "billPartnerIdentifier": "2156425",
      "billLocationIdentifier": "2205175",
      "dateInvoiced": "2023-04-05",
      "dateAcct": "2023-04-04",
      "dateOrdered": "2023-04-03",
      "externalHeaderId": "externalHeaderId_2023-04-05_1",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference1",
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
        "productIdentifier": "2005577",
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
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.C_Currency.ISO_Code | OPT.DateInvoiced | OPT.DateAcct | OPT.DateOrdered | OPT.C_DocType_ID.Identifier | OPT.C_DocTypeTarget_ID.Identifier | OPT.IsSOTrx | OPT.ExternalId                |
      | invoice_1               | endCustomer_1            | endCustomerLocation_1             | poReference1    | 30 Tage netto | false     | DR        | EUR                     | 2023-04-05       | 2023-04-04   | 2023-04-03      | docType                     | docType                           | true        | externalHeaderId_2023-04-05_1 |

    And the user creates a JsonInvoiceReviewUpsertItem and stores it in the context
      | OPT.C_Invoice_ID.Identifier | orgCode | customColumn |
      | invoice_1                   | 001     | 100          |
    And the metasfresh REST-API endpoint path 'api/v2/invoices/docReview' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process invoice review create response
      | C_Invoice_Review_ID.Identifier |
      | invoice_Review_1               |

    And validate invoice review
      | C_Invoice_Review_ID.Identifier | C_Invoice_ID.Identifier | OPT.CustomColumn |
      | invoice_Review_1               | invoice_1               | 100              |

    And the user creates a JsonInvoiceReviewUpsertItem and stores it in the context
      | OPT.ExternalId                | orgCode | customColumn |
      | externalHeaderId_2023-04-05_1 | 001     | 300          |
    And the metasfresh REST-API endpoint path 'api/v2/invoices/docReview' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process invoice review create response
      | C_Invoice_Review_ID.Identifier |
      | invoice_Review_3               |

    And validate invoice review
      | C_Invoice_Review_ID.Identifier | C_Invoice_ID.Identifier | OPT.CustomColumn |
      | invoice_Review_3               | invoice_1               | 300              |

  @from:cucumber
  @Id:S14758_200
  Scenario: Insert review by ExternalId and update it by C_Invoice_ID
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/new' and fulfills with '200' status code
"""
{
  "invoice": {
    "header": {
      "orgCode": "001",
      "acctSchemaCode": "metas fresh UN/34 CHF",
      "billPartnerIdentifier": "2156425",
      "billLocationIdentifier": "2205175",
      "dateInvoiced": "2023-04-05",
      "dateAcct": "2023-04-04",
      "dateOrdered": "2023-04-03",
      "externalHeaderId": "externalHeaderId_2023-04-05_2",
      "invoiceDocType": {
        "docBaseType": "ARI",
        "docSubType": null
      },
      "poReference": "poReference1",
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
        "productIdentifier": "2005577",
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
      | invoice_2               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus | OPT.C_Currency.ISO_Code | OPT.DateInvoiced | OPT.DateAcct | OPT.DateOrdered | OPT.C_DocType_ID.Identifier | OPT.C_DocTypeTarget_ID.Identifier | OPT.IsSOTrx | OPT.ExternalId                |
      | invoice_2               | endCustomer_1            | endCustomerLocation_1             | poReference1    | 30 Tage netto | false     | DR        | EUR                     | 2023-04-05       | 2023-04-04   | 2023-04-03      | docType                     | docType                           | true        | externalHeaderId_2023-04-05_2 |

    And the user creates a JsonInvoiceReviewUpsertItem and stores it in the context
      | OPT.ExternalId                | orgCode | customColumn |
      | externalHeaderId_2023-04-05_2 | 001     | 200          |
    And the metasfresh REST-API endpoint path 'api/v2/invoices/docReview' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process invoice review create response
      | C_Invoice_Review_ID.Identifier |
      | invoice_Review_2               |

    And validate invoice review
      | C_Invoice_Review_ID.Identifier | C_Invoice_ID.Identifier | OPT.CustomColumn |
      | invoice_Review_2               | invoice_2               | 200              |

    When the user creates a JsonInvoiceReviewUpsertItem and stores it in the context
      | OPT.C_Invoice_ID.Identifier | orgCode | customColumn |
      | invoice_2                   | 001     | 400          |
    And the metasfresh REST-API endpoint path 'api/v2/invoices/docReview' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process invoice review create response
      | C_Invoice_Review_ID.Identifier |
      | invoice_Review_4               |

    And validate invoice review
      | C_Invoice_Review_ID.Identifier | C_Invoice_ID.Identifier | OPT.CustomColumn |
      | invoice_Review_4               | invoice_2               | 400              |

  @from:cucumber
  @Id:S14758_300
  Scenario: Insert review using invalid ExternalId
    When the user creates a JsonInvoiceReviewUpsertItem and stores it in the context
      | OPT.ExternalId                | orgCode | customColumn |
      | externalHeaderId_2023-04-05_3 | 001     | 500          |
    And the metasfresh REST-API endpoint path 'api/v2/invoices/docReview' receives a 'POST' request with the payload from context and responds with '400' status code

