@from:cucumber
@ghActions:run_on_executor7
Feature: pInstanceLog store using metasfresh api
  As a REST-API invoker
  I want to be able to store external pInstanceLog

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And I_AD_PInstance with id 321321 is created
    
  @from:cucumber
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
