@from:cucumber
@allure.label.epic:E0370_Intralogistic_HUs
@allure.label.feature:F5000_Handling_Unit
@F5000
@ghActions:run_on_executor5
Feature: Move a TU onto an existing LU whose packing instructions link the TU only via a product-specific item
## F5000: Handling Unit

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-31T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier |
      | tomato     |
      | salad      |
    And load M_Warehouse:
      | Identifier   | Value        |
      | warehouseStd | StdWarehouse |

    # Pallet (LU) with two kinds of TUs:
    # - bag TUs via a generic item
    # - IFCO TUs only via an item for tomatoes (no generic IFCO item)
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | luPI       |
      | ifcoPI     |
      | bagPI      |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | luVersion          | luPI       | LU          | Y         |
      | ifcoVersion        | ifcoPI     | TU          | Y         |
      | bagVersion         | bagPI      | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID  | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID | M_Product_ID |
      | luItemBag        | luVersion          | 10  | HU       | bagPI             |              |
      | luItemIFCOTomato | luVersion          | 8   | HU       | ifcoPI            | tomato       |
      | ifcoItemMI       | ifcoVersion        | 0   | MI       |                   |              |
      | bagItemMI        | bagVersion         | 0   | MI       |                   |              |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | ifcoTomato              | ifcoItemMI      | tomato       | 10  | 2022-01-01 |
      | ifcoSalad               | ifcoItemMI      | salad        | 10  | 2022-01-01 |
      | bagTomato               | bagItemMI       | tomato       | 10  | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inventory      | 2022-03-20   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID  | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory      | inventoryLineTomato | tomato       | 0       | 20       | PCE          |
      | inventory      | inventoryLineSalad  | salad        | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID  | M_HU_ID  |
      | inventoryLineTomato | tomatoCU |
      | inventoryLineSalad  | saladCU  |

    # one bag TU and one IFCO TU of tomatoes, one IFCO TU of salad
    And transform CU to new TUs
      | sourceCU | cuQty | M_HU_PI_Item_Product_ID | resultedNewTUs |
      | tomatoCU | 10    | bagTomato               | bagTU          |
      | tomatoCU | 10    | ifcoTomato              | tomatoTU       |
      | saladCU  | 10    | ifcoSalad               | saladTU        |

    # the pallet holds the bag TU, so it has no IFCO item yet
    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | bagTU               | 1     | luItemBag                  | lu                        |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  Scenario: An IFCO TU of tomatoes is moved onto the pallet via the item for tomatoes
    When transform TU to existing LU
      | sourceTU | tuQty | targetLU |
      | tomatoTU | 1     | lu       |

    Then M_HU are validated:
      | M_HU_ID  | Parent | Parent_M_HU_PI_Item_ID |
      | tomatoTU | lu     | luItemIFCOTomato       |
      | bagTU    | lu     | luItemBag              |
    And the M_HU_Storage records of each HU are exactly:
      | M_HU_ID | M_Product_ID | Qty |
      | lu      | tomato       | 20  |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  Scenario: An IFCO TU of salad is moved onto the pallet via the item for tomatoes, so the pallet holds both products
    When transform TU to existing LU
      | sourceTU | tuQty | targetLU |
      | saladTU  | 1     | lu       |

    Then M_HU are validated:
      | M_HU_ID | Parent | Parent_M_HU_PI_Item_ID |
      | saladTU | lu     | luItemIFCOTomato       |
      | bagTU   | lu     | luItemBag              |
    And the M_HU_Storage records of each HU are exactly:
      | M_HU_ID | M_Product_ID | Qty |
      | lu      | tomato       | 10  |
      | lu      | salad        | 10  |


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  Scenario: With an additional generic IFCO item, an IFCO TU of tomatoes is moved onto the pallet via the generic item
    Given metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID   | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | luItemIFCOGeneric | luVersion          | 5   | HU       | ifcoPI            |

    When transform TU to existing LU
      | sourceTU | tuQty | targetLU |
      | tomatoTU | 1     | lu       |

    Then M_HU are validated:
      | M_HU_ID  | Parent | Parent_M_HU_PI_Item_ID |
      | tomatoTU | lu     | luItemIFCOGeneric      |
    And the M_HU_Storage records of each HU are exactly:
      | M_HU_ID | M_Product_ID | Qty |
      | lu      | tomato       | 20  |
