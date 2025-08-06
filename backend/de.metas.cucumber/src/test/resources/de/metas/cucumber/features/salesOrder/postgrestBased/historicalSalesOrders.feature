@from:cucumber
@ghActions:run_on_executor3
Feature: Historical Sales Orders via postgREST

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
      | Identifier | IsCustomer | REST.Context | Name                  | Value                  | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customer_ID  | customerName_08052025 | customerValue_08052025 | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |

  @from:cucumber
  Scenario: create a sales order and export it to JSON via ExternalId
    Given metasfresh contains M_Products:
      | Identifier | Value                       | REST.Context | Name                       | Description                       |
      | product1   | postgRESTExportProductValue | product_ID   | postgRESTExportProductName | postgRESTExportProductDescription |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 2.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | DocumentNo    | C_BPartner_ID | DateOrdered | POReference   | M_PricingSystem_ID | C_BPartner_Location_ID | ExternalId           | AD_InputDataSource_ID |
      | order1     | order_ID     | true    | test_08052025 | customer1     | 2022-06-17  | test_08052025 | pricingSystem      | bpartner_location_1    | ExtHeader_08052025_1 | 540217                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | ExternalId         |
      | orderLine1 | order1     | product1     | 10         | ExtLine_08052025_1 |

    When the order identified by order1 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Sales_Orders_By_Ids_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "ExternalId",
      "value": "ExtHeader_08052025_1"
    },
    {
      "name": "AD_InputDataSource_ID",
      "value": 540217
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Order_ID": @order_ID@,
    "DocumentNo": "test_08052025",
    "POReference": test_08052025,
    "DateOrdered": "2022-06-17T00:00:00",
    "DatePromised": "2025-04-30T22:00:00+00:00",
    "Partner_ID": @customer_ID@,
    "Partner_Value": "customerValue_08052025",
    "Partner_Name": "customerName_08052025",
    "ExternalId": "ExtHeader_08052025_1",
    "AD_InputDataSource_ID": 540217,
    "Bill_Partner_ID": @customer_ID@,
    "Bill_Partner_Value": "customerValue_08052025",
    "Bill_Partner_Name": "customerName_08052025",
    "Handover_Partner_ID": null,
    "Handover_Partner_Value": null,
    "Handover_Partner_Name": null,
    "Dropship_Partner_ID": null,
    "Dropship_Partner_Value": null,
    "Dropship_Partner_Name": null,
    "GrandTotal": 23.8,
    "PaymentTerm": "30 Tage netto",
    "Currency": "EUR",
    "Lines": [
      {
        "Order_Line": 10,
        "OrderUOM": "PCE",
        "QtyOrdered_In_OrderUOM": 10,
        "StockUOM": "PCE",
        "QtyOrdered_In_StockUOM": 10,
        "PriceEntered": 2.0,
        "Discount": 0,
        "PriceActual": 2.0,
        "Price-UOM": "PCE",
        "QtyOrdered_In_PriceUOM": 10,
        "Catch-Or-Nominal": "Nominal",
        "TaxRate": 19.0,
        "IsPackingMaterial": "N",
        "Product_Name": "postgRESTExportProductName",
        "Product_Value": "postgRESTExportProductValue",
        "Product_ID": @product_ID@
      }
    ]
  }
]
    """

  @from:cucumber
  Scenario: create a sales order and export it to JSON via C_Order_ID
    Given metasfresh contains M_Products:
      | Identifier | Value                         | REST.Context | Name                         | Description                         |
      | product1   | postgRESTExportProductValue_2 | product_ID   | postgRESTExportProductName_2 | postgRESTExportProductDescription_2 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 2.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | DocumentNo      | C_BPartner_ID | DateOrdered | POReference     | M_PricingSystem_ID | C_BPartner_Location_ID | ExternalId           | AD_InputDataSource_ID |
      | order1     | order_ID     | true    | test_08052025_2 | customer1     | 2022-06-17  | test_08052025_2 | pricingSystem      | bpartner_location_1    | ExtHeader_08052025_2 | 540217                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | ExternalId         |
      | orderLine1 | order1     | product1     | 10         | ExtLine_08052025_2 |

    When the order identified by order1 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Sales_Orders_By_Ids_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Order_ID",
      "value": "@order_ID@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
  {
    "Order_ID": @order_ID@,
    "DocumentNo": "test_08052025_2",
    "POReference": test_08052025_2,
    "DateOrdered": "2022-06-17T00:00:00",
    "DatePromised": "2025-04-30T22:00:00+00:00",
    "Partner_ID": @customer_ID@,
    "Partner_Value": "customerValue_08052025",
    "Partner_Name": "customerName_08052025",
    "ExternalId": "ExtHeader_08052025_2",
    "AD_InputDataSource_ID": 540217,
    "Bill_Partner_ID": @customer_ID@,
    "Bill_Partner_Value": "customerValue_08052025",
    "Bill_Partner_Name": "customerName_08052025",
    "Handover_Partner_ID": null,
    "Handover_Partner_Value": null,
    "Handover_Partner_Name": null,
    "Dropship_Partner_ID": null,
    "Dropship_Partner_Value": null,
    "Dropship_Partner_Name": null,
    "GrandTotal": 23.8,
    "PaymentTerm": "30 Tage netto",
    "Currency": "EUR",
    "Lines": [
      {
        "Order_Line": 10,
        "OrderUOM": "PCE",
        "QtyOrdered_In_OrderUOM": 10,
        "StockUOM": "PCE",
        "QtyOrdered_In_StockUOM": 10,
        "PriceEntered": 2.0,
        "Discount": 0,
        "PriceActual": 2.0,
        "Price-UOM": "PCE",
        "QtyOrdered_In_PriceUOM": 10,
        "Catch-Or-Nominal": "Nominal",
        "TaxRate": 19.0,
        "IsPackingMaterial": "N",
        "Product_Name": "postgRESTExportProductName_2",
        "Product_Value": "postgRESTExportProductValue_2",
        "Product_ID": @product_ID@
      }
    ]
  }
]
    """