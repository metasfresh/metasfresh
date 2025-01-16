@from:cucumber
Feature: Error handling during: create or update BPartner v2
  As a user
  I want create or update a BPartner record and validate error handling

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'


  Scenario: Setting bPartner.name to null generates a validation error
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '422' status code
    """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"gln-gln123456",
         "bpartnerComposite":{
            "bpartner":{
               "code":"EH_MissingName",
               "name":null,
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":"url_updated",
               "group":"test-group",
               "vatId":null,
               "urproduzent":false
            },
            "locations":{
               "requestItems":[
                  {
                     "locationIdentifier":"gln-gln123456",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                        "address1":null,
                        "address2":"test_address2",
                        "poBox":"test_poBox",
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":"gln123456",
                        "postal":null,
                        "vatId": null
                     }
                  }
               ]
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
    Then validate the following content is present in the response
      | Content                                                     |
      | Fehler, C_BPartner.Name kann nicht auf null gesetzt werden. |


  Scenario: Setting bPartner.companyName to a different value than bpartner.name generates a validation error
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '422' status code
   """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"gln-gln123456",
         "bpartnerComposite":{
            "bpartner":{
               "code":"DiffNameAndCmpName",
               "name":"name",
               "companyName":"cmp_name",
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":"url_updated",
               "group":"test-group",
               "vatId":null,
               "urproduzent":false
            },
            "locations":{
               "requestItems":[
                  {
                     "locationIdentifier":"gln-gln123456",
                     "isReadOnlyInMetasfresh": false,
                     "location":{
                        "address1":null,
                        "address2":"test_address2",
                        "poBox":"test_poBox",
                        "district":null,
                        "region":"test_region",
                        "city":"test_city",
                        "countryCode":"DE",
                        "gln":"gln123456",
                        "postal":null,
                        "vatId": null
                     }
                  }
               ]
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

    Then validate the following content is present in the response
      | Content                             |
      | Fehler, inkonsistente Eigenschaften |
