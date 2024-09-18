@from:cucumber
@ghActions:run_on_executor6
Feature: create Purchase Candidate having manual price set
  As a user
  I want to create a Purchase Candidate record with manual price set and a product without product price

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario:  The purchase candidate is created with manual price, on a product which does not have a product price
    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | bpartner_1               | 2156423           |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | C_BPartner_ID.Identifier |
      | bpartnerLocation_1                | 2205173                | bpartner_1               |
    And metasfresh contains M_Products:
      | Identifier | Name                      | IsStocked |
      | p_1        | noPriceProduct_17052022_1 | true      |
    And metasfresh contains M_Product_TaxCategory:
      | Identifier | M_Product_ID.Identifier | C_TaxCategory_ID.Identifier | C_Country_ID.Identifier | ValidFrom  |
      | ptc_1      | p_1                     | 1000009                     | 101                     | 2000-04-01 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/createCandidates' and fulfills with '200' status code
  """
{
   "purchaseCandidates":[
      {
         "orgCode":"001",
         "productIdentifier":"val-noPriceProduct_17052022_1",
         "vendor":{
            "bpartnerIdentifier":"2156423",
            "locationIdentifier":"2205173"
         },
         "warehouseIdentifier":"540008",
         "externalPurchaseOrderUrl": "www.PurchaseNoProductPrice.com",
         "externalHeaderId":"externalHeaderId_17052022_1",
         "externalLineId":"externalLineId_17052022_1",
         "poReference":"po_ref_17052022_1",
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
      "externalHeaderId": "externalHeaderId_17052022_1",
      "externalLineIds": [
        "externalLineId_17052022_1"
      ]
    }
  ]
}
"""
    And a PurchaseOrder with externalId 'externalHeaderId_17052022_1' is created after not more than 30 seconds and has values
      | ExternalPurchaseOrderURL       | POReference       | OPT.C_Order_ID.Identifier |
      | www.PurchaseNoProductPrice.com | po_ref_17052022_1 | purchaseOrder_1           |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | ol_1                      | purchaseOrder_1       | p_1                     | 0            | 10         | 0           | 10    | 0        | EUR          | true      | PCE                   | PCE                       |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_1               | purchaseOrder_1       | ol_1                      | bpartner_1               | bpartnerLocation_1                | p_1                     | 10         | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_1               | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_1               | inOut_1               |

    And after not more than 30s locate invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtToInvoice |
      | invoice_candidate_1               | bpartner_1                  | p_1                     | 100                 |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference       | paymentTerm   | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | po_ref_17052022_1 | 30 Tage netto | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            |