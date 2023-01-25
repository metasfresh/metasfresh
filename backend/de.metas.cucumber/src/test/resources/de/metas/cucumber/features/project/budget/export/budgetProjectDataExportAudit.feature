@from:cucumber
Feature: Budget project data export audit

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the export audit data is reset
    And metasfresh has date and time 2023-01-10T13:30:13+01:00[Europe/Berlin]

  Scenario:Project data export audit with external system config and pinstance is created when Budget Project is retrieved via API
    Given add Other external system config with identifier: otherConfig
      | Name    | Value   |
      | BaseURL | baseURL |
      | Token   | token   |

    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And metasfresh contains S_Resource_Group with the following id:
      | S_Resource_Group_ID | M_Product_Category_ID.Identifier | Name     | DurationUnit |
      | 4000000             | standard_category                | testName | h            |

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
    "dateContract" : "2023-01-10",
    "dateFinish" : "2023-01-11",
    "bpartnerId" : 2156423,
    "projectReferenceExt" : "projectReferenceExtTest",
    "active" : true,
     "resources" : [
        {
            "resourceIdentifier" : 540006,
            "externalId" : "testExternalId",
            "uomTimeId" : 101,
            "dateStartPlan" : "2023-01-10",
            "dateFinishPlan" : "2023-01-11",
            "description" : "resourceTestDescription",
            "resourceGroupId" : 4000000,
            "plannedAmt" : {
                "amount" : 100,
                "currencyCode" : "EUR"
            },
            "plannedDuration" : 2,
            "pricePerTimeUOM" : {
                "amount" : 100,
                "currencyCode" : "EUR"
            },
            "active" : true
        }
    ],
    "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""
    And process budget project upsert response
      | C_Project_ID.Identifier | OPT.C_Project_Resource_Budget_ID.Identifier |
      | projectToExportAudit    | projectBudgetResourceAudit                  |

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
        "name": "nameTestAudit",
        "value": "valueTestAudit",
        "isActive": true,
        "priceListVersionId": 2002141,
        "description": "descriptionTest",
        "projectTypeId": 540005,
        "projectReferenceExt": "projectReferenceExtTest",
        "bpartnerId": 2156423,
        "dateContract": "2023-01-10",
        "dateFinish": "2023-01-11",
        "extendedProps": {},
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 101,
                "dateStartPlan": "2023-01-10",
                "dateFinishPlan": "2023-01-11",
                "description": "resourceTestDescription",
                "plannedAmt": 100,
                "currencyCode": "EUR",
                "plannedDuration": 2,
                "pricePerTimeUOM": 100,
                "resourceGroupId": 4000000,
                "resourceId": 540006,
                "externalId": "testExternalId",
                "isActive": true
            }
        ]
    }
}
  """

    And after not more than 30s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier  | TableName                 | Record_ID.Identifier       | Data_Export_Audit_Parent_ID.Identifier |
      | dataExport_BudgetProject         | C_Project                 | projectToExportAudit       |                                        |
      | dataExport_BudgetProjectResource | C_Project_Resource_Budget | projectBudgetResourceAudit | dataExport_BudgetProject               |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier  | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | dataExport_BudgetProject         | Exported-Standalone      | otherConfig                         | pInstance                  |
      | dataExport_BudgetProjectResource | Exported-AlongWithParent | otherConfig                         | pInstance                  |
