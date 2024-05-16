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
