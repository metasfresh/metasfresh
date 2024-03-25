@from:cucumber
Feature: external references for metasfresh resources
  As a REST-API invoker
  I want want to query and update the external references for metasfresh resources like BPartners
  So that the resources 3rd party systems can be connected with metasfresh data

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @S0403
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

  @S0403
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

  @S0403
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

  @S0401
  Scenario: upsert - a new externalReference record is inserted
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReference": "to_be_ignored_because_the_lookupItem_ref_takes_precedence",
    "externalReferenceUrl": "https://example.com",
    "metasfreshId": 20240322,
    "lookupItem": {
      "externalReference": "externalReference_S0401",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference       | ExternalReferenceURL | OPT.Referenced_Record_ID |
      | Other          | BPartner | externalReference_S0401 | https://example.com  | 20240322                 |

  @S0402
  Scenario: upsert - an existing externalReference record is updated
    Given metasfresh contains C_BPartners:
      | Identifier     | Name           | OPT.IsCustomer | OPT.CompanyName    | OPT.AD_Language |
      | customer_S0402 | customer_S0402 | Y              | customer_S0402_cmp | de_DE           |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference       | OPT.C_BPartner_ID.Identifier |
      | externalRef_BPartner_S0402        | Other          | BPartner | externalReference_S0402 | customer_S0402               |

    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReference": "externalReference_S0402-NEW",
    "externalReferenceUrl": "https://example.com",
    "lookupItem": {
      "externalReference": "externalReference_S0402",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    Then verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference           | ExternalReferenceURL |
      | Other          | BPartner | externalReference_S0402-NEW | https://example.com  |

    # now omit the property "externalReferenceUrl" => expect it to be unchanged
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReference": "externalReference_S0402-NEW_2",
    "lookupItem": {
      "externalReference": "externalReference_S0402-NEW",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    Then verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference             | ExternalReferenceURL |
      | Other          | BPartner | externalReference_S0402-NEW_2 | https://example.com  |

    # now set the property "externalReferenceUrl" explicitly to null => expect it to be null
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReferenceUrl": null,
    "lookupItem": {
      "externalReference": "externalReference_S0402-NEW_2",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    Then verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference             | ExternalReferenceURL |
      | Other          | BPartner | externalReference_S0402-NEW_2 | null                 | 