@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Compensation group contract fixtures — smoke coverage
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-15T09:00:00+02:00[Europe/Berlin]
    And metasfresh contains M_Product_Category:
      | Identifier           | Name      | Value           |
      | compGroupCategoryTop | Beverages | BeveragesS32353 |
    And metasfresh contains M_Product_Category:
      | Identifier           | Name        | Value            | OPT.M_Product_Category_Parent_ID.Identifier |
      | compGroupCategorySub | Soft Drinks | SoftDrinksS32353 | compGroupCategoryTop                        |
    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier |
      | contractProduct |                                      |
      | discountProduct | compGroupCategorySub                 |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | contractPL | contractPS                    | DE                        | EUR                 | contractPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | contractPLV | contractPL                | contractPLV | 2026-01-01 |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | contractBP | Y              | contractPS                    |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier      | Name             | OPT.IsAdditive |
      | compGroupSchema | CompGroup schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | compGroupSchemaLine | compGroupSchema                          | discountProduct         | 10                        | compGroupCategorySub                 |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier        | Name               | C_CompensationGroup_Schema_ID.Identifier |
      | compGroupSettings | CompGroup settings | compGroupSchema                          |
    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | compGroupSettings                                  | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_Contract_Change:
      | Identifier   | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | contractChg1 | zeroDurTrans                        | SU     | Qu             | 0        | day          |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier          | Name                 | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | compGroupConditions | CompGroup conditions | CompensationGroup | contractProduct                      | zeroDurTrans                            | compGroupSettings                                      |

  # ##############################################################################################
  # Complete a duration-0 term, then prove overlap protection rejects a second overlapping term
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC1
  Scenario: Complete a duration-0 compensation-group contract term and keep its entered end date
    Given metasfresh contains C_Flatrate_Terms:
      | Identifier    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | compGroupTerm | compGroupConditions                 | contractBP                  | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by compGroupTerm is completed
    Then validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.EndDate |
      | compGroupTermChecked          | compGroupConditions                 | contractBP                  | contractProduct         | 2026-12-31  |

    Given metasfresh contains C_Flatrate_Terms:
      | Identifier        | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | compGroupTermNext | compGroupConditions                 | contractBP                  | 2026-11-01 | 2027-01-31 | DR            | false         |
    Then completing the C_Flatrate_Term identified by "compGroupTermNext" is rejected with message containing "überlappt"

  # ##############################################################################################
  # Cancel a completed term; order-level compensation-group wiring
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC2
  Scenario: Cancel a completed compensation-group term and wire an order to one
    Given metasfresh contains C_Flatrate_Terms:
      | Identifier    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | cancelledTerm | compGroupConditions                 | contractBP                  | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by cancelledTerm is completed
    And the C_Flatrate_Term identified by cancelledTerm is cancelled with change date 2027-01-01

    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderWithGroup    | true    | contractBP               | 2026-06-15  |
      | orderWithoutGroup | true    | contractBP               | 2026-06-15  |
    And metasfresh contains C_Order_CompensationGroups:
      | Identifier     | C_Order_ID.Identifier | Name        | OPT.C_Flatrate_Term_ID.Identifier |
      | orderCompGroup | orderWithGroup        | Mischkarton | cancelledTerm                     |
    Then no C_Order_CompensationGroup exists for order "orderWithoutGroup"
