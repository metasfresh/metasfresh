@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8010_Manufacturing_Bill_of_Material
@ghActions:run_on_executor3
Feature: Verify BOMs when a component was last changed by an invalid user
  A product whose stored UpdatedBy is -1 (no valid user) can still be saved, so a BOM that contains it can be verified.
  Products get a generated Value, the category scenario creates its own product category, and each business rule only
  watches the products with the Description of its scenario, so the feature can run again on the same DB.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC1
  @Id:S33498_TC5
  Scenario: verify the BOM without category, a component with UpdatedBy -1 does not break it; the production order is refused before and created after
    Given metasfresh contains M_Products:
      | Identifier  | Description  |
      | bom_product | bomcheck_tc1 |
      | component_a | bomcheck_tc1 |
      | component_b | bomcheck_tc1 |
      | component_c | bomcheck_tc1 |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom        | bom_product  | 2021-01-01 | bomVersions               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch | ComponentType |
      | line_a     | bom               | component_a  | 2021-01-01 | 3        | CO            |
      | line_b     | bom               | component_b  | 2021-01-01 | 2        | PK            |
      | line_c     | bom               | component_c  | 2021-01-01 | 1        | PK            |
    And the PP_Product_BOM identified by bom is completed
    # real data shape: the BOM product and component C are not verified, A and B are; B and C were last changed by user -1
    And update M_Product:
      | M_Product_ID | IsVerified |
      | bom_product  | N          |
      | component_a  | Y          |
      | component_b  | Y          |
      | component_c  | N          |
    And metasfresh contains AD_BusinessRules with M_Product trigger:
      | Identifier        | Name              | ValidationSQL                          | TriggerConditionSQL                    |
      | bomcheck_tc1_rule | bomcheck_tc1_rule | M_Product.Description = 'bomcheck_tc1' | M_Product.Description = 'bomcheck_tc1' |
    And set M_Product.UpdatedBy by SQL:
      | M_Product_ID | UpdatedBy |
      | component_b  | -1        |
      | component_c  | -1        |
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | productPlanning | bom_product  | bomVersions               | false        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And create S_Resource:
      | Identifier | S_ResourceType_ID | IsManufacturingResource | ManufacturingResourceType | PlanningHorizon |
      | plant      | 1000000           | Y                       | PT                        | 999             |

    # a production order for a BOM product that is not verified is refused (TC5, not pinned by any other test)
    Then create PP_Order expecting message containing not verified:
      | PP_Order_ID  | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | refusedOrder | MOP         | bom_product  | 5          | plant         | 2021-04-16T07:00:00.00Z | 2021-04-18T07:00:00.00Z | 2021-04-16T07:00:00.00Z | N                |

    When run PP_Product_BOM_Check for PP_Product_BOM bom

    Then the PP_Product_BOM_Check result is OK with message key OK
    And M_Product is verified:
      | M_Product_ID | IsVerified | UpdatedBy |
      | bom_product  | Y          | acting    |
      | component_a  | Y          | acting    |
      | component_b  | Y          | -1        |
      | component_c  | Y          | acting    |
    # after the verification the production order is created (TC5)
    And create PP_Order:
      | PP_Order_ID  | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | createdOrder | MOP         | bom_product  | 5          | plant         | 2021-04-16T07:00:00.00Z | 2021-04-18T07:00:00.00Z | 2021-04-16T07:00:00.00Z | N                |



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC2
  Scenario: verify with the product category of the BOM product, all products are verified
    Given metasfresh contains M_Product_Categories:
      | M_Product_Category_ID |
      | category_1            |
    And metasfresh contains M_Products:
      | Identifier  | Description  | M_Product_Category_ID |
      | bom_product | bomcheck_tc2 | category_1            |
      | component_a | bomcheck_tc2 | category_1            |
      | component_b | bomcheck_tc2 | category_1            |
      | component_c | bomcheck_tc2 | category_1            |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom        | bom_product  | 2021-01-01 | bomVersions               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch | ComponentType |
      | line_a     | bom               | component_a  | 2021-01-01 | 3        | CO            |
      | line_b     | bom               | component_b  | 2021-01-01 | 2        | PK            |
      | line_c     | bom               | component_c  | 2021-01-01 | 1        | PK            |
    And the PP_Product_BOM identified by bom is completed
    # real data shape: the BOM product and component C are not verified, A and B are; B and C were last changed by user -1
    And update M_Product:
      | M_Product_ID | IsVerified |
      | bom_product  | N          |
      | component_a  | Y          |
      | component_b  | Y          |
      | component_c  | N          |
    And metasfresh contains AD_BusinessRules with M_Product trigger:
      | Identifier        | Name              | ValidationSQL                          | TriggerConditionSQL                    |
      | bomcheck_tc2_rule | bomcheck_tc2_rule | M_Product.Description = 'bomcheck_tc2' | M_Product.Description = 'bomcheck_tc2' |
    And set M_Product.UpdatedBy by SQL:
      | M_Product_ID | UpdatedBy |
      | component_b  | -1        |
      | component_c  | -1        |

    When run PP_Product_BOM_Check for PP_Product_BOM bom with M_Product_Category category_1

    Then M_Product is verified:
      | M_Product_ID | IsVerified | UpdatedBy |
      | bom_product  | Y          | acting    |
    And the PP_Product_BOM_Check result is OK with message key org.eevolution.process.PP_Product_BOM_Check.CategoryRunOK and parameters:
      | Param |
      | 1     |

