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

    # the same customer also buys in CHF
    And metasfresh contains M_PricingSystems
      | Identifier |
      | chfPS      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name  | SOTrx | IsTaxIncluded | PricePrecision |
      | chfPL      | chfPS                         | DE                        | CHF                 | chfPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | chfPLV     | chfPL                     | chfPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goodsCHF | chfPLV                            | goodsProduct            | 100      | PCE               | Normal                        |

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
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsError | ErrorMsg.AD_Message                                    | ErrorMsg.Params |
      | refundIC               | termPerUnit        | true    | de.metas.contracts.refund.RefundAmountCurrencyMismatch | *,CHF,EUR       |
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

  # ##############################################################################################
  # Tiered refund (only the quantity above a scale): same error, same correction
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC3
  Scenario: A tiered per-unit refund in CHF on EUR sales is in error, and is computed in EUR once corrected
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Type_Conditions |
      | conditionsTiers | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID | RefundMode |
      | configTiers | conditionsTiers          | monthlySchedule      | 0.50      | CHF                 | goodsProduct | T          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termTiers  | conditionsTiers                     | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

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

    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsError | ErrorMsg.AD_Message                                    | ErrorMsg.Params |
      | refundIC               | termTiers          | true    | de.metas.contracts.refund.RefundAmountCurrencyMismatch | *,CHF,EUR       |
    And the C_Invoice_Candidate identified by salesIC has no C_Invoice_Candidate_Assignment

    When update C_Flatrate_RefundConfigs:
      | Identifier  | C_Currency.ISO_Code |
      | configTiers | EUR                 |

    # 10 units x 0.50 EUR
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsError | NetAmtToInvoice |
      | refundIC               | termTiers          | false   | 5               |

  # ##############################################################################################
  # Sales in two currencies: each currency has its own refund candidate; the one in another currency than the per-unit amount shows the error
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC4
  Scenario: EUR sale first, then a CHF sale, on a per-unit refund in EUR
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier   | Type_Conditions |
      | conditionsEU | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configEU   | conditionsEU             | monthlySchedule      | 0.50      | EUR                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termEU     | conditionsEU                        | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule | M_PricingSystem_ID |
      | orderEUR   | true    | customerBP               | 2026-07-01  | I           | refundPS           |
      | orderCHF   | true    | customerBP               | 2026-07-01  | I           | chfPS              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olEUR      | orderEUR              | goodsProduct            | 10         |
      | olCHF      | orderCHF              | goodsProduct            | 4          |
    When the order identified by orderEUR is completed
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundEUR              | termEU             | EUR                 | false   | 5               |
    And the order identified by orderCHF is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olCHF          | salesCHF               |

    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | ErrorMsg.AD_Message                                    | ErrorMsg.Params |
      | refundCHF              | termEU             | CHF                 | true    | de.metas.contracts.refund.RefundAmountCurrencyMismatch | *,EUR,CHF       |
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundEUR              | termEU             | EUR                 | false   | 5               |
    And the C_Invoice_Candidate identified by salesCHF has no C_Invoice_Candidate_Assignment

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC5
  Scenario: CHF sale first, then an EUR sale, on a per-unit refund in EUR
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier   | Type_Conditions |
      | conditionsEU | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configEU   | conditionsEU             | monthlySchedule      | 0.50      | EUR                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termEU     | conditionsEU                        | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule | M_PricingSystem_ID |
      | orderCHF   | true    | customerBP               | 2026-07-01  | I           | chfPS              |
      | orderEUR   | true    | customerBP               | 2026-07-01  | I           | refundPS           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olCHF      | orderCHF              | goodsProduct            | 4          |
      | olEUR      | orderEUR              | goodsProduct            | 10         |
    When the order identified by orderCHF is completed
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | ErrorMsg.AD_Message                                    | ErrorMsg.Params |
      | refundCHF              | termEU             | CHF                 | true    | de.metas.contracts.refund.RefundAmountCurrencyMismatch | *,EUR,CHF       |
    And the order identified by orderEUR is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olEUR          | salesEUR               |

    # the EUR sale gets an EUR refund candidate of its own, instead of being added to the CHF one
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundEUR              | termEU             | EUR                 | false   | 5               |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundEUR                   | salesEUR                        | 5                   | 10               |
