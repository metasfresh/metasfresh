@from:cucumber
Feature: Check renumber lines logic

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: we can allow order discount via api (compensation group)
    Given metasfresh contains M_Products:
      | Identifier | Name                  | IsStocked |
      | p_60       | salesProduct_04152022 | false     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.Description                     | OPT.IsActive |
      | ps_60      | pricing_system_name_04152022 | pricing_system_value_04152022 | pricing_system_description_04152022 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_60      | ps_60                         | DE                        | EUR                 | price_list_name_04152022 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | plv_60     | pl_60                     | salesOrder-PLV_04152022 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_60      | plv_60                            | p_60                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier     | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_60 | Endcustomer_04152022 | N            | Y              | ps_60                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN                | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_60       | bPLocation04152022 | endcustomer_60           | true                | true         |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
  "requests": [
    {
      "orgCode": "001",
      "externalLineId": "externalLineId04152022",
      "externalHeaderId": "externalHeaderId04152022",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation04152022",
        "bpartnerLocationIdentifier": "gln-bPLocation04152022",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_04152022",
      "qty": 1,
      "price": 0,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "poReference",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-05-11",
      "line": 1,
      "isManualPrice": true,
      "isImportedWithIssues": true,
      "deliveryViaRule": "S",
      "deliveryRule": "A",
      "orderLineGroup": {
        "groupKey": "parentId04152022",
        "groupMainItem": true,
        "discount": 2
      },
      "description": "lineDescription",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    },
    {
      "orgCode": "001",
      "externalLineId": "externalLineId04152022_2",
      "externalHeaderId": "externalHeaderId04152022",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation04152022",
        "bpartnerLocationIdentifier": "gln-bPLocation04152022",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_04152022",
      "qty": 10,
      "price": 100,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "poReference",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-05-11",
      "line": 2,
      "isManualPrice": true,
      "isImportedWithIssues": true,
      "deliveryViaRule": "S",
      "deliveryRule": "A",
      "orderLineGroup": {
        "groupKey": "parentId04152022",
        "groupMainItem": false
      },
      "description": "lineDescription2",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    },
    {
      "orgCode": "001",
      "externalLineId": "externalLineId04152022_3",
      "externalHeaderId": "externalHeaderId04152022",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation04152022",
        "bpartnerLocationIdentifier": "gln-bPLocation04152022",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_04152022",
      "qty": 20,
      "price": 100,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "poReference",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-05-11",
      "line": 3,
      "isManualPrice": true,
      "isImportedWithIssues": true,
      "deliveryViaRule": "S",
      "deliveryRule": "A",
      "orderLineGroup": {
        "groupKey": "parentId04152022_2",
        "groupMainItem": true,
        "discount": 4
      },
      "description": "lineDescription3",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    },
    {
      "orgCode": "001",
      "externalLineId": "externalLineId04152022_5",
      "externalHeaderId": "externalHeaderId04152022",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation04152022",
        "bpartnerLocationIdentifier": "gln-bPLocation04152022",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_04152022",
      "qty": 40,
      "price": 100,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "poReference",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-05-11",
      "line": 5,
      "isManualPrice": true,
      "isImportedWithIssues": true,
      "deliveryViaRule": "S",
      "deliveryRule": "A",
      "description": "lineDescription5",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    },
    {
      "orgCode": "001",
      "externalLineId": "externalLineId04152022_4",
      "externalHeaderId": "externalHeaderId04152022",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation04152022",
        "bpartnerLocationIdentifier": "gln-bPLocation04152022",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_04152022",
      "qty": 30,
      "price": 100,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "poReference",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-05-11",
      "line": 4,
      "isManualPrice": true,
      "isImportedWithIssues": true,
      "deliveryViaRule": "S",
      "deliveryRule": "A",
      "orderLineGroup": {
        "groupKey": "parentId04152022_2",
        "groupMainItem": false
      },
      "description": "lineDescription4",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    }
  ]
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "externalHeaderId04152022",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then validate order line allocated 'line'
      | ExternalLineId           | C_OLCand.Line | C_OrderLine.Line |
      | externalLineId04152022   | 1             | 20               |
      | externalLineId04152022_2 | 2             | 10               |
      | externalLineId04152022_3 | 3             | 40               |
      | externalLineId04152022_4 | 4             | 30               |
      | externalLineId04152022_5 | 5             | 50               |