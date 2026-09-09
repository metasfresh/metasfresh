@from:cucumber
@allure.label.epic:E0280_Document_and_Email_Management
@allure.label.feature:F00280
@ghActions:run_on_executor5
Feature: Shipment auto-print suppression for drop-ship deliveries
## F00280: Doc Outbound
  A per-partner "no auto-print for drop-ship" preference (C_BP_PrintFormat.IsDropShip + IsAutoPrint)
  must not suppress archiving or the outgoing-document log -- only the automatic print-queue enqueue.
  A manual reprint of the outgoing document still enqueues it, drop-ship or not.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-01-12T09:00:00+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value false for sys config InterceptorEnabled_de.metas.edi.model.validator.C_BPartner#validate
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains M_Products:
      | Identifier | Name    |
      | product    | product |
    # on-hand stock, so the shipment (DeliveryRule=Availability) can actually be generated
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyCount | QtyBook |
      | stock          | warehouse      | 2026-01-12   | product      | 10 PCE   | 0 PCE   |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           |
      | ps         | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | ps                 | DE                        | EUR                 | priceList  | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID | Name          | ValidFrom  |
      | priceListVers | priceList      | priceListVers | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | productPrice | priceListVers          | product      | 10.0     | Normal                        | PCE                |
    And metasfresh contains C_BPartners:
      | Identifier | Name     | M_PricingSystem_ID | OPT.IsCustomer |
      | customer   | customer | ps                  | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | GLN           | OPT.IsShipTo | OPT.IsBillTo |
      | customerLocation | customer      | 4008123456787 | true         | true         |
      | dropShipLocation | customer      | 4008123456788 | true         | false        |

  @Id:S0280_010
  Scenario: Drop-ship shipment completion suppresses auto-print but still archives and logs; manual reprint still enqueues
    Given metasfresh contains C_BP_PrintFormat:
      | C_BPartner_ID | IsDropShip | IsAutoPrint |
      | customer      | true       | false       |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID |
      | dropShipOrder | true    | customer      | 2026-01-12  | customerLocation        | warehouse      | true       | customer              | dropShipLocation      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID    | M_Product_ID | QtyEntered |
      | dropShipOrderLine | dropShipOrder | product      | 10         |
    And the order identified by dropShipOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID    | IsToRecompute | M_Warehouse_ID |
      | dropShipSchedule | dropShipOrderLine | N             | warehouse      |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | dropShipSchedule      | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID       |
      | dropShipSchedule      | dropShipShipment |
    And the shipment identified by dropShipShipment is completed

    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | dropShipOutboundLog              | dropShipShipment      | M_InOut       | MMS             | CO            |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name |
      | dropShipOutboundLogLine               | dropShipOutboundLog              | dropShipShipment      | M_InOut       |
    Then an AD_Archive exists for the record identified by "dropShipShipment"
    And C_Printing_Queue contains 0 items for the record identified by "dropShipShipment"

    When the doc outbound log identified by "dropShipOutboundLog" is reprinted
    Then C_Printing_Queue contains 1 items for the record identified by "dropShipShipment"

  @Id:S0280_020
  Scenario: Stock (non-drop-ship) shipment completion still auto-prints
    Given metasfresh contains C_BP_PrintFormat:
      | C_BPartner_ID | IsDropShip | IsAutoPrint |
      | customer      | true       | false       |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_BPartner_Location_ID | M_Warehouse_ID |
      | stockOrder | true    | customer      | 2026-01-12  | customerLocation       | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID | QtyEntered |
      | stockOrderLine | stockOrder | product      | 10         |
    And the order identified by stockOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute | M_Warehouse_ID |
      | stockSchedule | stockOrderLine | N             | warehouse      |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | stockSchedule         | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | stockSchedule         | stockShipment |
    And the shipment identified by stockShipment is completed

    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | stockOutboundLog                 | stockShipment         | M_InOut       | MMS             | CO            |
    Then an AD_Archive exists for the record identified by "stockShipment"
    And C_Printing_Queue contains 1 items for the record identified by "stockShipment"
