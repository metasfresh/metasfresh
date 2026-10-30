@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor7
Feature: MobileUI Picking - server-side over-delivery ceiling when over-pick is blocked

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-18T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier         | Name                   | Value                   | OPT.Description               | OPT.IsActive |
      | overPickPricingSys | overPickPricingSysName | overPickPricingSysValue | overPickPricingSysDescription | true         |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | overPickPriceList | overPickPricingSys            | DE                        | EUR                 | overPickPriceList | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier         | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | overPickPriceListV | overPickPriceList         | overPickPriceListV | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier      | Name            |
      | overPickProduct | overPickProduct |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | overPickPrice | overPickPriceListV                | overPickProduct         | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier       | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | overPickCustomer | overPickCustomer | N            | Y              | overPickPricingSys            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN         | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | overPickCustomerLocation | overPickGLN | overPickCustomer         | true                | true         |

    # stock of 20, so that the source HU is still pickable after the first pick of 10 (always split)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | overPickInventory         | 2021-10-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | overPickInventory         | overPickInventoryLine         | overPickProduct         | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'overPickInventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | overPickInventoryLine         | overPickStockHU    |

    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | overPickOrder | true    | overPickCustomer         | 2021-04-15  |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | overPickOrderLine | overPickOrder         | overPickProduct         | 10         |
    And the order identified by overPickOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID.Identifier | IsToRecompute |
      | overPickShipmentSchedule | overPickOrderLine         | N             |
    And metasfresh contains M_PickingSlot:
      | Identifier   | PickingSlot | IsDynamic |
      | overPickSlot | 063.4       | Y         |

  @from:cucumber
  @allure.label.epic:E0105_Picking
  @allure.label.feature:F00230_MobileUI_Picking
  @Id:S33201_10
  Scenario: Over-pick blocked: a repeat pick on an already fully picked line is refused and not booked again
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | IsAlwaysSplitHUsEnabled | IsShowConfirmationPromptWhenOverPick |
      | Y                   | Y                       | N                                    |
    And create JsonWFProcessStartRequest for picking and store it in context as request payload:
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier |
      | overPickOrder         | overPickCustomer         | overPickCustomerLocation          |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code
    And process response and extract picking step and main HU picking candidate:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier |
      | wf1                        | a1                          | line1                  |
    And scan M_PickingSlot for PickingJob
      | WorkflowProcess.Identifier | M_PickingSlot_ID.Identifier |
      | wf1                        | overPickSlot                |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier | HUQRCode.Identifier |
      | overPickStockHU    | huToPickQR          |

    # first pick: the full ordered qty
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 10        |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '200' status code
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by overPickShipmentSchedule
      | QtyPicked |
      | 10        |

    # second pick on the same, already full line: refused (PICK_OVERDELIVERY_BLOCKED, user validation error => 422)
    And create JsonPickingEventsList and store it in context as request payload:
      | WorkflowProcess.Identifier | WorkflowActivity.Identifier | PickingLine.Identifier | HUQRCode.Identifier | QtyPicked |
      | wf1                        | a1                          | line1                  | huToPickQR          | 10        |
    And the metasfresh REST-API endpoint path 'api/v2/picking/event' receives a 'POST' request with the payload from context and responds with '422' status code

    # no second booking
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by overPickShipmentSchedule
      | QtyPicked |
      | 10        |
