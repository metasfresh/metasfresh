@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00380_ExternalSystem_Scripted-Import-Processor
@ghActions:run_on_executor5
Feature: the endpoint interceptor's display logic is the window's own
## F00380: ExternalSystem Scripted-Import-Processor
  The interceptor that clears the endpoint fields a new transport or authentication configuration hides
  carries each field's AD_Field.DisplayLogic as a verbatim string copy. A copy cannot see the dictionary:
  a migration script may change a condition, or deactivate a field, and leave the copy behind saying
  something the window no longer says.
  As a developer changing either side
  I want the build to hold the copies against the live dictionary
  So that a stale copy fails here instead of clearing -- or keeping -- an endpoint field in production

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00380_ExternalSystem_Scripted-Import-Processor
  Scenario: every rule repeats its field's condition, and every conditionally shown field has a rule
    Then the ExternalSystem_Endpoint interceptor's display logic is exactly the window's
