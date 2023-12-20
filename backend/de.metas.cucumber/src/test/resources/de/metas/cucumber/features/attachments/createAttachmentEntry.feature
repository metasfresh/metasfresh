@ghActions:run_on_executor3
Feature: attachment creation using metasfresh api
  As a REST-API invoker
  As an API user I want to be able to create attachment entries

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Attachment entry and attachment multiRef given base64 data

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/attachment' and fulfills with '200' status code
    """
{
    "orgCode": "001",
    "targets": [
        {
            "externalReferenceType":"BPartner",
            "externalReferenceIdentifier":"2156425"
        }
    ],
    "attachment": {
        "type": "Data",
        "fileName": "Data.png",
        "data": "QmFzZTY0LVN0cmluZ0RhdGE=",
        "mimeType": "image/png"
    }
}
"""
    Then process attachment response
      | AD_AttachmentEntry_ID.Identifier |
      | attachmentEntry_1                |
    And validate the created attachment entry
      | AD_AttachmentEntry_ID.Identifier | Type | FileName | BinaryData        | ContentType |
      | attachmentEntry_1                | D    | Data.png | Base64-StringData | image/png   |
    And validate the created attachment multiref
      | AD_AttachmentEntry_ID.Identifier | Record_ID | TableName  |
      | attachmentEntry_1                | 2156425   | C_BPartner |


  @from:cucumber
  Scenario:  Attachment entry and attachment multiRef given URL

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/attachment' and fulfills with '200' status code
    """
{
    "orgCode": "001",
    "targets": [
        {
            "externalReferenceType":"BPartner",
            "externalReferenceIdentifier":"2156425"
        }
    ],
    "attachment": {
        "type": "URL",
        "fileName": "URLTest",
        "data": "https://test.com"
    }
}
"""
    Then process attachment response
      | AD_AttachmentEntry_ID.Identifier |
      | attachmentEntry_1                |
    And validate the created attachment entry
      | AD_AttachmentEntry_ID.Identifier | Type | FileName | BinaryData | ContentType | URL              |
      | attachmentEntry_1                | U    | URLTest  | null       | null        | https://test.com |
    And validate the created attachment multiref
      | AD_AttachmentEntry_ID.Identifier | Record_ID | TableName  |
      | attachmentEntry_1                | 2156425   | C_BPartner |


  @from:cucumber
  Scenario:  Attachment entry and attachment multiRef given LocalFileURL

    Given an existing local file
      | File.Identifier | FileName |
      | file_1          | Test.txt |

    And store JsonAttachmentRequest in context
      | orgCode | AD_AttachmentEntry.Type | OPT.File.Identifier | OPT.targets                                                                     |
      | 001     | LocalFileURL            | file_1              | [{"externalReferenceType":"BPartner", "externalReferenceIdentifier":"2156425"}] |

    When the metasfresh REST-API endpoint path 'api/v2/attachment' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process attachment response
      | AD_AttachmentEntry_ID.Identifier |
      | attachmentEntry_1                |
    And validate the created attachment entry
      | AD_AttachmentEntry_ID.Identifier | Type | FileName | BinaryData | ContentType | OPT.File.Identifier |
      | attachmentEntry_1                | LU   | Test.txt | null       | text/plain  | file_1              |
    And validate the created attachment multiref
      | AD_AttachmentEntry_ID.Identifier | Record_ID | TableName  |
      | attachmentEntry_1                | 2156425   | C_BPartner |

  @from:cucumber
  Scenario:  Add a 'LocalFileURL' type attachment to multiple targets
    Given an existing local file
      | File.Identifier | FileName |
      | file_1          | Test.txt |
    And store JsonAttachmentRequest in context
      | orgCode | AD_AttachmentEntry.Type | OPT.File.Identifier | OPT.targets                                                                                                                                                  |
      | 001     | LocalFileURL            | file_1              | [{"externalReferenceType":"BPartner", "externalReferenceIdentifier":"2156425"},{"externalReferenceType":"Product", "externalReferenceIdentifier":"2005577"}] |

    When the metasfresh REST-API endpoint path 'api/v2/attachment' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process attachment response
      | AD_AttachmentEntry_ID.Identifier |
      | attachmentEntry_1                |
    And validate the created attachment entry
      | AD_AttachmentEntry_ID.Identifier | Type | FileName | BinaryData | ContentType | OPT.File.Identifier |
      | attachmentEntry_1                | LU   | Test.txt | null       | text/plain  | file_1              |
    And validate the created attachment multiref
      | AD_AttachmentEntry_ID.Identifier | Record_ID | TableName  |
      | attachmentEntry_1                | 2156425   | C_BPartner |
      | attachmentEntry_1                | 2005577   | M_Product  |

  @from:cucumber
  Scenario:  Attachment entry with JsonTableRecordReference and attachment at a given LocalFileURL
    Given an existing local file
      | File.Identifier | FileName |
      | file_xml        | Test.xml |

    And store JsonAttachmentRequest in context
      | orgCode | AD_AttachmentEntry.Type | OPT.File.Identifier | OPT.references                                   |
      | 001     | LocalFileURL            | file_xml            | [{"tableName":"C_BPartner", "recordId":2156425}] |

    When the metasfresh REST-API endpoint path 'api/v2/attachment' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process attachment response
      | AD_AttachmentEntry_ID.Identifier |
      | attachmentEntry_1                |
    And validate the created attachment entry
      | AD_AttachmentEntry_ID.Identifier | Type | FileName | BinaryData | ContentType | OPT.File.Identifier |
      | attachmentEntry_1                | LU   | Test.xml | null       | text/xml    | file_xml            |
    And validate the created attachment multiref
      | AD_AttachmentEntry_ID.Identifier | Record_ID | TableName  |
      | attachmentEntry_1                | 2156425   | C_BPartner |


  @from:cucumber
  Scenario:  Attachment entry with JsonTableRecordReference and JsonExternalReferenceTarget with base64 payload
    Given store JsonAttachmentRequest in context
      | orgCode | AD_AttachmentEntry.Type | OPT.FileName | OPT.BinaryData | OPT.references                                   | OPT.targets                                                                                                                                                  |
      | 001     | Data                    | test_001.txt | this is a test | [{"tableName":"M_Warehouse", "recordId":540008}] | [{"externalReferenceType":"BPartner", "externalReferenceIdentifier":"2156425"},{"externalReferenceType":"Product", "externalReferenceIdentifier":"2005577"}] |

    When the metasfresh REST-API endpoint path 'api/v2/attachment' receives a 'POST' request with the payload from context and responds with '200' status code

    Then process attachment response
      | AD_AttachmentEntry_ID.Identifier |
      | attachmentEntry_1                |
    And validate the created attachment entry
      | AD_AttachmentEntry_ID.Identifier | Type | FileName     | BinaryData     | ContentType |
      | attachmentEntry_1                | D    | test_001.txt | this is a test | text/plain  |
    And validate the created attachment multiref
      | AD_AttachmentEntry_ID.Identifier | Record_ID | TableName   |
      | attachmentEntry_1                | 2156425   | C_BPartner  |
      | attachmentEntry_1                | 2005577   | M_Product   |
      | attachmentEntry_1                | 540008    | M_Warehouse |
