@from:cucumber
@topic:invoiceAddress
Feature: Compensation Group

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier         | Name               | IsStocked |
      | test_product_30_01 | test_product_30_01 | true      |
      | test_product_30_02 | test_product_30_02 | false     |
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
      | pp_product_01 | plv_so                            | test_product_30_01      | 10.0     | Normal                        | PCE               |
      | pp_product_02 | plv_so                            | test_product_30_02      | 5.0      | Normal                        | PCE               |

    # create bpartner with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_BPartners:
      | Identifier        | Name              | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName       | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | customer_bp_30_02 | customer_bp_30_02 | ps_1                          | Y              | customer_bp_30_02_cmp | I               | 1000002                    |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_2               | DE          | addr 22      | 456        | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234567890123 | customer_bp_30_02        | location_2                   | true         | true         | locationBPName   | locationName |

  Scenario: Compensated product is sent first
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
    """
{
  "requests": [
    {
      "orgCode": "001",
      "externalHeaderId": 12301,
      "externalLineId": 123011,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_30_01",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12301",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1"
      }
    },
    {
      "orgCode": "001",
      "externalHeaderId": 12301,
      "externalLineId": 123012,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_30_02",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12301",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1",
        "groupMainItem": true,
        "discount": 100
      }
    }
  ]
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "12301",
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
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus |
      | order_1               | customer_bp_30_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | ref_12301   | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02  | test_product_30_01      | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |
      | orderLine_2               | order_1               | 2022-02-02  | test_product_30_02      | 1            | 1          | 1           | 5.0   | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | shipment_1            | customer_bp_30_02        | bpLocation_2                      | 2022-02-02  | ref_12301   | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | line1                     | shipment_1            | test_product_30_01      | 1           | true      |
      | line2                     | shipment_1            | test_product_30_02      | 1           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         |
      | invoice_1               | customer_bp_30_02        | bpLocation_2                      | ref_12301       | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | il1                         | invoice_1               | test_product_30_01      | 1           | true      |
      | il2                         | invoice_1               | test_product_30_02      | 1           | true      |


  Scenario: Compensated product is sent last
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
    """
{
  "requests": [
    {
      "orgCode": "001",
      "externalHeaderId": 12302,
      "externalLineId": 123021,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_30_02",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12302",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1",
        "groupMainItem": true,
        "discount": 100
      }
    },
    {
      "orgCode": "001",
      "externalHeaderId": 12302,
      "externalLineId": 123022,
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1234567890123",
          "bpartnerLocationIdentifier": "gln-1234567890123"
      },
      "dateRequired": "2022-02-10",
      "dateOrdered": "2022-02-02",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_30_01",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12302",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "orderLineGroup" : {
        "groupKey": "group1"
      }
    }
  ]
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "12302",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_2               | shipment_2            | invoice_2               |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus |
      | order_2               | customer_bp_30_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | ref_12301   | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_2               | 2022-02-02  | test_product_30_01      | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |
      | orderLine_2               | order_2               | 2022-02-02  | test_product_30_02      | 1            | 1          | 1           | 5.0   | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | shipment_2            | customer_bp_30_02        | bpLocation_2                      | 2022-02-02  | ref_12302   | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | line1                     | shipment_2            | test_product_30_01      | 1           | true      |
      | lin32                     | shipment_2            | test_product_30_02      | 1           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         |
      | invoice_2               | customer_bp_30_02        | bpLocation_2                      | ref_12302       | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | il1                         | invoice_2               | test_product_30_01      | 1           | true      |
      | il2                         | invoice_2               | test_product_30_02      | 1           | true      |

