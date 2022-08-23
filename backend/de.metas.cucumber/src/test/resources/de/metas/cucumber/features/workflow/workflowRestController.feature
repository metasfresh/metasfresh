@workflowTests
@from:cucumber
Feature: workflow rest controller tests

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier            | Name                      | Value                      | OPT.Description                  | OPT.IsActive |
      | workflowPricingSystem | workflowPricingSystemName | workflowPricingSystemValue | workflowPricingSystemDescription | true         |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | workflowPriceList | workflowPricingSystem         | DE                        | EUR                 | workflowPriceListName | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier               | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | workflowPriceListVersion | workflowPriceList         | workflowPriceListVersionName | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier      | Name            |
      | workflowProduct | workflowProduct |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | workflowProductPrice | workflowPriceListVersion          | workflowProduct         | 5.0      | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huWorkflowPacking     | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name            | HU_UnitType | IsCurrent |
      | workflowPackingVersion        | huWorkflowPacking     | No Packing Item | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | workflowItem               | workflowPackingVersion        | 1   | HU       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | workflowItemProduct                | workflowItem               | workflowProduct         | 1   | 2022-01-01 |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

  @from:cucumber
  Scenario: create and start picking workflow
    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | pickingCustomer | pickingCustomer | N            | Y              | workflowPricingSystem         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | pickingCustomerLocation | cl_bPLocation | pickingCustomer          | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | workflowInventory         | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | workflowInventory         | workflowInventoryLine         | workflowProduct         | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'workflowInventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | workflowInventoryLine         | workflowProductHU  |
    And update HU clearance status
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | workflowProductHU  | Cleared         | Cleared HU        |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | pickingOrder | true    | pickingCustomer          | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | pickingOrderLine | pickingOrder          | workflowProduct         | 1          |

    And the order identified by pickingOrder is completed

    And create JsonWFProcessStartRequest for picking and store it as the request payload in the test context
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | pickingOrder          | pickingCustomer          | pickingCustomerLocation           |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract picking information:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | OPT.WorkflowStep.Identifier | OPT.WorkflowStepQRCode.Identifier |
      | pickingWorkflow            | workflowPickingActivity     | workflowPickingStep         | workflowPickingQRCode             |
    And create JsonPickingEventsList and store it in context:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | WorkflowStep.Identifier | WorkflowStepQRCode.Identifier | QtyPicked |
      | pickingWorkflow            | workflowPickingActivity     | workflowPickingStep     | workflowPickingQRCode         | 1         |

    And the metasfresh REST-API endpoint path 'api/v2/picking/events' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_M_ShipmentSchedule, I_M_Picking_Candidate and I_M_HU after picking order workflow
      | C_OrderLine_ID.Identifier | QtyPicked |
      | pickingOrderLine          | 1         |

  @from:cucumber
  Scenario: create and start distribution workflow

    And metasfresh contains C_BPartners without locations:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | distributionCustomer | distributionCustomer | N            | Y              | workflowPricingSystem         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | distributionCustomerLocation | cl_bPLocation | distributionCustomer     | true                | true         |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier    | Name                         | Value                        | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsInTransit |
      | distributionFromWarehouse    | distributionFromWarehouse    | distributionFromWarehouse    | distributionCustomer     | distributionCustomerLocation      | false       |
      | distributionToWarehouse      | distributionToWarehouse      | distributionToWarehouse      | distributionCustomer     | distributionCustomerLocation      | false       |
      | distributionTransitWarehouse | distributionTransitWarehouse | distributionTransitWarehouse | distributionCustomer     | distributionCustomerLocation      | true        |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier             | M_Warehouse_ID.Identifier    | Value                               |
      | distributionFromWarehouseLocator    | distributionFromWarehouse    | distributionFromWarehouseLocator    |
      | distributionToWarehouseLocator      | distributionToWarehouse      | distributionToWarehouseLocator      |
      | distributionTransitWarehouseLocator | distributionTransitWarehouse | distributionTransitWarehouseLocator |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID            |
      | distributionInventory     | 2021-10-12   | distributionFromWarehouse |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | distributionInventory     | distributionInventoryLine     | workflowProduct         | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'distributionInventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier            |
      | distributionInventoryLine     | workflowProductHUDistribution |

    And metasfresh contains DD_Orders:
      | Identifier        | C_BPartner_ID.Identifier | M_Warehouse_ID.From.Identifier | M_Warehouse_ID.To.Identifier | M_Warehouse_ID.Transit.Identifier | C_DocType_ID.Name    | S_Resource_ID.Identifier |
      | distributionOrder | distributionCustomer     | distributionFromWarehouse      | distributionToWarehouse      | distributionTransitWarehouse      | Distributionsauftrag | testResource             |
    And metasfresh contains DD_OrderLines:
      | Identifier            | M_Product_ID.Identifier | DD_Order_ID.Identifier | QtyEntered | M_Locator_ID.Identifier          | M_LocatorTo_ID.Identifier      |
      | distributionOrderLine | workflowProduct         | distributionOrder      | 1          | distributionFromWarehouseLocator | distributionToWarehouseLocator |

    And the dd_order identified by distributionOrder is completed

    And create JsonWFProcessStartRequest for distribution and store it as the request payload in the test context
      | DD_Order_ID.Identifier |
      | distributionOrder      |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract distribution information:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | OPT.WorkflowStep.Identifier | OPT.WorkflowStepQRCode.Identifier |
      | distributionWorkflow       | workflowDistributionActivity | workflowDistributionStep    | workflowDistributionQRCode        |
    And create JsonDistributionEvent and store it in context:
      | Event    | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowStep.Identifier  | WorkflowStepQRCode.Identifier |
      | PickFrom | distributionWorkflow       | workflowDistributionActivity | workflowDistributionStep | workflowDistributionQRCode    |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonDistributionEvent and store it in context:
      | Event  | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowStep.Identifier  |
      | DropTo | distributionWorkflow       | workflowDistributionActivity | workflowDistributionStep |
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
      | Identifier                    | Value                         | Name                          |
      | manufacturingProduct          | manufacturingProduct          | manufacturingProduct          |
      | manufacturingProductComponent | manufacturingProductComponent | manufacturingProductComponent |
    And metasfresh contains M_ProductPrices
      | Identifier                | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | manufacturingProductPrice | workflowPriceListVersion          | manufacturingProduct    | 5.0      | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier  | Name            |
      | huPackingLU            | huPackingLU     |
      | huPackingTU            | huPackingTU     |
      | huManufacturingPacking | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier  | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU            | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU            | packingVersionTU | TU          | Y         |
      | manufacturingPackingVersion   | huManufacturingPacking | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 1   | HU       | huPackingTU                      |
      | manufacturingItem          | packingVersionTU              | 1   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | manufacturingProductItem           | manufacturingItem          | manufacturingProduct    | 1   | 2022-01-01 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | manufacturingInventory    | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier       | M_Product_ID.Identifier       | QtyBook | QtyCount | UOM.X12DE355 |
      | manufacturingInventory    | manufacturingComponentInventoryLine | manufacturingProductComponent | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'manufacturingInventory'

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier       | M_HU_ID.Identifier              |
      | manufacturingComponentInventoryLine | createdManufacturingComponentHU |

    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | manufacturingBOM | manufacturingProduct    | 2021-01-02 | manufacturingBOMVersion              |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier       | ValidFrom  | QtyBatch |
      | manufacturingBOMLine | manufacturingBOM             | manufacturingProductComponent | 2021-01-02 | 1        |

    And the PP_Product_BOM identified by manufacturingBOM is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier                   | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | manufacturingProductPlanning | manufacturingProduct    | manufacturingBOMVersion                  | false        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.AD_Workflow_ID |
      | manufacturingOrder     | MOP         | manufacturingProduct    | 1          | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                | 540114             |

    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier       | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | manufacturingBOMLine           | manufacturingOrder     | manufacturingProductComponent | 1            | false           | PCE               | CO            |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |

    And create JsonWFProcessStartRequest for manufacturing and store it as the request payload in the test context
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract manufacturing/materialissue information:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier        | OPT.WorkflowStep.Identifier    | OPT.WorkflowStepQRCode.Identifier |
      | manufacturingWorkflow      | workflowManufacturingIssueActivity | workflowManufacturingIssueStep | workflowManufacturingQRCode       |
    And process response and extract manufacturing/materialreceipt information:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | OPT.WorkflowLine.Identifier      | OPT.WorkflowReceivingTargetValues.Identifier |
      | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues                |


    And create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event   | WorkflowProcess.Identifier | WorkflowActivity.Identifier        | WorkflowStep.Identifier        | WorkflowStepQRCode.Identifier |
      | IssueTo | manufacturingWorkflow      | workflowManufacturingIssueActivity | workflowManufacturingIssueStep | workflowManufacturingQRCode   |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context
      | Event       | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_PP_Cost_Collector after manufacturing order workflow
      | PP_Order_ID.Identifier | M_Product_ID.Identifier       | PP_Order_BOMLine_ID.Identifier |
      | manufacturingOrder     | manufacturingProduct          |                                |
      | manufacturingOrder     | manufacturingProductComponent | manufacturingBOMLine           |
    And validate I_PP_Order_Qty after manufacturing order workflow
      | PP_Order_ID.Identifier | M_Product_ID.Identifier       | PP_Order_BOMLine_ID.Identifier |
      | manufacturingOrder     | manufacturingProduct          |                                |
      | manufacturingOrder     | manufacturingProductComponent | manufacturingBOMLine           |