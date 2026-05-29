@from:cucumber
@allure.label.epic:E0355_Transport_Planning_Extralogistik
@allure.label.feature:F00355
@ghActions:run_on_executor7
Feature: nShift Shipment
## F00355: Shipper

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Shipper:
      | Identifier | Name   |
      | nShift     | nShift |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID |
      | nShift       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_CustomsTariff:
      | Identifier | Value    |
      | ct         | 12345678 |
    # Explicit Value+Name so reruns reuse the same product (upserts via productDAO.retrieveProductByValue).
    And metasfresh contains M_Products:
      | Identifier | Value          | Name           | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product    | nshift_product | nShift Product | 2 KGM     | 2.1 KGM     | ct                 |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | product      | 10.0     | PCE      |
    # Explicit Value+Name so reruns reuse the same BPartner (upserts via bpartnerDAO.retrieveBPartnerByValue) —
    # required for the composite FK c_order(c_bpartner_id, ad_user_id) to stay valid across reruns.
    And metasfresh contains C_BPartners without locations:
      | Identifier | Value           | Name            | Name2                  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | nshift_customer | nShift Customer | nShift Logistics Dept. | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 | OPT.Address2 |
      | customerLocation | customer      | CH           | Y               | Y               | 12345  | city | street 1 | Floor 2      |
    And load C_UOM:
      | C_UOM_ID.Identifier | X12DE355 |
      | cm                  | CM       |
    And metasfresh contains M_Products:
      | Identifier      | WeightNet | WeightGross |
      | packing_product | 0.1 KGM   | 0.1 KGM     |
    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID    | Length | Width | Height | C_UOM_Dimension_ID.Identifier |
      | pm         | packing_product | 30     | 20    | 10     | cm                            |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv            | 2022-12-12   | wh             |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID    | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_l_1    | inv            | product         | 0       | 100      | PCE          |
      | inv_l_2    | inv            | packing_product | 0       | 100      | PCE          |
    When the inventory identified by inv is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_1            | hu_1    |
      | inv_l_2            | hu_2    |
    And M_HU_Storage are validated
      | M_HU_ID | M_Product_ID    | Qty |
      | hu_1    | product         | 100 |
      | hu_2    | packing_product | 100 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU         |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU      | LU_Version         | 10  | HU       | TU                    | pm                                     |
      | huPiItemTU      | TU_Version         |     | MI       |                       |                                        |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_TU_10CU         | huPiItemTU      | product      | 10  | 2021-01-01 |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID |
      | cp1        | nShift       |
      | cp2        | nShift       |
      | cp3        | nShift       |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID |
      | cgt1       | nShift       |
      | cgt2       | nShift       |
    And metasfresh contains Carrier_Services:
      | Identifier | M_Shipper_ID |
      | cs1        | nShift       |
      | cs2        | nShift       |
      | cs3        | nShift       |
      | cs4        | nShift       |

  @from:cucumber
@allure.label.epic:E0355_Transport_Planning_Extralogistik
@allure.label.feature:F00355
  Scenario: nShift Carrier Advise
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | cp1                | cgt1                  | cs1                | cs2                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so1        | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so1_l1     | so1        | product      | 10         |
    When the order identified by so1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss1        | so1_l1         | N             | cp1                | cgt1                  |
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | nShift       | ss1                   | cp2                | cgt2                  | cs3                | cs4                 |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss1        | so1_l1         | N             | cp2                | cgt2                  |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss1                   |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss1        | so1_l1         | N             | cp2                | cgt2                  |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID | IsIncludeCarrierAdviseManual |
      | ss1                   | true                         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss1        | so1_l1         | N             | cp1                | cgt1                  |
    And update shipment schedules
      | Identifier | M_Shipper_ID |
      | ss1        | null         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss1        | so1_l1         | N             | null               | null                  |
    And M_ShipmentSchedule has no carrier services assigned
      | M_ShipmentSchedule_ID |
      | ss1                   |

  Scenario: nShift Carrier Automatic Schedule with RequireCarrierProductSet and CarrierProduct workplace criteria
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
    And deactivate all C_Workplace records
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | cp2                | cgt2                  | cs1                | cs2                 |
    And metasfresh contains C_Workplaces
      | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs | Carrier_Product_ID |
      | workplace1 | 10    | wh             | 1              | cp1, cp3           |
      | workplace2 | 20    | wh             | 1              | cp2                |
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | IsSOTrx | M_Warehouse_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered | M_Shipper_ID |
      | so2        | customer      | 2025-04-01  | true    | wh             | I           | so2_l1         | product      | 10         | nShift       |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss2        | so2_l1         | N             | cp2                | cgt2                  |
    And AD_Scheduler for classname 'de.metas.handlingunits.picking.process.M_ShipmentSchedule_Traffic_Management_assign' is ran once
    And after not more than 60s, picking job schedules are found:
      | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
      | ss2                   | workplace2     | 10        |

  @Id:S0355_DeliveryOrder_100
  Scenario: nShift Delivery Order Creation and Request Content Validation
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    # Test env has no AD_Printer_Config for the system user; auto-print would otherwise fail the WP.
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | cp1                | cgt1                  | cs1                | cs2                 |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # Stable Name + EMail so reruns reuse the same AD_User and don't repoint it to a different bpartner —
    # the composite FK c_order(c_bpartner_id, ad_user_id) would otherwise block the update.
    And metasfresh contains AD_Users:
      | Identifier      | Name                    | OPT.C_BPartner_ID.Identifier | OPT.EMail                    | OPT.Phone        |
      | customerContact | nShift Customer Contact | customer                     | contact@nshift-test.example  | +41 79 123 45 67 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | OPT.AD_User_ID.Identifier |
      | so_do      | true    | customer      | 2025-04-01  | wh             | nShift       | customerContact           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_do_l1   | so_do      | product      | 10         |
    When the order identified by so_do is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_do      | so_do_l1       | N             | cp1                | cgt1                  |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_do                 | inout_do   |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_do   | transpOrder_do             |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_do     | inout_do   |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb | TrackingURL | HasPdfLabel |
      | cso_do                   | awb | trackingUrl | true        |
    And validate Carrier_ShipmentOrder:
      | Carrier_ShipmentOrder_ID | Shipper_Name1 | Shipper_CountryISO2Code | Receiver_Name1  | Receiver_Name2         | Receiver_StreetName1 | Receiver_StreetName2 | Receiver_StreetNumber | Receiver_ZipCode | Receiver_City | Receiver_CountryISO2Code | Receiver_ContactName    | Receiver_Phone   | Receiver_Email              |
      | cso_do                   | metasfresh AG | DE                      | nShift Customer | nShift Logistics Dept. | street               | Floor 2              | 1                     | 12345            | city          | CH                       | nShift Customer Contact | +41 79 123 45 67 | contact@nshift-test.example |
    # 10 PCE / 10 PCE-per-TU => 1 parcel; total weight = product.GrossWeight (2.1) × qty (10) = 21 kg.
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName    | ArticleValue   | CustomsTariffNumber | QtyShipped | Price | TotalPrice | TotalWeightInKg |
      | cso_do                   | nShift Product | nshift_product | 12345678            | 10         | 10    | 100        | 21              |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 | NumParcels | SenderCompanyName | SenderCountryCode | ReceiverCompanyName | ReceiverCompanyName2   | ReceiverStreet | ReceiverAdditionalAddressInfo | ReceiverHouseNo | ReceiverZip | ReceiverCity | ReceiverCountryCode | ReceiverContactName     | ReceiverContactPhone | ReceiverContactEmail        | ParcelGrossWeightKg |
      | cp1                | cgt1                  | cs1                | cs2                 | 1          | metasfresh AG     | DE                | nShift Customer     | nShift Logistics Dept. | street         | Floor 2                       | 1               | 12345       | city         | CH                  | nShift Customer Contact | +41 79 123 45 67     | contact@nshift-test.example | 21                  |

  Scenario: reset settings to default
    Given set sys config boolean value false for sys config de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
    And set sys config boolean value false for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value true for sys config de.metas.shipper.gateway.printLabels.enabled