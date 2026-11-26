@from:cucumber
@allure.label.epic:E0110_Shipping
@allure.label.feature:F17011_Shipment_Line_Attribute_Propagation_ASI
@ghActions:run_on_executor3
Feature: Shipment line per TU content
  A TU that holds goods with different attribute values (e.g. different countries of origin) must ship as
  one shipment line per attribute combination, also when the quantities are equal.

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_tu          |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic | M_Warehouse_ID |
      | slot_tu    | 710         | Y         | wh_tu          |
    And metasfresh contains M_Products:
      | Identifier |
      | p_coo      |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_tu      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_tu      | ps_tu              | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_tu     | pl_tu          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_tu                 | p_coo        | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_tu | N        | Y          | ps_tu              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 |
      | loc_tu     | customer_tu   | CH           | Y               | Y               | 12345  | city | street 1 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | tu_pi      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | tu_pi_Version      | tu_pi      | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType |
      | tu_pi_item      | tu_pi_Version      |     | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | p_coo_tu_10CU           | tu_pi_item      | p_coo        | 10  | 2021-01-01 |
    # The lot number is a shipment-line aggregation attribute on the template PI (restored after the scenario).
    And update M_HU_PI_Attribute UseInASI:
      | M_HU_PI_Version_ID | M_Attribute.Value | UseInASI |
      | 100                | Lot-Nummer        | Y        |
    And M_ShipmentSchedule_AttributeConfig is active for M_Attribute.Value Lot-Nummer
    And M_ShipmentSchedule_AttributeConfig is active for M_Attribute.Value 1000001

  @Id:S34165_10
  Scenario: Equal-quantity picks of two origins into one TU ship as two lines
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_tu":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_tu":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_tu         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_it   | inv_tu         | p_coo        | 0       | 10       | PCE          | asi_IT_tu                 | p_coo_tu_10CU           |
      | inv_l_de   | inv_tu         | p_coo        | 0       | 10       | PCE          | asi_DE_tu                 | p_coo_tu_10CU           |
    When the inventory identified by inv_tu is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_it           | hu_it   |
      | inv_l_de           | hu_de   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_tu      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_tu_l1   | so_tu      | p_coo        | 4          |
    When the order identified by so_tu is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_tu      | so_tu_l1       | N             |
    # Two equal-quantity picks of different origins into the same new TU
    When start picking job for sales order identified by so_tu
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_it      | 2         |
      | p_coo                 | hu_de      | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_tu                 | inout_tu   | CO        |
    Then the shipment lines of inout_tu are exactly, in any order:
      | M_Product_ID | MovementQty | attr.1000001 |
      | p_coo        | 2           | IT           |
      | p_coo        | 2           | DE           |

  @Id:S34165_80
  Scenario: Equal-quantity picks of two origins into one TU that sits on a pallet ship as two lines
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_Products:
      | Identifier   |
      | pm_product80 |
    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID |
      | pm80       | pm_product80 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | lu80_pi    |
      | tu80_pi    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | lu80_pi_Version    | lu80_pi    | LU          | Y         |
      | tu80_pi_Version    | tu80_pi    | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID | OPT.M_HU_PackingMaterial_ID.Identifier |
      | lu80_pi_item    | lu80_pi_Version    | 10  | HU       | tu80_pi               | pm80                                   |
      | tu80_pi_item    | tu80_pi_Version    |     | MI       |                       |                                        |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | p_coo_tu80_10CU         | tu80_pi_item    | p_coo        | 10  | 2021-01-01 |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_80":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_80":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_80         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_it80 | inv_80         | p_coo        | 0       | 10       | PCE          | asi_IT_80                 | p_coo_tu80_10CU         |
      | inv_l_de80 | inv_80         | p_coo        | 0       | 10       | PCE          | asi_DE_80                 | p_coo_tu80_10CU         |
    When the inventory identified by inv_80 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_it80         | hu_it80 |
      | inv_l_de80         | hu_de80 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_80      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_80_l1   | so_80      | p_coo        | 4          |
    When the order identified by so_80 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_80      | so_80_l1       | N             |
    # Two equal-quantity picks of different origins into the same new TU (the TU's packing instruction has a parent pallet)
    When start picking job for sales order identified by so_80
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu80_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_it80    | 2         |
      | p_coo                 | hu_de80    | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_80                 | inout_80   | CO        |
    Then the shipment lines of inout_80 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.1000001 |
      | p_coo        | 2           | IT           |
      | p_coo        | 2           | DE           |

  @Id:S34165_91
  Scenario: One TU with two CUs of unequal quantities per origin, origins picked interleaved, ships as one line per origin with the summed quantity
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_90":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_90":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_FR_90":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "FR"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_90         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_it1  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_IT_90                 | p_coo_tu_10CU           |
      | inv_l_it2  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_IT_90                 | p_coo_tu_10CU           |
      | inv_l_de1  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_DE_90                 | p_coo_tu_10CU           |
      | inv_l_de2  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_DE_90                 | p_coo_tu_10CU           |
      | inv_l_fr1  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_FR_90                 | p_coo_tu_10CU           |
      | inv_l_fr2  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_FR_90                 | p_coo_tu_10CU           |
    When the inventory identified by inv_90 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_it1          | hu_it1  |
      | inv_l_it2          | hu_it2  |
      | inv_l_de1          | hu_de1  |
      | inv_l_de2          | hu_de2  |
      | inv_l_fr1          | hu_fr1  |
      | inv_l_fr2          | hu_fr2  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_90      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_90_l1   | so_90      | p_coo        | 14         |
    When the order identified by so_90 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_90      | so_90_l1       | N             |
    # Six picks into the same new TU: two CUs per origin, unequal quantities, origins interleaved
    When start picking job for sales order identified by so_90
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_it1     | 2         |
      | p_coo                 | hu_de1     | 1         |
      | p_coo                 | hu_fr1     | 2         |
      | p_coo                 | hu_it2     | 3         |
      | p_coo                 | hu_de2     | 4         |
      | p_coo                 | hu_fr2     | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_90                 | inout_90   | CO        |
    Then the shipment lines of inout_90 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.1000001 |
      | p_coo        | 5           | IT           |
      | p_coo        | 5           | DE           |
      | p_coo        | 4           | FR           |

  @Id:S34165_90
  Scenario: One TU with four equal-quantity CUs of three lots, two CUs of the same lot from one stock HU, ships as one line per lot
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lotA_3lot":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOTA"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lotB_3lot":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOTB"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lotC_3lot":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOTC"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_3lot       | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier   | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_a_3lot | inv_3lot       | p_coo        | 0       | 10       | PCE          | asi_lotA_3lot             | p_coo_tu_10CU           |
      | inv_l_b_3lot | inv_3lot       | p_coo        | 0       | 10       | PCE          | asi_lotB_3lot             | p_coo_tu_10CU           |
      | inv_l_c_3lot | inv_3lot       | p_coo        | 0       | 10       | PCE          | asi_lotC_3lot             | p_coo_tu_10CU           |
    When the inventory identified by inv_3lot is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID   |
      | inv_l_a_3lot       | hu_a_3lot |
      | inv_l_b_3lot       | hu_b_3lot |
      | inv_l_c_3lot       | hu_c_3lot |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_3lot    | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_3lot_l1 | so_3lot    | p_coo        | 8          |
    When the order identified by so_3lot is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_3lot    | so_3lot_l1     | N             |
    # Four picks of 2 into the same new TU: lot A twice from the same stock HU, then lot B, then lot C
    When start picking job for sales order identified by so_3lot
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_a_3lot  | 2         |
      | p_coo                 | hu_a_3lot  | 2         |
      | p_coo                 | hu_b_3lot  | 2         |
      | p_coo                 | hu_c_3lot  | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_3lot               | inout_3lot | CO        |
    Then the shipment lines of inout_3lot are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 4           | LOTA            |
      | p_coo        | 2           | LOTB            |
      | p_coo        | 2           | LOTC            |

  @Id:S34165_95
  Scenario: One TU with three CUs of three lots ships as three product lines and one packing material line for the one TU
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_Products:
      | Identifier   |
      | pm_product95 |
    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID |
      | pm95       | pm_product95 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | tu95_pi    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | tu95_pi_Version    | tu95_pi    | TU          | Y         |
    # the TU is made of one packing material (the TU itself) and holds the goods
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | tu95_pi_item_mi | tu95_pi_Version    |     | MI       |                                        |
      | tu95_pi_item_pm | tu95_pi_Version    | 0   | PM       | pm95                                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | p_coo_tu95_10CU         | tu95_pi_item_mi | p_coo        | 10  | 2021-01-01 |
    # moving packing material needs an empties warehouse for the shipping warehouse: add one to the shipped empties network
    And load DD_NetworkDistribution:
      | DD_NetworkDistribution_ID | Value   |
      | emptiesNetwork            | Gebinde |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_empties95   |
    And contains M_Shippers
      | Identifier |
      | shipper95  |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | emptiesNetwork            | wh_empties95   | wh_tu                | shipper95    |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lot1_95":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOT1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lot2_95":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOT2"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lot3_95":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOT3"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_95         | 2022-12-12   | wh_tu          |
    # the goods are inventoried in plain CUs-in-TU without packing material; only the picking target TU carries a packing material
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l1   | inv_95         | p_coo        | 0       | 10       | PCE          | asi_lot1_95               | p_coo_tu_10CU           |
      | inv_l_l2   | inv_95         | p_coo        | 0       | 10       | PCE          | asi_lot2_95               | p_coo_tu_10CU           |
      | inv_l_l3   | inv_95         | p_coo        | 0       | 10       | PCE          | asi_lot3_95               | p_coo_tu_10CU           |
    When the inventory identified by inv_95 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l1           | hu_l1   |
      | inv_l_l2           | hu_l2   |
      | inv_l_l3           | hu_l3   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_95      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_95_l1   | so_95      | p_coo        | 6          |
    When the order identified by so_95 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_95      | so_95_l1       | N             |
    # Three equal-quantity picks of three lots into ONE new TU (one TU, not three)
    When start picking job for sales order identified by so_95
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu95_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l1      | 2         |
      | p_coo                 | hu_l2      | 2         |
      | p_coo                 | hu_l3      | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_95                 | inout_95   | CO        |
    # three product lines, one per lot, and exactly ONE packing material line with quantity 1 (one TU)
    Then the shipment lines of inout_95 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 2           | LOT1            |
      | p_coo        | 2           | LOT2            |
      | p_coo        | 2           | LOT3            |
      | pm_product95 | 1           |                 |

  @Id:S34165_96
  Scenario: Reversing the shipment of a TU with two CUs of one lot and one CU each of two other lots reopens the whole picked quantity
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_AND_COMPLETE  | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lotA_96":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOTA"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lotB_96":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOTB"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_lotC_96":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "LOTC"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_96         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_a96  | inv_96         | p_coo        | 0       | 10       | PCE          | asi_lotA_96               | p_coo_tu_10CU           |
      | inv_l_b96  | inv_96         | p_coo        | 0       | 10       | PCE          | asi_lotB_96               | p_coo_tu_10CU           |
      | inv_l_c96  | inv_96         | p_coo        | 0       | 10       | PCE          | asi_lotC_96               | p_coo_tu_10CU           |
    When the inventory identified by inv_96 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_a96          | hu_a96  |
      | inv_l_b96          | hu_b96  |
      | inv_l_c96          | hu_c96  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_96      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_96_l1   | so_96      | p_coo        | 8          |
    When the order identified by so_96 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_96      | so_96_l1       | N             |
    When start picking job for sales order identified by so_96
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_a96     | 2         |
      | p_coo                 | hu_a96     | 2         |
      | p_coo                 | hu_b96     | 2         |
      | p_coo                 | hu_c96     | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_96                 | inout_96_1 | CO        |
    And the shipment lines of inout_96_1 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 4           | LOTA            |
      | p_coo        | 2           | LOTB            |
      | p_coo        | 2           | LOTC            |
    And all M_ShipmentSchedule_QtyPicked rows of M_ShipmentSchedule identified by ss_96 sum to 8
    # The reversal must give back every picked CU, also the two CUs of lot A that share one shipment line
    When the shipment identified by inout_96_1 is reversed as inout_96_1_reversal
    Then open M_ShipmentSchedule_QtyPicked rows of M_ShipmentSchedule identified by ss_96 sum to 8
    # The reversal flags the schedule for recompute: generate shipments only after its quantities are settled
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_96      | so_96_l1       | N             |
    # The reopened picks ship again with the right lots
    When 'generate shipments' process is invoked with QuantityType=P, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_96                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_96                 | inout_96_2 | CO        |
    Then the shipment lines of inout_96_2 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 4           | LOTA            |
      | p_coo        | 2           | LOTB            |
      | p_coo        | 2           | LOTC            |

  @Id:S34165_20
  Scenario: Two CUs of the same lot but different origins in one TU ship as one line per origin
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_L1_DE_20":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}, {"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_L1_IT_20":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}, {"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_20         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_de20 | inv_20         | p_coo        | 0       | 10       | PCE          | asi_L1_DE_20              | p_coo_tu_10CU           |
      | inv_l_it20 | inv_20         | p_coo        | 0       | 10       | PCE          | asi_L1_IT_20              | p_coo_tu_10CU           |
    When the inventory identified by inv_20 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_de20         | hu_de20 |
      | inv_l_it20         | hu_it20 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_20      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_20_l1   | so_20      | p_coo        | 4          |
    When the order identified by so_20 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_20      | so_20_l1       | N             |
    When start picking job for sales order identified by so_20
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_de20    | 2         |
      | p_coo                 | hu_it20    | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_20                 | inout_20   | CO        |
    Then the shipment lines of inout_20 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer | attr.1000001 |
      | p_coo        | 2           | L1              | DE           |
      | p_coo        | 2           | L1              | IT           |

  @Id:S34165_25
  Scenario: One TU shared by two order lines of one product with equal quantities ships each order line only the CUs picked for it
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_L1_25":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_L2_25":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L2"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_25         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l125 | inv_25         | p_coo        | 0       | 10       | PCE          | asi_L1_25                 | p_coo_tu_10CU           |
      | inv_l_l225 | inv_25         | p_coo        | 0       | 10       | PCE          | asi_L2_25                 | p_coo_tu_10CU           |
    When the inventory identified by inv_25 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l125         | hu_l125 |
      | inv_l_l225         | hu_l225 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_25      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_25_lA   | so_25      | p_coo        | 2          |
      | so_25_lB   | so_25      | p_coo        | 2          |
    When the order identified by so_25 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_25_A    | so_25_lA       | N             |
      | ss_25_B    | so_25_lB       | N             |
    # One new TU for the whole job: the picks for both order lines land in the same TU
    When start picking job for sales order identified by so_25
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines by order line
      | C_OrderLine_ID | PickFromHU | QtyPicked |
      | so_25_lA       | hu_l125    | 2         |
      | so_25_lB       | hu_l225    | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_25_A               | inout_25   | CO        |
    Then the shipment lines of inout_25 are exactly, in any order:
      | M_Product_ID | MovementQty | C_OrderLine_ID | attr.Lot-Nummer |
      | p_coo        | 2           | so_25_lA       | L1              |
      | p_coo        | 2           | so_25_lB       | L2              |

  @Id:S34165_30
  Scenario: A TU with three lots ships as one line when the lot number is not a line aggregation attribute
    Given M_ShipmentSchedule_AttributeConfig is inactive for M_Attribute.Value Lot-Nummer
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l1_30":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l2_30":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L2"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l3_30":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L3"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_30         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l130 | inv_30         | p_coo        | 0       | 10       | PCE          | asi_l1_30                 | p_coo_tu_10CU           |
      | inv_l_l230 | inv_30         | p_coo        | 0       | 10       | PCE          | asi_l2_30                 | p_coo_tu_10CU           |
      | inv_l_l330 | inv_30         | p_coo        | 0       | 10       | PCE          | asi_l3_30                 | p_coo_tu_10CU           |
    When the inventory identified by inv_30 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l130         | hu_l130 |
      | inv_l_l230         | hu_l230 |
      | inv_l_l330         | hu_l330 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_30      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_30_l1   | so_30      | p_coo        | 6          |
    When the order identified by so_30 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_30      | so_30_l1       | N             |
    When start picking job for sales order identified by so_30
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l130    | 2         |
      | p_coo                 | hu_l230    | 3         |
      | p_coo                 | hu_l330    | 1         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_30                 | inout_30_1 | CO        |
    Then the shipment lines of inout_30_1 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 6           |                 |

  @Id:S34165_40
  Scenario: CUs taken out of a picked TU by hand keep today's per-row handling
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_AND_COMPLETE  | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l1_40":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l2_40":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L2"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_40         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l140 | inv_40         | p_coo        | 0       | 10       | PCE          | asi_l1_40                 | p_coo_tu_10CU           |
      | inv_l_l240 | inv_40         | p_coo        | 0       | 10       | PCE          | asi_l2_40                 | p_coo_tu_10CU           |
    When the inventory identified by inv_40 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l140         | hu_l140 |
      | inv_l_l240         | hu_l240 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_40      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_40_l1   | so_40      | p_coo        | 5          |
    When the order identified by so_40 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_40      | so_40_l1       | N             |
    When start picking job for sales order identified by so_40
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l140    | 2         |
      | p_coo                 | hu_l240    | 3         |
    And register the picked TU and its CUs of M_ShipmentSchedule identified by ss_40:
      | M_HU_ID      | Lot-Nummer |
      | shipped_tu40 |            |
      | cu_l2_40     | L2         |
    # The picker's colleague moves the three CUs of lot L2 into a TU of their own before the shipment is generated
    And transform CU to new TUs
      | sourceCU | cuQty | M_HU_PI_Item_Product_ID |
      | cu_l2_40 | 3     | p_coo_tu_10CU           |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_40                 | inout_40_1 | CO        |
    Then the shipment lines of inout_40_1 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 2           | L1              |
      | p_coo        | 3           |                 |

  @Id:S34165_50
  Scenario: Reversing the shipment of a TU with three lots gives the picked quantity back and it ships again as three lines
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_AND_COMPLETE  | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l1_50":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l2_50":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L2"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l3_50":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L3"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_50         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l150 | inv_50         | p_coo        | 0       | 10       | PCE          | asi_l1_50                 | p_coo_tu_10CU           |
      | inv_l_l250 | inv_50         | p_coo        | 0       | 10       | PCE          | asi_l2_50                 | p_coo_tu_10CU           |
      | inv_l_l350 | inv_50         | p_coo        | 0       | 10       | PCE          | asi_l3_50                 | p_coo_tu_10CU           |
    When the inventory identified by inv_50 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l150         | hu_l150 |
      | inv_l_l250         | hu_l250 |
      | inv_l_l350         | hu_l350 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_50      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_50_l1   | so_50      | p_coo        | 6          |
    When the order identified by so_50 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_50      | so_50_l1       | N             |
    When start picking job for sales order identified by so_50
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l150    | 2         |
      | p_coo                 | hu_l250    | 3         |
      | p_coo                 | hu_l350    | 1         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_50                 | inout_50_1 | CO        |
    And the shipment lines of inout_50_1 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 2           | L1              |
      | p_coo        | 3           | L2              |
      | p_coo        | 1           | L3              |
    And all M_ShipmentSchedule_QtyPicked rows of M_ShipmentSchedule identified by ss_50 sum to 6
    When the shipment identified by inout_50_1 is reversed as inout_50_1_reversal
    Then open M_ShipmentSchedule_QtyPicked rows of M_ShipmentSchedule identified by ss_50 sum to 6
    # The reversal flags the schedule for recompute: generate shipments only after its quantities are settled
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_50      | so_50_l1       | N             |
    When 'generate shipments' process is invoked with QuantityType=P, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_50                 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_50                 | inout_50_2 | CO        |
    Then the shipment lines of inout_50_2 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 2           | L1              |
      | p_coo        | 3           | L2              |
      | p_coo        | 1           | L3              |

  @Id:S34165_60
  Scenario: Shipping a TU with two lots in equal quantities through the HU shipping process gives one line per lot
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | DO_NOT_CREATE        | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l1_60":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l2_60":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L2"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_60         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l160 | inv_60         | p_coo        | 0       | 10       | PCE          | asi_l1_60                 | p_coo_tu_10CU           |
      | inv_l_l260 | inv_60         | p_coo        | 0       | 10       | PCE          | asi_l2_60                 | p_coo_tu_10CU           |
    When the inventory identified by inv_60 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l160         | hu_l160 |
      | inv_l_l260         | hu_l260 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_60      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_60_l1   | so_60      | p_coo        | 4          |
    When the order identified by so_60 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_60      | so_60_l1       | N             |
    When start picking job for sales order identified by so_60
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l160    | 2         |
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l260    | 2         |
    And complete picking job
    And register the picked TU and its CUs of M_ShipmentSchedule identified by ss_60:
      | M_HU_ID      | Lot-Nummer |
      | shipped_tu60 |            |
    And ship HU identified by shipped_tu60 via the HU shipping process as inout_60_1
    Then the shipment lines of inout_60_1 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 2           | L1              |
      | p_coo        | 2           | L2              |

  @Id:S34165_70
  Scenario: The shipment report prints quantity and lot number of every line of a TU with three lots
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l1_70":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L1"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l2_70":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L2"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_l3_70":
    """
    {"attributeInstances": [{"attributeCode": "Lot-Nummer", "valueStr": "L3"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_70         | 2022-12-12   | wh_tu          |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l170 | inv_70         | p_coo        | 0       | 10       | PCE          | asi_l1_70                 | p_coo_tu_10CU           |
      | inv_l_l270 | inv_70         | p_coo        | 0       | 10       | PCE          | asi_l2_70                 | p_coo_tu_10CU           |
      | inv_l_l370 | inv_70         | p_coo        | 0       | 10       | PCE          | asi_l3_70                 | p_coo_tu_10CU           |
    When the inventory identified by inv_70 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l170         | hu_l170 |
      | inv_l_l270         | hu_l270 |
      | inv_l_l370         | hu_l370 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_70      | true    | customer_tu   | 2025-04-01  | wh_tu          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_70_l1   | so_70      | p_coo        | 6          |
    When the order identified by so_70 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_70      | so_70_l1       | N             |
    When start picking job for sales order identified by so_70
    And scan picking slot identified by slot_tu
    And set picking target as new TU identified by tu_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l170    | 2         |
      | p_coo                 | hu_l270    | 3         |
      | p_coo                 | hu_l370    | 1         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_70                 | inout_70_1 | CO        |
    Then the shipment report details of inout_70_1 in language de_DE have exactly:
      | QtyEntered | LotNo |
      | 2          | L1    |
      | 3          | L2    |
      | 1          | L3    |
