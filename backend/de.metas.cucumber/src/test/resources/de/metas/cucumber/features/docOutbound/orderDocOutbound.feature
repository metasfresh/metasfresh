@from:cucumber
@topic:orderDocOutbound
Feature: Validate order doc outbound log creation
  Especially tracing "C_Doc_Outbound_Log.CurrentEMailAddress"

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And enable sys config 'SKIP_WP_PROCESSOR_FOR_AUTOMATION'

    And metasfresh contains M_Products:
      | Identifier        | Name              |
      | addr_test_product | addr_test_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName |
      | pp_product | plv_so                            | addr_test_product       | 10.0     | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | sale_bpartner | sale_bpartner | ps_1                          | Y              |

  Scenario: Create sales order and validate email from doc outbound log - from order disposition
    Given metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.EMail          |
      | bpLocation | 1111123456789 | sale_bpartner            | true         | true         | location@email.com |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name   | OPT.EMail      | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | bpUser                | bpUser | user@email.com | sale_bpartner                | bpLocation                            |
      | bpUser                | bpUser | user@email.com | sale_bpartner                | bpLocation                            |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type   | ExternalReference | OPT.AD_User_ID.Identifier |
      | bpUser_ref                        | Shopware6      | UserID | bpUser_ref        | bpUser                    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 1128101,
    "externalLineId": 1111,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1111123456789",
        "bpartnerLocationIdentifier": "gln-1111123456789",
        "contactIdentifier": "ext-Shopware6-bpUser_ref"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-addr_test_product",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_1128101",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.EMail                   |
      | bpLocation                        | bpLocationUpdated@email.com |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "1128101",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | Order.Identifier | Shipment.Identifier | Invoice.Identifier |
      | order_1          | null                | null               |
    Then validate created order
      | Order.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference        | processed | docStatus | OPT.EMail          |
      | order_1          | 1128101    | sale_bpartner            | bpLocation                        | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_1128101 | true      | CO        | location@email.com |

    And validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.CurrentEMailAddress | OPT.C_BPartner_ID.Identifier | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLog                 | order_1              | C_Order       | location@email.com      | sale_bpartner                | SOO             | CO            |

    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLogLine                  | orderOutboundLog                 | order_1              | C_Order       | SOO             | CO            |


  Scenario: Create sales order and validate email from doc outbound log - from business partner contact
    Given metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpLocation | 2222223456789 | sale_bpartner            | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name             | OPT.EMail           | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | bpUser                | bpUser_secondary | secondary@email.com | sale_bpartner                | bpLocation                            |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type   | ExternalReference   | OPT.AD_User_ID.Identifier |
      | bpUserSecondary_ref               | Shopware6      | UserID | bpUserSecondary_ref | bpUser                    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 2085101,
    "externalLineId": 2222,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-2222223456789",
        "bpartnerLocationIdentifier": "gln-2222223456789",
        "contactIdentifier": "ext-Shopware6-bpUserSecondary_ref"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-addr_test_product",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_2085101",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "2085101",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    And process metasfresh response
      | Order.Identifier | Shipment.Identifier | Invoice.Identifier |
      | order_1          | null                | null               |
    Then validate created order
      | Order.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference        | processed | docStatus | OPT.EMail |
      | order_1          | 2085101    | sale_bpartner            | bpLocation                        | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_2085101 | true      | CO        | null      |

    And validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.CurrentEMailAddress | OPT.C_BPartner_ID.Identifier | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLog                 | order_1              | C_Order       | secondary@email.com     | sale_bpartner                | SOO             | CO            |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLogLine                  | orderOutboundLog                 | order_1              | C_Order       | SOO             | CO            |

