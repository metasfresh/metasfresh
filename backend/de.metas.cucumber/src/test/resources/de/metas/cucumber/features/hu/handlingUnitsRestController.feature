@from:cucumber
Feature: Handling unit rest controller

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  Scenario: retrieve HU by id and update HU attributes EPs validation
    Given metasfresh contains M_Products:
      | Identifier | Value     | Name      |
      | huProduct  | huProduct | huProduct |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | huProduct               | 10  | 2022-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo     |
      | huProduct_inventory       | 2022-01-05T00:00:00Z | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 9        |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | createdCU          | packingVersionCU              | A        | locatorHauptlager           |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedCU           | createdCU          | huProduct               | 9   |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 9     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | createdTU          | packingVersionTU              | huProductTU                            | A        | locatorHauptlager           |                            |
      | newCreatedCU       | packingVersionCU              |                                        | A        | locatorHauptlager           | createdTU                  |
      | createdCU          | packingVersionCU              |                                        | D        | locatorHauptlager           |                            |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedTU           | createdTU          | huProduct               | 9   |
      | storageNewCreatedCU        | newCreatedCU       | huProduct               | 9   |
      | storageCreatedCU           | createdCU          | huProduct               | 0   |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | createdLU          | packingVersionLU              |                                        | A        | locatorHauptlager           |                            |
      | createdTU          | packingVersionTU              | huProductTU                            | A        | locatorHauptlager           | createdLU                  |
      | newCreatedCU       | packingVersionCU              |                                        | A        | locatorHauptlager           | createdTU                  |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedLU           | createdLU          | huProduct               | 9   |
      | storageCreatedTU           | createdTU          | huProduct               | 9   |
      | storageNewCreatedCU        | newCreatedCU       | huProduct               | 9   |

    When store HU endpointPath /api/v2/hu/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | attributes.LockNotice | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdLU          | LU         | createdTU    | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | createdTU          | TU         | newCreatedCU | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | newCreatedCU       | CU         |              | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    When store HU endpointPath /api/v2/hu/byId/:newCreatedCU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | attributes.LockNotice | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | newCreatedCU       | CU         |             | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    And store JsonHUAttributesRequest in context
      | M_HU_ID.Identifier | attributes.LockNotice |
      | createdTU          | LockNoticeTest        |

    And the metasfresh REST-API endpoint path '/api/v2/hu' receives a 'PUT' request with the payload from context and responds with '200' status code

    And store HU endpointPath /api/v2/hu/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | attributes.LockNotice | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdLU          | LU         | createdTU    | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | createdTU          | TU         | newCreatedCU | LockNoticeTest        | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | newCreatedCU       | CU         |              | LockNoticeTest        | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
