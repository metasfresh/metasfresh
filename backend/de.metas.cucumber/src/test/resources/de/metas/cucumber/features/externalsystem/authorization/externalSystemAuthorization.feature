@from:cucumber
Feature: Camel-ExternalSystem authorization

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Note table is reset

  @from:cucumber
  @Id:S0168_100
  Scenario: sys config is set and matches `AD_User_AuthToken.AuthToken`, then JsonExternalSystemMessage is send to RabbitMQ
    Given metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken           | 100                   | 540024     | authToken |

    And set sys config String value authToken for sys config de.metas.externalsystem.externalservice.authorization.authToken

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with JsonExternalSystemMessagePayload authToken

  @from:cucumber
  @Id:S0168_200
  Scenario: sys config is missing, then UserNotification is send to the configured user group
    Given metasfresh contains AD_Message:
      | Identifier      | Value                                                    |
      | messageNotFound | External_Systems_Authorization_SysConfig_Not_Found_Error |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | metasfresh_user       | metasfresh |
    And metasfresh contains AD_UserGroup:
      | AD_UserGroup_ID.Identifier | Name      | IsActive |
      | userGroup_api              | API-Setup | true     |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | metasfresh_user       | true     |
    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken           | metasfresh_user       | 540024     | token     |

    And set sys config String value null for sys config de.metas.externalsystem.externalservice.authorization.authToken

    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note       | messageNotFound          | metasfresh_user           |


  @from:cucumber
  @Id:S0168_300
  Scenario: sys config is set but is does not match `AD_User_AuthToken.AuthToken`, then UserNotification is send to the configured user group
    Given metasfresh contains AD_Message:
      | Identifier               | Value                                           |
      | messageVerificationError | ExternalSystem_Authorization_Verification_Error |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | metasfresh_user       | metasfresh |
    And metasfresh contains AD_UserGroup:
      | AD_UserGroup_ID.Identifier | Name      | IsActive |
      | userGroup_api              | API-Setup | true     |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_api                    | userGroup_api              | metasfresh_user       | true     |
    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken           | metasfresh_user       | 540024     | token     |

    And set sys config String value testToken for sys config de.metas.externalsystem.externalservice.authorization.authToken

    And reset all cache

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note       | messageVerificationError | metasfresh_user           |
