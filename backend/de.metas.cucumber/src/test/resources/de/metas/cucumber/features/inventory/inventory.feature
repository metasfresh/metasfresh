@from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
@ghActions:run_on_executor6
Feature: Physical inventory tests
## F5010: Inventory

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
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
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: Inventory+ to CU
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_ID |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 1000 PCE | hu1     |
    And validate M_HUs:
      | M_HU_ID | M_HU_PI_ID | HUStatus | M_HU_Parent | M_Product_ID | Qty      |
      | hu1     | 101        | A        | -           | product      | 1000 PCE |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | hu1                | A        | Y        |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product                 | 1000      |

  @from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
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
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | lu1                | A        | Y        |
      | lu2                | A        | Y        |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product                 | 400       |

  @from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: Inventory+ to 2 x LU/TU/CU
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 800 PCE  | TUx10                   | LU            | lu1     | lu2      |
    And validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty     | QtyTUs | IsAggregate |
      | -           | lu1     | LU         | A        | product      | 400 PCE |        | N           |
      | lu1         | tu_agg1 | TU         | A        | product      | 400 PCE | 40     | Y           |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | lu1                | A        | Y        |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product                 | 800       |

  # Product-specific LU item: the pallet packing instruction LU_PS has a generic item (30 TUs) and an item for product XY (40 TUs).
  # Pallets built by the inventory use the item of the line's product; products without their own item keep the generic item.
  @from:cucumber
  @Id:S32544_INV_010
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: S32544_INV_010 - Inventory+ to LU/TU/CU: product with its own LU item gets pallets of that item (40 + 10 TUs)
    Given metasfresh contains M_Products:
      | Identifier |
      | XY         |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU_PS      |
      | TU_PS      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | LU_PS_version      | LU_PS      | LU          |
      | TU_PS_version      | TU_PS      | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID | M_Product_ID |
      | LU_PS_generic   | LU_PS_version      | HU       | 30  | TU_PS             |              |
      | LU_PS_XY        | LU_PS_version      | HU       | 40  | TU_PS             | XY           |
      | TU_PS_PI_Item   | TU_PS_version      | MI       |     |                   |              |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty   |
      | TUx4_XY                 | TU_PS_PI_Item   | XY           | 4 PCE |

    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | XY           | 0 PCE   | 200 PCE  | TUx4_XY                 | LU_PS         | lu1     | lu2      |

    Then validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty     | QtyTUs | IsAggregate |
      | -           | lu1     | LU_PS      | A        | XY           | 160 PCE |        | N           |
      | lu1         | tu_agg1 | TU_PS      | A        | XY           | 160 PCE | 40     | Y           |
      | -           | lu2     | LU_PS      | A        | XY           | 40 PCE  |        | N           |
      | lu2         | tu_agg2 | TU_PS      | A        | XY           | 40 PCE  | 10     | Y           |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | XY                      | 200       |

  @from:cucumber
  @Id:S32544_INV_020
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: S32544_INV_020 - Inventory+ to LU/TU/CU: product without its own LU item keeps the generic item (30 + 20 TUs)
    Given metasfresh contains M_Products:
      | Identifier |
      | XY         |
      | OTHER      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU_PS      |
      | TU_PS      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | LU_PS_version      | LU_PS      | LU          |
      | TU_PS_version      | TU_PS      | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID | M_Product_ID |
      | LU_PS_generic   | LU_PS_version      | HU       | 30  | TU_PS             |              |
      | LU_PS_XY        | LU_PS_version      | HU       | 40  | TU_PS             | XY           |
      | TU_PS_PI_Item   | TU_PS_version      | MI       |     |                   |              |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty   |
      | TUx4_OTHER              | TU_PS_PI_Item   | OTHER        | 4 PCE |

    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | OTHER        | 0 PCE   | 200 PCE  | TUx4_OTHER              | LU_PS         | lu1     | lu2      |

    Then validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty     | QtyTUs | IsAggregate |
      | -           | lu1     | LU_PS      | A        | OTHER        | 120 PCE |        | N           |
      | lu1         | tu_agg1 | TU_PS      | A        | OTHER        | 120 PCE | 30     | Y           |
      | -           | lu2     | LU_PS      | A        | OTHER        | 80 PCE  |        | N           |
      | lu2         | tu_agg2 | TU_PS      | A        | OTHER        | 80 PCE  | 20     | Y           |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | OTHER                   | 200       |

  # LU_XY_ONLY links the TU packing instruction only through an item for product XY (no generic item).
  @from:cucumber
  @Id:S32544_INV_030
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: S32544_INV_030 - Inventory+ to LU/TU/CU: pallet packing instruction linked to the TU only through the product's own item
    Given metasfresh contains M_Products:
      | Identifier |
      | XY         |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU_XY_ONLY |
      | TU_PS      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | LU_XY_ONLY_version | LU_XY_ONLY | LU          |
      | TU_PS_version      | TU_PS      | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID | M_Product_ID |
      | LU_XY_ONLY_XY   | LU_XY_ONLY_version | HU       | 40  | TU_PS             | XY           |
      | TU_PS_PI_Item   | TU_PS_version      | MI       |     |                   |              |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty   |
      | TUx4_XY                 | TU_PS_PI_Item   | XY           | 4 PCE |

    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | XY           | 0 PCE   | 200 PCE  | TUx4_XY                 | LU_XY_ONLY    | lu1     | lu2      |

    Then validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty     | QtyTUs | IsAggregate |
      | -           | lu1     | LU_XY_ONLY | A        | XY           | 160 PCE |        | N           |
      | lu1         | tu_agg1 | TU_PS      | A        | XY           | 160 PCE | 40     | Y           |
      | -           | lu2     | LU_XY_ONLY | A        | XY           | 40 PCE  |        | N           |
      | lu2         | tu_agg2 | TU_PS      | A        | XY           | 40 PCE  | 10     | Y           |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | XY                      | 200       |

  # Another product on the same TU packing instruction: LU_XY_ONLY has no item for it, so completing the inventory fails with a user error.
  @from:cucumber
  @Id:S32544_INV_040
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: S32544_INV_040 - Inventory+ to LU/TU/CU: pallet packing instruction with an item only for another product is refused
    Given metasfresh contains M_Products:
      | Identifier |
      | XY         |
      | OTHER      |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU_XY_ONLY |
      | TU_PS      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType |
      | LU_XY_ONLY_version | LU_XY_ONLY | LU          |
      | TU_PS_version      | TU_PS      | TU          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID | M_Product_ID |
      | LU_XY_ONLY_XY   | LU_XY_ONLY_version | HU       | 40  | TU_PS             | XY           |
      | TU_PS_PI_Item   | TU_PS_version      | MI       |     |                   |              |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty   |
      | TUx4_OTHER              | TU_PS_PI_Item   | OTHER        | 4 PCE |

    Then metasfresh fails to complete single line inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | ErrorMessage                    |
      | inventory1     | warehouse      | 2021-04-16   | OTHER        | 0 PCE   | 200 PCE  | TUx4_OTHER              | LU_XY_ONLY    | ERR_LU_HAS_NO_TU_SUB_PACK_INSTR |

  @from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5010
  Scenario: Inventory+ to 2 x TUs
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID | M_HU_ID2 |
      | inventory1     | warehouse      | 2021-04-16   | product      | 0 PCE   | 19 PCE   | TUx10                   |               | tu1     | tu2      |
    And validate M_HUs:
      | M_HU_Parent | M_HU_ID | M_HU_PI_ID | HUStatus | M_Product_ID | Qty    |
      | -           | tu1     | TU         | A        | product      | 10 PCE |
      | -           | tu2     | TU         | A        | product      | 9 PCE  |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | tu1                | A        | Y        |
      | tu2                | A        | Y        |
    And after not more than 30 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product                 | 19        |
