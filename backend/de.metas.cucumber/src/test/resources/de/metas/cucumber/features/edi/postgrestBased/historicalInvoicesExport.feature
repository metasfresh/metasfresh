@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor3
Feature: Invoice export via postgREST
## F00350: EDI

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
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |
    And metasfresh contains C_BPartners without locations:
      | Identifier | REST.Context.Value | REST.Context.Name | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | customer_value     | customer_name     | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |

  @Id:S0474_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via UpdatedGE and InputDataSource
    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 |
      | product_S0474_010 | postgRESTExportProductValue_S0474_010 | postgRESTExportProductName_S0474_010 | postgRESTExportProductDescription_S0474_010 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_010 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0474_010 | test_S0474_010 |
    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0474_010 | test_S0474_010 |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | AD_InputDataSource_ID | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_010 | salesInvoiceS0474_010_ID | dataSource_S0474_010  | test_S0474_010       | customer1     | Ausgangsrechnung        | S0474_010  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_010 | product_S0474_010 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_010 is completed
    And metasfresh contains C_Invoice:
      | Identifier              | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo  | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_010_2 | customer1     | Ausgangsrechnung        | S0474_010_2 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_010_2 | product_S0474_010 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_010_2 is completed

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
      "name": "UpdatedGE",
      "value": "2025-01-01 00:00:00"
    },
    {
      "name": "ExternalSystemCode",
      "value": "test_S0474_010"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_010_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "S0474_010",
    "Invoice_Date": "2025-05-01T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": null,
    "Order_Date": null,
    "Shipment_Date": null,
    "Shipment_DocumentNo": null,
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": null,
    "ExternalSystemCode": "test_S0474_010",
    "DataSource": "int-test_S0474_010",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": null,
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": null,
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_010@",
        "Product_Description": "postgRESTExportProductDescription_S0474_010",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_010@",
        "ExternalId": null
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """

  @Id:S0474_020
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via externalId
    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 |
      | product_S0474_020 | postgRESTExportProductValue_S0474_020 | postgRESTExportProductName_S0474_020 | postgRESTExportProductDescription_S0474_020 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_020 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | ExternalId           | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_020 | salesInvoiceS0474_020_ID | externalId_S0474_020 | Shopware6            | customer1     | Ausgangsrechnung        | S0474_020  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_020 | product_S0474_020 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_020 is completed
    And metasfresh contains C_Invoice:
      | Identifier              | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo  | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_020_2 | customer1     | Ausgangsrechnung        | S0474_020_2 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_020_2 | product_S0474_020 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_020_2 is completed

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
      "name": "ExternalId",
      "value": "externalId_S0474_020"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_020_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "S0474_020",
    "Invoice_Date": "2025-05-01T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": null,
    "Order_Date": null,
    "Shipment_Date": null,
    "Shipment_DocumentNo": null,
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": "externalId_S0474_020",
    "ExternalSystemCode": "Shopware6",
    "DataSource": "",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": null,
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": null,
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_020@",
        "Product_Description": "postgRESTExportProductDescription_S0474_020",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_020@",
        "ExternalId": null
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """

  @Id:S0474_030
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via BPartnerValue
    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 |
      | product_S0474_030 | postgRESTExportProductValue_S0474_030 | postgRESTExportProductName_S0474_030 | postgRESTExportProductDescription_S0474_030 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_030 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | ExternalId           | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_030 | salesInvoiceS0474_030_ID | externalId_S0474_030 | Shopware6            | customer1     | Ausgangsrechnung        | S0474_030  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_030 | product_S0474_030 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_030 is completed
    And metasfresh contains C_Invoice:
      | Identifier              | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo  | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_030_2 | customer1     | Ausgangsrechnung        | S0474_030_2 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_030_2 | product_S0474_030 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_030_2 is completed

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
      "name": "BPartnerValue",
      "value": "@customer_value@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_030_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "S0474_030",
    "Invoice_Date": "2025-05-01T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": null,
    "Order_Date": null,
    "Shipment_Date": null,
    "Shipment_DocumentNo": null,
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": "externalId_S0474_030",
    "ExternalSystemCode": "Shopware6",
    "DataSource": "",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": null,
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": null,
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_030@",
        "Product_Description": "postgRESTExportProductDescription_S0474_030",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_030@",
        "ExternalId": null
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """

  @Id:S0474_040
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via DateInvoicedGE and limit it to 1
    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 |
      | product_S0474_040 | postgRESTExportProductValue_S0474_040 | postgRESTExportProductName_S0474_040 | postgRESTExportProductDescription_S0474_040 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_040 | 5.00     | PCE      |
    And metasfresh contains External System
      | Name           | Value          |
      | test_S0474_040 | test_S0474_040 |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | ExternalId           | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_040 | salesInvoiceS0474_040_ID | externalId_S0474_040 | test_S0474_040       | customer1     | Ausgangsrechnung        | S0474_040  | 2025-05-04   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_040 | product_S0474_040 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_040 is completed
    And metasfresh contains C_Invoice:
      | Identifier              | REST.Context               | ExternalId             | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo  | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_040_2 | salesInvoiceS0474_040_2_ID | externalId_S0474_040_2 | test_S0474_040       | customer1     | Ausgangsrechnung        | S0474_040_2 | 2025-05-04   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_040_2 | product_S0474_040 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_040_2 is completed

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
      "name": "DateInvoicedGE",
      "value": "2025-05-04 00:00:00"
    },
    {
      "name": "Limit",
      "value": "1"
    },
    {
      "name": "ExternalSystemCode",
      "value": "test_S0474_040"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_040_2_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "S0474_040_2",
    "Invoice_Date": "2025-05-04T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": null,
    "Order_Date": null,
    "Shipment_Date": null,
    "Shipment_DocumentNo": null,
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": "externalId_S0474_040_2",
    "ExternalSystemCode": "test_S0474_040",
    "DataSource": "",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": null,
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": null,
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_040@",
        "Product_Description": "postgRESTExportProductDescription_S0474_040",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_040@",
        "ExternalId": null
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """

  @Id:S0474_050
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via BPartnerExternalSystemValue and BPartnerExternalReference
    Given metasfresh contains External System
      | Name           | Value          |
      | test_S0474_050 | test_S0474_050 |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference | C_BPartner_ID |
      | bpartner_ref                      | test_S0474_050 | BPartner | S0474_050         | customer1     |
    And metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 |
      | product_S0474_050 | postgRESTExportProductValue_S0474_050 | postgRESTExportProductName_S0474_050 | postgRESTExportProductDescription_S0474_050 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_050 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | ExternalId           | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_050 | salesInvoiceS0474_050_ID | externalId_S0474_050 | Shopware6            | customer1     | Ausgangsrechnung        | S0474_050  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0474_050 | product_S0474_050 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_050 is completed

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
      "name": "BPartnerExternalReference",
      "value": "S0474_050"
    },
    {
      "name": "BPartnerExternalSystemValue",
      "value": "test_S0474_050"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_050_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "S0474_050",
    "Invoice_Date": "2025-05-01T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": null,
    "Order_Date": null,
    "Shipment_Date": null,
    "Shipment_DocumentNo": null,
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": "externalId_S0474_050",
    "ExternalSystemCode": "Shopware6",
    "DataSource": "",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": null,
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": null,
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_050@",
        "Product_Description": "postgRESTExportProductDescription_S0474_050",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_050@",
        "ExternalId": null
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """

  @Id:S0474_060
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via Order_ID
    And metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 |
      | product_S0474_060 | postgRESTExportProductValue_S0474_060 | postgRESTExportProductName_S0474_060 | postgRESTExportProductDescription_S0474_060 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_060 | 5.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference       | ExternalSystem.Value | ExternalId           |
      | o_1        | order_S0474_060_ID | true    | customer1                | 2021-04-17  | po_ref_mock_S0474_060 | Shopware6            | externalId_S0474_060 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | product_S0474_060       | 1          |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.DocumentNo |
      | s_s_1                            | s_1                   | shipmentDocNo_S0474_060 |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 1            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context             | REST.Context.DocumentNo          |
      | ic_1                              | invoice_1               | salesInvoiceS0474_060_ID | salesInvoiceS0474_060_DocumentNo |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference       | processed | DocStatus |
      | invoice_1               | customer1                | bpartner_location_1               | po_ref_mock_S0474_060 | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | product_S0474_060       | 1           | true      |

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
      "name": "Order_ID",
      "value": "@order_S0474_060_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_060_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "@salesInvoiceS0474_060_DocumentNo@",
    "Invoice_Date": "2025-05-01T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": "po_ref_mock_S0474_060",
    "Order_Date": "2021-04-17T00:00:00",
    "Shipment_Date": "2025-05-01T00:00:00",
    "Shipment_DocumentNo": "@shipmentDocNo_S0474_060@",
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": "externalId_S0474_060",
    "ExternalSystemCode": "Shopware6",
    "DataSource": "",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": "po_ref_mock_S0474_060",
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": "Nominal",
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_060@",
        "Product_Description": "postgRESTExportProductDescription_S0474_060",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_060@",
        "ExternalId": ""
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """

  @Id:S0474_070
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON via DocType_Base
    Given metasfresh contains External System
      | Name             | Value            |
      | test_S0474_070_2 | test_S0474_070_2 |
    And metasfresh contains M_Products:
      | Identifier          | REST.Context.Value                      | REST.Context.Name                      | Description                                   |
      | product_S0474_070_2 | postgRESTExportProductValue_S0474_070_2 | postgRESTExportProductName_S0474_070_2 | postgRESTExportProductDescription_S0474_070_2 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID        | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0474_070_2 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier              | REST.Context               | ExternalId             | ExternalSystem.Value | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo  | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0474_070_2 | salesInvoiceS0474_070_2_ID | externalId_S0474_070_2 | test_S0474_070_2     | customer1     | Ausgangsrechnung        | S0474_070_2 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID        | QtyInvoiced |
      | salesInvoiceS0474_070_2 | product_S0474_070_2 | 1 PCE       |
    And the invoice identified by salesInvoiceS0474_070_2 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_2        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Invoices_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "DocType_Base",
      "value": "ARI"
    },
    {
      "name": "ExternalSystemCode",
      "value": "test_S0474_070_2"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Invoice_ID": @salesInvoiceS0474_070_2_ID@,
    "Invoice_Receiver_Tec_GLN": null,
    "Invoice_Sender_Tec_GLN": null,
    "Invoice_Sender_CountryCode": "DE",
    "Invoice_Sender_VATaxId": null,
    "Invoice_DocumentNo": "S0474_070_2",
    "Invoice_Date": "2025-05-01T00:00:00",
    "Invoice_Acct_Date": "2025-05-01T00:00:00",
    "DocType_Base": "ARI",
    "DocType_Sub": null,
    "CreditMemo_Reason": null,
    "CreditMemo_ReasonText": null,
    "Order_POReference": null,
    "Order_Date": null,
    "Shipment_Date": null,
    "Shipment_DocumentNo": null,
    "DESADV_DocumentNo": null,
    "Invoice_Currency_Code": "EUR",
    "Invoice_GrandTotal": 5.95,
    "Invoice_TotalLines": 5.0,
    "Invoice_TotalVAT": 0.95,
    "Invoice_TotalVATBaseAmt": 5.0,
    "Invoice_SurchargeAmt": 0.0,
    "Invoice_TotalLinesWithSurchargeAmt": 5.0,
    "Invoice_TotalVATWithSurchargeAmt": 0.95,
    "Invoice_GrandTotalWithSurchargeAmt": 5.95,
    "ExternalId": "externalId_S0474_070_2",
    "ExternalSystemCode": "test_S0474_070_2",
    "DataSource": "",
    "DocStatus": "CO",
    "Partners": [
      {
        "EANCOM_LocationType": "SU",
        "GLN": null,
        "Name": "metasfresh AG",
        "Name2": null,
        "PartnerNo": "metasfresh",
        "VATaxID": null,
        "ReferenceNo": null,
        "SiteName": null,
        "Setup_Place_No": null,
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "Postal": "53179",
        "City": "Bonn",
        "CountryCode": "DE",
        "Phone": null,
        "Fax": null,
        "CustomEdiAttributes": null
      }
    ],
    "PaymentTerms": [
      {
        "Net_Days": 30
      }
    ],
    "PaymentDiscounts": [
      {
        "Discount_Name": "30 Tage netto",
        "Tax_Percent": 19.0,
        "Discount_Days": 0,
        "Discount_Percent": 0,
        "Discount_BaseAmt": 5.0,
        "Discount_Amt": 0.0
      }
    ],
    "Lines": [
      {
        "Invoice_Line": 10,
        "Invoice_QtyInvoiced": 1,
        "Invoice_QtyInvoiced_UOM": "PCE",
        "ORDERS_Line": null,
        "ORDERS_QtyInvoiced": null,
        "ORDERS_QtyInvoiced_UOM": null,
        "Order_POReference": null,
        "Order_Line": 10,
        "Order_QtyInvoiced": 1,
        "Order_QtyInvoiced_UOM": "PCE",
        "Currency_Code": "EUR",
        "PricePerUnit": 5.0,
        "PriceUOM": "PCE",
        "Discount_Amt": 0,
        "QtyBasedOn": null,
        "NetAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Amount": 0.95,
        "Product_Name": "@postgRESTExportProductName_S0474_070_2@",
        "Product_Description": "postgRESTExportProductDescription_S0474_070_2",
        "Product_Buyer_CU_GTIN": null,
        "Product_Buyer_TU_GTIN": null,
        "Product_Buyer_ProductNo": null,
        "Product_Supplier_TU_GTIN": null,
        "Product_Supplier_ProductNo": "@postgRESTExportProductValue_S0474_070_2@",
        "ExternalId": null
      }
    ],
    "Sums": [
      {
        "TotalAmt": 5.95,
        "Tax_Amt": 0.95,
        "Tax_BaseAmt": 5.0,
        "Tax_Percent": 19.0,
        "Tax_Exempt": false,
        "SurchargeAmt": 0.0,
        "Tax_BaseAmtWithSurchargeAmt": 5.0,
        "Tax_AmtWithSurchargeAmt": 0.95
      }
    ]
  }
]
    """