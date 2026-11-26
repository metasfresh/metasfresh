@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund contracts that are completed after the sales were invoiced
## A back-dated refund contract picks up the sales of the current open period, even if they are already invoiced. Past full periods get no refund.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-15T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | bonusCategory |
    # not stocked: no inventory needed
    And metasfresh contains M_Products:
      | Identifier   | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goodsProduct | goodsCategory                        | false         |
      | bonusWare    | bonusCategory                        | false         |
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
      | pp_bonus   | refundPLV                         | bonusWare               | 1        | PCE               | Normal                        |

    # the store orders, the head office is invoiced
    And metasfresh contains C_BPartners without locations:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | headOffice | Y              | refundPS                      | I               |
      | store      | Y              | refundPS                      | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | C_Country_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | headLoc    | headOffice               | DE           | Y                   | Y                   |
      | storeLoc   | store                    | DE           | Y                   | Y                   |
    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundRetroactive_TC1
  Scenario: A term that starts in the past refunds the invoiced sales of the current period only
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | DateOrdered | InvoiceRule |
      | orderJune  | true    | store                    | storeLoc                              | headLoc                         | 2026-06-10  | I           |
      | orderJuly  | true    | store                    | storeLoc                              | headLoc                         | 2026-07-05  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | lineJune   | orderJune             | goodsProduct            | 10         |
      | lineJuly   | orderJuly             | goodsProduct            | 20         |
    And the order identified by orderJune is completed
    And the order identified by orderJuly is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | lineJune       | icJune                 |
      | lineJuly       | icJuly                 |
    # both are invoiced before there is a refund term
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | IgnoreInvoiceSchedule |
      | icJune                            | Y                     |
      | icJuly                            | Y                     |

    # the term is entered afterwards, with a start date in the past; the refund goes to the invoice partner
    When metasfresh contains C_Flatrate_Conditions:
      | Identifier | Type_Conditions |
      | condHead   | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgHead    | condHead                 | monthlySchedule      | 5             | goodsCategory         | bonusWare        |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus |
      | termHead   | condHead                            | headOffice                  | 2026-06-01 | 2026-12-31 | DR            |
    And the C_Flatrate_Term identified by termHead is completed

    # July is the current period: 5% of 2000. June is a past period: no refund candidate, so this finds exactly one.
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice | Bill_BPartner_ID |
      | refundJuly             | termHead           | 100             | 2026-07-31    | headOffice       |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundJuly                  | icJuly                          | termHead           | 100                 |
