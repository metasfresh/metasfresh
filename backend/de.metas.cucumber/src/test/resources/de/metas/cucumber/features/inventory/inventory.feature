@from:cucumber
@ghActions:run_on_executor6
Feature: Physical inventory tests

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | warehouse      |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU         |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | LU_version         | LU         | LU          |
      | TU_version         | TU         | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID |
      |                 | LU_version         | HU       | 40  | TU                |
      |                 | LU_version         | HU       | 1   | 101               |
      | TU_PI_Item      | TU_version         | MI       |     |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty     |
      | TUx10                   | TU_PI_Item      | product      | 10 PCE  |
      | CUx200                  | 101             | product      | 200 PCE |

  @from:cucumber
  Scenario: Inventory+ to CU
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_ID |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 1000 PCE | hu1     |
    And validate M_HUs:
      | M_HU_ID | M_HU_PI_ID | HUStatus | M_HU_Parent | M_Product_ID | Qty      |
      | hu1     | 101        | A        | -           | product      | 1000 PCE |

  @from:cucumber
  Scenario: Inventory+ to 2 x LU/CU
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 400 PCE  | CUx200                  | LU            | lu1     | lu2      |
    And validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty     |
      | -           | lu1     | LU         | A        | product      | 200 PCE |
      | lu1         | vhu     | 101        | A        | product      | 200 PCE |
      | -           | lu2     | LU         | A        | product      | 200 PCE |
      | lu2         | vhu     | 101        | A        | product      | 200 PCE |

  @from:cucumber
  Scenario: Inventory+ to 2 x LU/TU/CU
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 800 PCE  | TUx10                   | LU            | lu1     | lu2      |
    And validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty     | QtyTUs | IsAggregate |
      | -           | lu1     | LU         | A        | product      | 400 PCE |        | N           |
      | lu1         | tu_agg1 | TU         | A        | product      | 400 PCE | 40     | Y           |

  @from:cucumber
  Scenario: Inventory+ to 2 x TUs
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 19 PCE   | TUx10                   |               | tu1     | tu2      |
    And validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty    |
      | -           | tu1     | TU         | A        | product      | 10 PCE |
      | -           | tu2     | TU         | A        | product      | 9 PCE  | 
