@from:cucumber
Feature: Handling unit rest controller

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]

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

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | huProduct_inventory       | 2022-01-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 9        | PCE          |
    And the inventory identified by huProduct_inventory is completed

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

    When store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdLU          | LU         | createdTU    | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | createdTU          | TU         | newCreatedCU | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | newCreatedCU       | CU         |              | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    When store HU endpointPath /api/v2/material/handlingunits/byId/:newCreatedCU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | newCreatedCU       | CU         |             | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    And store JsonHUAttributesRequest in context
      | M_HU_ID.Identifier | attributes.Lot-Nummer |
      | createdTU          | LotNumberTest         |

    And the metasfresh REST-API endpoint path '/api/v2/material/handlingunits' receives a 'PUT' request with the payload from context and responds with '200' status code

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | attributes.Lot-Nummer | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdLU          | LU         | createdTU    | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | createdTU          | TU         | newCreatedCU | LotNumberTest         | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | newCreatedCU       | CU         |              | LotNumberTest         | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |


  Scenario: retrieve HU by id and update ClearanceStatus EPs validation
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

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | huProduct_inventory       | 2022-01-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 9        | PCE          |
    And the inventory identified by huProduct_inventory is completed

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

    And store JsonSetClearanceStatusRequest in context
      | M_HU_ID.Identifier | ClearanceNote     | ClearanceStatus |
      | createdTU          | ClearedStatusNote | Cleared         |

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU/clearance in context

    And a 'PUT' request is sent to metasfresh REST-API with endpointPath and payload from context and fulfills with '200' status code

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus | OPT.ClearanceStatus.key | OPT.ClearanceStatus.caption | OPT.ClearanceNote |
      | createdLU          | LU         | createdTU    | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | null                    | null                        | null              |
      | createdTU          | TU         | newCreatedCU | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Cleared                 | Freigegeben                 | ClearedStatusNote |
      | newCreatedCU       | CU         |              | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Cleared                 | Freigegeben                 | ClearedStatusNote |
