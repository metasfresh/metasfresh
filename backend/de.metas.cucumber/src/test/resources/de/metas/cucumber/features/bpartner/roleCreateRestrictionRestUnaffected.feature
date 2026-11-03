@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E0180_System_Administration
@allure.label.feature:F33020_Roles
Feature: A role's WebUI create-restriction does not affect the REST API

  ## F33020: Roles

  - The AD_Table_Access "cannot create new records" restriction is enforced only in the WebUI document layer.
  - A role blocked from creating C_BPartner in the WebUI can still create one via api/v2/bpartner.
  - Integrations keep working while the WebUI "New" button only greys out for interactive users.

  Background:
    Given infrastructure and metasfresh are running

  @Id:S27893_TC10
  Scenario: A role restricted from creating C_BPartner in the WebUI still creates a partner via the REST API
    Given metasfresh contains AD_Roles including the WebUI role:
      | AD_Role_ID     |
      | restrictedRole |
    And metasfresh contains AD_Table_Access:
      | AD_Role_ID     | TableName  | IsCanCreateNewRecords |
      | restrictedRole | C_BPartner | false                 |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | metasfreshUser        | metasfresh |
    And user has role
      | AD_User_ID     | AD_Role_ID     |
      | metasfreshUser | restrictedRole |
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with identifier 'restrictedRole'
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-role_restriction_bp",
         "bpartnerComposite":{
            "bpartner":{
               "code":"restricted_role_code",
               "name":"restricted_role_name",
               "companyName":"restricted_role_company",
               "language":"de"
            }
         }
      }
   ],
   "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""
    Then verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier              | code                 | name                 | companyName             | language |
      | created_bpartner         | ext-ALBERTA-role_restriction_bp | restricted_role_code | restricted_role_name | restricted_role_company | de       |
