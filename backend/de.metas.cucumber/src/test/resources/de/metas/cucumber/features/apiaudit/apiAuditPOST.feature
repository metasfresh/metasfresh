@from:cucumber
Feature: API Audit POST http method

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the API audit data is reset

  @from:cucumber
  Scenario: Testcase 100, normal POST and caller waits for result
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_1        | 10    | POST       | api/v2/test    | Y                       |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '200'

    And the actual response body is
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
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_1        | 10    | POST       | api/v2/test    | N                       |

    When invoke 'POST' '/api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200' with response code '202'

    And there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status    |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Empfangen |

    And there are no records in API_Request_Audit_Log
      | Logmessage | AD_Issue.Summary |

    And there are no records in API_Response_Audit
      | HttpCode | Body |

    And we wait for 2000 ms

    And there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status      |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 120, failing POST and caller waits for result
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_1                            | 10    | POST       | api/v2/test    | Y                       |

    When invoke 'POST' 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '404'
    And the actual response body is
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
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_1        | 10    | POST       | api/v2/test    | N                       |

    When invoke 'POST' '/api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404' with response code '202'

    And there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status    |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Empfangen |

    And there are no records in API_Request_Audit_Log
      | Logmessage | AD_Issue.Summary |

    And there are no records in API_Response_Audit
      | HttpCode | Body |

    And we wait for 2000 ms

    And there are added records in API_Request_Audit
      | Method | Path                                                                                           | AD_User.Name | Status |
      | POST   | /api/v2/test?delaymillis=1000&responseBody=%22test-endpoint%20was%20called%22&responseCode=404 | metasfresh   | Fehler |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary               |
      | Endpoint invoked; returning httpCode: 404 | null                           |
      | Endpoint invoked; log ad_issue            | Endpoint invoked; log ad_issue |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 404      | {"messageBody":"\"test-endpoint was called\""} |

  @from:cucumber
  Scenario: Testcase 140, failing POST and replay
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_1        | 10    | POST       | api/v2/test    | Y                       |

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

    And there are added records in API_Request_Audit
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
  Scenario: Testcase 200, reset to initial default data
    And all the API audit data is reset
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_100      | 9980  | null       | null           | Y                       |
