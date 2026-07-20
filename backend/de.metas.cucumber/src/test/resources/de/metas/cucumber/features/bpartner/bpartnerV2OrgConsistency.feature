@from:cucumber
@ghActions:run_on_executor4
@allure.label.epic:E0291_REST_API
@allure.label.feature:F4520_BPartner_REST_API
Feature: BPartner v2 upsert places records in the path org

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value |
      | org002               | 002  | 002   |
    And metasfresh contains External System
      | Name        | Value       |
      | Test System | Test_System |

  @Id:S30934_TC1
  Scenario: PUT api/v2/bpartner/002 places C_BPartner and S_ExternalReference under org 002
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/002' and fulfills with '201' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-Test_System-001",
      "bpartnerComposite": {
        "bpartner": {
          "name": "Org Consistency Test",
          "language": "de"
        },
        "locations": {
          "requestItems": [
            {
              "locationIdentifier": "ext-Test_System-001-loc1",
              "location": {
                "address1": "Test Street 1",
                "countryCode": "DE"
              }
            }
          ]
        },
        "contacts": {
          "requestItems": [
            {
              "contactIdentifier": "ext-Test_System-001-con1",
              "contact": {
                "name": "Test Contact"
              }
            }
          ]
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
"""
    Then verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier  | name                   | AD_Org_ID |
      | bpartner_org002          | ext-Test_System-001 | Org Consistency Test | org002    |
    And verify that location was created for bpartner
      | bpartnerIdentifier  | locationIdentifier       | address1       | countryCode | AD_Org_ID |
      | ext-Test_System-001 | ext-Test_System-001-loc1 | Test Street 1  | DE          | org002    |
    And verify that contact was created for bpartner
      | bpartnerIdentifier  | contactIdentifier        | name         | AD_Org_ID |
      | ext-Test_System-001 | ext-Test_System-001-con1 | Test Contact | org002    |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference | AD_Org_ID |
      | Test_System    | BPartner | 001               | org002    |
    # Re-run the same PUT: must succeed (UPDATE_MERGE) and result in exactly one row under org002
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/002' and fulfills with '201' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-Test_System-001",
      "bpartnerComposite": {
        "bpartner": {
          "name": "Org Consistency Test",
          "language": "de"
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
"""
    Then verify that bPartner was updated for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier  | name                 | AD_Org_ID |
      | bpartner_org002          | ext-Test_System-001 | Org Consistency Test | org002    |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type     | ExternalReference | AD_Org_ID |
      | Test_System    | BPartner | 001               | org002    |

  @Id:S30934_TC2
  Scenario: PUT api/v2/bpartner/002 with body orgCode 001 is rejected (org mismatch)
    # When the body orgCode contradicts the path org, the API must reject with 4xx.
    # Currently (bug): the code silently accepts and returns 201 — so this assertion fails RED.
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/002' and fulfills with '422' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-Test_System-002",
      "bpartnerComposite": {
        "orgCode": "001",
        "bpartner": {
          "name": "Org Mismatch Test",
          "language": "de"
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
"""

  @Id:S30934_TC3
  Scenario: GET with path org 002 finds the partner; GET with path org 001 does not
    # Ensure the partner exists under org 002
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/002' and fulfills with '201' status code
    """
{
  "requestItems": [
    {
      "bpartnerIdentifier": "ext-Test_System-001",
      "bpartnerComposite": {
        "bpartner": {
          "name": "Org Consistency Test",
          "language": "de"
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
"""
    # GET by path org 002 should find the partner (currently fails: record lands under MAIN, not org002)
    Then the metasfresh REST-API endpoint path 'api/v2/bpartner/002/ext-Test_System-001' receives a 'GET' request with the headers from context, expecting status='200'
    # GET by path org 001 should return not-found (org isolation)
    # Currently (bug): the code finds the partner across orgs — this assertion fails RED.
    Then the metasfresh REST-API endpoint path 'api/v2/bpartner/001/ext-Test_System-001' receives a 'GET' request with the headers from context, expecting status='404'
