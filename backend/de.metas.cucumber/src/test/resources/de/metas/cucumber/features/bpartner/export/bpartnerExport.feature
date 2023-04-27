Feature: BPartner interaction with RabbitMQ after export process was triggered

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And RabbitMQ MF_TO_ExternalSystem queue is purged

  Scenario: Create BPartner having one BPartnerLocation and send BPartnerID to RabbitMQ - export process is triggered on partner creation
    Given add Other external system config with identifier: otherConfig
      | Name                             | Value   |
      | BaseURL                          | baseURL |
      | Token                            | token   |
      | ExportBPartner                   | true    |
      | AutoExportDefaultShippingAddress | true    |

    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
     """
{
   "requestItems":[
      {
         "bpartnerIdentifier":"ext-Other-001",
         "bpartnerComposite":{
            "bpartner":{
               "code":"TestCode",
               "name":"BPartnerName",
               "companyName":"TestCompany",
               "parentId":null,
               "phone":null,
               "language":"de",
               "url":null,
               "group":"test-group",
               "vatId":null
            },
            "locations":{
               "requestItems":[
                  {
                     "locationIdentifier":"gln-1231231231237",
                     "location":{
                        "address1":"Address 111",
                        "address2":"Address 222",
                        "poBox":null,
                        "district":null,
                        "region":null,
                        "city": "City",
                        "countryCode":"DE",
                        "gln":null,
                        "postal":null,
                        "shipToDefault": true
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
    Then verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | OPT.Code | Name         | OPT.CompanyName | OPT.Language |
      | bpartner                 | ext-Other-001      | TestCode | BPartnerName | TestCompany     | de           |
    And verify that location was created for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.Address1 | OPT.Address2 | OPT.City | CountryCode | OPT.Gln       | OPT.IsShipToDefault |
      | ext-Other-001      | gln-1231231231237  | Address 111  | Address 222  | City     | DE          | 1231231231237 | Y                   |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and bpartnerId as parameters:
      | C_BPartner_ID.Identifier | ExternalSystem_Config_ID.Identifier |
      | bpartner                 | otherConfig                         |

