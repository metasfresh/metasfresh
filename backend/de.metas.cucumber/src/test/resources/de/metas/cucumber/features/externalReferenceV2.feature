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
    { "id": "Not_Referenced_GitHubIssue_v2_290922_1", "type": "IssueID" }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "items": [
    { "lookupItem":{"id":"Not_Referenced_GitHubIssue_v2_290922_1","type":"IssueID"} }
  ]
}
    """


  Scenario: some external resources are referenced to a metasfresh resource
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'POST' request with the payload
    """
{
  "systemName": "Github",
  "items": [
    { "lookupItem": { "id": "Referenced_GitHubIssue_v2_290922_1", "type": "IssueID" }, "metasfreshId": 43, "externalSystemConfigId": 540005, "isReadOnlyMetasfresh": true }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
  """
{
    "systemName": "Github",
    "items": [
        { "id": "Not_Referenced_GitHubIssue_v2_290922_2", "type": "IssueID" },
        { "id": "Referenced_GitHubIssue_v2_290922_1", "type": "IssueID" },
        { "id": "Not_Referenced_GitHubIssue_v2_290922_3", "type": "IssueID" }
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
                    "id": "Not_Referenced_GitHubIssue_v2_290922_2"
                }
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "id": "Referenced_GitHubIssue_v2_290922_1"
                },
                "metasfreshId": 43,
                "externalReference": "Referenced_GitHubIssue_v2_290922_1",
                "systemName": "Github",
                "externalSystemConfigId": 540005,
                "isReadOnlyMetasfresh": true
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "id": "Not_Referenced_GitHubIssue_v2_290922_3"
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
    { "lookupItem": { "id": "Referenced_BPartner_v2_290922", "type": "BPartner" }, "metasfreshId": 53, "externalSystemConfigId": 540006, "isReadOnlyMetasfresh": true },
    { "lookupItem": { "id": "Referenced_GitHubIssue_v2_290922_2", "type": "IssueID" }, "metasfreshId": 54, "externalSystemConfigId": 540006, "isReadOnlyMetasfresh": false }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "GRSSignum",
  "items": [
    { "metasfreshId": 53, "type": "BPartner" },
    { "id": "Referenced_GitHubIssue_v2_290922_2", "type": "IssueID" }
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
                "externalReference": "Referenced_BPartner_v2_290922",
                "systemName": "GRSSignum",
                "externalSystemConfigId": 540006,
                "isReadOnlyMetasfresh": true
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "id": "Referenced_GitHubIssue_v2_290922_2"
                },
                "metasfreshId": 54,
                "externalReference": "Referenced_GitHubIssue_v2_290922_2",
                "systemName": "GRSSignum",
                "externalSystemConfigId": 540006,
                "isReadOnlyMetasfresh": false
            }
        ]
}
    """

  Scenario: an external resource is referenced to a metasfresh resource and has 'IsReadOnlyMetasfresh' flag updated
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "id": "Referenced_Product_v2_290922", "type": "Product" }, "metasfreshId": 25, "isReadOnlyMetasfresh": true }
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "items": [
    { "metasfreshId": 25, "type": "Product" }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
    "items": [
            {
                "lookupItem": {
                    "metasfreshId": 25,
                    "type": "Product"
                },
                "metasfreshId": 25,
                "externalReference": "Referenced_Product_v2_290922",
                "systemName": "Shopware6",
                "isReadOnlyMetasfresh": true
            }
        ]
}
    """
    And the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "id": "Referenced_Product_v2_290922", "type": "Product" }, "metasfreshId": 25, "externalSystemConfigId": 540001, "isReadOnlyMetasfresh": false }
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "items": [
    { "metasfreshId": 25, "type": "Product" }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
    "items": [
            {
                "lookupItem": {
                    "metasfreshId": 25,
                    "type": "Product"
                },
                "metasfreshId": 25,
                "externalReference": "Referenced_Product_v2_290922",
                "systemName": "Shopware6",
                "externalSystemConfigId": 540001,
                "isReadOnlyMetasfresh": false
            }
        ]
}
    """
    And the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "id": "Referenced_Product_v2_290922", "type": "Product" }, "metasfreshId": 25, "isReadOnlyMetasfresh": true }
}
    """
    And the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "id": "Referenced_Product_v2_290922", "type": "Product" }, "externalSystemConfigId": 540001, "metasfreshId": 25 }
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "items": [
    { "metasfreshId": 25, "type": "Product" }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
    "items": [
            {
                "lookupItem": {
                    "metasfreshId": 25,
                    "type": "Product"
                },
                "metasfreshId": 25,
                "externalReference": "Referenced_Product_v2_290922",
                "systemName": "Shopware6",
                "externalSystemConfigId": 540001,
                "isReadOnlyMetasfresh": true
            }
        ]
}
    """