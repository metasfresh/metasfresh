@from:cucumber
@ghActions:run_on_executor3
Feature: API Audit DELETE http method

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the API audit data is reset

  @from:cucumber
  Scenario: Testcase 100, normal DELETE and caller waits for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | DELETE     | api/v2/test    | N                     | Y                                | Y                 |

    When invoke 'DELETE' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '200'

    Then the actual response body is
    """
   {
	"messageBody": "\"test-endpoint was called\""
  }
"""
    And there are added records in API_Request_Audit
      | Method | Path                                                                          | AD_User.Name | Status      |
      | DELETE | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 110, normal DELETE and caller does not wait for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | DELETE     | api/v2/test    | Y                     | Y                                | Y                 |

    When invoke 'DELETE' '/api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '202'

    # We call the test endpoint and instruct it to wait for 1 seconds before returning, and we have IsForceProcessedAsync=Y
    # So when we check right after the call, we can expect an audit record to be created and the request to be "received", but not yet "processed"
    Then there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status    |
      | DELETE | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Empfangen |

    And there are no records in API_Request_Audit_Log for the API_Request_Audit from context

    And there are no records in API_Response_Audit for the API_Request_Audit from context

    And after not more than 60s, there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status      |
      | DELETE | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 120, failing DELETE and caller waits for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | DELETE     | api/v2/test    | N                     | Y                                | Y                 |

    When invoke 'DELETE' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '404'
    Then the actual response body is
    """
   {
	"messageBody": "\"test-endpoint was called\""
  }
"""
    And there are added records in API_Request_Audit
      | Method | Path                                                                         | AD_User.Name | Status |
      | DELETE | api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary               |
      | Endpoint invoked; returning httpCode: 404 | null                           |
      | Endpoint invoked; log ad_issue            | Endpoint invoked; log ad_issue |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 130, failing DELETE and caller does not wait for result
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | DELETE     | api/v2/test    | Y                     | Y                                | Y                 |

    When invoke 'DELETE' '/api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '202'

    # We call the test endpoint and instruct it to wait for 1 seconds before returning, and we have IsForceProcessedAsync=Y
    # So when we check right after the call, we can expect an audit record to be created and the request to be "received", but not yet "processed"
    Then there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status    |
      | DELETE | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Empfangen |

    And there are no records in API_Request_Audit_Log for the API_Request_Audit from context

    And there are no records in API_Response_Audit for the API_Request_Audit from context

    And after not more than 60s, there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status |
      | DELETE | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And after not more than 60s, there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary               |
      | Endpoint invoked; returning httpCode: 404 | null                           |
      | Endpoint invoked; log ad_issue            | Endpoint invoked; log ad_issue |

    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 140, failing DELETE and replay
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | DELETE     | api/v2/test    | N                     | Y                                | Y                 |

    And invoke 'DELETE' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '404'

    And there are added records in API_Request_Audit
      | Method | Path                                                                         | AD_User.Name | Status |
      | DELETE | api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

    And on API_Request_Audit record we update the statusCode value from path
      | statusCode |
      | 200        |

    When invoke replay audit

    Then there are added records in API_Request_Audit
      | Method | Path                                                                         | AD_User.Name | Status      |
      | DELETE | api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 404 | null             |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 200, reset to initial default data
    And all the API audit data is reset
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 9980  | null       | null           | N                     | Y                                | Y                 |