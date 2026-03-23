@from:cucumber
@ghActions:run_on_executor5
Feature: Handling unit data export audit

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the export audit data is reset
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier     | Value          | Name           |
      | huAuditProduct | huAuditProduct | huAuditProduct |
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
      | huAuditProductTU                   | huPiItemTU                 | huAuditProduct          | 10  | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_1               | 2022-01-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_1               | inventory_line                | huAuditProduct          | 0       | 10       | PCE          |

    And the inventory identified by inventory_1 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventory_line                | createdCU          |

  Scenario: The request is good and HU data export audit is created
    Given store HU endpointPath /api/v2/material/handlingunits/byId/:createdCU in context

    When a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdCU          | CU         |             | huAuditProduct       | huAuditProduct        | 10           | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    And after not more than 10s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | cu_data_export                  | M_HU      | createdCU            |                                        |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action  | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | cu_data_export                  | Exported-Standalone | null                                | null                       |

  Scenario: The request is good and HU data export audit with external system config and pinstance is created
    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue | OPT.IsSyncHUsOnMaterialReceipt | OPT.IsSyncHUsOnProductionReceipt |
      | GRSConfig_HU                        | GRS  | exportGRSSignum_1   | Y                              | Y                                |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | GRSConfig_HU                        | pInstance_exportHU         |

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdCU in context

    When a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdCU          | CU         |             | huAuditProduct       | huAuditProduct        | 10           | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    And after not more than 10s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | cu_data_export                  | M_HU      | createdCU            |                                        |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action  | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | cu_data_export                  | Exported-Standalone | GRSConfig_HU                        | pInstance_exportHU         |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | GRSConfig_HU                        |

  Scenario: When M_HU is changed, a proper camel-request is sent to rabbit-mq
    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue | OPT.IsSyncHUsOnMaterialReceipt | OPT.IsSyncHUsOnProductionReceipt |
      | GRSConfig_HU                        | GRS  | exportGRSSignum_2   | Y                              | Y                                |
    And add external system config and pinstance headers
      | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | GRSConfig_HU                        | pInstance_exportHU         |

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdCU in context

    When a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdCU          | CU         |             | huAuditProduct       | huAuditProduct        | 10           | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    And after not more than 10s, there are added records in Data_Export_Audit
      | Data_Export_Audit_ID.Identifier | TableName | Record_ID.Identifier | Data_Export_Audit_Parent_ID.Identifier |
      | cu_data_export                  | M_HU      | createdCU            |                                        |
    And there are added records in Data_Export_Audit_Log
      | Data_Export_Audit_ID.Identifier | Data_Export_Action  | ExternalSystem_Config_ID.Identifier | AD_PInstance_ID.Identifier |
      | cu_data_export                  | Exported-Standalone | GRSConfig_HU                        | pInstance_exportHU         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 10    | huAuditProductTU                   | createdTU                 | newCreatedCU              |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.M_HU_ID.Identifier |
      | GRSConfig_HU                        | createdTU              |
      | GRSConfig_HU                        | createdCU              |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | GRSConfig_HU                        |