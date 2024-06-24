@from:cucumber
@ghActions:run_on_executor5
Feature: issue creation using metasfresh api
  As a REST-API invoker
  I want want to be able to create issues

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And I_AD_PInstance with id 123123 is created
    # We want the issue to be created synchronously, so when the "POST" step is done, the AD_Issue shall be there without any further ado
    # We use SeqNo=9 to make sure this config-record takes precedence
    And the following API_Audit_Config records are created:
      | Identifier  | SeqNo | OPT.Method | OPT.PathPrefix                       | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | wait4result | 9     | POST       | api/v2/externalsystem/externalstatus | N                     | Y                                | N                 |



  @from:cucumber
  Scenario: The request is good and the issue is created

    When the metasfresh REST-API endpoint path 'api/v2/externalsystem/externalstatus/123123/error' receives a 'POST' request with the payload
"""
{
	"errors": [
		{
			"issueCategory": "OTHER",
			"sourceClassName": "test",
			"sourceMethodName": "test",
			"stackTrace": "java.lang.NullPointerException at AnotherClassLoader.loadClass(test.java:58) at test.main(test.java:30) at Main.main(Main.java:68)",
			"orgCode": "001",
			"message": "unexpected NPE"
		}
	]
}
"""
    Then a new metasfresh AD issue is created
