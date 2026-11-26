@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8010_Manufacturing_Bill_of_Material
@ghActions:run_on_executor3
Feature: Verify BOMs with a product category, report the products that failed
  A category run verifies every BOM product of the category. When a product fails, the run still ends normally and its end
  message and process log name the failed product and the reason. A single run keeps showing the user error.
  Products get a generated Value and each scenario creates its own product category, so the feature can run again on the same DB.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC3
  Scenario: TC3 - verify with a product category, one of two products has no default BOM: the run ends normally and names the failed product
    Given metasfresh contains M_Product_Categories:
      | M_Product_Category_ID |
      | category_1            |
    And metasfresh contains M_Products:
      | Identifier        | M_Product_Category_ID |
      | valid_product     | category_1            |
      | valid_component   | category_1            |
      | failing_product   | category_1            |
      | failing_component | category_1            |
    And metasfresh contains PP_Product_BOM
      | Identifier  | M_Product_ID    | ValidFrom  | PP_Product_BOMVersions_ID |
      | valid_bom   | valid_product   | 2021-01-01 | validBomVersions          |
      | failing_bom | failing_product | 2021-01-01 | failingBomVersions        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier       | PP_Product_BOM_ID | M_Product_ID      | ValidFrom  | QtyBatch | ComponentType |
      | valid_bom_line   | valid_bom         | valid_component   | 2021-01-01 | 1        | CO            |
      | failing_bom_line | failing_bom       | failing_component | 2021-01-01 | 1        | CO            |
    # only the valid BOM is completed, so the failing product (IsBOM = Y) has no default BOM; real case: a BOM product without a default BOM; the process messages are German (local cucumber language)
    And the PP_Product_BOM identified by valid_bom is completed
    And update M_Product:
      | M_Product_ID      | IsVerified |
      | valid_product     | N          |
      | valid_component   | Y          |
      | failing_product   | N          |
      | failing_component | Y          |
    And the existing AD_Issue records are remembered

    When run PP_Product_BOM_Check for PP_Product_BOM valid_bom with M_Product_Category category_1

    Then the PP_Product_BOM_Check result is OK with message key PP_Product_BOM_Check.CategoryRunFailed and parameters:
      | Param           |
      | 1               |
      | 2               |
      | failing_product |
    And the PP_Product_BOM_Check process log contains:
      | M_Product_ID    | ReasonContains      |
      | failing_product | Standard-Stückliste |
    And M_Product is verified:
      | M_Product_ID    | IsVerified |
      | valid_product   | Y          |
      | failing_product | N          |
    And count of new AD_Issue rows since the run is 0



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC7
  Scenario: TC7 - verify a single product without a default BOM: the user validation error stays
    Given metasfresh contains M_Product_Categories:
      | M_Product_Category_ID |
      | category_1            |
    And metasfresh contains M_Products:
      | Identifier        | M_Product_Category_ID |
      | failing_product   | category_1            |
      | failing_component | category_1            |
    And metasfresh contains PP_Product_BOM
      | Identifier  | M_Product_ID    | ValidFrom  | PP_Product_BOMVersions_ID |
      | failing_bom | failing_product | 2021-01-01 | failingBomVersions        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier       | PP_Product_BOM_ID | M_Product_ID      | ValidFrom  | QtyBatch | ComponentType |
      | failing_bom_line | failing_bom       | failing_component | 2021-01-01 | 1        | CO            |
    And update M_Product:
      | M_Product_ID      | IsVerified |
      | failing_product   | N          |
      | failing_component | Y          |

    When run PP_Product_BOM_Check for PP_Product_BOM failing_bom

    Then the PP_Product_BOM_Check result is user validation error containing:
      | MessageContains     |
      | Standard-Stückliste |
    And M_Product is verified:
      | M_Product_ID    | IsVerified |
      | failing_product | N          |
