@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor5
Feature: EDI order candidates — the parties of the order candidate end up on the order and the following documents

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    # clock 2026-11-27: the invoice candidates get DateToInvoice = dateRequired 2026-11-27, so they must be invoiceable at ship+invoice time
    # the candidates keep dateOrdered 2026-11-16
    And metasfresh has date and time 2026-11-27T09:00:00+01:00[Europe/Berlin]
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


  # ##########################################################################
  # ##########################################################################
  # ##########################################################################
  @Id:S34092_010
  Scenario: candidate invoice address of the same partner is kept on order, shipment, DESADV, invoice and INVOIC

    # one partner with market, regional and central location; market bills to regional, the partner in general bills to central
    Given metasfresh contains C_BPartners without locations:
      | Identifier | Value               | IsCustomer | M_PricingSystem_ID |
      | customer   | S34092_010_customer | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipTo | IsBillTo | IsShipToDefault | IsBillToDefault | Address1            | Postal | City         |
      | market     | 4000000010068 | customer      | true     | true     | Y               | Y               | Market street 010   | 4623   | Marketcity   |
      | regional   | 4000000010549 | customer      | true     | true     | N               | N               | Regional street 010 | 4623   | Regionalcity |
      | central    | 4000000010556 | customer      | true     | true     | N               | N               | Central street 010  | 4623   | Centralcity  |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer      | true                 | 4000000010068         |
    And metasfresh contains C_BP_Relations:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | C_BPartnerRelation_ID | C_BPartnerRelation_Location_ID | IsBillTo |
      | relMarket  | customer      | market                 | customer              | regional                       | Y        |
      | relPartner | customer      |                        | customer              | central                        | Y        |
    And metasfresh contains M_Products:
      | Identifier | Name                | GTIN          |
      | product1   | S34092_010_product1 | 4000000010151 |
      | product2   | S34092_010_product2 | 4000000010152 |
    # the DESADV of the EDI recipient needs the TU packing of each product
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                     | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | huPip_product1                     | PCE                   | 3008003                    | product1                | 10  | 2026-01-01 | false                  | false                 | S34092_010_product1 x 10 PCE | false                   | false                     |
      | huPip_product2                     | PCE                   | 3008003                    | product2                | 10  | 2026-01-01 | false                  | false                 | S34092_010_product2 x 10 PCE | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product1     | 5.00     | PCE      |
      | salesPLV               | product2     | 5.00     | PCE      |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
"""
{
    "orgCode": "001",
    "externalHeaderId": "S34092_010_BGM",
    "externalLineId": "1",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-4000000010068",
        "bpartnerLocationIdentifier": "gln-4000000010068"
    },
    "billBPartner": {
        "bpartnerIdentifier": "gln-4000000010068",
        "bpartnerLocationIdentifier": "gln-4000000010068"
    },
    "dropShipBPartner": {
        "bpartnerIdentifier": "gln-4000000010068",
        "bpartnerLocationIdentifier": "gln-4000000010068"
    },
    "productIdentifier": "gtin-4000000010151",
    "qty": 3,
    "dateOrdered": "2026-11-16",
    "dateRequired": "2026-11-27",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "currencyCode": "EUR",
    "deliveryRule": "F",
    "poReference": "S34092_010_BGM"
}
"""
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
"""
{
    "orgCode": "001",
    "externalHeaderId": "S34092_010_BGM",
    "externalLineId": "2",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-4000000010068",
        "bpartnerLocationIdentifier": "gln-4000000010068"
    },
    "billBPartner": {
        "bpartnerIdentifier": "gln-4000000010068",
        "bpartnerLocationIdentifier": "gln-4000000010068"
    },
    "dropShipBPartner": {
        "bpartnerIdentifier": "gln-4000000010068",
        "bpartnerLocationIdentifier": "gln-4000000010068"
    },
    "productIdentifier": "gtin-4000000010152",
    "qty": 3,
    "dateOrdered": "2026-11-16",
    "dateRequired": "2026-11-27",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "currencyCode": "EUR",
    "deliveryRule": "F",
    "poReference": "S34092_010_BGM"
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "S34092_010_BGM",
    "externalSystemCode": "Shopware6",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |

    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | C_BPartner_Location_ID | Bill_BPartner_ID | Bill_Location_ID | IsDropShip | IsUseHandOver_Location | DocStatus |
      | order_1    | customer      | market                 | customer         | market           | false      | false                  | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2026-11-16      | product1                | 3            | 3          | 3           | 5     | 0        | EUR          | true      |
      | ol_2                      | order_1               | 2026-11-16      | product2                | 3            | 3          | 3           | 5     | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | shipment_1            | market                            |
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier |
      | ic_1                              | ol_1                      | customer                        | market                          |
      | ic_2                              | ol_2                      | customer                        | market                          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | invoice_1               | market                            |
    And after not more than 300s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 180s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID |
      | s_s_1                            | shipment_1            | shipment_S34092_010_ID  |
    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus | REST.Context |
      | d_1                      | customer                 | order_1               | P                | d_1          |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@shipment_S34092_010_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[{"metasfresh_DESADV":{"Parties":{"Buyer_Location":{"GLN":"4000000010068"},"Invoicee_Location":{"GLN":"4000000010068"},"DeliveryParty_Location":{"GLN":"4000000010068"},"UltimateConsignee_Location":{"GLN":"4000000010068"}}}}]
    """

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context          |
      | ic_1                              | invoice_1               | invoice_S34092_010_ID |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/C_Invoice_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": "@invoice_S34092_010_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{"metasfresh_INVOIC":[{"Partners":[{"EANCOM_LocationType":"SU"},{"EANCOM_LocationType":"BY","GLN":"4000000010068"},{"EANCOM_LocationType":"IV","GLN":"4000000010068"},{"EANCOM_LocationType":"SN","GLN":"4000000010068"},{"EANCOM_LocationType":"DP","GLN":"4000000010068"}]}]}
    """


  # ##########################################################################
  # ##########################################################################
  # ##########################################################################
  @Id:S34092_020
  Scenario: order candidate without invoice address gets the invoice address of the order location's bill-to relation

    # one partner with market, regional and central location; market bills to regional, the partner in general bills to central
    Given metasfresh contains C_BPartners without locations:
      | Identifier | Value               | IsCustomer | M_PricingSystem_ID |
      | customer   | S34092_020_customer | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipTo | IsBillTo | IsShipToDefault | IsBillToDefault | Address1            | Postal | City         |
      | market     | 4000000020068 | customer      | true     | true     | Y               | Y               | Market street 020   | 4623   | Marketcity   |
      | regional   | 4000000020549 | customer      | true     | true     | N               | N               | Regional street 020 | 4623   | Regionalcity |
      | central    | 4000000020556 | customer      | true     | true     | N               | N               | Central street 020  | 4623   | Centralcity  |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer      | true                 | 4000000020068         |
    And metasfresh contains C_BP_Relations:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | C_BPartnerRelation_ID | C_BPartnerRelation_Location_ID | IsBillTo |
      | relMarket  | customer      | market                 | customer              | regional                       | Y        |
      | relPartner | customer      |                        | customer              | central                        | Y        |
    And metasfresh contains M_Products:
      | Identifier | Name               | GTIN          |
      | product    | S34092_020_product | 4000000020151 |
    # the DESADV of the EDI recipient needs the TU packing of the product
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name            | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | huPip                              | PCE                   | 3008003                    | product                 | 10  | 2026-01-01 | false                  | false                 | S34092_020 x 10 PCE | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
"""
{
    "orgCode": "001",
    "externalHeaderId": "S34092_020_BGM",
    "externalLineId": "1",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-4000000020068",
        "bpartnerLocationIdentifier": "gln-4000000020068"
    },
    "dropShipBPartner": {
        "bpartnerIdentifier": "gln-4000000020068",
        "bpartnerLocationIdentifier": "gln-4000000020068"
    },
    "productIdentifier": "gtin-4000000020151",
    "qty": 3,
    "dateOrdered": "2026-11-16",
    "dateRequired": "2026-11-27",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "currencyCode": "EUR",
    "deliveryRule": "F",
    "poReference": "S34092_020_BGM"
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "S34092_020_BGM",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | null                    |

    Then validate the created orders
      | C_Order_ID | C_BPartner_Location_ID | Bill_BPartner_ID | Bill_Location_ID | IsDropShip | DocStatus |
      | order_1    | market                 | customer         | regional         | false      | CO        |


  # ##########################################################################
  # ##########################################################################
  # ##########################################################################
  @Id:S34092_050
  Scenario: regional-warehouse order: delivery location differs from buyer

    # one partner with market, regional and central location; market bills to regional, the partner in general bills to central
    Given metasfresh contains C_BPartners without locations:
      | Identifier | Value               | IsCustomer | M_PricingSystem_ID |
      | customer   | S34092_050_customer | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipTo | IsBillTo | IsShipToDefault | IsBillToDefault | Address1            | Postal | City         |
      | market     | 4000000050068 | customer      | true     | true     | Y               | Y               | Market street 050   | 4623   | Marketcity   |
      | regional   | 4000000050549 | customer      | true     | true     | N               | N               | Regional street 050 | 4623   | Regionalcity |
      | central    | 4000000050556 | customer      | true     | true     | N               | N               | Central street 050  | 4623   | Centralcity  |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer      | true                 | 4000000050068         |
    And metasfresh contains C_BP_Relations:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | C_BPartnerRelation_ID | C_BPartnerRelation_Location_ID | IsBillTo |
      | relMarket  | customer      | market                 | customer              | regional                       | Y        |
      | relPartner | customer      |                        | customer              | central                        | Y        |
    And metasfresh contains M_Products:
      | Identifier | Name               | GTIN          |
      | product    | S34092_050_product | 4000000050151 |
    # the DESADV of the EDI recipient needs the TU packing of each product
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                    | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | huPip_product                      | PCE                   | 3008003                    | product                 | 10  | 2026-01-01 | false                  | false                 | S34092_050_product x 10 PCE | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
"""
{
    "orgCode": "001",
    "externalHeaderId": "S34092_050_BGM",
    "externalLineId": "1",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-4000000050068",
        "bpartnerLocationIdentifier": "gln-4000000050068"
    },
    "billBPartner": {
        "bpartnerIdentifier": "gln-4000000050068",
        "bpartnerLocationIdentifier": "gln-4000000050068"
    },
    "dropShipBPartner": {
        "bpartnerIdentifier": "gln-4000000050549",
        "bpartnerLocationIdentifier": "gln-4000000050549"
    },
    "productIdentifier": "gtin-4000000050151",
    "qty": 3,
    "dateOrdered": "2026-11-16",
    "dateRequired": "2026-11-27",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "currencyCode": "EUR",
    "deliveryRule": "F",
    "poReference": "S34092_050_BGM"
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "S34092_050_BGM",
    "externalSystemCode": "Shopware6",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |

    Then validate the created orders
      | C_Order_ID | C_BPartner_ID | C_BPartner_Location_ID | Bill_BPartner_ID | Bill_Location_ID | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID | IsUseHandOver_Location | DocStatus |
      | order_1    | customer      | market                 | customer         | market           | true       | customer             | regional             | false                  | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2026-11-16      | product                 | 3            | 3          | 3           | 5     | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | shipment_1            | regional                          |
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier |
      | ic_1                              | ol_1                      | customer                        | market                          |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | invoice_1               | market                            |
    And after not more than 300s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 180s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID |
      | s_s_1                            | shipment_1            | shipment_S34092_050_ID  |
    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus | REST.Context |
      | d_1                      | customer                 | order_1               | P                | d_1          |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@shipment_S34092_050_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[{"metasfresh_DESADV":{"Parties":{"Buyer_Location":{"GLN":"4000000050068"},"Invoicee_Location":{"GLN":"4000000050068"},"DeliveryParty_Location":{"GLN":"4000000050068"},"UltimateConsignee_Location":{"GLN":"4000000050549"}}}}]
    """

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | REST.Context          |
      | ic_1                              | invoice_1               | invoice_S34092_050_ID |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/C_Invoice_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": "@invoice_S34092_050_ID@"
    }
  ]
}
    """
    # DP = regional also proves HandOver_Location_ID is empty: the view prefers the order's hand-over location for DP (view 5812520 :112-124)
    Then the metasfresh REST-API responds with
    """
{"metasfresh_INVOIC":[{"Partners":[{"EANCOM_LocationType":"SU"},{"EANCOM_LocationType":"BY","GLN":"4000000050068"},{"EANCOM_LocationType":"IV","GLN":"4000000050068"},{"EANCOM_LocationType":"SN","GLN":"4000000050549"},{"EANCOM_LocationType":"DP","GLN":"4000000050549"}]}]}
    """
