@from:cucumber
@ignore
@ghActions:run_on_executor7
Feature: workflow rest controller tests

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
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
      | Identifier             | Name                   |
      | workflowProduct_230822 | workflowProduct_230822 |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | workflowProductPrice | workflowPriceListVersion          | workflowProduct_230822  | 5.0      | PCE               | Normal                        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | N                   | DO_NOT_CREATE        |

  @Id:S0179_100
  @from:cucumber
  Scenario: create and start picking workflow

    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | pickingCustomer | pickingCustomer | N            | Y              | workflowPricingSystem         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN         | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | pickingCustomerLocation | picking_GLN | pickingCustomer          | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | workflowInventory         | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | workflowInventory         | workflowInventoryLine         | workflowProduct_230822  | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'workflowInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | workflowInventoryLine         | workflowProductHU  |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | pickingOrder | true    | pickingCustomer          | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | pickingOrderLine | pickingOrder          | workflowProduct_230822  | 1          |

    And the order identified by pickingOrder is completed

    And reserve HU to order
      | M_HU_ID.Identifier | C_OrderLine_ID.Identifier |
      | workflowProductHU  | pickingOrderLine          |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | pickingShipmentSchedule | pickingOrderLine          | N             |

    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | pickingOrder          | pickingCustomer          | pickingCustomerLocation           |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | wf1                        | a1                          | line1                  | step1                  | QR                           |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | step1                  | QR                           | 1         |

    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate picking candidate for shipment schedule:
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | PickStatus |
      | pickingShipmentSchedule          | 1         | A          |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly |
      | pickingShipmentSchedule          | 1         | false     | false                       |
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | workflowProductHU  | S        | Y        |

  @Id:S0179_110
  @from:cucumber
  Scenario: create and start distribution workflow

    And metasfresh contains C_BPartners without locations:
      | Identifier           | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | distributionCustomer | distributionCustomer_230822 | N            | Y              | workflowPricingSystem         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | GLN              | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | distributionCustomerLocation | distribution_GLN | distributionCustomer     | true                | true         |

    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier    | Name                         | Value                               | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsInTransit |
      | distributionFromWarehouse    | distributionFromWarehouse    | distributionFromWarehouse_230822    | distributionCustomer     | distributionCustomerLocation      | false       |
      | distributionToWarehouse      | distributionToWarehouse      | distributionToWarehouse_230822      | distributionCustomer     | distributionCustomerLocation      | false       |
      | distributionTransitWarehouse | distributionTransitWarehouse | distributionTransitWarehouse_230822 | distributionCustomer     | distributionCustomerLocation      | true        |

    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier             | M_Warehouse_ID.Identifier    | Value                             |
      | distributionFromWarehouseLocator    | distributionFromWarehouse    | distributionFromLocator_230822    |
      | distributionToWarehouseLocator      | distributionToWarehouse      | distributionToLocator_230822      |
      | distributionTransitWarehouseLocator | distributionTransitWarehouse | distributionTransitLocator_230822 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID            |
      | distributionInventory     | 2021-10-12   | distributionFromWarehouse |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | distributionInventory     | distributionInventoryLine     | workflowProduct_230822  | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'distributionInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier            |
      | distributionInventoryLine     | workflowProductHUDistribution |

    And metasfresh contains DD_Orders:
      | Identifier        | C_BPartner_ID.Identifier | M_Warehouse_ID.From.Identifier | M_Warehouse_ID.To.Identifier | M_Warehouse_ID.Transit.Identifier | C_DocType_ID.Name    | S_Resource_ID.Identifier |
      | distributionOrder | distributionCustomer     | distributionFromWarehouse      | distributionToWarehouse      | distributionTransitWarehouse      | Distributionsauftrag | testResource             |
    And metasfresh contains DD_OrderLines:
      | Identifier            | M_Product_ID.Identifier | DD_Order_ID.Identifier | QtyEntered | M_Locator_ID.Identifier          | M_LocatorTo_ID.Identifier      |
      | distributionOrderLine | workflowProduct_230822  | distributionOrder      | 1          | distributionFromWarehouseLocator | distributionToWarehouseLocator |

    And the dd_order identified by distributionOrder is completed

    And create JsonWFProcessStartRequest for distribution and store it in context as request payload:
      | DD_Order_ID.Identifier |
      | distributionOrder      |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract distribution step and pickFrom HU distribution candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowStep.Identifier  | WorkflowStepQRCode.Identifier |
      | distributionWorkflow       | workflowDistributionActivity | workflowDistributionStep | workflowDistributionQRCode    |
    And create JsonDistributionEvent and store it in context as request payload:
      | Event    | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowStep.Identifier  | WorkflowStepQRCode.Identifier |
      | PickFrom | distributionWorkflow       | workflowDistributionActivity | workflowDistributionStep | workflowDistributionQRCode    |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonDistributionEvent and store it in context as request payload:
      | Event  | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowStep.Identifier  |
      | DropTo | distributionWorkflow       | workflowDistributionActivity | workflowDistributionStep |
    And the metasfresh REST-API endpoint path 'api/v2/distribution/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate DD_Order_MoveSchedule
      | DD_OrderLine_ID.Identifier | QtyPicked |
      | distributionOrderLine      | 1         |
    And validate I_M_MovementLine for distribution order line
      | DD_OrderLine_ID.Identifier | MovementQty | M_LocatorTo_ID.Identifier           |
      | distributionOrderLine      | 1           | distributionTransitWarehouseLocator |
      | distributionOrderLine      | 1           | distributionToWarehouseLocator      |
    And M_HU are validated:
      | M_HU_ID.Identifier            | HUStatus | IsActive | OPT.M_Locator_ID.Identifier    |
      | workflowProductHUDistribution | A        | Y        | distributionToWarehouseLocator |

  @Id:S0179_120
  @from:cucumber
  Scenario: create and start manufacturing workflow

    And metasfresh contains M_Products:
      | Identifier                    | Value                | Name                          |
      | manufacturingProduct          | mwp_230822           | manufacturingWorkflowProduct  |
      | manufacturingProductComponent | mwp_component_230822 | manufacturingProductComponent |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier    | Name               |
      | huPackingManufacturingLU | huPackingLU_230822 |
      | huPackingManufacturingTU | huPackingTU_230822 |
      | huManufacturingPacking   | No Packing Item    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier    | Name                       | HU_UnitType | IsCurrent |
      | packingVersionManufacturingLU | huPackingManufacturingLU | huPackingLU_230822_version | LU          | Y         |
      | packingVersionManufacturingTU | huPackingManufacturingTU | huPackingTU_230822_version | TU          | Y         |
      | manufacturingPackingVersion   | huManufacturingPacking   | No Packing Item            | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemManufacturingLU    | packingVersionManufacturingLU | 1   | HU       | huPackingManufacturingTU         |
      | huPiItemManufacturingTU    | packingVersionManufacturingTU | 1   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | manufacturingProductItem           | huPiItemManufacturingTU    | manufacturingProduct    | 1   | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | manufacturingInventory    | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier       | M_Product_ID.Identifier       | QtyBook | QtyCount | UOM.X12DE355 |
      | manufacturingInventory    | manufacturingComponentInventoryLine | manufacturingProductComponent | 0       | 1        | PCE          |
    And complete inventory with inventoryIdentifier 'manufacturingInventory'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier       | M_HU_ID.Identifier              |
      | manufacturingComponentInventoryLine | createdManufacturingComponentHU |

    And metasfresh contains PP_Product_BOM
      | Identifier       | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | manufacturingBOM | manufacturingProduct    | 2021-01-02 | manufacturingBOMVersion              |
    And metasfresh contains PP_Product_BOMLines
      | Identifier           | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier       | ValidFrom  | QtyBatch |
      | manufacturingBOMLine | manufacturingBOM             | manufacturingProductComponent | 2021-01-02 | 1        |

    And the PP_Product_BOM identified by manufacturingBOM is completed

    And load AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                   |
      | mobileWorkflow            | mobileUI_workflow_test |
    And metasfresh contains PP_Product_Plannings
      | Identifier                   | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | manufacturingProductPlanning | mobileWorkflow                | manufacturingProduct    | manufacturingBOMVersion                  | false        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | manufacturingOrder     | MOP         | manufacturingProduct    | 1          | testResource             | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | 2022-03-31T23:59:00.00Z | Y                | manufacturingProductPlanning          |

    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier       | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | manufacturingBOMLine           | manufacturingOrder     | manufacturingProductComponent | 1            | false           | PCE               | CO            |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |

    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | manufacturingOrder     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract manufacturing step and issueTo HU manufacturing candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier        | WorkflowStep.Identifier        | WorkflowStepQRCode.Identifier |
      | manufacturingWorkflow      | workflowManufacturingIssueActivity | workflowManufacturingIssueStep | workflowManufacturingQRCode   |
    And process response and extract manufacturing line and receiving target values:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |

    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event   | WorkflowProcess.Identifier | WorkflowActivity.Identifier        | WorkflowStep.Identifier        | WorkflowStepQRCode.Identifier |
      | IssueTo | manufacturingWorkflow      | workflowManufacturingIssueActivity | workflowManufacturingIssueStep | workflowManufacturingQRCode   |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonManufacturingOrderEvent and store it in context as request payload:
      | Event       | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
      | ReceiveFrom | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate I_PP_Cost_Collector
      | PP_Order_ID.Identifier | M_Product_ID.Identifier       | MovementQty | PP_Order_BOMLine_ID.Identifier |
      | manufacturingOrder     | manufacturingProduct          | 1           |                                |
      | manufacturingOrder     | manufacturingProductComponent | 1           | manufacturingBOMLine           |
    And validate I_PP_Order_Qty
      | PP_Order_ID.Identifier | M_Product_ID.Identifier       | Qty | PP_Order_BOMLine_ID.Identifier |
      | manufacturingOrder     | manufacturingProduct          | 1   |                                |
      | manufacturingOrder     | manufacturingProductComponent | 1   | manufacturingBOMLine           |