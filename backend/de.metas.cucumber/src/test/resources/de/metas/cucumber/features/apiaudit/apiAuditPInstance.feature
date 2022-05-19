@from:cucumber
Feature: API Audit

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the API audit data is reset

  @from:cucumber
  Scenario: if a 'x-adpinstanceid' header is set when auditing v2 calls, it should end up in API_Request_Audit.AD_PInstance_ID
    Given the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_1        | 10    | POST       | api/v2/test    | Y                       |

    And add I_AD_PInstance with id 12052022

    And following http headers are set on context
      | Name            | Value    |
      | x-adpinstanceid | 12052022 |

    When the metasfresh REST-API endpoint path 'api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200' receives a 'POST' request with the headers from context

    Then there are added records in API_Request_Audit
      | OPT.AD_PInstance_ID | Method | Path                                                                          | AD_User.Name | Status      |
      | 12052022            | POST   | /api/v2/test?responseBody=%22test-endpoint%20was%20called%22&responseCode=200 | metasfresh   | Verarbeitet |

    And there are added records in API_Request_Audit_Log
      | Logmessage                                | AD_Issue.Summary |
      | Endpoint invoked; returning httpCode: 200 | null             |

    And there are added records in API_Response_Audit
      | HttpCode | Body                                           |
      | 200      | {"messageBody":"\"test-endpoint was called\""} |

    And all the API audit data is reset
    And the following API_Audit_Config record is set
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix | IsInvokerWaitsForResult |
      | c_2        | 9980  | null       | null           | Y                       |