@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: A refund amount per unit in another currency than the sales
## The refund is not converted and not silently 0: its invoice candidate is in error until the currency of the refund config is corrected

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And documents are accounted immediately

    # not stocked: no inventory needed
    And metasfresh contains M_Products:
      | Identifier   | OPT.IsStocked |
      | goodsProduct | false         |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | refundPS   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name     | SOTrx | IsTaxIncluded | PricePrecision |
      | refundPL   | refundPS                      | DE                        | EUR                 | refundPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name      | ValidFrom  |
      | refundPLV  | refundPL                  | refundPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods   | refundPLV                         | goodsProduct            | 100      | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP | Y              | refundPS                      | I               |

    # the refund is invoiced on the last day of every month
    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

  # ##############################################################################################
  # 0.50 CHF per unit on EUR sales -> the refund candidate is in error; corrected to EUR -> 0.50 EUR per unit
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC1
  Scenario: A per-unit refund in CHF on EUR sales is in error, and is computed in EUR once the currency of the refund config is corrected
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Type_Conditions |
      | conditionsPerUnit | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configPerUnit | conditionsPerUnit        | monthlySchedule      | 0.50      | CHF                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termPerUnit | conditionsPerUnit                   | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 10         |
    When the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol1            | salesIC                |

    # not converted, not silently 0: the refund candidate tells what is wrong and how to correct it, so it is not invoiced
    # (the invoice candidate update run writes its error in the base language)
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsError | ErrorMsg                                                                                                                                                                                   |
      | refundIC               | termPerUnit        | true    | kann nicht berechnet werden: der Betrag pro Einheit ist in CHF, die vergüteten Umsätze sind in EUR. Bitte die Währung der Rückvergütungszeile in der Vertragsbedingung auf EUR korrigieren |
    And the C_Invoice_Candidate identified by salesIC has no C_Invoice_Candidate_Assignment

    When update C_Flatrate_RefundConfigs:
      | Identifier    | C_Currency.ISO_Code |
      | configPerUnit | EUR                 |

    # 10 units x 0.50 EUR
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsError | NetAmtToInvoice |
      | refundIC               | termPerUnit        | false   | 5               |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundIC                    | salesIC                         | termPerUnit        | 5                   | 10               |

  # ##############################################################################################
  # The currency of a percentage refund line plays no role
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC2
  Scenario: A percentage refund line with a CHF currency refunds EUR sales normally
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier           | Type_Conditions |
      | conditionsPercentage | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier       | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | C_Currency.ISO_Code | M_Product_ID |
      | configPercentage | conditionsPercentage     | monthlySchedule      | 3             | CHF                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier     | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termPercentage | conditionsPercentage                | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 10         |
    When the order identified by order1 is completed

    # 3 % of 1000 EUR
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsError | NetAmtToInvoice |
      | refundIC               | termPercentage     | false   | 30              |
