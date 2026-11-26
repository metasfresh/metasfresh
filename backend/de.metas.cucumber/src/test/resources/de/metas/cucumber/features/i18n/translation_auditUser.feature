@from:cucumber
@allure.label.epic:E0200_System_Translations
@allure.label.feature:F35000_System_Translations
@allure.label.feature:F8010_Manufacturing_Bill_of_Material
@ghActions:run_on_executor3
Feature: Translation writers keep the audit columns right
  Missing translations added while no user is logged in carry the System user (0), not -1. Saving one base-language
  translation changes only the base record of that translation, not the base records of other products.
  Products get a generated Value, so the feature can run again on the same DB.

  Background:
    Given infrastructure and metasfresh are running



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC8b
  Scenario: missing translations added without a logged-in user get CreatedBy 0 and UpdatedBy 0
    Given metasfresh contains M_Products:
      | Identifier |
      | product_p  |
    And delete M_Product_Trl of product_p for the first active non-base system language
    When add missing translations for that language without a logged-in user
    Then M_Product_Trl of product_p for that language has CreatedBy 0 and UpdatedBy 0



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC10
  Scenario: saving the base-language translation of product A changes only product A, product B keeps Name, Updated and UpdatedBy
    Given metasfresh contains M_Products:
      | Identifier |
      | product_a  |
      | product_b  |
    # the translation rows differ from their products in Name and audit columns, as in real data
    And update M_Product:
      | M_Product_ID | Name      |
      | product_a    | Product A |
      | product_b    | Product B |
    And set M_Product_Trl of product_a for the base language by SQL:
      | Updated    | UpdatedBy | Name             |
      | 2020-01-01 | 99        | Translation of A |
    And set M_Product_Trl of product_b for the base language by SQL:
      | Updated    | UpdatedBy | Name             |
      | 2020-01-01 | 99        | Translation of B |
    When change Name of the base-language M_Product_Trl of product_a to "Renamed A" and save
    Then M_Product is verified:
      | M_Product_ID | Name      |
      | product_a    | Renamed A |
      | product_b    | Product B |
    And M_Product product_b keeps Updated and UpdatedBy
