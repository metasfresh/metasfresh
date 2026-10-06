@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Bonus that the customer deducts when paying an invoice
## - A refund contract can be deducted at payment: the customer pays the invoice minus the bonus.
## - The bonus is a percentage of the net goods value, plus the VAT of the bonus product on top.
## - The payment allocation books it with a payment bonus credit memo on the bonus product, offset against the invoice.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains C_TaxCategory
      | Identifier   |
      | lowTaxCateg  |
      | highTaxCateg |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | lowTax     | lowTaxCateg      | 7    | DE                       | DE                        |
      | highTax    | highTaxCateg     | 19   | DE                       | DE                        |

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | packCategory  |
      | bonusCategory |
    And metasfresh contains M_Product_Category:
      | Identifier       | Name                 | Value                | OPT.M_Product_Category_Parent_ID.Identifier |
      | subGoodsCategory | deduction sub-goods  | deductionSubGoods    | goodsCategory                               |

    # the bonus products post on their own revenue account
    # (applied before the products exist: a product copies its category's accounts when it is created)
    And metasfresh contains C_ElementValues:
      | Identifier       | Value |
      | bonusRevenueAcct | 4750  |
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct |
      | bonusCategory         | bonusRevenueAcct   |

    And metasfresh contains M_Products:
      | Identifier     | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct   | subGoodsCategory                     | false         |
      | crateProduct   | packCategory                         | false         |
      | bonusWare      | bonusCategory                        | false         |
      | bonusPack      | bonusCategory                        | false         |
      | serviceProduct | bonusCategory                        | false         |

    And metasfresh contains M_PricingSystems
      | Identifier  |
      | deductionPS |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision |
      | deductionPL | deductionPS                   | DE                        | EUR                 | deductionPL | true  | false         | 2              |
      | servicePL   | deductionPS                   | DE                        | EUR                 | servicePL   | false | true          | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | deductionPLV | deductionPL               | deductionPLV | 2026-01-01 |
      | servicePLV   | servicePL                 | servicePLV   | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_goods   | deductionPLV                      | goodsProduct            | 10       | PCE               | lowTaxCateg      |
      | pp_crate   | deductionPLV                      | crateProduct            | 10       | PCE               | highTaxCateg     |
      | pp_bWare   | deductionPLV                      | bonusWare               | 1        | PCE               | lowTaxCateg      |
      | pp_bPack   | deductionPLV                      | bonusPack               | 1        | PCE               | highTaxCateg     |
      | pp_service | servicePLV                        | serviceProduct          | 0        | PCE               | highTaxCateg     |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP     | Y              | N            | deductionPS                   | I               |
      | serviceCompany | N              | Y            | deductionPS                   |                 |
    # an account of its own: the organization may have other EUR accounts already
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID | AccountNo          |
      | org_EUR_account | EUR           | paymentBonusTestEUR |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

    # the sales invoice: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %)
    # GrandTotal = 100.00 + 7.00 + 50.00 + 9.50 = 166.50
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed


  # ##############################################################################################
  # ##############################################################################################
  # One bonus on the goods: the customer pays the invoice minus the bonus
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentCustomerDeduction_TC1
  Scenario: The bonus on the net goods value is booked on the bonus product with its own VAT, and the invoice is paid
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |

    # bonus: 2.6 % of the 100.00 goods (not of the crates) = 2.60 + 7 % VAT 0.18 = 2.78; the customer pays 166.50 - 2.78 = 163.72
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 163.72 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID | GrandTotal | DocBaseType | DocSubType | IsPaid |
      | invoice         | customerBP    | 166.50 EUR | ARI         |            | true   |
      | bonusCreditMemo | customerBP    | 2.78 EUR   | ARC         | PB         | true   |
    And validate created invoice lines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced | LineNetAmt | C_Tax_ID |
      | bonusCreditMemo | bonusWare    | 1           | 2.60       | lowTax   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment      | true        |
    And validate C_AllocationLines
      | C_Invoice_ID    | C_Payment_ID | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      # the bonus: the payment bonus credit memo is allocated against the invoice it references
      | invoice         | -            | 2.78   | 0           | 0            | alloc_bonus        |
      | bonusCreditMemo | -            | -2.78  | 0           | 0            | alloc_bonus        |
      # the payment settles the rest
      | invoice         | payment      | 163.72 | 0           | 0            | alloc_payment      |
    # the credit memo books the bonus on the bonus product's revenue account, with the 7 % VAT of the bonus product only
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID       | C_BPartner_ID | Record_ID       | M_Product_ID | C_Tax_ID |
      | P_Revenue_Acct        | 2.60 EUR    |             | bonusRevenueAcct | customerBP    | bonusCreditMemo | bonusWare    | lowTax   |
      | T_Due_Acct            | 0.18 EUR    |             |                  | customerBP    | bonusCreditMemo |              | lowTax   |
      | C_Receivable_Acct     |             | 2.78 EUR    |                  | customerBP    | bonusCreditMemo |              | -        |
      | *                     |             |             |                  |               | bonusCreditMemo |              |          |


  # ##############################################################################################
  # ##############################################################################################
  # Two bonuses on the goods, on two bonus products with different VAT rates
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentCustomerDeduction_TC2
  Scenario: Two bonuses on the goods become two credit memo lines, each with the VAT of its bonus product
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
      | conditionsPack | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 5.2           | goodsCategory         | bonusWare        | Y                   |
      | configPack | conditionsPack           | monthlySchedule      | 0.8           | goodsCategory         | bonusPack        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |
      | termPack   | conditionsPack                      | customerBP                  | 2026-07-01 | 2026-12-31 |

    # 5.2 % of 100.00 = 5.20 + 7 % VAT 0.36 = 5.56; 0.8 % of 100.00 = 0.80 + 19 % VAT 0.15 = 0.95; together 6.51
    # the customer pays 166.50 - 6.51 = 159.99
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 159.99 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 6.51 EUR   | PB         | true   |
    And validate created invoice lines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced | LineNetAmt | C_Tax_ID |
      | bonusCreditMemo | bonusWare    | 1           | 5.20       | lowTax   |
      | bonusCreditMemo | bonusPack    | 1           | 0.80       | highTax  |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID       | Record_ID       | M_Product_ID | C_Tax_ID |
      | P_Revenue_Acct        | 5.20 EUR    |             | bonusRevenueAcct | bonusCreditMemo | bonusWare    | lowTax   |
      | P_Revenue_Acct        | 0.80 EUR    |             | bonusRevenueAcct | bonusCreditMemo | bonusPack    | highTax  |
      | T_Due_Acct            | 0.36 EUR    |             |                  | bonusCreditMemo |              | lowTax   |
      | T_Due_Acct            | 0.15 EUR    |             |                  | bonusCreditMemo |              | highTax  |
      | C_Receivable_Acct     |             | 6.51 EUR    |                  | bonusCreditMemo |              | -        |
      | *                     |             |             |                  | bonusCreditMemo |              |          |


  # ##############################################################################################
  # ##############################################################################################
  # Partial payments: the bonus is deducted once
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentCustomerDeduction_TC3
  Scenario: The second payment of a partially paid invoice does not deduct the bonus again
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |

    # first payment: 100.00 + the bonus 2.78; open 166.50 - 2.78 - 100.00 = 63.72
    And metasfresh contains C_Payment
      | Identifier    | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | firstPayment  | customerBP    | 100.00 EUR | true      | org_EUR_account     |
      | secondPayment | customerBP    | 63.72 EUR  | true      | org_EUR_account     |
    And the payment identified by firstPayment is completed
    And the payment identified by secondPayment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | firstPayment | bonusCreditMemo           |
    Then validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid | OpenAmt |
      | invoice         | 166.50 EUR |            | false  | 63.72   |
      | bonusCreditMemo | 2.78 EUR   | PB         | true   | 0       |

    # still the one credit memo of the first payment
    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID  | PaymentBonus.C_Invoice_ID |
      | invoice      | secondPayment | bonusCreditMemo           |
    Then validate created invoices
      | C_Invoice_ID | IsPaid | OpenAmt |
      | invoice      | true   | 0       |
    And validate payments
      | C_Payment_ID  | IsAllocated |
      | firstPayment  | true        |
      | secondPayment | true        |


  # ##############################################################################################
  # ##############################################################################################
  # The customer deducts another amount than computed
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentCustomerDeduction_TC5
  Scenario: The bonus that the customer actually deducted is booked, with the VAT on top
    Given metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |

    # computed 2.78, but the customer deducted 2.00 = 1.87 net + 0.13 VAT; it pays 166.50 - 2.00 = 164.50
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 164.50 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonusAmt | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | 2.00 EUR        | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.00 EUR   | PB         | true   |
    And validate created invoice lines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced | LineNetAmt | C_Tax_ID |
      | bonusCreditMemo | bonusWare    | 1           | 1.87       | lowTax   |


  # ##############################################################################################
  # ##############################################################################################
  # Bonus and service company fee on the same invoice
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentCustomerDeduction_TC4
  Scenario: A service company pays an invoice minus its fee and minus the customer's bonus
    Given load C_DocType:
      | C_DocType_ID.Identifier | Name                         |
      | serviceInvoiceDocType   | Rechnung für Servicegebühren |
    And metasfresh contains InvoiceProcessingServiceCompany
      | Identifier | ServiceCompany_BPartner_ID | ServiceFee_Product_ID | ServiceInvoice_DocType_ID |
      | config     | serviceCompany             | serviceProduct        | serviceInvoiceDocType     |
    And metasfresh contains InvoiceProcessingServiceCompany_BPartnerAssignment
      | InvoiceProcessingServiceCompany_ID | C_BPartner_ID | FeePercentageOfGrandTotal |
      | config                             | customerBP    | 2.6                       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |

    # fee: 2.6 % of the GrandTotal 166.50 = 4.33; bonus: 2.78; the service company pays 166.50 - 4.33 - 2.78 = 159.39
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID  | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | serviceCompany | 159.39 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | InvoiceProcessing.C_Invoice_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | serviceInvoice                 | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | C_BPartner_ID  | GrandTotal | DocBaseType | DocSubType | IsPaid |
      | invoice         | customerBP     | 166.50 EUR | ARI         |            | true   |
      | serviceInvoice  | serviceCompany | 4.33 EUR   | API         |            | true   |
      | bonusCreditMemo | customerBP     | 2.78 EUR   | ARC         | PB         | true   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment      | true        |


  # ##############################################################################################
  # ##############################################################################################
  # The refund engine leaves a contract that is deducted at payment alone
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentCustomerDeduction_TC6
  Scenario: A sale gets a refund candidate for a periodic refund contract, but none for a contract that is deducted at payment
    Given set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-15T09:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Type_Conditions |
      | conditionsPeriodic | Refund          |
      | conditionsDeducted | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier     | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configPeriodic | conditionsPeriodic       | monthlySchedule      | 3             | goodsCategory         | bonusPack        | N                   |
      | configDeducted | conditionsDeducted       | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termPeriodic | conditionsPeriodic                  | customerBP                  | 2026-07-01 | 2026-12-31 |
      | termDeducted | conditionsDeducted                  | customerBP                  | 2026-07-01 | 2026-12-31 |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-15  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 10         |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol1            | ic1                    |

    # 3 % of the 100.00 goods for the periodic contract; the customer deducts the other bonus when paying
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | Bill_BPartner_ID |
      | refundPeriodic         | termPeriodic       | 3               | customerBP       |
    And the C_Flatrate_Term identified by termDeducted has no refund C_Invoice_Candidate
    And metasfresh has current date and time
