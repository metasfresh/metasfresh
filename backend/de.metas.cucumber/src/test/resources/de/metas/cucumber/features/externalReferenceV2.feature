@from:cucumber
@ghActions:run_on_executor5
Feature: external references for metasfresh resources (V2)
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
    { "id": "1", "type": "IssueID" }
  ]
}
"""
    Then the metasfresh REST-API responds with
    """
{
  "items": [
    { "lookupItem":{"id":"1","type":"IssueID"} }
  ]
}
    """


  Scenario: some external resources are referenced to a metasfresh resource
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'POST' request with the payload
    """
{
  "systemName": "Github",
  "items": [
    { "lookupItem": { "id": "1", "type": "IssueID" }, "metasfreshId": 43 }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
  """
{
    "systemName": "Github",
    "items": [
        { "id": "2", "type": "IssueID" },
        { "id": "1", "type": "IssueID" },
        { "id": "3", "type": "IssueID" }
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
                    "id": "2"
                }
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "id": "1"
                },
                "metasfreshId": 43,
                "externalReference": "1",
                "systemName": "Github"
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "id": "3"
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
    { "lookupItem": { "id": "1", "type": "BPartner" }, "metasfreshId": 53 },
    { "lookupItem": { "id": "2", "type": "IssueID" }, "metasfreshId": 54 }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "GRSSignum",
  "items": [
    { "metasfreshId": 53, "type": "BPartner" },
    { "id": "2", "type": "IssueID" }
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
                    "id": "2"
                },
                "metasfreshId": 54,
                "externalReference": "2",
                "systemName": "GRSSignum"
            }
        ]
}
    """