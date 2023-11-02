@from:cucumber
@ghActions:run_on_executor3
Feature: Allow order discount via API (compensation group)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: we can allow order discount via api (compensation group)
    Given metasfresh contains M_Products:
      | Identifier | Name            | IsStocked |
      | p_60       | salesProduct_60 | false     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.Description               | OPT.IsActive |
      | ps_60      | pricing_system_name_60 | pricing_system_value_60 | pricing_system_description_60 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_60      | ps_60                         | DE                        | EUR                 | price_list_name_60 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_60     | pl_60                     | salesOrder-PLV60 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_60      | plv_60                            | p_60                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier     | Name           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_60 | Endcustomer_60 | N            | Y              | ps_60                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_60       | bPLocation60 | endcustomer_60           | true                | true         |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
    """
{
  "requests": [
    {
      "orgCode": "001",
      "externalLineId": "externalLineId",
      "externalHeaderId": "externalHeaderId60",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation60",
        "bpartnerLocationIdentifier": "gln-bPLocation60",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_60",
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
        "groupKey": "parentId60",
        "groupMainItem": true,
		"discount": 2
      },
      "description": "lineDescription",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    },
    {
      "orgCode": "001",
      "externalLineId": "externalLineId2",
      "externalHeaderId": "externalHeaderId60",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-bPLocation60",
        "bpartnerLocationIdentifier": "gln-bPLocation60",
        "contactIdentifier": null
      },
      "dateRequired": "2021-05-12",
      "flatrateConditionsId": 0,
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-salesProduct_60",
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
        "groupKey": "parentId60",
        "groupMainItem": false
      },
      "description": "lineDescription2",
      "dateOrdered": "2021-05-08",
      "importWarningMessage": "The order had multiple shipping addresses, the process picked the last one!"
    }
  ]
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId60",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false,
  "closeOrder": false
}
"""
    Then the following group compensation order lines were created for externalHeaderId: 'externalHeaderId60'
      | Line | IsGroupCompensationLine | GroupCompensationPercentage | GroupCompensationType | GroupCompensationAmtType |
      | 20   | true                    | 2                           | D                     | P                        |
