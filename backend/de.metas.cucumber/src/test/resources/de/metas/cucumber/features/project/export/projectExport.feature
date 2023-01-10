Feature: Validate project is sent to RabbitMQ

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

#  @runThisOne
  Scenario:
    Given RabbitMQ MF_TO_ExternalSystem queue is purged

    And add Other external system config with identifier: otherConfig
      | Name                | Value   |
      | BaseURL             | baseURL |
      | Token               | token   |
      | ExportBudgetProject | true    |

    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

#    And metasfresh contains C_Project
#      | OPT.C_Project_ID.Identifier | Name        | C_Currency_ID.ISO_Code |
#      | projectToExport             | ProjectName | EUR                    |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testProject",
    "orgCode" : "001",
    "value" : "valueTest",
    "name" : "nameTest",
    "priceListVersionId" : 2002141,
    "projectTypeId" : 540005,
    "currencyCode" : "EUR",
    "description" : "descriptionTest",
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
      | C_Project_ID.Identifier | OPT.Name | OPT.Description | OPT.C_BPartner_ID.Identifier | OPT.C_ProjectType_ID.Identifier | OPT.IsActive |
      | projectToExport         | nameTest | descriptionTest | 2156423                      | 540005                          | true         |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.parameters.C_Project_ID.Identifier |
      | otherConfig                         | projectToExport                        |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | otherConfig                         |
