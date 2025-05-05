@from:cucumber
@ghActions:run_on_executor5
Feature: Handling unit export from manufacturing order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And RabbitMQ MF_TO_ExternalSystem queue is purged

  Scenario: HU export from manufacturing order
    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue         | OPT.IsSyncHUsOnMaterialReceipt | OPT.IsSyncHUsOnProductionReceipt |
      | GRSConfig_manufacturing             | GRS  | testGRSSignum_manufacturing | Y                              | Y                                |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |

    And metasfresh contains M_Products:
      | Identifier              | Name                 | HUClearanceStatus |
      | manufacturingProduct_HU | manufacturingProduct | L                 |
      | componentProduct_HU     | componentProduct     |                   |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemManufacturingProduct         | huPiItemTU                 | manufacturingProduct_HU | 10  | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier        | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_manufacturing | manufacturingProduct_HU | 2021-01-02 | bomVersions_manufacturing            |
    And metasfresh contains PP_Product_BOMLines
      | Identifier          | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bom_l_manufacturing | bom_manufacturing            | componentProduct_HU     | 2021-01-02 | 10       |

    And the PP_Product_BOM identified by bom_manufacturing is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder_manufacturing  | MOP         | manufacturingProduct_HU | 10         | testResource             | 2022-01-05T23:59:00.00Z | 2022-01-05T23:59:00.00Z | 2022-01-05T23:59:00.00Z | Y                |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | PP_Order_ID.Identifier | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig                          | ppOrder_manufacturing  | ppOrderTU          | N               | 0     | N               | 1     | N               | 10          | huItemManufacturingProduct         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder_manufacturing  |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.HUStatus |
      | ppOrderTU          | A            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | ppOrderTU          | packingVersionTU              | A        | locatorHauptlager           |                            |
      | ppOrderCU          | packingVersionCU              | A        | locatorHauptlager           | ppOrderTU                  |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storagePPOrderTU           | ppOrderTU          | manufacturingProduct_HU | 10  |
      | storagePPOrderCU           | ppOrderCU          | manufacturingProduct_HU | 10  |

    And after not more than 60s, PP_Cost_Collector are found:
      | PP_Cost_Collector_ID.Identifier | CostCollectorType | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus |
      | ppOrder_CostCollector           | MaterialReceipt   | ppOrder_manufacturing  | manufacturingProduct_HU | 10          | CO        |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.M_HU_ID.Identifier |
      | GRSConfig_manufacturing             | ppOrderTU              |

    When store HU endpointPath /api/v2/material/handlingunits/byId/:ppOrderTU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus | OPT.ClearanceStatus.key | OPT.ClearanceNote |
      | ppOrderTU          | TU         | ppOrderCU   | manufacturingProduct | manufacturingProduct  | 10           | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Locked                  | Hergestellt       |
      | ppOrderCU          | CU         |             | manufacturingProduct | manufacturingProduct  | 10           | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Locked                  | Hergestellt       |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | GRSConfig_manufacturing             |
