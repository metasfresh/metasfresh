@from:cucumber
Feature: Sales order API

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_sales   | ps_1               | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_sales  | pl_sales       |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_2       | plv_sales              | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |

  @from:cucumber
  Scenario: Revert Sales Order by Order ID via API
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | REST.Context | REST.Context.DocumentNo | C_BPartner_ID | DateOrdered |
      | so         | true    | orderId      | orderDocumentNo         | customer      | 2025-04-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 10         |
    And the order identified by so is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss1        | so_l1          | N             |

    When store sales order PDF endpointPath /api/v2/orders/sales/:so/revert in context

    And a 'PUT' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then the metasfresh REST-API responds with
    """
   {
    "salesOrderId": "@orderId@",
    "documentNo": "@orderDocumentNo@"
   }
"""
    And the sales order identified by 'so' is reversed

  @from:cucumber
  Scenario: Revert Sales Order by External ID and Input Data Source via API
    And metasfresh contains AD_InputDataSource:
      | Identifier              | InternalName      |
      | dataSource_09182025_010 | test_09182025_010 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | REST.Context | REST.Context.DocumentNo | AD_InputDataSource_ID   | ExternalId              | C_BPartner_ID | DateOrdered |
      | so         | true    | orderId      | orderDocumentNo         | dataSource_09182025_010 | externalId_09182025_010 | customer      | 2025-04-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 10         |
    And the order identified by so is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss1        | so_l1          | N             |

    When the metasfresh REST-API endpoint path '/api/v2/orders/sales/revert' receives a 'PUT' request with the payload
  """
{
  "orgCode": "001",
  "externalId": "externalId_09182025_010",
  "dataSource": "int-test_09182025_010"
}
"""
    Then the metasfresh REST-API responds with
    """
   {
    "salesOrderId": "@orderId@",
    "documentNo": "@orderDocumentNo@"
   }
"""
    And the sales order identified by 'so' is reversed

  @from:cucumber
  Scenario: Revert Sales Order by External ID and Input Data Source via API expecting exception because the order is delivered
    And metasfresh contains AD_InputDataSource:
      | Identifier              | InternalName      |
      | dataSource_09182025_020 | test_09182025_020 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | REST.Context | REST.Context.DocumentNo | AD_InputDataSource_ID   | ExternalId              | C_BPartner_ID | DateOrdered |
      | so         | true    | orderId      | orderDocumentNo         | dataSource_09182025_020 | externalId_09182025_020 | customer      | 2025-04-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 10         |
    And the order identified by so is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss1        | so_l1          | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss1                              | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss1                              | inOut                 |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API '/api/v2/orders/sales/revert' and fulfills with '422' status code
  """
{
  "orgCode": "001",
  "externalId": "externalId_09182025_020",
  "dataSource": "int-test_09182025_020"
}
"""
    Then the metasfresh REST-API responds with
    """
{
  "errors": [
    {
      "message": "Die Bestellung OrderId(repoId=@orderId@) enthält bereits gelieferte Artikel.",
      "errorCode": "MSG_ERR_ORDER_HAS_DELIVERED_ITEMS",
      "userFriendlyError": true,
      "stackTrace": "OrderService.reverseOrder:177 <~~~ SalesOrderRestController.revertOrder:231 <~~~ ApiAuditService.processRequestSync:500 <- ApiAuditService.processRequest:245 <- ApiAuditFilter.doFilter:115 <~~~ UserAuthTokenFilter.lambda$doFilter$1:66 <- UserAuthTokenService.lambda$asCallable$0:96 <- UserAuthTokenService.call:117 <- UserAuthTokenService.run:89 <- UserAuthTokenFilter.doFilter:61",
      "parameters": {
        "AD_Language": "<null>",
        "AD_Message": "MSG_ERR_ORDER_HAS_DELIVERED_ITEMS"
      },
      "orgCode": null,
      "sourceClassName": null,
      "sourceMethodName": null,
      "issueCategory": "OTHER"
    }
  ]
}
"""