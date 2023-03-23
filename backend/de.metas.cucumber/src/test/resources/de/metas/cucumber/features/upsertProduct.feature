@from:cucumber
Feature:product create/update using metasfresh api
  As a REST-API invoker
  I want want to be able to upsert products

  Background:
    Given infrastructure and metasfresh are running
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And no product external reference with value '345' exists
    And no bpartner external reference with value '345' exists
    And no bpartner external reference with value '456' exists
    And no product with value 'code345' exists

  @from:cucumber
  Scenario: create Product request

    Given metasfresh contains S_ExternalReferences
      | ExternalSystem.Code | ExternalReference  | ExternalReferenceType.Code |
      | ALBERTA             | 345                | bpartner               |
      | ALBERTA             | 456                | bpartner               |

    And the user adds JsonRequestBpartnerProductUpsertItems
      | ProductCode  | bpartnerIdentifier | OPT.isActive | OPT.seqNo | OPT.ProductNo | OPT.Description | OPT.EAN  | OPT.GTIN  | OPT.customerLabelName | OPT.ingredients |
      | code345      | ext-ALBERTA-345    | true         | 10        | test          | test            | ean_test | gtin_test | test                  | test            |
      | code345      | ext-ALBERTA-456    | true         | 10        | test          | test            | ean_test | gtin_test | test                  | test            |

    And the user adds JsonRequestProductUpsertItems
      | productIdentifier | Code    | Name         | Type | uomCode | OPT.EAN  | OPT.GTIN  | OPT.Description  | OPT.isActive | OPT.ExternalReferenceURL     |
      | ext-ALBERTA-345   | code345 | Product_Test | ITEM | PCE     | ean_test | gtin_test | test_description | true         | www.ExternalReferenceURL.com |

    And we create a JsonRequestProductUpsert, set syncAdvise to 'CREATE_OR_MERGE', add the JsonRequestProductUpsertItems and store it in the test context

    When the metasfresh REST-API endpoint path 'api/v2/products/001' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then verify if data is persisted correctly for each product
    And verify if data is persisted correctly for type 'product' and external reference 'ext-ALBERTA-345'
    And verify that S_ExternalReference was created
      | ExternalSystem | Type    | ExternalReference | ExternalReferenceURL         |
      | ALBERTA        | Product | 345               | www.ExternalReferenceURL.com |