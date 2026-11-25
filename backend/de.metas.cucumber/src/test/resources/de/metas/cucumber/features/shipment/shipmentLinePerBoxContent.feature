@from:cucumber
@allure.label.epic:E0110_Shipping
@allure.label.feature:F17011_Shipment_Line_Attribute_Propagation_ASI
@ghActions:run_on_executor3
@Id:ShipmentLinePerBoxContent
Feature: Shipment line per box content
  A box (TU) that holds goods with different attribute values (e.g. different countries of origin) must ship as
  one shipment line per attribute combination, also when the quantities are equal.

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh_box         |
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic | M_Warehouse_ID |
      | slot_box   | 710         | Y         | wh_box         |
    And metasfresh contains M_Products:
      | Identifier |
      | p_coo      |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_box     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_box     | ps_box             | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_box    | pl_box         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_box                | p_coo        | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_box | N        | Y          | ps_box             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 |
      | loc_box    | customer_box  | CH           | Y               | Y               | 12345  | city | street 1 |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | box_pi     |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | box_pi_Version     | box_pi     | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType |
      | box_pi_item     | box_pi_Version     |     | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | p_coo_box_10CU          | box_pi_item     | p_coo        | 10  | 2021-01-01 |
    # The lot number is a shipment-line aggregation attribute on the template PI (restored after the scenario).
    And update M_HU_PI_Attribute UseInASI:
      | M_HU_PI_Version_ID | M_Attribute.Value | UseInASI |
      | 100                | Lot-Nummer        | Y        |
    And M_ShipmentSchedule_AttributeConfig is active for M_Attribute.Value Lot-Nummer
    And M_ShipmentSchedule_AttributeConfig is active for M_Attribute.Value 1000001

  @Id:BOX_LOT_10
  Scenario: Equal-quantity picks of two origins into one box ship as two lines
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |
    And metasfresh contains M_AttributeSetInstance with identifier "asi_IT_box":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "IT"}]}
    """
    And metasfresh contains M_AttributeSetInstance with identifier "asi_DE_box":
    """
    {"attributeInstances": [{"attributeCode": "1000001", "valueStr": "DE"}]}
    """
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_box        | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_it   | inv_box        | p_coo        | 0       | 10       | PCE          | asi_IT_box                | p_coo_box_10CU          |
      | inv_l_de   | inv_box        | p_coo        | 0       | 10       | PCE          | asi_DE_box                | p_coo_box_10CU          |
    When the inventory identified by inv_box is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_it           | hu_it   |
      | inv_l_de           | hu_de   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_box     | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_box_l1  | so_box     | p_coo        | 4          |
    When the order identified by so_box is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_box     | so_box_l1      | N             |
    # Two equal-quantity picks of different origins into the same new box
    When start picking job for sales order identified by so_box
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_it      | 2         |
      | p_coo                 | hu_de      | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_box                | inout_box  | CO        |
    Then the shipment lines of inout_box are exactly, in any order:
      | M_Product_ID | MovementQty | attr.1000001 |
      | p_coo        | 2           | IT           |
      | p_coo        | 2           | DE           |

  @Id:BOX_LOT_80
  Scenario: Equal-quantity picks of two origins into one box that sits on a pallet ship as two lines
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
      | box80_pi   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | lu80_pi_Version    | lu80_pi    | LU          | Y         |
      | box80_pi_Version   | box80_pi   | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID | OPT.M_HU_PackingMaterial_ID.Identifier |
      | lu80_pi_item    | lu80_pi_Version    | 10  | HU       | box80_pi              | pm80                                   |
      | box80_pi_item   | box80_pi_Version   |     | MI       |                       |                                        |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | p_coo_box80_10CU        | box80_pi_item   | p_coo        | 10  | 2021-01-01 |
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
      | inv_80         | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_it80 | inv_80         | p_coo        | 0       | 10       | PCE          | asi_IT_80                 | p_coo_box80_10CU        |
      | inv_l_de80 | inv_80         | p_coo        | 0       | 10       | PCE          | asi_DE_80                 | p_coo_box80_10CU        |
    When the inventory identified by inv_80 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_it80         | hu_it80 |
      | inv_l_de80         | hu_de80 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_80      | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_80_l1   | so_80      | p_coo        | 4          |
    When the order identified by so_80 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_80      | so_80_l1       | N             |
    # Two equal-quantity picks of different origins into the same new box (the box's packing instruction has a parent pallet)
    When start picking job for sales order identified by so_80
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box80_pi
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

  @Id:BOX_LOT_91
  Scenario: One box with two CUs of unequal quantities per origin, origins picked interleaved, ships as one line per origin with the summed quantity
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
      | inv_90         | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_it1  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_IT_90                 | p_coo_box_10CU          |
      | inv_l_it2  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_IT_90                 | p_coo_box_10CU          |
      | inv_l_de1  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_DE_90                 | p_coo_box_10CU          |
      | inv_l_de2  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_DE_90                 | p_coo_box_10CU          |
      | inv_l_fr1  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_FR_90                 | p_coo_box_10CU          |
      | inv_l_fr2  | inv_90         | p_coo        | 0       | 10       | PCE          | asi_FR_90                 | p_coo_box_10CU          |
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
      | so_90      | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_90_l1   | so_90      | p_coo        | 14         |
    When the order identified by so_90 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_90      | so_90_l1       | N             |
    # Six picks into the same new box: two CUs per origin, unequal quantities, origins interleaved
    When start picking job for sales order identified by so_90
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box_pi
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

  @Id:BOX_LOT_90
  Scenario: One box with four equal-quantity CUs of three lots, two CUs of the same lot from one stock HU, ships as one line per lot
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
      | inv_3lot       | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier   | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_a_3lot | inv_3lot       | p_coo        | 0       | 10       | PCE          | asi_lotA_3lot             | p_coo_box_10CU          |
      | inv_l_b_3lot | inv_3lot       | p_coo        | 0       | 10       | PCE          | asi_lotB_3lot             | p_coo_box_10CU          |
      | inv_l_c_3lot | inv_3lot       | p_coo        | 0       | 10       | PCE          | asi_lotC_3lot             | p_coo_box_10CU          |
    When the inventory identified by inv_3lot is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID   |
      | inv_l_a_3lot       | hu_a_3lot |
      | inv_l_b_3lot       | hu_b_3lot |
      | inv_l_c_3lot       | hu_c_3lot |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_3lot    | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_3lot_l1 | so_3lot    | p_coo        | 8          |
    When the order identified by so_3lot is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_3lot    | so_3lot_l1     | N             |
    # Four picks of 2 into the same new box: lot A twice from the same stock HU, then lot B, then lot C
    When start picking job for sales order identified by so_3lot
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box_pi
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

  @Id:BOX_LOT_95
  Scenario: One box with three CUs of three lots ships as three product lines and one packing material line for the one box
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
      | box95_pi   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | box95_pi_Version   | box95_pi   | TU          | Y         |
    # the box is made of one packing material (the box itself) and holds the goods
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID  | M_HU_PI_Version_ID | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | box95_pi_item_mi | box95_pi_Version   |     | MI       |                                        |
      | box95_pi_item_pm | box95_pi_Version   | 0   | PM       | pm95                                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  |
      | p_coo_box95_10CU        | box95_pi_item_mi | p_coo        | 10  | 2021-01-01 |
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
      | emptiesNetwork            | wh_empties95   | wh_box               | shipper95    |
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
      | inv_95         | 2022-12-12   | wh_box         |
    # the goods are inventoried in plain CUs-in-TU without packing material; only the picking target box carries a packing material
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l1   | inv_95         | p_coo        | 0       | 10       | PCE          | asi_lot1_95               | p_coo_box_10CU          |
      | inv_l_l2   | inv_95         | p_coo        | 0       | 10       | PCE          | asi_lot2_95               | p_coo_box_10CU          |
      | inv_l_l3   | inv_95         | p_coo        | 0       | 10       | PCE          | asi_lot3_95               | p_coo_box_10CU          |
    When the inventory identified by inv_95 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l1           | hu_l1   |
      | inv_l_l2           | hu_l2   |
      | inv_l_l3           | hu_l3   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_95      | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_95_l1   | so_95      | p_coo        | 6          |
    When the order identified by so_95 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_95      | so_95_l1       | N             |
    # Three equal-quantity picks of three lots into ONE new box (one TU, not three)
    When start picking job for sales order identified by so_95
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box95_pi
    And pick lines
      | PickingLine.byProduct | PickFromHU | QtyPicked |
      | p_coo                 | hu_l1      | 2         |
      | p_coo                 | hu_l2      | 2         |
      | p_coo                 | hu_l3      | 2         |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ss_95                 | inout_95   | CO        |
    # three product lines, one per lot, and exactly ONE packing material line with quantity 1 (one box)
    Then the shipment lines of inout_95 are exactly, in any order:
      | M_Product_ID | MovementQty | attr.Lot-Nummer |
      | p_coo        | 2           | LOT1            |
      | p_coo        | 2           | LOT2            |
      | p_coo        | 2           | LOT3            |
      | pm_product95 | 1           |                 |

  @Id:BOX_LOT_96
  Scenario: Reversing the shipment of a box with two CUs of one lot and one CU each of two other lots reopens the whole picked quantity
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
      | inv_96         | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_a96  | inv_96         | p_coo        | 0       | 10       | PCE          | asi_lotA_96               | p_coo_box_10CU          |
      | inv_l_b96  | inv_96         | p_coo        | 0       | 10       | PCE          | asi_lotB_96               | p_coo_box_10CU          |
      | inv_l_c96  | inv_96         | p_coo        | 0       | 10       | PCE          | asi_lotC_96               | p_coo_box_10CU          |
    When the inventory identified by inv_96 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_a96          | hu_a96  |
      | inv_l_b96          | hu_b96  |
      | inv_l_c96          | hu_c96  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_96      | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_96_l1   | so_96      | p_coo        | 8          |
    When the order identified by so_96 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_96      | so_96_l1       | N             |
    When start picking job for sales order identified by so_96
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box_pi
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
    # The reversal must give back every picked unit, also the two CUs of lot A that share one shipment line
    When the shipment identified by inout_96_1 is reversed as inout_96_1_reversal
    Then open M_ShipmentSchedule_QtyPicked rows of M_ShipmentSchedule identified by ss_96 sum to 8
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

  @Id:BOX_LOT_20
  Scenario: Two CUs of the same lot but different origins in one box ship as one line per origin
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
      | inv_20         | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_de20 | inv_20         | p_coo        | 0       | 10       | PCE          | asi_L1_DE_20              | p_coo_box_10CU          |
      | inv_l_it20 | inv_20         | p_coo        | 0       | 10       | PCE          | asi_L1_IT_20              | p_coo_box_10CU          |
    When the inventory identified by inv_20 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_de20         | hu_de20 |
      | inv_l_it20         | hu_it20 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_20      | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_20_l1   | so_20      | p_coo        | 4          |
    When the order identified by so_20 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_20      | so_20_l1       | N             |
    When start picking job for sales order identified by so_20
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box_pi
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

  @Id:BOX_LOT_25
  Scenario: One box shared by two order lines of one product with equal quantities ships each order line only the units picked for it
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
      | inv_25         | 2022-12-12   | wh_box         |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | M_AttributeSetInstance_ID | M_HU_PI_Item_Product_ID |
      | inv_l_l125 | inv_25         | p_coo        | 0       | 10       | PCE          | asi_L1_25                 | p_coo_box_10CU          |
      | inv_l_l225 | inv_25         | p_coo        | 0       | 10       | PCE          | asi_L2_25                 | p_coo_box_10CU          |
    When the inventory identified by inv_25 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_l125         | hu_l125 |
      | inv_l_l225         | hu_l225 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID |
      | so_25      | true    | customer_box  | 2025-04-01  | wh_box         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_25_lA   | so_25      | p_coo        | 2          |
      | so_25_lB   | so_25      | p_coo        | 2          |
    When the order identified by so_25 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_25_A    | so_25_lA       | N             |
      | ss_25_B    | so_25_lB       | N             |
    # One new box for the whole job: the picks for both order lines land in the same box
    When start picking job for sales order identified by so_25
    And scan picking slot identified by slot_box
    And set picking target as new TU identified by box_pi
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
