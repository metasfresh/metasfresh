@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Payment bonus on the payment, next to Skonto
## - A refund contract can be deducted at payment: the customer pays the invoice minus the bonus.
## - Skonto and the bonus are each computed on their own base.

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
      # a customer without a refund contract
      | plainBP        | Y              | N            | deductionPS                   | I               |
    # the organization's EUR account (like the service company fee feature); an account of its own would be a second active EUR account for the features that run after this one
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

    And metasfresh contains C_PaymentTerm
      | Identifier | NetDays | Discount | DiscountDays |
      | skonto2    | 30      | 2        | 10           |

    # the refund contract of the customer: 2.6 % of the goods, deducted at payment
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Type_Conditions |
      | conditionsWare | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | configWare | conditionsWare           | monthlySchedule      | 2.6           | goodsCategory         | bonusWare        | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |


  # ##############################################################################################
  # ##############################################################################################
  # Allocation view: Skonto and the payment bonus on the same invoice
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC0
  Scenario: Allocation view — Skonto and the payment bonus on the same invoice, each on its own base (reference for AC12d)
    # the sales invoice with Skonto: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %)
    # GrandTotal = 100.00 + 7.00 + 50.00 + 9.50 = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | skonto2          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # Skonto 2 % of 166.50 = 3.33; bonus 2.6 % of 100.00 goods = 2.60 + 7 % VAT = 2.78; the customer pays 166.50 - 3.33 - 2.78 = 160.39
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 160.39 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.78 EUR   | PB         | true   |
    And validate C_AllocationLines
      | C_Invoice_ID    | C_Payment_ID | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      | invoice         | -            | 2.78   | 0           | 0            | alloc_bonus        |
      | bonusCreditMemo | -            | -2.78  | 0           | 0            | alloc_bonus        |
      | invoice         | payment      | 160.39 | 3.33        | 0            | alloc_payment      |


  # ##############################################################################################
  # ##############################################################################################
  # Payment without invoice: a changed amount leaves write-off and over/under at 0
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_W1
  Scenario: Payment without invoice — the amount typed on the saved draft creates no write-off
    # the sales invoice without payment term: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the WebUI saves the new payment first; the user then types the amount: 166.50 - 2.78 bonus = 163.72
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 0.00 EUR | true      | org_EUR_account     |
    When the draft payment identified by payment is changed
      | PayAmt |
      | 163.72 |
    Then validate payments
      | C_Payment_ID | PayAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0           | 0            |

    And the payment identified by payment is completed
    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | bonusCreditMemo           |
    And validate payments
      | C_Payment_ID | WriteOffAmt | IsAllocated |
      | payment      | 0           | true        |


  # ##############################################################################################
  # ##############################################################################################
  # Payment without invoice: switching "over/under payment" leaves write-off and over/under at 0
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_W2
  Scenario: Payment without invoice — switching on over/under payment creates no write-off
    Given metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 50.00 EUR | true      | org_EUR_account     |
    When the draft payment identified by payment is changed
      | IsOverUnderPayment |
      | true               |
    Then validate payments
      | C_Payment_ID | PayAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 50     | 0           | 0            |


  # ##############################################################################################
  # ##############################################################################################
  # Allocation view: the bonus is booked only up to what the payments leave open on the invoices
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_V1
  Scenario: Allocation view — a customer who pays the full invoice gets no payment bonus
    # the sales invoice without payment term: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50; the computed bonus is 2.78
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the customer did not deduct the bonus
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 166.50 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | null                      |

    Then validate created invoices
      | C_Invoice_ID | GrandTotal | DocSubType | IsPaid |
      | invoice      | 166.50 EUR |            | true   |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt | WriteOffAmt |
      | payment      | true        | 0.00    | 0           |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_V2
  Scenario: Allocation view — a customer who pays less than the invoice, but by less than the bonus, gets the shortfall as payment bonus
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the customer deducted 1.50 of the 2.78 bonus: 166.50 - 1.50 = 165.00
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 165.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 1.50 EUR   | PB         | true   |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt | WriteOffAmt |
      | payment      | true        | 0.00    | 0           |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_V3
  Scenario: Allocation view — one payment for two invoices, each short by its bonus, keeps both bonuses
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
      | invoice2   | customerBP    | Ausgangsrechnung        | 2026-07-16   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
      | goodsLine2 | invoice2     | goodsProduct | 10 PCE      |
      | crateLine2 | invoice2     | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed
    And the invoice identified by invoice2 is completed

    # each invoice 166.50 minus its bonus 2.78 = 163.72; 2 x 163.72 = 327.44
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | 327.44 EUR | true      | org_EUR_account     |
    And the payment identified by payment is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment      | bonusCreditMemo1          |
      | invoice2     |              | bonusCreditMemo2          |

    Then validate created invoices
      | C_Invoice_ID     | GrandTotal | DocSubType | IsPaid |
      | invoice          | 166.50 EUR |            | true   |
      | invoice2         | 166.50 EUR |            | true   |
      | bonusCreditMemo1 | 2.78 EUR   | PB         | true   |
      | bonusCreditMemo2 | 2.78 EUR   | PB         | true   |
    And validate payments
      | C_Payment_ID | IsAllocated | OpenAmt | WriteOffAmt |
      | payment      | true        | 0.00    | 0           |


  # ##############################################################################################
  # ##############################################################################################
  # Payment window: the payment for an invoice pre-fills the payment bonus like Skonto; the write-off leaves it out
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1a
  Scenario: Payment for an invoice — the payment bonus is pre-filled and the amount the customer pays creates no write-off
    # the sales invoice without payment term: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the user picks the invoice on the draft payment: Skonto 0 (no payment term), bonus 2.6 % of 100.00 goods = 2.60 + 7 % VAT = 2.78; 166.50 - 2.78 = 163.72
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    When the payment identified by payment gets its amounts from its invoice
    Then validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0           | 2.78            | 0           | 0            |

    # the user confirms the pre-filled amount
    When the draft payment identified by payment is changed
      | PayAmt |
      | 163.72 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 2.78            | 0           | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1d
  Scenario: Payment for an invoice — a save that changes only the payment bonus (no UI callout) recomputes the write-off and keeps the PayAmt
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    # a process / REST save that only lowers the bonus: no callout, only the C_Payment interceptor
    When the draft payment identified by payment is saved without UI callouts
      | PaymentBonusAmt |
      | 0               |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0               | 2.78        | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1b
  Scenario: Payment for an invoice — a customer who pays the full invoice gets no payment bonus
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the user picks the invoice on the draft payment: Skonto 0 (no payment term), bonus 2.6 % of 100.00 goods = 2.60 + 7 % VAT = 2.78; 166.50 - 2.78 = 163.72
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    When the payment identified by payment gets its amounts from its invoice
    Then validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0           | 2.78            | 0           | 0            |

    # the customer did not deduct the bonus
    When the draft payment identified by payment is changed
      | PayAmt |
      | 166.50 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 166.50 | 0               | 0           | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1c
  Scenario: Payment for an invoice — a customer who pays less, but by less than the bonus, gets the shortfall as payment bonus
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the user picks the invoice on the draft payment: Skonto 0 (no payment term), bonus 2.6 % of 100.00 goods = 2.60 + 7 % VAT = 2.78; 166.50 - 2.78 = 163.72
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    When the payment identified by payment gets its amounts from its invoice
    Then validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0           | 2.78            | 0           | 0            |

    # the customer deducted 1.50 of the 2.78 bonus: 166.50 - 1.50 = 165.00
    When the draft payment identified by payment is changed
      | PayAmt |
      | 165.00 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 165.00 | 1.50            | 0           | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1e
  Scenario: Payment for an invoice — the user pays less, then sets the bonus to 0: the bonus stays 0 and the whole shortfall is a write-off
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    # the customer paid 160.00
    When the draft payment identified by payment is changed
      | PayAmt |
      | 160.00 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 160.00 | 2.78            | 3.72        |

    # the user does not want a payment bonus credit memo
    When the draft payment identified by payment is changed
      | PaymentBonusAmt |
      | 0               |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 160.00 | 0               | 6.50        | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1f
  Scenario: Payment for an invoice — the user sets the bonus to 0, then pays less: the bonus stays 0 and the whole shortfall is a write-off
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    When the draft payment identified by payment is changed
      | PaymentBonusAmt |
      | 0               |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 0               | 2.78        |

    When the draft payment identified by payment is changed
      | PayAmt |
      | 160.00 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 160.00 | 0               | 6.50        | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1g
  Scenario: Payment for an invoice — a bonus the full payment removed does not come back when the amount is lowered again
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    When the draft payment identified by payment is changed
      | PayAmt |
      | 166.50 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 166.50 | 0               | 0           |

    # only re-selecting the invoice pre-fills the bonus again; lowering the amount does not
    When the draft payment identified by payment is changed
      | PayAmt |
      | 163.72 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0               | 2.78        | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H2a
  Scenario: Payment for an invoice with Skonto — Skonto and the payment bonus are pre-filled side by side (AC12d)
    # the sales invoice with Skonto: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | skonto2          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # paid within the 10 Skonto days: Skonto 2 % of 166.50 = 3.33, bonus 2.78; 166.50 - 3.33 - 2.78 = 160.39
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID | DateTrx    |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     | 2026-07-20 |
    When the payment identified by payment gets its amounts from its invoice
    Then validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 160.39 | 3.33        | 2.78            | 0           | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H2b
  Scenario: Payment for an invoice with Skonto — a later payment date drops the Skonto and keeps the payment bonus
    # the sales invoice with Skonto: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | skonto2          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # paid within the 10 Skonto days: Skonto 2 % of 166.50 = 3.33, bonus 2.78; 166.50 - 3.33 - 2.78 = 160.39
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID | DateTrx    |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     | 2026-07-20 |
    When the payment identified by payment gets its amounts from its invoice
    Then validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 160.39 | 3.33        | 2.78            | 0           | 0            |

    # after the 10 Skonto days: they end on Sat 2026-07-25, which the payment term moves to the next business day, Mon 2026-07-27
    When the draft payment identified by payment is changed
      | DateTrx    |
      | 2026-07-28 |
    Then validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 163.72 | 0           | 2.78            | 0           | 0            |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H4a
  Scenario: Payment for an invoice of a customer without refund contract — no payment bonus, the shortfall is a write-off as before
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | plainBP       | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | plainBP       | invoice      | true      | org_EUR_account     |
    When the payment identified by payment gets its amounts from its invoice
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 166.50 | 0               | 0           |

    # the customer pays 6.50 less
    When the draft payment identified by payment is changed
      | PayAmt |
      | 160.00 |
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 160.00 | 0               | 6.50        | 0            |


  # ##############################################################################################
  # ##############################################################################################
  # Payment window: completing the payment books the payment bonus with a payment bonus credit memo, not with a write-off
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1
  Scenario: Payment for an invoice — completing it books the payment bonus with a credit memo, and the invoice is paid without write-off (AC12a)
    # the sales invoice without payment term: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # bonus 2.6 % of 100.00 goods = 2.60 + 7 % VAT = 2.78; 166.50 - 2.78 = 163.72
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And the draft payment identified by payment is changed
      | PayAmt |
      | 163.72 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    When the payment identified by payment is completed

    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 163.72 | 2.78            | 0           | bonusCreditMemo            | true        |
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.78 EUR   | PB         | true   |
    And validate C_AllocationLines
      | C_Invoice_ID    | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt | OverUnderAmt | C_AllocationHdr_ID |
      # the payment bonus credit memo is allocated against the invoice it references
      | invoice         | -            | 2.78   | 0           | 0           | 0            | alloc_bonus        |
      | bonusCreditMemo | -            | -2.78  | 0           | 0           | 0            | alloc_bonus        |
      # the payment settles the rest
      | invoice         | payment      | 163.72 | 0           | 0           | 0            | alloc_payment      |
    # the credit memo books the bonus on the bonus product's revenue account, with the 7 % VAT of the bonus product only
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID       | C_BPartner_ID | Record_ID       | M_Product_ID | C_Tax_ID |
      | P_Revenue_Acct        | 2.60 EUR    |             | bonusRevenueAcct | customerBP    | bonusCreditMemo | bonusWare    | lowTax   |
      | T_Due_Acct            | 0.18 EUR    |             |                  | customerBP    | bonusCreditMemo |              | lowTax   |
      | C_Receivable_Acct     |             | 2.78 EUR    |                  | customerBP    | bonusCreditMemo |              | -        |
      | *                     |             |             |                  |               | bonusCreditMemo |              |          |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H1h
  Scenario: Payment for an invoice — completing a full payment creates no payment bonus credit memo
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # the customer did not deduct the bonus
    And the draft payment identified by payment is changed
      | PayAmt |
      | 166.50 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 166.50 | 0               | 0           |

    When the payment identified by payment is completed

    Then there is no payment bonus credit memo for invoice invoice
    And validate payments
      | C_Payment_ID | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 0               | 0           | null                       | true        |
    And validate created invoices
      | C_Invoice_ID | GrandTotal | IsPaid |
      | invoice      | 166.50 EUR | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | WriteOffAmt |
      | invoice      | payment      | 166.50 | 0           |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H2
  Scenario: Payment for an invoice with Skonto — completing it books Skonto and the payment bonus credit memo side by side, like the allocation view (AC12d)
    # the sales invoice with Skonto: 10 x 10.00 goods (7 %) + 5 x 10.00 crates (19 %) = 166.50
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | C_PaymentTerm_ID |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 | skonto2          |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # paid within the 10 Skonto days: Skonto 2 % of 166.50 = 3.33, bonus 2.78; 166.50 - 3.33 - 2.78 = 160.39
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID | DateTrx    |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     | 2026-07-20 |
    And the payment identified by payment gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | DiscountAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 160.39 | 3.33        | 2.78            | 0           |

    When the payment identified by payment is completed

    Then validate payments
      | C_Payment_ID | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 2.78            | 0           | bonusCreditMemo            | true        |
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.78 EUR   | PB         | true   |
    And validate C_AllocationLines
      | C_Invoice_ID    | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt | OverUnderAmt | C_AllocationHdr_ID |
      | invoice         | -            | 2.78   | 0           | 0           | 0            | alloc_bonus        |
      | bonusCreditMemo | -            | -2.78  | 0           | 0           | 0            | alloc_bonus        |
      | invoice         | payment      | 160.39 | 3.33        | 0           | 0            | alloc_payment      |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H4
  Scenario: Payment for an invoice of a customer without refund contract — completing it books the shortfall as write-off as before (AC17)
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | plainBP       | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | plainBP       | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # the customer pays 6.50 less
    And the draft payment identified by payment is changed
      | PayAmt |
      | 160.00 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 160.00 | 0               | 6.50        |

    When the payment identified by payment is completed

    Then there is no payment bonus credit memo for invoice invoice
    And validate payments
      | C_Payment_ID | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | null                       | true        |
    And validate created invoices
      | C_Invoice_ID | GrandTotal | IsPaid |
      | invoice      | 166.50 EUR | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt |
      | invoice      | payment      | 160.00 | 0           | 6.50        |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H6a
  Scenario: Payment for an invoice — completing it books the payment bonus the user lowered
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # the customer deducted only 2.00 of the 2.78 bonus: 166.50 - 2.00 = 164.50
    And the draft payment identified by payment is changed
      | PayAmt | PaymentBonusAmt |
      | 164.50 | 2.00            |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 164.50 | 2.00            | 0           |

    When the payment identified by payment is completed

    # the credit memo books 2.00 gross: net 1.87 + 7 % VAT 0.13
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 164.50 | 2.00            | 0           | bonusCreditMemo            | true        |
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.00 EUR   | PB         | true   |
    And validate created invoice lines
      | C_Invoice_ID    | M_Product_ID | QtyInvoiced | LineNetAmt | C_Tax_ID |
      | bonusCreditMemo | bonusWare    | 1           | 1.87       | lowTax   |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt |
      | invoice      | payment      | 164.50 | 0           | 0           |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H6b
  Scenario: Payment for an invoice — completing it with the payment bonus set to 0 creates no credit memo; the shortfall is a write-off as before
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # the user does not want a payment bonus credit memo
    And the draft payment identified by payment is saved without UI callouts
      | PaymentBonusAmt |
      | 0               |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 0               | 2.78        |

    When the payment identified by payment is completed

    Then there is no payment bonus credit memo for invoice invoice
    And validate payments
      | C_Payment_ID | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 0               | 2.78        | null                       | true        |
    And validate created invoices
      | C_Invoice_ID | GrandTotal | IsPaid |
      | invoice      | 166.50 EUR | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt |
      | invoice      | payment      | 163.72 | 0           | 2.78        |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H6c
  Scenario: Payment for an invoice — a payment bonus the credit memo cannot reach is booked with the closest lower amount; header, credit memo and allocation agree
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # a process sets the bonus to 2.06; the rest of the shortfall, 2.78 - 2.06 = 0.72, is a write-off
    # with 7 % VAT, net 1.92 gives 2.05 and net 1.93 gives 2.07: no credit memo has a gross amount of 2.06
    And the draft payment identified by payment is saved without UI callouts
      | PaymentBonusAmt |
      | 2.06            |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.06            | 0.72        |

    When the payment identified by payment is completed

    # the credit memo books 2.05; the cent it cannot reach is added to the write-off
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 163.72 | 2.05            | 0.73        | bonusCreditMemo            | true        |
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.05 EUR   | PB         | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt |
      | invoice      | payment      | 163.72 | 0           | 0.73        |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H6d
  Scenario: Payment for an invoice — completing it books a payment bonus the user raised above the computed one, like the allocation view
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # the customer pays 163.50 and deducts 3.00 instead of the computed 2.78: 166.50 - 163.50 = 3.00
    And the draft payment identified by payment is changed
      | PayAmt |
      | 163.50 |
    And the draft payment identified by payment is saved without UI callouts
      | PaymentBonusAmt |
      | 3.00            |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.50 | 3.00            | 0           |

    When the payment identified by payment is completed

    # the credit memo books 3.00 gross: net 2.80 + 7 % VAT 0.20; nothing is written off
    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 163.50 | 3.00            | 0           | bonusCreditMemo            | true        |
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 3.00 EUR   | PB         | true   |
    And validate C_AllocationLines
      | C_Invoice_ID | C_Payment_ID | Amount | DiscountAmt | WriteOffAmt |
      | invoice      | payment      | 163.50 | 0           | 0           |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H6e
  Scenario: Payment for an invoice — a payment bonus the user raised above the computed one stays when the amount is changed afterwards
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    # the customer deducts 3.00 instead of the computed 2.78, then the amount is corrected to what was paid: 166.50 - 3.00 = 163.50
    And the draft payment identified by payment is saved without UI callouts
      | PaymentBonusAmt |
      | 3.00            |
    And the draft payment identified by payment is changed
      | PayAmt |
      | 163.50 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.50 | 3.00            | 0           |

    When the payment identified by payment is completed

    Then validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | 163.50 | 3.00            | 0           | bonusCreditMemo            | true        |
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 3.00 EUR   | PB         | true   |

  # ##############################################################################################
  # ##############################################################################################
  # Payment window and allocation view on the same invoice: the payment bonus is booked once per invoice
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H5
  Scenario: Partial payment for an invoice books the payment bonus; the allocation view then allocates the rest without a second payment bonus credit memo
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the customer pays 100.00 now and deducts the bonus; 166.50 - 100.00 - 2.78 = 63.72 stays open
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And the draft payment identified by payment is changed
      | PayAmt | IsOverUnderPayment |
      | 100.00 | true               |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 100.00 | 2.78            | 0           | 63.72        |
    And the payment identified by payment is completed
    And validate payments
      | C_Payment_ID | PaymentBonusAmt | PaymentBonus_CreditMemo_ID |
      | payment      | 2.78            | bonusCreditMemo            |

    # the customer pays the rest; the allocation view books no second bonus
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment2   | customerBP    | 63.72 EUR | true      | org_EUR_account     |
    And the payment identified by payment2 is completed
    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment2     | bonusCreditMemo           |

    Then validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.78 EUR   | PB         | true   |
    And validate payments
      | C_Payment_ID | IsAllocated | WriteOffAmt |
      | payment2     | true        | 0           |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H5b
  Scenario: Partial payment for an invoice books the payment bonus; a second payment for the invoice gets no payment bonus
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the customer pays 100.00 now and deducts the bonus; 166.50 - 100.00 - 2.78 = 63.72 stays open
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And the draft payment identified by payment is changed
      | PayAmt | IsOverUnderPayment |
      | 100.00 | true               |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 100.00 | 2.78            | 0           | 63.72        |
    And the payment identified by payment is completed
    And validate payments
      | C_Payment_ID | PaymentBonusAmt | PaymentBonus_CreditMemo_ID |
      | payment      | 2.78            | bonusCreditMemo            |

    # the payment of the rest: the bonus was already booked, so none is pre-filled
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment2   | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment2 gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment2     | 63.72  | 0               | 0           | 0            |
    When the payment identified by payment2 is completed

    Then validate payments
      | C_Payment_ID | PaymentBonusAmt | WriteOffAmt | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment2     | 0               | 0           | null                       | true        |
    And the only payment bonus credit memo for invoice invoice is bonusCreditMemo
    And validate created invoices
      | C_Invoice_ID    | GrandTotal | DocSubType | IsPaid |
      | invoice         | 166.50 EUR |            | true   |
      | bonusCreditMemo | 2.78 EUR   | PB         | true   |


  # ##############################################################################################
  # ##############################################################################################
  # Payment window: completing a payment refuses a payment bonus that can no longer be booked
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H7
  Scenario: Payment for an invoice — the payment bonus was booked by another payment in the meantime: completing the payment is refused
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the draft payment for the invoice, with the payment bonus pre-filled
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And the draft payment identified by payment is changed
      | PayAmt |
      | 163.72 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    # meanwhile another payment of the customer is allocated in the allocation view, which books the bonus
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment2   | customerBP    | 163.72 EUR | true      | org_EUR_account     |
    And the payment identified by payment2 is completed
    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | PaymentBonus.C_Invoice_ID |
      | invoice      | payment2     | bonusCreditMemo           |

    When the payment identified by payment is completed expecting error code PAYMENT_BONUS_NOT_DEDUCTIBLE

    Then the only payment bonus credit memo for invoice invoice is bonusCreditMemo
    And validate payments
      | C_Payment_ID | DocStatus | PaymentBonus_CreditMemo_ID |
      | payment      | DR        | null                       |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H8
  Scenario: Payment for an invoice — a payment bonus typed above the open amount: completing the payment is refused
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    # the customer pays 165.00 without a bonus; 1.50 stays open
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And the draft payment identified by payment is changed
      | PayAmt |
      | 165.00 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt |
      | payment      | 165.00 | 1.50            |
    And the draft payment identified by payment is changed
      | IsOverUnderPayment | PaymentBonusAmt |
      | true               | 0               |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt | OverUnderAmt |
      | payment      | 165.00 | 0               | 0           | 1.50         |
    And the payment identified by payment is completed
    And there is no payment bonus credit memo for invoice invoice

    # the payment of the rest: the bonus 2.78 is above the open 1.50, so none is pre-filled
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment2   | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment2 gets its amounts from its invoice
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt |
      | payment2     | 1.50   | 0               |
    # a process sets the bonus anyway
    And the draft payment identified by payment2 is saved without UI callouts
      | PaymentBonusAmt |
      | 2.78            |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt |
      | payment2     | 1.50   | 2.78            |

    When the payment identified by payment2 is completed expecting error code PAYMENT_BONUS_ABOVE_OPEN_AMT

    Then there is no payment bonus credit memo for invoice invoice
    And validate payments
      | C_Payment_ID | DocStatus | PaymentBonus_CreditMemo_ID |
      | payment2     | DR        | null                       |


  # ##############################################################################################
  # ##############################################################################################
  # Payment window: a failing payment bonus credit memo fails the whole payment completion
  # ##############################################################################################
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:paymentBonusOnPayment_TC_H9
  Scenario: Payment for an invoice — the payment bonus credit memo cannot be created: the payment stays a draft and nothing is booked
    # without an active payment bonus credit memo doc type the credit memo cannot be created; the doc type is activated again after the scenario
    Given load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID        |
      | ARC         | PB         | paymentBonusDocType |
    And update C_DocType:
      | C_DocType_ID        | IsActive |
      | paymentBonusDocType | false    |

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerBP    | Ausgangsrechnung        | 2026-07-15   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | goodsLine  | invoice      | goodsProduct | 10 PCE      |
      | crateLine  | invoice      | crateProduct | 5 PCE       |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | C_Invoice_ID | IsReceipt | C_BP_BankAccount_ID |
      | payment    | customerBP    | invoice      | true      | org_EUR_account     |
    And the payment identified by payment gets its amounts from its invoice
    And the draft payment identified by payment is changed
      | PayAmt |
      | 163.72 |
    And validate payments
      | C_Payment_ID | PayAmt | PaymentBonusAmt | WriteOffAmt |
      | payment      | 163.72 | 2.78            | 0           |

    When the payment identified by payment is completed expecting error code NO_PAYMENT_BONUS_CREDIT_MEMO_DOC_TYPE

    Then there is no payment bonus credit memo for invoice invoice
    And validate payments
      | C_Payment_ID | DocStatus | PaymentBonus_CreditMemo_ID | IsAllocated |
      | payment      | DR        | null                       | false       |
    And validate created invoices
      | C_Invoice_ID | GrandTotal | IsPaid |
      | invoice      | 166.50 EUR | false  |
