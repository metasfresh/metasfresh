@workflowTests
@from:cucumber
Feature: workflow rest controller tests

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                  | OPT.Description              | OPT.IsActive |
      | ps_1       | p_pricing_system_name | p_pricing_system_value | p_pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | p_price_list_name | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_1      | pl_1                      | p_salesOrder-PLV | 2021-04-01 |

  @from:cucumber
  Scenario: create and start picking workflow
    And metasfresh contains M_Products:
      | Identifier  | Name                       |
      | p_1_picking | p_picking_workflow_product |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_picking | plv_1                             | p_1_picking             | 5.0      | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name            | HU_UnitType | IsCurrent |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemCU                 | packingVersionCU              | 1   | HU       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductCU                        | huPiItemCU                 | p_1_picking             | 1   | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_1               | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_1               | inventoryLine_1               | p_1_picking             | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'inventory_1'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_1               | huProductCU        |
    And update HU clearance status
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | huProductCU        | Cleared         | Cleared HU        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name              | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | cl_Endcustomer_18 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | cl_bPLocation | endcustomer_1            | true                | true         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_2        | true    | endcustomer_1            | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_1_picking             | 1          |
    And the order identified by o_2 is completed

    And create JsonWFProcessStartRequest for picking and store it as the request payload in the test context
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | o_2                   | endcustomer_1            | l_1                               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And create JsonPickingEventsList from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | QtyPicked |
      | 1         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/events' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate picking candidate and shipment schedule after picking workflow
      | C_OrderLine_ID.Identifier | QtyPicked |
      | ol_2                      | 1         |

  @from:cucumber
  Scenario: create and start distribution workflow
    And metasfresh contains M_Products:
      | Identifier       | Name                    |
      | p_1_distribution | p_dist_workflow_product |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_distribution | plv_1                             | p_1_distribution        | 5.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name              | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | cl_Endcustomer_20 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | cl_bPLocation | endcustomer_1            | true                | true         |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier   | Name            |
      | huDistributionVirtualPI | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier   | Name            | HU_UnitType | IsCurrent |
      | distributionVersionCU         | huDistributionVirtualPI | No Packing Item | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemCU                 | distributionVersionCU         | 1   | HU       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductCU                        | huPiItemCU                 | p_1_distribution        | 1   | 2022-01-01 |

    And metasfresh contains M_Warehouses:
      | M_Warehouse_ID.Identifier | Name             | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsInTransit |
      | fromWarehouse             | fromWarehouse    | endcustomer_1            | l_1                               | false       |
      | toWarehouse               | toWarehouse      | endcustomer_1            | l_1                               | false       |
      | transitWarehouse          | transitWarehouse | endcustomer_1            | l_1                               | true        |

    And metasfresh contains M_Locators:
      | Identifier              | M_Warehouse_ID.Identifier | Value                   |
      | fromWarehouseLocator    | fromWarehouse             | fromWarehouseLocator    |
      | toWarehouseLocator      | toWarehouse               | toWarehouseLocator      |
      | transitWarehouseLocator | transitWarehouse          | transitWarehouseLocator |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory_1               | 2021-10-12   | fromWarehouse  |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory_1               | inventoryLine_1               | p_1_distribution        | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'inventory_1'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_1               | huProductCU        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And metasfresh contains DD_Orders:
      | Identifier | C_BPartner_ID.Identifier | M_Warehouse_ID.From.Identifier | M_Warehouse_ID.To.Identifier | M_Warehouse_ID.Transit.Identifier | C_DocType_ID.Name    | S_Resource_ID.Identifier |
      | dd_order_1 | endcustomer_1            | fromWarehouse                  | toWarehouse                  | transitWarehouse                  | Distributionsauftrag | testResource             |
    And metasfresh contains DD_OrderLines:
      | Identifier | M_Product_ID.Identifier | DD_Order_ID.Identifier | QtyEntered | M_Locator_ID.Identifier | M_LocatorTo_ID.Identifier |
      | dd_ol_1    | p_1_distribution        | dd_order_1             | 1          | fromWarehouseLocator    | toWarehouseLocator        |
    And the dd_order identified by dd_order_1 is completed

    And create JsonWFProcessStartRequest for distribution and store it as the request payload in the test context
      | DD_Order_ID.Identifier |
      | dd_order_1             |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonDistributionEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event    |
      | PickFrom |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonWFProcessStartRequest for distribution and store it as the request payload in the test context
      | DD_Order_ID.Identifier |
      | dd_order_1             |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonDistributionEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event  |
      | DropTo |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate order move schedule and movement after distribution workflow
      | DD_OrderLine_ID.Identifier | QtyPicked | M_HU_ID.Identifier | M_Locator_ID.Identifier |
      | dd_ol_1                    | 1         | huProductCU        | toWarehouseLocator      |

  @from:cucumber
  Scenario: create and start manufacturing workflow

    And metasfresh contains M_Products:
      | Identifier              | Value                                | Name                                 |
      | huProduct               | huProduct                            | huProduct                            |
      | manufacturingProduct_HU | manufacturingProduct_IssueClearedHUs | manufacturingProduct_IssueClearedHUs |
    And metasfresh contains M_ProductPrices
      | Identifier                | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | manufacturingProductPrice | plv_1                             | huProduct               | 5.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | cl_Endcustomer_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | cl_bPLocation | endcustomer_1            | true                | true         |

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
      | huPiItemLU                 | packingVersionLU              | 1   | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | manufacturingProduct_HU | 1   | 2022-01-01 |

    And metasfresh contains M_Warehouses:
      | M_Warehouse_ID.Identifier | Name         | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsInTransit |
      | warehouseStd              | warehouseStd | endcustomer_1            | l_1                               | false       |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | huProduct_inventory       | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | manufacturingProduct_HU | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 1     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And update HU clearance status
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | createdLU          | Cleared         | Cleared HU        |

    And metasfresh contains PP_Product_BOM
      | Identifier        | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_manufacturing | huProduct               | 2021-01-02 | bomVersions_manufacturing            |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bom_l_manufacturing1 | bom_manufacturing            | manufacturingProduct_HU | 2021-01-02 | 1        |

    And the PP_Product_BOM identified by bom_manufacturing is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | huProduct               | bomVersions_manufacturing                | false        |

    And metasfresh contains PP_Order_Candidates
      | Identifier | M_Product_ID.Identifier | M_Warehouse_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    |
      | oc_1       | huProduct               | warehouseStd              | bom_manufacturing            | ppln_1                            | 540006        | 1          | 1            | 0            | PCE               | 2021-01-12T21:00:00Z | 2021-01-12T21:00:00Z |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.AD_Workflow_ID |
      | ppOrder_manufacturing  | MOP         | huProduct               | 1          | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                | 540114             |

    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderBOMLine_1               | ppOrder_manufacturing  | manufacturingProduct_HU | 1            | false           | PCE               | CO            |

    And select M_HU to be issued for productionOrder
      | M_HU_ID.Identifier | PP_Order_Qty_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.MovementDate     |
      | createdLU          | pp_order_qty_1             | ppOrderBOMLine_1               | 2022-03-31T13:30:13Z |

    And validate PP_Order_Qty records
      | PP_Order_Qty_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | PP_Order_BOMLine_ID.Identifier | OPT.Processed |
      | pp_order_qty_1             | createdLU          | manufacturingProduct_HU | ppOrderBOMLine_1               | false         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder_manufacturing  |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.ClearanceStatus | OPT.ClearanceNote |
      | createdLU          | packingVersionLU              |                                        | D        | C                   | Cleared HU        |
      | createdTU          | packingVersionTU              | huProductTU                            | D        | C                   | Cleared HU        |
      | newCreatedCU       | packingVersionCU              |                                        | D        | C                   | Cleared HU        |

    And create JsonWFProcessStartRequest for manufacturing and store it as the request payload in the test context
      | PP_Order_ID.Identifier |
      | ppOrder_manufacturing  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event   |
      | IssueTo |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonWFProcessStartRequest for manufacturing and store it as the request payload in the test context
      | PP_Order_ID.Identifier |
      | ppOrder_manufacturing  |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event       |
      | ReceiveFrom |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate cost collector after manufacturing workflow
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | PP_Order_BOMLine_ID.Identifier |
      | ppOrder_manufacturing  | huProduct               |                                |
      | ppOrder_manufacturing  | manufacturingProduct_HU | ppOrderBOMLine_1               |