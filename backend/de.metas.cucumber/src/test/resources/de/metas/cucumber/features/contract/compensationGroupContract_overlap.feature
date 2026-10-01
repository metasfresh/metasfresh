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
  # A second overlapping contract for the same invoice partner and a common document type is
  # rejected. Real compensation-group conditions carry no product (M_Product_Flatrate_ID is hidden
  # and optional for this type) — the terms below are product-less, as they would be in production.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC23
  Scenario: A second overlapping compensation-group contract for the same invoice partner and a common document type is rejected
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name         | OPT.IsAdditive |
      | firstSchema  | First bonus  | true           |
      | secondSchema | Second bonus | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier       | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | firstSchemaLine  | firstSchema                              | discountProduct         | 3                         | goodsCategory                        |
      | secondSchemaLine | secondSchema                             | discountProduct         | 5                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier     | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | firstSettings  | First settings  | firstSchema                              |
      | secondSettings | Second settings | secondSchema                             |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | firstSettings                                      | docTypeSalesOrder       |
      | secondSettings                                     | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Name              | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | firstConditions  | First conditions  | CompensationGroup | zeroDurTrans                            | firstSettings                                          |
      | secondConditions | Second conditions | CompensationGroup | zeroDurTrans                            | secondSettings                                         |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | firstTerm  | firstConditions                     | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
      | secondTerm | secondConditions                    | invoicePartner              | 2026-09-01 | 2027-03-31 | DR            | false         |
    And the C_Flatrate_Term identified by firstTerm is completed

    Then the C_Flatrate_Term identified by secondTerm cannot be completed because of error code ContractCompGroup_OverlappingTerm

  # ##############################################################################################
  # A non-overlapping contract (next year) for the same invoice partner and document type is
  # accepted.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC24
  Scenario: A non-overlapping compensation-group contract for the same invoice partner and document type is accepted
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name         | OPT.IsAdditive |
      | firstSchema  | First bonus  | true           |
      | secondSchema | Second bonus | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier       | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | firstSchemaLine  | firstSchema                              | discountProduct         | 3                         | goodsCategory                        |
      | secondSchemaLine | secondSchema                             | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier     | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | firstSettings  | First settings  | firstSchema                              |
      | secondSettings | Second settings | secondSchema                             |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | firstSettings                                      | docTypeSalesOrder       |
      | secondSettings                                     | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Name              | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | firstConditions  | First conditions  | CompensationGroup | zeroDurTrans                            | firstSettings                                          |
      | secondConditions | Second conditions | CompensationGroup | zeroDurTrans                            | secondSettings                                         |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | firstTerm  | firstConditions                     | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
      | secondTerm | secondConditions                    | invoicePartner              | 2027-01-01 | 2027-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by firstTerm is completed

    Then the C_Flatrate_Term identified by secondTerm is completed

  # ##############################################################################################
  # Mid-year replacement — cancel the old contract at date X (existing cancellation step), complete
  # the new one from X + 1: orders on/before X get the old bonus, later orders the new one.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC25
  Scenario: Ending a contract mid-year and completing its replacement from the next day is accepted, each order keeping its own bonus
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name      | OPT.IsAdditive |
      | oldSchema  | Old bonus | true           |
      | newSchema  | New bonus | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier    | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | oldSchemaLine | oldSchema                                | discountProduct         | 3                         | goodsCategory                        |
      | newSchemaLine | newSchema                                | discountProduct         | 5                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier  | Name         | C_CompensationGroup_Schema_ID.Identifier |
      | oldSettings | Old settings | oldSchema                                |
      | newSettings | New settings | newSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | oldSettings                                        | docTypeSalesOrder       |
      | newSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Name           | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | oldConditions | Old conditions | CompensationGroup | zeroDurTrans                            | oldSettings                                            |
      | newConditions | New conditions | CompensationGroup | zeroDurTrans                            | newSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termOld    | oldConditions                       | invoicePartner              | 2026-01-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by termOld is completed
    And the C_Flatrate_Term identified by termOld is cancelled with change date 2026-07-15

    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termNew    | newConditions                       | invoicePartner              | 2026-07-16 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by termNew is completed

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
      | ol_onCutoffDiscount       | orderOnCutoff         | discountProduct         | 1          | true                        | 3                               | -30   | termOld                           |
      | ol_afterCutoffDiscount    | orderAfterCutoff      | discountProduct         | 1          | true                        | 5                               | -50   | termNew                           |

  # ##############################################################################################
  # Draft and voided contracts do not count as overlapping — the new contract still completes.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC26
  Scenario: Draft and voided compensation-group contracts of the same invoice partner do not block completion
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name          | OPT.IsAdditive |
      | draftSchema  | Draft schema  | true           |
      | voidedSchema | Voided schema | true           |
      | newSchema    | New schema    | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier       | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | draftSchemaLine  | draftSchema                              | discountProduct         | 3                         | goodsCategory                        |
      | voidedSchemaLine | voidedSchema                             | discountProduct         | 3                         | goodsCategory                        |
      | newSchemaLine    | newSchema                                | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier     | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | draftSettings  | Draft settings  | draftSchema                              |
      | voidedSettings | Voided settings | voidedSchema                             |
      | newSettings    | New settings    | newSchema                                |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | draftSettings                                      | docTypeSalesOrder       |
      | voidedSettings                                     | docTypeSalesOrder       |
      | newSettings                                        | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Name              | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | draftConditions  | Draft conditions  | CompensationGroup | zeroDurTrans                            | draftSettings                                          |
      | voidedConditions | Voided conditions | CompensationGroup | zeroDurTrans                            | voidedSettings                                         |
      | newConditions    | New conditions    | CompensationGroup | zeroDurTrans                            | newSettings                                            |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier      | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termDraft       | draftConditions                     | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |
      | termVoidedSetup | voidedConditions                    | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by termVoidedSetup is completed
    And the C_Flatrate_Term identified by termVoidedSetup is cancelled with change date 2026-05-01

    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termNew    | newConditions                       | invoicePartner              | 2026-06-01 | 2026-12-31 | DR            | false         |

    Then the C_Flatrate_Term identified by termNew is completed

  # ##############################################################################################
  # The same invoice partner holds a sales-side and a purchase-side compensation-group contract,
  # with DISJOINT document types — overlapping dates, both complete.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC27
  Scenario: A sales-side and a purchase-side compensation-group contract of the same invoice partner with disjoint document types both complete
    Given metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name            | OPT.IsAdditive |
      | salesSchema    | Sales schema    | true           |
      | purchaseSchema | Purchase schema | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | salesSchemaLine    | salesSchema                              | discountProduct         | 3                         | goodsCategory                        |
      | purchaseSchemaLine | purchaseSchema                           | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | salesSettings    | Sales settings    | salesSchema                              |
      | purchaseSettings | Purchase settings | purchaseSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | salesSettings                                      | docTypeSalesOrder       |
      | purchaseSettings                                   | docTypePurchaseOrder    |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | salesConditions    | Sales conditions    | CompensationGroup | zeroDurTrans                            | salesSettings                                          |
      | purchaseConditions | Purchase conditions | CompensationGroup | zeroDurTrans                            | purchaseSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | salesTerm    | salesConditions                     | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
      | purchaseTerm | purchaseConditions                  | invoicePartner              | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by salesTerm is completed

    Then the C_Flatrate_Term identified by purchaseTerm is completed
