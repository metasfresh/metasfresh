@from:cucumber
@topic:auction
Feature: Auction

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Bucharest]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier         | Name               | IsStocked |
      | test_product_40_01 | test_product_40_01 | true      |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product_01 | plv_so                            | test_product_40_01      | 10.0     | Normal                        | PCE               |

    # create bpartner with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_BPartners:
      | Identifier        | Name              | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName       | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | customer_bp_40_02 | customer_bp_40_02 | ps_1                          | Y              | customer_bp_40_02_cmp | I               | 1000002                    |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_2               | DE          | addr 22      | 456        | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234567890123 | customer_bp_40_02        | location_2                   | true         | true         | locationBPName   | locationName |
    And metasfresh contains C_Auction:
      | Identifier | Name     | Date       |
      | Auction_1  | Auction1 | 2022-01-20 |
      | Auction_2  | Auction2 | 2022-01-20 |

  Scenario: 2 OLCAnds are created with same C_Auction_ID, resulting in single Order->IC->Invoice. C_Auction_ID is pushed through all these entities.
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
    """
{
  "requests": [
    {
      "orgCode": "001",
      "externalHeaderId": 14242,
      "externalLineId": 142421,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_40_01",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12301",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1"
      },
      "auction": "Auction1"
    },
    {
      "orgCode": "001",
      "externalHeaderId": 14242,
      "externalLineId": 142422,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_40_01",
      "qty": 2,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12301",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1"
      },
      "auction": "Auction1"
    }
  ]
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "14242",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.C_Auction_ID_Identifier |
      | order_1               | customer_bp_40_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | ref_12301   | true      | CO        | Auction_1                   |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02  | test_product_40_01      | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |
      | orderLine_2               | order_1               | 2022-02-02  | test_product_40_01      | 2            | 2          | 2           | 10.0  | 0        | EUR          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         | OPT.C_Auction_ID_Identifier |
      | invoice_1               | customer_bp_40_02        | bpLocation_2                      | ref_12301       | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 | Auction_1                   |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il1                         | invoice_1               | test_product_40_01      | 1           | true      |
      | il2                         | invoice_1               | test_product_40_01      | 2           | true      |


  Scenario: 2 OLCAnds are created with different C_Auction_IDs, resulting in 2 Orders->ICs->Invoices. C_Auction_ID is pushed through all these entities.
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
    """
{
  "requests": [
    {
      "orgCode": "001",
      "externalHeaderId": 14243,
      "externalLineId": 142431,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_40_01",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12302",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1"
      },
      "auction": "Auction1"
    },
    {
      "orgCode": "001",
      "externalHeaderId": 14243,
      "externalLineId": 142432,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_40_01",
      "qty": 2,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12302",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1"
      },
      "auction": "Auction2"
    }
  ]
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "14243",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_2               | shipment_2            | invoice_2               |
      | order_3               | shipment_3            | invoice_3               |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.C_Auction_ID_Identifier |
      | order_2               | customer_bp_40_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | ref_12301   | true      | CO        | Auction_1                   |
      | order_3               | customer_bp_40_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | ref_12301   | true      | CO        | Auction_2                   |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_2               | 2022-02-02  | test_product_40_01      | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |
      | orderLine_2               | order_3               | 2022-02-02  | test_product_40_01      | 2            | 2          | 2           | 20.0  | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | shipment_2            | customer_bp_40_02        | bpLocation_2                      | 2022-02-02  | ref_12302   | true      | CO        |
      | shipment_3            | customer_bp_40_02        | bpLocation_2                      | 2022-02-02  | ref_12302   | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | line1                     | shipment_2            | test_product_40_01      | 1           | true      |
      | line2                     | shipment_3            | test_product_40_01      | 2           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         | OPT.C_Auction_ID_Identifier |
      | invoice_2               | customer_bp_40_02        | bpLocation_2                      | ref_12302       | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 | Auction_1                   |
      | invoice_3               | customer_bp_40_02        | bpLocation_2                      | ref_12302       | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 | Auction_2                   |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il1                         | invoice_2               | test_product_40_01      | 1           | true      |
      | il2                         | invoice_3               | test_product_40_01      | 2           | true      |

