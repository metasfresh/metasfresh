@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund contracts on the sales and on the purchase side
## A partner that buys and sells gets its refund as a credit memo for its sales and receives it as a credit memo for its purchases, each on its own account

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And documents are accounted immediately

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | bonusCategory |
    # the bonus product books the granted refund on its revenue account and the received one on its expense account
    # (applied before the products exist: a product copies its category's accounts when it is created)
    And metasfresh contains C_ElementValues:
      | Identifier       | Value |
      | bonusRevenueAcct | 4750  |
      | bonusExpenseAcct | 5750  |
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct | OPT.P_Expense_Acct |
      | bonusCategory         | bonusRevenueAcct   | bonusExpenseAcct   |
    # not stocked: no inventory and no receipt needed
    And metasfresh contains M_Products:
      | Identifier   | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct | goodsCategory                        | false         |
      | bonusWare    | bonusCategory                        | false         |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | refundPS   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | salesPL    | refundPS                      | DE                        | EUR                 | salesPL    | true  | false         | 2              |
      | purchasePL | refundPS                      | DE                        | EUR                 | purchasePL | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | salesPLV    | salesPL                   | salesPLV    | 2026-01-01 |
      | purchasePLV | purchasePL                | purchasePLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_sales    | salesPLV                          | goodsProduct            | 100      | PCE               | Normal                        |
      | pp_purchase | purchasePLV                       | goodsProduct            | 60       | PCE               | Normal                        |
      | pp_bonus_s  | salesPLV                          | bonusWare               | 1        | PCE               | Normal                        |
      | pp_bonus_p  | purchasePLV                       | bonusWare               | 1        | PCE               | Normal                        |

    # the partner buys and sells
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.PO_PricingSystem_ID.Identifier | OPT.InvoiceRule | OPT.PO_InvoiceRule |
      | dualBP     | Y              | Y            | refundPS                      | refundPS                           | I               | I                  |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Type_Conditions |
      | condBoth   | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgBoth    | condBoth                 | monthlySchedule      | 5             | goodsCategory         | bonusWare        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termBoth   | condBoth                            | dualBP                      | 2026-07-01 | 2026-12-31 |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundBothWays_TC1
  Scenario: The sales are refunded as an AR credit memo and the purchases as an AP credit memo, each on its own account
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | salesOrder | true    | dualBP                   | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | salesLine  | salesOrder            | goodsProduct            | 10         |
    And the order identified by salesOrder is completed
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | purchaseOrder | false   | dualBP                   | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | purchaseLine | purchaseOrder         | goodsProduct            | 20         |
    And the order identified by purchaseOrder is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | salesLine      | salesIC                |
      | purchaseLine   | purchaseIC             |

    # sales 1000 -> 50; purchase 1200 -> 60: one refund candidate per side, none mixes the other side's amount
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsSOTrx | NetAmtToInvoice | DateToInvoice | DocBaseType | DocSubType |
      | salesRefund            | termBoth           | Y       | 50              | 2026-07-31    | ARC         | RI         |
      | purchaseRefund         | termBoth           | N       | 60              | 2026-07-31    | APC         | RI         |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | salesRefund                 | salesIC                         | termBoth           | 50                  |
      | purchaseRefund              | purchaseIC                      | termBoth           | 60                  |

    # the sales refund is booked on the bonus product's revenue account, the purchase refund on its expense account
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | salesRefund                       | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | salesRefundInvoice      | salesRefund                       |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID       | C_BPartner_ID | Record_ID          | M_Product_ID |
      | P_Revenue_Acct        | 50 EUR      |             | bonusRevenueAcct | dualBP        | salesRefundInvoice | bonusWare    |
      | *                     |             |             |                  |               | salesRefundInvoice |              |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | purchaseRefund                    | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | purchaseRefundInvoice   | purchaseRefund                    |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID       | C_BPartner_ID | Record_ID             | M_Product_ID |
      | P_Expense_Acct        |             | 60 EUR      | bonusExpenseAcct | dualBP        | purchaseRefundInvoice | bonusWare    |
      | *                     |             |             |                  |               | purchaseRefundInvoice |              |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundBothWays_TC2
  Scenario Outline: The refund candidate of a term is invoiceable at the end of its calendar period: <months> months
    Given metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | quarterBP  | Y              | refundPS                      | I               |
    And metasfresh contains C_InvoiceSchedules:
      | Identifier     | InvoiceDay | InvoiceDistance |
      | periodSchedule | 31         | <months>        |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier  | Type_Conditions |
      | condQuarter | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgQuarter | condQuarter              | periodSchedule       | 10            | goodsCategory         | bonusWare        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termQuarter | condQuarter                         | quarterBP                   | 2026-07-01 | 2027-12-31 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | quarterBP                | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | line1      | order1                | goodsProduct            | 10         |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | line1          | ic1                    |

    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice |
      | refundIC               | termQuarter        | 100             | <end of period> |

    Examples:
      | months | end of period |
      | 3      | 2026-09-30    |
      | 6      | 2026-12-31    |
      | 12     | 2026-12-31    |

  # ##############################################################################################
  # A term starting in the middle of a quarter: its first, short period ends with the calendar period
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundBothWays_TC3
  Scenario Outline: A term starting mid-quarter refunds its first sales at the end of the calendar period: <months> months
    Given metasfresh has date and time 2026-08-20T09:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_BPartners:
      | Identifier  | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | midPeriodBP | Y              | refundPS                      | I               |
    # invoice day 30, like the seeded schedules: the refund period still ends on the calendar period's last day
    And metasfresh contains C_InvoiceSchedules:
      | Identifier     | InvoiceDay | InvoiceDistance |
      | periodSchedule | 30         | <months>        |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Type_Conditions |
      | condMid    | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgMid     | condMid                  | periodSchedule       | 10            | goodsCategory         | bonusWare        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termMid    | condMid                             | midPeriodBP                 | 2026-08-15 | 2027-12-31 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | midPeriodBP              | 2026-08-20  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | line1      | order1                | goodsProduct            | 10         |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | line1          | ic1                    |

    # the short first period runs from the start date (15.08) to the end of its calendar period
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice   |
      | refundIC               | termMid            | 100             | <end of period> |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundIC                    | ic1                             | termMid            | 100                 |

    Examples:
      | months | end of period |
      | 3      | 2026-09-30    |
      | 6      | 2026-12-31    |
      | 12     | 2026-12-31    |
