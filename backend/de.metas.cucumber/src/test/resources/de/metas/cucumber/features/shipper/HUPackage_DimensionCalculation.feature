@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29100_Dimension_Calculation
@ghActions:run_on_executor4
Feature: HU Package Dimension Calculation
  Verifies that the PackageDimensionCalcMethod on a TU PI version drives the correct M_Package
  dimensions for multi-product TUs (Strapping, Repacking, Nesting), that setting the method on an
  LU version is rejected, and that a non-self-packed single-product TU still uses product dims.

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    # nShift shipper with carrier mapping so the advisory + shipment flows can run
    And contains M_Shippers
      | Identifier   | Value                  | Name                   | OPT.ShipperGateway |
      | dimcalc_ship | dimcalc_nshift_shipper | DimCalc nShift Shipper | nshift             |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID |
      | dimcalc_ship |
    And metasfresh contains Carrier_Products:
      | Identifier   | M_Shipper_ID |
      | dimcalc_cp1  | dimcalc_ship |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier   | M_Shipper_ID |
      | dimcalc_cgt1 | dimcalc_ship |
    And the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | dimcalc_cp1        | dimcalc_cgt1          |
    And the nShift shipment service is stubbed to return a successful shipment creation response
    # Product A: L=30, W=20, H=10 — explicit Value/Name so reruns reuse the same product record
    # Product B: L=25, W=15, H=12
    # Both are IsSelfPacked=N — IsSelfPacked does not gate dimension contribution.
    And metasfresh contains M_Products:
      | Identifier      | Value                  | Name                   | WeightNet | WeightGross | LengthInCm | WidthInCm | HeightInCm | IsSelfPacked |
      | dimcalc_prod_a  | dimcalc_product_a      | DimCalc Product A      | 0.5 KGM   | 0.5 KGM     | 30         | 20        | 10         | N            |
      | dimcalc_prod_b  | dimcalc_product_b      | DimCalc Product B      | 0.3 KGM   | 0.3 KGM     | 25         | 15        | 12         | N            |
    And metasfresh contains M_PricingSystems
      | Identifier  |
      | dimcalc_ps  |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | dimcalc_pl  | dimcalc_ps         | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | dimcalc_plv | dimcalc_pl     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID |
      | dimcalc_plv            | dimcalc_prod_a  | 5.0      | PCE      |
      | dimcalc_plv            | dimcalc_prod_b  | 3.0      | PCE      |
    # BPartner with location — needed for M_Package C_BPartner_ID / C_BPartner_Location_ID
    And metasfresh contains C_BPartners without locations:
      | Identifier       | Value              | Name               | IsVendor | IsCustomer | M_PricingSystem_ID |
      | dimcalc_customer | dimcalc_customer   | DimCalc Customer   | N        | Y          | dimcalc_ps         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | C_BPartner_ID    | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 |
      | dimcalc_custLocation  | dimcalc_customer | CH           | Y               | Y               | 99001  | city | street 1 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | dimcalc_wh     |
    # TU PI with NO packing material item: getPackageDimensions skips the packing-material branch
    # and enters the product-based branch, enabling the calc-method dispatch.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID    |
      | dimcalc_TU_PI |
      | dimcalc_LU_PI |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID     | M_HU_PI_ID    | HU_UnitType | IsCurrent |
      | dimcalc_TU_Version     | dimcalc_TU_PI | TU          | Y         |
      | dimcalc_LU_Version     | dimcalc_LU_PI | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID    | M_HU_PI_Version_ID | Qty | ItemType |
      | dimcalc_TU_MI_Item | dimcalc_TU_Version |     | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID    | M_Product_ID    | Qty | ValidFrom  |
      | dimcalc_piip_a          | dimcalc_TU_MI_Item | dimcalc_prod_a  | 100 | 2021-01-01 |
      | dimcalc_piip_b          | dimcalc_TU_MI_Item | dimcalc_prod_b  | 100 | 2021-01-01 |
    And metasfresh contains AD_Users:
      | Identifier             | Name                    | C_BPartner_ID    | EMail                          |
      | dimcalc_custContact    | DimCalc Customer Contact | dimcalc_customer | dimcalc.contact@test.example  |

  @Id:S30361_TC1
  Scenario: Strapping mode — mixed TU dimensions are stacked along the smallest edge
    # Strapping: stacking axis = Σ(min_edge × qty); other two = max across products.
    # Product A (30×20×10): sorted [10,20,30], qty=3 → stacking += 10×3=30; mid=20; max=30
    # Product B (25×15×12): sorted [12,15,25], qty=2 → stacking += 12×2=24; mid=max(20,15)=20; max=max(30,25)=30
    # → LengthInCm=54, HeightInCm=20, WidthInCm=30
    Given metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID    | HU_UnitType | IsCurrent | PackageDimensionCalcMethod |
      | dimcalc_TU_Version | dimcalc_TU_PI | TU          | Y         | S                          |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID  | MovementDate | M_Warehouse_ID |
      | inv_strapping_a | 2022-12-12   | dimcalc_wh     |
    And metasfresh contains M_InventoriesLines:
      | Identifier      | M_Inventory_ID  | M_Product_ID   | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_strapping_l_a | inv_strapping_a | dimcalc_prod_a | 0       | 3        | PCE          |
      | inv_strapping_l_b | inv_strapping_a | dimcalc_prod_b | 0       | 2        | PCE          |
    When the inventory identified by inv_strapping_a is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID              |
      | inv_strapping_l_a   | cu_strapping_a       |
      | inv_strapping_l_b   | cu_strapping_b       |
    # Pack product A into a new TU, then move product B CU into the same TU → multi-product TU
    And transform CU to new TUs
      | sourceCU         | cuQty | M_HU_PI_Item_Product_ID | resultedNewTUs  |
      | cu_strapping_a   | 3     | dimcalc_piip_a          | tu_strapping    |
    And move CU to existing TU
      | sourceCU       | targetTU     | qty |
      | cu_strapping_b | tu_strapping | 2   |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID    | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID          |
      | so_strapping      | true    | dimcalc_customer | 2022-12-12  | dimcalc_wh     | dimcalc_ship | dimcalc_custContact |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID    | M_Product_ID   | QtyEntered |
      | so_strapping_l_a  | so_strapping  | dimcalc_prod_a | 3          |
      | so_strapping_l_b  | so_strapping  | dimcalc_prod_b | 2          |
    When the order identified by so_strapping is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID   | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_strapping_a | so_strapping_l_a | N             | dimcalc_cp1        | dimcalc_cgt1          |
      | ss_strapping_b | so_strapping_l_b | N             | dimcalc_cp1        | dimcalc_cgt1          |
    When create M_PickingCandidate for M_HU
      | M_HU_ID      | M_ShipmentSchedule_ID | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_strapping | ss_strapping_a        | 3         | IP     | P          | ?              |
      | tu_strapping | ss_strapping_b        | 2         | IP     | P          | ?              |
    And process picking
      | M_HU_ID      | M_ShipmentSchedule_ID            |
      | tu_strapping | ss_strapping_a, ss_strapping_b   |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID            | M_InOut_ID        |
      | ss_strapping_a, ss_strapping_b   | inout_strapping   |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID      | M_ShipperTransportation_ID |
      | inout_strapping | transpOrder_strapping      |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier        | M_InOut_ID      |
      | cso_strapping     | inout_strapping |
    # Strapping: L=54, H=20, W=30
    And validate M_Packages for shipment inout_strapping
      | LengthInCm | HeightInCm | WidthInCm |
      | 54         | 20         | 30        |

  @Id:S30361_TC2
  Scenario: Repacking mode — mixed TU dims derived from total volume
    # Repacking: V = Σ(L×W×H×qty) × 1.05; height=⅔×∛V; width=⅗×√(V/height); length=(V/height)/width
    # rawVolume = 30×20×10×3 + 25×15×12×2 = 18000+9000 = 27000; V = 28350
    # height = round(2/3 × ∛28350) = round(20.33) = 20
    # width  = round(3/5 × √(28350/20)) = round(22.59) = 23
    # length = round((28350/20)/23) = round(61.63) = 62
    Given metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID    | HU_UnitType | IsCurrent | PackageDimensionCalcMethod |
      | dimcalc_TU_Version | dimcalc_TU_PI | TU          | Y         | R                          |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID   | MovementDate | M_Warehouse_ID |
      | inv_repacking_a  | 2022-12-12   | dimcalc_wh     |
    And metasfresh contains M_InventoriesLines:
      | Identifier        | M_Inventory_ID  | M_Product_ID   | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_repacking_l_a | inv_repacking_a | dimcalc_prod_a | 0       | 3        | PCE          |
      | inv_repacking_l_b | inv_repacking_a | dimcalc_prod_b | 0       | 2        | PCE          |
    When the inventory identified by inv_repacking_a is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID              |
      | inv_repacking_l_a   | cu_repacking_a       |
      | inv_repacking_l_b   | cu_repacking_b       |
    And transform CU to new TUs
      | sourceCU         | cuQty | M_HU_PI_Item_Product_ID | resultedNewTUs |
      | cu_repacking_a   | 3     | dimcalc_piip_a          | tu_repacking   |
    And move CU to existing TU
      | sourceCU       | targetTU     | qty |
      | cu_repacking_b | tu_repacking | 2   |
    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID    | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID          |
      | so_repacking     | true    | dimcalc_customer | 2022-12-12  | dimcalc_wh     | dimcalc_ship | dimcalc_custContact |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID   | M_Product_ID   | QtyEntered |
      | so_repacking_l_a | so_repacking | dimcalc_prod_a | 3          |
      | so_repacking_l_b | so_repacking | dimcalc_prod_b | 2          |
    When the order identified by so_repacking is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID   | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_repacking_a | so_repacking_l_a | N             | dimcalc_cp1        | dimcalc_cgt1          |
      | ss_repacking_b | so_repacking_l_b | N             | dimcalc_cp1        | dimcalc_cgt1          |
    When create M_PickingCandidate for M_HU
      | M_HU_ID      | M_ShipmentSchedule_ID | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_repacking | ss_repacking_a        | 3         | IP     | P          | ?              |
      | tu_repacking | ss_repacking_b        | 2         | IP     | P          | ?              |
    And process picking
      | M_HU_ID      | M_ShipmentSchedule_ID             |
      | tu_repacking | ss_repacking_a, ss_repacking_b    |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID             | M_InOut_ID       |
      | ss_repacking_a, ss_repacking_b    | inout_repacking  |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID      | M_ShipperTransportation_ID |
      | inout_repacking | transpOrder_repacking      |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier      | M_InOut_ID      |
      | cso_repacking   | inout_repacking |
    # Repacking: L=62, H=20, W=23
    And validate M_Packages for shipment inout_repacking
      | LengthInCm | HeightInCm | WidthInCm |
      | 62         | 20         | 23        |

  @Id:S30361_TC3
  Scenario: Nesting mode — TU takes the dimensions of the item with the largest single edge
    # Nesting: winner = item whose max edge is greatest; dims returned verbatim (qty ignored).
    # Product A max edge=30, Product B max edge=25 → Product A wins → L=30, H=10, W=20
    Given metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID    | HU_UnitType | IsCurrent | PackageDimensionCalcMethod |
      | dimcalc_TU_Version | dimcalc_TU_PI | TU          | Y         | N                          |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_nesting_a  | 2022-12-12   | dimcalc_wh     |
    And metasfresh contains M_InventoriesLines:
      | Identifier      | M_Inventory_ID | M_Product_ID   | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_nesting_l_a | inv_nesting_a  | dimcalc_prod_a | 0       | 3        | PCE          |
      | inv_nesting_l_b | inv_nesting_a  | dimcalc_prod_b | 0       | 2        | PCE          |
    When the inventory identified by inv_nesting_a is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID          |
      | inv_nesting_l_a    | cu_nesting_a     |
      | inv_nesting_l_b    | cu_nesting_b     |
    And transform CU to new TUs
      | sourceCU       | cuQty | M_HU_PI_Item_Product_ID | resultedNewTUs |
      | cu_nesting_a   | 3     | dimcalc_piip_a          | tu_nesting     |
    And move CU to existing TU
      | sourceCU     | targetTU   | qty |
      | cu_nesting_b | tu_nesting | 2   |
    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID    | DateOrdered | M_Warehouse_ID | M_Shipper_ID | AD_User_ID          |
      | so_nesting     | true    | dimcalc_customer | 2022-12-12  | dimcalc_wh     | dimcalc_ship | dimcalc_custContact |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID | M_Product_ID   | QtyEntered |
      | so_nesting_l_a | so_nesting | dimcalc_prod_a | 3          |
      | so_nesting_l_b | so_nesting | dimcalc_prod_b | 2          |
    When the order identified by so_nesting is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute | Carrier_Product_ID | Carrier_Goods_Type_ID |
      | ss_nesting_a | so_nesting_l_a | N             | dimcalc_cp1        | dimcalc_cgt1          |
      | ss_nesting_b | so_nesting_l_b | N             | dimcalc_cp1        | dimcalc_cgt1          |
    When create M_PickingCandidate for M_HU
      | M_HU_ID    | M_ShipmentSchedule_ID | QtyPicked | Status | PickStatus | ApprovalStatus |
      | tu_nesting | ss_nesting_a          | 3         | IP     | P          | ?              |
      | tu_nesting | ss_nesting_b          | 2         | IP     | P          | ?              |
    And process picking
      | M_HU_ID    | M_ShipmentSchedule_ID        |
      | tu_nesting | ss_nesting_a, ss_nesting_b   |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID        | M_InOut_ID     |
      | ss_nesting_a, ss_nesting_b   | inout_nesting  |
    And after not more than 60s, Transportation Order is found for Shipment:
      | M_InOut_ID    | M_ShipperTransportation_ID |
      | inout_nesting | transpOrder_nesting        |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier    | M_InOut_ID    |
      | cso_nesting   | inout_nesting |
    # Nesting: Product A wins (max edge 30 > 25); dims returned verbatim: L=30, H=10, W=20
    And validate M_Packages for shipment inout_nesting
      | LengthInCm | HeightInCm | WidthInCm |
      | 30         | 10         | 20        |

  @Id:S30361_TC4
  Scenario: LU version guard — PackageDimensionCalcMethod is rejected on non-TU versions
    # The M_HU_PI_Version interceptor throws an AdempiereException when the calc method
    # is set on an LU version. Only TU versions may carry a PackageDimensionCalcMethod.
    Then metasfresh contains M_HU_PI_Version expecting error "M_HU_PI_Version_CalcMethodOnlyOnTU":
      | M_HU_PI_Version_ID | M_HU_PI_ID    | HU_UnitType | IsCurrent | PackageDimensionCalcMethod |
      | dimcalc_LU_Version | dimcalc_LU_PI | LU          | Y         | S                          |
