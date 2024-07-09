@from:cucumber
@ghActions:run_on_executor5
Feature: external references for metasfresh resources (V2)
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
    { "externalReference": "Not_Referenced_GitHubIssue_v2_290922_1", "type": "IssueID" }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "items": [
    { "lookupItem":{"externalReference":"Not_Referenced_GitHubIssue_v2_290922_1","type":"IssueID"} }
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
    { "lookupItem": { "externalReference": "Referenced_GitHubIssue_v2_290922_1", "type": "IssueID" }, "metasfreshId": 43, "externalSystemConfigId": 540005, "readOnlyMetasfresh": true }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
  """
{
    "systemName": "Github",
    "items": [
        { "externalReference": "Not_Referenced_GitHubIssue_v2_290922_2", "type": "IssueID" },
        { "externalReference": "Referenced_GitHubIssue_v2_290922_1", "type": "IssueID" },
        { "externalReference": "Not_Referenced_GitHubIssue_v2_290922_3", "type": "IssueID" }
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
                    "externalReference": "Not_Referenced_GitHubIssue_v2_290922_2"
                }
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "Referenced_GitHubIssue_v2_290922_1"
                },
                "metasfreshId": 43,
                "externalReference": "Referenced_GitHubIssue_v2_290922_1",
                "systemName": "Github",
                "externalSystemConfigId": 540005,
                "readOnlyMetasfresh": true
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "Not_Referenced_GitHubIssue_v2_290922_3"
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
    { "lookupItem": { "externalReference": "Referenced_BPartner_v2_290922", "type": "BPartner" }, "metasfreshId": 53, "externalSystemConfigId": 540006, "readOnlyMetasfresh": true },
    { "lookupItem": { "externalReference": "Referenced_GitHubIssue_v2_290922_2", "type": "IssueID" }, "metasfreshId": 54, "externalSystemConfigId": 540006, "readOnlyMetasfresh": false }
  ]
}
    """
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "GRSSignum",
  "items": [
    { "metasfreshId": 53, "type": "BPartner" },
    { "externalReference": "Referenced_GitHubIssue_v2_290922_2", "type": "IssueID" }
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
                "readOnlyMetasfresh": true
            },
            {
                "lookupItem": {
                    "type": "IssueID",
                    "externalReference": "Referenced_GitHubIssue_v2_290922_2"
                },
                "metasfreshId": 54,
                "externalReference": "Referenced_GitHubIssue_v2_290922_2",
                "systemName": "GRSSignum",
                "externalSystemConfigId": 540006,
                "readOnlyMetasfresh": false
            }
        ]
}
    """

  Scenario: an external resource is referenced to a metasfresh resource and has 'IsReadOnlyMetasfresh' flag updated
    Given the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "externalReference": "Referenced_Product_v2_290922", "type": "Product" }, "metasfreshId": 25, "readOnlyMetasfresh": true }
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
                "readOnlyMetasfresh": true
            }
        ]
}
    """
    And the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "externalReference": "Referenced_Product_v2_290922", "type": "Product" }, "metasfreshId": 25, "externalSystemConfigId": 540001, "readOnlyMetasfresh": false }
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
                "readOnlyMetasfresh": false
            }
        ]
}
    """
    And the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "externalReference": "Referenced_Product_v2_290922", "type": "Product" }, "metasfreshId": 25, "readOnlyMetasfresh": true }
}
    """
    And the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "systemName": "Shopware6",
  "externalReferenceItem": { "lookupItem": { "externalReference": "Referenced_Product_v2_290922", "type": "Product" }, "externalSystemConfigId": 540001, "metasfreshId": 25 }
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
                "readOnlyMetasfresh": true
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
      | ExternalSystem | Type     | ExternalReference       | OPT.ExternalReferenceURL | OPT.Referenced_Record_ID |
      | Other          | BPartner | externalReference_S0401 | https://example.com      | 20240322                 |

  @S0402
  Scenario: upsert - an existing externalReference record is updated
    Given metasfresh contains C_BPartners:
      | Identifier     | Name           | OPT.IsCustomer | OPT.CompanyName    | OPT.AD_Language |
      | customer_S0402 | customer_S0402 | Y              | customer_S0402_cmp | de_DE           |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type     | ExternalReference       | OPT.C_BPartner_ID.Identifier |
      | externalRef_BPartner_S0402        | Other          | BPartner | externalReference_S0402 | customer_S0402               |

    # note externalSystemConfigId 540013 is "SAP"
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReference": "externalReference_S0402-NEW",
    "externalReferenceUrl": "https://example.com",
    "readOnlyMetasfresh": true,
    "externalSystemConfigId": 540013,
    "version": "v.1.2",
    "metasfreshId": 54321,
    "lookupItem": {
      "externalReference": "externalReference_S0402",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    Then verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference           | OPT.ExternalReferenceURL | OPT.IsReadOnlyInMetasfresh | OPT.ExternalSystem_Config_ID | OPT.Version | OPT.Referenced_Record_ID |
      | Other          | BPartner | externalReference_S0402-NEW | https://example.com      | true                       | 540013                       | v.1.2       | 54321                    |

    # now omit the property "externalReferenceUrl" and the others => expect them to be unchanged
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
      | ExternalSystem | Type     | ExternalReference             | OPT.ExternalReferenceURL | OPT.IsReadOnlyInMetasfresh | OPT.ExternalSystem_Config_ID | OPT.Version | OPT.Referenced_Record_ID |
      | Other          | BPartner | externalReference_S0402-NEW_2 | https://example.com      | true                       | 540013                       | v.1.2       | 54321                    |

    # now set the property "externalReferenceUrl" and the other "nullable" ones explicitly to null => expect them to be null
    When the metasfresh REST-API endpoint path '/api/v2/externalRefs/upsert/001' receives a 'PUT' request with the payload
    """
{
  "externalReferenceItem": {
    "externalReferenceUrl": null,
    "externalSystemConfigId": null,
    "version": null,
    "lookupItem": {
      "externalReference": "externalReference_S0402-NEW_2",
      "type": "BPartner"
    }
  },
  "systemName": "Other"
}
    """

    Then verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference             | OPT.ExternalReferenceURL | OPT.ExternalSystem_Config_ID | OPT.Version |
      | Other          | BPartner | externalReference_S0402-NEW_2 | null                     | null                         | null        |
