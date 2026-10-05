@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: The tax of a refund follows its bonus product
## The credit memo is taxed with the rate of the bonus product, whatever the tax of the sold goods

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
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
      | bonusCategory |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | lowGoods   | goodsCategory                        | false         |
      | highGoods  | goodsCategory                        | false         |
      | highBonus  | bonusCategory                        | false         |
      | lowBonus   | bonusCategory                        | false         |

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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_lowG    | refundPLV                         | lowGoods                | 100      | PCE               | lowTaxCateg      |
      | pp_highG   | refundPLV                         | highGoods               | 100      | PCE               | highTaxCateg     |
      | pp_highB   | refundPLV                         | highBonus               | 1        | PCE               | highTaxCateg     |
      | pp_lowB    | refundPLV                         | lowBonus                | 1        | PCE               | lowTaxCateg      |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP | Y              | refundPS                      | I               |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Type_Conditions |
      | condLow    | Refund          |
      | condHigh   | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_ID | Bonus_Product_ID |
      | cfgLow     | condLow                  | monthlySchedule      | 3             | lowGoods     | highBonus        |
      | cfgHigh    | condHigh                 | monthlySchedule      | 3             | highGoods    | lowBonus         |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    |
      | termLow    | condLow                             | customerBP                  | lowGoods                    | 2026-07-01 | 2026-12-31 |
      | termHigh   | condHigh                            | customerBP                  | highGoods                   | 2026-07-01 | 2026-12-31 |

  # ##############################################################################################
  # Goods with 7 % are refunded on a bonus product with 19 %, and the other way round
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundBonusTax_TC1
  Scenario: The credit memo carries the tax rate of the bonus product and not the one of the sold goods
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olLow      | order1                | lowGoods                | 10         |
      | olHigh     | order1                | highGoods               | 10         |
    And the order identified by order1 is completed
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | M_Product_ID | NetAmtToInvoice |
      | refundOfLow            | termLow            | highBonus    | 30              |
      | refundOfHigh           | termHigh           | lowBonus     | 30              |

    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundOfLow                       | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceOfLow            | refundOfLow                       |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundOfHigh                      | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceOfHigh           | refundOfHigh                      |

    # 30 net: 19 % of the bonus product = 5.70 (the sold goods have 7 %)
    Then Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID    | C_Tax_ID |
      | C_Receivable_Acct     |             | 35.70 EUR   | customerBP    | invoiceOfLow | -        |
      | P_Revenue_Acct        | 30 EUR      |             | customerBP    | invoiceOfLow | -        |
      | T_Due_Acct            | 5.70 EUR    |             | customerBP    | invoiceOfLow | highTax  |
    # 30 net: 7 % of the bonus product = 2.10 (the sold goods have 19 %)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID     | C_Tax_ID |
      | C_Receivable_Acct     |             | 32.10 EUR   | customerBP    | invoiceOfHigh | -        |
      | P_Revenue_Acct        | 30 EUR      |             | customerBP    | invoiceOfHigh | -        |
      | T_Due_Acct            | 2.10 EUR    |             | customerBP    | invoiceOfHigh | lowTax   |
