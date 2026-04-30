@from:cucumber
@allure.label.epic:E2300_Attributes
@allure.label.feature:F67043_Weight_calculation
@ghActions:run_on_executor7
Feature: HU weight attributes are derived from product master Net + Gross

  When a product has both a NetWeight and a GrossWeight on its master record, the
  HU transaction listener writes the NetWeight × qty into the HU's WeightNet attribute
  and the per-CU packaging delta ((GrossWeight - NetWeight) × qty) into WeightTare.
  Total WeightGross = WeightNet + WeightTare stays mathematically unchanged.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-04-29T13:30:13+02:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | X12DE355 | WeightNet | WeightGross |
      | product_1  | PCE      | 0.200 KGM | 0.205 KGM   |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | TU                 | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType |
      | TU              | TU                 | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | TUx100_product_1        | TU              | product_1    | 100 | 2000-01-01 |

  Scenario: Receiving 20 PCE writes WeightNet=4.000 kg, WeightTare delta=0.100 kg, WeightGross=4.100 kg
    Given metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2026-04-29   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | line1              | product_1    | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | line1              | hu_1    |

    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueNumber |
      | hu_1    | WeightNet            | 4.000       |
      | hu_1    | WeightTare           | 0.100       |
      | hu_1    | WeightGross          | 4.100       |
