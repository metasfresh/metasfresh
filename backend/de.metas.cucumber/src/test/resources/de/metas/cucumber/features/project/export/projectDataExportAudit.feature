@from:cucumber
Feature: Project data export audit

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the export audit data is reset
    And metasfresh has date and time 2023-01-10T13:30:13+01:00[Europe/Berlin]

  @runThisOne
  Scenario:Project data export audit with external system config and pinstance is created
    Given add Other external system config with identifier: otherConfig
      | Name    | Value   |
      | BaseURL | baseURL |
      | Token   | token   |

    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

#    And metasfresh contains C_Project
#      | OPT.C_Project_ID.Identifier | Name             | C_Currency_ID.ISO_Code |
#      | projectToExportAudit        | ProjectNameAudit | EUR                    |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testProjectAudit",
    "orgCode" : "001",
    "value" : "valueTestAudit",
    "name" : "nameTestAudit",
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
      | projectToExportAudit    |

    And build 'GET' budget project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | projectToExportAudit    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate budget project 'GET' response
  """
  {
    "requestId": 1000059,
    "endpointResponse": {
        "projectId": 1,
        "orgCode": "001",
        "currencyCode": "EUR",
        "name": "testName",
        "value": "testValue",
        "isActive": true,
        "priceListVersionId": 2002141,
        "description": "testDescription",
        "projectParentId": 2000000,
        "projectTypeId": 540005,
        "projectReferenceExt": "testReferenceExt",
        "externalId": "testExternalId1",
        "bpartnerId": 2156423,
        "salesRepId": 100,
        "dateContract": "2021-05-13",
        "dateFinish": "2021-05-14"
    }
}
  """

    And after not more than 30s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | dataExport_BudgetProject        | C_Project | projectToExportAudit |                                        |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action  | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | dataExport_BudgetProject        | Exported-Standalone | otherConfig                         | pInstance                  |
