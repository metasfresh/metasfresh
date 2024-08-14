@from:cucumber
@ghActions:run_on_executor7
Feature: Create or update using prices API
  Using default ad_orgId 1000000

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Create price list version from external ref
    Given metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | IsActive |
      | ps_1       | pricing_system_name_9 | pricing_system_value_9 | pricing_system_description_9 | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | CHF                 | price_list_name | null            | true  | false         | 2              | true         |
    When the user adds price list version data
      | Identifier  | OrgCode | M_PriceList_ID.Identifier | OPT.Description                | ValidFrom            | OPT.IsActive |
      | ext-Other-3 | 001     | pl_1                      | price_list_version_description | 2018-11-12T00:00:00Z | true         |
    And we create a JsonRequestPriceListVersionUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    And the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then price list version is persisted correctly

  @from:cucumber
  Scenario: Update price list version
    Given metasfresh contains M_PricingSystems
      | Identifier | Name                 | Value                 | OPT.Description            | IsActive |
      | ps_2       | pricing_system_name2 | pricing_system_value2 | pricing_system_description | true     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_2                          | DE                        | CHF                 | price_list_name | null            | true  | false         | 2              | true         |
    When the user adds price list version data
      | Identifier  | OrgCode | M_PriceList_ID.Identifier | OPT.Description            | ValidFrom            | OPT.IsActive |
      | ext-Other-3 | 001     | pl_1                      | price_list_version_updated | 2017-01-17T00:00:00Z | false        |
    And we create a JsonRequestPriceListVersionUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    When the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then price list version is persisted correctly

  @from:cucumber
  Scenario: Create product prices by price list version identifier
    Given metasfresh contains M_Products:
      | Identifier | Name             |
      | p_1        | salesProduct_100 |
    And the user adds product prices data
      | Identifier  | OrgCode | M_Product_ID.Identifier | PriceStd | OPT.PriceLimit | OPT.PriceList | SeqNo | OPT.IsActive | TaxCategory.InternalName |
      | ext-Other-4 | 001     | p_1                     | 10       | 20             | 30            | 10    | false        | NORMAL                   |
    And we create a JsonRequestProductPriceUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    When the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions/ext-Other-3/productPrices' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then product price is persisted correctly

  @from:cucumber
  Scenario: Update product prices by price list version identifier
    Given metasfresh contains M_Products:
      | Identifier | Name             |
      | p_1        | salesProduct_100 |
    When the user adds product prices data
      | Identifier  | OrgCode | M_Product_ID.Identifier | PriceStd | OPT.PriceLimit | OPT.PriceList | SeqNo | OPT.IsActive | TaxCategory.InternalName |
      | ext-Other-4 | 001     | p_1                     | 22       | 20             | 30            | 10    | true         | NORMAL                   |
    And we create a JsonRequestProductPriceUpsert, set syncAdvise to 'CREATE_OR_MERGE' and store request payload it in the test context
    When the metasfresh REST-API endpoint path 'api/v2/prices/priceListVersions/ext-Other-3/productPrices' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then product price is persisted correctly