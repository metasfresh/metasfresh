Feature: Validate external reference is sent to RabbitMQ

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: Export external reference and c_bpartner when created by RabbitMQ.SubjectCreatedByUserGroup_ID
    Given metasfresh contains AD_UserGroup:
      | AD_UserGroup_ID.Identifier | Name                  | IsActive | OPT.Description                   |
      | userGroup_externalRef      | userGroup_externalRef | true     | userGroup_externalRef description |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | metasfresh_user       | metasfresh |
    And metasfresh contains AD_UserGroup_User_Assign:
      | AD_UserGroup_User_Assign_ID.Identifier | AD_UserGroup_ID.Identifier | AD_User_ID.Identifier | IsActive |
      | userGroupAssign_1                      | userGroup_externalRef      | metasfresh_user       | true     |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue                       | OPT.IsSyncExternalReferencesToRabbitMQ | OPT.IsAutoSendWhenCreatedByUserGroup | OPT.IsSyncBPartnersToRabbitMQ | OPT.SubjectCreatedByUserGroup_ID.Identifier |
      | config_1                            | RabbitMQ | syncExternalReferenceExportRabbitMQUpdate | true                                   | true                                 | true                          | userGroup_externalRef                       |
      | config_noAutoSync                   | RabbitMQ | autoExportRabbitMQExternalRef             | true                                   |                                      | false                         |                                             |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
    "requestItems": [
        {
            "bpartnerIdentifier": "ext-Shopware6-BPartner_ER_25032022",
            "externalReferenceUrl": "www.Shopware6.ro",
            "bpartnerComposite": {
                "bpartner": {
                    "code": "shopware6code",
                    "name": "shopware6name",
                    "companyName": "shopware6cmp",
                    "language": "de"
                },
                "locations": {
                    "requestItems": [
                        {
                            "locationIdentifier": "ext-Shopware6-BPLocation_ER_25032022",
                            "location": {
                                "address1": "shopware6tes",
                                "countryCode": "DE"
                            }
                        }
                    ]
                },
                "contacts": {
                    "requestItems": [
                        {
                            "contactIdentifier": "ext-Shopware6-BPContact_ER_25032022",
                            "contact": {
                                "code": "shopware6_user",
                                "firstName": "shopware6_firstName",
                                "lastName": "shopware6_lastName"
                            }
                        }
                    ]
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
      | C_BPartner_ID.Identifier | externalIdentifier                 | OPT.Code      | Name          | OPT.CompanyName | OPT.Language |
      | created_bpartner         | ext-Shopware6-BPartner_ER_25032022 | shopware6code | shopware6name | shopware6cmp    | de           |

    And verify that S_ExternalReference was created
      | ExternalSystem | Type             | ExternalReference      | ExternalReferenceURL |
      | Shopware6      | BPartner         | BPartner_ER_25032022   | www.Shopware6.ro     |
      | Shopware6      | BPartnerLocation | BPLocation_ER_25032022 | null                 |
      | Shopware6      | UserID           | BPContact_ER_25032022  | null                 |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.parameters.C_BPartner_ID.Identifier | OPT.parameters.JsonExternalReferenceLookupRequest                                                                                                                                             |
      | config_1                            |                                         | {"systemName":"Shopware6","items":[{"id":"BPLocation_ER_25032022","type":"BPartnerLocation"}]}                                                                                                |
      | config_1                            |                                         | {"systemName":"Shopware6","items":[{"id":"BPContact_ER_25032022","type":"UserID"}]}                                                                                                           |
      | config_1                            |                                         | {"systemName":"Shopware6","items":[{"id":"BPartner_ER_25032022","type":"BPartner"},{"id":"BPLocation_ER_25032022","type":"BPartnerLocation"},{"id":"BPContact_ER_25032022","type":"UserID"}]} |
      | config_1                            | created_bpartner                        |                                                                                                                                                                                               |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |
      | config_noAutoSync                   |