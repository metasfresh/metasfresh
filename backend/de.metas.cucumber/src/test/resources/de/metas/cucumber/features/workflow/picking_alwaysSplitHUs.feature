@from:cucumber
@ghActions:run_on_executor7
Feature: Picking workflow - always split HUs

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier                   | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | picking_pricing_system_17663 | picking_pricing_system_17663Name | picking_pricing_system_17663Value | picking_pricing_system_17663Description | true         |
    And metasfresh contains M_PriceLists
      | Identifier             | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | picking_picelist_17663 | picking_pricing_system_17663  | DE                        | EUR                 | picking_pricelist_17663 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier                     | M_PriceList_ID.Identifier | Name                               | ValidFrom  |
      | picking_pricelistversion_17663 | picking_picelist_17663    | picking_pricelistversion_17663Name | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier            | Name                  |
      | picking_product_17663 | picking_product_17663 |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | workflowProductPrice | picking_pricelistversion_17663    | picking_product_17663   | 5.0      | PCE               | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |
    And metasfresh contains C_BPartners without locations:
      | Identifier            | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | pickingCustomer_17663 | pickingCustomer_17663 | N            | Y              | picking_pricing_system_17663  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | GLN               | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | pickingCustomer_17663Location | picking_GLN_17663 | pickingCustomer_17663    | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | pickingInventory_17663    | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | pickingInventory_17663    | pickingInventory_17663Line    | picking_product_17663   | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'pickingInventory_17663'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier     |
      | pickingInventory_17663Line    | pickingProductHU_17663 |

    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder_17663 | true    | pickingCustomer_17663    | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | salesOrder_17663Line | salesOrder_17663      | picking_product_17663   | 10         |

    And the order identified by salesOrder_17663 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | pickingShipmentSchedule | salesOrder_17663Line      | N             |

  @from:cucumber
  @Id:S0406_10
  Scenario: AlwaysSplitHU = Y, pick less than available => split
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled |
      | Y                   | Y                       |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17663      | pickingCustomer_17663    | pickingCustomer_17663Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17663 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 1         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And M_HU are validated:
      | M_HU_ID.Identifier     | HUStatus | IsActive |
      | pickingProductHU_17663 | A        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier     | M_HU_ID.Identifier     | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_17663 | pickingProductHU_17663 | picking_product_17663   | 9   |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule pickingShipmentSchedule can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | pickingShipmentSchedule_QtyPicked_17663    |
    And load M_HU as splitHU_17663 from M_ShipmentSchedule_QtyPicked identified by pickingShipmentSchedule_QtyPicked_17663
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | splitHU_17663      | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier             | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | splitHU_pickingProductHU_Storage_17663 | splitHU_17663      | picking_product_17663   | 1   |

  @from:cucumber
  @Id:S0406_20
  Scenario: AlwaysSplitHU = Y, pick exactly what's available => split
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled |
      | Y                   | Y                       |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17663      | pickingCustomer_17663    | pickingCustomer_17663Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17663 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 10        |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And M_HU are validated:
      | M_HU_ID.Identifier     | HUStatus | IsActive |
      | pickingProductHU_17663 | D        | N        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier     | M_HU_ID.Identifier     | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_17663 | pickingProductHU_17663 | picking_product_17663   | 0   |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule pickingShipmentSchedule can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | pickingShipmentSchedule_QtyPicked_17663    |
    And load M_HU as splitHU_17663 from M_ShipmentSchedule_QtyPicked identified by pickingShipmentSchedule_QtyPicked_17663
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | splitHU_17663      | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier             | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | splitHU_pickingProductHU_Storage_17663 | splitHU_17663      | picking_product_17663   | 10  |

  @from:cucumber
  @Id:S0406_30
  Scenario: AlwaysSplitHU = Y, pick more than what's available => split due virtual inventory for the diff
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled |
      | Y                   | Y                       |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17663      | pickingCustomer_17663    | pickingCustomer_17663Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17663 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 11        |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And M_HU are validated:
      | M_HU_ID.Identifier     | HUStatus | IsActive |
      | pickingProductHU_17663 | D        | N        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier     | M_HU_ID.Identifier     | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_17663 | pickingProductHU_17663 | picking_product_17663   | 0   |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule pickingShipmentSchedule can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | pickingShipmentSchedule_QtyPicked_17663    |
    And load M_HU as splitHU_17663 from M_ShipmentSchedule_QtyPicked identified by pickingShipmentSchedule_QtyPicked_17663
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | splitHU_17663      | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier             | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_splitHU_17663 | splitHU_17663      | picking_product_17663   | 11  |

  @from:cucumber
  @Id:S0406_40
  Scenario: AlwaysSplitHU = N, pick less than available => split
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled |
      | Y                   | N                       |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17663      | pickingCustomer_17663    | pickingCustomer_17663Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17663 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 1         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And M_HU are validated:
      | M_HU_ID.Identifier     | HUStatus | IsActive |
      | pickingProductHU_17663 | A        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier     | M_HU_ID.Identifier     | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_17663 | pickingProductHU_17663 | picking_product_17663   | 9   |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule pickingShipmentSchedule can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | pickingShipmentSchedule_QtyPicked_17663    |
    And load M_HU as splitHU_17663 from M_ShipmentSchedule_QtyPicked identified by pickingShipmentSchedule_QtyPicked_17663
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | splitHU_17663      | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier             | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | splitHU_pickingProductHU_Storage_17663 | splitHU_17663      | picking_product_17663   | 1   |

  @from:cucumber
  @Id:S0406_50
  Scenario: AlwaysSplitHU = N, pick exactly what's available => DON'T split
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled |
      | Y                   | N                       |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17663      | pickingCustomer_17663    | pickingCustomer_17663Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17663 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 10        |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And M_HU are validated:
      | M_HU_ID.Identifier     | HUStatus | IsActive |
      | pickingProductHU_17663 | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier     | M_HU_ID.Identifier     | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_17663 | pickingProductHU_17663 | picking_product_17663   | 10  |
    And validate M_ShipmentSchedule_QtyPicked:
      | M_ShipmentSchedule_ID.Identifier | OPT.VHU_ID.Identifier  | IsAnonymousHuPickedOnTheFly |
      | pickingShipmentSchedule          | pickingProductHU_17663 | N                           |

  @from:cucumber
  @Id:S0406_60
  Scenario: AlwaysSplitHU = N, pick more than what's available => split due to virtual inventory for the diff
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled |
      | Y                   | N                       |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17663      | pickingCustomer_17663    | pickingCustomer_17663Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17663 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 11        |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And M_HU are validated:
      | M_HU_ID.Identifier     | HUStatus | IsActive |
      | pickingProductHU_17663 | D        | N        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier     | M_HU_ID.Identifier     | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_17663 | pickingProductHU_17663 | picking_product_17663   | 0   |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule pickingShipmentSchedule can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | pickingShipmentSchedule_QtyPicked_17663    |
    And load M_HU as splitHU_17663 from M_ShipmentSchedule_QtyPicked identified by pickingShipmentSchedule_QtyPicked_17663
    And M_HU are validated:
      | M_HU_ID.Identifier | HUStatus | IsActive |
      | splitHU_17663      | S        | Y        |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier             | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | pickingProductHU_Storage_splitHU_17663 | splitHU_17663      | picking_product_17663   | 11  |
