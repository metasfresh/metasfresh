@from:cucumber
@ghActions:run_on_executor7
Feature: Dhl Shipment

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Shipper:
      | Identifier | Name    |
      | nShift     | nShift  |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
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
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |
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
      | Identifier | MovementDate | M_Warehouse_ID |
      | inv        | 2022-12-12   | wh             |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_InventoryLine_ID | M_Product_ID    | QtyBook | QtyCount | UOM.X12DE355 |
      | inv        | inv_l_1            | product         | 0       | 100      | PCE          |
      | inv        | inv_l_2            | packing_product | 0       | 100      | PCE          |
    When the inventory identified by inv is completed
    And after not more than 60s, there are added M_HUs for inventory
      | Identifier | M_HU_ID |
      | inv_l_1    | hu_1    |
      | inv_l_2    | hu_2    |
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

  @from:cucumber
  Scenario: Sales Test
    Given the nShift ship advisor service is stubbed to return a successful response based on the request
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so1        | true    | customer      | 2025-04-01  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so1_l1     | so1        | product      | 10         |
    When the order identified by so1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | M_Warehouse_ID |
      | ss1        | so1_l1         | N             | wh             |