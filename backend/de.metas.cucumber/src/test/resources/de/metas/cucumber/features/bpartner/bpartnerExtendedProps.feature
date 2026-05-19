@from:cucumber
@allure.label.epic:E0180_System_Administration
@allure.label.feature:F9100_Manage_Windows_Tabs_Fields_other
@ghActions:run_on_executor5
Feature: BPartner v2 REST — extendedProps round-trip

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has current date and time

  Scenario: BPartner upsert + read-back via extendedProps round-trips two custom columns
    And update AD_Column:
      | TableName  | ColumnName  | OPT.IsRestAPICustomColumn |
      | C_BPartner | Phone2      | true                      |
      | C_BPartner | Description | true                      |

    And the metasfresh cache is reset
    And we wait for 2000 ms

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-xprops001",
         "bpartnerComposite":{
            "bpartner":{
               "name":"BPartner_extProps_1",
               "extendedProps":{
                  "Phone2":"555-test-phone2",
                  "Description":"test-extended-description"
               }
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

    When the metasfresh REST-API endpoint path 'api/v2/bpartner/ext-ALBERTA-xprops001' receives a 'GET' request with the headers from context, expecting status='200'
    Then the metasfresh REST-API responds with
    """
{
  "bpartner": {
    "extendedProps": {
      "Phone2": "555-test-phone2",
      "Description": "test-extended-description"
    }
  }
}
    """

  Scenario: BPartner upsert rejects extendedProps key whose column is not IsRestAPICustomColumn=Y
    And update AD_Column:
      | TableName  | ColumnName      | OPT.IsRestAPICustomColumn |
      | C_BPartner | DeliveryViaRule | false                     |
    And the metasfresh cache is reset
    And we wait for 2000 ms

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '422' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-ALBERTA-xprops002",
         "bpartnerComposite":{
            "bpartner":{
               "name":"BPartner_extProps_2",
               "extendedProps":{
                  "DeliveryViaRule":"D"
               }
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

    Then the metasfresh REST-API responds with
    """
{
  "errors": [
    {
      "message": "C_BPartner.DeliveryViaRule ist nicht als benutzerdefinierte REST API-Spalte markiert (AD_Column.IsRestAPICustomColumn=N)"
    }
  ]
}
    """
