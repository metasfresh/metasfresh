@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund contracts on a product category base
## The refund is a percentage of the sales of a product category (incl. its sub-categories), booked on a bonus product

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
      | packCategory  |
      | pfandCategory |
      | emptyCategory |
      | bonusCategory |
    And metasfresh contains M_Product_Category:
      | Identifier       | Name                | Value               | OPT.M_Product_Category_Parent_ID.Identifier |
      | subGoodsCategory | refund sub-category | refundSubGoodsCateg | goodsCategory                               |

    # the bonus products post on their own revenue account
    # (applied before the products exist: a product copies its category's accounts when it is created)
    And metasfresh contains C_ElementValues:
      | Identifier        | Value |
      | bonusRevenueAcct  | 4750  |
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct |
      | bonusCategory         | bonusRevenueAcct   |

    # not stocked: no inventory needed
    And metasfresh contains M_Products:
      | Identifier   | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct | goodsCategory                        | false         |
      | subGoods     | subGoodsCategory                     | false         |
      | packProduct  | packCategory                         | false         |
      | pfandProduct | pfandCategory                        | false         |
      | bonusWare    | bonusCategory                        | false         |
      | bonusPack    | bonusCategory                        | false         |

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
      | pp_sub     | refundPLV                         | subGoods                | 100      | PCE               | Normal                        |
      | pp_pack    | refundPLV                         | packProduct             | 50       | PCE               | Normal                        |
      | pp_pfand   | refundPLV                         | pfandProduct            | 10       | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerBP | Y              | refundPS                      | I               |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

    # no product on the terms: the category of their configs is their base
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Type_Conditions |
      | conditionsWare  | Refund          |
      | conditionsGoods | Refund          |
      | conditionsPack  | Refund          |
      | conditionsEmpty | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | configWare  | conditionsWare           | monthlySchedule      | 3             | goodsCategory         | bonusWare        |
      | configGoods | conditionsGoods          | monthlySchedule      | 2             | goodsCategory         | bonusPack        |
      | configPack  | conditionsPack           | monthlySchedule      | 10            | packCategory          | bonusPack        |
      | configEmpty | conditionsEmpty          | monthlySchedule      | 5             | emptyCategory         | bonusPack        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termWare   | conditionsWare                      | customerBP                  | 2026-07-01 | 2026-12-31 |
      | termGoods  | conditionsGoods                     | customerBP                  | 2026-07-01 | 2026-12-31 |
      | termPack   | conditionsPack                      | customerBP                  | 2026-07-01 | 2026-12-31 |
      | termEmpty  | conditionsEmpty                     | customerBP                  | 2026-07-01 | 2026-12-31 |

  # ##############################################################################################
  # Every term refunds a percentage of the full net amount of its category, on its bonus product
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundCategoryBase_TC1
  Scenario: Each term refunds its category's sales on its bonus product and a term without sales gets no refund
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | customerBP               | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | olGoods    | order1                | goodsProduct            | 10         |
      | olSub      | order1                | subGoods                | 2          |
      | olPack     | order1                | packProduct             | 4          |
      | olPfand    | order1                | pfandProduct            | 5          |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olGoods        | icGoods                |
      | olSub          | icSub                  |
      | olPack         | icPack                 |
      | olPfand        | icPfand                |

    # goods base: 1000 + 200 of the sub-category; packaging base: 200; Pfand is in none of them
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | M_Product_ID | NetAmtToInvoice | DateToInvoice |
      | refundWare             | termWare           | bonusWare    | 36              | 2026-07-31    |
      | refundGoods            | termGoods          | bonusPack    | 24              | 2026-07-31    |
      | refundPack             | termPack           | bonusPack    | 20              | 2026-07-31    |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundWare                  | icGoods                         | termWare           | 30                  |
      | refundWare                  | icSub                           | termWare           | 6                   |
      | refundGoods                 | icGoods                         | termGoods          | 20                  |
      | refundGoods                 | icSub                           | termGoods          | 4                   |
      | refundPack                  | icPack                          | termPack           | 20                  |
    And the C_Flatrate_Term identified by termEmpty has no refund C_Invoice_Candidate

    # the refund is invoiced on the bonus product's revenue account, not on the sold product's
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | refundWare                        | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | refundInvoice           | refundWare                        |
    Then validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | DocStatus | DocBaseType | DocSubType |
      | refundInvoice | customerBP    | CO        | ARC         | RI         |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID       | C_BPartner_ID | Record_ID     | M_Product_ID |
      | P_Revenue_Acct        | 36 EUR      |             | bonusRevenueAcct | customerBP    | refundInvoice | bonusWare    |
      | *                     |             |             |                  |               | refundInvoice |              |
