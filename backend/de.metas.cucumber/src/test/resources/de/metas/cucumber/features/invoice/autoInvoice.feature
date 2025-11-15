@from:cucumber
@ghActions:run_on_executor4
Feature: Enqueue order candidate and auto invoice after shipped

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: Auto Invoice
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_sales    | ps_1               | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_sales    | pl_sales       |
    And metasfresh contains M_Products:
      | Identifier   | Name           |
      | autoInvoice1 | autoInvoice1   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_sales              | autoInvoice1 | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | Name         |
      | customer   | N        | Y          | ps_1               | autoInvoice1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | GLN          |
      | customerLocation | customer      | CH           | Y               | Y               | autoInvoice1 |


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "autoInvoice1",
            "externalLineId": "autoInvoice1_0",
            "externalSystemCode": "Other",
            "bpartner": {
                "bpartnerIdentifier": "val-autoInvoice1",
                "bpartnerLocationIdentifier": "gln-autoInvoice1"
            },
            "dateRequired": "2021-04-16",
            "dateOrdered": "2021-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-autoInvoice1",
            "qty": 2,
            "currencyCode": "CHF",
            "discount": 0,
            "poReference": "autoInvoice1",
            "deliveryViaRule": "S",
            "isAutoInvoice": true,
            "invoiceRule": "C"
        }
    ]
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "autoInvoice1",
    "externalSystemCode": "Other",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order1                |
    And validate the created orders
      | C_Order_ID.Identifier | externalId   |
      | order1                | autoInvoice1 |
    And validate the created order lines
      | Identifier   | C_Order_ID | M_Product_ID | QtyOrdered |
      | orderLine1_1 | order1     | autoInvoice1 | 2          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss1        | orderLine1_1   | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss1                   | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss1                   | shipment1  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | Identifier | C_OrderLine_ID | QtyToInvoice |
      | ic1        | orderLine1_1   | 2            |
    And AD_Scheduler for classname 'de.metas.invoicecandidate.process.C_Invoice_Candidate_Enqueue_AutoInvoice_Schedule' is ran once
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic1                    | invoice1     |
