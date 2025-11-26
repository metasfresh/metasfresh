@from:cucumber
@ghActions:run_on_executor7
Feature: nShift Shipment

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Shipper:
      | Identifier | Name    |
      | nShift     | nShift  |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID |
      | nShift       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_Products:
      | Identifier | WeightNet | WeightGross |
      | product    | 2 KGM     | 2.1 KGM     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl          | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv          | pl             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv                    | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City | Address1 |
      | customerLocation | customer      | CH           | Y               | Y               | 12345  | city | street 1 |
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
