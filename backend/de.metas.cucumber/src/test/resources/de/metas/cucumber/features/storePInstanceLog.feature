@from:cucumber
@allure.label.epic:E0180_System_Administration
@allure.label.feature:F00185
@ghActions:run_on_executor7
Feature: pInstanceLog store using metasfresh api
## F00185: Process Instance Log
  As a REST-API invoker
  I want to be able to store external pInstanceLog

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And I_AD_PInstance with id 321321 is created
    
  @from:cucumber
@allure.label.epic:E0180_System_Administration
@allure.label.feature:F00185
  Scenario: The request is good and the pInstanceLog is stored
    When the metasfresh REST-API endpoint path 'api/externalsystem/321321/externalstatus/message' receives a 'POST' request with the payload
  """
{
	"logs": [
		{
			"message": "External job finished successfully"
		}
	]
}
"""
    Then a new metasfresh AD_PInstance_Log is stored for PInstance id '321321'
