@from:cucumber
Feature: Check renumber lines logic

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  Scenario: Renumber lines with bundle product first when orderLineGroup has ordering "GroupFirst"
    # Importing C_OLCand with the following sequence and groupMainItem (bundle) flag
    # - externalLineId18052022_1: line: 1, groupMainItem: false, belongs to externalLineId18052022_2 groupKey
    # - externalLineId18052022_2: line: 2, groupMainItem: true
    # - externalLineId18052022_3: line: 3, groupMainItem: none, does not belong to any group
    # - externalLineId18052022_4: line: 4, groupMainItem: false, belongs to externalLineId18052022_5 groupKey
    # - externalLineId18052022_5: line: 5, groupMainItem: true
    #
    # Expecting the following C_OrderLine sequence (the group compensation line being first from its group):
    # - externalLineId18052022_2: line: 10
    # - externalLineId18052022_1: line: 20, belongs to externalLineId18052022_2 groupKey
    # - externalLineId18052022_5: line: 30
    # - externalLineId18052022_4: line: 40, belongs to externalLineId18052022_5 groupKey
    # - externalLineId18052022_3: line: 50, does not belong to any group
    #
    Given metasfresh contains M_Products:
      | Identifier                | Name                      | IsStocked |
      | componentProduct_18052022 | componentProduct_18052022 | false     |
      | bundleProduct_18052022    | bundleProduct_18052022    | false     |
    And metasfresh contains M_PricingSystems
      | Identifier  | Name                         | Value                         | OPT.IsActive |
      | ps_18052022 | pricing_system_name_18052022 | pricing_system_value_18052022 | true         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_18052022 | ps_18052022                   | DE                        | EUR                 | price_list_name_18052022 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | plv_18052022 | pl_18052022               | salesOrder-PLV_18052022 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier          | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier   | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_componentProduct | plv_18052022                      | componentProduct_18052022 | 10.0     | PCE               | Normal                        |
      | pp_bundleProduct    | plv_18052022                      | bundleProduct_18052022    | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_18052022 | Endcustomer_18052022 | N            | Y              | ps_18052022                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN                | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_18052022 | bPLocation18052022 | endcustomer_18052022     | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
   "requests":[
      {
         "orgCode":"001",
         "externalLineId":"externalLineId18052022_1",
         "externalHeaderId":"externalHeaderId18052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation18052022",
            "bpartnerLocationIdentifier":"gln-bPLocation18052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-componentProduct_18052022",
         "qty":1,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":1,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId18052022_1",
            "groupMainItem":false,
            "ordering":"GroupFirst"
         },
         "description":"lineDescription2",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId18052022_2",
         "externalHeaderId":"externalHeaderId18052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation18052022",
            "bpartnerLocationIdentifier":"gln-bPLocation18052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-bundleProduct_18052022",
         "qty":10,
         "price":0,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":2,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId18052022_1",
            "groupMainItem":true,
            "discount":2,
            "ordering":"GroupFirst"
         },
         "description":"lineDescription",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId18052022_3",
         "externalHeaderId":"externalHeaderId18052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation18052022",
            "bpartnerLocationIdentifier":"gln-bPLocation18052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-componentProduct_18052022",
         "qty":20,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":3,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "description":"lineDescription5",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId18052022_4",
         "externalHeaderId":"externalHeaderId18052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation18052022",
            "bpartnerLocationIdentifier":"gln-bPLocation18052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-componentProduct_18052022",
         "qty":30,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":4,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId18052022_5",
            "groupMainItem":false,
            "ordering":"GroupFirst"
         },
         "description":"lineDescription4",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId18052022_5",
         "externalHeaderId":"externalHeaderId18052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation18052022",
            "bpartnerLocationIdentifier":"gln-bPLocation18052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-bundleProduct_18052022",
         "qty":40,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":5,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId18052022_5",
            "groupMainItem":true,
            "discount":4,
            "ordering":"GroupFirst"
         },
         "description":"lineDescription3",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      }
   ]
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "externalHeaderId18052022",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then validate order line allocated 'line'
      | ExternalLineId           | C_OLCand.Line | C_OrderLine.Line |
      | externalLineId18052022_1 | 1             | 20               |
      | externalLineId18052022_2 | 2             | 10               |
      | externalLineId18052022_3 | 3             | 50               |
      | externalLineId18052022_4 | 4             | 40               |
      | externalLineId18052022_5 | 5             | 30               |


  Scenario: Renumber lines with bundle product last(default behavior) when orderLineGroup has no ordering sent
    #  Importing C_OLCand with the following sequence and groupMainItem (bundle) flag
    # - externalLineId19052022_1: line: 1, groupMainItem: false, belongs to externalLineId19052022_2 groupKey
    # - externalLineId19052022_2: line: 2, groupMainItem: true
    # - externalLineId19052022_3: line: 3, groupMainItem: none, does not belong to any group
    # - externalLineId19052022_4: line: 4, groupMainItem: false, belongs to externalLineId19052022_5 groupKey
    # - externalLineId19052022_5: line: 5, groupMainItem: true
    #
    # Expecting the following C_OrderLine sequence (the group compensation line being first from its group):
    # - externalLineId19052022_1: line: 10, belongs to externalLineId19052022_2 groupKey
    # - externalLineId19052022_2: line: 20
    # - externalLineId19052022_4: line: 30, belongs to externalLineId19052022_5 groupKey
    # - externalLineId19052022_5: line: 40
    # - externalLineId19052022_3: line: 50, does not belong to any group
    #
    Given metasfresh contains M_Products:
      | Identifier                | Name                      | IsStocked |
      | componentProduct_19052022 | componentProduct_19052022 | false     |
      | bundleProduct_19052022    | bundleProduct_19052022    | false     |
    And metasfresh contains M_PricingSystems
      | Identifier  | Name                         | Value                         | OPT.IsActive |
      | ps_19052022 | pricing_system_name_19052022 | pricing_system_value_19052022 | true         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_19052022 | ps_19052022                   | DE                        | EUR                 | price_list_name_19052022 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | plv_19052022 | pl_19052022               | salesOrder-PLV_19052022 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier                | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier   | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_componentProduct       | plv_19052022                      | componentProduct_19052022 | 10.0     | PCE               | Normal                        |
      | pp_bundleProduct_19052022 | plv_19052022                      | bundleProduct_19052022    | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_19052022 | Endcustomer_19052022 | N            | Y              | ps_19052022                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN                | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_19052022 | bPLocation19052022 | endcustomer_19052022     | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
   "requests":[
      {
         "orgCode":"001",
         "externalLineId":"externalLineId19052022_1",
         "externalHeaderId":"externalHeaderId19052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation19052022",
            "bpartnerLocationIdentifier":"gln-bPLocation19052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-componentProduct_19052022",
         "qty":1,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":1,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId19052022_2",
            "groupMainItem":false
         },
         "description":"lineDescription2",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId19052022_2",
         "externalHeaderId":"externalHeaderId19052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation19052022",
            "bpartnerLocationIdentifier":"gln-bPLocation19052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-bundleProduct_19052022",
         "qty":10,
         "price":0,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":2,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId19052022_2",
            "groupMainItem":true,
            "discount":2
         },
         "description":"lineDescription",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId19052022_3",
         "externalHeaderId":"externalHeaderId19052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation19052022",
            "bpartnerLocationIdentifier":"gln-bPLocation19052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-componentProduct_19052022",
         "qty":20,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":3,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "description":"lineDescription5",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId19052022_4",
         "externalHeaderId":"externalHeaderId19052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation19052022",
            "bpartnerLocationIdentifier":"gln-bPLocation19052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-componentProduct_19052022",
         "qty":30,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":4,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId19052022_5",
            "groupMainItem":false
         },
         "description":"lineDescription4",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      },
      {
         "orgCode":"001",
         "externalLineId":"externalLineId19052022_5",
         "externalHeaderId":"externalHeaderId19052022",
         "dataSource":"int-Shopware",
         "dataDest":null,
         "bpartner":{
            "bpartnerIdentifier":"gln-bPLocation19052022",
            "bpartnerLocationIdentifier":"gln-bPLocation19052022"
         },
         "dateRequired":"2021-05-12",
         "orderDocType":"SalesOrder",
         "productIdentifier":"val-bundleProduct_19052022",
         "qty":40,
         "price":100,
         "currencyCode":"EUR",
         "discount":0,
         "poReference":"poReference",
         "dateCandidate":"2021-05-11",
         "line":5,
         "isManualPrice":true,
         "isImportedWithIssues":true,
         "deliveryViaRule":"S",
         "deliveryRule":"A",
         "orderLineGroup":{
            "groupKey":"externalLineId19052022_5",
            "groupMainItem":true,
            "discount":4
         },
         "description":"lineDescription3",
         "dateOrdered":"2021-05-08",
         "importWarningMessage":"The order had multiple shipping addresses, the process picked the last one!"
      }
   ]
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "externalHeaderId19052022",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then validate order line allocated 'line'
      | ExternalLineId           | C_OLCand.Line | C_OrderLine.Line |
      | externalLineId19052022_1 | 1             | 10               |
      | externalLineId19052022_2 | 2             | 20               |
      | externalLineId19052022_3 | 3             | 50               |
      | externalLineId19052022_4 | 4             | 30               |
      | externalLineId19052022_5 | 5             | 40               |
