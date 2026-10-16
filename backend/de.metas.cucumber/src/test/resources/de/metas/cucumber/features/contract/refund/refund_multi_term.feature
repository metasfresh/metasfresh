@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Several refund contracts of one partner
## Every matching term gets the full net amount of a sale, additively

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
      | Identifier        | Type_Conditions |
      | conditionsThree   | Refund          |
      | conditionsOne     | Refund          |
      | conditionsTwo     | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier   | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID |
      | configThree  | conditionsThree          | monthlySchedule      | 3             | goodsProduct |
      | configOne    | conditionsOne            | monthlySchedule      | 1             | goodsProduct |
      | configTwo    | conditionsTwo            | monthlySchedule      | 2             | goodsProduct |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termThree  | conditionsThree                     | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |
      | termOne    | conditionsOne                       | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |
      | termTwo    | conditionsTwo                       | customerBP                  | goodsProduct                | 2026-07-01 | 2026-12-31 |

  # ##############################################################################################
  # A sale is assigned to every matching refund term, each with the sale's full net amount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundMultiTerm_TC1
  Scenario: Three parallel refund terms each refund their percentage of the same sale
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

    # 3 %, 1 % and 2 % of the full 1000
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice |
      | refundIC3              | termThree          | 30              | 2026-07-31    |
      | refundIC1              | termOne            | 10              | 2026-07-31    |
      | refundIC2              | termTwo            | 20              | 2026-07-31    |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount | AssignedQuantity |
      | refundIC3                   | salesIC                         | termThree          | 30                  | 10               |
      | refundIC1                   | salesIC                         | termOne            | 10                  | 10               |
      | refundIC2                   | salesIC                         | termTwo            | 20                  | 10               |

    # the sale stays at its full amount
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | salesIC                           | 1000            |
