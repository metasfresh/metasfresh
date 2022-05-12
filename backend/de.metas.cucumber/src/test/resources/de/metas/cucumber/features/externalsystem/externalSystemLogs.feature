@from:cucumber
Feature: Store external system logs

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Store logs with table record reference
    Given add I_AD_PInstance with id 11042022

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/externalsystem/54321/externalstatus/message' and fulfills with '201' status code
    """
{
   "logs":
      [
        {
            "message" : "Log Message",
            "tableRecordReference" : {
              "tableName" : "AD_PInstance",
              "recordId" : 11042022
            }
        }
      ]
}
"""
    Then validate AD_PInstance_Log:
      | AD_PInstance_ID | AD_Table_ID.TableName | Record_Id | P_Msg       |
      | 11042022        | AD_PInstance          | 11042022  | Log Message |