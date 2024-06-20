@from:cucumber
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: Process order candidate and automatically generate shipment and invoice for it
  As a user
  I create an order candidate and the process EP will automatically generate shipment schedule, shipment, invoice candidate and invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And preexisting test data is put into tableData
      | C_BPartner_ID.Identifier | C_BPartner_ID | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | M_Product_ID.Identifier | M_Product_ID |
      | bpartner_1               | 2156425       | bpartnerLocation_1                | 2205175                | product_1               | 2005577      |


  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_100
  Scenario: Order candidate to shipment and invoice flow and closed order
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "1555",
    "externalHeaderId": "1444",
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
    "deliveryRule": "F",
    "bpartnerName": "testName"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "1444",
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
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.BPartnerName | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 1444           | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | SOO         | EUR          | F            | S               | po_ref_mock | true      | CL        | testName         | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2021-07-20      | product_1               | 10           | 10         | 10          | 5     | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 10          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1_1             | invoice_1               | product_1               | 10          | true      |


  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_110
  Scenario: Order candidate to shipment and invoice flow and unclosed order, with qtyShipped = 0!
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "1555_zeroQtyShipped",
    "externalHeaderId": "1444_zeroQtyShipped",
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
    "qtyShipped": 0,
    "bpartnerName": "testName"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "1444_zeroQtyShipped",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               |                       |                         |
    # We expect just an order and no shipment and no invoice. Thus the empty identifiers

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.BPartnerName | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 1444_zeroQtyShipped | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | SOO         | EUR          | A            | S               | po_ref_mock | true      | CO        | testName         | Shopware                               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ordereLine_1_1            | order_1               | 2021-07-20      | product_1               | 10         | 0            | 0           | 5     | 0        | EUR          | true      |
    # We didn't close the order, so we expect QtyOrdered=10

  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_120
  Scenario: Order candidate to shipment in first step and then invoice with close order
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "111",
    "externalHeaderId": "222",
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
    "deliveryRule": "F",
    "qtyShipped": 8,
    "bpartnerName": "testName"
}
"""
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "222",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | null                    |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.BPartnerName | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 222            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | SOO         | EUR          | F            | S               | po_ref_mock | true      | CO        | testName         | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2021-07-20      | product_1               | 8            | 10         | 0           | 5     | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 8           | true      |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "222",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": true,
    "closeOrder": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | invoice_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 222            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | SOO         | EUR          | F            | S               | po_ref_mock | true      | CL        | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2021-07-20      | product_1               | 8            | 8          | 8           | 5     | 0        | EUR          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1_1             | invoice_1               | product_1               | 8           | true      |


  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_130
  Scenario: Order candidate to complete order, then shipment endpoint to complete shipment, invoice and close shipment
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "333",
    "externalHeaderId": "444",
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
    "deliveryRule": "F",
    "bpartnerName": "testName"
}
"""
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "444",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | null                    |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.BPartnerName | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 444            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | SOO         | EUR          | F            | S               | po_ref_mock | true      | CO        | testName         | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2021-07-20      | product_1               | 0            | 10         | 0           | 5     | 0        | EUR          | true      |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
"""
{
    "createShipmentRequest": {
        "shipmentList": [
            {
                "shipmentScheduleIdentifier ": {
                    "externalHeaderId": "444",
                    "externalLineId": "333"
                },
                "movementDate": "2017-01-13T17:09:42.411",
                "location": {
                    "street": "street",
                    "houseNo": "houseNo",
                    "city": "city",
                    "countryCode": "DE",
                    "zipCode": "zipCode"
                },
                "businessPartnerSearchKey": "businessPartnerSearchKey",
                "attributes": [
                    {
                        "attributeCode": "1000020",
                        "valueStr": "1000020",
                        "valueNumber": 30,
                        "valueDate": "2021-04-03"
                    }
                ],
                "movementQuantity": 8,
                "deliveryRule": "F",
                "shipperInternalName": "shipperInternalName"
            }
        ]
    },
    "invoice": true,
    "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null                  | shipment_1            | invoice_1               |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 8           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1_1             | invoice_1               | product_1               | 8           | true      |


  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_140
  Scenario: Order candidate to complete order and partial shipment, then shipment endpoint to complete shipment, invoice and close shipment
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "8888",
    "externalHeaderId": "9999",
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
    "deliveryRule": "F",
    "qtyShipped": 8,
    "bpartnerName": "testName"
}
"""
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "9999",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | null                    |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.BPartnerName | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 9999           | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | SOO         | EUR          | F            | S               | po_ref_mock | true      | CO        | testName         | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | order_1               | 2021-07-20      | product_1               | 8            | 10         | 0           | 5     | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 8           | true      |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 10 seconds for order 'order_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
"""
{
    "createShipmentRequest": {
        "shipmentList": [
            {
                "shipmentScheduleIdentifier ": {
                    "externalHeaderId": "9999",
                    "externalLineId": "8888"
                },
                "movementDate": "2017-01-13T17:09:42.411",
                "location": {
                    "street": "street",
                    "houseNo": "houseNo",
                    "city": "city",
                    "countryCode": "DE",
                    "zipCode": "zipCode"
                },
                "businessPartnerSearchKey": "businessPartnerSearchKey",
                "attributes": [
                    {
                        "attributeCode": "1000020",
                        "valueStr": "1000020",
                        "valueNumber": 30,
                        "valueDate": "2021-04-03"
                    }
                ],
                "movementQuantity": 2,
                "deliveryRule": "F",
                "shipperInternalName": "shipperInternalName"
            }
        ]
    },
    "invoice": true,
    "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null                  | shipment_1,shipment_2 | invoice_1               |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 8           | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_2            | bpartner_1               | bpartnerLocation_1                | 2021-07-20  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_2            | product_1               | 2           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1_1             | invoice_1               | product_1               | 8           | true      |
      | invoiceLine_1_2             | invoice_1               | product_1               | 2           | true      |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_150
  Scenario: Order candidate to shipment and invoice flow and closed order for AUTO_SHIP_AND_INVOICE
    And set sys config boolean value true for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_1               | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_1               | inventoryLine_1               | 2005577                 | 0       | 100      | PCE          |
    And the inventory identified by inventory_1 is completed

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "33365",
    "externalHeaderId": "744777",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "2156425",
        "bpartnerLocationIdentifier": "2205175",
        "contactIdentifier": "2188224"
    },
    "dateRequired": "2021-10-20",
    "dateOrdered": "2021-10-13",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": 2005577,
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "A"
}
"""
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "744777",
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
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 744777         | bpartner_1               | bpartnerLocation_1                | 2021-10-13  | SOO         | EUR          | A            | S               | po_ref_mock | true      | CL        | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | c_ol_1                    | order_1               | 2021-10-13      | product_1               | 10           | 10         | 10          | 5     | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-10-13  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 10          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1_1             | invoice_1               | product_1               | 10          | true      |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0150_160
  Scenario: Order candidate to shipment in first step and then invoice with close order for AUTO_SHIP_AND_INVOICE
    And set sys config boolean value true for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_1               | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_1               | inventoryLine_1               | 2005577                 | 0       | 100      | PCE          |
    And the inventory identified by inventory_1 is completed

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "777",
    "externalHeaderId": "888",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "2156425",
        "bpartnerLocationIdentifier": "2205175",
        "contactIdentifier": "2188224"
    },
    "dateRequired": "2021-10-20",
    "dateOrdered": "2021-10-13",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": 2005577,
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "A",
    "qtyShipped": 8
}
"""
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "888",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | null                    |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 888            | bpartner_1               | bpartnerLocation_1                | 2021-10-13  | SOO         | EUR          | A            | S               | po_ref_mock | true      | CO        | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | c_ol_1                    | order_1               | 2021-10-13      | product_1               | 8            | 10         | 0           | 5     | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-10-13  | po_ref_mock     | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | product_1               | 8           | true      |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "888",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": true,
    "closeOrder": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | invoice_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 888            | bpartner_1               | bpartnerLocation_1                | 2021-10-13  | SOO         | EUR          | A            | S               | po_ref_mock | true      | CL        | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | c_ol_1                    | order_1               | 2021-10-13      | product_1               | 8            | 8          | 8           | 5     | 0        | EUR          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1_1             | invoice_1               | product_1               | 8           | true      |
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE