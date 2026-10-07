@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Automatic renewal of a compensation-group contract by the scheduled contract extension
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-01T09:00:00+01:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name | Value               |
      | goodsCategory | Ware | WareS32353AutoRenew |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier |
      | goodsProduct    | goodsCategory                        |
      | discountProduct | goodsCategory                        |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | contractPL | contractPS                    | DE                        | EUR                 | contractPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | contractPLV | contractPL                | contractPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods    | contractPLV                       | goodsProduct            | 1000     | PCE               | Normal                        |
      | pp_discount | contractPLV                       | discountProduct         | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name  | OPT.IsAdditive |
      | bonusSchema | Bonus | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | bonusSchemaLine | bonusSchema                              | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | bonusSettings | Bonus settings | bonusSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | bonusSettings                                      | docTypeSalesOrder       |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | invoicePartner | Y              | contractPS                    |

  # ##############################################################################################
  # Transition: 1 year, notice 1 month, extend one, auto-complete the follow-up term.
  # - the term runs 2026-01-01 .. 2026-12-31, notice date 2026-11-30
  # - the scheduled extension creates and completes the follow-up term 2027-01-01 .. 2027-12-31
  # - orders in both periods get the 3% bonus, each from its own term
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC69
  Scenario: A compensation-group contract with a 1-year extend-one transition is renewed by the scheduled extension, and orders of both periods get the bonus
    Given metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.IsAutoCompleteNewTerm | OPT.EnsurePeriodsForYears |
      | oneYearTrans | 1            | year             | 1                | month                | EO                | true                      | 2026,2027,2028            |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | bonusConditions | Bonus conditions | CompensationGroup | oneYearTrans                            | bonusSettings                                          |
    # DropShip_BPartner_ID is set on purpose: FlatrateBL.createNewTerm throws an NPE when extending a term without a
    # drop-ship partner (known defect, handled separately). Imported and partner-created contracts always have one.
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | firstTerm  | bonusConditions                     | invoicePartner              | invoicePartner                      | 2026-01-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by firstTerm is completed

    # the notice date has passed
    And metasfresh has date and time 2026-12-15T09:00:00+01:00[Europe/Berlin]

    When the scheduled contract extension runs for the C_Flatrate_Term identified by firstTerm

    Then the C_Flatrate_Term identified by firstTerm is marked as processed by the scheduled contract extension
    And the C_Flatrate_Term identified by firstTerm is not eligible for the scheduled contract extension
    And the C_Flatrate_Term identified by firstTerm has the follow-up C_Flatrate_Term:
      | Identifier | StartDate  | EndDate    | NoticeDate | DocStatus | IsAutoRenew | C_Flatrate_Conditions_ID | Bill_BPartner_ID |
      | nextTerm   | 2027-01-01 | 2027-12-31 | 2027-11-30 | CO        | true        | bonusConditions          | invoicePartner   |

    And metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderFirstPeriod    | true    | invoicePartner           | 2026-12-16  |
      | orderFollowUpPeriod | true    | invoicePartner           | 2027-03-15  |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_firstPeriod    | orderFirstPeriod      | goodsProduct            | 1          |
      | ol_followUpPeriod | orderFollowUpPeriod   | goodsProduct            | 1          |
    And the order identified by orderFirstPeriod is completed
    And the order identified by orderFollowUpPeriod is completed

    # 3% of 1000
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_firstPeriodDiscount    | orderFirstPeriod      | discountProduct         | 1          | true                        | 3                               | -30   | firstTerm                         |
      | ol_followUpPeriodDiscount | orderFollowUpPeriod   | discountProduct         | 1          | true                        | 3                               | -30   | nextTerm                          |


  # ##############################################################################################
  # Transition: duration 0 (keep the entered end date), notice 0 days, extend one.
  # - the term runs 2026-01-01 .. 2026-12-31 (entered), notice date 2026-12-31
  # - the scheduled extension cannot compute an end date for the follow-up term;
  #   saving it is refused (EndDate missing), so no follow-up term is created
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC70
  Scenario: A compensation-group contract with a zero-duration extend-one transition is not renewed by the scheduled extension
    Given metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.IsAutoCompleteNewTerm | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | true                      | 2026,2027                 |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | bonusConditions | Bonus conditions | CompensationGroup | zeroDurTrans                            | bonusSettings                                          |
    # DropShip_BPartner_ID is set on purpose: FlatrateBL.createNewTerm throws an NPE when extending a term without a
    # drop-ship partner (known defect, handled separately). Imported and partner-created contracts always have one.
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | firstTerm  | bonusConditions                     | invoicePartner              | invoicePartner                      | 2026-01-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by firstTerm is completed

    # the notice date has passed
    And metasfresh has date and time 2027-01-05T09:00:00+01:00[Europe/Berlin]

    When the scheduled contract extension fails for the C_Flatrate_Term identified by firstTerm with message containing Should have been set by the system

    Then the C_Flatrate_Term identified by firstTerm has no follow-up C_Flatrate_Term

    # the contract still applies within its own period; after its end date there is no bonus
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderOwnPeriod | true    | invoicePartner           | 2026-12-20  |
      | orderAfterEnd  | true    | invoicePartner           | 2027-02-01  |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_ownPeriod | orderOwnPeriod        | goodsProduct            | 1          |
      | ol_afterEnd  | orderAfterEnd         | goodsProduct            | 1          |
    And the order identified by orderOwnPeriod is completed
    And the order identified by orderAfterEnd is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_ownPeriodDiscount      | orderOwnPeriod        | discountProduct         | 1          | true                        | 3                               | -30   | firstTerm                         |
    And the order identified by orderAfterEnd has 1 order lines
