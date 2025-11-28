@from:cucumber
@ghActions:run_on_executor5
Feature: R_Request upsert and retrieval via API

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value |
      | 002                  | 002  | org-2 |
    And metasfresh contains M_Products:
      | Identifier | Value   |
      | product    | reqProd |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | Value |
      | customer   | N        | Y          | reqBP |
    And metasfresh contains AD_Users:
      | Identifier | AD_User_ID | Name     |
      | someUser   | 12345      | someUser |

  @from:cucumber
  @Id:S0476_010
  @Id:S0477_010
  Scenario: R_Request upsert
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/request' and fulfills with '201' status code
"""
{
  "orgCode" : "org-2",
  "requestType" : "A_CustomerComplaint",
  "summary" : "Another summary",
  "dateDelivered" : "2025-06-15",
  "priority" : "Medium",
  "confidentialityLevel" : "PartnerConfidential"
}
"""

    Then a new request exists:
      | AD_Org_ID | R_RequestType_ID    | Summary         | DateDelivered | Priority | ConfidentialType |
      | 002       | A_CustomerComplaint | Another summary | 2025-06-15    | 5        | C                |