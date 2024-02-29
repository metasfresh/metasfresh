@ghActions:run_on_executor5
Feature: Validate external reference is sent to RabbitMQ

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

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


  Scenario: Export external reference when updated, if it was previously exported.
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
    "requestItems": [
        {
            "bpartnerIdentifier": "ext-Shopware6-BPartner_ER_S2_25032022",
            "externalReferenceUrl": "www.Shopware6.ro",
            "bpartnerComposite": {
                "bpartner": {
                    "code": "shopware6code_25032022",
                    "name": "shopware6nameAudit",
                    "companyName": "shopware6cmpAudit",
                    "language": "de"
                },
                "locations": {
                    "requestItems": [
                        {
                            "locationIdentifier": "ext-Shopware6-BPLocation_ER_S2_25032022",
                            "location": {
                                "address1": "shopware6testAudit",
                                "countryCode": "DE"
                            }
                        }
                    ]
                },
                "contacts": {
                    "requestItems": [
                        {
                            "contactIdentifier": "ext-Shopware6-BPContact_ER_S2_25032022",
                            "contact": {
                                "code": "shopware6BPContactCode_25032022",
                                "firstName": "shopware6_firstNameAudit",
                                "lastName": "shopware6_lastNameAudit"
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
      | C_BPartner_ID.Identifier | externalIdentifier                    | OPT.Code               | Name               | OPT.CompanyName   | OPT.Language |
      | created_bpartner         | ext-Shopware6-BPartner_ER_S2_25032022 | shopware6code_25032022 | shopware6nameAudit | shopware6cmpAudit | de           |

    And verify that S_ExternalReference was created
      | ExternalSystem | Type             | ExternalReference         | ExternalReferenceURL |
      | Shopware6      | BPartner         | BPartner_ER_S2_25032022   | www.Shopware6.ro     |
      | Shopware6      | BPartnerLocation | BPLocation_ER_S2_25032022 | null                 |
      | Shopware6      | UserID           | BPContact_ER_S2_25032022  | null                 |

    # FIXME: temporary fix to make sure the syncExternalReferenceDebouncer is empty before adding a new config (it's a temp fix as the full solution is already in master)
    And wait for 10s

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue       | OPT.IsSyncExternalReferencesToRabbitMQ |
      | config_1                            | RabbitMQ | externalReferenceAudit_S2 | true                                   |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_1                            | p_1                        |

    When a 'PUT' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/externalRefs/001' and fulfills with '200' status code
    """
  {
    "systemName": "Shopware6",
    "items": [
        {
            "id": "BPartner_ER_S2_25032022",
            "type": "BPartner"
        },
        {
            "id": "BPLocation_ER_S2_25032022",
            "type": "BPartnerLocation"
        },
        {
            "id": "BPContact_ER_S2_25032022",
            "type": "UserID"
        }
    ]
  }
    """

    Then process external reference lookup endpoint response
      | S_ExternalReference_ID.Identifier | ExternalReference         |
      | externalRef_BPartner              | BPartner_ER_S2_25032022   |
      | externalRef_BPLocation            | BPLocation_ER_S2_25032022 |
      | externalRef_BPContact             | BPContact_ER_S2_25032022  |
    And after not more than 60s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName           | Record_ID.Identifier   | Data_Export_Audit_Parent_ID.Identifier |
      | dataExport_BPartner             | S_ExternalReference | externalRef_BPartner   |                                        |
      | dataExport_BPLocation           | S_ExternalReference | externalRef_BPLocation |                                        |
      | dataExport_BPContact            | S_ExternalReference | externalRef_BPContact  |                                        |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action  | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | dataExport_BPartner             | Exported-Standalone | config_1                            | p_1                        |
      | dataExport_BPLocation           | Exported-Standalone | config_1                            | p_1                        |
      | dataExport_BPContact            | Exported-Standalone | config_1                            | p_1                        |

    And the following S_ExternalReference is changed:
      | S_ExternalReference_ID.Identifier | OPT.Version |
      | externalRef_BPartner              | version_1   |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.parameters.JsonExternalReferenceLookupRequest                                                                                                                                                      |
      | config_1                            | {"systemName":"Shopware6","items":[{"id":"BPartner_ER_S2_25032022","type":"BPartner"},{"id":"BPLocation_ER_S2_25032022","type":"BPartnerLocation"},{"id":"BPContact_ER_S2_25032022","type":"UserID"}]} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |