@from:cucumber
@ghActions:run_on_executor5
Feature: Camel-ExternalSystem authorization

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Note table is reset
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | OPT.AD_User_ID | Name                    | OPT.EMail                         | OPT.Login |
      | externalSystemUser    | 10168          | externalSystemUser_0168 | externalSystemUser_0168@email.com | es_0168   |
    And load AD_Roles
      | AD_Role_ID.Identifier | Name  |
      | WebUIRole             | WebUI |
    And user has role
      | AD_User_ID.Identifier | AD_Role_ID.Identifier |
      | externalSystemUser    | WebUIRole             |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier      | Name                       | OPT.EMail                            | OPT.NotificationType |
      | user_to_get_notified_S0168 | user_to_get_notified_S0168 | user_to_get_notified_S0168@email.com | N                    |
    And load AD_UserGroup from AD_SysConfig:
      | AD_UserGroup_ID.Identifier | AD_SysConfig.Name                                                             |
      | userGroup_api              | de.metas.externalsystem.externalservice.authorization.notificationUserGroupId |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier      | IsActive |
      | userGroupAssign_api                    | userGroup_api              | user_to_get_notified_S0168 | true     |

  @from:cucumber
  @Id:S0168_100
  Scenario: Both sys configs are set and no `AD_User_AuthToken` record found for provided pair, an `AD_User_AuthToken` record will be created and then JsonExternalSystemMessage containing the generated authToken is sent to RabbitMQ
    Given no AD_User_AuthToken records for userId:externalSystemUser
    # 10168 -> externalSystemUser
    And set sys config String value 10168 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    # 540024 -> WebUI
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request
      | JsonExternalSystemMessage.type |
      | REQUEST_AUTHORIZATION          |

    Then after not more than 60s, validate AD_User_AuthToken record
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID.Identifier |
      | cucumberUserAuthToken_100       | externalSystemUser    | WebUIRole             |

    And metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with authToken from cucumberUserAuthToken_100


  @from:cucumber
  @Id:S0168_200
  Scenario: Both sys configs are set and there is an `AD_User_AuthToken` record found for provided pair, then JsonExternalSystemMessage containing the authToken is sent to RabbitMQ
    Given no AD_User_AuthToken records for userId:externalSystemUser
    # 10168 -> externalSystemUser
    And set sys config String value 10168 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    ## 540024 -> WebUI
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID

    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken |
      | cucumberUserAuthToken_200       | externalSystemUser    | 540024     | S0168_200 |

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request
      | JsonExternalSystemMessage.type |
      | REQUEST_AUTHORIZATION          |

    Then metasfresh replies on Custom_Metasfresh_To_ExternalSystem queue with JsonExternalSystemMessagePayload S0168_200


  @from:cucumber
  @Id:S0168_300
  Scenario: One sys config is missing, then a custom notification is sent to the configured user group
    Given no AD_User_AuthToken records for userId:externalSystemUser
    And set sys config String value null for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    ## 540024 -> WebUI
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID
    And reset all cache

    And load AD_Message:
      | Identifier               | Value                                                    |
      | messageSysConfigNotFound | External_Systems_Authorization_SysConfig_Not_Found_Error |

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request
      | JsonExternalSystemMessage.type |
      | REQUEST_AUTHORIZATION          |

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier  |
      | note       | messageSysConfigNotFound | user_to_get_notified_S0168 |


  @from:cucumber
  @Id:S0168_400
  Scenario: Both sys configs are set but AD_Role_ID doesn't match any role associated with provided AD_User_ID, then a custom notification is sent to the configured user group
    Given no AD_User_AuthToken records for userId:externalSystemUser
    # 10168 -> externalSystemUser
    And set sys config String value 10168 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    # 1000000 -> Admin
    And set sys config String value 1000000 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID
    And reset all cache

    And load AD_Message:
      | Identifier          | Value                                                  |
      | roleNotFoundForUser | External_Systems_Authorization_Role_Not_Found_For_User |

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request
      | JsonExternalSystemMessage.type |
      | REQUEST_AUTHORIZATION          |

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier  |
      | note       | roleNotFoundForUser      | user_to_get_notified_S0168 |


  @from:cucumber
  @Id:S0168_500
  Scenario: Both sys config are set but there are multiple `AD_User_AuthToken` records found for provided pair, then a custom notification is sent to the configured user group
    Given no AD_User_AuthToken records for userId:externalSystemUser
    ## 10168 -> externalSystemUser
    And set sys config String value 10168 for sys config de.metas.externalsystem.externalservice.authorization.AD_User_ID
    ## 540024 -> WebUI
    And set sys config String value 540024 for sys config de.metas.externalsystem.externalservice.authorization.AD_Role_ID
    And reset all cache

    And metasfresh contains AD_User_AuthToken:
      | AD_User_AuthToken_ID.Identifier | AD_User_ID.Identifier | AD_Role_ID | AuthToken   |
      | cucumberUserAuthToken_500_1     | externalSystemUser    | 540024     | S0168_500_1 |
      | cucumberUserAuthToken_500_2     | externalSystemUser    | 540024     | S0168_500_2 |

    And load AD_Message:
      | Identifier               | Value                                           |
      | messageVerificationError | ExternalSystem_Authorization_Verification_Error |

    When Custom_ExternalSystem_To_Metasfresh queue receives an JsonExternalSystemMessage request
      | JsonExternalSystemMessage.type |
      | REQUEST_AUTHORIZATION          |

    Then after not more than 10s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier  |
      | note       | messageVerificationError | user_to_get_notified_S0168 |
