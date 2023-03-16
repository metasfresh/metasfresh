@from:cucumber
Feature: WorkOrder Project API Test

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-02T08:00:00+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Persist WorkOrder Project with associated steps, resources and objectsUnderTest using API
  1st we insert a WO project setting all available fields in API
  _Then we retrieve the inserted project to validate all fields

  2nd we update the created project setting all available fields in API
  _Then we retrieve the updated project to validate all fields

  3rd we update parent and check that the required values are propagated
  _Then we retrieve the updated project once again to validate that only that field has been changed

    Given load C_ProjectType:
      | C_ProjectType_ID.Identifier | ProjectCategory |
      | parentProjectType           | B               |

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier      | OPT.AD_User_ID | Name                       |
      | parentSpecialistConsultant | 222            | parentSpecialistConsultant |
      | parentSalesRep             | 333            | parentSalesRep             |

    And metasfresh contains C_Project
      | C_Project_ID | Name       | C_Currency_ID.ISO_Code | OPT.Value     | OPT.ProjectCategory | OPT.C_ProjectType_ID.Identifier | OPT.C_Project_Reference_Ext | OPT.InternalPriority | OPT.Specialist_Consultant_ID.Identifier | OPT.BPartnerDepartment   | OPT.SalesRep_ID.Identifier |
      | 210001       | testName_1 | EUR                    | parentProject | B                   | parentProjectType               | parentExternalRef           | 3                    | parentSpecialistConsultant              | parentBPartnerDepartment | parentSalesRep             |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "value": "valueTest",
  "name": "nameTest",
  "priceListVersionId": 2002141,
  "currencyCode": "EUR",
  "salesRepId": 100,
  "description": "testDescription",
  "dateContract": "2022-06-07",
  "dateFinish": "2022-06-08",
  "bpartnerId": 2156423,
  "projectReferenceExt": "woReferenceExt",
  "externalId": "woExternalId",
  "projectParentId": 210001,
  "orgCode": "001",
  "isActive": true,
  "bpartnerDepartment": "bpartnerDepartmentTest",
  "woOwner": "woOwnerTest",
  "poReference": "RDCBCO212ABN",
  "bpartnerTargetDate": "2022-06-08",
  "woProjectCreatedDate": "2022-06-07",
  "dateOfProvisionByBPartner": "2022-06-07",
  "steps":[
    {
      "identifier": "ext-296950",
      "name": "stepNameTest",
      "description": "stepDescriptionTest",
      "seqNo": 10,
      "dateStart": "2022-06-15",
      "dateEnd": "2022-08-20",
      "woPartialReportDate": "2022-06-16",
      "woPlannedResourceDurationHours": 1,
      "deliveryDate": "2022-06-17",
      "woTargetStartDate": "2022-06-20",
      "woTargetEndDate": "2022-07-02",
      "woPlannedPersonDurationHours": 2,
      "woStepStatus": "CREATED",
      "woFindingsReleasedDate": "2022-08-10",
      "woFindingsCreatedDate": "2022-08-02",
      "externalId": "296950",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-15",
          "assignDateTo": "2022-08-20",
          "isActive": true,
          "isAllDay": true,
          "duration": 1,
          "durationUnit": "Hour",
          "testFacilityGroupName": "testFacilityGroupNameTest",
          "externalId": "296950"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-247514",
      "numberOfObjectsUnderTest": 1,
      "woDeliveryNote": "deliveryNote",
      "woManufacturer": "woManufacturerTest",
      "woObjectType": "woObjectTypeTest",
      "woObjectName": "woObjectNameTest",
      "woObjectWhereabouts": "whereabouts",
      "externalId": "247514"
    }
  ]
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier |
      | wo_1                    |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_1                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
  {
  "requestId": 1000005,
  "endpointResponse": {
    "projectId": 1000002,
    "value": "valueTest",
    "name": "nameTest",
    "projectTypeId": 540009,
    "priceListVersionId": 2002141,
    "currencyCode": "EUR",
    "salesRepId": 333,
    "description": "testDescription",
    "dateContract": "2022-06-07",
    "dateFinish": "2022-06-08",
    "bpartnerId": 2156423,
    "projectReferenceExt": "parentExternalRef",
    "externalId": "woExternalId",
    "projectParentId": 210001,
    "orgCode": "001",
    "isActive": true,
    "dateOfProvisionByBPartner": "2022-06-07",
    "woOwner": "woOwnerTest",
    "poReference": "RDCBCO212ABN",
    "bpartnerDepartment": "parentBPartnerDepartment",
    "bpartnerTargetDate": "2022-06-08",
    "woProjectCreatedDate": "2022-06-07",
    "specialistConsultantId": 222,
    "internalPriority": "3",
    "steps": [
      {
        "stepId": 1000001,
        "name": "stepNameTest",
        "projectId": 1000002,
        "description": "stepDescriptionTest",
        "seqNo": 10,
        "dateStart": "2022-06-15",
        "dateEnd": "2022-08-20",
        "externalId": "296950",
        "woPartialReportDate": "2022-06-16",
        "woPlannedResourceDurationHours": 1,
        "deliveryDate": "2022-06-17",
        "woTargetStartDate": "2022-06-20",
        "woTargetEndDate": "2022-07-02",
        "woPlannedPersonDurationHours": 2,
        "woStepStatus": "CREATED",
        "woFindingsReleasedDate": "2022-08-10",
        "woFindingsCreatedDate": "2022-08-02",
        "resources": [
          {
            "woResourceId": 1000000,
            "stepId": 1000001,
            "assignDateFrom": "2022-06-15",
            "assignDateTo": "2022-08-20",
            "isActive": true,
            "resourceId": 540006,
            "isAllDay": true,
            "duration": 1,
            "durationUnit": "Hour",
            "testFacilityGroupName": "testFacilityGroupNameTest",
            "externalId": "296950"
          }
        ]
      }
    ],
    "objectsUnderTest": [
      {
        "objectUnderTestId": 1000001,
        "numberOfObjectsUnderTest": 1,
        "externalId": "247514",
        "woDeliveryNote": "deliveryNote",
        "woManufacturer": "woManufacturerTest",
        "woObjectType": "woObjectTypeTest",
        "woObjectName": "woObjectNameTest",
        "woObjectWhereabouts": "whereabouts"
      }
    ]
  }
}
  """


    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier         | OPT.AD_User_ID | Name                          |
      | newParentSpecialistConsultant | 444            | newParentSpecialistConsultant |
      | newParentSalesRep             | 555            | newParentSalesRep             |
    And metasfresh contains C_Project
      | C_Project_ID | Name       | C_Currency_ID.ISO_Code | OPT.Value        | OPT.ProjectCategory | OPT.C_ProjectType_ID.Identifier | OPT.C_Project_Reference_Ext | OPT.InternalPriority | OPT.Specialist_Consultant_ID.Identifier | OPT.BPartnerDepartment      | OPT.SalesRep_ID.Identifier |
      | 3000001      | new_parent | EUR                    | newParentProject | B                   | parentProjectType               | newParentExternalRef        | 7                    | newParentSpecialistConsultant           | newParentBPartnerDepartment | newParentSalesRep          |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
  """
{
  "identifier": "ext-woExternalId",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "value": "valueTest_2",
  "name": "nameTest_2",
  "priceListVersionId": 2002139,
  "currencyCode": "CHF",
  "salesRepId": 2188223,
  "description": "testDescription_2",
  "dateContract": "2022-06-15",
  "dateFinish": "2022-06-16",
  "bpartnerId": 2156425,
  "projectReferenceExt": "woReferenceExt",
  "projectParentId": 3000001,
  "orgCode": "001",
  "isActive": true,
  "bpartnerDepartment": "bpartnerDepartmentTest_2",
  "woOwner": "woOwnerTest_2",
  "poReference": "RDCBCO212ABN_2",
  "bpartnerTargetDate": "2022-06-21",
  "woProjectCreatedDate": "2022-06-20",
  "dateOfProvisionByBPartner": "2022-06-22",
  "steps": [
    {
      "identifier": "ext-296950",
      "name": "stepNameTest_2",
      "description": "stepDescriptionTest_2",
      "seqNo": 20,
      "dateStart": "2022-06-16",
      "dateEnd": "2022-08-21",
      "woPartialReportDate": "2022-06-17",
      "woPlannedResourceDurationHours": 2,
      "deliveryDate": "2022-06-18",
      "woTargetStartDate": "2022-06-21",
      "woTargetEndDate": "2022-07-03",
      "woPlannedPersonDurationHours": 3,
      "woStepStatus": "READY",
      "woFindingsReleasedDate": "2022-08-11",
      "woFindingsCreatedDate": "2022-08-03",
      "externalId": "296950",
      "resources": [
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-16",
          "assignDateTo": "2022-08-21",
          "isActive": true,
          "isAllDay": false,
          "duration": 2,
          "durationUnit": "Minute",
          "testFacilityGroupName": "testFacilityGroupNameTest_2",
          "externalId": "296950"
        }
      ]
    }
  ],
  "objectsUnderTest": [
    {
      "identifier": "ext-247514",
      "numberOfObjectsUnderTest": 2,
      "woDeliveryNote": "deliveryNote_2",
      "woManufacturer": "woManufacturerTest_2",
      "woObjectType": "woObjectTypeTest_2",
      "woObjectName": "woObjectNameTest_2",
      "woObjectWhereabouts": "whereabouts_2",
      "externalId": "247514"
    }
  ]
}
  """

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
  "requestId": 1000004,
  "endpointResponse": {
    "projectId": 1000001,
    "value": "valueTest_2",
    "name": "nameTest_2",
    "projectTypeId": 540009,
    "priceListVersionId": 2002139,
    "currencyCode": "CHF",
    "salesRepId": 555,
    "description": "testDescription_2",
    "dateContract": "2022-06-15",
    "dateFinish": "2022-06-16",
    "bpartnerId": 2156425,
    "projectReferenceExt": "newParentExternalRef",
    "externalId": "woExternalId",
    "projectParentId": 3000001,
    "orgCode": "001",
    "isActive": true,
    "dateOfProvisionByBPartner": "2022-06-22",
    "woOwner": "woOwnerTest_2",
    "poReference": "RDCBCO212ABN_2",
    "bpartnerDepartment": "newParentBPartnerDepartment",
    "bpartnerTargetDate": "2022-06-21",
    "woProjectCreatedDate": "2022-06-20",
    "specialistConsultantId": 444,
    "internalPriority": "7",
    "steps": [
      {
        "stepId": 1000000,
        "name": "stepNameTest_2",
        "projectId": 1000001,
        "description": "stepDescriptionTest_2",
        "seqNo": 20,
        "dateStart": "2022-06-16",
        "dateEnd": "2022-08-21",
        "externalId": "296950",
        "woPartialReportDate": "2022-06-17",
        "woPlannedResourceDurationHours": 2,
        "deliveryDate": "2022-06-18",
        "woTargetStartDate": "2022-06-21",
        "woTargetEndDate": "2022-07-03",
        "woPlannedPersonDurationHours": 3,
        "woStepStatus": "READY",
        "woFindingsReleasedDate": "2022-08-11",
        "woFindingsCreatedDate": "2022-08-03",
        "resources": [
          {
            "woResourceId": 1000000,
            "stepId": 1000000,
            "assignDateFrom": "2022-06-16",
            "assignDateTo": "2022-08-21",
            "isActive": true,
            "resourceId": 540006,
            "isAllDay": false,
            "duration": 2,
            "durationUnit": "Minute",
            "testFacilityGroupName": "testFacilityGroupNameTest_2",
            "externalId": "296950"
          }
        ]
      }
    ],
    "objectsUnderTest": [
      {
        "objectUnderTestId": 1000000,
        "numberOfObjectsUnderTest": 2,
        "externalId": "247514",
        "woDeliveryNote": "deliveryNote_2",
        "woManufacturer": "woManufacturerTest_2",
        "woObjectType": "woObjectTypeTest_2",
        "woObjectName": "woObjectNameTest_2",
        "woObjectWhereabouts": "whereabouts_2"
      }
    ]
  }
}
  """

    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier          | OPT.AD_User_ID | Name                           |
      | newParentSpecialistConsultant2 | 666            | newParentSpecialistConsultant2 |
      | newParentSalesRep2             | 777            | newParentSalesRep2             |
    And metasfresh contains C_Project
      | C_Project_ID | Name         | C_Currency_ID.ISO_Code | OPT.Value         | OPT.ProjectCategory | OPT.C_ProjectType_ID.Identifier | OPT.C_Project_Reference_Ext | OPT.InternalPriority | OPT.Specialist_Consultant_ID.Identifier | OPT.BPartnerDepartment       | OPT.SalesRep_ID.Identifier |
      | 3000002      | new_parent_2 | EUR                    | newParentProject2 | B                   | parentProjectType               | newParentExternalRef2       | 1                    | newParentSpecialistConsultant2          | newParentBPartnerDepartment2 | newParentSalesRep2         |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
  """
{
  "identifier": "ext-woExternalId",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "projectParentId": 3000002,
  "projectReferenceExt": "woReferenceExt"
}
  """

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
  "requestId": 1000008,
  "endpointResponse": {
    "projectId": 1000001,
    "value": "valueTest_2",
    "name": "nameTest_2",
    "projectTypeId": 540009,
    "priceListVersionId": 2002139,
    "currencyCode": "CHF",
    "salesRepId": 777,
    "description": "testDescription_2",
    "dateContract": "2022-06-15",
    "dateFinish": "2022-06-16",
    "bpartnerId": 2156425,
    "projectReferenceExt": "newParentExternalRef2",
    "externalId": "woExternalId",
    "projectParentId": 3000002,
    "orgCode": "001",
    "isActive": true,
    "dateOfProvisionByBPartner": "2022-06-22",
    "woOwner": "woOwnerTest_2",
    "poReference": "RDCBCO212ABN_2",
    "bpartnerDepartment": "newParentBPartnerDepartment2",
    "bpartnerTargetDate": "2022-06-21",
    "woProjectCreatedDate": "2022-06-20",
    "specialistConsultantId": 666,
    "internalPriority": "1",
    "steps": [
      {
        "stepId": 1000000,
        "name": "stepNameTest_2",
        "projectId": 1000001,
        "description": "stepDescriptionTest_2",
        "seqNo": 20,
        "dateStart": "2022-06-16",
        "dateEnd": "2022-08-21",
        "externalId": "296950",
        "woPartialReportDate": "2022-06-17",
        "woPlannedResourceDurationHours": 2,
        "deliveryDate": "2022-06-18",
        "woTargetStartDate": "2022-06-21",
        "woTargetEndDate": "2022-07-03",
        "woPlannedPersonDurationHours": 3,
        "woStepStatus": "READY",
        "woFindingsReleasedDate": "2022-08-11",
        "woFindingsCreatedDate": "2022-08-03",
        "resources": [
          {
            "woResourceId": 1000000,
            "stepId": 1000000,
            "assignDateFrom": "2022-06-16",
            "assignDateTo": "2022-08-21",
            "isActive": true,
            "resourceId": 540006,
            "isAllDay": false,
            "duration": 2,
            "durationUnit": "Minute",
            "testFacilityGroupName": "testFacilityGroupNameTest_2",
            "externalId": "296950"
          }
        ]
      }
    ],
    "objectsUnderTest": [
      {
        "objectUnderTestId": 1000000,
        "numberOfObjectsUnderTest": 2,
        "externalId": "247514",
        "woDeliveryNote": "deliveryNote_2",
        "woManufacturer": "woManufacturerTest_2",
        "woObjectType": "woObjectTypeTest_2",
        "woObjectName": "woObjectNameTest_2",
        "woObjectWhereabouts": "whereabouts_2"
      }
    ]
  }
}
  """

  @from:cucumber
  Scenario: Persist WorkOrder Project setting only mandatory fields using API
    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId2",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt2"
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier |
      | wo_2                    |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_2                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
    "requestId": 1000001,
    "endpointResponse": {
        "projectId": 1000001,
        "value": "nextDocNo",
        "name": "nextDocNo",
        "projectTypeId": 540009,
        "priceListVersionId": null,
        "currencyCode": "EUR",
        "salesRepId": 0,
        "description": null,
        "dateContract": null,
        "dateFinish": null,
        "bpartnerId": null,
        "projectReferenceExt": "woReferenceExt2",
        "externalId": "woExternalId2",
        "projectParentId": null,
        "orgCode": "001",
        "isActive": true,
        "dateOfProvisionByBPartner": null,
        "woOwner": null,
        "poReference": null,
        "bpartnerDepartment": null,
        "bpartnerTargetDate": null,
        "woProjectCreatedDate": null,
        "steps": [],
        "objectsUnderTest": []
    }
}
  """

  @from:cucumber
  Scenario: Persist WorkOrder Project with associated steps, resources and objectsUnderTest, setting only mandatory fields, using API
    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId3",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt3",
  "steps":[
    {
      "identifier": "ext-296951",
      "name": "stepNameTest_3",
      "externalId": "296951",
      "dateStart": "2022-06-02",
      "dateEnd": "2022-08-20",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-01",
          "assignDateTo": "2022-08-02"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-247515",
      "numberOfObjectsUnderTest": 1,
      "externalId": "247515"
    }
  ]
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier |
      | wo_3                    |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_3                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
    "requestId": 1000005,
    "endpointResponse": {
        "projectId": 1000002,
        "value": "nextDocNo",
        "name": "nextDocNo",
        "projectTypeId": 540009,
        "priceListVersionId": null,
        "currencyCode": "EUR",
        "salesRepId": 0,
        "description": null,
        "dateContract": null,
        "dateFinish": null,
        "bpartnerId": null,
        "projectReferenceExt": "woReferenceExt3",
        "externalId": "woExternalId3",
        "projectParentId": null,
        "orgCode": "001",
        "isActive": true,
        "dateOfProvisionByBPartner": null,
        "woOwner": null,
        "poReference": null,
        "bpartnerDepartment": null,
        "bpartnerTargetDate": null,
        "woProjectCreatedDate": null,
        "steps": [
            {
                "stepId": 1000000,
                "name": "stepNameTest_3",
                "projectId": 1000003,
                "description": null,
                "seqNo": 10,
                "dateStart": "2022-06-01",
                "dateEnd": "2022-08-02",
                "externalId": "296951",
                "woPartialReportDate": null,
                "woPlannedResourceDurationHours": 0,
                "deliveryDate": null,
                "woTargetStartDate": null,
                "woTargetEndDate": null,
                "woPlannedPersonDurationHours": 0,
                "woStepStatus": "CREATED",
                "woFindingsReleasedDate": null,
                "woFindingsCreatedDate": null,
                "resources": [
                    {
                        "woResourceId": 1000000,
                        "stepId": 1000000,
                        "assignDateFrom": "2022-06-01",
                        "assignDateTo": "2022-08-02",
                        "isActive": true,
                        "resourceId": 540006,
                        "isAllDay": false,
                        "duration": 0,
                        "durationUnit": "Hour",
                        "testFacilityGroupName": null,
                        "externalId": null
                    }
                ]
            }
        ],
        "objectsUnderTest": [
            {
                "objectUnderTestId": 1000002,
                "numberOfObjectsUnderTest": 1,
                "externalId": "247515",
                "woDeliveryNote": null,
                "woManufacturer": null,
                "woObjectType": null,
                "woObjectName": null,
                "woObjectWhereabouts": null
            }
        ]
    }
}
  """

  @from:cucumber
  Scenario: Corner cases for persisting WorkOrder Project with associated steps, resources and objectsUnderTest using API
    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId4",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt4",
  "steps":[
    {
      "identifier": "ext-296952",
      "name": "stepNameTest_4",
      "externalId": "296952",
      "dateStart": "2022-06-02",
      "dateEnd": "2022-08-20",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-01",
          "assignDateTo": "2022-08-02"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-247516",
      "numberOfObjectsUnderTest": 1,
      "externalId": "247516"
    }
  ]
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier |
      | wo_3                    |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_3                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
    "requestId": 12345,
    "endpointResponse": {
        "projectId": 1000002,
        "value": "nextDocNo",
        "name": "nextDocNo",
        "projectTypeId": 540009,
        "priceListVersionId": null,
        "currencyCode": "EUR",
        "salesRepId": 0,
        "description": null,
        "dateContract": null,
        "dateFinish": null,
        "bpartnerId": null,
        "projectReferenceExt": "woReferenceExt4",
        "externalId": "woExternalId4",
        "projectParentId": null,
        "orgCode": "001",
        "isActive": true,
        "dateOfProvisionByBPartner": null,
        "woOwner": null,
        "poReference": null,
        "bpartnerDepartment": null,
        "bpartnerTargetDate": null,
        "woProjectCreatedDate": null,
        "steps": [
            {
                "stepId": 1000000,
                "name": "stepNameTest_4",
                "projectId": 1000003,
                "description": null,
                "seqNo": 10,
                "dateStart": "2022-06-01",
                "dateEnd": "2022-08-02",
                "externalId": "296952",
                "woPartialReportDate": null,
                "woPlannedResourceDurationHours": 0,
                "deliveryDate": null,
                "woTargetStartDate": null,
                "woTargetEndDate": null,
                "woPlannedPersonDurationHours": 0,
                "woStepStatus": "CREATED",
                "woFindingsReleasedDate": null,
                "woFindingsCreatedDate": null,
                "resources": [
                    {
                        "woResourceId": 1000000,
                        "stepId": 1000000,
                        "assignDateFrom": "2022-06-01",
                        "assignDateTo": "2022-08-02",
                        "isActive": true,
                        "resourceId": 540006,
                        "isAllDay": false,
                        "duration": 0,
                        "durationUnit": "Hour",
                        "testFacilityGroupName": null,
                        "externalId": null
                    }
                ]
            }
        ],
        "objectsUnderTest": [
            {
                "objectUnderTestId": 1000002,
                "numberOfObjectsUnderTest": 1,
                "externalId": "247516",
                "woDeliveryNote": null,
                "woManufacturer": null,
                "woObjectType": null,
                "woObjectName": null,
                "woObjectWhereabouts": null
            }
        ]
    }
}
  """

    # Now we try to create another project with externalId woExternalId5 - which is new - 
    # but include in the request the step with externalID 296952 which we already added as part of project woExternalId4 
    # => this shall fail
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '422' status code
    """
{
  "identifier": "ext-woExternalId5",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt5",
  "steps":[
    {
      "identifier": "ext-296952",
      "name": "stepNameTest_5",
      "externalId": "296952",
      "dateStart": "2022-06-03",
      "dateEnd": "2022-08-21",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-02",
          "assignDateTo": "2022-08-03"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-247517",
      "numberOfObjectsUnderTest": 2,
      "externalId": "247517"
    }
  ]
}
"""

    And validate the following exception message was thrown by the work order project upsert endpoint
      | ExceptionMessage                                                   |
      | WOProjectStep.ExternalId already stored under a different project! |

    # Now we again try to create another project with externalId woExternalId5 - which is new - 
    # but include in the request the object under test with externalID 296952 which we already added as part of project woExternalId4 
    # => this shall fail
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '422' status code
    """
{
  "identifier": "ext-woExternalId5",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt5",
  "steps":[
    {
      "identifier": "ext-396952",
      "name": "stepNameTest_5",
      "externalId": "396952",
      "dateStart": "2022-06-03",
      "dateEnd": "2022-08-21",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-02",
          "assignDateTo": "2022-08-03"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-247516",
      "numberOfObjectsUnderTest": 2,
      "externalId": "247516"
    }
  ]
}
"""

    And validate the following exception message was thrown by the work order project upsert endpoint
      | ExceptionMessage                                                        |
      | WOProjectUnderTest.ExternalId already stored under a different project! |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '422' status code
    """
{
  "identifier": "ext-woExternalId5",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt5",
  "priceListVersionId": 2002139,
  "steps":[
    {
      "identifier": "ext-296953",
      "name": "stepNameTest_5",
      "externalId": "296953",
      "dateStart": "2022-06-03",
      "dateEnd": "2022-08-21",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-02",
          "assignDateTo": "2022-08-03"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-247516",
      "numberOfObjectsUnderTest": 2,
      "externalId": "247516"
    }
  ]
}
"""

    And validate the following exception message was thrown by the work order project upsert endpoint
      | ExceptionMessage                                           |
      | Project currency does not match the price list's currency! |

  @from:cucumber
  Scenario: Retrieve WorkOrder Project with associated steps, resources and objectsUnderTest using API and 'query' endpoint
    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-DEBN-77",
  "projectTypeId": 540009,
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "DEBN-77",
  "steps":[
    {
      "identifier": "ext-496952",
      "name": "stepNameTest_6",
      "externalId": "496952",
      "dateStart": "2022-06-02",
      "dateEnd": "2022-08-20",
      "resources":[
        {
          "resourceIdentifier": "540006",
          "assignDateFrom": "2022-06-01",
          "assignDateTo": "2022-08-02"
        }
      ]
    }
  ],
  "objectsUnderTest":[
    {
      "identifier": "ext-447516",
      "numberOfObjectsUnderTest": 1,
      "externalId": "447516"
    }
  ]
}
"""

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder/query' and fulfills with '200' status code
   """
{
    "orgCode": "001",
    "projectReferenceExtPattern": "EBN"
}
"""

    # note that the requestId is not validated against
    And validate work order project 'JsonWorkOrderProjectResponses' response
  """
{
    "requestId": 1000016,
    "endpointResponse": {
        "projects": [
            {
                "projectId": 3000005,
                "value": "nextDocNo",
                "name": "nextDocNo",
                "projectTypeId": 540009,
                "priceListVersionId": null,
                "currencyCode": "EUR",
                "salesRepId": 0,
                "description": null,
                "dateContract": null,
                "dateFinish": null,
                "bpartnerId": null,
                "projectReferenceExt": "DEBN-77",
                "projectParentId": null,
                "orgCode": "001",
                "isActive": true,
                "dateOfProvisionByBPartner": null,
                "woOwner": null,
                "poReference": null,
                "bpartnerDepartment": null,
                "bpartnerTargetDate": null,
                "woProjectCreatedDate": null,
                "steps": [
                    {
                        "stepId": 1000004,
                        "name": "stepNameTest_6",
                        "projectId": 3000005,
                        "description": null,
                        "seqNo": 10,
                        "dateStart": "2022-06-01",
                        "dateEnd": "2022-08-02",
                        "externalId": "496952",
                        "woPartialReportDate": null,
                        "woPlannedResourceDurationHours": 0,
                        "deliveryDate": null,
                        "woTargetStartDate": null,
                        "woTargetEndDate": null,
                        "woPlannedPersonDurationHours": 0,
                        "woStepStatus": "CREATED",
                        "woFindingsReleasedDate": null,
                        "woFindingsCreatedDate": null,
                        "resources": [
                            {
                                "woResourceId": 1000004,
                                "stepId": 1000004,
                                "assignDateFrom": "2022-06-01",
                                "assignDateTo": "2022-08-02",
                                "isActive": true,
                                "resourceId": 540006,
                                "isAllDay": false,
                                "duration": 0,
                                "durationUnit": "Hour",
                                "testFacilityGroupName": null,
                                "externalId": null
                            }
                        ]
                    }
                ],
                "objectsUnderTest": [
                    {
                        "objectUnderTestId": 1000005,
                        "numberOfObjectsUnderTest": 1,
                        "externalId": "447516",
                        "woDeliveryNote": null,
                        "woManufacturer": null,
                        "woObjectType": null,
                        "woObjectName": null,
                        "woObjectWhereabouts": null
                    }
                ]
            }
        ]
    }
}
  """

  @from:cucumber
  Scenario: Persist WorkOrder Project with associated step and resource using API
  There's no master C_SimulationPlan existing when workOrder is persisted
  Resource's assignDateFrom and assignDateTo are not specified => we add it to a C_SimulationPlan created just in time
  Step's dateStart and dateTo are specified => C_Project_WO_Resource_Simulation's dates are step's dates
  Process C_SimulationPlan
  Validate C_Project_WO_Step and C_Project_WO_Resource

    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId_12_09_2022",
  "projectTypeId": 540009,
  "value" : "testValue_12_09_2022",
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt_12_09_2022",
  "steps":[
    {
      "identifier": "ext-120922",
      "name": "stepNameTest_12_09_2022",
      "externalId": "120922",
      "dateStart": "2022-01-02",
      "dateEnd": "2022-01-04",
      "resources":[
        {
          "resourceIdentifier": "val-test"
        }
      ]
    }
  ]
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier | OPT.C_Project_WO_Step_ID.Identifier |
      | wo_4                    | wo_s_1                              |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_4                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated
    And validate work order project 'GET' response
  """
{
    "requestId": 1,
    "endpointResponse": {
        "projectId": 1000002,
        "value": "testValue_12_09_2022",
        "name": "testValue_12_09_2022",
        "projectTypeId": 540009,
        "priceListVersionId": null,
        "currencyCode": "EUR",
        "salesRepId": 0,
        "description": null,
        "dateContract": null,
        "dateFinish": null,
        "bpartnerId": null,
        "projectReferenceExt": "woReferenceExt_12_09_2022",
        "externalId": "woExternalId_12_09_2022",
        "projectParentId": null,
        "orgCode": "001",
        "isActive": true,
        "dateOfProvisionByBPartner": null,
        "woOwner": null,
        "poReference": null,
        "bpartnerDepartment": null,
        "bpartnerTargetDate": null,
        "woProjectCreatedDate": null,
        "steps": [
            {
                "stepId": 1000000,
                "name": "stepNameTest_12_09_2022",
                "projectId": 1000003,
                "description": null,
                "seqNo": 10,
                "dateStart": "2022-01-02",
                "dateEnd": "2022-01-04",
                "externalId": "120922",
                "woPartialReportDate": null,
                "woPlannedResourceDurationHours": 0,
                "deliveryDate": null,
                "woTargetStartDate": null,
                "woTargetEndDate": null,
                "woPlannedPersonDurationHours": 0,
                "woStepStatus": "CREATED",
                "woFindingsReleasedDate": null,
                "woFindingsCreatedDate": null,
                "resources": [
                    {
                        "woResourceId": 1000000,
                        "stepId": 1000000,
                        "assignDateFrom": null,
                        "assignDateTo": null,
                        "isActive": true,
                        "resourceId": 540006,
                        "isAllDay": false,
                        "duration": 0,
                        "durationUnit": "Hour",
                        "testFacilityGroupName": null,
                        "externalId": null
                    }
                ]
            }
        ],
        "objectsUnderTest": []
    }
}
  """

    And load C_Project_WO_Resources
      | C_Project_WO_Resource_ID.Identifier | C_Project_WO_Step_ID.Identifier |
      | wo_r_1                              | wo_s_1                          |

    And master C_SimulationPlan is found
      | C_SimulationPlan_ID.Identifier | OrgCode |
      | sp_1                           | 001     |

    And load C_Project_WO_Step_Simulation
      | C_Project_WO_Step_Simulation_ID.Identifier | C_Project_ID.Identifier | C_Project_WO_Step_ID.Identifier | C_SimulationPlan_ID.Identifier |
      | wo_s_s_1                                   | wo_4                    | wo_s_1                          | sp_1                           |

    And validate C_Project_WO_Step_Simulation
      | C_Project_WO_Step_Simulation_ID.Identifier | OPT.DateStart | OPT.DateEnd |
      | wo_s_s_1                                   | 2022-01-02    | 2022-01-04  |

    And load C_Project_WO_Resource_Simulation
      | C_Project_WO_Resource_Simulation_ID.Identifier | C_Project_ID.Identifier | C_Project_WO_Resource_ID.Identifier | C_SimulationPlan_ID.Identifier |
      | wo_r_s_1                                       | wo_4                    | wo_r_1                              | sp_1                           |

    And validate C_Project_WO_Resource_Simulation
      | C_Project_WO_Resource_Simulation_ID.Identifier | OPT.AssignDateTo | OPT.AssignDateFrom |
      | wo_r_s_1                                       | 2022-01-04       | 2022-01-02         |

    And C_SimulationPlan is processed
      | C_SimulationPlan_ID.Identifier |
      | sp_1                           |

    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | loginUser             | metasfresh |

    And validate C_SimulationPlan
      | C_SimulationPlan_ID.Identifier | AD_User_Responsible_ID.Identifier | OPT.IsMainSimulation | OPT.Processed |
      | sp_1                           | loginUser                         | true                 | true          |

    And validate C_Project_WO_Step
      | C_Project_WO_Step_ID.Identifier | OPT.DateStart | OPT.DateEnd |
      | wo_s_1                          | 2022-01-02    | 2022-01-04  |

    And validate C_Project_WO_Resource
      | C_Project_WO_Resource_ID.Identifier | OPT.AssignDateTo | OPT.AssignDateFrom |
      | wo_r_1                              | 2022-01-04       | 2022-01-02         |

    And C_SimulationPlan is deactivated
      | C_SimulationPlan_ID.Identifier |
      | sp_1                           |

  @from:cucumber
  Scenario: Persist WorkOrder Project with associated step and resource using API
  There's one master C_SimulationPlan existing when workOrder is persisted
  Resource's assignDateFrom and assignDateTo are not specified => we add it to the existing C_SimulationPlan
  Step's dateStart and dateTo are specified => C_Project_WO_Resource_Simulation's dates are step's dates
  C_Project_WO_Resource_Conflict is found because the same resource (with the same dates) is also used in the above scenario
  C_Project_WO_Resource_Conflict is approved
  Process C_SimulationPlan
  Validate C_Project_WO_Step and C_Project_WO_Resource

    And deactivate all master C_SimulationPlan

    Given load AD_User:
      | AD_User_ID.Identifier | Login      |
      | loginUser             | metasfresh |

    And metasfresh contains C_SimulationPlan
      | C_SimulationPlan_ID.Identifier | AD_User_Responsible_ID.Identifier | OPT.IsMainSimulation | OPT.IsProcessed |
      | sp_1                           | loginUser                         | true                 | false           |

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId_12_12_2022",
  "projectTypeId": 540009,
  "value" : "testValue_12_12_2022",
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt_12_12_2022",
  "steps":[
    {
      "identifier": "ext-121222",
      "name": "stepNameTest_12_12_2022",
      "externalId": "121222",
      "dateStart": "2022-01-02",
      "dateEnd": "2022-01-04",
      "resources":[
        {
          "resourceIdentifier": "val-test"
        }
      ]
    }
  ]
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier | OPT.C_Project_WO_Step_ID.Identifier |
      | wo_4                    | wo_s_1                              |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_4                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
    "requestId": 1000005,
    "endpointResponse": {
        "projectId": 1000002,
        "value": "testValue_12_12_2022",
        "name": "testValue_12_12_2022",
        "projectTypeId": 540009,
        "priceListVersionId": null,
        "currencyCode": "EUR",
        "salesRepId": 0,
        "description": null,
        "dateContract": null,
        "dateFinish": null,
        "bpartnerId": null,
        "projectReferenceExt": "woReferenceExt_12_12_2022",
        "externalId": "woExternalId_12_12_2022",
        "projectParentId": null,
        "orgCode": "001",
        "isActive": true,
        "dateOfProvisionByBPartner": null,
        "woOwner": null,
        "poReference": null,
        "bpartnerDepartment": null,
        "bpartnerTargetDate": null,
        "woProjectCreatedDate": null,
        "steps": [
            {
                "stepId": 1000000,
                "name": "stepNameTest_12_12_2022",
                "projectId": 1000003,
                "description": null,
                "seqNo": 10,
                "dateStart": "2022-01-02",
                "dateEnd": "2022-01-04",
                "externalId": "121222",
                "woPartialReportDate": null,
                "woPlannedResourceDurationHours": 0,
                "deliveryDate": null,
                "woTargetStartDate": null,
                "woTargetEndDate": null,
                "woPlannedPersonDurationHours": 0,
                "woStepStatus": "CREATED",
                "woFindingsReleasedDate": null,
                "woFindingsCreatedDate": null,
                "resources": [
                    {
                        "woResourceId": 1000000,
                        "stepId": 1000000,
                        "assignDateFrom": null,
                        "assignDateTo": null,
                        "isActive": true,
                        "resourceId": 540006,
                        "isAllDay": false,
                        "duration": 0,
                        "durationUnit": "Hour",
                        "testFacilityGroupName": null,
                        "externalId": null
                    }
                ]
            }
        ],
        "objectsUnderTest": []
    }
}
  """
    And load C_Project_WO_Resources
      | C_Project_WO_Resource_ID.Identifier | C_Project_WO_Step_ID.Identifier |
      | wo_r_1                              | wo_s_1                          |

    And load C_Project_WO_Step_Simulation
      | C_Project_WO_Step_Simulation_ID.Identifier | C_Project_ID.Identifier | C_Project_WO_Step_ID.Identifier | C_SimulationPlan_ID.Identifier |
      | wo_s_s_1                                   | wo_4                    | wo_s_1                          | sp_1                           |

    And validate C_Project_WO_Step_Simulation
      | C_Project_WO_Step_Simulation_ID.Identifier | OPT.DateStart | OPT.DateEnd |
      | wo_s_s_1                                   | 2022-01-02    | 2022-01-04  |

    And load C_Project_WO_Resource_Simulation
      | C_Project_WO_Resource_Simulation_ID.Identifier | C_Project_ID.Identifier | C_Project_WO_Resource_ID.Identifier | C_SimulationPlan_ID.Identifier |
      | wo_r_s_1                                       | wo_4                    | wo_r_1                              | sp_1                           |

    And validate C_Project_WO_Resource_Simulation
      | C_Project_WO_Resource_Simulation_ID.Identifier | OPT.AssignDateTo | OPT.AssignDateFrom |
      | wo_r_s_1                                       | 2022-01-04       | 2022-01-02         |

    # found if and only if the above scenario is successfully passed, otherwise it is not found and this scenario FAILS as well 
    And C_Project_WO_Resource_Conflict is found
      | C_Project_WO_Resource_Conflict_ID.Identifier | C_Project2_ID.Identifier | C_Project_WO_Resource2_ID.Identifier | C_SimulationPlan_ID.Identifier |
      | wo_r_c_1                                     | wo_4                     | wo_r_1                               | sp_1                           |

    And C_Project_WO_Resource_Conflict is validated
      | C_Project_WO_Resource_Conflict_ID.Identifier | IsApproved |
      | wo_r_c_1                                     | false      |

    And C_Project_WO_Resource_Conflict is approved
      | C_Project_WO_Resource_Conflict_ID.Identifier |
      | wo_r_c_1                                     |

    And C_SimulationPlan is processed
      | C_SimulationPlan_ID.Identifier |
      | sp_1                           |

    And validate C_SimulationPlan
      | C_SimulationPlan_ID.Identifier | AD_User_Responsible_ID.Identifier | OPT.IsMainSimulation | OPT.Processed |
      | sp_1                           | loginUser                         | true                 | true          |

    And validate C_Project_WO_Step
      | C_Project_WO_Step_ID.Identifier | OPT.DateStart | OPT.DateEnd |
      | wo_s_1                          | 2022-01-02    | 2022-01-04  |

    And validate C_Project_WO_Resource
      | C_Project_WO_Resource_ID.Identifier | OPT.AssignDateTo | OPT.AssignDateFrom |
      | wo_r_1                              | 2022-01-04       | 2022-01-02         |

    And C_SimulationPlan is deactivated
      | C_SimulationPlan_ID.Identifier |
      | sp_1                           |

  @from:cucumber
  Scenario: Persist WorkOrder Project with associated step using API
  There's no master C_SimulationPlan existing when workOrder is persisted
  Incoming step has no dates set & no resources
    Given a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/workorder' and fulfills with '200' status code
    """
{
  "identifier": "ext-woExternalId_12_13_2022",
  "projectTypeId": 540009,
  "value" : "testValue_12_13_2022",
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  },
  "currencyCode": "EUR",
  "projectReferenceExt": "woReferenceExt_12_13_2022",
  "steps":[
    {
      "identifier": "ext-121322",
      "name": "stepNameTest_12_13_2022",
      "externalId": "121322"
    }
  ]
}
"""

    And process work order project upsert response
      | C_Project_ID.Identifier | OPT.C_Project_WO_Step_ID.Identifier |
      | wo_4                    | wo_s_1                              |

    And build 'GET' work order project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | wo_4                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that the requestId is not validated against
    And validate work order project 'GET' response
  """
{
    "requestId": 1000005,
    "endpointResponse": {
        "projectId": 1000002,
        "value": "testValue_12_13_2022",
        "name": "testValue_12_13_2022",
        "projectTypeId": 540009,
        "priceListVersionId": null,
        "currencyCode": "EUR",
        "salesRepId": 0,
        "description": null,
        "dateContract": null,
        "dateFinish": null,
        "bpartnerId": null,
        "projectReferenceExt": "woReferenceExt_12_13_2022",
        "externalId": "woExternalId_12_13_2022",
        "projectParentId": null,
        "orgCode": "001",
        "isActive": true,
        "dateOfProvisionByBPartner": null,
        "woOwner": null,
        "poReference": null,
        "bpartnerDepartment": null,
        "bpartnerTargetDate": null,
        "woProjectCreatedDate": null,
        "steps": [
            {
                "stepId": 1000000,
                "name": "stepNameTest_12_13_2022",
                "projectId": 1000003,
                "description": null,
                "seqNo": 10,
                "dateStart": null,
                "dateEnd": null,
                "externalId": "121322",
                "woPartialReportDate": null,
                "woPlannedResourceDurationHours": 0,
                "deliveryDate": null,
                "woTargetStartDate": null,
                "woTargetEndDate": null,
                "woPlannedPersonDurationHours": 0,
                "woStepStatus": "CREATED",
                "woFindingsReleasedDate": null,
                "woFindingsCreatedDate": null,
                "resources": null
            }
        ],
        "objectsUnderTest": []
    }
}
  """
    