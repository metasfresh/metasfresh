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
