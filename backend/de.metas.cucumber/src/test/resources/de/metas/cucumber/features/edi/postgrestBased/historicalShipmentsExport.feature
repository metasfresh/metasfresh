@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor5
Feature: Shipments export via postgREST
## F00350: EDI

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-05-15T16:30:17+02:00[Europe/Berlin]
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
      | Identifier | REST.Context.Value       | REST.Context.Name       | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | customer_value_S0475_010 | customer_name_S0475_010 | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |


  @Id:S0475_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via UpdatedGE and InputDataSource

    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_010 | postgRESTExportProductValue_S0475_010 | postgRESTExportProductName_S0475_010 | postgRESTExportProductDescription_S0475_010 | product_S0475_010_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_010 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_010 | test_S0475_010 |

    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0475_010 | test_S0475_010 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_010_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_010  | externalId_S0475_010 | test_S0475_010       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId               |
      | ol_1       | o_1                   | product_S0475_010 | 100        | externalLineId_S0475_010 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0475_010_ID   | shipment_S0475_010_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "UpdatedGE",
      "value": "2025-01-01 00:00:00"
    },
    {
      "name": "ExternalSystemCode",
      "value": "test_S0475_010"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_010_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_010_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_010",
    "ExternalSystemCode": "test_S0475_010",
    "DataSource": "int-test_S0475_010",
    "Order_ID": @order_S0475_010_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_010",
        "Product_ID": @product_S0475_010_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_010@",
        "ProductValue": "@postgRESTExportProductValue_S0475_010@"
      }
    ]
  }
]
    """

  @Id:S0475_020
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via externalId

    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_020 | postgRESTExportProductValue_S0475_020 | postgRESTExportProductName_S0475_020 | postgRESTExportProductDescription_S0475_020 | product_S0475_020_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_020 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_020 | test_S0475_020 |

    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0475_020 | test_S0475_020 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_020_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_020  | externalId_S0475_020 | test_S0475_020       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId               |
      | ol_1       | o_1                   | product_S0475_020 | 100        | externalLineId_S0475_020 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0475_020_ID   | shipment_S0475_020_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "ExternalId",
      "value": "externalId_S0475_020"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_020_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_020_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_020",
    "ExternalSystemCode": "test_S0475_020",
    "DataSource": "int-test_S0475_020",
    "Order_ID": @order_S0475_020_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_020",
        "Product_ID": @product_S0475_020_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_020@",
        "ProductValue": "@postgRESTExportProductValue_S0475_020@"
      }
    ]
  }
]
    """

  @Id:S0475_030
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via Order_ID

    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_030 | postgRESTExportProductValue_S0475_030 | postgRESTExportProductName_S0475_030 | postgRESTExportProductDescription_S0475_030 | product_S0475_030_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_030 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_030 | test_S0475_030 |

    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0475_030 | test_S0475_030 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_030_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_030  | externalId_S0475_030 | test_S0475_030       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId               |
      | ol_1       | o_1                   | product_S0475_030 | 100        | externalLineId_S0475_030 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0475_030_ID   | shipment_S0475_030_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "Order_ID",
      "value": "@order_S0475_030_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_030_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_030_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_030",
    "ExternalSystemCode": "test_S0475_030",
    "DataSource": "int-test_S0475_030",
    "Order_ID": @order_S0475_030_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_030",
        "Product_ID": @product_S0475_030_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_030@",
        "ProductValue": "@postgRESTExportProductValue_S0475_030@"
      }
    ]
  }
]
    """

  @Id:S0475_040
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via BPartnerValue

    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_040 | postgRESTExportProductValue_S0475_040 | postgRESTExportProductName_S0475_040 | postgRESTExportProductDescription_S0475_040 | product_S0475_040_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_040 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_040 | test_S0475_040 |

    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0475_040 | test_S0475_040 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_040_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_040  | externalId_S0475_040 | test_S0475_040       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId               |
      | ol_1       | o_1                   | product_S0475_040 | 100        | externalLineId_S0475_040 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0475_040_ID   | shipment_S0475_040_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "BPartnerValue",
      "value": "@customer_value_S0475_010@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_040_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_040_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_040",
    "ExternalSystemCode": "test_S0475_040",
    "DataSource": "int-test_S0475_040",
    "Order_ID": @order_S0475_040_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_040",
        "Product_ID": @product_S0475_040_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_040@",
        "ProductValue": "@postgRESTExportProductValue_S0475_040@"
      }
    ]
  }
]
    """

  @Id:S0475_050
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create two shipments and export them to JSON via Order_ID and limit it to 1

    Given metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_050 | postgRESTExportProductValue_S0475_050 | postgRESTExportProductName_S0475_050 | postgRESTExportProductDescription_S0475_050 | product_S0475_050_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_050 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_050 | test_S0475_050 |

    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0475_050 | test_S0475_050 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_050_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_050  | externalId_S0475_050 | test_S0475_050       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId                 |
      | ol_1       | o_1                   | product_S0475_050 | 100        | externalLineId_S0475_050_1 |
      | ol_2       | o_1                   | product_S0475_050 | 100        | externalLineId_S0475_050_2 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
      | s_s_2      | ol_2                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | DeliveryDate_Override |
      | s_s_1                            | 2025-05-17            |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo         |
      | s_s_1                            | s_1                   | shipment_S0475_050_1_ID | shipment_S0475_050_1_DocumentNo |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | true        |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo         |
      | s_s_2                            | s_2                   | shipment_S0475_050_2_ID | shipment_S0475_050_2_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "Order_ID",
      "value": "@order_S0475_050_ID@"
    },
    {
      "name": "Limit",
      "value": "1"
    }
  ]
}
    """
    And the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_050_1_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_050_1_DocumentNo@",
    "Shipment_Date": "2025-05-17T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_050",
    "ExternalSystemCode": "test_S0475_050",
    "DataSource": "int-test_S0475_050",
    "Order_ID": @order_S0475_050_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_050_1",
        "Product_ID": @product_S0475_050_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_050@",
        "ProductValue": "@postgRESTExportProductValue_S0475_050@"
      }
    ]
  }
]
    """

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "Order_ID",
      "value": "@order_S0475_050_ID@"
    },
    {
      "name": "Limit",
      "value": "1"
    },
    {
      "name": "Offset",
      "value": "1"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_050_2_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_050_2_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_050",
    "ExternalSystemCode": "test_S0475_050",
    "DataSource": "int-test_S0475_050",
    "Order_ID": @order_S0475_050_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 20,
        "ExternalId": "externalLineId_S0475_050_2",
        "Product_ID": @product_S0475_050_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_050@",
        "ProductValue": "@postgRESTExportProductValue_S0475_050@"
      }
    ]
  }
]
    """

  @Id:S0475_060
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via ShipmentDateGE

    Given metasfresh has date and time 2025-05-16T16:30:17+02:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_060 | postgRESTExportProductValue_S0475_060 | postgRESTExportProductName_S0475_060 | postgRESTExportProductDescription_S0475_060 | product_S0475_060_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_060 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_060 | test_S0475_060 |

    When metasfresh contains External System
      | Name                 | Value          |
      | TestSystem_S0475_060 | test_S0475_060 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_060_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_060  | externalId_S0475_060 | test_S0475_060       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId               |
      | ol_1       | o_1                   | product_S0475_060 | 100        | externalLineId_S0475_060 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0475_060_ID   | shipment_S0475_060_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "ShipmentDateGE",
      "value": "2025-05-16 00:00:00"
    },
    {
      "name": "ExternalSystemCode",
      "value": "test_S0475_060"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_060_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_060_DocumentNo@",
    "Shipment_Date": "2025-05-16T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_060",
    "ExternalSystemCode": "test_S0475_060",
    "DataSource": "int-test_S0475_060",
    "Order_ID": @order_S0475_060_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_060",
        "Product_ID": @product_S0475_060_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_060@",
        "ProductValue": "@postgRESTExportProductValue_S0475_060@"
      }
    ]
  }
]
    """

  @Id:S0475_070
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via BPartnerExternalSystemValue and BPartnerExternalReference

    Given metasfresh contains External System
      | Name           | Value          |
      | test_S0475_070 | test_S0475_070 |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference | C_BPartner_ID |
      | bpartner_ref                      | test_S0475_070 | BPartner | S0475_070         | customer1     |
    And metasfresh contains M_Products:
      | Identifier        | REST.Context.Value                    | REST.Context.Name                    | Description                                 | REST.Context         |
      | product_S0475_070 | postgRESTExportProductValue_S0475_070 | postgRESTExportProductName_S0475_070 | postgRESTExportProductDescription_S0475_070 | product_S0475_070_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_070 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_070 | test_S0475_070 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context       | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           | ExternalSystem.Value |
      | o_1        | order_S0475_070_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_070  | externalId_S0475_070 | test_S0475_070       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | ExternalId               |
      | ol_1       | o_1                   | product_S0475_070 | 100        | externalLineId_S0475_070 |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0475_070_ID   | shipment_S0475_070_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "BPartnerExternalReference",
      "value": "S0475_070"
    },
    {
      "name": "BPartnerExternalSystemValue",
      "value": "test_S0475_070"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_070_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_070_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_070",
    "ExternalSystemCode": "test_S0475_070",
    "DataSource": "int-test_S0475_070",
    "Order_ID": @order_S0475_070_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "BPartnerExternalReference": "S0475_070",
    "BPartnerExternalSystemValue": "test_S0475_070",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_070",
        "Product_ID": @product_S0475_070_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_070@",
        "ProductValue": "@postgRESTExportProductValue_S0475_070@"
      }
    ]
  }
]
    """

  @Id:S0475_080
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON via DocType_Base

    And metasfresh contains M_Products:
      | Identifier          | REST.Context.Value                      | REST.Context.Name                      | Description                                   | REST.Context           |
      | product_S0475_080_2 | postgRESTExportProductValue_S0475_080_2 | postgRESTExportProductName_S0475_080_2 | postgRESTExportProductDescription_S0475_080_2 | product_S0475_080_2_ID |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID        | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_080_2 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier             | InternalName     |
      | dataSource_S0475_080_2 | test_S0475_080_2 |

    When metasfresh contains External System
      | Name                   | Value            |
      | TestSystem_S0475_080_2 | test_S0475_080_2 |

    And metasfresh contains C_Orders:
      | Identifier | REST.Context         | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID  | ExternalId             | ExternalSystem.Value |
      | o_2        | order_S0475_080_2_ID | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_080_2 | externalId_S0475_080_2 | test_S0475_080_2     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID        | QtyEntered | ExternalId                 |
      | ol_2       | o_2                   | product_S0475_080_2 | 100        | externalLineId_S0475_080_2 |

    And the order identified by o_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_2                            | D            | true                | true        |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo         |
      | s_s_2                            | s_2                   | shipment_S0475_080_2_ID | shipment_S0475_080_2_DocumentNo |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_2        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "DocType_Base",
      "value": "MMS"
    },
    {
      "name": "ExternalSystemCode",
      "value": "test_S0475_080_2"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Shipment_ID": @shipment_S0475_080_2_ID@,
    "Shipment_DocumentNo": "@shipment_S0475_080_2_DocumentNo@",
    "Shipment_Date": "2025-05-15T00:00:00",
    "DocStatus": "CO",
    "ExternalId": "externalId_S0475_080_2",
    "ExternalSystemCode": "test_S0475_080_2",
    "DataSource": "int-test_S0475_080_2",
    "Order_ID": @order_S0475_080_2_ID@,
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
    "BPartnerValue": "@customer_value_S0475_010@",
    "Supplier": {
      "Name": "metasfresh AG",
      "Name2": null,
      "Value": "metasfresh"
    },
    "Supplier_Location": {
      "GLN": null,
      "City": "Bonn",
      "Postal": "53179",
      "Address1": "Am Nossbacher Weg 2",
      "Address2": null,
      "CountryCode": "DE"
    },
    "Buyer": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Buyer_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "Invoicee": {
      "Name": "@customer_name_S0475_010@",
      "Name2": null,
      "Value": "@customer_value_S0475_010@"
    },
    "Invoicee_Location": {
      "GLN": null,
      "City": null,
      "Postal": null,
      "Address1": null,
      "Address2": null,
      "CountryCode": "DE"
    },
    "DeliveryParty": null,
    "DeliveryParty_Location": null,
    "UltimateConsignee": null,
    "UltimateConsignee_Location": null,
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Lines": [
      {
        "UOM": "Stk",
        "LineNo": 10,
        "ExternalId": "externalLineId_S0475_080_2",
        "Product_ID": @product_S0475_080_2_ID@,
        "QtyEntered": 100,
        "ProductName": "@postgRESTExportProductName_S0475_080_2@",
        "ProductValue": "@postgRESTExportProductValue_S0475_080_2@"
      }
    ]
  }
]
    """
