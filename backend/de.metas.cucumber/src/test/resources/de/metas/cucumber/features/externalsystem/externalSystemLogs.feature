@from:cucumber
Feature: Store external system logs

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Store logs with table record reference
    Given add I_AD_PInstance with id 16042022

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/externalsystem/externalstatus/16042022/message' and fulfills with '200' status code
    """
{
   "logs":[
      {
         "message":"Log Message",
         "tableRecordRef":{
            "tableName":"AD_PInstance",
            "recordId":16042022
         }
      }
   ]
}
"""
    Then validate AD_PInstance_Log:
      | AD_PInstance_ID | AD_Table_ID.TableName | Record_ID | P_Msg       |
      | 16042022        | AD_PInstance          | 16042022  | Log Message |