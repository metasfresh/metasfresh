@workflowTests
@from:cucumber
Feature: workflow rest controller tests

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh contains M_PricingSystems
      | Identifier            | Name                      | Value                      | OPT.Description                  | OPT.IsActive |
      | workflowPricingSystem | workflowPricingSystemName | workflowPricingSystemValue | workflowPricingSystemDescription | true         |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | workflowPriceList | workflowPricingSystem         | DE                        | EUR                 | workflowPriceListName | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier               | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | workflowPriceListVersion | workflowPriceList         | workflowPriceListVersionName | 2021-04-01 |

  @from:cucumber
  Scenario: create and start picking workflow
    And metasfresh contains M_Products:
      | Identifier     | Name           |
      | pickingProduct | pickingProduct |
    And metasfresh contains M_ProductPrices
      | Identifier          | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pickingProductPrice | workflowPriceListVersion          | pickingProduct          | 5.0      | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPickingPacking      | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name            | HU_UnitType | IsCurrent |
      | pickingPackingVersion         | huPickingPacking      | No Packing Item | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | pickingItem                | pickingPackingVersion         | 1   | HU       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | pickingItemProduct                 | pickingItem                | pickingProduct          | 1   | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | pickingInventory          | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | pickingInventory          | pickingInventoryLine          | pickingProduct          | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'pickingInventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | pickingInventoryLine          | pickingItemProduct |
    And update HU clearance status
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | pickingItemProduct | Cleared         | Cleared HU        |

    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | pickingCustomer | pickingCustomer | N            | Y              | workflowPricingSystem         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | pickingCustomerLocation | cl_bPLocation | pickingCustomer          | true                | true         |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | pickingOrder | true    | pickingCustomer          | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | pickingOrderLine | pickingOrder          | pickingProduct          | 1          |
    And the order identified by pickingOrder is completed

    And create JsonWFProcessStartRequest for picking and store it as the request payload in the test context
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | pickingOrder          | pickingCustomer          | pickingCustomerLocation           |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And create JsonPickingEventsList from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | QtyPicked |
      | 1         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/events' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_M_ShipmentSchedule, I_M_Picking_Candidate and I_M_HU after picking order workflow
      | C_OrderLine_ID.Identifier | QtyPicked |
      | pickingOrderLine          | 1         |

  @from:cucumber
  Scenario: create and start distribution workflow
    And metasfresh contains M_Products:
      | Identifier          | Name                |
      | distributionProduct | distributionProduct |
    And metasfresh contains M_ProductPrices
      | Identifier               | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | distributionProductPrice | workflowPriceListVersion          | distributionProduct     | 5.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | distributionCustomer | distributionCustomer | N            | Y              | workflowPricingSystem         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | distributionCustomerLocation | cl_bPLocation | distributionCustomer     | true                | true         |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huDistributionPacking | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name            | HU_UnitType | IsCurrent |
      | distributionPackingVersion    | huDistributionPacking | No Packing Item | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | distributionItem           | distributionPackingVersion    | 1   | HU       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | distributionItemProduct            | distributionItem           | distributionProduct     | 1   | 2022-01-01 |

    And metasfresh contains M_Warehouses:
      | M_Warehouse_ID.Identifier    | Name                         | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsInTransit |
      | distributionFromWarehouse    | distributionFromWarehouse    | distributionCustomer     | distributionCustomerLocation      | false       |
      | distributionToWarehouse      | distributionToWarehouse      | distributionCustomer     | distributionCustomerLocation      | false       |
      | distributionTransitWarehouse | distributionTransitWarehouse | distributionCustomer     | distributionCustomerLocation      | true        |

    And metasfresh contains M_Locators:
      | Identifier                          | M_Warehouse_ID.Identifier    | Value                               |
      | distributionFromWarehouseLocator    | distributionFromWarehouse    | distributionFromWarehouseLocator    |
      | distributionToWarehouseLocator      | distributionToWarehouse      | distributionToWarehouseLocator      |
      | distributionTransitWarehouseLocator | distributionTransitWarehouse | distributionTransitWarehouseLocator |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID            |
      | distributionInventory     | 2021-10-12   | distributionFromWarehouse |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | distributionInventory     | distributionInventoryLine     | distributionProduct     | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'distributionInventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier      |
      | distributionInventoryLine     | huDistributionProductCU |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And metasfresh contains DD_Orders:
      | Identifier        | C_BPartner_ID.Identifier | M_Warehouse_ID.From.Identifier | M_Warehouse_ID.To.Identifier | M_Warehouse_ID.Transit.Identifier | C_DocType_ID.Name    | S_Resource_ID.Identifier |
      | distributionOrder | distributionCustomer     | distributionFromWarehouse      | distributionToWarehouse      | distributionTransitWarehouse      | Distributionsauftrag | testResource             |
    And metasfresh contains DD_OrderLines:
      | Identifier            | M_Product_ID.Identifier | DD_Order_ID.Identifier | QtyEntered | M_Locator_ID.Identifier          | M_LocatorTo_ID.Identifier      |
      | distributionOrderLine | distributionProduct     | distributionOrder      | 1          | distributionFromWarehouseLocator | distributionToWarehouseLocator |
    And the dd_order identified by distributionOrder is completed

    And create JsonWFProcessStartRequest for distribution and store it as the request payload in the test context
      | DD_Order_ID.Identifier |
      | distributionOrder      |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonDistributionEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event    |
      | PickFrom |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonWFProcessStartRequest for distribution and store it as the request payload in the test context
      | DD_Order_ID.Identifier |
      | distributionOrder      |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonDistributionEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event  |
      | DropTo |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_DD_Order_MoveSchedule after distribution order workflow
      | DD_OrderLine_ID.Identifier | QtyPicked |
      | distributionOrderLine      | 1         |
    And validate I_M_Movement and I_M_HU after distribution order workflow
      | DD_OrderLine_ID.Identifier | M_Locator_ID.Identifier        |
      | distributionOrderLine      | distributionToWarehouseLocator |

  @from:cucumber
  Scenario: create and start manufacturing workflow

    And metasfresh contains M_Products:
      | Identifier                      | Value                           | Name                            |
      | huManufacturingProduct          | huManufacturingProduct          | huManufacturingProduct          |
      | huManufacturingProductComponent | huManufacturingProductComponent | huManufacturingProductComponent |
    And metasfresh contains M_ProductPrices
      | Identifier                | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | manufacturingProductPrice | workflowPriceListVersion          | huManufacturingProduct  | 5.0      | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier    | Name                     |
      | huManufacturingPackingLU | huManufacturingPackingLU |
      | huManufacturingPackingTU | huManufacturingPackingTU |
      | huManufacturingPacking   | No Packing Item          |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier    | Name             | HU_UnitType | IsCurrent |
      | manufacturingPackingVersionLU | huManufacturingPackingLU | packingVersionLU | LU          | Y         |
      | manufacturingPackingVersionTU | huManufacturingPackingTU | packingVersionTU | TU          | Y         |
      | manufacturingPackingVersionCU | huManufacturingPacking   | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | manufacturingItemLU        | manufacturingPackingVersionLU | 1   | HU       | huManufacturingPackingTU         |
      | manufacturingItemTU        | manufacturingPackingVersionTU | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier         | Qty | ValidFrom  |
      | manufacturingProductTU             | manufacturingItemTU        | huManufacturingProduct          | 1   | 2022-01-01 |
      | manufacturingProductComponentTU    | manufacturingItemTU        | huManufacturingProductComponent | 1   | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | manufacturingInventory    | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | manufacturingInventory    | manufacturingInventoryLine    | huManufacturingProduct  | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'manufacturingInventory'

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier     |
      | manufacturingInventoryLine    | createdManufacturingCU |

    And transform CU to new TUs
      | sourceCU.Identifier    | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdManufacturingCU | 1     | manufacturingProductComponentTU    | createdManufacturingTU    | createdManufacturingCU    |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier     | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdManufacturingTU | manufacturingProductComponentTU        |

    And transform TU to new LUs
      | sourceTU.Identifier    | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdManufacturingTU | 1     | manufacturingItemLU        | createdManufacturingLU    |

    And update HU clearance status
      | M_HU_ID.Identifier     | ClearanceStatus | OPT.ClearanceNote |
      | createdManufacturingLU | Cleared         | Cleared HU        |

    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | manufacturingBOM | huManufacturingProduct  | 2021-01-02 | manufacturingBOMVersion              |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier         | ValidFrom  | QtyBatch |
      | manufacturingBOMLine | manufacturingBOM             | huManufacturingProductComponent | 2021-01-02 | 1        |

    And the PP_Product_BOM identified by manufacturingBOM is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier                   | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | manufacturingProductPlanning | huManufacturingProduct  | manufacturingBOMVersion                  | false        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.AD_Workflow_ID |
      | manufacturingOrder     | MOP         | huManufacturingProduct  | 1          | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                | 540114             |

    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier         | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | manufacturingBOMLine           | manufacturingOrder     | huManufacturingProductComponent | 1            | false           | PCE               | CO            |

    And select M_HU to be issued for productionOrder
      | M_HU_ID.Identifier     | PP_Order_Qty_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.MovementDate     |
      | createdManufacturingLU | manufacturingOrderQty      | manufacturingBOMLine           | 2022-03-31T13:30:13Z |

    And validate PP_Order_Qty records
      | PP_Order_Qty_ID.Identifier | M_HU_ID.Identifier     | M_Product_ID.Identifier         | PP_Order_BOMLine_ID.Identifier | OPT.Processed |
      | manufacturingOrderQty      | createdManufacturingLU | huManufacturingProductComponent | manufacturingBOMLine           | false         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |

    Then validate M_HUs:
      | M_HU_ID.Identifier     | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus |
      | createdManufacturingLU | manufacturingPackingVersionLU |                                        | D        |
      | createdManufacturingTU | manufacturingPackingVersionTU | manufacturingProductComponentTU        | D        |
      | createdManufacturingCU | manufacturingPackingVersionCU |                                        | D        |

    And create JsonWFProcessStartRequest for manufacturing and store it as the request payload in the test context
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event   |
      | IssueTo |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonWFProcessStartRequest for manufacturing and store it as the request payload in the test context
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event       | OPT.M_HU_PI_ID.Identifier |
      | ReceiveFrom | huManufacturingPackingTU  |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_PP_Cost_Collector after manufacturing order workflow
      | PP_Order_ID.Identifier | M_Product_ID.Identifier         | PP_Order_BOMLine_ID.Identifier |
      | manufacturingOrder     | huManufacturingProduct          |                                |
      | manufacturingOrder     | huManufacturingProductComponent | manufacturingBOMLine           |
    And validate I_PP_Order_Qty after manufacturing order workflow
      | PP_Order_ID.Identifier | M_Product_ID.Identifier         | PP_Order_BOMLine_ID.Identifier |
      | manufacturingOrder     | huManufacturingProduct          |                                |
      | manufacturingOrder     | huManufacturingProductComponent | manufacturingBOMLine           |