Feature: issue creation using metasfresh api
  As a REST-API invoker
  I want want to be able to create issues

  Background:
    Given the existing user has a valid API token
    And I_AD_PInstance with id '9999999' is created

  Scenario: The request is good and the issue is created
    When the metasfresh REST-API endpoint for issue creation receives a request with the payload
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
