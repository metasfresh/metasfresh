@from:cucumber
@ghActions:run_on_executor6
Feature: Prices rest controller

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-09T13:30:13+01:00[Europe/Berlin]

  Scenario: Search product prices available for BPartnerIdentifier&ProductIdentifier at given date

    Given metasfresh contains M_Products:
      | Identifier | Value        | Name        |
      | product_1  | productValue | productName |

    And metasfresh contains M_PricingSystems
      | Identifier      | Name              | Value              | OPT.IsActive |
      | pricingSystem_1 | pricingSystemName | pricingSystemValue | true         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name            | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_1 | pricingSystem_1               | DE                        | EUR                 | priceListName_1 | true  | false         | 2              | true         |
      | priceList_2 | pricingSystem_1               | CH                        | CHF                 | priceListName_2 | true  | true          | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier         | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | priceListVersion_1 | priceList_1               | priceListVersionName_1 | 2022-05-01 |
      | priceListVersion_2 | priceList_2               | priceListVersionName_2 | 2022-05-15 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_1 | priceListVersion_1                | product_1               | 10.0     | PCE               | Normal                        |
      | productPrice_2 | priceListVersion_2                | product_1               | 15.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | BPartnerName | N            | Y              | pricingSystem_1               |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference   | ExternalReferenceType.Code | RecordId.Identifier |
      | LeichUndMehl        | productExternalRef  | Product                    | product_1           |
      | LeichUndMehl        | bpartnerExternalRef | BPartner                   | bpartner_1          |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/prices/001/product/search' and fulfills with '200' status code
    """
 {
      "bpartnerIdentifier": "ext-LeichUndMehl-bpartnerExternalRef",
      "productIdentifier": "ext-LeichUndMehl-productExternalRef",
      "targetDate": "2022-05-10"
 }
    """

    Then validate JsonResponseProductPriceQuery.JsonPriceListResponse
      | M_PriceList_ID.Identifier | JsonPriceListResponse.Name | JsonPriceListResponse.PricePrecision | JsonPriceListResponse.CountryCode | JsonPriceListResponse.CurrencyCode | JsonPriceListResponse.IsSOTrx |
      | priceList_1               | priceListName_1            | 2                                    | DE                                | EUR                                | SALES                         |

    And validate JsonResponseProductPriceQuery.JsonPriceListResponse.JsonPriceListVersionResponse
      | M_PriceList_Version_ID.Identifier | M_PriceList_ID.Identifier | JsonPriceListVersionResponse.ValidFrom |
      | priceListVersion_1                | priceList_1               | 2022-05-01                             |

    And validate JsonResponseProductPriceQuery.JsonPriceListResponse.JsonPriceListVersionResponse.JsonResponsePrice
      | M_PriceList_Version_ID.Identifier | M_PriceList_ID.Identifier | M_Product_ID.Identifier | JsonResponsePrice.Value | JsonResponsePrice.PriceStd | C_TaxCategory_ID.InternalName |
      | priceListVersion_1                | priceList_1               | product_1               | productValue            | 10.0                       | Normal                        |
