@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund reference case: a partner with several parallel periodic refund terms
## A partner that buys and sells has four parallel terms on the goods base: 3 % and 0,125 % yearly, 1 % quarterly on the goods, and 0,3 % quarterly as packaging disposal fee.
## Every term refunds the full goods value (additive), on the sales and on the purchase side, each booked on the accounts of its own bonus product.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And documents are accounted immediately

    And metasfresh contains M_Product_Categories:
      | Identifier        |
      | goodsCategory     |
      | bonusWareCategory |
      | bonusPackCategory |
    # each bonus product books on its own accounts: revenue for the granted refund, expense for the received one
    # (applied before the products exist: a product copies its category's accounts when it is created)
    And metasfresh contains C_ElementValues:
      | Identifier       | Value |
      | wareRevenueAcct  | 4750  |
      | wareExpenseAcct  | 5750  |
      | packRevenueAcct  | 4760  |
      | packExpenseAcct  | 5760  |
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct | OPT.P_Expense_Acct |
      | bonusWareCategory     | wareRevenueAcct    | wareExpenseAcct    |
      | bonusPackCategory     | packRevenueAcct    | packExpenseAcct    |
    # not stocked: no inventory and no receipt needed
    And metasfresh contains M_Products:
      | Identifier   | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct | goodsCategory                        | false         |
      | bonusWare    | bonusWareCategory                    | false         |
      | bonusPack    | bonusPackCategory                    | false         |

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
      | pp_ware_s   | salesPLV                          | bonusWare               | 1        | PCE               | Normal                        |
      | pp_ware_p   | purchasePLV                       | bonusWare               | 1        | PCE               | Normal                        |
      | pp_pack_s   | salesPLV                          | bonusPack               | 1        | PCE               | Normal                        |
      | pp_pack_p   | purchasePLV                       | bonusPack               | 1        | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.PO_PricingSystem_ID.Identifier | OPT.InvoiceRule | OPT.PO_InvoiceRule |
      | partnerBP  | Y              | Y            | refundPS                      | refundPS                           | I               | I                  |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier        | InvoiceDay | InvoiceDistance |
      | quarterlySchedule | 31         | 3               |
      | yearlySchedule    | 31         | 12              |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Type_Conditions |
      | cond3Year  | Refund          |
      | condSmall  | Refund          |
      | condQuart  | Refund          |
      | condPack   | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfg3Year   | cond3Year                | yearlySchedule       | 3             | goodsCategory         | bonusWare        |
      | cfgSmall   | condSmall                | yearlySchedule       | 0.125         | goodsCategory         | bonusWare        |
      | cfgQuart   | condQuart                | quarterlySchedule    | 1             | goodsCategory         | bonusWare        |
      | cfgPack    | condPack                 | quarterlySchedule    | 0.3           | goodsCategory         | bonusPack        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | term3Year  | cond3Year                           | partnerBP                   | 2026-07-01 | 2027-06-30 |
      | termSmall  | condSmall                           | partnerBP                   | 2026-07-01 | 2027-06-30 |
      | termQuart  | condQuart                           | partnerBP                   | 2026-07-01 | 2027-06-30 |
      | termPack   | condPack                            | partnerBP                   | 2026-07-01 | 2027-06-30 |

  # ##############################################################################################
  # Four parallel periodic terms, on the sales and on the purchase side
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundReferenceCase_TC1
  Scenario: Four parallel terms refund the sales and the purchases, each on the accounts of its bonus product
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | salesOrder    | true    | partnerBP                | 2026-07-01  | I           |
      | purchaseOrder | false   | partnerBP                | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | salesLine    | salesOrder            | goodsProduct            | 40         |
      | purchaseLine | purchaseOrder         | goodsProduct            | 50         |
    And the order identified by salesOrder is completed
    And the order identified by purchaseOrder is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | salesLine      | salesIC                |
      | purchaseLine   | purchaseIC             |

    # sales 4000, purchase 3000: every term refunds the full value, none reduces another
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsSOTrx | NetAmtToInvoice | DateToInvoice | DocBaseType | M_Product_ID |
      | salesYear3             | term3Year          | Y       | 120             | 2026-12-31    | ARC         | bonusWare    |
      | salesYearSmall         | termSmall          | Y       | 5               | 2026-12-31    | ARC         | bonusWare    |
      | salesQuarter           | termQuart          | Y       | 40              | 2026-09-30    | ARC         | bonusWare    |
      | salesPack              | termPack           | Y       | 12              | 2026-09-30    | ARC         | bonusPack    |
      | purchaseYear3          | term3Year          | N       | 90              | 2026-12-31    | APC         | bonusWare    |
      | purchaseYearSmall      | termSmall          | N       | 3.75            | 2026-12-31    | APC         | bonusWare    |
      | purchaseQuarter        | termQuart          | N       | 30              | 2026-09-30    | APC         | bonusWare    |
      | purchasePack           | termPack           | N       | 9               | 2026-09-30    | APC         | bonusPack    |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | salesYear3                  | salesIC                         | term3Year          | 120                 |
      | salesYearSmall              | salesIC                         | termSmall          | 5                   |
      | salesQuarter                | salesIC                         | termQuart          | 40                  |
      | salesPack                   | salesIC                         | termPack           | 12                  |
      | purchaseYear3               | purchaseIC                      | term3Year          | 90                  |
      | purchaseYearSmall           | purchaseIC                      | termSmall          | 3.75                |
      | purchaseQuarter             | purchaseIC                      | termQuart          | 30                  |
      | purchasePack                | purchaseIC                      | termPack           | 9                   |

    # the quarterly refunds are invoiced as credit memos, on the accounts of their bonus product
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | salesQuarter                      | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | salesQuarterInvoice     | salesQuarter                      |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID      | C_BPartner_ID | Record_ID           | M_Product_ID |
      | P_Revenue_Acct        | 40 EUR      |             | wareRevenueAcct | partnerBP     | salesQuarterInvoice | bonusWare    |
      | *                     |             |             |                 |               | salesQuarterInvoice |              |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | salesPack                         | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | salesPackInvoice        | salesPack                         |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID      | C_BPartner_ID | Record_ID        | M_Product_ID |
      | P_Revenue_Acct        | 12 EUR      |             | packRevenueAcct | partnerBP     | salesPackInvoice | bonusPack    |
      | *                     |             |             |                 |               | salesPackInvoice |              |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | purchaseQuarter                   | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | purchaseQuarterInvoice  | purchaseQuarter                   |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID      | C_BPartner_ID | Record_ID              | M_Product_ID |
      | P_Expense_Acct        |             | 30 EUR      | wareExpenseAcct | partnerBP     | purchaseQuarterInvoice | bonusWare    |
      | *                     |             |             |                 |               | purchaseQuarterInvoice |              |


  # ##############################################################################################
  # The same partner also gets 3 % Bonus Ware on the invoice:
  # the periodic terms still refund the full goods value, not the value after the invoice bonus
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundReferenceCase_TC2
  Scenario: The periodic terms refund the full goods value of an invoice that also carries the on-invoice bonus
    # the on-invoice bonus product sits in the goods category, like the goods it is granted on
    Given metasfresh contains M_Products:
      | Identifier     | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | onInvoiceBonus | goodsCategory                        | false         |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_bonus_s | salesPLV                          | onInvoiceBonus          | 1        | PCE               | Normal                        |
    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier      | Name          | OPT.IsAdditive |
      | onInvoiceSchema | Bonus Ware 3% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | onInvoiceSchemaLine | onInvoiceSchema                          | onInvoiceBonus          | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier        | Name               | C_CompensationGroup_Schema_ID.Identifier |
      | onInvoiceSettings | On-invoice bonuses | onInvoiceSchema                          |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | onInvoiceSettings                                  | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier    | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | condOnInvoice | CompensationGroup | zeroDurTrans                            | onInvoiceSettings                                      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier    | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termOnInvoice | condOnInvoice                       | partnerBP                   | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by termOnInvoice is completed

    # invoice rule "after delivery": the order is shipped, then invoiced
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | salesOrder | true    | partnerBP                | 2026-07-01  | D           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | salesLine  | salesOrder            | goodsProduct            | 40         |
    And the order identified by salesOrder is completed

    # goods 40 x 100 = 4000; 3 % on the invoice = -120
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | bonusLine                 | salesOrder            | onInvoiceBonus          | 1          | true                        | -120  | termOnInvoice                     |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods   | salesLine                 | N             |
      | ss_bonus   | bonusLine                 | N             |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods              |
      | ss_bonus              |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods                         | shipment              |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | salesLine      | salesIC                |
      | bonusLine      | bonusIC                |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | salesIC                           |
      | bonusIC                           |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | salesInvoice            | salesIC                           |
    And validate invoice lines for salesInvoice:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | salesInvoice_goods          | goodsProduct            | 40          | 4000       |
      | salesInvoice_bonus          | onInvoiceBonus          | 1           | -120       |

    # every periodic term refunds the full goods value of 4000, additive to the on-invoice bonus
    # (on 4000 - 120 = 3880 they would be 116.40, 4.85, 38.80 and 11.64)
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsSOTrx | NetAmtToInvoice | DateToInvoice | DocBaseType | M_Product_ID |
      | salesYear3             | term3Year          | Y       | 120             | 2026-12-31    | ARC         | bonusWare    |
      | salesYearSmall         | termSmall          | Y       | 5               | 2026-12-31    | ARC         | bonusWare    |
      | salesQuarter           | termQuart          | Y       | 40              | 2026-09-30    | ARC         | bonusWare    |
      | salesPack              | termPack           | Y       | 12              | 2026-09-30    | ARC         | bonusPack    |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | salesYear3                  | salesIC                         | term3Year          | 120                 |
      | salesYearSmall              | salesIC                         | termSmall          | 5                   |
      | salesQuarter                | salesIC                         | termQuart          | 40                  |
      | salesPack                   | salesIC                         | termPack           | 12                  |
    # the on-invoice bonus line is in no refund base
    And the C_Invoice_Candidate identified by bonusIC has no C_Invoice_Candidate_Assignment
