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
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlan1  | mobileWorkflow                | finProd                 | bomVersion1                              | false        |

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
      | M_InventoryLine_ID | M_HU_ID |
      | invLine1           | huComp1 |
      | invLine2           | huComp2 |

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
      | M_InventoryLine_ID | M_HU_ID     |
      | invLineUnrelated   | huUnrelated |

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

  @from:cucumber
  Scenario: TC-D5 — Component stocked in PCE, BOM line demands kg: on-the-fly schedule issues whole stocking units (exact multiple)
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    # comp1Prod is stocked in PCE (Background) but this BOM demands it in kg, 1 PCE = 2 kg.
    # A separate finished product + BOM is used so the Background's bom1 (already completed) stays untouched.
    # BOM demand: QtyBOM(20 kg) * QtyEntered(5) = 100 kg required -> 100 / 2 = 50 PCE needed (exact, no rounding).
    # The scanned HU holds 100 PCE (per Background), well above the 50 PCE needed, so the cap is not hit.
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | comp1Prod               | PCE                    | KGM                  | 2            |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | finProdKg  | PCE      |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bomKg      | finProdKg    | bomVersionKg              |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | C_UOM_ID.X12DE355 | ValidFrom  | QtyBatch |
      | bomLineKg  | bomKg                        | comp1Prod               | KGM               | 2021-01-02 | 20       |
    And the PP_Product_BOM identified by bomKg is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlanKg | mobileWorkflow                | finProdKg               | bomVersionKg                             | false        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder6               | MOP         | finProdKg               | 5          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlanKg                            |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder6               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder6               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    # Scan the HU holding comp1Prod in PCE (100 PCE, per Background)
    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp1            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '200' status code

    # The on-the-fly schedule must be created in comp1Prod's STOCKING UOM (PCE), as whole units covering the
    # 100 kg BOM demand: 100 / 2 = 50 PCE exactly. It must NOT carry the HU's full 100 PCE nor the BOM's kg UOM.
    Then verify PP_Order_IssueSchedule:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_HU_ID.Identifier | QtyToIssue |
      | ppOrder6               | comp1Prod               | huComp1            | 50 PCE     |

  @from:cucumber
  Scenario: TC-D6 — On-the-fly rounds the demand UP to whole stocking units and caps at the scanned HU (over-issue defect)
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    # comp3Prod is stocked in PCE; 1 PCE = 20 kg.
    # BOM demands 34.5 kg of comp3Prod for a 1-unit order -> 34.5 / 20 = 1.725 PCE -> rounded UP = 2 whole PCE.
    # The scanned HU holds 3 whole PCE (= 60 kg available), which EXCEEDS the 2 PCE (40 kg) needed.
    # Correct behaviour: issue schedule = 2 PCE (rounded-up whole units). Buggy behaviour: issues the FULL HU (3 PCE).
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 |
      | comp3Prod   | PCE      |
      | finProdOver | PCE      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | comp3Prod               | PCE                    | KGM                  | 20           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bomOver    | finProdOver  | bomVersionOver            |
    And metasfresh contains PP_Product_BOMLines
      | Identifier  | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | C_UOM_ID.X12DE355 | ValidFrom  | QtyBatch |
      | bomLineOver | bomOver                      | comp3Prod               | KGM               | 2021-01-02 | 34.5     |
    And the PP_Product_BOM identified by bomOver is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier   | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlanOver | mobileWorkflow                | finProdOver             | bomVersionOver                           | false        |

    # Stock comp3Prod as an HU holding 3 whole PCE (= 60 kg available, over the 40 kg / 2 PCE needed)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | invOver        | 2026-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | invOver        | invLineOver        | comp3Prod               | 0       | 3        | PCE          |
    And complete inventory with inventoryIdentifier 'invOver'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLineOver        | huComp3 |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder7               | MOP         | finProdOver             | 1          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlanOver                          |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder7               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder7               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    # Scan the HU holding 3 PCE of comp3Prod
    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp3            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '200' status code

    # The schedule must issue 2 whole PCE (34.5 kg demand rounded UP to whole stocking units), in the STOCKING UOM (PCE).
    # It must NOT issue the full scanned HU (3 PCE / 60 kg). This proves BOTH defects:
    # wrong UOM (kg instead of PCE) AND over-issue (full HU 3 PCE instead of the rounded-up 2 PCE demand).
    Then verify PP_Order_IssueSchedule:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_HU_ID.Identifier | QtyToIssue |
      | ppOrder7               | comp3Prod               | huComp3            | 2 PCE      |

  @from:cucumber
  Scenario: TC-D7 — Sub-0.5 fractional demand still rounds UP to a whole stocking unit (locks round-UP, not HALF_UP)
    And set MobileUI_MFG_Config IsAllowIssuingAnyHU to 'Y'

    # comp4Prod is stocked in PCE; 1 PCE = 10 kg.
    # BOM demands 12 kg of comp4Prod for a 1-unit order -> 12 / 10 = 1.2 PCE.
    # 1.2 is BELOW the 0.5 rounding boundary, so HALF_UP would give 1 PCE — only round-UP gives 2 PCE.
    # This scenario therefore passes ONLY with round-UP, locking the behaviour against a future HALF_UP change.
    # The scanned HU holds 3 whole PCE (= 30 kg available), well over the 2 PCE needed, so the cap is not hit.
    And metasfresh contains M_Products:
      | Identifier  | X12DE355 |
      | comp4Prod   | PCE      |
      | finProdKg2  | PCE      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | comp4Prod               | PCE                    | KGM                  | 10           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bomKg2     | finProdKg2   | bomVersionKg2             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | C_UOM_ID.X12DE355 | ValidFrom  | QtyBatch |
      | bomLineKg2 | bomKg2                       | comp4Prod               | KGM               | 2021-01-02 | 12       |
    And the PP_Product_BOM identified by bomKg2 is completed

    And metasfresh contains PP_Product_Plannings
      | Identifier  | OPT.AD_Workflow_ID.Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | prodPlanKg2 | mobileWorkflow                | finProdKg2              | bomVersionKg2                            | false        |

    # Stock comp4Prod as an HU holding 3 whole PCE (= 30 kg available, over the 20 kg / 2 PCE needed)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | invRoundUp     | 2026-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | invRoundUp     | invLineRoundUp     | comp4Prod               | 0       | 3        | PCE          |
    And complete inventory with inventoryIdentifier 'invRoundUp'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | invLineRoundUp     | huComp4 |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument | OPT.PP_Product_Planning_ID.Identifier |
      | ppOrder8               | MOP         | finProdKg2              | 1          | testResource             | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | 2026-03-20T23:59:00.00Z | Y                | prodPlanKg2                           |
    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppOrder8               |
    And create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:
      | PP_Order_ID.Identifier |
      | ppOrder8               |
    And the metasfresh REST-API endpoint path 'api/v2/userWorkflows/wfProcess/start' receives a 'POST' request with the payload from context and responds with '200' status code

    # Scan the HU holding 3 PCE of comp4Prod
    And create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:
      | WorkflowProcess.Identifier | M_HU_ID.Identifier |
      | from_last_response         | huComp4            |
    And the metasfresh REST-API endpoint path 'api/v2/manufacturing/issueSchedule/createOnTheFly' receives a 'POST' request with the payload from context and responds with '200' status code

    # 12 kg demand / 10 kg per PCE = 1.2 PCE -> round UP -> 2 whole PCE, in the STOCKING UOM (PCE).
    # HALF_UP would round 1.2 down to 1 PCE — so a green result here is only possible with RoundingMode.UP.
    Then verify PP_Order_IssueSchedule:
      | PP_Order_ID.Identifier | M_Product_ID.Identifier | M_HU_ID.Identifier | QtyToIssue |
      | ppOrder8               | comp4Prod               | huComp4            | 2 PCE      |
