@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor8
Feature: Invoicing from a shipment of an order with a contract bonus (customer case)
## F2070: Compensation Group Contract
##
## Customer case: a partial shipment of a contract order; the user opens the shipment's Related Documents ->
## "Rechnungsdisposition", ticks the whole list and runs "Create invoices". The steps list the candidates as the WebUI shows them.
## The contract fixture mirrors the customer: 3 % bonus, service-type bonus product (never shipped), goods 1000 / 500 / 200,
## invoice rule "after delivery".

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    # the bonus product is a service, so its tax category must not be restricted to items
    And taxCategory 'Normal' is updated to work with all productTypes

    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |
    And metasfresh contains M_Products:
      | Identifier   | M_Product_Category_ID | ProductType | IsStocked |
      | goods1       | goodsCategory         | I           | false     |
      | goods2       | goodsCategory         | I           | false     |
      | goods3       | goodsCategory         | I           | false     |
      | bonusProduct |                       | S           | false     |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | contractPL | contractPS         | DE                    | EUR                 | contractPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID | Name        | ValidFrom  |
      | contractPLV | contractPL     | contractPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods1  | contractPLV            | goods1       | 1000     | PCE               | Normal                        |
      | pp_goods2  | contractPLV            | goods2       | 500      | PCE               | Normal                        |
      | pp_goods3  | contractPLV            | goods3       | 200      | PCE               | Normal                        |
      | pp_bonus   | contractPLV            | bonusProduct | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | TermOfNotice | TermOfNoticeUnit | ExtensionType | EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0            | day              | EO            | 2026,2027             |

    # invoice rule "after delivery": only what was shipped becomes invoiceable
    And metasfresh contains C_BPartners:
      | Identifier   | IsCustomer | M_PricingSystem_ID | InvoiceRule |
      | headOfficeBP | Y          | contractPS         | D           |
      | storeBP      | Y          | contractPS         | D           |
      | plainBP      | Y          | contractPS         | D           |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name          | IsAdditive |
      | mainSchema | Bonus Ware 3% | true       |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID | M_Product_ID | CompleteOrderDiscount | M_Product_Category_ID |
      | mainSchemaLine | mainSchema                    | bonusProduct | 3                     | goodsCategory         |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier   | Name          | C_CompensationGroup_Schema_ID |
      | mainSettings | Main settings | mainSchema                    |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID | C_DocType_ID      |
      | mainSettings                            | docTypeSalesOrder |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name            | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | mainConditions | Main conditions | CompensationGroup | zeroDurTrans                            | mainSettings                                           |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | mainTerm   | mainConditions                      | headOfficeBP                | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by mainTerm is completed

  @from:cucumber
  @Id:S33240_TC1
  Scenario: Partial shipment of a contract order: ticking the whole related list invoices the delivered goods with their bonus
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 1          |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    Then the related invoice candidates of shipment "shipment1" are:
      | C_Invoice_Candidate_ID                    |
      | ic_goods1, ic_goods2, ic_goods3, ic_bonus |
    When run the invoicing process for the invoice candidates opened from the shipment "shipment1"
    Then the invoicing run succeeded
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice1     | ic_goods1              |
    # 3% of the delivered 1000; goods2 and goods3 are not invoiced
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID | M_Product_ID | QtyInvoiced | LineNetAmt |
      | invoice1_goods1  | goods1       | 1           | 1000       |
      | invoice1_bonus   | bonusProduct | 1           | -30        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyInvoiced |
      | ic_goods1              | 1           |
      | ic_goods2              | 0           |
      | ic_goods3              | 0           |

  @from:cucumber
  @Id:S33240_TC1r
  Scenario: Ticking only part of the group (goods1 and the bonus) is still refused
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | order      | true    | storeBP       | storeBP                | 2026-07-01  | headOfficeBP                    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_goods1  | order      | goods1       | 1          |
      | ol_goods2  | order      | goods2       | 1          |
      | ol_goods3  | order      | goods3       | 1          |
    And the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID | QtyOrdered | IsGroupCompensationLine |
      | ol_bonus       | order      | bonusProduct | 1          | true                    |
    And the order identified by order has 4 order lines
    # only the goods are shipped: the service bonus line has no shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_goods1  | ol_goods1      | N             |
      | ss_goods2  | ol_goods2      | N             |
      | ss_goods3  | ol_goods3      | N             |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods1      | ic_goods1              |
      | ol_goods2      | ic_goods2              |
      | ol_goods3      | ic_goods3              |
      | ol_bonus       | ic_bonus               |
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_goods1             | shipment1  |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_bonus               |
    When run the invoicing process for the invoice candidates "ic_goods1, ic_bonus"
    Then the invoicing run failed
    And the invoicing run summary contains:
      | Cannot invoice incomplete compensation groups |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyInvoiced |
      | ic_goods1              | 0           |
