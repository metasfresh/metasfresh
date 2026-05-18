@from:cucumber
@allure.label.epic:F9100_Manage_Windows_Tabs_Fields_other
@allure.label.feature:F9100
@ghActions:run_on_executor5
Feature: BPartner v2 REST — extendedProps round-trip

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has current date and time

  Scenario: BPartner upsert + read-back via extendedProps round-trips two custom columns
    Given metasfresh contains C_BPartners:
      | Identifier | Name                | OPT.IsVendor | OPT.IsCustomer |
      | bp_xprops  | BPartner_extProps_1 | N            | Y              |

    And update AD_Column:
      | TableName  | ColumnName  | OPT.IsRestAPICustomColumn |
      | C_BPartner | Phone2      | true                      |
      | C_BPartner | Description | true                      |

    And the metasfresh cache is reset
    And we wait for 2000 ms

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/ext-xprops-001' and fulfills with '201' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-xprops-001",
         "bpartnerComposite":{
            "bpartner":{
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

    Then the metasfresh REST-API responds with
    """
{
  "responseItems": [
    {
      "bpartnerComposite": {
        "bpartner": {
          "extendedProps": {
            "Phone2": "555-test-phone2",
            "Description": "test-extended-description"
          }
        }
      }
    }
  ]
}
    """

  Scenario: BPartner upsert rejects extendedProps key whose column is not IsRestAPICustomColumn=Y
    Given metasfresh contains C_BPartners:
      | Identifier | Name                | OPT.IsVendor | OPT.IsCustomer |
      | bp_xprops2 | BPartner_extProps_2 | N            | Y              |

    And the metasfresh cache is reset
    And we wait for 2000 ms

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/ext-xprops-002' and fulfills with '422' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-xprops-002",
         "bpartnerComposite":{
            "bpartner":{
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
