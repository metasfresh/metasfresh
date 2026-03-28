@from:cucumber
@gh28943
@ghActions:run_on_executor7
@allure.label.epic:E0160_Manufacturing_Execution
@allure.label.feature:F8030_MobileUI_Manufacturing
@F8030
Feature: Manufacturing Mobile UI - On-the-fly issue schedule creation

  ## F8030: MobileUI Manufacturing
  ## gh#28943: When IsAllowIssuingAnyHU=Y, users can scan any HU and the system creates
  ## a PP_Order_IssueSchedule on-the-fly via POST /manufacturing/issueSchedule/createOnTheFly

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-20T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | comp1Prod  | PCE      |
      | comp2Prod  | PCE      |
      | finProd    | PCE      |
      | unrelated  | PCE      |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom1       | finProd      | bomVersion1               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bomLine1   | bom1                         | comp1Prod               | 2021-01-02 | 10       |
      | bomLine2   | bom1                         | comp2Prod               | 2021-01-02 | 20       |
    And the PP_Product_BOM identified by bom1 is completed

    And load AD_Workflow:
      | AD_Workflow_ID.Identifier | Name                   |
      | mobileWorkflow            | mobileUI_workflow_test |
    And metasfresh contains PP_Product_Plannings
      | Identifier  | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan1   | mobileWorkflow                | finProd                 | bomVersion1                              | false        |

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540011        |

    # Create HUs with stock for components
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv1           | 2026-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv1           | invLine1           | comp1Prod               | 0       | 100      | PCE          |
      | inv1           | invLine2           | comp2Prod               | 0       | 200      | PCE          |
    And complete inventory with inventoryIdentifier 'inv1'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID   |
      | invLine1           | huComp1   |
      | invLine2           | huComp2   |

  # ========================================================================
  # TC-B3 runs FIRST — before any IsAllowIssuingAnyHU=Y config is created.
  # The default config (no MobileUI_MFG_Config record) means IsAllowIssuingAnyHU=false.
  # ========================================================================
  @from:cucumber
  Scenario: TC-B3 — Error: on-the-fly blocked when IsAllowIssuingAnyHU=N
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'N'

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder3               | MOP         | finProd                 | 5          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlan1                             |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder3               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder3               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    # Call on-the-fly — should be rejected (422) because IsAllowIssuingAnyHU defaults to false
    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp1            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '422' status code

  @from:cucumber
  Scenario: TC-B1 — Happy path: on-the-fly schedule creation for matching HU
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder1               | MOP         | finProd                 | 5          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlan1                             |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder1               |

    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder1               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    # Call on-the-fly endpoint with comp1's HU QR code
    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp1            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '200' status code

    # Verify schedule was created for this specific PP_Order + HU
    Then verify PP_Order_IssueSchedule:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_HU_ID.Identifier | SeqNo |
      | ppOrder1               | comp1Prod               | huComp1            | 10    |

  @from:cucumber
  Scenario: TC-B2 — Error: HU product does not match any BOM line
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | invUnrelated   | 2026-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | invUnrelated   | invLineUnrelated   | unrelated               | 0       | 50       | PCE          |
    And complete inventory with inventoryIdentifier 'invUnrelated'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID      |
      | invLineUnrelated   | huUnrelated  |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder2               | MOP         | finProd                 | 5          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlan1                             |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder2               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder2               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huUnrelated        |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '422' status code

  @from:cucumber
  Scenario: TC-D4 — Multi-BOM-line: on-the-fly matches correct BOM line
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder4               | MOP         | finProd                 | 5          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlan1                             |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder4               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder4               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp2            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '200' status code

    Then verify PP_Order_IssueSchedule:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_HU_ID.Identifier | SeqNo |
      | ppOrder4               | comp2Prod               | huComp2            | 10    |

  @from:cucumber
  Scenario: TC-B4 — Error: scan inactive/destroyed HU
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder5               | MOP         | finProd                 | 5          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlan1                             |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder5               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder5               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    # Destroy the HU
    And set M_HU status:
      | M_HU_ID.Identifier | HUStatus |
      | huComp1            | D        |

    # Call on-the-fly with destroyed HU — should fail
    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp1            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '422' status code

    # Reset HU status for other tests
    And set M_HU status:
      | M_HU_ID.Identifier | HUStatus |
      | huComp1            | A        |
