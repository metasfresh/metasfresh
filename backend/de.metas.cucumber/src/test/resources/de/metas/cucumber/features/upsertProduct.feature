Feature:product create/update using metasfresh api
  As a REST-API invoker
  I want want to be able to upsert products

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And no product external reference with value '345' exists
    And no bpartner external reference with value '345' exists
    And no product with value 'code345' exists

  Scenario: create Product request

    Given the user adds external reference
      | systemname | externalId | externalReferenceType |
      | ALBERTA    | 345        | bpartner              |

    Given the user adds bpartner product
      | bpartnerIdentifier | OPT.isActive | OPT.seqNo | OPT.ProductNo | OPT.Description | OPT.EAN  | OPT.GTIN  | OPT.customerLabelName | OPT.ingredients |
      | ext-ALBERTA-345    | true         | 10        | test          | test            | ean_test | gtin_test | test                  | test            |

    Given the user adds product
      | productIdentifier | OPT.Code | Name         | Type | UOMCode | OPT.EAN  | OPT.GTIN  | OPT.Description  | OPT.isActive |
      | ext-ALBERTA-345   | code345  | Product_Test | ITEM | PCE     | ean_test | gtin_test | test_description | true         |


    And the request is set in context of syncAdvise 'CREATE_OR_MERGE'

    When the metasfresh REST-API endpoint path '/api/v2-pre/product/001' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then verify if data is persisted correctly for productValue 'code345'
    And verify if data is persisted correctly for type 'product' and external reference 'ext-ALBERTA-345'


