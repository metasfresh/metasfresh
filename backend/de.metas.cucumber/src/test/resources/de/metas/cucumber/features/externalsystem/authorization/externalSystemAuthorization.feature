@from:cucumber
Feature: Camel-ExternalSystem authorization

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Note table is reset

  @from:cucumber
  @Id:S0168_100
  Scenario: sys config is set and matches `AD_User_AuthToken.AuthToken`, then JsonExternalSystemMessage containing the authToken is sent to RabbitMQ
    Given metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           |
      | user_S0168_100        | user_S0168_100 |
    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken           | user_S0168_100        | 540024     | S0168_100 |

    And set sys config String value S0168_100 for sys config de.metas.externalsystem.externalservice.authorization.authToken

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with JsonExternalSystemMessagePayload S0168_100

  @from:cucumber
  @Id:S0168_200
  Scenario: sys config is missing, then a custom notification is sent to the configured user group
    Given load AD_Message:
      | Identifier                | Value                                                    |
      | sysConfigNotFound_message | External_Systems_Authorization_SysConfig_Not_Found_Error |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           |
      | user_S0168_200        | user_S0168_200 |
    And load AD_UserGroup from AD_SysConfig:
      | AD_UserGroup_ID.Identifier | AD_SysConfig.Name                                                             |
      | userGroup_api              | de.metas.externalsystem.externalservice.authorization.notificationUserGroupId |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | user_S0168_200        | true     |

    And set sys config String value null for sys config de.metas.externalsystem.externalservice.authorization.authToken

    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier  | OPT.AD_User_ID.Identifier |
      | note       | sysConfigNotFound_message | user_S0168_200            |


  @from:cucumber
  @Id:S0168_300
  Scenario: sys config is set but it doesn't match any `AD_User_AuthToken.AuthToken`, then a custom notification is sent to the configured user group
    Given load AD_Message:
      | Identifier               | Value                                           |
      | messageVerificationError | ExternalSystem_Authorization_Verification_Error |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           |
      | user_S0168_300        | user_S0168_300 |
    And load AD_UserGroup from AD_SysConfig:
      | AD_UserGroup_ID.Identifier | AD_SysConfig.Name                                                             |
      | userGroup_api              | de.metas.externalsystem.externalservice.authorization.notificationUserGroupId |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | user_S0168_300        | true     |

    And set sys config String value S0168_300 for sys config de.metas.externalsystem.externalservice.authorization.authToken

    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note       | messageVerificationError | user_S0168_300            |
