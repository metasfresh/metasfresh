@from:cucumber
@topic:orderCandidate
Feature: Process order candidate and validate created order

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | Name               |
      | product    | product_12_07_2022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | priceListVersionSOName | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product_1 | plv_1                             | product                 | 10.0     | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier        | Name                         | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | bpartner          | bpartner_12_07_2022          | ps_1                          | Y              |
      | bill_bpartner     | bill_bpartner_12_07_2022     | ps_1                          | Y              |
      | dropShip_bpartner | dropShip_bpartner_12_07_2022 | ps_1                          | Y              |
      | handOver_bpartner | handOver_bpartner_12_07_2022 | ps_1                          | Y              |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1         | OPT.Postal |
      | location_1               | DE          | addrLocation_1       | 456        |
      | location_2               | DE          | addrLocation_2       | 457        |
      | bill_location            | DE          | addrBillLocation     | 2011       |
      | dropShip_location        | DE          | addrDropShipLocation | 1209       |
      | handOver_location        | DE          | addrHandOverLocation | 4556       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpLocation_1       | 1234567892280 | bpartner                 | location_1                   | true         | true         |
      | bpLocation_2       | 1234567892282 | bpartner                 | location_2                   | true         | true         |
      | billBPLocation     | 1234567892284 | bill_bpartner            | bill_location                | true         | true         |
      | dropShipBPLocation | 1234567892286 | dropShip_bpartner        | dropShip_location            | true         | true         |
      | handOverBPLocation | 1234567892288 | handOver_bpartner        | handOver_location            | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | OPT.C_BPartner_ID.Identifier | Name                    | OPT.EMail      |
      | contact_1             | bpartner                     | contact1_partner_name   | contact1@email |
      | contact_2             | bpartner                     | contact2_partner_name   | contact2@email |
      | contact_3             | bill_bpartner                | contact3_bill_name      | contact3@email |
      | contact_4             | dropShip_bpartner            | contact4_dropShip_name  | contact4@email |
      | contact_5             | handOver_bpartner            | contact5_handOver__name | contact5@email |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | ExternalReference | Type   | OPT.AD_User_ID.Identifier |
      | s_externalRef_1                   | Shopware6      | 200               | UserID | contact_1                 |
      | s_externalRef_2                   | Shopware6      | 250               | UserID | contact_2                 |
      | s_externalRef_3                   | Shopware6      | 300               | UserID | contact_3                 |
      | s_externalRef_4                   | Shopware6      | 350               | UserID | contact_4                 |
      | s_externalRef_5                   | Shopware6      | 400               | UserID | contact_5                 |


  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order - different dropShip partner&location&contact
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
 """
{
    "orgCode": "001",
    "externalLineId": "Test_111_1",
    "externalHeaderId": "Test_111_1",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "dropShipBPartner":{
        "bpartnerIdentifier": "gln-1234567892286",
        "bpartnerLocationIdentifier": "gln-1234567892286",
        "contactIdentifier": "ext-Shopware6-350"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_1",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.DropShip_Partner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.DropShip_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | order_1               | Test_111_1 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | dropShip_bpartner                  | dropShipBPLocation                  | contact_4                       | bpartner                        | bpLocation_1                    | contact_1                   |


  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order - same dropShip partner but different location&contact
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
 """
{
    "orgCode": "001",
    "externalLineId": "Test_111_2",
    "externalHeaderId": "Test_111_2",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "dropShipBPartner":{
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892282",
        "contactIdentifier": "ext-Shopware6-250"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_2",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.DropShip_Partner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.DropShip_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | order_1               | Test_111_2 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | bpartner                           | bpLocation_2                        | contact_2                       | bpartner                        | bpLocation_1                    | contact_1                   |


  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order with BPartner
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "Test_111_3",
    "externalHeaderId": "Test_111_3",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_3",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | order_1               | Test_111_3 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | bpartner                        | bpLocation_1                    | contact_1                   |


  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order - different bill partner&location&contact
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "Test_111_4",
    "externalHeaderId": "Test_111_4",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "billBPartner":{
        "bpartnerIdentifier": "gln-1234567892284",
        "bpartnerLocationIdentifier": "gln-1234567892284",
        "contactIdentifier": "ext-Shopware6-300"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_4",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | order_1               | Test_111_4 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | bill_bpartner                   | billBPLocation                  | contact_3                   |

  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order - different handOver partner&location&contact
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
 """
{
    "orgCode": "001",
    "externalLineId": "Test_111_5",
    "externalHeaderId": "Test_111_5",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "handOverBPartner":{
        "bpartnerIdentifier": "gln-1234567892288",
        "bpartnerLocationIdentifier": "gln-1234567892288",
        "contactIdentifier": "ext-Shopware6-400"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_5",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.HandOver_Partner_ID.Identifier | OPT.HandOver_Location_ID.Identifier | OPT.HandOver_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier |
      | order_1               | Test_111_5 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | handOver_bpartner                  | handOverBPLocation                  | contact_5                       | bpartner                        | bpLocation_1                    | contact_1                   |


  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order - different dropShip&handOver partners
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
 """
{
    "orgCode": "001",
    "externalLineId": "Test_111_6",
    "externalHeaderId": "Test_111_6",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "handOverBPartner":{
        "bpartnerIdentifier": "gln-1234567892288",
        "bpartnerLocationIdentifier": "gln-1234567892288",
        "contactIdentifier": "ext-Shopware6-400"
    },
    "dropShipBPartner":{
        "bpartnerIdentifier": "gln-1234567892286",
        "bpartnerLocationIdentifier": "gln-1234567892286",
        "contactIdentifier": "ext-Shopware6-350"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_6",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.DropShip_Partner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.DropShip_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.HandOver_Partner_ID.Identifier | OPT.HandOver_Location_ID.Identifier | OPT.HandOver_User_ID.Identifier |
      | order_1               | Test_111_6 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | dropShip_bpartner                  | dropShipBPLocation                  | contact_4                       | bpartner                        | bpLocation_1                    | contact_1                   | handOver_bpartner                  | handOverBPLocation                  | contact_5                       |


  @from:cucumber
  @topic:orderCandidate
  Scenario: Order candidate to order - different bill&dropShip&handOver partners
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
 """
{
    "orgCode": "001",
    "externalLineId": "Test_111_7",
    "externalHeaderId": "Test_111_7",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892280",
        "bpartnerLocationIdentifier": "gln-1234567892280",
        "contactIdentifier": "ext-Shopware6-200"
    },
    "billBPartner":{
        "bpartnerIdentifier": "gln-1234567892284",
        "bpartnerLocationIdentifier": "gln-1234567892284",
        "contactIdentifier": "ext-Shopware6-300"
    },
    "dropShipBPartner":{
        "bpartnerIdentifier": "gln-1234567892286",
        "bpartnerLocationIdentifier": "gln-1234567892286",
        "contactIdentifier": "ext-Shopware6-350"
    },
    "handOverBPartner":{
        "bpartnerIdentifier": "gln-1234567892288",
        "bpartnerLocationIdentifier": "gln-1234567892288",
        "contactIdentifier": "ext-Shopware6-400"
    },
    "dateRequired": "2022-07-20",
    "dateOrdered": "2022-07-11",
    "orderDocType": "SalesOrder",
    "productIdentifier": "val-product_12_07_2022",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "Test_111_7",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | dateordered | docbasetype | deliveryViaRule | deliveryRule | currencyCode | poReference | processed | docStatus | OPT.DropShip_Partner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.DropShip_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.HandOver_Partner_ID.Identifier | OPT.HandOver_Location_ID.Identifier | OPT.HandOver_User_ID.Identifier |
      | order_1               | Test_111_7 | bpartner                 | bpLocation_1                      | contact_1                 | 2022-07-11  | SOO         | P               | A            | EUR          | po_ref_mock | true      | CO        | dropShip_bpartner                  | dropShipBPLocation                  | contact_4                       | bill_bpartner                   | billBPLocation                  | contact_3                   | handOver_bpartner                  | handOverBPLocation                  | contact_5                       |
