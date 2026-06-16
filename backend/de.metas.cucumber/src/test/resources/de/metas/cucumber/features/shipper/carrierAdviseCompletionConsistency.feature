@from:cucumber
@allure.label.epic:E0355_Transport_Planning_Extralogistik
@allure.label.feature:F00355
@ghActions:run_on_executor4
Feature: Carrier-advise consistency guard on picking-job completion
## F00355: Shipper
##
## When a picker completes a picking job, the system validates that all shipment schedules packed
## into each top-level HU carry consistent carrier-advise configuration. The job is blocked if:
##   (E1) Manual advise is mixed with automatic, or multiple manual tuples differ on the same HU.
##   (E2) All-automatic schedules have divergent carrier products or goods types on the same HU.
##   (E3) Schedules on the same HU belong to more than one advise-enabled shipper.
## For E2, re-advising the divergent schedules unifies them and lets the job complete.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-04-01T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    And load M_Shipper:
      | Identifier | Name   |
      | nShift     | nShift |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID |
      | nShift       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_guard       |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_guard   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_guard   | ps_guard           | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_guard  | pl_guard       |
    And metasfresh contains M_Products:
      | Identifier | Value         | Name          | WeightNet | WeightGross |
      | product_A  | guard_prod_A  | Guard Prod A  | 1 KGM     | 1.1 KGM     |
      | product_B  | guard_prod_B  | Guard Prod B  | 2 KGM     | 2.2 KGM     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_guard              | product_A    | 5.0      | PCE      |
      | plv_guard              | product_B    | 8.0      | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier       | Name         | IsVendor | IsCustomer | M_PricingSystem_ID |
      | guard_customer   | Guard Cust   | N        | Y          | ps_guard           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | C_BPartner_ID  | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City        | Address1      |
      | guard_customerLocation  | guard_customer | CH           | Y               | Y               | 12345  | GuardCity   | Guard Street  |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   |
      | guard_TU     |
      | guard_LU     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID   | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | guard_TU_v           | guard_TU   | TU          | Y         |
      | guard_LU_v           | guard_LU   | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID      | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID |
      | guard_TU_MI          | guard_TU_v         |     | MI       |                        |
      | guard_LU_HU          | guard_LU_v         | 10  | HU       | guard_TU               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | guard_PIIP_A            | guard_TU_MI     | product_A    | 10  | 2000-01-01 |
      | guard_PIIP_B            | guard_TU_MI     | product_B    | 10  | 2000-01-01 |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID |
      | cp_g1      | nShift       |
      | cp_g2      | nShift       |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID |
      | cgt_g1     | nShift       |
      | cgt_g2     | nShift       |
    And metasfresh contains Carrier_Services:
      | Identifier | M_Shipper_ID |
      | cs_g1      | nShift       |
      | cs_g2      | nShift       |
    And metasfresh contains M_PickingSlot:
      | Identifier    | PickingSlot | IsDynamic |
      | slot_guard    | guard_1     | Y         |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    # Put stock for both products into the warehouse
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_guard      | 2025-04-01   | wh_guard       |
    And metasfresh contains M_InventoriesLines:
      | Identifier    | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_HU_PI_Item_Product_ID |
      | inv_guard_lA  | inv_guard      | product_A    | 0       | 100      | PCE          | guard_PIIP_A            |
      | inv_guard_lB  | inv_guard      | product_B    | 0       | 100      | PCE          | guard_PIIP_B            |
    When the inventory identified by inv_guard is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID    |
      | inv_guard_lA       | cu_guard_A |
      | inv_guard_lB       | cu_guard_B |

  @from:cucumber
  @Id:S0355_DeliveryOrder_200
  Scenario: Picking job completion is blocked when a packed LU carries manual and automatic carrier advise (E1 — mixed manual/non-manual)
    # Two order lines on one nShift order. Schedule A is manually advised with cp_g1/cgt_g1.
    # Schedule B stays automatically advised (status Completed via auto-advise) with cp_g1/cgt_g1.
    # Mixed manual + automatic on the same LU → E1 guard rejects completion.
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_g1              | cgt_g1                | cs_g1              |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID  | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_e1       | true    | guard_customer | 2025-04-01  | wh_guard       | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | so_e1_lA    | so_e1      | product_A    | 10         |
      | so_e1_lB    | so_e1      | product_B    | 10         |
    When the order identified by so_e1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute |
      | ss_e1_A     | so_e1_lA       | N             |
      | ss_e1_B     | so_e1_lB       | N             |
    # Auto-advise schedule B → Completed status (non-manual)
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_e1_B               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_e1_B    | so_e1_lB       | N             | cp_g1              |
    # Manually advise schedule A → Manual status with cp_g2/cgt_g2 (different from B's auto-advise result)
    And Process M_ShipmentSchedule_Advise_Manual is run
      | M_Shipper_ID | M_ShipmentSchedule_ID | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | nShift       | ss_e1_A               | cp_g2              | cgt_g2                | cs_g2              |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_e1_A    | so_e1_lA       | N             | cp_g2              |
    # Pick both schedules into the same LU so the guard sees mixed manual/automatic on one HU
    When start picking job for sales order identified by so_e1
    And scan picking slot identified by slot_guard
    And set picking target as new LU identified by guard_LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product_A             | cu_guard_A | 10        |
      | product_B             | cu_guard_B | 10        |
    Then completing the picking job is rejected with AD_Message "de.metas.picking.CarrierAdvise_ManualInconsistentOnHU"

  @from:cucumber
  @Id:S0355_DeliveryOrder_210
  Scenario: Picking job completion is blocked when a packed LU carries divergent non-manual carrier products (E2), then succeeds after re-advise
    # Two order lines on one nShift order. Both schedules are non-manual (NotRequested is non-manual).
    # Schedule A is auto-advised → cp_g1. Schedule B is not yet advised → carrier product null.
    # Divergent non-manual products (cp_g1 vs null) on the same LU → E2 guard rejects completion.
    # After advising schedule B the products converge → completion succeeds.
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_g1              | cgt_g1                | cs_g1              |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID  | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_e2       | true    | guard_customer | 2025-04-01  | wh_guard       | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | so_e2_lA    | so_e2      | product_A    | 10         |
      | so_e2_lB    | so_e2      | product_B    | 10         |
    When the order identified by so_e2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute |
      | ss_e2_A     | so_e2_lA       | N             |
      | ss_e2_B     | so_e2_lB       | N             |
    # Advise only schedule A — schedule B stays NotRequested (non-manual, null carrier product)
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_e2_A               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_e2_A    | so_e2_lA       | N             | cp_g1              |
    # Pick both schedules into the same LU — ss_e2_A has cp_g1, ss_e2_B has null carrier product
    When start picking job for sales order identified by so_e2
    And scan picking slot identified by slot_guard
    And set picking target as new LU identified by guard_LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product_A             | cu_guard_A | 10        |
      | product_B             | cu_guard_B | 10        |
    # E2: divergent non-manual carrier products on the same HU
    Then completing the picking job is rejected with AD_Message "de.metas.picking.CarrierAdvise_NonManualDivergentOnHU"
    # Re-advise schedule B — now both schedules have cp_g1 → consistent
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_e2_B               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_e2_B    | so_e2_lB       | N             | cp_g1              |
    # After re-advise the guard passes and the job completes
    When complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_e2_A               | inout_e2   | CO        |

  @from:cucumber
  @Id:S0355_DeliveryOrder_220
  Scenario: Picking job completion is blocked when a packed LU spans more than one advise-enabled shipper (E3)
    # Two order lines on one nShift order. After the order is completed, one schedule's shipper is
    # updated to a second advise-enabled shipper (nShift2). Both shippers have IsApiCarrierAdvise=Y.
    # Picking both lines into the same LU → E3 guard rejects completion.
    Given contains M_Shippers
      | Identifier | Value   | Name    | OPT.ShipperGateway | OPT.IsApiCarrierAdvise |
      | nShift2    | nshift2 | nShift2 | nshift             | Y                      |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_g1              | cgt_g1                | cs_g1              |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID  | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_e3       | true    | guard_customer | 2025-04-01  | wh_guard       | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | so_e3_lA    | so_e3      | product_A    | 10         |
      | so_e3_lB    | so_e3      | product_B    | 10         |
    When the order identified by so_e3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute |
      | ss_e3_A     | so_e3_lA       | N             |
      | ss_e3_B     | so_e3_lB       | N             |
    # Move schedule B's shipper to nShift2 (also IsApiCarrierAdvise=Y) — now two different shippers on the HU
    And update shipment schedules
      | Identifier | M_Shipper_ID |
      | ss_e3_B    | nShift2      |
    # Pick both lines into the same LU
    When start picking job for sales order identified by so_e3
    And scan picking slot identified by slot_guard
    And set picking target as new LU identified by guard_LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product_A             | cu_guard_A | 10        |
      | product_B             | cu_guard_B | 10        |
    Then completing the picking job is rejected with AD_Message "de.metas.picking.CarrierAdvise_MultipleShippersOnHU"

  @from:cucumber
  @Id:S0355_DeliveryOrder_230
  Scenario: Picking job completes normally when all packed schedules share one consistent carrier advise (OK case)
    # Two order lines on one nShift order. Both are auto-advised and receive the same cp_g1/cgt_g1.
    # The guard sees consistent non-manual advise on the single LU → completion passes.
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID |
      | cp_g1              | cgt_g1                | cs_g1              |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID  | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so_ok       | true    | guard_customer | 2025-04-01  | wh_guard       | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID | QtyEntered |
      | so_ok_lA    | so_ok      | product_A    | 10         |
      | so_ok_lB    | so_ok      | product_B    | 10         |
    When the order identified by so_ok is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute |
      | ss_ok_A     | so_ok_lA       | N             |
      | ss_ok_B     | so_ok_lB       | N             |
    # Auto-advise both schedules — both get cp_g1/cgt_g1 from the stub
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_ok_A               |
    And Process M_ShipmentSchedule_Advise is run
      | M_ShipmentSchedule_ID |
      | ss_ok_B               |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID |
      | ss_ok_A    | so_ok_lA       | N             | cp_g1              |
      | ss_ok_B    | so_ok_lB       | N             | cp_g1              |
    # Pick both schedules into the same LU — consistent non-manual advise on both
    When start picking job for sales order identified by so_ok
    And scan picking slot identified by slot_guard
    And set picking target as new LU identified by guard_LU
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | product_A             | cu_guard_A | 10        |
      | product_B             | cu_guard_B | 10        |
    When complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_ok_A               | inout_ok   | CO        |
