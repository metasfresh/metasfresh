@from:cucumber
@workOrder
Feature: Work Order and Budget Project API Test

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-02T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains S_ResourceType:
      | S_ResourceType_ID.Identifier | Value                    | C_UOM_ID.X12DE355 |
      | projectResourceType          | projectResourceTypeValue | MJ                |
    And metasfresh contains S_Resource:
      | S_Resource_ID.Identifier | Value                | InternalName                | S_ResourceType_ID.Identifier |
      | projectResource          | projectResourceValue | projectResourceInternalName | projectResourceType          |

  @from:cucumber
  Scenario: Persist Work Order Project with associated step and resource using API
    Given metasfresh contains C_ProjectType
      | C_ProjectType_ID.Identifier | ProjectCategory | Name                     |
      | workOrderProjectType        | W               | workOrderProjectTypeName |

    And store JsonWorkOrderProjectUpsertRequest in context
      | C_ProjectType_ID.Identifier | S_Resource_ID.Identifier |
      | workOrderProjectType        | projectResource          |
    And the metasfresh REST-API endpoint path 'api/v2/project/workorder' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate JsonWorkOrderProjectUpsertResponse coming back after POST request

    And append id from response to project endpoint path 'api/v2/project/workorder' and store it to context
    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    And validate JsonWorkOrderProjectResponse coming back after GET request

  @from:cucumber
  Scenario: Persist Budget Project with associated resource using API
    Given metasfresh contains C_ProjectType
      | C_ProjectType_ID.Identifier | ProjectCategory | Name                  |
      | budgetProjectType           | B               | budgetProjectTypeName |
    And metasfresh contains S_Resource_Group:
      | S_Resource_Group_ID.Identifier | Name                | DurationUnit |
      | budgetResourceGroup            | budgetResourceGroup | m            |

    And store JsonBudgetProjectUpsertRequest in context
      | C_ProjectType_ID.Identifier | S_Resource_ID.Identifier | C_UOM_ID.X12DE355 | S_Resource_Group_ID.Identifier |
      | budgetProjectType           | projectResource          | MJ                | budgetResourceGroup            |
    And the metasfresh REST-API endpoint path 'api/v2/project/budget' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate JsonBudgetProjectResourceResponse coming back after POST request

    And append id from response to project endpoint path 'api/v2/project/budget' and store it to context
    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    And validate JsonBudgetProjectResponse coming back after GET request