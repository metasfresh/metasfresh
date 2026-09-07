@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor3
Feature: EDI INVOIC export via postgREST
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

  @Id:S0467_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create an invoice and export it to JSON
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
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
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoiceS0467_010_ID@,
      "Invoice_Receiver_Tec_GLN": null,
      "Invoice_Sender_Tec_GLN": null,
      "Invoice_Sender_CountryCode": "DE",
      "Invoice_Sender_VATaxId": null,
      "Invoice_DocumentNo": "S0467_010",
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
          "Product_Name": "postgRESTExportProductName",
          "Product_Description": "postgRESTExportProductDescription",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "postgRESTExportProductValue",
          "Product_DepositType": null
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
      ],
      "Version": "0.2"
    }
  ]
}
    """

  @Id:S0467_020
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC JSON export exposes Product_DepositType for the line product (me03#29557)
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier        | Value                    | Name                    | Description                    | DepositType |
      | product_S0467_020 | depositTypeProductValue  | depositTypeProductName  | depositTypeProductDescription  | NRC         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0467_020 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0467_020 | salesInvoiceS0467_020_ID | customer1     | Ausgangsrechnung        | S0467_020  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0467_020 | product_S0467_020 | 1 PCE       |
    And the invoice identified by salesInvoiceS0467_020 is completed

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
      "value": "@salesInvoiceS0467_020_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoiceS0467_020_ID@,
      "Invoice_DocumentNo": "S0467_020",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "depositTypeProductName",
          "Product_Supplier_ProductNo": "depositTypeProductValue",
          "Product_DepositType": "NRC"
        }
      ]
    }
  ]
}
    """

  @Id:S0467_030
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC JSON export exposes Product_DepositType="RC" without space-padding (me03#29557 follow-up)
    # Regression for the bug fixed by migration 5802960: M_Product.DepositType was originally CHAR(3),
    # so the 2-char value 'RC' was stored and emitted as 'RC ' (space-padded). After the type change
    # to VARCHAR(3) + RTRIM data fix the JSON must carry exactly "RC".
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier        | Value                       | Name                       | Description                       | DepositType |
      | product_S0467_030 | depositTypeRCProductValue   | depositTypeRCProductName   | depositTypeRCProductDescription   | RC          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0467_030 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier            | REST.Context             | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS0467_030 | salesInvoiceS0467_030_ID | customer1     | Ausgangsrechnung        | S0467_030  | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID          | M_Product_ID      | QtyInvoiced |
      | salesInvoiceS0467_030 | product_S0467_030 | 1 PCE       |
    And the invoice identified by salesInvoiceS0467_030 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_030      | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
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
      "value": "@salesInvoiceS0467_030_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoiceS0467_030_ID@,
      "Invoice_DocumentNo": "S0467_030",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "depositTypeRCProductName",
          "Product_Supplier_ProductNo": "depositTypeRCProductValue",
          "Product_DepositType": "RC"
        }
      ]
    }
  ]
}
    """

  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  @Id:S0481_010
  Scenario: create an invoice and export it to JSON taking into consideration the BPartner of the Warehouse as Supplier
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_2               | DE          | addr 0481    | 456        | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | IsRemitTo | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234568890123 | customer1                | location_2                   | true      | true         | true         | locationBPName   | locationName |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | wh_S0481_010   | customer1     | bpLocation_2           |
    And metasfresh contains M_Products:
      | Identifier        | REST.Context.Value | REST.Context.Name | Description        |
      | product_S0481_010 | productValue       | productName       | productDescription |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0481_010 | 5.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_Warehouse_ID |
      | o_1        | true    | customer1     | 2025-05-01  | 2025-05-01Z  | wh_S0481_010   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered |
      | ol_1       | o_1                   | product_S0481_010 | 1          |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.DocumentNo |
      | s_s_1                            | s_1                   | shipmentDocNo           |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_ci_1                           | ol_1                      | 1            |

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_ci_1                           |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context              | REST.Context.DocumentNo      |
      | ic_ci_1                           | salesInvoice_S0481_010  | salesInvoice_S0481_010_ID | salesInvoice_S0481_010_docNo |

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
      "value": "@salesInvoice_S0481_010_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoice_S0481_010_ID@,
      "Invoice_Receiver_Tec_GLN": "1234568890123",
      "Invoice_Sender_Tec_GLN": null,
      "Invoice_Sender_CountryCode": "DE",
      "Invoice_Sender_VATaxId": null,
      "Invoice_DocumentNo": "@salesInvoice_S0481_010_docNo@",
      "Invoice_Date": "2025-05-01T00:00:00",
      "Invoice_Acct_Date": "2025-05-01T00:00:00",
      "DocType_Base": "ARI",
      "DocType_Sub": null,
      "CreditMemo_Reason": null,
      "CreditMemo_ReasonText": null,
      "Order_POReference": null,
      "Order_Date": "2025-05-01T00:00:00",
      "Shipment_Date": "2025-05-01T00:00:00",
      "Shipment_DocumentNo": "@shipmentDocNo@",
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
      "Partners": [
        {
          "EANCOM_LocationType": "BY",
          "GLN": "1234568890123",
          "Name": "@customerName@",
          "Name2": null,
          "PartnerNo": "@customerValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "locationBPName",
          "Setup_Place_No": null,
          "Address1": "addr 0481",
          "Address2": null,
          "Postal": "456",
          "City": "locationCity_2",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "DP",
          "GLN": "1234568890123",
          "Name": "@customerName@",
          "Name2": null,
          "PartnerNo": "@customerValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "locationBPName",
          "Setup_Place_No": null,
          "Address1": "addr 0481",
          "Address2": null,
          "Postal": "456",
          "City": "locationCity_2",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "IV",
          "GLN": "1234568890123",
          "Name": "@customerName@",
          "Name2": null,
          "PartnerNo": "@customerValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "locationBPName",
          "Setup_Place_No": null,
          "Address1": "addr 0481",
          "Address2": null,
          "Postal": "456",
          "City": "locationCity_2",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SN",
          "GLN": "1234568890123",
          "Name": "@customerName@",
          "Name2": null,
          "PartnerNo": "@customerValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "locationBPName",
          "Setup_Place_No": null,
          "Address1": "addr 0481",
          "Address2": null,
          "Postal": "456",
          "City": "locationCity_2",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SU",
          "GLN": "1234568890123",
          "Name": "@customerName@",
          "Name2": null,
          "PartnerNo": "@customerValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "locationBPName",
          "Setup_Place_No": null,
          "Address1": "addr 0481",
          "Address2": null,
          "Postal": "456",
          "City": "locationCity_2",
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
          "QtyBasedOn": "Nominal",
          "NetAmt": 5.0,
          "Tax_Percent": 19.0,
          "Tax_Amount": 0.95,
          "Product_Name": "@productName@",
          "Product_Description": "productDescription",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@productValue@",
          "Product_DepositType": null
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
      ],
      "Version": "0.2"
    }
  ]
}
    """

  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00359_EDI_INVOICE_JSON
@F00359
  Scenario: a packaging-material invoice line does not add a second delivery party (DP)
    # A packaging-material line (deposit / Leergut) carries no C_OrderLine_ID.
    # It must not be exported as an INVOIC line item, and — because it would otherwise fall back to
    # the invoice's own bill-to location — it must not emit a second DP party. The order's delivery
    # (HandOver) location differs from the bill-to, so the genuine DP is the delivery location and a
    # spurious second DP (= bill-to, from the packaging line) is detectable.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City   |
      | loc_bill                 | DE          | billAddr     | 111        | billCity   |
      | loc_delivery             | DE          | delivAddr    | 222        | delivCity  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName |
      | bpLoc_bill   | 1111111111111 | customer1                | loc_bill                     | true         | true         | billBPName       |
      | bpLoc_deliv  | 2222222222222 | customer1                | loc_delivery                 | true         | false        | delivBPName      |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | wh             | customer1     | bpLoc_bill             |
    And metasfresh contains M_Products:
      | Identifier    | Value              | Name              | Description              |
      | productNormal | normalProductValue | normalProductName | normalProductDescription |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID |
      | salesPLV               | productNormal | 5.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | HandOver_Location_ID | DeliveryRule | DateOrdered | DatePromised | M_Warehouse_ID |
      | o_1        | true    | customer1     | bpLoc_bill             | bpLoc_deliv          | F            | 2025-05-01  | 2025-05-01Z  | wh             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID  | QtyEntered |
      | ol_1       | o_1                   | productNormal | 1          |
    And the order identified by o_1 is completed

    # Build the invoice directly (not via the IC pipeline): a product line linked to the order line
    # (so its delivery party resolves to the order's HandOver location) plus an order-less
    # packaging-material line (as a deposit / Leergut line would be — no C_OrderLine_ID).
    # M_PriceList_ID is set explicitly: bpLoc_bill is a non-default bill location, so the invoice's
    # price list is not defaulted for it — the explicit price list keeps completion deterministic.
    And metasfresh contains C_Invoice:
      | Identifier | REST.Context  | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | pkgInvoice | pkgInvoice_ID | customer1     | bpLoc_bill             | salesPriceList | Ausgangsrechnung        | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID  | C_OrderLine_ID | QtyInvoiced | IsPackagingMaterial |
      | pkgInvoice   | productNormal | ol_1           | 1 PCE       | N                   |
      | pkgInvoice   | productNormal |                | 1 PCE       | Y                   |
    And the invoice identified by pkgInvoice is completed

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
      "value": "@pkgInvoice_ID@"
    }
  ]
}
    """

    # The packaging line is excluded from Lines (only the product line is exported), and Partners
    # carries exactly ONE "DP" (the HandOver delivery location) — NOT a second DP at the bill-to
    # location. Without the fix the order-less packaging line adds that second DP and the Partners
    # array has 6 entries, so this assertion fails.
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @pkgInvoice_ID@,
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "normalProductName",
          "Product_Supplier_ProductNo": "normalProductValue"
        }
      ],
      "Partners": [
        { "EANCOM_LocationType": "SU", "Name": "metasfresh AG", "City": "Bonn" },
        { "EANCOM_LocationType": "BY", "GLN": "1111111111111", "Address1": "billAddr", "City": "billCity" },
        { "EANCOM_LocationType": "IV", "GLN": "1111111111111", "Address1": "billAddr", "City": "billCity" },
        { "EANCOM_LocationType": "SN", "GLN": "1111111111111", "Address1": "billAddr", "City": "billCity" },
        { "EANCOM_LocationType": "DP", "GLN": "2222222222222", "Address1": "delivAddr", "City": "delivCity" }
      ]
    }
  ]
}
    """
  @Id:S31499_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC consumer-unit GTIN falls back to the ASI-data EAN_CU when the product has no GTIN
  ## The product carries no M_Product.GTIN. Its only identifier is EAN_CU on a wildcard
  ## M_Product_ASI_Data row, so Buyer_GTIN_CU can only resolve through the EAN fallback.
  ## Without that fallback the exported Product_Buyer_CU_GTIN is null and the clearing
  ## centre rejects the line for missing product info.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier        | Value              | Name              | Description              |
      | product_S31499_010 | eanCuProductValue | eanCuProductName  | eanCuProductDescription  |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier          | M_Product_ID       | SeqNo | EAN_CU        |
      | asiData_S31499_010  | product_S31499_010 | 10    | 4055555000019 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID |
      | salesPLV               | product_S31499_010 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier             | REST.Context              | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS31499_010 | salesInvoiceS31499_010_ID | customer1     | Ausgangsrechnung        | S31499_010 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID           | M_Product_ID       | QtyInvoiced |
      | salesInvoiceS31499_010 | product_S31499_010 | 1 PCE       |
    And the invoice identified by salesInvoiceS31499_010 is completed

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
      "value": "@salesInvoiceS31499_010_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoiceS31499_010_ID@,
      "Invoice_DocumentNo": "S31499_010",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "eanCuProductName",
          "Product_Supplier_ProductNo": "eanCuProductValue",
          "Product_Buyer_CU_GTIN": "4055555000019"
        }
      ]
    }
  ]
}
    """

  @Id:S31499_020
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC consumer-unit GTIN falls back to the ASI-data EAN13_ProductCode when neither GTIN nor EAN_CU is set
  ## Last resort of the consumer-unit chain before the product's own GTIN:
  ## M_Product_ASI_Data.EAN13_ProductCode. It was widened to VARCHAR(50) by migration
  ## 5802510 precisely so it can hold a real 13-digit EAN for this fallback.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier         | Value             | Name             | Description             |
      | product_S31499_020 | ean13ProductValue | ean13ProductName | ean13ProductDescription |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID       | SeqNo | EAN13_ProductCode |
      | asiData_S31499_020 | product_S31499_020 | 10    | 4055555000026     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID |
      | salesPLV               | product_S31499_020 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier             | REST.Context              | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoiceS31499_020 | salesInvoiceS31499_020_ID | customer1     | Ausgangsrechnung        | S31499_020 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID           | M_Product_ID       | QtyInvoiced |
      | salesInvoiceS31499_020 | product_S31499_020 | 1 PCE       |
    And the invoice identified by salesInvoiceS31499_020 is completed

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
      "value": "@salesInvoiceS31499_020_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoiceS31499_020_ID@,
      "Invoice_DocumentNo": "S31499_020",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "ean13ProductName",
          "Product_Supplier_ProductNo": "ean13ProductValue",
          "Product_Buyer_CU_GTIN": "4055555000026"
        }
      ]
    }
  ]
}
    """
