@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Contract data import of refund terms
## A refund term is based on a product category or a bonus product, so an import row needs no product

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | bonusCategory |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | bonusWare  | bonusCategory                        | false         |
    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer |
      | invoicePartner | Y              |
    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2024,2025,2026,2027       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier |
      | importConditions | Refund          | zeroDurTrans                            |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgImport  | importConditions         | monthlySchedule      | 3             | goodsCategory         | bonusWare        |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundTermImport_TC1
  Scenario: A refund row without a product creates and completes a refund term without a product, a row with an unknown product key is rejected
    Given metasfresh contains I_Flatrate_Term:
      | Identifier | C_BPartner_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | StartDate  | EndDate    | ProductValue          |
      | row1       | invoicePartner           | importConditions                    | 2026-07-01 | 2026-12-31 |                       |
      | rowNoProd  | invoicePartner           | importConditions                    | 2026-07-01 | 2026-12-31 | noSuchProductKey12345 |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier | IsResolved | CreatedTermDocStatus | CreatedTermHasProduct |
      | row1       | true       | CO                   | false                 |
    And validate I_Flatrate_Term:
      | Identifier | I_ErrorMsg        |
      | rowNoProd  | Product not found |
