Feature: Validate BPartner is sent to RabbitMQ

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: Export bpartner when created via rest-api
    Given metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name     | OPT.EMail          | OPT.Login |
      | testUser_1            | testUser | testUser@email.com | testUser  |
    And load AD_Roles
      | AD_Role_ID.Identifier | Name  |
      | userRole              | WebUI |
    And user has role
      | AD_User_ID.Identifier | AD_Role_ID.Identifier |
      | testUser_1            | userRole              |
    And the existing user with login 'testUser' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh contains AD_UserGroup:
      | AD_UserGroup_ID.Identifier | Name        | IsActive | OPT.Description         |
      | userGroup_1                | userGroup_1 | true     | userGroup_1 description |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_1                      | userGroup_1                | testUser_1            | true     |
    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue | OPT.IsAutoSendWhenCreatedByUserGroup | OPT.SubjectCreatedByUserGroup_ID.Identifier | OPT.IsSyncBPartnersToRabbitMQ |
      | config_1                            | RabbitMQ | autoExportRabbitMQ  | true                                 | userGroup_1                                 | true                          |

    And RabbitMQ MF_TO_ExternalSystem queue is purged
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
    "requestItems": [
        {
            "bpartnerIdentifier": "ext-Shopware6-001",
            "bpartnerComposite": {
                "bpartner": {
                    "code": "test_code_export",
                    "name": "test_name_export",
                    "companyName": "test_company_export",
                    "language": "de"
                }
            }
        }
    ],
    "syncAdvise": {
        "ifNotExists": "CREATE",
        "ifExists": "UPDATE_MERGE"
    }
}
"""
    Then verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code         | Name             | OPT.CompanyName     | OPT.CreatedBy | OPT.Language |
      | created_bpartner         | ext-Shopware6-001  | test_code_export | test_name_export | test_company_export | testUser_1    | de           |
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and bpartnerId as parameters:
      | C_BPartner_ID.Identifier | ExternalSystem_Config_ID.Identifier |
      | created_bpartner         | config_1                            |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  Scenario: When C_BPartner_Location is changed, a proper camel-request is sent to rabbit-mq
    Given all the export audit data is reset
    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue   | OPT.IsSyncBPartnersToRabbitMQ |
      | config_1                            | RabbitMQ | testRabbitMQ_26032022 | true                          |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_1                            | p_1                        |
    When the metasfresh REST-API endpoint path 'api/v2/bpartner/2156425' receives a 'GET' request with the headers from context
    And process bpartner endpoint response
      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | C_Location_ID.Identifier |
      | bpartner_1               | bpartner_location_1               | location_1               |
    And after not more than 30s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName           | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | bpartner_data_export            | C_BPartner          | bpartner_1           |                                        |
      | bp_location_data_export         | C_BPartner_Location | bpartner_location_1  | bpartner_data_export                   |
      | location_data_export            | C_Location          | location_1           | bp_location_data_export                |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | bpartner_data_export            | Exported-Standalone      | config_1                            | p_1                        |
      | bp_location_data_export         | Exported-AlongWithParent | config_1                            | p_1                        |
      | location_data_export            | Exported-AlongWithParent | config_1                            | p_1                        |
    And RabbitMQ MF_TO_ExternalSystem queue is purged
    And the following c_bpartner_location is changed
      | C_BPartner_Location_ID.Identifier |
      | bpartner_location_1               |
    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and bpartnerId as parameters:
      | C_BPartner_ID.Identifier | ExternalSystem_Config_ID.Identifier |
      | bpartner_1               | config_1                            |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |