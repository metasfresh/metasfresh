@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00127_BundleSinglePrice
@ghActions:run_on_executor5
Feature: Compensation group calibration rules

  A calibration rule needs a customer or a business partner group and a non-negative factor.
  A rule that an order line refers to cannot be deleted, it is deactivated instead.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-10-07T08:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_Products:
      | Identifier | Name                  |
      | component  | CalibrationRule_Comp  |
    And metasfresh contains C_BP_Groups:
      | Identifier |
      | bpGroup    |
    And metasfresh contains C_BPartners:
      | Identifier | Name                   | IsCustomer | C_BP_Group_ID |
      | customer   | CalibrationRule_Cust   | Y          | bpGroup       |


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  @Id:S26881_TC9
  Scenario: A rule needs a customer or a business partner group and a non-negative factor
    When metasfresh contains C_CompensationGroup_CalibrationRule expecting error:
      | Identifier | M_Product_ID | GroupCompensationCalibrationFactor | ErrorMessageKey                                                |
      | rule_none  | component    | 0.5                                | C_CompensationGroup_CalibrationRule_BPartnerOrGroupRequired    |
    And metasfresh contains C_CompensationGroup_CalibrationRule expecting error:
      | Identifier | C_BPartner_ID | GroupCompensationCalibrationFactor | ErrorMessageKey                                     |
      | rule_neg   | customer      | -1                                 | C_CompensationGroup_CalibrationRule_NegativeFactor  |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier    | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_customer | customer      | component    | 0.5                                |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | C_BP_Group_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_group | bpGroup       | component    | 0.8                                |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
      | rule_zero  | customer      | component    | 0                                  |


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  @Id:S26881_TC15
  Scenario: A rule used by an order line is deactivated instead of deleted
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_sales   | ps                 | DE           | EUR           | true  | N             |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_sales  | pl_sales       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_sales              | component    | 10.00    | PCE      |
    And metasfresh contains C_BPartners:
      | Identifier | Name                    | IsCustomer | M_PricingSystem_ID |
      | priced     | CalibrationRule_Priced  | Y          | ps                 |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_used  | priced        | 0.7                                |
    And metasfresh contains C_CompensationGroup_CalibrationRule:
      | Identifier  | C_BPartner_ID | GroupCompensationCalibrationFactor |
      | rule_unused | priced        | 0.9                                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order      | true    | priced        | 2026-10-07  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | OPT.C_CompensationGroup_CalibrationRule_ID |
      | orderLine  | order      | component    | 7          | rule_used                                  |

    When delete C_CompensationGroup_CalibrationRule expecting error:
      | Identifier | ErrorMessageKey                                            |
      | rule_used  | C_CompensationGroup_CalibrationRule_UsedDeactivateInstead  |
    And deactivate C_CompensationGroup_CalibrationRule:
      | Identifier |
      | rule_used  |
    And delete C_CompensationGroup_CalibrationRule:
      | Identifier  |
      | rule_unused |
