@from:cucumber
@allure.label.epic:E0159_Manufacturing_Planning
@allure.label.feature:F8005_Order_Checkup
@ghActions:run_on_executor7
Feature: Tour planning — the shipment schedule's PreparationDate follows the shipper lead time
  ## The order header's PreparationDate is derived backward from the lead time
  ## (= workday(DatePromised, -LeadTimeDays + 1)). The per-line shipment schedule — whose PreparationDate
  ## dates the material-demand event that drives MD_Candidate / the stock projection (Bestand pro Woche) —
  ## must carry the SAME provisioning date, not fall back to the delivery date (DatePromised). task 28993.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-08-03T08:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl         | ps                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax19      | taxCategory      | 19   | DE                       | DE                        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv                    | product      | 10.0     | PCE      | taxCategory      |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And contains M_Shippers
      | Identifier | LeadTimeDays |
      | shipper    | 7            |
    And contains M_Shippers
      | Identifier |
      | noLeadTime |

  @Id:S28993_TC13a
  Scenario: Lead time 7 — the schedule's PreparationDate is 6 working days before DatePromised, not the delivery date
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | DatePromised | M_PricingSystem_ID | M_Shipper_ID |
      | order      | true    | customer      | warehouse      | 2026-08-03  | 2026-08-19Z  | ps                 | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 10         |
    When the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule   | orderLine      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate |
      | schedule              | 2026-08-11      |

  @Id:S28993_TC13b
  Scenario: Shipper without lead time — the schedule keeps the existing no-tour fallback (PreparationDate = DatePromised)
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | DatePromised | M_PricingSystem_ID | M_Shipper_ID |
      | order      | true    | customer      | warehouse      | 2026-08-03  | 2026-08-19Z  | ps                 | noLeadTime   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 10         |
    When the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule   | orderLine      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate |
      | schedule              | 2026-08-19      |

  @Id:S28993_TC13c
  Scenario: Lead time 7 with IsFixedPreparationDate=Y — the schedule mirrors the pinned header PreparationDate
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | DatePromised | PreparationDate | IsFixedPreparationDate | M_PricingSystem_ID | M_Shipper_ID |
      | order      | true    | customer      | warehouse      | 2026-08-03  | 2026-08-19Z  | 2026-08-14Z     | Y                      | ps                 | shipper      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 10         |
    When the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule   | orderLine      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate |
      | schedule              | 2026-08-14      |

  @Id:S28993_TC13d
  Scenario: Lead-time route set on an order with a pinned promised date — the schedule follows the new route, not the line's stale value
    # Created with a no-lead-time route, so the line's PreparationDate is derived as the fallback (= DatePromised, no tour).
    # The route is then changed to a lead-time shipper while DatePromised is pinned (IsFixedDatePromised=Y): the header
    # does not propagate a new DatePromised and the line interceptor (which watches only line columns) never re-derives
    # the line, so the line keeps its stale 2026-08-19. The schedule must still follow the new route's lead time (2026-08-11).
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | M_Warehouse_ID | DateOrdered | DatePromised | IsFixedDatePromised | M_PricingSystem_ID | M_Shipper_ID |
      | order      | true    | customer      | warehouse      | 2026-08-03  | 2026-08-19Z  | Y                   | ps                 | noLeadTime   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | DatePromised |
      | orderLine  | order      | product      | 10         | 2026-08-19Z  |
    When update order
      | C_Order_ID | M_Shipper_ID |
      | order      | shipper      |
    And the order identified by order is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule   | orderLine      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate |
      | schedule              | 2026-08-11      |
