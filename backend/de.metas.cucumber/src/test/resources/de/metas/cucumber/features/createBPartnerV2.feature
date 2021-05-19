Feature: create or update BPartner v2
  As a user
  I want create or update a BPartner record

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: create BPartner request
    Given the user adds v2 bpartner
      | OPT.Code  | Name      | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url | OPT.Group  | OPT.VatId |
      | test_code | test_name | test_company    | null         | null      | de           | null    | test-group | null      |
    And the user adds v2 locations
      | locationIdentifier | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln | OPT.Postal |
      | gln-l11            | test_address1 | test_address2 | null       | null         | null        | null      | DE          | null    | null       |
      | gln-l22            | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | null    | null       |
    And the user adds v2 contacts
      | bpartnerIdentifier | contactIdentifier | Name          | OPT.Email  | OPT.Fax  | Code |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11 | test_email | fax      | c11  |
      | ext-ALBERTA-001    | ext-ALBERTA-c22   | test_name_c22 | null       | test_fax | c22  |
    And the request is set in context for v2 bpartnerId 'ext-ALBERTA-001' and syncAdvise 'CREATE_OR_MERGE'
    When the metasfresh REST-API endpoint path 'api/v2-pre/bpartner/001' receives a 'PUT' request with the payload from context and responds with '201' status code
    Then verify that bPartner was created for externalIdentifier
      | externalIdentifier | OPT.Code  | Name      | OPT.CompanyName | OPT.ParentId | OPT.Phone | OPT.Language | OPT.Url | OPT.Group  | OPT.VatId |
      | ext-ALBERTA-001    | test_code | test_name | test_company    | null         | null      | de           | null    | test-group | null      |
    And verify that location was created for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.Address1  | OPT.Address2  | OPT.PoBox  | OPT.District | OPT.Region  | OPT.City  | CountryCode | OPT.Gln | OPT.Postal |
      | ext-ALBERTA-001    | gln-l11            | test_address1 | test_address2 | null       | null         | null        | null      | DE          | l11     | null       |
      | ext-ALBERTA-001    | gln-l22            | null          | test_address2 | test_poBox | null         | test_region | test_city | DE          | l22     | null       |
    And verify that contact was created for bpartner
      | bpartnerIdentifier | contactIdentifier | Name          | OPT.Email  | OPT.Fax  | Code |
      | ext-ALBERTA-001    | ext-ALBERTA-c11   | test_name_c11 | test_email | fax      | c11  |
      | ext-ALBERTA-001    | ext-ALBERTA-c22   | test_name_c22 | null       | test_fax | c22  |