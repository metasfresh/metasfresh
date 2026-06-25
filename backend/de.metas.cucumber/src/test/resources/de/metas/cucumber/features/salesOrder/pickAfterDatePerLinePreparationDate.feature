@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00131
@ghActions:run_on_executor5
Feature: Pick-after-date holds each order line until its own preparation date

  The order header flag C_Order.IsFixedPreparationDate ("pick after preparation date") must hold each
  order line back until that line's OWN preparation date, not the whole order until the header date.
  The base M_ShipmentSchedule.PreparationDate is derived per order line from the line's own delivery date
  (per-line C_OrderLine.DatePromised), using the same tour / fallback / offset logic as the order header.
  With no tour configured the preparation date equals the delivery date, so the two lines get different
  preparation dates; only the line whose preparation date has been reached is shippable.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2022-08-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_Products:
      | Identifier | IsStocked |
      | product    | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | DeliveryRule |
      | bpartner   | Y          | ps_1               | A            |

  Scenario: IsFixedPreparationDate=Y holds the future-preparation-date line but ships the past-preparation-date line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | IsFixedPreparationDate |
      | order      | true    | bpartner      | 2022-08-16  | true                   |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID | M_Product_ID | QtyEntered | DatePromised |
      | orderLine_past   | order      | product      | 1          | 2022-08-10   |
      | orderLine_future | order      | product      | 1          | 2022-08-20   |
    When the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID   | IsToRecompute | QtyToDeliver |
      | schedule_past   | orderLine_past   | N             | 1            |
      | schedule_future | orderLine_future | N             | 1            |
    # The per-line preparation date is derived from the per-line delivery date (no tour => prep == delivery date):
    # the past line is shippable, the future line must be held back.
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver | PreparationDate |
      | schedule_past         | 1            | 2022-08-10      |
      | schedule_future       | 1            | 2022-08-20      |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_past         | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | schedule_past         | shipment_past |

    # The future-preparation-date line is held back: the per-line PreparationDate filter excludes it from the
    # enqueuer selection, so no valid record remains to enqueue.
    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule and expects error message
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | AD_Message.Value                                                                    |
      | schedule_future       | D            | true                | false       | de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords |

  Scenario: Single-date order keeps the header preparation date on its shipment schedule (no regression)
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | IsFixedPreparationDate |
      | order      | true    | bpartner      | 2022-08-16  | 2022-08-16   | false                  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine  | order      | product      | 1          |
    When the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | schedule   | orderLine      | N             | 1            |
    # Single-date order: the per-line delivery date equals the header DatePromised, so the base
    # PreparationDate equals the header's DatePromised under the default no-tour + fallback + offset-0 config.
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver | PreparationDate |
      | schedule              | 1            | 2022-08-16      |

  Scenario: An explicit per-line C_OrderLine.PreparationDate overrides the derived preparation date
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | IsFixedPreparationDate |
      | order      | true    | bpartner      | 2022-08-16  | false                  |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID | M_Product_ID | QtyEntered | DatePromised | PreparationDate |
      | orderLine_override | order      | product      | 1          | 2022-08-20   | 2022-08-05      |
      | orderLine_derived  | order      | product      | 1          | 2022-08-10   |                 |
    When the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID     | IsToRecompute |
      | schedule_override | orderLine_override | N             |
      | schedule_derived  | orderLine_derived  | N             |
    # The override line's preparation date is its explicit C_OrderLine.PreparationDate (2022-08-05), NOT its
    # delivery date (2022-08-20). The derived line (no override) keeps prep == delivery date (no tour configured).
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate |
      | schedule_override     | 2022-08-05      |
      | schedule_derived      | 2022-08-10      |
