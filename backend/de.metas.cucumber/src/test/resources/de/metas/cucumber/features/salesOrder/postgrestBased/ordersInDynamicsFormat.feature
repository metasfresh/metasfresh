@from:cucumber
@ghActions:run_on_executor3
Feature: Export Orders in specific format via postgREST

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-05-01T16:30:17+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

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
      | Identifier | IsCustomer | REST.Context | Name         | Value         | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | customer_ID  | customerName | customerValue | N        | pricingSystem      |
      | vendor1    | N          | vendor_ID    | vendorName   | vendorValue   | Y        | pricingSystem      |
      | dropShip1  | N          | dropShip_ID  | dropShipName | dropShipValue | Y        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault | City       | Postal | Address1       | Address2       | Address3       | Address4       |
      | customer_location_1 | customer1     | Y               | Y               |            |        |                |                |                |                |
      | vendor_location_1   | vendor1       | Y               | Y               | VendorCity | 5100   | VendorAddress1 | VendorAddress2 | VendorAddress3 | VendorAddress4 |
      | dropShip_location_1 | dropShip1     | Y               | Y               | City       | 5000   | Address1       | Address2       | Address3       | Address4       |

  @from:cucumber
  Scenario: create a sales order and export it to JSON via C_Order_ID. The used BPartner does not have an external reference for Dynamics365 External System
    Given metasfresh contains M_Products:
      | Identifier | REST.Context.Value           | REST.Context | REST.Context.Name           | Description                        | ProductType |
      | product1   | postgRESTExportProductValue1 | product_ID_1 | postgRESTExportProductName1 | postgRESTExportProductDescription1 |             |
      | product2   | postgRESTExportProductValue2 | product_ID_2 | postgRESTExportProductName2 | postgRESTExportProductDescription2 | F           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 2.00     | PCE      |
      | salesPLV               | product2     | 2.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID |
      | order1     | order_ID     | true    | true       | dropShip1            | dropShip_location_1  | customer1     | 2022-06-17  | pricingSystem      | customer_location_1    |
    And metasfresh contains C_OrderLines:
      | Identifier | REST.Context   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine1 | orderLine_ID   | order1     | product1     | 10         |
      | orderLine2 | orderLine_ID_2 | order1     | product2     | 10         |

    When the order identified by order1 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Sales_Order_Dynamics_JSON/invoke' and fulfills with '200' status code
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
{
  "orgCode": "001",
  "orderNumber": @order_ID@,
  "dateOrdered": "2022-06-17",
  "datePromised": "2025-04-30",
  "partnerIdentifier": "@customer_ID@",
  "partnerValue": "customerValue",
  "partnerName": "customerName",
  "currency": "EUR",
  "lines": [
    {
      "qty": 10,
      "uom": "PCE",
      "price": 2.0,
      "discount": 0,
      "orderLineId": @orderLine_ID@,
      "productName": "@postgRESTExportProductName1@",
      "orderLineNumber": 10,
      "productIdentifier": "@postgRESTExportProductValue1@",
      "productDescription": "postgRESTExportProductDescription1"
    }
  ],
  "charges": [
    {
      "price": 2.0,
      "chargeIdentifier": @orderLine_ID_2@
    }
  ]
}   
    """

  @from:cucumber
  Scenario: create a sales order and export it to JSON via C_Order_ID. The used BPartner has an external reference for Dynamics365 External System
    And metasfresh contains External System
      | Name        | Value       |
      | Dynamics365 | Dynamics365 |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference | C_BPartner_ID |
      | bpartner_ref                      | Dynamics365    | BPartner | DT200             | customer1     |
    And metasfresh contains M_Products:
      | Identifier | REST.Context.Value           | REST.Context | REST.Context.Name           | Description                        | ProductType |
      | product1   | postgRESTExportProductValue1 | product_ID_1 | postgRESTExportProductName1 | postgRESTExportProductDescription1 |             |
      | product2   | postgRESTExportProductValue2 | product_ID_2 | postgRESTExportProductName2 | postgRESTExportProductDescription2 | F           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 2.00     | PCE      |
      | salesPLV               | product2     | 2.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID |
      | order1     | order_ID     | true    | true       | dropShip1            | dropShip_location_1  | customer1     | 2022-06-17  | pricingSystem      | customer_location_1    |
    And metasfresh contains C_OrderLines:
      | Identifier | REST.Context   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine1 | orderLine_ID   | order1     | product1     | 10         |
      | orderLine2 | orderLine_ID_2 | order1     | product2     | 10         |

    When the order identified by order1 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Sales_Order_Dynamics_JSON/invoke' and fulfills with '200' status code
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
{
  "orgCode": "001",
  "orderNumber": @order_ID@,
  "dateOrdered": "2022-06-17",
  "datePromised": "2025-04-30",
  "partnerIdentifier": "DT200",
  "partnerValue": "customerValue",
  "partnerName": "customerName",
  "currency": "EUR",
  "lines": [
    {
      "qty": 10,
      "uom": "PCE",
      "price": 2.0,
      "discount": 0,
      "orderLineId": @orderLine_ID@,
      "productName": "@postgRESTExportProductName1@",
      "orderLineNumber": 10,
      "productIdentifier": "@postgRESTExportProductValue1@",
      "productDescription": "postgRESTExportProductDescription1"
    }
  ],
  "charges": [
    {
      "price": 2.0,
      "chargeIdentifier": @orderLine_ID_2@
    }
  ]
}   
    """

  @ignore
  @from:cucumber
  Scenario: create a purchase order and export it to JSON via C_Order_ID
    Given metasfresh contains M_Products:
      | Identifier | REST.Context.Value           | REST.Context | REST.Context.Name           | Description                        |
      | product1   | postgRESTExportProductValue1 | product_ID_1 | postgRESTExportProductName1 | postgRESTExportProductDescription1 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product1     | 2.00     | PCE      |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID | DocumentNo       | C_BPartner_ID | DateOrdered | POReference      | M_PricingSystem_ID | C_BPartner_Location_ID | DatePromised            |
      | order1     | order_ID     | false   | true       | dropShip1            | dropShip_location_1  | test_11202025_11 | vendor1       | 2022-06-17  | test_11202025_11 | pricingSystem      | vendor_location_1      | 2022-06-18T12:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | REST.Context | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine1 | orderLine_ID | order1     | product1     | 10         |

    When the order identified by order1 is completed

    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | POReference        | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | C_BPartner_Location_ID | DatePromised            |
      | order2     | order_ID_2   | false   | test_11202025_11_2 | vendor1       | 2022-06-17  | pricingSystem      | vendor_location_1      | 2022-06-18T12:00:00.00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | REST.Context   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine2 | orderLine_ID_2 | order2     | product1     | 10         |

    When the order identified by order2 is completed

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Purchase_Order_Dynamics_JSON/invoke' and fulfills with '200' status code
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

    # the dropship-partner is our Org-BPartner ("partnerID": 2155894)
    And the metasfresh REST-API responds with
    """
{
  "orgCode": "001",
  "orderDocumentNo": "test_11202025_11",
  "orderNumber": @order_ID@,
  "currencyCode": "EUR",
  "partnerIdentifier": "@vendor_ID@",
  "partnerValue": "vendorValue",
  "partnerName": "vendorName",
  "dropShip": {
    "city": "City",
    "postal": "5000",
    "country": "DEU",
    "address1": "Address1",
    "address2": "Address2",
    "address3": "Address3",
    "address4": "Address4",
    "partnerID": 2155894,
    "partnerName": "dropShipName",
    "partnerValue": "dropShipValue"
  },
  "lines": [
    {
      "qty": 10,
      "uom": "PCE",
      "price": 2.0,
      "discount": 0,
      "orderLineId": @orderLine_ID@,
      "productName": "@postgRESTExportProductName1@",
      "dateRequested": "2022-06-18",
      "orderLineNumber": 10,
      "productIdentifier": "@postgRESTExportProductValue1@",
      "productDescription": "postgRESTExportProductDescription1"
    }
  ]
}
    """

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Purchase_Order_Dynamics_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Order_ID",
      "value": "@order_ID_2@"
    }
  ]
}
    """

  # the dropship-partner is our Org-BPartner ("partnerID": 2155894)
    And the metasfresh REST-API responds with
    """
{
  "orgCode": "001",
  "orderDocumentNo": "test_11202025_11_2",
  "orderNumber": @order_ID_2@,
  "currencyCode": "EUR",
  "partnerIdentifier": "@vendor_ID@",
  "partnerValue": "vendorValue",
  "partnerName": "vendorName",
  "dropShip": {
    "city": "Bonn",
    "postal": "53179",
    "country": "DEU",
    "address1": "Am Nossbacher Weg 2",
    "address2": null,
    "address3": null,
    "address4": null,
    "partnerID": 2155894,
    "partnerName": "metasfresh AG",
    "partnerValue": "metasfresh"
  },
  "lines": [
    {
      "qty": 10,
      "uom": "PCE",
      "price": 2.0,
      "discount": 0,
      "orderLineId": @orderLine_ID_2@,
      "productName": "@postgRESTExportProductName1@",
      "dateRequested": "2022-06-18",
      "orderLineNumber": 10,
      "productIdentifier": "@postgRESTExportProductValue1@",
      "productDescription": "postgRESTExportProductDescription1"
    }
  ]
}
    """