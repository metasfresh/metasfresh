@from:cucumber
Feature: WorkOrder project data export audit

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the export audit data is reset
    And metasfresh has date and time 2023-01-12T13:30:13+01:00[Europe/Berlin]

  Scenario:Project data export audit with external system config and pinstance is created when WO Project is retrieved via API
    Given add Other external system config with identifier: otherConfig
      | Name      | Value      |
      | paramName | paramValue |

    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | otherConfig                         | pInstance                  |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-externalIdAuditWO",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "value": "valueTestAuditWO",
  "name": "nameTestAuditWO",
  "priceListVersionId": 2002141,
  "currencyCode": "EUR",
  "salesRepId": 100,
  "description": "testDescription",
  "dateContract": "2023-01-06",
  "dateFinish": "2023-01-15",
  "bpartnerId": 2156423,
  "projectReferenceExt": "woReferenceExt",
  "externalId": "externalIdAuditWO",
  "orgCode": "001",
  "isActive": true,
  "bpartnerDepartment": "bpartnerDepartmentTest",
  "woOwner": "woOwnerTest",
  "poReference": "RDCBCO212ABN",
  "bpartnerTargetDate": "2023-02-08",
  "woProjectCreatedDate": "2023-01-03",
  "steps":[
    {
      "identifier": "ext-11111",
      "name": "stepNameTestAudit",
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
      "externalId": "11111",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2023-01-01",
          "assignDateTo": "2023-01-20",
          "isActive": true,
          "isAllDay": true,
          "duration": 1,
          "durationUnit": "Hour",
          "testFacilityGroupName": "testFacilityGroupNameTestAudit",
          "externalId": "234566"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-22222",
      "numberOfObjectsUnderTest": 1,
      "woDeliveryNote": "deliveryNote",
      "woManufacturer": "woManufacturerTest",
      "woObjectType": "woObjectTypeTestAudit",
      "woObjectName": "woObjectNameTestAudit",
      "woObjectWhereabouts": "whereabouts",
      "externalId": "22222"
    }
  ]
}
"""
    And process work order project upsert response
      | C_Project_ID.Identifier | OPT.C_Project_WO_Step_ID.Identifier | OPT.C_Project_WO_Resource_ID.Identifier | OPT.C_Project_WO_ObjectUnderTest_ID.Identifier |
      | workOrderProjectAudit   | workOrderStepAudit                  | workOrderResourceAudit                  | workOrderObjectUnderTestAudit                  |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | workOrderProjectAudit   |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate work order project 'GET' response
  """
  {
  "requestId": 1000005,
  "endpointResponse": {
    "projectId": 1000002,
    "value": "valueTestAuditWO",
    "name": "nameTestAuditWO",
    "projectTypeId": 540009,
    "priceListVersionId": 2002141,
    "currencyCode": "EUR",
    "salesRepId": 100,
    "description": "testDescription",
    "dateContract": "2023-01-06",
    "dateFinish": "2023-01-15",
    "bpartnerId": 2156423,
    "projectReferenceExt": "woReferenceExt",
    "externalId": "externalIdAuditWO",
    "orgCode": "001",
    "isActive": true,
    "woOwner": "woOwnerTest",
    "poReference": "RDCBCO212ABN",
    "bpartnerDepartment": "bpartnerDepartmentTest",
    "bpartnerTargetDate": "2023-02-08",
    "woProjectCreatedDate": "2023-01-03",
    "steps": [
      {
        "stepId": 1000001,
        "name": "stepNameTestAudit",
        "projectId": 1000002,
        "description": "stepDescriptionTest",
        "seqNo": 10,
        "dateStart": "2023-01-10",
        "dateEnd": "2023-01-20",
        "externalId": "11111",
        "woPlannedResourceDurationHours": 1,
        "deliveryDate": "2023-01-16",
        "woTargetStartDate": "2023-01-01",
        "woTargetEndDate": "2023-01-20",
        "woPlannedPersonDurationHours": 2,
        "woStepStatus": "CREATED",
        "resources": [
          {
            "woResourceId": 1000000,
            "stepId": 1000001,
            "assignDateFrom": "2023-01-01",
            "assignDateTo": "2023-01-20",
            "isActive": true,
            "resourceId": 540006,
            "isAllDay": true,
            "duration": 1,
            "durationUnit": "Hour",
            "testFacilityGroupName": "testFacilityGroupNameTestAudit",
            "externalId": "234566"
          }
        ]
      }
    ],
    "objectsUnderTest": [
      {
        "objectUnderTestId": 1000001,
        "numberOfObjectsUnderTest": 1,
        "externalId": "22222",
        "woDeliveryNote": "deliveryNote",
        "woManufacturer": "woManufacturerTest",
        "woObjectType": "woObjectTypeTestAudit",
        "woObjectName": "woObjectNameTestAudit",
        "woObjectWhereabouts": "whereabouts"
      }
    ]
  }
}
  """

    And after not more than 30s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName                    | Record_ID.Identifier          | Data_Export_Audit_Parent_ID.Identifier |
      | dataExport_WorkOrderProject     | C_Project                    | workOrderProjectAudit         |                                        |
      | dataExport_WOStep               | C_Project_WO_Step            | workOrderStepAudit            | dataExport_WorkOrderProject            |
      | dataExport_WOResource           | C_Project_WO_Resource        | workOrderResourceAudit        | dataExport_WOStep                      |
      | dataExport_WOObjectUnderTest    | C_Project_WO_ObjectUnderTest | workOrderObjectUnderTestAudit | dataExport_WorkOrderProject            |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action       | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | dataExport_WorkOrderProject     | Exported-Standalone      | otherConfig                         | pInstance                  |
      | dataExport_WOStep               | Exported-AlongWithParent | otherConfig                         | pInstance                  |
      | dataExport_WOResource           | Exported-AlongWithParent | otherConfig                         | pInstance                  |
      | dataExport_WOObjectUnderTest    | Exported-AlongWithParent | otherConfig                         | pInstance                  |
