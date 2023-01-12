Feature: Validate work order project is sent to RabbitMQ

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

    @runThisOne
  Scenario: Work order project is exported when created
    Given RabbitMQ MF_TO_ExternalSystem queue is purged

    And add Other external system config with identifier: otherConfig
      | Name         | Value   |
      | BaseURL      | baseURL |
      | Token        | token   |
      | ExportWOStep | true    |

    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-externalIdExportWO",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "value": "valueTestExportWO",
  "name": "nameTestExportWO",
  "priceListVersionId": 2002141,
  "currencyCode": "EUR",
  "salesRepId": 100,
  "description": "testDescription",
  "dateContract": "2023-01-06",
  "dateFinish": "2023-01-15",
  "bpartnerId": 2156423,
  "projectReferenceExt": "woReferenceExt",
  "externalId": "externalIdExportWO",
  "orgCode": "001",
  "isActive": true,
  "bpartnerDepartment": "bpartnerDepartmentTest",
  "woOwner": "woOwnerTest",
  "poReference": "RDCBCO212ABN",
  "bpartnerTargetDate": "2023-02-08",
  "woProjectCreatedDate": "2023-01-03",
  "steps":[
    {
      "identifier": "ext-1115",
      "name": "stepNameTestExport",
      "description": "stepDescriptionTest",
      "seqNo": 10,
      "dateStart": "2023-01-10",
      "dateEnd": "2023-01-20",
      "deliveryDate": "2023-01-16",
      "woTargetStartDate": "2023-01-05",
      "woTargetEndDate": "2023-01-15",
      "woPlannedPersonDurationHours": 2,
      "woStepStatus": "CREATED",
      "woPlannedResourceDurationHours": 1,
      "externalId": "1115",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2023-01-01",
          "assignDateTo": "2023-01-20",
          "isActive": true,
          "isAllDay": true,
          "duration": 1,
          "durationUnit": "Hour",
          "testFacilityGroupName": "testFacilityGroupNameTestExport",
          "externalId": "2341"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-2221",
      "numberOfObjectsUnderTest": 1,
      "woDeliveryNote": "deliveryNote",
      "woManufacturer": "woManufacturerTest",
      "woObjectType": "woObjectTypeTestExport",
      "woObjectName": "woObjectNameTestExport",
      "woObjectWhereabouts": "whereabouts",
      "externalId": "2221"
    }
  ]
}
"""
    And process work order project upsert response
      | C_Project_ID.Identifier  |
      | workOrderProjectToExport |

    And validate the created projects
      | C_Project_ID.Identifier  | OPT.Name         | OPT.Description | OPT.C_BPartner_ID.Identifier | OPT.C_ProjectType_ID.Identifier | OPT.IsActive |
      | workOrderProjectToExport | nameTestExportWO | testDescription | 2156423                      | 540009                          | true         |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.parameters.C_Project_ID.Identifier |
      | otherConfig                         | workOrderProjectToExport               |
