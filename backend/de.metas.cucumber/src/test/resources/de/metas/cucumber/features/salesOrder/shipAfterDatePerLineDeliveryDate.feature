@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00131
@ghActions:run_on_executor5
Feature: Ship-after-date holds each order line until its own delivery date

  The order header flag C_Order.IsFixedDatePromised ("ship after above date") must hold each
  order line back until that line's OWN delivery date, not the whole order until the header date.
  The two order lines of one order get different per-line delivery dates (one in the past, one in
  the future relative to "now"); only the line whose delivery date has been reached is shippable.

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

  Scenario: IsFixedDatePromised=Y holds the future-delivery-date line but ships the past-delivery-date line
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | IsFixedDatePromised |
      | order      | true    | bpartner      | 2022-08-16  | true                |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_past   | order      | product      | 1          |
      | orderLine_future | order      | product      | 1          |
    When the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID   | IsToRecompute | QtyToDeliver |
      | schedule_past   | orderLine_past   | N             | 1            |
      | schedule_future | orderLine_future | N             | 1            |
    # Give each line its own delivery date: the past one is shippable, the future one must be held back.
    And update shipment schedules
      | M_ShipmentSchedule_ID | DeliveryDate_Override |
      | schedule_past         | 2022-08-10            |
      | schedule_future       | 2022-08-20            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver |
      | schedule_past         | 1            |
      | schedule_future       | 1            |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_past         | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | schedule_past         | shipment_past |

    # The future-delivery-date line is held back: the per-line DeliveryDate filter excludes it from the
    # enqueuer selection, so no valid record remains to enqueue.
    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule and expects error message
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | AD_Message.Value                                                                    |
      | schedule_future       | D            | true                | false       | de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords |

  Scenario: IsFixedDatePromised=N ships both lines regardless of delivery date
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | IsFixedDatePromised |
      | order      | true    | bpartner      | 2022-08-16  | false               |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_past   | order      | product      | 1          |
      | orderLine_future | order      | product      | 1          |
    When the order identified by order is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID   | IsToRecompute | QtyToDeliver |
      | schedule_past   | orderLine_past   | N             | 1            |
      | schedule_future | orderLine_future | N             | 1            |
    And update shipment schedules
      | M_ShipmentSchedule_ID | DeliveryDate_Override |
      | schedule_past         | 2022-08-10            |
      | schedule_future       | 2022-08-20            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver |
      | schedule_past         | 1            |
      | schedule_future       | 1            |

    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_past         | D            | true                | false       |
      | schedule_future       | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | schedule_past         | shipment_past |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | schedule_future       | shipment_future |
