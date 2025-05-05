@ghActions:run_on_executor5
Feature: data export audit using bpartner metasfresh api
  `When` a retrieve bpartner API call is made
  export audit data should be created

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the export audit data is reset

  Scenario: The request is good and the export audit data is created
    When the metasfresh REST-API endpoint path 'api/v2/bpartner/2156425' receives a 'GET' request
    Then process bpartner endpoint response
      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | C_Location_ID.Identifier |
      | bpartner_1               | bpartner_location_1               | location_1               |

    Then after not more than 10s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName           | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | bpartner_data_export            | C_BPartner          | bpartner_1           |                                        |
      | bp_location_data_export         | C_BPartner_Location | bpartner_location_1  | bpartner_data_export                   |
      | location_data_export            | C_Location          | location_1           | bp_location_data_export                |
    Then there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | bpartner_data_export            | Exported-Standalone      | null                                | null                       |
      | bp_location_data_export         | Exported-AlongWithParent | null                                | null                       |
      | location_data_export            | Exported-AlongWithParent | null                                | null                       |


  Scenario: The request is good with config and pinstance parameters and the export audit data is created
    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue    |
      | config_1                            | RabbitMQ | testRabbitMQ_dataAudit |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_1                            | p_1                        |
    When the metasfresh REST-API endpoint path 'api/v2/bpartner/2156425' receives a 'GET' request with the headers from context, expecting status='200'
    Then process bpartner endpoint response
      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | C_Location_ID.Identifier |
      | bpartner_1               | bpartner_location_1               | location_1               |
    Then after not more than 60s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName           | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | bpartner_data_export            | C_BPartner          | bpartner_1           |                                        |
      | bp_location_data_export         | C_BPartner_Location | bpartner_location_1  | bpartner_data_export                   |
      | location_data_export            | C_Location          | location_1           | bp_location_data_export                |
    Then there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | bpartner_data_export            | Exported-Standalone      | config_1                            | p_1                        |
      | bp_location_data_export         | Exported-AlongWithParent | config_1                            | p_1                        |
      | location_data_export            | Exported-AlongWithParent | config_1                            | p_1                        |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |


  Scenario: When C_BPartner_Location is changed, a proper camel-request is sent to rabbit-mq
    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue           | OPT.IsSyncBPartnersToRabbitMQ |
      | config_1                            | RabbitMQ | testRabbitMQ_exportToRabbitMQ | true                          |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_1                            | p_1                        |
    When the metasfresh REST-API endpoint path 'api/v2/bpartner/2156425' receives a 'GET' request with the headers from context, expecting status='200'
    And process bpartner endpoint response
      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | C_Location_ID.Identifier |
      | bpartner_1               | bpartner_location_1               | location_1               |
    And after not more than 60s, there are added records in Data_Export_Audit
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

  Scenario: When C_BPartner is changed, a proper camel-request is sent to GRS
    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue |
      | config_2                            | GRS  | testGRS             |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_2                            | p_2                        |
    When the metasfresh REST-API endpoint path 'api/v2/bpartner/2156423' receives a 'GET' request with the headers from context, expecting status='200'
    And process bpartner endpoint response
      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | C_Location_ID.Identifier |
      | bpartner_2               | bpartner_location_2               | location_2               |
    And after not more than 60s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName           | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | bpartner_data_export            | C_BPartner          | bpartner_2           |                                        |
      | bp_location_data_export         | C_BPartner_Location | bpartner_location_2  | bpartner_data_export                   |
      | location_data_export            | C_Location          | location_2           | bp_location_data_export                |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | bpartner_data_export            | Exported-Standalone      | config_2                            | p_2                        |
      | bp_location_data_export         | Exported-AlongWithParent | config_2                            | p_2                        |
      | location_data_export            | Exported-AlongWithParent | config_2                            | p_2                        |
    And RabbitMQ MF_TO_ExternalSystem queue is purged
    And the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.Name2 |
      | bpartner_2               | name2     |
    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and bpartnerId as parameters:
      | C_BPartner_ID.Identifier | ExternalSystem_Config_ID.Identifier |
      | bpartner_2               | config_2                            |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_2                            |

  Scenario: External reference data export audit
    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
    "requestItems": [
        {
            "bpartnerIdentifier": "ext-Shopware6-BPartner_ER_Audit_25032022",
            "externalReferenceUrl": "www.Shopware6.ro",
            "bpartnerComposite": {
                "bpartner": {
                    "code": "shopware6codeAudit",
                    "name": "shopware6nameAudit",
                    "companyName": "shopware6cmpAudit",
                    "language": "de"
                },
                "locations": {
                    "requestItems": [
                        {
                            "locationIdentifier": "ext-Shopware6-BPLocation_ER_Audit_25032022",
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
                            "contactIdentifier": "ext-Shopware6-BPContact_ER_Audit_25032022",
                            "contact": {
                                "code": "shopware6_BPContact_ER_Audit_25032022",
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
    And verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier                       | OPT.Code           | Name               | OPT.CompanyName   | OPT.Language |
      | created_bpartner         | ext-Shopware6-BPartner_ER_Audit_25032022 | shopware6codeAudit | shopware6nameAudit | shopware6cmpAudit | de           |

    And verify that S_ExternalReference was created
      | ExternalSystem | Type             | ExternalReference            | OPT.ExternalReferenceURL |
      | Shopware6      | BPartner         | BPartner_ER_Audit_25032022   | www.Shopware6.ro         |
      | Shopware6      | BPartnerLocation | BPLocation_ER_Audit_25032022 | null                     |
      | Shopware6      | UserID           | BPContact_ER_Audit_25032022  | null                     |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue    | OPT.IsSyncExternalReferencesToRabbitMQ |
      | config_1                            | RabbitMQ | externalReferenceAudit | true                                   |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_1                            | p_1                        |

    When a 'PUT' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/externalRefs/001' and fulfills with '200' status code
    """
  {
    "systemName": "Shopware6",
    "items": [
        {
            "externalReference": "BPartner_ER_Audit_25032022",
            "type": "BPartner"
        },
        {
            "externalReference": "BPLocation_ER_Audit_25032022",
            "type": "BPartnerLocation"
        },
        {
            "externalReference": "BPContact_ER_Audit_25032022",
            "type": "UserID"
        }
    ]
  }
    """

    Then process external reference lookup endpoint response
      | S_ExternalReference_ID.Identifier | ExternalReference            |
      | externalRef_BPartner              | BPartner_ER_Audit_25032022   |
      | externalRef_BPLocation            | BPLocation_ER_Audit_25032022 |
      | externalRef_BPContact             | BPContact_ER_Audit_25032022  |
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
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |