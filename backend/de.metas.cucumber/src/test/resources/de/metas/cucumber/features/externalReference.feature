@from:cucumber
@ghActions:run_on_executor5
Feature: external references for metasfresh resources
  As a REST-API invoker
  I want want to query and update the external references for metasfresh resources like BPartners
  So that the resources 3rd party systems can be connected with metasfresh data

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: external resource is not referenced to a metasfresh resource
    When the metasfresh REST-API endpoint path '/api/externalRef/001' receives a 'PUT' request with the payload
  """
{
  "systemName": "Github",
  "items": [
    { "id": "noIssueWithThisId", "type": "IssueID" }
  ]
}
"""
    Then the metasfresh REST-API responds with
    """
{
  "items": [
    { "lookupItem":{"id":"noIssueWithThisId","type":"IssueID"} }
  ]
}
    """


  Scenario: some external resources are referenced to a metasfresh resource
    Given the metasfresh REST-API endpoint path '/api/externalRef/001' receives a 'POST' request with the payload
    """
{
  "systemName": "Github",
  "items": [
    { "lookupItem": { "id": "existingId", "type": "IssueID" }, "metasfreshId": 43 }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/externalRef/001' receives a 'PUT' request with the payload
  """
{
    "systemName": "Github",
    "items": [
        { "id": "noIssueWithThisId", "type": "IssueID" },
        { "id": "existingId", "type": "IssueID" },
        { "id": "againNoIssueWithThisId", "type": "IssueID" }
    ]
}
"""
    Then the metasfresh REST-API responds with
    """
{
    "items": [
        {
            "lookupItem": {
                "id": "noIssueWithThisId",
                "type": "IssueID"
            }
        },
        {
            "lookupItem": {
                "id": "existingId",
                "type": "IssueID"
            },
            "metasfreshId": 43,
            "systemName": "Github"
        },
        {
            "lookupItem": {
                "id": "againNoIssueWithThisId",
                "type": "IssueID"
            }
        }
    ]
}
    """
