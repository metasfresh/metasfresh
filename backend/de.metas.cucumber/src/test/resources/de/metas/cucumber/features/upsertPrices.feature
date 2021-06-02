Feature: Create or update using prices API
  Using default ad_orgId 1000000

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: Create price list version from external ref
    Given metasfresh contains M_PricingSystems
      | M_PricingSystem_ID | Name                | Value                | OPT.Description            | IsActive |
      | 1                  | pricing_system_name | pricing_system_value | pricing_system_description | true     |
    And metasfresh contains M_PriceLists
      | M_PriceList_ID | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | 2              | 1                  | DE                        | CHF                 | price_list_name | null            | true  | false         | 2              | true     |
    When the user adds price list version data
      | Identifier  | OrgCode | M_PriceList_ID | OPT.Description                | ValidFrom            | OPT.IsActive |
      | ext-Other-3 | 001     | 2              | price_list_version_description | 2018-11-12T00:00:00Z | true         |
    And we create a JsonRequestPriceListVersionUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    And the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then price list version is persisted correctly

  Scenario: Update price list version
    When the user adds price list version data
      | Identifier  | OrgCode | M_PriceList_ID | OPT.Description            | ValidFrom            | OPT.IsActive |
      | ext-Other-3 | 001     | 2              | price_list_version_updated | 2017-01-17T00:00:00Z | false        |
    And we create a JsonRequestPriceListVersionUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    When the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then price list version is persisted correctly

  Scenario: Create product prices by price list version identifier
    And metasfresh contains M_Product with M_Product_ID '123'
    And the user adds product prices data
      | Identifier  | OrgCode | M_Product_ID | PriceStd | OPT.PriceLimit | OPT.PriceList | SeqNo | OPT.IsActive | TaxCategory.InternalName |
      | ext-Other-4 | 001     | 123          | 10       | 20             | 30            | 10    | false        | NORMAL                   |
    And we create a JsonRequestProductPriceUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    When the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions/ext-Other-3/productPrices' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then product price is persisted correctly

  Scenario: Update product prices by price list version identifier
    When the user adds product prices data
      | Identifier  | OrgCode | M_Product_ID | PriceStd | OPT.PriceLimit | OPT.PriceList | SeqNo | OPT.IsActive | TaxCategory.InternalName |
      | ext-Other-4 | 001     | 123          | 22       | 20             | 30            | 10    | true         | NORMAL                   |
    And we create a JsonRequestProductPriceUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    When the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions/ext-Other-3/productPrices' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then product price is persisted correctly