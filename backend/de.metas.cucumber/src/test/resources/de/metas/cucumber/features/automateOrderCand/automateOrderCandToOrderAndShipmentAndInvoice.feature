@from:cucumber
@topic:orderCandidate
Feature: Process order candidate and automatically generate shipment and invoice for it
  As a user
  I create an order candidate and the process EP will automatically generate shipment schedule, shipment, invoice candidate and invoice

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And enable sys config 'SKIP_WP_PROCESSOR_FOR_AUTOMATION'

  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to shipment and invoice flow
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId",
    "externalHeaderId": "121412146801852",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "2156425",
        "bpartnerLocationIdentifier": "2205175",
        "contactIdentifier": "2188224"
    },
    "dateRequired": "2021-08-20",
    "dateOrdered": "2021-07-20",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": 2005577,
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "121412146801852",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true
}
"""

    Then process metasfresh response
      | Order.Identifier | Shipment.Identifier | Invoice.Identifier |
      | order_1          | shipment_1          | invoice_1          |

    And validate created order
      | Order.Identifier | externalId      | c_bpartner_id | c_bpartner_location_id | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed |
      | order_1          | 121412146801852 | 2156425       | 2205175                | 2021-07-20  | SOO         | EUR          | F            | S               | po_ref_mock | true      |

    And validate the created order lines
      | Order.Identifier | dateordered | productIdentifier.m_product_id | qty | price | discount | currencyCode | processed |
      | order_1          | 2021-07-20  | 2005577                        | 10  | 5     | 0        | EUR          | true      |

    And validate created shipments
      | Shipment.Identifier | c_bpartner_id | c_bpartner_location_id | dateordered | poreference | processed |
      | shipment_1          | 2156425       | 2205175                | 2021-07-20  | po_ref_mock | true      |

    And validate the created shipment lines
      | Shipment.Identifier | productIdentifier.m_product_id | qty | processed |
      | shipment_1          | 2005577                        | 10  | true      |

    And validate created invoices
      | Invoice.Identifier | c_bpartner_id | c_bpartner_location_id | poReference | paymentTerm | processed |
      | invoice_1          | 2156425       | 2205175                | po_ref_mock | 1000002     | true      |

    And validate created invoice lines
      | Invoice.Identifier | productIdentifier.m_product_id | qty | processed |
      | invoice_1          | 2005577                        | 10  | true      |
