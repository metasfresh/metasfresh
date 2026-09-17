@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29099_nShift_Interface
@ghActions:run_on_executor4
Feature: nShift Shipment
## F29099: nShift Interface

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
      | Identifier | Value           | Name            | WeightNet | WeightGross | M_CustomsTariff_ID | LengthInCm | WidthInCm | HeightInCm |
      | product    | nshift_product  | nShift Product  | 2 KGM     | 2.1 KGM     | ct                 | 30         | 20        | 10         |
      | product_2  | nshift_product2 | nShift Product2 | 1 KGM     | 1.1 KGM     | ct                 | 30         | 20        | 10         |
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
      | plv                    | product_2    | 8.0      | PCE      |
    # Explicit Value+Name so reruns reuse the same BPartner (upserts via bpartnerDAO.retrieveBPartnerByValue) —
    # required for the composite FK c_order(c_bpartner_id, ad_user_id) to stay valid across reruns.
    And metasfresh contains C_BPartners without locations:
      | Identifier | Value           | Name            | Name2                  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | nshift_customer | nShift Customer | nShift Logistics Dept. | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 | Address2 | Attention      | IsPreAdviceRequired |
      | customerLocation | customer      | CH           | Y               | Y               | 12345  | city | street 1 | Floor 2  | Attention Test | Y                       |
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
      | inv_l_3    | inv            | product_2       | 0       | 100      | PCE          |
    When the inventory identified by inv is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID      |
      | inv_l_1            | hu_1         |
      | inv_l_2            | hu_2         |
      | inv_l_3            | hu_product_2 |
    And M_HU_Storage are validated
      | M_HU_ID      | M_Product_ID    | Qty |
      | hu_1         | product         | 100 |
      | hu_2         | packing_product | 100 |
      | hu_product_2 | product_2       | 100 |
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
      | product_2_TU_10CU       | huPiItemTU      | product_2    | 10  | 2021-01-01 |
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

  Scenario: nShift Carrier Advise
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
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
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss1                   | cp2                | cgt2                  | cs3, cs4           |
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
    # Only automatic carrier advise (M_ShipmentSchedule_Advise with IsIncludeCarrierAdviseManual=true)
    # creates allocation records. Manual advise (M_ShipmentSchedule_Advise_Manual) does not — so
    # cp1 allocations are expected here (from the auto-advise response), but not cp2 ones.
    Then Carrier_Product_GoodsType_Allocs are found:
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp1                | cgt1                  |
    And Carrier_Product_Service_Allocs are found:
      | Carrier_Product_ID | Carrier_Service_ID |
      | cp1                | cs1                |
      | cp1                | cs2                |
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
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp2                | cgt2                  | cs1, cs2           |
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
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # Stable Name + EMail so reruns reuse the same AD_User and don't repoint it to a different bpartner —
    # the composite FK c_order(c_bpartner_id, ad_user_id) would otherwise block the update.
    And metasfresh contains AD_Users:
      | Identifier      | Name                    | C_BPartner_ID | EMail                       | Phone            |
      | customerContact | nShift Customer Contact | customer      | contact@nshift-test.example | +41 79 123 45 67 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID      | IsPreAdviceRequired |
      | so_do      | true    | customer      | 2025-04-01  | wh             | nShift       | customerContact | Y                   |
    # Order line carries the 10-CU-per-TU packing (product_TU_10CU) so the on-the-fly pick packs the 10 CUs
    # into ONE real TU (not a loose VHU) — a TU ships as 1 parcel (loose CUs would split 1-label-per-CU).
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_do_l1   | so_do      | product      | 10         | product_TU_10CU         |
    When the order identified by so_do is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_do      | so_do_l1       | N             | cp1                | cgt1                  |
    # IsOnTheFlyPickToPackingInstructions=true: pack the on-the-fly-picked CUs into a TU per the order line's
    # packing (mirrors the real shipper-transportation flow) → 1 TU → 1 parcel, not 10 loose-CU parcels.
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID | IsOnTheFlyPickToPackingInstructions |
      | ss_do                 | inout_do   | true                                |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_do   | transpOrder_do             |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_do     | inout_do   |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_do                   | awb1 | trackingUrl1 | true        |
    And validate Carrier_ShipmentOrder:
      | Carrier_ShipmentOrder_ID | Shipper_Name1 | Shipper_CountryISO2Code | Receiver_Name1  | Receiver_Name2         | Receiver_StreetName1 | Receiver_StreetName2 | Receiver_StreetNumber | Receiver_ZipCode | Receiver_City | Receiver_CountryISO2Code | Receiver_ContactName    | Receiver_Phone | Receiver_Email              | IsPreAdviceRequired |
      | cso_do                   | metasfresh AG | DE                      | nShift Customer | nShift Logistics Dept. | street               | Floor 2              | 1                     | 12345            | city          | CH                       | nShift Customer Contact | +41791234567   | contact@nshift-test.example | Y                   |
    # 10 PCE / 10 PCE-per-TU => 1 parcel; total weight = product.GrossWeight (2.1) × qty (10) = 21 kg.
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName    | ArticleValue   | CustomsTariffNumber | QtyShipped | Price | TotalPrice | TotalWeightInKg |
      | cso_do                   | nShift Product | nshift_product | 12345678            | 10         | 10    | 100        | 21              |
    # advisor request carries the per-unit baseline (qty 1): totalValue=1×unitPrice=10, weight per-unit=3 —
    # unlike the shipment request below, which carries the full ordered qty 10 (totalValue 100, weight 21).
    And validate the captured nShift advisor request:
      | SenderCompanyName | SenderCountryCode | ReceiverCompanyName | ReceiverCompanyName2   | ReceiverStreet | ReceiverAdditionalAddressInfo | ReceiverHouseNo | ReceiverZip | ReceiverCity | ReceiverCountryCode | ReceiverAttention | ReceiverContactName     | ReceiverContactPhone | ReceiverContactEmail        | IsPreAdviceRequired | unitPrice | totalValue | shippedQuantity | customsTariff | totalWeightInKg |
      | metasfresh AG     | DE                | nShift Customer     | nShift Logistics Dept. | street         | Floor 2                       | 1               | 12345       | city         | CH                  | Attention Test    | nShift Customer Contact | +41791234567         | contact@nshift-test.example | Y                   | 10        | 10         | 1               | 12345678      | 3               |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | NumParcels | SenderCompanyName | SenderCountryCode | ReceiverCompanyName | ReceiverCompanyName2   | ReceiverStreet | ReceiverAdditionalAddressInfo | ReceiverHouseNo | ReceiverZip | ReceiverCity | ReceiverCountryCode | ReceiverAttention | ReceiverContactName     | ReceiverContactPhone | ReceiverContactEmail        | IsPreAdviceRequired |
      | cp1                | cgt1                  | cs1, cs2           | 1          | metasfresh AG     | DE                | nShift Customer     | nShift Logistics Dept. | street         | Floor 2                       | 1               | 12345       | city         | CH                  | Attention Test    | nShift Customer Contact | +41791234567         | contact@nshift-test.example | Y                   |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 21            |
    And validate the captured nShift shipment request contents:
      | productName    | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product | 10              | 10        | 100        | 21              | 12345678      |

  @Id:S0355_DeliveryOrder_160
  Scenario: nShift Carrier Advise — advisor request item always carries numberOfItems=1 regardless of order quantity
    # The advisor uses a per-unit baseline (1 item) regardless of the total ordered quantity.
    # This ensures the carrier advisor sees a single-unit weight/dimension, not the full shipment size.
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ac1     | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_ac1_l1  | so_ac1     | product      | 30         |
    When the order identified by so_ac1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ac1     | so_ac1_l1      | N             |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID | IsIncludeCarrierAdviseManual |
      | ss_ac1                | true                         |
    # the advise runs asynchronously — wait for the carrier product to be set so the advisor request has been captured before validating
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | Carrier_Product_ID |
      | ss_ac1     | so_ac1_l1      | cp1                |
    # numberOfItems=1 and per-unit weight/dimensions prove the qty-1 baseline (not scaled by the order qty of 30):
    # grossWeightKg: product.WeightGross=2.1 KGM rounded UP (RoundingMode.UP) to 0 decimals = 3
    # length/width/height: the product's per-unit dimensions (30/20/10 cm), unscaled
    Then validate the captured nShift advisor request:
      | numberOfItems | grossWeightKg | lengthInCM | widthInCM | heightInCM |
      | 1             | 3             | 30         | 20        | 10         |

  @Id:S0355_DeliveryOrder_170
  Scenario: nShift Delivery Order — IsSelectionRules=true when shipper has IsSelectionRules and schedule is non-Manual
    # When a schedule goes through the automatic advise flow (status Completed, not Manual),
    # the gateway patches the shipment request with UseShippingRules=true plus the effective ServiceLevel.
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And metasfresh contains M_Shipper_ServiceLevel_Configs:
      | M_Shipper_ID | SeqNo | ServiceLevel |
      | nShift       | 10    | STANDARD     |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ac6     | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_ac6_l1  | so_ac6     | product      | 10         |
    When the order identified by so_ac6 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_ac6     | so_ac6_l1      | N             | cp1                | cgt1                  |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_ac6                | inout_ac6  |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_ac6  | transpOrder_ac6            |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_ac6    | inout_ac6  |
    Then validate the captured nShift shipment request options:
      | IsManual | IsSelectionRules | ServiceLevel |
      | N        | Y                | STANDARD     |

  @Id:S0355_DeliveryOrder_180
  Scenario: nShift Delivery Order — IsSelectionRules=false when shipper has IsSelectionRules and schedules have any manual
    # When every schedule linked to the delivery order was advised manually,
    # the gateway must NOT set UseShippingRules, so nShift uses its own shipment rules.
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_manual_advise  | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID       | M_Product_ID | QtyEntered |
      | so_manual_advise_l1  | so_manual_advise | product      | 10         |
    When the order identified by so_manual_advise is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID      | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_manual_advise | so_manual_advise_l1 | N             | cp1                | cgt1                  |
    # Set the carrier-advising status to Manual via the manual-advise process
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_manual_advise      | cp2                | cgt2                  | cs3, cs4           |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID      | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_manual_advise | so_manual_advise_l1 | N             | cp2                | cgt2                  |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID        |
      | ss_manual_advise      | inout_manual_advise |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID          | M_ShipperTransportation_ID    |
      | inout_manual_advise | transpOrder_manual_advise     |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier          | M_InOut_ID          |
      | cso_manual_advise   | inout_manual_advise |
    Then validate the captured nShift shipment request options:
      | IsManual | IsSelectionRules |
      | Y        | N                |

  @Id:S0355_DeliveryOrder_TC1
  Scenario: nShift Delivery Order exported via Historical Shipments JSON includes parcel tracking
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    # Test env has no AD_Printer_Config for the system user; auto-print would otherwise fail the WP.
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp1                | cgt1                  |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # Stable Name + EMail so reruns reuse the same AD_User and don't repoint it to a different bpartner —
    # the composite FK c_order(c_bpartner_id, ad_user_id) would otherwise block the update.
    And metasfresh contains AD_Users:
      | Identifier      | Name                    | C_BPartner_ID | EMail                       | Phone            |
      | customerContact | nShift Customer Contact | customer      | contact@nshift-test.example | +41 79 123 45 67 |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID      |
      | so_exp     | order_exp_ID | true    | customer      | 2025-04-01  | wh             | nShift       | customerContact |
    # 10-CU-per-TU packing → on-the-fly pick builds ONE TU → 1 parcel (not 10 loose-CU parcels).
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_exp_l1  | so_exp     | product      | 10         | product_TU_10CU         |
    When the order identified by so_exp is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_exp     | so_exp_l1      | N             | cp1                | cgt1                  |
    # pack the on-the-fly-picked CUs into a TU per the order-line packing (real shipper-transportation flow) → 1 parcel.
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID | IsOnTheFlyPickToPackingInstructions |
      | ss_exp                | inout_exp  | true                                |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_exp  | transpOrder_exp            |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_exp    | inout_exp  |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_exp                  | awb1 | trackingUrl1 | true        |
    # 10 PCE / 10 PCE-per-TU => 1 parcel; total weight = product.GrossWeight (2.1) × qty (10) = 21 kg.
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName    | ArticleValue   | CustomsTariffNumber | QtyShipped | Price | TotalPrice | TotalWeightInKg |
      | cso_exp                  | nShift Product | nshift_product | 12345678            | 10         | 10    | 100        | 21              |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | REST.Context.M_InOut_ID | REST.Context.DocumentNo |
      | ss_exp                | inout_exp  | shipment_exp_ID         | shipment_exp_DocumentNo |
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_exp      | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "Order_ID",
      "value": "@order_exp_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Order_ID": @order_exp_ID@,
    "Shipment_DocumentNo": "@shipment_exp_DocumentNo@",
    "DocStatus": "CO",
    "Lines": [
      {
        "LineNo": 10,
        "ProductValue": "nshift_product",
        "ProductName": "nShift Product",
        "QtyEntered": 10,
        "UOM": "Stk"
      }
    ],
    "Parcels": [
      {
        "TrackingNumber": "awb1",
        "TrackingURL": "trackingUrl1",
        "Carrier": "nShift",
        "Items": [
          {
            "ProductValue": "nshift_product",
            "ProductName": "nShift Product",
            "QtyShipped": 10,
            "TotalWeightInKg": 21,
            "CustomsTariffNumber": "12345678"
          }
        ]
      }
    ]
  }
]
    """

  Scenario: nShift Carrier Advise uses ExternalSystem-specific service level
    Given metasfresh contains External System
      | Name      | Value     |
      | Shopware6 | Shopware6 |
    And metasfresh contains M_Shipper_ServiceLevel_Configs:
      | M_Shipper_ID | SeqNo | ServiceLevel | ExternalSystem.Value |
      | nShift       | 10    | EXPRESS      | Shopware6            |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | ExternalSystem.Value |
      | so_sl1     | true    | customer      | 2025-04-01  | wh             | nShift       | Shopware6                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_sl1_l1  | so_sl1     | product      | 10         |
    When the order identified by so_sl1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_sl1     | so_sl1_l1      | N             | cp1                | cgt1                  |
    Then the last nShift ship advisor request had shipperConfig serviceLevel "EXPRESS"

  Scenario: nShift Carrier Advise falls back to default service level when no ExternalSystem matches
    Given metasfresh contains External System
      | Name      | Value     |
      | Shopware6 | Shopware6 |
    And metasfresh contains M_Shipper_ServiceLevel_Configs:
      | M_Shipper_ID | SeqNo | ServiceLevel | ExternalSystem.Value |
      | nShift       | 10    | EXPRESS      | Shopware6            |
      | nShift       | 20    | FALLBACK     |                      |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_sl2     | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_sl2_l1  | so_sl2     | product      | 10         |
    When the order identified by so_sl2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_sl2     | so_sl2_l1      | N             | cp1                | cgt1                  |
    Then the last nShift ship advisor request had shipperConfig serviceLevel "FALLBACK"

  @Id:S0355_DeliveryOrder_110
  Scenario: nShift Delivery Order - mixed-origin LU produces 1 parcel with per-COO content lines
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And contains M_Shippers
      | Identifier      | Value           | Name            | OPT.ShipperGateway |
      | nShift_coo_test | nshift_coo_test | nShift COO Test | nshift             |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID    |
      | nShift_coo_test |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID    |
      | cp_coo1    | nShift_coo_test |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID    |
      | cgt_coo1   | nShift_coo_test |
    And metasfresh contains M_Shipper_Mapping_Configs:
      | M_Shipper_ID    | SeqNo | MappingAttributeType | MappingGroupKey | MappingAttributeKey | MappingAttributeValue |
      | nShift_coo_test | 170   | LineDetailGroup      | 1               | 4                   | CountryOfOrigin       |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp_coo1            | cgt_coo1              |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # Dedicated warehouse so picking is not polluted by the background product stock in wh
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_coo         |
    # Two ASIs: one per country of origin
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_Products:
      | Identifier | Value    | Name             | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product2   | product2 | nShift Product 2 | 1 KGM     | 1.2 KGM     | ct                 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | product2     | 5.0      | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product2_TU_8CU         | huPiItemTU      | product2     | 8   | 2021-01-01 |
    # Stock: 7 IT + 13 DE of product (13 DE exceeds TU capacity → 2 TUs), 5 IT + 3 DE of product2.
    # All 5 TUs aggregated onto one mixed LU → 1 package → 1 parcel with 4 COO content lines.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_coo        | 2022-12-12   | wh_coo         |
    And metasfresh contains M_InventoriesLines:
      | Identifier    | M_Inventory_ID | M_Product_ID    | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_coo_l_it  | inv_coo        | product         | 0       | 7        | PCE          | asi_IT                    | product_TU_10CU         |
      | inv_coo_l_de  | inv_coo        | product         | 0       | 13       | PCE          | asi_DE                    | product_TU_10CU         |
      | inv_coo_l_pm  | inv_coo        | packing_product | 0       | 100      | PCE          |                           |                         |
      | inv_coo_l_it2 | inv_coo        | product2        | 0       | 5        | PCE          | asi_IT                    | product2_TU_8CU         |
      | inv_coo_l_de2 | inv_coo        | product2        | 0       | 3        | PCE          | asi_DE                    | product2_TU_8CU         |
    When the inventory identified by inv_coo is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | inv_coo_l_it       | tu_coo_it   |
      | inv_coo_l_de       | tu_coo_de_1 |
      | inv_coo_l_de       | tu_coo_de_2 |
      | inv_coo_l_it2      | tu_coo_it2  |
      | inv_coo_l_de2      | tu_coo_de2  |
    And aggregate TUs to new LU
      | sourceTUs                                                   | newLUs       |
      | tu_coo_it, tu_coo_de_1, tu_coo_de_2, tu_coo_it2, tu_coo_de2 | lu_coo_mixed |
    And metasfresh contains AD_Users:
      | Identifier          | Name                     | C_BPartner_ID | EMail                        | Phone            |
      | customerContact_coo | nShift Customer Contact2 | customer      | contact2@nshift-test.example | +41 79 123 45 68 |
    # One order for product (20 PCE) and product2 (8 PCE) — the system picks from both IT and DE batches
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID    | AD_User_ID          |
      | so_coo     | order_coo_ID | true    | customer      | 2025-04-01  | wh_coo         | nShift_coo_test | customerContact_coo |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_coo_l1  | so_coo     | product      | 20         |
      | so_coo_l2  | so_coo     | product2     | 8          |
    And the order identified by so_coo is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_coo     | so_coo_l1      | N             | cp_coo1            | cgt_coo1              |
      | ss_coo2    | so_coo_l2      | N             | cp_coo1            | cgt_coo1              |
    When create M_PickingCandidate for M_HU
      | M_HU_ID      | M_ShipmentSchedule_ID | QtyPicked | Status | PickStatus | ApprovalStatus |
      | lu_coo_mixed | ss_coo                | 20        | IP     | P          | ?              |
      | lu_coo_mixed | ss_coo2               | 8         | IP     | P          | ?              |
    And process picking
      | M_HU_ID      | M_ShipmentSchedule_ID |
      | lu_coo_mixed | ss_coo, ss_coo2       |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_coo, ss_coo2       | inout_coo  |
    # Verify 4 separate InOutLines — one per product×COO batch, driven by per-TU M_HU_Attribute COO values
    And validate the created shipment lines
      | M_InOut_ID | M_Product_ID | MovementQty | M_AttributeSetInstance_ID |
      | inout_coo  | product      | 7           | asi_IT                    |
      | inout_coo  | product      | 13          | asi_DE                    |
      | inout_coo  | product2     | 5           | asi_IT                    |
      | inout_coo  | product2     | 3           | asi_DE                    |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_coo  | transpOrder_coo            |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_coo    | inout_coo  |
    # nShift request — high level → parcel → content detail
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp_coo1            | cgt_coo1              | 1          |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 51.6          |
    And validate the captured nShift shipment request contents:
      | productName      | countryOfOrigin | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product   | IT              | 7               | 10        | 70         | 14.7            | 12345678      |
      | nShift Product   | DE              | 13              | 10        | 130        | 27.3            | 12345678      |
      | nShift Product 2 | IT              | 5               | 5         | 25         | 6.0             | 12345678      |
      | nShift Product 2 | DE              | 3               | 5         | 15         | 3.6             | 12345678      |
    # Carrier_ShipmentOrder in DB — parcels (AWB/tracking) → items
    # Mixed LU: 1 physical package → 1 parcel with 4 content lines, total weight 51.6 kg
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_coo                  | awb1 | trackingUrl1 | true        |
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName      | CountryOfOrigin | QtyShipped | Price | TotalPrice | TotalWeightInKg | CustomsTariffNumber |
      | cso_coo                  | nShift Product   | IT              | 7          | 10    | 70         | 14.7            | 12345678            |
      | cso_coo                  | nShift Product   | DE              | 13         | 10    | 130        | 27.3            | 12345678            |
      | cso_coo                  | nShift Product 2 | IT              | 5          | 5     | 25         | 6.0             | 12345678            |
      | cso_coo                  | nShift Product 2 | DE              | 3          | 5     | 15         | 3.6             | 12345678            |
    # gh30205 — the per-country-of-origin content lines must be distinguishable in the Historical Shipments JSON export
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_coo      | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Historical_Shipments_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "Order_ID",
      "value": "@order_coo_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
[
  {
    "Order_ID": @order_coo_ID@,
    "Parcels": [
      {
        "Carrier": "nShift COO Test",
        "Items": [
          {
            "ProductValue": "nshift_product",
            "ProductName": "nShift Product",
            "QtyShipped": 7,
            "CountryOfOrigin": "IT"
          },
          {
            "ProductValue": "nshift_product",
            "ProductName": "nShift Product",
            "QtyShipped": 13,
            "CountryOfOrigin": "DE"
          },
          {
            "ProductValue": "product2",
            "ProductName": "nShift Product 2",
            "QtyShipped": 5,
            "CountryOfOrigin": "IT"
          },
          {
            "ProductValue": "product2",
            "ProductName": "nShift Product 2",
            "QtyShipped": 3,
            "CountryOfOrigin": "DE"
          }
        ]
      }
    ]
  }
]
    """


  @Id:S0355_DeliveryOrder_120
  Scenario: nShift Delivery Order - Country of Origin from mixed TUs (3 batches in one, 1 in another)
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    # Dedicated shipper with IsSelectionRules=N: the carrier is explicit/known, so the two same-carrier TUs
    # consolidate into ONE delivery order with two parcels (no per-package split at ship time). A distinct shipper
    # (not the shared nShift_coo_test, which stays rules=Y) keeps this setting from leaking into the sibling COO scenarios.
    And contains M_Shippers
      | Identifier    | Value         | Name               | OPT.ShipperGateway |
      | nShift_coo_tu | nshift_coo_tu | nShift COO TU Test | nshift             |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID  | IsSelectionRules |
      | nShift_coo_tu | N                |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID  |
      | cp_coo1    | nShift_coo_tu |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID  |
      | cgt_coo1   | nShift_coo_tu |
    And metasfresh contains M_Shipper_Mapping_Configs:
      | M_Shipper_ID  | SeqNo | MappingAttributeType | MappingGroupKey | MappingAttributeKey | MappingAttributeValue |
      | nShift_coo_tu | 170   | LineDetailGroup      | 1               | 4                   | CountryOfOrigin       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_coo         |
    And metasfresh contains M_PickingSlot:
      | Identifier      | PickingSlot | IsDynamic |
      | pickingSlot_coo | 300         | Y         |
    And metasfresh contains M_Products:
      | Identifier | Value    | Name             | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product2   | product2 | nShift Product 2 | 1 KGM     | 1.2 KGM     | ct                 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | product2     | 5.0      | PCE      |
    # TU PI without LU parent so picked TUs stay top-level (no LU wrapping during transportation)
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID    |
      | TU_without_lu |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID    | M_HU_PI_ID    | HU_UnitType | IsCurrent |
      | TU_without_lu_Version | TU_without_lu | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID        | M_HU_PI_Version_ID    | Qty | ItemType |
      | huPiItem_TU_without_lu | TU_without_lu_Version |     | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID    | M_HU_PI_Item_ID        | M_Product_ID | Qty | ValidFrom  |
      | product_TU_without_lu_10CU | huPiItem_TU_without_lu | product      | 10  | 2021-01-01 |
      | product2_TU_without_lu_8CU | huPiItem_TU_without_lu | product2     | 8   | 2021-01-01 |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp_coo1            | cgt_coo1              |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_120":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_120":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    # 4 CU batches received as TUs. TU_without_lu PI has no LU parent so TUs stay top-level during shipping.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_coo_tu     | 2022-12-12   | wh_coo         |
    And metasfresh contains M_InventoriesLines:
      | Identifier       | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID    |
      | inv_coo_tu_l_it  | inv_coo_tu     | product      | 0       | 6        | PCE          | asi_IT_120                | product_TU_without_lu_10CU |
      | inv_coo_tu_l_de  | inv_coo_tu     | product      | 0       | 9        | PCE          | asi_DE_120                | product_TU_without_lu_10CU |
      | inv_coo_tu_l_it2 | inv_coo_tu     | product2     | 0       | 4        | PCE          | asi_IT_120                | product2_TU_without_lu_8CU |
      | inv_coo_tu_l_de2 | inv_coo_tu     | product2     | 0       | 3        | PCE          | asi_DE_120                | product2_TU_without_lu_8CU |
    When the inventory identified by inv_coo_tu is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID       |
      | inv_coo_tu_l_it    | cu_coo_tu_it  |
      | inv_coo_tu_l_de    | cu_coo_tu_de  |
      | inv_coo_tu_l_it2   | cu_coo_tu_it2 |
      | inv_coo_tu_l_de2   | cu_coo_tu_de2 |
    And metasfresh contains AD_Users:
      | Identifier         | Name                     | C_BPartner_ID | EMail                        | Phone            |
      | customerContact_tu | nShift Customer Contact3 | customer      | contact3@nshift-test.example | +41 79 123 45 69 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID    | AD_User_ID         |
      | so_coo_tu  | true    | customer      | 2025-04-01  | wh_coo         | nShift_coo_tu   | customerContact_tu |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | so_coo_tu_l1 | so_coo_tu  | product      | 15         |
      | so_coo_tu_l2 | so_coo_tu  | product2     | 7          |
    When the order identified by so_coo_tu is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_coo_tu  | so_coo_tu_l1   | N             | cp_coo1            | cgt_coo1              |
      | ss_coo_tu2 | so_coo_tu_l2   | N             | cp_coo1            | cgt_coo1              |
    # Mobile picking: set new TU as target, pick 3 batches into it, then new TU for the 4th batch
    When start picking job for sales order identified by so_coo_tu
    And scan picking slot identified by pickingSlot_coo
    And set picking target as new TU identified by TU_without_lu
    And pick lines
      | PickingLine.byProduct | PickFromHU    | QtyPicked |
      | product               | cu_coo_tu_it  | 6         |
      | product               | cu_coo_tu_de  | 9         |
      | product2              | cu_coo_tu_it2 | 4         |
    And set picking target as new TU identified by TU_without_lu
    And pick lines
      | PickingLine.byProduct | PickFromHU    | QtyPicked |
      | product2              | cu_coo_tu_de2 | 3         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID   | DocStatus |
      | ss_coo_tu             | inout_coo_tu | CO        |
    And validate the created shipment lines
      | M_InOut_ID   | M_Product_ID | MovementQty | M_AttributeSetInstance_ID |
      | inout_coo_tu | product      | 6           | asi_IT_120                |
      | inout_coo_tu | product      | 9           | asi_DE_120                |
      | inout_coo_tu | product2     | 4           | asi_IT_120                |
      | inout_coo_tu | product2     | 3           | asi_DE_120                |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID   | M_ShipperTransportation_ID |
      | inout_coo_tu | transpOrder_coo_tu         |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID   |
      | cso_coo_tu | inout_coo_tu |
    # nShift request — high level → parcel → content detail
    # TU1 (3 batches): product IT, product DE, product2 IT → 3 content lines
    # TU2 (1 batch):   product2 DE                         → 1 content line
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp_coo1            | cgt_coo1              | 2          |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 36.3          |
      | 3.6           |
    And validate the captured nShift shipment request contents:
      | productName      | countryOfOrigin | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product   | IT              | 6               | 10        | 60         | 12.6            | 12345678      |
      | nShift Product   | DE              | 9               | 10        | 90         | 18.9            | 12345678      |
      | nShift Product 2 | IT              | 4               | 5         | 20         | 4.8             | 12345678      |
      | nShift Product 2 | DE              | 3               | 5         | 15         | 3.6             | 12345678      |
    # Carrier_ShipmentOrder in DB — parcels (AWB/tracking) → items
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_coo_tu               | awb1 | trackingUrl1 | true        |
      | cso_coo_tu               | awb2 | trackingUrl2 | true        |
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName      | CountryOfOrigin | QtyShipped | Price | TotalPrice | TotalWeightInKg | CustomsTariffNumber |
      | cso_coo_tu               | nShift Product   | IT              | 6          | 10    | 60         | 12.6            | 12345678            |
      | cso_coo_tu               | nShift Product   | DE              | 9          | 10    | 90         | 18.9            | 12345678            |
      | cso_coo_tu               | nShift Product 2 | IT              | 4          | 5     | 20         | 4.8             | 12345678            |
      | cso_coo_tu               | nShift Product 2 | DE              | 3          | 5     | 15         | 3.6             | 12345678            |

  @from:cucumber
  @Id:S0355_DeliveryOrder_130
  Scenario: nShift COO — pre-packed mixed-origin TU produces per-COO InOutLines when picked
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And contains M_Shippers
      | Identifier      | Value           | Name            | OPT.ShipperGateway |
      | nShift_coo_test | nshift_coo_test | nShift COO Test | nshift             |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID    |
      | nShift_coo_test |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID    |
      | cp_coo1    | nShift_coo_test |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID    |
      | cgt_coo1   | nShift_coo_test |
    And metasfresh contains M_Shipper_Mapping_Configs:
      | M_Shipper_ID    | SeqNo | MappingAttributeType | MappingGroupKey | MappingAttributeKey | MappingAttributeValue |
      | nShift_coo_test | 170   | LineDetailGroup      | 1               | 4                   | CountryOfOrigin       |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp_coo1            | cgt_coo1              |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_coo_130     |
    And metasfresh contains M_PickingSlot:
      | Identifier      | PickingSlot | IsDynamic | M_Warehouse_ID |
      | pickingSlot_130 | 500         | Y         | wh_coo_130     |
    And metasfresh contains AD_Users:
      | Identifier          | Name                      | C_BPartner_ID | EMail                        | Phone            |
      | customerContact_130 | nShift Customer Contact 4 | customer      | contact4@nshift-test.example | +41 79 123 45 71 |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_130":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_130":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_coo_130    | 2022-12-12   | wh_coo_130     |
    And metasfresh contains M_InventoriesLines:
      | Identifier       | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID |
      | inv_coo_130_l_it | inv_coo_130    | product      | 0       | 6        | PCE          | asi_IT_130                |
      | inv_coo_130_l_de | inv_coo_130    | product      | 0       | 9        | PCE          | asi_DE_130                |
    When the inventory identified by inv_coo_130 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID   |
      | inv_coo_130_l_it   | cu_130_it |
      | inv_coo_130_l_de   | cu_130_de |
    # Pack cu_130_it into a new TU (tu_mixed_130), then move cu_130_de into the same TU.
    # Result: tu_mixed_130 is a non-virtual TU with VHU_IT (6 PCE, COO=IT) + VHU_DE (9 PCE, COO=DE).
    And transform CU to new TUs
      | sourceCU  | cuQty | M_HU_PI_Item_Product_ID | resultedNewTUs |
      | cu_130_it | 6     | product_TU_10CU         | tu_mixed_130   |
    And move CU to existing TU
      | sourceCU  | targetTU     | qty |
      | cu_130_de | tu_mixed_130 | 9   |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID    | AD_User_ID          |
      | so_coo_130  | true    | customer      | 2025-04-01  | wh_coo_130     | nShift_coo_test | customerContact_130 |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | so_coo_130_l1 | so_coo_130 | product      | 15         |
    When the order identified by so_coo_130 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_coo_130 | so_coo_130_l1  | N             | cp_coo1            | cgt_coo1              |
    # Pick FROM tu_mixed_130 — the gap manifests here: non-virtual TU with mixed VHUs
    When start picking job for sales order identified by so_coo_130
    And scan picking slot identified by pickingSlot_130
    And set picking target as new TU identified by TU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | tu_mixed_130 | 15        |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    | DocStatus |
      | ss_coo_130            | inout_coo_130 | CO        |
    And validate the created shipment lines
      | M_InOut_ID    | M_Product_ID | MovementQty | M_AttributeSetInstance_ID |
      | inout_coo_130 | product      | 6           | asi_IT_130                |
      | inout_coo_130 | product      | 9           | asi_DE_130                |
    # Every shipment line the attribute-mixed TU is split into carries its own M_ShipmentSchedule_QtyPicked row —
    # the whole-TU pick is split into one allocation per attribute group. Matched in any order (the rows are a set).
    And validate M_ShipmentSchedule_QtyPicked records in any order for M_ShipmentSchedule identified by ss_coo_130
      | QtyPicked | M_TU_HU_ID   |
      | 6         | tu_mixed_130 |
      | 9         | tu_mixed_130 |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID    | M_ShipperTransportation_ID |
      | inout_coo_130 | transpOrder_coo_130        |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier  | M_InOut_ID    |
      | cso_coo_130 | inout_coo_130 |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp_coo1            | cgt_coo1              | 1          |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 31.5          |
    And validate the captured nShift shipment request contents:
      | productName    | countryOfOrigin | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product | IT              | 6               | 10        | 60         | 12.6            | 12345678      |
      | nShift Product | DE              | 9               | 10        | 90         | 18.9            | 12345678      |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_coo_130              | awb1 | trackingUrl1 | true        |
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName    | CountryOfOrigin | QtyShipped | Price | TotalPrice | TotalWeightInKg | CustomsTariffNumber |
      | cso_coo_130              | nShift Product | IT              | 6          | 10    | 60         | 12.6            | 12345678            |
      | cso_coo_130              | nShift Product | DE              | 9          | 10    | 90         | 18.9            | 12345678            |

  @from:cucumber
  @Id:S0355_DeliveryOrder_140
  Scenario: nShift COO — same-COO picks of the same product merge; same-COO picks of different products do not
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And contains M_Shippers
      | Identifier      | Value           | Name            | OPT.ShipperGateway |
      | nShift_coo_test | nshift_coo_test | nShift COO Test | nshift             |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID    |
      | nShift_coo_test |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID    |
      | cp_coo1    | nShift_coo_test |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID    |
      | cgt_coo1   | nShift_coo_test |
    And metasfresh contains M_Shipper_Mapping_Configs:
      | M_Shipper_ID    | SeqNo | MappingAttributeType | MappingGroupKey | MappingAttributeKey | MappingAttributeValue |
      | nShift_coo_test | 170   | LineDetailGroup      | 1               | 4                   | CountryOfOrigin       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_coo         |
    And metasfresh contains M_PickingSlot:
      | Identifier      | PickingSlot | IsDynamic |
      | pickingSlot_coo | 300         | Y         |
    And metasfresh contains M_Products:
      | Identifier | Value    | Name             | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product2   | product2 | nShift Product 2 | 1 KGM     | 1.2 KGM     | ct                 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | product2     | 5.0      | PCE      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID    |
      | TU_without_lu |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID    | M_HU_PI_ID    | HU_UnitType | IsCurrent |
      | TU_without_lu_Version | TU_without_lu | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID        | M_HU_PI_Version_ID    | Qty | ItemType |
      | huPiItem_TU_without_lu | TU_without_lu_Version |     | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID    | M_HU_PI_Item_ID        | M_Product_ID | Qty | ValidFrom  |
      | product_TU_without_lu_10CU | huPiItem_TU_without_lu | product      | 10  | 2021-01-01 |
      | product2_TU_without_lu_8CU | huPiItem_TU_without_lu | product2     | 8   | 2021-01-01 |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp_coo1            | cgt_coo1              |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_140":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_140":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    # product:  6 PCE COO=IT  (batch a) + 3 PCE COO=IT (batch b) + 7 PCE COO=DE
    # product2: 6 PCE COO=IT — same qty AND same COO as product batch a
    # Expected: product IT batches a+b merge into 9 PCE; product DE stays 7 PCE; product2 IT stays separate
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_coo_140    | 2022-12-12   | wh_coo         |
    And metasfresh contains M_InventoriesLines:
      | Identifier           | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID    |
      | inv_coo_140_l_it_a   | inv_coo_140    | product      | 0       | 6        | PCE          | asi_IT_140                | product_TU_without_lu_10CU |
      | inv_coo_140_l_it_b   | inv_coo_140    | product      | 0       | 3        | PCE          | asi_IT_140                | product_TU_without_lu_10CU |
      | inv_coo_140_l_de     | inv_coo_140    | product      | 0       | 7        | PCE          | asi_DE_140                | product_TU_without_lu_10CU |
      | inv_coo_140_l_p2_it  | inv_coo_140    | product2     | 0       | 6        | PCE          | asi_IT_140                | product2_TU_without_lu_8CU |
    When the inventory identified by inv_coo_140 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID        |
      | inv_coo_140_l_it_a  | cu_140_it_a    |
      | inv_coo_140_l_it_b  | cu_140_it_b    |
      | inv_coo_140_l_de    | cu_140_de      |
      | inv_coo_140_l_p2_it | cu_140_p2_it   |
    And metasfresh contains AD_Users:
      | Identifier          | Name                     | C_BPartner_ID | EMail                        | Phone            |
      | customerContact_140 | nShift Customer Contact5 | customer      | contact5@nshift-test.example | +41 79 123 45 71 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID    | AD_User_ID          |
      | so_coo_140 | true    | customer      | 2025-06-01  | wh_coo         | nShift_coo_test | customerContact_140 |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered |
      | so_coo_140_l1 | so_coo_140 | product      | 16         |
      | so_coo_140_l2 | so_coo_140 | product2     | 6          |
    When the order identified by so_coo_140 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_coo_140    | so_coo_140_l1  | N             | cp_coo1            | cgt_coo1              |
      | ss_coo_140_p2 | so_coo_140_l2  | N             | cp_coo1            | cgt_coo1              |
    When start picking job for sales order identified by so_coo_140
    And scan picking slot identified by pickingSlot_coo
    And set picking target as new TU identified by TU_without_lu
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | cu_140_it_a  | 6         |
      | product               | cu_140_it_b  | 3         |
      | product               | cu_140_de    | 7         |
      | product2              | cu_140_p2_it | 6         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    | DocStatus |
      | ss_coo_140            | inout_coo_140 | CO        |
    And validate the created shipment lines
      | M_InOut_ID    | M_Product_ID | MovementQty | M_AttributeSetInstance_ID |
      | inout_coo_140 | product      | 9           | asi_IT_140                |
      | inout_coo_140 | product      | 7           | asi_DE_140                |
      | inout_coo_140 | product2     | 6           | asi_IT_140                |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID    | M_ShipperTransportation_ID |
      | inout_coo_140 | transpOrder_coo_140        |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier  | M_InOut_ID    |
      | cso_coo_140 | inout_coo_140 |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp_coo1            | cgt_coo1              | 1          |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 40.8          |
    # product IT 9*2.1=18.9, product DE 7*2.1=14.7, product2 IT 6*1.2=7.2
    # product and product2 share COO=IT but appear as separate content lines (different products)
    And validate the captured nShift shipment request contents:
      | productName      | countryOfOrigin | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product   | IT              | 9               | 10        | 90         | 18.9            | 12345678      |
      | nShift Product   | DE              | 7               | 10        | 70         | 14.7            | 12345678      |
      | nShift Product 2 | IT              | 6               | 5         | 30         | 7.2             | 12345678      |

  @Id:S0355_DeliveryOrder_150
  Scenario: nShift Partial Shipment — the schedule's carrier freezes into the first shipment's order; the re-advised remainder ships on a second carrier
    # The carrier is sourced from the shipment SCHEDULE at send time (not from the picking-job line).
    # Pick 6 of 10 in a first job → the schedule's carrier (cp1) is frozen into the first Carrier_ShipmentOrder.
    # After re-advise to cp2 the remaining 4 are picked in a SECOND job → a second Carrier_ShipmentOrder on
    # cp2. The first (frozen) Carrier_ShipmentOrder must still carry cp1.
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    # CREATE_AND_COMPLETE (not …_CLOSE): the partial shipment is created+completed (freezing its
    # Carrier_ShipmentOrder) but the schedule stays OPEN so the 4-CU remainder can be picked in a second job.
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_AND_COMPLETE  | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot_ps    | slot_ps_v   | Y         |
    # Stub advisor to return cp1 — the carrier the first pick's line receives.
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1, cs2           |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ps      | true    | customer      | 2025-04-01  | wh             | nShift       |
    # No M_HU_PI_Item_Product_ID: pick loose CU so QtyPicked is in product units (2 of 4), not TUs.
    # This customer ships self-packed loose CUs, so each picked CU becomes its own M_Package ⇒ its own
    # Carrier_ShipmentOrder under selection rules. Small qty keeps the 1-order-per-CU split visible without noise.
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_ps_l1   | so_ps      | product      | 4          |
    When the order identified by so_ps is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ps      | so_ps_l1       | N             |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_ps                 |
    # First advise landed on the schedule = cp1.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | Carrier_Product_ID |
      | ss_ps      | so_ps_l1       | cp1                |
    # First job: pick only 2 of 4 and complete (partial). The line carrier advise resolves to cp1.
    When start picking job for sales order identified by so_ps
    And scan picking slot identified by slot_ps
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 2         |
    When complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    | DocStatus |
      | ss_ps                 | inout_partial | CO        |
    # Loose-CU split: 2 CU picked with no packing item ⇒ 2 M_Package (one label/parcel per CU) ⇒ 2 delivery
    # orders, both frozen on cp1. Listed 1-to-1 so the split is visible on the page. The exact-set step polls
    # (delivery orders are the LAST link of the async packages→orders chain), so it also gates the M_Package check.
    And after not more than 60s, Carrier_ShipmentOrders for M_InOut_ID inout_partial have exactly:
      | Carrier_Product_ID |
      | cp1                |
      | cp1                |
    And validate M_Packages for shipment inout_partial
      | M_Package_ID |
      | pkg_p1       |
      | pkg_p2       |
    # Re-stub to cp2 AFTER the first advise landed + froze onto inout_partial's orders.
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp2                | cgt2                  | cs3, cs4           |
    # Job 1 shipped 2 of 4 ⇒ the remainder settles to QtyToDeliver=2. (In production a qty change auto-re-advises;
    # under SKIP_WP_PROCESSOR_FOR_AUTOMATION the async workpackage doesn't run, so trigger the re-advise explicitly.)
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | QtyToDeliver |
      | ss_ps      | so_ps_l1       | 2            |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID | IsIncludeCarrierAdviseManual |
      | ss_ps                 | true                         |
    # Remainder re-advised to cp2 on the schedule, so the SECOND job's line inherits cp2 at job creation.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | Carrier_Product_ID |
      | ss_ps      | so_ps_l1       | cp2                |
    # Second job: pick the remaining 2 → its line inherits cp2 from the schedule → two more Carrier_ShipmentOrders on cp2.
    When start picking job for sales order identified by so_ps
    And scan picking slot identified by slot_ps
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 2         |
    When complete picking job
    # The 2-CU remainder ships in a SECOND shipment; capture it (ignoring the first shipment's lines).
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      | DocStatus | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | ss_ps                 | inout_remainder | CO        | inout_partial                           |
    # Loose-CU split: the re-advised remainder of 2 CU ⇒ 2 M_Package ⇒ 2 delivery orders, both on cp2.
    # Exact-set step polls (gates the M_Package check below on the async packages→orders chain).
    And after not more than 60s, Carrier_ShipmentOrders for M_InOut_ID inout_remainder have exactly:
      | Carrier_Product_ID |
      | cp2                |
      | cp2                |
    And validate M_Packages for shipment inout_remainder
      | M_Package_ID |
      | pkg_r1       |
      | pkg_r2       |
    # The remainder was re-advised to cp2 (asserted above) and now shipped in this second job.
    # The FIRST (frozen) shipment's orders must ALL still carry cp1 — the remainder's cp2 must not mutate them.
    Then after not more than 60s, Carrier_ShipmentOrders for M_InOut_ID inout_partial have exactly:
      | Carrier_Product_ID |
      | cp1                |
      | cp1                |

  @from:cucumber
  @Id:S0355_DeliveryOrder_200
  Scenario: Carrier-advise guard — picking job completion rejected when one package/pallet carries two different manual carriers
    # Two order lines (product + product_2) on one nShift order → two schedules. Both are auto-advised to cp1
    # on order completion, then MANUALLY overridden with DIFFERENT carriers (product_2→cp1, product→cp2).
    # Picked into ONE LU (one package/pallet) → two distinct manual carriers on a single HU → the manual-inconsistent-on-HU guard
    # rejects completion: a manual carrier is a human override and two conflicting overrides on one package
    # cannot be auto-resolved. (One manual + one automatic on the same package would instead COMPLETE — manual wins.)
    Given set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier     | PickingSlot     | IsDynamic |
      | slot_manualmix | guard_manualmix | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_manualmix | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID   | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_manualmix_l1 | so_manualmix | product      | 10         | product_TU_10CU         |
      | so_manualmix_l2 | so_manualmix | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_manualmix is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | IsToRecompute |
      | ss_manualmix_1 | so_manualmix_l1 | N             |
      | ss_manualmix_2 | so_manualmix_l2 | N             |
    # The auto-advise workpackage runs on order completion; a manual advise is only eligible once a schedule has
    # reached Completed (isEligibleForManualEnqueue excludes Requested). So WAIT for the auto-advise to land the
    # carrier (cp1) on BOTH schedules before overriding them manually — otherwise a manual advise on a
    # still-Requested schedule is silently skipped (it stays Requested and later auto-advises, losing the Manual).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | Carrier_Product_ID |
      | ss_manualmix_1 | so_manualmix_l1 | cp1                |
      | ss_manualmix_2 | so_manualmix_l2 | cp1                |
    # Manually override BOTH schedules with DIFFERENT carriers (product_2→cp1, product→cp2) → two distinct manual carriers
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_manualmix_2        | cp1                | cgt1                  | cs1                |
      | nShift       | ss_manualmix_1        | cp2                | cgt2                  | cs2                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | IsToRecompute | Carrier_Product_ID |
      | ss_manualmix_2 | so_manualmix_l2 | N             | cp1                |
      | ss_manualmix_1 | so_manualmix_l1 | N             | cp2                |
    # Pick both schedules into the same LU (one package/pallet) → two distinct manual carriers on one HU
    When start picking job for sales order identified by so_manualmix
    And scan picking slot identified by slot_manualmix
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | hu_1         | 10        |
      | product_2             | hu_product_2 | 10        |
    Then completing the picking job is rejected with AD_Message "de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"

  @from:cucumber
  @Id:S0355_DeliveryOrder_201
  Scenario: Carrier-advise guard — closing an LU carrying two different manual carriers is rejected, and the advise is read-only
    # Same two-distinct-manual setup as _200, but the picker CLOSES the LU instead of completing. Closing the LU
    # routes through closeLUAndTUPickingTargets, so the consistency guard runs on the closed LU → rejected at close.
    # Also: with a manual carrier on the target, the mobile advise button must be read-only (a manual is a human
    # override that a re-advise cannot converge). The read-only expectation is target-shape-agnostic (resolveInfo
    # keys on presence-of-target, not LU-vs-TU), so it is asserted here for the LU target and in _202 for the TU.
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot       | guard_lu    | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so         | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_l1      | so         | product      | 5          | product_TU_10CU         |
      | so_l2      | so         | product_2    | 5          | product_2_TU_10CU       |
    When the order identified by so is completed
    # Wait for the auto-advise (cp1) to land on BOTH schedules — recompute settled AND carrier resolved in one
    # gate — before overriding them manually (a manual advise on a still-Requested schedule is silently skipped).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_1       | so_l1          | N             | cp1                |
      | ss_2       | so_l2          | N             | cp1                |
    # Manually override the two schedules with DIFFERENT carriers → two distinct manual carriers on one package.
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_2                  | cp1                | cgt1                  | cs1                |
      | nShift       | ss_1                  | cp2                | cgt2                  | cs2                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_2       | so_l2          | N             | cp1                |
      | ss_1       | so_l1          | N             | cp2                |
    When start picking job for sales order identified by so
    And scan picking slot identified by slot
    And set picking target as new LU identified by LU
    # The Background hu_1/hu_product_2 are TUs — valid here because they pack as whole TUs onto the LU pallet
    # (a TU target instead needs CU sources — see _202).
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | hu_1         | 5         |
      | product_2             | hu_product_2 | 5         |
    # Both lines carry a (distinct) manual carrier → the header advise button must be read-only.
    Then expect current picking job:
      | HasLuTarget |
      | Y           |
    # The advise button (resolveInfo output → JSON) must be read-only: the target LU holds a manual carrier.
    Then expect current picking job header carrier advise
      | available | readOnly |
      | true      | true     |
    Then closing the LU picking target is rejected with AD_Message "de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"

  @from:cucumber
  @Id:S0355_DeliveryOrder_202
  Scenario: Carrier-advise guard — closing a TU carrying two different manual carriers is rejected, and the advise is read-only
    # Same two-distinct-manual setup as _200/_201, but both products are packed into ONE TU (a mixed TU) and the
    # picker CLOSES the TU. The standalone TU-close must run the same consistency guard as LU-close/complete —
    # otherwise the two distinct manual carriers slip silently past close and only surface as a raw
    # ShipperGatewayException at shipment generation.
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot       | guard_tu    | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    # Own inventory producing plain CUs (no packing presupposed — the TU is a pick-time decision) so both
    # products can be picked INTO the one new TU target. The Background hu_1/hu_product_2 are bare TUs and a TU
    # cannot be nested into a TU pick target.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_tu         | 2022-12-12   | wh             |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_tu_l1  | inv_tu         | product      | 0       | 5        | PCE          |
      | inv_tu_l2  | inv_tu         | product_2    | 0       | 5        | PCE          |
    When the inventory identified by inv_tu is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_tu_l1          | cu_p1   |
      | inv_tu_l2          | cu_p2   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so         | true    | customer      | 2025-04-01  | wh             | nShift       |
    # Order lines in CU units (no M_HU_PI_Item_Product_ID) so picks pack CUs INTO the one TU target (like :689);
    # the TU-unit picking that M_HU_PI_Item_Product_ID triggers is for LU targets (whole TUs onto a pallet).
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 5          |
      | so_l2      | so         | product_2    | 5          |
    When the order identified by so is completed
    # Wait for the auto-advise (cp1) to land on BOTH schedules — recompute settled AND carrier resolved in one
    # gate — before overriding them manually (a manual advise on a still-Requested schedule is silently skipped).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_1       | so_l1          | N             | cp1                |
      | ss_2       | so_l2          | N             | cp1                |
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_2                  | cp1                | cgt1                  | cs1                |
      | nShift       | ss_1                  | cp2                | cgt2                  | cs2                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_2       | so_l2          | N             | cp1                |
      | ss_1       | so_l1          | N             | cp2                |
    When start picking job for sales order identified by so
    And scan picking slot identified by slot
    # Before a target is selected: divergent API-advise carriers → available, but read-only (nothing to advise onto).
    Then expect current picking job header carrier advise
      | available | readOnly |
      | true      | true     |
    And set picking target as new TU identified by TU
    # Target set but nothing picked into it yet → still read-only (nothing to advise onto).
    Then expect current picking job header carrier advise
      | available | readOnly |
      | true      | true     |
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | cu_p1      | 5         |
      | product_2             | cu_p2      | 5         |
    Then expect current picking job:
      | HasTuTarget |
      | Y           |
    # The advise button (resolveInfo output → JSON) must be read-only: the target TU holds a manual carrier.
    Then expect current picking job header carrier advise
      | available | readOnly |
      | true      | true     |
    Then closing the TU picking target is rejected with AD_Message "de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"

  @from:cucumber
  @Id:S0355_DeliveryOrder_210
  Scenario: Carrier-advise guard — picking job completion blocked on divergent non-manual carrier products, selection rules OFF
    # Two non-manual schedules on one LU. On order completion both auto-advise to cp1. Re-advising
    # schedule 2 against a cp2 stub (while still un-picked) makes its carrier product diverge (cp1 vs
    # cp2). The shipper's Carrier_Config.IsSelectionRules='N' → the explicit carrier product is
    # authoritative → the non-manual-divergent-on-HU guard rejects completion. (The convergent-then-succeeds counterpart is
    # covered by the OK scenario; a picked schedule is no longer eligible for re-advise, so it cannot be
    # re-unified here.)
    Given set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    # Selection rules OFF on the nShift shipper: the explicit carrier product is authoritative, so divergence blocks completion.
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID | IsSelectionRules |
      | nShift       | N                |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier     | PickingSlot     | IsDynamic |
      | slot_divergoff | guard_divergoff | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_divergoff | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID   | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_divergoff_l1 | so_divergoff | product      | 10         | product_TU_10CU         |
      | so_divergoff_l2 | so_divergoff | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_divergoff is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | IsToRecompute | Carrier_Product_ID |
      | ss_divergoff_1 | so_divergoff_l1 | N             | cp1                |
      | ss_divergoff_2 | so_divergoff_l2 | N             | cp1                |
    # Re-stub the advisor to cp2 and re-advise schedule 2 only → ss_divergoff_1=cp1, ss_divergoff_2=cp2 (divergent, both non-manual)
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp2                | cgt2                  | cs2                |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_divergoff_2        |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID  | IsToRecompute | Carrier_Product_ID |
      | ss_divergoff_2 | so_divergoff_l2 | N             | cp2                |
    # Pick both schedules into the same LU — ss_divergoff_1 has cp1, ss_divergoff_2 has cp2
    When start picking job for sales order identified by so_divergoff
    And scan picking slot identified by slot_divergoff
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | hu_1         | 10        |
      | product_2             | hu_product_2 | 10        |
    # divergent non-manual carrier products on the same HU
    Then completing the picking job is rejected with AD_Message "de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"

  @from:cucumber
  @Id:S0355_DeliveryOrder_215
  Scenario: Carrier-advise guard — picking job completes on divergent non-manual carrier products when selection rules are ON (guard skipped)
    # Same divergent-non-manual-products-on-one-LU setup as 210, but the shipper's
    # Carrier_Config.IsSelectionRules='Y' (the column default). With selection rules ON, nShift resolves
    # the carrier via its rules and a re-advise harmonises it, so the explicit carrier product is not
    # authoritative → the non-manual-divergent-on-HU guard is skipped and completion creates the shipment.
    Given set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    # Selection rules ON on the nShift shipper (explicit default 'Y'): divergence is NOT a completion blocker.
    # Why: with selection rules ON + non-manual carriers, the carrier is re-advised at shipment creation, so the
    # carrier product is (re)selected on ship — the divergent product provided during picking is not relevant.
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID | IsSelectionRules |
      | nShift       | Y                |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier    | PickingSlot    | IsDynamic |
      | slot_divergon | guard_divergon | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_divergon | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_divergon_l1 | so_divergon | product      | 10         | product_TU_10CU         |
      | so_divergon_l2 | so_divergon | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_divergon is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_divergon_1 | so_divergon_l1 | N             | cp1                |
      | ss_divergon_2 | so_divergon_l2 | N             | cp1                |
    # Re-stub the advisor to cp2 and re-advise schedule 2 only → ss_divergon_1=cp1, ss_divergon_2=cp2 (divergent, both non-manual)
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp2                | cgt2                  | cs2                |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_divergon_2         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_divergon_2 | so_divergon_l2 | N             | cp2                |
    # Pick both schedules into the same LU — ss_divergon_1 has cp1, ss_divergon_2 has cp2 (divergent)
    When start picking job for sales order identified by so_divergon
    And scan picking slot identified by slot_divergon
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | hu_1         | 10        |
      | product_2             | hu_product_2 | 10        |
    # Selection rules ON → guard skipped → completion succeeds and creates the shipment
    When complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID     | DocStatus |
      | ss_divergon_1         | inout_divergon | CO        |

  @from:cucumber
  @Id:S0355_DeliveryOrder_230
  Scenario: Carrier-advise guard — picking job completes when all packed schedules share one consistent carrier advise (OK)
    # Two schedules on one nShift order, both auto-advised to the same cp1/cgt1. Consistent non-manual
    # advise on a single LU → the guard passes and completion creates the shipment.
    Given set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot_ok    | guard_ok    | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ok      | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_ok_l1   | so_ok      | product      | 10         | product_TU_10CU         |
      | so_ok_l2   | so_ok      | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_ok is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ok_1    | so_ok_l1       | N             |
      | ss_ok_2    | so_ok_l2       | N             |
    # Auto-advise both schedules — both get cp1/cgt1 from the stub
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_ok_1               |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_ok_2               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_ok_1    | so_ok_l1       | N             | cp1                |
      | ss_ok_2    | so_ok_l2       | N             | cp1                |
    # Pick both schedules into the same LU — consistent non-manual advise on both
    When start picking job for sales order identified by so_ok
    And scan picking slot identified by slot_ok
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | hu_1         | 10        |
      | product_2             | hu_product_2 | 10        |
    When complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_ok_1               | inout_ok   | CO        |

  @from:cucumber
  @Id:S0355_PickingDisplay_300
  Scenario: Carrier-advise picking display — LU target, non-manual API-advise shipper shows available, editable, with carrier product caption
    # Schedule on the advise-enabled nShift shipper, auto-advised (non-manual, Completed) to a named carrier
    # product. Picked into a new LU → the LU target exposes carrierAdvise available + editable + the product caption.
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot   | IsDynamic |
      | slot_lu_avl | display_lu_av | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier  | M_Shipper_ID | Name           |
      | cp_lu_named | nShift       | LU Std Parcel  |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_lu_named        | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_lu_avl  | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_lu_avl_l1 | so_lu_avl  | product      | 10         | product_TU_10CU         |
    When the order identified by so_lu_avl is completed
    # The auto-advise workpackage runs on order completion; wait for it to land the carrier product (advise Completed, no explicit advise needed).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_lu_avl  | so_lu_avl_l1   | N             | cp_lu_named        | CO                      |
    When start picking job for sales order identified by so_lu_avl
    And scan picking slot identified by slot_lu_avl
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 10        |
    Then expect current picking job line carrier advise
      | target | available | readOnly | carrierProductCaption |
      | LU     | true      | false    | LU Std Parcel         |

  @from:cucumber
  @Id:S0355_PickingDisplay_310
  Scenario: Carrier-advise picking display — LU target, manual advise shows available but read-only
    # The schedule is advised Manually (M_ShipmentSchedule_Advise_Manual → Manual status). Picked into a new LU
    # → the LU target exposes carrierAdvise available but read-only (every advise-enabled schedule is Manual).
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier  | PickingSlot    | IsDynamic |
      | slot_lu_man | display_lu_man | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier      | M_Shipper_ID | Name          |
      | cp_lu_man_named | nShift       | LU Man Parcel |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_lu_man_named    | cgt2                  | cs3                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_lu_man  | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_lu_man_l1 | so_lu_man  | product      | 10         | product_TU_10CU         |
    When the order identified by so_lu_man is completed
    # The advise enqueued at order completion is processed asynchronously. Wait until the carrier product is set
    # (advise Completed) before manually overriding it — running the manual advise while the schedule is still
    # Requested is a silent no-op (Requested is ineligible for manual enqueue).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_lu_man  | so_lu_man_l1   | N             | cp_lu_man_named    | CO                      |
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_lu_man             | cp_lu_man_named    | cgt2                  | cs3                |
    # Assert the manual advise took effect: carrier-advising status is now Manual (drives readOnly=true in the picking display).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_lu_man  | so_lu_man_l1   | N             | cp_lu_man_named    | MAN                     |
    When start picking job for sales order identified by so_lu_man
    And scan picking slot identified by slot_lu_man
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 10        |
    Then expect current picking job line carrier advise
      | target | available | readOnly | carrierProductCaption |
      | LU     | true      | true     | LU Man Parcel         |

  @from:cucumber
  @Id:S0355_PickingDisplay_320
  Scenario: Carrier-advise picking display — LU target, non-API-advise shipper shows not available
    # The order's shipper is NOT advise-enabled (IsApiCarrierAdvise=N). Picked into a new LU → the LU target
    # exposes carrierAdvise as not available (no advise button rendered).
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And contains M_Shippers
      | Identifier | Value     | Name      | IsApiCarrierAdvise |
      | noAdvise   | no_advise | No Advise | N                  |
    And metasfresh contains M_PickingSlot:
      | Identifier   | PickingSlot   | IsDynamic |
      | slot_lu_none | display_lu_no | Y         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_lu_none | true    | customer      | 2025-04-01  | wh             | noAdvise     |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_lu_none_l1 | so_lu_none | product      | 10         | product_TU_10CU         |
    When the order identified by so_lu_none is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_lu_none | so_lu_none_l1  | N             |
    When start picking job for sales order identified by so_lu_none
    And scan picking slot identified by slot_lu_none
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 10        |
    Then expect current picking job line carrier advise
      | target | available | readOnly |
      | LU     | false     | false    |

  @from:cucumber
  @Id:S0355_PickingDisplay_330
  Scenario: Carrier-advise picking display — two lines with divergent non-manual carriers on one LU show the button but no current carrier product
    # Two order lines on one nShift order, both auto-advised to cp1 on completion, then line 2 is re-advised to
    # cp2 (still un-picked) → divergent non-manual carriers. Picked into ONE LU → the job-level advise resolves
    # to "available + editable, but no single carrier product" (the picker re-advises to converge). The advise
    # flags are asserted on the JOB HEADER, since both lines share it. The POJO assertions first pin the
    # ground-truth job state (LU target set, both carriers seeded) before the display flags are read.
    Given set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier   | PickingSlot     | IsDynamic |
      | slot_diverge | display_diverge | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_diverge | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_diverge_l1 | so_diverge | product      | 10         | product_TU_10CU         |
      | so_diverge_l2 | so_diverge | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_diverge is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_diverge_1 | so_diverge_l1  | N             | cp1                | CO                      |
      | ss_diverge_2 | so_diverge_l2  | N             | cp1                | CO                      |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp2                | cgt2                  | cs2                |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_diverge_2          |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_diverge_2 | so_diverge_l2  | N             | cp2                |
    When start picking job for sales order identified by so_diverge
    And scan picking slot identified by slot_diverge
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked |
      | product               | hu_1         | 10        |
      | product_2             | hu_product_2 | 10        |
    Then expect current picking job:
      | HasLuTarget | IsCarrierAdviseReadOnly |
      | Y           | N                       |
    And expect current picking job lines:
      | M_Product_ID | Carrier_Product_ID | IsCarrierAdviseManual |
      | product      | cp1                | N                     |
      | product_2    | cp2                | N                     |
    Then expect current picking job header carrier advise
      | available | readOnly |
      | true      | false    |

  @from:cucumber
  @Id:S0355_PickingDisplay_340
  Scenario: Carrier-advise picking display — no target yet, each line shows its OWN divergent carrier, read-only
    # Two order lines auto-advised to divergent carriers (product→Div Parcel 1, product_2→Div Parcel 2) BEFORE any
    # pick target is selected. Each line's advise button must display THAT line's own carrier product, read-only
    # (nothing to advise onto yet) — NOT the job-wide "divergent → no carrier" collapse, which is a header-only
    # display. The per-line display never depends on where the pick target is stored (line vs header), so it is
    # identical for PRODUCT and SALES_ORDER aggregation.
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier    | PickingSlot       | IsDynamic |
      | slot_div_notg | display_div_notgt | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID | Name         |
      | cp_div1    | nShift       | Div Parcel 1 |
      | cp_div2    | nShift       | Div Parcel 2 |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_div1            | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_div_nt  | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_div_nt_l1 | so_div_nt  | product      | 10         | product_TU_10CU         |
      | so_div_nt_l2 | so_div_nt  | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_div_nt is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_div_nt_1 | so_div_nt_l1   | N             | cp_div1            | CO                      |
      | ss_div_nt_2 | so_div_nt_l2   | N             | cp_div1            | CO                      |
    # Re-advise line 2 to a distinct carrier product → divergent per-line carriers.
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_div2            | cgt2                  | cs2                |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_div_nt_2           |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_div_nt_2 | so_div_nt_l2   | N             | cp_div2            |
    When start picking job for sales order identified by so_div_nt
    And scan picking slot identified by slot_div_notg
    # No pick target selected, nothing picked yet: each line still shows its OWN carrier, read-only.
    Then expect current picking job line carrier advise
      | target | M_Product_ID | available | readOnly | carrierProductCaption |
      | none   | product      | true      | true     | Div Parcel 1          |
      | none   | product_2    | true      | true     | Div Parcel 2          |

  @from:cucumber
  @Id:S0355_PickingDisplay_350
  Scenario: Carrier-advise picking display — ORDER header carrier follows the parcel: select LU shows the carrier read-only, a non-manual pick keeps it and makes it editable
    # One order, two lines sharing the SAME auto-advised (non-manual) carrier cp_ord_named. The header holds the
    # CURRENT top-level parcel's carrier, maintained by picking events: selecting a NEW LU re-inits the header from
    # the still-unprocessed lines (all-same → that carrier) and read-only (the new LU is not materialised yet, so
    # there is nothing to advise onto). Picking a NON-manual line materialises the LU and leaves the carrier as-is,
    # but the button becomes editable (a real parcel now exists to (re-)advise onto).
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier   | PickingSlot     | IsDynamic |
      | slot_ord_hdr | display_ord_hdr | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier    | M_Shipper_ID | Name           |
      | cp_ord_named  | nShift       | Ord Std Parcel |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_ord_named       | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ord_hdr | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_ord_hdr_l1 | so_ord_hdr | product      | 10         | product_TU_10CU         |
      | so_ord_hdr_l2 | so_ord_hdr | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_ord_hdr is completed
    # Wait for the order-completion auto-advise to land the SAME carrier on both schedules (advise Completed).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_ord_hdr_1    | so_ord_hdr_l1  | N             | cp_ord_named       | CO                      |
      | ss_ord_hdr_2    | so_ord_hdr_l2  | N             | cp_ord_named       | CO                      |
    When start picking job for sales order identified by so_ord_hdr
    And scan picking slot identified by slot_ord_hdr
    And set picking target as new LU identified by LU
    # New LU selected but not yet materialised (no pick onto it) → the header shows the carrier of the unprocessed
    # lines (all-same) but read-only.
    Then expect current picking job:
      | HasLuTarget | Carrier_Product_ID | IsCarrierAdviseReadOnly |
      | Y           | cp_ord_named       | N                       |
    And expect current picking job header carrier advise
      | available | readOnly | carrierProductCaption |
      | true      | true     | Ord Std Parcel        |
    # Pick a non-manual line → the LU materialises; the header carrier is UNCHANGED (only advise sets it), now editable.
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 10        |
    Then expect current picking job:
      | HasLuTarget | Carrier_Product_ID | IsCarrierAdviseReadOnly |
      | Y           | cp_ord_named       | N                       |
    And expect current picking job header carrier advise
      | available | readOnly | carrierProductCaption |
      | true      | false    | Ord Std Parcel        |

  @from:cucumber
  @Id:S0355_PickingDisplay_360
  Scenario: Carrier-advise picking display — ORDER header carrier: a MANUAL pick carries the manual carrier onto the header and makes it read-only
    # Same all-same setup as _350, but the (single) schedule is advised MANUALLY. Picking a manual line folds its
    # carrier onto the header AND flags the header read-only (a manual is a human override advise cannot converge).
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier       | PickingSlot         | IsDynamic |
      | slot_ord_hdr_man | display_ord_hdr_man | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier       | M_Shipper_ID | Name           |
      | cp_ord_man_named | nShift       | Ord Man Parcel |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_ord_man_named   | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ord_hdr_man | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID     | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_ord_hdr_man_l1 | so_ord_hdr_man | product      | 10         | product_TU_10CU         |
    When the order identified by so_ord_hdr_man is completed
    # Wait for the auto-advise to land (Completed) before overriding manually — a manual advise on a still-Requested
    # schedule is silently skipped.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID    | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_ord_hdr_man | so_ord_hdr_man_l1 | N             | cp_ord_man_named   | CO                      |
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_ord_hdr_man        | cp_ord_man_named   | cgt1                  | cs1                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID    | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_ord_hdr_man | so_ord_hdr_man_l1 | N             | cp_ord_man_named   | MAN                     |
    When start picking job for sales order identified by so_ord_hdr_man
    And scan picking slot identified by slot_ord_hdr_man
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 10        |
    # Manual pick → header carries the manual carrier and is read-only.
    Then expect current picking job:
      | HasLuTarget | Carrier_Product_ID | IsCarrierAdviseReadOnly |
      | Y           | cp_ord_man_named   | Y                       |
    And expect current picking job header carrier advise
      | available | readOnly | carrierProductCaption |
      | true      | true     | Ord Man Parcel        |

  @from:cucumber
  @Id:S0355_PickingDisplay_370
  Scenario: Carrier-advise picking display — PRODUCT aggregation, the line shows its OWN carrier (no target), read-only
    # PRODUCT (line-level) aggregation: the LINE is the parcel. Before any pick target exists, the per-line advise
    # display reads the LINE's own create-time carrier, read-only (nothing to advise onto yet) — identically to the
    # SALES_ORDER per-line no-target case (_340). The per-line read branches only on has-target, never on the
    # aggregation type, so this proves the PRODUCT (line-level) scope reads the line the same way.
    # (A single product line → a single PRODUCT-aggregation launcher, so the sales-order start step applies; a
    # multi-product PRODUCT order yields one launcher per product, which needs a product-scoped start step.)
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | PickingJobAggregationType |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | product                   |
    And metasfresh contains M_PickingSlot:
      | Identifier    | PickingSlot      | IsDynamic |
      | slot_prod_hdr | display_prod_hdr | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier     | M_Shipper_ID | Name        |
      | cp_prod_manual | nShift       | Prod Manual |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_prod_manual     | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_prod_hdr | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID  | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_prod_hdr_l1 | so_prod_hdr | product      | 10         | product_TU_10CU         |
    When the order identified by so_prod_hdr is completed
    # Wait for the auto-advise to land (Completed) before overriding manually — a manual advise on a still-Requested
    # schedule is silently skipped.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_prod_hdr_1 | so_prod_hdr_l1 | N             | cp_prod_manual     | CO                      |
    # Override the schedule manually → the line becomes manual/read-only.
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_prod_hdr_1         | cp_prod_manual     | cgt1                  | cs1                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_prod_hdr_1 | so_prod_hdr_l1 | N             | cp_prod_manual     | MAN                     |
    When start picking job for sales order identified by so_prod_hdr
    # No pick target yet → the line shows its OWN carrier, read-only (manual override).
    Then expect current picking job line carrier advise
      | target | M_Product_ID | available | readOnly | carrierProductCaption |
      | none   | product      | true      | true     | Prod Manual           |

  @from:cucumber
  @Id:S0355_PickingDisplay_380
  Scenario: Carrier-advise picking display — ORDER nested LU→TU: selecting a TU UNDER an existing LU does NOT reset the header carrier
    # The header tracks the TOP-LEVEL parcel (the LU). Selecting a TU nested under an existing LU pick target is a
    # sub-parcel action and must leave the header carrier state untouched (only a NEW top-level parcel re-inits it).
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier    | PickingSlot      | IsDynamic |
      | slot_nest_lutu | display_nest_lutu | Y         |
    And metasfresh contains Carrier_Products:
      | Identifier    | M_Shipper_ID | Name          |
      | cp_nest_named | nShift       | Nest Parcel   |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_nest_named      | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_nest    | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_nest_l1 | so_nest    | product      | 10         | product_TU_10CU         |
    When the order identified by so_nest is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Advising_Status |
      | ss_nest    | so_nest_l1     | N             | cp_nest_named      | CO                      |
    When start picking job for sales order identified by so_nest
    And scan picking slot identified by slot_nest_lutu
    # Select the top-level LU → header re-inits from the unprocessed line (cp_nest_named).
    And set picking target as new LU identified by LU
    Then expect current picking job:
      | HasLuTarget | Carrier_Product_ID | IsCarrierAdviseReadOnly |
      | Y           | cp_nest_named      | N                       |
    # Select a TU nested UNDER the existing LU → header carrier state is UNCHANGED (sub-parcel action).
    And set picking target as new TU identified by TU
    Then expect current picking job:
      | HasLuTarget | HasTuTarget | Carrier_Product_ID | IsCarrierAdviseReadOnly |
      | Y           | Y           | cp_nest_named      | N                       |

  @from:cucumber
  @Id:S0355_PickingReadvise_390
  Scenario: nShift Carrier Re-advise — mobile packing re-advise sets the carrier on the JOB and leaves the schedule UNCHANGED
    # The schedule is auto-advised to cp1 at order completion (advising status Completed). When the operator packs
    # and triggers the carrier advise from mobile picking, the packed HU is re-advised (advisor now returns cp2) and
    # the result is persisted ONLY onto the picking job (header + line) — the SHIPMENT SCHEDULE is NOT written (it is
    # the WebUI advise / shipment-carrier source, and each schedule write triggers expensive recomputes).
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot   | IsDynamic |
      | slot_readv | display_readv | Y         |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_readv   | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_readv_l1 | so_readv   | product      | 10         | product_TU_10CU         |
    When the order identified by so_readv is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_readv   | so_readv_l1    | N             |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_readv              |
    # Initial advise resolves to cp1
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | Carrier_Product_ID |
      | ss_readv   | so_readv_l1    | cp1                |
    When start picking job for sales order identified by so_readv
    And scan picking slot identified by slot_readv
    And set picking target as new TU identified by TU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product               | hu_1       | 10        |
    # The advisor now returns a different product; the mobile packing re-advise persists it onto the JOB only.
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp2                | cgt1                  | cs1                |
    And run carrier advise for the current picking job
    # The JOB header + line now carry the re-advised carrier cp2 …
    Then expect current picking job:
      | HasTuTarget | Carrier_Product_ID |
      | Y           | cp2                |
    And expect current picking job lines:
      | M_Product_ID | Carrier_Product_ID |
      | product      | cp2                |
    # … while the SHIPMENT SCHEDULE carrier is UNCHANGED (still cp1 — the mobile advise no longer writes the schedule).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | Carrier_Product_ID |
      | ss_readv   | so_readv_l1    | cp1                |

  @from:cucumber
  @Id:S0355_DeliveryOrder_400
  Scenario: nShift Delivery Order — one shipment whose HUs carry different carriers is split into two delivery orders
    # One nShift order, two lines on two different products, each packed into its own top-level HU (one package each).
    # Each schedule is advised to a DIFFERENT carrier product of the same shipper (cp1/cgt1 vs cp2/cgt2). The gateway
    # groups packages by carrier (ShipperGatewayFacade.createDeliveryOrderKey, one DeliveryOrderKey per M_Package
    # grouped by carrier), so the single shipment yields TWO Carrier_ShipmentOrders / TWO nShift createShipment calls,
    # one per carrier. Different carriers on DIFFERENT HUs is allowed by the consistency guard (it only rejects
    # different carriers on the SAME HU).
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # Stub the advisor so order-completion auto-advise resolves deterministically to cp1/cgt1 on BOTH lines.
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp1                | cgt1                  | cs1                |
    And metasfresh contains AD_Users:
      | Identifier      | Name                    | C_BPartner_ID | EMail                       | Phone            |
      | customerContact | nShift Customer Contact | customer      | contact@nshift-test.example | +41 79 123 45 67 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID      |
      | so_split   | true    | customer      | 2025-04-01  | wh             | nShift       | customerContact |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_split_l1 | so_split   | product      | 10         | product_TU_10CU         |
      | so_split_l2 | so_split   | product_2    | 10         | product_2_TU_10CU       |
    When the order identified by so_split is completed
    # Wait for the auto-advise to COMPLETE (both schedules resolve to the stubbed cp1/cgt1) BEFORE the manual
    # overrides — otherwise the async auto-advise races and overwrites the manual cp1/cp2 assigned below.
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_split_l1  | so_split_l1    | N             | cp1                | cgt1                  |
      | ss_split_l2  | so_split_l2    | N             | cp1                | cgt1                  |
    # Each line advised to its own carrier product of the same shipper.
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_split_l1           | cp1                | cgt1                  | cs1                |
      | nShift       | ss_split_l2           | cp2                | cgt2                  | cs2                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_split_l1 | so_split_l1    | N             | cp1                | cgt1                  |
      | ss_split_l2 | so_split_l2    | N             | cp2                | cgt2                  |
    # Both schedules generate into ONE shipment (one order, one shipper).
    # pack each line's CUs into its own TU per the order-line packing (real shipper-transportation flow) → 1 parcel/carrier.
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID    | M_InOut_ID  | IsOnTheFlyPickToPackingInstructions |
      | ss_split_l1, ss_split_l2 | inout_split | true                                |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID  | M_ShipperTransportation_ID |
      | inout_split | transpOrder_split          |
    # Two delivery orders: the gateway split the shipment's two packages by carrier.
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID  | Carrier_Product_ID |
      | cso_cp1    | inout_split | cp1                |
      | cso_cp2    | inout_split | cp2                |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_cp1                  | awb1 | trackingUrl1 | true        |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_cp2                  | awb1 | trackingUrl1 | true        |
    # 10 PCE / 10 PCE-per-TU => 1 parcel each; product weight 2.1×10=21, product_2 weight 1.1×10=11.
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName     | ArticleValue    | CustomsTariffNumber | QtyShipped | Price | TotalPrice | TotalWeightInKg |
      | cso_cp1                  | nShift Product  | nshift_product  | 12345678            | 10         | 10    | 100        | 21              |
      | cso_cp2                  | nShift Product2 | nshift_product2 | 12345678            | 10         | 8     | 80         | 11              |
    # Two createShipment calls, one per carrier; each request carries its own carrier product / goods type / parcel.
    And validate the captured nShift shipment requests:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp1                | cgt1                  | 1          |
      | cp2                | cgt2                  | 1          |

  @Id:S30591_TC1
  Scenario: C_Order — single goods-type allocation auto-sets Carrier_Goods_Type_ID
    # When a carrier product has exactly one allocated goods type, setting it on a draft order
    # must auto-populate Carrier_Goods_Type_ID via the C_Order interceptor.
    Given metasfresh contains Carrier_Product_GoodsType_Allocs:
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp1                | cgt1                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_tc1     | true    | customer      | 2025-04-01  | wh             | nShift       |
    When C_Order carrier product is set:
      | C_Order_ID | Carrier_Product_ID |
      | so_tc1     | cp1                |
    Then C_Order carrier goods type is:
      | C_Order_ID | Carrier_Goods_Type_ID |
      | so_tc1     | cgt1                  |

  @Id:S30591_TC2
  Scenario: C_Order — multiple goods-type allocations leave Carrier_Goods_Type_ID unset
    # When a carrier product has several allocated goods types, the interceptor must not
    # auto-set Carrier_Goods_Type_ID (the user picks from the val-rule-constrained list).
    Given metasfresh contains Carrier_Product_GoodsType_Allocs:
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp2                | cgt1                  |
      | cp2                | cgt2                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_tc2     | true    | customer      | 2025-04-01  | wh             | nShift       |
    When C_Order carrier product is set:
      | C_Order_ID | Carrier_Product_ID |
      | so_tc2     | cp2                |
    Then C_Order carrier goods type is:
      | C_Order_ID | Carrier_Goods_Type_ID |
      | so_tc2     | null                  |

  @Id:S30591_TC3
  Scenario: C_Order — changing Carrier_Product_ID clears Carrier_Goods_Type_ID and bridge rows
    # When Carrier_Product_ID is changed on a draft order, the interceptor must clear
    # Carrier_Goods_Type_ID and delete any C_Order_Carrier_Service rows.
    Given metasfresh contains Carrier_Product_GoodsType_Allocs:
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp1                | cgt1                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_tc3     | true    | customer      | 2025-04-01  | wh             | nShift       |
    # Pre-set cp1 → auto-sets cgt1
    And C_Order carrier product is set:
      | C_Order_ID | Carrier_Product_ID |
      | so_tc3     | cp1                |
    # Pre-create a bridge service row to verify it gets cleared
    And metasfresh contains C_Order_Carrier_Services:
      | C_Order_ID | Carrier_Service_ID |
      | so_tc3     | cs1                |
    # Now change to cp3 (no goods-type alloc for cp3 → Carrier_Goods_Type_ID stays unset)
    When C_Order carrier product is set:
      | C_Order_ID | Carrier_Product_ID |
      | so_tc3     | cp3                |
    Then C_Order carrier goods type is:
      | C_Order_ID | Carrier_Goods_Type_ID |
      | so_tc3     | null                  |
    And C_Order has no carrier services assigned:
      | C_Order_ID |
      | so_tc3     |

  @Id:S30591_TC4
  Scenario: M_ShipmentSchedule — order-header carrier product propagated to schedule as Manual; auto-advise leaves it unchanged
    # Goods type is always available for a carrier product. cp3 has a single goods-type allocation
    # so the interceptor auto-sets cgt1 on the order when the carrier product is selected.
    # Completing the order propagates carrier product + goods type to the shipment schedule with
    # status=Manual. A subsequent auto-advise must skip the schedule because Manual blocks
    # isEligibleForAutoEnqueue — so goods type stays on the schedule unchanged.
    Given metasfresh contains Carrier_Product_GoodsType_Allocs:
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | cp3                | cgt1                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_tc4     | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | so_tc4_l1  | so_tc4     | product      | 10         | product_TU_10CU         |
    # Single alloc for cp3 → interceptor auto-sets Carrier_Goods_Type_ID = cgt1
    When C_Order carrier product is set:
      | C_Order_ID | Carrier_Product_ID |
      | so_tc4     | cp3                |
    And metasfresh contains C_Order_Carrier_Services:
      | C_Order_ID | Carrier_Service_ID |
      | so_tc4     | cs1                |
      | so_tc4     | cs2                |
    When the order identified by so_tc4 is completed
    # Wait for recompute + assert propagation: cp3 + cgt1 (goods type always set) + Manual status
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Advising_Status |
      | ss_tc4     | so_tc4_l1      | N             | cp3                | cgt1                  | MAN                     |
    # Auto-advise must skip the schedule (Manual is ineligible for auto-enqueue);
    # goods type must remain cgt1 — auto-advise does not overwrite a Manual schedule.
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_tc4                |
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Advising_Status |
      | ss_tc4     | so_tc4_l1      | N             | cp3                | cgt1                  | MAN                     |

  Scenario: reset settings to default
    Given set sys config boolean value false for sys config de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
    And set sys config boolean value false for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value true for sys config de.metas.shipper.gateway.printLabels.enabled
    And set sys config boolean value false for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU