@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00380_ExternalSystem_Scripted-Import-Processor
@ghActions:run_on_executor5
Feature: the endpoint interceptor's rules are the dictionary's own
## F00380: ExternalSystem Scripted-Import-Processor
  The interceptor that resets the endpoint fields a new transport or authentication configuration hides
  carries two verbatim copies of the dictionary per field: that field's AD_Field.DisplayLogic, which says
  when the window shows it, and that column's AD_Column.DefaultValue, which is what hiding it leaves
  behind. A copy cannot see the dictionary: a migration script may change a condition, change a default,
  or deactivate a field, and leave the copy behind saying something the dictionary no longer says.
  As a developer changing either side
  I want the build to hold the copies against the live dictionary
  So that a stale copy fails here instead of resetting -- or keeping -- an endpoint field in production

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00380_ExternalSystem_Scripted-Import-Processor
  Scenario: every rule repeats its field's condition, and every conditionally shown field has a rule
    Then the ExternalSystem_Endpoint interceptor's display logic is exactly the window's

  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00380_ExternalSystem_Scripted-Import-Processor
  Scenario: every rule repeats its column's default value, and the same columns carry one on both sides
    Then the ExternalSystem_Endpoint interceptor's column defaults are exactly the dictionary's
