@from:cucumber
@ghActions:run_on_executor3
Feature: create or update BPartner v1
  As a user
  I want create or update a BPartner record

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: create BPartner request
    Given the user adds bpartner
      | ExternalId | OPT.Code  | Name      | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url | OPT.Group  | OPT.VatId |
      | ext-001    | test_code | test_name | test_company    | null         | null      | de           | null    | test-group | null      |

    And the user adds locations
      | ExternalId | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln  | OPT.Postal |
      | ext-l11    | test_address1 | test_address2 | null       | null         | null        | null      | DE          | test_gln | null       |
      | ext-l22    | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | null     | null       |

    And the user adds contacts
      | ExternalId | Name          | OPT.Email  | OPT.Fax  |
      | ext-c11    | test_name_c11 | test_email | fax      |
      | ext-c22    | test_name_c22 | null       | test_fax |

    And the request is set in context for bpartnerId 'ext-001' and syncAdvise 'CREATE_OR_MERGE'
    When the metasfresh REST-API endpoint path 'api/v1/bpartner' receives a 'PUT' request with the payload from context and responds with '201' status code
    Then verify if data is persisted correctly for bpartnerId 'ext-001'