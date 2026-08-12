@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00353_EDI_DESADV_InOut_Link
@ghActions:run_on_executor7
Feature: EPCIS scripted-export status — success, error and re-send flows
## Verifies the ExternalSystem_ScriptedExportConversion_Status lifecycle for the EPCIS export config:
## (a) shipment completed → invocation enqueued → /ok callback → status row Sent + roll-up M_InOut.EPCIS_ExportStatus=Sent
## (b) same path but /error callback → status row Error + AD_Issue_ID linked + roll-up Error
## (c) re-send of an errored shipment → status row flipped to Pending+IsResend=Y (single-row upsert) → Sent via /ok

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-09T10:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh is configured for One-DESADV-Per-ORDERS

    # Process the /ok and /error scripted-export callbacks SYNCHRONOUSLY (HTTP 200, not async 202) so the
    # status row is visible right after the POST. Self-contained — do not rely on a sibling feature having
    # created this config on the shared executor (SeqNo=9 takes precedence).
    And the following API_Audit_Config records are created:
      | Identifier  | SeqNo | OPT.Method | OPT.PathPrefix                       | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | wait4result | 9     | POST       | api/v2/externalsystem/externalstatus | N                     | Y                                | N                 |

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_es      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_es      | ps_es              | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_es     | pl_es          |

    And metasfresh contains M_Products:
      | Identifier | GTIN          |
      | product_es | 4060000000550 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_es                 | product_es   | 10.00    | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_es      | Y          | ps_es              | 9900000550001 |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | pi_TU_es   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | piv_TU_es          | pi_TU_es   | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType |
      | pii_TU_es       | piv_TU_es          | 0   | PM       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | pip_es                  | pii_TU_es       | product_es   | 10  | 2020-01-01 |

    # Scripted-export config with IsTriggerOnComplete=Y (triggers export when shipment is completed)
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | esConfig_es              | scriptedCfg_es                                    | M_InOut_EDI_Export_JSON          | M_InOut   |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID | Identifier        |
      | bp_es         | true                 | 9900000550001         | E                    | esConfig_es                        | edi_setting_es_bp |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30088_010
  Scenario: S30088_010 — shipment completed, invocation enqueued, /ok callback: status row Sent + roll-up Sent

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference                |
      | o_010      | true    | bp_es         | 2026-06-09  | PO_S30088_010_@Date@       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_010     | o_010      | product_es   | 10         | pip_es                  |
    When the order identified by o_010 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_010     | ol_010         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_010                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_010                | io_010     |

    # Drain async material/DESADV workpackages from shipment generation so they don't spill into
    # the next scenario in this executor (would de-calibrate sibling DESADV-aggregation tests).
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # After invocation the status row reaches Enqueued; simulate /ok success callback
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_010     | scriptedCfg_es                                    | U            |

    When the scripted-export /ok callback is posted for shipment io_010 with HTTP code 200

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
      | io_010     | scriptedCfg_es                                    | S            | N        |
    And after not more than 10s, M_InOut EPCIS_ExportStatus is:
      | M_InOut_ID | EPCIS_ExportStatus |
      | io_010     | S                  |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30088_020
  Scenario: S30088_020 — /error callback: status row Error + AD_Issue_ID linked + roll-up Error

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference                |
      | o_020      | true    | bp_es         | 2026-06-09  | PO_S30088_020_@Date@       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_020     | o_020      | product_es   | 10         | pip_es                  |
    When the order identified by o_020 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_020     | ol_020         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_020                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_020                | io_020     |

    # Drain async material/DESADV workpackages from shipment generation (avoid spilling into sibling executor tests).
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Wait for invocation to be Enqueued (AD_PInstance_ID is set) before sending error callback
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_020     | scriptedCfg_es                                    | U            |

    # Simulate /error callback (reuses ExternalSystem_Error_StepDef.sendErrorResponseForShipment)
    And the external system sends an error response for the shipment
      | M_InOut_ID | ErrorMessage          |
      | io_020     | EPCIS_rejected_test   |

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | HasAD_Issue |
      | io_020     | scriptedCfg_es                                    | E            | Y           |
    And after not more than 10s, M_InOut EPCIS_ExportStatus is:
      | M_InOut_ID | EPCIS_ExportStatus |
      | io_020     | E                  |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30088_030
  Scenario: S30088_030 — re-send of an errored shipment flips the single status row to Pending+IsResend=Y

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference                |
      | o_030      | true    | bp_es         | 2026-06-09  | PO_S30088_030_@Date@       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_030     | o_030      | product_es   | 10         | pip_es                  |
    When the order identified by o_030 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_030     | ol_030         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_030                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_030                | io_030     |

    # Drain async material/DESADV workpackages from shipment generation (avoid spilling into sibling executor tests).
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Wait for invocation to be Enqueued before sending error callback
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_030     | scriptedCfg_es                                    | U            |

    # First attempt ends in Error
    And the external system sends an error response for the shipment
      | M_InOut_ID | ErrorMessage        |
      | io_030     | EPCIS_error_resend  |
    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_030     | scriptedCfg_es                                    | E            |

    # Run re-send process on the errored shipment
    When M_InOut_ReSend_ScriptedExportConversion process is run for shipment io_030

    # The single status row is flipped to IsResend=Y (single-row upsert), reaching at minimum Enqueued
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
      | io_030     | scriptedCfg_es                                    | U            | Y        |

    # Simulate /ok to confirm the resend attempt reaches Sent
    When the scripted-export /ok callback is posted for shipment io_030 with HTTP code 200

    Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
      | io_030     | scriptedCfg_es                                    | S            | Y        |
    And after not more than 10s, M_InOut EPCIS_ExportStatus is:
      | M_InOut_ID | EPCIS_ExportStatus |
      | io_030     | S                  |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @Id:S30279_040
  Scenario: S30279_040 — a WhereClause that depends on the committed-complete state still matches at trigger time
    ## Reproduces the complete-time eligibility timing bug fixed in this change.
    ## The production EPCIS config gates sending on "de.metas.edi".epcis_has_events(m_inout_id), which
    ## internally requires the shipment's DocStatus IN ('CO','CL'). This scenario uses the minimal
    ## equivalent clause `docstatus IN ('CO','CL')` to isolate the timing without LU/SSCC pallet setup.
    ##
    ## Before the fix, the eligibility WhereClause was evaluated synchronously inside
    ## docValidate(AFTER_COMPLETE) — fired by MInOut.completeIt() BEFORE the engine sets+saves
    ## DocStatus='CO' — so the in-trx SQL saw the not-yet-completed row, the clause was false, and the
    ## row was recorded DontSend (N): the export never enqueued. With the matching moved to
    ## after-commit, DocStatus is committed and the row reaches Enqueued (U) (was DontSend (N) before).

    # Override the Background config with one whose match clause only holds once the shipment is committed-complete.
    # Creating it deactivates the Background's always-true scriptedCfg_es for M_InOut (table-scoped takeover).
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause            |
      | esConfig_gated           | scriptedCfg_gated                                 | M_InOut_EDI_Export_JSON          | M_InOut   | docstatus IN ('CO','CL') |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference           |
      | o_040      | true    | bp_es         | 2026-06-09  | PO_S30279_040_@Date@  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_040     | o_040      | product_es   | 10         | pip_es                  |
    When the order identified by o_040 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_040     | ol_040         | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_040                | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_040                | io_040     |

    # Drain async material/DESADV workpackages from shipment generation (avoid spilling into sibling executor tests).
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # The completed shipment matches the WhereClause (docstatus is now CO) → must be Enqueued, not DontSend.
    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | io_040     | scriptedCfg_gated                                 | U            |


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00353_EDI_DESADV_InOut_Link
  @ghActions:run_on_executor7
  @Id:S30558_040
  Scenario: S30558_040 — shared-LU pallet: first completion yields DontSend, closing completion yields Enqueued
    ## Close-gate / closer-enqueues: two orders are picked onto ONE shared physical pallet (one SSCC).
    ## The scripted-export config is gated on "de.metas.edi".epcis_has_events(m_inout_id) — a function
    ## that returns true only when the pallet is fully covered by completed (CO/CL) shipments.
    ##
    ## Flow:
    ##   1. Both picking jobs complete onto ONE shared LU (DO_NOT_CREATE policy — no auto-shipment).
    ##   2. Both shipments generated as drafts (QuantityType=P, IsCompleteShipments=false).
    ##   3. ioA completed first → epcis_has_events(ioA) = false (ioB still draft) → ExportStatus = N (DontSend).
    ##   4. ioB completed (closer) → epcis_has_events(ioB) = true (all TUs now CO) → ExportStatus = U (Enqueued).
    ##
    ## This proves the CLOSER — not the first completer — is the one enqueued for scripted EPCIS export.

    # Gated scripted-export config: WhereClause returns true only when the shared pallet is fully
    # covered by completed shipments. Overrides the Background's always-true config (table-scoped takeover).
    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion and StatusColumn:
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName | WhereClause                                  |
      | esConfig_040             | scriptedCfg_040                                   | M_InOut_EDI_Export_JSON          | M_InOut   | "de.metas.edi".epcis_has_events(m_inout_id) |

    # Dedicated BPartner with AllowConsolidateInOut=N — keeps the two orders' shipments as separate M_InOuts.
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | M_PricingSystem_ID | GLN           | AllowConsolidateInOut |
      | bp_040     | Y          | ps_es              | 9900000305584 | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier    | GLN           | C_BPartner_ID | OPT.IsBillToDefault | OPT.IsShipTo |
      | bpLoc_040     | 2900000305584 | bp_040        | true                | true         |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | EdiDESADVSendingMode | EdiDESADV_ExternalSystem_Config_ID | Identifier       |
      | bp_040        | true                 | 9900000305584         | E                    | esConfig_040                       | edi_setting_040  |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | bp_040        | product_es   |

    # HU PI: LU holds up to 20 TUs
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID  |
      | pi_LU_040   |
      | pi_TU_040   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | piv_LU_040         | pi_LU_040  | LU          | Y         |
      | piv_TU_040         | pi_TU_040  | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | pii_LU_040      | piv_LU_040         | 20  | HU       | pi_TU_040         |
      | pii_TU_040      | piv_TU_040         | 0   | MI       |                   |
    And metasfresh contains M_HU_PI_Attribute:
      | M_HU_PI_Version_ID | M_Attribute.Value |
      | piv_LU_040         | SSCC18            |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | pip_LU_TU_040           | pii_TU_040      | product_es   | 10  | 2020-01-01 |

    # DO_NOT_CREATE: no shipment is auto-created on picking job completion;
    # both picking jobs finish before any shipment is generated.
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU
    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | slot_040   | 300.0       | Y         |
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob | IsAlwaysSplitHUsEnabled |
      | Y                   | DO_NOT_CREATE        | Y                                  | N                       |

    # Source stock: 150 PCE (5 TUs for order A + 10 TUs for order B)
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv_040        | 2026-06-09   | warehouseStd   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_040        | invLine_040        | product_es   | 0       | 150      | PCE          |
    And complete inventory with inventoryIdentifier 'inv_040'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID      |
      | invLine_040        | pickFromCU_040 |

    And transform CU to new LU
      | sourceCU       | newLU                    | TU_PI_ID  | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU_040 | pickFromAggregatedLU_040 | pi_TU_040 | 10          | 15          |

    # Order A — 50 PCE → 5 TUs. Distinct POReference.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oA_040     | true    | bp_040        | 2026-06-09  | 3058400001  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_040    | oA_040     | product_es   | 50         | pip_LU_TU_040           |

    When the order identified by oA_040 is completed

    # Order B — 100 PCE → 10 TUs. Distinct POReference.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | oB_040     | true    | bp_040        | 2026-06-09  | 3058400002  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_040    | oB_040     | product_es   | 100        | pip_LU_TU_040           |

    When the order identified by oB_040 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssA_040    | olA_040        | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssB_040    | olB_040        | N             |

    # ─── Picking job 1: order A → new shared LU ──────────────────────────────────────
    And start picking job for sales order identified by oA_040
    And scan picking slot identified by slot_040
    And set picking target as new LU identified by pi_LU_040
    And pick lines
      | PickingLine.byProduct | PickFromHU               | QtyPicked |
      | product_es            | pickFromAggregatedLU_040 | 5         |
    And expect current picking target
      | Existing_LU |
      | sharedLu_040 |
    And complete picking job

    # ─── Picking job 2: order B → SAME LU (all 15 TUs on one physical pallet) ────────
    And start picking job for sales order identified by oB_040
    And scan picking slot identified by slot_040
    And set picking target as existing LU identified by sharedLu_040
    And pick lines
      | PickingLine.byProduct | PickFromHU               | QtyPicked |
      | product_es            | pickFromAggregatedLU_040 | 10        |
    And complete picking job

    # Stamp a deterministic SSCC18 on the shared LU for traceability.
    And M_HU_Attribute is changed
      | M_HU_ID      | M_Attribute_ID.Value | Value              |
      | sharedLu_040 | SSCC18               | 987654321000003060 |

    # ─── Both-drafts flow: generate DRAFT for ssA, then separately for ssB ──────────
    # QuantityType=P: each schedule claims its own picked TUs without completing the shipment.
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssA_040               | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ssA_040               | ioA_040    |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ssB_040               | P            | false               | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ssB_040               | ioB_040    |

    # ─── Complete ioA first — ioB is still a draft ────────────────────────────────────
    # epcis_has_events(ioA) = false: order B's 10 TUs are covered only by a draft shipment (not CO/CL).
    # The gate evaluates false → scripted export records DontSend (N) for ioA.
    And the shipment identified by ioA_040 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | ioA_040    | scriptedCfg_040                                   | N            |

    # ─── Complete ioB — closing completion; all TUs now covered by CO shipments ───────
    # epcis_has_events(ioB) = true: pallet is fully closed → gate passes → Enqueued (U).
    And the shipment identified by ioB_040 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
      | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus |
      | ioB_040    | scriptedCfg_040                                   | U            |
