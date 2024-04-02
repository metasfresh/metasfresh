@from:cucumber
@topic:orderCandidate
@ghActions:run_on_executor6
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
  @Id:S0163_100
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
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000009                     | DE                       | 2000-04-01 |

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
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_TaxCategory_ID.Identifier | OPT.Price_UOM_ID.X12DE355 |
      | ol_1                      | order_1               | 2021-04-15      | p_1                     | 10           | 10         | 10          | 10    | 0        | EUR          | true      | 1000009                         | PCE                       |

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


  @from:cucumber
  @topic:orderCandidate
  @Id:S0163_200
  Scenario: Insert and process C_OlCand with manual price,stock uom and discount for product p1
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_08072022_1 | true      |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000009                     | DE                       | 2000-04-01 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_08072022_1",
    "externalHeaderId": "externalHeaderId_08072022_1",
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
    "productIdentifier": "val-noPriceProduct_08072022_1",
    "qty": 10,
    "uomCode": "PCE",
    "poReference": "po_ref_08072022_1",
    "line": 1,
    "isManualPrice": true,
    "currencyCode": "EUR",
    "price": 10,
    "discount": 10,
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "externalHeaderId_08072022_1",
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
      | order_1               | externalHeaderId_08072022_1 | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | SOO         | EUR          | F            | S               | po_ref_08072022_1 | true      | CL        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_TaxCategory_ID.Identifier | OPT.Price_UOM_ID.X12DE355 |
      | ol_1                      | order_1               | 2021-04-15      | p_1                     | 10           | 10         | 10          | 10    | 10       | EUR          | true      | 1000009                         | PCE                       |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference   | processed | docStatus |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | po_ref_08072022_1 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 10          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_08072022_1 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.C_TaxCategory_ID.Identifier | OPT.Discount |
      | invoiceLine_1_1             | invoice_1               | p_1                     | 10          | true      | 1000009                         | 10           |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0163_300
  Scenario: Insert and process C_OlCand with manual price and TU uom for product p1
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_08072022_2 | true      |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                |
      | packingTU             | packingTU_S0163_300 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingTUVersion              | packingTU             | packingVersionTU_S0163_300 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingTUVersion              | 0   | PM       |

    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | TU                   | 0.1          |

    And metasfresh contains M_HU_PI_Item_Product:
      | OPT.M_HU_PI_Item_Product_ID | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | 5010005                     | hu_pi_item_product_1               | PCE                   | huPiItemTU                 | p_1                     | 10  | 2020-04-01 | false                  | false                 | IFCO_Test_5 x 10 PCE | false                   |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000009                     | DE                       | 2000-04-01 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_08072022_2",
    "externalHeaderId": "externalHeaderId_08072022_2",
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
    "productIdentifier": "val-noPriceProduct_08072022_2",
    "qty": 10,
    "uomCode": "TU",
    "packingMaterialId": 5010005,
    "poReference": "po_ref_08072022_2",
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
    "externalHeaderId": "externalHeaderId_08072022_2",
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
      | order_1               | externalHeaderId_08072022_2 | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | SOO         | EUR          | F            | S               | po_ref_08072022_2 | true      | CL        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_TaxCategory_ID.Identifier | OPT.Price_UOM_ID.X12DE355 |
      | ol_1                      | order_1               | 2021-04-15      | p_1                     | 100          | 100        | 100         | 10    | 0        | EUR          | true      | 1000009                         | TU                        |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference   | processed | docStatus |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | po_ref_08072022_2 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_08072022_2 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.C_TaxCategory_ID.Identifier |
      | invoiceLine_1_1             | invoice_1               | p_1                     | 100         | true      | 1000009                         |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0163_600
  Scenario: Insert and process C_OlCand directly to C_Invoice with manual price and stock uom for product p1
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_08072022_3 | true      |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000009                     | DE                       | 2000-04-01 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_08072022_3",
    "externalHeaderId": "externalHeaderId_08072022_3",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.invoicecandidate",
    "bpartner": {
        "bpartnerIdentifier": "2156425",
        "bpartnerLocationIdentifier": "2205175",
        "contactIdentifier": null
    },
    "dateRequired": "2021-04-15",
    "dateOrdered": "2021-04-15",
    "dateCandidate": "2021-04-15",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-noPriceProduct_08072022_3",
    "qty": 10,
    "uomCode": "PCE",
    "poReference": "po_ref_08072022_3",
    "line": 1,
    "isManualPrice": true,
    "currencyCode": "EUR",
    "price": 10,
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId            |
      | invoice_candidate_1               | externalHeaderId_08072022_3 |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered |
      | invoice_candidate_1               | 10               | 10             | 10               |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |

    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_08072022_3 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.C_TaxCategory_ID.Identifier |
      | invoiceLine_1_1             | invoice_1               | p_1                     | 10          | true      | 1000009                         |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0163_900
  Scenario: Order candidate to shipment and invoice flow and closed order with Reduced Vat Tax Category matching on C_Country_ID
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_07072022_1 | true      |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000010                     | DE                       | 2000-04-01 |
      | ptc_2      | p_1                     | 1000009                     | AL                       | 2000-04-01 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_07072022_1",
    "externalHeaderId": "externalHeaderId_07072022_1",
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
    "productIdentifier": "val-noPriceProduct_07072022_1",
    "qty": 10,
    "uomCode": "PCE",
    "poReference": "po_ref_07072022_1",
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
    "externalHeaderId": "externalHeaderId_07072022_1",
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
      | order_1               | externalHeaderId_07072022_1 | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | SOO         | EUR          | F            | S               | po_ref_07072022_1 | true      | CL        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_TaxCategory_ID.Identifier | OPT.Price_UOM_ID.X12DE355 |
      | ol_1                      | order_1               | 2021-04-15      | p_1                     | 10           | 10         | 10          | 10    | 0        | EUR          | true      | 1000010                         | PCE                       |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference   | processed | docStatus |
      | shipment_1            | bpartner_1               | bpartnerLocation_1                | 2021-04-15  | po_ref_07072022_1 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | shipment_1            | p_1                     | 10          | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_07072022_1 | 10 Tage 1 % | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.C_TaxCategory_ID.Identifier |
      | invoiceLine_1_1             | invoice_1               | p_1                     | 10          | true      | 1000010                         |


  @from:cucumber
  @topic:orderCandidate
  @Id:S0163_700
  Scenario: Order candidate with error due to missing tax category
  Product doesn't have a M_ProductPrice
  There is a M_Product_TaxCategory for it but on a different country
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_07072022_2 | true      |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000010                     | ES                       | 2000-04-01 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_07072022_2",
    "externalHeaderId": "externalHeaderId_07072022_2",
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
    "productIdentifier": "val-noPriceProduct_07072022_2",
    "qty": 10,
    "uomCode": "PCE",
    "poReference": "po_ref_07072022_2",
    "line": 1,
    "isManualPrice": true,
    "currencyCode": "EUR",
    "price": 10,
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |

    And validate C_OLCand is with error
      | C_OLCand_ID.Identifier | ErrorMsg                             |
      | olCand_1               | Produkt ist nicht auf der Preisliste |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0163_800
  Scenario: Order candidate with error due to missing tax category
  Product doesn't have a M_ProductPrice
  There is a M_Product_TaxCategory for it but with ValidFrom in the future
    Given metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_07072022_3 | true      |

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156425           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205175                | bpartner_1               |

    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.CountryCode | ValidFrom  |
      | ptc_1      | p_1                     | 1000010                     | ES                       | 2023-04-01 |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "externalLineId_07072022_3",
    "externalHeaderId": "externalHeaderId_07072022_3",
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
    "productIdentifier": "val-noPriceProduct_07072022_3",
    "qty": 10,
    "uomCode": "PCE",
    "poReference": "po_ref_07072022_3",
    "line": 1,
    "isManualPrice": true,
    "currencyCode": "EUR",
    "price": 10,
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |

    And validate C_OLCand is with error
      | C_OLCand_ID.Identifier | ErrorMsg                             |
      | olCand_1               | Produkt ist nicht auf der Preisliste |
