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
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 | Address2 | Attention      |
      | customerLocation | customer      | CH           | Y               | Y               | 12345  | city | street 1 | Floor 2  | Attention Test |
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
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID      |
      | so_do      | true    | customer      | 2025-04-01  | wh             | nShift       | customerContact |
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
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_do                   | awb1 | trackingUrl1 | true        |
    And validate Carrier_ShipmentOrder:
      | Carrier_ShipmentOrder_ID | Shipper_Name1 | Shipper_CountryISO2Code | Receiver_Name1  | Receiver_Name2         | Receiver_StreetName1 | Receiver_StreetName2 | Receiver_StreetNumber | Receiver_ZipCode | Receiver_City | Receiver_CountryISO2Code | Receiver_ContactName    | Receiver_Phone | Receiver_Email              |
      | cso_do                   | metasfresh AG | DE                      | nShift Customer | nShift Logistics Dept. | street               | Floor 2              | 1                     | 12345            | city          | CH                       | nShift Customer Contact | +41791234567   | contact@nshift-test.example |
    # 10 PCE / 10 PCE-per-TU => 1 parcel; total weight = product.GrossWeight (2.1) × qty (10) = 21 kg.
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName    | ArticleValue   | CustomsTariffNumber | QtyShipped | Price | TotalPrice | TotalWeightInKg |
      | cso_do                   | nShift Product | nshift_product | 12345678            | 10         | 10    | 100        | 21              |
    And validate the captured nShift advisor request:
      | SenderCompanyName | SenderCountryCode | ReceiverCompanyName | ReceiverCompanyName2   | ReceiverStreet | ReceiverAdditionalAddressInfo | ReceiverHouseNo | ReceiverZip | ReceiverCity | ReceiverCountryCode | ReceiverAttention | ReceiverContactName     | ReceiverContactPhone | ReceiverContactEmail        |
      | metasfresh AG     | DE                | nShift Customer     | nShift Logistics Dept. | street         | Floor 2                       | 1               | 12345       | city         | CH                  | Attention Test    | nShift Customer Contact | +41791234567         | contact@nshift-test.example |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | NumParcels | SenderCompanyName | SenderCountryCode | ReceiverCompanyName | ReceiverCompanyName2   | ReceiverStreet | ReceiverAdditionalAddressInfo | ReceiverHouseNo | ReceiverZip | ReceiverCity | ReceiverCountryCode | ReceiverAttention | ReceiverContactName     | ReceiverContactPhone | ReceiverContactEmail        |
      | cp1                | cgt1                  | cs1, cs2           | 1          | metasfresh AG     | DE                | nShift Customer     | nShift Logistics Dept. | street         | Floor 2                       | 1               | 12345       | city         | CH                  | Attention Test    | nShift Customer Contact | +41791234567         | contact@nshift-test.example |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 21            |
    And validate the captured nShift shipment request contents:
      | productName    | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product | 10              | 10        | 100        | 21              | 12345678      |

  @Id:S30196_TC1
  Scenario: nShift Delivery Order exported via Historical Shipments JSON includes parcel tracking
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    # Test env has no AD_Printer_Config for the system user; auto-print would otherwise fail the WP.
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | cp1                | cgt1                  | cs1                | cs2                 |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # The export process ANDs ExternalSystemCode ILIKE '%'; a shipment without an external system is excluded,
    # so the order must carry an AD_InputDataSource + External System.
    # Explicit Value+Name so reruns reuse the same AD_InputDataSource (upserts by Value) and don't
    # collide on the AD_InputDataSource_InternalName unique index.
    And metasfresh contains AD_InputDataSource:
      | Identifier     | Value      | Name       | InternalName |
      | dataSource_exp | nshift_exp | nshift_exp | nshift_exp   |
    And metasfresh contains External System
      | Name           | Value      |
      | nShiftExport   | nshift_exp |
    # Stable Name + EMail so reruns reuse the same AD_User and don't repoint it to a different bpartner —
    # the composite FK c_order(c_bpartner_id, ad_user_id) would otherwise block the update.
    And metasfresh contains AD_Users:
      | Identifier         | Name                    | OPT.C_BPartner_ID.Identifier | OPT.EMail                    | OPT.Phone        |
      | customerContact    | nShift Customer Contact | customer                     | contact@nshift-test.example  | +41 79 123 45 67 |
    And metasfresh contains C_Orders:
      | Identifier | REST.Context | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | OPT.AD_User_ID.Identifier | AD_InputDataSource_ID | ExternalSystem.Value |
      | so_exp     | order_exp_ID | true    | customer      | 2025-04-01  | wh             | nShift       | customerContact           | dataSource_exp        | nshift_exp           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_exp_l1  | so_exp     | product      | 10         |
    When the order identified by so_exp is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_exp     | so_exp_l1      | N             | cp1                | cgt1                  |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_exp                | inout_exp  |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_exp  | transpOrder_exp            |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_exp    | inout_exp  |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb | TrackingURL | HasPdfLabel |
      | cso_exp                  | awb | trackingUrl | true        |
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
        "TrackingNumber": "awb",
        "TrackingURL": "trackingUrl",
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

  @from:cucumber
  @allure.label.epic:E0355_Transport_Planning_Extralogistik
  @allure.label.feature:F00355
  @ghActions:run_on_executor7
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
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID | OPT.ExternalSystem.Value |
      | so_sl1     | true    | customer      | 2025-04-01  | wh             | nShift       | Shopware6                |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_sl1_l1  | so_sl1     | product      | 10         |
    When the order identified by so_sl1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_sl1     | so_sl1_l1      | N             | cp1                | cgt1                  |
    Then the last nShift ship advisor request had shipperConfig serviceLevel "EXPRESS"

  @from:cucumber
  @allure.label.epic:E0355_Transport_Planning_Extralogistik
  @allure.label.feature:F00355
  @ghActions:run_on_executor7
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

  @from:cucumber
  @allure.label.epic:E0355_Transport_Planning_Extralogistik
  @allure.label.feature:F00355
  @Id:S0355_DeliveryOrder_110
  Scenario: nShift Delivery Order - Country of Origin split into two parcels
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
    # Stock: 7 IT + 13 DE of product, 5 IT + 3 DE of product2, packing material
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_coo        | 2022-12-12   | wh_coo         |
    And metasfresh contains M_InventoriesLines:
      | Identifier    | M_Inventory_ID | M_Product_ID    | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID |
      | inv_coo_l_it  | inv_coo        | product         | 0       | 7        | PCE          | asi_IT                    |
      | inv_coo_l_de  | inv_coo        | product         | 0       | 13       | PCE          | asi_DE                    |
      | inv_coo_l_pm  | inv_coo        | packing_product | 0       | 100      | PCE          |                           |
      | inv_coo_l_it2 | inv_coo        | product2        | 0       | 5        | PCE          | asi_IT                    |
      | inv_coo_l_de2 | inv_coo        | product2        | 0       | 3        | PCE          | asi_DE                    |
    When the inventory identified by inv_coo is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | inv_coo_l_it       | hu_coo_it  |
      | inv_coo_l_de       | hu_coo_de  |
      | inv_coo_l_it2      | hu_coo_it2 |
      | inv_coo_l_de2      | hu_coo_de2 |
    And metasfresh contains AD_Users:
      | Identifier          | Name                     | C_BPartner_ID | EMail                        | Phone            |
      | customerContact_coo | nShift Customer Contact2 | customer      | contact2@nshift-test.example | +41 79 123 45 68 |
    # One order for product (20 PCE) and product2 (8 PCE) — the system picks from both IT and DE batches
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID    | AD_User_ID          |
      | so_coo     | true    | customer      | 2025-04-01  | wh_coo         | nShift_coo_test | customerContact_coo |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_coo_l1  | so_coo     | product      | 20         |
      | so_coo_l2  | so_coo     | product2     | 8          |
    And the order identified by so_coo is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_coo     | so_coo_l1      | N             | cp_coo1            | cgt_coo1              |
      | ss_coo2    | so_coo_l2      | N             | cp_coo1            | cgt_coo1              |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_coo, ss_coo2       | inout_coo  |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID | M_ShipperTransportation_ID |
      | inout_coo  | transpOrder_coo            |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso_coo    | inout_coo  |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_coo                  | awb1 | trackingUrl1 | true        |
      | cso_coo                  | awb2 | trackingUrl2 | true        |
      | cso_coo                  | awb3 | trackingUrl3 | true        |
      | cso_coo                  | awb4 | trackingUrl4 | true        |
    # Four items — one VHU per country per product (4 VHUs → 4 parcels in VHU-direct packing)
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName      | CountryOfOrigin | QtyShipped | Price | TotalPrice | TotalWeightInKg | CustomsTariffNumber |
      | cso_coo                  | nShift Product   | IT              | 7          | 10    | 70         | 14.7            | 12345678            |
      | cso_coo                  | nShift Product   | DE              | 13         | 10    | 130        | 27.3            | 12345678            |
      | cso_coo                  | nShift Product 2 | IT              | 5          | 5     | 25         | 6.0             | 12345678            |
      | cso_coo                  | nShift Product 2 | DE              | 3          | 5     | 15         | 3.6             | 12345678            |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp_coo1            | cgt_coo1              | 4          |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 14.7          |
      | 27.3          |
      | 6.0           |
      | 3.6           |
    And validate the captured nShift shipment request contents:
      | productName      | countryOfOrigin | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product   | IT              | 7               | 10        | 70         | 14.7            | 12345678      |
      | nShift Product   | DE              | 13              | 10        | 130        | 27.3            | 12345678      |
      | nShift Product 2 | IT              | 5               | 5         | 25         | 6.0             | 12345678      |
      | nShift Product 2 | DE              | 3               | 5         | 15         | 3.6             | 12345678      |


  @from:cucumber
  @allure.label.epic:E0355_Transport_Planning_Extralogistik
  @allure.label.feature:F00355
  @Id:S0355_DeliveryOrder_120
  Scenario: nShift Delivery Order - Country of Origin split, CU directly picked onto LU without TU
    Given set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    # Reuse test shipper from S0355_DeliveryOrder_110 (nShift_coo_test already idempotent)
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
    # Reuse wh_coo warehouse (created idempotently)
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_coo         |
    # LU PI that holds CUs directly (MI item type — no TU intermediate)
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   |
      | LU_cu_direct |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID   | M_HU_PI_ID   | HU_UnitType | IsCurrent |
      | LU_cu_direct_Version | LU_cu_direct | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID   | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItem_LU_cu  | LU_cu_direct_Version | 200 | MI       | pm                                     |
    And metasfresh contains M_Products:
      | Identifier | Value    | Name             | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product2   | product2 | nShift Product 2 | 1 KGM     | 1.2 KGM     | ct                 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | product2     | 5.0      | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_LU_cu_200CU     | huPiItem_LU_cu  | product      | 200 | 2021-01-01 |
      | product2_LU_cu_200CU    | huPiItem_LU_cu  | product2     | 200 | 2021-01-01 |
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
    # product: 80 IT + 120 DE = 200 PCE, each batch < LU capacity 200 → 1 LU per country
    # product2: 16 IT + 24 DE = 40 PCE, each batch < LU capacity 200 → 1 LU per country
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_coo_lu     | 2022-12-12   | wh_coo         |
    And metasfresh contains M_InventoriesLines:
      | Identifier       | M_Inventory_ID | M_Product_ID    | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID |
      | inv_coo_lu_l_it  | inv_coo_lu     | product         | 0       | 80       | PCE          | asi_IT_120                |
      | inv_coo_lu_l_de  | inv_coo_lu     | product         | 0       | 120      | PCE          | asi_DE_120                |
      | inv_coo_lu_l_pm  | inv_coo_lu     | packing_product | 0       | 100      | PCE          |                           |
      | inv_coo_lu_l_it2 | inv_coo_lu     | product2        | 0       | 16       | PCE          | asi_IT_120                |
      | inv_coo_lu_l_de2 | inv_coo_lu     | product2        | 0       | 24       | PCE          | asi_DE_120                |
    When the inventory identified by inv_coo_lu is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID       |
      | inv_coo_lu_l_it    | hu_coo_lu_it  |
      | inv_coo_lu_l_de    | hu_coo_lu_de  |
      | inv_coo_lu_l_it2   | hu_coo_lu_it2 |
      | inv_coo_lu_l_de2   | hu_coo_lu_de2 |
    And metasfresh contains AD_Users:
      | Identifier         | Name                     | C_BPartner_ID | EMail                        | Phone            |
      | customerContact_lu | nShift Customer Contact3 | customer      | contact3@nshift-test.example | +41 79 123 45 69 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID    | AD_User_ID         |
      | so_coo_lu  | true    | customer      | 2025-04-01  | wh_coo         | nShift_coo_test | customerContact_lu |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | so_coo_lu_l1 | so_coo_lu  | product      | 200        |
      | so_coo_lu_l2 | so_coo_lu  | product2     | 40         |
    When the order identified by so_coo_lu is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_coo_lu  | so_coo_lu_l1   | N             | cp_coo1            | cgt_coo1              |
      | ss_coo_lu2 | so_coo_lu_l2   | N             | cp_coo1            | cgt_coo1              |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID   |
      | ss_coo_lu, ss_coo_lu2 | inout_coo_lu |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID   | M_ShipperTransportation_ID |
      | inout_coo_lu | transpOrder_coo_lu         |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID   |
      | cso_coo_lu | inout_coo_lu |
    # LU1: 80 IT product → 1 parcel; LU2: 120 DE product → 1 parcel; LU3: 16 IT product2 → 1 parcel; LU4: 24 DE product2 → 1 parcel
    And validate Carrier_ShipmentOrder_Items:
      | Carrier_ShipmentOrder_ID | ProductName      | CountryOfOrigin | QtyShipped | Price | TotalPrice | TotalWeightInKg | CustomsTariffNumber |
      | cso_coo_lu               | nShift Product   | IT              | 80         | 10    | 800        | 168             | 12345678            |
      | cso_coo_lu               | nShift Product   | DE              | 120        | 10    | 1200       | 252             | 12345678            |
      | cso_coo_lu               | nShift Product 2 | IT              | 16         | 5     | 80         | 19.2            | 12345678            |
      | cso_coo_lu               | nShift Product 2 | DE              | 24         | 5     | 120        | 28.8            | 12345678            |
    And validate the captured nShift shipment request:
      | Carrier_Product_ID | Carrier_Goods_Type_ID | NumParcels |
      | cp_coo1            | cgt_coo1              | 4          |
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb  | TrackingURL  | HasPdfLabel |
      | cso_coo_lu               | awb1 | trackingUrl1 | true        |
      | cso_coo_lu               | awb2 | trackingUrl2 | true        |
      | cso_coo_lu               | awb3 | trackingUrl3 | true        |
      | cso_coo_lu               | awb4 | trackingUrl4 | true        |
    And validate the captured nShift shipment request parcels:
      | grossWeightKg |
      | 168           |
      | 252           |
      | 19.2          |
      | 28.8          |
    And validate the captured nShift shipment request contents:
      | productName      | countryOfOrigin | shippedQuantity | unitPrice | totalValue | totalWeightInKg | customsTariff |
      | nShift Product   | IT              | 80              | 10        | 800        | 168             | 12345678      |
      | nShift Product   | DE              | 120             | 10        | 1200       | 252             | 12345678      |
      | nShift Product 2 | IT              | 16              | 5         | 80         | 19.2            | 12345678      |
      | nShift Product 2 | DE              | 24              | 5         | 120        | 28.8            | 12345678      |

  Scenario: reset settings to default
    Given set sys config boolean value false for sys config de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
    And set sys config boolean value false for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value true for sys config de.metas.shipper.gateway.printLabels.enabled