@from:cucumber
Feature: Budget Project API Test

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-02T08:00:00+02:00[Europe/Athens]

  @from:cucumber
  Scenario: Persist Budget Project with associated resource using API
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
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testReferenceExt",
    "orgCode" : "001",
    "value" : "testValue",
    "name" : "testName",
    "priceListVersionId" : 2002141,
    "salesRepId" : 100,
    "projectTypeId" : 540005,
    "currencyId" : 102,
    "description" : "testDescription",
    "dateContract" : "2021-05-13",
    "dateFinish" : "2021-05-14",
    "bpartnerId" : 2156423,
    "projectReferenceExt" : "testReferenceExt",
    "projectParentId" : 2000000,
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
      | ProjectId.Identifier |
      | bp_1                 |

    And build get budget project endpoint path with the following id:
      | ProjectId.Identifier |
      | bp_1                 |

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate budget project get response
  """
  {
    "requestId": 1000059,
    "endpointResponse": {
        "projectId": 1,
        "orgCode": "001",
        "currencyId": 102,
        "name": "testName",
        "value": "testValue",
        "isActive": true,
        "priceListVersionId": 2002141,
        "description": "testDescription",
        "projectParentId": 2000000,
        "projectTypeId": 540005,
        "projectReferenceExt": "testReferenceExt",
        "bpartnerId": 2156423,
        "salesRepId": 100,
        "dateContract": "2021-05-13",
        "dateFinish": "2021-05-14",
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 103,
                "dateStartPlan": "2021-05-11",
                "dateFinishPlan": "2021-05-12",
                "description" : "resourceTestDescription",
                "plannedAmt": 150,
                "currencyId": 102,
                "plannedDuration": 2,
                "pricePerTimeUOM": 200,
                "resourceGroupId": 2000000,
                "resourceId": 540006,
                "externalId": "testExternalId",
                "resourceGroupId" : 2000000,
                "isActive": true
            }
        ]
    }
}
  """

    And metasfresh contains C_Project
      | C_Project_ID | Name       | C_Currency_ID.ISO_Code |
      | 3000000      | new_parent | EUR                    |
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testReferenceExt",
    "orgCode" : "001",
    "value" : "testValue_2",
    "name" : "testName_2",
    "priceListVersionId" : 540005,
    "salesRepId" : 2188223,
    "projectTypeId" : 540005,
    "currencyId" : 100,
    "description" : "testDescription_2",
    "dateContract" : "2021-05-15",
    "dateFinish" : "2021-05-16",
    "bpartnerId" : 2156425,
    "projectReferenceExt" : "testReferenceExt",
    "projectParentId" : 3000000,
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
                "currencyCode" : "USD"
            },
            "plannedDuration" : 2,
            "pricePerTimeUOM" : {
                "amount" : 400,
                "currencyCode" : "USD"
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

    And validate budget project get response
  """
{
    "requestId": 1,
    "endpointResponse": {
        "projectId": 1000000,
        "orgCode": "001",
        "currencyId": 102,
        "name": "testName_2",
        "value": "testValue_2",
        "isActive": true,
        "priceListVersionId": 540005,
        "description": "testDescription_2",
        "projectParentId": 3000000,
        "projectTypeId": 540005,
        "projectReferenceExt": "testReferenceExt",
        "bpartnerId": 2156425,
        "salesRepId": 100,
        "dateContract": "2021-05-15",
        "dateFinish": "2021-05-16",
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 101,
                "dateStartPlan": "2021-05-17",
                "dateFinishPlan": "2021-05-18",
                "description": "resourceTestDescription_2",
                "plannedAmt": 300,
                "currencyId": 100,
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

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/project/budget' and fulfills with '200' status code

"""
{
    "projectIdentifier" : "ext-testReferenceExt",
    "orgCode" : "001",
    "priceListVersionId" : 2002141,
    "projectTypeId" : 540005,
    "syncAdvise":{
      "ifNotExists":"CREATE",
      "ifExists":"UPDATE_MERGE"
   }
}
"""

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate budget project get response
  """
{
    "requestId": 1,
    "endpointResponse": {
        "projectId": 1000000,
        "orgCode": "001",
        "currencyId": 102,
        "name": "testName_2",
        "value": "testValue_2",
        "isActive": true,
        "priceListVersionId": 2002141,
        "description": "testDescription_2",
        "projectParentId": 3000000,
        "projectTypeId": 540005,
        "projectReferenceExt": "testReferenceExt",
        "bpartnerId": 2156425,
        "salesRepId": 100,
        "dateContract": "2021-05-15",
        "dateFinish": "2021-05-16",
        "projectResources": [
            {
                "budgetProjectResourceId": 1,
                "projectId": 1,
                "uomTimeId": 101,
                "dateStartPlan": "2021-05-17",
                "dateFinishPlan": "2021-05-18",
                "description": "resourceTestDescription_2",
                "plannedAmt": 300,
                "currencyId": 100,
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
