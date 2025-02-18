@from:cucumber
@topic:orderCandidate
Feature: Process order candidate and automatically generate shipment and invoice for it, using a product without product price
  As a user
  I create an order candidate, using a product without product price
  and the process EP will automatically generate shipment schedule, shipment, invoice candidate and invoice

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to shipment and invoice flow and closed order
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_10052022_1 | true      |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.Identifier | ValidFrom  |
      | ptc_1      | p_1                     | 1000009                     | 101                     | 2000-04-01 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_10052022_1",
    "externalHeaderId": "externalHeaderId_10052022_1",
    "dataSource": "int-Shopware",
    "dataDest": null,
    "bpartner": {
        "bpartnerIdentifier": "2156425",
        "bpartnerLocationIdentifier": "2205175",
        "contactIdentifier": null
    },
    "dateRequired": "2021-04-15",
    "dateOrdered": "2021-04-15",
    "dateCandidate": "2021-04-15",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-noPriceProduct_10052022_1",
    "qty": 10,
    "uomCode": "PCE",
    "poReference": "po_ref_10052022_1",
    "line": 1,
    "isManualPrice": true,
    "currencyCode": "EUR",
    "price": 10,
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "externalHeaderId_10052022_1",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": true
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId              | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference       | processed | docStatus |
      | order_1               | externalHeaderId_10052022_1 | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | SOO         | EUR          | F            | S               | po_ref_10052022_1 | true      | CL        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_TaxCategory_ID.Identifier |
      | ol_1                      | order_1               | 2021-04-15      | p_1                     | 10           | 10         | 10          | 10    | 0        | EUR          | true      | 1000009                         |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference   | processed | docStatus |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | po_ref_10052022_1 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 10          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_10052022_1 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.C_TaxCategory_ID.Identifier |
      | invoiceLine_1_1             | invoice_1               | p_1                     | 10          | true      | 1000009                         |