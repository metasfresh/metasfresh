@from:cucumber
@ghActions:run_on_executor5
Feature: Cleared HU can be issued to production order

  Scenario: Cleared HUs can be issued to a production order
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-31T13:30:13+01:00[Europe/Berlin]

    And destroy existing M_HUs
    And metasfresh contains M_Products:
      | Identifier              |
      | huProduct               |
      | manufacturingProduct_HU |
    And load M_Warehouse:
      | Identifier   | Value        |
      | warehouseStd | StdWarehouse |
    And load M_Locator:
      | Identifier        | M_Warehouse_ID | Value      |
      | locatorHauptlager | warehouseStd   | Hauptlager |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID         | Name            |
      | huPackingLU        | huPackingLU     |
      | huPackingTU        | huPackingTU     |
      | huPackingVirtualPI | No Packing Item |
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
      | M_Inventory_ID.Identifier | MovementDate | DocumentNo      | M_Warehouse_ID |
      | huProduct_inventory       | 2022-03-20   | inventoryDocNo2 | warehouseStd   |
    And  metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | OPT.resultedNewTUs.Identifier | OPT.resultedNewCUs.Identifier |
      | createdCU           | 10    | huProductTU                        | createdTU                     | newCreatedCU                  |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And update HU clearance status
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | createdLU          | Cleared         | Cleared HU        |

    And metasfresh contains PP_Product_BOM
      | Identifier        | M_Product_ID            | PP_Product_BOMVersions_ID |
      | bom_manufacturing | manufacturingProduct_HU | bomVersions_manufacturing |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | bom_l_manufacturing1 | bom_manufacturing | huProduct    | 10       |

    And the PP_Product_BOM identified by bom_manufacturing is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder_manufacturing  | MOP         | manufacturingProduct_HU | 10         | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                |

    And after not more than 60s, PP_Order_BomLines are found
      | Identifier       | PP_Order_ID           | M_Product_ID | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBOMLine_1 | ppOrder_manufacturing | huProduct    | 100          | false           | PCE               | CO            |

    And select M_HU to be issued for productionOrder
      | M_HU_ID.Identifier | PP_Order_Qty_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.MovementDate     |
      | createdLU          | pp_order_qty_1             | ppOrderBOMLine_1               | 2022-03-31T13:30:13Z |

    And validate PP_Order_Qty records
      | PP_Order_Qty_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.Processed |
      | pp_order_qty_1             | createdLU          | huProduct               | ppOrderBOMLine_1               | false         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder_manufacturing  |

    Then validate M_HUs:
      | M_HU_ID      | M_HU_PI_Version_ID | M_HU_PI_Item_Product_ID | HUStatus | M_Locator_ID      | ClearanceStatus | ClearanceNote |
      | createdLU    | packingVersionLU   |                         | D        | locatorHauptlager | C               | Cleared HU    |
      | createdTU    | packingVersionTU   | huProductTU             | D        | locatorHauptlager | C               | Cleared HU    |
      | newCreatedCU | packingVersionCU   |                         | D        | locatorHauptlager | C               | Cleared HU    |

