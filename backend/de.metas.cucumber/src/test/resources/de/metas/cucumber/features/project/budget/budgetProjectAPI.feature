@from:cucumber
@ghActions:run_on_executor7
Feature: Budget Project API Test

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-02T08:00:00+01:00[Europe/Berlin]

  @flaky # https://github.com/metasfresh/metasfresh/actions/runs/7023554155/job/19111210552
  @from:cucumber
  Scenario: Persist Budget Project with associated resource using API
  1st we insert a budget project setting all available fields in API
  _Then we retrieve the inserted project to validate all fields

  2nd we update the created project setting all available fields in API
  _Then we retrieve the updated project to validate all fields

  3rd we update only one random field
  _Then we retrieve the updated project once again to validate that only that field has been changed

    Given load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And metasfresh contains S_Resource_Group with the following id:
      | S_Resource_Group_ID | M_Product_Category_ID.Identifier | Name     | DurationUnit |
      | 2000000             | standard_category                | testName | m            |
    And metasfresh contains S_Resource_Group with the following id:
      | S_Resource_Group_ID | M_Product_Category_ID.Identifier | Name     | DurationUnit |
      | 3000000             | standard_category                | testName | h            |
    And metasfresh contains C_Project
      | C_Project_ID | Name       | C_Currency_ID.ISO_Code |
      | 2000000      | testName_1 | EUR                    |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testExternalId1",
    "orgCode" : "001",
    "value" : "testValue",
    "name" : "testName",
    "priceListVersionId" : 2002141,
    "salesRepId" : 100,
    "projectTypeId" : 540005,
    "currencyCode" : "EUR",
    "description" : "testDescription",
    "dateContract" : "2021-05-13",
    "dateFinish" : "2021-05-14",
    "bpartnerId" : 2156423,
    "projectReferenceExt" : "testReferenceExt",
    "projectParentIdentifier" : "2000000",
    "active" : true,
    "resources" : [
        {
            "resourceIdentifier" : 540006,
            "externalId" : "testExternalId",
            "uomTimeId" : 103,
            "dateStartPlan" : "2021-05-11",
            "dateFinishPlan" : "2021-05-12",
            "description" : "resourceTestDescription",
            "resourceGroupId" : 2000000,
            "plannedAmt" : {
                "amount" : 150,
                "currencyCode" : "EUR"
            },
            "plannedDuration" : 2,
            "pricePerTimeUOM" : {
                "amount" : 200,
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
      | C_Project_ID.Identifier |
      | bp_1                    |

    And build 'GET' budget project endpoint path with the following id:
      | C_Project_ID.Identifier |
      | bp_1                    |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    # note that we expect the response to contain "externalId": "testExternalId1", because that was the indetifier in our insert.
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
        "dateFinish": "2021-05-14",
        "extendedProps": {},
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 103,
                "dateStartPlan": "2021-05-11",
                "dateFinishPlan": "2021-05-12",
                "description" : "resourceTestDescription",
                "plannedAmt": 150,
                "currencyCode": "EUR",
                "plannedDuration": 2,
                "pricePerTimeUOM": 200,
                "resourceGroupId": 2000000,
                "resourceId": 540006,
                "externalId": "testExternalId",
                "isActive": true
            }
        ]
    }
}
  """

    And metasfresh contains C_Project
      | C_Project_ID | Name       | C_Currency_ID.ISO_Code |
      | 3000000      | new_parent | EUR                    |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testExternalId1",
    "orgCode" : "001",
    "value" : "testValue_2",
    "name" : "testName_2",
    "priceListVersionId" : 2002139,
    "salesRepId" : 2188223,
    "projectTypeId" : 540005,
    "currencyCode" : "CHF",
    "description" : "testDescription_2",
    "dateContract" : "2021-05-15",
    "dateFinish" : "2021-05-16",
    "bpartnerId" : 2156425,
    "projectReferenceExt" : "testReferenceExt",
    "projectParentIdentifier" : "3000000",
    "active" : true,
    "resources" : [
        {
            "resourceIdentifier" : 540006,
            "externalId" : "testExternalId_2",
            "uomTimeId" : 101,
            "dateStartPlan" : "2021-05-17",
            "dateFinishPlan" : "2021-05-18",
            "description" : "resourceTestDescription_2",
            "resourceGroupId" : 3000000,
            "plannedAmt" : {
                "amount" : 300,
                "currencyCode" : "CHF"
            },
            "plannedDuration" : 2,
            "pricePerTimeUOM" : {
                "amount" : 400,
                "currencyCode" : "CHF"
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

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate budget project 'GET' response
  """
{
    "requestId": 1,
    "endpointResponse": {
        "projectId": 1000000,
        "orgCode": "001",
        "currencyCode": "CHF",
        "name": "testName_2",
        "value": "testValue_2",
        "isActive": true,
        "priceListVersionId": 2002139,
        "description": "testDescription_2",
        "projectParentId": 3000000,
        "projectTypeId": 540005,
        "projectReferenceExt": "testReferenceExt",
        "externalId": "testExternalId1",
        "bpartnerId": 2156425,
        "salesRepId": 2188223,
        "dateContract": "2021-05-15",
        "dateFinish": "2021-05-16",
        "extendedProps": {},
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 101,
                "dateStartPlan": "2021-05-17",
                "dateFinishPlan": "2021-05-18",
                "description": "resourceTestDescription_2",
                "plannedAmt": 300,
                "currencyCode": "CHF",
                "plannedDuration": 2,
                "pricePerTimeUOM": 400,
                "resourceGroupId": 3000000,
                "resourceId": 540006,
                "externalId": "testExternalId_2",
                "isActive": true
            }
        ]
    }
}
  """

    And update AD_Column:
      | TableName | ColumnName  | OPT.IsRestAPICustomColumn |
      | C_Project | POReference | true                      |
      | C_Project | Note        | true                      |

    And the metasfresh cache is reset

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testExternalId1",
    "orgCode" : "001",
    "bpartnerId" : 2156423,
    "projectTypeId" : 540005,
    "extendedProps": {
      "Note": "testNote",
      "POReference": "testReference"
    },
    "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate budget project 'GET' response
  """
{
    "requestId": 1,
    "endpointResponse": {
        "projectId": 1000000,
        "orgCode": "001",
        "currencyCode": "CHF",
        "name": "testName_2",
        "value": "testValue_2",
        "isActive": true,
        "priceListVersionId": 2002139,
        "description": "testDescription_2",
        "projectParentId": 3000000,
        "projectTypeId": 540005,
        "projectReferenceExt": "testReferenceExt",
        "externalId": "testExternalId1",
        "bpartnerId": 2156423,
        "salesRepId": 2188223,
        "dateContract": "2021-05-15",
        "dateFinish": "2021-05-16",
        "extendedProps": {
          "Note": "testNote",
          "POReference": "testReference"
        },
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 101,
                "dateStartPlan": "2021-05-17",
                "dateFinishPlan": "2021-05-18",
                "description": "resourceTestDescription_2",
                "plannedAmt": 300,
                "currencyCode": "CHF",
                "plannedDuration": 2,
                "pricePerTimeUOM": 400,
                "resourceGroupId": 3000000,
                "resourceId": 540006,
                "externalId": "testExternalId_2",
                "isActive": true
            }
        ]
    }
}
  """

    And update AD_Column:
      | TableName | ColumnName  | OPT.IsRestAPICustomColumn |
      | C_Project | POReference | false                     |
      | C_Project | Note        | false                     |

    And the metasfresh cache is reset
