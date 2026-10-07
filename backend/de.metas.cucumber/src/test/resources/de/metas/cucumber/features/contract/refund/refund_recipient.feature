@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F00970_Flatrate_Contract
@ghActions:run_on_executor3
Feature: Refund contracts go to the invoice partner
## The refund always goes to the partner that is invoiced, never to the partner that orders or receives the goods

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
      | bonusCategory |
    # not stocked: no inventory needed
    And metasfresh contains M_Products:
      | Identifier   | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked | REST.Context.Value |
      | goodsProduct | goodsCategory                        | false         | goodsValue         |
      | bonusWare    | bonusCategory                        | false         |                    |
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

    # the head office is invoiced for what the store orders
    And metasfresh contains C_BPartners without locations:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule | REST.Context.Value |
      | headOffice | Y              | refundPS                      | I               | headOfficeValue    |
      | store      | Y              | refundPS                      | I               | storeValue         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | C_Country_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | headLoc    | headOffice               | DE           | Y                   | Y                   |
      | storeLoc   | store                    | DE           | Y                   | Y                   |

    And metasfresh contains C_InvoiceSchedules:
      | Identifier      | InvoiceDay | InvoiceDistance |
      | monthlySchedule | 31         | 1               |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Type_Conditions |
      | condHead   | Refund          |
      | condStore  | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgHead    | condHead                 | monthlySchedule      | 3             | goodsCategory         | bonusWare        |
      | cfgStore   | condStore                | monthlySchedule      | 9             | goodsCategory         | bonusWare        |

  # ##############################################################################################
  # A regular order: the store orders, the head office is invoiced
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundRecipient_TC1
  Scenario: The refund goes to the invoice partner, not to the partner that orders and receives the goods
    Given metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termHead   | condHead                            | headOffice                  | 2026-07-01 | 2026-12-31 |
      | termStore  | condStore                           | store                       | 2026-07-01 | 2026-12-31 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | DateOrdered | InvoiceRule |
      | order1     | true    | store                    | storeLoc                              | headLoc                         | 2026-07-01  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol1        | order1                | goodsProduct            | 10         |
    And the order identified by order1 is completed
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol1            | ic1                    |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier |
      | ic1                               | headOffice                      |

    # the head office is invoiced -> its term
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | Bill_BPartner_ID |
      | refundHead             | termHead           | 30              | headOffice       |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundHead                  | ic1                             | termHead           | 30                  |
    # the store ordered and receives the goods, but is not invoiced: no refund for its term
    And the C_Flatrate_Term identified by termStore has no refund C_Invoice_Candidate

  # ##############################################################################################
  # An invoice candidate without an order
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F00970_Flatrate_Contract
  @Id:refundRecipient_TC3
  Scenario: An invoice candidate without an order gets the refund of its invoice partner
    Given metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    |
      | termHead   | condHead                            | headOffice                  | 2026-07-01 | 2026-12-31 |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "@goodsValue@_H",
            "externalLineId": "@goodsValue@_L1",
            "billPartnerIdentifier": "val-@headOfficeValue@",
            "productIdentifier": "val-@goodsValue@",
            "dateOrdered": "2026-07-01",
            "qtyOrdered": 10,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort"
          }
        ]
      }
      """
    # (the header id derives from the product's random value, so that a rerun on a persistent DB does not hit the candidate of an earlier run)
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | Bill_BPartner_ID |
      | refundHead             | termHead           | 30              | headOffice       |
