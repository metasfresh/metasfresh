@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: The currency of a refund
## A per-unit refund is issued in the currency of its refund config, whatever the currency of the sales; a percentage refund in the currency of the sales.
## The quantities sold in different currencies count together for the scales of a per-unit refund.
## The currency of a per-unit line can be corrected until the line has issued a refund.

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

    # the customer buys in EUR; its pricing system also has a CHF price list (for every country), on which a refund in CHF is issued
    And metasfresh contains M_PricingSystems
      | Identifier |
      | refundPS   |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision |
      | refundPL    | refundPS                      | DE                        | EUR                 | refundPL    | true  | false         | 2              |
      | refundPLCHF | refundPS                      |                           | CHF                 | refundPLCHF | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | refundPLV    | refundPL                  | refundPLV    | 2026-01-01 |
      | refundPLVCHF | refundPLCHF               | refundPLVCHF | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods       | refundPLV                         | goodsProduct            | 100      | PCE               | Normal                        |
      | pp_goodsRefund | refundPLVCHF                      | goodsProduct            | 100      | PCE               | Normal                        |

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
  # 2 CHF per unit on EUR sales -> a refund in CHF, invoiced as a CHF credit memo
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC1
  Scenario: A per-unit refund of 2 CHF on EUR sales is issued in CHF and invoiced as a CHF credit memo
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Type_Conditions |
      | conditionsPerUnit | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configPerUnit | conditionsPerUnit        | monthlySchedule      | 2         | CHF                 | goodsProduct |
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

    # 10 units x 2 CHF
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundIC               | termPerUnit        | CHF                 | false   | 20              |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundIC                    | salesIC                         | termPerUnit        | 20                  | 10               |

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundIC                          | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    # 20 CHF net + 19 % tax of the refund product
    Then validate created invoices
      | C_Invoice_ID.Identifier | DocBaseType | GrandTotal | DocStatus |
      | refundInvoice           | ARC         | 23.80 CHF  | CO        |

  # ##############################################################################################
  # A percentage refund is issued in the currency of the sales; the currency of its line plays no role
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC2
  Scenario: A percentage refund line with a CHF currency refunds EUR sales in EUR
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
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundIC               | termPercentage     | EUR                 | false   | 30              |

  # ##############################################################################################
  # Quantities sold in EUR and in CHF count together for the scale of a per-unit refund
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC3
  Scenario: 6 units sold in EUR and 6 units sold in CHF reach the per-unit scale from 10 units together
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Type_Conditions |
      | conditionsScaled | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier     | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID | MinQty |
      | configFromZero | conditionsScaled         | monthlySchedule      | 1         | CHF                 | goodsProduct | 0      |
      | configFromTen  | conditionsScaled         | monthlySchedule      | 2         | CHF                 | goodsProduct | 10     |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termScaled | conditionsScaled                    | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule | M_PricingSystem_ID |
      | orderEUR   | true    | customerBP               | 2026-07-01  | I           | refundPS           |
      | orderCHF   | true    | customerBP               | 2026-07-01  | I           | chfPS              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olEUR      | orderEUR              | goodsProduct            | 6          |
      | olCHF      | orderCHF              | goodsProduct            | 6          |
    When the order identified by orderEUR is completed
    # 6 units x 1 CHF, below the scale
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundIC               | termScaled         | CHF                 | false   | 6               |
    And the order identified by orderCHF is completed

    # 12 units reach the scale from 10: all 12 units x 2 CHF, on the same CHF refund candidate
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundIC               | termScaled         | CHF                 | false   | 24              |
    And the C_Flatrate_Term identified by termScaled has no refund C_Invoice_Candidate in EUR

  # ##############################################################################################
  # The currency of a per-unit line is corrected before the line has issued a refund
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC4
  Scenario: A per-unit line entered in CHF instead of EUR is corrected: the open refund is computed again in EUR
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Type_Conditions |
      | conditionsPerUnit | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configPerUnit | conditionsPerUnit        | monthlySchedule      | 2         | CHF                 | goodsProduct |
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
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundCHF              | termPerUnit        | CHF                 | false   | 20              |

    When update C_Flatrate_RefundConfigs:
      | Identifier    | C_Currency.ISO_Code |
      | configPerUnit | EUR                 |

    # 10 units x 2 EUR, on a refund candidate in EUR; the one in CHF is gone
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundEUR              | termPerUnit        | EUR                 | false   | 20              |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundEUR                   | salesIC                         | termPerUnit        | 20                  | 10               |
    And the C_Flatrate_Term identified by termPerUnit has no refund C_Invoice_Candidate in CHF

  # ##############################################################################################
  # Once a per-unit line has issued a refund, its currency is fixed
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC5
  Scenario: The currency of a per-unit line that has issued a refund cannot be changed anymore
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Type_Conditions |
      | conditionsPerUnit | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configPerUnit | conditionsPerUnit        | monthlySchedule      | 2         | CHF                 | goodsProduct |
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
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundIC               | termPerUnit        | CHF                 | false   | 20              |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundIC                          | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    Then update C_Flatrate_RefundConfigs:
      | Identifier    | C_Currency.ISO_Code | ErrorCode                             |
      | configPerUnit | EUR                 | REFUND_CONFIG_CURRENCY_NOT_CHANGEABLE |

  # ##############################################################################################
  # A refund in a currency without a price list of the customer's pricing system is in error
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundAmountCurrency_TC6
  Scenario: A per-unit refund in CHF for a customer whose pricing system has no CHF price list is in error
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | eurOnlyPS  |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision |
      | eurOnlyPL  | eurOnlyPS                     | DE                        | EUR                 | eurOnlyPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | eurOnlyPLV | eurOnlyPL                 | eurOnlyPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goodsEurOnly | eurOnlyPLV                        | goodsProduct            | 100      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | eurCustomer | Y              | eurOnlyPS                     | I               |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Type_Conditions |
      | conditionsPerUnit | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configPerUnit | conditionsPerUnit        | monthlySchedule      | 2         | CHF                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termPerUnit | conditionsPerUnit                   | eurCustomer                 | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | eurCustomer              | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 10         |
    When the order identified by order1 is completed

    # the refund in CHF is not invoiced on a price list in another currency
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | ErrorMsg.AD_Message                                         | ErrorMsg.Params |
      | refundIC               | termPerUnit        | CHF                 | true    | de.metas.contracts.refund.RefundProductHasNoPriceInCurrency | *,CHF,*         |
