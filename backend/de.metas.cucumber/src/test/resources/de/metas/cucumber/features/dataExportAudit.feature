Feature: data export audit using bpartner metasfresh api
  `When` a retrieve bpartner API call is made
  export audit data should be created

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
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
    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue |
      | config_1                            | RabbitMQ | testRabbitMQ        |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | config_1                            | p_1                        |
    When the metasfresh REST-API endpoint path 'api/v2/bpartner/2156425' receives a 'GET' request with the headers from context
    Then process bpartner endpoint response
      | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | C_Location_ID.Identifier |
      | bpartner_1               | bpartner_location_1               | location_1               |
    Then after not more than 30s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName           | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | bpartner_data_export            | C_BPartner          | bpartner_1           |                                        |
      | bp_location_data_export         | C_BPartner_Location | bpartner_location_1  | bpartner_data_export                   |
      | location_data_export            | C_Location          | location_1           | bp_location_data_export                |
    Then there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | bpartner_data_export            | Exported-Standalone      | config_1                            | p_1                        |
      | bp_location_data_export         | Exported-AlongWithParent | config_1                            | p_1                        |
      | location_data_export            | Exported-AlongWithParent | config_1                            | p_1                        |


  Scenario: When C_BPartner_Location is changed, a proper camel-request is sent to rabbit-mq
    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type     | ExternalSystemValue |
      | config_1                            | RabbitMQ | testRabbitMQ        |
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