@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: One active compensation-group contract per invoice partner and document type
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name | Value             |
      | goodsCategory | Ware | WareS32353Overlap |

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
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_Contract_Change:
      | Identifier   | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | contractChg1 | zeroDurTrans                        | ST     | Qu             | 0        | day          |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | invoicePartner | Y              | contractPS                    |

  # ##############################################################################################
  # TS7: a second overlapping contract for the same invoice partner and a common document type
  # is rejected. The two conditions use their own placeholder product so the pre-existing,
  # product-keyed overlap check (FlatrateBL#hasOverlappingTerms) never fires here, isolating the
  # new document-type-aware check under test.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC23
  Scenario: A second overlapping compensation-group contract for the same invoice partner and a common document type is rejected
    Given metasfresh contains M_Products:
      | Identifier           | OPT.M_Product_Category_ID.Identifier |
      | contractProductTc23A | goodsCategory                        |
      | contractProductTc23B | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name          | OPT.IsAdditive |
      | tc23SchemaA | TC23 schema A | true           |
      | tc23SchemaB | TC23 schema B | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | tc23SchemaLineA | tc23SchemaA                              | discountProduct         | 3                         | goodsCategory                        |
      | tc23SchemaLineB | tc23SchemaB                              | discountProduct         | 5                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | tc23SettingsA | TC23 settings A | tc23SchemaA                              |
      | tc23SettingsB | TC23 settings B | tc23SchemaB                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | tc23SettingsA                                      | docTypeSalesOrder       |
      | tc23SettingsB                                      | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name              | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | tc23ConditionsA | TC23 conditions A | CompensationGroup | contractProductTc23A                 | zeroDurTrans                            | tc23SettingsA                                          |
      | tc23ConditionsB | TC23 conditions B | CompensationGroup | contractProductTc23B                 | zeroDurTrans                            | tc23SettingsB                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc23TermA  | tc23ConditionsA                     | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
      | tc23TermB  | tc23ConditionsB                     | invoicePartner              | 2026-09-01 | 2027-03-31 | DR            | false         |
    And the C_Flatrate_Term identified by tc23TermA is completed

    Then the C_Flatrate_Term identified by tc23TermB cannot be completed because of error code ContractCompGroup_OverlappingTerm

  # ##############################################################################################
  # TS7: a non-overlapping contract (next year) for the same invoice partner and document type
  # is accepted.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC24
  Scenario: A non-overlapping compensation-group contract for the same invoice partner and document type is accepted
    Given metasfresh contains M_Products:
      | Identifier           | OPT.M_Product_Category_ID.Identifier |
      | contractProductTc24A | goodsCategory                        |
      | contractProductTc24B | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name          | OPT.IsAdditive |
      | tc24SchemaA | TC24 schema A | true           |
      | tc24SchemaB | TC24 schema B | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | tc24SchemaLineA | tc24SchemaA                              | discountProduct         | 3                         | goodsCategory                        |
      | tc24SchemaLineB | tc24SchemaB                              | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | tc24SettingsA | TC24 settings A | tc24SchemaA                              |
      | tc24SettingsB | TC24 settings B | tc24SchemaB                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | tc24SettingsA                                      | docTypeSalesOrder       |
      | tc24SettingsB                                      | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name              | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | tc24ConditionsA | TC24 conditions A | CompensationGroup | contractProductTc24A                 | zeroDurTrans                            | tc24SettingsA                                          |
      | tc24ConditionsB | TC24 conditions B | CompensationGroup | contractProductTc24B                 | zeroDurTrans                            | tc24SettingsB                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc24TermA  | tc24ConditionsA                     | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
      | tc24TermB  | tc24ConditionsB                     | invoicePartner              | 2027-01-01 | 2027-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by tc24TermA is completed

    Then the C_Flatrate_Term identified by tc24TermB is completed

  # ##############################################################################################
  # TS7: mid-year replacement — cancel the old contract at date X (existing cancellation step),
  # complete the new one from X + 1: orders on/before X get the old bonus, later orders the new one.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC25
  Scenario: Ending a contract mid-year and completing its replacement from the next day is accepted, each order keeping its own bonus
    Given metasfresh contains M_Products:
      | Identifier             | OPT.M_Product_Category_ID.Identifier |
      | contractProductTc25Old | goodsCategory                        |
      | contractProductTc25New | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier    | Name           | OPT.IsAdditive |
      | tc25SchemaOld | TC25 old bonus | true           |
      | tc25SchemaNew | TC25 new bonus | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier        | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | tc25SchemaLineOld | tc25SchemaOld                            | discountProduct         | 3                         | goodsCategory                        |
      | tc25SchemaLineNew | tc25SchemaNew                            | discountProduct         | 5                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier      | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | tc25SettingsOld | TC25 settings old | tc25SchemaOld                            |
      | tc25SettingsNew | TC25 settings new | tc25SchemaNew                            |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | tc25SettingsOld                                    | docTypeSalesOrder       |
      | tc25SettingsNew                                    | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Name                | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | tc25ConditionsOld | TC25 conditions old | CompensationGroup | contractProductTc25Old               | zeroDurTrans                            | tc25SettingsOld                                        |
      | tc25ConditionsNew | TC25 conditions new | CompensationGroup | contractProductTc25New               | zeroDurTrans                            | tc25SettingsNew                                        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc25TermOld | tc25ConditionsOld                   | invoicePartner              | 2026-01-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by tc25TermOld is completed
    And the C_Flatrate_Term identified by tc25TermOld is cancelled with change date 2026-07-15

    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc25TermNew | tc25ConditionsNew                   | invoicePartner              | 2026-07-16 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by tc25TermNew is completed

    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderOnCutoff    | true    | invoicePartner           | 2026-07-15  |
      | orderAfterCutoff | true    | invoicePartner           | 2026-07-16  |

    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_onCutoff    | orderOnCutoff         | goodsProduct            | 1          |
      | ol_afterCutoff | orderAfterCutoff      | goodsProduct            | 1          |

    And the order identified by orderOnCutoff is completed
    And the order identified by orderAfterCutoff is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_onCutoffDiscount       | orderOnCutoff         | discountProduct         | 1          | true                        | 3                               | -30   | tc25TermOld                       |
      | ol_afterCutoffDiscount    | orderAfterCutoff      | discountProduct         | 1          | true                        | 5                               | -50   | tc25TermNew                       |

  # ##############################################################################################
  # TS7: draft and voided contracts do not count as overlapping — the new contract still completes.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC26
  Scenario: Draft and voided compensation-group contracts of the same invoice partner do not block completion
    Given metasfresh contains M_Products:
      | Identifier                | OPT.M_Product_Category_ID.Identifier |
      | contractProductTc26Draft  | goodsCategory                        |
      | contractProductTc26Voided | goodsCategory                        |
      | contractProductTc26New    | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier       | Name               | OPT.IsAdditive |
      | tc26SchemaDraft  | TC26 draft schema  | true           |
      | tc26SchemaVoided | TC26 voided schema | true           |
      | tc26SchemaNew    | TC26 new schema    | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier           | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | tc26SchemaLineDraft  | tc26SchemaDraft                          | discountProduct         | 3                         | goodsCategory                        |
      | tc26SchemaLineVoided | tc26SchemaVoided                         | discountProduct         | 3                         | goodsCategory                        |
      | tc26SchemaLineNew    | tc26SchemaNew                            | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier         | Name                 | C_CompensationGroup_Schema_ID.Identifier |
      | tc26SettingsDraft  | TC26 draft settings  | tc26SchemaDraft                          |
      | tc26SettingsVoided | TC26 voided settings | tc26SchemaVoided                         |
      | tc26SettingsNew    | TC26 new settings    | tc26SchemaNew                            |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | tc26SettingsDraft                                  | docTypeSalesOrder       |
      | tc26SettingsVoided                                 | docTypeSalesOrder       |
      | tc26SettingsNew                                    | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier           | Name                   | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | tc26ConditionsDraft  | TC26 conditions draft  | CompensationGroup | contractProductTc26Draft             | zeroDurTrans                            | tc26SettingsDraft                                      |
      | tc26ConditionsVoided | TC26 conditions voided | CompensationGroup | contractProductTc26Voided            | zeroDurTrans                            | tc26SettingsVoided                                     |
      | tc26ConditionsNew    | TC26 conditions new    | CompensationGroup | contractProductTc26New               | zeroDurTrans                            | tc26SettingsNew                                        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier          | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc26TermDraft       | tc26ConditionsDraft                 | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |
      | tc26TermVoidedSetup | tc26ConditionsVoided                | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by tc26TermVoidedSetup is completed
    And the C_Flatrate_Term identified by tc26TermVoidedSetup is cancelled with change date 2026-05-01

    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc26TermNew | tc26ConditionsNew                   | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |

    Then the C_Flatrate_Term identified by tc26TermNew is completed

  # ##############################################################################################
  # Review Focus 4: the same invoice partner holds a sales-side and a purchase-side compensation-
  # group contract, with DISJOINT document types — overlapping dates, both complete.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC27
  Scenario: A sales-side and a purchase-side compensation-group contract of the same invoice partner with disjoint document types both complete
    Given metasfresh contains M_Products:
      | Identifier                  | OPT.M_Product_Category_ID.Identifier |
      | contractProductTc27Sales    | goodsCategory                        |
      | contractProductTc27Purchase | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier         | Name                 | OPT.IsAdditive |
      | tc27SalesSchema    | TC27 sales schema    | true           |
      | tc27PurchaseSchema | TC27 purchase schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier             | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | tc27SalesSchemaLine    | tc27SalesSchema                          | discountProduct         | 3                         | goodsCategory                        |
      | tc27PurchaseSchemaLine | tc27PurchaseSchema                       | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier           | Name                   | C_CompensationGroup_Schema_ID.Identifier |
      | tc27SalesSettings    | TC27 sales settings    | tc27SalesSchema                          |
      | tc27PurchaseSettings | TC27 purchase settings | tc27PurchaseSchema                       |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | tc27SalesSettings                                  | docTypeSalesOrder       |
      | tc27PurchaseSettings                               | docTypePurchaseOrder    |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                     | Type_Conditions   | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | tc27SalesConditions    | TC27 sales conditions    | CompensationGroup | contractProductTc27Sales             | zeroDurTrans                            | tc27SalesSettings                                      |
      | tc27PurchaseConditions | TC27 purchase conditions | CompensationGroup | contractProductTc27Purchase          | zeroDurTrans                            | tc27PurchaseSettings                                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier       | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | tc27SalesTerm    | tc27SalesConditions                 | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
      | tc27PurchaseTerm | tc27PurchaseConditions              | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by tc27SalesTerm is completed

    Then the C_Flatrate_Term identified by tc27PurchaseTerm is completed
