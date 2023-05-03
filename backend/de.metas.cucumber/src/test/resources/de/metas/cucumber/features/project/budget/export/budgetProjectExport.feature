Feature: Budget Project interaction with RabbitMQ after export process was triggered

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: Create Budget Project and send BudgetProjectID to RabbitMQ - export process is triggered on project creation
    Given RabbitMQ MF_TO_ExternalSystem queue is purged

    And add Other external system config with identifier: otherConfig
      | Name                | Value   |
      | BaseURL             | baseURL |
      | Token               | token   |
      | ExportBudgetProject | true    |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-exportProjectTest",
    "orgCode" : "001",
    "value" : "valueTestExport",
    "name" : "nameTestExport",
    "priceListVersionId" : 2002141,
    "projectTypeId" : 540005,
    "currencyCode" : "EUR",
    "description" : "descriptionTestExport",
    "dateContract" : "2022-01-10",
    "dateFinish" : "2022-01-11",
    "bpartnerId" : 2156423,
    "projectReferenceExt" : "projectReferenceExtTest",
    "active" : true,
    "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""

    And process budget project upsert response
      | C_Project_ID.Identifier |
      | projectToExport         |

    And validate the created projects
      | C_Project_ID.Identifier | OPT.Name       | OPT.Description       | OPT.C_BPartner_ID.Identifier | OPT.C_ProjectType_ID.Identifier | OPT.IsActive |
      | projectToExport         | nameTestExport | descriptionTestExport | 2156423                      | 540005                          | true         |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.parameters.C_Project_ID.Identifier |
      | otherConfig                         | projectToExport                        |
