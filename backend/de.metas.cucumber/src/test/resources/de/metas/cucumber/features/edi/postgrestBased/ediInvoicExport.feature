@from:cucumber
@ghActions:run_on_executor3
Feature: EDI export via postgREST

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the API audit data is reset
    And metasfresh has date and time 2025-05-01T16:30:17+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

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
      | customer1  | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |

  @Id:S0467_010
  @from:cucumber
  Scenario: create an invoice and exports it to JSON
    Given metasfresh contains M_Products:
      | Identifier        | Value                       | Name                       | Description                       |
      | product_S0467_010 | postgRESTExportProductValue | postgRESTExportProductName | postgRESTExportProductDescription |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0467_010 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0467_010 | salesInvoiceS0467_010_ID | customer1     | Ausgangsrechnung        | S0467_010  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0467_010 | product_S0467_010 | 1 PCE       |
    And the invoice identified by salesInvoiceS0467_010 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/C_Invoice_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": "@salesInvoiceS0467_010_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[ {
  "metasfresh_INVOIC" : [ {
    "Invoice_ID" : @salesInvoiceS0467_010_ID@,
    "Invoice_Receiver_Tec_GLN" : null,
    "Invoice_Sender_Tec_GLN" : null,
    "Invoice_Sender_CountryCode" : "DE",
    "Invoice_Sender_VATaxId" : null,
    "Invoice_DocumentNo" : "S0467_010",
    "Invoice_Date" : "2025-05-01T00:00:00",
    "Invoice_Acct_Date" : "2025-05-01T00:00:00",
    "DocType_Base" : "ARI",
    "DocType_Sub" : null,
    "CreditMemo_Reason" : null,
    "CreditMemo_ReasonText" : null,
    "Order_POReference" : null,
    "Order_Date" : null,
    "Shipment_Date" : null,
    "Shipment_DocumentNo" : null,
    "DESADV_DocumentNo" : null,
    "Invoice_Currency_Code" : "EUR",
    "Invoice_GrandTotal" : 5.95,
    "Invoice_TotalLines" : 5.0,
    "Invoice_TotalVAT" : 0.95,
    "Invoice_TotalVATBaseAmt" : 5.0,
    "Invoice_SurchargeAmt" : 0.0,
    "Invoice_TotalLinesWithSurchargeAmt" : 5.0,
    "Invoice_TotalVATWithSurchargeAmt" : 0.95,
    "Invoice_GrandTotalWithSurchargeAmt" : 5.95,
    "Partners" : [ {
      "EANCOM_LocationType" : "SU",
      "GLN" : null,
      "Name" : "metasfresh AG",
      "Name2" : null,
      "PartnerNo" : "metasfresh",
      "VATaxID" : null,
      "ReferenceNo" : null,
      "SiteName" : null,
      "Setup_Place_No" : null,
      "Address1" : "Am Nossbacher Weg 2",
      "Address2" : null,
      "Postal" : "53179",
      "City" : "Bonn",
      "CountryCode" : "DE",
      "Phone" : null,
      "Fax" : null,
      "CustomEdiAttributes" : null
    } ],
    "PaymentTerms" : [ {
      "Net_Days" : 30
    } ],
    "PaymentDiscounts" : [ {
      "Discount_Name" : "30 Tage netto",
      "Tax_Percent" : 19.0,
      "Discount_Days" : 0,
      "Discount_Percent" : 0,
      "Discount_BaseAmt" : 5.0,
      "Discount_Amt" : 0.0
    } ],
    "Lines" : [ {
      "Invoice_Line" : 10,
      "Invoice_QtyInvoiced" : 1,
      "Invoice_QtyInvoiced_UOM" : "PCE",
      "ORDERS_Line" : null,
      "ORDERS_QtyInvoiced" : null,
      "ORDERS_QtyInvoiced_UOM" : null,
      "Order_POReference" : null,
      "Order_Line" : 10,
      "Order_QtyInvoiced" : 1,
      "Order_QtyInvoiced_UOM" : "PCE",
      "Currency_Code" : "EUR",
      "PricePerUnit" : 5.0,
      "PriceUOM" : "PCE",
      "Discount_Amt" : 0,
      "QtyBasedOn" : null,
      "NetAmt" : 5.0,
      "Tax_Percent" : 19.0,
      "Tax_Amount" : 0.95,
      "Product_Name" : "postgRESTExportProductName",
      "Product_Description" : "postgRESTExportProductDescription",
      "Product_Buyer_CU_GTIN" : null,
      "Product_Buyer_TU_GTIN" : null,
      "Product_Buyer_ProductNo" : null,
      "Product_Supplier_TU_GTIN" : null,
      "Product_Supplier_ProductNo" : "postgRESTExportProductValue"
    } ],
    "Sums" : [ {
      "TotalAmt" : 5.95,
      "Tax_Amt" : 0.95,
      "Tax_BaseAmt" : 5.0,
      "Tax_Percent" : 19.0,
      "Tax_Exempt" : false,
      "SurchargeAmt" : 0.0,
      "Tax_BaseAmtWithSurchargeAmt" : 5.0,
      "Tax_AmtWithSurchargeAmt" : 0.95
    } ]
  } ]
} ]
    """