@from:cucumber
@allure.label.epic:E0280_Document_and_Email_Management
@allure.label.feature:F00280
@ghActions:run_on_executor5
Feature: Delivery-note copies from the shipper (Lieferweg) of the ship-to address
## F00280: Doc Outbound
  The ship-to address references a shipper (Lieferweg); the shipper carries the number of delivery-note copies.
  - The sales order takes the shipper over from its ship-to address; the shipment takes it from the order.
  - A shipper copy count wins over a per-address C_BP_PrintFormat copy count.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-10-19T09:00:00+02:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value false for sys config InterceptorEnabled_de.metas.edi.model.validator.C_BPartner#validate
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains M_Products:
      | Identifier |
      | seafood    |
    # on-hand stock, so the shipment (DeliveryRule=Availability) can actually be generated
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyCount | QtyBook |
      | stock          | warehouse      | 2026-10-19   | seafood      | 20 PCE   | 0 PCE   |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           |
      | ps         | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | ps                 | DE                        | EUR                 | priceList | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID | Name          | ValidFrom  |
      | priceListVers | priceList      | priceListVers | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | productPrice | priceListVers          | seafood      | 10.0     | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier   | M_PricingSystem_ID | OPT.IsCustomer |
      | DulkHaasnoot | ps                 | Y              |


  # ##############################################################################################
  # ##############################################################################################
  # ##############################################################################################
  # ##############################################################################################

  @Id:S32661_TC1
  Scenario: Shipper "frei Bremerhaven" with 3 copies on the ship-to address, no per-address print format -> 3 copies are enqueued
    Given contains M_Shippers
      | Identifier       | DocumentCopies |
      | frei_Bremerhaven | 3              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | GLN           | IsShipTo | IsBillTo | M_Shipper_ID     |
      | FUZ_Bremerhaven | DulkHaasnoot  | 4008123466101 | true     | true     | frei_Bremerhaven |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | salesOrder | true    | DulkHaasnoot  | 2026-10-19  | FUZ_Bremerhaven        | warehouse      |
    And the order identified by salesOrder takes over the delivery defaults of its ship-to location
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | salesOrderLine | salesOrder | seafood      | 10         |
    And the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | M_Warehouse_ID |
      | schedule   | salesOrderLine | N             | warehouse      |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule              | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | schedule              | shipment   |
    And validate the created shipments
      | M_InOut_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Shipper_ID     |
      | shipment   | DulkHaasnoot  | FUZ_Bremerhaven        | frei_Bremerhaven |

    When the shipment identified by shipment is completed

    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | outboundLog                      | shipment             | M_InOut       | MMS             | CO            |
    And C_Printing_Queue item is located:
      | Identifier    | Record_ID |
      | shipmentQueue | shipment  |
    And C_Printing_Queue has Copies:
      | C_Printing_Queue_ID | Copies |
      | shipmentQueue       | 3      |
    And C_Printing_Queue contains 1 items for the record identified by "shipment"

