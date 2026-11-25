@from:cucumber
@allure.label.epic:E0380_Masterdata_Products
@allure.label.feature:F00500
@ghActions:run_on_executor3
Feature: Business rule on M_Product when the stored UpdatedBy is not a valid user
  A product that was last changed by an unknown user can still be saved.
  Its business rule events name the System user (AD_User_ID 0) as triggering user.
  Products get a generated Value, so the feature can run again on the same DB;
  each rule only watches the products with the Description of its scenario.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC4_valid_user
  Scenario: valid user - the stored user, not the logged-in user, stays the triggering user
    Given metasfresh contains AD_Users:
      | Identifier |
      | other_user |
    And metasfresh contains M_Products:
      | Identifier | Description |
      | product_1  | tc4valid    |
    And metasfresh contains AD_BusinessRules with M_Product trigger:
      | Identifier          | Name                | ValidationSQL                       | TriggerConditionSQL                 |
      | tc4_valid_user_rule | tc4_valid_user_rule | M_Product.Description = 'tc4valid'  | M_Product.Description = 'tc4valid'  |
    And set M_Product.UpdatedBy by SQL:
      | M_Product_ID | UpdatedBy  |
      | product_1    | other_user |

    When update M_Product:
      | M_Product_ID | IsStocked |
      | product_1    | N         |

    Then validate AD_BusinessRule_Events for:
      | M_Product_ID | AD_BusinessRule_ID  | Triggering_User_ID |
      | product_1    | tc4_valid_user_rule | other_user         |



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC4_minus_one
  Scenario: minus one - stored UpdatedBy -1 does not block the save, System user is the triggering user
    Given metasfresh contains M_Products:
      | Identifier | Description |
      | product_1  | tc4minus1   |
    And metasfresh contains AD_BusinessRules with M_Product trigger:
      | Identifier         | Name               | ValidationSQL                       | TriggerConditionSQL                 |
      | tc4_minus_one_rule | tc4_minus_one_rule | M_Product.Description = 'tc4minus1' | M_Product.Description = 'tc4minus1' |
    And set M_Product.UpdatedBy by SQL:
      | M_Product_ID | UpdatedBy |
      | product_1    | -1        |

    When update M_Product:
      | M_Product_ID | IsStocked |
      | product_1    | N         |

    Then validate M_Product.UpdatedBy of product_1 is the logged-in user
    And validate AD_BusinessRule_Events for:
      | M_Product_ID | AD_BusinessRule_ID | Triggering_User_ID |
      | product_1    | tc4_minus_one_rule | 0                  |



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC4_notice
  Scenario: notice - stored UpdatedBy -1, the warning of a violated rule reaches the System user as a notice
    Given AD_Note table is reset
    And set notification type of AD_User 0 to N
    And metasfresh contains M_Products:
      | Identifier | Description |
      | product_1  | tc4notice   |
    And metasfresh contains AD_BusinessRules with M_Product trigger:
      | Identifier      | Name            | ValidationSQL                          | TriggerConditionSQL                |
      | tc4_notice_rule | tc4_notice_rule | M_Product.Description <> 'tc4notice'   | M_Product.Description = 'tc4notice' |
    And set M_Product.UpdatedBy by SQL:
      | M_Product_ID | UpdatedBy |
      | product_1    | -1        |

    When update M_Product:
      | M_Product_ID | IsStocked |
      | product_1    | N         |
    And process business rule events

    Then validate AD_Record_Warning for M_Product product_1
      | AD_BusinessRule_ID |
      | tc4_notice_rule    |
    And load AD_User:
      | AD_User_ID.Identifier | Login  |
      | user_system           | System |
    And after not more than 30s, validate AD_Note:
      | Identifier | AD_Message_ID.Identifier | OPT.AD_User_ID.Identifier |
      | note_1     | tc4_notice_rule_warning  | user_system               |



  # ##########################################################################################################
  # ##########################################################################################################
  # ##########################################################################################################
  @Id:S33498_TC4_missing_user
  Scenario: missing user - stored UpdatedBy without AD_User row does not block the save, System user is the triggering user
    Given metasfresh contains M_Products:
      | Identifier | Description |
      | product_1  | tc4missing  |
    And metasfresh contains AD_BusinessRules with M_Product trigger:
      | Identifier           | Name                 | ValidationSQL                        | TriggerConditionSQL                  |
      | tc4_missing_user_rule | tc4_missing_user_rule | M_Product.Description = 'tc4missing' | M_Product.Description = 'tc4missing' |
    And set M_Product.UpdatedBy by SQL:
      | M_Product_ID | UpdatedBy    |
      | product_1    | NO_SUCH_USER |

    When update M_Product:
      | M_Product_ID | IsStocked |
      | product_1    | N         |

    Then validate M_Product.UpdatedBy of product_1 is the logged-in user
    And validate AD_BusinessRule_Events for:
      | M_Product_ID | AD_BusinessRule_ID    | Triggering_User_ID |
      | product_1    | tc4_missing_user_rule | 0                  |
