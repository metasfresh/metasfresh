@from:cucumber
@ghActions:run_on_executor5
Feature: Shipments export via postgREST

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
      | Identifier | Value                    | Name                    | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | customer_value_S0475_010 | customer_name_S0475_010 | Y          | N        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1     | Y               | Y               |


  @Id:S0475_010
  @from:cucumber
  Scenario: create a shipment and export it to JSON via UpdatedGE and InputDataSource

    Given metasfresh contains M_Products:
      | Identifier        | Value                       | Name                       | Description                       | REST.Context.M_Product_ID |
      | product_S0475_010 | postgRESTExportProductValue | postgRESTExportProductName | postgRESTExportProductDescription | product_S0475_010_ID      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_010 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_010 | test_S0475_010 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           |
      | o_1        | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_010  | externalId_S0475_010 |
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
      "name": "DataSource",
      "value": "int-test_S0475_010"
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
    "DataSource": "int-test_S0475_010",
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
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
      "Name": "customer_name_S0475_010",
      "Name2": null,
      "Value": "customer_value_S0475_010"
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
      "Name": "customer_name_S0475_010",
      "Name2": null,
      "Value": "customer_value_S0475_010"
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
        "ProductName": "postgRESTExportProductName",
        "ProductValue": "postgRESTExportProductValue"
      }
    ]
  }
]
    """

  @Id:S0475_020
  @from:cucumber
  Scenario: create a shipment and export it to JSON via externalId

    Given metasfresh contains M_Products:
      | Identifier        | Value                       | Name                       | Description                       | REST.Context.M_Product_ID |
      | product_S0475_020 | postgRESTExportProductValue | postgRESTExportProductName | postgRESTExportProductDescription | product_S0475_020_ID      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0475_020 | 5.00     | PCE      |
    And metasfresh contains AD_InputDataSource:
      | Identifier           | InternalName   |
      | dataSource_S0475_020 | test_S0475_020 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | AD_InputDataSource_ID | ExternalId           |
      | o_1        | true    | customer1     | 2025-04-17  | 2025-04-18Z  | dataSource_S0475_020  | externalId_S0475_020 |
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
    "DataSource": "int-test_S0475_020",
    "Order_Date": "2025-04-17T00:00:00",
    "Order_POReference": null,
    "DESADV_ID": null,
    "DeliveryViaRule": "P",
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
      "Name": "customer_name_S0475_020",
      "Name2": null,
      "Value": "customer_value_S0475_020"
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
      "Name": "customer_name_S0475_020",
      "Name2": null,
      "Value": "customer_value_S0475_020"
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
        "ProductName": "postgRESTExportProductName",
        "ProductValue": "postgRESTExportProductValue"
      }
    ]
  }
]
    """
