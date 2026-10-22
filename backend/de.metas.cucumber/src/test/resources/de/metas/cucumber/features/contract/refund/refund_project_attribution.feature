@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@allure.label.feature:F2000_Contract_Management
@ghActions:run_on_executor3
Feature: Refund credit memo lines per project
## A flat refund (percentage or amount per unit) is issued as one credit memo with one line per project of the refunded sales, plus one line for the sales without project.
## A percentage is shared by the money of each project's sales, an amount per unit by their quantity.
## A scaled (tiered) refund is not shared: its scale is reached on the whole base, so it stays one line.

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

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP | Y              | refundPS                      | I               |

    # the refund is invoiced on the last day of every month
    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

  # ##############################################################################################
  # A flat percentage over the sales of two projects: one line per project, each its share of the money
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @allure.label.feature:F2000_Contract_Management
  @Id:refundProjectAttribution_TC1
  Scenario: A flat percentage refund is split into one credit memo line per project by the money of its sales
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Type_Conditions |
      | refundConditions | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID |
      | refundConfig | refundConditions         | monthlySchedule      | 10            | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | refundTerm | refundConditions                    | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |
    And metasfresh contains C_Projects:
      | Identifier |
      | project1   |
      | project2   |

    # project 1: 6 x 100 = 600; project 2: 8 x 50 = 400 (its quantity share would be 6/14, not 60 %)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | olP1       | order1                | goodsProduct            | 6          | 100   | project1     |
      | olP2       | order1                | goodsProduct            | 8          | 50    | project2     |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olP1           | salesICP1              |
      | olP2           | salesICP2              |

    # 10 % of 1000
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DocBaseType | DocSubType | OnePerProject |
      | refundIC               | refundTerm         | 100             | ARC         | RI         | Y             |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule | IncludeSameRecordCandidates |
      | refundIC                          | Y                     | Y                           |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    # 10 % of 600 on project 1 and 10 % of 400 on project 2; the total stays 100 net + 19 % tax
    Then validate created invoices
      | C_Invoice_ID.Identifier | DocBaseType | GrandTotal | DocStatus |
      | refundInvoice           | ARC         | 119.00 EUR | CO        |
    And validate invoice lines for refundInvoice:
      | M_Product_ID | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goodsProduct | 1           | project1     | 60.00      |
      | goodsProduct | 1           | project2     | 40.00      |

  # ##############################################################################################
  # A flat amount per unit over the sales of two projects: one line per project, each its share of the quantity, in the config's currency
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @allure.label.feature:F2000_Contract_Management
  @Id:refundProjectAttribution_TC2
  Scenario: A flat refund of 2 CHF per unit is split into one credit memo line per project by the quantity of its sales
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier        | Type_Conditions |
      | conditionsPerUnit | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | configPerUnit | conditionsPerUnit        | monthlySchedule      | 2         | CHF                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termPerUnit | conditionsPerUnit                   | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |
    And metasfresh contains C_Projects:
      | Identifier |
      | project1   |
      | project2   |

    # project 1: 6 units x 100 = 600; project 2: 4 units x 50 = 200 (its money share would be 3/4 and 1/4, not 6/10 and 4/10)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price | C_Project_ID |
      | olP1       | order1                | goodsProduct            | 6          | 100   | project1     |
      | olP2       | order1                | goodsProduct            | 4          | 50    | project2     |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olP1           | salesICP1              |
      | olP2           | salesICP2              |

    # 10 units x 2 CHF
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice | OnePerProject |
      | refundIC               | termPerUnit        | CHF                 | false   | 20              | Y             |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule | IncludeSameRecordCandidates |
      | refundIC                          | Y                     | Y                           |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    # 6 units x 2 CHF on project 1 and 4 units x 2 CHF on project 2; the total stays 20 CHF net + 19 % tax
    Then validate created invoices
      | C_Invoice_ID.Identifier | DocBaseType | GrandTotal | DocStatus |
      | refundInvoice           | ARC         | 23.80 CHF  | CO        |
    And validate invoice lines for refundInvoice:
      | M_Product_ID | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goodsProduct | 1           | project1     | 12.00      |
      | goodsProduct | 1           | project2     | 8.00       |

  # ##############################################################################################
  # Sales without any project: one credit memo line without project, as before
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @allure.label.feature:F2000_Contract_Management
  @Id:refundProjectAttribution_TC3
  Scenario: A refund of sales without project stays one credit memo line without project
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Type_Conditions |
      | refundConditions | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID |
      | refundConfig | refundConditions         | monthlySchedule      | 10            | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | refundTerm | refundConditions                    | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 6          |
      | ol2        | order1                | goodsProduct            | 4          |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol1            | salesIC1               |
      | ol2            | salesIC2               |

    # 10 % of 1000
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DocBaseType | DocSubType |
      | refundIC               | refundTerm         | 100             | ARC         | RI         |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundIC                          | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    Then validate created invoices
      | C_Invoice_ID.Identifier | DocBaseType | GrandTotal | DocStatus |
      | refundInvoice           | ARC         | 119.00 EUR | CO        |
    And validate invoice lines for refundInvoice:
      | M_Product_ID | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goodsProduct | 1           | null         | 100.00     |

  # ##############################################################################################
  # Sales of two projects and sales without project: one line per project plus one line without project
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @allure.label.feature:F2000_Contract_Management
  @Id:refundProjectAttribution_TC4
  Scenario: A refund of sales with and without project has one credit memo line per project and one line without project
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Type_Conditions |
      | refundConditions | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID |
      | refundConfig | refundConditions         | monthlySchedule      | 10            | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | refundTerm | refundConditions                    | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |
    And metasfresh contains C_Projects:
      | Identifier |
      | project1   |
      | project2   |

    # project 1: 500; project 2: 300; without project: 200
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_Project_ID |
      | olP1       | order1                | goodsProduct            | 5          | project1     |
      | olP2       | order1                | goodsProduct            | 3          | project2     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olNoProj   | order1                | goodsProduct            | 2          |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olP1           | salesICP1              |
      | olP2           | salesICP2              |
      | olNoProj       | salesICNoProj          |

    # 10 % of 1000
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DocBaseType | DocSubType | OnePerProject |
      | refundIC               | refundTerm         | 100             | ARC         | RI         | Y             |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule | IncludeSameRecordCandidates |
      | refundIC                          | Y                     | Y                           |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    # 50 + 30 + 20 = 100 net + 19 % tax
    Then validate created invoices
      | C_Invoice_ID.Identifier | DocBaseType | GrandTotal | DocStatus |
      | refundInvoice           | ARC         | 119.00 EUR | CO        |
    And validate invoice lines for refundInvoice:
      | M_Product_ID | QtyInvoiced | C_Project_ID | LineNetAmt |
      | goodsProduct | 1           | project1     | 50.00      |
      | goodsProduct | 1           | project2     | 30.00      |
      | goodsProduct | 1           | null         | 20.00      |

  # ##############################################################################################
  # A scaled refund over the sales of two projects: the scale is reached on the whole base, so it stays one line
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @allure.label.feature:F2000_Contract_Management
  @Id:refundProjectAttribution_TC5
  Scenario: A scaled refund over two projects is not split: the scale applies to the whole base and stays one credit memo line
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
    And metasfresh contains C_Projects:
      | Identifier |
      | project1   |
      | project2   |

    # 6 units on each project: neither reaches the scale from 10 units alone, together they do
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_Project_ID |
      | olP1       | order1                | goodsProduct            | 6          | project1     |
      | olP2       | order1                | goodsProduct            | 6          | project2     |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olP1           | salesICP1              |
      | olP2           | salesICP2              |

    # all 12 units x 2 CHF, on one refund candidate and without error
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | C_Currency.ISO_Code | IsError | NetAmtToInvoice |
      | refundIC               | termScaled         | CHF                 | false   | 24              |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundIC                          | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |

    # 24 CHF net + 19 % tax, on one line (no project is asserted: the line belongs to no single project)
    Then validate created invoices
      | C_Invoice_ID.Identifier | DocBaseType | GrandTotal | DocStatus |
      | refundInvoice           | ARC         | 28.56 CHF  | CO        |
    And validate invoice lines for refundInvoice:
      | M_Product_ID | QtyInvoiced | LineNetAmt |
      | goodsProduct | 1           | 24.00      |
