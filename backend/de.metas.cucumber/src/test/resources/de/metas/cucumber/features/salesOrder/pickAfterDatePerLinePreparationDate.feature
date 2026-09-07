@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00131
@ghActions:run_on_executor5
Feature: Pick-after-date holds each order line until its own preparation date

  The order header flag C_Order.IsFixedPreparationDate ("pick after preparation date") must hold each
  order line back until that line's OWN preparation date, not the whole order until the header date.
  The base M_ShipmentSchedule.PreparationDate is derived per order line from the line's own delivery date
  (per-line C_OrderLine.DatePromised), using the same tour / fallback / offset logic as the order header.
  The derived preparation date is also written back to C_OrderLine.PreparationDate, so each line always
  reflects the value its shipment schedule receives initially. With no tour configured the preparation date
  equals the delivery date (offset 0); only the line whose preparation date has been reached is shippable.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2022-08-16T13:30:13+01:00[Europe/Berlin]
    # Isolation: start every scenario from the default preparation-date offset (one scenario below overrides it to -24h).
    And set sys config int value 0 for sys config de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours
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
    # Order line (upstream): each line keeps its OWN DatePromised; the per-line preparation date is derived from it
    # (no tour => prep == delivery date) and written back to C_OrderLine.PreparationDate.
    Then validate C_OrderLine:
      | C_OrderLine_ID   | DatePromised | PreparationDate |
      | orderLine_past   | 2022-08-10   | 2022-08-10      |
      | orderLine_future | 2022-08-20   | 2022-08-20      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID   | IsToRecompute | QtyToDeliver |
      | schedule_past   | orderLine_past   | N             | 1            |
      | schedule_future | orderLine_future | N             | 1            |
    # The per-line preparation date becomes the shipment schedule's PreparationDate:
    # the past line is shippable, the future line must be held back.
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver | PreparationDate | DeliveryDate |
      | schedule_past         | 1            | 2022-08-10      | 2022-08-10   |
      | schedule_future       | 1            | 2022-08-20      | 2022-08-20   |

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
    # Single-date order: the line's delivery date equals the header DatePromised, so the derived per-line
    # PreparationDate (and its C_OrderLine.PreparationDate) equals the header DatePromised (no tour, offset 0).
    Then validate C_OrderLine:
      | C_OrderLine_ID | DatePromised | PreparationDate |
      | orderLine      | 2022-08-16   | 2022-08-16      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | schedule   | orderLine      | N             | 1            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver | PreparationDate | DeliveryDate |
      | schedule              | 1            | 2022-08-16      | 2022-08-16   |

  Scenario: The preparation-date offset is applied when deriving the per-line preparation date
    # With a -24h fallback offset the derived preparation date is one day before the delivery date, proving the
    # line's PreparationDate is genuinely derived (delivery date - offset), not merely copied from DatePromised.
    Given set sys config int value -24 for sys config de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | IsFixedPreparationDate |
      | order      | true    | bpartner      | 2022-08-16  | false                  |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID | M_Product_ID | QtyEntered | DatePromised |
      | orderLine_early | order      | product      | 1          | 2022-08-10   |
      | orderLine_late  | order      | product      | 1          | 2022-08-20   |
    When the order identified by order is completed
    # prep = delivery date - 24h = DatePromised - 1 day, on both the order line and its shipment schedule.
    Then validate C_OrderLine:
      | C_OrderLine_ID  | DatePromised | PreparationDate |
      | orderLine_early | 2022-08-10   | 2022-08-09      |
      | orderLine_late  | 2022-08-20   | 2022-08-19      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | IsToRecompute |
      | schedule_early | orderLine_early | N             |
      | schedule_late  | orderLine_late  | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate | DeliveryDate |
      | schedule_early        | 2022-08-09      | 2022-08-10   |
      | schedule_late         | 2022-08-19      | 2022-08-20   |

  Scenario: Reset the preparation-date offset sysconfig
    # Isolation: restore the default offset so sibling features on this executor are not affected.
    Given set sys config int value 0 for sys config de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours
