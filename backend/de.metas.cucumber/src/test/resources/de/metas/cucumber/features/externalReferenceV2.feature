@from:cucumber
Feature: external references for metasfresh resources
  As a REST-API invoker
  I want want to query and update the external references for metasfresh resources like BPartners
  So that the resources 3rd party systems can be connected with metasfresh data

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: external resource is not referenced to a metasfresh resource
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
  """
{
  "systemName": "Github",
  "items": [
    { "externalReference": "1", "type": "IssueID" }
  ]
}
"""
    Then the metasfresh REST-API responds with
    """
{
  "items": [
    { "lookupItem":{"externalReference":"1","type":"IssueID"} }
  ]
}
    """


  Scenario: some external resources are referenced to a metasfresh resource
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'POST' request with the payload
    """
{
  "systemName": "Github",
  "items": [
    { "lookupItem": { "externalReference": "1", "type": "IssueID" }, "metasfreshId": 43 }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
  """
{
    "systemName": "Github",
    "items": [
        { "externalReference": "2", "type": "IssueID" },
        { "externalReference": "1", "type": "IssueID" },
        { "externalReference": "3", "type": "IssueID" }
    ]
}
"""
    Then the metasfresh REST-API responds with
    """
{
    "items": [
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "2"
                }
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "1"
                },
                "metasfreshId": 43,
                "externalReference": "1",
                "systemName": "Github"
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "3"
                }
            }
        ]
}
    """

  Scenario: some external resources are referenced to metasfresh resources and are searched by externalReference and metasfreshId
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'POST' request with the payload
    """
{
  "systemName": "GRSSignum",
  "items": [
    { "lookupItem": { "externalReference": "1", "type": "BPartner" }, "metasfreshId": 53 },
    { "lookupItem": { "externalReference": "2", "type": "IssueID" }, "metasfreshId": 54 }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "GRSSignum",
  "items": [
    { "metasfreshId": 53, "type": "BPartner" },
    { "externalReference": "2", "type": "IssueID" }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
    "items": [
            {
                "lookupItem": {
                    "metasfreshId": 53,
                    "type": "BPartner"
                },
                "metasfreshId": 53,
                "externalReference": "1",
                "systemName": "GRSSignum"
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "2"
                },
                "metasfreshId": 54,
                "externalReference": "2",
                "systemName": "GRSSignum"
            }
        ]
}
    """

  Scenario: upsert - a new externalReference record is inserted
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReference": "customer_so_25_01",
    "externalReferenceUrl": "https://example.com",
    "metasfreshId": 12345,
    "lookupItem": {
      "externalReference": "nosuchexistingpartner",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference | ExternalReferenceURL | OPT.Referenced_Record_ID |
      | Other          | BPartner | customer_so_25_01 | https://example.com  | 12345                    |


  Scenario: upsert - an existing externalReference record is updated
    Given metasfresh contains C_BPartners:
      | Identifier        | Name              | OPT.IsCustomer | OPT.CompanyName       | OPT.AD_Language |
      | customer_so_25_02 | customer_so_25_02 | Y              | customer_so_25_02_cmp | de_DE           |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference | OPT.C_BPartner_ID.Identifier |
      | externalRef_BPartner              | Other          | BPartner | 345               | customer_so_25_02            |

    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReference": "345-NEW",
    "externalReferenceUrl": "https://example.com",
    "lookupItem": {
      "externalReference": "345",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    Then the following S_ExternalReference is changed:
      | S_ExternalReference_ID.Identifier | OPT.ExternalReference | OPT.ExternalReferenceURL |
      | externalRef_BPartner              | 345-NEW               | https://example.com      |