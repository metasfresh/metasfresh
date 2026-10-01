@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Compensation-group contract take-over of the customer's discount lines
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name  | Value                  |
      | goodsCategory | Ware | WareS32355TakeOver     |
      | foodCategory  | Food  | FoodS32355TakeOver     |

    And metasfresh contains M_Products:
      | Identifier              | OPT.M_Product_Category_ID.Identifier |
      | ownDiscountProduct      | goodsCategory                        |
      | otherOwnDiscountProduct | foodCategory                         |
      | customerDiscountProduct | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name           | OPT.IsAdditive |
      | schema     | Contract bonus | true           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name     | C_CompensationGroup_Schema_ID.Identifier |
      | settings   | Settings | schema                                   |

  # ##############################################################################################
  # One take-over record per product category and settings.
  # A second take-over record for a category that already has one is refused.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS9a
  Scenario: A second take-over record for the same product category on one settings is refused
    Given metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID       |
      | takeOver1  | settings                                | goodsCategory         | ownDiscountProduct |

    Then creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code DBUniqueConstraint:
      | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID       |
      | settings                                | goodsCategory         | ownDiscountProduct |

  # ##############################################################################################
  # A customer discount product is listed on at most one take-over record per settings.
  # The same customer product on a second take-over record of the same settings is refused.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS9b
  Scenario: The same customer discount product on two take-over records of one settings is refused
    Given metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID            |
      | takeOver1  | settings                                | goodsCategory         | ownDiscountProduct      |
      | takeOver2  | settings                                | foodCategory          | otherOwnDiscountProduct |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID            |
      | takeOver1                                        | customerDiscountProduct |

    Then creating C_CompensationGroup_ContractSettings_TakeOver_Product is refused with error code ContractCompGroup_TakeOverProductUnique:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID            |
      | takeOver2                                        | customerDiscountProduct |
