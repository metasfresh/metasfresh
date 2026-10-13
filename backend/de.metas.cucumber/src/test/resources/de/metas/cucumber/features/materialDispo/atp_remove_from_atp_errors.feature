@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F19000_Material_Dispo
@ghActions:run_on_executor6
Feature: MD_Candidate_Remove_From_ATP fails loudly on a precondition it cannot satisfy

  Guard: MD_Candidate_Remove_From_ATP checks three preconditions before it writes anything - the
  candidate must exist and be active, it must not itself be a STOCK candidate, and it must have a
  STOCK sibling. None of them can be satisfied by doing the work anyway, so each one raises.

  The process that exposes the function to an operator runs it through ExecuteUpdateSQL, which
  discards the result set. A precondition reported as an ordinary returned row would therefore be
  read by nobody: the process would come back successful, the candidate would be untouched, and the
  operator would see an unchanged record with no reason given - and a caller asserting the removal
  would fail later, somewhere else, with no trace of the real cause. These scenarios pin the loud
  failure so that cannot come back.

  The third branch (an active non-STOCK candidate whose STOCK sibling cannot be found) is not
  reachable through a live business scenario: every code path that creates a demand/supply candidate
  creates its STOCK sibling in the same operation, and no operator action removes only the sibling
  while leaving the candidate active. It is covered by a direct SQL probe against the function
  instead.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @Id:ATPRFA_001
  @from:cucumber
  Scenario: removing a candidate ID that resolves to no active MD_Candidate fails with that reason

    Then the MD_Candidate_Remove_From_ATP process is run for MD_Candidate_ID 999999999 and fails with 'MD_Candidate not found or not active'
