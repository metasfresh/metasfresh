@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund per unit is computed in the sales currency
## The refund is always computed and booked in the currency of the sale.
## The currency stored on an "amount per unit" refund condition line is not used: the amount is taken as an amount in the sales currency.

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

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Type_Conditions |
      | refundConditions | Refund          |
    # 0.50 per unit; the condition line carries CHF (e.g. defaulted from the accounting currency), the sales are in EUR
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_ID |
      | refundConfig | refundConditions         | monthlySchedule      | 0.50      | CHF                 | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | refundTerm | refundConditions                    | customerBP                  | goodsProduct            | 2026-07-01 | 2026-12-31 |

  # ##############################################################################################
  # A sales order in EUR against a per-unit refund line that carries CHF
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundSalesCurrency_TC1
  Scenario: A per-unit refund is computed and booked in the sales currency, whatever currency the refund line carries
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 10         |
    And the order identified by order1 is completed

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol1            | salesIC                |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | salesIC                           | 1000            |

    # 10 units x 0.50 = 5, in EUR and not converted from CHF
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice | DocBaseType | DocSubType |
      | refundIC               | refundTerm         | 5               | 2026-07-31    | ARC         | RI         |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundIC                    | salesIC                         | refundTerm         | 5                   | 10               |

    # the refund is invoiced as a credit memo in EUR
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundIC                          | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |
    Then validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | DocStatus | DocBaseType | DocSubType |
      | refundInvoice | customerBP    | CO        | ARC         | RI         |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID     | M_Product_ID |
      | C_Receivable_Acct     |             | 5.95 EUR    | customerBP    | refundInvoice | -            |
      | P_Revenue_Acct        | 5 EUR       |             | customerBP    | refundInvoice | goodsProduct |
      | T_Due_Acct            | 0.95 EUR    |             | customerBP    | refundInvoice | -            |
