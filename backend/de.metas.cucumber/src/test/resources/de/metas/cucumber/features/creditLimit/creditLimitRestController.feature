@from:cucumber
Feature:credit limit delete using metasfresh api

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  @runThisOne
  Scenario: delete all credit limits for a given BPartner
    And metasfresh contains M_Products:
      | Identifier | Value        | Name        |
      | product    | ProductValue | ProductName |
    Given metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                   | OPT.Description               | OPT.IsActive |
      | ps_1       | hu_pricing_system_name | hu_pricing_system_value | hu_pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_price_list_name | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_1      | pl_1                      | hu_salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner   | BPartnerName | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_CreditLimit:
      | C_BPartner_CreditLimit_ID.Identifier | C_BPartner_ID.Identifier | Amount | ApprovedBy_ID | Processed | OPT.DateFrom |
      | creditLimit_1                        | bpartner                 | 12.5   | 100           | false     | 2022-10-31   |
      | creditLimit_2                        | bpartner                 | 9      | 100           | false     | 2022-08-15   |
      | creditLimit_3                        | bpartner                 | 4      | 100           | false     | 2022-10-01   |

    And store creditLimit endpointPath /api/v2/bpartner/credit-limit/001/:bpartner in context

    When a 'DELETE' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate no credit limit records found for: bpartner

