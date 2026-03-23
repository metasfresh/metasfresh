@ghActions:run_on_executor5
Feature: Validate BPartner is sent to RabbitMQ

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And RabbitMQ MF_TO_ExternalSystem queue is purged

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
        "ifExists": "DONT_UPDATE"
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