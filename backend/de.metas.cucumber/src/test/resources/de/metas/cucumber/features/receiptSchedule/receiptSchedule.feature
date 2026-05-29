@from:cucumber
@allure.label.epic:E0150_Material_Receipt
@allure.label.feature:F65010_Material_Receipt_Candidates
@F65010
@ghActions:run_on_executor6
Feature: create Material Receipt via REST API
## F65010: Receipt Schedule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156423           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205173                | bpartner_1               |

  @from:cucumber
@allure.label.epic:E0150_Material_Receipt
@allure.label.feature:F65010_Material_Receipt_Candidates
@F65010
  Scenario:  The material receipt is created via REST API, receipt schedule being identified by external ids
    Given a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/createCandidates' and fulfills with '200' status code
  """
{
   "purchaseCandidates":[
      {
         "orgCode":"001",
         "productIdentifier":"2005577",
         "vendor":{
            "bpartnerIdentifier":"2156423",
            "locationIdentifier":"2205173"
         },
         "warehouseIdentifier":"540008",
         "externalSystemCode": "Other",
         "externalHeaderId":"externalHeaderId_12082025_1",
         "externalLineId":"externalLineId_12082025_1",
         "poReference":"po_ref_12082025_1",
         "purchaseDatePromised":"2021-12-05T23:17:35.644Z",
         "purchaseDateOrdered":"2021-12-05T23:17:35.644Z",
         "qty":{
            "qty":10,
            "uomCode":"PCE"
         },
		 "isManualPrice": true,
		 "price": {
			"value": 10,
			"currencyCode": "EUR",
			"priceUomCode": "PCE"
		 }
      }
   ]
}
"""
    And a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/enqueueForOrdering' and fulfills with '202' status code
"""
{
  "purchaseCandidates": [
    {
      "externalSystemCode": "Other",
      "externalHeaderId": "externalHeaderId_12082025_1",
      "externalLineIds": [
        "externalLineId_12082025_1"
      ]
    }
  ]
}
"""
    And a PurchaseOrder with externalId 'externalHeaderId_12082025_1' is created after not more than 30 seconds and has values
      | C_Order_ID      |
      | purchaseOrder_1 |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | ExternalId                |
      | ol_1                      | purchaseOrder_1       | 2005577                 | 0            | 10         | 0           | 10    | 0        | EUR          | true      | PCE                   | PCE                       | externalLineId_12082025_1 |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | ExternalHeaderId            | ExternalLineId            |
      | receiptSchedule_1               | purchaseOrder_1       | ol_1                      | bpartner_1               | bpartnerLocation_1                | 2005577                 | 10         | warehouseStd              | externalHeaderId_12082025_1 | externalLineId_12082025_1 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/receipts' and fulfills with '200' status code
    """
{
  "receiptList": [
    {
      "externalSystemCode": "Other",
      "externalHeaderId": "externalHeaderId_12082025_1",
      "externalLineId": "externalLineId_12082025_1",
      "movementQuantity": 10
    }
  ]
}
"""
    Then process single receipt response
      | M_InOut_ID |
      | receipt_1  |

    And validate the created material receipt lines
      | M_InOut_ID | ExternalId                | M_Product_ID |
      | receipt_1  | externalLineId_12082025_1 | 2005577      |

  @from:cucumber
@allure.label.epic:E0150_Material_Receipt
@allure.label.feature:F65010_Material_Receipt_Candidates
@F65010
  Scenario:  The material receipt is created via REST API, receipt schedule being identified by orderLineId
    Given a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/createCandidates' and fulfills with '200' status code
  """
{
   "purchaseCandidates":[
      {
         "orgCode":"001",
         "productIdentifier":"2005577",
         "vendor":{
            "bpartnerIdentifier":"2156423",
            "locationIdentifier":"2205173"
         },
         "warehouseIdentifier":"540008",
         "externalSystemCode": "Other",
         "externalHeaderId":"externalHeaderId_12082025_2",
         "externalLineId":"externalLineId_12082025_2",
         "poReference":"po_ref_12082025_1",
         "purchaseDatePromised":"2021-12-05T23:17:35.644Z",
         "purchaseDateOrdered":"2021-12-05T23:17:35.644Z",
         "qty":{
            "qty":10,
            "uomCode":"PCE"
         },
		 "isManualPrice": true,
		 "price": {
			"value": 10,
			"currencyCode": "EUR",
			"priceUomCode": "PCE"
		 }
      }
   ]
}
"""
    And a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/enqueueForOrdering' and fulfills with '202' status code
"""
{
  "purchaseCandidates": [
    {
      "externalSystemCode": "Other",
      "externalHeaderId": "externalHeaderId_12082025_2",
      "externalLineIds": [
        "externalLineId_12082025_2"
      ]
    }
  ]
}
"""
    And a PurchaseOrder with externalId 'externalHeaderId_12082025_2' is created after not more than 30 seconds and has values
      | C_Order_ID      |
      | purchaseOrder_1 |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | REST.Context | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | ExternalId                |
      | ol_1                      | orderLineId  | purchaseOrder_1       | 2005577                 | 0            | 10         | 0           | 10    | 0        | EUR          | true      | PCE                   | PCE                       | externalLineId_12082025_2 |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | ExternalHeaderId            | ExternalLineId            |
      | receiptSchedule_1               | purchaseOrder_1       | ol_1                      | bpartner_1               | bpartnerLocation_1                | 2005577                 | 10         | warehouseStd              | externalHeaderId_12082025_2 | externalLineId_12082025_2 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/receipts' and fulfills with '200' status code
    """
{
  "receiptList": [
    {
      "orderLineId": @orderLineId@,
      "movementQuantity": 10
    }
  ]
}
"""
    Then process single receipt response
      | M_InOut_ID |
      | receipt_1  |

    And validate the created material receipt lines
      | M_InOut_ID | ExternalId                | M_Product_ID |
      | receipt_1  | externalLineId_12082025_2 | 2005577      |

  @from:cucumber
@allure.label.epic:E0150_Material_Receipt
@allure.label.feature:F65010_Material_Receipt_Candidates
@F65010
  Scenario:  The material receipt is created via REST API, receipt schedule being identified by receiptScheduleId
    Given a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/createCandidates' and fulfills with '200' status code
  """
{
   "purchaseCandidates":[
      {
         "orgCode":"001",
         "productIdentifier":"2005577",
         "vendor":{
            "bpartnerIdentifier":"2156423",
            "locationIdentifier":"2205173"
         },
         "warehouseIdentifier":"540008",
         "externalSystemCode": "Other",
         "externalHeaderId":"externalHeaderId_12082025_3",
         "externalLineId":"externalLineId_12082025_3",
         "poReference":"po_ref_12082025_1",
         "purchaseDatePromised":"2021-12-05T23:17:35.644Z",
         "purchaseDateOrdered":"2021-12-05T23:17:35.644Z",
         "qty":{
            "qty":10,
            "uomCode":"PCE"
         },
		 "isManualPrice": true,
		 "price": {
			"value": 10,
			"currencyCode": "EUR",
			"priceUomCode": "PCE"
		 }
      }
   ]
}
"""
    And a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/enqueueForOrdering' and fulfills with '202' status code
"""
{
  "purchaseCandidates": [
    {
      "externalSystemCode": "Other",
      "externalHeaderId": "externalHeaderId_12082025_3",
      "externalLineIds": [
        "externalLineId_12082025_3"
      ]
    }
  ]
}
"""
    And a PurchaseOrder with externalId 'externalHeaderId_12082025_3' is created after not more than 30 seconds and has values
      | C_Order_ID      |
      | purchaseOrder_1 |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 | ExternalId                |
      | ol_1                      | purchaseOrder_1       | 2005577                 | 0            | 10         | 0           | 10    | 0        | EUR          | true      | PCE                   | PCE                       | externalLineId_12082025_3 |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | REST.Context      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | ExternalHeaderId            | ExternalLineId            |
      | receiptSchedule_1               | receiptScheduleId | purchaseOrder_1       | ol_1                      | bpartner_1               | bpartnerLocation_1                | 2005577                 | 10         | warehouseStd              | externalHeaderId_12082025_3 | externalLineId_12082025_3 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/receipts' and fulfills with '200' status code
    """
{
  "receiptList": [
    {
      "receiptScheduleId": @receiptScheduleId@,
      "movementQuantity": 10
    }
  ]
}
"""

    Then process single receipt response
      | M_InOut_ID |
      | receipt_1  |

    And validate the created material receipt lines
      | M_InOut_ID | ExternalId                | M_Product_ID |
      | receipt_1  | externalLineId_12082025_3 | 2005577      |
    