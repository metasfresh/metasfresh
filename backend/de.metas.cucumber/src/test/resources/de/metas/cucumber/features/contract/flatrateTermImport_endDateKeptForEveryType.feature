@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract data import keeps a duration-0 end date for every contract type, not only compensation-group
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier   | OPT.X12DE355 |
      | callOProduct | PCE          |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | callOPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name    | SOTrx | IsTaxIncluded | PricePrecision |
      | callOPL    | callOPS                       | DE                        | EUR                 | callOPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name     | ValidFrom  |
      | callOPLV   | callOPL                   | callOPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | callOPP    | callOPLV                          | callOProduct            | 10       | PCE               | Normal                        |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType |
      | callOTrans | 0            | day              | 0                | day                  | EO                |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer |
      | callOBP    | Y              |

  # ##############################################################################################
  # A CallOrder-type row (product-ful, unlike compensation-group) on a duration-0 transition keeps
  # the imported end date instead of failing at creation — the same fix applies to every contract
  # type, not only compensation-group.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC33
  Scenario: Importing a call-order contract row with a duration-0 transition keeps the imported end date
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name              | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend |
      | callOConditions | Import call order | CallOrder       | callOTrans                              | callOPS                           | Ca                       |
    And metasfresh contains I_Flatrate_Term:
      | Identifier | C_BPartner_ID.Identifier | C_Flatrate_Conditions_Value | M_Product_ID.Identifier | StartDate  | EndDate    | Price | Qty |
      | row1       | callOBP                  | Import call order           | callOProduct            | 2026-01-01 | 2026-12-31 | 10    | 1   |

    When the FlatrateTermImportProcess is invoked

    Then validate I_Flatrate_Term:
      | Identifier | IsResolved | CreatedTermEndDate | CreatedTermDocStatus |
      | row1       | true       | 2026-12-31         | CO                   |
