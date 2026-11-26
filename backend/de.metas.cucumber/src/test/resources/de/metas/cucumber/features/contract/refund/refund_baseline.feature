@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund contract with a single product
## Pins today's behaviour: one refund term, one product, a percentage of the invoiced sales

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
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID |
      | refundConfig | refundConditions         | monthlySchedule      | 20            | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | refundTerm | refundConditions                    | customerBP                  | goodsProduct            | 2026-07-01 | 2026-12-31 |

  # ##############################################################################################
  # A sales order of the product creates a refund candidate that is invoiced as a credit memo
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundBaseline_TC1
  Scenario: The refund of a single-product term is the term's percentage of the invoiced sale
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

    # 20 % of 1000, invoiceable from the schedule's next invoice date
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice | DocBaseType | DocSubType |
      | refundIC               | refundTerm         | 200             | 2026-07-31    | ARC         | RI         |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundIC                    | salesIC                         | refundTerm         | 200                 | 10               |

    # the sale is invoiced; the refund candidate stays as it is
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | salesIC                           |
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice |
      | refundIC               | refundTerm         | 200             |

    # the refund is invoiced as a credit memo
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundIC                          | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundIC                          |
    Then validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | DocStatus | DocBaseType | DocSubType |
      | refundInvoice | customerBP    | CO        | ARC         | RI         |
    # the credit memo reduces the customer's receivable by 200 plus 19 % tax and books the 200 on the refunded product's account
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID     | M_Product_ID |
      | C_Receivable_Acct     |             | 238 EUR     | customerBP    | refundInvoice | -            |
      | P_Revenue_Acct        | 200 EUR     |             | customerBP    | refundInvoice | goodsProduct |
      | T_Due_Acct            | 38 EUR      |             | customerBP    | refundInvoice | -            |
