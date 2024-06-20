@from:cucumber
@ghActions:run_on_executor7
Feature: workflow rest controller tests

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier                   | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | picking_pricing_system_17497 | picking_pricing_system_17497Name | picking_pricing_system_17497Value | picking_pricing_system_17497Description | true         |
    And metasfresh contains M_PriceLists
      | Identifier             | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | picking_picelist_17497 | picking_pricing_system_17497  | DE                        | EUR                 | picking_pricelist_17497 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier                     | M_PriceList_ID.Identifier | Name                               | ValidFrom  |
      | picking_pricelistversion_17497 | picking_picelist_17497    | picking_pricelistversion_17497Name | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier            | Name                  |
      | picking_product_17497 | picking_product_17497 |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | workflowProductPrice | picking_pricelistversion_17497    | picking_product_17497   | 5.0      | PCE               | Normal                        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | N                   | CREATE_AND_COMPLETE  |
    And metasfresh contains C_BPartners without locations:
      | Identifier            | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | pickingCustomer_17497 | pickingCustomer_17497 | N            | Y              | picking_pricing_system_17497  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | GLN               | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | pickingCustomer_17497Location | picking_GLN_17497 | pickingCustomer_17497    | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | pickingInventory_17497    | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | pickingInventory_17497    | pickingInventory_17497Line    | picking_product_17497   | 0       | 2        | PCE          |
    And complete inventory with inventoryIdentifier 'pickingInventory_17497'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier     |
      | pickingInventory_17497Line    | pickingProductHU_17497 |

    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | salesOrder_17497 | true    | pickingCustomer_17497    | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | salesOrder_17497Line | salesOrder_17497      | picking_product_17497   | 2          |

    And the order identified by salesOrder_17497 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute |
      | pickingShipmentSchedule | salesOrder_17497Line      | N             |

  @from:cucumber
  Scenario: start a fresh picking job, do the picking, complete the picking => ship the goods
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17497      | pickingCustomer_17497    | pickingCustomer_17497Location     |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | wf1                        | a1                          | line1                  | step1                  | QR                           |
    And process response and extract activityId:
      | componentType        | WorkflowActivity.Identifier |
      | common/confirmButton | CompletePickingActivity     |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | step1                  | QR                           | 2         |

    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate picking candidate for shipment schedule:
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | PickStatus |
      | pickingShipmentSchedule          | 2         | A          |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | Y      |
    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/:CompletePickingActivity/userConfirmation in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | pickingShipmentSchedule          | shipment_1            | CO            |

  @from:cucumber
  Scenario: start a fresh picking job, do the picking, log out, log back in with the same user, complete the picking => ship the goods
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17497      | pickingCustomer_17497    | pickingCustomer_17497Location     |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | wf1                        | a1                          | line1                  | step1                  | QR                           |
    And process response and extract activityId:
      | componentType        | WorkflowActivity.Identifier |
      | common/confirmButton | CompletePickingActivity     |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | step1                  | QR                           | 2         |

    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate picking candidate for shipment schedule:
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | PickStatus |
      | pickingShipmentSchedule          | 2         | A          |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | Y      |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/logout' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate workflow process
      | WorkflowProcess.Identifier | DocStatus |
      | wf1                        | DR        |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | N      |

    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/continue in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | Y      |

    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/:CompletePickingActivity/userConfirmation in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | pickingShipmentSchedule          | shipment_1            | CO            |

  @from:cucumber
  Scenario: start a fresh picking job, do the whole picking, log out, log back in with a different user, complete the picking => ship the goods
    Given metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           | OPT.EMail                | OPT.Login      | OPT.Role_ID |
      | testUser_17497        | testUser_17497 | testUser_17497@email.com | testUser_17497 | 540024      |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17497      | pickingCustomer_17497    | pickingCustomer_17497Location     |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | wf1                        | a1                          | line1                  | step1                  | QR                           |
    And process response and extract activityId:
      | componentType        | WorkflowActivity.Identifier |
      | common/confirmButton | CompletePickingActivity     |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | step1                  | QR                           | 2         |

    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code

    And validate picking candidate for shipment schedule:
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | PickStatus |
      | pickingShipmentSchedule          | 2         | A          |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | Y      |

    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/logout' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate workflow process
      | WorkflowProcess.Identifier | DocStatus |
      | wf1                        | DR        |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | N      |

    And the existing user with login 'testUser_17497' receives a random a API token for the existing role with name 'WebUI'

    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/continue in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login          | Exists |
      | pickingShipmentSchedule          | testUser_17497 | Y      |

    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/:CompletePickingActivity/userConfirmation in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | pickingShipmentSchedule          | shipment_1            | CO            |

  @from:cucumber
  Scenario: start a fresh picking job, do nothing, log out, log back in with a different user, do the whole picking, complete the picking => ship the goods
    Given metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           | OPT.EMail                | OPT.Login      | OPT.Role_ID |
      | testUser_17497        | testUser_17497 | testUser_17497@email.com | testUser_17497 | 540024      |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17497      | pickingCustomer_17497    | pickingCustomer_17497Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | wf1                        | a1                          | line1                  | step1                  | QR                           |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | Y      |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/logout' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | N      |
    And validate workflow process
      | WorkflowProcess.Identifier | DocStatus |
      | wf1                        | VO        |
    And the existing user with login 'testUser_17497' receives a random a API token for the existing role with name 'WebUI'
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17497      | pickingCustomer_17497    | pickingCustomer_17497Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier |
      | wf2                        | wf2-a1                      | wf2-line1              | wf2-step1              | wf2-QR                       |
    And process response and extract activityId:
      | componentType        | WorkflowActivity.Identifier |
      | common/confirmButton | CompletePickingActivityWf2  |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login          | Exists |
      | pickingShipmentSchedule          | testUser_17497 | Y      |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | PickingStep.Identifier | PickingStepQRCode.Identifier | QtyPicked |
      | wf2                        | wf2-a1                      | wf2-line1              | wf2-step1              | wf2-QR                       | 2         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate picking candidate for shipment schedule:
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | PickStatus |
      | pickingShipmentSchedule          | 2         | A          |
    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf2/:CompletePickingActivityWf2/userConfirmation in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | pickingShipmentSchedule          | shipment_1            | CO            |

  @from:cucumber
  Scenario: start a fresh picking job, do a partial pick, log out, log back in with a different user, do the picking, complete the picking => ship the goods
    Given metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name           | OPT.EMail                | OPT.Login      | OPT.Role_ID |
      | testUser_17497        | testUser_17497 | testUser_17497@email.com | testUser_17497 | 540024      |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | salesOrder_17497      | pickingCustomer_17497    | pickingCustomer_17497Location     |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And process response and extract activityId:
      | componentType        | WorkflowActivity.Identifier |
      | common/confirmButton | CompletePickingActivity     |
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | Y      |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier     | HUQRCode.Identifier |
      | pickingProductHU_17497 | huToPickQR          |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 1         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/logout' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login      | Exists |
      | pickingShipmentSchedule          | metasfresh | N      |
    And validate workflow process
      | WorkflowProcess.Identifier | DocStatus |
      | wf1                        | DR        |
    And the existing user with login 'testUser_17497' receives a random a API token for the existing role with name 'WebUI'
    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/continue in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    And validate M_ShipmentSchedule_Lock record for
      | M_ShipmentSchedule_ID.Identifier | Login          | Exists |
      | pickingShipmentSchedule          | testUser_17497 | Y      |
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 1         |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And store workflow endpointPath api/v2/userWorkflows/wfProcess/:wf1/:CompletePickingActivity/userConfirmation in context
    And a 'POST' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | pickingShipmentSchedule          | shipment_1            | CO            |
