@from:cucumber
@ghActions:run_on_executor7
Feature: mobileUI Distribution

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | PCE      |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name | HU_UnitType | IsCurrent |
      | TU                            | TU                    | TU   | TU          | Y         |
      | LU                            | LU                    | LU   | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx4                               | TU                         | product                 | 4   | 2000-01-01 |


    And set mobile UI distribution profile
      | IsAllowPickingAnyHU |
      | Y                   |

    And metasfresh contains M_Warehouse:
      | Identifier | IsInTransit | M_Locator_ID     |
      | wh1        | N           | locator1         |
      | wh2        | N           | locator2         |
      | InTransit  | Y           | InTransitLocator |

    And metasfresh contains M_Inventories:
      | Identifier | MovementDate | M_Warehouse_ID |
      | inventory  | 2024-03-20   | wh1            |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | M_Locator_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | line1              | product      | locator1     | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | line1              | hu1     |

    
    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Simply move one HU
    And metasfresh contains DD_Orders:
      | Identifier | M_Warehouse_ID.From | M_Warehouse_ID.To | M_Warehouse_ID.Transit |
      | ddo        | wh1                 | wh2               | InTransit              |
    And metasfresh contains DD_OrderLines:
      | Identifier | DD_Order_ID | M_Product_ID | QtyEntered | M_Locator_ID | M_LocatorTo_ID |
      | ddo_l1     | ddo         | product      | 900        | locator1     | locator2       |
    And the dd_order identified by ddo is completed

    And Start distribution job for dd_order identified by ddo
      | DD_Order_ID |
      | ddo         |

    And Pick HU for distribution job line
      | M_HU_ID |
      | hu1     |
    And M_HU are validated:
      | M_HU_ID | M_Locator_ID     |
      | hu1     | InTransitLocator |

    And Drop HU for distribution job line

    And M_HU are validated:
      | M_HU_ID | M_Locator_ID | HUStatus |
      | hu1     | locator2     | A        |
    And validate I_M_MovementLine for distribution order line
      | DD_OrderLine_ID.Identifier | MovementQty | M_LocatorTo_ID.Identifier |
      | ddo_l1                     | 1000        | InTransitLocator          |
      | ddo_l1                     | 1000        | locator2                  |
