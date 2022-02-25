@from:cucumber
@topic:invoiceAddress
Feature: Render invoice address
  Especially tracing the "BPartnerName" part of the address

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Berlin]
    And enable sys config 'SKIP_WP_PROCESSOR_FOR_AUTOMATION'

    And metasfresh contains M_Products:
      | Identifier        | Name              |
      | addr_test_product | addr_test_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName |
      | pp_product | plv_so                            | addr_test_product       | 10.0     | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier        | Name              | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName       |
      | customer_bpartner | customer_bpartner | ps_1                          | Y              | customer_bpartner_cmp |

  Scenario: Invoice when business partner location doesn't have BPartnerName
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | Address1 | Postal | City           |
      | location_1               | addr 11  | 123    | locationCity_1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.Name         |
      | bpLocation_1 | 1234567891111 | customer_bpartner        | location_1                   | true         | true         | locationNameTest |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 61081,
    "externalLineId": 1111,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567891111",
        "bpartnerLocationIdentifier": "gln-1234567891111"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-addr_test_product",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_61081",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "61081",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | Order.Identifier | Shipment.Identifier | Invoice.Identifier |
      | order_1          | shipment_1          | invoice_1          |

    And validate created order
      | Order.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference      | processed | docStatus |
      | order_1          | 61081      | customer_bpartner        | bpLocation_1                      | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_61081 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | Order.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | qtyordered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1          | 2022-02-02  | addr_test_product       | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |

    And validate created shipments
      | Shipment.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference      | processed | docStatus |
      | shipment_1          | customer_bpartner        | bpLocation_1                      | 2022-02-02  | olCand_ref_61081 | true      | CO        |

    And validate the created shipment lines
      | Shipment.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipment_1          | addr_test_product       | 1           | true      |

    And validate created invoices
      | Invoice.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus | OPT.BPartnerAddress                            |
      | invoice_1          | customer_bpartner        | bpLocation_1                      | olCand_ref_61081 | 1000002     | true      | CO        | customer_bpartner\naddr 11\n123 locationCity_1 |

    And validate created invoice lines
      | Invoice.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_1          | addr_test_product       | 1           | true      |


  Scenario: Invoice when business partner location has BPartnerName
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | Address1 | Postal | City           |
      | location_2               | addr 22  | 456    | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234567892222 | customer_bpartner        | location_2                   | true         | true         | locationBPName   | locationName |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 18231,
    "externalLineId": 2222,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892222",
        "bpartnerLocationIdentifier": "gln-1234567892222"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-addr_test_product",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_18231",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "18231",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | Order.Identifier | Shipment.Identifier | Invoice.Identifier |
      | order_1          | shipment_1          | invoice_1          |

    And validate created order
      | Order.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference      | processed | docStatus |
      | order_1          | 18231      | customer_bpartner        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_18231 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | Order.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | qtyordered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1          | 2022-02-02  | addr_test_product       | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |

    And validate created shipments
      | Shipment.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference      | processed | docStatus |
      | shipment_1          | customer_bpartner        | bpLocation_2                      | 2022-02-02  | olCand_ref_18231 | true      | CO        |

    And validate the created shipment lines
      | Shipment.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipment_1          | addr_test_product       | 1           | true      |

    And validate created invoices
      | Invoice.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         |
      | invoice_1          | customer_bpartner        | bpLocation_2                      | olCand_ref_18231 | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 |

    And validate created invoice lines
      | Invoice.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_1          | addr_test_product       | 1           | true      |


  Scenario: Invoice when origin order disposition has bpartnerName
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | Address1 | Postal | City           |
      | location_2               | addr 22  | 456    | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234567892223 | customer_bpartner        | location_2                   | true         | true         | locationBPName   | locationName |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 18232,
    "externalLineId": 2222,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892223",
        "bpartnerLocationIdentifier": "gln-1234567892223"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-addr_test_product",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_18232",
    "deliveryViaRule": "S",
    "deliveryRule": "F",
    "bpartnerName": "olCandBPartnerName"
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "18232",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | Order.Identifier | Shipment.Identifier | Invoice.Identifier |
      | order_1          | shipment_1          | invoice_1          |

    And validate created order
      | Order.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference      | processed | docStatus |
      | order_1          | 18232      | customer_bpartner        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_18231 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | Order.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | qtyordered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1          | 2022-02-02  | addr_test_product       | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |

    And validate created shipments
      | Shipment.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference      | processed | docStatus |
      | shipment_1          | customer_bpartner        | bpLocation_2                      | 2022-02-02  | olCand_ref_18232 | true      | CO        |

    And validate the created shipment lines
      | Shipment.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipment_1          | addr_test_product       | 1           | true      |

    And validate created invoices
      | Invoice.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus | OPT.BPartnerAddress                             |
      | invoice_1          | customer_bpartner        | bpLocation_2                      | olCand_ref_18232 | 1000002     | true      | CO        | olCandBPartnerName\naddr 22\n456 locationCity_2 |

    And validate created invoice lines
      | Invoice.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_1          | addr_test_product       | 1           | true      |