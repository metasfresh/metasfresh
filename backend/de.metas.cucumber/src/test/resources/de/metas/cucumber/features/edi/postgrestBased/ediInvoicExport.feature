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
    And temporarily set sys config boolean value false for sys config 'de.metas.payment.esr.Enabled'
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
      "Invoice_PaymentReference": null,
      "Order_Date": "2025-05-01T00:00:00",
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
          "Order_IsPOReferenceBuyerOrderNo": false,
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

  @Id:S32406_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC of a credit memo without any order exports the invoice date as Order_Date
  ## A return credit memo (e.g. one created manually for returned goods) has no C_Order, so neither the invoice,
  ## an order header nor its lines can provide an order date; INVOIC then falls back to DateInvoiced.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier | Name                  |
      | product    | creditMemoProductName |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier | REST.Context  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | creditMemo | creditMemo_ID | customer1     | Gutschrift              | S32406_010 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | creditMemo   | product      | 1 PCE       |
    And the invoice identified by creditMemo is completed

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
      "value": "@creditMemo_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @creditMemo_ID@,
      "Invoice_DocumentNo": "S32406_010",
      "DocType_Base": "ARC",
      "Invoice_Date": "2025-05-01T00:00:00",
      "Order_Date": "2025-05-01T00:00:00"
    }
  ]
}
    """


  # ############################################################
  # ############################################################

  @Id:S33256_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC product description falls back to the product description when the ASI data carries the migration marker
  ## The wildcard M_Product_ASI_Data row carries the placeholder text written by the
  ## C_BPartner_Product migration as ProductDescription, with no ProductName.
  ## The exported Product_Description must be the M_Product.Description, never the placeholder.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier         | Value              | Name              | Description              |
      | product_S33256_010 | markerProductValue | markerProductName | markerProductDescription |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID       | SeqNo | ProductDescription                             |
      | asiData_S33256_010 | product_S33256_010 | 10    | [Migrated from C_BPartner_Product test-marker] |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID |
      | salesPLV               | product_S33256_010 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier              | REST.Context               | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice_S33256_010 | salesInvoice_S33256_010_ID | customer1     | Ausgangsrechnung        | S33256_010 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID       | QtyInvoiced |
      | salesInvoice_S33256_010 | product_S33256_010 | 1 PCE       |
    And the invoice identified by salesInvoice_S33256_010 is completed

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
      "value": "@salesInvoice_S33256_010_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoice_S33256_010_ID@,
      "Invoice_DocumentNo": "S33256_010",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "markerProductName",
          "Product_Supplier_ProductNo": "markerProductValue",
          "Product_Description": "markerProductDescription"
        }
      ]
    }
  ]
}
    """


  # ############################################################
  # ############################################################

  @Id:S33268_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC consumer-unit GTIN prefers the product GTIN over a variable-measure EAN_CU with prefix 21
  ## EAN_CU 2115454000000 is a variable-measure (price/weight) code with prefix 21, not a real GTIN.
  ## The product carries a real M_Product.GTIN, which must be exported as Product_Buyer_CU_GTIN.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier         | Value               | Name               | Description               | GTIN          |
      | product_S33268_010 | gtinProductValue010 | gtinProductName010 | gtinProductDescription010 | 7640119467344 |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID       | SeqNo | EAN_CU        |
      | asiData_S33268_010 | product_S33268_010 | 10    | 2115454000000 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID |
      | salesPLV               | product_S33268_010 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier              | REST.Context               | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice_S33268_010 | salesInvoice_S33268_010_ID | customer1     | Ausgangsrechnung        | S33268_010 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID       | QtyInvoiced |
      | salesInvoice_S33268_010 | product_S33268_010 | 1 PCE       |
    And the invoice identified by salesInvoice_S33268_010 is completed

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
      "value": "@salesInvoice_S33268_010_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoice_S33268_010_ID@,
      "Invoice_DocumentNo": "S33268_010",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "gtinProductName010",
          "Product_Buyer_CU_GTIN": "7640119467344"
        }
      ]
    }
  ]
}
    """


  # ############################################################
  # ############################################################

  @Id:S33268_020
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: INVOIC consumer-unit GTIN prefers the product GTIN over a variable-measure EAN_CU with prefix 02
  ## EAN_CU 0212345000000 is a variable-measure code with prefix 02, not a real GTIN.
  ## The product carries a real M_Product.GTIN, which must be exported as Product_Buyer_CU_GTIN.
    Given metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customerName      | customerValue      | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier         | Value               | Name               | Description               | GTIN          |
      | product_S33268_020 | gtinProductValue020 | gtinProductName020 | gtinProductDescription020 | 7640119467344 |
    And metasfresh contains M_Product_ASI_Data:
      | Identifier         | M_Product_ID       | SeqNo | EAN_CU        |
      | asiData_S33268_020 | product_S33268_020 | 10    | 0212345000000 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID |
      | salesPLV               | product_S33268_020 | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier              | REST.Context               | C_BPartner_ID | C_DocTypeTarget_ID.Name | DocumentNo | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice_S33268_020 | salesInvoice_S33268_020_ID | customer1     | Ausgangsrechnung        | S33268_020 | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID            | M_Product_ID       | QtyInvoiced |
      | salesInvoice_S33268_020 | product_S33268_020 | 1 PCE       |
    And the invoice identified by salesInvoice_S33268_020 is completed

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
      "value": "@salesInvoice_S33268_020_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @salesInvoice_S33268_020_ID@,
      "Invoice_DocumentNo": "S33268_020",
      "Lines": [
        {
          "Invoice_Line": 10,
          "Product_Name": "gtinProductName020",
          "Product_Buyer_CU_GTIN": "7640119467344"
        }
      ]
    }
  ]
}
    """
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario Outline: INVOIC of an order from the EDI-ORDERS data source <dataSource> carries the payment reference and the buyer-order-number flag
    Given temporarily set sys config boolean value true for sys config 'de.metas.payment.esr.Enabled'
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | orgBankAccount                 | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | orgBankAccount                 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |
    And metasfresh contains M_Products:
      | Identifier | Name                   |
      | product    | invPoRefT1<key>Product |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN   | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | <gln> | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN   | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | <gln> | customer                 | Y               | Y               |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "<header>",
            "externalLineId": "<header>_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-<dataSource>",
            "bpartner": {
                "bpartnerIdentifier": "gln-<gln>",
                "bpartnerLocationIdentifier": "gln-<gln>"
            },
            "dateRequired": "2025-05-01",
            "dateOrdered": "2025-05-01",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-invPoRefT1<key>Product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234567",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "<header>",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | POReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234567  | true                      |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered |
      | ol_1                      | order_1               | product                 | 2          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_1       | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 2            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_1                              | invoice                 | invoice_ID   |
    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | REST.Context     |
      | referenceNo                 | invoice              | 540006                           | invoiceReference |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Invoice_PaymentReference": "@invoiceReference@",
      "Order_POReference": "0021234567",
      "Lines": [
        {
          "Order_POReference": "0021234567",
          "Order_IsPOReferenceBuyerOrderNo": true
        }
      ]
    }
  ]
}
    """

    Examples:
      | key     | dataSource                | header                | gln           |
      | Ecosio  | DEST.de.metas.edi.ecosio  | invPoRefHeaderEcosio  | 4337010331011 |
      | Eddyson | DEST.de.metas.edi.eddyson | invPoRefHeaderEddyson | 4337010331012 |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC of a manually entered order with ESR disabled has no payment reference and flag false on every line
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | product    | invPoRefT2Product |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | 4337010331021 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010331021 | customer                 | Y               | Y               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | POReference |
      | order_1    | true    | customer      | warehouse      | 2025-05-01  | REF-ESR-OFF      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product                 | 2          |
    And the order identified by order_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_1       | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 2            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_1                              | invoice                 | invoice_ID   |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Invoice_PaymentReference": null,
      "Order_POReference": "REF-ESR-OFF",
      "Lines": [
        {
          "Order_POReference": "REF-ESR-OFF",
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC of a flagged order whose POReference was changed before invoicing has flag false next to the new reference
    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | product    | invPoRefT4aProduct |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | 4337010331031 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010331031 | customer                 | Y               | Y               |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "invPoRefHeaderT4a",
            "externalLineId": "invPoRefHeaderT4a_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-DEST.de.metas.edi.ecosio",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010331031",
                "bpartnerLocationIdentifier": "gln-4337010331031"
            },
            "dateRequired": "2025-05-01",
            "dateOrdered": "2025-05-01",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-invPoRefT4aProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234568",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "invPoRefHeaderT4a",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | POReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234568  | true                      |
    And update order
      | C_Order_ID.Identifier | POReference |
      | order_1               | 0029999993  |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered |
      | ol_1                      | order_1               | product                 | 2          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_1       | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 2            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_1                              | invoice                 | invoice_ID   |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Lines": [
        {
          "Order_POReference": "0029999993",
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC of a flagged order keeps the flag when the POReference is changed on the invoice only
    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | product    | invPoRefT4cProduct |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | 4337010331041 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010331041 | customer                 | Y               | Y               |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "invPoRefHeaderT4c",
            "externalLineId": "invPoRefHeaderT4c_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-DEST.de.metas.edi.ecosio",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010331041",
                "bpartnerLocationIdentifier": "gln-4337010331041"
            },
            "dateRequired": "2025-05-01",
            "dateOrdered": "2025-05-01",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-invPoRefT4cProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234569",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "invPoRefHeaderT4c",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered |
      | ol_1                      | order_1               | product                 | 2          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_1       | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 2            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_1                              | invoice                 | invoice_ID   |
    And update C_Invoice:
      | Identifier | POReference |
      | invoice    | INVOICE-REF |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Order_POReference": "INVOICE-REF",
      "Lines": [
        {
          "Order_POReference": "0021234569",
          "Order_IsPOReferenceBuyerOrderNo": true
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC over two orders gives each line the flag of its own order
    Given metasfresh contains M_Products:
      | Identifier | Name              |
      | product    | invPoRefT6Product |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | 4337010331061 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010331061 | customer                 | Y               | Y               |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "invPoRefHeaderT6",
            "externalLineId": "invPoRefHeaderT6_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-DEST.de.metas.edi.ecosio",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010331061",
                "bpartnerLocationIdentifier": "gln-4337010331061"
            },
            "dateRequired": "2025-05-01",
            "dateOrdered": "2025-05-01",
            "orderDocType": "SalesOrder",
            "productIdentifier": "val-invPoRefT6Product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234570",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_A               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "invPoRefHeaderT6",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_A               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered |
      | ol_A                      | order_A               | product                 | 2          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | POReference |
      | order_B    | true    | customer      | warehouse      | 2025-05-01  | REF-B       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | order_B               | product                 | 2          |
    And the order identified by order_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_A       | ol_A                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_A                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_A                             | shipment_A            |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_B       | ol_B                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_B                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_B                             | shipment_B            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_A                              | ol_A                      | 2            |
      | ic_B                              | ol_B                      | 2            |
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_A                              |
      | ic_B                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_A                              | invoice                 | invoice_ID   |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Lines": [
        {
          "Order_POReference": "0021234570",
          "Order_IsPOReferenceBuyerOrderNo": true
        },
        {
          "Order_POReference": "REF-B",
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC line aggregated from two orders has no order line: reference falls back to the invoice's, flag false
    Given metasfresh contains M_Products:
      | Identifier | Name                |
      | product    | invPoRefT6b1Product |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | 4337010331071 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010331071 | customer                 | Y               | Y               |
    And load C_Aggregations:
      | Identifier | C_Aggregation_ID |
      | lineAgg    | 540003           |
    And update C_BPartner:
      | Identifier | OPT.SO_InvoiceLine_Aggregation_ID.Identifier |
      | customer   | lineAgg                                      |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | POReference |
      | order_A    | true    | customer      | warehouse      | 2025-05-01  | REF-A       |
    And update order
      | C_Order_ID.Identifier | IsPOReferenceBuyerOrderNo |
      | order_A               | true                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | order_A               | product                 | 2          |
    And the order identified by order_A is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | POReference |
      | order_B    | true    | customer      | warehouse      | 2025-05-01  | REF-B       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_B       | order_B               | product                 | 2          |
    And the order identified by order_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_A       | ol_A                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_A                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_A                             | shipment_A            |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_B       | ol_B                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_B                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_B                             | shipment_B            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_A                              | ol_A                      | 2            |
      | ic_B                              | ol_B                      | 2            |
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_A                              |
      | ic_B                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_A                              | invoice                 | invoice_ID   |
    And update C_Invoice:
      | Identifier | POReference |
      | invoice    | INVOICE-REF |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Lines": [
        {
          "Order_POReference": "INVOICE-REF",
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC line whose order has an empty POReference: reference falls back to the invoice's, flag false although the order is ticked
    Given metasfresh contains M_Products:
      | Identifier | Name                |
      | product    | invPoRefT6b2Product |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | salesPLV                          | product                 | 5.00     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | Y              | N            | pricingSystem                 | customerLocation           | 4337010331081 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010331081 | customer                 | Y               | Y               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered |
      | order_1    | true    | customer      | warehouse      | 2025-05-01  |
    And update order
      | C_Order_ID.Identifier | IsPOReferenceBuyerOrderNo |
      | order_1               | true                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product                 | 2          |
    And the order identified by order_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_1       | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_1                             | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_1                             | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 2            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context |
      | ic_1                              | invoice                 | invoice_ID   |
    And update C_Invoice:
      | Identifier | POReference |
      | invoice    | INVOICE-REF |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Lines": [
        {
          "Order_POReference": "INVOICE-REF",
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC of an invoice without any sales order carries the payment reference and flag false on its line
    Given temporarily set sys config boolean value true for sys config 'de.metas.payment.esr.Enabled'
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | orgBankAccount                 | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | orgBankAccount                 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer   | Y          | invPoRefT6b3Name  | invPoRefT6b3Value  | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier | Name                |
      | product    | invPoRefT6b3Product |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier | REST.Context | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | invoice_ID   | customer      | Ausgangsrechnung        | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice      | product      | 1 PCE       |
    And the invoice identified by invoice is completed
    And load C_ReferenceNo:
      | C_ReferenceNo_ID.Identifier | Record_ID.Identifier | C_ReferenceNo_Type_ID.Identifier | REST.Context     |
      | referenceNo                 | invoice              | 540006                           | invoiceReference |
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Invoice_PaymentReference": "@invoiceReference@",
      "Lines": [
        {
          "Order_POReference": null,
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: INVOIC of a credit memo has no payment reference even with ESR enabled
    Given temporarily set sys config boolean value true for sys config 'de.metas.payment.esr.Enabled'
    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | orgBankAccount                 | 2000257                 |
    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code | OPT.IsEsrAccount | OPT.AccountNo | OPT.ESR_RenderedAccountNo | OPT.IBAN              |
      | orgBankAccount                 | CHF                     | Y                | 1234567890    | 123456789                 | CH3908704016075473007 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | REST.Context.Name | REST.Context.Value | IsVendor | M_PricingSystem_ID |
      | customer   | Y          | invPoRefT6b4Name  | invPoRefT6b4Value  | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |
    And metasfresh contains M_Products:
      | Identifier | Name                |
      | product    | invPoRefT6b4Product |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |
    And metasfresh contains C_Invoice:
      | Identifier | REST.Context | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | invoice_ID   | customer      | Gutschrift              | 2025-05-01   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoice      | product      | 1 PCE       |
    And the invoice identified by invoice is completed
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
      "value": "@invoice_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "DocType_Base": "ARC",
      "Invoice_PaymentReference": null,
      "Lines": [
        {
          "Order_POReference": null,
          "Order_IsPOReferenceBuyerOrderNo": false
        }
      ]
    }
  ]
}
    """

