@from:cucumber
@ghActions:run_on_executor5
Feature: REST /api/v2/material/handlingunits/qty

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID      | M_Warehouse_ID | Value      | REST.Context.QRCode |
      | locatorHauptlager | warehouseStd   | Hauptlager | locatorQRCode       |
    And metasfresh contains M_Products:
      | Identifier |
      | huProduct  |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID  | Name        |
      | huPackingTU | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | huProduct               | 10  | 2022-01-01 |
    
    

# ##############################################################################################################################
# ##############################################################################################################################
# ##############################################################################################################################

  Scenario: Increase the qty of an existing HU (by QR code and ID) from 10 to 100
    Given metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2022-01-02   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | inventoryLine      | huProduct    | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID   | REST.Context |
      | inventoryLine      | createdCU | M_HU_ID      |
    And validate M_HU_Storage:
      | M_HU_ID   | M_Product_ID | Qty |
      | createdCU | huProduct    | 10  |
    And generate QR Codes for HUs
      | M_HU_ID   | REST.Context |
      | createdCU | qrCode       |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/material/handlingunits/qty' and fulfills with '200' status code
      """
      {
        "huId": "@M_HU_ID@",
        "huQRCode": @qrCode/asJsonString@,
        "qty": { "qty": 100, "uomCode":"PCE" }
      }
      """
    Then validate M_HU_Storage:
      | M_HU_ID   | M_Product_ID | Qty |
      | createdCU | huProduct    | 100 |



# ##############################################################################################################################
# ##############################################################################################################################
# ##############################################################################################################################

  Scenario: Increase the qty of an existing HU (by QR code but no ID provided) from 10 to 100
    Given metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2022-01-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | inventoryLine      | huProduct    | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID   | REST.Context |
      | inventoryLine      | createdCU | M_HU_ID      |
    And validate M_HU_Storage:
      | M_HU_ID   | M_Product_ID | Qty |
      | createdCU | huProduct    | 10  |
    And generate QR Codes for HUs
      | M_HU_ID   | REST.Context |
      | createdCU | qrCode       |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/material/handlingunits/qty' and fulfills with '200' status code
      """
      {
        "huQRCode": @qrCode/asJsonString@,
        "qty": { "qty": 100, "uomCode":"PCE" }
      }
      """
    Then validate M_HU_Storage:
      | M_HU_ID   | M_Product_ID | Qty |
      | createdCU | huProduct    | 100 |


# ##############################################################################################################################
# ##############################################################################################################################
# ##############################################################################################################################

  Scenario: Create a new TU with 100 PCE using a new QR code
    When generate new QR Codes
      | M_HU_PI_ID  | M_Product_ID | LotNo | REST.Context |
      | huPackingTU | huProduct    | 1234  | qrCode       |
    And a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/material/handlingunits/qty' and fulfills with '200' status code
      """
      {
        "huQRCode": @qrCode/asJsonString@,
        "qty": { "qty": 100, "uomCode":"PCE" },
        "locatorQRCode": @locatorQRCode/asJsonString@
      }
      """

    Then load M_HU from REST response JSON path
      | JsonPath   | M_HU_ID  |
      | /result/id | createHU |
    And validate M_HU_Storage:
      | M_HU_ID  | M_Product_ID | Qty |
      | createHU | huProduct    | 100 |
