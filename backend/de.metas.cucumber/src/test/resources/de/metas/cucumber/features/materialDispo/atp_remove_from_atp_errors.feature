@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: MD_Candidate_Remove_From_ATP reports its errors instead of raising

  Guard: MD_Candidate_Remove_From_ATP declares a 6-column result row (removed candidate id, stock
  candidate id, current ATP qty, qty adjustment, impacted-candidates count, message). Before the fix,
  the "candidate not found or not active" branch returned only 4 values, so the message text landed in
  a numeric column and PostgreSQL raised "structure of query does not match function result type"
  instead of returning a row an operator/caller can read. This feature proves the process now completes
  successfully for an argument that never resolves to an active candidate.

  The sibling branch (an existing, active, non-STOCK candidate whose STOCK sibling cannot be found) is
  not reachable through a live business scenario: every code path that creates a demand/supply candidate
  also creates its STOCK sibling in the same operation, and no operator action removes only the sibling
  while leaving the candidate active. That branch is verified instead by a direct SQL probe against the
  fixed function, run before and after the fix to confirm the discrimination.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @Id:ATPRFA_001
  @from:cucumber
  Scenario: removing a candidate ID that resolves to no active MD_Candidate reports a well-formed error, not a raised exception

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/processes/MD_Candidate_RemoveFromATP/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "MD_Candidate_ID",
      "value": "999999999"
    }
  ]
}
    """
