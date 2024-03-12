@from:cucumber
@ghActions:run_on_executor3
Feature: API Audit POST http method

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the API audit data is reset

  @from:cucumber
  Scenario: Testcase 100, normal POST and caller waits for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | Y                 |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '200'

    Then the actual response body is
    """
   {
	"messageBody": "\"test-endpoint was called\""
  }
"""
    And there are added records in API_Request_Audit
      | Method | Path                                                                          | AD_User.Name | Status      |
      | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 110, normal POST and caller does not wait for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | Y                     | Y                                | Y                 |

    When invoke 'POST' '/api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '202'

    # We call the test endpoint and instruct it to wait for 1 seconds before returning, and we have IsForceProcessedAsync=Y
    # So when we check right after the call, we can expect an audit record to be created and the request to be "received", but not yet "processed"
    Then there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status                   |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Empfangen |

    And there are no records in API_Request_Audit_Log for the API_Request_Audit from context

    And there are no records in API_Response_Audit for the API_Request_Audit from context

    And after not more than 60s, there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status      |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 120, failing POST and caller waits for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | Y                 |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '404'
    Then the actual response body is
    """
   {
	"messageBody": "\"test-endpoint was called\""
  }
"""
    And there are added records in API_Request_Audit
      | Method | Path                                                                         | AD_User.Name | Status |
      | POST   | api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary               |
      | Endpoint invoked; returning httpCode: 404 | null                           |
      | Endpoint invoked; log ad_issue            | Endpoint invoked; log ad_issue |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 130, failing POST and caller does not wait for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | Y                     | Y                                | Y                 |

    When invoke 'POST' '/api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '202'

    # We call the test endpoint and instruct it to wait for 1 seconds before returning, and we have IsForceProcessedAsync=Y
    # So when we check right after the call, we can expect an audit record to be created and the request to be "received", but not yet "processed"
    Then there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status                   |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Empfangen |

    And there are no records in API_Request_Audit_Log for the API_Request_Audit from context

    And there are no records in API_Response_Audit for the API_Request_Audit from context

    And after not more than 60s, there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary               |
      | Endpoint invoked; returning httpCode: 404 | null                           |
      | Endpoint invoked; log ad_issue            | Endpoint invoked; log ad_issue |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 140, failing POST and replay
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | Y                 |

    And invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '404'

    And there are added records in API_Request_Audit
      | Method | Path                                                                         | AD_User.Name | Status |
      | POST   | api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

    And on API_Request_Audit record we update the statusCode value from path
      | statusCode |
      | 200        |

    When invoke replay audit

    Then there are added records in API_Request_Audit
      | Method | Path                                                                         | AD_User.Name | Status      |
      | POST   | api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 404 | null             |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 150, normal POST with IsForceProcessedAsync = Y, IsSynchronousAuditLoggingEnabled = Y and IsWrapApiResponse = N
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | Y                     | Y                                | N                 |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '202'

    Then the actual response body is empty

    And after not more than 60s, there are added records in API_Request_Audit
      | Method | Path                                                                          | AD_User.Name | Status      |
      | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |


  @from:cucumber
  Scenario: Testcase 160, normal POST, caller waits for result, IsSynchronousAuditLoggingEnabled is true and IsWrapApiResponse is false
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | N                 |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '200'

    Then the actual response body is
    """
   {
	"messageBody": "\"test-endpoint was called\""
  }
"""
    And there are added records in API_Request_Audit
      | Method | Path                                                                          | AD_User.Name | Status      |
      | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 170, normal POST, caller waits for result, IsSynchronousAuditLoggingEnabled is true, IsWrapApiResponse is false and X-Api-Async header is true
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | N                 |

    When add HTTP header
      | Key         | Value |
      | X-Api-Async | true  |

    Then the metasfresh REST-API endpoint path 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' receives a 'POST' request with the headers from context, expecting status='202'

    And the actual response body is empty

    And after not more than 60s, there are added records in API_Request_Audit
      | Method | Path                                                                          | AD_User.Name | Status      |
      | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 175, normal POST with IsSynchronousAuditLoggingEnabled is false, IsWrapApiResponse is false and response body is missing
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | N                                | N                 |

    When invoke 'POST' 'api/v2/test?responseCode=200' with response code '200'

    Then the actual response body is empty

    And after not more than 60s, find and add last API_Request_Audit_ID to context

    And there are added records in API_Request_Audit
      | Method | Path                          | AD_User.Name | Status      |
      | POST   | /api/v2/test?responseCode=200 | metasfresh   | Verarbeitet |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                          | AD_Issue.Summary |
      | Async audit performed successfully! | null             |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body |
      | 200      |      |

  @from:cucumber
  Scenario: Testcase 180, normal POST, caller waits for result, IsSynchronousAuditLoggingEnabled is true, IsWrapApiResponse is false and response body is missing
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | N                 |

    When invoke 'POST' 'api/v2/test?responseCode=200' with response code '200'

    Then the actual response body is empty

    And there are added records in API_Request_Audit
      | Method | Path                          | AD_User.Name | Status      |
      | POST   | /api/v2/test?responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body |
      | 200      |      |

  @from:cucumber
  Scenario: Testcase 185, failing POST, caller waits for result, IsSynchronousAuditLoggingEnabled is true, IsWrapApiResponse is false and exception thrown in metasfresh api
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | N                 |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=422&throwException=true' with response code '422'

    Then there are added records in API_Request_Audit
      | Method | Path                                                                                              | AD_User.Name | Status |
      | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=422&throwException=true | metasfresh   | Fehler |

    And there are added records in API_Request_Audit_Log
      | Logmessage       | AD_Issue.Summary |
      | Exception thrown | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body             |
      | 422      | Exception thrown |

  @from:cucumber
  Scenario: Testcase 190, failing POST with IsSynchronousAuditLoggingEnabled is false, IsWrapApiResponse is false and exception thrown in metasfresh api
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | N                                | N                 |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=422&throwException=true' with response code '422'

    Then after not more than 60s, find and add last API_Request_Audit_ID to context

    And there are added records in API_Request_Audit
      | Method | Path                                                                                              | AD_User.Name | Status |
      | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=422&throwException=true | metasfresh   | Fehler |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                          | AD_Issue.Summary |
      | Async audit performed successfully! | null             |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body             |
      | 422      | Exception thrown |

  @from:cucumber
  Scenario: Testcase 195, normal POST, caller waits for result, IsSynchronousAuditLoggingEnabled is true and IsWrapApiResponse is false and response cannot be deserialized
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | Y                                | N                 |

    When invoke 'POST' 'api/v2/test?responseBody=test-endpoint%20was%20called&responseCode=500&nonJsonBody=true' with response code '500'

    Then there are added records in API_Request_Audit
      | Method | Path                                                                                     | AD_User.Name | Status    |
      | POST   | /api/v2/test?responseBody=test-endpoint%20was%20called&responseCode=500&nonJsonBody=true | metasfresh   | Empfangen |

    And there are added records in API_Request_Audit_Log
      | Logmessage         | AD_Issue.Summary |
      | Unrecognized token | null             |

    And there are no records in API_Response_Audit for the API_Request_Audit from context

  @from:cucumber
  Scenario: Testcase 200, normal POST with IsSynchronousAuditLoggingEnabled is false and IsWrapApiResponse is false and response cannot be deserialized
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | POST       | api/v2/test    | N                     | N                                | N                 |

    When invoke 'POST' 'api/v2/test?responseBody=test-endpoint%20was%20called&responseCode=200&nonJsonBody=true' with response code '200'

    Then the actual non JSON response body is
    """
test-endpoint was called
    """

    And there are no records in API_Request_Audit_Log

    And there are no records in API_Response_Audit

  @from:cucumber
  Scenario: Testcase 210, reset to initial default data
    And all the API audit data is reset
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_100      | 9980  | null       | null           | N                     | Y                                | Y                 |
