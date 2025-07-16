@from:cucumber
@ghActions:run_on_executor5
Feature: check invoice candidates status

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2021-12-21T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Generate invoice from order and validate check invoice candidate status EP
    Given a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "ExtLine_1",
    "externalHeaderId": "ExtHeader_1",
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
    "qty": 5,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
   """

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code

   """
{
    "externalHeaderId": "ExtHeader_1",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": true
}
   """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | i_c_1                             | ExtHeader_1      |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | i_c_1                             |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/status' and fulfills with '200' status code

   """
{
  "invoiceCandidates": [
      {
         "externalHeaderId": "ExtHeader_1"
      }
  ]
}
   """
    Then validate invoice candidate status response
      | ExternalHeaderId | ExternalLineId | QtyEntered | QtyToInvoice | QtyInvoiced | Processed |
      | ExtHeader_1      | ExtLine_1      | 5          | 0            | 5           | true      |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/status' and fulfills with '200' status code

   """
{
  "invoiceCandidates": [
      {
         "externalHeaderId": "ExtHeader_1",
         "orgCode": "001",
         "orderDocumentType":
         {
            "docBaseType": "SOO",
            "docSubType": "SO"
         }         
      }
  ]
}
   """
    Then validate invoice candidate status response
      | ExternalHeaderId | ExternalLineId | QtyEntered | QtyToInvoice | QtyInvoiced | Processed |
      | ExtHeader_1      | ExtLine_1      | 5          | 0            | 5           | true      |

  @from:cucumber
  Scenario: Generate invoice candidate from order and validate that checking the invoice candidate status works via order document number
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE      |
      | pp_2       | plv_1                  | p_2          | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocumentNo    | ExternalId           |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | test_07162025 | ExtHeader_07162025_2 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | ExternalId         |
      | ol_1       | o_1        | p_1          | 10         | ExtLine_07162025_2 |
      | ol_2       | o_1        | p_2          | 5          | ExtLine_07162025_3 |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/status' and fulfills with '200' status code

   """
{
  "invoiceCandidates": [
      {
         "orderDocumentNo": "test_07162025"         
      }
  ]
}
   """
    Then validate invoice candidate status response
      | ExternalHeaderId     | ExternalLineId     | QtyEntered | QtyToInvoice | QtyInvoiced | Processed |
      | ExtHeader_07162025_2 | ExtLine_07162025_2 | 10         | 0            | 0           | false     |
      | ExtHeader_07162025_2 | ExtLine_07162025_3 | 5          | 0            | 0           | false     |

  @from:cucumber
  Scenario: Generate invoice candidate from order and validate that checking the invoice candidate status works via order document number and order line
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE      |
      | pp_2       | plv_1                  | p_2          | 10.0     | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocumentNo      | ExternalId           |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | test_07162025_2 | ExtHeader_07162025_3 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | ExternalId         |
      | ol_1       | o_1        | p_1          | 10         | ExtLine_07162025_4 |
      | ol_2       | o_1        | p_2          | 5          | ExtLine_07162025_5 |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/status' and fulfills with '200' status code

   """
{
  "invoiceCandidates": [
      {
         "orderDocumentNo": "test_07162025_2",
         "orderLines": [ 20 ]
      }
  ]
}
   """
    Then validate invoice candidate status response
      | ExternalHeaderId     | ExternalLineId     | QtyEntered | QtyToInvoice | QtyInvoiced | Processed |
      | ExtHeader_07162025_3 | ExtLine_07162025_5 | 5          | 0            | 0           | false     |