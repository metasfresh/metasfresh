@from:cucumber
Feature: Camel-ExternalSystem authorization

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Note table is reset

  @from:cucumber
  @Id:S0168_100
  Scenario: Both sys configs are set and no `AD_User_AuthToken` record found for provided pair, an `AD_User_AuthToken` record will be created and then JsonExternalSystemMessage containing the generated authToken is sent to RabbitMQ
    Given no AD_User_AuthToken records
    And set sys config String value 100 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_User_AuthToken record
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID.Identifier |
      | cucumberUserAuthToken_100       | 100                   | 540024                |

    And metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with authToken from cucumberUserAuthToken_100


  @from:cucumber
  @Id:S0168_200
  Scenario: Both sys configs are set and there is an `AD_User_AuthToken` record found for provided pair, then JsonExternalSystemMessage containing the authToken is sent to RabbitMQ
    Given no AD_User_AuthToken records
    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken_200       | 100                   | 540024     | S0168_200 |

    And set sys config String value 100 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with JsonExternalSystemMessagePayload S0168_200


  @from:cucumber
  @Id:S0168_300
  Scenario: One sys config is missing, then a custom notification is sent to the configured user group
    Given no AD_User_AuthToken records
    And load AD_Message:
      | Identifier               | Value                                                    |
      | messageSysConfigNotFound | External_Systems_Authorization_SysConfig_Not_Found_Error |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           |
      | user_S0168_300        | user_S0168_300 |
    And load AD_UserGroup from AD_SysConfig:
      | AD_UserGroup_ID.Identifier | AD_SysConfig.Name                                                             |
      | userGroup_api              | de.metas.externalsystem.externalservice.authorization.notificationUserGroupId |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | user_S0168_300        | true     |
    And set sys config String value null for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note       | messageSysConfigNotFound | user_S0168_300            |


  @from:cucumber
  @Id:S0168_400
  Scenario: Both sys configs are set but AD_Role_ID doesn't match any role associated with provided AD_User_ID, then a custom notification is sent to the configured user group
    Given no AD_User_AuthToken records
    And load AD_Message:
      | Identifier                        | Value                                                         |
      | messageSysConfigValueDoesNotExist | External_Systems_Authorization_SysConfig_Value_Does_Not_Exist |
    And load AD_UserGroup from AD_SysConfig:
      | AD_UserGroup_ID.Identifier | AD_SysConfig.Name                                                             |
      | userGroup_api              | de.metas.externalsystem.externalservice.authorization.notificationUserGroupId |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | 100                   | true     |
    And set sys config String value 100 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    And set sys config String value 99999 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID
    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier          | OPT.AD_User_ID.Identifier |
      | note       | messageSysConfigValueDoesNotExist | 100                       |


  @from:cucumber
  @Id:S0168_500
  Scenario: Both sys config are set but there are multiple `AD_User_AuthToken` records found for provided pair, then a custom notification is sent to the configured user group
    Given no AD_User_AuthToken records
    And load AD_Message:
      | Identifier               | Value                                           |
      | messageVerificationError | ExternalSystem_Authorization_Verification_Error |
    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken   |
      | cucumberUserAuthToken_500_1     | 100                   | 540024     | S0168_500_1 |
      | cucumberUserAuthToken_500_2     | 100                   | 540024     | S0168_500_2 |
    And load AD_UserGroup from AD_SysConfig:
      | AD_UserGroup_ID.Identifier | AD_SysConfig.Name                                                             |
      | userGroup_api              | de.metas.externalsystem.externalservice.authorization.notificationUserGroupId |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | 100                   | true     |
    And set sys config String value 100 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID
    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note       | messageVerificationError | 100                       |
