@from:cucumber
@ghActions:run_on_executor2
Feature: Invoice to comply with RKSV export via postgREST

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-05-01T16:30:17+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | OPT.C_PaymentTerm_ID | PaymentRule |
      | vendor1    | N          | Y        | pricingSystem      | 1000009              | B           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault | City | Postal |
      | bpartner_location_1 | vendor1       | Y               | Y               | Bonn | 53175  |

  @from:cucumber
  Scenario: create an invoice and export it
    Given metasfresh contains M_Products:
      | Identifier           | REST.Context.Value                       | REST.Context.Name                       | Description                                    |
      | product_10022025_010 | postgRESTExportProductValue_10022025_010 | postgRESTExportProductName_10022025_010 | postgRESTExportProductDescription_10022025_010 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID |
      | salesPLV               | product_10022025_010 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier              | REST.Context               | C_BPartner_ID | PaymentRule | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInvoice10022025 | purchaseInvoice10022025_ID | vendor1       | B           | Eingangsrechnung        | 10022025   | 2025-05-01   | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID         | QtyInvoiced |
      | purchaseInvoice10022025 | product_10022025_010 | 1 PCE       |
    And the invoice identified by purchaseInvoice10022025 is completed
    And metasfresh contains C_Invoice:
      | Identifier                | C_BPartner_ID | C_DocTypeTarget_ID.Name | PaymentRule | DocumentNo | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | purchaseInvoice10022025_2 | vendor1       | Eingangsrechnung        | B           | 10022025_2 | 2025-05-01   | false   | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID              | M_Product_ID         | QtyInvoiced |
      | purchaseInvoice10022025_2 | product_10022025_010 | 1 PCE       |
    And the invoice identified by purchaseInvoice10022025_2 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Invoices_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": @purchaseInvoice10022025_ID@
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "invoice": "10022025",
  "amount": 5.95,
  "customer": "Test Lieferant 1, 53175 Bonn",
  "payment": "B"
}
    """