@from:cucumber
@ghActions:run_on_executor6
Feature: Manufacturing order rest controller

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-09T13:30:13+01:00[Europe/Berlin]

  Scenario: retrieve PP_Order by metasfreshId

    Given metasfresh contains M_Products:
      | Identifier   | Value         | Name         |
      | finishedGood | productValue  | finishedGood |
      | component_1  | productValue1 | component1   |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | finishedGood            | 2022-05-09 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | component_1             | 2022-05-09 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder                | MOP         | finishedGood            | 10         | testResource             | 2022-05-09T23:59:00.00Z | 2022-05-09T23:59:00.00Z | 2022-05-09T23:59:00.00Z | Y                |

    When store PP_Order endpointPath /api/v2/manufacturing/orders/:ppOrder in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate retrieve pp_order response:
      | PP_Order_ID.Identifier | OrgCode | DateOrdered             | DatePromised            | DateStartSchedule       | QtyEntered | UomCode | M_Product_ID.Identifier | components.M_Product_ID.Identifier | components.QtyBatch | components.ComponentType | components.UomCode |
      | ppOrder                | 001     | 2022-05-09T23:59:00.00Z | 2022-05-09T23:59:00.00Z | 2022-05-09T23:59:00.00Z | 10         | PCE     | finishedGood            | component_1                        | 100.0               | CO                       | PCE                |
